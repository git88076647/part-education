package com.czyl.project.system.service;

import com.czyl.project.system.domain.SysFile;
import java.util.List;

/**
 * 文件管理Service接口
 * 
 * @author tanghx
 * @date 2020-01-15
 */
public interface ISysFileService 
{
    /**
     * 查询文件管理
     * 
     * @param fileId 文件管理ID
     * @return 文件管理
     */
    public SysFile selectSysFileById(Long fileId);

    /**
     * 查询文件管理列表
     * 
     * @param sysFile 文件管理
     * @return 文件管理集合
     */
    public List<SysFile> selectSysFileList(SysFile sysFile);

    /**
     * 新增文件管理
     * 
     * @param sysFile 文件管理
     * @return 结果
     */
    public int insertSysFile(SysFile sysFile);

    /**
     * 修改文件管理
     * 
     * @param sysFile 文件管理
     * @return 结果
     */
    public int updateSysFile(SysFile sysFile);

    /**
     * 批量删除文件管理
     * 
     * @param fileIds 需要删除的文件管理ID
     * @return 结果
     */
    public int deleteSysFileByIds(Long[] fileIds);

    /**
     * 删除文件管理信息
     * 
     * @param fileId 文件管理ID
     * @return 结果
     */
    public int deleteSysFileById(Long fileId);
}
