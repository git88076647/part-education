package com.czyl.project.report.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 报表模板对象 rep_template
 *
 * @author tanghx
 * @date 2020-04-08
 */
@Setter
@Getter
@Table("rep_template")
public class RepTemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 表id */
	@Id("pk_template")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long pkTemplate;

    /** 编码 */
    @Excel(name = "编码")
    private String code;

    /** 名字 */
    @Excel(name = "名字")
    private String name;

    /** 格式设计json */
    @Excel(name = "格式设计json")
    private String formatjson;

    /** 格式生成html */
    @Excel(name = "格式生成html")
    private String formathtml;

    /** 备注 */
    @Excel(name = "备注")
    private String description;

    /** 删除标志 */
    private Integer dr;

    /** 状态 */
    @Excel(name = "状态")
    private Integer statue;

}
