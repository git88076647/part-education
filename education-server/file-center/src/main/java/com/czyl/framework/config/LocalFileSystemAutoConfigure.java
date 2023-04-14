package com.czyl.framework.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.czyl.framework.properties.FileServerProperties;
import com.czyl.framework.utils.FileKit;
import com.czyl.framework.utils.FileUploadKit;
import com.czyl.project.system.domain.SysFile;
import com.czyl.project.system.service.impl.AbstractFileService;

/**
 * 本地文件系统
 *
 * @author tanghx
 */
@Configuration
@ConditionalOnProperty(name = "czyl.filesystem.type", havingValue = "localfs")
public class LocalFileSystemAutoConfigure {
    @Autowired
    private FileServerProperties fileProperties;

    @Service
    public class LocalFileSystemServiceImpl extends AbstractFileService {
       

        @Override
        protected String fileType() {
            return fileProperties.getType();
        }

        @Override
        protected void uploadFile(MultipartFile file, SysFile sysFile) throws Exception {
        	String path = FileUploadKit.upload(fileProperties.getLocalfs().getRootPath(), file);
        	sysFile.setPath(path);
        	sysFile.setUrl("/common/download/"+sysFile.getFileId());
        }

        @Override
        protected boolean deleteFile(SysFile sysFile) {
        	FileKit.deleteFile(sysFile.getPath());
            return true;
        }

		@Override
		public String getAbsolutePath(SysFile sysfile) {
			return fileProperties.getLocalfs().getRootPath()+File.separator+sysfile.getPath();
		}

		
		
    }
}
