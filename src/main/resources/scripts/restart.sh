#!/bin/bash
#
# Program:
#	Program kill spring-boot-demo java process and start spring-boot-demo by java command.
#
# Histroy:
#	2021-02-23 18:57:21	alone	First release

# 执行任何语句出错则退出
set -e

./stop.sh
sleep 1
./start.sh