package com.czyl.project.report.service;

import com.czyl.common.exception.CustomException;
import com.czyl.common.meta.DataResultMeta;
import com.czyl.common.report.itf.IDataQuery;
import com.czyl.common.report.itf.IQueryResultDataVO;
import com.czyl.project.report.domain.RepSemantic;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 语义模型Service接口
 *
 * @author tanghx
 * @date 2020-04-03
 */
public interface IRepSemanticService {
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
     * 批量删除语义模型
     *
     * @param pkSemantics 需要删除的语义模型ID
     * @return 结果
     */
    public int deleteRepSemanticByIds(Long[] pkSemantics);

    /**
     * 删除语义模型信息
     *
     * @param pkSemantic 语义模型ID
     * @return 结果
     */
    public int deleteRepSemanticById(Long pkSemantic);

    /**
     * 执行语义模型 查询
     *
     * @param repSemantic
     * @param parmarMap
     * @return
     * @throws CustomException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     */
    IQueryResultDataVO exec(RepSemantic repSemantic, Map<Integer, Object> parmarMap)
            throws CustomException, ClassNotFoundException, IllegalAccessException
            , InstantiationException, SQLException;

    /**
     * 获得查询结果结构描述
     *
     * @param repSemantic
     * @return
     * @throws CustomException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     */
    DataResultMeta getResultMeta(RepSemantic repSemantic) throws CustomException
            , ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException;

    /**
     * 根据 语义模型 得到 他的查询实现类对象
     *
     * @param repSemantic
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    IDataQuery buildDataQueryByRepSemantic(RepSemantic repSemantic) throws ClassNotFoundException
            , IllegalAccessException, InstantiationException;
}
