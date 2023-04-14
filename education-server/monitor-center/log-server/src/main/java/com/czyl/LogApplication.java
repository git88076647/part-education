package com.czyl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动程序
 * 
 * @author tanghx
 */
@EnableTransactionManagement
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableDiscoveryClient
@EnableFeignClients
public class LogApplication {
	
	
	public static void main(String[] args) {
		System.setProperty("es.set.netty.runtime.available.processors", "false");
		ConfigurableApplicationContext context = SpringApplication.run(LogApplication.class, args);
		System.out.println("(♥◠‿◠)ﾉﾞ  "+context.getId() +"启动成功   ლ(´ڡ`ლ)ﾞ  \n");
	}

}
