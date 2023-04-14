package com.czyl.project.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 定时任务配置
 * 
 * @author tanghx
 */
@Configuration
public class ScheduleConfig {
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setDataSource(dataSource);

		// quartz参数
		Properties prop = new Properties();
		//quartz集群配置  
		//调度标识名 集群中每一个实例都必须使用相同的名称 
		prop.put("org.quartz.scheduler.instanceName", "CzylScheduler");
		//ID设置为自动获取 每一个必须不同  
		prop.put("org.quartz.scheduler.instanceId", "AUTO");
		prop.put("org.quartz.scheduler.makeSchedulerThreadDaemon", "true");
		//线程池的实现类（一般使用SimpleThreadPool即可满足需求）  
		prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		//指定在线程池里面创建的线程是否是守护线程
		prop.put("org.quartz.threadPool.makeThreadsDaemons", "true");
		//指定线程数，至少为1（无默认值）
		prop.put("org.quartz.threadPool.threadCount", "30");
		//设置线程的优先级（最大为java.lang.Thread.MAX_PRIORITY 10，最小为Thread.MIN_PRIORITY 1，默认为5） 
		prop.put("org.quartz.threadPool.threadPriority", "5");
		// JobStore配置 数据保存方式为数据库持久化 
		prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
		// 集群配置 是否加入集群
		prop.put("org.quartz.jobStore.isClustered", "true");
		//数据库代理类，一般org.quartz.impl.jdbcjobstore.StdJDBCDelegate可以满足大部分数据库 
		prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
		prop.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
		prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");
		prop.put("org.quartz.jobStore.txIsolationLevelSerializable", "true");

		// sqlserver 启用
		// prop.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS
		// UPDLOCK WHERE LOCK_NAME = ?");
		//信息保存时间 默认值60秒   
		prop.put("org.quartz.jobStore.misfireThreshold", "25000");
		prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
		factory.setQuartzProperties(prop);

		factory.setSchedulerName("CzylScheduler");
		// 延时启动
		factory.setStartupDelay(1);
		factory.setApplicationContextSchedulerContextKey("applicationContextKey");
		// 可选，QuartzScheduler
		// 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
		factory.setOverwriteExistingJobs(true);
		// 设置自动启动，默认为true
		factory.setAutoStartup(true);

		return factory;
	}
   
	
}
