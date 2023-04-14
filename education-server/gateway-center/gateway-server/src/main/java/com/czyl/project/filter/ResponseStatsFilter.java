package com.czyl.project.filter;

import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.czyl.common.constant.TraceConstant;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 过滤器设置traceID 等信息
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class ResponseStatsFilter implements GlobalFilter, Ordered {

	@Override
	public int getOrder() {
		return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpResponse response = exchange.getResponse();
		String traceId = MDC.get(TraceConstant.LOG_B3_TRACEID);
		String apmtraceid = TraceContext.traceId();
		// sleuth traceid
		response.getHeaders().add(TraceConstant.HTTP_HEADER_TRACE_ID, traceId);
		response.getCookies().add(TraceConstant.HTTP_HEADER_TRACE_ID, ResponseCookie.from(TraceConstant.HTTP_HEADER_TRACE_ID, traceId).build());
		//sw apmtraceid
		response.getHeaders().add(TraceConstant.HTTP_HEADER_APMTRACE_ID, apmtraceid);
		response.getCookies().add(TraceConstant.HTTP_HEADER_APMTRACE_ID, ResponseCookie.from(TraceConstant.HTTP_HEADER_APMTRACE_ID, apmtraceid).build());
		
		if (log.isDebugEnabled()) {
			ServerHttpRequest request = exchange.getRequest();
			String url = request.getURI().getPath();
			log.debug("{} ,cookie and header set {}:{}", url, TraceConstant.HTTP_HEADER_TRACE_ID, traceId);
			log.debug("{} ,cookie and header set {}:{}", url, TraceConstant.HTTP_HEADER_APMTRACE_ID, apmtraceid);
		}
		// 将现在的request 变成 change对象
		ServerWebExchange newExchange = exchange.mutate().request(exchange.getRequest()).response(response).build();

		return chain.filter(newExchange);

	}

}
