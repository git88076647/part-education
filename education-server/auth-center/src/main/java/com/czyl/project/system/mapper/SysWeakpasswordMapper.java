package com.czyl.project.system.mapper;

import java.util.List;

import com.czyl.project.system.domain.SysWeakpassword;

/**
 * 弱口令管理Mapper接口
 * 
 * @author tanghx
 * @date 2020-04-08
 */
public interface SysWeakpasswordMapper 
{
    /**
     * 查询弱口令管理
     * 
     * @param pwdId 弱口令管理ID
     * @return 弱口令管理
     */
    public SysWeakpassword selectSysWeakpasswordById(Long pwdId);
    
    /**
     * 根据密码查询
     * @param password
     * @return
     */
    public SysWeakpassword selectSysWeakpasswordByPassword(String password);

    /**
     * 查询弱口令管理列表
     * 
     * @param sysWeakpassword 弱口令管理
     * @return 弱口令管理集合
     */
    public List<SysWeakpassword> selectSysWeakpasswordList(SysWeakpassword sysWeakpassword);

    /**
     * 新增弱口令管理
     * 
     * @param sysWeakpassword 弱口令管理
     * @return 结果
     */
    public int insertSysWeakpassword(SysWeakpassword sysWeakpassword);

    /**
     * 批量插入
     * @param list
     * @return
     */
    public int insertSysWeakpasswordBatch(List<SysWeakpassword> list);
    /**
     * 修改弱口令管理
     * 
     * @param sysWeakpassword 弱口令管理
     * @return 结果
     */
    public int updateSysWeakpassword(SysWeakpassword sysWeakpassword);

    /**
     * 删除弱口令管理
     * 
     * @param pwdId 弱口令管理ID
     * @return 结果
     */
    public int deleteSysWeakpasswordById(Long pwdId);

    /**
     * 批量删除弱口令管理
     * 
     * @param pwdIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysWeakpasswordByIds(Long[] pwdIds);
}
