#!/bin/bash
#
# Program:
#	Program kill spring-boot-demo java process.
#
# Histroy:
#	2021-02-22 17:49:36	alone	First release

# 执行任何语句出错则退出
set -e

# find process id by process name
# ps -ef | grep "spring-boot-demo"：查找 spring-boot-demo 相关的进程信息
# grep -v "grep"：过滤掉包含 grep 字符的行
# awk '{print $2}'：输出第二列的内容
#pid=$(ps -ef | grep "spring-boot-demo.jar" | grep -v "grep" | awk '{print $2}')

# find port by process id
# lsof -i:8888 | awk 'END {print $2}'
# awk 'END {print $2}'：输出第二列最后一行的内容
port=8888
pid=$(lsof -i:${port} | awk 'END {print $2}')

if [ -n "$pid" ]
then
	echo -e "Kill process: $pid, listening port: $port"
	kill -9 $pid
else
	echo -e "No process is running, listening port: $port"	
fi

