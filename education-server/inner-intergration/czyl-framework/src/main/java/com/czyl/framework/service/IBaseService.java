package com.czyl.framework.service;


import cn.hutool.core.bean.BeanUtil;

import java.util.List;

/**
 * 基础常用接口
 *
 * @Description:
 * @Package: com.czyl.framework
 * @ClassName: IBaseService
 * @Author: tanghx
 * @Date: 2021/4/9 10:04
 * @Version: 1.0
 */
public interface IBaseService<T> {


    /**
     * 根据ids 查询档案
     *
     * @param ids
     * @return
     */
    List<T> selectByIds(List<Long> ids);

    default String getName(T obj) {
        Object value = BeanUtil.getFieldValue(obj, "name");
        return value == null ? null : value.toString();
    }

    default String getCode(T obj) {
        Object value = BeanUtil.getFieldValue(obj, "code");
        return value == null ? null : value.toString();
    }

}
