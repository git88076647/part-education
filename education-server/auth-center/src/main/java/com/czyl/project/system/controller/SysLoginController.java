package com.czyl.project.system.controller;

import com.czyl.common.constant.Constants;
import com.czyl.common.utils.AppContextUtils;
import com.czyl.common.utils.ServletUtils;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.utils.security.LoginUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.security.ClientLoginReuestBody;
import com.czyl.framework.security.LoginReuestBody;
import com.czyl.framework.security.LoginUser;
import com.czyl.framework.security.service.SysLoginService;
import com.czyl.framework.security.service.SysPermissionService;
import com.czyl.framework.security.service.TokenService;
import com.czyl.project.system.domain.SysMenu;
import com.czyl.project.system.domain.SysUser;
import com.czyl.project.system.service.ISysMenuService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 登录验证
 *
 * @author tanghx
 */
@RestController
@Slf4j
@Api(tags = "授权管理")
public class SysLoginController {
	@Autowired
	private SysLoginService loginService;

	@Autowired
	private ISysMenuService menuService;

	@Autowired
	private SysPermissionService permissionService;

	@Autowired
	private TokenService tokenService;

	/**
	 * 登录方法
	 *
	 * @param body 登录参数
	 * @return 结果
	 */
	@PostMapping("/login")
	public AjaxResult login(@RequestBody LoginReuestBody body) {
		body.setPassword(LoginUtils.decrypt(body.getPassword()));
		// 生成令牌
		String token = loginService.login(body.getUserCode(), body.getPassword(), body.getCode(), body.getUuid(), SysLoginService.LOGIN_SOURCE_WEB);
		AjaxResult ajax = AjaxResult.success();
		ajax.put(Constants.TOKEN, token);
		ajax.put("changePwd",loginService.needChangePwd(body.getUserCode(),body.getPassword()));
		return ajax;
	}

	@ApiOperation(value="第三方账户授权",notes="建议采用RequestBody方式,若采用params方式将更容易被捕获登录密码")
	@PostMapping("/clientLogin")
	@ApiImplicitParams({
		@ApiImplicitParam(value ="userCode",name="params方式 登录帐号"),
		@ApiImplicitParam(value ="password",name="params方式 登录密码")
		})
	public AjaxResult clientLogin(@ApiParam(value="RequestBody方式 登录参数") @RequestBody(required = false) ClientLoginReuestBody body) {
		String userCode;
		String password;
		boolean useBody = body != null && (StringUtils.isNotBlank(body.getUserCode()) || StringUtils.isNotBlank(body.getPassword()));
		if (useBody) {
			userCode = body.getUserCode();
			password = body.getPassword();
		}else {
			userCode = AppContextUtils.getRequest().getParameter("userCode");
			password = AppContextUtils.getRequest().getParameter("password");
		}
		// 生成令牌
		String token = loginService.login(userCode, password, null, null, SysLoginService.LOGIN_SOURCE_CLIENT);
		return AjaxResult.success().setData(token);
	}

	/**
	 * 获取用户信息
	 *
	 * @return 用户信息
	 */
	@GetMapping("getInfo")
	public AjaxResult getInfo() {
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		SysUser user = loginUser.getUser();
		// 角色集合
		Set<String> roles = permissionService.getRolePermission(user);
		// 权限集合
		Set<String> permissions = permissionService.getMenuPermission(user);
		AjaxResult ajax = AjaxResult.success();
		user.setPassword(null);
		user.setSalt(null);
		ajax.put("user", user);
		ajax.put("roles", roles);
		ajax.put("permissions", permissions);
		return ajax;
	}

	/**
	 * 获取路由信息
	 *
	 * @return 路由信息
	 */
	@GetMapping("getRouters")
	public AjaxResult getRouters() {
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		// 用户信息
		SysUser user = loginUser.getUser();
		List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
		return AjaxResult.success(menuService.buildMenus(menus));
	}
}
