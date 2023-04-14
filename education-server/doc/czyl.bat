@echo off
#rem jdk1.8 废弃了 PermSize 和 MaxPermSize 采用 MetaspaceSize 和 MaxMetaspaceSize 替代
TITLE tangzh-admin
set "JVM_OPTS=-Dname=tangzh-admin -Duser.timezone=Asia/Shanghai -Xms4G -Xmx4G -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=512M -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps  -XX:+PrintGCDetails -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC"
java %JVM_OPTS% -jar tangzh-admin.jar --spring.profiles.active=prod


