package com.czyl.project.system.service.impl;

import com.czyl.common.constant.UserConstants;
import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.StringUtils;
import com.czyl.project.system.domain.SysRole;
import com.czyl.project.system.domain.SysRoleDept;
import com.czyl.project.system.domain.SysRoleMenu;
import com.czyl.project.system.domain.SysRoleOrg;
import com.czyl.project.system.mapper.*;
import com.czyl.project.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色 业务层处理
 * 
 * @author tanghx
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {
	@Autowired
	private SysRoleMapper roleMapper;

	@Autowired
	private SysRoleOrgMapper roleOrgMapper;

	@Autowired
	private SysRoleMenuMapper roleMenuMapper;

	@Autowired
	private SysUserRoleMapper userRoleMapper;

	@Autowired
	private SysRoleDeptMapper roleDeptMapper;

	/**
	 * 根据条件分页查询角色数据
	 * 
	 * @param role 角色信息
	 * @return 角色数据集合信息
	 */
	@Override
	public List<SysRole> selectRoleList(SysRole role) {
		return roleMapper.selectRoleList(role);
	}

	/**
	 * 根据用户ID查询权限
	 * 
	 * @param userId 用户ID
	 * @return 权限列表
	 */
	@Override
	public Set<String> selectRolePermissionByUserId(Long userId) {
		List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
		Set<String> permsSet = new HashSet<>();
		for (SysRole perm : perms) {
			if (StringUtils.isNotNull(perm)) {
				permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
			}
		}
		return permsSet;
	}

	/**
	 * 查询所有角色
	 * 
	 * @return 角色列表
	 */
	@Override
	public List<SysRole> selectRoleAll() {
		return roleMapper.selectRoleAll();
	}

	/**
	 * 根据用户ID获取角色选择框列表
	 * 
	 * @param userId 用户ID
	 * @return 选中角色ID列表
	 */
	@Override
	public List<String> selectRoleListByUserId(Long userId) {
		return roleMapper.selectRoleListByUserId(userId);
	}

	/**
	 * 通过角色ID查询角色
	 * 
	 * @param roleId 角色ID
	 * @return 角色对象信息
	 */
	@Override
	public SysRole selectRoleById(Long roleId) {
		return roleMapper.selectRoleById(roleId);
	}

	/**
	 * 校验角色名称是否唯一
	 * 
	 * @param role 角色信息
	 * @return 结果
	 */
	@Override
	public Integer checkRoleNameUnique(SysRole role) {
		Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
		SysRole info = roleMapper.checkRoleNameUnique(role.getRoleName());
		if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校验角色权限是否唯一
	 * 
	 * @param role 角色信息
	 * @return 结果
	 */
	@Override
	public Integer checkRoleKeyUnique(SysRole role) {
		Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
		SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
		if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校验角色是否允许操作
	 * 
	 * @param role 角色信息
	 */
	@Override
	public void checkRoleAllowed(SysRole role) {
		if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin()) {
			throw new CustomException("不允许操作超级管理员角色");
		}
	}

	/**
	 * 通过角色ID查询角色使用数量
	 * 
	 * @param roleId 角色ID
	 * @return 结果
	 */
	@Override
	public int countUserRoleByRoleId(Long roleId) {
		return userRoleMapper.countUserRoleByRoleId(roleId);
	}

	/**
	 * 新增保存角色信息
	 * 
	 * @param role 角色信息
	 * @return 结果
	 */
	@Override
	@Transactional
	public int insertRole(SysRole role) {
		// 新增角色信息
		roleMapper.insertRole(role);
		insertRoleOrg(role);
		return insertRoleMenu(role);
	}

	/**
	 * 修改保存角色信息
	 * 
	 * @param role 角色信息
	 * @return 结果
	 */
	@Override
	@Transactional
	public int updateRole(SysRole role) {
		// 修改角色信息
		roleMapper.updateRole(role);
		// 删除角色与菜单关联
		roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
		// 删除角色与组织关联
		roleOrgMapper.deleteRoleOrgByRoleId(role.getRoleId());
		insertRoleOrg(role);
		return insertRoleMenu(role);
	}

	/**
	 * 修改角色状态
	 * 
	 * @param role 角色信息
	 * @return 结果
	 */
	@Transactional
	@Override
	public int updateRoleStatus(SysRole role) {
		return roleMapper.updateRole(role);
	}

	/**
	 * 修改数据权限信息
	 * 
	 * @param role 角色信息
	 * @return 结果
	 */
	@Override
	@Transactional
	public int authDataScope(SysRole role) {
		// 修改角色信息
		roleMapper.updateRole(role);
		// 删除角色与部门关联
		roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
		// 新增角色和部门信息（数据权限）
		return insertRoleDept(role);
	}

	/**
	 * 新增角色组织信息
	 * 
	 * @param role 角色对象
	 */
	public int insertRoleOrg(SysRole role) {
		int rows = 1;
		// 新增组织与角色管理
		List<SysRoleOrg> list = new ArrayList<SysRoleOrg>();
		for (Long orgId : role.getOrgIds()) {
			SysRoleOrg ro = new SysRoleOrg();
			ro.setRoleId(role.getRoleId());
			ro.setOrgId(orgId);
			list.add(ro);
		}
		if (list.size() > 0) {
			rows = roleOrgMapper.batchRoleOrg(list);
		}
		return rows;
	}

	/**
	 * 新增角色菜单信息
	 * 
	 * @param role 角色对象
	 */
	public int insertRoleMenu(SysRole role) {
		int rows = 1;
		// 新增用户与角色管理
		List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
		for (Long menuId : role.getMenuIds()) {
			SysRoleMenu rm = new SysRoleMenu();
			rm.setRoleId(role.getRoleId());
			rm.setMenuId(menuId);
			list.add(rm);
		}
		if (list.size() > 0) {
			rows = roleMenuMapper.batchRoleMenu(list);
		}
		return rows;
	}

	/**
	 * 新增角色部门信息(数据权限)
	 *
	 * @param role 角色对象
	 */
	public int insertRoleDept(SysRole role) {
		int rows = 1;
		// 新增角色与部门（数据权限）管理
		List<SysRoleDept> list = new ArrayList<SysRoleDept>();
		for (Long deptId : role.getDeptIds()) {
			SysRoleDept rd = new SysRoleDept();
			rd.setRoleId(role.getRoleId());
			rd.setDeptId(deptId);
			list.add(rd);
		}
		if (list.size() > 0) {
			rows = roleDeptMapper.batchRoleDept(list);
		}
		return rows;
	}

	/**
	 * 通过角色ID删除角色
	 * 
	 * @param roleId 角色ID
	 * @return 结果
	 */
	@Override
	@Transactional
	public int deleteRoleById(Long roleId) {
		checkRoleAllowed(new SysRole(roleId));
		SysRole role = selectRoleById(roleId);
		if(role == null) {
			return 1;
		}
		if (countUserRoleByRoleId(roleId) > 0) {
			throw new CustomException(String.format("%s已分配,不能删除", role.getRoleName()));
		}
		// 删除角色与菜单关联
		roleMenuMapper.deleteRoleMenuByRoleId(roleId);
		// 删除角色与组织关联
		roleOrgMapper.deleteRoleOrgByRoleId(roleId);
		return roleMapper.deleteRoleById(roleId);
	}

	/**
	 * 批量删除角色信息
	 * 
	 * @param roleIds 需要删除的角色ID
	 * @return 结果
	 */
	@Transactional
	@Override
	public int deleteRoleByIds(Long[] roleIds) {
		for (Long roleId : roleIds) {
			checkRoleAllowed(new SysRole(roleId));
			SysRole role = selectRoleById(roleId);
			if(role == null) {
				continue;
			}
			if (countUserRoleByRoleId(roleId) > 0) {
				throw new CustomException(String.format("%s已分配,不能删除", role.getRoleName()));
			}
		}
		for (Long roleId : roleIds) {
			// 删除角色与菜单关联
			roleMenuMapper.deleteRoleMenuByRoleId(roleId);
			// 删除角色与组织关联
			roleOrgMapper.deleteRoleOrgByRoleId(roleId);
		}
		return roleMapper.deleteRoleByIds(roleIds);
	}
}
