import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Test {
	public static Timer timer = new Timer();

	public static void main(String[] args) {
		Test t=new Test();
		t.setEn_title("sdf");
		System.out.println(t.en_title);
		//testTask();
	}
	
	private String en_title;
	
	 public void setEn_title(String en_title) {
	        this.en_title = en_title == null ? null : en_title.trim();
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
