package com.czyl.common.utils.job;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.czyl.common.constant.Constants;
import com.czyl.common.constant.ScheduleConstants;
import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.utils.spring.SpringUtils;
import com.czyl.project.job.domain.SysJob;
import com.czyl.project.job.domain.SysJobLog;
import com.czyl.project.job.jobtask.IJobTask;
import com.czyl.project.job.service.ISysJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * 抽象quartz调用
 *
 * @author tanghx
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {

	/**
	 * 本地线程变量
	 */
	private ThreadLocal<Date> threadLocal = new TransmittableThreadLocal<>();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SysJob sysJob = new SysJob();
		BeanUtil.copyProperties(context.getMergedJobDataMap().get(ScheduleConstants.JOB_PROPERTIES),sysJob);
		try {
			before(context, sysJob);
			if (sysJob != null) {
				doExecute(context, sysJob);
			}
			after(context, sysJob, null);
		} catch (Exception e) {
			log.error("任务执行异常  - ：", e);
			after(context, sysJob, e);
		}
	}

	/**
	 * 执行前
	 *
	 * @param context 工作执行上下文对象
	 * @param sysJob  系统计划任务
	 */
	protected void before(JobExecutionContext context, SysJob sysJob) {
		threadLocal.set(new Date());
	}

	/**
	 * 执行后
	 *
	 * @param context        工作执行上下文对象
	 * @param sysJob
	 * @param e
	 */
	protected void after(JobExecutionContext context, SysJob sysJob, Exception e) {
		Date startTime = threadLocal.get();
		threadLocal.remove();

		final SysJobLog sysJobLog = new SysJobLog();
		sysJobLog.setJobId(sysJob.getJobId());
		sysJobLog.setJobName(sysJob.getJobName());
		sysJobLog.setJobGroup(sysJob.getJobGroup());
		sysJobLog.setInvokeTarget(sysJob.getInvokeTarget());
		sysJobLog.setExeParams(sysJob.getExeParams());
		long runMs = System.currentTimeMillis() - startTime.getTime();
		sysJobLog.setJobMessage(sysJobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
		if (e != null) {
			sysJobLog.setStatus(String.valueOf(Constants.FAIL));
			String errorMsg = StringUtils.substring(ExceptionUtils.getStackTrace(e), 0, 2000);
			sysJobLog.setExceptionInfo(errorMsg);
		} else {
			sysJobLog.setStatus(String.valueOf(Constants.SUCCESS));
		}
		// 写入数据库当中
		SpringUtils.getBean(ISysJobLogService.class).insertSysJobLog(sysJobLog);
	}

	/**
	 * 执行方法，由子类重载
	 *
	 * @param context 工作执行上下文对象
	 * @param sysJob  系统计划任务
	 * @throws Exception 执行过程中的异常
	 */
	protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
		String beanName = sysJob.getInvokeTarget();
		if (SpringUtils.containsBean(beanName) == false) {
			throw new CustomException(String.format("不存在目标Bean:%s",beanName));
		}
		if(SpringUtils.getBean(beanName) instanceof IJobTask) {
			IJobTask jobTask = SpringUtils.getBean(beanName);
			jobTask.doExecute(sysJob.getExeParams());
		}else {
			throw new CustomException(String.format("目标Bean:%s 未实现接口IJobTask: ", beanName)) ;
		}
		
	}
}
