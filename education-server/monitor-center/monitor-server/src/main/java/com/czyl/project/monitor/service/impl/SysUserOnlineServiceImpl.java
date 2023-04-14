package com.czyl.project.monitor.service.impl;

import org.springframework.stereotype.Service;

import com.czyl.common.utils.StringUtils;
import com.czyl.framework.security.LoginUser;
import com.czyl.project.monitor.domain.SysUserOnline;
import com.czyl.project.monitor.service.ISysUserOnlineService;

/**
 * 在线用户 服务层处理
 * 
 * @author tanghx
 */
@Service
public class SysUserOnlineServiceImpl implements ISysUserOnlineService {
	/**
	 * 通过登录地址查询信息
	 * 
	 * @param ipaddr 登录地址
	 * @param user   用户信息
	 * @return 在线用户信息
	 */
	@Override
	public SysUserOnline selectOnlineByIpaddr(String ipaddr, LoginUser user) {
		if (StringUtils.equals(ipaddr, user.getIpaddr())) {
			return loginUserToUserOnline(user);
		}
		return null;
	}

	/**
	 * 通过用户编码查询信息
	 * 
	 * @param userCode 用户编码
	 * @param user     用户信息
	 * @return 在线用户信息
	 */
	@Override
	public SysUserOnline selectOnlineByUserCode(String userCode, LoginUser user) {
		if (StringUtils.equals(userCode, user.getUsername())) {
			return loginUserToUserOnline(user);
		}
		return null;
	}

	/**
	 * 通过登录地址/用户编码查询信息
	 * 
	 * @param ipaddr   登录地址
	 * @param userCode 用户编码
	 * @param user     用户信息
	 * @return 在线用户信息
	 */
	@Override
	public SysUserOnline selectOnlineByInfo(String ipaddr, String userCode, LoginUser user) {
		if (StringUtils.equals(ipaddr, user.getIpaddr()) && StringUtils.equals(userCode, user.getUsername())) {
			return loginUserToUserOnline(user);
		}
		return null;
	}

	/**
	 * 设置在线用户信息
	 * 
	 * @param user 用户信息
	 * @return 在线用户
	 */
	@Override
	public SysUserOnline loginUserToUserOnline(LoginUser user) {
		if (StringUtils.isNull(user) && StringUtils.isNull(user.getUser())) {
			return null;
		}
		SysUserOnline sysUserOnline = new SysUserOnline();
		sysUserOnline.setTokenId(user.getToken());
		sysUserOnline.setUserCode(user.getUsername());
		sysUserOnline.setIpaddr(user.getIpaddr());
		sysUserOnline.setLoginLocation(user.getLoginLocation());
		sysUserOnline.setBrowser(user.getBrowser());
		sysUserOnline.setOs(user.getOs());
		sysUserOnline.setLoginTime(user.getLoginTime());
		if (StringUtils.isNotNull(user.getUser().getDept())) {
			sysUserOnline.setDeptName(user.getUser().getDept().getDeptName());
		}
		return sysUserOnline;
	}
}
