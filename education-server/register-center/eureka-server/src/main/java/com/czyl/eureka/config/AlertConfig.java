package com.czyl.eureka.config;

import cn.hutool.extra.mail.MailUtil;
import com.czyl.eureka.config.properties.AlertProperties;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author tanghx
 * @version 1.0
 * @date 2021/10/16 23:21
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "czyl.alert")
public class AlertConfig {

    AlertProperties up = new AlertProperties();

    AlertProperties down = new AlertProperties();


}
