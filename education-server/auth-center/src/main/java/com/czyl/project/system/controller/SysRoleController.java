package com.czyl.project.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import com.czyl.common.utils.StringUtils;
import com.czyl.framework.aspectj.lang.annotation.DataSource;
import com.github.pagehelper.PageInfo;
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
import com.czyl.framework.aspectj.lang.annotation.Lock4j;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.system.domain.SysRole;
import com.czyl.project.system.service.ISysRoleService;

/**
 * 角色信息
 * 
 * @author tanghx
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {
	@Autowired
	private ISysRoleService roleService;

	@PreAuthorize("@ss.hasPermi('system:role:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysRole role) {
		startPage();
		List<SysRole> list = roleService.selectRoleList(role);
		return getDataTable(list);
	}


	@GetMapping("/ref")
	@DataSource("slave")
	public TableDataInfo ref(SysRole role){
		startPage();
		List<SysRole> list = roleService.selectRoleAll();
		if(list != null && !list.isEmpty()) {
			List<Map> rows = list.stream().map(d -> {
				return MapUtil.builder("dictLabel",d.getRoleName()).put("dictValue", d.getRoleId().toString()).put("status", StringUtils.trim(d.getStatus())).build();
			}).collect(Collectors.toList());
			Map<String, String> map = new HashMap<>();
			map.put("dictLabel","-- < 空 > --");
			map.put("dictValue","0");
			map.put("status","0");
			rows.add(0,map);
			TableDataInfo dataTable = getDataTable(rows);
			dataTable.setTotal(new PageInfo(list).getTotal());
			return dataTable;
		}
		return getDataTable(ListUtil.empty());
	}




	@Log(title = "角色管理", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('system:role:export')")
	@GetMapping("/export")
	public AjaxResult export(SysRole role) {
		List<SysRole> list = roleService.selectRoleList(role);
		ExcelUtil<SysRole> util = new ExcelUtil<SysRole>(SysRole.class);
		return util.exportExcel(list, "角色数据");
	}

	/**
	 * 根据角色编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:role:query')")
	@GetMapping(value = "/{roleId}")
	public AjaxResult getInfo(@PathVariable Long roleId) {
		return AjaxResult.success(roleService.selectRoleById(roleId));
	}

	/**
	 * 新增角色
	 */
	@PreAuthorize("@ss.hasPermi('system:role:add')")
	@Log(title = "角色管理", businessType = BusinessType.INSERT)
	@PostMapping
	@Lock4j(keys= {"#role.roleName"},autoPrefix = true)
	public AjaxResult add(@Validated @RequestBody SysRole role) {
		if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
			return AjaxResult.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
		} else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
			return AjaxResult.error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
		}
		role.setCreateBy(SecurityUtils.getUserId());
		return toAjax(roleService.insertRole(role));

	}

	/**
	 * 修改保存角色
	 */
	@PreAuthorize("@ss.hasPermi('system:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.UPDATE)
	@PutMapping
	@Lock4j(keys = { "#role.roleId" })
	public AjaxResult edit(@Validated @RequestBody SysRole role) {
		roleService.checkRoleAllowed(role);
		if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
			return AjaxResult.error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
		} else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
			return AjaxResult.error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
		}
		role.setUpdateBy(SecurityUtils.getUserId());
		return toAjax(roleService.updateRole(role));
	}

	/**
	 * 修改保存数据权限
	 */
	@PreAuthorize("@ss.hasPermi('system:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.UPDATE)
	@PutMapping("/dataScope")
	public AjaxResult dataScope(@RequestBody SysRole role) {
		try {
			LockUtils.lock(role.getRoleId());
			roleService.checkRoleAllowed(role);
			return toAjax(roleService.authDataScope(role));
		} finally {
			LockUtils.releaseLock(role.getRoleId());
		}
	}

	/**
	 * 状态修改
	 */
	@PreAuthorize("@ss.hasPermi('system:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	public AjaxResult changeStatus(@RequestBody SysRole role) {
		try {
			LockUtils.lock(role.getRoleId());
			roleService.checkRoleAllowed(role);
			role.setUpdateBy(SecurityUtils.getUserId());
			if (roleService.updateRoleStatus(role) > 0) {
				role = roleService.selectRoleById(role.getRoleId());
				return AjaxResult.success(role);
			} else {
				return AjaxResult.error();
			}
		} finally {
			LockUtils.releaseLock(role.getRoleId());
		}
	}

	/**
	 * 删除角色
	 */
	@PreAuthorize("@ss.hasPermi('system:role:remove')")
	@Log(title = "角色管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{roleIds}")
	public AjaxResult remove(@PathVariable Long[] roleIds) {
		return toAjax(roleService.deleteRoleByIds(roleIds));
	}

	/**
	 * 获取角色选择框列表
	 */
	@PreAuthorize("@ss.hasPermi('system:role:query')")
	@GetMapping("/optionselect")
	public AjaxResult optionselect() {
		return AjaxResult.success(roleService.selectRoleAll());
	}
}