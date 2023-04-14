package com.czyl.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.czyl.common.constant.HttpStatus;
import com.czyl.common.exception.CustomException;
import com.czyl.framework.config.CustomBCryptPasswordEncoder;
import com.czyl.framework.security.LoginUser;

/**
 * 安全服务工具类
 * 
 * @author tanghx
 */
public class SecurityUtils {
	/**
	 * 获取用户账户
	 **/
	public static String getUserCode() {
		try {
			return getLoginUser().getUsername();
		} catch (Exception e) {
			throw new CustomException("获取用户账户异常", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * 如果为登录 获取不到UserCode 不报错。
	 * @return
	 */
	public static String getUserCode2() {
		try {
			return getLoginUser().getUser().getUserCode();
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 如果为登录 获取不到UserName 不报错。
	 * @return
	 */
	public static String getUserName2() {
		try {
			return getLoginUser().getUser().getNickName();
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 如果为登录 获取不到ID 不报错。
	 * @return
	 */
	public static Long getUserId2() {
		try {
			return getLoginUser().getUser().getUserId();
		} catch (Exception e) {
			return -1L;
		}
	}
	/**
	 * 获取用户ID，如果未登陆会报错
	 **/
	public static Long getUserId() {
		try {
			return getLoginUser().getUser().getUserId();
		} catch (Exception e) {
			throw new CustomException("获取用户ID异常", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * 获取用户
	 **/
	public static LoginUser getLoginUser() {
		try {
			return (LoginUser) getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new CustomException("获取用户信息异常", HttpStatus.UNAUTHORIZED);
		}
	}
	
	/**
	 * 获取用户,未登陆不报错返回null
	 **/
	public static LoginUser getLoginUser2() {
		try {
			return (LoginUser) getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取Authentication
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 生成BCryptPasswordEncoder密码
	 *
	 * @param password 密码
	 * @return 加密字符串
	 */
	public static String encryptPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new CustomBCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}

	/**
	 * 判断密码是否相同
	 *
	 * @param rawPassword     真实密码
	 * @param encodedPassword 加密后字符
	 * @return 结果
	 */
	public static boolean matchesPassword(String rawPassword, String encodedPassword) {
		BCryptPasswordEncoder passwordEncoder = new CustomBCryptPasswordEncoder();
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	/**
	 * 是否为管理员
	 * 
	 * @param userId 用户ID
	 * @return 结果
	 */
	public static boolean isAdmin(Long userId) {
		return userId != null && 1L == userId;
	}
}
