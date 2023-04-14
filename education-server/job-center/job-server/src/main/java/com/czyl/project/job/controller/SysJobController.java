package com.czyl.project.job.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.czyl.common.utils.cron.CronUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.DataSource;
import com.czyl.framework.aspectj.lang.annotation.Lock4j;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.job.domain.SysJob;
import com.czyl.project.job.service.ISysJobService;

/**
 * 任务调度Controller
 * 
 * @author tanghx
 * @date 2020-02-24
 */
@RestController
@RequestMapping("/dispatcher/job")
public class SysJobController extends BaseController {
	@Autowired
	private ISysJobService sysJobService;

	/**
	 * 查询任务调度列表
	 */
	@PreAuthorize("@ss.hasPermi('dispatcher:job:list')")
	@GetMapping("/list")
	@DataSource("slave")
	public TableDataInfo list(SysJob sysJob) {
		startPage();
		List<SysJob> list = sysJobService.selectSysJobList(sysJob);
		return getDataTable(list);
	}

	/**
	 * 获取任务调度详细信息
	 */
	@PreAuthorize("@ss.hasPermi('dispatcher:job:query')")
	@GetMapping(value = "/{jobId}")
	@DataSource("slave")
	public AjaxResult getInfo(@PathVariable("jobId") Long jobId) {
		return AjaxResult.success(sysJobService.selectSysJobById(jobId));
	}

	/**
	 * 新增任务调度
	 */
	@PreAuthorize("@ss.hasPermi('dispatcher:job:add')")
	@Log(title = "任务调度", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody SysJob sysJob) {
		return toAjax(sysJobService.insertSysJob(sysJob));
	}

	/**
	 * 修改任务调度
	 */
	@PreAuthorize("@ss.hasPermi('dispatcher:job:edit')")
	@Log(title = "任务调度", businessType = BusinessType.UPDATE)
	@PutMapping
	@Lock4j(keys= {"#sysJob.jobId"})
	public AjaxResult edit(@RequestBody SysJob sysJob) {
		return toAjax(sysJobService.updateSysJob(sysJob));
	}

	/**
	 * 删除任务调度
	 */
	@PreAuthorize("@ss.hasPermi('dispatcher:job:remove')")
	@Log(title = "任务调度", businessType = BusinessType.DELETE)
	@DeleteMapping("/{jobIds}")
	public AjaxResult remove(@PathVariable Long[] jobIds) {
		return toAjax(sysJobService.deleteSysJobByIds(jobIds));
	}

	/**
	 * 任务调度状态修改
	 */
	@Log(title = "任务调度", businessType = BusinessType.UPDATE)
	@PreAuthorize("@ss.hasPermi('dispatcher:job:changeStatus')")
	@PutMapping("/changeStatus")
	@ResponseBody
	public AjaxResult changeStatus(@RequestBody SysJob job) {
		return toAjax(sysJobService.changeStatus(job));
	}

	/**
	 * 任务调度立即执行一次
	 */
	@Log(title = "任务调度", businessType = BusinessType.RUN)
	@PreAuthorize("@ss.hasPermi('dispatcher:job:run')")
	@PostMapping("/run")
	@ResponseBody
	public AjaxResult run(Long jobId) {
		SysJob job = sysJobService.selectSysJobById(jobId);
		if(job == null) {
			return AjaxResult.error();
		}
		sysJobService.run(job);
		return AjaxResult.success("执行成功！");
	}

	/**
	 * 校验cron表达式是否有效
	 */
	@PostMapping("/checkCronExpressionIsValid")
	@ResponseBody
	public boolean checkCronExpressionIsValid(SysJob job) {
		return CronUtils.isValid(job.getCronExpression());
	}
}
