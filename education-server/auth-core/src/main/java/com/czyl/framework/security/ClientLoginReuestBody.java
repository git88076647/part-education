package com.czyl.framework.security;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 第三方账户登录参数
 * @author tanghx
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="第三方账户授权-参数")
public class ClientLoginReuestBody {

	@ApiModelProperty(position = 1,name = "userCode", value = "登录帐号", dataType = "String",example = "test2")
	String userCode;

	@ApiModelProperty(position = 2,name = "password", value = "登录密码", dataType = "String",example = "123456")
	String password;
}
