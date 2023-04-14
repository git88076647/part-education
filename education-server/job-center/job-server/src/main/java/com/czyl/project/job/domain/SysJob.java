package com.czyl.project.job.domain;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * 定时任务调度对象 sys_job
 * 
 * @author tanghx
 * @date 2020-02-24
 */
@Setter
@Table("sys_job")
public class SysJob extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 任务ID */
	@Id("job_id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long jobId;

	/** 任务名称 */
	@Excel(name = "任务名称")
	private String jobName;

	/** 任务组名 */
	@Excel(name = "任务组名")
	private String jobGroup;

	/** 调用目标字符串 */
	@Excel(name = "调用目标字符串")
	private String invokeTarget;

	/** cron执行表达式 */
	@Excel(name = "cron执行表达式")
	private String cronExpression;

	/** 计划执行错误策略（1立即执行 2执行一次 3放弃执行） */
	private String misfirePolicy;

	/** 是否并发执行（0允许 1禁止） */
	private String concurrent;

	/** 状态（0正常 1暂停） */
	@Excel(name = "状态", readConverterExp = "0=正常,1=暂停")
	private String status;

	/** 参数 */
	private String exeParams;

	public Long getJobId() {
		return jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public String getInvokeTarget() {
		return invokeTarget;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public String getConcurrent() {
		return concurrent;
	}

	public String getStatus() {
		return status;
	}

	public String getExeParams() {
		return exeParams;
	}

	public String getMisfirePolicy() {
		return misfirePolicy;
	}
	
	
	
	
}
