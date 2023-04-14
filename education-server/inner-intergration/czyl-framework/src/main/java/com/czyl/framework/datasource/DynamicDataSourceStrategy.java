package com.czyl.framework.datasource;

import java.util.List;

/**
 * 动态数据源选择的策略
 *
 * @author tanghx
 * @see LoadBalanceDynamicDataSourceStrategy
 */
public interface DynamicDataSourceStrategy {

	/**
	 * 选择一个数据源
	 * @param groupKey 数据源组
	 * @param dataSources 数据源组里的全部数据源
	 * @return
	 */
	String determineCurrentLookupKey(String groupKey,List<String> dataSources);
}
