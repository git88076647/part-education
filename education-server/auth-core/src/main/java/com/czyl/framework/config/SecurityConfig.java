package com.czyl.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.czyl.framework.security.filter.JwtAuthenticationTokenFilter;
import com.czyl.framework.security.handle.AuthenticationEntryPointImpl;
import com.czyl.framework.security.handle.LogoutSuccessHandlerImpl;

/**
 * spring security配置
 * 
 * @author tanghx
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * 认证失败处理类
	 */
	@Autowired
	private AuthenticationEntryPointImpl unauthorizedHandler;

	/**
	 * 退出处理类
	 */
	@Autowired
	private LogoutSuccessHandlerImpl logoutSuccessHandler;

	/**
	 * token认证过滤器
	 */
	@Autowired
	private JwtAuthenticationTokenFilter authenticationTokenFilter;

	@Autowired
	DruidStatProperties properties;

	/**
	 * 解决 无法直接注入 AuthenticationManager
	 *
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * anyRequest | 匹配所有请求路径 access | SpringEl表达式结果为true时可以访问 anonymous | 匿名可以访问
	 * denyAll | 用户不能访问 fullyAuthenticated | 用户完全认证可以访问（非remember-me下自动登录）
	 * hasAnyAuthority | 如果有参数，参数表示权限，则其中任何一个权限可以访问 hasAnyRole |
	 * 如果有参数，参数表示角色，则其中任何一个角色可以访问 hasAuthority | 如果有参数，参数表示权限，则其权限可以访问 hasIpAddress
	 * | 如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问 hasRole | 如果有参数，参数表示角色，则其角色可以访问 permitAll
	 * | 用户可以任意访问 rememberMe | 允许通过remember-me登录的用户访问 authenticated | 用户登录后可访问
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		 /** 获取web监控页面的参数 */
		DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
		/** 提取common.js的配置路径 */
		String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
		
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = httpSecurity
				/** CRSF禁用，因为不使用session */
				.csrf().disable()
				/** 认证失败处理类 */
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				/** 基于token，所以不需要session */
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				/** 过滤请求 */
				.authorizeRequests();
				if(CzylConfig.getPermitAllUrls() != null && !CzylConfig.getPermitAllUrls().trim().isEmpty()) {
					String[] urls = CzylConfig.getPermitAllUrls().split(",");
					expressionInterceptUrlRegistry = expressionInterceptUrlRegistry.antMatchers(urls).permitAll();
				}
				/** 对于登录login 验证码captchaImage 允许匿名访问,登录后可能会在其他地方使用获取验证码 */
		expressionInterceptUrlRegistry.antMatchers("/login","/captchaImage").permitAll()
				/**测试该接口的时候可能已登录，所以需要无条件可访问 */
				.antMatchers("/clientLogin").permitAll()
				/** druid的所有资源包括html都需要校验  */
				.antMatchers(pattern).authenticated()
				.antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
				.antMatchers("/monitor/server/ping").permitAll()
				.antMatchers("/profile/**").permitAll()
				.antMatchers("/common/download**").permitAll()
				
				/**访问该页面的时候可能已登录，所以需要无条件可访问   让他们可以通过spring security的安全体系*/
				.antMatchers(
                       "/swagger-ui.html",
                       "/swagger-resources/**",
                       "/*/api-docs",
                       "/webjars/**"
               ).permitAll()

				/**注册中心判断服务是否可用的3个actuator不需要权限外，其他都要权限 */
				.antMatchers("/actuator/health").permitAll()
				.antMatchers("/actuator/health/*").permitAll()
				.antMatchers("/actuator/info").permitAll()
				
//				.antMatchers("/actuator/reload").authenticated()
//				.antMatchers("/actuator/refresh").authenticated()
//				.antMatchers("/actuator/shutdown").authenticated()
//				.antMatchers("/actuator/env").authenticated()
//				.antMatchers("/actuator/beans").authenticated()
				.antMatchers("/actuator/**").authenticated()
				/** 除上面外的所有请求全部需要鉴权认证 */
				.anyRequest().authenticated()

				.and().headers().frameOptions().disable();
		httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
		/** 添加JWT filter*/
		httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

	/**
	 * 强散列哈希加密实现
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new CustomBCryptPasswordEncoder();
	}

	/**
	 * 身份认证接口
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//此处是用户登录时认证使用，而除了授权服务，其他服务都不允许颁发授权，全靠filter和securty 调用PermissionService -> tokenService 进行鉴权
//		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
}
