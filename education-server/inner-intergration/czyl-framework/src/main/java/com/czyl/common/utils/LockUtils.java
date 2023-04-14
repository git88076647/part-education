package com.czyl.common.utils;

import com.czyl.common.lock.DistributedLock;
import com.czyl.common.utils.spring.SpringUtils;

/**
 * <pre>
 * 方便使用锁
 * 
 * 需要注意的是 锁必须使用在数据库事物的前面，否则无意义。
 * 
 * 锁-->开启事物-->提交/回滚事物-->释放锁
 * 开启事物-->锁-->释放锁-->提交/回滚事物
 * </pre>
 * 
 * @author tanghx
 *
 */
public class LockUtils {

	/**
	 * 锁
	 * @return
	 */
	public static DistributedLock me() {
		return SpringUtils.getBean(DistributedLock.class);
	}
	
	/**
	 * <pre>
	 * 尝试获取锁不重试,失败则抛出异常
	 * 默认失效时间5秒
	 * </pre>
	 * @param key
	 */
	public static void lock(String key) {
		LockUtils.me().tryLock(key, "数据正在被操作,请稍后再试！");
	}
	
	/**
	 * <pre>
	 * 尝试获取锁不重试,失败则抛出异常
	 * 默认失效时间5秒
	 * </pre>
	 * @param key
	 */
	public static void lock(Long key) {
		LockUtils.me().tryLock(String.valueOf(key), "数据正在被操作,请稍后再试！");
	}
	
	/**
	 * <pre>
	 * 尝试获取锁不重试,失败则抛出异常
	 * </pre>
	 * @param key
	 * @param exprie 失效时间ms(超过这个时间会自动释放锁)
	 */
	public static void lock(Long key,long exprie) {
		LockUtils.me().tryLock(String.valueOf(key),exprie, "数据正在被操作,请稍后再试！");
	}
	
	/**
	 * 释放锁
	 * @param key
	 */
	public static boolean releaseLock(Long key) {
		return LockUtils.me().releaseLock(String.valueOf(key));
	}
	
	/**
	 * 释放锁
	 * @param key
	 */
	public static boolean releaseLock(String key) {
		return LockUtils.me().releaseLock(key);
	}
	/**
	 * 强制释放锁
	 * @param key
	 */
	public static boolean releaseLockForce(String key) {
		return LockUtils.me().releaseLockForce(key);
	}

}
