package com.czyl.framework.ref.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.StringUtils;
import com.czyl.framework.ref.domain.SysRefdoc;
import com.czyl.framework.ref.mapper.SysRefdocMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 *  引用service
 * @author tanghx
 * @date 2021/1/11 16:00
 */
@Service
@Slf4j
public class RefdocService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    SysRefdocMapper mapper;
    public static String SQL_PATTERN = "[a-zA-Z0-9_]+";

    public List<String> check(Object obj, String tableName, boolean throwMsg) {
        SysRefdoc query = new SysRefdoc();
        query.setSrcTable(tableName);
        List<SysRefdoc> list = mapper.selectList(query);
        if (list == null || list.isEmpty()) {
            log.info("{}没有引用检查规则", tableName);
            return ListUtil.empty();
        }
        List<String> errList = new LinkedList<>();
        for (SysRefdoc refdoc : list) {
            if (StringUtils.isBlank(refdoc.getRefTable()) || StringUtils.isBlank(refdoc.getRefField()) || StringUtils.isBlank(refdoc.getSrcField())) {
                log.error("{}引用检查规则错误", tableName);
                continue;
            }
            refdoc.setSrcField(refdoc.getSrcField().trim());
            refdoc.setRefTable(refdoc.getRefTable().trim());
            refdoc.setRefField(refdoc.getRefField().trim());
            if (!refdoc.getRefTable().matches(SQL_PATTERN) || !refdoc.getRefField().matches(SQL_PATTERN)) {
                log.error("{}引用检查规则{}存在SQL注入", tableName, refdoc.getId());
                continue;
            }
            Object srcValue = BeanUtil.getFieldValue(obj, refdoc.getSrcField());
            if (srcValue == null || StringUtils.isBlank(srcValue.toString())) {
                log.info("{}引用检查规则{}无引用数据", tableName, refdoc.getId());
                continue;
            }
            Map<String, Object> data = null;
            try {
                refdoc.setSrcValue(srcValue);
                data = mapper.checkDataRef(refdoc);
            } catch (Throwable e) {
                log.error("{}引用检查规则{}配置错误,导致SQL异常", tableName, refdoc.getId());
                log.error("检查数据引用异常", e);
                throw new CustomException("引用检查规则配置错误");
            }
            if (data == null) {
                continue;
            }
            // key 转换小写
            data = data.entrySet().parallelStream().collect(Collectors.toMap(o -> o.getKey().toLowerCase(), o -> o.getValue()));
            //存在，则已被引用
            if (StringUtils.isBlank(refdoc.getMsg())) {
                if (throwMsg) {
                    throw new CustomException("数据已被引用");
                } else {
                    errList.add("数据已被引用");
                }
            } else {
                //格式化消息
                String msg = StrUtil.format(refdoc.getMsg(), data);
                log.info(msg);
                if (throwMsg) {
                    throw new CustomException(msg);
                } else {
                    errList.add(msg);
                }
            }
        }
        return errList;
    }
}
