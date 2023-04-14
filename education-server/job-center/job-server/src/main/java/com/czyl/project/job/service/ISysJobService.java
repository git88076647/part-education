package com.czyl.project.job.service;

import com.czyl.project.job.domain.SysJob;
import java.util.List;

/**
 * 任务调度Service接口
 * 
 * @author tanghx
 * @date 2020-02-24
 */
public interface ISysJobService {
	/**
	 * 查询任务调度
	 * 
	 * @param jobId 任务调度ID
	 * @return 任务调度
	 */
	public SysJob selectSysJobById(Long jobId);

	/**
	 * 查询任务调度列表
	 * 
	 * @param sysJob 任务调度
	 * @return 任务调度集合
	 */
	public List<SysJob> selectSysJobList(SysJob sysJob);

	/**
	 * 新增任务调度
	 * 
	 * @param sysJob 任务调度
	 * @return 结果
	 */
	public int insertSysJob(SysJob sysJob);

	/**
	 * 修改任务调度
	 * 
	 * @param sysJob 任务调度
	 * @return 结果
	 */
	public int updateSysJob(SysJob sysJob);
	
	/**
	 * 任务调度状态修改
	 * @param sysJob 任务调度
	 * @return
	 */
	public int changeStatus(SysJob sysJob);

	/**
	 * 批量删除任务调度
	 * 
	 * @param jobIds 需要删除的任务调度ID
	 * @return 结果
	 */
	public int deleteSysJobByIds(Long[] jobIds);

	/**
	 * 删除任务调度信息
	 * 
	 * @param jobId 任务调度ID
	 * @return 结果
	 */
	public int deleteSysJobById(Long jobId);
	
	/**
	 * 执行一次任务
	 * @param sysJob
	 * @return
	 */
	public void run(SysJob sysJob);
}
