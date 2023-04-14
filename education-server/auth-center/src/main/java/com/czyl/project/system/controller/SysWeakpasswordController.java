package com.czyl.project.system.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.czyl.common.utils.AppContextUtils;
import com.czyl.common.utils.LockUtils;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.utils.poi.ExcelUtil;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.Lock4j;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.system.domain.SysWeakpassword;
import com.czyl.project.system.service.ISysWeakpasswordService;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

/**
 * 弱口令管理Controller
 * 
 * @author tanghx
 * @date 2020-04-08
 */
@RestController
@RequestMapping("/system/weakpassword")
@Slf4j
public class SysWeakpasswordController extends BaseController {
	@Autowired
	private ISysWeakpasswordService sysWeakpasswordService;

	/**
	 * 查询弱口令管理列表
	 */
	@PreAuthorize("@ss.hasPermi('system:weakpassword:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysWeakpassword sysWeakpassword) {
		startPage();
		List<SysWeakpassword> list = sysWeakpasswordService.selectSysWeakpasswordList(sysWeakpassword);
		return getDataTable(list);
	}

	/**
	 * 导出弱口令管理列表
	 */
	@PreAuthorize("@ss.hasPermi('system:weakpassword:export')")
	@Log(title = "弱口令管理", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public AjaxResult export(SysWeakpassword sysWeakpassword) {
		List<SysWeakpassword> list = sysWeakpasswordService.selectSysWeakpasswordList(sysWeakpassword);
		ExcelUtil<SysWeakpassword> util = new ExcelUtil<SysWeakpassword>(SysWeakpassword.class);
		return util.exportExcel(list, "weakpassword");
	}

	/**
	 * 获取弱口令管理详情信息
	 */
	@PreAuthorize("@ss.hasPermi('system:weakpassword:query')")
	@GetMapping(value = "/{pwdId}")
	public AjaxResult getInfo(@PathVariable("pwdId") Long pwdId) {
		return AjaxResult.success(sysWeakpasswordService.selectSysWeakpasswordById(pwdId));
	}

	/**
	 * 新增弱口令管理
	 */
	@PreAuthorize("@ss.hasPermi('system:weakpassword:add')")
	@Log(title = "弱口令管理", businessType = BusinessType.INSERT)
	@PostMapping
	@Lock4j(keys = { "#sysWeakpassword.password" })
	public AjaxResult add(@RequestBody SysWeakpassword sysWeakpassword) {
		return toAjax(sysWeakpasswordService.insertSysWeakpassword(sysWeakpassword));
	}

	/**
	 * 修改弱口令管理
	 */
	@PreAuthorize("@ss.hasPermi('system:weakpassword:edit')")
	@Log(title = "弱口令管理", businessType = BusinessType.UPDATE)
	@PutMapping
	@Lock4j(keys = { "#sysWeakpassword.pwdId" })
	public AjaxResult edit(@RequestBody SysWeakpassword sysWeakpassword) {
		return toAjax(sysWeakpasswordService.updateSysWeakpassword(sysWeakpassword));
	}

	/**
	 * 删除弱口令管理
	 */
	@PreAuthorize("@ss.hasPermi('system:weakpassword:remove')")
	@Log(title = "弱口令管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{pwdIds}")
	public AjaxResult remove(@PathVariable Long[] pwdIds) {
		return toAjax(sysWeakpasswordService.deleteSysWeakpasswordByIds(pwdIds));
	}

	@Log(title = "弱口令管理", businessType = BusinessType.IMPORT)
	@PreAuthorize("@ss.hasPermi('system:weakpassword:import')")
	@PostMapping("/importData")
	public AjaxResult importData(MultipartFile file) throws Exception {
		if (file == null) {
			AjaxResult.error("文件不能为空");
		}
		String lockKey = String.format("importWeakpassword:%s", AppContextUtils.getUserId());
		LockUtils.me().tryLock(lockKey, "之前的导入操作还在继续,耗时较长,请耐心等待!");
		try {
			@Cleanup
			BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
			String lineTxt = null;
			List<String> list = new LinkedList<String>();
			int index = 0;
			int counter = 0;
			while ((lineTxt = br.readLine()) != null) {
				if (StringUtils.isNotBlank(lineTxt) && !list.contains(lineTxt.trim())) {
					list.add(lineTxt.trim());
					index ++;
					counter ++;
					if (index > 9999) {
						log.info("读取到10000条数据开始执行一次异步导入");
						sysWeakpasswordService.importData(list, AppContextUtils.getUserId());
						list = new LinkedList<String>();
						index = 0;
					}
				}
			}
			if(list.size() > 0){
				sysWeakpasswordService.importData(list, AppContextUtils.getUserId());
			}
			return AjaxResult.success(String.format("正在后台异步导入[ %d ]条数据耗时很长，请耐心等待！", counter));
		} catch (Exception e) {
			log.error("导入异常", e);
			throw e;
		} finally {
			LockUtils.releaseLock(lockKey);
		}
	}
}
