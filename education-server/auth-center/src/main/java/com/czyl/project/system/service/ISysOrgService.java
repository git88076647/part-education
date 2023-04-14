package com.czyl.project.system.service;

import java.util.List;
import com.czyl.framework.web.domain.TreeSelect;
import com.czyl.project.system.domain.SysOrg;

/**
 * 组织管理 服务层
 * 
 * @author tanghx
 */
public interface ISysOrgService
{
    /**
     * 查询组织管理数据
     * 
     * @param org 组织信息
     * @return 组织信息集合
     */
    public List<SysOrg> selectOrgList(SysOrg org);

    /**
     * 构建前端所需要树结构
     * 
     * @param orgs 组织列表
     * @return 树结构列表
     */
    public List<SysOrg> buildOrgTree(List<SysOrg> orgs);

    /**
     * 构建前端所需要下拉树结构
     * 
     * @param orgs 组织列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildOrgTreeSelect(List<SysOrg> orgs);

    /**
     * 根据角色ID查询组织树信息
     * 
     * @param roleId 角色ID
     * @return 选中组织列表
     */
    public List<String> selectOrgListByRoleId(Long roleId);

    /**
     * 根据组织ID查询信息
     * 
     * @param orgId 组织ID
     * @return 组织信息
     */
    public SysOrg selectOrgById(Long orgId);

    /**
     * 是否存在组织子节点
     * 
     * @param orgId 组织ID
     * @return 结果
     */
    public boolean hasChildByOrgId(Long orgId);

    /**
     * 查询组织是否存在用户
     * 
     * @param orgId 组织ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkOrgExistUser(Long orgId);

    /**
     * 校验组织名称是否唯一
     * 
     * @param org 组织信息
     * @return 结果
     */
    public Integer checkOrgNameUnique(SysOrg org);
    
    /**
     * 校验组织编码是否唯一
     * 
     * @param org 组织信息
     * @return 结果
     */
    public Integer checkOrgCodeUnique(SysOrg org);

    /**
     * 新增保存组织信息
     * 
     * @param org 组织信息
     * @return 结果
     */
    public int insertOrg(SysOrg org);

    /**
     * 修改保存组织信息
     * 
     * @param org 组织信息
     * @return 结果
     */
    public int updateOrg(SysOrg org);

    /**
     * 删除组织管理信息
     * 
     * @param orgId 组织ID
     * @return 结果
     */
    public int deleteOrgById(Long orgId);
}
