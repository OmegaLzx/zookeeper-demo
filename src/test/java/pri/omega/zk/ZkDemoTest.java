package pri.omega.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author zexuan.Li  2022/3/23
 */
@Slf4j
public class ZkDemoTest {
    String connectString = "node1:2181,node2:2181,node3:2181";
    int sessionTimeout = 5000;

    Watcher event = event1 -> {
        log.info("event: {}", event1);
    };

    ZooKeeper zkClient = null;

    @Before
    public void init() throws IOException {
        log.info("connecting...");
        zkClient = new ZooKeeper(connectString, sessionTimeout, event);
    }

    @Test
    public void create() throws InterruptedException, KeeperException {
        String createResult = zkClient.create("/test", "测试数据".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        log.info("create result: {}", createResult);
    }


    @Test
    public void getChildren() throws InterruptedException, KeeperException {
        List<String> children = zkClient.getChildren("/", true);
        children.forEach(System.out::println);
    }
}