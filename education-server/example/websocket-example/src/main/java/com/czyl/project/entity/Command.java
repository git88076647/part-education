package com.czyl.project.entity;

import lombok.Data;

/**
 * @author tanghx
 * @version 1.0
 * @date 2022/3/30 11:02
 */
@Data
public class Command {
    /**
     * 指令
     */
    String action;
    /**
     * 发送人
     */
    String from;
    /**
     * 接收人
     */
    String to;
    /**
     * 业务数据
     */
    Object data;
}
