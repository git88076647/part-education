package com.czyl.project.integrate.mapper;

import com.czyl.project.integrate.domain.SysAppreg;
import java.util.List;

/**
 * 应用注册Mapper接口
 * 
 * @author tanghx
 * @date 2020-04-19
 */
public interface SysAppregMapper{
    /**
     * 查询应用注册
     * 
     * @param appregId 应用注册ID
     * @return 应用注册
     */
    public SysAppreg selectById(Long appregId);
    
    /**
     * 查询应用注册
     * @param code 应用编码
     * @return
     */
    public SysAppreg selectByCode(String code);

    /**
     * 查询应用注册列表
     * 
     * @param sysAppreg 应用注册
     * @return 应用注册集合
     */
    public List<SysAppreg> selectList(SysAppreg sysAppreg);

    /**
     * 新增应用注册
     * 
     * @param sysAppreg 应用注册
     * @return 结果
     */
    public int insert(SysAppreg sysAppreg);

    /**
     * 修改应用注册
     * 
     * @param sysAppreg 应用注册
     * @return 结果
     */
    public int update(SysAppreg sysAppreg);

    /**
     * 删除应用注册
     * 
     * @param appregId 应用注册ID
     * @return 结果
     */
    public int deleteById(Long appregId);

    /**
     * 批量删除应用注册
     * 
     * @param appregIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteByIds(Long[] appregIds);
}
