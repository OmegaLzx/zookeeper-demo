package com.jc.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class ClientApplication {
    public static void main(String[] args) {
        ConnectionManager cm = new ConnectionManager();
        try {
            ZooKeeper zk = cm.connect();
            zk.getChildren("/servers", null);
        } catch (IOException | InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }
}
