import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Test {
	public static Timer timer = new Timer();

	public static void main(String[] args) {
		List<Integer> limitShopRangeProductIds = new ArrayList<Integer>();
		limitShopRangeProductIds.add(1);
		limitShopRangeProductIds.add(2);
		limitShopRangeProductIds.add(3);
		List<Integer> noLimitShopRangeProductIds = new ArrayList<Integer>();
		noLimitShopRangeProductIds.add(3);
		noLimitShopRangeProductIds.add(4);
		noLimitShopRangeProductIds.add(5);
		limitShopRangeProductIds.removeAll(noLimitShopRangeProductIds);
		System.out.println(limitShopRangeProductIds);
		//testTask();
		Integer a= 256;
		Integer b = 256;
		System.out.println(a==b);
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
