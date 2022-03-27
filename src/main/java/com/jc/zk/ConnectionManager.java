package com.jc.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

@Slf4j
public class ConnectionManager {
    private final String CONNECT_STRING = "node1:2181,node2:2181,node3:2181";
    private final int SESSION_TIMEOUT = 2000;
    private ZooKeeper zooKeeper;

    public ZooKeeper connect() throws IOException {
        zooKeeper = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, watchedEvent -> getServerList());
        return zooKeeper;
    }

    public void getServerList() {
        try {
            List<String> children = zooKeeper.getChildren("/servers", true);
            children.forEach(child -> log.info("child: {}", child));

        } catch (KeeperException | InterruptedException e) {
            log.error("getServerList error", e);
        }
    }

    public void register(String serverName) {
        try {
            zooKeeper.create("/servers/" + serverName,
                    serverName.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (KeeperException | InterruptedException e) {
            log.error("register error", e);
        }
    }
}
