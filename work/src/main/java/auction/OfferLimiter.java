package auction;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.concurrent.ExecutionException;
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
     * 本地缓存限流器是有限的，业务量比较大的场景还是通过redis实现比较靠谱
     */
    public Cache<Long, Limiter<T>> limiterCache = CacheBuilder.newBuilder()
            // 设置缓存的最大容量
            .maximumSize(5)
            // 设置缓存在写入一分钟后失效
            .expireAfterWrite(1, TimeUnit.MINUTES)
            // 设置并发级别为10
            .concurrencyLevel(10)
            // 开启缓存统计
            .recordStats()
            .build();

    public void execute(OfferElement<T> offerElement) {
        Limiter<T> limiter = null;
        try {
            limiter = limiterCache.get(offerElement.getId(),()-> new Limiter<T>());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        limiter.getQueue().put(offerElement);
        if (limiter.getRateLimiter().tryAcquire(1, 2, TimeUnit.SECONDS)) {
            try {
                OfferElement<T> result = limiter.getQueue().take();
                result.getOfferAction().execute(result.getParam());
                System.out.println(String.format("当前时间=%s 出价成功=%s", new Date(), result));
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

        OfferElement(Long id, int priority, T param, OfferAction<T> offerAction) {
            this.id = id;
            this.priority = priority;
            this.offerAction = offerAction;
            this.param = param;
        }

        @Override
        public int compareTo(OfferElement o) {
            // 按照优先级大小进行排序
            return priority >= o.getPriority() ? -1 : 1;
        }
    }
}
