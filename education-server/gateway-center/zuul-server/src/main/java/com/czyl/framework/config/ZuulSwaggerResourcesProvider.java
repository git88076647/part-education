package com.czyl.framework.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/**
 * ZUUL 集成swagger
 * @author tanghx
 *
 */
@Component
@Primary
public class ZuulSwaggerResourcesProvider implements SwaggerResourcesProvider {
	
	private final RouteLocator routeLocator;

	public ZuulSwaggerResourcesProvider(RouteLocator routeLocator) {
		this.routeLocator = routeLocator;
	}

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
		List<Route> routes = routeLocator.getRoutes();
		routes.forEach(route -> {
			if(CzylConfig.getOpenswagger().contains(route.getId())) {
				resources.add(swaggerResource(route.getId(), route.getFullPath().replace("**", "v2/api-docs"), "2.2"));
			}
		});
		return resources;
	}

	private SwaggerResource swaggerResource(String name, String location, String version) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion(version);
		return swaggerResource;
	}
}