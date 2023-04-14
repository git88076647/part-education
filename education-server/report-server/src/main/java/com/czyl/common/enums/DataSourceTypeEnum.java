package com.czyl.common.enums;

/**
 * 数据源类型 <br/>
 * <br/>
 * <br/>
 *
 * @author air Email:209308343@qq.com
 * @date 2020/4/1 0001 13:29
 * @project
 * @Version
 */
public enum DataSourceTypeEnum {
    /**
     * 关系型数据库
     */
    SQLDB(1, "关系型数据库")
    /**
     * 关系型数据库
     */
    , NOSQLDB(2, "NoSql数据库")
    /**
     * java类
     */
    , CLASS(3, "数据提供类");

    /**
     * 类型码
     */
    private int value;
    /**
     * 类型名
     */
    private String name;


    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    DataSourceTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
