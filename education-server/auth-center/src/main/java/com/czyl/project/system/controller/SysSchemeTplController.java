package com.czyl.project.system.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.czyl.common.utils.AppContextUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.system.domain.SysSchemeTpl;
import com.czyl.project.system.service.ISysSchemeTplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模版方案Controller
 * 
 * @author tanghx
 * @date 2021-07-21
 */
@RestController
@RequestMapping("/system/schemetpl")
public class SysSchemeTplController extends BaseController{
    @Autowired
    private ISysSchemeTplService service;

    /**
     * 查询模版方案列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysSchemeTpl entity){
        if(StrUtil.isBlank(entity.getTypecode())){
            return getDataTable(ListUtil.empty());
        }
        startPage();
        //查询用户=0与当前登录用户的模板
        entity.setUserIds(ListUtil.toList(0L,AppContextUtils.getUserId()));
        List<SysSchemeTpl> list = service.selectList(entity);
        return getDataTable(list);
    }

    /**
     * 获取模版方案详情信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id){
        return AjaxResult.success(service.selectById(id));
    }

    /**
     * 新增模版方案
     */
    @Log(title = "模版方案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysSchemeTpl entity){
        entity.setUserId(AppContextUtils.getUserId());
        service.insert(entity);
        return AjaxResult.success(entity);
    }

    /**
     * 删除模版方案
     */
    @Log(title = "模版方案", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/{id}")
    public AjaxResult remove(@PathVariable("id") Long id){
        SysSchemeTpl dbEntity = service.selectById(id);
        if(!AppContextUtils.getUserId().equals(dbEntity.getUserId())){
            return AjaxResult.error("不能删除非本人数据");
        }
        return toAjax(service.deleteById(id));
    }
}
