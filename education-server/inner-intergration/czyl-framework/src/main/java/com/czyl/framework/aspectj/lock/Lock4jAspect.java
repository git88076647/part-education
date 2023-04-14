package com.czyl.framework.aspectj.lock;

import com.alibaba.fastjson.JSON;
import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.LockUtils;
import com.czyl.common.utils.StringUtils;
import com.czyl.framework.aspectj.lang.annotation.Lock4j;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 分布式锁
 * 
 * @author tanghx
 */
@Aspect
@Order(1)
@Component
@Slf4j
public class Lock4jAspect {

	@Pointcut("@annotation(com.czyl.framework.aspectj.lang.annotation.Lock4j)" + "|| @within(com.czyl.framework.aspectj.lang.annotation.Lock4j)")
	public void lockPointCut() {

	}

	@Around("lockPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Lock4j lock4j = getLock(point);
		if (StringUtils.isNull(lock4j)) {
			return point.proceed();
		}
		String key = getLockKey(point, lock4j);
		if (log.isDebugEnabled()) {
			log.debug("lock4j key:{}", key);
		}
		if (key != null) {
			boolean flag = LockUtils.me().lock(key, lock4j.expire(), lock4j.retryTimes());
			if (flag == false) {
				log.info("lockFailKey:{},{}",key , lock4j.message());
				throw new CustomException(lock4j.message());
			}
		}
		try {
			return point.proceed();
		} finally {
			if (key != null) {
				LockUtils.releaseLock(key);
			}
		}
	}

	/**
	 * 获取Lock
	 */
	public Lock4j getLock(ProceedingJoinPoint point) {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Lock4j lock = method.getAnnotation(Lock4j.class);
		if (lock == null) {
			lock = (Lock4j) signature.getDeclaringType().getAnnotation(Lock4j.class);
		}
		if (lock == null) {
			Class<? extends Object> targetClass = point.getTarget().getClass();
			lock = targetClass.getAnnotation(Lock4j.class);
		}
		return lock;
	}

	private final ParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

	private final ExpressionParser PARSER = new SpelExpressionParser();

	public String getLockKey(ProceedingJoinPoint point, Lock4j lock) {
		StringBuilder sb = new StringBuilder();
		Method method = ((MethodSignature) point.getSignature()).getMethod();
		// 是否自动增加包名
		if (lock.autoPrefix()) {
			sb.append(method.getDeclaringClass().getName()).append(".").append(method.getName()).append(":");
		}
		if (lock.keys().length > 1 || !"".equals(lock.keys()[0])) {
			sb.append(getSpelDefinitionKey(lock.keys(), method, point.getArgs()));
		} else {
			sb.append(JSON.toJSONString(point.getArgs()));
		}
		return sb.toString();
	}

	private String getSpelDefinitionKey(String[] definitionKeys, Method method, Object[] parameterValues) {
		EvaluationContext context = new MethodBasedEvaluationContext(null, method, parameterValues, NAME_DISCOVERER);
		List<String> definitionKeyList = new ArrayList<>(definitionKeys.length);
		for (String definitionKey : definitionKeys) {
			if (definitionKey != null && !definitionKey.isEmpty()) {
				Object value = PARSER.parseExpression(definitionKey).getValue(context);
				String key = value == null ? "" : value.toString();
				definitionKeyList.add(key);
			}
		}
		return org.springframework.util.StringUtils.collectionToDelimitedString(definitionKeyList, ".", "", "");
	}
}
