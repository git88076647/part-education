package com.czyl.project.report.controller;

import com.czyl.common.utils.AppContextUtils;
import com.czyl.common.utils.domain.BaseEntityUtil;
import com.czyl.common.utils.poi.ExcelUtil;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.report.domain.RepTemplateSemantic;
import com.czyl.project.report.service.IRepTemplateSemanticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 报表模板_语义模型关联Controller
 *
 * @author tanghx
 * @date 2020-04-08
 */
@RestController
@RequestMapping("/rep/templatesemantic")
public class RepTemplateSemanticController extends BaseController {
    @Autowired
    private IRepTemplateSemanticService repTemplateSemanticService;

    /**
     * 查询报表模板_语义模型关联列表
     */
    @PreAuthorize("@ss.hasPermi('rep:templatesemantic:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepTemplateSemantic repTemplateSemantic) {
        startPage();
        List<RepTemplateSemantic> list = repTemplateSemanticService.selectRepTemplateSemanticList(repTemplateSemantic);
        return getDataTable(list);
    }

    /**
     * 导出报表模板_语义模型关联列表
     */
    @PreAuthorize("@ss.hasPermi('rep:templatesemantic:export')")
    @Log(title = "报表模板_语义模型关联", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepTemplateSemantic repTemplateSemantic) {
        List<RepTemplateSemantic> list = repTemplateSemanticService.selectRepTemplateSemanticList(repTemplateSemantic);
        ExcelUtil<RepTemplateSemantic> util = new ExcelUtil<RepTemplateSemantic>(RepTemplateSemantic.class);
        return util.exportExcel(list, "semantic");
    }

    /**
     * 获取报表模板_语义模型关联详情信息
     */
    @PreAuthorize("@ss.hasPermi('rep:templatesemantic:query')")
    @GetMapping(value = "/{pkTemplateSemantic}")
    public AjaxResult getInfo(@PathVariable("pkTemplateSemantic") Long pkTemplateSemantic) {
        return AjaxResult.success(repTemplateSemanticService.selectRepTemplateSemanticById(pkTemplateSemantic));
    }

    /**
     * 新增报表模板_语义模型关联
     */
    @PreAuthorize("@ss.hasPermi('rep:templatesemantic:add')")
    @Log(title = "报表模板_语义模型关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepTemplateSemantic repTemplateSemantic) {
        BaseEntityUtil.fillForce(repTemplateSemantic, AppContextUtils.getUserName2()
                , AppContextUtils.getUserId(), AppContextUtils.getOrgId());

        return toAjax(repTemplateSemanticService.insertRepTemplateSemantic(repTemplateSemantic));
    }

    /**
     * 修改报表模板_语义模型关联
     */
    @PreAuthorize("@ss.hasPermi('rep:templatesemantic:edit')")
    @Log(title = "报表模板_语义模型关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepTemplateSemantic repTemplateSemantic) {
        BaseEntityUtil.fill(repTemplateSemantic
                , AppContextUtils.getUserId(), AppContextUtils.getOrgId());

        return toAjax(repTemplateSemanticService.updateRepTemplateSemantic(repTemplateSemantic));
    }

    /**
     * 删除报表模板_语义模型关联
     */
    @PreAuthorize("@ss.hasPermi('rep:templatesemantic:remove')")
    @Log(title = "报表模板_语义模型关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{pkTemplateSemantics}")
    public AjaxResult remove(@PathVariable Long[] pkTemplateSemantics) {
        return toAjax(repTemplateSemanticService.deleteRepTemplateSemanticByIds(pkTemplateSemantics));
    }
}
