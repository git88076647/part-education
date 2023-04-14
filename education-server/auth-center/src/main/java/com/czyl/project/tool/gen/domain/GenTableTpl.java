package com.czyl.project.tool.gen.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class GenTableTpl implements Serializable {
	/**
	 * 模版主键
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long id;

	private String billtplcode;

	/**
	 * 模版类型
	 */
	private String type;
	private String label;

	private String align;
	private String prop;
	private String formatter;
	private Integer showList;
	private Integer showCard;
	private Integer sortindex;

}
