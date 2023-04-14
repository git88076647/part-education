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
public class JobApplication {
	
	
	public static void main(String[] args) {
//		SpringApplication app = new SpringApplication(LogApplication.class);
//		app.addListeners(new PortApplicationEnvironmentPreparedEventListener());
//		ConfigurableApplicationContext context=app.run(args);
		ConfigurableApplicationContext context = SpringApplication.run(JobApplication.class, args);
		System.out.println("(♥◠‿◠)ﾉﾞ  "+context.getId() +"启动成功   ლ(´ڡ`ლ)ﾞ  \n");
	}

}
