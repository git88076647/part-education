package com.czyl.framework.table.domain;

import java.io.Serializable;

import com.czyl.common.utils.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表信息缓存
 * @author tanghx
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TableInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ColumnInfo id;
	
	ColumnInfo version;
	
	String tableName;
	
	Class entity;
	
	public boolean isSupportVersion() {
		return id != null && version!=null && StringUtils.isNotBlank(tableName);
	}
	

}
