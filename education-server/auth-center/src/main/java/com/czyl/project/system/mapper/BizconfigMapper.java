package com.czyl.project.system.mapper;

import com.czyl.project.system.domain.Bizconfig;

import java.util.List;

/**
 * 业务参数配置Mapper接口
 * 
 * @author tanghx
 * @date 2021-06-08
 */
public interface BizconfigMapper{
    /**
     * 查询业务参数配置
     * 
     * @param id 业务参数配置ID
     * @return 业务参数配置
     */
    public Bizconfig selectById(Long id);
    /**
     * 查询业务参数配置
     *
     * @param key 业务参数配置key
     * @return 业务参数配置
     */
    public String selectByKey(String key);

    /**
     * 查询业务参数配置列表
     * 
     * @param entity 业务参数配置
     * @return 业务参数配置集合
     */
    public List<Bizconfig> selectList(Bizconfig entity);

    /**
     * 新增业务参数配置
     * 
     * @param entity 业务参数配置
     * @return 结果
     */
    public int insert(Bizconfig entity);
    
    /**
     * 新增业务参数配置
     * 
     * @param list 业务参数配置
     * @return 结果
     */
    public int insertBatch(List<Bizconfig> list);

    /**
     * 修改业务参数配置
     * 
     * @param entity 业务参数配置
     * @return 结果
     */
    public int update(Bizconfig entity);
    
    /**
     * 修改业务参数配置
     * 
     * @param list 业务参数配置
     * @return 结果
     */
    public int updateBatch(List<Bizconfig> list);

    /**
     * 删除业务参数配置
     * 
     * @param id 业务参数配置ID
     * @return 结果
     */
    public int deleteById(Long id);

    /**
     * 批量删除业务参数配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByIds(Long[] ids);
}
