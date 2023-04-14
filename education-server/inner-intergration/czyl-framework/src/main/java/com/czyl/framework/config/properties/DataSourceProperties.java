package com.czyl.framework.config.properties;

import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import com.czyl.framework.config.properties.druid.DruidConfig;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据源属性
 * @author tanghx
 *
 */
@Setter
@Getter
@Configuration
public class DataSourceProperties {
	
	boolean enabled = true;
	
	String url;
	
	String username;
	
	String password;

	String driverClassName;

	String dbType;


	/**
	 * Druid参数配置
	 */
	@NestedConfigurationProperty
	private DruidConfig druid = new DruidConfig();
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
