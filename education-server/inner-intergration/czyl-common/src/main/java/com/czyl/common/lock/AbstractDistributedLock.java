package com.czyl.common.lock;

import com.czyl.common.exception.CustomException;

/**
 * 分布式锁抽象类
 *
 * @author tanghx
 */
public abstract class AbstractDistributedLock implements DistributedLock {

	@Override
	public boolean lock(String key) {
		return abstractLock(key, EXPIRE_MILLIS, RETRY_TIMES, SLEEP_MILLIS);
	}

	@Override
	public boolean tryLock(String key) {
		return abstractLock(key, EXPIRE_MILLIS, 0, 0);
	}

	@Override
	public void tryLock(String key, String message) {
		if (tryLock(key) == false) {
			throw new CustomException(message);
		}
	}

	@Override
	public void tryLock(String key, long expire, String message) {
		if (abstractLock(key, expire, 0, 0) == false) {
			throw new CustomException(message);
		}
	}

	@Override
	public boolean lock(String key, int retryTimes) {
		return abstractLock(key, EXPIRE_MILLIS, retryTimes, SLEEP_MILLIS);
	}

	@Override
	public boolean lock(String key, int retryTimes, long sleepMillis) {
		return abstractLock(key, EXPIRE_MILLIS, retryTimes, sleepMillis);
	}

	@Override
	public boolean lock(String key, long expire) {
		return abstractLock(key, expire, RETRY_TIMES, SLEEP_MILLIS);
	}

	@Override
	public boolean lock(String key, long expire, int retryTimes) {
		return abstractLock(key, expire, retryTimes, SLEEP_MILLIS);
	}

	@Override
	public void lock(String key, long expire, int retryTimes, long sleepMillis, String message) {
		if (abstractLock(key, expire, retryTimes, sleepMillis) == false) {
			throw new CustomException(message);
		}
	}

	boolean abstractLock(String key, long expire, int retryTimes, long sleepMillis) {
		return lock(extractedKey(key), expire, retryTimes, sleepMillis);
	}
	
	public String extractedKey(String key) {
		if (key == null || key.startsWith(LOCK_PREX) == false) {
			key = LOCK_PREX + key;
		}
		return key;
	}

}
