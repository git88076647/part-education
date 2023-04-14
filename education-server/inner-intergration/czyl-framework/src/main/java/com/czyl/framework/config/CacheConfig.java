package com.czyl.framework.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 装配CacheKeyTTL
 * @author tanghx
 *
 */
@Configuration
@PropertySource(value = {"classpath:cache/cache.properties","classpath:cache/default.properties"})
@ConfigurationProperties(prefix = "czyl")
public class CacheConfig {

	private Map<String, Long> cache = new LinkedHashMap<>();

	public Map<String, Long> getCache() {
		return cache;
	}

	public void setCache(Map<String, Long> cache) {
		this.cache = cache;
	}
	
	

}
