import harry.redisconn.RedisHandler;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: chenyehui
 * Date: 13-11-12
 * Time: 下午12:00
 * To change this template use File | Settings | File Templates.
 */
public class UtilTest {

    @Test
    public void testSub(){
        RedisHandler.initJedis(RedisHandler.IP,RedisHandler.PORT);
        RedisHandler.concurrent(50,10);
    }
}
