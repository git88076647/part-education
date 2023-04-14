package com.czyl.project.system.domain;

import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.web.domain.BaseEntity;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 人员任职信息对象 sys_psnjob
 * 
 * @author tanghx
 * @date 2020-04-26
 */
@Setter
@Getter
@Table("sys_psnjob")
public class SysPsnjob extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** 人员任职ID */
    @Id("psnjob_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long psnjobId;

    /** 人员 */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long psnId;

    /** 组织 */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orgId;

    /**
     * 组织名称
     */
    @Excel(name = "组织")
    private String orgIdName;
    
    /** 部门 */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long deptId;
    
    /** 部门名称 */
    @Excel(name = "部门")
    private String deptIdName;
    
    /** 岗位 */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long postId;

    /** 岗位名称 */
    @Excel(name = "岗位")
    private String postIdName;
    
    /** 生效日期 */
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effecttime;

    /** 失效日期 */
    @Excel(name = "失效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date invalidtime;

    /** 是否主职（Y是,N否） */
    @Excel(name = "是否主职", readConverterExp = "Y=是,N=否")
    private String mainjob;

    /** 任职状态（0生效 1失效） */
    @Excel(name = "任职状态", readConverterExp = "0=生效,1=失效")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer status;

    /** 删除标志（0代表存在 1代表删除） */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer dr;



   
}
