package com.czyl.project.log.controller;

import com.czyl.framework.web.domain.SearchBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.czyl.common.utils.DateUtils;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.log.domain.SysBackLog;
import com.czyl.project.log.service.ISysBackLogService;
import com.github.pagehelper.PageInfo;

/**
 * 操作日志记录
 * 
 * @author tanghx
 */
@RestController
@RequestMapping("/log/backlog")
public class SysBackLogController extends BaseController {
	
	@Autowired
	ISysBackLogService backLogService;

	
	@PreAuthorize("@ss.hasPermi('monitor:backlog:list')")
	@GetMapping("/list")
	public TableDataInfo list(@Validated SearchBO search) {
		startPage();
		
		PageInfo<SysBackLog> page = backLogService.selectList(DateUtils.dateTime(search.getBeginTime()), DateUtils.dateTime(search.getEndTime()), search.getSearchValue());
		
		return getDataTable(page);
	}

	
	
}
