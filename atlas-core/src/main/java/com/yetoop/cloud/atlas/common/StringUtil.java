package com.yetoop.cloud.atlas.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;


public class StringUtil {

	private static final String phoneReg = "^((13[0-9])|(11[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
	
	private static final String chineseReg = "[\u4e00-\u9fa5]"; 
	
	/**
	 * 将小数格式化成百分数 
	 * 如:0.233格式为23.3% 保留1位百分比小数位
	 * @param num
	 * @return
	 */
	public static String formatPercentage(double num){
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(1);// 设置保留百分比小数位
		return nf.format(num);
	}
	
	public static String toString(Integer i) {
		if (i != null) {
			return String.valueOf(i);
		}
		return "";
	}

	public static String toString(Double d) {
		if (null != d) {
			return String.valueOf(d);
		}
		return "";
	}
	
	public static String getRandStr(int n) {
		Random random = new Random();
		String sRand = "";
		for (int i = 0; i < n; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}
		return sRand;
	}

	/**
	 * Object转String的方法
	 * 
	 * @param o
	 *            需要转String的对象
	 * @param checked
	 *            是否检测异常
	 * @return String 转换之后的字符串
	 */
	public static String toString(Object o, boolean checked) {
		String value = "";
		if (null != o) {
			try {
				value = o.toString();

			} catch (Exception e) {
				if (checked) {
					throw new RuntimeException("String类型不正确");
				}
			}
		}
		return value;
	}

	/**
	 * Object转String的方法 若为空，或者转换出现异常
	 * 
	 * @param o
	 *            需要转String的对象
	 * @return 转换之后的字符串
	 */
	public static String toString(Object o) {
		String value = "";
		if (null != o) {
			value = o.toString();
		}
		return value;
	}
	
	/**
	 * 将object转为数字
	 * @param obj  需要转object的对象
	 * @param defaultValue checked为false或obj为null时的默认值
	 * @param checked checked如果为true格式不正确抛出异常
	 * @return
	 */
	public static int toInt(Object obj,Integer defaultValue,boolean checked){
		int value = defaultValue;
		if (obj == null) {
			return defaultValue;
		}
		try {
			value = Integer.parseInt(obj.toString());
		} catch (Exception ex) {
			if (checked) {
				throw new RuntimeException("整型数字格式不正确");
			} else {
				return defaultValue;
			}
		}
		return value;
	}
	
	/**
	 * 将字串转为数字
	 * 
	 * @param str
	 * @param checked如果为treu格式不正确抛出异常
	 * @return
	 */
	public static int toInt(String str, boolean checked) {
		int value = 0;
		if (str == null || str.equals("")) {
			return 0;
		}
		try {
			value = Integer.parseInt(str);
		} catch (Exception ex) {
			if (checked) {
				throw new RuntimeException("整型数字格式不正确");
			} else {
				return 0;
			}
		}
		return value;
	}

	/**
	 * 将object转为数字
	 * 
	 * @param obj
	 *            需要转object的对象
	 * @param checked如果为true格式不正确抛出异常
	 * @return
	 */
	public static int toInt(Object obj, boolean checked) {
		int value = 0;
		if (obj == null) {
			return 0;
		}
		try {
			value = Integer.parseInt(obj.toString());
		} catch (Exception ex) {
			if (checked) {
				throw new RuntimeException("整型数字格式不正确");
			} else {
				return 0;
			}
		}
		return value;
	}

	/**
	 * 将一个字串转为int，如果无空，则返回默认值
	 * 
	 * @param str
	 *            要转换的数字字串
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static Integer toInt(String str, Integer defaultValue) {
		Integer value = defaultValue;
		if (StringUtil.isNullString(str)) {
			return defaultValue;
		}
		try {
			value = Integer.parseInt(str);
		} catch (Exception ex) {
			return defaultValue;
		}
		return value;
	}

 
	public static String randomString(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}

	public static Integer randomNum() {
		return RandomUtils.nextInt(0,Integer.MAX_VALUE);
	}

	public static String randomNum(int length) {
		return RandomStringUtils.randomNumeric(length);
	}
	
	public static boolean isMatcher(String reg, String text) {
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(text);
		return matcher.matches();
	}

	public static boolean isNullString(String str) {
		return str == null || str.trim().equals("");
	}

	public static boolean isNum(String text) {
		if (isNullString(text)) {
			return false;
		}
		return isMatcher("[0-9]+", text);
	}

	public static boolean isMobilePhone(String num) {
		return isMatcher(phoneReg, num);
	}

    public static String leftPad(String str, int size, String padStr) {
    	return org.apache.commons.lang3.StringUtils.leftPad(str,size,padStr);
    }
    
	
	public static boolean isContainsChinese(String str)
	{
		Pattern pat = Pattern.compile(chineseReg);
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find())    {
			flg = true;
		}
		return flg;
	}
	
	public static String md5(String str) {
		return md5(str, true);
	}

	public static String md5(String str, boolean zero) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
			return null;
		}
		byte[] resultByte = messageDigest.digest(str.getBytes());
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < resultByte.length; ++i) {
			int v = 0xFF & resultByte[i];
			if (v < 16 && zero)
				result.append("0");
			result.append(Integer.toHexString(v));
		}
		return result.toString();
	}
 
}
