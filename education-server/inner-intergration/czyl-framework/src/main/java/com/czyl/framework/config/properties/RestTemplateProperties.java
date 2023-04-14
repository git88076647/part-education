package com.czyl.framework.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * RestTemplate 配置
 *
 * @author tanghx
 */
@Setter
@Getter
@ConfigurationProperties(prefix = RestTemplateProperties.PREFIX )
public class RestTemplateProperties {
	
	public final static String PREFIX="czyl.rest-template";
    /**
     * 最大链接数
     */
    private int maxTotal = 200;
    /**
     * 同路由最大并发数
     */
    private int maxPerRoute = 50;
    /**
     * 读取超时时间 ms
     */
    private int readTimeout = 35000;
    /**
     * 链接超时时间 ms
     */
    private int connectTimeout = 10000;
}
