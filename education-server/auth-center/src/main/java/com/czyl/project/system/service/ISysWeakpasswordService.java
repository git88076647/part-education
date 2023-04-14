package com.czyl.project.system.service;

import com.czyl.project.system.domain.SysWeakpassword;
import java.util.List;

/**
 * 弱口令管理Service接口
 * 
 * @author tanghx
 * @date 2020-04-08
 */
public interface ISysWeakpasswordService 
{
    /**
     * 查询弱口令管理
     * 
     * @param pwdId 弱口令管理ID
     * @return 弱口令管理
     */
    public SysWeakpassword selectSysWeakpasswordById(Long pwdId);

    /**
     * 查询弱口令管理列表
     * 
     * @param sysWeakpassword 弱口令管理
     * @return 弱口令管理集合
     */
    public List<SysWeakpassword> selectSysWeakpasswordList(SysWeakpassword sysWeakpassword);

    /**
     * 根据密码查询
     * @param password
     * @return
     */
    public SysWeakpassword selectSysWeakpasswordByPassword(String password);
    
    /**
     * 新增弱口令管理
     * 
     * @param sysWeakpassword 弱口令管理
     * @return 结果
     */
    public int insertSysWeakpassword(SysWeakpassword sysWeakpassword);
    
    /**
     * 导入
     * @param sysWeakpassword
     * @return
     */
    public void importData(List<String> list,Long userid);

    /**
     * 修改弱口令管理
     * 
     * @param sysWeakpassword 弱口令管理
     * @return 结果
     */
    public int updateSysWeakpassword(SysWeakpassword sysWeakpassword);

    /**
     * 批量删除弱口令管理
     * 
     * @param pwdIds 需要删除的弱口令管理ID
     * @return 结果
     */
    public int deleteSysWeakpasswordByIds(Long[] pwdIds);

    /**
     * 删除弱口令管理信息
     * 
     * @param pwdId 弱口令管理ID
     * @return 结果
     */
    public int deleteSysWeakpasswordById(Long pwdId);
}
