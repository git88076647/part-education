package com.czyl.project.log.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.AppContextUtils;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.utils.poi.ExcelUtil;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.interceptor.annotation.RepeatSubmit;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.log.domain.SysLogininfor;
import com.czyl.project.log.service.ISysLogininforService;
import com.github.pagehelper.PageHelper;

/**
 * 系统访问记录
 * 
 * @author tanghx
 */
@RestController
@RequestMapping("/log/logininfor")
public class SysLogininforController extends BaseController {
	@Autowired
	private ISysLogininforService logininforService;

	@GetMapping("/mylist")
	public TableDataInfo myList(SysLogininfor logininfor) {
		if(StringUtils.isBlank(logininfor.getBeginTime())) {
			throw new CustomException("登录日期必填！");
		}
		logininfor.date2dateTime();
		PageHelper.startPage(1, 20, "login_time desc");
		logininfor.setUserCode(AppContextUtils.getUserCode());
		List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
		return getDataTable(list);
	}
	
	@PreAuthorize("@ss.hasPermi('monitor:logininfor:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysLogininfor logininfor) {
		if(StringUtils.isBlank(logininfor.getBeginTime())) {
			throw new CustomException("登录日期必填！");
		}
		logininfor.date2dateTime();
		startPage();
		PageHelper.orderBy("login_time desc");
		if(!AppContextUtils.isAdmin() && !AppContextUtils.hasAdminRole()) {
			logininfor.setUserCode(AppContextUtils.getUserCode());
		}
		List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
		return getDataTable(list);
	}

	@Log(title = "登陆日志", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('monitor:logininfor:export')")
	@GetMapping("/export")
	public AjaxResult export(SysLogininfor logininfor) {
		if(!AppContextUtils.isAdmin() && !AppContextUtils.hasAdminRole()) {
			logininfor.setUserCode(AppContextUtils.getUserCode());
		}
		List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
		ExcelUtil<SysLogininfor> util = new ExcelUtil<SysLogininfor>(SysLogininfor.class);
		return util.exportExcel(list, "登陆日志");
	}

	@PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
	@Log(title = "登陆日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{infoIds}")
	@RepeatSubmit
	public AjaxResult remove(@PathVariable Long[] infoIds) {
		return toAjax(logininforService.deleteLogininforByIds(infoIds));
	}

	@PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
	@Log(title = "登陆日志", businessType = BusinessType.CLEAN)
	@DeleteMapping("/clean")
	@RepeatSubmit
	public AjaxResult clean() {
		logininforService.cleanLogininfor();
		return AjaxResult.success();
	}

	/** 不能加@Log注解否则是死循环*/
	@PostMapping()
	public AjaxResult add(@RequestBody SysLogininfor logininfor) {
		logininforService.insertLogininfor(logininfor);
		return AjaxResult.success();
	}
}
