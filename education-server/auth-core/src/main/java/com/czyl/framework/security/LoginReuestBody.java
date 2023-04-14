package com.czyl.framework.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录参数
 * @author tanghx
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReuestBody {

	String userCode;
	String password;
	String code;
	String uuid;
}
