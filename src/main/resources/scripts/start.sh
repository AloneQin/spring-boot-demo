#!/bin/bash
#
# Program:
#	Program start spring-boot-demo by java command.
#
# Histroy:
#	2021-02-22 17:49:36	alone	First release

# 执行任何语句出错则退出
set -e

nohup java -jar spring-boot-demo.jar > server.log 2>&1 &
echo -e "Starting, output log file name: server.log"
sleep 1s
tail -f server.log