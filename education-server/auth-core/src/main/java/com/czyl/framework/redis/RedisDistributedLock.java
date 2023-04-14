package com.czyl.framework.redis;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.czyl.common.lock.AbstractDistributedLock;
import com.czyl.common.lock.DistributedLock;
import com.czyl.common.utils.AppContextUtils;
import com.czyl.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁实现
 *
 * @author tanghx
 */
@Slf4j
@Component
public class RedisDistributedLock extends AbstractDistributedLock {

    @Autowired()
    public RedisTemplate<Object, Object> redisTemplate;

    private ThreadLocal<byte[]> lockFlag = new TransmittableThreadLocal<>();

    private static String pid = "";
    private static final String UNLOCK_LUA;

    /*
     * 通过lua脚本释放锁,来达到释放锁的原子操作
     */
    static {
        UNLOCK_LUA = "if redis.call(\"get\",KEYS[1]) == ARGV[1] " + "then " + "    return redis.call(\"del\",KEYS[1]) " + "else " + "    return 0 " + "end ";
        // PID 获取耗时，并且启动一次只有一个，所以缓存在这里
        pid = ManagementFactory.getRuntimeMXBean().getName();
        int indexOf = pid.indexOf('@');
        if (indexOf > 0) {
            pid = pid.substring(0, indexOf);
        }
    }

    public RedisDistributedLock(RedisTemplate<Object, Object> redisTemplate) {
        super();
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
        key = extractedKey(key);
        boolean result = setRedis(key, expire);
        // 如果获取锁失败，按照传入的重试次数进行重试
        while ((!result) && retryTimes-- > 0) {
            try {
                log.debug("get redisDistributeLock failed, retrying..." + retryTimes);
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                log.warn("Interrupted!", e);
                Thread.currentThread().interrupt();
            }
            result = setRedis(key, expire);
        }
        return result;
    }

    private String getUuid() {
        return pid + Thread.currentThread().getId();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private boolean setRedis(final String key, final long expire) {
        try {
            boolean status = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                String uuid = getUuid();
                String value = String.format("%s@%s", AppContextUtils.getUserCode2(),uuid);
                byte[] keyByte = redisTemplate.getStringSerializer().serialize(key);
                RedisSerializer serializer = redisTemplate.getValueSerializer();
                byte[] uuidByte = serializer.serialize(value);//redisTemplate.getStringSerializer().serialize(uuid);
                lockFlag.set(uuidByte);
                boolean result = connection.set(keyByte, uuidByte, Expiration.from(expire, TimeUnit.MILLISECONDS), RedisStringCommands.SetOption.ifAbsent());
                return result;
            });
            return status;
        } catch (Exception e) {
            log.warn("set redisDistributeLock occured an exception", e);
        }
        return false;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public boolean releaseLock(String key) {
        final String lockKey = extractedKey(key);
        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {
            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
            Boolean result = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                byte[] scriptByte = redisTemplate.getStringSerializer().serialize(UNLOCK_LUA);
                return connection.eval(scriptByte, ReturnType.BOOLEAN, 1, redisTemplate.getStringSerializer().serialize(lockKey), lockFlag.get());
            });
            return result;
        } catch (Exception e) {
            log.error("release redisDistributeLock occured an exception", e);
        }
        return false;
    }

    @Override
    public boolean releaseLockForce(String key) {
        key = extractedKey(key);
        return redisTemplate.delete(key);
    }

    @Override
    public Set<Object> getKeys(String key) {
        key = StringUtils.trimToNull(key);
        key = key == null ? "*" : "*" + key + "*";
        return redisTemplate.keys(DistributedLock.LOCK_PREX + key);
    }

    @Override
    public boolean hasLock(String key) {
        return redisTemplate.hasKey(DistributedLock.LOCK_PREX + key);
    }
}
