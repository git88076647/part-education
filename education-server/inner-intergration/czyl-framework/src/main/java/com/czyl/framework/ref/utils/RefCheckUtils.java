package com.czyl.framework.ref.utils;

import cn.hutool.core.collection.ListUtil;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.utils.spring.SpringUtils;
import com.czyl.framework.cache.TableInfoCache;
import com.czyl.framework.ref.service.RefdocService;
import com.czyl.framework.table.domain.TableInfo;

import java.util.List;

/**
 *
 * 引用检查
 * @author tanghx
 * @date 2020/1/11 15:25
 */
public class RefCheckUtils {

    /**
     * 检查obj数据是否被其他档案或业务数据引用
     *
     * @param obj      数据对象
     * @return
     */
    public static List<String> check(Object obj) {
        return check(obj,true);
    }

    /**
     * 检查obj数据是否被其他档案或业务数据引用
     *
     * @param obj      数据对象
     * @param throwMsg 是否抛出异常消息
     * @return
     */
    public static List<String> check(Object obj, boolean throwMsg) {
        if (obj == null) {
            return ListUtil.empty();
        }
        TableInfoCache infoCache = SpringUtils.getBean(TableInfoCache.class);
        TableInfo tableInfo = infoCache.getTableInfo(obj.getClass());
        if (tableInfo == null || StringUtils.isBlank(tableInfo.getTableName())) {
            return ListUtil.empty();
        }
        return SpringUtils.getBean(RefdocService.class).check(obj, tableInfo.getTableName(), throwMsg);
    }
}
