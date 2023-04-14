package com.czyl.common.utils;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.NumberUtil;
import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.spring.SpringUtils;
import com.czyl.framework.feign.AuthServerClient;
import com.czyl.project.system.domain.SysBilltplItem;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  单据工具
 * @author tanghx
 * @date 2021/1/19 19:05
 */
public class BillUtils {

    List<SysBilltplItem> items;

    private BillUtils() {
    }

    public static BillUtils create(String billtplcode) {
        BillUtils bu = new BillUtils();
        bu.items = SpringUtils.getBean(AuthServerClient.class).getBillItems(billtplcode);
        return bu;
    }

    /**
     * 校验字段必填
     * 有必填为空则抛出异常
     *
     * @param obj
     * @return
     */
    public void validateNotNull(Object obj) {
        validateNotNull(true, obj);
    }

    /**
     * 校验字段必填
     *
     * @param throwFlag
     * @param obj
     * @return
     */
    public List<String> validateNotNull(boolean throwFlag, Object obj) {
        return items.parallelStream().filter(o -> Boolean.TRUE.equals(o.getNotNull())).map(o -> {
            Object value = BeanUtil.getFieldValue(obj, o.getProp());
            boolean error = false;
            if (value == null || StringUtils.isBlank(value.toString())) {
                error = true;
            }
            //参照
            if ("ref".equals(o.getType())) {
                if (NumberUtil.isNumber(value.toString()) && Long.valueOf(value.toString()) == 0L) {
                    error = true;
                }
            }
            if (error) {
                String msg = String.format("%s必填", o.getLabel());
                if (throwFlag) {
                    throw new CustomException(msg);
                } else {
                    return msg;
                }
            }
            return null;
        }).filter(o -> o != null).collect(Collectors.toList());
    }


}
