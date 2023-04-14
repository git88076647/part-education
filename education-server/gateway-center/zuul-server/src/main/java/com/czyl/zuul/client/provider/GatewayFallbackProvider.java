package com.czyl.zuul.client.provider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.exception.HystrixTimeoutException;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义Zuul回退机制处理器。
 *
 * Provides fallback when a failure occurs on a route
 * 英文意思就是说提供一个回退机制当路由后面的服务发生故障时。
 * @author tanghx
 *
 */
@Slf4j
@Component
public class GatewayFallbackProvider implements FallbackProvider {

	/**
	 * 返回值表示需要针对此微服务做回退处理（该名称一定要是注册进入 eureka 微服务中的那个 serviceId 名称）；
	 * 表明是为哪个微服务提供回退，*表示为所有微服务提供回退
	 * 
	 * @return
	 */

	@Override
	public String getRoute() {
		return "*";
	}

	/**
	 * 网关向api服务请求是失败了，但是消费者客户端向网关发起的请求是OK的， 不应该把api的404,500等问题抛给客户端
	 * 网关和api服务集群对于客户端来说是黑盒子
	 */

	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		//TODO 需要丰富失败的信息,帮助定位,比如是找不到服务,服务全部不可用 邮件短信微信等渠道进行通知
		log.warn("网关代理服务异常", cause);
		if (cause instanceof HystrixTimeoutException) {
			log.warn("服务[{}]请求超时,请稍后再试!",route);
			return response(HttpStatus.GATEWAY_TIMEOUT,"请求超时 !");
		}else if(cause instanceof com.netflix.client.ClientException && cause.getMessage() != null && cause.getMessage().contains("Load balancer does not have available server for client:") ) {
			String msg=cause.getMessage();
			msg = String.format("服务[%s]不可用,请稍后再试!", route);
			log.warn(msg);
			return this.response(HttpStatus.OK,msg);
		}else if(cause instanceof com.netflix.client.ClientException ) {
			return this.response(HttpStatus.INTERNAL_SERVER_ERROR,cause.getMessage());
		} else {
			return this.fallbackResponse();
		}
	}

	public ClientHttpResponse fallbackResponse() {
		return this.response(HttpStatus.INTERNAL_SERVER_ERROR,"系统繁忙,请稍后再试!");
	}

	private ClientHttpResponse response(final HttpStatus status,final String msg) {

		return new ClientHttpResponse() {
			@Override
			public HttpStatus getStatusCode() throws IOException {
				return status;
			}

			@Override
			public int getRawStatusCode() throws IOException {
				return status.value();
			}

			@Override
			public String getStatusText() throws IOException {
				return msg;
			}

			@Override
			public void close() {
			}

			/**
			 * 当 springms-provider-user 微服务出现宕机后，客户端再请求时候就会返回 fallback 等字样的字符串提示；
			 * 但对于复杂一点的微服务，我们这里就得好好琢磨该怎么友好提示给用户了； 如果请求用户服务失败，返回什么信息给消费者客户端
			 * 
			 * @return
			 * @throws IOException
			 */
			@Override
			public InputStream getBody() throws IOException {
				JSONObject r = new JSONObject();
				r.put("code",500);
				r.put("msg",msg);
				return new ByteArrayInputStream(r.toString().getBytes("UTF-8"));
			}

			/**
			 * headers设定
			 */
			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
				MediaType mt = new MediaType("application", "json", Charset.forName("UTF-8"));
				headers.setContentType(mt);
				return headers;
			}

		};

	}

}