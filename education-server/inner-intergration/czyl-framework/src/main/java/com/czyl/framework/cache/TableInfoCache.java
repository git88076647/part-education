package com.czyl.framework.cache;

import com.alibaba.fastjson.JSON;
import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Version;
import com.czyl.common.utils.reflect.ReflectUtils;
import com.czyl.framework.table.domain.ColumnInfo;
import com.czyl.framework.table.domain.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.defaults.DefaultSqlSession.StrictMap;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 表信息缓存
 *
 * @author tanghx
 */
@Slf4j
@Component
public class TableInfoCache {
    /**
     * @see org.apache.ibatis.reflection.ParamNameResolver#getNamedParams 中自动参数包裹
     */
    final String GENERIC_NAME_PREFIX = "param";

    /**
     * mybatis 参数
     */
    final String COLLECTION_NAME = "collection";


    private ConcurrentHashMap<String, TableInfo> tableInfoCache = new ConcurrentHashMap<String, TableInfo>();

    /**
     * 获取Id注解的字段
     *
     * @param parameter
     * @return
     */
    public TableInfo getTableInfo(final Object parameter) {
        List<Object> vos = analysisVOs(parameter);
        Object object = vos.get(0);
        String key = object.getClass().getName().intern();
        if (tableInfoCache.containsKey(key)) {
            return tableInfoCache.get(key);
        }
        synchronized (key) {
            ColumnInfo idColumn = null;
            ColumnInfo versionColumn = null;
            Field[] idFields = ReflectUtils.getFields(object, Id.class, true);
            if (idFields != null && idFields.length > 0) {
                idColumn = new ColumnInfo(idFields[0].getName(), idFields[0].getAnnotation(Id.class).value());
            }
            Field[] versionFields = ReflectUtils.getFields(object, Version.class, true);
            if (versionFields != null && versionFields.length > 0) {
                versionColumn = new ColumnInfo(versionFields[0].getName(), versionFields[0].getAnnotation(Version.class).value());
            }
            String tableName = ReflectUtils.getTableName(object);
            TableInfo tabinfo = new TableInfo(idColumn, versionColumn, tableName, object.getClass());
            tableInfoCache.put(key, tabinfo);
            log.debug("{}解析出{}", object.getClass().getName(), JSON.toJSONString(tabinfo));
            return tabinfo;
        }
    }


    /**
     * 获取Id注解的字段
     *
     * @param voClz VO的class对象
     * @return
     */
    public TableInfo getTableInfo(final Class<?> voClz) {
        if (voClz == null) {
            return null;
        }

        String key = voClz.getName().intern();
        if (tableInfoCache.containsKey(key)) {
            return tableInfoCache.get(key);
        }
        synchronized (key) {
            ColumnInfo idColumn = null;
            ColumnInfo versionColumn = null;
            Field[] idFields = ReflectUtils.getFields(voClz, Id.class, true);
            if (idFields != null && idFields.length > 0) {
                idColumn = new ColumnInfo(idFields[0].getName(), idFields[0].getAnnotation(Id.class).value());
            }
            Field[] versionFields = ReflectUtils.getFields(voClz, Version.class, true);
            if (versionFields != null && versionFields.length > 0) {
                versionColumn = new ColumnInfo(versionFields[0].getName(), versionFields[0].getAnnotation(Version.class).value());
            }
            String tableName = ReflectUtils.getTableName(voClz);
            TableInfo tabinfo = new TableInfo(idColumn, versionColumn, tableName, voClz);
            tableInfoCache.put(key, tabinfo);
            log.debug("{}解析出{}", voClz.getName(), JSON.toJSONString(tabinfo));
            return tabinfo;
        }
    }


    /***
     * 分析 mybatis的 执行参数，过滤出里面的 vo对象
     *
     * @param mybatisInvokeMethodParmar
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List<Object> analysisVOs(Object mybatisInvokeMethodParmar) {
        LinkedList<Object> vos = new LinkedList<>();
        if (mybatisInvokeMethodParmar instanceof StrictMap) {
            // 如果是普通的集合参数
            Collection<?> collection = (Collection<?>) ((StrictMap<?>) mybatisInvokeMethodParmar).get(COLLECTION_NAME);
            if (collection.isEmpty()) {
                return vos;
            }

            vos.addAll(collection);
        } else if (mybatisInvokeMethodParmar instanceof MapperMethod.ParamMap) {
            // 如果是@Param 指定了参数名的
            MapperMethod.ParamMap map = (MapperMethod.ParamMap) mybatisInvokeMethodParmar;
            int start = 1;
            Object v;
            while (map.containsKey(GENERIC_NAME_PREFIX + start)) {
                v = map.get(GENERIC_NAME_PREFIX + start++);
                if (v instanceof Collection) {
                    vos.addAll((Collection<?>) v);
                    continue;
                } else if (v.getClass().isArray()) {
                    int length = Array.getLength(v);
                    for (int i = 0; i < length; i++) {
                        vos.add(Array.get(v, i));
                    }
                    continue;
                } else {
                    vos.add(v);
                }
            }
            if (vos.isEmpty() && map.containsKey(COLLECTION_NAME)) {
                v = map.get(COLLECTION_NAME);
                vos.addAll((Collection<?>) v);
            }
        } else {
            vos.add(mybatisInvokeMethodParmar);
        }
        return vos;
    }
}
