package com.czyl.project.job.service;

import com.czyl.project.job.domain.SysJobLog;
import java.util.List;

/**
 * 任务调度日志Service接口
 * 
 * @author tanghx
 * @date 2020-02-25
 */
public interface ISysJobLogService 
{
    /**
     * 查询任务调度日志
     * 
     * @param jobLogId 任务调度日志ID
     * @return 任务调度日志
     */
    public SysJobLog selectSysJobLogById(Long jobLogId);

    /**
     * 查询任务调度日志列表
     * 
     * @param sysJobLog 任务调度日志
     * @return 任务调度日志集合
     */
    public List<SysJobLog> selectSysJobLogList(SysJobLog sysJobLog);

    /**
     * 新增任务调度日志
     * 
     * @param sysJobLog 任务调度日志
     * @return 结果
     */
    public int insertSysJobLog(SysJobLog sysJobLog);

    /**
     * 修改任务调度日志
     * 
     * @param sysJobLog 任务调度日志
     * @return 结果
     */
    public int updateSysJobLog(SysJobLog sysJobLog);

    /**
     * 批量删除任务调度日志
     * 
     * @param jobLogIds 需要删除的任务调度日志ID
     * @return 结果
     */
    public int deleteSysJobLogByIds(Long[] jobLogIds);

    /**
     * 删除任务调度日志信息
     * 
     * @param jobLogId 任务调度日志ID
     * @return 结果
     */
    public int deleteSysJobLogById(Long jobLogId);
    
    /**
     * 清空任务日志
     */
    public void cleanJobLog();
}
