package com.czyl.common.utils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.czyl.common.constant.PermissionConstans;
import com.czyl.framework.security.LoginUser;
import com.czyl.project.system.domain.SysRole;

/**
 * 当前上下文
 * 
 * @author tanghx
 *
 */
public class AppContextUtils {

	/**
	 * 获取用户账户,未登陆会报错
	 **/
	public static String getUserCode() {
		return SecurityUtils.getUserCode();
	}

	/**
	 * 获取用户账户,未登陆不报错
	 **/
	public static String getUserCode2() {
		return SecurityUtils.getUserCode2();
	}

	/**
	 * 获取用户名称,未登陆不报错
	 **/
	public static String getUserName2() {
		return SecurityUtils.getUserName2();
	}

	/**
	 * 如果未登录 获取不到ID 不报错。
	 * 
	 * @return
	 */
	public static Long getUserId2() {
		return SecurityUtils.getUserId2();
	}

	/**
	 * 获取用户ID，如果未登陆会报错
	 **/
	public static Long getUserId() {
		return SecurityUtils.getUserId();
	}

	/**
	 * 获取用户组织ID，如果未登陆会报错
	 **/
	public static Long getOrgId() {
		try {
			return SecurityUtils.getLoginUser().getUser().getOrgId();
		} catch (Exception e) {
			return -1L;
		}
	}
	
	/**
	 * 获取用户角色的组织ID，如果未登陆会报错
	 **/
	public static Long[] getOrgIds() {
		try {
			return SecurityUtils.getLoginUser().getUser().getOrgIds();
		} catch (Exception e) {
			return new Long[] {};
		}
	}

	/**
	 * 获取用户
	 **/
	public static LoginUser getLoginUser() {
		return SecurityUtils.getLoginUser();
	}

	/**
	 * 获取用户
	 **/
	public static LoginUser getLoginUser2() {
		return SecurityUtils.getLoginUser2();
	}

	/**
	 * 是否为管理员
	 * 
	 * @param userId 用户ID
	 * @return 结果
	 */
	public static boolean isAdmin() {
		return SecurityUtils.isAdmin(getUserId2());
	}
	
	/**
	 * 是否有权限标识是admin的角色
	 * @return
	 */
	public static boolean hasAdminRole() {
		return hasAdminRole(getLoginUser());
	}
	public static boolean hasAdminRole(LoginUser loginUser) {
		List<SysRole> roles = loginUser.getUser().getRoles();
		if(roles != null && roles.size() >0) {
			for (SysRole role : roles) {
				if(PermissionConstans.SUPER_ADMIN.equals(role.getRoleKey())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 是否为管理员
	 * 
	 * @param userId 用户ID
	 * @return 结果
	 */
	public static boolean isAdmin(Long userId) {
		return SecurityUtils.isAdmin(userId);
	}

	/**
	 * 当前时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getServerDateTime() {
		return DateUtils.getTime();
	}

	/**
	 * 当前日期 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getServerDate() {
		return DateUtils.getDate();
	}

	/**
	 * 获取当前request
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return ServletUtils.getRequest();
	}

	/**
	 * 获取当前response
	 * 
	 * @return
	 */
	public static HttpServletResponse getResponse() {
		return ServletUtils.getResponse();
	}
}
