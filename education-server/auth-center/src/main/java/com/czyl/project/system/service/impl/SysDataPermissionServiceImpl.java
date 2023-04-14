package com.czyl.project.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.czyl.common.constant.Constants;
import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.SecurityUtils;
import com.czyl.framework.SQLGenerator;
import com.czyl.framework.SQLGeneratorFactory;
import com.czyl.project.system.domain.*;
import com.czyl.project.system.mapper.DataPermissionMapper;
import com.czyl.project.system.mapper.SysScopeResourceMapper;
import com.czyl.project.system.service.ISysDataPermissionService;
import com.czyl.project.system.service.ISysResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author hhhcccggg
 * @since 1.0.0 2022-02-14
 */
@Service
@Slf4j
public class SysDataPermissionServiceImpl implements ISysDataPermissionService {


    @Resource
    private ISysResourceService sysResourceServiceImpl;
    @Resource
    private SysRoleServiceImpl sysRoleServiceImpl;
    @Resource
    private SysScopeResourceMapper sysScopeResourceMapper;

    @Resource
    private DataPermissionMapper dataPermissionMapper;

    @Override
    public List<DataPermissionDTO> getPage(DataPermissionEntity entity) {
        List<DataPermissionDTO> list = dataPermissionMapper.getPage(entity);
        return list;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPermission(DataPermissionDTO dto) {
        ResourceEntity searchResource = new ResourceEntity();
        searchResource.setResourceCode(dto.getResourceCode());
        ResourceEntity resourceEntity = sysResourceServiceImpl.getOne(searchResource);
        if (resourceEntity == null) {
            throw new CustomException("非法资源编码！");
        }

        if (Constants.DataPermissionSubject.valueOf(dto.getSubjectType().toUpperCase()) == null) {
            throw new CustomException("非法授权主体！");
        }
        SysRole sysRole = sysRoleServiceImpl.selectRoleById(dto.getSubjectId());
        if (sysRole == null) {
            throw new CustomException("非法角色！");
        }
        SysScopeResource scopeResourceSearch = new SysScopeResource();
        scopeResourceSearch.setId(dto.getResourceId());
        SysScopeResource sysScopeResource = sysScopeResourceMapper.getOne(scopeResourceSearch);

        DataPermissionEntity dataPermissionEntitySearch = new DataPermissionEntity();
        dataPermissionEntitySearch.setResourceCode(dto.getResourceCode());
        dataPermissionEntitySearch.setScopeCode(dto.getScopeCode());
        dataPermissionEntitySearch.setColumnName(sysScopeResource.getColumnName());
        dataPermissionEntitySearch.setSubjectId(dto.getSubjectId());

        Integer count = dataPermissionMapper.count(dataPermissionEntitySearch);
        if (count != null && count > 0) {
            throw new CustomException("该资源与作用域下已存在数据权限规则，无法重复添加！");
        }
        dto.setPermissionData(StrUtil.isNotEmpty(dto.getPermissionData()) && dto.getPermissionData().endsWith(",") ?
                dto.getPermissionData().substring(0, dto.getPermissionData().lastIndexOf(",")) : dto.getPermissionData());
        DataPermissionEntity permissionEntity = new DataPermissionEntity();
        BeanUtil.copyProperties(dto, permissionEntity);
        permissionEntity.setColumnName(sysScopeResource.getColumnName());
        if (!dto.getVirtualResource()) {
            SQLGenerator sqlGenerator = SQLGeneratorFactory.getInstance(dto.getRule());
            if (sqlGenerator == null) {
                throw new CustomException("非法规则！");
            }
            permissionEntity.setRefSql(sqlGenerator.execute(dto.getPermissionData()));
        }
        permissionEntity.setCreateBy(SecurityUtils.getUserId());
        permissionEntity.setCreateTime(new Date());
        dataPermissionMapper.insert(permissionEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePermission(DataPermissionDTO dto) {
        ResourceEntity searchResource = new ResourceEntity();
        searchResource.setResourceCode(dto.getResourceCode());
        ResourceEntity resourceEntity = sysResourceServiceImpl.getOne(searchResource);
        if (resourceEntity == null) {
            throw new CustomException("非法资源编码！");
        }
        if (Constants.DataPermissionSubject.valueOf(dto.getSubjectType().toUpperCase()) == null) {
            throw new CustomException("非法授权主体！");
        }
        SysRole sysRole = sysRoleServiceImpl.selectRoleById(dto.getSubjectId());
        if (sysRole == null) {
            throw new CustomException("非法角色！");
        }
        SysScopeResource scopeResourceSearch = new SysScopeResource();
        scopeResourceSearch.setId(dto.getResourceId());
        SysScopeResource sysScopeResource = sysScopeResourceMapper.getOne(scopeResourceSearch);

        DataPermissionEntity dataPermissionEntitySearch = new DataPermissionEntity();
        dataPermissionEntitySearch.setResourceCode(dto.getResourceCode());
        dataPermissionEntitySearch.setScopeCode(dto.getScopeCode());
        dataPermissionEntitySearch.setColumnName(sysScopeResource.getColumnName());
        dataPermissionEntitySearch.setSubjectId(dto.getSubjectId());
        dataPermissionEntitySearch.setId(dto.getId());

        Integer count = dataPermissionMapper.count(dataPermissionEntitySearch);
        if (count != null && count > 0) {
            throw new CustomException("该资源与作用域下已存在数据权限规则，无法重复添加！");
        }
        dto.setPermissionData(StrUtil.isNotEmpty(dto.getPermissionData()) && dto.getPermissionData().endsWith(",") ?
                dto.getPermissionData().substring(0, dto.getPermissionData().lastIndexOf(",")) : dto.getPermissionData());

        DataPermissionEntity permissionEntity = new DataPermissionEntity();
        BeanUtil.copyProperties(dto, permissionEntity);
        if (!dto.getVirtualResource()) {
            SQLGenerator sqlGenerator = SQLGeneratorFactory.getInstance(dto.getRule());
            if (sqlGenerator == null) {
                throw new CustomException("非法规则！");
            }
            permissionEntity.setRefSql(sqlGenerator.execute(dto.getPermissionData()));
        }

        permissionEntity.setColumnName(sysScopeResource.getColumnName());
        permissionEntity.setUpdateBy(SecurityUtils.getUserId());
        permissionEntity.setUpdateTime(new Date());
        dataPermissionMapper.updateById(permissionEntity);
    }

    @Override
    public List<Map<String, Object>> getData(String resourceCode) {
        ResourceEntity searchVO = new ResourceEntity();
        searchVO.setResourceCode(resourceCode);
        ResourceEntity resourceEntity = sysResourceServiceImpl.getOne(searchVO);
        if (resourceEntity == null) {
            throw new CustomException("非法资源编码！");
        }
        //如果是物料还要返回code
        if (resourceEntity.getTableName().equals("kl_materials")){
            return dataPermissionMapper.getDataCode(resourceEntity.getTableName());
        }
        return dataPermissionMapper.getData(resourceEntity.getTableName());
    }

    @Override
    public List<DataPermissionEntity> list(DataPermissionEntity entity) {
        return dataPermissionMapper.list(entity);
    }

    @Override
    public void remove(List<Long> ids) {
        dataPermissionMapper.remove(ids);
    }

    @Override
    public DataPermissionEntity getById(Long id) {
        DataPermissionEntity entity = new DataPermissionEntity();
        entity.setId(id);
        return dataPermissionMapper.getOne(entity);
    }
}
