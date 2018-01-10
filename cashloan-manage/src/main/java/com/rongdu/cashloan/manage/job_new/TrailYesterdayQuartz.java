package com.rongdu.cashloan.manage.job_new;

import com.alibaba.fastjson.JSON;
import com.rongdu.cashloan.cl.domain.ClickTrack;
import com.rongdu.cashloan.cl.service.IClickTrackService;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import com.rongdu.cashloan.core.common.util.DateUtil;
import com.rongdu.cashloan.core.constant.AppConstant;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Lazy(value = false)
public class TrailYesterdayQuartz
        implements Job
{
    private static final Logger logger = LoggerFactory.getLogger(TrailYesterdayQuartz.class);

    public void execute(JobExecutionContext context)
            throws JobExecutionException
    {
        QuartzInfoService quartzInfoService = (QuartzInfoService) BeanUtil.getBean("quartzInfoService");
        QuartzLogService quartzLogService = (QuartzLogService) BeanUtil.getBean("quartzLogService");
        QuartzLog quartzLog = new QuartzLog();
        Map<String,Object> quartzInfoData = new HashMap<>();
        QuartzInfo quartzInfo = quartzInfoService.findByCode("trailYesterdayQuartz");//查询出表中trailYesterdayQuartz对应的QuartzInfo对象
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
        String trailYesterdayQuartz = "trailYesterdayQuartz:"+ DateUtils.getNowDateHm();
        boolean flag = redisClient.setnx("trailYesterdayQuartz",trailYesterdayQuartz);
        if(!flag){//已存在返回false
            return "trailYesterdayQuartz未执行，trailYesterdayQuartz存在缓存";
        }
        redisClient.expire("trailYesterdayQuartz",12*60*60);

        logger.info("开始启动trailYesterdayQuartz...");
        logger.info("===>开始执行每天更新userId浏览页面轨迹操作...");

        IClickTrackService clickTrackService = (IClickTrackService)BeanUtil.getBean("clickTrackService");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String date = calendar.get(1) + "-" + (calendar.get(2) + 1) + "-" + calendar.get(5);
//        String date = DateUtil.getNowDate();
        Map<String, String> map = redisClient.hgetAll(AppConstant.REDIS_KEY_CLICK_TRACK+date);
        for (String key : map.keySet()) {
            String value = map.get(key);
            System.out.println("=="+value+"===");
            ClickTrack clickTrack = JSON.parseObject(value,ClickTrack.class);
            clickTrackService.save(clickTrack);
        }
        logger.info("===>执行每天更新userId浏览页面轨迹操作完成");
        return "每天更新userId浏览页面轨迹操作完成";
    }
}