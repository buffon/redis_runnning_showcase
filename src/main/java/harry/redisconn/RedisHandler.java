package harry.redisconn;

import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.CountDownLatch;

/**
 * Created with IntelliJ IDEA.
 * User: chenyehui
 * Date: 13-11-11
 * Time: 下午8:37
 * To change this template use File | Settings | File Templates.
 */
public class RedisHandler {

    public static JedisPool pool;
    public static final String IP = "127.0.0.1";
    public static final int PORT = 6379;

    public static String initJedis(String ip, int port) {
        JedisPoolConfig config = new JedisPoolConfig();
        //config.setMaxActive(100);
        config.setMaxWait(5000);
        try {
            pool = new JedisPool(config, ip, port);
        } catch (Exception e) {
            e.printStackTrace();
            return "change server failed";
        }
        return "change server success";
    }

    private static Jedis getJedis() {
        return pool.getResource();
    }

    private static void releaseJedis(Jedis jedis) {
        pool.returnResource(jedis);
    }

    public static String setValue(String key, String value) {
        Jedis jedis = getJedis();
        String result = jedis.set(stringFull(key), value);
        releaseJedis(jedis);
        return result;

    }

    public static String getValue(String key) {
        Jedis jedis = getJedis();
        String result = jedis.get(stringFull(key));
        releaseJedis(jedis);
        return result;
    }

    public static String collectinfo() {

        Jedis jedis = getJedis();
        String info = jedis.info().replaceAll("used_memory_human", "<b>used_memory_human</b>");
        releaseJedis(jedis);
        return info;

    }

    public static long dbsize() {
        Jedis jedis = getJedis();
        long dbsize = jedis.dbSize();
        releaseJedis(jedis);
        return dbsize;
    }

    public static String init(long number) {
        Jedis jedis = getJedis();
        jedis.flushDB();
        try {
            for (int i = 0; i < number; i++) {
                String key = stringFull(String.valueOf(i));
                jedis.set(key, valueFull(key));
                System.out.println(i + " inserted.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jedis.flushDB();
            releaseJedis(jedis);
            return "insert fail,Db is empty";
        }
        releaseJedis(jedis);
        return "insert success";
    }

    public static String stringFull(String s) {
        if (s.length() > 42) {
            return s.substring(0, 42);
        } else {
            return StringUtils.leftPad(s, 42, "0");
        }
    }

    public static String valueFull(String value) {
        return value.substring(value.length() - 15, value.length());
    }

    public static String concurrent(int number, final int operation) {
        long begin = System.currentTimeMillis();
        final CountDownLatch latch = new CountDownLatch(number);
        for (int i = 0; i < number; i++) {
            final int t = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Jedis jedis = getJedis();
                    for (int j = 0; j < operation; j++) {
                        if (j % 2 == 0) {
                            jedis.get(stringFull("54321"));
                        } else {
                            jedis.set(stringFull(t + "_" + j), t + "" + j);
                        }
                    }
                    System.out.println("Thread " + t + " is over!");
                    releaseJedis(jedis);
                    latch.countDown();
                }
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {

        }
        return (System.currentTimeMillis() - begin) + "ms";
    }

}
