package com.czyl.framework.config;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.czyl.project.wechat.WeChatAuthStateCache;

import lombok.Data;
import me.zhyd.oauth.cache.AuthStateCache;

@Data
@Configuration
@ConfigurationProperties(prefix = "wechat")
@ConditionalOnProperty(name = "wechat.enabled", havingValue = "true")
public class WeChatConfig {
	
	private String clientId;
	
	private String clientSecret;
	
	private String callBackUrl;
	
	private String mainUri;
	
	private List<String> allowDomain;
	
	@Bean
	public AuthStateCache authStateCache() {
		return new WeChatAuthStateCache();
	}

}
