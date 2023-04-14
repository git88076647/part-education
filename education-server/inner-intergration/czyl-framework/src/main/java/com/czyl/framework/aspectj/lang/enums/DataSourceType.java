package com.czyl.framework.aspectj.lang.enums;

/**
 * 数据源
 * 
 * @author tanghx
 */
public class DataSourceType {
	/**
	 * 主库
	 */
	public static final String MASTER = "master";

	/**
	 * 从库
	 */
	public static final String SLAVE = "slave";

	/** 分布式ID数据源  */
	public static final String WORKER_NODE = "workerNode";
	
	/**
	 * 数据分片 数据源
	 */
	public static final String SHARDING = "sharding";

	public static final String SHARDING_BEAN = "shardingDataSource";
}
