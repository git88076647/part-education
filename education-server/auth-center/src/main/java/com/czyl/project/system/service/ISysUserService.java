package com.czyl.project.system.service;

import com.czyl.project.system.domain.SysRoleOrg;
import com.czyl.project.system.domain.SysUser;

import java.util.List;

/**
 * 用户 业务层
 * 
 * @author tanghx
 */
public interface ISysUserService
{
    /**
     * 根据条件分页查询用户列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUser user);

    /**
     * 通过用户名查询用户
     * 
     * @param userCode 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserCode(String userCode);
    
    /**
     * 通过微信openid查询用户
     * @param openId
     * @return
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
     * 根据用户ID查询用户所属角色组
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public String selectUserRoleGroup(Long userId);

    /**
     * 根据用户ID查询用户所属岗位组
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public String selectUserPostGroup(Long userId);

    /**
     * 校验用户编码是否唯一
     * 
     * @param userCode 用户编码
     * @param userId
     * @return 结果
     */
    public Integer checkUserCodeUnique(String userCode,Long userId);

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public Integer checkPhoneUnique(SysUser user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public Integer checkEmailUnique(SysUser user);

    /**
     * 校验用户是否允许操作
     * 
     * @param user 用户信息
     */
    public void checkUserAllowed(SysUser user);

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
     * 修改用户状态
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUserStatus(SysUser user);

    /**
     * 修改用户基本信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUserProfile(SysUser user);

    /**
     * 修改用户头像
     * 
     * @param userId 用户ID
     * @param avatar 头像地址
     * @return 结果
     */
    public boolean updateUserAvatar(Long userId, String avatar);

    /**
     * 重置用户密码
     * 
     * @param userId 用户ID
     * @param password 密码
     * @param updateBy 修改人
     * @return 结果
     */
    public int resetUserPwd(Long userId, String password,Long updateBy);

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
     * 是否是需要修改密码
     * @param userCode
     * @param pwd
     * @return
     */
    public Integer needChangePwd(String userCode, String pwd);
}
