package spring.schedule;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import util.DateUtil;

import java.util.Date;

@Component
@EnableScheduling
public class TaskTest {

    @Scheduled(cron = "0/5 * * * * ?")
    public void work() {
        Date date = DateUtil.getNowDate();
        for (int i = 0; i < 1000000; i++) {

        }
        System.out.println(date);
    }

}
