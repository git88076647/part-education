package com.czyl.project.system.mapper;


import com.czyl.project.system.domain.ResourceEntity;
import com.czyl.project.system.domain.SysScopeResource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author hhhcccggg
 * @since 1.0.0 2022-02-14
 */
@Mapper
public interface SysScopeResourceMapper {


    SysScopeResource getOne(SysScopeResource entity);

    List<SysScopeResource> list();
}
