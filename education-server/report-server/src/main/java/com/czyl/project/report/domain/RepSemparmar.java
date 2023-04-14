package com.czyl.project.report.domain;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * 语义模型参数对象 rep_semparmar
 *
 * @author tanghx
 * @date 2020-04-02
 */
@Setter
@Getter
@Table("rep_semparmar")
public class RepSemparmar extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 表id
     */
    @Id("pk_semparmar")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long pkSemparmar;

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
     * 语义模型id
     */
    @Excel(name = "语义模型id")
    private String pkSemantic;

    /**
     * 参数文本，比如 如果是固定的字符参数啥的 就是值
     */
    @Excel(name = "参数文本，比如 如果是固定的字符参数啥的 就是值")
    private String valuestr;

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
     * 参数的java类型class全名
     */
    @Excel(name = "参数的java类型class全名")
    private String javatype;
}
