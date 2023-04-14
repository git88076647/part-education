package com.czyl.project.monitor.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czyl.common.utils.DateUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.domain.Server;

/**
 * 服务器监控
 * 
 * @author tanghx
 */
@Controller
@RequestMapping("/monitor/server")
public class ServerController extends BaseController {
	
	@PreAuthorize("@ss.hasPermi('monitor:server:info')")
	@GetMapping("/info")
	@ResponseBody
	public AjaxResult getInfo() throws Exception {
		// 每个服务都需要这个API 用于获取服务器信息
		Server server = new Server();
		server.copyTo();
		return AjaxResult.success(server);
	}
	
	@GetMapping("/ping")
	@ResponseBody
	public AjaxResult ping() throws Exception {
		// 每个服务都需要这个API 用于测试是否可访问
		return AjaxResult.success(DateUtils.getTime());
	}
}
