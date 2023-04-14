package com.czyl.project.report.controller;

import com.czyl.common.utils.AppContextUtils;
import com.czyl.common.utils.domain.BaseEntityUtil;
import com.czyl.common.utils.poi.ExcelUtil;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.report.domain.RepTemplate;
import com.czyl.project.report.service.IRepTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 报表模板Controller
 *
 * @author tanghx
 * @date 2020-04-08
 */
@RestController
@RequestMapping("/rep/template")
public class RepTemplateController extends BaseController {
    @Autowired
    private IRepTemplateService repTemplateService;

    /**
     * 查询报表模板列表
     */
    @PreAuthorize("@ss.hasPermi('rep:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepTemplate repTemplate) {
        startPage();
        List<RepTemplate> list = repTemplateService.selectRepTemplateList(repTemplate);
        return getDataTable(list);
    }

    /**
     * 导出报表模板列表
     */
    @PreAuthorize("@ss.hasPermi('rep:template:export')")
    @Log(title = "报表模板", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepTemplate repTemplate) {
        List<RepTemplate> list = repTemplateService.selectRepTemplateList(repTemplate);
        ExcelUtil<RepTemplate> util = new ExcelUtil<RepTemplate>(RepTemplate.class);
        return util.exportExcel(list, "template");
    }

    /**
     * 获取报表模板详情信息
     */
    @PreAuthorize("@ss.hasPermi('rep:template:query')")
    @GetMapping(value = "/{pkTemplate}")
    public AjaxResult getInfo(@PathVariable("pkTemplate") Long pkTemplate) {
        return AjaxResult.success(repTemplateService.selectRepTemplateById(pkTemplate));
    }

    /**
     * 新增报表模板
     */
    @PreAuthorize("@ss.hasPermi('rep:template:add')")
    @Log(title = "报表模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepTemplate repTemplate) {
        BaseEntityUtil.fillForce(repTemplate, AppContextUtils.getUserName2()
                , AppContextUtils.getUserId(), AppContextUtils.getOrgId());

        return toAjax(repTemplateService.insertRepTemplate(repTemplate));
    }

    /**
     * 修改报表模板
     */
    @PreAuthorize("@ss.hasPermi('rep:template:edit')")
    @Log(title = "报表模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepTemplate repTemplate) {
        BaseEntityUtil.fill(repTemplate
                , AppContextUtils.getUserId(), AppContextUtils.getOrgId());

        return toAjax(repTemplateService.updateRepTemplate(repTemplate));
    }

    /**
     * 删除报表模板
     */
    @PreAuthorize("@ss.hasPermi('rep:template:remove')")
    @Log(title = "报表模板", businessType = BusinessType.DELETE)
    @DeleteMapping("/{pkTemplates}")
    public AjaxResult remove(@PathVariable Long[] pkTemplates) {
        return toAjax(repTemplateService.deleteRepTemplateByIds(pkTemplates));
    }
}
