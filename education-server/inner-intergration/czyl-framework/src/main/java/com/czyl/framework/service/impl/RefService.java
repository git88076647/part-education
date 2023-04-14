package com.czyl.framework.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.spring.SpringUtils;
import com.czyl.framework.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

/**
 * 参照服务
 * @author: tanghx
 * @date: 2021/4/9 10:52
 */
@Service
public class RefService {
    @Autowired
    ScheduledExecutorService scheduledExecutorService;


    /**
     * 填充实体引用档案
     * 填充值的时候会根据 refField+Name 或 refField+Code 进行填充
     *
     * @param list     需要填充的实体集合
     * @param cls      实现IBaseService接口的对应档案service
     * @param refField 引用对应的字段
     * @return
     */
    public Future<Object> fillAny(List list, Class cls, String... refField) {
        Object service = SpringUtils.getBean(cls);
        if (service instanceof IBaseService) {
            return fillAny(list, (IBaseService) service, refField);
        }
        throw new CustomException(String.format("%s未实现接口IBaseService", cls));
    }

    /**
     * 填充实体引用档案
     * 填充值的时候会根据 refField+Name 或 refField+Code 进行填充
     *
     * @param list     需要填充的实体集合
     * @param itf      实现IBaseService接口的实现
     * @param refField 引用对应的字段
     * @return
     */
    public Future<Object> fillAny(List list, IBaseService itf, String... refField) {
        return scheduledExecutorService.submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                List<Long> ids = new LinkedList<>();

                ids.addAll((List<Long>) list.parallelStream().map(o -> {
                    List<Long> tmp = new LinkedList<>();
                    for (int i = 0; i < refField.length; i++) {
                        Long value = (Long) BeanUtil.getFieldValue(o, refField[i]);
                        if (value != null && value.longValue() > 0) {
                            tmp.add(value);
                        }
                    }
                    return tmp;
                }).flatMap(item -> ((List<Long>) item).stream())
                        .distinct().collect(Collectors.toList()));

                if (CollectionUtil.isEmpty(ids)) {
                    return null;
                }
                Map<Long, Object> refsMap = (Map<Long, Object>) itf.selectByIds(ids).parallelStream().collect(Collectors.toMap(item -> (Long) BeanUtil.getFieldValue(item, "id"), item -> item));
                list.parallelStream().forEach(o -> {
                    Long value;
                    for (int i = 0; i < refField.length; i++) {
                        value = (Long) BeanUtil.getFieldValue(o, refField[i]);
                        if (refsMap.containsKey(value)) {
                            try {
                                BeanUtil.setFieldValue(o, refField[i] + "Name", itf.getName(refsMap.get(value)));
                            } catch (Throwable e) {
                            }
                            try {
                                BeanUtil.setFieldValue(o, refField[i] + "Code", itf.getCode(refsMap.get(value)));
                            } catch (Throwable e) {
                            }
                        }
                    }
                });
                return null;
            }
        });
    }
}
