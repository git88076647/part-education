hosts 配置 
- 127.0.0.1 register.com

开发工具IDEA或eclipse 都行 需要安装 `lombok `插件

本地启动一个无密码默认端口的redis
服务启动顺序
1. 启动 register-center\eureka-server
2. gateway-center\zuul-server
3. auth-center

非必须启动的服务

4. file-center
5. monitor-center\log-server
6. monitor-center\monitor-server
7. report-server

再通过前端项目登录



```
新增项目包规则
com.czyl.XXApplication 启动类
         XXServletInitializer 部署tomcat启动
		 common 工具常量等包
			   utils 工具类，语法糖等
			   constant 常量
			   enums 枚举
			   exception 异常
	     framework 和业务无关，又不是工具常量之类的，
		           比如自己封装的可以提供给project下多模块使用的内容
		          config springboot 引入一些依赖的配置项
				  annotaion 注解
				  aspect 注解的实现
				  
		 project
		        模块名 例如system ism 等 
				       controller 这个模块下的控制类
					   domain 这个模块下的pojo
					   mapper 这个模块下的 mapper
					   service 这个模块下的 接口
					          impl 这个模块下的接口实现
```

涉及数据库分库分表时 需注意SQL 支持情况。非全部SQL 都能支持。
例如 不支持 CASE WHEN、HAVING、UNION (ALL)、INSERT INTO XX SELECT ...，不支持 同时使用普通聚合函数和DISTINCT聚合函数 等,有限支持子查询。
详情见
https://shardingsphere.apache.org/document/current/cn/features/sharding/use-norms/sql/
		 
		 
		 

Docker 部署

构建镜像 

```shell
cd pubplatform\auth-center
docker build -t czyl/auth-center:latest .
```

```shell
cd pubplatform\file-center
docker build -t czyl/file-center:latest .
```

```shell
cd pubplatform\gateway-center\zuul-server
docker build -t czyl/zuul-server:latest .
```

```
cd pubplatform\job-center\job-server
docker build -t czyl/job-server:latest .
```

```
cd pubplatform\monitor-center\log-server
docker build -t czyl/log-server:latest .
```

```
cd pubplatform\monitor-center\monitor-server
docker build -t czyl/monitor-server:latest .
```

```
cd pubplatform\register-center\eureka-server
docker build -t czyl/eureka-server:latest .
```

