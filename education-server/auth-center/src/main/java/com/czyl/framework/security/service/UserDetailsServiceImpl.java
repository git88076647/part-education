package com.czyl.framework.security.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.czyl.common.enums.UserStatus;
import com.czyl.common.exception.BaseException;
import com.czyl.common.utils.StringUtils;
import com.czyl.framework.security.LoginUser;
import com.czyl.project.system.domain.SysRole;
import com.czyl.project.system.domain.SysRoleOrg;
import com.czyl.project.system.domain.SysUser;
import com.czyl.project.system.service.ISysUserService;

/**
 * 用户验证处理
 *
 * @author tanghx
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private ISysUserService userService;

	@Autowired
	private SysPermissionService permissionService;

	@Override
	public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
		SysUser user = userService.selectUserByUserCode(userCode);
		if (StringUtils.isNull(user)) {
			log.info("登录用户：{} 不存在.", userCode);
			throw new UsernameNotFoundException("登录用户：" + userCode + " 不存在");
		} else if (UserStatus.DELETED.value().equals(user.getDr())) {
			log.info("登录用户：{} 已被删除.", userCode);
			throw new BaseException("您的账号：" + userCode + " 已被删除");
		} else if (UserStatus.DISABLE.value().equals(user.getStatus())) {
			log.info("登录用户：{} 已被停用.", userCode);
			throw new BaseException("您的账号：" + userCode + " 已停用");
		}

		return createLoginUser(user);
	}

	public UserDetails createLoginUser(SysUser user) {
		List<SysRoleOrg> roleOrgs = userService.selectRoleOrgByUserId(user.getUserId());
		Set<Long> orgIds = new HashSet<>();
		HashMap<Long,Set<Long>> roleOrgIds=new HashMap<Long,Set<Long>>();
		if (roleOrgs != null && roleOrgs.size() > 0) {
			for (SysRoleOrg roleOrg : roleOrgs) {
				orgIds.add(roleOrg.getOrgId());
				if(!roleOrgIds.containsKey(roleOrg.getRoleId())) {
					roleOrgIds.put(roleOrg.getRoleId(),new HashSet<Long>());
				}
				roleOrgIds.get(roleOrg.getRoleId()).add(roleOrg.getOrgId());
			}
		}
		/**
		 * 设置用户快捷获取角色的组织信息
		 */
		user.setOrgIds(orgIds.size() == 0 ? null : orgIds.toArray(new Long[orgIds.size()]));
		//设置用户的角色的组织信息
		List<SysRole> roles = user.getRoles();
		Set<Long> roleIds = new HashSet<>();
		if(roles != null && roles.size() >0) {
			for (SysRole role : roles) {
				roleIds.add(role.getRoleId());
				if(roleOrgIds.containsKey(role.getRoleId())) {
					orgIds = roleOrgIds.get(role.getRoleId());
					role.setOrgIds(orgIds.toArray(new Long[orgIds.size()]));
				}
			}
		}
		user.setRoleIds(roleIds.toArray(new Long[roleIds.size()]));
		return new LoginUser(user, permissionService.getMenuPermission(user));
	}
}
