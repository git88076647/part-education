package com.czyl.project.common;

import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.ServletUtils;
import com.czyl.common.utils.file.FileUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.system.domain.SysFile;
import com.czyl.project.system.service.IFileService;
import com.czyl.project.system.service.ISysFileService;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 通用请求处理
 *
 * @author tanghx
 */
@RestController
public class CommonController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    IFileService fileService;

    @Autowired
    private ISysFileService sysFileService;

    /**
     * 通用下载请求
     *
     * @param fileId 文件ID
     * @param delete   是否删除
     * @throws
     */
    @GetMapping("/common/download/{fileId}")
    @Log(title = "文件管理", businessType = BusinessType.DOWNLOAD)
    public void downloadFile(@PathVariable Long fileId, Boolean delete) {
        HttpServletResponse response = ServletUtils.getResponse();
        HttpServletRequest request = ServletUtils.getRequest();
        try {
            SysFile file = sysFileService.selectSysFileById(fileId);
            if (file == null) {
                throw new CustomException("文件不存在!");
            }
            if (!FileUtils.isValidFilename(file.getName())) {
                throw new CustomException(String.format("文件名称(%s)非法，不允许下载。 ", file.getName()));
            }
            boolean fullUrl = file.getUrl() != null && (file.getUrl().startsWith("//:") || file.getUrl().startsWith("http//:") || file.getUrl().startsWith("https//:"));
            if (fullUrl) {
                response.sendRedirect(file.getUrl());
                if (delete != null && delete) {
                    fileService.delete(fileId);
                }
                return;
            }

            String realFileName = System.currentTimeMillis() + file.getName();
            String filePath = fileService.getAbsolutePath(file);
            response.setCharacterEncoding("utf-8");
            if (file != null && "Y".equals(file.getIsimg())) {
                response.setContentType(file.getContenttype());
            } else {
                response.setContentType("multipart/form-data");
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "No-cache");
                response.setDateHeader("Expires", 0);
                response.setHeader("Content-Disposition", "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, realFileName));
            }
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete != null && delete) {
                fileService.delete(fileId);
            }
        } catch (IOException e) {
            log.warn("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     *
     * @param file     文件
     * @param fileName 文件名
     * @param srcId    来源ID
     * @param srcType  来源类型
     * @param perms    查询下载文件所需权限 (默认只要登录即可查询下载)
     * @param expire   失效时间(秒)
     * @return
     * @throws Exception
     */
    @PostMapping("/common/upload")
    @Log(title = "文件管理", businessType = BusinessType.UPLOAD)
    public AjaxResult uploadFile(MultipartFile file, String fileName, Long srcId, String srcType, String perms, Integer expire) throws Exception {
        try {
            if (file == null) {
                throw new CustomException("文件不能为空!");
            }
            SysFile sysFile = new SysFile();
            sysFile.setName(fileName);
            sysFile.setSrcId(srcId);
            sysFile.setSrcType(srcType);
            if (expire != null && expire != 0) {
                sysFile.setExpireTime(DateUtils.addSeconds(new Date(), expire));
            }
            sysFile = fileService.upload(file, sysFile);

            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", sysFile.getName());
            ajax.put("url", sysFile.getUrl());
            return ajax;
        } catch (Exception e) {
            log.warn("上传文件失败", e);
            return AjaxResult.error(e.getMessage());
        }
    }

    @GetMapping("/common/list")
    public TableDataInfo listFile(Long srcId, String srcType) {
        if (srcId == null || srcId.longValue() == 0) {
            throw new CustomException("参数 srcId 不能为空！");
        }
        SysFile sysFile = new SysFile();
        sysFile.setSrcId(srcId);
        sysFile.setSrcType(srcType);
        List<SysFile> list = sysFileService.selectSysFileList(sysFile);
        return getDataTable(list);
    }

}
