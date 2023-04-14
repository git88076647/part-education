package com.czyl.framework.config;

import com.czyl.common.constant.Constants;
import com.czyl.common.utils.ServletUtils;
import com.czyl.framework.config.properties.RestTemplateProperties;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

/**
 * 装配RestTemplate
 * @author tanghx
 *
 */
@Configuration
@EnableConfigurationProperties(RestTemplateProperties.class)
public class RestTemplateConfig {

	@Autowired
	private RestTemplateProperties restTemplateProperties;

	/** 普通无权限不传入token的restemplate 不负载均衡 */
	public static final String REST_TEMPLATE_NORMAL_NO_AUTH = "restTemplateNormalNoAuth";

	/** 
	 * 普通权限需传入token的restemplate 不负载均衡 
	 */
	public static final String REST_TEMPLATE_NORMAL = "restTemplateNormal";

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(TokenInterceptor tokenInterceptor) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(httpRequestFactory());
		restTemplate.setInterceptors(Collections.singletonList(tokenInterceptor));
		return restTemplate;
	}

	@Bean(REST_TEMPLATE_NORMAL_NO_AUTH)
	public RestTemplate restTemplateNormalNoAuth() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(httpRequestFactory());
		return restTemplate;
	}

	@Bean(REST_TEMPLATE_NORMAL)
	public RestTemplate restTemplateNormal(TokenInterceptor tokenInterceptor) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(httpRequestFactory());
		restTemplate.setInterceptors(Collections.singletonList(tokenInterceptor));
		return restTemplate;
	}

	/**
	 * 
	 * @author tanghx 增加RestTemplate 拦截器，自动添加Authorization 信息
	 *
	 */
	@Component
	public class TokenInterceptor implements ClientHttpRequestInterceptor {

		@Autowired
		DiscoveryClient discoveryClient;

		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
			HttpHeaders headers = request.getHeaders();
			if (headers.containsKey(Constants.AUTHORIZATION) == false) {
				if(ServletUtils.getRequest() != null){
					headers.add(Constants.AUTHORIZATION, ServletUtils.getRequest().getHeader(Constants.AUTHORIZATION));
				}
			}
			return execution.execute(request, body);
		}
	}

	/**
	 * httpclient 实现的ClientHttpRequestFactory
	 */
	@Bean
	public ClientHttpRequestFactory httpRequestFactory() {
		return new HttpComponentsClientHttpRequestFactory(httpClient());
	}

	/**
	 * 使用连接池的 httpclient
	 * 避免与feign httpclient冲突，不做bean管理
	 */
//	@Bean
	public HttpClient httpClient() {
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
		// 最大链接数
		connectionManager.setMaxTotal(restTemplateProperties.getMaxTotal());
		// 同路由并发数20
		connectionManager.setDefaultMaxPerRoute(restTemplateProperties.getMaxPerRoute());

		RequestConfig requestConfig = RequestConfig.custom()
				// 读超时
				.setSocketTimeout(restTemplateProperties.getReadTimeout())
				// 链接超时
				.setConnectTimeout(restTemplateProperties.getConnectTimeout())
				// 链接不够用的等待时间
				.setConnectionRequestTimeout(restTemplateProperties.getReadTimeout()).build();

		return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setConnectionManager(connectionManager).setRetryHandler(new DefaultHttpRequestRetryHandler(3, true)).build();
	}

}
