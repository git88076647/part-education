package com.czyl.common.utils;

/**
 * 处理并记录日志文件
 * 
 * @author tanghx
 */
public class LogUtils {
	
	/** [ msg ] */
	public static String getBlock(Object msg) {
		if (msg == null) {
			msg = "";
		}
		return "[" + msg.toString() + "]";
	}
}
