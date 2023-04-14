package com.czyl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
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
public class IsmApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(IsmApplication.class, args);
		System.out.println("(♥◠‿◠)ﾉﾞ  "+context.getId() +"启动成功   ლ(´ڡ`ლ)ﾞ  \n");
	}
}
