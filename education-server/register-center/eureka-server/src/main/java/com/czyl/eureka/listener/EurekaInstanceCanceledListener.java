package com.czyl.eureka.listener;


import cn.hutool.extra.mail.MailUtil;
import com.czyl.eureka.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRenewedEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaRegistryAvailableEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * 用于监听eureka服务停机通知
 *
 * @author tanghx
 */
@Slf4j
@Configuration
public class EurekaInstanceCanceledListener implements ApplicationListener {

    @Autowired
    AlertService alertService;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        // 服务挂掉事件
        if (applicationEvent instanceof EurekaInstanceCanceledEvent) {
            EurekaInstanceCanceledEvent event = (EurekaInstanceCanceledEvent) applicationEvent;
            log.info("服务：{} 实例：{} 下线。。。", event.getAppName(), event.getServerId());
            alertService.down(event);
        }
        if (applicationEvent instanceof EurekaInstanceRegisteredEvent) {
            EurekaInstanceRegisteredEvent event = (EurekaInstanceRegisteredEvent) applicationEvent;
            log.info("服务：{} 实例:{}注册成功。。。", event.getInstanceInfo().getAppName(), event.getInstanceInfo().getInstanceId());
            alertService.up(event);
        }
        if (applicationEvent instanceof EurekaInstanceRenewedEvent) {
            EurekaInstanceRenewedEvent event = (EurekaInstanceRenewedEvent) applicationEvent;
            log.debug("心跳检测服务：{}", event);
        }
        if (applicationEvent instanceof EurekaRegistryAvailableEvent) {
            log.info("服务 Aualiable。。");
        }

    }


}

