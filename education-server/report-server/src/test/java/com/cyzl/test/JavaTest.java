package com.cyzl.test;

import com.czyl.common.util.sql.SqlUtil;

/**
 * <br/>
 * <br/>
 * <br/>
 *
 * @author air Email:209308343@qq.com
 * @date 2020/4/3 0003 13:31
 * @project
 * @Version
 */
public class JavaTest {
    public static void main(String[] args) {
        String sql = "select t.a,t.b,t.#{name} from tb_user  t \n" +
                "where \n" +
                "t.age > ${age}\n" +
                "and t.sex = ${sex}\n" +
                "and t.area = '#{area#$}' and t.code<>NVL(t.code,${$#你好呀})";
        System.out.println(sql);
        System.out.println(SqlUtil.analyzeParmar(sql));
    }
}
