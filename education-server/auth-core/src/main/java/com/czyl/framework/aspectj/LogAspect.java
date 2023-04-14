package com.czyl.framework.aspectj;

import com.alibaba.fastjson.JSON;
import com.czyl.common.constant.Constants;
import com.czyl.common.constant.TraceConstant;
import com.czyl.common.utils.ServletUtils;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.utils.ip.IpUtils;
import com.czyl.common.utils.spring.SpringUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessStatus;
import com.czyl.framework.feign.AuthServerClient;
import com.czyl.framework.manager.factory.AsyncFactory;
import com.czyl.framework.security.LoginUser;
import com.czyl.framework.security.service.TokenService;
import com.czyl.framework.web.domain.OperLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * 操作日志记录处理
 * 
 * @author tanghx
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

	private static final String SYS_OPER_TYPE = "sys_oper_type";

	/** 配置织入点*/
	@Pointcut("@annotation(com.czyl.framework.aspectj.lang.annotation.Log)")
	public void logPointCut() {
	}

	@Before("logPointCut()")
	public void doBeforeReturning(JoinPoint joinPoint) {
		// 获得注解
		try {
			Log controllerLog = getAnnotationLog(joinPoint);
			if (controllerLog == null) {
				return;
			}
			// BusinessType
			AjaxResult ajaxResult = SpringUtils.getBean(AuthServerClient.class).dictLabel(SYS_OPER_TYPE, String.valueOf(controllerLog.businessType().ordinal()));
			Object label = ajaxResult.getData();
			MDC.put(TraceConstant.LOG_B3_BIZ, String.format("%s-%s", controllerLog.title(), label));
		} catch (Exception exp) {
			// 记录本地异常日志
			log.warn("==前置通知异常==");
			log.warn("日志记录异常信息:\n", exp);
		}

	}

	/**
	 * 处理完请求后执行
	 *
	 * @param joinPoint 切点
	 */
	@AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
	public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
		handleLog(joinPoint, null, jsonResult);
	}

	/**
	 * 拦截异常操作
	 * 
	 * @param joinPoint 切点
	 * @param e         异常
	 */
	@AfterThrowing(value = "logPointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
		handleLog(joinPoint, e, null);
	}

	protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
		try {
			// 获得注解
			Log controllerLog = getAnnotationLog(joinPoint);
			if (controllerLog == null) {
				return;
			}
			// 获取当前的用户
			LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
			// *========数据库日志=========*//
			if(log.isDebugEnabled()) {
				log.debug("组装操作日志");
			}
			OperLog operLog = new OperLog();
			operLog.setCreateBy(loginUser.getUser().getUserId());
			operLog.setCreateTime(new Date());
			// traceid
			operLog.setTraceid(MDC.get(TraceConstant.LOG_B3_TRACEID));
			operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
			// 请求的地址
			String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
			operLog.setOperIp(ip);
			// 返回参数
			operLog.setJsonResult(JSON.toJSONString(jsonResult));

			operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
			if (loginUser != null) {
				operLog.setOperName(loginUser.getUsername());
			}

			if (e != null) {
				operLog.setStatus(BusinessStatus.FAIL.ordinal());
				operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
			}
			// 设置方法名称
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			operLog.setMethod(className + "." + methodName + "()");
			// 设置请求方式
			operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
			// 处理设置注解上的参数
			getControllerMethodDescription(joinPoint, controllerLog, operLog);
			// 保存数据库
			String authorization = ServletUtils.getRequest().getHeader(Constants.AUTHORIZATION);
//			RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
//			((ServletRequestAttributes)attributes).getRequest().getHeaderNames();
			MDC.put(TraceConstant.LOG_B3_BIZ, "");
			SpringUtils.getBean(AsyncFactory.class).recordOper(operLog,authorization);
			
		} catch (Exception exp) {
			// 记录本地异常日志
			log.warn("==业务操作日志通知异常==");
			log.warn("异常信息:\n", exp);
		}
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * 
	 * @param log     日志
	 * @param operLog 操作日志
	 * @throws Exception
	 */
	public void getControllerMethodDescription(JoinPoint joinPoint, Log log, OperLog operLog) throws Exception {
		// 设置action动作
		operLog.setBusinessType(log.businessType().ordinal());
		// 设置标题
		operLog.setTitle(log.title());
		// 设置操作人类别
		operLog.setOperatorType(log.operatorType().ordinal());
		// 是否需要保存request，参数和值
		if (log.isSaveRequestData()) {
			// 获取参数的信息，传入到数据库中。
			setRequestValue(joinPoint, operLog);
		}
	}

	/**
	 * 获取请求的参数，放到log中
	 * 
	 * @param operLog 操作日志
	 * @throws Exception 异常
	 */
	private void setRequestValue(JoinPoint joinPoint, OperLog operLog) throws Exception {
		String requestMethod = operLog.getRequestMethod();
		if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
			String params = argsArrayToString(joinPoint.getArgs());
			operLog.setOperParam(params);
		} else {
//			Map<?, ?> urlParamsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			Map<String, String[]> params = ServletUtils.getRequest().getParameterMap();
			operLog.setOperParam(JSON.toJSONString(params));

		}
	}

	/**
	 * 是否存在注解，如果存在就获取
	 */
	private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (method != null) {
			return method.getAnnotation(Log.class);
		}
		return null;
	}

	/**
	 * 参数拼装
	 */
	private String argsArrayToString(Object[] paramsArray) {
		String params = "";
		if (paramsArray != null && paramsArray.length > 0) {
			for (int i = 0; i < paramsArray.length; i++) {
				if (!isFilterObject(paramsArray[i])) {
					Object jsonObj = JSON.toJSON(paramsArray[i]);
					params += jsonObj == null ? "null" : jsonObj.toString() + " ";
				}
			}
		}
		return params.trim();
	}

	/**
	 * 判断是否需要过滤的对象。
	 * 
	 * @param o 对象信息。
	 * @return 如果是需要过滤的对象，则返回true；否则返回false。
	 */
	public boolean isFilterObject(final Object o) {
		return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
	}
}
