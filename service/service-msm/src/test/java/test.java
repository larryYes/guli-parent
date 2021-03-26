import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author by liuguangjin
 * @Description TODO
 * @Date 21/3/26
 */
public class test {

    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void run() {
        redisTemplate.opsForValue().set("123456", "0527", 5,TimeUnit.MINUTES);
    }
}
