package com.czyl.eureka.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedList;
import java.util.List;

/**
 * @author tanghx
 * @version 1.0
 * @date 2021/10/16 23:24
 */
@Data
public class AlertProperties {
    private boolean enabled = false;
    private List<String> to = new LinkedList<>();
    private String subject;
    private String content;

    private long minTime = 60;
    private boolean html;


}
