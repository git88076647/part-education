package com.czyl.project.swagger;


import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import com.czyl.common.constant.SwaggerConstant;

/**
 * 路由为admin/test/{a}/{b}，在swagger会显示为test/{a}/{b}，缺少了admin这个路由节点。
 * 断点源码时发现在Swagger中会根据X-Forwarded-Prefix这个Header来获取BasePath
 * @author tanghx
 *
 */
@Component
@SuppressWarnings("all")
public class GatewaySwaggerHeaderFilter extends AbstractGatewayFilterFactory {

	private static final String HEADER_NAME = "X-Forwarded-Prefix";
	 
	    @Override
	    public GatewayFilter apply(Object config) {
	        return (exchange, chain) -> {
	            ServerHttpRequest request = exchange.getRequest();
	            String path = request.getURI().getPath();
	            if (!StringUtils.endsWithIgnoreCase(path, SwaggerConstant.API_DOC_PATH)) {
	                return chain.filter(exchange);
	            }
	            String basePath = path.substring(0, path.lastIndexOf(SwaggerConstant.API_DOC_PATH));
	            ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, basePath).build();
	            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
	            return chain.filter(newExchange);
	        };
	    }
 
}