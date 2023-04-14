package com.czyl.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.czyl.framework.properties.FileServerProperties;
import com.czyl.project.system.domain.SysFile;
import com.czyl.project.system.service.impl.AbstractFileService;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * 七牛云配置
 *
 * @author tanghx
 */
@Configuration
@ConditionalOnProperty(name = "czyl.filesystem.type", havingValue = "qiniu")
public class QiniuOSSAutoConfigure {
	@Autowired
	private FileServerProperties fileProperties;

	/**
	 * 华东机房
	 */
	@Bean
	public com.qiniu.storage.Configuration qiniuConfig() {
		return new com.qiniu.storage.Configuration(Zone.zone2());
	}

	/**
	 * 构建一个七牛上传工具实例
	 */
	@Bean
	public UploadManager uploadManager() {
		return new UploadManager(qiniuConfig());
	}

	/**
	 * 认证信息实例
	 *
	 * @return
	 */
	@Bean
	public Auth auth() {
		return Auth.create(fileProperties.getOss().getAccessKey(), fileProperties.getOss().getAccessKeySecret());
	}

	/**
	 * 构建七牛空间管理实例
	 */
	@Bean
	public BucketManager bucketManager() {
		return new BucketManager(auth(), qiniuConfig());
	}

	@Service
	public class QiniuOssServiceImpl extends AbstractFileService {
		@Autowired
		private UploadManager uploadManager;
		@Autowired
		private BucketManager bucketManager;
		@Autowired
		private Auth auth;

		@Override
		protected String fileType() {
			return fileProperties.getType();
		}

		@Override
		protected void uploadFile(MultipartFile file, SysFile sysFile) throws Exception {
			// 调用put方法上传
			uploadManager.put(file.getBytes(), sysFile.getName(),
					auth.uploadToken(fileProperties.getOss().getBucketName()));
			sysFile.setUrl(fileProperties.getOss().getEndpoint() + "/" + sysFile.getName());
			sysFile.setPath(fileProperties.getOss().getEndpoint() + "/" + sysFile.getName());
		}

		@Override
		protected boolean deleteFile(SysFile sysFile) {
			try {
				Response response = bucketManager.delete(fileProperties.getOss().getBucketName(), sysFile.getPath());
				int retry = 0;
				while (response.needRetry() && retry++ < 3) {
					response = bucketManager.delete(fileProperties.getOss().getBucketName(), sysFile.getPath());
				}
			} catch (QiniuException e) {
				return false;
			}
			return true;
		}
		
	
	}
}
