package base;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class Dispatch {

    private static Scheduler sched;
    public static void run() throws Exception{
        //创建LzstoneTimeTask的定时任务
        JobDetail jobDetail = new JobDetail("lzstoneJob","lzstone",CronTask.class);
        //目标 创建任务计划
        CronTrigger trigger = new CronTrigger("lzstoneJob","lzstone","0/2 * * * * ?");

        //0 0 12 * * ? 代表每天的中午12点触发
        sched = new org.quartz.impl.StdSchedulerFactory().getScheduler();
        sched.scheduleJob(jobDetail,trigger);
        sched.start();
    }

    //停止
    public static void stop() throws Exception{
        sched.shutdown();
    }

    public static void main(String[] args) throws Exception {
        run();
        Thread.sleep(20000);
        stop();
    }
}

