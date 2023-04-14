package com.czyl.framework.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Swagger2的接口配置
 * 
 * @author tanghx
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class SwaggerConfig {

	/**
	 * 创建API
	 */
	@Bean
	public Docket createRestApi() {
		java.util.List<ResponseMessage> resMsgList = Arrays.asList(
				new ResponseMessageBuilder().code(200).message("操作成功！").build(), 
				new ResponseMessageBuilder().code(401).message("未登录或登录已过期！").build(),
				new ResponseMessageBuilder().code(403).message("没有权限操作，请联系管理员添加相应权限！").build(), 
				new ResponseMessageBuilder().code(404).message("访问路径不存在！").build(),
				new ResponseMessageBuilder().code(500).message("操作失败！").build()
				);

		return new Docket(DocumentationType.SWAGGER_2).pathMapping("/")
				// 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
				.apiInfo(apiInfo())
				// 设置哪些接口暴露给Swagger展示
				.select()
				// 扫描所有有注解的api，用这种方式更灵活
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				// 扫描指定包中的swagger注解
//				 .apis(RequestHandlerSelectors.basePackage("com.czyl.project"))
				// 扫描所有 .apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build()
				.globalResponseMessage(RequestMethod.GET, resMsgList)
				.globalResponseMessage(RequestMethod.POST, resMsgList)
				.globalResponseMessage(RequestMethod.PUT, resMsgList)
				.globalResponseMessage(RequestMethod.DELETE, resMsgList)
				/* 设置安全模式，swagger可以设置访问token */
				.securitySchemes(securitySchemes()).securityContexts(securityContexts());
	}

	/**
	 * 安全模式，这里指定token通过Authorization头请求头传递
	 */
	private List<ApiKey> securitySchemes() {
		List<ApiKey> apiKeyList = new ArrayList<ApiKey>();
		apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
		return apiKeyList;
	}

	/**
	 * 安全上下文
	 */
	private List<SecurityContext> securityContexts() {
		List<SecurityContext> securityContexts = new ArrayList<>();
		securityContexts.add(SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("^(?!auth).*$")).build());
		return securityContexts;
	}

	/**
	 * 默认的安全上引用
	 */
	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		List<SecurityReference> securityReferences = new ArrayList<>();
		securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
		return securityReferences;
	}

	/**
	 * 添加摘要信息
	 */
	private ApiInfo apiInfo() {
		// 用ApiInfoBuilder进行定制
		return new ApiInfoBuilder()
				// 设置标题
				.title(String.format("标题：%s_接口文档", CzylConfig.getName()))
				// 描述
				.description(CzylConfig.getDescription())
				// 作者信息
				.contact(new Contact(CzylConfig.getName(), null, null))
				// 版本
				.version("版本号:" + CzylConfig.getVersion()).build();
	}
}
