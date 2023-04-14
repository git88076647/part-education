package com.czyl.project.system.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.czyl.common.utils.poi.ExcelUtil;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.DataScope;
import com.czyl.framework.aspectj.lang.annotation.Lock4j;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.system.domain.SysPsndoc;
import com.czyl.project.system.service.ISysPsndocService;
import com.czyl.project.system.service.ISysPsnjobService;

/**
 * 人员管理Controller
 * 
 * @author tanghx
 * @date 2019-12-11
 */
@RestController
@RequestMapping("/system/psndoc")
public class SysPsndocController extends BaseController {
	@Autowired
	private ISysPsndocService sysPsndocService;

	@Autowired
	private ISysPsnjobService sysPsnjobService;
	
	/**
	 * 查询人员管理列表
	 */
	@PreAuthorize("@ss.hasPermi('system:psndoc:list')")
	@GetMapping("/list")
	@DataScope(orgAlias = "p")
	public TableDataInfo list(SysPsndoc sysPsndoc) {
		startPage();
		List<SysPsndoc> list = sysPsndocService.selectSysPsndocList(sysPsndoc);
		if(list != null && list.size() > 0) {
			list.get(0).setPsnJobs(sysPsnjobService.selectByParentId(list.get(0).getPsnId()));
		}
		return getDataTable(list);
	}

	/**
	 * 导出人员管理列表
	 */
	@PreAuthorize("@ss.hasPermi('system:psndoc:export')")
	@Log(title = "人员管理", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	@DataScope(orgAlias = "p")
	public AjaxResult export(SysPsndoc sysPsndoc) {
		List<SysPsndoc> list = sysPsndocService.selectSysPsndocList(sysPsndoc);
		ExcelUtil<SysPsndoc> util = new ExcelUtil<SysPsndoc>(SysPsndoc.class);
		return util.exportExcel(list, "psndoc");
	}

	/**
	 * 获取人员管理详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:psndoc:query')")
	@GetMapping(value = "/{psnId}")
	public AjaxResult getInfo(@PathVariable("psnId") Long psnId) {
		SysPsndoc psn = sysPsndocService.selectSysPsndocById(psnId);
		if(psn != null ) {
			psn.setPsnJobs(sysPsnjobService.selectByParentId(psn.getPsnId()));
		}
		return AjaxResult.success(psn);
	}

	/**
	 * 获取人员工作详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:psndoc:query')")
	@GetMapping(value = "/tabs")
	public AjaxResult getTabs(@RequestParam Long psnId,@RequestParam(required = false) String tab) {
		AjaxResult ajax = AjaxResult.success();
		ajax.put(AjaxResult.DATA_TAG,sysPsnjobService.selectByParentId(psnId));
		return ajax;
	}
	/**
	 * 新增人员管理
	 */
	@PreAuthorize("@ss.hasPermi('system:psndoc:add')")
	@Log(title = "人员管理", businessType = BusinessType.INSERT)
	@PostMapping
	@Lock4j(keys= {"#sysPsndoc.psnCode"},autoPrefix = true)
	public AjaxResult add(@RequestBody SysPsndoc sysPsndoc) {
		return toAjax(sysPsndocService.insertSysPsndoc(sysPsndoc));
	}

	/**
	 * 修改人员管理
	 */
	@PreAuthorize("@ss.hasPermi('system:psndoc:edit')")
	@Log(title = "人员管理", businessType = BusinessType.UPDATE)
	@PutMapping
	@Lock4j(keys= {"#psn.psnId"})
	public AjaxResult edit(@RequestBody SysPsndoc psn) {
		return toAjax(sysPsndocService.updateSysPsndoc(psn));
	}

	/**
	 * 删除人员管理
	 */
	@PreAuthorize("@ss.hasPermi('system:psndoc:remove')")
	@Log(title = "人员管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{psnIds}")
	public AjaxResult remove(@PathVariable Long[] psnIds) {
		return toAjax(sysPsndocService.deleteSysPsndocByIds(psnIds));
	}

}
