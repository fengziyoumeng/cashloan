package com.rongdu.cashloan.manage.job_new;

import com.rongdu.cashloan.cl.domain.AccountRecord;
import com.rongdu.cashloan.cl.service.ClFlowInfoService;
import com.rongdu.cashloan.cl.service.ClFlowUVService;
import com.rongdu.cashloan.cl.service.IAccountRecordService;
import com.rongdu.cashloan.cl.util.FileUtil;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import com.rongdu.cashloan.core.common.util.DateUtil;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import com.rongdu.cashloan.manage.domain.QuartzInfo;
import com.rongdu.cashloan.manage.domain.QuartzLog;
import com.rongdu.cashloan.manage.job.DateUtils;
import com.rongdu.cashloan.manage.service.QuartzInfoService;
import com.rongdu.cashloan.manage.service.QuartzLogService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import tool.util.BeanUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Lazy(value = false)
public class UpdateRemRepDayQuartz
        implements Job
{
    private static final Logger logger = LoggerFactory.getLogger(UpdateRemRepDayQuartz.class);

    public void execute(JobExecutionContext context)
            throws JobExecutionException
    {
        QuartzInfoService quartzInfoService = (QuartzInfoService) BeanUtil.getBean("quartzInfoService");
        QuartzLogService quartzLogService = (QuartzLogService) BeanUtil.getBean("quartzLogService");
        QuartzLog quartzLog = new QuartzLog();
        Map<String,Object> quartzInfoData = new HashMap<>();
        QuartzInfo quartzInfo = quartzInfoService.findByCode("updateRemRepDayQuartz");//查询出表中todayQuartz对应的QuartzInfo对象
        try {
            quartzInfoData.put("id", quartzInfo.getId());
            quartzInfoData.put("succeed", quartzInfo.getSucceed()+1);

            String remark = executeContent();//执行内容
            quartzLog.setQuartzId(quartzInfo.getId());
            quartzLog.setStartTime(tool.util.DateUtil.getNow());
            quartzLog.setTime(tool.util.DateUtil.getNow().getTime()-quartzLog.getStartTime().getTime());
            quartzLog.setResult("10");//成功状态是10
            quartzLog.setRemark(remark);
        }catch (Exception e) {
            quartzInfoData.put("fail", quartzInfo.getFail()+1);
            quartzLog.setResult("20");//失败状态是20
            logger.error(e.getMessage(),e);
        }finally{
            logger.info("更新定时任务数据");
            quartzInfoService.update(quartzInfoData);
            logger.info("保存定时任务日志");
            quartzLogService.save(quartzLog);
        }
    }

    private String executeContent() throws ServiceException {
        ShardedJedisClient redisClient = (ShardedJedisClient)BeanUtil.getBean("redisClient");
        String updateRemRepDayQuartz = "updateRemRepDayQuartz:"+ DateUtils.getNowDateHm();
        boolean flag = redisClient.setnx("updateRemRepDayQuartz",updateRemRepDayQuartz);
        if(!flag){//已存在返回false
            return "updateRemRepDayQuartz未执行，updateRemRepDayQuartz存在缓存";
        }
        redisClient.expire("updateRemRepDayQuartz",12*60*60);

        logger.info("开始启动UpdateRemainDayQuartz...");
        logger.info("===>开始执行每天更新记账表剩余还款日操作...");

        IAccountRecordService accountRecordService = (IAccountRecordService)BeanUtil.getBean("IAccountRecordService");
        Map<String,Object> paramsMap = new HashMap<>();
        List<AccountRecord> accountRecords = accountRecordService.listAccRecords(paramsMap);
        if(accountRecords!=null && accountRecords.size()>0){
            for(AccountRecord accountRecord : accountRecords){
                int repayDay = Integer.parseInt(FileUtil.StringToNumber(accountRecord.getRepayDay()));//还款日
                int currentDay = DateUtil.getDay(new Date());
                if(currentDay>repayDay){//当前日期>还款日
                    accountRecord.setRemainRepayDay("当前已超出预期还款日，请还款！");
                }else{
                    accountRecord.setRemainRepayDay(String.valueOf(repayDay-currentDay));
                }
            }
            accountRecordService.batchUpdateRemRepDay(accountRecords);
        }
        logger.info("===>执行每天更新记账表剩余还款日操作完成");
        return "每天更新记账表剩余还款日操作完成";
    }
}