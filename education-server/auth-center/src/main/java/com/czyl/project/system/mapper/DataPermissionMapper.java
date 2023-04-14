package com.czyl.project.system.mapper;

import com.czyl.project.system.domain.DataPermissionDTO;
import com.czyl.project.system.domain.DataPermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author hhhcccggg
 * @since 1.0.0 2022-02-14
 */
@Mapper

public interface DataPermissionMapper {


    @Select("select id ,name from  ${tableName} ")
    List<Map<String, Object>> getData(@Param("tableName") String tableName);
    @Select("select id ,name ,code from  ${tableName} ")
    List<Map<String, Object>> getDataCode(@Param("tableName") String tableName);

    List<DataPermissionDTO> getPage(@Param("vo") DataPermissionEntity vo);


    DataPermissionEntity getOne(DataPermissionEntity dataPermissionEntity);

    Integer count(DataPermissionEntity dataPermissionEntity);

    Integer insert(DataPermissionEntity entity);

    Integer updateById(DataPermissionEntity entity);

    List<DataPermissionEntity> list(DataPermissionEntity entity);

    Integer remove(@Param("ids") List<Long> ids);

    DataPermissionEntity getById(Long id);
}
