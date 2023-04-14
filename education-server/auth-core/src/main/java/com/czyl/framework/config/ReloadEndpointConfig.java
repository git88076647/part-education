
package com.czyl.framework.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.czyl.framework.endpoint.ReloadEndpoint;

/**
 *  reload 端点
 *
 * @author tanghx
 */
@Configuration
@ConditionalOnProperty(prefix = "management.endpoint.reload", name = "enabled", havingValue = "true")
public class ReloadEndpointConfig {

	@Bean(destroyMethod = "")
	public ReloadEndpoint reloadEndpoint() {
		return new ReloadEndpoint();
	}

}
