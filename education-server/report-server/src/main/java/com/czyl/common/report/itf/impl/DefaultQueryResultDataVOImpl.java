package com.czyl.common.report.itf.impl;

import com.czyl.common.meta.DataResultMeta;
import com.czyl.common.report.itf.IQueryResultDataVO;
import com.czyl.project.report.domain.bo.DataResultBO;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 查询结果 默认实现类 <br/>
 * <br/>
 * <br/>
 *
 * @author air Email:209308343@qq.com
 * @date 2020/4/3 0003 11:39
 * @project
 * @Version
 */
public class DefaultQueryResultDataVOImpl implements IQueryResultDataVO {
    /**
     * 所有字段名
     */
    private List<String> allColumnNames;
    /**
     * 查询出来的最终数据
     */
    private DataResultBO dataResultBO;
    /**
     * 结果结构描述
     */
    private DataResultMeta dataResultMeta;

    @Override
    public List<String> getAllColumnNames() {
        return allColumnNames;
    }

    @Override
    public Class getColumnType(String columnName) {
        return dataResultMeta.getFiled2TypeMap().get(columnName);
    }

    @Override
    public Map<String, Class> getAllColumnTypes() {
        return dataResultMeta.getFiled2TypeMap();
    }

    @Override
    public List<Object> getColumnValues(String columnName) {
        return null;
    }

    @Override
    public Object getColumnValue(String columnName, int rowIndex) {
        if (dataResultBO == null) {
            return null;
        }

        if (dataResultBO.getDatas() == null) {
            return null;
        }

        return dataResultBO.getDatas().get(rowIndex).get(columnName);
    }

    @Override
    public DataResultBO getDataBO() {
        return dataResultBO;
    }

    public DataResultBO getDataResultBO() {
        return dataResultBO;
    }

    public void setDataResultBO(DataResultBO dataResultBO) {
        this.dataResultBO = dataResultBO;
    }

    @Override
    public DataResultMeta getDataResultMeta() {
        return dataResultMeta;
    }

    public void setDataResultMeta(DataResultMeta dataResultMeta) {
        allColumnNames = new LinkedList<>();
        Map<String, Class> filed2TypeMap = dataResultMeta.getFiled2TypeMap();
        Iterator<String> iterator = filed2TypeMap.keySet().iterator();
        while (iterator.hasNext()) {
            allColumnNames.add(iterator.next());
        }
        this.dataResultMeta = dataResultMeta;
    }
}
