package redis.rank;

import com.google.common.collect.Lists;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import util.GsonUtil;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Set;

public class RedisRank {
    private static Jedis jedis = new Jedis("127.0.0.1", 6379);

    private static final long MAX_SUPPLY_END_TIME_MILLIS = 2208960000000L;//apply to 2040-01-01 00:00:00
    private static final long VALUE_BASE_FACTOR = 1000000000000L;
    private static final String TIME_KEY_SUFFIX = "_with_time";
    private static final String RANK_SAVE_SCRIPT = "local score = redis.call('zincrby',KEYS[1],ARGV[2],ARGV[1]);redis.call('zadd',KEYS[1]..'" + TIME_KEY_SUFFIX + "',score * " + VALUE_BASE_FACTOR + "+ARGV[3],ARGV[1]);return score;";

    private static Long getLeftTime() {
        return MAX_SUPPLY_END_TIME_MILLIS - System.currentTimeMillis();
    }

    public Long incrByStep(String key, String memberKey, int step) {
        Object value = jedis.eval(RANK_SAVE_SCRIPT,
                Lists.<String>newArrayList(key),
                Lists.<String>newArrayList(memberKey, String.valueOf(step), String.valueOf(getLeftTime())));
        if (value == null) {
            return 0L;
        }
        return Long.parseLong(value.toString());
    }

    public Long getRankMaxValue(String key) {
        Set<Tuple> result = jedis.zrevrangeWithScores(key, 0, 0);
        if (result == null) {
            return 0L;
        }
        return Double.valueOf(result.iterator().next().getScore()).longValue();
    }

    public String getRankMaxMemberKey(String key) {
        Set<Tuple> result = jedis.zrevrangeWithScores(key + TIME_KEY_SUFFIX, 0, 0);
        if (result == null) {
            return null;
        }
        return result.iterator().next().getElement();
    }

    public static void testRank() {
        RedisRank redisRank = new RedisRank();
        String key = "activity1";
        redisRank.incrByStep(key, "vip1", getRandomCount());
        redisRank.incrByStep(key, "vip2", getRandomCount());
        redisRank.incrByStep(key, "vip3", getRandomCount());
        System.out.println(doubleToString(Double.valueOf(redisRank.getRankMaxValue(key))));
        System.out.println(redisRank.getRankMaxMemberKey(key));

        showAll(key);
    }

    private static void showAll(String key) {
        Set<Tuple> result = jedis.zrevrangeWithScores(key + TIME_KEY_SUFFIX, 0, -1);
        Iterator<Tuple> iterator = result.iterator();
        while (iterator.hasNext()) {
            Tuple t = iterator.next();
            System.out.println("key:" + t.getElement() + ",value:" + doubleToString(t.getScore()));
        }

    }

    private static int getRandomCount() {
        return (int) (Math.random() * 2)+1;
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
