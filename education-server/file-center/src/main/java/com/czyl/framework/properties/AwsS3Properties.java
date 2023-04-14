package com.czyl.framework.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tanghx
 */
@Setter
@Getter
public class AwsS3Properties {
    /**
     * 密钥key
     */
    private String accessKey;
    /**
     * 密钥密码
     */
    private String accessKeySecret;
    /**
     * 端点
     */
    private String endpoint;
    /**
     * bucket名称
     */
    private String bucketName;
    /**
     * 说明
     */
    private String domain;

    private String rootPath;
}
