package com.czyl.project.job.service.impl;

import java.util.List;
import com.czyl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.czyl.project.job.mapper.SysJobLogMapper;
import com.czyl.project.job.domain.SysJobLog;
import com.czyl.project.job.service.ISysJobLogService;

/**
 * 任务调度日志Service业务层处理
 * 
 * @author tanghx
 * @date 2020-02-25
 */
@Service
public class SysJobLogServiceImpl implements ISysJobLogService {
	@Autowired
	private SysJobLogMapper sysJobLogMapper;

	/**
	 * 查询任务调度日志
	 * 
	 * @param jobLogId 任务调度日志ID
	 * @return 任务调度日志
	 */
	@Override
	public SysJobLog selectSysJobLogById(Long jobLogId) {
		return sysJobLogMapper.selectSysJobLogById(jobLogId);
	}

	/**
	 * 查询任务调度日志列表
	 * 
	 * @param sysJobLog 任务调度日志
	 * @return 任务调度日志
	 */
	@Override
	public List<SysJobLog> selectSysJobLogList(SysJobLog sysJobLog) {
		return sysJobLogMapper.selectSysJobLogList(sysJobLog);
	}

	/**
	 * 新增任务调度日志
	 * 
	 * @param sysJobLog 任务调度日志
	 * @return 结果
	 */
	@Override
	public int insertSysJobLog(SysJobLog sysJobLog) {
		sysJobLog.setCreateTime(DateUtils.getNowDate());
		return sysJobLogMapper.insertSysJobLog(sysJobLog);
	}

	/**
	 * 修改任务调度日志
	 * 
	 * @param sysJobLog 任务调度日志
	 * @return 结果
	 */
	@Override
	public int updateSysJobLog(SysJobLog sysJobLog) {
		return sysJobLogMapper.updateSysJobLog(sysJobLog);
	}

	/**
	 * 批量删除任务调度日志
	 * 
	 * @param jobLogIds 需要删除的任务调度日志ID
	 * @return 结果
	 */
	@Override
	public int deleteSysJobLogByIds(Long[] jobLogIds) {
		return sysJobLogMapper.deleteSysJobLogByIds(jobLogIds);
	}

	/**
	 * 删除任务调度日志信息
	 * 
	 * @param jobLogId 任务调度日志ID
	 * @return 结果
	 */
	@Override
	public int deleteSysJobLogById(Long jobLogId) {
		return sysJobLogMapper.deleteSysJobLogById(jobLogId);
	}

	@Override
	public void cleanJobLog() {
		sysJobLogMapper.cleanJobLog();
	}
}
