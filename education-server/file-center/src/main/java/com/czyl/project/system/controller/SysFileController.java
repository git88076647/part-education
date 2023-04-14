package com.czyl.project.system.controller;

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
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.project.system.domain.SysFile;
import com.czyl.project.system.service.ISysFileService;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.common.utils.poi.ExcelUtil;
import com.czyl.framework.web.page.TableDataInfo;

/**
 * 文件管理Controller
 * 
 * @author tanghx
 * @date 2020-01-15
 */
@RestController
@RequestMapping("/system/file")
public class SysFileController extends BaseController {
	@Autowired
	private ISysFileService sysFileService;

	/**
	 * 查询文件管理列表
	 */
	@PreAuthorize("@ss.hasPermi('system:file:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysFile sysFile) {
		startPage();
		List<SysFile> list = sysFileService.selectSysFileList(sysFile);
		return getDataTable(list);
	}

	/**
	 * 导出文件管理列表
	 */
	@PreAuthorize("@ss.hasPermi('system:file:export')")
	@Log(title = "文件管理", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public AjaxResult export(SysFile sysFile) {
		List<SysFile> list = sysFileService.selectSysFileList(sysFile);
		ExcelUtil<SysFile> util = new ExcelUtil<SysFile>(SysFile.class);
		return util.exportExcel(list, "file");
	}

	/**
	 * 获取文件管理详情信息
	 */
	@PreAuthorize("@ss.hasPermi('system:file:query')")
	@GetMapping(value = "/{fileId}")
	public AjaxResult getInfo(@PathVariable("fileId") Long fileId) {
		return AjaxResult.success(sysFileService.selectSysFileById(fileId));
	}

	/**
	 * 新增文件管理
	 */
	@PreAuthorize("@ss.hasPermi('system:file:add')")
	@Log(title = "文件管理", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody SysFile sysFile) {
		return toAjax(sysFileService.insertSysFile(sysFile));
	}

	/**
	 * 修改文件管理
	 */
	@PreAuthorize("@ss.hasPermi('system:file:edit')")
	@Log(title = "文件管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody SysFile sysFile) {
		return toAjax(sysFileService.updateSysFile(sysFile));
	}

	/**
	 * 删除文件管理
	 */
	@PreAuthorize("@ss.hasPermi('system:file:remove')")
	@Log(title = "文件管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fileIds}")
	public AjaxResult remove(@PathVariable Long[] fileIds) {
		return toAjax(sysFileService.deleteSysFileByIds(fileIds));
	}
}
