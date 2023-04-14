package com.czyl.project.system.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.ServletUtils;
import com.czyl.common.utils.easyexcel.EasyExcelUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.DataSource;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.interceptor.annotation.RepeatSubmit;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.system.domain.SysUserPermission;
import com.czyl.project.system.service.ISysUserPermissionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户数据权限Controller
 * 
 * @author fangxioaoh
 * @date 2021-12-06
 */
@RestController
@RequestMapping("/system/permission")
public class SysUserPermissionController extends BaseController{
    @Autowired
    private ISysUserPermissionService service;

    /**
     * 查询用户数据权限列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUserPermission entity){
        startPage();
        List<SysUserPermission> list = service.selectList(entity);
        return getDataTable(list);
    }

    @GetMapping("/getUserPermision")
    public List<SysUserPermission> getUserPermision(@RequestParam(value = "userId") Long userId){
        if (userId == null || userId < 0){
            throw new CustomException("未获取到用户信息");
        }
        SysUserPermission entity = new SysUserPermission();
        entity.setUserId(userId);
        return service.selectList(entity);
    }

    @GetMapping("/ref")
    @DataSource("slave")
    public TableDataInfo ref(SysUserPermission entity){
        startPage();
        List<SysUserPermission> list = service.selectList(entity);
        if(list != null && !list.isEmpty()) {
            List<Map> rows = list.stream().map(o -> {
                return MapUtil.builder()
                        .put("id", o.getId().toString())
                        .put("dataType", o.getDataType().toString())
                        .put("dataNode", o.getDataNode().toString())
                        .put("dataId", o.getDataId().toString())
                        .build();
            }).collect(Collectors.toList());
            TableDataInfo dataTable = getDataTable(rows);
            dataTable.setTotal(new PageInfo(list).getTotal());
            return dataTable;
        }
        return getDataTable(ListUtil.empty());
    }




    /**
     * 导出用户数据权限列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @Log(title = "用户数据权限", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @DataSource("slave")
    @RepeatSubmit
    public void export(SysUserPermission entity) throws IOException {
        new EasyExcelUtils().export(ServletUtils.getResponse(), entity, "tpl_sys_user_permission", "用户权限数据" + System.currentTimeMillis() + ".xlsx", o -> {
            return service.selectList(entity,true);
        });
    }

    /**
     * 获取用户数据权限详情信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id){
        return AjaxResult.success(service.selectById(id));
    }

    /**
     * 新增用户数据权限
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户数据权限", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysUserPermission entity){
        return toAjax(service.insert(entity));
    }

    /**
     * 导入用户数据权限
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户数据权限", businessType = BusinessType.INSERT)
    @PostMapping("/import")
    public AjaxResult importData(@RequestBody SysUserPermission entity){
        return toAjax(service.importData(entity));
    }

    /**
     * 修改用户数据权限
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户数据权限", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysUserPermission entity){
        return toAjax(service.update(entity));
    }

    /**
     * 删除用户数据权限
     */
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "用户数据权限", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids){
        return toAjax(service.deleteByIds(ids));
    }

    /**
     * 删除用户数据权限
     */
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "用户数据权限", businessType = BusinessType.DELETE)
    @PostMapping("/del")
    public AjaxResult remove(@RequestBody SysUserPermission entity){
        return toAjax(service.deleteByIds(entity.getDelIds()));
    }
}
