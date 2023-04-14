package com.czyl.project.system.domain;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * @author hhhcccggg
 * @since 1.0.0 2022-02-14
 * 每一个基础档案都可以看做是一个资源
 * 各种费用字段视为虚拟资源 tableName为空 virtualResource为true
 */
@Data
@Table("sys_resource")
public class ResourceEntity extends BaseEntity {
    @Id("id")
    private Long id;
    /**
     * 资源编码
     */
    private String resourceCode;

    /**
     * 数据库表名
     */
    private String tableName;
    /**
     * 资源名称
     */
    private String resourceName;
    /**
     * 是否为虚拟资源( 默认false )
     */
    private Boolean virtualResource;

}
