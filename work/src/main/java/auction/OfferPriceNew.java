package auction;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 描述信息
 *
 * @author chengwei11
 * @date 2019/10/21
 */
public class OfferPriceNew {
    ExecutorService parallelExecuteExecutor = new ThreadPoolExecutor(100, 100, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(8888), new ThreadFactoryBuilder()
            .setNameFormat("parallelExecuteService-thread-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    private OfferLimiter<String> offerLimiter = new OfferLimiter<>();
    private CopyOnWriteArrayList<Integer> set = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        OfferPriceNew offerPriceNew = new OfferPriceNew();
        offerPriceNew.test();
    }

    private OfferLimiter.OfferAction<String> offerAction = param -> {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    public void test() {
        Long auctionId = 10234L;
        for (int i = 0; i < 500; i++) {
            parallelExecuteExecutor.execute(() -> {
                Random random = new Random();
                Integer value = random.nextInt(1000);
                set.add(value);

                offerLimiter.execute(new OfferLimiter.OfferElement<String>(auctionId, value, String.valueOf(value), offerAction));
            });
        }

        parallelExecuteExecutor.shutdown();
        while (true) {
            if (parallelExecuteExecutor.isTerminated()) {
                List<Integer> treeSet = new ArrayList<>();
                treeSet.addAll(set);
                treeSet.sort((o1, o2) -> {
                    return o2.compareTo(o1);
                });
                System.out.println(String.format("出价列表=%s", treeSet));
                break;
            }
        }
    }
}
