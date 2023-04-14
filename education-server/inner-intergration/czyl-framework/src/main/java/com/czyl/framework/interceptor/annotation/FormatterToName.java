package com.czyl.framework.interceptor.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 根据主键翻译名称
 * 
 * @author tanghx
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FormatterToName
{
	/** 数据来源字段  */
	public String idField() ;
	
	/** 参照关联表 */
	public String refTable() default "";

	/** 参照关联字段 */
	public String refIdField() default "";
	
	/** 参照显示字段 */
	public String refName() default "";
	
	
	
}
