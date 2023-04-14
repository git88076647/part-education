package com.czyl.project.domain;

import java.util.Date;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * 消息对象 t_msg
 * 
 * @author tanghx
 * @date 2020-05-26
 */
@Setter
@Getter
@Table("t_msg")
public class Msg extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @Id("id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String vmsg;
    private int vtype;

    private Date senddate;


   
}
