package com.czyl.project.system.service.impl;

import java.util.List;
import com.czyl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.czyl.project.system.mapper.SysFileMapper;
import com.czyl.project.system.domain.SysFile;
import com.czyl.project.system.service.ISysFileService;

/**
 * 文件管理Service业务层处理
 * 
 * @author tanghx
 * @date 2020-01-15
 */
@Service
public class SysFileServiceImpl implements ISysFileService {
	@Autowired
	private SysFileMapper sysFileMapper;

	/**
	 * 查询文件管理
	 * 
	 * @param fileId 文件管理ID
	 * @return 文件管理
	 */
	@Override
	public SysFile selectSysFileById(Long fileId) {
		return sysFileMapper.selectSysFileById(fileId);
	}

	/**
	 * 查询文件管理列表
	 * 
	 * @param sysFile 文件管理
	 * @return 文件管理
	 */
	@Override
	public List<SysFile> selectSysFileList(SysFile sysFile) {
		return sysFileMapper.selectSysFileList(sysFile);
	}

	/**
	 * 新增文件管理
	 * 
	 * @param sysFile 文件管理
	 * @return 结果
	 */
	@Override
	@Transactional
	public int insertSysFile(SysFile sysFile) {
		sysFile.setCreateTime(DateUtils.getNowDate());
		return sysFileMapper.insertSysFile(sysFile);
	}

	/**
	 * 修改文件管理
	 * 
	 * @param sysFile 文件管理
	 * @return 结果
	 */
	@Override
	@Transactional
	public int updateSysFile(SysFile sysFile) {
		return sysFileMapper.updateSysFile(sysFile);
	}

	/**
	 * 批量删除文件管理
	 * 
	 * @param fileIds 需要删除的文件管理ID
	 * @return 结果
	 */
	@Override
	@Transactional
	public int deleteSysFileByIds(Long[] fileIds) {
		return sysFileMapper.deleteSysFileByIds(fileIds);
	}

	/**
	 * 删除文件管理信息
	 * 
	 * @param fileId 文件管理ID
	 * @return 结果
	 */
	@Override
	@Transactional
	public int deleteSysFileById(Long fileId) {
		return sysFileMapper.deleteSysFileById(fileId);
	}
}
