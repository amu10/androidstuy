package com.szcmcc.movie.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szcmcc.movie.R;
import com.szcmcc.movie.adapter.ZSQEventInfoAdapter;
import com.szcmcc.movie.bean.YouhuiBean;
import com.szcmcc.movie.bean.YouhuiDetailInfoBean;
import com.szcmcc.movie.bean.YouhuiInfoBean;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.view.MessageDialog;
import com.szcmcc.movie.view.ToastAlone;

public class ZSQConcessionsActivity extends ZSQBaseActivity implements OnClickListener,
		OnItemClickListener {
	private RelativeLayout rvOffer;// 优惠信息响应区
	private TextView tvOffer;
	private ImageView ivOfferLine;
	private RelativeLayout rvEvent;// 活动信息响应区
	private TextView tvEvent;
	private ImageView ivEventLine;
	// private ListView listPreferential;// 优惠信息list
	// private ZSQOffersInfoAdapter offersInfoAdapter;
	private ListView listEvent;// 活动信息list
	private ZSQEventInfoAdapter eventInfoAdapter,huodongAdapter;
//	private YouhuiInfoBean youhuiInfoBean = null;
	private ArrayList<YouhuiBean> youHuiList;
	private ArrayList<YouhuiBean> huoDongList;
	protected MovieLib lib;

	private final int YOUHUI_MODE = 0;
	private final int HUODONG_MODE = 1;
	private int currentMode = 0; 
//	YouhuiInfoBean youhuiInfoBean,huodongInfoBean = null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zsq_act_concessions);
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(currentMode==YOUHUI_MODE){
		if (youHuiList==null) {
			eventInfoAdapter = new ZSQEventInfoAdapter(this);
			listEvent.setAdapter(eventInfoAdapter);
			new GetYouhuiActivityTask(this).execute("1",getNowDate());
		}
		}else{
			if(huoDongList==null){
				eventInfoAdapter = new ZSQEventInfoAdapter(this);
				listEvent.setAdapter(eventInfoAdapter);
				new GetYouhuiActivityTask(this).execute("2","");
			}
		}
	}

	private void init() {
		tvOffer = (TextView) findViewById(R.id.tvOffer);
		ivOfferLine = (ImageView) findViewById(R.id.ivOfferLine);
		rvOffer = (RelativeLayout) findViewById(R.id.rvOffer);
		tvEvent = (TextView) findViewById(R.id.tvEvent);
		ivEventLine = (ImageView) findViewById(R.id.ivEventLine);
		rvEvent = (RelativeLayout) findViewById(R.id.rvEvent);
		listEvent = (ListView) findViewById(R.id.listEvent);

		rvOffer.setOnClickListener(this);
		rvEvent.setOnClickListener(this);
		listEvent.setOnItemClickListener(this);
	}
	
	public String getNowDate(){   
	    String temp_str="";   
	    Date dt = new Date();   
	    //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制   
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
	    temp_str=sdf.format(dt);   
	    return temp_str;   
	}  

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg0.getId()) {
		case R.id.listEvent:
			try{
				new GetYouhuiDetailActivityTask(ZSQConcessionsActivity.this).execute(eventInfoAdapter.getItem(arg2).id);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			break;
		default:
			break;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rvOffer:
			if (ivOfferLine.getVisibility() == View.INVISIBLE) {
				currentMode = YOUHUI_MODE;
				tvEvent.setTextColor(getResources().getColor(R.color.white));
				tvOffer.setTextColor(getResources().getColor(R.color.blue_top));
				ivEventLine.setVisibility(View.INVISIBLE);
				ivOfferLine.setVisibility(View.VISIBLE);
				if (youHuiList==null) {
					eventInfoAdapter = new ZSQEventInfoAdapter(this);
					listEvent.setAdapter(eventInfoAdapter);
					new GetYouhuiActivityTask(this).execute("1",getNowDate());
				}else{
				eventInfoAdapter.setData(youHuiList);
				}
			}
			break;
		case R.id.rvEvent:
			if (ivEventLine.getVisibility() == View.INVISIBLE) {
				currentMode = HUODONG_MODE;
				tvEvent.setTextColor(getResources().getColor(R.color.blue_top));
				tvOffer.setTextColor(getResources().getColor(R.color.white));
				ivEventLine.setVisibility(View.VISIBLE);
				ivOfferLine.setVisibility(View.INVISIBLE);
				if(huoDongList==null){
					eventInfoAdapter = new ZSQEventInfoAdapter(this);
					listEvent.setAdapter(eventInfoAdapter);
					new GetYouhuiActivityTask(this).execute("2","");
				}else{
				eventInfoAdapter.setData(huoDongList);
				}
			}
			break;
		default:
			break;
		}
	}

	private class GetYouhuiActivityTask extends MovieAsyncTask<String, String, YouhuiInfoBean> {
		public GetYouhuiActivityTask(Activity activity) {
			super(activity, null, true, true, true);
		}
		YouhuiInfoBean youhuiInfoBean = null;
		@Override
		protected YouhuiInfoBean doInBackground(String... params) {
			try {
				lib = MovieLib.getInstance(ZSQConcessionsActivity.this);
				youhuiInfoBean = lib.getYouhuiActivity(params[0], params[1]);
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return youhuiInfoBean;
		}

		@Override
		protected void onPostExecute(YouhuiInfoBean result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.result_code.equals("200")) {
					if(result.data==null){
//						new MessageDialog(ZSQConcessionsActivity.this, result.desc, false);
						switch(currentMode){
							case YOUHUI_MODE:
								MessageDialog.getInstance().setData(ZSQConcessionsActivity.this, R.string.concessions_youhui_null, false);
								break;
							case HUODONG_MODE:
								MessageDialog.getInstance().setData(ZSQConcessionsActivity.this, R.string.concessions_huodong_null, false);
								break;
						}
						return;
					}
					switch(currentMode){
					case YOUHUI_MODE:
						youHuiList = result.data;
						eventInfoAdapter.setData(youHuiList);
						break;
					case HUODONG_MODE:
						huoDongList = result.data;
						eventInfoAdapter.setData(huoDongList);
						break;
					}
				} else {
//					Toast.makeText(ZSQConcessionsActivity.this, "网络连接失败", Toast.LENGTH_SHORT)
//							.show();
					MessageDialog.getInstance().setData(ZSQConcessionsActivity.this, R.string.data_failed_to_load, false);
				}
			}else {
//				Toast.makeText(ZSQConcessionsActivity.this, "网络连接失败", Toast.LENGTH_SHORT)
//				.show();
				MessageDialog.getInstance().setData(ZSQConcessionsActivity.this, R.string.data_failed_to_load, false);
			}
		}

	}
	
	private class GetYouhuiDetailActivityTask extends MovieAsyncTask<String, String, YouhuiDetailInfoBean> {
		public GetYouhuiDetailActivityTask(Activity activity) {
			super(activity, null, true, true, true);
		}
		YouhuiDetailInfoBean youhuiDetailInfoBean = null;
		@Override
		protected YouhuiDetailInfoBean doInBackground(String... params) {
			try {
				lib = MovieLib.getInstance(ZSQConcessionsActivity.this);
				youhuiDetailInfoBean = lib.getYouhuiDetailActivity(params[0]);
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return youhuiDetailInfoBean;
		}

		@Override
		protected void onPostExecute(YouhuiDetailInfoBean result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.result_code.equals("200")) {
					if(result.data!=null&&result.data.size()!=0){
						Intent in = new Intent(ZSQConcessionsActivity.this, YouHuiAct.class);
						in.putExtra("YouhuiDetailBean",  result.data.get(0));
						startActivity(in);
					} else {
//						MessageDialog.getInstance().setData(ZSQConcessionsActivity.this, result.desc, false);
						switch(currentMode){
							case YOUHUI_MODE:
								MessageDialog.getInstance().setData(ZSQConcessionsActivity.this, R.string.concessions_youhui_null, false);
								break;
							case HUODONG_MODE:
								MessageDialog.getInstance().setData(ZSQConcessionsActivity.this, R.string.concessions_huodong_null, false);
								break;
						}
					}
				} else {
//					Toast.makeText(ZSQConcessionsActivity.this, "网络连接失败", Toast.LENGTH_SHORT)
//							.show();
					MessageDialog.getInstance().setData(ZSQConcessionsActivity.this, R.string.data_failed_to_load, false);
				}
			}else {
//				Toast.makeText(ZSQConcessionsActivity.this, "网络连接失败", Toast.LENGTH_SHORT)
//				.show();
				MessageDialog.getInstance().setData(ZSQConcessionsActivity.this, R.string.data_failed_to_load, false);
	}
		}

	}

	private void showDailog(String msg) {
		
		AlertDialog.Builder builder = new Builder(this);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle("确认退出");
		builder.setMessage(msg);
		builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Intent startMain = new Intent(Intent.ACTION_MAIN);
				// startMain.addCategory(Intent.CATEGORY_HOME);
				// startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// startActivity(startMain);// 跳转到home下
				// finish();
				// System.exit(0);
				ExitQuest();
				finish();
//				// 判断SDK版本
//				int sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
//				// 如果是2.2返回sdkVersion =8
//
//				// 加判断
//				ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//				if (sdkVersion < 8) {
//					manager.restartPackage(getPackageName());
//					System.exit(0);
//				} else {
//					Intent startMain = new Intent(Intent.ACTION_MAIN);
//					startMain.addCategory(Intent.CATEGORY_HOME);
//					startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					startActivity(startMain);
//					System.exit(0);
//
//				}

			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// if(NetImageView.isWifi(mContext)){
			showDailog("您确定要退出么？");
			// }else{
			// finish();
			// }
		}
		return false;
	}
}
