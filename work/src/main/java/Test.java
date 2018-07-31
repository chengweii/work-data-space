import java.util.*;

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
		System.out.println(7&3);
		System.out.println(new Long(4234234453453645645L));
		for(int i=430;i<800;i++){
			System.out.println("http://mp3-2.ting89.com:9090/2017/35/%E8%AF%A1%E7%A5%9E%E5%86%A2/"+i+".mp3");
		}
		Map<String,String> map = new HashMap<>();
		System.out.println(map.size());
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
