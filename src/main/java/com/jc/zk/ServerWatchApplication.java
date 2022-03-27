package com.jc.zk;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class ServerWatchApplication {
    public static void main(String[] args) throws InterruptedException {
        ConnectionManager cm = new ConnectionManager();
        try {
            ZooKeeper zk = cm.connect();
            cm.register(args[0]);
            doService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doService() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }
}
