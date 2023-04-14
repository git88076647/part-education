package com.czyl.framework.interceptor.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.czyl.framework.service.IRepeatSubmit;
import com.czyl.framework.service.impl.DefaultRepeatSubmit;

/**
 * 自定义注解防止表单重复提交
 * 
 * @author tanghx
 *
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {

	/** 频率 (秒) */
	public int intervalTime() default 12;

	/** 实现类 */
	public Class<? extends IRepeatSubmit> implClass() default DefaultRepeatSubmit.class;

}