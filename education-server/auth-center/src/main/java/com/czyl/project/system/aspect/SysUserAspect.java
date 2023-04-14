package com.czyl.project.system.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户更新接口的demo
 * @author tanghx
 *
 */
@Aspect
@Component
@Slf4j
public class SysUserAspect {

	@Pointcut("execution(public * com.czyl.project.system.service.impl.SysUserServiceImpl.updateUser(..)))")
	public void updateUserPointCut() {
	}

	/**
	 * 需要注意的是被切的方法有返回值这里就不能是void
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("updateUserPointCut()")
	public Object doAroundUpdateUser(ProceedingJoinPoint pjp) throws Throwable {
		Object[] objs = pjp.getArgs();
		/** 参数名 */
		String[] argNames = ((MethodSignature) pjp.getSignature()).getParameterNames(); 
		if(log.isDebugEnabled()) {
			log.debug("updateUser前"+JSON.toJSONString(objs));
		}
		Object proceed = pjp.proceed();
		if(log.isDebugEnabled()) {
			log.debug("updateUser后" +JSON.toJSONString(proceed));
		}
//		if("".equals("")) {
//			throw new CustomException("XX异常更新数据回滚");
//		}
		return proceed;
	}

}
