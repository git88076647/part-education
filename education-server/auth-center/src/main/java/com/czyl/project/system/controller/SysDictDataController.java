package com.czyl.project.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.collection.ListUtil;
import com.czyl.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.czyl.common.constant.CacheNameConstants;
import com.czyl.common.utils.SecurityUtils;
import com.czyl.common.utils.poi.ExcelUtil;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.Lock4j;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.system.domain.SysDictData;
import com.czyl.project.system.service.ISysDictDataService;

/**
 * 数据字典信息
 *
 * @author tanghx
 */
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {
    @Autowired
    private ISysDictDataService dictDataService;

    @PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDictData dictData) {
        startPage();
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:dict:export')")
    @GetMapping("/export")
    public AjaxResult export(SysDictData dictData) {
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
        return util.exportExcel(list, "字典数据");
    }


    @GetMapping(value = "/dictLabel/{dictType}/{dictValue}")
    public AjaxResult dictLabel(@PathVariable String dictType, @PathVariable String dictValue) {
        return AjaxResult.success().setData(dictDataService.selectDictLabel(dictType, dictValue));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/dictType/{dictType}")
    public AjaxResult dictType(@PathVariable String dictType) {
        return AjaxResult.success(dictDataService.selectDictDataByType(dictType));
    }


    @GetMapping(value = "/dictLabels/info")
    public List<SysDictData> dictLabels(@RequestParam(value = "dictType") String dictType,@RequestParam(value = "dictValues") List<String> dictValues) {
        if (StringUtils.isBlank(dictType) || StringUtils.isEmpty(dictValues)) {
            return ListUtil.empty();
        }
        dictValues = dictValues.stream().distinct().collect(Collectors.toList());
        if (dictValues.size() > 499) {
            int len = dictValues.size();
            int pageNo = 0;
            int pageSize = 499;
            List<SysDictData> retList = new ArrayList<>();
            while (pageNo * pageSize < len) {
                List<String> vals = ListUtil.page(pageNo, pageSize, dictValues);
                retList.addAll(dictDataService.selectDictLabels(dictType, vals));
                pageNo++;
            }
            return retList;
        }
        return dictDataService.selectDictLabels(dictType, dictValues);
    }

    /**
     * 查询字典数据详细
     */
    @PreAuthorize("@ss.hasPermi('system:dict:query')")
    @GetMapping(value = "/{dictCode}")
    public AjaxResult getInfo(@PathVariable Long dictCode) {
        return AjaxResult.success(dictDataService.selectDictDataById(dictCode));
    }


    /**
     * 新增字典类型
     */
    @PreAuthorize("@ss.hasPermi('system:dict:add')")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    @Lock4j(keys = {"#dict.dictType", "#dict.dictLabel"}, autoPrefix = true)
    @CacheEvict(cacheNames = CacheNameConstants.DICT_LABEL, key = "#dict.dictType+#dict.dictValue")
    public AjaxResult add(@Validated @RequestBody SysDictData dict) {
        dict.setCreateBy(SecurityUtils.getUserId());
        return toAjax(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */
    @PreAuthorize("@ss.hasPermi('system:dict:edit')")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    @Lock4j(keys = {"#dict.dictCode"})
    @CacheEvict(cacheNames = CacheNameConstants.DICT_LABEL, key = "#dict.dictType+#dict.dictValue")
    public AjaxResult edit(@Validated @RequestBody SysDictData dict) {
        dict.setUpdateBy(SecurityUtils.getUserId());
        return toAjax(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictCodes}")
    public AjaxResult remove(@PathVariable Long[] dictCodes) {
        return toAjax(dictDataService.deleteDictDataByIds(dictCodes));
    }
}
