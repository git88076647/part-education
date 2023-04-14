package com.czyl.project.job.jobtask.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.czyl.project.job.jobtask.IJobTask;

import lombok.extern.slf4j.Slf4j;

/**
 * 微服务任务调度
 * @author tanghx
 *
 */
@Service("microServiceDefaultJobTask")
@Slf4j
public class MicroServiceDefaultJobTask implements IJobTask {

	@Autowired
	RestTemplate restTemplate;

	@Override
	public void doExecute(String url) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("微服务默认任务调度,参数：{}", url);
		}
		restTemplate.postForObject(url, null, String.class);
	}

}
