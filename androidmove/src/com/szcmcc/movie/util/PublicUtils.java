package com.szcmcc.movie.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class PublicUtils {

	private static final String TAG = "PublicUtils";

	/**
	 * 获得当前日期时间格式
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateFormat() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return simpleDateFormat.format(date);
	}

	/**
	 * 获得当前日期时间格式
	 * 
	 * @return yyyyMMddHHmmss
	 */
	public static String getDateMyFormat() {
		String str1 = getDateFormat();
		String str2 = str1.split(" ")[0];// yyyy-MM-dd
		String str3 = str1.split(" ")[1];// HH:mm:ss
		String year = str2.split("-")[0];
		String month = str2.split("-")[1];
		String day = str2.split("-")[2];
		String hour = str3.split(":")[0];
		String min = str3.split(":")[1];
		String sec = str3.split(":")[2];
		return year + month + day + hour + min + sec;
	}

	/**
	 * 获得输入时间日期时间格式
	 * 
	 * @param timeMillis
	 *            yyyy-MM-dd HH:mm:ss
	 * @return yyyyMMddHHmmss
	 */
	public static String getDateMyFormat(String timeMillis) {
		String str2 = timeMillis.split(" ")[0];// yyyy-MM-dd
		String str3 = timeMillis.split(" ")[1];// HH:mm:ss
		String year = str2.split("-")[0];
		String month = str2.split("-")[1];
		String day = str2.split("-")[2];
		String hour = str3.split(":")[0];
		String min = str3.split(":")[1];
		String sec = str3.split(":")[2];
		return year + month + day + hour + min + sec;
	}

	/**
	 * 获得当前日期时间格式
	 * 
	 * @param date
	 *            yyyy-M-d
	 * @return yyyyMMdd
	 */
	public static String getDateFormat(String date) {

		String year = date.split("-")[0];
		String month = date.split("-")[1];
		String day = date.split("-")[2];
		if (month.length() == 1) {
			month = "0" + month;
		}
		if (day.length() == 1) {
			day = "0" + day;
		}
		return year + month + day;

	}

	/**
	 * 获得当前日期时间格式
	 * 
	 * @param date
	 *            YYYYMMDD
	 * @return YYYY-MM-DD
	 */
	public static String getDateService(String date) {
		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		String day = date.substring(6, 8);
		return year + "-" + month + "-" + day;
	}

	/**
	 * 获得当前日期时间格式
	 * 
	 * @param date
	 *            YYYYMMDDHHMMSS
	 * @return YYYY-MM-DD HH:MM:SS
	 */
	public static String getDateSpecific(String date) {
		if (TextUtils.isEmpty(date) || date.length() < 14) {
			// 返回当前时间,如果date位数不够的话
			return getDateFormat();
		}
		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		String day = date.substring(6, 8);
		String hour = date.substring(8, 10);
		String min = date.substring(10, 12);
		String sec = date.substring(12, 14);
		return year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec;
	}

	/**
	 * 获得当前日期时间格式
	 * 
	 * @param date
	 *            HH:mm
	 * @return HHmm
	 */
	public static String getTimeFormat(String time) {

		String hour = time.split(":")[0];
		String min = time.split(":")[1];
		if (hour.length() == 1) {
			hour = "0" + hour;
		}
		if (min.length() == 1) {
			min = "0" + min;
		}
		return hour + min;
	}

	/**
	 * 获得当前日期时间格式
	 * 
	 * @param time
	 *            HHmm
	 * @return HH:mm
	 */
	public static String getTimeReturn(String time) {
		String hour = "00";
		String min = "00";
		if (time.length() == 4) {
			hour = time.substring(0, 2);
			min = time.substring(2, 4);
		}
		return hour + ":" + min;
	}

	/**
	 * 
	 * @param day
	 *            输入日期格式 yyyy-MM-dd
	 * @leaveDay 差距的天数 -1 or +1
	 * @return 前后的日期
	 */
	public static String getLeaveDayFormat(String day, int leaveDay) {
		// TODO 获得前一天的日期
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		;
		Date date = null;
		try {
			date = simpleDateFormat.parse(day);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.DATE, leaveDay); // 得到前一天
		// calendar.add(Calendar.MONTH, -1); //得到前一个月
		// int year = calendar.get(Calendar.YEAR);
		// int month = calendar.get(Calendar.MONTH)+1;
		date = calendar.getTime();
		return simpleDateFormat.format(date);
	}

	/**
	 * 
	 * @param day
	 *            输入日期格式 yyyy-MM-dd
	 * @return 前一天的日期
	 */
	public static String getBeforeDayFormat(String day) {
		// TODO 获得前一天的日期
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		;
		Date date = null;
		try {
			date = simpleDateFormat.parse(day);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.DATE, -1); // 得到前一天
		// calendar.add(Calendar.MONTH, -1); //得到前一个月
		// int year = calendar.get(Calendar.YEAR);
		// int month = calendar.get(Calendar.MONTH)+1;
		date = calendar.getTime();
		return simpleDateFormat.format(date);
	}

	/**
	 * 
	 * @param day
	 *            输入日期格式 yyyy-MM-dd
	 * @return
	 */
	public static String getNextDayFormat(String day) {
		// TODO 获得前一天的日期
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		;
		Date date = null;
		try {
			date = simpleDateFormat.parse(day);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.DATE, +1); // 得到后一天
		// calendar.add(Calendar.MONTH, -1); //得到前一个月
		// int year = calendar.get(Calendar.YEAR);
		// int month = calendar.get(Calendar.MONTH)+1;
		date = calendar.getTime();
		return simpleDateFormat.format(date);
	}

	/**
	 * 将时间戳转换为时间
	 * 
	 * @param mill
	 *            System.currentTimeMillis()
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String convertForTimeMillis(long mill) {
		Date date = new Date(mill * 1000L);
		String strs = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strs = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strs;

	}

	/**
	 * 将时间戳直接转换为时间
	 * 
	 * @param mill
	 *            System.currentTimeMillis()
	 * @return yyyyMMddHHmmss
	 */
	public static String getDateTimeForTimeMillis(long mill) {
		return getDateMyFormat(convertForTimeMillis(mill));

	}

	/**
	 * 隐藏软键盘
	 */
	public static void hideSoftInputMode(Context context, View windowToken) {
		((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(windowToken.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 对比两个字符串哪里不一样
	 * 
	 * @param firstStr
	 * @param otherStr
	 */
	public static void strcmp(String firstStr, String otherStr) {
		if (TextUtils.isEmpty(firstStr) || TextUtils.isEmpty(otherStr)) {
			return;
		}
		int firstLen = firstStr.length();
		int otherLen = otherStr.length();
		Log.i(TAG, "firstStr=" + firstStr);
		Log.i(TAG, "otherStr=" + otherStr);
		Log.i(TAG, "firstLen : " + firstLen + " , otherLen : " + otherLen);
		if (firstStr.equals(otherStr)) {
			return;
		}
		int tempLen = 0;
		if (firstLen >= otherLen) {
			tempLen = otherLen;
		}
		if (firstLen < otherLen) {
			tempLen = firstLen;
		}
		for (int i = 0; i < tempLen; i++) {
			char firstChar = firstStr.charAt(i);
			char otherChar = otherStr.charAt(i);
			if (firstChar == otherChar) {
				// 一样的字符

			} else {
				// 字符不一样时
				Log.i(TAG, "firstChar:" + firstChar + ", otherChar:" + otherChar);
			}
		}
	}

	/**
	 * 获得输入日期的星期
	 * 
	 * @param inputDate
	 *            需要转换的日期 yyyy-MM-dd
	 * @return 星期×
	 */
	public static String getWeekDay(String inputDate) {
		// String weekStrArr1[] = {"周日","周一","周二","周三","周四","周五","周六"};
		String weekStrArr1[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		;
		Date date = null;
		try {
			date = simpleDateFormat.parse(inputDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int outWeek = calendar.get(Calendar.DAY_OF_WEEK);// 返回的是1-7的整数，1为周日，2为周一，以此类推。
		return weekStrArr1[outWeek - 1];
	}

	/*
	 * String weekStrArr1[] = {"周日","周一","周二","周三","周四","周五","周六"};
	 * 
	 * public String[] getWeekA(int y,int c, int m, int d){ String[] weekArr =
	 * new String[7]; for(int i = 0; i < weekArr.length; i++){ weekArr[i] = "";
	 * } for(int i = 0; i < weekArr.length; i++){ weekArr[i] =
	 * weekStrArr1[getWeekB(y, c, m, d + i)]; } return weekArr; } /** 根据日期获得星期
	 * 
	 * @param y 年 比如10年
	 * 
	 * @param c 世纪比如20世纪
	 * 
	 * @param m 月
	 * 
	 * @param d 日
	 * 
	 * @return
	 */
	/*
	 * private int getWeekB(int y, int c, int m, int d) { if(m == 1){ m = 13; y
	 * = y-1; }else if(m == 2){ m = 14; y = y-1; } int tempDate =
	 * (y+(y/4)+(c/4)-2*c+(26*(m+1)/10)+d-1)%7; if(tempDate < 0){ return
	 * 7+tempDate; } return tempDate; }
	 */
	/**
	 * 检测时间是否在某个时间段内
	 * 
	 * @param timeSlot
	 *            时间段 00：00--24：00
	 * @param time
	 *            需要检测的时间 00：23
	 * @return
	 */
	public static boolean isInsideTime(String timeSlot, String time) {
		String startTime = timeSlot.split("--")[0];
		String endTime = timeSlot.split("--")[1];
		boolean isGreaterStart = isCompareTime(time, startTime);
		boolean isLessEnd = isCompareTime(endTime, time);
		if (isGreaterStart && isLessEnd) {
			return true;
		}
		return false;
	}

	/**
	 * 比较两个时间的大小
	 * 
	 * @param time1
	 *            00：23
	 * @param time2
	 *            00：25
	 * @return time1大于等于time2 为 true,time1小于time2 为 false
	 */
	public static boolean isCompareTime(String time1, String time2) {
		if (time1.equals("24:00") || time2.equals("00:00") || time1.equals("24：00") || time2.equals("00：00")) {
			return true;
		}
		if (time2.equals("24:00") || time1.equals("00:00") || time2.equals("24：00") || time1.equals("00：00")) {
			return false;
		}
		// DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat df = new SimpleDateFormat("HH:mm");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(time1));
			c2.setTime(df.parse(time2));
		} catch (java.text.ParseException e) {
			System.err.println("格式不正确");
		}
		int result = c1.compareTo(c2);

		if (result < 0) {
			return false;
		} else if (result >= 0) {
			return true;
		}
		return true;
	}

	/**
	 * 设置需要高亮的字
	 * 
	 * @param wholeText
	 *            原始字符串
	 * @param spanableText
	 *            需要高亮的字符串
	 * @return 高亮后的字符串
	 */
	public static SpannableString getSpanableText(String wholeText, String spanableText) {
		if (TextUtils.isEmpty(wholeText))
			wholeText = "";
		SpannableString spannableString = new SpannableString(wholeText);
		if (spanableText.equals(""))
			return spannableString;
		wholeText = wholeText.toLowerCase();
		spanableText = spanableText.toLowerCase();
		int startPos = wholeText.indexOf(spanableText);
		if (startPos == -1) {
			int tmpLength = spanableText.length();
			String tmpResult = "";
			for (int i = 1; i <= tmpLength; i++) {
				tmpResult = spanableText.substring(0, tmpLength - i);
				int tmpPos = wholeText.indexOf(tmpResult);
				if (tmpPos == -1) {
					tmpResult = spanableText.substring(i, tmpLength);
					tmpPos = wholeText.indexOf(tmpResult);
				}
				if (tmpPos != -1)
					break;
				tmpResult = "";
			}
			if (tmpResult.length() != 0) {
				return getSpanableText(wholeText, tmpResult);
			} else {
				return spannableString;
			}
		}
		int endPos = startPos + spanableText.length();
		do {
			endPos = startPos + spanableText.length();
			spannableString.setSpan(new BackgroundColorSpan(Color.YELLOW), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			startPos = wholeText.indexOf(spanableText, endPos);
		} while (startPos != -1);
		return spannableString;
	}

	public static String getVersionName(Context context) throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
		String version = packInfo.versionName;
		return version;
	}

	/**
	 * 保存从网络获得的数据到文件
	 * 
	 * @param context
	 * @param saveStr
	 *            为要储存的数据，一般传JSON字符串
	 * @param pathName
	 *            要储存的文件名，为字符串即可 例如："bus_from_station"
	 */
	public static void saveToLocalFile(Context context, String saveStr, String pathName) {
		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput(pathName, 0);
			fos.write(saveStr.getBytes("utf-8"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
					fos = null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取本地文件的数据
	 * 
	 * @param context
	 * @param pathName
	 *            要储存的文件名， 为字符串即可 例如："bus_from_station"
	 * @return
	 */
	public static String readFromLocalFile(Context context, String pathName) {
		FileInputStream fis = null;
		ByteArrayOutputStream bao = null;
		try {
			fis = context.openFileInput(pathName);
			String localStr = "";
			// int evenSize = fis.available();
			bao = new ByteArrayOutputStream();
			byte[] arrayOfByte = new byte[16384];
			int i = 0;
			while ((i = fis.read(arrayOfByte)) != -1) {
				// localStr = localStr + new String(arrayOfByte, 0, i);
				bao.write(arrayOfByte);
			}
			localStr = bao.toString("utf-8");
			return localStr;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
					fis = null;
				}
				if (bao != null) {
					bao.close();
					bao = null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return "";
	}

	/**
	 * 读取 assets 文件夹 文件
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String getFromAssets(Context context, String fileName) {
		try {
			InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			String Result = "";
			while ((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 判断信用卡真伪
	 * 
	 * @param str
	 *            信用卡号 1.偶数位卡号奇数位上数字*2，奇数位卡号偶数位上数字*2。 2.大于10的位数减9。 3.全部数字加起来。
	 *            4.结果不是10的倍数的卡号非法
	 * @return
	 */
	public static boolean isXinyongkaNum(String str) {
		boolean state = false;
		int length = str.length();
		int num1 = 0;
		int num2 = 0;
		int num = 0;
		if ((length % 2) == 0) {// 位数为偶数
			for (int i = 0; i < length; i++) {
				System.out.println("num   " + i + "     " + num1);
				try {
					if ((Integer.parseInt(str.substring(i * 2, i * 2 + 1)) * 2) >= 10) {
						num1 += ((Integer.parseInt(str.substring(i * 2, i * 2 + 1)) * 2) - 9);
					} else {
						num1 += (Integer.parseInt(str.substring(i * 2, i * 2 + 1)) * 2);
					}
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
					break;
				}
			}

			for (int i = 0; i < length; i++) {
				System.out.println("num   " + i + "     " + num2);
				try {
					num2 += (Integer.parseInt(str.substring(i * 2 + 1, i * 2 + 2)));
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
					break;
				}
			}
		}

		if ((length % 2) != 0) {// 位数为基数
			for (int i = 0; i < length; i++) {
				try {
					if ((Integer.parseInt(str.substring(i * 2 + 1, i * 2 + 2)) * 2) >= 10) {
						num1 += ((Integer.parseInt(str.substring(i * 2 + 1, i * 2 + 2)) * 2) - 9);
					} else {
						num1 += (Integer.parseInt(str.substring(i * 2 + 1, i * 2 + 2)) * 2);
					}
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
					break;
				}
				System.out.println("num1   " + i + "     " + num1);
			}

			for (int i = 0; i < length; i++) {

				try {
					num2 += (Integer.parseInt(str.substring(i * 2, i * 2 + 1)));
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
					break;
				}
				System.out.println("num2   " + i + "     " + num2);
			}
		}

		System.out.println("num   " + (num1 + num2));

		num = num1 + num2;
		if (num % 10 == 0) {
			state = true;
		}
		return state;
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
		 * 单位由B转变为M
		 * @param data
		 * @return
		 */
		public static String getDouble(int data) {
			double d = (double)data;
			return d/1024/1024+"";
		}
		/**
	     * 判断当前网络是2G、3G、WIFI
	     * @return "WIFI" "2G" "3G" null表示没有网
	     */
	    public static String ConnectionFast(Context context){
	    	ConnectivityManager connectionManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    	NetworkInfo info = connectionManager.getActiveNetworkInfo();
	    	int type = info.getType();
	    	int subType = info.getSubtype();
//	    	System.out.println("---------------------subType  "+subType);
	        if(type==ConnectivityManager.TYPE_WIFI){
	            return "WIFI";
	        }else if(type==ConnectivityManager.TYPE_MOBILE){
	     	   //2G: 1、2、4
	     	   //3G: 3、5、6、8、12
	            switch(subType){
	            case TelephonyManager.NETWORK_TYPE_GPRS://1
	            case TelephonyManager.NETWORK_TYPE_EDGE://2
	            case TelephonyManager.NETWORK_TYPE_CDMA://4
	         	   return "2G";
	            default:
	         	   return "3G"; 
	            }
	        }else{
	     	   return null; 
	        }
	    }

	  
	       /**
	        * 获取设备号
	        * @param context
	        * @return
	        */
	       public static String getIMEI(Context context) {
	       	TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	   		return tm.getDeviceId();
	       }

	       /**
	        * 获取IMSI
	        * @param context
	        * @return
	        */
	       public static String getIMSI(Context context) {
	       	TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//	   		return tm.getSimSerialNumber();
	   		return tm.getSubscriberId();
	       }
	       
	       /**
	        * 统计开始时间
	        * @return
	        */
	       public static int getStime() {
	   		return (int)System.currentTimeMillis();
	       }
	       
	       /**
	        * 统计结束时间
	        * @return
	        */
	       public static int getEtime() {
	       	return (int)System.currentTimeMillis();
	       }
}
