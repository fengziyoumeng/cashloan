package com.rongdu.cashloan.manage.job_new;

import com.rongdu.cashloan.cl.domain.ClFlowInfo;
import com.rongdu.cashloan.cl.domain.ClFlowUV;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Lazy(value = false)
public class YesterdayQuartz
        implements Job
{
    private static final Logger logger = LoggerFactory.getLogger(YesterdayQuartz.class);

    public void execute(JobExecutionContext context)
            throws JobExecutionException
    {
        QuartzInfoService quartzInfoService = (QuartzInfoService) BeanUtil.getBean("quartzInfoService");
        QuartzLogService quartzLogService = (QuartzLogService) BeanUtil.getBean("quartzLogService");
        QuartzLog quartzLog = new QuartzLog();
        Map<String,Object> quartzInfoData = new HashMap<>();
        QuartzInfo quartzInfo = quartzInfoService.findByCode("yesterdayQuartzUV");//查询出表中todayQuartz对应的QuartzInfo对象
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
        String yesterdayQuartz = "yesterdayQuartz:"+DateUtils.getNowDate();
        boolean flag = redisClient.setnx("yesterdayQuartz",yesterdayQuartz);
        if(!flag){//已存在返回false
            return "yesterdayQuartz未执行，yesterdayQuartz存在缓存";
        }
        redisClient.expire("yesterdayQuartz",12*60*60);

        logger.info("开始启动YesterdayQuazy...");
        logger.info("===>开始将昨日的点击数插入数据库...");

        ClFlowInfoService clFlowInfoService = (ClFlowInfoService) BeanUtil.getBean("clFlowInfoService");
        ClFlowUVService clFlowUVService = (ClFlowUVService)BeanUtil.getBean("clFlowUVService");

        List pCodes = clFlowInfoService.getAllPCodeForYesterday();

        ClFlowUV uv = new ClFlowUV();
        Date newDate = new Date();
        Date oldDate = DateUtils.getOldDay();
        String date = new SimpleDateFormat("yyyyMMdd").format(oldDate);
        for (int i = 0; i < pCodes.size(); i++) {
            ClFlowInfo info = (ClFlowInfo)pCodes.get(i);
            String temp = redisClient.get("FLOW:PALTFORM:CLICK:" + info.getPCode() + ":" + date);
            Long click = Long.valueOf(temp == null ? 0L : Long.parseLong(temp));
            logger.info(String.format("【%s】缓存中【%s】日点击数量为【%s】", new Object[] { info.getPName(), date, click }));

            Long history = info.getPHistoryTotalClickCount();
            logger.info(String.format("【%s】更新总点击数前的总点击数为【%s】", new Object[] { info.getPName(), history }));
            info.setPHistoryTotalClickCount(Long.valueOf((history == null ? 0L : history.longValue()) + click.longValue()));
            Map param = new HashMap();
            param.put("countDate", DateUtils.getNowMonth());
            param.put("flowId", info.getId());
            Long pPreviousMonthClick = clFlowUVService.getPreviousMonthClick(param);
            logger.info(String.format("【%s】当月点击数是【%s】", new Object[] { info.getPName(), Long.valueOf((pPreviousMonthClick == null ? 0L : pPreviousMonthClick.longValue()) + click.longValue()) }));
            info.setpPreMonthClickCount(Long.valueOf((pPreviousMonthClick == null ? 0L : pPreviousMonthClick.longValue()) + click.longValue()));
            info.setUpdateTime(newDate);
            clFlowInfoService.update(info);

            uv.setFlowId(info.getId());
            uv.setPCode(info.getPCode());
            uv.setCountDate(oldDate);
            uv.setClickCount(click);
            uv.setCreateTime(newDate);
            uv.setUpdateTime(newDate);
            uv.setpName(info.getPName());
            clFlowUVService.insert(uv);
        }
        logger.info("===>昨日的点击数插入数据库完成");
        return "昨日的点击数插入数据库完成";
    }
}