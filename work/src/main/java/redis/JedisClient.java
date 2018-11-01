package redis;

import redis.clients.jedis.Jedis;

/**
 * @author: chengwei11
 * @since: 2018/11/1 11:01
 * @description:
 */
public class JedisClient {
    public static Jedis jedis;
    static{
        jedis = new Jedis("127.0.0.1", 6379);
    }
}
