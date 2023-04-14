FROM openjdk:8-jre-alpine
#生产环境自己调整大小或镜像
ENV JAVA_OPTS="-Xms128m -Xmx128m"
ENV TZ=Asia/Shanghai
EXPOSE 8101
#字体
RUN apk add --update font-adobe-100dpi ttf-dejavu fontconfig
RUN mkdir -p /home/czyl
RUN mkdir -p /home/czyl/logs
RUN mkdir -p /home/czyl/temp

WORKDIR /home/czyl
ADD ./target/file-center.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
VOLUME /home/czyl