package com.czyl.framework.config.properties.druid;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Druid全局属性装配
 * @author tanghx
 *
 */
@ConfigurationProperties(prefix = GlobalDruidConfig.PREFIX)
public class GlobalDruidConfig {
	public static final String PREFIX = "spring.datasource";

	
	/**
	 * Druid参数配置
	 */
	@NestedConfigurationProperty
	private DruidConfig druid = new DruidConfig();


	public DruidConfig getDruid() {
		return druid;
	}


	public void setDruid(DruidConfig druid) {
		this.druid = druid;
	}
	
	
}
