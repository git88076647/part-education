package com.czyl.project.system.mapper;

import com.czyl.project.system.domain.SysRoleOrg;
import com.czyl.project.system.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 数据层
 * 
 * @author tanghx
 */
public interface SysUserMapper
{
    /**
     * 根据条件分页查询用户列表
     * 
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUser sysUser);

    /**
     * 通过用户名查询用户
     * 
     * @param userCode 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserCode(String userCode);
    /**
     * 通过微信openid查询用户
     * 
     * @param openid 微信openId
     * @return 用户对象信息
     */
    public SysUser selectUserByWeChatOpenId(String openId);
    
    /**
     * 查询用户的角色组织
     * @param userId
     * @return
     */
    public List<SysRoleOrg> selectRoleOrgByUserId(Long userId);

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);

    /**
     * 新增用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 修改用户头像
     * 
     * @param userId 用户ID
     * @param avatar 头像地址
     * @return 结果
     */
    public int updateUserAvatar(@Param("userId") Long userId, @Param("avatar") String avatar);

    /**
     * 重置用户密码
     * 
     * @param userId 用户ID
     * @param password 密码
     * @return 结果
     */
    public int resetUserPwd(@Param("salt") String salt,@Param("userId") Long userId, @Param("password") String password,@Param("updateBy") Long updateBy);

    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] userIds);

    /**
     * 校验用户编码是否唯一
     * 
     * @param userCode 用户编码
     * @param userId 角色ID
     * @return 结果
     */
    public int checkUserCodeUnique(String userCode,Long userId);

    /**
     * 校验手机号码是否唯一
     *
     * @param phonenumber 手机号码
     * @param userId 角色ID
     * @return 结果
     */
    public SysUser checkPhoneUnique(String phonenumber,Long userId);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @param userId 角色ID
     * @return 结果
     */
    public SysUser checkEmailUnique(String email,Long userId);
}
