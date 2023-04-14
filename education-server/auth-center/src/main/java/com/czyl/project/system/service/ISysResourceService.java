package com.czyl.project.system.service;

import com.czyl.project.system.domain.ResourceEntity;

import java.util.List;
import java.util.Map;


/**
 * @author hhhcccggg
 * @since 1.0.0 2022-02-14
 */
public interface ISysResourceService {


    List<Map<String, Object>> getList(String scopeCode, String resourceCode, String resourceName);

    Integer count(Long id, String resourceCode);

    void save(ResourceEntity entity);

    void updateById(ResourceEntity entity);

    ResourceEntity getOne(ResourceEntity entity);

}
