package com.czyl.common.enums;

/**
 * 实体状态
 * 
 * @author tanghx
 */
public enum EntityStatus {
	/** 正常 */
	NORMAL(0, "正常"),
	/** 删除 */
	DELETED(1, "删除"),
	/** 更新 */
	UPDATED(2, "更新"),
	/** 新增*/
	ADD(3, "新增");

	/**
	 * 编码
	 */
	private final Integer code;
	/**
	 * 描述信息
	 */
	private final String info;

	EntityStatus(Integer code, String info) {
		this.code = code;
		this.info = info;
	}

	public Integer value() {
		return code;
	}

	public String getInfo() {
		return info;
	}
}
