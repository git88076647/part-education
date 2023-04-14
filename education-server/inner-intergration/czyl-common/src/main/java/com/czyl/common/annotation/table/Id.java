package com.czyl.common.annotation.table;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定ID数据库字段,注到实体字段上
 *
 * @author tanghx
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { FIELD,})
public @interface Id {
	/**
	 * 数据库字段
	 * @return
	 */
	String value() ;	
}
