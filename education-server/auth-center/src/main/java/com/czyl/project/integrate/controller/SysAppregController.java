package com.czyl.project.integrate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.czyl.common.constant.CacheNameConstants;
import com.czyl.common.utils.poi.ExcelUtil;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.Lock4j;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.integrate.domain.SysAppreg;
import com.czyl.project.integrate.service.ISysAppregService;

/**
 * 应用注册Controller
 * 
 * @author tanghx
 * @date 2020-04-19
 */
@RestController
@RequestMapping("/integrate/appreg")
public class SysAppregController extends BaseController{
    @Autowired
    private ISysAppregService sysAppregService;

    /**
     * 查询应用注册列表
     */
    @PreAuthorize("@ss.hasPermi('integrate:appreg:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysAppreg sysAppreg){
        startPage();
        List<SysAppreg> list = sysAppregService.selectList(sysAppreg);
        if(list != null && list.size() > 0) {
        	for (SysAppreg appreg : list) {
        		appreg.setPassword(null);
			}
        }
        return getDataTable(list);
    }

    /**
     * 导出应用注册列表
     */
    @PreAuthorize("@ss.hasPermi('integrate:appreg:export')")
    @Log(title = "应用注册", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysAppreg sysAppreg){
        List<SysAppreg> list = sysAppregService.selectList(sysAppreg);
        ExcelUtil<SysAppreg> util = new ExcelUtil<SysAppreg>(SysAppreg.class);
        return util.exportExcel(list, "appreg");
    }

    /**
     * 获取应用注册详情信息
     */
    @PreAuthorize("@ss.hasPermi('integrate:appreg:query')")
    @GetMapping(value = "/{appregId}")
    public AjaxResult getInfo(@PathVariable("appregId") Long appregId){
    	SysAppreg appreg = sysAppregService.selectById(appregId);
    	if(appreg != null) {
    		appreg.setPassword(null);
    	}
        return AjaxResult.success(appreg);
    }
    
    /**
     * 获取应用注册详情信息
     */
    @PreAuthorize("@ss.hasPermi('integrate:appreg:manage')")
    @GetMapping(value = "/getAppReg/{code}")
    public SysAppreg getAppReg(@PathVariable("code") String code){
        return sysAppregService.selectByCode(code);
    }

    /**
     * 新增应用注册
     */
    @PreAuthorize("@ss.hasPermi('integrate:appreg:add')")
    @Log(title = "应用注册", businessType = BusinessType.INSERT)
    @PostMapping
    @Lock4j(keys= {"#sysAppreg.code"},autoPrefix = true)
    @CacheEvict(cacheNames = CacheNameConstants.APP_REG,key="#sysAppreg.code")
    public AjaxResult add(@RequestBody SysAppreg sysAppreg){
    	SysAppreg dbappreg = sysAppregService.selectByCode(sysAppreg.getCode());
    	if(dbappreg != null) {
    		return AjaxResult.error("新增应用'" + sysAppreg.getCode() + "'失败，编码已存在");
    	}
        return toAjax(sysAppregService.insert(sysAppreg));
    }

    /**
     * 修改应用注册
     */
    @PreAuthorize("@ss.hasPermi('integrate:appreg:edit')")
    @Log(title = "应用注册", businessType = BusinessType.UPDATE)
    @PutMapping
    @Lock4j(keys = { "#sysAppreg.appregId" })
	@CacheEvict(cacheNames = CacheNameConstants.APP_REG,key="#sysAppreg.code")
    public AjaxResult edit(@RequestBody SysAppreg sysAppreg){
    	SysAppreg dbappreg = sysAppregService.selectByCode(sysAppreg.getCode());
    	if(dbappreg != null && dbappreg.getAppregId().longValue() != sysAppreg.getAppregId().longValue()) {
    		return AjaxResult.error("修改应用'" + sysAppreg.getCode() + "'失败，编码已存在");
    	}
        return toAjax(sysAppregService.update(sysAppreg));
    }

    /**
     * 删除应用注册
     */
    @PreAuthorize("@ss.hasPermi('integrate:appreg:remove')")
    @Log(title = "应用注册", businessType = BusinessType.DELETE)
    @DeleteMapping("/{appregIds}")
    public AjaxResult remove(@PathVariable Long[] appregIds){
        return toAjax(sysAppregService.deleteByIds(appregIds));
    }
}
