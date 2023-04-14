package com.czyl.project.system.domain;


import java.util.List;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * 人员管理对象 sys_psndoc
 * 
 * @author tanghx
 * @date 2019-12-11
 */
@Getter
@Setter
@Table("sys_psndoc")
public class SysPsndoc extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 人员ID */
	@Id("psn_id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long psnId;

	/** 组织ID */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long orgId;

	@Excel(name = "组织")
	private String orgName;

	/** 部门ID */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long deptId;

	@Excel(name = "部门")
	private String deptName;

	/** 兼职部门1 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long deptId1;

	@Excel(name = "兼职部门1")
	private String deptName1;

	/** 兼职部门2 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long deptId2;

	@Excel(name = "兼职部门2")
	private String deptName2;

	/** 兼职部门3 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long deptId3;

	@Excel(name = "兼职部门3")
	private String deptName3;

	/** 岗位 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long postId;

	@Excel(name = "岗位")
	private String postName;

	/** 人员工号 */
	@Excel(name = "人员工号")
	private String psnCode;

	/** 人员姓名 */
	@Excel(name = "人员姓名")
	private String psnName;

	/** 电子邮箱 */
	@Excel(name = "电子邮箱")
	private String email;

	/** 手机号码 */
	@Excel(name = "手机号码")
	private String phonenumber;

	/** 家庭电话 */
	@Excel(name = "家庭电话")
	private String homephone;

	/** 用户性别（0男 1女 2未知） */
	@Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
	private String sex;

	/** 头像地址 */
	private String avatar;

	/** 人员状态（0正常 1停用） */
	@Excel(name = "人员状态", readConverterExp = "0=正常,1=停用")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Integer status;

	/** 地址 */
	@Excel(name = "地址")
	private String address;

	/** 删除标志（0代表存在 1代表删除） */
	private Integer dr;
	
	List<SysPsnjob> psnJobs;

}
