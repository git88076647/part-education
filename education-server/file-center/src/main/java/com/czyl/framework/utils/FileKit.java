package com.czyl.framework.utils;

import java.util.Date;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import com.czyl.common.utils.SecurityUtils;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.utils.file.FileUtils;
import com.czyl.common.utils.file.MimeTypeKit;
import com.czyl.project.system.domain.SysFile;

/**
 * 文件工具类
 *
 * @author tanghx
 */
public class FileKit extends FileUtils {
	private FileKit() {
		throw new IllegalStateException("Utility class");
	}

	public static SysFile getSysFile(MultipartFile file, SysFile sysFile) throws Exception {
		if (sysFile == null) {
			sysFile = new SysFile();
		}
		if (file == null) {
			return sysFile;
		}
		if (StringUtils.isBlank(sysFile.getName())) {
			sysFile.setName(file.getOriginalFilename());
		}
		sysFile.setContenttype(file.getContentType());
		sysFile.setIsimg(sysFile.getContenttype().startsWith("image/") ? "Y" : "N");
		if (sysFile.getName().lastIndexOf(".") <= 0 ) {
			String extension = MimeTypeKit.getExtension(sysFile.getContenttype());
			sysFile.setName(String.format("%s.%s", sysFile.getName(),extension));
		}
		sysFile.setSize(file.getSize());
		sysFile.setCreateTime(new Date());
		sysFile.setCreateBy(SecurityUtils.getUserId2());
		sysFile.setCreateByCode(SecurityUtils.getUserCode2());
		sysFile.setCreateByName(SecurityUtils.getUserName2());
		return sysFile;
	}

}
