package com.rongdu.cashloan.manage.job_new;

import com.rongdu.cashloan.cl.domain.PosInfo;
import com.rongdu.cashloan.cl.service.ExpressageService;
import com.rongdu.cashloan.cl.service.IPosService;
import com.rongdu.cashloan.cl.threads.SingleThreadPool;
import com.rongdu.cashloan.core.common.context.Global;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import com.rongdu.cashloan.core.constant.AppConstant;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import com.rongdu.cashloan.core.service.CloanUserService;
import com.rongdu.cashloan.manage.domain.QuartzInfo;
import com.rongdu.cashloan.manage.domain.QuartzLog;
import com.rongdu.cashloan.manage.job.DateUtils;
import com.rongdu.cashloan.manage.job.SftpUtils;
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

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Lazy(value = false)
public class PosQuartz
        implements Job
{
    private static final Logger logger = LoggerFactory.getLogger(PosQuartz.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        QuartzInfoService quartzInfoService = (QuartzInfoService) BeanUtil.getBean("quartzInfoService");
        QuartzLogService quartzLogService = (QuartzLogService) BeanUtil.getBean("quartzLogService");
        QuartzLog quartzLog = new QuartzLog();
        Map<String,Object> quartzInfoData = new HashMap<>();
        QuartzInfo quartzInfo = quartzInfoService.findByCode("posQuartz");//查询出表中posQuartz对应的QuartzInfo对象
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
        final ShardedJedisClient redisClient = (ShardedJedisClient)BeanUtil.getBean("redisClient");
        String posQuartz = "posQuartz:"+DateUtils.getNowDate();
        boolean flag = redisClient.setnx("posQuartz",posQuartz);
        if(!flag){//已存在返回false
            return "posQuartz未执行，posQuartz存在缓存";
        }
        redisClient.expire("posQuartz",12*60*60);

        SingleThreadPool.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                String date = calendar.get(1) + "-" + (calendar.get(2) + 1) + "-" + calendar.get(5);
                Map<String, String> map = redisClient.hgetAll(AppConstant.REDIS_KEY_LOGIN_TIME+date);
                CloanUserService cloanUserService = (CloanUserService)BeanUtil.getBean("cloanUserService");
                for (String key : map.keySet()) {
                    System.out.println("=="+key+"===");
                    String value = map.get(key);
                    System.out.println("=="+value+"===");
                    Map<String,Object> userMap = new HashMap<String,Object>();
                    userMap.put("id",key);
                    userMap.put("loginTime",value);
                    cloanUserService.updateLoginTime(userMap);
                }
                logger.info("===>结束执行定期持久化最近一次登录时间数据到数据库任务线程");
            }
        });

        logger.info("开始启动PosQuartz...");
        logger.info("===>开始执行每日半夜1点的POS机获取收货人信息定时器...");

        Date cDate = DateUtils.beforeDate(new Date(), 1);
        String clearPerferDate = new SimpleDateFormat("yyyyMMdd").format(cDate);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("clear_date", clearPerferDate);
        ExpressageService expressageService = (ExpressageService) BeanUtil.getBean("ExpressageService");
        List<Map<String, Object>> expressageList = expressageService.listExpressages(map);//收货人数合计
        logger.info("收货人原始数量" + expressageList.size());

        IPosService posService = (IPosService) BeanUtil.getBean("IPosService");
        List<PosInfo> posInfos = posService.listPosInfo();//pos机平台合计
        int m = posInfos.size();

        //创建m维数组，从tempArray[0]~tempArray[m-1]
        List<Map<String, Object>>[] tempArray = new List[m];
        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = new ArrayList<>();
        }
        for (int i = 0; i < expressageList.size(); i++) {
            tempArray[i % m].add(expressageList.get(i)); //平均分配到m个list，轮流一个一个填坑分
        }
        for (int i = 0; i < tempArray.length; i++) {
            logger.info("=============分配" + tempArray[i].size() + "=============");//打印出m个list的值
            if (tempArray[i].size() > 0) {
                // 生成本地文件
                String fileDirName = "/home/jjhao/uploadFiles/" + "POS_DATA/" + posInfos.get(i).getPosPlatName();
                String fileName = "POS_DATA_" + clearPerferDate + ".txt";//前一天日期文件
                File file = new File(fileDirName, fileName);
                if (file.exists()) { // 判断文件或文件夹是否存在
                    logger.info(file.getName() + " 已经存在");
                } else {
                    try {
                        file.getParentFile().mkdirs();
                        file.createNewFile(); // 创建文件或者文件夹
                        logger.info(file.getName() + " 创建成功");
                        PrintWriter pw = new PrintWriter(new FileWriter(file), true);
                        for (int a = 0; a < tempArray[i].size(); a++) {
                            pw.println(tempArray[i].get(a).get("expressageInfo").toString());
                        }
                        pw.println("ENDFLAG");
                        logger.info("====================本地文件写入成功=======================");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // 生成sftp文件
                String ftp_path = "/home/ftp/" + posInfos.get(i).getPosPlatName();
                String addr = Global.getValue("ftp_addr");
                String username = Global.getValue("ftp_username");
                String password = Global.getValue("ftp_password");
                SftpUtils.listFileNames(addr, 22, username, password, fileDirName + "/" + fileName,ftp_path);
            }
        }
        return "每日上传ftp操作完成";
    }
}