package com.czyl.common.report.itf;

import com.czyl.common.exception.CustomException;
import com.czyl.common.meta.DataResultMeta;
import com.czyl.project.report.domain.RepSemantic;
import com.github.pagehelper.Page;

import java.sql.SQLException;
import java.util.Map;

/**
 * 数据查询接口 <br/>
 * <br/>
 * <br/>
 *
 * @author air Email:209308343@qq.com
 * @date 2020/4/1 0001 13:56
 * @project
 * @Version
 */
public interface IDataQuery {
    /**
     * 获得 当前的 语义模型
     *
     * @return
     */
    RepSemantic getRepSemantic();

    /**
     * 设置当前的 语义模型
     *
     * @param repSemantic
     */
    void setRepSemantic(RepSemantic repSemantic);

    /**
     * 查询所有符合数据
     *
     * @param parmarMap 参数Map，  key=参数索引(1开始)， value=参数值，如果没有 就是空map
     * @return 符合的所有数据
     * @throws SQLException
     * @throws CustomException
     */
    IQueryResultDataVO queryAll(Map<Integer, Object> parmarMap)
            throws SQLException, CustomException;

    /**
     * 查询指定页的符合数据（也就是分页查询啦）
     *
     * @param parmarMap 参数Map，  key=参数索引(1开始)， value=参数值，如果没有 就是空map
     * @param page      分页描述
     * @return 符合的所有数据
     * @throws SQLException
     * @throws CustomException
     */
    IQueryResultDataVO queryPage(Map<Integer, Object> parmarMap
            , Page page) throws SQLException, CustomException;

    /**
     * 获得 查询传入的 参数map（可修改里面的参数，如果查询未生效前 修改了 查询会使用修改后的值）
     *
     * @return
     */
    Map<Integer, Object> getParmarMap();

    /**
     * 获得 查询结果结构
     *
     * @return
     * @throws SQLException
     * @throws CustomException
     */
    DataResultMeta getDataResultMate() throws SQLException, CustomException;
}
