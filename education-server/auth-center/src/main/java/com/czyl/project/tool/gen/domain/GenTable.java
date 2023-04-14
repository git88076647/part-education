package com.czyl.project.tool.gen.domain;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.common.constant.GenConstants;
import com.czyl.common.utils.StringUtils;
import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务表 gen_table
 * 
 * @author tanghx
 */
@Setter
@Getter
@Table("gen_table")
public class GenTable extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 编号 */
	@Id("table_id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long tableId;

	/** 表名称 */
	@NotBlank(message = "表名称不能为空")
	private String tableName;

	/** 表描述 */
	@NotBlank(message = "表描述不能为空")
	private String tableComment;

	/** 实体类名称(首字母大写) */
	@NotBlank(message = "实体类名称不能为空")
	private String className;

	/** 使用的模板（crud单表操作 tree树表操作） */
	private String tplCategory;

	/** 生成包路径 */
	@NotBlank(message = "生成包路径不能为空")
	private String packageName;

	/** 生成模块名 */
	@NotBlank(message = "生成模块名不能为空")
	private String moduleName;
	
	/** 网关 */
	@NotBlank(message = "网关不能为空")
	private String gateway;

	/** 生成业务名 */
	@NotBlank(message = "生成业务名不能为空")
	private String businessName;

	/** 生成功能名 */
	@NotBlank(message = "生成功能名不能为空")
	private String functionName;

	/** 生成作者 */
	@NotBlank(message = "作者不能为空")
	private String functionAuthor;

	/** 主键信息 */
	private GenTableColumn pkColumn;

	/** 表列信息 */
	@Valid
	private List<GenTableColumn> columns;

	/** 其它生成选项 */
	private String options;

	/** 树编码字段 */
	private String treeCode;

	/** 树父编码字段 */
	private String treeParentCode;

	/** 树名称字段 */
	private String treeName;


	public boolean isTree() {
		return isTree(this.tplCategory);
	}

	public static boolean isTree(String tplCategory) {
		return tplCategory != null && StringUtils.equals(GenConstants.TPL_TREE, tplCategory);
	}

	public boolean isCrud() {
		return isCrud(this.tplCategory);
	}

	public static boolean isCrud(String tplCategory) {
		return tplCategory != null && StringUtils.equals(GenConstants.TPL_CRUD, tplCategory);
	}

	public boolean isSuperColumn(String javaField) {
		return isSuperColumn(this.tplCategory, javaField);
	}

	public static boolean isSuperColumn(String tplCategory, String javaField) {
		if (isTree(tplCategory)) {
			StringUtils.equalsAnyIgnoreCase(javaField, GenConstants.TREE_ENTITY);
		}
		return StringUtils.equalsAnyIgnoreCase(javaField, GenConstants.BASE_ENTITY);
	}

}