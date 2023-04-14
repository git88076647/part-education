package com.czyl.framework.feign;

import com.czyl.framework.feign.fallback.AuthServerClientFallbackFactory;
import com.czyl.framework.feign.fallback.FileServerClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.czyl.common.web.domain.AjaxResult;

/**
 * file-server 的Feign
 * @author tanghx
 *
 */
@FeignClient(value="file-server",fallback= FileServerClientFallbackFactory.class)
public interface FileServerClient {

	/**
	 * @param file 文件
	 * @param fileName 文件名
	 * @param srcId    来源ID
	 * @param srcType  来源类型
	 * @param perms    查询下载文件所需权限 (默认只要登录即可查询下载)
	 * @param expire   失效时间(秒)
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/common/upload", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public AjaxResult uploadFile(@RequestPart(value = "file") MultipartFile file,@RequestParam("fileName") String fileName,@RequestParam("srcId") Long srcId,@RequestParam("srcType") String srcType,@RequestParam("perms") String perms,@RequestParam("expire") int expire);
	
}