package com.czyl.project.job.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.czyl.framework.aspectj.lang.annotation.DataSource;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.project.job.domain.SysJobLog;
import com.czyl.project.job.service.ISysJobLogService;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.common.utils.poi.ExcelUtil;
import com.czyl.framework.web.page.TableDataInfo;

/**
 * 任务调度日志Controller
 * 
 * @author tanghx
 * @date 2020-02-25
 */
@RestController
@RequestMapping("/dispatcher/joblog")
public class SysJobLogController extends BaseController {
	@Autowired
	private ISysJobLogService sysJobLogService;

	/**
	 * 查询任务调度日志列表
	 */
	@PreAuthorize("@ss.hasPermi('dispatcher:joblog:list')")
	@GetMapping("/list")
	@DataSource("slave")
	public TableDataInfo list(SysJobLog sysJobLog) {
		startPage();
		List<SysJobLog> list = sysJobLogService.selectSysJobLogList(sysJobLog);
		return getDataTable(list);
	}

	/**
	 * 导出任务调度日志列表
	 */
	@PreAuthorize("@ss.hasPermi('dispatcher:joblog:export')")
	@Log(title = "任务调度日志", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	@DataSource("slave")
	public AjaxResult export(SysJobLog sysJobLog) {
		List<SysJobLog> list = sysJobLogService.selectSysJobLogList(sysJobLog);
		ExcelUtil<SysJobLog> util = new ExcelUtil<SysJobLog>(SysJobLog.class);
		return util.exportExcel(list, "joblog");
	}

	/**
	 * 获取任务调度日志详细信息
	 */
	@PreAuthorize("@ss.hasPermi('dispatcher:joblog:query')")
	@GetMapping(value = "/{jobLogId}")
	@DataSource("slave")
	public AjaxResult getInfo(@PathVariable("jobLogId") Long jobLogId) {
		return AjaxResult.success(sysJobLogService.selectSysJobLogById(jobLogId));
	}

	/**
	 * 新增任务调度日志
	 */
	@PreAuthorize("@ss.hasPermi('dispatcher:joblog:add')")
	@Log(title = "任务调度日志", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody SysJobLog sysJobLog) {
		return toAjax(sysJobLogService.insertSysJobLog(sysJobLog));
	}

	/**
	 * 修改任务调度日志
	 */
	@PreAuthorize("@ss.hasPermi('dispatcher:joblog:edit')")
	@Log(title = "任务调度日志", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody SysJobLog sysJobLog) {
		return toAjax(sysJobLogService.updateSysJobLog(sysJobLog));
	}

	/**
	 * 删除任务调度日志
	 */
	@PreAuthorize("@ss.hasPermi('dispatcher:joblog:remove')")
	@Log(title = "任务调度日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{jobLogIds}")
	public AjaxResult remove(@PathVariable Long[] jobLogIds) {
		return toAjax(sysJobLogService.deleteSysJobLogByIds(jobLogIds));
	}
	
	/**
	 * 清空任务调度日志
	 */
	@PreAuthorize("@ss.hasPermi('dispatcher:joblog:clean')")
	@Log(title = "任务调度日志", businessType = BusinessType.CLEAN)
	@DeleteMapping("/clean")
	public AjaxResult clean() {
		sysJobLogService.cleanJobLog();
		return success();
	}
}
