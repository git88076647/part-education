package com.czyl.project.system.domain;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * 弱口令管理对象 sys_weakpassword
 * 
 * @author tanghx
 * @date 2020-04-08
 */
@Setter
@Getter
@Table("sys_weakpassword")
public class SysWeakpassword extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
	@Id("pwd_id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long pwdId;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 状态(0正常 1停用) */
    @Excel(name = "状态(0正常 1停用)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer status;

    /** 删除标志(0存在 1删除) */
    private Integer dr;



   
}
