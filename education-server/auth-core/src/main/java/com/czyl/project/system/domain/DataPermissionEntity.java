package com.czyl.project.system.domain;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * @author hhhcccggg
 * @since 1.0.0 2022-02-14
 */
@Data
@Table("sys_data_permission")
public class DataPermissionEntity extends BaseEntity {
    @Id("id")
    private Long id;
    /**
     * 资源编码
     */
    private Long resourceId;
    private String resourceCode;
    private String resourceName;
    /**
     * 作用域编码
     */
    private String scopeCode;

    /**
     * 规则 大于小于包含
     */
    private Integer rule;
    /**
     * 授权主体  role 角色  user 默认用户
     */
    private String subjectType;
    /**
     * 根据规则生成的sql
     */
    private String refSql;
    /**
     * 授权主体Id
     */
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
