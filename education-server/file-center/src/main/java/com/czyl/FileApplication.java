package com.czyl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.czyl.framework.properties.FileServerProperties;

/**
 * 启动程序
 * 
 * @author tanghx
 */
@EnableTransactionManagement
@EnableConfigurationProperties(FileServerProperties.class)
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableDiscoveryClient
public class FileApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(FileApplication.class, args);
		System.out.println("(♥◠‿◠)ﾉﾞ  "+context.getId() +"启动成功   ლ(´ڡ`ლ)ﾞ  \n");
	}
}
