package com.czyl.project.system.domain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * 菜单权限表 sys_menu
 * 
 * @author tanghx
 */
@Setter
@Getter
@Table("sys_menu")
public class SysMenu extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 菜单ID */
	@Id("menu_id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long menuId;

	/** 菜单名称 */
	private String menuName;

	/** 父菜单名称 */
	private String parentName;

	/** 父菜单ID */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long parentId;

	/** 显示顺序 */
	private String orderNum;

	/** 路由地址 */
	private String path;

	/** 组件路径 */
	private String component;

	/** 是否为外链（0是 1否） */
	private String isFrame;

	/** 类型（M目录 C菜单 F按钮） */
	private String menuType;

	/** 菜单状态:0显示,1隐藏 */
	private String visible;

	/** 权限字符串 */
	private String perms;

	/** 菜单图标 */
	private String icon;

	/** 扩展URL */
	private String extUrl;

	/** 子菜单 */
	private List<SysMenu> children = new ArrayList<SysMenu>();

	@NotBlank(message = "菜单名称不能为空")
	@Size(min = 0, max = 50, message = "菜单名称长度不能超过50个字符")
	public String getMenuName() {
		return menuName;
	}

	@NotBlank(message = "显示顺序不能为空")
	public String getOrderNum() {
		return orderNum;
	}

	@Size(min = 0, max = 200, message = "路由地址不能超过200个字符")
	public String getPath() {
		return path;
	}

	@Size(min = 0, max = 200, message = "组件路径不能超过255个字符")
	public String getComponent() {
		return component;
	}

	@NotBlank(message = "菜单类型不能为空")
	public String getMenuType() {
		return menuType;
	}

	@Size(min = 0, max = 100, message = "权限标识长度不能超过100个字符")
	public String getPerms() {
		return perms;
	}

//    @Override
//    public String toString() {
//        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
//            .append("menuId", getMenuId())
//            .append("menuName", getMenuName())
//            .append("parentId", getParentId())
//            .append("orderNum", getOrderNum())
//            .append("path", getPath())
//            .append("component", getComponent())
//            .append("isFrame", getIsFrame())
//            .append("menuType", getMenuType())
//            .append("visible", getVisible())
//            .append("perms", getPerms())
//            .append("icon", getIcon())
//            .append("createBy", getCreateBy())
//            .append("createTime", getCreateTime())
//            .append("updateBy", getUpdateBy())
//            .append("updateTime", getUpdateTime())
//            .append("remark", getRemark())
//            .toString();
//    }
}
