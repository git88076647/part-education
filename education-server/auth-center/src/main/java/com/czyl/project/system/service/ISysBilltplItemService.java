package com.czyl.project.system.service;

import com.czyl.project.system.domain.SysBilltplItem;
import java.util.List;

/**
 * 单据模板Service接口
 * 
 * @author tanghx
 * @date 2020-11-28
 */
public interface ISysBilltplItemService{
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
     * @param sysBilltplItem 单据模板
     * @return 单据模板集合
     */
    public List<SysBilltplItem> selectList(SysBilltplItem sysBilltplItem);

    /**
     * 新增单据模板
     * 
     * @param sysBilltplItem 单据模板
     * @return 结果
     */
    public int insert(SysBilltplItem sysBilltplItem);

    /**
     * 修改单据模板
     * 
     * @param sysBilltplItem 单据模板
     * @return 结果
     */
    public int update(SysBilltplItem sysBilltplItem);
    /**
     * 修改单据模板
     *
     * @param billtplcode 单据模板编码
     * @return 结果
     */
    public int reSort(String billtplcode);

    /**
     * 批量删除单据模板
     * 
     * @param ids 需要删除的单据模板ID
     * @return 结果
     */
    public int deleteByIds(Long[] ids);

    /**
     * 删除单据模板信息
     * 
     * @param id 单据模板ID
     * @return 结果
     */
    public int deleteById(Long id);
}
