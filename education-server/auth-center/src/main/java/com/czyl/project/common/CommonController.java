package com.czyl.project.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.czyl.common.utils.file.FileUploadUtils;
import com.czyl.common.utils.file.FileUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.config.CzylConfig;
import com.czyl.framework.config.ServerConfig;

/**
 * 通用请求处理
 * 
 * @author tanghx
 */
@RestController
public class CommonController {
	private static final Logger log = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private ServerConfig serverConfig;

	/**
	 * 通用下载请求
	 * 
	 * @param fileName 文件名称
	 * @param delete   是否删除
	 */
	@GetMapping("common/download")
	@Log(title = "文件管理", businessType = BusinessType.DOWNLOAD)
	public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
		try {
			if (!FileUtils.isValidFilename(fileName)) {
				throw new Exception(String.format("文件名称(%s)非法，不允许下载。 ", fileName));
			}
			String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
			String filePath = CzylConfig.getDownloadPath() + fileName;

			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, realFileName));
			FileUtils.writeBytes(filePath, response.getOutputStream());
			if (delete) {
				FileUtils.deleteFile(filePath);
			}
		} catch (Exception e) {
			log.error("下载文件失败", e);
		}
	}

	/**
	 * 通用上传请求
	 */
	@PostMapping("/common/upload")
	@Log(title = "文件管理", businessType = BusinessType.UPLOAD)
	public AjaxResult uploadFile(MultipartFile file) throws Exception {
		try {
			// 上传文件路径
			String filePath = CzylConfig.getUploadPath();
			// 上传并返回新文件名称
			String fileName = FileUploadUtils.upload(filePath, file);
			String url = serverConfig.getUrl() + fileName;
			AjaxResult ajax = AjaxResult.success();
			ajax.put("fileName", fileName);
			ajax.put("url", url);
			return ajax;
		} catch (Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}
}
