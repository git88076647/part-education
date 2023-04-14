package com.czyl.project.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.czyl.common.utils.AppContextUtils;
import com.czyl.common.utils.LockUtils;
import com.czyl.common.utils.SecurityUtils;
import com.czyl.common.utils.ServletUtils;
import com.czyl.common.utils.security.LoginUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.security.LoginUser;
import com.czyl.framework.security.service.TokenService;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.project.system.domain.SysUser;
import com.czyl.project.system.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 个人信息 业务处理
 *
 * @author tanghx
 */
@RestController
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private TokenService tokenService;

    /**
     * 个人信息
     */
    @GetMapping
    public AjaxResult profile() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        SysUser ru = new SysUser();
        BeanUtil.copyProperties(user, ru);
        ru.setPassword(null);
        ru.setSalt(null);
        AjaxResult ajax = AjaxResult.success(ru);
        ajax.put("roleGroup", userService.selectUserRoleGroup(loginUser.getUser().getUserId()));
//        ajax.put("postGroup", userService.selectUserPostGroup(loginUser.getUser().getUserId()));
        return ajax;
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProfile(@RequestBody SysUser user) {
        try {
            LockUtils.lock(user.getUserId());
            return toAjax(userService.updateUserProfile(user));
        } finally {
            LockUtils.releaseLock(user.getUserId());
        }
    }

    /**
     * 重置密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public AjaxResult updatePwd(String oldPassword, String newPassword) {
        if (com.czyl.common.utils.StringUtils.isBlank(oldPassword)) {
            return AjaxResult.error("旧密码不能为空!");
        }
        if (com.czyl.common.utils.StringUtils.isBlank(newPassword)) {
            return AjaxResult.error("新密码不能为空!");
        }
        oldPassword = LoginUtils.decrypt(oldPassword);
        newPassword = LoginUtils.decrypt(newPassword);
        if (oldPassword != null && newPassword != null && newPassword.equals(oldPassword)) {
            return AjaxResult.error("新密码不能与旧密码相同");
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        try {
            LockUtils.lock(userId);
            String password = loginUser.getPassword();
            if (!SecurityUtils.matchesPassword(loginUser.getUser().getSalt() + oldPassword, password)) {
                return AjaxResult.error("修改密码失败，旧密码错误");
            }
            int ret = userService.resetUserPwd(userId, newPassword, AppContextUtils.getUserId());
            if (ret > 0) {
                SysUser user = userService.selectUserById(userId);
                loginUser.setUser(user);
                tokenService.refreshToken(loginUser);
                return success();
            }
            return error();
        } finally {
            LockUtils.releaseLock(userId);
        }
    }

    /**
     * 头像上传
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    public AjaxResult avatar(String avatar, HttpServletRequest request) throws IOException {
        if (StringUtils.isBlank(avatar)) {
            return AjaxResult.error("参数avatar为空");
        }
        LoginUser loginUser = tokenService.getLoginUser(request);
        try {
            LockUtils.lock(loginUser.getUser().getUserId());
            if (userService.updateUserAvatar(loginUser.getUser().getUserId(), avatar)) {
                AjaxResult ajax = AjaxResult.success();
                loginUser.getUser().setAvatar(avatar);
                tokenService.setLoginUser(loginUser);
                return ajax;
            }
            return AjaxResult.error("上传图片异常，请联系管理员");
        } finally {
            LockUtils.releaseLock(loginUser.getUser().getUserId());
        }
    }
}
