package com.czyl.common.constant;

/**
 * 用户常量信息
 * 
 * @author tanghx
 */
public class UserConstants
{
    /**
     * 平台内系统用户的唯一标志
     */
    public static final String SYS_USER = "SYS_USER";

    /** 正常状态 */
    public static final Integer NORMAL = 0;

    /** 异常状态 */
    public static final Integer EXCEPTION = 1;

    /** 用户封禁状态 */
    public static final Integer USER_BLOCKED = 1;

    /** 角色封禁状态 */
    public static final Integer ROLE_BLOCKED = 1;

    /** 部门正常状态 */
    public static final Integer DEPT_NORMAL = 0;

    /** 字典正常状态 */
    public static final Integer DICT_NORMAL = 0;

    /** 是否为系统默认（是） */
    public static final String YES = "Y";

    public static final String NO = "N";
    
    /** 校验返回结果码 */
    public final static Integer UNIQUE = 0;
    public final static Integer NOT_UNIQUE = 1;
    
    /**
     * 登录失败几次冻结账户
     */
    public final static String LOGIN_FAILCOUNT="sys.login.failcount";
    
    /**
     * 冻结几分钟
     */
    public final static String LOGIN_FROZEN="sys.login.frozen";
    
    /**
     * redis 冻结ID的key
     */
    public final static String LOGIN_FAILCOUNT_KEY="loginFailCount:%s";
    
    /**
     * 密码长度
     * 4~20
     */
    public final static String PWD_LENGTH="sys.pwd.length";
    
    /**
     * 密码等级
     * 
     */
    public final static String PWD_LEVEL="sys.pwd.level";
    
    /**
     * 密码最大连续长度
     */
    public final static String PWD_MAXSERIES="sys.pwd.maxseries";
    
    
}
