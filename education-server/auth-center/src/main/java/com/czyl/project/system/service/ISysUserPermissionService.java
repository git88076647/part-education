package com.czyl.project.system.service;

import com.czyl.project.system.domain.SysUserPermission;
import java.util.List;

/**
 * 角色数据权限Service接口
 * 
 * @author fangxioaoh
 * @date 2021-12-06
 */
public interface ISysUserPermissionService {
    /**
     * 查询角色数据权限
     * 
     * @param id 角色数据权限ID
     * @return 角色数据权限
     */
    public SysUserPermission selectById(Long id);

    /**
     * 查询角色数据权限列表
     * 
     * @param entity 角色数据权限
     * @return 角色数据权限集合
     */
    public List<SysUserPermission> selectList(SysUserPermission entity);
    /**
     * 查询角色数据权限列表
     *
     * @param entity 角色数据权限
     * @param fill 是否填充虚字段
     * @return 角色数据权限集合
     */
    public List<SysUserPermission> selectList(SysUserPermission entity, boolean fill);

    /**
     * 新增角色数据权限
     * 
     * @param entity 角色数据权限
     * @return 结果
     */
    public int importData(SysUserPermission entity);
    public int insert(SysUserPermission entity);

    /**
     * 修改角色数据权限
     * 
     * @param entity 角色数据权限
     * @return 结果
     */
    public int update(SysUserPermission entity);

    /**
     * 批量删除角色数据权限
     * 
     * @param ids 需要删除的角色数据权限ID
     * @return 结果
     */
    public int deleteByIds(List<Long> ids);

    /**
     * 删除角色数据权限信息
     * 
     * @param id 角色数据权限ID
     * @return 结果
     */
    public int deleteById(Long id);
}
