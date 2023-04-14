package com.czyl.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.czyl.framework.datasource.DynamicDataSourceStrategy;
import com.czyl.framework.datasource.LoadBalanceDynamicDataSourceStrategy;

/**
 * 数据源策略
 * @author tanghx
 *
 */
@Configuration
public class DynamicDataSourceStrategyConfig {

	
	@Bean
	public DynamicDataSourceStrategy dynamicDataSourceStrategy() {
		return new LoadBalanceDynamicDataSourceStrategy();
	}
}
