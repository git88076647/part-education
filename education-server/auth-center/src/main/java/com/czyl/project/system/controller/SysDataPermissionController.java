package com.czyl.project.system.controller;

import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.system.domain.DataPermissionDTO;
import com.czyl.project.system.domain.DataPermissionEntity;
import com.czyl.project.system.domain.SysScopeResource;
import com.czyl.project.system.mapper.SysScopeResourceMapper;
import com.czyl.project.system.service.ISysDataPermissionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hhhcccggg
 * @Date 2022/2/24 15:06
 * @Description TODO
 **/
@Api(tags = "资源权限相关")
@RestController
@RequestMapping("/system/dataPermission")
public class SysDataPermissionController extends BaseController {

    @Autowired
    private ISysDataPermissionService sysDataPermissionServiceImpl;

    @Autowired
    private SysScopeResourceMapper sysScopeResourceMapper;


    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermi('system:datapermission:list')")

    public TableDataInfo page(DataPermissionEntity entity) {
        startPage();
        List<DataPermissionDTO> list = sysDataPermissionServiceImpl.getPage(entity);
        return getDataTable(list);

    }

    @GetMapping("/getScopeResource")

    public AjaxResult getScopeResource() {
        Map<String, SysScopeResource> result = sysScopeResourceMapper.list().stream().collect(
                Collectors.toMap(o -> o.getScopeCode() + "@" + o.getResourceCode() + "@" + o.getColumnName(),
                        o -> o,
                        (a, b) -> a));
        return AjaxResult.success(result);
    }

    @GetMapping

    public AjaxResult getList(@RequestParam("scopeCode") String scopeCode,
                              @RequestParam("subjectType") String subjectType) {
        DataPermissionEntity search = new DataPermissionEntity();
        search.setScopeCode(scopeCode);
        search.setSubjectType(subjectType);
        List<DataPermissionEntity> list = sysDataPermissionServiceImpl.list(search);
        return AjaxResult.success(list);
    }

    @PostMapping
    @PreAuthorize("@ss.hasPermi('system:datapermission:add')")

    public AjaxResult save(@RequestBody @Validated DataPermissionDTO dto) {
        sysDataPermissionServiceImpl.createPermission(dto);
        return AjaxResult.success();
    }

    @PutMapping
    @PreAuthorize("@ss.hasPermi('system:datapermission:edit')")

    public AjaxResult update(@RequestBody @Validated DataPermissionDTO dto) {
        sysDataPermissionServiceImpl.updatePermission(dto);
        return AjaxResult.success();
    }

    /**
     * 查询资源数据
     *
     * @param resourceCode
     * @return
     */
    @GetMapping("/data")
    public AjaxResult data(@RequestParam("resourceCode") String resourceCode) {
        return AjaxResult.success(sysDataPermissionServiceImpl.getData(resourceCode));

    }

    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable("id") Long id) {
        return AjaxResult.success(sysDataPermissionServiceImpl.getById(id));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermi('system:datapermission:remove')")
    public AjaxResult delete(@PathVariable("id") Long id) {
        List list = new ArrayList();
        list.add(id);
        sysDataPermissionServiceImpl.remove(list);
        return AjaxResult.success();
    }

    @DeleteMapping()
    public AjaxResult deleteBatch(@RequestBody List<Long> ids) {

        sysDataPermissionServiceImpl.remove(ids);
        return AjaxResult.success();
    }


}
