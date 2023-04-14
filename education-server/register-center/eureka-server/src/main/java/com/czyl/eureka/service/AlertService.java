package com.czyl.eureka.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailUtil;
import com.czyl.eureka.config.AlertConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author tanghx
 * @version 1.0
 * @date 2021/10/17 13:30
 */
@Slf4j
@Service
@EnableAsync
public class AlertService {

    @Autowired(required = false)
    AlertConfig alertConfig;

    static Map<String, Long> UP_TIME = MapUtil.newConcurrentHashMap();

    static Map<String, Long> DOWN_TIME = MapUtil.newConcurrentHashMap();

    @Async
    public void up(EurekaInstanceRegisteredEvent event) {
        String key = event.getInstanceInfo().getInstanceId().intern();
        if (alertConfig != null && alertConfig.getUp().isEnabled()) {
            synchronized (key) {
                if (UP_TIME.containsKey(key) && (System.currentTimeMillis() - UP_TIME.get(key)) / 1000 < alertConfig.getUp().getMinTime()) {
                    log.info("服务：{} 实例:{} 注册成功距离上次发送邮件时间太短，不发送邮件", event.getInstanceInfo().getAppName(), event.getInstanceInfo().getInstanceId());
                    return;
                }
                UP_TIME.put(key, System.currentTimeMillis());
                log.info("服务：{} 实例:{} 注册成功，开始发送邮件", event.getInstanceInfo().getAppName(), event.getInstanceInfo().getInstanceId());
                Map<?, ?> params = MapUtil.builder().put("appName", event.getInstanceInfo().getAppName())
                        .put("instanceId", event.getInstanceInfo().getInstanceId()).put("now", DateUtil.now()).build();
                MailUtil.send(alertConfig.getUp().getTo(), StrUtil.format(alertConfig.getUp().getSubject(), params), StrUtil.format(alertConfig.getUp().getContent(), params), alertConfig.getUp().isHtml());
                log.info("服务：{} 实例:{} 注册成功，发送邮件成功", event.getInstanceInfo().getAppName(), event.getInstanceInfo().getInstanceId());
            }
        }
    }

    @Async
    public void down(EurekaInstanceCanceledEvent event) {
        String key = event.getServerId().intern();
        if (alertConfig != null && alertConfig.getDown().isEnabled()) {
            synchronized (key) {
                if (DOWN_TIME.containsKey(key) && (System.currentTimeMillis() - DOWN_TIME.get(key)) / 1000 < alertConfig.getDown().getMinTime()) {
                    log.info("服务：{} 实例:{} 下线,距离上次发送邮件时间太短，不发送邮件", event.getAppName(), event.getServerId());
                    return;
                }
                DOWN_TIME.put(key, System.currentTimeMillis());
                log.info("服务：{} 实例:{} 下线，开始发送邮件", event.getAppName(), event.getServerId());
                Map<?, ?> params = MapUtil.builder().put("appName", event.getAppName())
                        .put("instanceId", event.getServerId()).put("now", DateUtil.now()).build();
                MailUtil.send(alertConfig.getDown().getTo(), StrUtil.format(alertConfig.getDown().getSubject(), params), StrUtil.format(alertConfig.getDown().getContent(), params), alertConfig.getDown().isHtml());
                log.info("服务：{} 实例:{} 下线，发送邮件成功", event.getAppName(), event.getServerId());
            }
        }
    }

}
