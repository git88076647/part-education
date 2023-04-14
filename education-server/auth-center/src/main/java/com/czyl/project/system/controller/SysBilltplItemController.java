package com.czyl.project.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.czyl.common.constant.CacheNameConstants;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.utils.poi.ExcelUtil;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.redis.RedisCache;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.system.domain.SysBilltplItem;
import com.czyl.project.system.service.ISysBilltplItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 单据模板Controller
 * 
 * @author tanghx
 * @date 2020-11-28
 */
@RestController
@RequestMapping("/system/billtpl")
public class SysBilltplItemController extends BaseController {
	@Autowired
	private ISysBilltplItemService service;

	@Autowired
	RedisCache redisCache;

	/**
	 * 查询单据模板列表
	 */
	@PreAuthorize("@ss.hasPermi('system:billtpl:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysBilltplItem entity) {
		startPage();
		List<SysBilltplItem> list = service.selectList(entity);
		return getDataTable(list);
	}

	/**
	 * 
	 * @param billtplcode
	 * @return
	 */
	@GetMapping("/ref")
	public TableDataInfo ref(@Validated @NotNull(message = "billtplcode不能为空") String billtplcode) {
		List<SysBilltplItem> list = service.selectByBillTplCode(billtplcode);
		return getDataTable(list);
	}

	/**
	 * FeignClient
	 * @param billtplcode
	 * @return
	 */
	@GetMapping("/billitems")
	public List<SysBilltplItem> billitems(@Validated @NotNull(message = "billtplcode不能为空") String billtplcode) {
		return service.selectByBillTplCode(billtplcode);
	}

	/**
	 * 导出单据模板列表
	 */
	@PreAuthorize("@ss.hasPermi('system:billtpl:export')")
	@Log(title = "单据模板", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public AjaxResult export(SysBilltplItem entity) {
		List<SysBilltplItem> list = service.selectList(entity);
		ExcelUtil<SysBilltplItem> util = new ExcelUtil<SysBilltplItem>(SysBilltplItem.class);
		return util.exportExcel(list, "billtpl");
	}

	/**
	 * 获取单据模板详情信息
	 */
	@PreAuthorize("@ss.hasPermi('system:billtpl:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return AjaxResult.success(service.selectById(id));
	}

	/**
	 * 新增单据模板
	 */
	@PreAuthorize("@ss.hasPermi('system:billtpl:add')")
	@Log(title = "单据模板", businessType = BusinessType.INSERT)
	@PostMapping
	@CacheEvict(cacheNames = CacheNameConstants.BILL_ITEM,key= "#p0.billtplcode")
	public AjaxResult add(@RequestBody SysBilltplItem entity) {
		BeanUtil.trimStrFields(entity);
		return toAjax(service.insert(entity));
	}

	/**
	 * 修改单据模板
	 */
	@PreAuthorize("@ss.hasPermi('system:billtpl:edit')")
	@Log(title = "单据模板", businessType = BusinessType.UPDATE)
	@PutMapping
	@CacheEvict(cacheNames = CacheNameConstants.BILL_ITEM,key= "#p0.billtplcode")
	public AjaxResult edit(@RequestBody SysBilltplItem entity) {
		BeanUtil.trimStrFields(entity);
		return toAjax(service.update(entity));
	}

	/**
	 * 单据模板重排序
	 */
	@PreAuthorize("@ss.hasPermi('system:billtpl:edit')")
	@Log(title = "单据模板", businessType = BusinessType.UPDATE)
	@PutMapping("/reSort")
	public AjaxResult reSort(@RequestBody SysBilltplItem entity) {
		if(entity == null || StringUtils.isBlank(entity.getBilltplcode())){
			return AjaxResult.error("模板不能为空");
		}
		return toAjax(service.reSort(entity.getBilltplcode()));
	}

	/**
	 * 删除单据模板
	 */
	@PreAuthorize("@ss.hasPermi('system:billtpl:remove')")
	@Log(title = "单据模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		SysBilltplItem item = service.selectById(ids[0]);
		if(item != null){
			redisCache.cacheEvict(CacheNameConstants.BILL_ITEM,item.getBilltplcode());
		}
		return toAjax(service.deleteByIds(ids));
	}


}
