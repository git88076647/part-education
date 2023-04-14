package com.czyl.framework.config;

import com.czyl.common.constant.Constants;
import com.czyl.framework.plugin.TenantContextHolder;
import com.czyl.framework.web.context.TokenContextHolder;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * feign拦截器，只包含http相关数据
 *
 * @author tanghx
 * 
 */
@Configuration
public class FeignHttpInterceptorConfig {
	protected List<String> requestHeaders = new ArrayList<>();

	@PostConstruct
	public void initialize() {
		requestHeaders.add(Constants.AUTHORIZATION);
		requestHeaders.add(Constants.AUTHORIZATION.toLowerCase());
	}

	/**
	 * 使用feign client访问别的微服务时，将上游传过来的Authorization等信息放入header传递给下一个服务
	 */
	@Bean
	public RequestInterceptor httpFeignInterceptor() {
		return template -> {
			if(TokenContextHolder.get() != null) {
				template.header(Constants.AUTHORIZATION, TokenContextHolder.get());
			}
			if(TenantContextHolder.get() != null) {
				template.header(Constants.TENANT_HEADER, TenantContextHolder.get().toString());
			}
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			if (attributes != null) {
				HttpServletRequest request = attributes.getRequest();
				Enumeration<String> headerNames = request.getHeaderNames();
				if (headerNames != null) {
					String headerName;
					String headerValue;
					while (headerNames.hasMoreElements()) {
						headerName = headerNames.nextElement();
						if (requestHeaders.contains(headerName)) {
							headerValue = request.getHeader(headerName);
							template.header(headerName, headerValue);
						}
					}
				}

			}
		};
	}

}
