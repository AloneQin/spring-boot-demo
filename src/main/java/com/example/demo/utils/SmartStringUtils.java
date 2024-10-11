package com.example.demo.utils;

import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class SmartStringUtils extends StringUtils {
	
	/**
     * 正则匹配用户名
     * ^[a-zA-z][a-zA-Z0-9_]{6,20}$: 字母、数字、下划线、必须以字母开头、长度 6-20 位
     *
     * @param userName 用户名
     * @return true or false
     *
     * 链接：
     * <a href="http://www.codeceo.com/article/useful-regular-expression.html">example1</a>
     * <a href="http://www.jb51.net/article/72867.htm">example2</a>
     */
	public static boolean isUserName(String userName) {
		if (userName == null) {
			return false;
		}
		Pattern p = Pattern.compile("^[a-zA-z][a-zA-Z0-9_]{6,20}$");
        Matcher m = p.matcher(userName);
        return m.matches();
	}
	
	/**
	 * 正则匹配邮箱
	 * @param email 邮箱
	 * @return true or false
	 */
	public static boolean isEmail(String email) {
		if (email == null) {
			return false;
		}
		Pattern p = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        Matcher m = p.matcher(email);
        return m.matches();
	}
	
	/**
	 * 正则匹配手机
	 * @param mobile 手机
	 * @return true or false
	 */
	public static boolean isMobile(String mobile) {
		if (mobile == null) {
			return false;
		}
		Pattern p = Pattern.compile("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
	}
	
	/**
	 * 正则匹配密码：必须包含大小写字母和数字的组合，不能使用特殊字符，长度 6-20 位
	 * @param password 密码
	 * @return true or false
	 */
	public static boolean isPassword(String password) {
		if (password == null) {
			return false;
		}
		Pattern p = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$");
        Matcher m = p.matcher(password);
        return m.matches();
	}

	/**
	 * 字符串判空
	 * @param str 待判定字符串
	 * @return true or false
	 */
	public static boolean isNull(String str) {
		return Objects.isNull(str);
	}

	/**
	 * 字符串判非空
	 * @param str 待判定字符串
	 * @return true or false
	 */
	public static boolean nonNull(String str) {
		return !isNull(str);
	}

	/**
	 * 字符串判空
	 * @param str 待判定字符串
	 * @return true or false
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	/**
	 * 字符串判非空
	 * @param str 待判定字符串
	 * @return true or false
	 */
	public static boolean nonEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 格式化sql，去除模板引擎中的换行符、制表符、多空格
	 */
	public static String formatSql(String sql) {
		return sql.replaceAll("\n", "")
				.replaceAll("\r", "")
				.replaceAll("\t", "")
				.replaceAll("\\s+", " ");
	}

	/**
	 * 字符串格式化填充
	 * @param str 需要填充的字符串，格式：xxx{}xxx{}xxx
	 * @param params 需要填充的参数
	 * @return 填充后的字符串
	 */
	public static String format(String str, Object... params) {
		if (params == null) {
			return str;
		}
        for (Object param : params) {
            if (!str.contains("{}")) {
                break;
            }
            str = str.replaceFirst("\\{}", param.toString());
        }
		return str;
	}

	/**
	 * 下划线转驼峰
	 * @param underline 下划线字符串
	 * @return 驼峰字符串
	 */
	public static String underline2Camel(String underline) {
		return com.baomidou.mybatisplus.core.toolkit.StringUtils.underlineToCamel(underline);
	}

	/**
	 * 驼峰转下划线
     * @param camel 驼峰字符串
	 * @return 下划线字符串
	 */
	public static String camel2underline(String camel) {
		return com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(camel);
	}

	public static void main(String[] args) {
		String camel = "priceRange";
		String underline = "price_range";
		System.out.println(camel2underline(camel));
		System.out.println(underline2Camel(underline));
		System.out.println(Objects.equals(null, null));
	}
}
