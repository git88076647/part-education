package com.czyl.project.system.mapper;

import com.czyl.project.system.domain.SysPsndoc;
import java.util.List;

/**
 * 人员管理Mapper接口
 * 
 * @author tanghx
 * @date 2019-12-11
 */
public interface SysPsndocMapper 
{
    /**
     * 查询人员管理
     * 
     * @param psnId 人员管理ID
     * @return 人员管理
     */
    public SysPsndoc selectSysPsndocById(Long psnId);

    /**
     * 查询人员管理列表
     * 
     * @param sysPsndoc 人员管理
     * @return 人员管理集合
     */
    public List<SysPsndoc> selectSysPsndocList(SysPsndoc sysPsndoc);

    /**
     * 新增人员管理
     * 
     * @param sysPsndoc 人员管理
     * @return 结果
     */
    public int insertSysPsndoc(SysPsndoc sysPsndoc);

    /**
     * 修改人员管理
     * 
     * @param sysPsndoc 人员管理
     * @return 结果
     */
    public int updateSysPsndoc(SysPsndoc sysPsndoc);

    /**
     * 删除人员管理
     * 
     * @param psnId 人员管理ID
     * @return 结果
     */
    public int deleteSysPsndocById(Long psnId);

    /**
     * 批量删除人员管理
     * 
     * @param psnIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysPsndocByIds(Long[] psnIds);
}
