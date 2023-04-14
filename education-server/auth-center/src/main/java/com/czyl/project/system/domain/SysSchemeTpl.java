package com.czyl.project.system.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 模版方案对象 sys_scheme_tpl
 * 
 * @author tanghx
 * @date 2021-07-21
 */
@Setter
@Getter
@Table("sys_scheme_tpl")
public class SysSchemeTpl implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 用户 */
    @Excel(name = "用户")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    /** 内容 */
    private String content;

    /** 方案名称 */
    @Excel(name = "方案名称")
    @NotBlank(message = "方案名称必填")
    private String name;

    /** 类型编码 */
    @Excel(name = "类型编码")
    @NotBlank(message = "类型编码必填")
    private String typecode;

    /** 主键 */
    @Id("id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;


    //多选查询
    /** 用户 */
    @JsonIgnore
    @JSONField(serialize = false)
    private List<Long> userIds;
   
}
