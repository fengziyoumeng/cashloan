package com.rongdu.cashloan.manage.job_new;

import com.rongdu.cashloan.cl.domain.ClFlowInfo;
import com.rongdu.cashloan.cl.service.ClFlowInfoService;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rongdu.cashloan.manage.domain.QuartzInfo;
import com.rongdu.cashloan.manage.domain.QuartzLog;
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

@Component
@Lazy(value = false)
public class TodayQuartz
        implements Job
{
    private static final Logger logger = LoggerFactory.getLogger(TodayQuartz.class);

    public void execute(JobExecutionContext context)
            throws JobExecutionException
    {
        QuartzInfoService quartzInfoService = (QuartzInfoService) BeanUtil.getBean("quartzInfoService");
        QuartzLogService quartzLogService = (QuartzLogService) BeanUtil.getBean("quartzLogService");
        QuartzLog quartzLog = new QuartzLog();
        Map<String,Object> quartzInfoData = new HashMap<>();
        QuartzInfo quartzInfo = quartzInfoService.findByCode("todayQuartzUV");//查询出表中todayQuartz对应的QuartzInfo对象
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
        logger.info("开始启动TodayQuartz...");
        logger.info("===>开始执行定期持久化今日数据到数据库...");
        ClFlowInfoService clFlowInfoService = (ClFlowInfoService)BeanUtil.getBean("clFlowInfoService");
        ShardedJedisClient redisClient = (ShardedJedisClient)BeanUtil.getBean("redisClient");
        List pCodes = clFlowInfoService.getAllPCode();
        for (int i = 0; i < pCodes.size(); i++) {
            ClFlowInfo info = (ClFlowInfo)pCodes.get(i);
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String temp = redisClient.get("FLOW:PALTFORM:CLICK:" + info.getPCode() + ":" + date);
            Long click = temp == null ? 0 : Long.valueOf(Long.parseLong(temp));
            logger.info(String.format("【%s】今日当前更新点击数为【%s】", new Object[] { info.getPCode(), click }));
            info.setPTodayClickCount(click);
            info.setUpdateTime(new Date());
            clFlowInfoService.update(info);
        }
        logger.info("===>结束执行定期持久化今日数据到数据库任务");
        return "执行定期持久化今日数据到数据库任务完成";
    }
}