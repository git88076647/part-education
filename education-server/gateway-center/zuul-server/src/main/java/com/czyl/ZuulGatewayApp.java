package com.czyl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @author tanghx
 */
@Configuration
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ZuulGatewayApp {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ZuulGatewayApp.class, args);
		System.out.println("(♥◠‿◠)ﾉﾞ  " + context.getId() + "启动成功   ლ(´ڡ`ლ)ﾞ  \n");
	}

}
