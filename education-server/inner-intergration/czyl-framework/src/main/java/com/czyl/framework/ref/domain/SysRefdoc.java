package com.czyl.framework.ref.domain;

import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 引用对象 sys_refdoc
 * 
 * @author tanghx
 * @date 2021-01-11
 */
@Setter
@Getter
@Table("sys_refdoc")
public class SysRefdoc extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** 提示信息 */
    @Excel(name = "提示信息")
    private String msg;

    /** 引用字段 */
    @Excel(name = "引用字段")
    private String refField;

    /** 引用表 */
    @Excel(name = "引用表")
    private String refTable;

    /** 来源字段 */
    @Excel(name = "来源字段")
    private String srcField;

    /**
     * 来源字段值
     */
    private Object srcValue;

    /** 来源表 */
    @Excel(name = "来源表")
    private String srcTable;

    /** 主键 */
    @Excel(name = "主键")
    @Id("id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer id;



   
}
