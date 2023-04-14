package com.czyl.project.integrate.service.impl;

import java.util.List;
import com.czyl.common.utils.DateUtils;
import com.czyl.common.utils.security.LoginUtils;
import com.czyl.common.utils.AppContextUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.czyl.project.integrate.mapper.SysAppregMapper;
import com.czyl.project.integrate.domain.SysAppreg;
import com.czyl.project.integrate.service.ISysAppregService;

/**
 * 应用注册Service业务层处理
 * 
 * @author tanghx
 * @date 2020-04-19
 */
@Service
public class SysAppregServiceImpl implements ISysAppregService{
    @Autowired
    private SysAppregMapper sysAppregMapper;

    /**
     * 查询应用注册
     * 
     * @param appregId 应用注册ID
     * @return 应用注册
     */
    @Override
    public SysAppreg selectById(Long appregId){
        return sysAppregMapper.selectById(appregId);
    }
    
    @Override
    public SysAppreg selectByCode(String code){
        return sysAppregMapper.selectByCode(code);
    }

    /**
     * 查询应用注册列表
     * 
     * @param sysAppreg 应用注册
     * @return 应用注册
     */
    @Override
    public List<SysAppreg> selectList(SysAppreg sysAppreg){
        return sysAppregMapper.selectList(sysAppreg);
    }

    /**
     * 新增应用注册
     * 
     * @param sysAppreg 应用注册
     * @return 结果
     */
    @Override
    @Transactional
    public int insert(SysAppreg sysAppreg){
        if(sysAppreg.getCreateBy() == null || sysAppreg.getCreateBy() == 0) {
            sysAppreg.setCreateBy(AppContextUtils.getUserId());
        }
        sysAppreg.setCreateTime(DateUtils.getNowDate());
        sysAppreg.setPassword(LoginUtils.encrypt(sysAppreg.getPassword()));
        return sysAppregMapper.insert(sysAppreg);
    }

    /**
     * 修改应用注册
     * 
     * @param sysAppreg 应用注册
     * @return 结果
     */
    @Override
    @Transactional
    public int update(SysAppreg sysAppreg){
        if(sysAppreg.getUpdateBy() == null || sysAppreg.getUpdateBy() == 0) {
            sysAppreg.setUpdateBy(AppContextUtils.getUserId());
        }
        sysAppreg.setUpdateTime(DateUtils.getNowDate());
        sysAppreg.setPassword(LoginUtils.encrypt(sysAppreg.getPassword()));
        return sysAppregMapper.update(sysAppreg);
    }

    /**
     * 批量删除应用注册
     * 
     * @param appregIds 需要删除的应用注册ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteByIds(Long[] appregIds){
        return sysAppregMapper.deleteByIds(appregIds);
    }

    /**
     * 删除应用注册信息
     * 
     * @param appregId 应用注册ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteById(Long appregId){
        return sysAppregMapper.deleteById(appregId);
    }
}
