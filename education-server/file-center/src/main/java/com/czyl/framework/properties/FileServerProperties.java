package com.czyl.framework.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author tanghx
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "czyl.filesystem")
@RefreshScope
public class FileServerProperties {
    /**
     * 为以下3个值，指定不同的自动化配置
     * qiniu：七牛oss
     * aliyun：阿里云oss
     * fastdfs：本地部署的fastDFS
     * localfs: 本地文件存储
     */
    private String type;

    /**
     * oss配置
     */
    OssProperties oss = new OssProperties();

    /**
     * fastDFS配置
     */
    FdfsProperties fdfs = new FdfsProperties();
    
    /**
     * localfs 配置
     */
    LocalFileSystemProperties localfs= new LocalFileSystemProperties();

    AwsS3Properties awss3s= new AwsS3Properties();
}
