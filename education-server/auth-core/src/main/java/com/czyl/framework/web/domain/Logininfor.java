package com.czyl.framework.web.domain;

import java.util.Date;

import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 	系统访问记录表 
 * 	每个系统组装数据再调用日志服务存储日志
 * </pre>
 * @author tanghx
 */
@Getter
@Setter
public class Logininfor extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** ID */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long infoId;

	/** 用户账号 */
	private String userCode;

	/** 登录状态 0成功 1失败 */
	private Integer status;

	/** 登录IP地址 */
	private String ipaddr;

	/** 登录地点 */
	private String loginLocation;

	/** 浏览器类型 */
	private String browser;

	/** 操作系统 */
	private String os;

	/** 提示消息 */
	private String msg;

	/** 访问时间 */
	private Date loginTime;

}