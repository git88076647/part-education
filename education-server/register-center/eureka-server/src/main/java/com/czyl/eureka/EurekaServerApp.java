package com.czyl.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;


/** 
* @author tanghx
* 类说明 
* eureka高可用三台机器(2*N+1)
*/
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApp {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EurekaServerApp.class, args);
//		new SpringApplicationBuilder(EurekaServerApp.class).run(args);
//    	服务器采用此方法 java -jar   --spring.profiles.active=slave3;
    	
		System.out.println("(♥◠‿◠)ﾉﾞ  "+context.getApplicationName() +"启动成功   ლ(´ڡ`ლ)ﾞ  \n");
	}

}