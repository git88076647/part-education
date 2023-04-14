package com.czyl.framework.datasource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询选择数据源
 * 
 * @author tanghx
 *
 */
public class LoadBalanceDynamicDataSourceStrategy implements DynamicDataSourceStrategy {

	/**
	 * 负载均衡计数器
	 */
	Map<String, AtomicInteger> indexMap = new HashMap<>();

	@Override
	public String determineCurrentLookupKey(String groupKey, List<String> dataSources) {
		AtomicInteger index;
		if (indexMap.containsKey(groupKey)) {
			index = indexMap.get(groupKey);
		} else {
			index = new AtomicInteger(0);
			indexMap.put(groupKey, index);
		}
		return dataSources.get(Math.abs(index.getAndAdd(1) % dataSources.size()));
	}

}
