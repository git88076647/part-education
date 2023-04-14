//package com.czyl.project.filter;
//
//import javax.servlet.http.Cookie;
//
//import org.apache.skywalking.apm.toolkit.trace.TraceContext;
//import org.slf4j.MDC;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//
//import com.czyl.common.constant.TraceConstant;
//
//import lombok.extern.slf4j.Slf4j;
//import reactor.core.publisher.Mono;
//
///**
// * 如果成功转发到服务
// * 过滤器设置traceID 等信息
// */
//@Slf4j
//@Component
//@SuppressWarnings("all")
//public class RequestStatsFilter implements GlobalFilter, Ordered {
//
//
//	@Override
//	public int getOrder() {
//		// TODO Auto-generated method stub
//		return -501;
//	}
//
//	@Override
//	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//		//构建head
//		ServerHttpRequest request = exchange.getRequest().mutate().build();
//		ServerWebExchange build = exchange.mutate().request(request).build();
//        return chain.filter(build);
//	}
//
//	 
//
//}
