package com.czyl.framework.manager.factory;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.czyl.common.constant.Constants;
import com.czyl.common.utils.LogUtils;
import com.czyl.common.utils.ServletUtils;
import com.czyl.common.utils.ip.AddressUtils;
import com.czyl.common.utils.ip.IpUtils;
import com.czyl.common.utils.spring.SpringUtils;
import com.czyl.framework.feign.LogServerClient;
import com.czyl.framework.security.service.SuperLoginService;
import com.czyl.framework.web.context.TokenContextHolder;
import com.czyl.framework.web.domain.Logininfor;
import com.czyl.framework.web.domain.OperLog;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * 异步工厂（产生任务用）
 * 
 * @author tanghx
 */
@Async
@Service
public class AsyncFactory {
	private static final Logger logger = LoggerFactory.getLogger("platform");

	/**
	 * 记录登陆信息
	 * 
	 * @param userCode 用户名
	 * @param status   状态
	 * @param message  消息
	 * @param args     列表
	 * @return 任务task
	 */
	@SuppressWarnings("unused")
	public static TimerTask recordLogininfor(final String userCode, final String status, final String message, final Object... args) {
		final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
		final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
		return new TimerTask() {
			@Override
			public void run() {
				String address = AddressUtils.getRealAddressByIP(ip);
				StringBuilder s = new StringBuilder();
				s.append(LogUtils.getBlock(ip));
				s.append(address);
				s.append(LogUtils.getBlock(userCode));
				s.append(LogUtils.getBlock(status));
				s.append(LogUtils.getBlock(message));
				// 打印信息到日志
				logger.info(s.toString(), args);
				// 获取客户端操作系统
				String os = userAgent.getOperatingSystem().getName();
				// 获取客户端浏览器
				String browser = userAgent.getBrowser().getName();
				// 封装对象
				Logininfor logininfor = new Logininfor();
				logininfor.setUserCode(userCode);
				logininfor.setIpaddr(ip);
				logininfor.setLoginLocation(address);
				logininfor.setBrowser(browser);
				logininfor.setOs(os);
				logininfor.setMsg(message);
				// 日志状态
				if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status)) {
					logininfor.setStatus(Constants.SUCCESS);
				} else if (Constants.LOGIN_FAIL.equals(status)) {
					logininfor.setStatus(Constants.FAIL);
				}
				SpringUtils.getBean(SuperLoginService.class).login();
				// 插入数据
				SpringUtils.getBean(LogServerClient.class).insertLogininfor(logininfor);
			}
		};
	}

	/**
	 * 操作日志记录
	 * 
	 * @param operLog 操作日志信息
	 */
	public void recordOper(final OperLog operLog, final String authorization) {
		TokenContextHolder.set(authorization);
		// 尝试 传递 attributes 过来模拟在主线程时的Feign操作，但是不知道为什么传过来headerNames就变0个了
//		RequestContextHolder.setRequestAttributes(attributes);
//		((ServletRequestAttributes)attributes).getRequest().getHeaderNames();
		// 根据IP查询操作地点
		operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
		SpringUtils.getBean(LogServerClient.class).insertOperlog(operLog);
	}
}
