package com.czyl.project.system.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.czyl.common.constant.UserConstants;
import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.StringUtils;
import com.czyl.framework.aspectj.lang.annotation.DataScope;
import com.czyl.framework.web.domain.TreeSelect;
import com.czyl.project.system.domain.SysOrg;
import com.czyl.project.system.mapper.SysOrgMapper;
import com.czyl.project.system.service.ISysOrgService;

/**
 * 组织管理 服务实现
 * 
 * @author tanghx
 */
@Service
public class SysOrgServiceImpl implements ISysOrgService {
	@Autowired
	private SysOrgMapper orgMapper;

	/**
	 * 查询组织管理数据
	 * 
	 * @param org 组织信息
	 * @return 组织信息集合
	 */
	@Override
	@DataScope(orgAlias = "org")
	public List<SysOrg> selectOrgList(SysOrg org) {
		return orgMapper.selectOrgList(org);
	}

	/**
	 * 构建前端所需要树结构
	 * 
	 * @param orgs 组织列表
	 * @return 树结构列表
	 */
	@Override
	public List<SysOrg> buildOrgTree(List<SysOrg> orgs) {
		List<SysOrg> returnList = new ArrayList<SysOrg>();
		for (Iterator<SysOrg> iterator = orgs.iterator(); iterator.hasNext();) {
			SysOrg t = (SysOrg) iterator.next();
			// 根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (t.getParentId() == null || t.getParentId() == 0) {
				recursionFn(orgs, t);
				returnList.add(t);
			}
		}
		if (returnList.isEmpty()) {
			returnList = orgs;
		}
		return returnList;
	}

	/**
	 * 构建前端所需要下拉树结构
	 * 
	 * @param orgs 组织列表
	 * @return 下拉树结构列表
	 */
	@Override
	public List<TreeSelect> buildOrgTreeSelect(List<SysOrg> orgs) {
		List<SysOrg> orgTrees = buildOrgTree(orgs);
		return orgTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
	}

	/**
	 * 根据角色ID查询组织树信息
	 * 
	 * @param roleId 角色ID
	 * @return 选中组织列表
	 */
	@Override
	public List<String> selectOrgListByRoleId(Long roleId) {
		return orgMapper.selectOrgListByRoleId(roleId);
	}

	/**
	 * 根据组织ID查询信息
	 * 
	 * @param orgId 组织ID
	 * @return 组织信息
	 */
	@Override
	public SysOrg selectOrgById(Long orgId) {
		return orgMapper.selectOrgById(orgId);
	}

	/**
	 * 是否存在子节点
	 * 
	 * @param orgId 组织ID
	 * @return 结果
	 */
	@Override
	public boolean hasChildByOrgId(Long orgId) {
		int result = orgMapper.hasChildByOrgId(orgId);
		return result > 0 ? true : false;
	}

	/**
	 * 查询组织是否存在用户
	 * 
	 * @param orgId 组织ID
	 * @return 结果 true 存在 false 不存在
	 */
	@Override
	public boolean checkOrgExistUser(Long orgId) {
		int result = orgMapper.checkOrgExistUser(orgId);
		return result > 0 ? true : false;
	}

	/**
	 * 校验组织名称是否唯一
	 * 
	 * @param org 组织信息
	 * @return 结果
	 */
	@Override
	public Integer checkOrgNameUnique(SysOrg org) {
		Long orgId = StringUtils.isNull(org.getOrgId()) ? -1L : org.getOrgId();
		SysOrg info = orgMapper.checkOrgNameUnique(org.getOrgName());
		if (StringUtils.isNotNull(info) && info.getOrgId().longValue() != orgId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校验组织名称是否唯一
	 * 
	 * @param org 组织信息
	 * @return 结果
	 */
	@Override
	public Integer checkOrgCodeUnique(SysOrg org) {
		Long orgId = StringUtils.isNull(org.getOrgId()) ? -1L : org.getOrgId();
		SysOrg info = orgMapper.checkOrgCodeUnique(org.getOrgCode());
		if (StringUtils.isNotNull(info) && info.getOrgId().longValue() != orgId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 新增保存组织信息
	 * 
	 * @param org 组织信息
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertOrg(SysOrg org) {
		SysOrg parent = orgMapper.selectOrgById(org.getParentId());
		// 如果父节点不为正常状态,则不允许新增子节点
		if (parent != null && !UserConstants.DICT_NORMAL.equals(parent.getStatus())) {
			throw new CustomException("组织停用，不允许新增");
		}

		return orgMapper.insertOrg(org);
	}

	/**
	 * 修改保存组织信息
	 * 
	 * @param org 组织信息
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateOrg(SysOrg org) {
		int result = orgMapper.updateOrg(org);
		if (UserConstants.DICT_NORMAL.equals(org.getStatus()) && org.getParentId() != null && org.getParentId() > 0) {
			// 如果该组织是启用状态，则启用该组织的所有上级组织
			updateParentOrgStatus(org);
		}
		return result;
	}

	/**
	 * 修改该组织的父级组织状态
	 * 
	 * @param org 当前组织
	 */
	private void updateParentOrgStatus(SysOrg org) {
		Long updateBy = org.getUpdateBy();
		org = orgMapper.selectOrgById(org.getOrgId());
		org.setUpdateBy(updateBy);
		orgMapper.updateOrgStatus(org);
	}

	/**
	 * 删除组织管理信息
	 * 
	 * @param orgId 组织ID
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteOrgById(Long orgId) {
		return orgMapper.deleteOrgById(orgId);
	}

	/**
	 * 递归列表
	 */
	private void recursionFn(List<SysOrg> list, SysOrg t) {
		// 得到子节点列表
		List<SysOrg> childList = getChildList(list, t);
		t.setChildren(childList);
		for (SysOrg tChild : childList) {
			if (hasChild(list, tChild)) {
				// 判断是否有子节点
				Iterator<SysOrg> it = childList.iterator();
				while (it.hasNext()) {
					SysOrg n = (SysOrg) it.next();
					recursionFn(list, n);
				}
			}
		}
	}

	/**
	 * 得到子节点列表
	 */
	private List<SysOrg> getChildList(List<SysOrg> list, SysOrg t) {
		List<SysOrg> tlist = new ArrayList<SysOrg>();
		Iterator<SysOrg> it = list.iterator();
		while (it.hasNext()) {
			SysOrg n = (SysOrg) it.next();
			if (n.getParentId() != null && n.getParentId().longValue() == t.getOrgId().longValue()) {
				tlist.add(n);
			}
		}
		return tlist;
	}

	/**
	 * 判断是否有子节点
	 */
	private boolean hasChild(List<SysOrg> list, SysOrg t) {
		return getChildList(list, t).size() > 0 ? true : false;
	}
}
