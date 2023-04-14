package com.czyl.project.system.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.czyl.common.utils.AppContextUtils;
import com.czyl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.czyl.project.system.mapper.SysWeakpasswordMapper;
import com.czyl.project.system.domain.SysWeakpassword;
import com.czyl.project.system.service.ISysWeakpasswordService;

/**
 * 弱口令管理Service业务层处理
 * 
 * @author tanghx
 * @date 2020-04-08
 */
@Service
public class SysWeakpasswordServiceImpl implements ISysWeakpasswordService {
	@Autowired
	private SysWeakpasswordMapper sysWeakpasswordMapper;

	/**
	 * 查询弱口令管理
	 * 
	 * @param pwdId 弱口令管理ID
	 * @return 弱口令管理
	 */
	@Override
	public SysWeakpassword selectSysWeakpasswordById(Long pwdId) {
		return sysWeakpasswordMapper.selectSysWeakpasswordById(pwdId);
	}

	/**
	 * 查询弱口令管理列表
	 * 
	 * @param sysWeakpassword 弱口令管理
	 * @return 弱口令管理
	 */
	@Override
	public List<SysWeakpassword> selectSysWeakpasswordList(SysWeakpassword sysWeakpassword) {
		return sysWeakpasswordMapper.selectSysWeakpasswordList(sysWeakpassword);
	}

	/**
	 * 新增弱口令管理
	 * 
	 * @param sysWeakpassword 弱口令管理
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertSysWeakpassword(SysWeakpassword sysWeakpassword) {
		if (sysWeakpassword.getCreateBy() == null || sysWeakpassword.getCreateBy() == 0) {
			sysWeakpassword.setCreateBy(AppContextUtils.getUserId());
		}
		sysWeakpassword.setCreateTime(DateUtils.getNowDate());
		return sysWeakpasswordMapper.insertSysWeakpassword(sysWeakpassword);
	}

	/**
	 * 修改弱口令管理
	 * 
	 * @param sysWeakpassword 弱口令管理
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateSysWeakpassword(SysWeakpassword sysWeakpassword) {
		if (sysWeakpassword.getUpdateBy() == null || sysWeakpassword.getUpdateBy() == 0) {
			sysWeakpassword.setUpdateBy(AppContextUtils.getUserId());
		}
		sysWeakpassword.setUpdateTime(DateUtils.getNowDate());
		return sysWeakpasswordMapper.updateSysWeakpassword(sysWeakpassword);
	}

	/**
	 * 批量删除弱口令管理
	 * 
	 * @param pwdIds 需要删除的弱口令管理ID
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteSysWeakpasswordByIds(Long[] pwdIds) {
		return sysWeakpasswordMapper.deleteSysWeakpasswordByIds(pwdIds);
	}

	/**
	 * 删除弱口令管理信息
	 * 
	 * @param pwdId 弱口令管理ID
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteSysWeakpasswordById(Long pwdId) {
		return sysWeakpasswordMapper.deleteSysWeakpasswordById(pwdId);
	}

	@Override
	public SysWeakpassword selectSysWeakpasswordByPassword(String password) {
		return sysWeakpasswordMapper.selectSysWeakpasswordByPassword(password);
	}

	@Override
	@Async
	public void importData(final List<String> pwdList,final Long userid) {
		int counter=0;
		SysWeakpassword tmp;
		List<SysWeakpassword> list = new LinkedList<SysWeakpassword>();
		Date createTime = DateUtils.getNowDate();
		for (String password : pwdList) {
			tmp = sysWeakpasswordMapper.selectSysWeakpasswordByPassword(password);
			if(tmp == null) {
				tmp = new SysWeakpassword();
				tmp.setPassword(password);
				tmp.setCreateBy(userid);
				tmp.setCreateTime(createTime);
				tmp.setStatus(0);
				tmp.setDr(0);
				tmp.setVersion(1L);
				list.add(tmp);
				
				if(list.size() == 1000) {
					counter += sysWeakpasswordMapper.insertSysWeakpasswordBatch(list);
					list.clear();
				}
			}
		}
		if(list.size() > 0) {
			counter += sysWeakpasswordMapper.insertSysWeakpasswordBatch(list);
		}
		
//		return counter;
	}
}
