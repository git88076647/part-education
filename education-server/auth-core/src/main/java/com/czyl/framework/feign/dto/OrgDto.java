package com.czyl.framework.feign.dto;

import com.czyl.common.annotation.table.Id;
import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.json.LongZeroToNullSerializer;
import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 机构档案对象 kl_org
 *
 * @author tanghx
 * @date 2020-12-13
 */
@Setter
@Getter
public class OrgDto extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /**
     * 数据权限类型 （1终端 2商业）
     */
    private Integer permisionType;
    /**
     * 节点 （1发货 2流向 3政策）
     */
    private Integer dataNode;
    /**
     * 权限数据
     */
    private List<Long> permisionList;

    /** 医院等次 */
    @Excel(name = "医院等次")
    private String yydc;

    /** 医院级别 */
    @Excel(name = "医院级别")
    private String yyjb;

    /** 三级属性 */
    @Excel(name = "三级属性")
    @JsonSerialize(using = LongZeroToNullSerializer.class)
    private Long sjsy;
    private List<Long> sjsyIds;


    /** 二级属性 */
    @Excel(name = "二级属性")
    @JsonSerialize(using = LongZeroToNullSerializer.class)
    private Long ejsy;
    private List<Long> ejsyIds;

    /** 一级属性 */
    @Excel(name = "一级属性")
    @JsonSerialize(using = LongZeroToNullSerializer.class)
    private Long yjsy;
    private List<Long> yjsyIds;

    /** 区县 */
    @Excel(name = "区县")
    @JsonSerialize(using = LongZeroToNullSerializer.class)
    private Long areaId;

    /** 城市 */
    @Excel(name = "城市")
    @JsonSerialize(using = LongZeroToNullSerializer.class)
    private Long cityId;

    /** 省份 */
    @Excel(name = "省份")
    @JsonSerialize(using = LongZeroToNullSerializer.class)
    private Long provinceId;

    /** 机构类型 */
    @Excel(name = "机构类型")
    @JsonSerialize(using = LongZeroToNullSerializer.class)
    private Long jglxId;
    private List<Long> jglxIds;

    /** 别名 */
    @Excel(name = "别名")
    private String bname;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 编码 */
    @Excel(name = "编码")
    private String code;

    /** 机构档案主键 */
    @Id("id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;


    /** 状态（0正常 1停用）  */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer status;



    @Excel(name = "一级属性名称")
    private String          yjsyName;
    @Excel(name = "二级属性名称")
    private String          ejsyName;
    @Excel(name = "三级属性名称")
    private String          sjsyName;

    @Excel(name = "省份名称")
    private String          provinceIdName;
    @Excel(name = "省份编码")
    private String          provinceIdCode;
    @Excel(name = "城市名称")
    private String          cityIdName;
    @Excel(name = "城市编码")
    private String          cityIdCode;
    @Excel(name = "区县名称")
    private String          areaIdName;
    @Excel(name = "区县编码")
    private String          areaIdCode;
    @Excel(name = "机构类型名称")
    private String          jglxIdName;
    @Excel(name = "机构类型编码")
    private String          jglxIdCode;
    /** 状态名称 */
    @Excel(name = "状态名称")
    private String statusName;

    private List<Long> ids;

    private List<Long> delIds;









}
