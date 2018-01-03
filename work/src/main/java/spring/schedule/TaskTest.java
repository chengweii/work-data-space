package spring.schedule;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import util.DateUtil;

@Service("taskTest")
public class TaskTest {

	public static void main(String[] args) {
		ApplicationContext appContext = new AnnotationConfigApplicationContext("spring");
		ThreadPoolTaskExecutor threadPoolTaskExecutor = appContext.getBean(ThreadPoolTaskExecutor.class);
		threadPoolTaskExecutor.setMaxPoolSize(50);
		threadPoolTaskExecutor.setCorePoolSize(20);
		threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
	}

	@Resource
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Scheduled(cron = "0/5 * * * * ?")
	public void work() {
		threadPoolTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				Date date = DateUtil.getNowDate();
				for (int i = 0; i < 1000000; i++) {

				}
				System.out.println(date);
			}
		});
	}

}
