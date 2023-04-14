package com.czyl.framework.web.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

/**
 *  搜索
 * @author tanghx
 */
@ToString
@EqualsAndHashCode
@Setter
public class SearchBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date beginTime;
	
	private Date endTime;

	// 索引名
	private String searchType;
	
	
	private String searchValue;

	@NotNull(message = "开始日期不能为空")
	public Date getBeginTime() {
		return beginTime;
	}

	@NotNull(message = "结束日期不能为空")
	public Date getEndTime() {
		return endTime;
	}


	public String getSearchType() {
		return searchType;
	}

	@Size(min = 0, max = 150, message = "搜索内容不能超过150个字符")
	public String getSearchValue() {
		return searchValue;
	}


	
}
