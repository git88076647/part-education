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
 * 岗位表 sys_post
 * 
 * @author tanghx
 */
@Setter
@Getter
@Table("sys_post")
public class SysPost extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 岗位序号 */
	@Excel(name = "岗位序号", cellType = ColumnType.NUMERIC)
	@Id("post_id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long postId;

	/** 岗位编码 */
	@Excel(name = "岗位编码")
	private String postCode;

	/** 岗位名称 */
	@Excel(name = "岗位名称")
	private String postName;

	/** 岗位排序 */
	@Excel(name = "岗位排序")
	private String postSort;

	/** 状态（0正常 1停用） */
	@Excel(name = "状态", readConverterExp = "0=正常,1=停用")
	private Integer status;

	/** 用户是否存在此岗位标识 默认不存在 */
	private boolean flag = false;

	@NotBlank(message = "岗位编码不能为空")
	@Size(min = 0, max = 64, message = "岗位编码长度不能超过64个字符")
	public String getPostCode() {
		return postCode;
	}

	@NotBlank(message = "岗位名称不能为空")
	@Size(min = 0, max = 50, message = "岗位名称长度不能超过50个字符")
	public String getPostName() {
		return postName;
	}

	@NotBlank(message = "显示顺序不能为空")
	public String getPostSort() {
		return postSort;
	}

//    @Override
//    public String toString() {
//        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
//            .append("postId", getPostId())
//            .append("postCode", getPostCode())
//            .append("postName", getPostName())
//            .append("postSort", getPostSort())
//            .append("status", getStatus())
//            .append("createBy", getCreateBy())
//            .append("createTime", getCreateTime())
//            .append("updateBy", getUpdateBy())
//            .append("updateTime", getUpdateTime())
//            .append("remark", getRemark())
//            .toString();
//    }
}
