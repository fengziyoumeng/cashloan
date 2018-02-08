package base;

import org.quartz.*;

public class CronTask implements Job{

    public static int num;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("cronTask-mark: "+num);
        num ++;
    }

}
