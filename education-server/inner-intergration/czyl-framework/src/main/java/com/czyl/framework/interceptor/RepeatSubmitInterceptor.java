package com.czyl.framework.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.czyl.common.utils.ServletUtils;
import com.czyl.common.utils.spring.SpringUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.interceptor.annotation.RepeatSubmit;
import com.czyl.framework.service.IRepeatSubmit;

/**
 * 防止重复提交拦截器
 * 
 * @author tanghx
 */
@Component
public class RepeatSubmitInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
			if (annotation != null) {
				IRepeatSubmit impl = SpringUtils.getBean(annotation.implClass());
				//根据注解获取校验的对象
				AjaxResult ajaxResult=impl.getRepeatSubmit(request,annotation.intervalTime());
				if (ajaxResult != null) {
					ServletUtils.renderString(response, JSONObject.toJSONString(ajaxResult));
					return false;
				}
			}
			return true;
		} else {
			return super.preHandle(request, response, handler);
		}
	}
}
