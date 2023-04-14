package com.czyl.project.job.domain;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * 定时任务调度日志对象 sys_job_log
 * 
 * @author tanghx
 * @date 2020-02-25
 */
@Setter
@Getter
@Table("sys_job_log")
public class SysJobLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务日志ID */
	@Id("job_log_id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long jobLogId;

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

    /** 日志信息 */
    @Excel(name = "日志信息")
    private String jobMessage;

    /** 执行状态（0正常 1失败） */
    @Excel(name = "执行状态", readConverterExp = "0=正常,1=失败")
    private String status;

    /** 异常信息 */
    @Excel(name = "异常信息")
    private String exceptionInfo;

    /** 参数 */
    @Excel(name = "参数")
    private String exeParams;



   
}
