package com.czyl.framework.interceptor.impl;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import com.czyl.common.utils.spring.SpringUtils;
import com.czyl.framework.interceptor.annotation.FormatterToName;

import lombok.extern.slf4j.Slf4j;

/**
 * mybatis 拦截器select 操作翻译参照字段
 * @author tanghx
 *
 */
@Slf4j
@Component
@Intercepts(@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,RowBounds.class, ResultHandler.class,CacheKey.class,BoundSql.class}))
public class SelectRefFormatterInterceptor implements Interceptor {


	ConcurrentHashMap<String, Set<Field>> cache = new ConcurrentHashMap<String, Set<Field>>();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
		if (sqlCommandType != SqlCommandType.SELECT) {
			return invocation.proceed();
		}
		Object returnObj=invocation.proceed();
		if(returnObj == null) {
			return returnObj;
		}
//		SqlSessionFactory
//		SqlSession session = SpringUtils.getBean(SqlSessionFactory.class).openSession();
//		session.selectOne("包加方法名");
		
		Object typeObj;
		if(returnObj instanceof Object[]) {
			if(((Object[])returnObj).length == 0) {
				return returnObj;
			}
			typeObj=((Object[])returnObj)[0];
		}else if(returnObj instanceof Collection) {
			if(((Collection)returnObj).size() == 0) {
				return returnObj;
			}
			typeObj=((Collection)returnObj).iterator().next();
		}else {
			typeObj = returnObj;
		}
		
//		Set<Field> filedSet = getFields(typeObj);
//		
//		if (filedSet != null && filedSet.size() > 0) {
//			for (Field set : filedSet) {
//				FormatterToName ftn = set.getAnnotation(FormatterToName.class);
//				try {
//					
//				}catch(Exception e) {
//					
//				}
////				ReflectUtils.setFieldValue(parameter, set.getName(), idGenerator.getUID());
//			}
//		}
		return returnObj;
	}

	private Set<Field> getFields(Object parameter) {
		String key = parameter.getClass().getName().intern();
		if (cache.containsKey(key)) {
			return cache.get(key);
		}
		Field[] fields = parameter.getClass().getFields();
		//父类字段
		Field[] superFields = parameter.getClass().getDeclaredFields();
		Set<Field> filedSet = new HashSet<Field>();
		if (fields != null) {
			for (int i = 0; i < fields.length; i++) {
				FormatterToName tableid = fields[i].getAnnotation(FormatterToName.class);
				if (tableid != null) {
					filedSet.add(fields[i]);
				}
			}
		}
		if (superFields != null) {
			for (int i = 0; i < superFields.length; i++) {
				FormatterToName tableid = superFields[i].getAnnotation(FormatterToName.class);
				if (tableid != null) {
					filedSet.add(superFields[i]);
				}
			}
		}
		cache.put(key, filedSet);
		log.info("class:{},解析出Ref字段{}",key,filedSet);
		return filedSet;
	}

}
