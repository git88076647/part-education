package com.czyl.project.system.mapper;

import com.czyl.project.system.domain.SysUserPermission;
import java.util.List;

/**
 * 角色数据权限Mapper接口
 * 
 * @author fangxioaoh
 * @date 2021-12-06
 */
public interface SysUserPermissionMapper {
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
     * 新增角色数据权限
     * 
     * @param entity 角色数据权限
     * @return 结果
     */
    public int insert(SysUserPermission entity);
    
    /**
     * 新增角色数据权限
     * 
     * @param list 角色数据权限
     * @return 结果
     */
    public int insertBatch(List<SysUserPermission> list);

    /**
     * 修改角色数据权限
     * 
     * @param entity 角色数据权限
     * @return 结果
     */
    public int update(SysUserPermission entity);
    
    /**
     * 修改角色数据权限
     * 
     * @param list 角色数据权限
     * @return 结果
     */
    public int updateBatch(List<SysUserPermission> list);

    /**
     * 删除角色数据权限
     * 
     * @param id 角色数据权限ID
     * @return 结果
     */
    public int deleteById(Long id);

    /**
     * 批量删除角色数据权限
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByIds(List<Long> ids);

    SysUserPermission checkout(SysUserPermission entity);

    List<SysUserPermission> selectByIds(List<Long> ids);
}
