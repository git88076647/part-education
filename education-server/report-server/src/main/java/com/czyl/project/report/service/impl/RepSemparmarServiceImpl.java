package com.czyl.project.report.service.impl;

import com.czyl.common.utils.DateUtils;
import com.czyl.project.report.domain.RepSemparmar;
import com.czyl.project.report.mapper.RepSemparmarMapper;
import com.czyl.project.report.service.IRepSemparmarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 语义模型参数Service业务层处理
 *
 * @author tangsu
 * @date 2020-04-02
 */
@Service
public class RepSemparmarServiceImpl implements IRepSemparmarService {
    @Autowired
    private RepSemparmarMapper repSemparmarMapper;

    /**
     * 查询语义模型参数
     *
     * @param pkSemparmar 语义模型参数ID
     * @return 语义模型参数
     */
    @Override
    public RepSemparmar selectRepSemparmarById(Long pkSemparmar) {
        return repSemparmarMapper.selectRepSemparmarById(pkSemparmar);
    }

    /**
     * 查询语义模型参数列表
     *
     * @param repSemparmar 语义模型参数
     * @return 语义模型参数
     */
    @Override
    public List<RepSemparmar> selectRepSemparmarList(RepSemparmar repSemparmar) {
        return repSemparmarMapper.selectRepSemparmarList(repSemparmar);
    }

    /**
     * 新增语义模型参数
     *
     * @param repSemparmar 语义模型参数
     * @return 结果
     */
    @Override
    public int insertRepSemparmar(RepSemparmar repSemparmar) {
        repSemparmar.setCreateTime(DateUtils.getNowDate());
        return repSemparmarMapper.insertRepSemparmar(repSemparmar);
    }

    /**
     * 修改语义模型参数
     *
     * @param repSemparmar 语义模型参数
     * @return 结果
     */
    @Override
    public int updateRepSemparmar(RepSemparmar repSemparmar) {
        repSemparmar.setUpdateTime(DateUtils.getNowDate());
        return repSemparmarMapper.updateRepSemparmar(repSemparmar);
    }

    /**
     * 批量删除语义模型参数
     *
     * @param pkSemparmars 需要删除的语义模型参数ID
     * @return 结果
     */
    @Override
    public int deleteRepSemparmarByIds(Long[] pkSemparmars) {
        return repSemparmarMapper.deleteRepSemparmarByIds(pkSemparmars);
    }

    /**
     * 删除语义模型参数信息
     *
     * @param pkSemparmar 语义模型参数ID
     * @return 结果
     */
    @Override
    public int deleteRepSemparmarById(Long pkSemparmar) {
        return repSemparmarMapper.deleteRepSemparmarById(pkSemparmar);
    }

    @Override
    public List<RepSemparmar> selectRepSemparmarBySemanticId(Long pkSemantic) {
        return repSemparmarMapper.selectRepSemparmarBySemanticId(pkSemantic);
    }
}
