package com.czyl.common.utils.job;

import org.quartz.DisallowConcurrentExecution;

/**
 * 定时任务处理（禁止并发执行）
 * 
 * @author tanghx
 *
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
	
}
