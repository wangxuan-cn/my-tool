package com.wangxuan.tool.lock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author wangxuan
 */
@RunWith(JUnit4.class)
public class RedisDistributedLockTest {

    private RedisService redisService = new RedisService();

    @Test
    public void test() {
        for (int i = 0; i < 50; i++) {
            RedisServiceThread thread = new RedisServiceThread(redisService);
            thread.start();
        }
    }

}
