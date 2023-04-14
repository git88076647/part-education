package com.czyl.common.web.domain;

import java.util.HashMap;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.czyl.common.constant.HttpStatus;
import com.czyl.common.utils.StringUtils;

/**
 * 操作消息提醒
 * 
 * @author tanghx
 */
public class AjaxResult extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	/** 状态码 */
	public static final String CODE_TAG = "code";

	/** 返回内容 */
	public static final String MSG_TAG = "msg";

	/** 返回详细内容 */
	public static final String MSG_DETAIL_TAG = "msgDetail";

	/** 数据对象 */
	public static final String DATA_TAG = "data";

	/**
	 * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
	 */
	public AjaxResult() {
	}

	/**
	 * 初始化一个新创建的 AjaxResult 对象
	 * 
	 * @param code 状态码
	 * @param msg  返回内容
	 */
	public AjaxResult(int code, String msg) {
		super.put(CODE_TAG, code);
		super.put(MSG_TAG, msg);
	}

	/**
	 * 获取DATA
	 * 
	 * @return
	 */
	public Object getData() {
		return this.get(DATA_TAG);
	}

	public AjaxResult setData(Object object) {
		this.put(DATA_TAG, object);
		return this;
	}

	public AjaxResult setCode(int code) {
		this.put(CODE_TAG, code);
		return this;
	}

	public int getCode() {
		return (int) this.get(CODE_TAG);
	}

	public String getMsg() {
		return (String) this.get(MSG_TAG);
	}

	/**
	 * 初始化一个新创建的 AjaxResult 对象
	 * 
	 * @param code 状态码
	 * @param msg  返回内容
	 * @param data 数据对象
	 */
	public AjaxResult(int code, String msg, Object data) {
		super.put(CODE_TAG, code);
		super.put(MSG_TAG, msg);
		if (StringUtils.isNotNull(data)) {
			super.put(DATA_TAG, data);
		}
	}

	/**
	 * 设置详细消息
	 * 
	 * @param msgDetail
	 * @return
	 */
	public AjaxResult setMsgDetail(String msgDetail) {
		this.put(MSG_DETAIL_TAG, msgDetail);
		return this;
	}

	/**
	 * 返回成功消息
	 * 
	 * @return 成功消息
	 */
	public static AjaxResult success() {
		return AjaxResult.success("操作成功");
	}

	/**
	 * 返回成功数据
	 * 
	 * @return 成功消息
	 */
	public static AjaxResult success(Object data) {
		return AjaxResult.success("操作成功", data);
	}

	/**
	 * 返回成功消息
	 * 
	 * @param msg 返回内容
	 * @return 成功消息
	 */
	public static AjaxResult success(String msg) {
		return AjaxResult.success(msg, null);
	}

	/**
	 * 返回成功消息
	 * 
	 * @param msg  返回内容
	 * @param data 数据对象
	 * @return 成功消息
	 */
	public static AjaxResult success(String msg, Object data) {
		return new AjaxResult(HttpStatus.SUCCESS, msg, data);
	}

	/**
	 * 返回错误消息
	 * 
	 * @return
	 */
	public static AjaxResult error(Exception e) {
		return AjaxResult.error("操作失败").setMsgDetail(ExceptionUtils.getStackTrace(e));
	}

	/**
	 * 返回错误消息
	 * 
	 * @return
	 */
	public static AjaxResult error() {
		return AjaxResult.error("操作失败");
	}

	/**
	 * 返回错误消息
	 * 
	 * @param msg 返回内容
	 * @return 警告消息
	 */
	public static AjaxResult error(String msg) {
		return AjaxResult.error(msg, null);
	}

	/**
	 * 返回错误消息
	 * 
	 * @param e   异常对象
	 * @param msg 消息简讯
	 * @return
	 */
	public static AjaxResult error(Exception e, String msg) {
		return new AjaxResult(HttpStatus.ERROR, msg).setMsgDetail(ExceptionUtils.getStackTrace(e));
	}

	/**
	 * 返回错误消息
	 * 
	 * @param msg  返回内容
	 * @param data 数据对象
	 * @return 警告消息
	 */
	public static AjaxResult error(String msg, Object data) {
		return new AjaxResult(HttpStatus.ERROR, msg, data);
	}

	/**
	 * 返回错误消息
	 * 
	 * @param code 状态码
	 * @param msg  返回内容
	 * @return 警告消息
	 */
	public static AjaxResult error(int code, String msg) {
		return new AjaxResult(code, msg, null);
	}
}
