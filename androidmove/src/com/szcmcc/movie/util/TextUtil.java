package com.szcmcc.movie.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

/**
 * 文字的工具类
 * 
 * @author Haylee
 * 
 */
public class TextUtil {

	/**
	 * 拼接字段：style： field1|field2
	 * 
	 * @param list_fields
	 * @return
	 */
	public static String montageField(List<String> list_fields) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list_fields.size(); i++) {
			if (!TextUtils.isEmpty(list_fields.get(i))) {
				sb.append(list_fields.get(i) + "|");
			}
		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * 拼接字段：style： field1|field2,由于价钱的特殊性，去掉价钱小数点后面的值
	 * 
	 * @param list_fields
	 * @return
	 */
	public static String montageField_Price(List<String> list_fields) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list_fields.size(); i++) {
			if (!TextUtils.isEmpty(list_fields.get(i))) {
				String newPrice = "";
				String price = list_fields.get(i);
//				System.out.println("dongdianzhouTextUtils1" + price);
				if (price.contains(".")) {
					int indexOf = price.indexOf(".");
					newPrice = price.substring(0, indexOf);
				}
//				System.out.println("dongdianzhouTextUtils2" + price + newPrice);
				sb.append(newPrice + "|");
			}
		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * 判断字符是否是数字和字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isStrOrNumeric(String str) {
		Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$ ");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断字符是否是字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isStr(String str) {
		Pattern pattern = Pattern.compile("^[A-Za-z]+$ ");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断字符是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 匹配一个字符串中是否包含另一个字符串
	 * 
	 * @param str
	 * @param keyword
	 * @return
	 */
	public static boolean iscontain(String str, String keyword) {
		Pattern pattern = Pattern.compile(keyword);
		// Pattern pattern = Pattern.compile(keyword,Pattern.CANON_EQ); // 智能匹配
		// Pattern pattern = Pattern.compile(keyword,Pattern.CASE_INSENSITIVE);
		// //匹配字符串时，不考虑大小写
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
		// System.out.println("dongdainzhouTextUtils" + str.contains(keyword));
		// return str.contains(keyword);
	}

	/**
	 * 将时间戳转成指定格式的日期 
	 *（当调用TimeStampToDate("1252639886", "yyyy-MM-dd HH:mm:ss");
	 * 返回值：2009-11-09 11:31:26 ）
	 * @param timestampString
	 * @param formats
	 * @return
	 */
	public static String TimeStamp2Date(Long timestampString, String formats) {
		Long timestamp = timestampString * 1000;
		String date = new java.text.SimpleDateFormat(formats).format(new java.util.Date(timestamp));
		return date;
	}
}
