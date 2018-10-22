package com.wangxuan.tool.lock;

/**
 * @author wangxuan
 */
public class RedisServiceThread extends Thread {

    private RedisService redisService;

    public RedisServiceThread(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public void run() {
        redisService.seckill();
    }

}
