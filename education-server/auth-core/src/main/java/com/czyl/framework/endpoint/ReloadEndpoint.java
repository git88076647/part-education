
package com.czyl.framework.endpoint;

import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import com.czyl.common.web.domain.AjaxResult;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link Endpoint @Endpoint} to reload the {@link ApplicationContext}.
 *
 * @author tanghx
 */
@Endpoint(id = "reload", enableByDefault = false)
@Slf4j
public class ReloadEndpoint implements ApplicationContextAware {

	private static final AjaxResult NO_CONTEXT_MESSAGE = AjaxResult.error("No context to reload");

	private static final AjaxResult RELOAD_MESSAGE = AjaxResult.success("reloading on, bye...");

	private ConfigurableApplicationContext context;

	@WriteOperation
	public AjaxResult reload() {
		log.info("重启服务");
		if (this.context == null) {
			return NO_CONTEXT_MESSAGE;
		}
		try {
			return RELOAD_MESSAGE;
		} finally {
			Thread thread = new Thread(this::performReload);
			thread.setContextClassLoader(getClass().getClassLoader());
			thread.start();
		}
	}

	private void performReload() {
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		this.context.stop();
//		this.context.start(); 
		//TODO 这里得重新实现，这个stop是通知注册中心下线 不提供服务，实际上根据IP地址可以访问。
		
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		if (context instanceof ConfigurableApplicationContext) {
			this.context = (ConfigurableApplicationContext) context;
		}
	}

}
