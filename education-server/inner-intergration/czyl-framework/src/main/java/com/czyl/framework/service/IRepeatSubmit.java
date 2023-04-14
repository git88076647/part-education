package com.czyl.framework.service;

import javax.servlet.http.HttpServletRequest;

import com.czyl.common.web.domain.AjaxResult;

/**
 * 判断是否重复提交的实现
 * @author tanghx
 *
 */
public interface IRepeatSubmit {

	AjaxResult getRepeatSubmit(HttpServletRequest request,int intervalTime);
}
