package com.czyl.framework.plugin;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.List;
import java.util.Map;

/**
 * @author hhhcccggg
 * @Date 2022/3/14 9:21
 * @Description TODO
 **/

public class DataContextHolder {

    private static final ThreadLocal<List<String>> CONTEXT_HOLDER = new TransmittableThreadLocal();


    public static List<String> getDataColumn() {
        return CONTEXT_HOLDER.get();

    }

    public static void setDataColumn(List<String> map) {
        CONTEXT_HOLDER.set(map);

    }

    public static void remove() {
        CONTEXT_HOLDER.remove();
    }
}
