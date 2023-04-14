package com.czyl.project.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.czyl.project.system.domain.ResourceEntity;
import com.czyl.project.system.mapper.ResourceMapper;
import com.czyl.project.system.service.ISysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author hhhcccggg
 * @since 1.0.0 2022-02-14
 */
@Service

public class SysResourceServiceImpl implements ISysResourceService {


    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<Map<String, Object>> getList(String scopeCode, String resourceCode, String resourceName) {
        return resourceMapper.getList(StrUtil.isEmpty(scopeCode) ? scopeCode : "%" + scopeCode + "%",
                StrUtil.isEmpty(resourceCode) ? resourceCode : "%" + resourceCode + "%",
                StrUtil.isEmpty(resourceName) ? resourceName : "%" + resourceName + "%");
    }

    @Override
    public Integer count(Long id, String resourceCode) {
        return resourceMapper.count(id, resourceCode);
    }

    @Override
    public void save(ResourceEntity entity) {
        resourceMapper.insert(entity);
    }

    @Override
    public void updateById(ResourceEntity entity) {
        resourceMapper.updateById(entity);
    }

    @Override
    public ResourceEntity getOne(ResourceEntity entity) {
        return resourceMapper.getOne(entity);
    }
}
