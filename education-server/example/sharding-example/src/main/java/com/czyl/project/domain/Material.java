package com.czyl.project.domain;

import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 物料对象 t_material
 * 
 * @author tanghx
 * @date 2020-05-26
 */
@Setter
@Getter
@Table("t_material")
public class Material extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @Id("id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String name;



   
}
