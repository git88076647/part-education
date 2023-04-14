package com.czyl.project.report.mapper;

import com.czyl.project.report.domain.RepSemantic;

import java.util.List;

/**
 * 语义模型Mapper接口
 *
 * @author tanghx
 * @date 2020-04-03
 */
public interface RepSemanticMapper {
    /**
     * 查询语义模型
     *
     * @param pkSemantic 语义模型ID
     * @return 语义模型
     */
    public RepSemantic selectRepSemanticById(Long pkSemantic);

    /**
     * 查询语义模型列表
     *
     * @param repSemantic 语义模型
     * @return 语义模型集合
     */
    public List<RepSemantic> selectRepSemanticList(RepSemantic repSemantic);

    /**
     * 新增语义模型
     *
     * @param repSemantic 语义模型
     * @return 结果
     */
    public int insertRepSemantic(RepSemantic repSemantic);

    /**
     * 修改语义模型
     *
     * @param repSemantic 语义模型
     * @return 结果
     */
    public int updateRepSemantic(RepSemantic repSemantic);

    /**
     * 删除语义模型
     *
     * @param pkSemantic 语义模型ID
     * @return 结果
     */
    public int deleteRepSemanticById(Long pkSemantic);

    /**
     * 批量删除语义模型
     *
     * @param pkSemantics 需要删除的数据ID
     * @return 结果
     */
    public int deleteRepSemanticByIds(Long[] pkSemantics);
}
