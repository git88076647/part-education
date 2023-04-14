package com.czyl.project.system.domain;

import java.util.Date;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.aspectj.lang.annotation.Excel;
import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * 文件管理对象 sys_file
 * 
 * @author tanghx
 * @date 2020-01-15
 */
@Setter
@Getter
@Table("sys_file")
public class SysFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文件编码 */
    @Id("file_id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long fileId;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String name;

    /** 文件类型 */
    @Excel(name = "文件类型")
    private String contenttype;

    /** 是否图片 */
    @Excel(name = "是否图片" ,readConverterExp="Y=是,N=否")
    private String isimg;

    /** 文件大小 */
    @Excel(name = "文件大小")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long size;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String path;

    /** OSS地址 */
    @Excel(name = "OSS地址")
    private String url;

    /** 存储源 */
    @Excel(name = "存储源")
    private String source;

    /** 创建者编码 */
    private String createByCode;

    /** 创建者名称 */
    @Excel(name = "创建者名称")
    private String createByName;

    /** 来源ID */
    @Excel(name = "来源ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long srcId;

    /** 来源类型 */
    @Excel(name = "来源类型")
    private String srcType;

    /** 文件失效时间 */
    private Date expireTime;
   

  
}
