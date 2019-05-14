package auction;

import util.HttpUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述信息
 *
 * @author chengwei11
 * @date 2019/5/14
 */
public class AuctionTest {
    private static ExecutorService executor;

    public static void main(String[] args) {
        concurrentTest(20, () -> {
            detailTest(114430786);
        });

    }

    public static void concurrentTest(int threadCount, Runnable task) {
        executor = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
            executor.submit(task);
        }
    }

    private static final String DETAIL = "http://pp-order-used.jd.local/auction/detail?auctionId=";

    private static void detailTest(long auctionId) {
        long start = System.currentTimeMillis();
        try {
            String content = HttpUtil.get(DETAIL + auctionId, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(String.format("拍卖明细接口耗时：%s ms", System.currentTimeMillis() - start));
    }
}
