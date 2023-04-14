package com.czyl.project.system.service.impl;

import cn.hutool.core.util.IdUtil;
import com.czyl.common.constant.UserConstants;
import com.czyl.common.enums.UserStatus;
import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.PasswordCheckUtil;
import com.czyl.common.utils.SecurityUtils;
import com.czyl.common.utils.StringUtils;
import com.czyl.framework.aspectj.lang.annotation.DataScope;
import com.czyl.project.system.domain.*;
import com.czyl.project.system.mapper.*;
import com.czyl.project.system.service.ISysConfigService;
import com.czyl.project.system.service.ISysUserService;
import com.czyl.project.system.service.ISysWeakpasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户 业务层处理
 *
 * @author tanghx
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysWeakpasswordService weakpasswordService;

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userCode 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserCode(String userCode) {
        return userMapper.selectUserByUserCode(userCode);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 查询用户所属角色组
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(Long userId) {
        List<SysRole> list = roleMapper.selectRolesByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (SysRole role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(Long userId) {
        List<SysPost> list = postMapper.selectPostsByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (SysPost post : list) {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 校验用户编码是否唯一
     *
     * @param userCode 用户编码
     * @return 结果
     */
    @Override
    public Integer checkUserCodeUnique(String userCode,Long userId){
        int count = userMapper.checkUserCodeUnique(userCode,userId);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户手机是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public Integer checkPhoneUnique(SysUser user) {
        if (StringUtils.isBlank(user.getPhonenumber())) {
            return UserConstants.UNIQUE;
        }
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber(),user.getUserId());
        if (info != null) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public Integer checkEmailUnique(SysUser user) {
        if (StringUtils.isBlank(user.getEmail())) {
            return UserConstants.UNIQUE;
        }
        SysUser info = userMapper.checkEmailUnique(user.getEmail(),user.getUserId());
        if (info != null) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new CustomException("不允许操作超级管理员用户");
        }
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(SysUser user) {
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserStatus(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserProfile(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户头像
     *
     * @param userId 用户ID
     * @param avatar 头像地址
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUserAvatar(Long userId, String avatar) {
        return userMapper.updateUserAvatar(userId, avatar) > 0;
    }

    /**
     * 重置用户密码
     *
     * @param userId   用户ID
     * @param password 密码明文
     * @param updateBy 修改人
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resetUserPwd(Long userId, String password, Long updateBy) {
        //检查弱口令
        SysWeakpassword weakpwd = weakpasswordService.selectSysWeakpasswordByPassword(password);
        if (weakpwd != null && weakpwd.getStatus().equals(UserStatus.OK.value()) && weakpwd.getDr().equals(UserStatus.OK.value()))  {
            throw new CustomException("当前密码属于弱口令,禁止使用！");
        }
        // 检查密码是否符合密码等级
//		数字 大写字母 小写字母 符号
//		0= 无限制
//		1= 上面满足一个
//		2= 上面满足两个
//		3= 上面满足三个
//		4= 上面满足四个
        String length = configService.selectConfigByKey(UserConstants.PWD_LENGTH);
        String[] lens = StringUtils.trim(length, "6~20").split("~");
        if (password.length() < Integer.valueOf(lens[0])) {
            throw new CustomException(String.format("密码长度不足[%s]位", lens[0]));
        }
        if (password.length() > Integer.valueOf(lens[1])) {
            throw new CustomException(String.format("密码长度超过[%s]位", lens[1]));
        }
        // 判断密码等级
        Integer level = Integer.valueOf(StringUtils.trim(configService.selectConfigByKey(UserConstants.PWD_LEVEL), "0"));
        if (PasswordCheckUtil.getLevel(password) < level) {
            switch (level) {
                case 0:
                    break;
                case 1:
                    throw new CustomException(String.format("密码应包含大写字母、小写字母、数字、英文符号 中的一项"));
                case 2:
                    throw new CustomException(String.format("密码应包含大写字母、小写字母、数字、英文符号 中的两项"));
                case 3:
                    throw new CustomException(String.format("密码应包含大写字母、小写字母、数字、英文符号 中的三项"));
                case 4:
                    throw new CustomException(String.format("密码应包含大写字母、小写字母、数字、英文符号"));
                default:
                    throw new CustomException(String.format("密码等级设置错误,不存在密码等级[%s]", level.toString()));
            }

        }
        // 判断密码连续位数
        Integer maxSeries = Integer.valueOf(StringUtils.trim(configService.selectConfigByKey(UserConstants.PWD_MAXSERIES), "20"));
        if (PasswordCheckUtil.isOverSeries(password, maxSeries)) {
            throw new CustomException(String.format("密码最大连续数超过[%s]", maxSeries.toString()));
        }

        String salt = IdUtil.simpleUUID();
        password = SecurityUtils.encryptPassword(salt + password);
        return userMapper.resetUserPwd(salt, userId, password, updateBy);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    private void insertUserRole(SysUser user) {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    private void insertUserPost(SysUser user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts)) {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteUserByIds(Long[] userIds) {
        for (Long userId : userIds) {
            checkUserAllowed(new SysUser(userId));
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        // 删除用户与岗位表
        userPostMapper.deleteUserPost(userIds);
        return userMapper.deleteUserByIds(userIds);
    }

    @Override
    public Integer needChangePwd(String userCode, String pwd) {
        //是否是历史密码
        //是否是弱口令

        //是否是默认密码
        String defaultPwd = configService.selectConfigByKey("sys.user.initPassword");
        return StringUtils.trim(defaultPwd).equals(StringUtils.trim(pwd)) ? 601 : 0;
    }

    @Override
    public List<SysRoleOrg> selectRoleOrgByUserId(Long userId) {
        return userMapper.selectRoleOrgByUserId(userId);
    }

    @Override
    public SysUser selectUserByWeChatOpenId(String openId) {
        return userMapper.selectUserByWeChatOpenId(openId);
    }
}
