#!/bin/bash
#
# Program:
#	Program update spring-boot-demo by git.
#
# Histroy:
#	2021-02-22 14:21:28	alone	First release

# 执行任何语句出错则退出
set -e

read -p "Please input a branch, default is \"master\": " git_branch
if [ "$git_branch" ]
then
	echo -e "Current branch: $git_branch"
else
	git_branch=master
	echo -e "Default branch: master"
fi

echo -e "---------------------------------------------"
echo -e "Checkout project"
echo -e "---------------------------------------------"
cd spring-boot-demo
git checkout $git_branch

echo -e "---------------------------------------------"
echo -e "Pull code"
echo -e "---------------------------------------------"
git pull

echo -e "---------------------------------------------"
echo -e "Maven package"
echo -e "---------------------------------------------"
mvn clean package '-Dmaven.test.skip=true'

echo -e "---------------------------------------------"
echo -e "Transfer jar"
echo -e "---------------------------------------------"
cp target/spring-boot-demo-1.0.0-SNAPSHOT.jar ../spring-boot-demo.jar

echo -e "---------------------------------------------"
echo -e "Transfer jar"
echo -e "---------------------------------------------"

echo -e "Update success"

