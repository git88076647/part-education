package com.czyl.common.utils;

import com.czyl.common.utils.spring.SpringUtils;
import com.czyl.framework.uid.UidGenerator;

/**
 * 获取20位主键
 * @author tanghx
 *
 */
public class IdUtils {

	static UidGenerator idGenerator = null;

	private static synchronized UidGenerator getGenerator() {
		if (idGenerator == null) {
			idGenerator = (UidGenerator) SpringUtils.getBean(UidGenerator.class);
		}
		return idGenerator;
	}

	public static Long getUid() {
		return getGenerator().getUID();
	}

}
