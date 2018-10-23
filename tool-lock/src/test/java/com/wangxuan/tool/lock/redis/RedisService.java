package com.wangxuan.tool.lock.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author wangxuan
 */
public class RedisService {

    private static JedisPool pool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        //设置最大连接数
        config.setMaxTotal(200);
        //设置最大空闲数
        config.setMaxIdle(8);
        //设置最大等待时间
        config.setMaxWaitMillis(1000 * 100);
        //在borrow一个jedis实例时，是否需要验证
        //若为true，则所有实例均是可用的
        config.setTestOnBorrow(true);
        pool = new JedisPool(config, "172.16.36.71", 6379, 3000);
    }

    RedisDistributedLock lock = new RedisDistributedLock(pool);
    int n = 500;

    public void secondKill0() {
        System.out.println(Thread.currentThread().getName());
        System.out.println(--n);
    }

    public void secondKill2() {
        String lockName = "resource";
        String identifier = lock.lockWithTimeout(lockName, 5000, 1000);
        System.out.println(Thread.currentThread().getName() + "获得了锁，锁返回的标识符：" + identifier);
        System.out.println(--n);
        lock.releaseLock(lockName, identifier);
    }

    public void secondKill() {
        String lockName = "resource";
        String identifier = lock.lockWithTimeout(lockName, 5000, 1000);
        if (identifier != null && !"".equals(identifier)) {
            System.out.println(Thread.currentThread().getName() + "获得了锁，锁返回的标识符：" + identifier);
            System.out.println(--n);
            while (true) {
                boolean flag = lock.releaseLock(lockName, identifier);
                if (flag) break;
            }
        } else {
            System.out.println(Thread.currentThread().getName() + "获得锁失败");
        }
    }

    public void secondKill3() {
        String lockName = "resource";
        String identifier = lock.lockWithTimeout(lockName, 5000, 1000);
        if (identifier != null && !"".equals(identifier)) {
            System.out.println(Thread.currentThread().getName() + "获得了锁，锁返回的标识符：" + identifier);
            System.out.println(--n);
            boolean flag = lock.releaseLock(lockName, identifier);
            if (!flag) lock.releaseLock(lockName, identifier);
        } else {
            System.out.println(Thread.currentThread().getName() + "获得锁失败");
        }
    }

}
