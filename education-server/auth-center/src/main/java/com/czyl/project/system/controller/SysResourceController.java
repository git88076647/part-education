package com.czyl.project.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.SecurityUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.system.domain.ResourceEntity;
import com.czyl.project.system.domain.SysResourceDTO;
import com.czyl.project.system.mapper.SysScopeResourceMapper;
import com.czyl.project.system.service.ISysResourceService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author hhhcccggg
 * @Date 2022/2/24 15:06
 * @Description TODO
 **/
@Api(tags = "资源权限相关")
@RestController
@RequestMapping("/system/resource")
public class SysResourceController extends BaseController {

    @Autowired
    private ISysResourceService sysResourceServiceImpl;

    @Autowired
    private SysScopeResourceMapper sysScopeResourceMapper;

    @GetMapping("/list")
    public AjaxResult getList(@RequestParam(value = "scopeCode", required = false) String scopeCode,
                              @RequestParam(value = "resourceCode", required = false) String resourceCode,
                              @RequestParam(value = "resourceName", required = false) String resourceName) {

        return AjaxResult.success(sysResourceServiceImpl.getList(scopeCode, resourceCode, resourceName));
    }

    @GetMapping("/page")
    public TableDataInfo page(@RequestParam(value = "scopeCode", required = false) String scopeCode,
                              @RequestParam(value = "resourceCode", required = false) String resourceCode,
                              @RequestParam(value = "resourceName", required = false) String resourceName) {
        startPage();
        return getDataTable(sysResourceServiceImpl.getList(scopeCode, resourceCode, resourceName));
    }

    @PostMapping
    public AjaxResult save(@RequestBody @Validated SysResourceDTO dto) {
        Integer count = sysResourceServiceImpl.count(null, dto.getResourceCode());
        if (count != null && count > 0) {
            throw new CustomException("资源编码重复！");
        }
        ResourceEntity entity = new ResourceEntity();
        BeanUtil.copyProperties(dto, entity);
        entity.setCreateBy(SecurityUtils.getUserId());
        entity.setCreateTime(new Date());
        sysResourceServiceImpl.save(entity);
        return AjaxResult.success();

    }

    @PutMapping
    public AjaxResult update(@RequestBody @Validated SysResourceDTO dto) {
        if (sysResourceServiceImpl.count(dto.getId(), dto.getResourceCode()) > 0) {
            throw new CustomException("资源编码重复！");
        }
        ResourceEntity entity = new ResourceEntity();
        BeanUtil.copyProperties(dto, entity);
        entity.setUpdateBy(SecurityUtils.getUserId());
        entity.setUpdateTime(new Date());
        sysResourceServiceImpl.updateById(entity);
        return AjaxResult.success();

    }

}
