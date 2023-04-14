package com.czyl.project.system.domain;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 角色数据权限对象 sys_role_permission
 * 
 * @author fangxioaoh
 * @date 2021-12-06
 */
@Setter
@Getter
@Table("sys_role_permission")
public class SysUserPermission extends BaseEntity{
    private static final long serialVersionUID = 1L;
    List<Long> delIds;
    /** 权限数据id */
    @Excel(name = "权限数据id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long dataId;
    private String dataIdName;
    private List<Long> dataIds;

    /** 数据节点（1发货2流向3政策） */
    @Excel(name = "数据节点", readConverterExp = "1=发货2流向3政策")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer dataNode;
    private String dataNodeName;
    private List<Integer> dataNodes;

    /** 权限类型（1终端2商业） */
    @Excel(name = "权限类型", readConverterExp = "1=终端2商业")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer dataType;
    private String dataTypeName;
    private List<Integer> dataTypes;

    /** 角色ID */
    @Excel(name = "角色ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;
    private String userIdName;
    private List<Long> userIds;

    /** 费用明细主键 */
    @Id("id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private List<Long> ids;

}
