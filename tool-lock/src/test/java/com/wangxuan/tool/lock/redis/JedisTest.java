package com.wangxuan.tool.lock.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * @author wangxuan
 */
@RunWith(JUnit4.class)
public class JedisTest {

    private Jedis conn;

    @Before
    public void init() {
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
        JedisPool pool = new JedisPool(config, "172.16.36.71", 6379, 3000);
        conn = pool.getResource();
        System.out.println("初始化成功");
    }

    @After
    public void close() {
        conn.close();
        System.out.println("关闭连接成功");
    }

    @Test
    public void keys() {
        Set<String> keys = conn.keys("*");
        System.out.println(keys);
        keys.forEach(key -> System.out.println(conn.get(key)));
    }

    @Test
    public void get() {
        String value = conn.get("lock:resource");
        System.out.println(value);
    }

}
