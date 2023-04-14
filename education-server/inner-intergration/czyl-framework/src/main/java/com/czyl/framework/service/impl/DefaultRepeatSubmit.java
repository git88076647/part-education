package com.czyl.framework.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.czyl.common.utils.ServletUtils;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.redis.RedisCache;
import com.czyl.framework.service.IRepeatSubmit;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 默认实现判断请求url和数据 {intervalTime}秒内是否重复，重复则提示。
 *
 * @author tanghx
 */
@Component
public class DefaultRepeatSubmit implements IRepeatSubmit {
    public final String REPEAT_KEY = "repeatKey::";

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private RedisCache redisCache;

    @Override
    public AjaxResult getRepeatSubmit(HttpServletRequest request, int intervalTime) {
        String requestMethod = ServletUtils.getRequest().getMethod();
        String nowParams = "";
        try {
            if (request.getParameterMap() != null) {
                nowParams = JSON.toJSONString(request.getParameterMap());
            }
            String requestBody = IOUtils.toString(request.getInputStream(), "utf-8");
            if (StringUtils.isNotBlank(requestBody)) {
                nowParams += requestBody;
            }
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (paramsMap != null) {
                nowParams += JSON.toJSONString(request.getParameterMap());
            }
            nowParams = DigestUtil.md5Hex(nowParams);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // key=repeatKey::服务名::url::请求参数
        String url = request.getRequestURI();
        String key = String.format("%s:%s:%s:%s:%S", REPEAT_KEY, requestMethod, url, appName, nowParams);
        if (redisCache.hasKey(key)) {
            return AjaxResult.error("不允许频繁重复提交，请稍后再试！");
        } else {
            redisCache.setCacheObject(key, "1", intervalTime, TimeUnit.SECONDS);
        }
        return null;
    }
}
