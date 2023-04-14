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
import com.czyl.framework.aspectj.lang.annotation.Lock4j;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.project.system.domain.SysMenu;
import com.czyl.project.system.service.ISysMenuService;

/**
 * 菜单信息
 * 
 * @author tanghx
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {
	@Autowired
	private ISysMenuService menuService;

	/**
	 * 获取菜单列表
	 */
	@PreAuthorize("@ss.hasPermi('system:menu:list')")
	@GetMapping("/list")
	public AjaxResult list(SysMenu menu) {
		List<SysMenu> menus = menuService.selectMenuList(menu);
		return AjaxResult.success(menuService.buildMenuTree(menus));
	}

	/**
	 * 根据菜单编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:menu:query')")
	@GetMapping(value = "/{menuId}")
	public AjaxResult getInfo(@PathVariable Long menuId) {
		return AjaxResult.success(menuService.selectMenuById(menuId));
	}

	/**
	 * 获取菜单下拉树列表
	 */
	@GetMapping("/treeselect")
	public AjaxResult treeselect(SysMenu dept) {
		List<SysMenu> menus = menuService.selectMenuList(dept);
		return AjaxResult.success(menuService.buildMenuTreeSelect(menus));
	}

	/**
	 * 加载对应角色菜单列表树
	 */
	@GetMapping(value = "/roleMenuTreeselect/{roleId}")
	public AjaxResult roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
		return AjaxResult.success(menuService.selectMenuListByRoleId(roleId));
	}

	/**
	 * 新增菜单
	 */
	@PreAuthorize("@ss.hasPermi('system:menu:add')")
	@Log(title = "菜单管理", businessType = BusinessType.INSERT)
	@PostMapping
	@Lock4j(keys= {"#menu.menuName"},autoPrefix = true)
	public AjaxResult add(@Validated @RequestBody SysMenu menu) {
		if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
			return AjaxResult.error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
		}
		menu.setCreateBy(SecurityUtils.getUserId());
		return toAjax(menuService.insertMenu(menu));
	}

	/**
	 * 修改菜单
	 */
	@PreAuthorize("@ss.hasPermi('system:menu:edit')")
	@Log(title = "菜单管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody SysMenu menu) {
		try {
			LockUtils.lock(menu.getMenuId());
			if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
				return AjaxResult.error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
			}
			menu.setUpdateBy(SecurityUtils.getUserId());
			return toAjax(menuService.updateMenu(menu));
		} finally {
			LockUtils.releaseLock(menu.getMenuId());
		}
	}

	/**
	 * 删除菜单
	 */
	@PreAuthorize("@ss.hasPermi('system:menu:remove')")
	@Log(title = "菜单管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{menuId}")
	public AjaxResult remove(@PathVariable("menuId") Long menuId) {
		if (menuService.hasChildByMenuId(menuId)) {
			return AjaxResult.error("存在子菜单,不允许删除");
		}
		if (menuService.checkMenuExistRole(menuId)) {
			return AjaxResult.error("菜单已分配,不允许删除");
		}
		return toAjax(menuService.deleteMenuById(menuId));
	}
}