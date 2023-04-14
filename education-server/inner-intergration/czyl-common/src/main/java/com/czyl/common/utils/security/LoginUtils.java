package com.czyl.common.utils.security;

import java.security.PrivateKey;
import java.security.PublicKey;

import com.czyl.common.constant.Constants;
import com.czyl.common.utils.RSAEncoder;

import lombok.extern.slf4j.Slf4j;

/**
 * 登录密码解密
 * @author tanghx
 *
 */
@Slf4j
public class LoginUtils {
	
	private static final String  PUBLICKEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGtnT8QI52oxj0dlX8II5wvSp4ywU38uZd5ahQm8iKJN4LzzbSr7+2jLZWTA/XdaxBYB8E0Mzk/99aFAUCuYNo4otG1GCpKRygKOfNAKCLPFThxKLMCAdraCZ/lZr1b6KEPnI5MgfaZOcAa20Wa/ITh70KVouqiU0V3Hqu8dpytwIDAQAB";
	
	private static final String  PRIVATEKEY="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIa2dPxAjnajGPR2VfwgjnC9KnjLBTfy5l3lqFCbyIok3gvPNtKvv7aMtlZMD9d1rEFgHwTQzOT/31oUBQK5g2jii0bUYKkpHKAo580AoIs8VOHEoswIB2toJn+VmvVvooQ+cjkyB9pk5wBrbRZr8hOHvQpWi6qJTRXceq7x2nK3AgMBAAECgYA38nrvcJg6KU5p6OOZDKRbqhSDij2mmNiX0pNutuQ4i0s0uGBaa+dBF3t6p6hEoXhixlrbkyQlTychsALMuOMcB8gbAzYXI04tOgfwPLZsVX6XBKFrYjAz/YQFf5cIK1zJUh/XjKGTpfP+3CmIAiZ2wBVIKHRq+kkrAhRvgQLpcQJBAMS5IiKcLjR/xX36gbwFB6r2OvJZen+LbkVhXLhM4e3sT9AtWsidwtKmIkJ1YITh+UQ+6lm0oBNziIqrEYdixX0CQQCvTfNNjO2KHF6WfLUJXXvkycajZ7LAo6jiNde3xZqkS9v9xI+9SE4D1oo5N3qADInwnFoim3PjzJkLw4X2sz9DAkA4BuYS51o9d3pes77JNsGFTlVhelxqAOLp/Z2zm6no4hmlF5W4dz3qUoiFVvqjPdyJNEX4QHoqsAfePnRik53dAkEArMFcq2feDuS2MgbjQT9nAKf3oMBoX37ox0PYObn6Ez7OpN436IiAmTerL4eEqPuyU/NAwCoBotPVXspoEh3CkQJAaRMvpEuq23xYcjGVRV9BnsDJ7/ncHx5YLHcUUOxh+CjVqzej3pDluN4mF+N4KXsu4CsRzeYf0+nYtm0uI6Oliw==";
	
	private static PrivateKey rsaPrivateKey = RSAEncoder.getPrivateKey(PRIVATEKEY);
	
	private static PublicKey rsaPublicKey = RSAEncoder.getPublicKey(PUBLICKEY);
	
    
	public static String decrypt(String content){
		if(content != null && content.startsWith(Constants.RSA_PREFIX)){
			String tmp = content.substring(Constants.RSA_PREFIX.length());
			try {
				return RSAEncoder.decrypt(tmp, rsaPrivateKey);
			} catch (Exception e) {
				log.error("public static String decrypt(String content) ",e);
			}
		}
		return content;
	}
	
	
	public static String encrypt(String content){
		if(content != null && !content.startsWith(Constants.RSA_PREFIX)){
			try {
				return Constants.RSA_PREFIX+RSAEncoder.encrypt(content, rsaPublicKey);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("public static String encrypt(String content) ",e);
			}
		}
		return content;
	}

}
