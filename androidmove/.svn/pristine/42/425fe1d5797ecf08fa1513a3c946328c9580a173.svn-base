package com.szcmcc.movie.activity;

import java.io.IOException;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.backup.RestoreObserver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.FlowStatisticsBean;
import com.szcmcc.movie.bean.MovieInfo;
import com.szcmcc.movie.bean.UpDateBean;
import com.szcmcc.movie.bean.UserBean;
import com.szcmcc.movie.cache.AppConstants;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.storage.SharedPreferencesUtil;
import com.szcmcc.movie.util.PublicUtils;
import com.szcmcc.movie.view.MessageDialog;
import com.szcmcc.movie.view.ToastAlone;

public class LoadingActivity extends BaseActivity {
	MovieInfo movieInfo = null;
	private ProgressBar progressBar = null;
	private TextView text = null;
	UpDateBean upDateBean = null;
	private boolean updateFlag = false;
	SharedPreferencesUtil spUtil;
	SharedPreferences prefs;
	boolean firstTime = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MovieApplication.isCanClose = false;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_first);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		text = (TextView) findViewById(R.id.text);
		progressBar.setVisibility(View.VISIBLE);
		text.setVisibility(View.VISIBLE);
		spUtil = SharedPreferencesUtil.getInstance(this);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		firstTime = prefs.getBoolean("first_time", true);
		getAppKey();
		new GetMovieInfoTask(this).execute();
		new Thread() {

			@Override
			public void run() {
				if (!spUtil.getUserName().equals("") && spUtil.getLoginType().equals("1")) {
					UserBean bean = null;
					try {
						bean = lib.getLogin(spUtil.getUserName(), spUtil.getPassword(), "1");

					} catch (HttpException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (bean != null) {
						if (bean.result == 1) {
							try {
								CmccDataStatistics.Comm_Login(LoadingActivity.this,
										spUtil.getUserName());
							} catch (Exception e) {
								e.printStackTrace();
							}
							spUtil.saveUid(bean.uid, bean.token);
						} else {// 如果自动登录失败 将清除缓存
							spUtil.clearAllData();
						}
					} else {// 如果自动登录失败 将清除缓存
						spUtil.clearAllData();
					}
				} else {
					spUtil.clearNameAndPassword();
				}
			}

		}.start();
		// if (firstTime){
		//
		// Editor pEdit = prefs.edit();
		// pEdit.putBoolean("first_time", false);
		// pEdit.commit();
		// if(!updateFlag){
		// new UpDate(LoadingActivity.this,"检测升级中...").execute();
		// }
		// }
	}

	private void showDialogUp(final String url) throws Exception {
		final AlertDialog.Builder builder = new AlertDialog.Builder(LoadingActivity.this);
		builder.setTitle("升级提示!");
		builder.setMessage("发现新版本 ，是否下载新版本？");
		builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse(url);
				intent.setData(content_url);
				startActivity(intent);
			}
		});
		builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

			}
		});
		builder.create().show();
	}

	// class UpDate extends MovieAsyncTask<String, String, UpDateBean> {
	// public String exception;
	//
	// public UpDate(Activity activity, String loadingText) {
	// super(activity, null, true, true, false, loadingText);
	// }
	//
	// @Override
	// protected UpDateBean doInBackground(String... params) {
	//
	// try {
	// upDateBean =
	// lib.update("","",PublicUtils.getVersionName(LoadingActivity.this));
	// } catch (HttpException e) {
	// exception = getResources().getString(R.string.exception_network);
	// e.printStackTrace();
	// } catch (IOException e) {
	// exception = getResources().getString(R.string.exception_network);
	// e.printStackTrace();
	// } catch (JSONException e) {
	// exception = getResources().getString(R.string.exception_json);
	// e.printStackTrace();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return upDateBean;
	// }
	//
	// @Override
	// protected void onPostExecute(UpDateBean result) {
	// super.onPostExecute(result);
	// if (result != null && !result.updateReturn.equals("")) {
	// if (result.updateReturn.equals("-4")) {
	// Toast.makeText(LoadingActivity.this, "当前已是最新版本！",
	// Toast.LENGTH_SHORT).show();
	// } else if (result.updateReturn.equals("1")) {
	// try {
	// showDialogUp();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// } else {
	// // Toast.makeText(HomePageActivity.this,
	// // "版本检测失败，请重新尝试！",Toast.LENGTH_LONG).show();
	// }
	// updateFlag = true;
	// } else {
	// Toast.makeText(LoadingActivity.this, "版本检测失败，请重新尝试！",
	// Toast.LENGTH_LONG).show();
	// }
	// }
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// }
	//
	// }

	private String[] getAppKey() {
		ApplicationInfo info;
		String testData[] = new String[2];
		try {
			info = this.getPackageManager().getApplicationInfo(getPackageName(),
					PackageManager.GET_META_DATA);
			testData[0] = info.metaData.getString("SZICITY_APPKEY");
			testData[1] = info.metaData.getString("SZICITY_CHANNEL");
			System.out.println("---SZICITY_CHANNEL--- " + testData[1]);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return testData;
	}
	long time = 0;

	class GetMovieInfoTask extends MovieAsyncTask<String, String, MovieInfo> {
		public String exception;

		public GetMovieInfoTask(Activity activity) {
			super(activity, null, true, true, false);
		}

		@Override
		protected MovieInfo doInBackground(String... params) {

			try {
				time = System.currentTimeMillis();
				System.out.println("当前时间：-------  "+time);
				movieInfo = lib.getAllMovie("0");
				AppConstants.movieInfo = movieInfo;
//				if (!updateFlag) {
//					if (getAppKey()[0] == null || getAppKey()[0].equals("")) {
//						upDateBean = lib.update("eceqgbyha79y", "CHANNEL001",
//								PublicUtils.getVersionName(LoadingActivity.this));
//					} else {
//						upDateBean = lib.update(getAppKey()[0], getAppKey()[1],
//								PublicUtils.getVersionName(LoadingActivity.this));
//					}
//				}
			} catch (HttpException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (IOException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (JSONException e) {
				exception = getResources().getString(R.string.exception_json);
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return movieInfo;
		}

		@Override
		protected void onPostExecute(MovieInfo result) {
			super.onPostExecute(result);

			progressBar.setVisibility(View.GONE);
			text.setVisibility(View.GONE);
			if (result != null && result.isSuccess()) {
				if(result.movies != null && result.movies.size() > 0) {
					System.out.println("进入首页时间消耗：---  "+(System.currentTimeMillis()-time));
					AppConstants.movieInfo = result;
					Intent in = new Intent(LoadingActivity.this, ZSQMainTabActivity.class);
//					in.putExtra("downloadUrl", "");
					in.putExtra("movieInfo", result);
					startActivity(in);
					finish();
				} else {
					MessageDialog.getInstance().setData(LoadingActivity.this, result.result.message, true);
				}
//				if (upDateBean != null) {
//					updateFlag = true;
//					if (upDateBean.updateReturn.equals("-2")) {// 已是最新版本
//						Intent in = new Intent(LoadingActivity.this, ZSQMainTabActivity.class);
//						in.putExtra("downloadUrl", "");
//						in.putExtra("movieInfo", result);
//						startActivity(in);
//						finish();
//					} else if (upDateBean.updateReturn.equals("1")) {// 服务器返回有版本更新
//						try {
//							// 返回的版本与当前版本相同，这里是针对服务器返回错误时做处理，一般不走这里
//							if (upDateBean.AppVersion.equals(PublicUtils
//									.getVersionName(LoadingActivity.this))) {
//								Intent in = new Intent(LoadingActivity.this,
//										ZSQMainTabActivity.class);
//								in.putExtra("movieInfo", result);
//								in.putExtra("downloadUrl", "");
//								startActivity(in);
//								finish();
//							} else {// 确实可更新
//								Intent in = new Intent(LoadingActivity.this,
//										ZSQMainTabActivity.class);
//								in.putExtra("movieInfo", result);
//								in.putExtra("downloadUrl", upDateBean.DownLoad);
//								startActivity(in);
//								finish();
//							}
//
//						} catch (Exception e) {
//							e.printStackTrace();
//							Intent in = new Intent(LoadingActivity.this, ZSQMainTabActivity.class);
//							in.putExtra("movieInfo", result);
//							in.putExtra("downloadUrl", "");
//							startActivity(in);
//							finish();
//						}
//					} else {// 服务器返回其他值
//						Intent in = new Intent(LoadingActivity.this, ZSQMainTabActivity.class);
//						in.putExtra("movieInfo", result);
//						in.putExtra("downloadUrl", "");
//						startActivity(in);
//						finish();
//					}
//
//				} else {// 服务器返回控制
//					Intent in = new Intent(LoadingActivity.this, ZSQMainTabActivity.class);
//					in.putExtra("movieInfo", result);
//					in.putExtra("downloadUrl", "");
//					startActivity(in);
//					finish();
//				}

			} else {
//				Toast.makeText(LoadingActivity.this, "网络连接异常,请检查网络连接！", Toast.LENGTH_LONG).show();
//				finish();
				MessageDialog.getInstance().setData(LoadingActivity.this, R.string.data_failed_to_load, true);
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 判断SDK版本
			int sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
			// 如果是2.2返回sdkVersion =8

			// 加判断
			ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			if (sdkVersion < 8) {
				manager.restartPackage(getPackageName());
				System.exit(0);
			} else {
				Intent startMain = new Intent(Intent.ACTION_MAIN);
				startMain.addCategory(Intent.CATEGORY_HOME);
				startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(startMain);
				System.exit(0);

			}
		}
		return false;
	}

	public void onResume() {
		super.onResume();
		CmccDataStatistics.onStart(this);

		if (!spUtil.IsPostFlowStatistics()) {
			String mobile = "";
			try {
				mobile = spUtil.getUserName();
			} catch (Exception e) {
			}
			new GetFlowStatisticsTask(this).execute(MovieApplication.getAppVersionName(this),
					PublicUtils.getIMEI(this), PublicUtils.getIMSI(this), mobile,
					PublicUtils.getDouble(spUtil.get2G()), PublicUtils.getDouble(spUtil.get3G()),
					PublicUtils.getDouble(spUtil.getWIFI()), spUtil.getStime(), spUtil.getEtime());
		} else {
			spUtil.savePostFlowStatistics(false);
			spUtil.saveStime(PublicUtils.getStime());// 保存统计开始时间
			spUtil.saveEtime(0);// 初始化统计结束时间
		}
	}

	public void onPause() {
		super.onPause();
		CmccDataStatistics.onStop(this);
	}

	class GetFlowStatisticsTask extends MovieAsyncTask<String, Integer, FlowStatisticsBean> {

		String exception = null;

		public GetFlowStatisticsTask(Activity activity) {
			super(activity, null, true, true, false);
		}

		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
		}

		@Override
		protected FlowStatisticsBean doInBackground(String... params) {
			FlowStatisticsBean json = null;
			try {
				json = lib.FlowStatisticsRequest(params[0], params[1], params[2], params[3],
						params[4], params[5], params[6], params[7], params[8]);
			} catch (HttpException e) {
				exception = getResources().getString(R.string.exception_network);
			} catch (IOException e) {
				exception = getResources().getString(R.string.exception_network);
			} catch (JSONException e) {
				exception = getResources().getString(R.string.exception_json);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return json;
		}

		@Override
		protected void onPostExecute(FlowStatisticsBean result) {
			super.onPostExecute(result);
			System.out.println("----------GetFlowStatisticsTask  result--------" + result);
//			if (exception != null) {
//				ToastAlone.showToast(LoadingActivity.this, exception, Toast.LENGTH_SHORT);
//				return;
//			}
			if (result != null) {
				if ("1".equals(result.result)) {
					// 提交成功
					System.out.println("----------GetFlowStatisticsTask  result--------" + result);
					spUtil.save2G(0);// 初始化2G流量数据
					spUtil.save3G(0);// 初始化3G流量数据
					spUtil.saveWIFI(0);// 初始化WIFI流量数据
					spUtil.saveStime(PublicUtils.getStime());// 保存统计开始时间
					spUtil.saveEtime(0);// 初始化统计结束时间
					spUtil.savePostFlowStatistics(true);
				}
			} else {
				// ToastAlone.showToast(LoadingActivity.this,
				// R.string.error_msg, Toast.LENGTH_SHORT);
			}
		}

	}
}
