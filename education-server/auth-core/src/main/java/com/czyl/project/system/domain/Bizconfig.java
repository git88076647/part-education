package com.czyl.project.system.domain;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 业务参数配置对象 sys_bizconfig
 * 
 * @author tanghx
 * @date 2021-06-08
 */
@Setter
@Getter
@Table("sys_bizconfig")
public class Bizconfig extends BaseEntity{
    private static final long serialVersionUID = 1L;
    Long[] delIds;
    /** 值 */
    @Excel(name = "值")
    @NotBlank(message = "值不能为空")
    @Length(min=1,max = 200,message = "值长度只允许1~200位")
    private String value;

    /** 键 */
    @NotBlank(message = "键不能为空")
    @Length(min=1,max = 20,message = "键长度只允许1~20位")
    @Excel(name = "键")
    private String key;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 主键 */
    @Id("id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;



    //多选查询
   
}
