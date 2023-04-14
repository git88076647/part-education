package com.czyl.framework.plugin;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 *
 * @author tanghx
 */
public class TenantContextHolder {
    private static final ThreadLocal<Long> CONTEXT = new TransmittableThreadLocal<>();

    public static void set(Long orgId) {
        CONTEXT.set(orgId);
    }

    public static Long get() {
        return CONTEXT.get();
    }

    public static void remove() {
        CONTEXT.remove();
    }


}
