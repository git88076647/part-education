package com.czyl.project.log.domain;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 操作日志记录表 oper_log
 * 
 * @author tanghx
 */
@Data
@Setter
@Getter
public class SysBackLog implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String type;
	private String index;
	private String shard;
	
	@JSONField(name = "@timestamp")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date timestamp;
	private String message;
	
	private String offset;
	private String source;
	private String beat;
	private String host;

}
