package delegate;

import redis.JedisClient;
import util.KeyGenUtils;

import java.util.function.Function;

/**
 * @author: chengwei11
 * @since: 2018/11/1 11:10
 * @description:
 */
public class CountLimitor {
    public <T, R> R limit(Function<T, R> action, T input, long limitCount, int initCount, int expireTime, String... limitKeyFactors) {
        String key = KeyGenUtils.genCacheKeys(limitKeyFactors);
        String currentCount = JedisClient.jedis.get(key);
        if (currentCount != null && Long.valueOf(currentCount) > limitCount) {
            return null;
        }

        if (!JedisClient.jedis.exists(key)) {
            JedisClient.jedis.set(key, String.valueOf(initCount));
            JedisClient.jedis.expire(key, expireTime);
        }
        Long count = JedisClient.jedis.incr(key);
        if (count > limitCount) {
            return null;
        }

        try {
            return action.apply(input);
        } catch (Throwable t) {
            JedisClient.jedis.decr(key);
            throw t;
        }
    }
}
