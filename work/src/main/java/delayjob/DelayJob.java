package delayjob;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

/**
 * @author: chengwei11
 * @since: 2018/9/27 15:55
 * @description:
 */
public class DelayJob {
    public static void main(String[] args){
        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(1,TimeUnit.SECONDS, 1);

        TimerTask task1 = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("task 1 will run per 5 seconds");
            }
        };

        hashedWheelTimer.newTimeout(task1,10,TimeUnit.SECONDS);
    }
}
