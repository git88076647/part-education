package com.czyl.project.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 角色和菜单关联 sys_role_menu
 * 
 * @author tanghx
 */
public class SysRoleMenu
{
    /** 角色ID */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long roleId;
    
    /** 菜单ID */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long menuId;

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public Long getMenuId()
    {
        return menuId;
    }

    public void setMenuId(Long menuId)
    {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roleId", getRoleId())
            .append("menuId", getMenuId())
            .toString();
    }
}
