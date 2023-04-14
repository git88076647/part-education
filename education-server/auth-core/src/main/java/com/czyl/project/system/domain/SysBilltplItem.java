package com.czyl.project.system.domain;

import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 单据模板对象 sys_billtpl_item
 * 
 * @author tanghx
 * @date 2020-11-28
 */
@Setter
@Getter
@Table("sys_billtpl_item")
public class SysBilltplItem extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sortindex;

    /** 卡片显示 */
    @Excel(name = "卡片显示")
    private Boolean showCard ;

    /** 列表显示 */
    @Excel(name = "列表显示")
    private Boolean showList ;

    /** 必填 */
    @Excel(name = "必填")
    private Boolean notNull ;

    /** 查询条件 */
    @Excel(name = "查询条件")
    private Boolean query ;

    /** 查询条件 */
    @Excel(name = "默认查询条件")
    private Boolean defaultQuery ;

    /** 虚字段 */
    @Excel(name = "虚字段")
    private Boolean virtual ;

    /** 导出字段 */
    @Excel(name = "导出字段")
    private Boolean export ;

    /** 规则 */
    @Excel(name = "规则")
    private String rules;

    /** 参照json */
    @Excel(name = "参照json")
    private String refjson;

    /** 列模板 */
    @Excel(name = "列模板")
    private String tpl;

    /** 格式化内容 */
    @Excel(name = "格式化")
    private String formatter;

    /** 显示tooltip(0=不显示,1=显示) */
    @Excel(name = "显示tooltip(0=不显示,1=显示)")
    private Boolean tooltip ;

    /** 最大宽 */
    @Excel(name = "最大宽")
    private String maxWidth;

    /** 最小宽 */
    @Excel(name = "最小宽")
    private String minWidth;

    /** 宽 */
    @Excel(name = "宽")
    private String width;

    /** 列固定(true,left,right) */
    @Excel(name = "列固定(true,left,right)")
    private String fixed;

    /** 字段 */
    @Excel(name = "字段")
    private String prop;

    /** 对齐方式(left,center,right) */
    @Excel(name = "对齐方式(left,center,right)")
    private String align;

    /** 显示标题 */
    @Excel(name = "显示标题")
    private String label;

    /** 类型(text,date,textSearch) */
    @Excel(name = "类型(text,date,textSearch)")
    private String type;

    /** 单据模板编码 */
    @Excel(name = "单据模板编码")
    private String billtplcode;

    /** 主键 */
    @Id("id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;



   
}
