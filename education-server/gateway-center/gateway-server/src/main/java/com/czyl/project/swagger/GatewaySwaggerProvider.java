package com.czyl.project.swagger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.czyl.common.constant.SwaggerConstant;
import com.czyl.framework.config.CzylConfig;

import lombok.AllArgsConstructor;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/**
 * 
 * @Primary注解的实例优先于其他实例被注入
 */
@Component
@Primary
@AllArgsConstructor
public class GatewaySwaggerProvider implements SwaggerResourcesProvider {
	
	@Autowired
	private RouteLocator routeLocator;

	private final GatewayProperties gatewayProperties;

	@Override
	public List<SwaggerResource> get() {

		List<SwaggerResource> resources = new ArrayList<>();
		Set<String> routes = new HashSet<>();
		// 取出Spring Cloud Gateway中的route
		routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));

		// 配置路由服务指定了的服务才自动生成
		gatewayProperties.getRoutes().stream().filter(routeDefinition -> (
		CzylConfig.getOpenswagger().contains(routeDefinition.getId()) && routes.contains(routeDefinition.getId())
		)).forEach(routeDefinition -> routeDefinition.getPredicates().stream()
				.filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
				.forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId(), predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("/**", SwaggerConstant.API_DOC_PATH)))));
		return resources;
	}

	private SwaggerResource swaggerResource(String name, String location) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion("2.2");
		return swaggerResource;
	}

}