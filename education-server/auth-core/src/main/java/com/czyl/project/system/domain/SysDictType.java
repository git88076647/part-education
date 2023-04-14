package com.czyl.project.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.aspectj.lang.annotation.Excel.ColumnType;
import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * 字典类型表 sys_dict_type
 * 
 * @author tanghx
 */
@Setter
@Getter
@Table("sys_dict_type")
public class SysDictType extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 字典主键 */
	@Excel(name = "字典主键", cellType = ColumnType.NUMERIC)
	@Id("dict_id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long dictId;

	/** 字典名称 */
	@Excel(name = "字典名称")
	private String dictName;

	/** 字典类型 */
	@Excel(name = "字典类型")
	private String dictType;

	/** 状态（0正常 1停用） */
	@Excel(name = "状态", readConverterExp = "0=正常,1=停用")
	private Integer status;

	@NotBlank(message = "字典名称不能为空")
	@Size(min = 0, max = 100, message = "字典类型名称长度不能超过100个字符")
	public String getDictName() {
		return dictName;
	}

	@NotBlank(message = "字典类型不能为空")
	@Size(min = 0, max = 100, message = "字典类型类型长度不能超过100个字符")
	public String getDictType() {
		return dictType;
	}

}
