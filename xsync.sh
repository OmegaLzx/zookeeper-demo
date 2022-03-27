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
