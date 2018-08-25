package redis;

import com.google.common.collect.Lists;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.*;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JdeisTest {
    private Jedis jedis;

    public static void main(String[] args) {
        JdeisTest test = new JdeisTest();
        test.setup();
        test.testString();
        //concurrentJedisPoolTest();
        //testShardedJedis();
    }

    public static void concurrentJedisPoolTest() {
        int scheduleCount = 1000;

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMinIdle(10);
        poolConfig.setMaxTotal(20);
        poolConfig.setMaxWaitMillis(3000);
        poolConfig.setTestOnBorrow(true);

        JedisPool jedisPool1 = new JedisPool(poolConfig, "127.0.0.1", 6379);

        JedisPool jedisPool2 = new JedisPool(poolConfig, "127.0.0.1", 6380);


        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < scheduleCount; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        long start = System.currentTimeMillis();

                        try (Jedis client = jedisPool1.getResource()) {
                            client.set("testkey1", "ddddd1");
                            //System.out.println(client.get("testkey1"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try (Jedis client = jedisPool2.getResource()) {
                            client.set("testkey2", "ddddd2");
                            //System.out.println(client.get("testkey2"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        System.out.println("cost:" + (System.currentTimeMillis() - start));

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        executorService.shutdown();
    }

    public static void testJedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMinIdle(10);
        poolConfig.setMaxTotal(20);
        poolConfig.setMaxWaitMillis(3000);
        poolConfig.setTestOnBorrow(true);

        JedisPool jedisPool = new JedisPool(poolConfig, "127.0.0.1");
        try (Jedis client = jedisPool.getResource()) {
            client.set("testJedisPool", "testJedisPool");
            System.out.println(client.get("testJedisPool"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testShardedJedis() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxWaitMillis(3000);
        genericObjectPoolConfig.setMaxTotal(100);
        genericObjectPoolConfig.setMaxIdle(100);
        genericObjectPoolConfig.setTestOnBorrow(true);

        List<JedisShardInfo> shards = Lists.newArrayList();
        shards.add(new JedisShardInfo("127.0.0.1", 6379, 3000));
        shards.add(new JedisShardInfo("127.0.0.1", 6380, 3000));

        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(genericObjectPoolConfig,shards);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 20000; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try (ShardedJedis jedis = shardedJedisPool.getResource()) {
                        Collection<Jedis> jedisList = jedis.getAllShards();
                        for (Jedis client : jedisList) {
                            try {
                                String result = client.set("testShardedJedis", client.getDB().toString(), "NX", "PX", 30000);
                                System.out.println(result);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
    }

    public static void testJedisCluster() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxWaitMillis(1000);
        genericObjectPoolConfig.setMaxTotal(20);
        genericObjectPoolConfig.setMaxIdle(20);
        genericObjectPoolConfig.setTestOnBorrow(true);

        HostAndPort r1 = new HostAndPort("127.0.0.1", 6379);
        HostAndPort r2 = new HostAndPort("127.0.0.1", 6380);
        Set<HostAndPort> haps = new HashSet<HostAndPort>();
        haps.add(r1);
        haps.add(r2);

        JedisCluster jedis = new JedisCluster(haps, genericObjectPoolConfig);
        jedis.set("testJedisCluster", "testJedisCluster");
        System.out.println(jedis.get("testJedisCluster"));
    }

    public void setup() {
        //连接redis服务器，192.168.0.100:6379
        jedis = new Jedis("127.0.0.1", 6379);
        //权限认证
        //jedis.auth("admin");
    }

    public void testeval() {
        jedis.eval("return redis.call('set','first','hell word !!!')");
        System.out.println(jedis.get("first"));
    }

    /**
     * redis存储字符串
     */
    public void testString() {
        //-----添加数据----------  
        jedis.set("name", "xinxin");//向key-->name中放入了value-->xinxin
        System.out.println(jedis.get("name"));//执行结果：xinxin  

        jedis.append("name", " is my lover"); //拼接
        System.out.println(jedis.get("name"));

        jedis.del("name");  //删除某个键
        System.out.println(jedis.get("name"));
        //设置多个键值对
        jedis.mset("name", "liuling", "age", "23", "qq", "476777XXX");
        jedis.incr("age"); //进行加1操作
        System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));

        jedis.zincrby("jc1001",1,"menber1");
        jedis.zincrby("jc1001",1,"menber2");
        Set<Tuple> set = jedis.zrevrangeWithScores("jc1001",0,0);
        System.out.println(set);
        System.out.println(jedis.zscore("jc1001","menber1"));
        System.out.println(jedis.zscore("jc1001","menber2"));
    }

    /**
     * redis操作Map
     */
    public void testMap() {
        //-----添加数据----------  
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "xinxin");
        map.put("age", "22");
        map.put("qq", "123456");
        jedis.hmset("user", map);
        //取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List  
        //第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数  
        List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
        System.out.println(rsmap);

        //删除map中的某个键值  
        jedis.hdel("user", "age");
        System.out.println(jedis.hmget("user", "age")); //因为删除了，所以返回的是null  
        System.out.println(jedis.hlen("user")); //返回key为user的键中存放的值的个数2 
        System.out.println(jedis.exists("user"));//是否存在key为user的记录 返回true  
        System.out.println(jedis.hkeys("user"));//返回map对象中的所有key  
        System.out.println(jedis.hvals("user"));//返回map对象中的所有value 

        Iterator<String> iter = jedis.hkeys("user").iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            System.out.println(key + ":" + jedis.hmget("user", key));
        }
    }

    /**
     * jedis操作List
     */
    public void testList() {
        //开始前，先移除所有的内容  
        jedis.del("java framework");
        System.out.println(jedis.lrange("java framework", 0, -1));
        //先向key java framework中存放三条数据  
        jedis.lpush("java framework", "spring");
        jedis.lpush("java framework", "struts");
        jedis.lpush("java framework", "hibernate");
        //再取出所有数据jedis.lrange是按范围取出，  
        // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有  
        System.out.println(jedis.lrange("java framework", 0, -1));

        jedis.del("java framework");
        jedis.rpush("java framework", "spring");
        jedis.rpush("java framework", "struts");
        jedis.rpush("java framework", "hibernate");
        System.out.println(jedis.lrange("java framework", 0, -1));
    }

    /**
     * jedis操作Set
     */
    public void testSet() {
        //添加  
        jedis.sadd("user", "liuling");
        jedis.sadd("user", "xinxin");
        jedis.sadd("user", "ling");
        jedis.sadd("user", "zhangxinxin");
        jedis.sadd("user", "who");
        //移除noname  
        jedis.srem("user", "who");
        System.out.println(jedis.smembers("user"));//获取所有加入的value  
        System.out.println(jedis.sismember("user", "who"));//判断 who 是否是user集合的元素  
        System.out.println(jedis.srandmember("user"));
        System.out.println(jedis.scard("user"));//返回集合的元素个数  
    }

    public void test() throws InterruptedException {
        //jedis 排序  
        //注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）  
        jedis.del("a");//先清除数据，再加入数据进行测试
        jedis.rpush("a", "1");
        jedis.lpush("a", "6");
        jedis.lpush("a", "3");
        jedis.lpush("a", "9");
        System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
        System.out.println(jedis.sort("a")); //[1, 3, 6, 9]  //输入排序后结果  
        System.out.println(jedis.lrange("a", 0, -1));
    }

}
