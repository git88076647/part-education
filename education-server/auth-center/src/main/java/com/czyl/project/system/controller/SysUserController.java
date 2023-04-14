package com.czyl.project.system.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.czyl.common.constant.UserConstants;
import com.czyl.common.utils.AppContextUtils;
import com.czyl.common.utils.SecurityUtils;
import com.czyl.common.utils.ServletUtils;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.utils.easyexcel.EasyExcelUtils;
import com.czyl.common.utils.poi.ExcelUtil;
import com.czyl.common.utils.security.LoginUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.DataScope;
import com.czyl.framework.aspectj.lang.annotation.DataSource;
import com.czyl.framework.aspectj.lang.annotation.Lock4j;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.redis.RedisCache;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;
import com.czyl.project.system.domain.SysUser;
import com.czyl.project.system.service.ISysPostService;
import com.czyl.project.system.service.ISysRoleService;
import com.czyl.project.system.service.ISysUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户信息
 * 
 * @author tanghx
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
	@Autowired
	private ISysUserService userService;

	@Autowired
	private ISysRoleService roleService;

	@Autowired
	private ISysPostService postService;

	@Autowired
	private RedisCache redisCache;
	
	/**
	 * 获取用户列表
	 */
	@PreAuthorize("@ss.hasPermi('system:user:list')")
	@GetMapping("/list")
	@DataSource("slave")
	@DataScope(orgAlias = "u")
	public TableDataInfo list(SysUser user) {
		startPage();
		List<SysUser> list = userService.selectUserList(user);
		return getDataTable(list);
	}

	/**
	 * 获取用户列表
	 */
	@GetMapping("/ref")
	@DataSource("slave")
	public TableDataInfo ref(SysUser user) {
		startPage();
		List<SysUser> list = userService.selectUserList(user);
		if(list != null && !list.isEmpty()) {
			List<Map> rows = list.stream().map(m -> MapUtil.builder()
					.put("userCode",m.getUserCode())
					.put("id", m.getUserId().toString())
					.put("nickName",m.getNickName())
					.put("status", StringUtils.trim(m.getStatus()))
					.build()).collect(Collectors.toList());
			TableDataInfo dataTable = getDataTable(rows);
			dataTable.setTotal(new PageInfo(list).getTotal());
			return dataTable;
		}
		return getDataTable(ListUtil.empty());
	}

	@Log(title = "用户管理", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('system:user:export')")
	@GetMapping("/export")
	@DataSource("slave")
	@DataScope(orgAlias = "u")
	public void export(SysUser entity) throws IOException {
		new EasyExcelUtils().export(ServletUtils.getResponse(), entity, "tpl_sys_user", "用户信息" + System.currentTimeMillis() + ".xlsx",
				o -> userService.selectUserList(entity));
	}

	/**
	 * 根据用户编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:user:query')")
	@GetMapping(value = { "/", "/{userId}" })
	@DataSource("slave")
	public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
		AjaxResult ajax = AjaxResult.success();
		ajax.put("roles", roleService.selectRoleAll());
		ajax.put("posts", postService.selectPostAll());
		if (StringUtils.isNotNull(userId)) {
			SysUser user = userService.selectUserById(userId);
			if(user != null) {
				user.setSalt(null);
				user.setPassword(null);
				ajax.put(AjaxResult.DATA_TAG, user);
//            ajax.put("postIds", postService.selectPostListByUserId(userId));
				ajax.put("roleIds", roleService.selectRoleListByUserId(userId));
			}
		}
		return ajax;
	}

	/**
	 * 其他服务调用查询用户角色接口
	 * @param userId
	 * @return
	 */
	@GetMapping("/permision/{userId}" )
	public List<String> selectRoleListByUserId(@PathVariable Long userId){
		return roleService.selectRoleListByUserId(userId);
	}

	/**
	 * 新增用户
	 */
	@PreAuthorize("@ss.hasPermi('system:user:add')")
	@Log(title = "用户管理", businessType = BusinessType.INSERT)
	@PostMapping
	@Lock4j(keys= {"#user.userCode"},autoPrefix = true)
	public AjaxResult add(@Validated @RequestBody SysUser user) {
		if (UserConstants.NOT_UNIQUE.equals(userService.checkUserCodeUnique(user.getUserCode(),null))) {
			return AjaxResult.error("新增用户'" + user.getUserCode() + "'失败，登录账号已存在");
		} else if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
			return AjaxResult.error("新增用户'" + user.getUserCode() + "'失败，手机号码已存在");
		} else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
			return AjaxResult.error("新增用户'" + user.getUserCode() + "'失败，邮箱账号已存在");
		}
		user.setCreateBy(SecurityUtils.getUserId());
		String salt = UUID.randomUUID().toString().replace("-", "");
		user.setSalt(salt);
		user.setPassword(SecurityUtils.encryptPassword(salt + user.getPassword()));
		return toAjax(userService.insertUser(user));
	}

	/**
	 * 修改用户
	 */
	@PreAuthorize("@ss.hasPermi('system:user:edit')")
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@PutMapping
	@Lock4j(keys = { "#user.userId" })
	public AjaxResult edit(@Validated @RequestBody SysUser user) {
		userService.checkUserAllowed(user);
		if (UserConstants.NOT_UNIQUE.equals(userService.checkUserCodeUnique(user.getUserCode(),user.getUserId()))) {
			return AjaxResult.error("修改用户'" + user.getUserCode() + "'失败，登录账号已存在");
		} else if(UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
			return AjaxResult.error("修改用户'" + user.getUserCode() + "'失败，手机号码已存在");
		} else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
			return AjaxResult.error("修改用户'" + user.getUserCode() + "'失败，邮箱账号已存在");
		}
		user.setSalt(null);
		user.setPassword(null);
		user.setUpdateBy(SecurityUtils.getUserId());
		return toAjax(userService.updateUser(user));
	}

	/**
	 * 删除用户
	 */
	@PreAuthorize("@ss.hasPermi('system:user:remove')")
	@Log(title = "用户管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
	public AjaxResult remove(@PathVariable Long[] userIds) {
		return toAjax(userService.deleteUserByIds(userIds));
	}

	/**
	 * 重置密码
	 */
	@PreAuthorize("@ss.hasPermi('system:user:edit')")
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@PutMapping("/resetPwd")
	@Lock4j(keys = { "#user.userId" })
	public AjaxResult resetPwd(@RequestBody SysUser user) {
		userService.checkUserAllowed(user);
		String password = LoginUtils.decrypt(user.getPassword());
		return toAjax(userService.resetUserPwd(user.getUserId(),password,AppContextUtils.getUserId()));
	}

	/**
	 * 状态修改
	 */
	@PreAuthorize("@ss.hasPermi('system:user:edit')")
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	@Lock4j(keys = { "#user.userId" })
	public AjaxResult changeStatus(@RequestBody SysUser user) {
		userService.checkUserAllowed(user);
		user.setUpdateBy(SecurityUtils.getUserId());
		if (userService.updateUserStatus(user) > 0) {
			SysUser data = userService.selectUserById(user.getUserId());
//        	data.setPassword(null);
			return AjaxResult.success(data);
		} else {
			return AjaxResult.error();
		}
	}
	
	/**
	 * 解冻用户
	 */
	@PreAuthorize("@ss.hasPermi('system:user:thaw')")
	@Log(title = "用户管理", businessType = BusinessType.THAW)
	@PostMapping("/thaw")
	public AjaxResult thaw(@RequestParam String[] userCodes) {
		for (String userCode : userCodes) {
			redisCache.deleteObject(String.format(UserConstants.LOGIN_FAILCOUNT_KEY, userCode.toLowerCase()));
		}
		return success();
	}
	
}