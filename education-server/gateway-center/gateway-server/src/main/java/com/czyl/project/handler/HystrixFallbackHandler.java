package com.czyl.project.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 网关本身500错误
 * @author tanghx
 *
 */
@Slf4j
@Component
public class HystrixFallbackHandler implements HandlerFunction<ServerResponse> {

	@Override
	public Mono<ServerResponse> handle(ServerRequest serverRequest) {

		serverRequest.attribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR)
				.ifPresent(originalUrls -> 
					log.error("网关执行请求:{}失败,hystrix服务降级处理", originalUrls)
				);

		Map<String,String> resp = new HashMap<>();
		resp.put("code", "500");
		resp.put("msg", "网关程序失败");
		return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(BodyInserters.fromValue(resp));
	}
}
