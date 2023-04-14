package com.czyl.common.utils;

import com.czyl.common.exception.CustomException;

/**
 * 密码等级检查
 * @author tanghx
 *
 */
public class PasswordCheckUtil {

	/**
	 * 获取密码等级，大小写字母，数字，字符。无中文  左右箭头等特殊符号。有非法字符抛出异常CustomException
	 * @param password
	 * @return 密码等级
	 */
	public static int getLevel(String password) {
		if (StringUtils.isBlank(password)) {
			return 0;
		}
		byte[] foundflag = new byte[4];
		char[] pwd = password.toCharArray();
		for (char p : pwd) {
			Integer ascII = Integer.valueOf(p);
			if (ascII < 33 || ascII > 126) {
				throw new CustomException("非法字符" + p);
			} else if (ascII >= 48 && ascII <= 57) {
//				数字
				foundflag[0] = 1;
			} else if (ascII >= 65 && ascII <= 90) {
//				大写字母
				foundflag[1] = 1;
			} else if (ascII >= 97 && ascII <= 122) {
//				小写字母
				foundflag[2] = 1;
			} else {
//				特殊字符
				foundflag[3] = 1;
			}
			if ((foundflag[0] + foundflag[1] + foundflag[2] + foundflag[3]) == 4) {
				break;
			}
		}
		return foundflag[0] + foundflag[1] + foundflag[2] + foundflag[3];
	}
	
	/**
	 * 是否超过最大连续长度
	 * @param password 
	 * @param maxSeries 最大连续长度
	 * @return true/false
	 */
	public static boolean isOverSeries(String password,int maxSeries) {
		if (StringUtils.isBlank(password)) {
			return false;
		}
		//顺序
		int counter = 0;
		//倒叙
		int counterDesc = 0;
		char[] pwd = password.toCharArray();
		Integer oldAscII = 0;
		for (char p : pwd) {
			Integer ascII = Integer.valueOf(p);
			if(ascII - oldAscII == 1) {
				counter ++;
				counterDesc = 1;
			}else if(ascII - oldAscII == -1) {
				counterDesc ++;
				counter = 1;
			}else {
				counter = 1;
				counterDesc = 1;
			}
			if (counter > maxSeries || counterDesc > maxSeries) {
				return true;
			}
			oldAscII = ascII;
		}
		return false;
	}

}
