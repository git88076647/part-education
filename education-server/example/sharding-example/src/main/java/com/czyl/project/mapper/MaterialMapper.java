package com.czyl.project.mapper;

import com.czyl.project.domain.Material;
import java.util.List;

/**
 * 物料Mapper接口
 * 
 * @author tanghx
 * @date 2020-05-26
 */
public interface MaterialMapper{
    /**
     * 查询物料
     * 
     * @param id 物料ID
     * @return 物料
     */
    public Material selectById(Long id);

    /**
     * 查询物料列表
     * 
     * @param material 物料
     * @return 物料集合
     */
    public List<Material> selectList(Material material);

    /**
     * 新增物料
     * 
     * @param material 物料
     * @return 结果
     */
    public int insert(Material material);
    
    /**
     * 批量插入
     * @param material
     * @return
     */
    public int insertBatch(List<Material> materials);

    /**
     * 修改物料
     * 
     * @param material 物料
     * @return 结果
     */
    public int update(Material material);

    /**
     * 删除物料
     * 
     * @param id 物料ID
     * @return 结果
     */
    public int deleteById(Long id);

    /**
     * 批量删除物料
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByIds(Long[] ids);
}
