package com.czyl.project.system.service;

import org.springframework.web.multipart.MultipartFile;

import com.czyl.project.system.domain.SysFile;

/**
 * 文件service
 * @author tanghx
 *
 */
public interface IFileService {

	public static final String FILE_SPLIT = ".";

	/**
	 * 上传文件
	 * 
	 * @param file
	 * @param sysfile
	 * @return
	 * @throws Exception
	 */
	SysFile upload(MultipartFile file, SysFile sysfile) throws Exception;

	/**
	 * 删除文件
	 * 
	 * @param fileId
	 */
	void delete(Long fileId);

	/**
	 * 获取文件绝对地址
	 * 
	 * @param sysfile
	 * @return
	 */
	String getAbsolutePath(SysFile sysfile);
}
