package com.czyl.framework.config;

import java.io.IOException;
import java.util.Collections;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.czyl.common.constant.Constants;

/**
 * eureka启用basic认证后 调用eureka
 * 
 * @Author tanghx
 */
@Configuration
public class EurekaConfig {

	private static String eurekaClientServiceUrlDefaultZone;

	private static boolean eurekaEnableBasic = false;

	private static String authorization;

	public static boolean isEurekaEnableBasic() {
		return eurekaEnableBasic;
	}

	@Value("${eureka.client.serviceUrl.defaultZone}")
	public void setEurekaClientServiceUrlDefaultZone(String eurekaClientServiceUrlDefaultZone) {
		synchronized (EurekaConfig.class.getName()) {
			EurekaConfig.eurekaClientServiceUrlDefaultZone = eurekaClientServiceUrlDefaultZone;
			authorization = null;
			eurekaEnableBasic = false;
			if (EurekaConfig.eurekaClientServiceUrlDefaultZone != null && EurekaConfig.eurekaClientServiceUrlDefaultZone.contains("@")) {
				eurekaEnableBasic = true;
				// http://eureka:eureka123@127.0.0.1:1111/eureka/,http://eureka:eureka123@127.0.0.1:1112/eureka/
				String url = EurekaConfig.eurekaClientServiceUrlDefaultZone.split(",")[0];
				String eureka = url.substring(url.indexOf("//") + 2, url.indexOf("@"));
				authorization = String.format("Basic %s", new String(Base64.encodeBase64(eureka.getBytes())));

			}
		}
	}

	/** 调用eureka的restemplate 负载均衡 */
	public static final String REST_TEMPLATE_EUREKA = "restTemplateEureka";

	@Bean(REST_TEMPLATE_EUREKA)
	@LoadBalanced
	public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory, EurekaBasicAuthInterceptor eurekaBasicAuthInterceptor) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(clientHttpRequestFactory);
		restTemplate.setInterceptors(Collections.singletonList(eurekaBasicAuthInterceptor));
		return restTemplate;
	}

	/**
	 * 获取eureka启用basic认证后Authorization信息
	 * 
	 * @return
	 */
	private String getEurekaBasicAuthorization() {
		return authorization;
	}

	@Component
	public class EurekaBasicAuthInterceptor implements ClientHttpRequestInterceptor {

		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
			HttpHeaders headers = request.getHeaders();
			if (isEurekaEnableBasic()) {
				headers.add(Constants.AUTHORIZATION, getEurekaBasicAuthorization());
			}
			return execution.execute(request, body);
		}
	}

}
