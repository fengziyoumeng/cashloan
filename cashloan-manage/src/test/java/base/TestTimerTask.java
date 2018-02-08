package base;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TestTimerTask extends TimerTask{

    @Override
    public void run() {
        System.out.println("dingshi-mark");
    }


    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TestTimerTask(),0);
//        timer.cancel();
    }
}
