package com.czyl.framework.config.properties;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.czyl.framework.config.properties.druid.DruidConfig;
import com.czyl.framework.config.properties.druid.GlobalDruidConfig;

/**
 * 动态数据源属性
 * @author tanghx
 *
 */
@ConfigurationProperties(prefix = DynamicDataSourceProperties.PREFIX)
@EnableConfigurationProperties(GlobalDruidConfig.class)
public class DynamicDataSourceProperties {

	public static final String PREFIX = "spring.datasource.dynamic";

	/** 设置默认的数据源或者数据源组,默认值即为master */
	private String primary = "master";

	private Map<String, DataSourceProperties> datasource = new LinkedHashMap<>();

	/**
	 * Druid全局参数配置
	 */
	@Autowired
	private GlobalDruidConfig globalDruidConfig;

	public String getPrimary() {
		return primary;
	}

	public void setPrimary(String primary) {
		this.primary = primary;
	}

	public Map<String, DataSourceProperties> getDatasource() {
		return datasource;
	}

	public void setDatasource(Map<String, DataSourceProperties> datasource) {
		this.datasource = datasource;
	}

	public DruidConfig getDruid() {
		return globalDruidConfig.getDruid();
	}

	public GlobalDruidConfig getGlobalDruidConfig() {
		return globalDruidConfig;
	}

	public void setGlobalDruidConfig(GlobalDruidConfig globalDruidConfig) {
		this.globalDruidConfig = globalDruidConfig;
	}

}
