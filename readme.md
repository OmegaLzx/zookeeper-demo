# Zookeeper学习文档

## 参数说明

~~~properties
# 客户端与服务器或者服务器与服务器之间维持心跳，也就是每一个tickTime时间就会发送一次心跳。经过心跳不只可以用来监听机器的工做状态，还能够经过心跳来控制Flower跟Leader的通讯时间，默认状况下FL的会话时常是心跳间隔的两倍
tickTime=2000
# 集群中的follower服务器(F)与leader服务器(L)之间初始链接时能容忍的最多心跳数（tickTime的数量）。
initLimit=10
# 集群中flower服务器（F）跟leader（L）服务器之间的请求和答应最多能容忍的心跳数。
syncLimit=5
# 该属性对应的目录是用来存放myid信息跟一些版本，日志，跟服务器惟一的ID信息等
dataDir=/opt/module/zookeeper-3.8.0/zkData
# 暴露服务的端口号
clientPort=2181
# the maximum number of client connections.
# increase this if you need to handle more clients
#maxClientCnxns=60
#
# Be sure to read the maintenance section of the 
# administrator guide before turning on autopurge.
#
# https://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_maintenance
#
# The number of snapshots to retain in dataDir
#autopurge.snapRetainCount=3
# Purge task interval in hours
# Set to "0" to disable auto purge feature
#autopurge.purgeInterval=1

## Metrics Providers
#
# https://prometheus.io Metrics Exporter
#metricsProvider.className=org.apache.zookeeper.metrics.prometheus.PrometheusMetricsProvider
#metricsProvider.httpHost=0.0.0.0
#metricsProvider.httpPort=7000
#metricsProvider.exportJvmInfo=true


~~~

## 集群分发脚本

- rsync远程分发命令详解

 ~~~shell
 # 基本语法
 $ rsync -rvl /sourcefile $user@remoteServer:$/targetfile
 ~~~

- 需求：循环复制文件到所有节点相同的目录下

~~~shell
# 创建文件
$ vim xsync.sh.sh

# 文件内容
#!/bin/bash
# $#：表示传递给脚本或函数的参数个数。
#1 获取输入参数个数，如果没有参数，直接退出
pCount=$#
if ((pCount == 0)); then
  echo no args
  exit
fi

#2 获取文件名称
p1=$1
fName=$(basename "$p1")
echo fName="$fName"

#3 获取上级目录到绝对路径
pDir=$(
  cd -P "$(dirname "$p1")" || exit
  pwd
)
echo pDir="$pDir"

#4 获取当前用户名称
user=$(whoami)

#5 循环
for host in {node1,node2,node3}; do
  echo --------------- "$host" ----------------
  rsync -rvl "$pDir"/"$fName" "$user"@"$host":"$pDir"
done

# 添加环境变量
$ vim /root/.bashrc

# 追加内容
export PATH=$PATH:/root/bin

# 刷新环境变量
$ source /root/.bashrc
~~~

## 部署手册

~~~shell
# 配置集群hostname
$ vim /etc/hostname
# 配置服务器ID，该配置在dataDir对应目录下
$ cd /opt/module/zookeeper-3.8.0/zkData
$ vim myid
# 在zKData目录下创建myid文件，并在第一行手动输入唯一标识
# 启动所有服务
$ ./bin/zkServer.sh start
~~~

## 节点类型

1. 持久不带序号
2. 短暂不带序号
3. 持久带序号
4. 短暂带序号

> 持久节点和非持久节点的区别在于客户端断开连接后，由该客户端创建的节点是否自动删除
>
> 是否为序号节点的区别在于同名节点会按照递增的顺序增加序号值

## Java API

### 创建节点

~~~java
~~~

