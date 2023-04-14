package com.czyl.project.system.mapper;


import com.czyl.project.system.domain.ResourceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author hhhcccggg
 * @since 1.0.0 2022-02-14
 */
@Mapper

public interface ResourceMapper {


    List<Map<String, Object>> getList(@Param("scopeCode") String scopeCode,
                                      @Param("resourceCode") String resourceCode,
                                      @Param("resourceName") String resourceName);


    Integer count(@Param("id") Long id,
                  @Param("resourceCode") String resourceCode);

    Integer insert(ResourceEntity entity);

    Integer updateById(ResourceEntity entity);

    ResourceEntity getOne(ResourceEntity entity);
}
