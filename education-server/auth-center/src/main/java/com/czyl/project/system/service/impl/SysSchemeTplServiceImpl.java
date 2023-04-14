package com.czyl.project.system.service.impl;

import com.czyl.project.system.domain.SysSchemeTpl;
import com.czyl.project.system.mapper.SysSchemeTplMapper;
import com.czyl.project.system.service.ISysSchemeTplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 模版方案Service业务层处理
 *
 * @author tanghx
 * @date 2021-07-21
 */
@Service
public class SysSchemeTplServiceImpl implements ISysSchemeTplService {
    @Autowired
    private SysSchemeTplMapper mapper;

    /**
     * 查询模版方案
     *
     * @param id 模版方案ID
     * @return 模版方案
     */
    @Override
    public SysSchemeTpl selectById(Long id) {
        return mapper.selectById(id);
    }

    /**
     * 查询模版方案列表
     *
     * @param entity 模版方案
     * @return 模版方案
     */
    @Override
    public List<SysSchemeTpl> selectList(SysSchemeTpl entity) {
        return selectList(entity, false);
    }

    /**
     * 查询模版方案列表
     *
     * @param entity 模版方案
     * @param fill   是否填充虚字段
     * @return 模版方案
     */
    @Override
    public List<SysSchemeTpl> selectList(SysSchemeTpl entity, boolean fill) {
        List<SysSchemeTpl> list = mapper.selectList(entity);
        if (fill) {
            //TODO 根据业务进行填充虚字段

        }
        return list;
    }

    /**
     * 新增模版方案
     *
     * @param entity 模版方案
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(SysSchemeTpl entity) {
        //查询 同一个人、同类型、同名称 的模板方案
        SysSchemeTpl queryEntity = new SysSchemeTpl();
        queryEntity.setTypecode(entity.getTypecode());
        queryEntity.setName(entity.getName());
        queryEntity.setUserId(entity.getUserId());
        List<SysSchemeTpl> list = mapper.selectList(queryEntity);
        if (!list.isEmpty()) {
            entity.setId(list.get(0).getId());
            return mapper.update(entity);
        }
        return mapper.insert(entity);
    }

    /**
     * 修改模版方案
     *
     * @param entity 模版方案
     * @return 结果
     */
    @Override
    @Transactional
    public int update(SysSchemeTpl entity) {
        return mapper.update(entity);
    }

    /**
     * 批量删除模版方案
     *
     * @param ids 需要删除的模版方案ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteByIds(Long[] ids) {
        return mapper.deleteByIds(ids);
    }

    /**
     * 删除模版方案信息
     *
     * @param id 模版方案ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteById(Long id) {
        return mapper.deleteById(id);
    }
}
