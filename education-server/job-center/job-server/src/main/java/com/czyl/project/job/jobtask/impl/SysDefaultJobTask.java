package com.czyl.project.job.jobtask.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.czyl.framework.config.RestTemplateConfig;
import com.czyl.project.job.jobtask.IJobTask;

import lombok.extern.slf4j.Slf4j;

/**
 * 非微服务的任务调度
 * @author tanghx
 *
 */
@Service("sysDefaultJobTask")
@Slf4j
public class SysDefaultJobTask implements IJobTask {

	@Autowired
	@Qualifier(RestTemplateConfig.REST_TEMPLATE_NORMAL_NO_AUTH)
	RestTemplate restTemplateNormalNoAuth;

	@Override
	public void doExecute(String url) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("第三方系统默认任务调度,参数：{}", url);
		}
		restTemplateNormalNoAuth.postForObject(url, null, String.class);
	}

}
