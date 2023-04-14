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
import com.czyl.project.log.domain.SysOperLog;
import com.czyl.project.log.service.ISysOperLogService;
import com.github.pagehelper.PageHelper;

/**
 * 操作日志记录
 * 
 * @author tanghx
 */
@RestController
@RequestMapping("/log/operlog")
public class SysOperLogController extends BaseController {
	@Autowired
	private ISysOperLogService operLogService;

	
	@GetMapping("/mylist")
	public TableDataInfo myList(SysOperLog operLog) {
		if(StringUtils.isBlank(operLog.getBeginTime())) {
			throw new CustomException("操作日期必填！");
		}
		operLog.date2dateTime();
		PageHelper.startPage(1, 20, "oper_time desc");
		operLog.setOperName(AppContextUtils.getUserCode());
		List<SysOperLog> list = operLogService.selectOperLogList(operLog);
		return getDataTable(list);
	}
	
	@PreAuthorize("@ss.hasPermi('monitor:operlog:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysOperLog operLog) {
		if(StringUtils.isBlank(operLog.getBeginTime())) {
			throw new CustomException("操作日期必填！");
		}
		operLog.date2dateTime();
		startPage();
		PageHelper.orderBy("oper_time desc");
		if(!AppContextUtils.isAdmin() && !AppContextUtils.hasAdminRole()) {
			operLog.setOperName(AppContextUtils.getUserCode());
		}
		List<SysOperLog> list = operLogService.selectOperLogList(operLog);
		return getDataTable(list);
	}

	@Log(title = "操作日志", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
	@GetMapping("/export")
	public AjaxResult export(SysOperLog operLog) {
		if(!AppContextUtils.isAdmin() && !AppContextUtils.hasAdminRole()) {
			operLog.setOperName(AppContextUtils.getUserCode());
		}
		List<SysOperLog> list = operLogService.selectOperLogList(operLog);
		ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
		return util.exportExcel(list, "操作日志");
	}

	@PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
	@DeleteMapping("/{operIds}")
	@RepeatSubmit
	public AjaxResult remove(@PathVariable Long[] operIds) {
		return toAjax(operLogService.deleteOperLogByIds(operIds));
	}

	@Log(title = "操作日志", businessType = BusinessType.CLEAN)
	@PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
	@DeleteMapping("/clean")
	@RepeatSubmit
	public AjaxResult clean() {
		operLogService.cleanOperLog();
		return AjaxResult.success();
	}
	/**不能加@Log注解否则是死循环*/
	@PostMapping()
	public AjaxResult add(@RequestBody SysOperLog operLog) {
		operLogService.insertOperlog(operLog);
		return AjaxResult.success();
	}
}
