package com.czyl.project.system.mapper;

import com.czyl.project.system.domain.SysBilltplItem;
import java.util.List;

/**
 * 单据模板Mapper接口
 * 
 * @author tanghx
 * @date 2020-11-28
 */
public interface SysBilltplItemMapper{
    /**
     * 查询单据模板
     * 
     * @param id 单据模板ID
     * @return 单据模板
     */
    public SysBilltplItem selectById(Long id);
    
    /**
     * 根据单据模板查询模板字段
     * @param billtplcode
     * @return
     */
    public List<SysBilltplItem> selectByBillTplCode(String billtplcode);

    /**
     * 查询单据模板列表
     * 
     * @param entity 单据模板
     * @return 单据模板集合
     */
    public List<SysBilltplItem> selectList(SysBilltplItem entity);

    /**
     * 查询prop是否唯一
     *
     * @param entity 单据模板
     * @return 单据模板
     */
    public SysBilltplItem checkUniqueProp(SysBilltplItem entity);

    /**
     * 查询label+virtual是否唯一
     *
     * @param entity 单据模板
     * @return 单据模板
     */
    public SysBilltplItem checkUniqueLabel(SysBilltplItem entity);

    /**
     * 新增单据模板
     * 
     * @param entity 单据模板
     * @return 结果
     */
    public int insert(SysBilltplItem entity);

    /**
     * 修改单据模板
     * 
     * @param entity 单据模板
     * @return 结果
     */
    public int update(SysBilltplItem entity);

    /**
     * 删除单据模板
     * 
     * @param id 单据模板ID
     * @return 结果
     */
    public int deleteById(Long id);

    /**
     * 批量删除单据模板
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByIds(Long[] ids);
}
