package com.czyl.common.enums;

/**
 * 自由报表平台 事件 类型<br/>
 * <br/>
 * <br/>
 *
 * @author air Email:209308343@qq.com
 * @date 2020/4/2 0002 15:44
 * @project
 * @Version
 */
public enum DataEventTypeEnum {
	/** 新增数据源 */
	ADDDS(1001, "新增数据源"),
	/** 新增语义模型 */
	ADDSEM(1002, "新增语义模型"),
	/** 新增语义脚本 */
	ADDDSEMSCR(1003, "新增语义脚本"),
	/** 新增语义参数 */
	ADDDSEMPAR(1004, "新增语义参数"),
	/** 执行语义模型的查询之前 */
	QUERYBEFOR(1005, "执行语义模型的查询之前"),
	/** 执行语义模型的查询之后 */
	QUERYAFTER(1006, "执行语义模型的查询之后");

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	DataEventTypeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * 事件码
	 */
	private int value;
	
	/**
	 * 事件说明
	 */
	private String name;
}
