package com.czyl.framework.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.util.Utils;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.utils.spring.SpringUtils;
import com.czyl.framework.aspectj.lang.enums.DataSourceType;
import com.czyl.framework.config.properties.DataSourceProperties;
import com.czyl.framework.config.properties.DynamicDataSourceProperties;
import com.czyl.framework.config.properties.druid.DruidConfig;
import com.czyl.framework.config.properties.druid.DruidWallConfigUtil;
import com.czyl.framework.datasource.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * druid 配置多数据源
 * 
 * @author tanghx
 */
@Configuration
@EnableConfigurationProperties({ DynamicDataSourceProperties.class })
@Slf4j
public class DynamicDataSourceConfig {

	@SuppressWarnings("resource")
	@Bean(name = "dynamicDataSource")
	@DependsOn
	@Order
	@Primary
	public DataSource dataSource(DynamicDataSourceProperties dynamicDataSourceProperties) {
		Map<String, DataSourceProperties> dataSourceProperties = dynamicDataSourceProperties.getDatasource();
		DruidConfig gDruid = dynamicDataSourceProperties.getDruid();
		Map<Object, Object> targetDataSources = new LinkedHashMap<>();
		DataSourceProperties value;
		String key;
		// 全局配置
		Properties gProperties = gDruid.toProperties(gDruid);
		List<com.alibaba.druid.filter.Filter> gProxyFilters = new ArrayList<>(2);
		String gFilters = gDruid.getFilters();
		if (StringUtils.isNotBlank(gFilters) && gFilters.contains("stat")) {
			StatFilter statFilter = new StatFilter();
			statFilter.configFromProperties(gProperties);
			gProxyFilters.add(statFilter);
		}
		if (StringUtils.isNotBlank(gFilters) && gFilters.contains("wall")) {
			WallConfig wallConfig = DruidWallConfigUtil.toWallConfig(gDruid.getFilter().getWall(), gDruid.getFilter().getWall());
			WallFilter wallFilter = new WallFilter();
			wallFilter.setConfig(wallConfig);
			gProxyFilters.add(wallFilter);
		}
		for (Map.Entry<String, DataSourceProperties> entry : dataSourceProperties.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			if (value.isEnabled()) {
				DruidDataSource druidDataSource = new DruidDataSource();
				druidDataSource.setUrl(value.getUrl());
				druidDataSource.setUsername(value.getUsername());
				druidDataSource.setPassword(value.getPassword());
				druidDataSource.setDbType(value.getDbType());
				druidDataSource.setDriverClassName(value.getDriverClassName());
				// 本地配置
				DruidConfig cDruid = value.getDruid();
				Properties cProperties = cDruid.toProperties(gDruid);
				String cFilters = cDruid.getFilters();

				List<com.alibaba.druid.filter.Filter> cProxyFilters = new ArrayList<>(2);
				if (StringUtils.isBlank(cFilters)) {
					// 浅拷贝List，否则多数据源会有问题
					cProxyFilters = new ArrayList<>(gProxyFilters);
				} else {
					if (StringUtils.isNotBlank(cFilters) && cFilters.contains("stat")) {
						StatFilter statFilter = new StatFilter();
						statFilter.configFromProperties(cProperties);
						cProxyFilters.add(statFilter);
					}
					if (StringUtils.isNotBlank(cFilters) && cFilters.contains("wall")) {
						WallConfig wallConfig = DruidWallConfigUtil.toWallConfig(cDruid.getFilter().getWall(), gDruid.getFilter().getWall());
						WallFilter wallFilter = new WallFilter();
						wallFilter.setConfig(wallConfig);
						cProxyFilters.add(wallFilter);
					}

				}
				druidDataSource.configFromPropety(cProperties);
				//清除cProperties 产生的默认filter配置
				druidDataSource.setClearFiltersEnable(true);
				druidDataSource.clearFilters();
				druidDataSource.setProxyFilters(cProxyFilters);
				try {
					druidDataSource.init();
				} catch (SQLException e) {
					throw new CustomException("create druid error", e);
				}
				targetDataSources.put(key, druidDataSource);

			}
		}

		// 数据分片数据源
		if (SpringUtils.containsBean(DataSourceType.SHARDING_BEAN)) {
			// 排除 workerNode 数据源 如果没有数据源，则sharding数据源为默认数据源
			if (targetDataSources.size() == 0 || (targetDataSources.size() == 1 && targetDataSources.containsKey(DataSourceType.WORKER_NODE))) {
				dynamicDataSourceProperties.setPrimary(DataSourceType.SHARDING);
			}
			targetDataSources.put(DataSourceType.SHARDING, SpringUtils.getBean(DataSourceType.SHARDING_BEAN));
		}
		
		if (targetDataSources.size() > 0 && targetDataSources.containsKey(dynamicDataSourceProperties.getPrimary()) == false) {
			throw new CustomException(String.format("未配置数据源%s启动失败", dynamicDataSourceProperties.getPrimary()));
		}
		return new DynamicDataSource((DataSource) targetDataSources.get(dynamicDataSourceProperties.getPrimary()), targetDataSources);
	}

	/**
	 * 去除监控页面底部的广告
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	@ConditionalOnProperty(name = "spring.datasource.druid.stat-view-servlet.enabled", havingValue = "true")
	public FilterRegistrationBean removeDruidFilterRegistrationBean(DruidStatProperties properties) {
		// 获取web监控页面的参数
		DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
		// 提取common.js的配置路径
		String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
		String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");
		final String filePath = "support/http/resources/js/common.js";
		// 创建filter进行过滤
		Filter filter = new Filter() {
			String text = null;

			@Override
			public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
			}

			@Override
			public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
				// 获取common.js
				if (text == null) {
					String tmp = Utils.readFromResource(filePath);
					text = tmp.replace("this.buildFooter();", "");
				}
				response.setContentType("text/javascript");
				response.getWriter().write(text);
			}

			@Override
			public void destroy() {
			}
		};
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(filter);
		registrationBean.addUrlPatterns(commonJsPattern);
		return registrationBean;
	}
}
