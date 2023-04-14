package com.czyl.project.report.controller;

import com.czyl.common.utils.AppContextUtils;
import com.czyl.common.utils.domain.BaseEntityUtil;
import com.czyl.common.utils.poi.ExcelUtil;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.report.domain.RepSemparmar;
import com.czyl.project.report.service.IRepSemparmarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 语义模型参数Controller
 *
 * @author tanghx
 * @date 2020-04-02
 */
@RestController
@RequestMapping("/rep/semparmar")
public class RepSemparmarController extends BaseController {
    @Autowired
    private IRepSemparmarService repSemparmarService;

    /**
     * 查询语义模型参数列表
     */
    @PreAuthorize("@ss.hasPermi('rep:semparmar:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepSemparmar repSemparmar) {
        startPage();
        List<RepSemparmar> list = repSemparmarService.selectRepSemparmarList(repSemparmar);
        return getDataTable(list);
    }

    /**
     * 导出语义模型参数列表
     */
    @PreAuthorize("@ss.hasPermi('rep:semparmar:export')")
    @Log(title = "语义模型参数", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepSemparmar repSemparmar) {
        List<RepSemparmar> list = repSemparmarService.selectRepSemparmarList(repSemparmar);
        ExcelUtil<RepSemparmar> util = new ExcelUtil<RepSemparmar>(RepSemparmar.class);
        return util.exportExcel(list, "semparmar");
    }

    /**
     * 获取语义模型参数详情信息
     */
    @PreAuthorize("@ss.hasPermi('rep:semparmar:query')")
    @GetMapping(value = "/{pkSemparmar}")
    public AjaxResult getInfo(@PathVariable("pkSemparmar") Long pkSemparmar) {
        return AjaxResult.success(repSemparmarService.selectRepSemparmarById(pkSemparmar));
    }

    /**
     * 新增语义模型参数
     */
    @PreAuthorize("@ss.hasPermi('rep:semparmar:add')")
    @Log(title = "语义模型参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepSemparmar repSemparmar) {
        BaseEntityUtil.fillForce(repSemparmar, AppContextUtils.getUserName2()
                , AppContextUtils.getUserId(), AppContextUtils.getOrgId());

        return toAjax(repSemparmarService.insertRepSemparmar(repSemparmar));
    }

    /**
     * 修改语义模型参数
     */
    @PreAuthorize("@ss.hasPermi('rep:semparmar:edit')")
    @Log(title = "语义模型参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepSemparmar repSemparmar) {
        BaseEntityUtil.fill(repSemparmar
                , AppContextUtils.getUserId(), AppContextUtils.getOrgId());

        return toAjax(repSemparmarService.updateRepSemparmar(repSemparmar));
    }

    /**
     * 删除语义模型参数
     */
    @PreAuthorize("@ss.hasPermi('rep:semparmar:remove')")
    @Log(title = "语义模型参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{pkSemparmars}")
    public AjaxResult remove(@PathVariable Long[] pkSemparmars) {
        return toAjax(repSemparmarService.deleteRepSemparmarByIds(pkSemparmars));
    }
}
