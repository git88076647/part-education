package com.czyl.project.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户和岗位关联 sys_user_post
 * 
 * @author tanghx
 */
public class SysUserPost
{
    /** 用户ID */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;
    
    /** 岗位ID */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long postId;

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getPostId()
    {
        return postId;
    }

    public void setPostId(Long postId)
    {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("postId", getPostId())
            .toString();
    }
}
