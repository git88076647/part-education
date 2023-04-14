package com.czyl.project.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 角色和组织关联 sys_role_org
 * 
 * @author tanghx
 */
public class SysRoleOrg
{
    /** 角色ID */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long roleId;
    
    /** 组织ID */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orgId;

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public Long getOrgId()
    {
        return orgId;
    }

    public void setOrgId(Long orgId)
    {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roleId", getRoleId())
            .append("orgId", getOrgId())
            .toString();
    }
}
