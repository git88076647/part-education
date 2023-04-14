package com.czyl.framework.config;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.czyl.framework.properties.FileServerProperties;
import com.czyl.project.system.domain.SysFile;
import com.czyl.project.system.service.impl.AbstractFileService;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;


/**
 * FastDFS配置
 *
 * @author tanghx
 */
@Configuration
@ConditionalOnProperty(name = "czyl.filesystem.type", havingValue = "fastdfs")
public class FastdfsAutoConfigure {
    @Autowired
    private FileServerProperties fileProperties;

    @Service
    public class FastdfsServiceImpl extends AbstractFileService {
        @Autowired
        private FastFileStorageClient storageClient;

        @Override
        protected String fileType() {
            return fileProperties.getType();
        }

        @Override
        protected void uploadFile(MultipartFile file, SysFile sysFile) throws Exception {
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
            sysFile.setUrl("http://" + fileProperties.getFdfs().getWebUrl() + "/" + storePath.getFullPath());
            sysFile.setPath(storePath.getFullPath());
        }

        @Override
        protected boolean deleteFile(SysFile sysFile) {
            if (sysFile != null &&StringUtils.isNotBlank(sysFile.getPath())) {
                StorePath storePath = StorePath.parseFromUrl(sysFile.getPath());
                storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
            }
            return true;
        }
	
    }
}
