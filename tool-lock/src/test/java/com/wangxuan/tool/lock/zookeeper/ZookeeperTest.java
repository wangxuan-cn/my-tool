package com.wangxuan.tool.lock.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

/**
 * @author wangxuan
 */
@RunWith(JUnit4.class)
public class ZookeeperTest {

    private String rootLock = "/locks";
    private int sessionTimeout = 30000;
    private ZooKeeper zk;

    @Before
    public void init() {
        try {
            zk = new ZooKeeper("127.0.0.1:2181", sessionTimeout, event -> System.out.println("开始监控"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void close() {
        try {
            zk.close();
            System.out.println("关闭连接成功");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void create() {
        try {
            Stat stat = zk.exists(rootLock, false);
            if (stat == null) {
                // 如果根节点不存在，则创建根节点
                zk.create(rootLock, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
