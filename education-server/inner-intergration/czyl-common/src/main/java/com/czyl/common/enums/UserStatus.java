package com.czyl.common.enums;

/**
 * 用户状态
 * 
 * @author tanghx
 */
public enum UserStatus {
	/** 正常 */
	OK(0, "正常"),
	/** 删除 */
	DELETED(1, "删除"),
	/** 停用 */
	DISABLE(1, "停用"),;

	/**
	 * 编码
	 */
	private final Integer code;
	/**
	 * 描述信息
	 */
	private final String info;

	UserStatus(Integer code, String info) {
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
