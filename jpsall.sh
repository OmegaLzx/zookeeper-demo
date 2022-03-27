user=$(whoami)
#5 循环
for host in {node1,node2,node3};
do
  echo --------------- "$host" ----------------
  ssh "$user"@"$host" "/opt/module/zookeeper-3.8.0/bin/zkServer.sh start"
done
