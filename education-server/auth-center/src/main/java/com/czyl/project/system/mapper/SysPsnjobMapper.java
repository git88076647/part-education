package com.czyl.project.system.mapper;

import java.util.List;

import com.czyl.project.system.domain.SysPsnjob;

/**
 * 人员任职信息Mapper接口
 * 
 * @author tanghx
 * @date 2020-04-26
 */
public interface SysPsnjobMapper{
    /**
     * 查询人员任职信息
     * 
     * @param psnjobId 人员任职信息ID
     * @return 人员任职信息
     */
    public SysPsnjob selectById(Long psnjobId);

    /**
     * 查询人员任职信息列表
     * 
     * @param sysPsnjob 人员任职信息
     * @return 人员任职信息集合
     */
    public List<SysPsnjob> selectList(SysPsnjob sysPsnjob);

    /**
     * 根据主表ID查询子表数据
     * @param parentId
     * @return
     */
    public List<SysPsnjob> selectByParentId(Long parentId);
    
    /**
     * 新增人员任职信息
     * 
     * @param sysPsnjob 人员任职信息
     * @return 结果
     */
    public int insert(SysPsnjob sysPsnjob);
    /**
     * 新增人员任职信息
     * 
     * @param sysPsnjob 人员任职信息
     * @return 结果
     */
    public int insertBatch(List<SysPsnjob> list);

    /**
     * 修改人员任职信息
     * 
     * @param sysPsnjob 人员任职信息
     * @return 结果
     */
    public int update(SysPsnjob sysPsnjob);
    
    /**
     * 修改人员任职信息
     * 
     * @param list 人员任职信息
     * @return 结果
     */
    public int updateBatch(List<SysPsnjob> list);

    /**
     * 删除人员任职信息
     * 
     * @param psnjobId 人员任职信息ID
     * @return 结果
     */
    public int deleteById(Long psnjobId);

    /**
     * 批量删除人员任职信息
     * 
     * @param psnjobIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteByIds(Long[] psnjobIds);
    /**
     * 删除人员任职信息
     * 
     * @param parentId 人员ID
     * @return 结果
     */
    public int deleteByparentId(Long parentId);
    
    /**
     * 批量删除人员任职信息
     * 
     * @param parentIds 人员ID
     * @return 结果
     */
    public int deleteByparentIds(Long[] parentIds);
}
