package com.czyl.project.system.service.impl;

import com.czyl.common.exception.CustomException;
import com.czyl.project.system.domain.SysBilltplItem;
import com.czyl.project.system.mapper.SysBilltplItemMapper;
import com.czyl.project.system.service.ISysBilltplItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 单据模板Service业务层处理
 *
 * @author tanghx
 * @date 2020-11-28
 */
@Service
public class SysBilltplItemServiceImpl implements ISysBilltplItemService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SysBilltplItemMapper mapper;

    /**
     * 查询单据模板
     *
     * @param id 单据模板ID
     * @return 单据模板
     */
    @Override
    public SysBilltplItem selectById(Long id) {
        return mapper.selectById(id);
    }

    /**
     * 查询单据模板列表
     *
     * @param entity 单据模板
     * @return 单据模板
     */
    @Override
    public List<SysBilltplItem> selectList(SysBilltplItem entity) {
        return mapper.selectList(entity);
    }

    /**
     * 新增单据模板
     *
     * @param entity 单据模板
     * @return 结果
     */
    @Override
    @Transactional
    public int insert(SysBilltplItem entity) {
        checkUnique(entity);
        return mapper.insert(entity);
    }

    /**
     * 唯一性校验
     * @param entity
     */
    private void checkUnique(SysBilltplItem entity){
        SysBilltplItem db = mapper.checkUniqueProp(entity);
        if(db != null){
            throw new CustomException(String.format("字段[%s]已存在", entity.getProp()));
        }
        db = mapper.checkUniqueLabel(entity);
        if(db != null){
            throw new CustomException(String.format("%s显示标题[%s]已存在",(entity.getVirtual() !=null && entity.getVirtual().booleanValue()?"虚字段":"非虚字段"), entity.getLabel()));
        }
    }

    /**
     * 修改单据模板
     *
     * @param entity 单据模板
     * @return 结果
     */
    @Override
    @Transactional
    public int update(SysBilltplItem entity) {
        checkUnique(entity);
        return mapper.update(entity);
    }

    @Override
    @Transactional
    public int reSort(String billtplcode) {
        List<SysBilltplItem> list = selectByBillTplCode(billtplcode);
        if (list != null && !list.isEmpty()) {
            for (int i = 0, len = list.size(); i < len; i++) {
                list.get(i).setSortindex((i + 1) * 5);
                update(list.get(i));
            }
            return list.size();
        }
        return 0;
    }

    /**
     * 批量删除单据模板
     *
     * @param ids 需要删除的单据模板ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteByIds(Long[] ids) {
        return mapper.deleteByIds(ids);
    }

    /**
     * 删除单据模板信息
     *
     * @param id 单据模板ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteById(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public List<SysBilltplItem> selectByBillTplCode(String billtplcode) {
        return mapper.selectByBillTplCode(billtplcode);
    }
}
