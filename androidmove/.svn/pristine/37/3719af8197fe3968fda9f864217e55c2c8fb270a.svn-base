package com.szcmcc.movie.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.szcmcc.movie.R;
import com.szcmcc.movie.util.Log;

public class NetImageView extends ImageView {

	private final int ISWIF = 0;
	private final int IS3G = 1;
	private final int NONET = 2;

	private Context mContext = null;

	public NetImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		subThreadGetState();
	}

	public NetImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		try {
			subThreadGetState();
		} catch (Exception e) {
			Log.i("--------------Exception-----" + e.getMessage());
			Message mMessage = handler.obtainMessage(NONET);
			handler.sendMessage(mMessage);
		}

	}

	private void subThreadGetState() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				getNetState(mContext);

			}

		}).start();
	}

	public NetImageView(Context context) {
		super(context);
		mContext = context;
		subThreadGetState();
	}

	private NetworkReceiver mNetworkReceiver = null;
	private IntentFilter intentFilter = null;

	public void onResume() {
		if (mNetworkReceiver == null) {
			// 生成广播处理
			mNetworkReceiver = new NetworkReceiver();
			// 实例化过滤器并设置要过滤的广播

			intentFilter = new IntentFilter(
					"android.net.conn.CONNECTIVITY_CHANGE");

		}
		// 注册广播
		mContext.registerReceiver(mNetworkReceiver, intentFilter);
		Log.i("--------------registerReceiver(mNetworkReceiver, intentFilter)-----");

	}

	public void onStop() {
		try{
		mContext.unregisterReceiver(mNetworkReceiver);
		}catch(IllegalArgumentException  e){
			e.printStackTrace();
		}
		Log.i("--------------unregisterReceiver(mNetworkReceiver)-----");
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IS3G:
				setImageResource(R.drawable.san_g);
				System.out.println("3g");
				handler.removeMessages(IS3G);
				break;
			case NONET:
				setImageResource(R.drawable.noweb);
				System.out.println("noweb");
				handler.removeMessages(NONET);
				break;
			case ISWIF:
				setImageResource(R.drawable.wireless);
				System.out.println("wireless");
				handler.removeMessages(ISWIF);
				break;
			}
		};
	};

	private String netName = "";
	private DetailedState currDetailedState = null;

	private void getNetState(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		// NetworkInfo mobileNetInfo = connectivityManager
		// .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		logNet("Active Network Type", currDetailedState);
		if (activeNetInfo != null) {
			currDetailedState = activeNetInfo.getDetailedState();
			if (currDetailedState == NetworkInfo.DetailedState.DISCONNECTED) {
				Message mMessage = handler.obtainMessage(NONET);
				handler.sendMessage(mMessage);
			} else if (currDetailedState == NetworkInfo.DetailedState.CONNECTED) {
				netName = activeNetInfo.getTypeName();
				logNet("Mobile Network Type", netName);
				if (netName.equals("WIFI")) {
					Message mMessage = handler.obtainMessage(ISWIF);
					handler.sendMessage(mMessage);
				} else {
					Message mMessage = handler.obtainMessage(IS3G);
					handler.sendMessage(mMessage);
				}
			}
		} else {
			Message mMessage = handler.obtainMessage(NONET);
			handler.sendMessage(mMessage);
		}
		// // 第二种
		// NetworkInfo networkInfo = intent
		// .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
		// String extraInfo = intent
		// .getStringExtra(ConnectivityManager.EXTRA_EXTRA_INFO);
		// boolean isFailOver = intent.getBooleanExtra(
		// ConnectivityManager.EXTRA_IS_FAILOVER, false);
		// boolean noConnectivity = intent.getBooleanExtra(
		// ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
		// NetworkInfo otherNetworkInfo = intent
		// .getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);
		// String reason =
		// intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
		// //
		// showToast("networkInfo", networkInfo);
		// showToast("extraInfo", extraInfo);
		// showToast("isFailOver", isFailOver);
		// showToast("noConnectivity", noConnectivity);
		// showToast("otherNetworkInfo", otherNetworkInfo);
		// showToast("reason", reason);
	}
	
	public static boolean isWifi(Context context){
		DetailedState currDetailedState = null;
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null) {
                currDetailedState = activeNetInfo.getDetailedState();
			}
		 if (currDetailedState == NetworkInfo.DetailedState.CONNECTED) {
			 if (activeNetInfo.getTypeName().equals("WIFI")) {
					return true;
				}
		 }
		 return false;
	}

	public void logNet(String info, Object object) {
		if (object == null) {
			return;
		}
		Log.i("-------------------" + info + " : " + object.toString());
	}

	private class NetworkReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 第一种
			getNetState(mContext);
		}

	}
}
