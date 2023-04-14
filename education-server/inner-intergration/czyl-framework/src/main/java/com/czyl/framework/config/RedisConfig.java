package com.czyl.framework.config;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.czyl.common.constant.CacheNameConstants;
import com.czyl.framework.config.properties.DynamicDataSourceProperties;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * redis配置
 * 
 * @author tanghx
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
	
	@Autowired
	private CacheConfig cacheConfig;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setValueSerializer(new FastJson2JsonRedisSerializer(Object.class));
		// 使用StringRedisSerializer来序列化和反序列化redis的key值
		template.setKeySerializer(new StringRedisSerializer());
		template.afterPropertiesSet();
		return template;
	}

	/**
	 * 自定义缓存key的生成策略。默认的生成策略是看不懂的(乱码内容) 通过Spring
	 * 的依赖注入特性进行自定义的配置注入并且此类是一个配置类可以更多程度的自定义配置
	 *
	 * @return
	 */
	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				return String.format("%s:%s", ObjectUtils.nullSafeToString(method), generateKey(params));
			}
		};
	}

	/**
	 * Generate a key based on the specified parameters.
	 */
	public static Object generateKey(Object... params) {
		StringBuffer sb = new StringBuffer();

		if (params != null && params.length > 0) {
			sb.append(StringUtils.arrayToCommaDelimitedString(params));
		}
		Page<Object> page = PageHelper.getLocalPage();
		if (page != null) {
			sb.append(page.toString()).append(page.getOrderBy());
		}
		return sb.toString();
	}

	/**
	 * 缓存配置管理器
	 */
	@Bean
	public CacheManager cacheManager(LettuceConnectionFactory factory) {
		// 以锁写入的方式创建RedisCacheWriter对象
		RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(factory);
		// 创建默认缓存配置对象
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10L));
		
		RedisCacheManager cacheManager = new RedisCacheManager(writer, config,getRedisCacheConfigurationMap());
		return cacheManager;
	}
	
	private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10L));
        redisCacheConfigurationMap.put(CacheNameConstants.DICT_LABEL, config.entryTtl(Duration.ofHours(1L)));
        redisCacheConfigurationMap.put(CacheNameConstants.APP_REG, config.entryTtl(Duration.ofHours(1L)));
        if(cacheConfig != null && cacheConfig.getCache() != null && cacheConfig.getCache().size() > 0) {
        	for (Map.Entry<String,Long> entry: cacheConfig.getCache().entrySet()) {
        		redisCacheConfigurationMap.put(entry.getKey(), config.entryTtl(Duration.ofMinutes(entry.getValue())));
			}
        }
        return redisCacheConfigurationMap;
    }
	
}
