package com.czyl.framework.interceptor.impl;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.utils.reflect.ReflectUtils;
import com.czyl.common.utils.spring.SpringUtils;
import com.czyl.framework.cache.TableInfoCache;
import com.czyl.framework.table.domain.TableInfo;
import com.czyl.framework.uid.UidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * mybatis 拦截器insert操作时检查是否有主键注解生成主键
 *
 * @author tanghx
 */
@Slf4j
@Component
@Intercepts(@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}))
public class AutoIdInterceptor implements Interceptor {
    private static ThreadLocal<Boolean> ENABLE = new TransmittableThreadLocal<>();

    public static void setEnable(Boolean enable) {
        ENABLE.set(enable);
    }

    public static void remove() {
        ENABLE.remove();
    }

    /**
     * MappedStatement args的序号
     */
    final private static int MAPPED_STATEMENT_INDEX = 0;
    /**
     * parameter args的序号
     */
    final private static int PARAMETER_INDEX = 1;

    /**
     * @see org.apache.ibatis.reflection.ParamNameResolver#getNamedParams 中自动参数包裹
     */
    final String GENERIC_NAME_PREFIX = "param";

    /**
     * mybatis 参数
     */
    final String COLLECTION_NAME = "collection";

    @Autowired
    TableInfoCache tableInfoCache;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (ENABLE.get() != null && ENABLE.get().equals(Boolean.FALSE)) {
            return invocation.proceed();
        }
        final Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[MAPPED_STATEMENT_INDEX];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (sqlCommandType != SqlCommandType.INSERT) {
            return invocation.proceed();
        }
        final Object parameter = args[PARAMETER_INDEX];
        if (parameter == null) {
            return invocation.proceed();
        }
        // 1,解析参数，获得vo对象，如果是单个新增 那么就是 单个vo，如果是多个的 获得集合
        List<Object> vos = tableInfoCache.analysisVOs(parameter);
        if (vos.isEmpty()) {
            return invocation.proceed();
        }
        // 获得 id字段描述
        TableInfo tableinfo = tableInfoCache.getTableInfo(vos.get(0).getClass());
        if (tableinfo == null || tableinfo.getId() == null || StringUtils.isBlank(tableinfo.getId().getAttr())) {
            return invocation.proceed();
        }
        // 注入id
        injectionIds(vos, tableinfo);
        // 继续执行新增逻辑
        return invocation.proceed();
    }

    /**
     * 自动注入新增记录的id值
     *
     * @param vos
     * @param tableinfo
     */
    private void injectionIds(List<Object> vos, TableInfo tableinfo) {
        for (Object vo : vos) {
            setIdFiledValue(tableinfo, vo);
        }
    }


    /**
     * 对没有id的VO的id字段 设置一个新生成的id
     *
     * @param tableinfo
     * @param object
     */
    private void setIdFiledValue(TableInfo tableinfo, Object object) {
        Long id = ReflectUtils.getFieldValue(object, tableinfo.getId().getAttr());
        if (id != null && id.longValue() > 0) {
            return;
        }
        ReflectUtils.setFieldValue(object, tableinfo.getId().getAttr(), SpringUtils.getBean(UidGenerator.class).getUID());
    }

}
