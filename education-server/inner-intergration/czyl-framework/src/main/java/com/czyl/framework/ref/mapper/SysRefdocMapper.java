package com.czyl.framework.ref.mapper;

import com.czyl.framework.ref.domain.SysRefdoc;
import java.util.List;
import java.util.Map;

/**
 * 引用Mapper接口
 * 
 * @author tanghx
 * @date 2021-01-11
 */
public interface SysRefdocMapper{
    /**
     * 查询引用
     * 
     * @param id 引用ID
     * @return 引用
     */
    public SysRefdoc selectById(Integer id);

    /**
     * 查询引用列表
     * 
     * @param sysRefdoc 引用
     * @return 引用集合
     */
    public List<SysRefdoc> selectList(SysRefdoc sysRefdoc);

    /**
     * 检查数据是否被引用
     * @param sysRefdoc
     * @return
     */
    public Map<String,Object> checkDataRef(SysRefdoc sysRefdoc);

    /**
     * 新增引用
     * 
     * @param sysRefdoc 引用
     * @return 结果
     */
    public int insert(SysRefdoc sysRefdoc);
    
    /**
     * 新增引用
     * 
     * @param list 引用
     * @return 结果
     */
    public int insertBatch(List<SysRefdoc> list);

    /**
     * 修改引用
     * 
     * @param sysRefdoc 引用
     * @return 结果
     */
    public int update(SysRefdoc sysRefdoc);
    
    /**
     * 修改引用
     * 
     * @param list 引用
     * @return 结果
     */
    public int updateBatch(List<SysRefdoc> list);

    /**
     * 删除引用
     * 
     * @param id 引用ID
     * @return 结果
     */
    public int deleteById(Integer id);

    /**
     * 批量删除引用
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByIds(Integer[] ids);
}
