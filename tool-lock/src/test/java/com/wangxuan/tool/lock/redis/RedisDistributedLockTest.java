package com.wangxuan.tool.lock.redis;

/**
 * @author wangxuan
 */
public class RedisDistributedLockTest {

    public static void main(String[] args) {
        RedisService redisService = new RedisService();
        for (int i = 0; i < 50; i++) {
            Thread thread = new Thread(redisService::secKill);
            thread.start();
        }
    }

}
