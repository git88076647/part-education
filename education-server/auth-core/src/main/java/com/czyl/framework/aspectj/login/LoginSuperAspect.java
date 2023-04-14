package com.czyl.framework.aspectj.login;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.czyl.framework.security.service.SuperLoginService;

import lombok.extern.slf4j.Slf4j;

/**
 * 登录super账户
 * @author tanghx
 *
 */
@Aspect
@Order(1)
@Component
@Slf4j
public class LoginSuperAspect {

	@Autowired
	SuperLoginService superLoginService;

	@Pointcut("@annotation(com.czyl.framework.aspectj.login.LoginSuper)" + "|| @within(com.czyl.framework.aspectj.login.LoginSuper)")
	public void loginSuperPointCut() {

	}

	@Around("loginSuperPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		LoginSuper loginSuper = getLoginSuper(point);
		if (loginSuper != null) {
			log.info("切换Super用户调用其他服务");
			superLoginService.login();
		}
		try {
			return point.proceed();
		} finally {
			superLoginService.logout();
		}
	}

	public LoginSuper getLoginSuper(ProceedingJoinPoint point) {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		LoginSuper loginSuper = method.getAnnotation(LoginSuper.class);
		if (loginSuper == null) {
			loginSuper = (LoginSuper) signature.getDeclaringType().getAnnotation(LoginSuper.class);
		}
		if (loginSuper == null) {
			Class<? extends Object> targetClass = point.getTarget().getClass();
			loginSuper = targetClass.getAnnotation(LoginSuper.class);
		}
		return loginSuper;
	}

}
