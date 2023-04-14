package com.czyl.project.system.domain;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import lombok.Data;

/**
 * @author hhhcccggg
 * @since 1.0.0 2022-02-14
 * 每一个基础档案都可以看做是一个资源
 * 各种费用字段视为虚拟资源 tableName为空 virtualResource为true
 */
@Data
@Table("sys_scope_resource")
public class SysScopeResource {
    @Id("id")
    private Long id;
    /**
     * 资源编码
     */
    private String resourceCode;


    private String scopeCode;

    private String name;

    private String columnName;
    private String permissionSql;

}
