package com.czyl.project.report.domain;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * 报表模板_语义模型关联对象 rep_template_semantic
 *
 * @author tanghx
 * @date 2020-04-08
 */
@Setter
@Getter
@Table("rep_template_semantic")
public class RepTemplateSemantic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 表id */
	@Id("pk_template_semantic")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long pkTemplateSemantic;

    /** 报表模板 */
    @Excel(name = "报表模板")
    private String pkTemplate;

    /** 备注 */
    @Excel(name = "备注")
    private String description;

    /** 删除标志 */
    private Integer dr;

    /** 语义模型id */
    @Excel(name = "语义模型id")
    private String pkSemantic;

}
