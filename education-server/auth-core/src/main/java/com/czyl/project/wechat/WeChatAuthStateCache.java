package com.czyl.project.wechat;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.czyl.framework.redis.RedisCache;

import me.zhyd.oauth.cache.AuthStateCache;


public class WeChatAuthStateCache implements AuthStateCache {

	@Autowired
	RedisCache cache;
	
	@Override
	public void cache(String key, String value) {
		cache(key,value,180_000);
	}

	@Override
	public void cache(String key, String value, long timeout) {
		cache.setCacheObject(key, value, (int)timeout/1000, TimeUnit.SECONDS);
	}

	@Override
	public String get(String key) {
		return cache.getCacheObject(key);
	}

	@Override
	public boolean containsKey(String key) {
		return cache.hasKey(key);
	}

}
