package com.czyl.project.log.service;

import com.czyl.project.log.domain.SysBackLog;
import com.github.pagehelper.PageInfo;

/**
 * 后台日志  服务层
 * 
 * @author tanghx
 */
public interface ISysBackLogService {

	/**
	 * 查询ES
	 * @param begin 开始日期
	 * @param end 截止日期
	 * @param searchValue
	 * @return
	 */
	public PageInfo<SysBackLog> selectList(String begin,String end,String searchValue);

}
