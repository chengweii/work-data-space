package concurrent;

import java.util.concurrent.*;

/**
 * 描述信息
 *
 * @author chengwei11
 * @date 2019/4/17
 */
public class FutureTest {
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public FutureTest() {
    }

    public static void main(String[] args) {
        Future future = executorService.submit(() -> {
            try {
                System.out.println("开始任务");
                sleep(3000);
                System.out.println("结束任务");
            } catch (Throwable t) {
                System.out.println("任务异常:" + t.getMessage());
                t.printStackTrace();
            }
        });
        try {
            future.get(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            future.cancel(true);
        }
    }

    private static void sleep(long millseconds) {
        long start = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - start > millseconds) {
                break;
            }
        }
    }
}
