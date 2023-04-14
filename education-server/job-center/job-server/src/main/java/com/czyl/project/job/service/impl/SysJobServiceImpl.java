package com.czyl.project.job.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czyl.common.constant.ScheduleConstants;
import com.czyl.common.exception.CustomException;
import com.czyl.common.exception.job.TaskException;
import com.czyl.common.utils.AppContextUtils;
import com.czyl.common.utils.DateUtils;
import com.czyl.common.utils.job.ScheduleUtils;
import com.czyl.project.job.domain.SysJob;
import com.czyl.project.job.mapper.SysJobMapper;
import com.czyl.project.job.service.ISysJobService;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务调度Service业务层处理
 * 
 * @author tanghx
 * @date 2020-02-24
 */
@Slf4j
@Service
public class SysJobServiceImpl implements ISysJobService {
	@Autowired
	private SysJobMapper sysJobMapper;

	@Autowired
	private Scheduler scheduler;

	/**
	 * 项目启动时，初始化定时器 主要是防止手动修改数据库导致未同步到定时任务处理 （注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
	 */
	@PostConstruct
	public void init() throws SchedulerException, TaskException {
		SysJob queryJob = new SysJob();
		queryJob.setStatus("0");
		List<SysJob> jobList = sysJobMapper.selectSysJobList(queryJob);
		for (SysJob job : jobList) {
			updateSchedulerJob(job);
		}
	}

	/**
	 * 更新任务
	 * 
	 * @param job 任务对象
	 */
	public void updateSchedulerJob(SysJob job) {
		Long jobId = job.getJobId();
		// 判断是否存在
		JobKey jobKey = ScheduleUtils.getJobKey(jobId, job.getJobGroup());
		try {
			if (scheduler.checkExists(jobKey)) {
				// 防止创建时存在数据问题 先移除，然后在执行创建操作
				scheduler.deleteJob(jobKey);
			}
			ScheduleUtils.createScheduleJob(scheduler, job);
		} catch (Exception e) {
			log.error("更保存任务-更新任务异常",e);
		}
	}

	/**
	 * 查询定时任务调度
	 * 
	 * @param jobId 定时任务调度ID
	 * @return 定时任务调度
	 */
	@Override
	public SysJob selectSysJobById(Long jobId) {
		return sysJobMapper.selectSysJobById(jobId);
	}

	/**
	 * 查询定时任务调度列表
	 * 
	 * @param sysJob 定时任务调度
	 * @return 定时任务调度
	 */
	@Override
	public List<SysJob> selectSysJobList(SysJob sysJob) {
		return sysJobMapper.selectSysJobList(sysJob);
	}

	/**
	 * 新增定时任务调度
	 * 
	 * @param sysJob 定时任务调度
	 * @return 结果
	 */
	@Override
	public int insertSysJob(SysJob sysJob) {
		sysJob.setCreateTime(DateUtils.getNowDate());
		int rows = sysJobMapper.insertSysJob(sysJob);
		if (rows > 0) {
			try {
				ScheduleUtils.createScheduleJob(scheduler, sysJob);
			} catch (Exception e) {
				log.error("新增保存任务-初始化任务异常",e);
			}
		}
		return rows;
	}

	/**
	 * 修改定时任务调度
	 * 
	 * @param sysJob 定时任务调度
	 * @return 结果
	 */
	@Override
	public int updateSysJob(SysJob sysJob) {
		sysJob.setUpdateTime(DateUtils.getNowDate());
		sysJob.setUpdateBy(AppContextUtils.getUserId2());
		SysJob properties = selectSysJobById(sysJob.getJobId());
		int rows = sysJobMapper.updateSysJob(sysJob);
		if (rows > 0) {
			properties = selectSysJobById(sysJob.getJobId());
			updateSchedulerJob(properties);
		}
		return rows;
	}

	/**
	 * 批量删除定时任务调度
	 * 
	 * @param jobIds 需要删除的定时任务调度ID
	 * @return 结果
	 */
	@Override
	public int deleteSysJobByIds(Long[] jobIds) {
		return sysJobMapper.deleteSysJobByIds(jobIds);
	}

	/**
	 * 删除定时任务调度信息
	 * 
	 * @param jobId 定时任务调度ID
	 * @return 结果
	 */
	@Override
	public int deleteSysJobById(Long jobId) {
		return sysJobMapper.deleteSysJobById(jobId);
	}

	@Override
	public int changeStatus(SysJob sysJob) {
		SysJob jobStatus = new SysJob();
		jobStatus.setVersion(sysJob.getVersion());
		jobStatus.setJobId(sysJob.getJobId());
		jobStatus.setStatus(sysJob.getStatus());
		return updateSysJob(jobStatus);
	}

	@Override
	public void run(SysJob sysJob) {
		Long jobId = sysJob.getJobId();
		String jobGroup = sysJob.getJobGroup();
		SysJob properties = selectSysJobById(sysJob.getJobId());
		// 参数
		JobDataMap dataMap = new JobDataMap();
		dataMap.put(ScheduleConstants.JOB_PROPERTIES, properties);
		try {
			JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);

			if (scheduler.checkExists(jobKey) == false) {
				try {
					ScheduleUtils.createScheduleJob(scheduler, properties);
				} catch (TaskException e) {
					log.error("执行一次-创建任务失败", e);
					throw new CustomException("执行一次-创建任务失败");
				}
			}
			if (properties.getStatus().equals(ScheduleConstants.Status.PAUSE.getValue())) {
				scheduler.resumeJob(jobKey);
				scheduler.triggerJob(jobKey, dataMap);
				scheduler.pauseJob(jobKey);
			} else {
				scheduler.triggerJob(jobKey, dataMap);
			}
		} catch (SchedulerException e) {
			log.warn("执行一次任务异常", e);
			throw new CustomException("执行任务异常");
		}

	}
}
