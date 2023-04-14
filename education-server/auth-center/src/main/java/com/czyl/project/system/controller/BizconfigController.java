package com.czyl.project.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.czyl.common.constant.CacheNameConstants;
import com.czyl.common.utils.poi.ExcelUtil;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.DataSource;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.system.domain.Bizconfig;
import com.czyl.project.system.service.IBizconfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 业务参数配置Controller
 * 
 * @author tanghx
 * @date 2021-06-08
 */
@RestController
@RequestMapping("/system/bizconfig")
public class BizconfigController extends BaseController{
    @Autowired
    private IBizconfigService service;

    /**
     * 查询业务参数配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:bizconfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(Bizconfig entity){
        startPage();
        List<Bizconfig> list = service.selectList(entity);
        return getDataTable(list);
    }

    /**
     * 导出业务参数配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:bizconfig:export')")
    @Log(title = "业务参数配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @DataSource("slave")
    public void export(Bizconfig entity){
        List<Bizconfig> list = service.selectList(entity);
        ExcelUtil<Bizconfig> util = new ExcelUtil<Bizconfig>(Bizconfig.class,2);
        util.exportExcel(list, "bizconfig");
    }

    /**
     * 获取业务参数配置详情信息
     */
    @PreAuthorize("@ss.hasPermi('system:bizconfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id){
        return AjaxResult.success(service.selectById(id));
    }

 /**
     * 获取业务参数配置详情信息
     */
    @GetMapping(value = "/value")
    @Cacheable(cacheNames = CacheNameConstants.BIZ_CONFIG,key= "#p0")
    public String getInfoByCode(@RequestParam("key") String key){
        return service.selectByKey(key);
    }

    /**
     * 新增业务参数配置
     */
    @PreAuthorize("@ss.hasPermi('system:bizconfig:add')")
    @Log(title = "业务参数配置", businessType = BusinessType.INSERT)
    @PostMapping
    @CacheEvict(cacheNames = CacheNameConstants.BIZ_CONFIG,key= "#p0.key")
    public AjaxResult add(@Validated @RequestBody Bizconfig entity){
        BeanUtil.trimStrFields(entity);
        return toAjax(service.insert(entity));
    }

    /**
     * 修改业务参数配置
     */
    @PreAuthorize("@ss.hasPermi('system:bizconfig:edit')")
    @Log(title = "业务参数配置", businessType = BusinessType.UPDATE)
    @PutMapping
    @CacheEvict(cacheNames = CacheNameConstants.BIZ_CONFIG,key= "#p0.key")
    public AjaxResult edit(@Validated @RequestBody Bizconfig entity){
        BeanUtil.trimStrFields(entity);
        return toAjax(service.update(entity));
    }

    /**
     * 删除业务参数配置
     */
    @PreAuthorize("@ss.hasPermi('system:bizconfig:remove')")
    @Log(title = "业务参数配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids){
        return toAjax(service.deleteByIds(ids));
    }

    /**
     * 删除业务参数配置
     */
    @PreAuthorize("@ss.hasPermi('system:bizconfig:remove')")
    @Log(title = "业务参数配置", businessType = BusinessType.DELETE)
    @PostMapping("/del")
    public AjaxResult remove(@RequestBody Bizconfig entity){
        return toAjax(service.deleteByIds(entity.getDelIds()));
    }
}
