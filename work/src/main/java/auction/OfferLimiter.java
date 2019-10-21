package auction;

import com.google.common.util.concurrent.RateLimiter;
import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 出价限流器
 *
 * @author chengwei11
 * @date 2019/10/21
 */
public class OfferLimiter<T> {
    /**
     * 此处限流信息无法清除，仅作思路参考，具体实现可以采用jedis实现
     */
    private ConcurrentHashMap<Long, Limiter<T>> limiterConcurrentHashMap = new ConcurrentHashMap<>();

    public void execute(OfferElement<T> offerElement) {
        if (!limiterConcurrentHashMap.containsKey(offerElement.getId())) {
            synchronized (limiterConcurrentHashMap) {
                if (!limiterConcurrentHashMap.containsKey(offerElement.getId())) {
                    limiterConcurrentHashMap.put(offerElement.getId(), new Limiter<T>());
                }
            }
        }

        Limiter<T> limiter = limiterConcurrentHashMap.get(offerElement.getId());
        limiter.getQueue().put(offerElement);
        if (limiter.getRateLimiter().tryAcquire(1, 2, TimeUnit.SECONDS)) {
            try {
                System.out.println(String.format("当前时间=%s 出价成功=%s", new Date(), ""));
                OfferElement<T> result = limiter.getQueue().take();
                result.getOfferAction().execute(result.getParam());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FunctionalInterface
    public interface OfferAction<T> {
        /**
         * 执行动作
         *
         * @param param
         */
        void execute(T param);
    }

    @Data
    public static class Limiter<T> {
        private PriorityBlockingQueue<OfferElement<T>> queue = new PriorityBlockingQueue<>();
        private RateLimiter rateLimiter = RateLimiter.create(5);
    }

    @Data
    public static class OfferElement<T> implements Comparable<OfferElement> {
        /**
         * 主键
         */
        @Getter
        private Long id;
        /**
         * 优先级
         */
        @Getter
        private int priority;
        /**
         * 出价参数
         */
        @Getter
        private T param;
        /**
         * 出价行为
         */
        @Getter
        private OfferAction<T> offerAction;

        OfferElement(Long id,int priority, T param,OfferAction<T> offerAction) {
            this.id = id;
            this.priority = priority;
            this.offerAction = offerAction;
            this.param =param;
        }

        @Override
        public int compareTo(OfferElement o) {
            // 按照优先级大小进行排序
            return priority >= o.getPriority() ? -1 : 1;
        }
    }
}
