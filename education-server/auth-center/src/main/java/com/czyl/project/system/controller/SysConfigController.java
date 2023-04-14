package com.czyl.project.system.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.czyl.common.constant.UserConstants;
import com.czyl.common.utils.LockUtils;
import com.czyl.common.utils.SecurityUtils;
import com.czyl.common.utils.poi.ExcelUtil;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.DataSource;
import com.czyl.framework.aspectj.lang.annotation.Lock4j;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.system.domain.SysConfig;
import com.czyl.project.system.service.ISysConfigService;

/**
 * 参数配置 信息操作处理
 * 
 * @author tanghx
 */
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {
	@Autowired
	private ISysConfigService configService;

	/**
	 * 获取参数配置列表
	 */
	@PreAuthorize("@ss.hasPermi('system:config:list')")
	@GetMapping("/list")
	@DataSource("slave")
	public TableDataInfo list(SysConfig config) {
		startPage();
		List<SysConfig> list = configService.selectConfigList(config);
		return getDataTable(list);
	}

	@Log(title = "参数管理", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('system:config:export')")
	@GetMapping("/export")
	@DataSource("slave")
	public AjaxResult export(SysConfig config) {
		List<SysConfig> list = configService.selectConfigList(config);
		ExcelUtil<SysConfig> util = new ExcelUtil<SysConfig>(SysConfig.class);
		return util.exportExcel(list, "参数数据");
	}

	/**
	 * 根据参数编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:config:query')")
	@GetMapping(value = "/{configId}")
	@DataSource("slave")
	public AjaxResult getInfo(@PathVariable Long configId) {
		return AjaxResult.success(configService.selectConfigById(configId));
	}

	/**
	 * 根据参数键名查询参数值
	 */
	@GetMapping(value = "/configKey/{configKey}")
	@DataSource("slave")
	public AjaxResult getConfigKey(@PathVariable String configKey) {
		return AjaxResult.success().setData(configService.selectConfigByKey(configKey));
	}

	/**
	 * 新增参数配置
	 */
	@PreAuthorize("@ss.hasPermi('system:config:add')")
	@Log(title = "参数管理", businessType = BusinessType.INSERT)
	@PostMapping
	@Lock4j(keys= {"#config.configKey"},autoPrefix = true)
	public AjaxResult add(@Validated @RequestBody SysConfig config) {
		if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
			return AjaxResult.error("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
		}
		config.setCreateBy(SecurityUtils.getUserId());
		return toAjax(configService.insertConfig(config));
	}

	/**
	 * 修改参数配置
	 */
	@PreAuthorize("@ss.hasPermi('system:config:edit')")
	@Log(title = "参数管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody SysConfig config) {
		try {
			LockUtils.lock(config.getConfigId());
			if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
				return AjaxResult.error("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
			}
			config.setUpdateBy(SecurityUtils.getUserId());
			return toAjax(configService.updateConfig(config));
		} finally {
			LockUtils.releaseLock(config.getConfigId());
		}
	}

	/**
	 * 删除参数配置
	 */
	@PreAuthorize("@ss.hasPermi('system:config:remove')")
	@Log(title = "参数管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{configIds}")
	public AjaxResult remove(@PathVariable Long[] configIds) {
		return toAjax(configService.deleteConfigByIds(configIds));
	}
}
