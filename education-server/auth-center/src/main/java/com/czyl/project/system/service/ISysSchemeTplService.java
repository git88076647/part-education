package com.czyl.project.system.service;

import com.czyl.project.system.domain.SysSchemeTpl;
import java.util.List;

/**
 * 模版方案Service接口
 * 
 * @author tanghx
 * @date 2021-07-21
 */
public interface ISysSchemeTplService{
    /**
     * 查询模版方案
     * 
     * @param id 模版方案ID
     * @return 模版方案
     */
    public SysSchemeTpl selectById(Long id);

    /**
     * 查询模版方案列表
     * 
     * @param entity 模版方案
     * @return 模版方案集合
     */
    public List<SysSchemeTpl> selectList(SysSchemeTpl entity);
    /**
     * 查询模版方案列表
     *
     * @param entity 模版方案
     * @param fill 是否填充虚字段
     * @return 模版方案集合
     */
    public List<SysSchemeTpl> selectList(SysSchemeTpl entity,boolean fill);

    /**
     * 新增模版方案
     * 
     * @param entity 模版方案
     * @return 结果
     */
    public int insert(SysSchemeTpl entity);

    /**
     * 修改模版方案
     * 
     * @param entity 模版方案
     * @return 结果
     */
    public int update(SysSchemeTpl entity);

    /**
     * 批量删除模版方案
     * 
     * @param ids 需要删除的模版方案ID
     * @return 结果
     */
    public int deleteByIds(Long[] ids);

    /**
     * 删除模版方案信息
     * 
     * @param id 模版方案ID
     * @return 结果
     */
    public int deleteById(Long id);
}
