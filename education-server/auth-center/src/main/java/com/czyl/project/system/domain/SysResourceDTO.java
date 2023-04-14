package com.czyl.project.system.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * @author hhhcccggg
 * @Date 2022/3/15 9:22
 * @Description TODO
 **/

@Data
public class SysResourceDTO {

    private Long id;
    /**
     * 资源编码
     */
    @NotEmpty(message = "资源编码不能为空", groups = Default.class)
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
    @NotNull(message = "资源编码不能为空", groups = Default.class)
    private Boolean virtualResource;
}
