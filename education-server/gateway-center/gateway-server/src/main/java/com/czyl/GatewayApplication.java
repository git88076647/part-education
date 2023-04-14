package com.czyl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableAutoConfiguration
public class GatewayApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(GatewayApplication.class, args);
		System.out.println("(♥◠‿◠)ﾉﾞ  " + context.getId() + "启动成功   ლ(´ڡ`ლ)ﾞ  \n");
	}

}
