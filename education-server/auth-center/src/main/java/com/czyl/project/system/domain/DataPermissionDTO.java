package com.czyl.project.system.domain;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;


/**
 * @author hhhcccggg
 * @Date 2022/3/15 9:49
 * @Description TODO
 **/
@Data
public class DataPermissionDTO {

    private Long id;
    /**
     * 资源编码
     */
    private Long resourceId;
    @NotEmpty(message = "资源编码不能为空", groups = Default.class)
    private String resourceCode;
//    @NotEmpty(message = "资源名称不能为空", groups = Default.class)
    private String resourceName;

    /**
     * 作用域编码
     */
    @NotEmpty(message = "作用域编码不能为空", groups = Default.class)
    private String scopeCode;

    /**
     * 规则 大于小于包含
     */
//    @NotNull(message = "规则不能为空", groups = Default.class)
    private Integer rule;
    /**
     * 授权主体  role 角色  user 默认用户
     */
    @NotEmpty(message = "授权主体不能为空", groups = Default.class)
    private String subjectType;

    /**
     * 授权主体翻译名称（角色名称）
     */
    private String subjectName;
    /**
     * 根据规则生成的sql
     */
    private String refSql;
    /**
     * 授权主体Id
     */
    @NotNull(message = "授权主体不能为空", groups = Default.class)
    private Long subjectId;

    /**
     * 权限数据，多条数据使用逗号隔开 0表示全选
     */
    private String permissionData;

    private String columnName;
    /**
     * 是否为虚拟资源( 来源于resourceCode)
     */
    private Boolean virtualResource;
}
