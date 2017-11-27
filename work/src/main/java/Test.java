import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Test {
	public static Timer timer = new Timer();

	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(1, 10);
		Map<String,>
		BigDecimal bigDecimal=new BigDecimal(String.valueOf(couponInfo.get("couponPrice")))
		System.out.println("");
		//testTask();
	}

	public static void testTask() {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					long sleeptime = (long) (Math.random() * 5000);
					Thread.sleep(sleeptime);
					System.out.println(sleeptime);
				} catch (Exception e) {
				}
			}
		}, 5000, 1000);
	}
}
