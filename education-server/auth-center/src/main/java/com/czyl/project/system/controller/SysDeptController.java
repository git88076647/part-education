package com.czyl.project.system.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.DataScope;
import com.czyl.framework.aspectj.lang.annotation.DataSource;
import com.czyl.framework.aspectj.lang.annotation.Lock4j;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.project.system.domain.SysDept;
import com.czyl.project.system.service.ISysDeptService;

/**
 * 部门信息
 * 
 * @author tanghx
 */
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {
	@Autowired
	private ISysDeptService deptService;

	/**
	 * 获取部门列表
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:list')")
	@GetMapping("/list")
	@DataSource("slave")
	@DataScope(orgAlias = "d")
	public AjaxResult list(SysDept dept) {
		List<SysDept> depts = deptService.selectDeptList(dept);
		return AjaxResult.success(deptService.buildDeptTree(depts));
	}

	/**
	 * 根据部门编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:query')")
	@GetMapping(value = "/{deptId}")
	@DataSource("slave")
	public AjaxResult getInfo(@PathVariable Long deptId) {
		return AjaxResult.success(deptService.selectDeptById(deptId));
	}

	/**
	 * 获取部门下拉树列表
	 */
	@GetMapping("/treeselect")
	@DataSource("slave")
	public AjaxResult treeselect(SysDept dept) {
		List<SysDept> depts = deptService.selectDeptList(dept);
		return AjaxResult.success(deptService.buildDeptTreeSelect(depts));
	}

	/**
	 * 加载对应角色部门列表树
	 */
	@GetMapping(value = "/roleDeptTreeselect/{roleId}")
	@DataSource("slave")
	public AjaxResult roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
		return AjaxResult.success(deptService.selectDeptListByRoleId(roleId));
	}

	/**
	 * 新增部门
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:add')")
	@Log(title = "部门管理", businessType = BusinessType.INSERT)
	@PostMapping
	@Lock4j(keys= {"#dept.orgId","#dept.deptName"},autoPrefix = true)
	public AjaxResult add(@Validated @RequestBody SysDept dept) {
		if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
			return AjaxResult.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
		}
		dept.setCreateBy(SecurityUtils.getUserId());
		return toAjax(deptService.insertDept(dept));
	}

	/**
	 * 修改部门
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:edit')")
	@Log(title = "部门管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody SysDept dept) {
		try {
			LockUtils.lock(dept.getDeptId());
			if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
				return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
			} else if (dept.getParentId() != null && dept.getParentId().equals(dept.getDeptId())) {
				return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
			}
			dept.setUpdateBy(SecurityUtils.getUserId());
			return toAjax(deptService.updateDept(dept));
		} finally {
			LockUtils.releaseLock(dept.getDeptId());
		}
	}

	/**
	 * 删除部门
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:remove')")
	@Log(title = "部门管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{deptId}")
	public AjaxResult remove(@PathVariable Long deptId) {
		if (deptService.hasChildByDeptId(deptId)) {
			return AjaxResult.error("存在下级部门,不允许删除");
		}
		if (deptService.checkDeptExistUser(deptId)) {
			return AjaxResult.error("部门存在用户,不允许删除");
		}
		return toAjax(deptService.deleteDeptById(deptId));
	}
}
