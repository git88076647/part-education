package com.czyl.project.system.service.impl;

import java.util.List;
import com.czyl.common.utils.AppContextUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.czyl.project.system.mapper.SysPsnjobMapper;
import com.czyl.project.system.domain.SysPsnjob;
import com.czyl.project.system.service.ISysPsnjobService;

/**
 * 人员任职信息Service业务层处理
 * 
 * @author tanghx
 * @date 2020-04-26
 */
@Service
public class SysPsnjobServiceImpl implements ISysPsnjobService{
    @Autowired
    private SysPsnjobMapper sysPsnjobMapper;

    /**
     * 查询人员任职信息
     * 
     * @param psnjobId 人员任职信息ID
     * @return 人员任职信息
     */
    @Override
    public SysPsnjob selectById(Long psnjobId){
        return sysPsnjobMapper.selectById(psnjobId);
    }

    /**
     * 查询人员任职信息列表
     * 
     * @param sysPsnjob 人员任职信息
     * @return 人员任职信息
     */
    @Override
    public List<SysPsnjob> selectList(SysPsnjob sysPsnjob){
        return sysPsnjobMapper.selectList(sysPsnjob);
    }

    /**
     * 新增人员任职信息
     * 
     * @param sysPsnjob 人员任职信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(SysPsnjob sysPsnjob){
        return sysPsnjobMapper.insert(sysPsnjob);
    }

    /**
     * 修改人员任职信息
     * 
     * @param sysPsnjob 人员任职信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(SysPsnjob sysPsnjob){
        return sysPsnjobMapper.update(sysPsnjob);
    }

    /**
     * 批量删除人员任职信息
     * 
     * @param psnjobIds 需要删除的人员任职信息ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByIds(Long[] psnjobIds){
        return sysPsnjobMapper.deleteByIds(psnjobIds);
    }

    /**
     * 删除人员任职信息信息
     * 
     * @param psnjobId 人员任职信息ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(Long psnjobId){
        return sysPsnjobMapper.deleteById(psnjobId);
    }

	@Override
	public List<SysPsnjob> selectByParentId(Long parentId) {
		return sysPsnjobMapper.selectByParentId(parentId);
	}
}
