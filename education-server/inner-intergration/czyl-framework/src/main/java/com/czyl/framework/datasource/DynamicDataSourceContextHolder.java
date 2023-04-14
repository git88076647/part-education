package com.czyl.framework.datasource;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据源切换处理
 * 
 * @author tanghx
 */
@Slf4j
public class DynamicDataSourceContextHolder {

	/**
	 * 使用ThreadLocal维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
	 * 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
	 */
	private static final ThreadLocal<String> CONTEXT_HOLDER = new TransmittableThreadLocal<>();

	private static final ThreadLocal<Boolean> CONTEXT_HOLDER_FLAG = new TransmittableThreadLocal<>();

	/**
	 * 设置数据源的变量
	 */
	public static void setDataSourceType(String dsType) {
		CONTEXT_HOLDER.set(dsType);
	}

	/**
	 * 获得数据源的变量
	 */
	public static String getDataSourceType() {
		return getEnableChange() ? CONTEXT_HOLDER.get() : null;
	}

	/**
	 * 清空数据源变量
	 */
	public static void clearDataSourceType() {
		CONTEXT_HOLDER.remove();
	}
	
	/**
	 * 	设置是否启用切换数据源
	 * @param enable
	 */
	public static void setEnableChange(boolean enable) {
		CONTEXT_HOLDER_FLAG.set(enable);
	}
	
	/**
	 * 	是否启用切换数据源
	 */
	public static boolean getEnableChange() {
		return CONTEXT_HOLDER_FLAG.get() == null ? true : CONTEXT_HOLDER_FLAG.get();
	}
	/**
	 * 	清空设置
	 */
	public static void clearEnableChange() {
		CONTEXT_HOLDER_FLAG.remove();
	}
	
	
}
