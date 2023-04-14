package com.czyl.framework.aspectj.lang.annotation;

import com.czyl.common.lock.DistributedLock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 锁注解
 * @author tanghx
 *
 */
@Target(value = { ElementType.METHOD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Lock4j {

	/**
	 * <pre>
	 * #user.userId
	 * KEY 默认包名+方法名+参数
	 * 如果参数继承了 BaseEntity 则判断是否注解了Id,获取ID作为KEY
	 * </pre> 
	 */
	String[] keys() default "";

	/**
	 * <pre>
	 *	过期时间 单位：毫秒
	 *	默认1小时
	 *	过期时间一定是要长于业务的执行时间.
	 * </pre>
	 */
	long expire() default DistributedLock.EXPIRE_MILLIS;

	/**
	 * <pre>
	 *	重试次数
	 * 	默认20次,每次休眠100ms
	 * 	结合业务,建议该次数不宜设置过大,特别在并发高的情况下.
	 * </pre>
	 */
	int retryTimes() default 20;
	
	/**
	 * <pre>
	 * 是否自动增加包名作为key前缀,默认false
	 * </pre>
	 */
	boolean autoPrefix() default false;

	/**
	 * 无法获取锁则提示
	 * @return
	 */
	String message() default "数据正在被操作,请稍后再试！";

}
