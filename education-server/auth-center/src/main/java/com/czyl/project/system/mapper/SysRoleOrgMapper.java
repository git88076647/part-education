package com.czyl.project.system.mapper;

import java.util.List;

import com.czyl.project.system.domain.SysRoleOrg;

/**
 * 角色与组织关联表 数据层
 * 
 * @author tanghx
 */
public interface SysRoleOrgMapper
{
    /**
     * 查询组织使用数量
     * 
     * @param orgId 组织ID
     * @return 结果
     */
    public int checkOrgExistRole(Long orgId);

    /**
     * 通过角色ID删除角色和组织关联
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleOrgByRoleId(Long roleId);

    /**
     * 批量新增角色组织信息
     * 
     * @param roleOrgList 角色组织列表
     * @return 结果
     */
    public int batchRoleOrg(List<SysRoleOrg> roleOrgList);
}
