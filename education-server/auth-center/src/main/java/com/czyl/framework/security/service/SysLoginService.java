package com.czyl.framework.security.service;

import com.czyl.common.constant.Constants;
import com.czyl.common.constant.UserConstants;
import com.czyl.common.exception.CustomException;
import com.czyl.common.exception.user.CaptchaException;
import com.czyl.common.exception.user.CaptchaExpireException;
import com.czyl.common.exception.user.UserPasswordNotMatchException;
import com.czyl.common.utils.MessageUtils;
import com.czyl.common.utils.StringUtils;
import com.czyl.framework.config.CustomBCryptPasswordEncoder;
import com.czyl.framework.manager.AsyncManager;
import com.czyl.framework.manager.factory.AsyncFactory;
import com.czyl.framework.redis.RedisCache;
import com.czyl.framework.security.LoginUser;
import com.czyl.project.system.domain.SysUser;
import com.czyl.project.system.service.ISysConfigService;
import com.czyl.project.system.service.ISysUserService;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 登录校验方法
 * 
 * @author tanghx
 */
@Component
public class SysLoginService {
	@Autowired
	private TokenService tokenService;

	@Autowired
	private ISysConfigService configService;

	@Resource
	private AuthenticationManager authenticationManager;

	@Autowired
	private RedisCache redisCache;

	@Autowired
	private ISysUserService sysUserService;

	public static String LOGIN_SOURCE_CLIENT = "client";
	public static String LOGIN_SOURCE_WEB = "web";

	public String loginByWeChat(AuthUser weChatUser) {
		// 用户验证
		String openId = weChatUser.getToken().getOpenId();
		Authentication authentication = null;
		SysUser dbUser = sysUserService.selectUserByWeChatOpenId(openId);
		if (dbUser == null) {
			//未绑定，不继续处理
			return null;
		}
		try {
			// 该方法会去调用UserDetailsServiceImpl.loadUserByUserCode
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dbUser.getUserCode(), CustomBCryptPasswordEncoder.SUPER_PASSWORD));
		} catch (Exception e) {
			return null;
		}
		// 生成token
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		String authorization = tokenService.createToken(loginUser);
		//保存微信信息
		tokenService.saveWeChatInfo(authorization,weChatUser);
		AsyncManager.me().execute(AsyncFactory.recordLogininfor(openId, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
		return authorization;
	}

	/**
	 * 登录验证
	 * 
	 * @param userCode 用户名
	 * @param password 密码
	 * @param code  验证码
	 * @param uuid     唯一标识
	 * @param source 登录终端 LOGIN_SOURCE_WEB
	 * @return 结果
	 */
	public String login(String userCode, String password, String code, String uuid, String source) {
		if (StringUtils.isBlank(userCode)) {
			throw new CustomException("登录用户不能为空");
		}
		if (StringUtils.isBlank(password)) {
			throw new CustomException("登录密码不能为空");
		}
		userCode = userCode.toLowerCase();
		if (LOGIN_SOURCE_WEB.equals(source)) {
			String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
			String captcha = redisCache.getCacheObject(verifyKey);
			redisCache.deleteObject(verifyKey);
			if (captcha == null) {
				AsyncManager.me().execute(AsyncFactory.recordLogininfor(userCode, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
				throw new CaptchaExpireException();
			}
			if (!code.equalsIgnoreCase(captcha)) {
				AsyncManager.me().execute(AsyncFactory.recordLogininfor(userCode, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
				throw new CaptchaException();
			}
		}

		// 失败次数
		String failTimes = configService.selectConfigByKey(UserConstants.LOGIN_FAILCOUNT);
		final String key = String.format(UserConstants.LOGIN_FAILCOUNT_KEY, userCode);
		Integer count = null;
		if (StringUtils.isNotBlank(failTimes) && Integer.valueOf(failTimes) > 0) {
			count = redisCache.getCacheObject(key);
			if (count == null) {
				count = 0;
			}
			if (Integer.valueOf(failTimes) < count) {
				Long expire = redisCache.getExpire(key);
				throw new CustomException(String.format("帐号:[%s]连续登录失败[%s]次,剩余冻结时间[%s]秒", userCode, failTimes, expire.toString()));
			}
		}

		// 用户验证
		Authentication authentication = null;
		try {
			String salt = "";
			SysUser dbUser = sysUserService.selectUserByUserCode(userCode);
			if (dbUser != null) {
				salt = dbUser.getSalt();
				if ("1".equals(dbUser.getUserType())) {
					// 普通用户需要校验验证码
					if (LOGIN_SOURCE_WEB.equals(source) == false) {
						throw new CustomException("请从正规渠道登录!");
					}
				}
			}
			// 该方法会去调用UserDetailsServiceImpl.loadUserByUserCode
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCode, salt + password));
			redisCache.deleteObject(key);
		} catch (Exception e) {
			if (e instanceof BadCredentialsException) {
				AsyncManager.me().execute(AsyncFactory.recordLogininfor(userCode, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
				if (StringUtils.isNotBlank(failTimes) && Integer.valueOf(failTimes) > 0) {
					// 冻结时间 分钟
					String frozen = configService.selectConfigByKey(UserConstants.LOGIN_FROZEN);
					if (StringUtils.isNotBlank(frozen) && Integer.valueOf(frozen) > 0) {
//						Object count = redisCache.getCacheObject(key);
						redisCache.setCacheObject(key, (count + 1), Integer.valueOf(frozen), TimeUnit.MINUTES);
					}
				}
				throw new UserPasswordNotMatchException();
			} else {
				AsyncManager.me().execute(AsyncFactory.recordLogininfor(userCode, Constants.LOGIN_FAIL, e.getMessage()));
				throw new CustomException(e.getMessage());
			}
		}
		// 生成token
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		String authorization = tokenService.createToken(loginUser);
		AsyncManager.me().execute(AsyncFactory.recordLogininfor(userCode, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
		return authorization;
	}

	/**
	 * 是否需要修改密码
	 * @param userCode
	 * @param pwd
	 * @return
	 */
	public Integer needChangePwd(String userCode, String pwd) {
		return sysUserService.needChangePwd(userCode,pwd);
	}
}
