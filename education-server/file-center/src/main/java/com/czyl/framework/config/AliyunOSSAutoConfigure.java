package com.czyl.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.czyl.framework.properties.FileServerProperties;
import com.czyl.project.system.domain.SysFile;
import com.czyl.project.system.service.impl.AbstractFileService;

/**
 * 阿里云配置
 *
 * @author tanghx
 */
@Configuration
@ConditionalOnProperty(name = "czyl.filesystem.type", havingValue = "aliyun")
public class AliyunOSSAutoConfigure {
	@Autowired
	private FileServerProperties fileProperties;

	/**
	 * 阿里云文件存储client 只有配置了aliyun.oss.access-key才可以使用
	 */
	@Bean
	public OSSClient ossClient() {
		OSSClient ossClient = new OSSClient(fileProperties.getOss().getEndpoint(), new DefaultCredentialProvider(fileProperties.getOss().getAccessKey(), fileProperties.getOss().getAccessKeySecret()), null);
		return ossClient;
	}

	@Service
	public class AliyunOssServiceImpl extends AbstractFileService {
		@Autowired
		private OSSClient ossClient;

		@Override
		protected String fileType() {
			return fileProperties.getType();
		}

		@Override
		protected void uploadFile(MultipartFile file, SysFile sysFile) throws Exception {
			ossClient.putObject(fileProperties.getOss().getBucketName(), sysFile.getName(), file.getInputStream());
			sysFile.setUrl(fileProperties.getOss().getDomain() + "/" + sysFile.getName());
		}

		@Override
		protected boolean deleteFile(SysFile sysFile) {
			ossClient.deleteObject(fileProperties.getOss().getBucketName(), sysFile.getName());
			return true;
		}

	}
}
