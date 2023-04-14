package com.czyl.project.system.service;

import com.czyl.project.system.domain.SysPsndoc;
import java.util.List;

/**
 * 人员管理Service接口
 * 
 * @author tanghx
 * @date 2019-12-11
 */
public interface ISysPsndocService 
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
     * 批量删除人员管理
     * 
     * @param psnIds 需要删除的人员管理ID
     * @return 结果
     */
    public int deleteSysPsndocByIds(Long[] psnIds);

    /**
     * 删除人员管理信息
     * 
     * @param psnId 人员管理ID
     * @return 结果
     */
    public int deleteSysPsndocById(Long psnId);
}
