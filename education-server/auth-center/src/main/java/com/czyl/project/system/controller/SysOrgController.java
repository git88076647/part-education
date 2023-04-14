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
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.DataScope;
import com.czyl.framework.aspectj.lang.annotation.Lock4j;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.project.system.domain.SysOrg;
import com.czyl.project.system.service.ISysOrgService;

/**
 * 组织信息
 * 
 * @author tanghx
 */
@RestController
@RequestMapping("/system/org")
public class SysOrgController extends BaseController {
	@Autowired
	private ISysOrgService orgService;

	/**
	 * 获取组织列表
	 */
	@PreAuthorize("@ss.hasPermi('system:org:list')")
	@GetMapping("/list")
	public AjaxResult list(SysOrg org) {
		org.setDataScope(null);
		List<SysOrg> orgs = orgService.selectOrgList(org);
		return AjaxResult.success(orgService.buildOrgTree(orgs));
	}

	/**
	 * 根据组织编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:org:query')")
	@GetMapping(value = "/{orgId}")
	public AjaxResult getInfo(@PathVariable Long orgId) {
		return AjaxResult.success(orgService.selectOrgById(orgId));
	}

	/**
	 * 获取组织下拉树列表 任何人都会参照组织，所以不需要授权,只需要登录即可
	 */
	@GetMapping("/treeselect")
	@DataScope(orgAlias = "org")
	public AjaxResult treeselect(SysOrg org) {
		List<SysOrg> orgs = orgService.selectOrgList(org);
		return AjaxResult.success(orgService.buildOrgTreeSelect(orgs));
	}

	/**
	 * 加载对应角色组织列表树
	 */
	@PreAuthorize("@ss.hasPermi('system:org:query')")
	@GetMapping(value = "/roleOrgTreeselect/{roleId}")
	public AjaxResult roleOrgTreeselect(@PathVariable("roleId") Long roleId) {
		return AjaxResult.success(orgService.selectOrgListByRoleId(roleId));
	}

	/**
	 * 新增组织
	 */
	@PreAuthorize("@ss.hasPermi('system:org:add')")
	@Log(title = "组织管理", businessType = BusinessType.INSERT)
	@PostMapping
	@Lock4j(keys= {"#org.orgCode"},autoPrefix = true)
	public AjaxResult add(@Validated @RequestBody SysOrg org) {
		if (UserConstants.NOT_UNIQUE.equals(orgService.checkOrgNameUnique(org))) {
			return AjaxResult.error("新增组织'" + org.getOrgName() + "'失败，组织名称已存在");
		}
		if (UserConstants.NOT_UNIQUE.equals(orgService.checkOrgCodeUnique(org))) {
			return AjaxResult.error("新增组织'" + org.getOrgName() + "'失败，组织编码已存在");
		}
		org.setCreateBy(SecurityUtils.getUserId());
		return toAjax(orgService.insertOrg(org));
	}

	/**
	 * 修改组织
	 */
	@PreAuthorize("@ss.hasPermi('system:org:edit')")
	@Log(title = "组织管理", businessType = BusinessType.UPDATE)
	@PutMapping
	@Lock4j(keys = { "#org.orgId" })
	public AjaxResult edit(@Validated @RequestBody SysOrg org) {
		if (UserConstants.NOT_UNIQUE.equals(orgService.checkOrgNameUnique(org))) {
			return AjaxResult.error("修改组织'" + org.getOrgName() + "'失败，组织名称已存在");
		} else if (org.getParentId() != null && org.getParentId().equals(org.getOrgId())) {
			return AjaxResult.error("修改组织'" + org.getOrgName() + "'失败，上级组织不能是自己");
		}
		org.setUpdateBy(SecurityUtils.getUserId());
		return toAjax(orgService.updateOrg(org));
	}

	/**
	 * 删除组织
	 */
	@PreAuthorize("@ss.hasPermi('system:org:remove')")
	@Log(title = "组织管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orgId}")
	public AjaxResult remove(@PathVariable Long orgId) {
		if (orgService.hasChildByOrgId(orgId)) {
			return AjaxResult.error("存在下级组织,不允许删除");
		}
		if (orgService.checkOrgExistUser(orgId)) {
			return AjaxResult.error("组织存在用户,不允许删除");
		}
		return toAjax(orgService.deleteOrgById(orgId));
	}
}
