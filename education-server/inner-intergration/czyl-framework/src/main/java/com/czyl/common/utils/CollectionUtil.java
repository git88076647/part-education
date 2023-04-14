package com.czyl.common.utils;

import java.util.*;

/**
 * 集合工具类，提供JDK没有提供的部分 <br/>
 * <br/>
 * <br/>
 *
 * @author air Email:209308343@qq.com
 * @date 2020/3/21 0021 12:02
 * @project
 * @Version
 */
public abstract class CollectionUtil {
    /**
     * 转换成一个map，ks key的数组， vs value的数组，如果ks 的长度大于vs，多余的用null作为值，vs多余ks 多余的vs丢弃
     *
     * @param ks
     * @param vs
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> HashMap<K, V> asMap(K[] ks, V[] vs) {
        return asMap(Arrays.asList(ks), Arrays.asList(vs));
    }

    /**
     * 转换成一个map，ks key的数组， vs value的数组，如果ks 的长度大于vs，多余的用null作为值，vs多余ks 多余的vs丢弃
     *
     * @param ks
     * @param vs
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> HashMap<K, V> asMap(List<K> ks, List<V> vs) {
        if (isEmpty(ks)) {
            return new HashMap<>();
        }

        if (vs == null) {
            vs = Collections.emptyList();
        }

        HashMap<K, V> map = new HashMap(ks.size() << 1);
        for (int i = 0; i < ks.size(); i++) {
            if (i < vs.size()) {
                map.put(ks.get(i), vs.get(i));
            } else {
                map.put(ks.get(i), null);
            }
        }

        return map;
    }

    /**
     * 转换成一个map，list元素视为： key value 这样循环
     *
     * @param kvs
     * @return
     */
    public static HashMap asMap(List kvs) {
        if (isEmpty(kvs)) {
            return new HashMap<>();
        }

        HashMap map = new HashMap(kvs.size());
        for (int i = 0; i < kvs.size(); i++) {
            if (i < kvs.size()) {
                map.put(kvs.get(i), kvs.get(++i));
            } else {
                map.put(kvs.get(i), null);
            }
        }

        return map;
    }

    /**
     * 集合为空： c == null || c.size < 1 返回true
     *
     * @param c
     * @return
     */
    public static boolean isEmpty(Collection c) {
        return c == null || c.isEmpty();
    }

    /**
     * 集合非空： c != null && c.size > 0 返回true
     *
     * @param c
     * @return
     */
    public static boolean notEmpty(Collection c) {
        return !isEmpty(c);
    }

    /**
     * Map 空： c == null || c.size = 0 返回true
     *
     * @param c
     * @return
     */
    public static boolean isEmpty(Map c) {
        return null == c || c.isEmpty();
    }

    /**
     * Map非空： c != null && c.size > 0 返回true
     *
     * @param c
     * @return
     */
    public static boolean notEmpty(Map c) {
        return null != c && !c.isEmpty();
    }
}
