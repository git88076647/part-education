package com.czyl.common.lock;

import java.util.Set;

/**
 * <pre>
 * 分布式锁接口 
 * 例如： 
 * RETRY_TIMES=100
 * SLEEP_MILLIS=100 
 * RETRY_TIMES * SLEEP_MILLIS = 10000 
 * 意味着如果一直获取不了锁 最长会等待10秒后返回失败
 * 
 * 默认实现为com.czyl.framework.redis.RedisDistributedLock
 * 如果想重写锁的实现可实现接口并使用 @Primary 注解
 *</pre>
 * @author tanghx
 */
public interface DistributedLock {

	public static final String LOCK_PREX="diLock:";
	
	/**
	 * 默认失效时间 ms (1小时)
	 */
	long EXPIRE_MILLIS =  60 * 60 * 1000;

	/**
	 * 重试次数
	 */
	int RETRY_TIMES = 100;

	/**
	 * 每次重试后等待的时间 ms
	 */
	long SLEEP_MILLIS = 100;

	/**
	 * 获取锁
	 *
	 * @param key key
	 * @return 成功/失败
	 */
	boolean lock(String key);
	
	/**
	 * 尝试获取锁不重试
	 * @param key
	 * @return 成功/失败
	 */
	boolean tryLock(String key);
	
	/**
	 * 尝试获取锁不重试
	 * @param key
	 * @param message 失败则抛出异常
	 */
	void tryLock(String key,String message);
	
	/**
	 * 尝试获取锁不重试
	 * @param key
	 * @param expire 失效时间ms(超过这个时间会自动释放锁)
	 * @param message 失败则抛出异常
	 */
	void tryLock(String key,long expire,String message);

	/**
	 * 获取锁
	 *
	 * @param key        key
	 * @param retryTimes 重试次数,每次间隔100ms
	 * @return 成功/失败
	 */
	boolean lock(String key, int retryTimes);

	/**
	 * 获取锁
	 *
	 * @param key         key
	 * @param retryTimes  重试次数
	 * @param sleepMillis 获取锁失败的重试间隔 ms
	 * @return 成功/失败
	 */
	boolean lock(String key, int retryTimes, long sleepMillis);

	/**
	 * 获取锁
	 *
	 * @param key    key
	 * @param expire 锁失效时间 ms
	 * @return 成功/失败
	 */
	boolean lock(String key, long expire);

	/**
	 * 获取锁
	 *
	 * @param key        key
	 * @param expire     锁失效时间 ms
	 * @param retryTimes 重试次数
	 * @return 成功/失败
	 */
	boolean lock(String key, long expire, int retryTimes);

	/**
	 * 获取锁
	 *
	 * @param key         key
	 * @param expire      锁失效时间 ms
	 * @param retryTimes  重试次数
	 * @param sleepMillis 获取锁失败的重试间隔 ms
	 * @param message 全部失败后抛出异常
	 */
	void lock(String key, long expire, int retryTimes, long sleepMillis,String message);
	/**
	 * 获取锁
	 *
	 * @param key         key
	 * @param expire      锁失效时间 ms
	 * @param retryTimes  重试次数
	 * @param sleepMillis 获取锁失败的重试间隔 ms
	 * @return 成功/失败
	 */
	boolean lock(String key, long expire, int retryTimes, long sleepMillis);

	/**
	 * 释放锁
	 *
	 * @param key key值
	 * @return 释放结果
	 */
	boolean releaseLock(String key);
	
	/**
	 * 强制释放锁
	 *
	 * @param key key值
	 * @return 释放结果
	 */
	boolean releaseLockForce(String key);
	
	/**
	 * 获取keys
	 * keys(*key*)
	 * @param key key值
	 * @return
	 */
	Set<Object> getKeys(String key);

	/**
	 * 是否存在锁
	 * @param key
	 * @return
	 */
	boolean hasLock(String key);
}
