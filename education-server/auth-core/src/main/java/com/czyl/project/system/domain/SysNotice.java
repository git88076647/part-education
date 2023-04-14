package com.czyl.project.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.czyl.common.annotation.table.Id;
import com.czyl.common.annotation.table.Table;
import com.czyl.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * 通知公告表 sys_notice
 * 
 * @author tanghx
 */
@Setter
@Getter
@Table("sys_notice")
public class SysNotice extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 公告ID */
	@Id("notice_id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long noticeId;

	/** 公告标题 */
	private String noticeTitle;

	/** 公告类型（1通知 2公告） */
	private String noticeType;

	/** 公告内容 */
	private String noticeContent;

	/** 公告状态（0正常 1关闭） */
	private Integer status;

	@NotBlank(message = "公告标题不能为空")
	@Size(min = 0, max = 50, message = "公告标题不能超过50个字符")
	public String getNoticeTitle() {
		return noticeTitle;
	}

//    @Override
//    public String toString() {
//        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
//            .append("noticeId", getNoticeId())
//            .append("noticeTitle", getNoticeTitle())
//            .append("noticeType", getNoticeType())
//            .append("noticeContent", getNoticeContent())
//            .append("status", getStatus())
//            .append("createBy", getCreateBy())
//            .append("createTime", getCreateTime())
//            .append("updateBy", getUpdateBy())
//            .append("updateTime", getUpdateTime())
//            .append("remark", getRemark())
//            .toString();
//    }
}
