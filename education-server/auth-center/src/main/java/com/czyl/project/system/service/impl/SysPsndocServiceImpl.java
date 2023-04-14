package com.czyl.project.system.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.czyl.common.enums.EntityStatus;
import com.czyl.common.utils.DateUtils;
import com.czyl.project.system.domain.SysPsndoc;
import com.czyl.project.system.domain.SysPsnjob;
import com.czyl.project.system.mapper.SysPsndocMapper;
import com.czyl.project.system.mapper.SysPsnjobMapper;
import com.czyl.project.system.service.ISysPsndocService;

/**
 * 人员管理Service业务层处理
 * 
 * @author tanghx
 * @date 2019-12-11
 */
@Service
public class SysPsndocServiceImpl implements ISysPsndocService {
	@Autowired
	private SysPsndocMapper sysPsndocMapper;

	@Autowired
	private SysPsnjobMapper sysPsnjobMapper;

	/**
	 * 查询人员管理
	 * 
	 * @param psnId 人员管理ID
	 * @return 人员管理
	 */
	@Override
	public SysPsndoc selectSysPsndocById(Long psnId) {
		return sysPsndocMapper.selectSysPsndocById(psnId);
	}

	/**
	 * 查询人员管理列表
	 * 
	 * @param sysPsndoc 人员管理
	 * @return 人员管理
	 */
	@Override
	public List<SysPsndoc> selectSysPsndocList(SysPsndoc sysPsndoc) {
		return sysPsndocMapper.selectSysPsndocList(sysPsndoc);
	}

	/**
	 * 新增人员管理
	 * 
	 * @param sysPsndoc 人员管理
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertSysPsndoc(SysPsndoc sysPsndoc) {
		sysPsndoc.setCreateTime(DateUtils.getNowDate());
		int retRow = sysPsndocMapper.insertSysPsndoc(sysPsndoc);
		if(sysPsndoc.getPsnJobs() != null && sysPsndoc.getPsnJobs().size() >0) {
			for (SysPsnjob psnjob : sysPsndoc.getPsnJobs()) {
				psnjob.setPsnId(sysPsndoc.getPsnId());
			}
			sysPsnjobMapper.insertBatch(sysPsndoc.getPsnJobs());
		}
		return retRow;
	}

	/**
	 * 修改人员管理
	 * 
	 * @param sysPsndoc 人员管理
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateSysPsndoc(SysPsndoc sysPsndoc) {
		sysPsndoc.setUpdateTime(DateUtils.getNowDate());
		int retRow = sysPsndocMapper.updateSysPsndoc(sysPsndoc);
		
		if(sysPsndoc.getPsnJobs() != null && sysPsndoc.getPsnJobs().size() >0) {
			List<SysPsnjob> addRows=new LinkedList<>();
			List<SysPsnjob> updateRows=new LinkedList<>();
			List<Long> delRows=new LinkedList<>();
			
			for (SysPsnjob psnjob : sysPsndoc.getPsnJobs()) {
				if(psnjob.getPsnjobId() == null || psnjob.getPsnjobId().longValue() == 0) {
					psnjob.setPsnId(sysPsndoc.getPsnId());
					addRows.add(psnjob);
				}else if(EntityStatus.DELETED.value().equals(psnjob.getEntityStatus())) {
					delRows.add(psnjob.getPsnjobId());
				}else if(EntityStatus.UPDATED.value().equals(psnjob.getEntityStatus())) {
					updateRows.add(psnjob);
				}
			}
			if(addRows.size() > 0) {
				sysPsnjobMapper.insertBatch(addRows);
			}
			if(updateRows.size() > 0) {
				sysPsnjobMapper.updateBatch(updateRows);
			}
			if(delRows.size() > 0) {
				sysPsnjobMapper.deleteByIds(delRows.toArray(new Long[delRows.size()]));
			}
		}
		return retRow;
	}

	/**
	 * 批量删除人员管理
	 * 
	 * @param psnIds 需要删除的人员管理ID
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteSysPsndocByIds(Long[] psnIds) {
		sysPsnjobMapper.deleteByparentIds(psnIds);
		return sysPsndocMapper.deleteSysPsndocByIds(psnIds);
	}

	/**
	 * 删除人员管理信息
	 * 
	 * @param psnId 人员管理ID
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteSysPsndocById(Long psnId) {
		sysPsnjobMapper.deleteByparentId(psnId);
		return sysPsndocMapper.deleteSysPsndocById(psnId);
	}
}
