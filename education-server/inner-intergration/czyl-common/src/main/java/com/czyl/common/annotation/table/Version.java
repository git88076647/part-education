
package com.czyl.common.annotation.table;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * @author tanghx
 */
@Documented
@Inherited
@Retention(RUNTIME)
@Target(value = { FIELD })
public @interface Version {
	/**
	 * 数据库字段
	 * 
	 * @return
	 */
	String value() default "version";
}
