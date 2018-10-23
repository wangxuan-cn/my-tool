package com.wangxuan.tool.lock.zookeeper;

/**
 * @author wangxuan
 */
public class ZookeeperDistributedLockTest {

    static int n = 500;

    public static void secondKill() {
        System.out.println(--n);
    }

    public static void main(String[] args) {

        for (int i = 0; i < 50; i++) {
            Thread thread = new Thread(() -> {
                ZookeeperDistributedLock lock = null;
                try {
                    lock = new ZookeeperDistributedLock("127.0.0.1:2181", "test1");
                    lock.lock();
                    secondKill();
                    System.out.println(Thread.currentThread().getName() + "正在运行");
                } finally {
                    if (lock != null) {
                        lock.unlock();
                    }
                }
            });
            thread.start();
        }
    }

}
