package com.czyl.framework.web.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.czyl.common.annotation.table.Version;
import com.czyl.common.utils.StringUtils;
import com.czyl.framework.interceptor.annotation.FormatterToName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Entity基类
 *
 * @author tanghx
 */
@Slf4j
@Setter
@Getter
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 数据权限
     */
    private String scope;

    /**
     * 异步导出编码
     */
    private Integer exportCode;
    /**
     * 搜索值
     */
    private String searchValue;

    /**
     * 创建者ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long createBy;

    /**
     * 创建者名称
     */
    @FormatterToName(idField = "createBy", refIdField = "user_id", refName = "nick_name", refTable = "sys_user")
    private String createByName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long updateBy;

    /**
     * 更新人名称
     */
    @FormatterToName(idField = "updateBy", refIdField = "user_id", refName = "nick_name", refTable = "sys_user")
    private String updateByName;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Version
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long version;

    /**
     * 备注
     */
    private String remark;

    /**
     * 数据权限
     */
    private String dataScope;

    /**
     * 实体状态 EntityStatus
     */
    private Integer entityStatus;

    /**
     * 开始时间
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String beginTime;

    /**
     * 结束时间
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String endTime;

    /**
     * 组织id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orgId;

    /**
     * 查询条件日期转日期时间
     */
    public void date2dateTime() {
        if (StringUtils.isNotBlank(this.getEndTime()) && this.getBeginTime().trim().length() == 10) {
            this.setBeginTime(String.format("%s%s", this.getBeginTime().trim(), " 00:00:00"));
        }
        if (StringUtils.isNotBlank(this.getEndTime()) && this.getEndTime().trim().length() == 10) {
            this.setEndTime(String.format("%s%s", this.getEndTime().trim(), " 23:59:59"));
        }
    }

    /**
     * 请求参数
     */
    private Map<String, Object> params;

    @Override
    public String toString() {
//		return new ToStringBuilder(this,ToStringStyle.JSON_STYLE).toString();
//		return JSON.toJSONString(this);
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("jackson: Object to JsonString error! \n", e);
            return JSON.toJSONString(this);
        }
    }

}
