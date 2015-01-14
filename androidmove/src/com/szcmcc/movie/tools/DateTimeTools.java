package com.szcmcc.movie.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间转换工具类
 * 
 * @author zhangsiqi
 */
public class DateTimeTools {
	public Calendar calendar;
	public Date date;
	public SimpleDateFormat format;
	public final static String DATE_FORMAT_YEAR_MONTH_DAY_HOURS_MINUTES_SECONDS = "yyyy-MM-dd HH:mm:ss";
	public final static String DATE_FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";
	public final static String DATE_FORMAT_YEAR_MONTH_DAY_HOURS_MINUTES = "yyyy-MM-dd HH:mm";
	public final static String DATE_FORMAT_YEAR_MONTH_DAY_HOURS_MINUTES_SECONDS_AD = "yyyy-MM-ddHH:mm:ss";
	public final static String DATE_FORMAT_YEAR_MONTH_DAY_HOURS_MINUTES_SECONDS_PM = "yyyyMMddHHmmss";

	public DateTimeTools(String DateFormat) {
		format = new SimpleDateFormat(DateFormat);
	}

	/**
	 * 根据日期字符串返回相对应的long时间
	 * 
	 * @param time
	 * @return long
	 */
	public long getLongTimeForString(String time) {
		date = new Date();
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}

	/**
	 * 根据日期long返回相对应的字符串时间
	 * 
	 * @param time
	 * @return String
	 */
	public String getStringTimeForLong(long time) {
		date = new Date(time);
		return format.format(date);
	}

	/**
	 * 根据日期long返回年
	 * 
	 * @param time
	 * @return
	 */
	public int getYearForLong(long time) {
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 根据日期long返回月
	 * 
	 * @param time
	 * @return
	 */
	public int getMonthForLong(long time) {
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 根据日期long返回日
	 * 
	 * @param time
	 * @return
	 */
	public int getDateForLong(long time) {
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 根据日期long返回周
	 * 
	 * @param time
	 * @return
	 */
	public int getDayForLong(long time) {
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 根据日期long返回小时
	 * 
	 * @param time
	 * @return
	 */
	public int getHourForLong(long time) {
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 根据日期long返回分钟
	 * 
	 * @param time
	 * @return
	 */
	public int getMinuteForLong(long time) {
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.get(Calendar.MINUTE);
	}
}
