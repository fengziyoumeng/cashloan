package com.rongdu.cashloan.manage.job_new;

import com.rongdu.cashloan.cl.domain.ClFlowInfo;
import com.rongdu.cashloan.cl.service.ClFlowInfoService;
import com.rongdu.cashloan.cl.service.ClFlowUVService;
import com.rongdu.cashloan.core.common.exception.ServiceException;
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
import tool.util.DateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Lazy(value = false)
public class MonthQuartz
        implements Job
{
    private static final Logger logger = LoggerFactory.getLogger(MonthQuartz.class);

    public void execute(JobExecutionContext context)
            throws JobExecutionException
    {
        QuartzInfoService quartzInfoService = (QuartzInfoService) BeanUtil.getBean("quartzInfoService");
        QuartzLogService quartzLogService = (QuartzLogService) BeanUtil.getBean("quartzLogService");
        QuartzLog quartzLog = new QuartzLog();
        Map<String,Object> quartzInfoData = new HashMap<>();
        QuartzInfo quartzInfo = quartzInfoService.findByCode("monthQuartzUV");//查询出表中monthQuartz对应的QuartzInfo对象
        try {
            quartzInfoData.put("id", quartzInfo.getId());
            quartzInfoData.put("succeed", quartzInfo.getSucceed()+1);

            String remark = executeContent();//执行内容
            quartzLog.setQuartzId(quartzInfo.getId());
            quartzLog.setStartTime(DateUtil.getNow());
            quartzLog.setTime(DateUtil.getNow().getTime()-quartzLog.getStartTime().getTime());
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
        String monthQuartz = "monthQuartz:"+DateUtils.getNowMonth();
        boolean flag = redisClient.setnx("monthQuartz",monthQuartz);
        if(!flag){//已存在返回false
            return "monthQuartz未执行，monthQuartz存在缓存";
        }
        redisClient.expire("monthQuartz",12*60*60);

        logger.info("开始启动MonthQuartz...");
        logger.info("===>开始执行每月点击数更新数据库操作...");

        ClFlowUVService clFlowUVService = (ClFlowUVService)BeanUtil.getBean("clFlowUVService");
        ClFlowInfoService clFlowInfoService = (ClFlowInfoService)BeanUtil.getBean("clFlowInfoService");

        Date newDate = new Date();
        List infos = clFlowInfoService.getAllInfo();

        for (int i = 0; i < infos.size(); i++) {
            ClFlowInfo info = (ClFlowInfo)infos.get(i);

            Map param = new HashMap();
            param.put("countDate", DateUtils.getPrevious());
            param.put("flowId", info.getId());
            Long pPreviousMonthClick = clFlowUVService.getPreviousMonthClick(param);
            Long pPreviousMonthClick1 = info.getpPreMonthClickCount1();
            Long pPreviousMonthClick2 = info.getPPreMonthClickCount2();

            info.setpPreMonthClickCount(Long.valueOf(0L));
            info.setpPreMonthClickCount1(pPreviousMonthClick);
            info.setPPreMonthClickCount2(Long.valueOf(pPreviousMonthClick1 == null ? 0L : pPreviousMonthClick1.longValue()));
            info.setPPreMonthClickCount3(Long.valueOf(pPreviousMonthClick2 == null ? 0L : pPreviousMonthClick2.longValue()));
            info.setUpdateTime(newDate);

            clFlowInfoService.update(info);
        }
        logger.info("===>每月点击数更新数据库操作完成");
        return "每月点击数更新数据库操作完成";
    }
}