package com.czyl.framework.aspectj.lang.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {
    /**
     * 表的别名
     */
    String tableAlias() default "";

    String tableName();
    /**
     * 作用域
     */
    String scopeCode();

    /**
     * 授权主体
     *
     * @return
     */
    String subjectType() default "role";
}
