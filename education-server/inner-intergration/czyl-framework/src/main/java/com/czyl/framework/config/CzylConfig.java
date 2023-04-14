package com.czyl.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 * 
 * @author tanghx
 */
@Component
@ConfigurationProperties(prefix = "czyl")
public class CzylConfig {
	/** 项目名称 */
	private static String name;

	private static String description;

	/** 版本 */
	private static String version;

	/** 版权年份 */
	private static String copyrightYear;

	/** 实例演示开关 */
	private boolean demoEnabled;

	/** 上传路径 */
	private static String profile;

	/** 获取地址开关 */
	private static boolean addressEnabled;

	/**
	 * 已登录或未登陆都能访问的url
	 */
	private static String permitAllUrls;
	
	
	public static String getName() {
		return name;
	}

	public void setName(String name) {
		CzylConfig.name = name;
	}

	public static String getVersion() {
		return CzylConfig.version;
	}

	public void setVersion(String version) {
		CzylConfig.version = version;
	}

	public String getCopyrightYear() {
		return copyrightYear;
	}

	public void setCopyrightYear(String copyrightYear) {
		CzylConfig.copyrightYear = copyrightYear;
	}

	public boolean isDemoEnabled() {
		return demoEnabled;
	}

	public void setDemoEnabled(boolean demoEnabled) {
		this.demoEnabled = demoEnabled;
	}

	public static String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		CzylConfig.profile = profile;
	}

	public static boolean isAddressEnabled() {
		return addressEnabled;
	}

	public void setAddressEnabled(boolean addressEnabled) {
		CzylConfig.addressEnabled = addressEnabled;
	}
	
	public static String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		CzylConfig.description = description;
	}

	/**
	 * 获取头像上传路径
	 */
	public static String getAvatarPath() {
		return getProfile() + "/avatar";
	}

	/**
	 * 获取下载路径
	 */
	public static String getDownloadPath() {
		return getProfile() + "/download/";
	}

	/**
	 * 获取上传路径
	 */
	public static String getUploadPath() {
		return getProfile() + "/upload";
	}

	public static String getPermitAllUrls() {
		return permitAllUrls;
	}

	public void setPermitAllUrls(String permitAllUrls) {
		CzylConfig.permitAllUrls = permitAllUrls;
	}
	
	
}