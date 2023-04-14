package com.czyl.project.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.czyl.common.utils.spring.SpringUtils;
import com.czyl.framework.uid.UidGenerator;
import com.czyl.framework.utils.FileKit;
import com.czyl.project.system.domain.SysFile;
import com.czyl.project.system.service.IFileService;
import com.czyl.project.system.service.ISysFileService;

import lombok.extern.slf4j.Slf4j;

/**
 * AbstractIFileService 抽取类
 *
 * @author tanghx
 */
@Slf4j
public abstract class AbstractFileService implements IFileService {

	@Autowired
	private ISysFileService sysFileService;

	@Override
	public SysFile upload(MultipartFile file, SysFile sysfile) throws Exception {
		sysfile = FileKit.getSysFile(file, sysfile);
		if(log.isDebugEnabled()) {
			log.debug("获取到上传的文件信息");
		}
//		if (!FileUtils.isValidFilename(file.getOriginalFilename())) {
//			throw new CustomException(StringUtils.format("文件名称[{}]非法，禁止上传。 ", file.getOriginalFilename()));
//		}
//		if (!sysfile.getName().contains(FILE_SPLIT)) {
//			throw new IllegalArgumentException("缺少后缀名");
//		}
		if(sysfile.getFileId() == null || sysfile.getFileId().longValue() <= 0) {
			UidGenerator idGenerator = (UidGenerator) SpringUtils.getBean(UidGenerator.class);
			sysfile.setFileId(idGenerator.getUID());
		}
		uploadFile(file, sysfile);
		if(log.isDebugEnabled()) {
			log.debug("完成文件上传");
		}
		// 设置文件来源
		sysfile.setSource(fileType());
		// 将文件信息保存到数据库
		sysFileService.insertSysFile(sysfile);
		if(log.isDebugEnabled()) {
			log.debug("将文件信息保存到数据库");
		}
		return sysfile;
	}

	/**
	 * 文件来源
	 *
	 * @return
	 */
	protected abstract String fileType();

	/**
	 * 上传文件
	 *
	 * @param file
	 * @param sysFile
	 */
	protected abstract void uploadFile(MultipartFile file, SysFile sysFile) throws Exception;

	/**
	 * 删除文件
	 * 
	 * @param id 文件id
	 */
	@Override
	public void delete(Long fileId) {
		SysFile sysFile = sysFileService.selectSysFileById(fileId);
		if (sysFile != null) {
			sysFileService.deleteSysFileById(sysFile.getFileId());
			this.deleteFile(sysFile);
		}
	}

	/**
	 * 删除文件资源
	 *
	 * @param sysFile
	 * @return
	 */
	protected abstract boolean deleteFile(SysFile sysFile);

	@Override
	public String getAbsolutePath(SysFile sysfile) {
		return null;
	}

}
