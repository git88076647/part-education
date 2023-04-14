package com.czyl.framework.sharding;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import com.czyl.common.utils.DateUtils;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;

import cn.hutool.core.date.DateUtil;

/**
 * 根据日期类型字段 按年 分表 区间查询
 * 
 * @author tanghx
 *
 */
public class DateYearRangeShardingAlgorithm implements RangeShardingAlgorithm<Date> {

	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> shardingValue) {
		Range<Date> range = shardingValue.getValueRange();
		Set<String> tables = Sets.newLinkedHashSet();
		Set<String> allTables = Sets.newLinkedHashSet();
		if (range == null) {
			throw new IllegalArgumentException("日期数据不能为空，否则无法确定查询哪一张表");
		}
		// 启示日期修改为年第一天，避免开始日期的月日大于结束的月日导致最后一年加不上
		Date beginD = DateUtils.parseDate(String.format("%s-01-01", DateUtil.year(range.lowerEndpoint())));
		Date endD = range.upperEndpoint();

		String table;
		while (beginD.compareTo(endD) <= 0) {
			// 物理表
			table = String.format("%s_%s", shardingValue.getLogicTableName() ,DateUtil.year(beginD));
			allTables.add(table);
			if (availableTargetNames.contains(table)) {
				tables.add(table);
			}
			beginD = DateUtils.addYears(beginD, 1);
		}

		if (tables.size() == 0) {
			throw new IllegalArgumentException(String.format("数据库不存在表%s", allTables));
		}
		return tables;
	}

}
