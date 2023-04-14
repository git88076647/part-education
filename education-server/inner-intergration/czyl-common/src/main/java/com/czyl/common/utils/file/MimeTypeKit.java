package com.czyl.common.utils.file;

/**
 * 媒体类型工具类
 * 
 * @author tanghx
 */
public class MimeTypeKit
{
    public static final String IMAGE_PNG = "image/png";

    public static final String IMAGE_JPG = "image/jpg";

    public static final String IMAGE_JPEG = "image/jpeg";

    public static final String IMAGE_BMP = "image/bmp";

    public static final String IMAGE_GIF = "image/gif";
    
    public static final String TEXT_HTML = "text/html";
    
    public static final String TEXT_TXT = "text/plain";
    
    public static final String TEXT_XML = "text/xml";
    
    public static final String WORD_DOC = "application/msword";

    public static final String WORD_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    
    public static final String EXCEL_XLS = "application/vnd.ms-excel";
    
    public static final String EXCEL_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    
    public static final String PPT_PPT = "application/vnd.ms-powerpoint";
    
    public static final String PPT_PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
   
    public static final String PDF = "application/pdf";

    public static final String ZIP = "application/zip";
    
    public static final String[] IMAGE_EXTENSION = { "bmp", "gif", "jpg", "jpeg", "png" };

    public static final String[] FLASH_EXTENSION = { "swf", "flv" };

    public static final String[] MEDIA_EXTENSION = { "swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg",
            "asf", "rm", "rmvb" };

    public static final String[] DEFAULT_ALLOWED_EXTENSION = {
            // 图片
            "bmp", "gif", "jpg", "jpeg", "png",
            // word excel powerpoint
            "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
            // 压缩文件
            "rar", "zip", "gz", "bz2",
            // pdf
            "pdf" };

    public static String getExtension(String prefix)
    {
        switch (prefix)
        {
            case IMAGE_PNG:
                return "png";
            case IMAGE_JPG:
                return "jpg";
            case IMAGE_JPEG:
                return "jpeg";
            case IMAGE_BMP:
                return "bmp";
            case IMAGE_GIF:
                return "gif";
            case TEXT_HTML:
            	return "html";
            case TEXT_XML:
            	return "xml";
            case TEXT_TXT:
            	return "txt";
            case WORD_DOC:
            	return "doc";
            case WORD_DOCX:
            	return "docx";
            case EXCEL_XLS:
            	return "xls";
            case EXCEL_XLSX:
            	return "xlsx";
            case PPT_PPT:
            	return "ppt";
            case PPT_PPTX:
            	return "pptx";
            case PDF:
            	return "pdf";
            case ZIP:
            	return "zip";
            default:
                return "";
        }
    }
}
