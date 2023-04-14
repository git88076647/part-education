package com.czyl.common.report.itf;

import com.czyl.common.meta.DataResultMeta;
import com.czyl.project.report.domain.bo.DataResultBO;

import java.util.List;
import java.util.Map;

/**
 * 查询结果  <br/>
 * <br/>
 * <br/>
 *
 * @author air Email:209308343@qq.com
 * @date 2020/4/1 0001 13:57
 * @project
 * @Version
 */
public interface IQueryResultDataVO {
    /**
     * 获得所有的 列名字
     *
     * @return
     */
    List<String> getAllColumnNames();

    /**
     * 根据列名字获得列的java类型
     *
     * @param columnName
     * @return
     */
    Class getColumnType(String columnName);

    /**
     * 获得所有列的 列名字 -》 Java类型
     *
     * @return
     */
    Map<String, Class> getAllColumnTypes();

    /**
     * 根据 列名字获得列值
     *
     * @param columnName
     * @return
     */
    List<Object> getColumnValues(String columnName);

    /**
     * 根据 列名字获得列值
     *
     * @param columnName
     * @param rowIndex   第几行 0开始
     * @return
     */
    Object getColumnValue(String columnName, int rowIndex);

    /**
     * 获得本次查询一共多少行
     *
     * @return
     */

    /**
     * 根据查询结果获得 和前台交互的 标准BO对象
     *
     * @return
     */
    DataResultBO getDataBO();

    /**
     * 获得查询结构的 字段类型这些描述
     * @return
     */
    DataResultMeta getDataResultMeta();
}
