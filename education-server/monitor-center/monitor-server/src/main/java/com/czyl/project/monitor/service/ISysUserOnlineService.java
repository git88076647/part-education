package com.czyl.project.monitor.service;

import com.czyl.framework.security.LoginUser;
import com.czyl.project.monitor.domain.SysUserOnline;

/**
 * 在线用户 服务层
 * 
 * @author tanghx
 */
public interface ISysUserOnlineService
{
    /**
     * 通过登录地址查询信息
     * 
     * @param ipaddr 登录地址
     * @param user 用户信息
     * @return 在线用户信息
     */
    public SysUserOnline selectOnlineByIpaddr(String ipaddr, LoginUser user);

    /**
     * 通过用户编码查询信息
     * 
     * @param userCode 用户编码
     * @param user 用户信息
     * @return 在线用户信息
     */
    public SysUserOnline selectOnlineByUserCode(String userCode, LoginUser user);

    /**
     * 通过登录地址/用户编码查询信息
     * 
     * @param ipaddr 登录地址
     * @param userCode 用户编码
     * @param user 用户信息
     * @return 在线用户信息
     */
    public SysUserOnline selectOnlineByInfo(String ipaddr, String userCode, LoginUser user);

    /**
     * 设置在线用户信息
     * 
     * @param user 用户信息
     * @return 在线用户
     */
    public SysUserOnline loginUserToUserOnline(LoginUser user);
}
