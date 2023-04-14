package com.czyl.project.report.service;

import com.czyl.project.report.domain.RepSemparmar;

import java.util.List;

/**
 * 语义模型参数Service接口
 *
 * @author tangsu
 * @date 2020-04-02
 */
public interface IRepSemparmarService {
    /**
     * 查询语义模型参数
     *
     * @param pkSemparmar 语义模型参数ID
     * @return 语义模型参数
     */
    public RepSemparmar selectRepSemparmarById(Long pkSemparmar);

    /**
     * 查询语义模型参数列表
     *
     * @param repSemparmar 语义模型参数
     * @return 语义模型参数集合
     */
    public List<RepSemparmar> selectRepSemparmarList(RepSemparmar repSemparmar);

    /**
     * 新增语义模型参数
     *
     * @param repSemparmar 语义模型参数
     * @return 结果
     */
    public int insertRepSemparmar(RepSemparmar repSemparmar);

    /**
     * 修改语义模型参数
     *
     * @param repSemparmar 语义模型参数
     * @return 结果
     */
    public int updateRepSemparmar(RepSemparmar repSemparmar);

    /**
     * 批量删除语义模型参数
     *
     * @param pkSemparmars 需要删除的语义模型参数ID
     * @return 结果
     */
    public int deleteRepSemparmarByIds(Long[] pkSemparmars);

    /**
     * 删除语义模型参数信息
     *
     * @param pkSemparmar 语义模型参数ID
     * @return 结果
     */
    public int deleteRepSemparmarById(Long pkSemparmar);


    /**
     * 查询语义参数列表 根据 语义模型id
     *
     * @param pk_semantic 语义模型ID
     * @return 语义模型参数
     */
    public List<RepSemparmar> selectRepSemparmarBySemanticId(Long pkSemantic);
}
