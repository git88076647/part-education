package com.czyl.project.report.controller;

import com.czyl.common.meta.DataResultMeta;
import com.czyl.common.report.itf.IQueryResultDataVO;
import com.czyl.common.utils.AppContextUtils;
import com.czyl.common.utils.domain.BaseEntityUtil;
import com.czyl.common.utils.poi.ExcelUtil;
import com.czyl.common.utils.spring.SpringUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.report.domain.RepSemantic;
import com.czyl.project.report.domain.bo.QueryResultDataBO;
import com.czyl.project.report.service.IRepSemanticService;
import com.czyl.project.report.service.IRepSemparmarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 语义模型Controller
 *
 * @author tanghx
 * @date 2020-04-03
 */
@RestController
@RequestMapping("/rep/semantic")
public class RepSemanticController extends BaseController {
    @Autowired
    private IRepSemanticService repSemanticService;

    /**
     * 查询语义模型列表
     */
    @PreAuthorize("@ss.hasPermi('rep:semantic:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepSemantic repSemantic) {
        startPage();
        List<RepSemantic> list = repSemanticService.selectRepSemanticList(repSemantic);
        return getDataTable(list);
    }

    /**
     * 导出语义模型列表
     */
    @PreAuthorize("@ss.hasPermi('rep:semantic:export')")
    @Log(title = "语义模型", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RepSemantic repSemantic) {
        List<RepSemantic> list = repSemanticService.selectRepSemanticList(repSemantic);
        ExcelUtil<RepSemantic> util = new ExcelUtil<RepSemantic>(RepSemantic.class);
        return util.exportExcel(list, "semantic");
    }

    /**
     * 获取语义模型详情信息
     */
    @PreAuthorize("@ss.hasPermi('rep:semantic:query')")
    @GetMapping(value = "/{pkSemantic}")
    public AjaxResult getInfo(@PathVariable("pkSemantic") Long pkSemantic) {
        RepSemantic repSemantic = repSemanticService.selectRepSemanticById(pkSemantic);

        //查询参数列表
        if (repSemantic != null) {
            repSemantic.setRepSemparmars(SpringUtils.getBean(IRepSemparmarService.class)
                    .selectRepSemparmarBySemanticId(repSemantic.getPkSemantic()));
        }

        return AjaxResult.success(repSemantic);
    }

    /**
     * 新增语义模型
     */
    @PreAuthorize("@ss.hasPermi('rep:semantic:add')")
    @Log(title = "语义模型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepSemantic repSemantic) {
        BaseEntityUtil.fillForce(repSemantic, AppContextUtils.getUserName2()
                , AppContextUtils.getUserId(), AppContextUtils.getOrgId());

        return toAjax(repSemanticService.insertRepSemantic(repSemantic));
    }

    /**
     * 修改语义模型
     */
    @PreAuthorize("@ss.hasPermi('rep:semantic:edit')")
    @Log(title = "语义模型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepSemantic repSemantic) {
        BaseEntityUtil.fill(repSemantic
                , AppContextUtils.getUserId(), AppContextUtils.getOrgId());

        return toAjax(repSemanticService.updateRepSemantic(repSemantic));
    }

    /**
     * 删除语义模型
     */
    @PreAuthorize("@ss.hasPermi('rep:semantic:remove')")
    @Log(title = "语义模型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{pkSemantics}")
    public AjaxResult remove(@PathVariable Long[] pkSemantics) {
        return toAjax(repSemanticService.deleteRepSemanticByIds(pkSemantics));
    }

    /**
     * 执行语义模型查询
     */
    @PreAuthorize("@ss.hasPermi('rep:semantic:exec')")
    @PostMapping("/exec/{repSemanticId}")
    public AjaxResult exec(@PathVariable Long repSemanticId, @RequestBody(required = false)
            Map<Integer, Object> parmarMap) {
        RepSemantic repSemantic = repSemanticService.selectRepSemanticById(repSemanticId);
        if (repSemantic == null) {
            return AjaxResult.error("语义模型不存在!");
        }

        try {
            //防止SQL注入


            IQueryResultDataVO queryRe = repSemanticService.exec(repSemantic, parmarMap);
            QueryResultDataBO bo = new QueryResultDataBO();
            bo.setAllColumnNames(queryRe.getAllColumnNames());
            bo.setDatas(queryRe.getDataBO().getDatas());
            bo.setPageInfo(queryRe.getDataBO().getPageInfo());
            bo.setDataResultMeta(queryRe.getDataResultMeta());
            return AjaxResult.success(bo);
        } catch (Exception e) {
            return AjaxResult.error(e);
        }
    }

    /**
     * 获得 语义模型 查询结果的 字段列表和信息
     */
    @PreAuthorize("@ss.hasPermi('rep:semantic:list')")
    @GetMapping("/getresultmeta/{repSemanticId}")
    public AjaxResult getResultMeta(@PathVariable Long repSemanticId) {
        RepSemantic repSemantic = repSemanticService.selectRepSemanticById(repSemanticId);
        if (repSemantic == null) {
            return AjaxResult.error("语义模型不存在!");
        }

        try {
            DataResultMeta bo = repSemanticService.getResultMeta(repSemantic);
            return AjaxResult.success(bo);
        } catch (Exception e) {
            return AjaxResult.error(e);
        }
    }

    /**
     * 获得 语义模型 查询结果的 字段列表和信息(自定义sql脚本)
     */
    @PreAuthorize("@ss.hasPermi('rep:semantic:list')")
    @PostMapping("/getresultmeta/{repSemanticId}")
    public AjaxResult getResultMetaWithSql(@PathVariable Long repSemanticId, @RequestBody(required = false) String sql) {
        RepSemantic repSemantic = repSemanticService.selectRepSemanticById(repSemanticId);
        if (repSemantic == null) {
            return AjaxResult.error("语义模型不存在!");
        }

        try {
            repSemantic.setScripttxt(sql);
            DataResultMeta bo = repSemanticService.getResultMeta(repSemantic);
            return AjaxResult.success(bo);
        } catch (Exception e) {
            return AjaxResult.error(e.toString());
        }
    }

    /**
     * 获得 语义模型 查询结果的 字段列表和信息
     */
    @PreAuthorize("@ss.hasPermi('rep:semantic:list')")
    @PostMapping("/getresultmeta")
    public AjaxResult getResultMeta(@RequestBody List<Long> repSemanticIds) {
        if (repSemanticIds == null) {
            return AjaxResult.error("参数不能为空!");
        }
        HashMap<Object, Object> res = new HashMap<>(repSemanticIds.size());
        for (Long repSemanticId : repSemanticIds) {
            res.put(repSemanticId,  getResultMeta(repSemanticId));
        }

        return AjaxResult.success(res);
    }
}
