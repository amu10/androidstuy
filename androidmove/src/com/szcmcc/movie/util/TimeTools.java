package com.szcmcc.movie.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.content.Context;
import android.util.Log;

public class TimeTools {
	
	static final String TAG = "TimeTools";

	/**
	 * 
	 * @param context
	 * @param time 时间戳，以秒计
	 * @return
	 */
	public static String getTimeString(Context context, String time){
		Date afterDate = new Date();
//		Log.i(TAG, "After time:" + afterDate.toGMTString());
		
		long beforeTime = Long.valueOf(time) * 1000L;
		Date beforeDate = new Date(beforeTime);
		System.out.println("beforeDate   "+beforeTime);
//		Log.i(TAG, "Before time:" + beforeDate.toGMTString());
		SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
		String values = sDate.format(beforeDate);
		System.out.println("values       "+values);
		if(afterDate.getYear() == beforeDate.getYear()){
			int month = afterDate.getMonth() - beforeDate.getMonth();
			if(month > 3 || month < 0){
				return values;
			}else{
				int day = afterDate.getDate() -beforeDate.getDate();
				if(month == 3){
					if(day >= 0){
						return "三个月前";
					}else{
						return "二个月前";
					}
				}else if(month == 2){
					if(day >= 0){
						return "二个月前";
					}else{
						return "一个月前";
					}
				}else if(month == 1){
					if(day >= 0){
						return "一个月前";
					}else{
						return getDateString(context, afterDate.getTime() - beforeTime);
					}
				}else if(month == 0){
					return getDateString(context, afterDate.getTime() - beforeTime);
				}
			}
		}
		return values;
	}
	
	private static String getDateString(Context context, long time){
		long days = time/(1000 * 60 * 60 * 24);
		if(days >= 7){
			return days/7 + "周前";
		}else{
			if(days > 0){
				return days + "天前";
			}else{
				long hours = time/(1000 * 60 * 60);
				if(hours > 0){
					return hours + "小时前";
				}else{
					long minutes = time/(1000 * 60);
					if(minutes > 1){
						return minutes + "分钟前";
					}else{
						long seconds = time/1000 - minutes *  60;
						if(seconds<=0){
							seconds=10;
						}
						return seconds + "秒钟前";						
					}
				}
			}
		}
	}

}
