package com.czyl.project.system.service;

import com.czyl.project.system.domain.SysPsnjob;
import java.util.List;

/**
 * 人员任职信息Service接口
 * 
 * @author tanghx
 * @date 2020-04-26
 */
public interface ISysPsnjobService{
    /**
     * 查询人员任职信息
     * 
     * @param psnjobId 人员任职信息ID
     * @return 人员任职信息
     */
    public SysPsnjob selectById(Long psnjobId);

    /**
     * 根据主表ID查询子表数据
     * @param parentId
     * @return
     */
    public List<SysPsnjob> selectByParentId(Long parentId);
    
    /**
     * 查询人员任职信息列表
     * 
     * @param sysPsnjob 人员任职信息
     * @return 人员任职信息集合
     */
    public List<SysPsnjob> selectList(SysPsnjob sysPsnjob);

    /**
     * 新增人员任职信息
     * 
     * @param sysPsnjob 人员任职信息
     * @return 结果
     */
    public int insert(SysPsnjob sysPsnjob);

    /**
     * 修改人员任职信息
     * 
     * @param sysPsnjob 人员任职信息
     * @return 结果
     */
    public int update(SysPsnjob sysPsnjob);

    /**
     * 批量删除人员任职信息
     * 
     * @param psnjobIds 需要删除的人员任职信息ID
     * @return 结果
     */
    public int deleteByIds(Long[] psnjobIds);

    /**
     * 删除人员任职信息信息
     * 
     * @param psnjobId 人员任职信息ID
     * @return 结果
     */
    public int deleteById(Long psnjobId);
}
