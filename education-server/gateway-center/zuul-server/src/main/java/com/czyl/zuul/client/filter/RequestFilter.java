package com.czyl.zuul.client.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * zuul 前置过虑器
 * 
 */
@Component
public class RequestFilter extends ZuulFilter {
	private static final Logger logger = LoggerFactory.getLogger("platform");
	private static final int FILTER_ORDER = 1;
	private static final boolean SHOULD_FILTER = true;
	private static final String FILTER_TYPE = "pre";

	/*** filter类型，前置过虑器，后置过虑器和路由过虑器 */
	@Override
	public String filterType() {
		return FILTER_TYPE;
	}

	/*** 返回一个整数值，表示filter执行顺序 */
	@Override
	public int filterOrder() {
		return FILTER_ORDER;
	}

	/*** 返回一个boolean，表示该过虑器是否执行 */
	@Override
	public boolean shouldFilter() {
		return SHOULD_FILTER;
	}

	/*** 每次filter执行的代码 */
	@Override
	public Object run() {
		RequestContext requestContext = RequestContext.getCurrentContext();
		String url = requestContext.getRequest().getRequestURL().toString();
		logger.info(url);
		return null;
	}
}
