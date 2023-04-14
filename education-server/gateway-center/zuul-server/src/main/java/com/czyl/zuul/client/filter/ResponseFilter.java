package com.czyl.zuul.client.filter;

import javax.servlet.http.Cookie;

import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.czyl.common.constant.TraceConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;

/**
 * 请求完成后，将trace_id设置到response header里面进行传递
 * 
 * @author tanghx
 *
 */
@Slf4j
@Component
public class ResponseFilter extends ZuulFilter {
	private static final Logger logger = LoggerFactory.getLogger("platform");
	private static final int FILTER_ORDER = 1;
	private static final boolean SHOULD_FILTER = true;
	private static final String FILTER_TYPE = "post";

	@Override
	public String filterType() {
		return FILTER_TYPE;
	}

	@Override
	public int filterOrder() {
		return FILTER_ORDER;
	}

	@Override
	public boolean shouldFilter() {
		return SHOULD_FILTER;
	}

	@Override
	public Object run() {
		RequestContext requestContext = RequestContext.getCurrentContext();
		String url = requestContext.getRequest().getRequestURL().toString();
		String traceId = MDC.get(TraceConstant.LOG_B3_TRACEID);
		String apmtraceid = TraceContext.traceId();
		if (log.isDebugEnabled()) {
			log.debug("{} ,cookie and header set {}:{}", url, TraceConstant.HTTP_HEADER_TRACE_ID, traceId);
			log.debug("{} ,cookie and header set {}:{}", url, TraceConstant.HTTP_HEADER_APMTRACE_ID, apmtraceid);
		}

		requestContext.getResponse().addCookie(new Cookie(TraceConstant.HTTP_HEADER_TRACE_ID, traceId));
		requestContext.getResponse().addHeader(TraceConstant.HTTP_HEADER_TRACE_ID, traceId);
		// skywalking
		requestContext.getResponse().addCookie(new Cookie(TraceConstant.HTTP_HEADER_APMTRACE_ID, apmtraceid));
		requestContext.getResponse().addHeader(TraceConstant.HTTP_HEADER_APMTRACE_ID, apmtraceid);
		return null;
	}
}
