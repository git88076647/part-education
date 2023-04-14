package com.czyl.common.util.sql;

import com.czyl.common.utils.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SQL语句工具类 <br/>
 * <br/>
 * <br/>
 *
 * @author air Email:209308343@qq.com
 * @date 2020/4/3 0003 13:08
 * @project
 * @Version
 */
public abstract class SqlUtil {
    /**
     * SQL变量语法 正则匹配
     */
    private static final Pattern PATTERN_SQL_PARMAR = Pattern.compile("[#$]\\{\\S+\\}");

    /**
     * 把SQL里的 ${} 占位 和 #{} 预编译 2种变量变成 null值
     *
     * @param sql
     * @param replece 要替换成的值 ，null就默认是 null
     * @return
     */
    public static String warpParmarForNull(String sql, String replece) {
        if (sql == null) {
            return null;
        }

        Matcher matcher = PATTERN_SQL_PARMAR.matcher(sql);
        if (!matcher.find()) {
            return sql;
        }

        return matcher.replaceAll(replece == null ? "null" : replece);
    }

    /**
     * 分析SQL语句中 #{} 预编译 和 %{} 字符替换 2种变量，如果变量是${}占位 integer=null， 如果是 #{}预编译 那么是SQL参数顺序的下标的数组
     *
     * @param sql
     * @return
     */
    public static Map<String, List<Integer>> analyzeParmar(String sql) {
        Map<String, List<Integer>> map = new HashMap<>();
        if (StringUtils.isEmpty(sql)) {
            return map;
        }

        Matcher matcher = PATTERN_SQL_PARMAR.matcher(sql);
        String variateName;
        int cussor = 1;
        while (matcher.find()) {
            variateName = matcher.group(0);
            if (variateName.charAt(0) == '$') {
                map.put(variateName, null);
                continue;
            }

            map.computeIfAbsent(variateName, k -> new LinkedList<>()).add(cussor++);
        }

        return map;
    }

    /**
     * 编译SQL，把sql里的 #{} 变成？ 和 ${} 字符替换 值 <><br/>
     * eg: select name,age,sex from man where age=#{age} and name like '%${nameend}' and sex=#{age}
     * <br/> <br/>(nameend=麻子)编译后： <br/> <br/>
     * select name,age,sex from man where age=? and name like '%麻子' and sex=?
     *
     * @param sql
     * @param parmarMap ${}变量 要替换成的值
     * @return
     */
    public static String compilerSql(final String sql, final Map<Integer, Object> parmarMap) {
        if (sql == null) {
            return null;
        }

        Map<String, List<Integer>> parmarIndexMap = analyzeParmar(sql);
        Iterator<Map.Entry<String, List<Integer>>> parmarIndexIterator = parmarIndexMap.entrySet().iterator();
        Map.Entry<String, List<Integer>> parmarIndexEntry;
        Object parmar;
        List<Integer> parmarIndexs;
        String buildsql = sql;
        while (parmarIndexIterator.hasNext()) {
            parmarIndexEntry = parmarIndexIterator.next();
            parmar = parmarMap.get(parmarIndexEntry.getKey());
            parmarIndexs = parmarIndexEntry.getValue();
            if (parmarIndexs == null) {
                //替换型 #{} 变量的，跳过他
                buildsql = StringUtils.replace(buildsql, parmarIndexEntry.getKey()
                        , parmarMap.get(parmarIndexEntry.getKey()) == null
                                ? "null" : parmarMap.get(parmarIndexEntry.getKey()).toString());
            } else {
                //预编译 ${} 变量
                buildsql = StringUtils.replace(buildsql, parmarIndexEntry.getKey(), "?");
            }
        }

        return buildsql;
    }
}
