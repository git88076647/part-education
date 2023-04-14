package com.czyl.project.monitor.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.czyl.common.constant.HttpStatus;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.config.EurekaConfig;
import com.czyl.framework.config.RestTemplateConfig;
import com.czyl.framework.interceptor.annotation.RepeatSubmit;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.domain.TreeSelect;

import lombok.extern.slf4j.Slf4j;

/**
 * 服务治理
 * 
 * @author tanghx
 */
@RestController
@RequestMapping("/monitor/apps")
@Slf4j
public class AppController extends BaseController {

	@Autowired
	@Qualifier(EurekaConfig.REST_TEMPLATE_EUREKA)
	RestTemplate restTemplateEureka;

	@Autowired
	@Qualifier(RestTemplateConfig.REST_TEMPLATE_NORMAL)
	RestTemplate restTemplateNormal;

	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("@ss.hasPermi('monitor:apps:list')")
	@GetMapping()
	public AjaxResult getApps() throws Exception {
		Map<?, ?> ret = restTemplateEureka.getForObject("http://eureka-server/eureka/apps", Map.class);
		List<TreeSelect> tree = new ArrayList<TreeSelect>();
		List<Map> applications = (List<Map>) ((Map) ret.get("applications")).get("application");
		for (int i = 0, len = applications.size(); i < len; i++) {
			String name = applications.get(i).get("name").toString();
//			if("EUREKA-SERVER".equalsIgnoreCase(name) || "ZUUL-SERVER".equalsIgnoreCase(name)) {
//				continue;
//			}
			List instanceList = (List) applications.get(i).get("instance");
			tree.add(new TreeSelect(name, name, null, instanceList.size()));
		}
		return AjaxResult.success(tree);
	}

	@PreAuthorize("@ss.hasPermi('monitor:apps:list')")
	@GetMapping("/{appId}")
	public AjaxResult getInstances(@PathVariable String appId) throws Exception {
		Map<?, ?> ret = restTemplateEureka.getForObject("http://eureka-server/eureka/apps/" + appId, Map.class);
		return AjaxResult.success(ret);
	}

	@PreAuthorize("@ss.hasPermi('monitor:apps:status')")
	@PutMapping("/{appId}/{instanceId}/down")
	@RepeatSubmit
	@Log(title = "服务治理", businessType = BusinessType.DOWNLINE)
	public AjaxResult down(@PathVariable String appId, @PathVariable String instanceId) throws Exception {
		if ("EUREKA-SERVER".equalsIgnoreCase(appId) || "ZUUL-SERVER".equalsIgnoreCase(appId)) {
			return AjaxResult.error(appId + " 禁止操作");
		}
		restTemplateEureka.put("http://eureka-server/eureka/apps/" + appId + "/" + instanceId + "/status?value=OUT_OF_SERVICE", null);
		return AjaxResult.success();
	}

	@PreAuthorize("@ss.hasPermi('monitor:apps:status')")
	@DeleteMapping("/{appId}/{instanceId}/up")
	@Log(title = "服务治理", businessType = BusinessType.UPLINE)
	public AjaxResult up(@PathVariable String appId, @PathVariable String instanceId) throws Exception {
		if ("EUREKA-SERVER".equalsIgnoreCase(appId) || "ZUUL-SERVER".equalsIgnoreCase(appId)) {
			return AjaxResult.error(appId + " 禁止操作");
		}
		restTemplateEureka.delete("http://eureka-server/eureka/apps/" + appId + "/" + instanceId + "/status?value=UP");
		return AjaxResult.success();
	}

	@SuppressWarnings("deprecation")
	@PreAuthorize("@ss.hasPermi('monitor:apps:shutdown')")
	@PutMapping("/{appId}/{instanceId}/shutdown")
	@Log(title = "服务治理", businessType = BusinessType.SHUTDOWN)
	public AjaxResult shutdown(@PathVariable String appId, @PathVariable String instanceId) throws Exception {
		if ("EUREKA-SERVER".equalsIgnoreCase(appId) || "ZUUL-SERVER".equalsIgnoreCase(appId)) {
			return AjaxResult.error(appId + " 禁止操作");
		}
		Map<?, ?> instanceMap = restTemplateEureka.getForObject("http://eureka-server/eureka/apps/" + appId + "/" + instanceId, Map.class);
		Map<?, ?> instance = (Map<?, ?>) instanceMap.get("instance");
		String url = String.format("http://%s:%s/actuator/shutdown", instance.get("hostName"), ((Map<?, ?>) instance.get("port")).get("$"));
		Map<?, ?> retMsg = null;
		try {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON_UTF8);
			HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(header);
			retMsg = restTemplateNormal.postForObject(url, httpEntity, Map.class);
		} catch (Exception e) {
			log.warn("服务治理-停机",e);
			return AjaxResult.error();
		}
		if (retMsg.get("message") != null && retMsg.get("message").toString().contains("Shutting")) {
			return AjaxResult.success();
		} else {
			return retMsg.get("msg") != null ? AjaxResult.error(retMsg.get("msg").toString()) : (retMsg.get("message") != null ? AjaxResult.error(retMsg.get("message").toString()) : AjaxResult.error());
		}
	}

	@SuppressWarnings("deprecation")
	@PreAuthorize("@ss.hasPermi('monitor:apps:reload')")
	@PutMapping("/{appId}/{instanceId}/reload")
	@Log(title = "服务治理", businessType = BusinessType.RELOAD)
	public AjaxResult reload(@PathVariable String appId, @PathVariable String instanceId) throws Exception {
		if ("ZUUL-SERVER".equalsIgnoreCase(appId)) {
			return AjaxResult.error(appId + " 禁止操作");
		}
		Map<?, ?> instanceMap = restTemplateEureka.getForObject("http://eureka-server/eureka/apps/" + appId + "/" + instanceId, Map.class);
		Map<?, ?> instance = (Map<?, ?>) instanceMap.get("instance");
		String url = String.format("http://%s:%s/actuator/reload", instance.get("hostName"), ((Map<?, ?>) instance.get("port")).get("$"));
		Map<?, ?> retMsg = null;
		try {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON_UTF8);
			HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(header);
			retMsg = restTemplateNormal.postForObject(url, httpEntity, Map.class);
		} catch (Exception e) {
			log.warn("服务治理-重载",e);
			return AjaxResult.error("操作失败");
		}
		if (!String.valueOf(HttpStatus.SUCCESS).equals(retMsg.get("code"))) {
			return AjaxResult.success();
		} else {
			return retMsg.get("msg") == null ? AjaxResult.error() : AjaxResult.error(retMsg.get("msg").toString());
		}
	}

	@SuppressWarnings("deprecation")
	@PreAuthorize("@ss.hasPermi('monitor:apps:refresh')")
	@PutMapping("/{appId}/{instanceId}/refresh")
	@Log(title = "服务治理", businessType = BusinessType.REFRESH)
	public AjaxResult refresh(@PathVariable String appId, @PathVariable String instanceId) throws Exception {
		if ("ZUUL-SERVER".equalsIgnoreCase(appId)) {
			return AjaxResult.error(appId + " 禁止操作");
		}
		Map<?, ?> instanceMap = restTemplateEureka.getForObject("http://eureka-server/eureka/apps/" + appId + "/" + instanceId, Map.class);
		Map<?, ?> instance = (Map<?, ?>) instanceMap.get("instance");
		String url = String.format("http://%s:%s/actuator/refresh", instance.get("hostName"), ((Map<?, ?>) instance.get("port")).get("$"));

		try {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON_UTF8);
//			header.add("User-Agent","Chrome/69.0.3497.81 Safari/537.36");
			HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(header);
			String retMsg = restTemplateNormal.postForObject(url, httpEntity, String.class);
			if ("[]".equals(retMsg)) {
				return AjaxResult.success();
			} else {
				return AjaxResult.error(retMsg);
			}
		} catch (Exception e) {
			log.warn("服务治理-刷新", e);
			return AjaxResult.error();
		}

	}

}
