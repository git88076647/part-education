package com.czyl.common.annotation.table;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表注解,如果类注解后更新保存等能能根据注解自动生成主键,乐观锁等信息.
 * 需配合 
 * <br/>
 * org.springframework.data.annotation.Id
 * <br/>
 * org.springframework.data.annotation.Version
 * @author tanghx
 *
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {

	/**
	 * 表名
	 * @return
	 */
	String value();
}
