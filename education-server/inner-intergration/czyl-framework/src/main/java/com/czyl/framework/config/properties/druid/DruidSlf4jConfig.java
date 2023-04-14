
package com.czyl.framework.config.properties.druid;

import lombok.Data;

/**
 * Druid日志配置
 *
 * @author tanghx
 */
@Data
public class DruidSlf4jConfig {

  private Boolean enable = true;

  private Boolean statementExecutableSqlLogEnable = false;
}