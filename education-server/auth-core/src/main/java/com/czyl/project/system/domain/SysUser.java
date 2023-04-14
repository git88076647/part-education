package com.czyl.project.system.domain;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.aspectj.lang.annotation.Excel.ColumnType;
import com.czyl.framework.aspectj.lang.annotation.Excels;
import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 * 
 * @author tanghx
 */
@Getter
@Setter
@Table("sys_user")
public class SysUser extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 查询角色名
	 */
	private String roleName;

	/** 用户ID */
	@Excel(name = "用户序号", cellType = ColumnType.NUMERIC, prompt = "用户编号")
	@Id("user_id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long userId;

	/** 组织ID */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long orgId;

	/** 用户账号 */
	@Excel(name = "登录帐号")
	private String userCode;

	/**
	 * 微信openid
	 */
	private String wechatopenid;

	/** 用户昵称 */
	@Excel(name = "用户名称")
	private String nickName;

	/** 用户邮箱 */
	@Excel(name = "用户邮箱")
	private String email;

	/** 用户类型  （1普通用户,2系统用户）*/
	private String userType;

	/** 人员 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long psnId;

	/** 手机号码 */
	@Excel(name = "手机号码")
	private String phonenumber;

	/** 用户性别 */
	@Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
	private String sex;

	/** 用户头像 */
	private String avatar;

	/** 密码 */
	private String password;

	/** 盐加密 */
	private String salt;

	/** 帐号状态（0正常 1停用） */
	@Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Integer status;

	/** 删除标志（0代表存在 1代表删除） */
	private Integer dr;

	/** 最后登陆IP */
	@Excel(name = "最后登陆IP")
	private String loginIp;

	/** 最后登陆时间 */
	@Excel(name = "最后登陆时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	private Date loginDate;

	/** 部门对象 */
	@Excels({ @Excel(name = "部门名称", targetAttr = "deptName"), @Excel(name = "部门负责人", targetAttr = "leader") })
	private SysDept dept;

	/** 组织对象 */
	@Excels({ @Excel(name = "组织名称", targetAttr = "orgName") })
	private SysOrg org;

	private SysPsndoc psndoc;

	/** 角色对象 */
	private List<SysRole> roles;

	/** 角色组 */
	private Long[] roleIds;
	
	/** 角色分配的组织 */
	private Long[] orgIds;

	/** 岗位组 */
	private Long[] postIds;

	public SysUser() {
	}

	public SysUser(Long userId) {
		this.userId = userId;
	}

	public boolean isAdmin() {
		return isAdmin(this.userId);
	}

	public static boolean isAdmin(Long userId) {
		return userId != null && (1L == userId || 2L == userId);
	}

	@Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
	public String getNickName() {
		return nickName;
	}

	@NotBlank(message = "用户账号不能为空")
	@Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
	public String getUserCode() {
		return userCode;
	}

	@Email(message = "邮箱格式不正确")
	@Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
	public String getEmail() {
		return email;
	}

	@Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
	public String getPhonenumber() {
		return phonenumber;
	}

	@NotBlank(message = "用户类型不能为空")
	public String getUserType() {
		return userType;
	}

}
