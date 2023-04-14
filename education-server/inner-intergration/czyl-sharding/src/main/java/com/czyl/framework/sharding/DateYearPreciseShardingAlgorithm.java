package com.czyl.framework.sharding;

import java.util.Collection;
import java.util.Date;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import cn.hutool.core.date.DateUtil;

/**
 * 根据日期类型字段 按年 分表
 * 
 * @author tanghx
 *
 */
public class DateYearPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {

	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
		if (shardingValue == null) {
			throw new IllegalArgumentException("日期数据不能为空，否则无法分表");
		}
		String table = String.format("%s_%s", shardingValue.getLogicTableName(),DateUtil.year(shardingValue.getValue()));
		if (availableTargetNames.contains(table)) {
			return table;
		}
		throw new IllegalArgumentException(String.format("数据库不存在表%s", table));
	}

}
