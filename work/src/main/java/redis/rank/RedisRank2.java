package redis.rank;

import com.google.common.collect.Lists;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Set;

public class RedisRank2 {
    private static Jedis jedis = new Jedis("127.0.0.1", 6379);

    public Long incrByStep(String key, String memberKey, int step) {
        //distributed lock
        Double score = jedis.zincrby(key, step, memberKey);
        long updateTime = System.currentTimeMillis();
        Double scoreWithTime = score.longValue() + 1 - updateTime / Math.pow(10, (int) Math.log10(updateTime) + 1);
        jedis.zadd(key, scoreWithTime, memberKey);
        //distributed unlock
        return score.longValue();
    }

    public Tuple getRankMax(String key) {
        Set<Tuple> result = jedis.zrevrangeWithScores(key, 0, 0);
        if (result == null) {
            return new Tuple(key, 0D);
        } else {
            Tuple value = result.iterator().next();
            return new Tuple(key, Double.valueOf((long) value.getScore()));
        }
    }

    public static void testRank() {
        RedisRank2 redisRank = new RedisRank2();
        String key = "activity1";
        redisRank.incrByStep(key, "vip1", getRandomCount());
        redisRank.incrByStep(key, "vip2", getRandomCount());
        redisRank.incrByStep(key, "vip3", getRandomCount());
        System.out.println(doubleToString(Double.valueOf(redisRank.getRankMax(key).getScore())));
        System.out.println(redisRank.getRankMax(key).getElement());

        showAll(key);
    }

    private static void showAll(String key) {
        Set<Tuple> result = jedis.zrevrangeWithScores(key, 0, -1);
        Iterator<Tuple> iterator = result.iterator();
        while (iterator.hasNext()) {
            Tuple t = iterator.next();
            System.out.println("key:" + t.getElement() + ",value:" + doubleToString(t.getScore()));
        }

    }

    private static int getRandomCount() {
        return (int) (Math.random() * 2) + 1;
    }

    private static void initValue() {
        jedis.zadd("activity1", 0, "vip1");
        jedis.zadd("activity1", 0, "vip2");
        jedis.zadd("activity1", 0, "vip3");
    }

    private static String doubleToString(Double d) {
        if (d == null) {
            return "";
        }
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        return nf.format(d);
    }

    public static void main(String[] args) {
        //initValue();
        testRank();
    }
}
