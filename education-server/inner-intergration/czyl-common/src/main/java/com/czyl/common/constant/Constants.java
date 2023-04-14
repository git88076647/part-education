package com.czyl.common.constant;

import io.jsonwebtoken.Claims;

/**
 * 通用常量信息
 *
 * @author tanghx
 */
public class Constants {
    public static final String AUTHORIZATION = "Authorization";

    /**
     * 租户header
     */
    public static final String TENANT_HEADER = "x-tenantId";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 通用成功标识
     */
    public static final int SUCCESS = 0;

    /**
     * 通用失败标识
     */
    public static final int FAIL = 1;

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 6;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 微信端用户
     */
    public static final String WX_TOKEN = "wx_token";

    /**
     * 前端cookie存放token的key
     */
    public static final String ADMIN_TOKEN = "Admin-Token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户编码
     */
    public static final String JWT_USERCODE = Claims.SUBJECT;

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * RSA加密字符串 前缀
     */
    public static final String RSA_PREFIX = "CPF_Cxx~丨";

    /**
     * 数据权限授权主体
     */
    public enum DataPermissionSubject {
        /**
         * 角色
         */
        ROLE("ROLE"),
        /**
         * 用户
         */
        USER("USER");

        private String value;

        DataPermissionSubject(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    public enum RuleType {
        大于(0),
        小于(1),
        等于(2),
        包含(3),
        无权限(4),
        全权限(5);
        private Integer value;

        RuleType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

    }
}
