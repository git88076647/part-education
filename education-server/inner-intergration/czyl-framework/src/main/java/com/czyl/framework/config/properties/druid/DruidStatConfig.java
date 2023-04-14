
package com.czyl.framework.config.properties.druid;

import lombok.Data;

/**
 * Druid监控配置
 *
 * @author tanghx
 */
@Data
public class DruidStatConfig {

	private boolean enabled;

	private Long slowSqlMillis;

	private Boolean logSlowSql;

	private Boolean mergeSql;

	private Integer sqlMaxSize;

}