package com.szcmcc.movie.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.szcmcc.movie.util.Log;

public class ThreadManger {
	public static ProgressDialog dialog;
	private static Context mContext;

	// static ThreadPool threadPool = new ThreadPool(1);
	public static ExecutorService threadPool = Executors.newFixedThreadPool(10);

	static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Log.i("-------ThreadManger.exeTask(...).new Runnable() {...}.handleMessage()");		
			ResultData resultData = (ResultData) msg.obj;
			ThreadCallBack callBack = (ThreadCallBack) msg.getData()
					.getSerializable("callback");
			int taskId = msg.getData().getInt("taskId");
			callBack.onCallbackFromThread(resultData, taskId);
			if (dialog != null && dialog.isShowing()) {
				dialog.cancel();
			}
		}
	};

	public static interface DoInSubThread {
		public ArrayList predData();
	}

	public static void exeTask(final ThreadCallBack callBack, final int taskId,
			final HashMap<String, String> hashMap, final String reqUrl,
			final boolean isShow,final DoInSubThread mDoInSubThread) {
		mContext = (Context) callBack;
		/*
		 * if(!NetManger.checkNetWork(mContext)){ return ; }
		 */
		if (isShow) {
			if (dialog != null && !dialog.isShowing()) {
				dialog = new ProgressDialog(mContext);
				dialog.setCanceledOnTouchOutside(true);
				dialog.setMessage("数据加载中");
				dialog.show();
			} else {
				if (dialog == null) {
					dialog = new ProgressDialog(mContext);
					dialog.setCanceledOnTouchOutside(true);
					dialog.setMessage("数据加载中");
					dialog.show();
				}
			}
		}
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i("-------ThreadManger.exeTask(...).new Runnable() {...}.run()");
				ResultData resultData=new ResultData ();
				 resultData.setArrayList(mDoInSubThread.predData());
				Message msg = new Message();
				msg.arg1 = taskId;
				msg.obj = resultData;
				msg.getData().putSerializable("callback", callBack);
				msg.getData().putInt("taskId", taskId);
				handler.sendMessage(msg);
			}
		});
		threadPool.execute(thread);
	}

//	public static ResultData sendToServer(int taskId,
//			HashMap<String, String> hashMap, String reqUrl) {
//		ResultData resultData = null;
//		String response;
//		switch (taskId) {
//		case APIContants.TODAYLIST:// 今日团购
//			response = NetManger.doPost(reqUrl, hashMap);
//			if (response != null && !"".equals(response)) {
//				// 保存json
//				String currPage = hashMap.get("currPage");
//				SpUtil.save(mContext, "todaylist" + currPage, response);
//			}
//			resultData = ParseManger.getTodayList(response);
//			break;
//		case APIContants.AROUNDLIST:// 周边团购
//			response = NetManger.doPost(reqUrl, hashMap);
//			if (response != null && !"".equals(response)) {
//				// 保存json
//				String currPage = hashMap.get("currPage");
//				SpUtil.save(mContext, "aroundlist" + currPage, response);
//			}
//			resultData = ParseManger.getTodayList(response);
//			break;
//		case APIContants.GETDETAIL:// 获取详情
//			response = NetManger.doPost(reqUrl, hashMap);
//			if (response != null && !"".equals(response)) {
//				// 保存json
//				SpUtil.save(mContext, hashMap.get("id"), response);
//			}
//			resultData = ParseManger.getDetail(response);
//			break;
//		case APIContants.GETSEARCHLIST:// 搜索
//			response = NetManger.doPost(reqUrl, hashMap);
//			resultData = ParseManger.getTodayList(response);
//			break;
//		case APIContants.GETPURCHASEDLIST:// 我的团购记录
//			response = NetManger.doPost(reqUrl, hashMap);
//			if (response != null && !"".equals(response)) {
//				// 保存json
//				SpUtil.save(mContext, "getpurchasedlist", response);
//			}
//			resultData = ParseManger.getPurchasedList(response);
//			break;
//		case APIContants.GETPURCHASEDWARN:// 我的团购提醒
//			response = NetManger.doPost(reqUrl, hashMap);
//			if (response != null && !"".equals(response)) {
//				// 保存json
//				SpUtil.save(mContext, "getpurchasedwarn", response);
//			}
//			resultData = ParseManger.getPurchasedWarn(response);
//			break;
//		case APIContants.LOGIN:// 登陆
//			response = NetManger.doPost(reqUrl, hashMap);
//			resultData = ParseManger.getLogin(response);
//			break;
//		case APIContants.GETCITY:// 获取城市
//			// 读取本地城市数据
//			String cityJson = StringUtil.readCityJson(mContext);
//			if (cityJson != null && !"".equals(cityJson)) {
//				resultData = ParseManger.getCityList(cityJson, mContext);
//			} else {
//				response = NetManger.doPost(reqUrl, hashMap);
//				resultData = ParseManger.getCityList(response, mContext);
//			}
//			break;
//
//		default:
//			break;
//
//		}
//		return resultData;
//	}

}
