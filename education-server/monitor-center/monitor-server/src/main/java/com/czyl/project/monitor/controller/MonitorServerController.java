package com.czyl.project.monitor.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.config.EurekaConfig;
import com.czyl.framework.config.RestTemplateConfig;
import com.czyl.framework.interceptor.annotation.RepeatSubmit;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.domain.TreeSelect;

import lombok.extern.slf4j.Slf4j;

/**
 * 服务器监控
 * 
 * @author tanghx
 */
@Slf4j
@RestController
@RequestMapping("/monitor/server")
public class MonitorServerController extends BaseController {

	@Autowired
	@Qualifier(EurekaConfig.REST_TEMPLATE_EUREKA)
	RestTemplate restTemplateEureka;

	@Autowired
	@Qualifier(RestTemplateConfig.REST_TEMPLATE_NORMAL)
	RestTemplate restTemplateNormal;

	@PreAuthorize("@ss.hasPermi('monitor:server:info')")
	@GetMapping("/{appId}/{instanceId}/info")
	@RepeatSubmit
	public AjaxResult getInfo(@PathVariable String appId, @PathVariable String instanceId) throws Exception {
		// 调用其他所有微服务的 monitor/server/info 接口获取服务器信息集
		Map<?, ?> instanceMap = restTemplateEureka.getForObject("http://eureka-server/eureka/apps/" + appId + "/" + instanceId, Map.class);
		Map<?, ?> instance = (Map<?, ?>) instanceMap.get("instance");
		String url = String.format("http://%s:%s/monitor/server/info", instance.get("hostName"), ((Map<?, ?>) instance.get("port")).get("$"));
		AjaxResult retMsg = null;
		try {
			retMsg = restTemplateNormal.getForObject(url, AjaxResult.class);
		} catch (Exception e) {
			log.warn("获取服务信息失败", e);
			return AjaxResult.error("获取");
		}
		return retMsg;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PreAuthorize("@ss.hasPermi('monitor:druid:list')")
	@GetMapping("/druid/list")
	public AjaxResult list() throws Exception {
		Map<?, ?> appsMap = restTemplateEureka.getForObject("http://eureka-server/eureka/apps/", Map.class);
		List<TreeSelect> tree = new ArrayList<TreeSelect>();
		List<Map> applications = (List<Map>) ((Map) appsMap.get("applications")).get("application");
		String name;
		String label;
		String ip;
		String port;
		List<Map> instanceList;
		for (int i = 0, len = applications.size(); i < len; i++) {
			 name = applications.get(i).get("name").toString();
			if ("EUREKA-SERVER".equalsIgnoreCase(name) || "ZUUL-SERVER".equalsIgnoreCase(name) || "MONITOR-SERVER".equalsIgnoreCase(name)) {
				continue;
			}
			instanceList = (List<Map>) applications.get(i).get("instance");
			for (int j = 0, len2 = instanceList.size(); j < len2; j++) {
				label = instanceList.get(j).get("instanceId").toString();
				ip = instanceList.get(j).get("ipAddr").toString();
				port = ((Map) (instanceList.get(j).get("port"))).get("$").toString();
				tree.add(new TreeSelect(String.format("http://%s:%s/czyldatasourceboard/index.html", ip,port), label, null,0));
			}
		}
		return AjaxResult.success(tree);

	}
}
