package com.czyl.framework.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.StringUtils;
import com.czyl.framework.aspectj.lang.enums.DataSourceType;

import lombok.extern.slf4j.Slf4j;

/**
 * Sharding 配置多数据源
 * 
 * @author tanghx
 */
@Configuration
@Slf4j
@ConditionalOnProperty(name = "spring.datasource.sharding", havingValue = "true")
public class ShardingDataSourceConfig {
	@Value("${spring.datasource.shardingyaml}")
	private String shardingyaml;

	@Bean(DataSourceType.SHARDING_BEAN)
	public DataSource getShardingDataSource() {
		if (StringUtils.isBlank(shardingyaml)) {
			log.error("spring.datasource.shardingyaml is empty");
			throw new CustomException("spring.datasource.shardingyaml is empty");
		}
		DataSource dataSource = null;
		org.springframework.core.io.ClassPathResource resource = new org.springframework.core.io.ClassPathResource(shardingyaml);
		try {
			dataSource = YamlShardingDataSourceFactory.createDataSource(resource.getFile());
		} catch (SQLException e) {
			log.error("装载分片异常SQLException", e);
			throw new CustomException(String.format("%s config SQLException", shardingyaml),e);
		} catch (FileNotFoundException e) {
			throw new CustomException(e.getMessage());
		} catch (IOException e) {
			log.error("装载分片异常IOException", e);
			throw new CustomException(String.format("%s config IOException", shardingyaml));
		}
		return dataSource;
	}

}
