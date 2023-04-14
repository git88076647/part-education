package com.czyl.framework.datasource;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections4.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * 动态数据源, 代码里指定数据源但是配置文件未配置则采用默认主数据源
 * 
 * @author tanghx
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Autowired
	private DynamicDataSourceStrategy dynamicDataSourceStrategy;

	private Map<String, List<String>> targetGroupDataSources = new LinkedMap<>();

	private Map<Object, Object> targetDataSources;

	public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
		if(log.isDebugEnabled()) {
			log.debug("初始化所有数据源: {}",targetDataSources.keySet());
			for (Map.Entry<Object, Object> entry: targetDataSources.entrySet()) {
				if(entry.getValue() == defaultTargetDataSource) {
					log.debug("主数据源: {}",entry.getKey());
					break;
				}
			}
		}
		this.targetDataSources = targetDataSources;
		super.setDefaultTargetDataSource(defaultTargetDataSource);
		super.setTargetDataSources(this.targetDataSources);
		afterPropertiesSet();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		// TODO 此处需要处理targetDataSources的分组与负载均衡，比如 一主两从的时候，两从需轮询或其他负载均衡策略进行决策
		String dsType = DynamicDataSourceContextHolder.getDataSourceType();
		if(dsType == null) {
			return null;
		}
		if (targetGroupDataSources.containsKey(dsType)) {
			String ds = dynamicDataSourceStrategy.determineCurrentLookupKey(dsType, targetGroupDataSources.get(dsType));
			log.debug("切换到数据源组{}的数据源{}", dsType,ds);
			return ds;
		}
		if(targetDataSources.containsKey(dsType) == false) {
			log.debug("数据源{}不存在,默认主数据源", dsType);
			return null;
		}
		log.debug("切换到数据源{}", dsType);
		return dsType;
	}

	/**
	 * 动态新增数据源
	 * @param key
	 * @param dataSource
	 */
	public synchronized void addDynamicDataSource(String key, DataSource dataSource) {
		targetDataSources.put(key, dataSource);
		super.setTargetDataSources(targetDataSources);
		afterPropertiesSet();
	}
	
	/**
	 * 获取数据源key
	 * @return
	 */
	public String[] getDsNames(){
		return targetDataSources.keySet().toArray(new String[0]);
	}
	
	/**
	 * 获取数据源分组key
	 * @return
	 */
	public String[] getDsGroupNames(){
		return targetGroupDataSources.keySet().toArray(new String[0]);
	}

	@Override
	public synchronized void afterPropertiesSet() {
		String key;
		String groupKey;
		int index;
		for (Map.Entry<Object, Object> entry : targetDataSources.entrySet()) {
			key = entry.getKey().toString();
			index = key.lastIndexOf("_");
			if (index > -1) {
				groupKey = key.substring(0, index);
				if (targetGroupDataSources.containsKey(groupKey) == false) {
					targetGroupDataSources.put(groupKey, new LinkedList<>());
				}
				if(targetGroupDataSources.get(groupKey).contains(key) == false) {
					targetGroupDataSources.get(groupKey).add(key);
				}
			}
		}

		super.afterPropertiesSet();
	}

}