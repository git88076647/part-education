package com.czyl.project.system.domain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * 组织表 sys_org
 * 
 * @author tanghx
 */
@Getter
@Setter
@Table("sys_org")
public class SysOrg extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 组织ID */
	@Id("org_id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long orgId;

	/** 组织编码 */
	private String orgCode;

	/** 组织名称 */
	private String orgName;

	/** 组织简称 */
	private String orgShortName;

	/** 上级组织ID */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long parentId;

	/** 上级组织名称 */
	private String parentName;

	/** 显示顺序 */
	private String orderNum;

	/** 负责人 */
	private String leader;

	/** 联系电话 */
	private String phone;

	/** 邮箱 */
	private String email;

	/** 部门状态:0正常,1停用 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Integer status;

	/** 删除标志（0代表存在 1代表删除） */
	private Integer dr;

	/** 统一社会信用代码 */
	private String creditCode;

	/** 地址 */
	private String address;

	/** 子部门 */
	private List<SysOrg> children = new ArrayList<SysOrg>();

	@NotBlank(message = "组织名称不能为空")
	@Size(min = 0, max = 100, message = "组织名称长度不能超过100个字符")
	public String getOrgName() {
		return orgName;
	}

	@NotBlank(message = "显示顺序不能为空")
	public String getOrderNum() {
		return orderNum;
	}

	@Size(min = 0, max = 11, message = "联系电话长度不能超过11个字符")
	public String getPhone() {
		return phone;
	}

	@Email(message = "邮箱格式不正确")
	@Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
	public String getEmail() {
		return email;
	}

}
