package com.czyl.framework.security.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czyl.common.constant.Constants;
import com.czyl.framework.security.LoginUser;
import com.czyl.framework.web.context.TokenContextHolder;
import com.czyl.project.system.domain.SysUser;

/**
 * super用户登录服务
 * @author tanghx
 *
 */
@Service
public class SuperLoginService {

	@Autowired
	TokenService tokenService;

	private LoginUser loginUser;
	private String authorization;

	public void login() {
		String token = getToken();
		TokenContextHolder.set(token);
	}
	
	public void logout() {
		TokenContextHolder.remove();
	}

	public synchronized String getToken() {
		if (authorization == null) {
			// super用户全部权限
			SysUser user = new SysUser();
			user.setUserId(2L);
			user.setUserCode("super");
			user.setNickName("SUPER系统管理员");
			HashSet<String> set = new HashSet<>();
			set.add("*:*:*");
			loginUser = new LoginUser(user, set);
			authorization = tokenService.createToken(loginUser);
		}
		tokenService.verifyToken(loginUser);
		return String.format("%s%s", Constants.TOKEN_PREFIX, authorization);
	}
}
