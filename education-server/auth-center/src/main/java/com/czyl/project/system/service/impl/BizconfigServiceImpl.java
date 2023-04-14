package com.czyl.project.system.service.impl;

import com.czyl.common.utils.AppContextUtils;
import com.czyl.common.utils.DateUtils;
import com.czyl.project.system.domain.Bizconfig;
import com.czyl.project.system.mapper.BizconfigMapper;
import com.czyl.project.system.service.IBizconfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务参数配置Service业务层处理
 * 
 * @author tanghx
 * @date 2021-06-08
 */
@Service
public class BizconfigServiceImpl implements IBizconfigService{
    @Autowired
    private BizconfigMapper mapper;

    /**
     * 查询业务参数配置
     * 
     * @param id 业务参数配置ID
     * @return 业务参数配置
     */
    @Override
    public Bizconfig selectById(Long id){
        return mapper.selectById(id);
    }

    @Override
    public String selectByKey(String key) {
        return mapper.selectByKey(key);
    }

    /**
     * 查询业务参数配置列表
     * 
     * @param entity 业务参数配置
     * @return 业务参数配置
     */
    @Override
    public List<Bizconfig> selectList(Bizconfig entity){
        return selectList(entity,false);
    }
    /**
     * 查询业务参数配置列表
     *
     * @param entity 业务参数配置
     * @param fill 是否填充虚字段
     * @return 业务参数配置
     */
    @Override
    public List<Bizconfig> selectList(Bizconfig entity,boolean fill){
        List<Bizconfig> list = mapper.selectList(entity);
        if(fill){
            //TODO 根据业务进行填充虚字段

        }
        return list;
    }

    /**
     * 新增业务参数配置
     * 
     * @param entity 业务参数配置
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(Bizconfig entity){
        entity.setCreateTime(DateUtils.getNowDate());
        if(entity.getCreateBy() == null || entity.getCreateBy() == 0) {
            entity.setCreateBy(AppContextUtils.getUserId());
        }
        return mapper.insert(entity);
    }

    /**
     * 修改业务参数配置
     * 
     * @param entity 业务参数配置
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(Bizconfig entity){
        entity.setUpdateTime(DateUtils.getNowDate());
        if(AppContextUtils.getUserId2() != null && AppContextUtils.getUserId2() > 0) {
            entity.setUpdateBy(AppContextUtils.getUserId2());
        }
        return mapper.update(entity);
    }

    /**
     * 批量删除业务参数配置
     * 
     * @param ids 需要删除的业务参数配置ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByIds(Long[] ids){
        return mapper.deleteByIds(ids);
    }

    /**
     * 删除业务参数配置信息
     * 
     * @param id 业务参数配置ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(Long id){
        return mapper.deleteById(id);
    }
}
