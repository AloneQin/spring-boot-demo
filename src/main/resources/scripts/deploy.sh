!/bin/bash
#
# Program:
#	Program deploy spring-boot-demo.
#
# Histroy:
#	2021-02-23 19:04:17	alone	First release

# 执行任何语句出错
set -e

echo -e "Update project"
./update.sh
sleep 1

echo -e "Restart project"
./restart.sh


