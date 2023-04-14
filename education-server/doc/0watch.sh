#!/bin/bash

declare -a AppNames=(eureka-server.jar zuul-server.jar auth-center.jar log-server.jar monitor-server.jar file-center.jar job-server.jar emc-server.jar )

for(( i=0;i<${#AppNames[@]};i++)) do
AppName=${AppNames[i]};

PID=`ps -ef |grep java|grep $AppName|grep -v grep|wc -l`
if [ $PID != 0 ];then
    PID=`ps -ef |grep java|grep $AppName|awk '{print $2}'`
    echo "PID: $PID , $AppName is running..."
else
    echo "$AppName is not running..."
fi
done;


