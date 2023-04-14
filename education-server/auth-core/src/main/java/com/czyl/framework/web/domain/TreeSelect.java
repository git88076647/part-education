package com.czyl.framework.web.domain;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.czyl.project.system.domain.SysDept;
import com.czyl.project.system.domain.SysMenu;
import com.czyl.project.system.domain.SysOrg;

import lombok.Getter;
import lombok.Setter;

/**
 * Treeselect树结构实体类
 * 
 * @author tanghx
 */
@Setter
@Getter
public class TreeSelect implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 节点ID */
	private String id;

	/** value值 */
	private String value;
	
	/** 节点名称 */
	private String label;
	
	/** badge*/
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Integer badge;

	/** 子节点 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<TreeSelect> children;

	public TreeSelect() {

	}
	
	public TreeSelect(String id,String label,List<TreeSelect> children,Integer badge) {
		this.id=id;
		this.value=id;
		this.label=label;
		this.children=children;
		this.badge=badge;
	}

	public TreeSelect(SysDept dept) {
		this.id = dept.getDeptId().toString();
		this.value = dept.getDeptId().toString();
		this.label = dept.getDeptName();
		this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
	}

	public TreeSelect(SysMenu menu) {
		this.id = menu.getMenuId().toString();
		this.value = menu.getMenuId().toString();
		this.label = menu.getMenuName();
		this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
	}

	public TreeSelect(SysOrg menu) {
		this.id = menu.getOrgId().toString();
		this.value = menu.getOrgId().toString();
		this.label = menu.getOrgCode() + " " + menu.getOrgName();
		this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
	}

}
