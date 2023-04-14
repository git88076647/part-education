package com.czyl.project.integrate.domain;

import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.common.utils.security.LoginUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 应用注册对象 sys_appreg
 * 
 * @author tanghx
 * @date 2020-04-19
 */
@Setter
@Getter
@Table("sys_appreg")
public class SysAppreg extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id("appreg_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long appregId;

    /** 应用编码 */
    @Excel(name = "应用编码")
    private String code;

    /** 应用名称 */
    @Excel(name = "应用名称")
    private String name;

    /** 单点登录地址 */
    private String ssologinurl;

    /** 单点注册地址 */
    private String ssoregurl;

    /** 接口地址 */
    private String itfurl;

    /** 账户 */
    private String account;

    /** 密码 */
    private String password;

    /** 状态(0正常,1停用) */
    @Excel(name = "状态(0正常,1停用)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer status;

    /** 预留字段1 */
    private String def1;

    /** 预留字段2 */
    private String def2;

    /** 预留字段3 */
    private String def3;

    /** 预留字段4 */
    private String def4;

    /** 预留字段5 */
    private String def5;

    /** 删除标志（0代表存在 2代表删除） */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer dr;

    @JsonIgnore
    public String decodePassword() {
    	return LoginUtils.decrypt(this.getPassword());
    }
    
   
}
