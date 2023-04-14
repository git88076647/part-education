package com.czyl.framework.table.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表列属性缓存
 * @author tanghx
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ColumnInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String attr;
	String column;
	
}
