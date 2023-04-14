package com.czyl.framework.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.czyl.framework.uid.impl.DefaultUidGenerator;
import com.czyl.framework.uid.worker.DisposableWorkerIdAssigner;

/**
 * UID装配
 * @author tanghx
 *
 */
@Configuration
@ConditionalOnProperty(name = UidConfig.PREFIX,havingValue = "true")
public class UidConfig {
	public static final String PREFIX="spring.datasource.dynamic.datasource.workerNode.enabled";
	
	@Bean
	public DefaultUidGenerator getDefaultUidGenerator(DisposableWorkerIdAssigner disposableWorkerIdAssigner) {
		DefaultUidGenerator defaultUidGenerator = new DefaultUidGenerator();
		defaultUidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner);
		
		return defaultUidGenerator;
	}

}
