package com.czyl.project.integrate.controller;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.czyl.common.constant.Constants;
import com.czyl.common.lock.DistributedLock;
import com.czyl.common.utils.LockUtils;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.config.WeChatConfig;
import com.czyl.framework.redis.RedisCache;
import com.czyl.framework.security.service.SysLoginService;
import com.czyl.framework.web.controller.BaseController;
import com.google.common.collect.Lists;

import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.scope.AuthWechatMpScope;
import me.zhyd.oauth.log.Log;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.request.AuthWeChatMpRequest;
import me.zhyd.oauth.utils.AuthStateUtils;

@RestController
@RequestMapping("/wechat")
public class WeChatAuthController extends BaseController {

	@Lazy
	@Autowired
	AuthStateCache authStateCache;

	@Autowired
	RedisCache redisCache;

	@Lazy
	@Autowired
	WeChatConfig weChatConfig;

	@Autowired
	private SysLoginService loginService;

	@RequestMapping("/oauth")
	public AjaxResult oauth(String redirectUrl) throws IOException {
		if (StringUtils.isNotBlank(redirectUrl)) {
			URL url = new URL(redirectUrl);
			if (!weChatConfig.getAllowDomain().contains(url.getHost())) {
				return AjaxResult.error("非法域名");
			}
		}
		AuthRequest authRequest = getAuthRequest();
		String state = AuthStateUtils.createState();
		String authorizeUrl = authRequest.authorize(state);
		if (StringUtils.isNotBlank(redirectUrl)) {
			redisCache.setCacheObject(getRedirectKey(state), redirectUrl, 300, TimeUnit.SECONDS);
		}
		return AjaxResult.success(authorizeUrl);
	}

	private String getRedirectKey(String state) {
		return String.format("wechat:redirecturl:%s", state);
	}

	@RequestMapping("/oauth/authredirect")
	public String authredirect(AuthCallback callback){
		final String lockKey = callback.getState() == null ? this.getClass().getName() : callback.getState();
		DistributedLock lock = LockUtils.me();
		try {
			lock.lock(lockKey, 10);
			AuthRequest authRequest = getAuthRequest();
			AuthResponse authRes = null;
			try {
				authRes = authRequest.login(callback);
			}catch(Exception e) {
				Log.error("获取微信openid失败",e);
			}
			String redirectKey = getRedirectKey(callback.getState());
			//获取缓存跳转的url
			String url = redisCache.getCacheObject(redirectKey);
			redisCache.deleteObject(redirectKey);
			if (StringUtils.isBlank(url)) {
				url = weChatConfig.getMainUri();
			}
			if(!url.contains("?")) {
				url += "?t=" + (System.currentTimeMillis()/1000);
			}
			if (authRes != null && authRes.getData() != null) {
				AuthUser weChatUser = (AuthUser) (authRes.getData());
				String token = loginService.loginByWeChat(weChatUser);
				if(StringUtils.isNotBlank(token)) {
					url += String.format("&%s=%s&openid=%s",Constants.TOKEN,token,weChatUser.getToken().getOpenId());
				}
			}
			return redirect(url);
		}finally {
			lock.releaseLock(lockKey);
		}
	}
	
	@RequestMapping("/main")
	public String main(String openid,String token) throws IOException {
		return String.format("this is main page openid:%s token:%s",openid,token);
	}

	private AuthRequest getAuthRequest() {
		AuthConfig config = AuthConfig.builder().clientId(weChatConfig.getClientId()).clientSecret(weChatConfig.getClientSecret()).redirectUri(weChatConfig.getCallBackUrl()).build();
		config.setScopes(Lists.newArrayList(AuthWechatMpScope.SNSAPI_USERINFO.getScope()));
		return new AuthWeChatMpRequest(config, authStateCache);
	}
}
