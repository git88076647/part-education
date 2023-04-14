package com.czyl.framework.interceptor.impl;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.reflect.ReflectUtils;
import com.czyl.common.utils.spring.SpringUtils;
import com.czyl.framework.cache.TableInfoCache;
import com.czyl.framework.table.domain.TableInfo;
import com.czyl.framework.table.mapper.BillBaseMapper;
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
 * mybatis 拦截器update操作时检查是否数据版本
 *
 * @author tanghx
 */
@Slf4j
@Component
@Intercepts(@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}))
public class VersionInterceptor implements Interceptor {

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

    final private static String COLLECTION = "collection";

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
        if (sqlCommandType != SqlCommandType.UPDATE) {
            return invocation.proceed();
        }
        final Object parameter = args[PARAMETER_INDEX];
        if (parameter == null) {
            return invocation.proceed();
        }
        List<Object> objs = tableInfoCache.analysisVOs(parameter);
        TableInfo tableInfo = tableInfoCache.getTableInfo(parameter);
        if (tableInfo == null || !tableInfo.isSupportVersion()) {
            return invocation.proceed();
        }
        // TODO 修改校验方式为，改写SQL，增加where条件 version 判断返回值数量是否等于 objs.length
        for (int i = 0, len = objs.size(); i < len; i++) {
            checkObj(objs.get(i), tableInfo);
        }
        return invocation.proceed();
    }

    private void checkObj(final Object parameter, TableInfo tableInfo) {
        Long version = (Long) ReflectUtils.getFieldValue(parameter, tableInfo.getVersion().getAttr());
        if (version == null) {
            return;
        }
        Long bdVersion = SpringUtils.getBean(BillBaseMapper.class).getVersion(tableInfo.getVersion().getColumn(), tableInfo.getTableName(), tableInfo.getId().getColumn(), ReflectUtils.getFieldValue(parameter, tableInfo.getId().getAttr()));
        if (bdVersion != null && !bdVersion.equals(version)) {
            throw new CustomException("数据已被他人修改,请刷新后重新提交");
        }
        ReflectUtils.setFieldValue(parameter, tableInfo.getVersion().getAttr(), version + 1);
    }

}
