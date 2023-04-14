package com.czyl.common.meta;

import com.czyl.common.enums.DataSourceTypeEnum;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表要展示的数据的 结构 描述 <br/>
 * <br/>
 * <br/>
 *
 * @author air Email:209308343@qq.com
 * @date 2020/4/1 0001 11:44
 * @project
 * @Version
 */
@Data
public class DataResultMeta {
    /**
     * 数据字段名 -》 java类型映射
     */
    private Map<String, Class> filed2TypeMap;
    /**
     * 参数名 -》 参数索引位置映射,如果是#{}占位的 value是null， 如果是${}预编译的 value就是SQL参数下标的list因为可能变量有重复使用
     */
    private Map<String, List<Integer>> parmarName2IndexMap;
    /**
     * 字段名 -》 字段显示名
     */
    private Map<String, String> filedShowNames;
    /**
     * 数据源类型
     */
    private DataSourceTypeEnum dataSourceType;
    /**
     * 如果有数据库表的话或临时表或试图，那么表名
     */
    private String tableName;

    /**
     * 实例化内部变量
     */
    public void init(int size) {
        filed2TypeMap = new HashMap<>(size);
        parmarName2IndexMap = new HashMap<>(size);
    }
}
