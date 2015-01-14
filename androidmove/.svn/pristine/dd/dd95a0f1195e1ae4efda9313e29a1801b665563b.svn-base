package com.szcmcc.movie.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;

public class AppContext {
	private static AppContext instance;

	private AppContext() {
	}

	// 单例模式中获取唯一的AppContext实例
	public static AppContext getInstance() {
		if (null == instance) {
			instance = new AppContext();
		}
		return instance;

	}

	public static Map<String, Activity> activitys = new HashMap<String, Activity>();

	// // 添加Activity到容器中
	// public void addActivity(Activity activity) {
	// activityList.add(activity);
	// }

	// 遍历所有Activity并finish
	public void exit() {
		Iterator<Entry<String, Activity>> iter = activitys.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Entry<String, Activity> entry = (Entry<String, Activity>) iter
					.next();
			entry.getValue().finish();
		}

	}

	public static LoctionInfo currLoc;

}
