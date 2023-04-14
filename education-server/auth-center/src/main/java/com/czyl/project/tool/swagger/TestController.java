//package com.czyl.project.tool.swagger;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.czyl.common.utils.StringUtils;
//import com.czyl.common.web.domain.AjaxResult;
//import com.czyl.framework.feign.AuthServerClient;
//import com.czyl.framework.web.controller.BaseController;
//import com.czyl.project.integrate.domain.SysAppreg;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * swagger 用户测试方法
// * 
// * @author tanghx
// */
//@Api(tags="测试用户信息管理")
//@RestController
//@RequestMapping("/test/user")
//@Slf4j
//public class TestController extends BaseController {
//	
//	@Autowired
//	AuthServerClient authServerClient;
//	
//	private final static Map<Integer, UserEntity> users = new LinkedHashMap<Integer, UserEntity>();
//	{
//		users.put(1, new UserEntity(1, "admin", "admin123", "15888888888"));
//		users.put(2, new UserEntity(2, "test", "admin123", "15666666666"));
//	}
//
//	@ApiOperation("应用注册")
//	@GetMapping("/appreg")
//	public SysAppreg appreg() {
//		SysAppreg appreg = authServerClient.getAppReg("sap");
//		log.error("密码明文:{}",appreg.decodePassword());
//		return appreg;
//	}
//	
//	@ApiOperation("获取用户列表")
//	@GetMapping("/list")
//	public AjaxResult userList() {
//		List<UserEntity> userList = new ArrayList<UserEntity>(users.values());
//		return AjaxResult.success(userList);
//	}
//
//	@ApiOperation("获取用户详细")
//	@ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path")
//	@GetMapping("/{userId}")
//	public AjaxResult getUser(@PathVariable Integer userId) {
//		if (!users.isEmpty() && users.containsKey(userId)) {
//			return AjaxResult.success(users.get(userId));
//		} else {
//			return AjaxResult.error("用户不存在");
//		}
//	}
//
//	@ApiOperation("新增用户")
//	@ApiImplicitParam(name = "userEntity", value = "新增用户信息", dataType = "UserEntity")
//	@PostMapping("/save")
//	public AjaxResult save(UserEntity user) {
//		if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId())) {
//			return AjaxResult.error("用户ID不能为空");
//		}
//		if(users.size() > 99) {
//			return AjaxResult.error("测试新增的数量不能超过100");
//		}
//		return AjaxResult.success(users.put(user.getUserId(), user));
//	}
//
//	@ApiOperation("更新用户")
//	@ApiImplicitParam(name = "userEntity", value = "新增用户信息", dataType = "UserEntity")
//	@PutMapping("/update")
//	public AjaxResult update(UserEntity user) {
//		if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId())) {
//			return AjaxResult.error("用户ID不能为空");
//		}
//		if (users.isEmpty() || !users.containsKey(user.getUserId())) {
//			return AjaxResult.error("用户不存在");
//		}
//		users.remove(user.getUserId());
//		return AjaxResult.success(users.put(user.getUserId(), user));
//	}
//
//	@ApiOperation("删除用户信息")
//	@ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path")
//	@DeleteMapping("/{userId}")
//	public AjaxResult delete(@PathVariable Integer userId) {
//		if (!users.isEmpty() && users.containsKey(userId)) {
//			users.remove(userId);
//			return AjaxResult.success();
//		} else {
//			return AjaxResult.error("用户不存在");
//		}
//	}
//}
//
//@ApiModel("用户实体")
//class UserEntity {
//	@ApiModelProperty("用户ID")
//	private Integer userId;
//
//	@ApiModelProperty("用户编码")
//	private String userCode;
//
//	@ApiModelProperty("用户密码")
//	private String password;
//
//	@ApiModelProperty("用户手机")
//	private String mobile;
//
//	public UserEntity() {
//
//	}
//
//	public UserEntity(Integer userId, String userCode, String password, String mobile) {
//		this.userId = userId;
//		this.userCode = userCode;
//		this.password = password;
//		this.mobile = mobile;
//	}
//
//	public Integer getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Integer userId) {
//		this.userId = userId;
//	}
//
//	public String getUserCode() {
//		return userCode;
//	}
//
//	public void setUserCode(String userCode) {
//		this.userCode = userCode;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String getMobile() {
//		return mobile;
//	}
//
//	public void setMobile(String mobile) {
//		this.mobile = mobile;
//	}
//}
