import harry.redisconn.RedisHandler;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.concurrent.CountDownLatch;

/**
 * Created with IntelliJ IDEA.
 * User: chenyehui
 * Date: 13-11-12
 * Time: 上午11:24
 * To change this template use File | Settings | File Templates.
 */
public class RedisShoot {

    @Test
    public void shootInfo() {
//        long begin =
//        Jedis jedis = new Jedis("127.0.0.1");
//        jedis.set("a", "v");

    }


    static CountDownLatch latch = new CountDownLatch(2000);

    public static void concurrent() {


        for (int i = 0; i < 2000; i++) {
            final int t = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RedisHandler.initJedis(RedisHandler.IP,RedisHandler.PORT);
                    Jedis jedis = RedisHandler.pool.getResource();
                    for (int j = 0; j < 100; j++) {
                        System.out.println("Thread:" + t + "  Number:" + j + "  Result:" + jedis.get(StringUtils.leftPad(String.valueOf(4321), 42, "0")));
                    }
                    RedisHandler.pool.returnResource(jedis);
                    latch.countDown();
                }
            }).start();
        }
    }

//    public static void main(String[] args) {
//        long begin = System.currentTimeMillis();
//        concurrent();
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(String.valueOf(System.currentTimeMillis() - begin) + "ms");
//    }
}
