package com.czyl.framework.web.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 	解决异步调用的时候token传递问题
 * @author tanghx
 *
 */
public class TokenContextHolder {

	private static final ThreadLocal<String> CONTEXT_HOLDER = new TransmittableThreadLocal<>();

	public static void set(String token) {
		CONTEXT_HOLDER.set(token);
	}
	
	public static String get() {
		return CONTEXT_HOLDER.get();
	}
	
	public static void remove() {
		CONTEXT_HOLDER.remove();
	}
}
