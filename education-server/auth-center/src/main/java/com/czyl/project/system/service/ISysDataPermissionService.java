package com.czyl.project.system.service;

import com.czyl.project.system.domain.DataPermissionDTO;
import com.czyl.project.system.domain.DataPermissionEntity;

import java.util.List;
import java.util.Map;


/**
 * @author hhhcccggg
 * @since 1.0.0 2022-02-14
 */
public interface ISysDataPermissionService {


    List<DataPermissionDTO> getPage(DataPermissionEntity entity);


    void createPermission(DataPermissionDTO dto);


    void updatePermission(DataPermissionDTO dto);

    List<Map<String, Object>> getData(String resourceCode);

    List<DataPermissionEntity> list(DataPermissionEntity entity);

    void remove(List<Long> ids);

    DataPermissionEntity getById(Long id);

}
