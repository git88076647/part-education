package com.czyl.project.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 角色和部门关联 sys_role_dept
 * 
 * @author tanghx
 */
public class SysRoleDept
{
    /** 角色ID */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long roleId;
    
    /** 部门ID */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long deptId;

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roleId", getRoleId())
            .append("deptId", getDeptId())
            .toString();
    }
}
