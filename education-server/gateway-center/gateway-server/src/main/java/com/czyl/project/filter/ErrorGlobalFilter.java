package com.czyl.project.filter;
//package com.example.gateway.filter;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//
//import reactor.core.publisher.Mono;
//
///**
// * 故意抛出异常 测试 gateway 异常处理
// * @author tanghx
// *
// */
//@Component
//public class ErrorGlobalFilter implements GlobalFilter {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        int i = 1/0;
//        return chain.filter(exchange);
//    }
//}