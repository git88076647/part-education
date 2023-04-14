package com.czyl.project.log.controller;

import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.domain.SearchBO;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.log.service.ISearchService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ES通用存储与检索
 * 
 * @author tanghx
 */
@RestController
@RequestMapping("/log/search")
public class SearchController extends BaseController {
	
	@Autowired
	ISearchService searchService;

	
	@GetMapping("/list/{indexName}")
	public TableDataInfo list(@PathVariable String indexName,SearchBO search) {
		startPage();
		PageInfo<Map> page = searchService.strQuery(indexName,search);
		return getDataTable(page);
	}

	@PostMapping("/add/{indexName}")
	public AjaxResult save(@PathVariable String indexName,@RequestBody Map[] map) {
		searchService.save(indexName,map);
		return success();
	}


}
