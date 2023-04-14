package com.czyl.project.report.domain;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import java.util.List;

/**
 * 语义模型对象 rep_semantic
 *
 * @author tanghx
 * @date 2020-04-03
 */
@Setter
@Getter
@Table("rep_semantic")
public class RepSemantic extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 表id
     */
    @Id("pk_semantic")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long pkSemantic;

    /**
     * 编码
     */
    @Excel(name = "编码")
    private String code;

    /**
     * 名字
     */
    @Excel(name = "名字")
    private String name;

    /**
     * 数据源
     */
    @Excel(name = "数据源")
    private String datasource;

    /**
     * 前端格式化设计json对象
     */
    @Excel(name = "前端格式化设计json对象")
    private String filednamejson;

    /**
     * 前端格式化设计生成的html内容
     */
    @Excel(name = "前端格式化设计生成的html内容")
    private String formathtml;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String description;

    /**
     * 删除标志
     */
    private Integer dr;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer statue;

    /**
     * 数据查询实现类
     */
    @Excel(name = "数据查询实现类")
    private String querclass;

    /**
     * 查询语句
     */
    @Excel(name = "查询语句")
    private String scripttxt;

    /**
     * 查询变量参数列表
     */
    @Transient
    private transient List<RepSemparmar> repSemparmars;
}
