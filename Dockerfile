# 基础镜像
FROM openjdk:11

# 作者
LABEL authors="alone"

# 设置工作目录
WORKDIR /work/app

# 拷贝文件到容器中
COPY ./target/spring-boot-demo.jar /work/app/spring-boot-demo.jar

# 设置环境变量
ENV TZ=Asia/Shanghai
ENV JAVA_OPS="-Dspring.profiles.active=dev"

# 启动执行命令
ENTRYPOINT java $JAVA_OPS -jar spring-boot-demo.jar