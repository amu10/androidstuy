package com.szcmcc.movie.activity;

import java.io.IOException;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.MovieInfo;
import com.szcmcc.movie.bean.UpDateBean;
import com.szcmcc.movie.bean.UserBean;
import com.szcmcc.movie.cache.MovieSaveDao;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.storage.SharedPreferencesUtil;
import com.szcmcc.movie.util.PublicUtils;
import com.weibo.net.AccessToken;
import com.weibo.net.DialogError;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboDialogListener;
import com.weibo.net.WeiboException;

public class SettingAct extends ZSQBaseActivity {
//	private ImageButton imBack;
	// private CheckBox bound_c = null;
	private LinearLayout account_l = null;
	private LinearLayout sina_l = null;
	private LinearLayout myzone_l = null;
	private LinearLayout share_l = null;
	private LinearLayout update_l = null;
	private LinearLayout about_l = null;
	private LinearLayout myCollect = null;// 我的收藏
	SharedPreferencesUtil spUtil;
	private Context mContext = SettingAct.this;
	UserBean userBean;
	private TextView textAccountState;
	private TextView textUserNum, textview_shareSina;
	private UpDateBean upDateBean = null;
	private MovieInfo movieInfo = null;
	// 新浪微博
	public boolean isSinaBinging;
	private String expires_in_time = "0";
	private TextView tvNum;// 收藏电影数量
	private Activity myActivity;

	public void release() {
		if (account_l != null) {
			account_l.removeAllViews();
			account_l = null;
		}
		if (sina_l != null) {
			sina_l.removeAllViews();
			sina_l = null;
		}
		if (myzone_l != null) {
			myzone_l.removeAllViews();
			myzone_l = null;
		}
		if (share_l != null) {
			share_l.removeAllViews();
			share_l = null;
		}
		if (update_l != null) {
			update_l.removeAllViews();
			update_l = null;
		}
		if (about_l != null) {
			about_l.removeAllViews();
			about_l = null;
		}
		if (myCollect != null) {
			myCollect.removeAllViews();
			myCollect = null;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settingact);
		myActivity = this;
		spUtil = SharedPreferencesUtil.getInstance(this);
		expires_in_time = spUtil.getString("expires_in", "0");
		
		findViewByIds();
		
		Intent in = getIntent();
		if (in.getExtras() != null) {
			try {
				movieInfo = (MovieInfo) in.getExtras().getSerializable("movieInfo");
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
//		initTitleBar();
		account_l.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// if(userBean == null){
				if (spUtil.getUserName().equals("")) {
					Intent i = new Intent(mContext, UserLoginAct.class);
					// mContext.startActivity(i);
					startActivityForResult(i, UserLoginAct.FLAG_LOGIN);
				} else {
					// 弹出提示框 是否退出登录
					dialogLogout();
				}
			}
		});

		myCollect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, ZSQMyCollectAct.class);
				intent.putExtra("movieInfo", movieInfo);
				mContext.startActivity(intent);
			}
		});

		sina_l.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (isSinaBinging) {// 已绑定
					Builder dialog = new AlertDialog.Builder(SettingAct.this);
					dialog.setTitle("确定要解除新浪微博绑定?");
					dialog.setPositiveButton("以后再说", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {

						}
					});
					dialog.setNegativeButton("解除绑定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							spUtil.setSinaToken("0");
							spUtil.setSinaTokenSecret("0");
							textview_shareSina.setText("未绑定"); // 设置绑定的条目
							isSinaBinging = false;
						}
					});
					dialog.show();
				} else { // 未绑定
					// new
					// OAuthSinaTask(myActivity).execute(MovieApplication.WEIBO_SINA_CALLBACKURL);
					Weibo weibo = Weibo.getInstance();
					weibo.setupConsumerConfig(MovieApplication.WEIBO_SINA_KEY,
							MovieApplication.WEIBO_SINA_SECRET);

					// Oauth2.0
					// 隐式授权认证方式
					weibo.setRedirectUrl(MovieApplication.WEIBO_SINA_CALLBACKURL);// 此处回调页内容应该替换为与appkey对应的应用回调页
					// 对应的应用回调页可在开发者登陆新浪微博开发平台之后，
					// 进入我的应用--应用详情--应用信息--高级信息--授权设置--应用回调页进行设置和查看，
					// 应用回调页不可为空

					weibo.authorize(SettingAct.this, new AuthDialogListener());
				}

			}
		});

		myzone_l.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (!spUtil.getUserName().equals("")) {
					Intent intent = new Intent(mContext, MyZoneAct.class);
					intent.putExtra("movieInfo", movieInfo);
					mContext.startActivity(intent);
				} else {
					Intent intent = new Intent(SettingAct.this, UserLoginAct.class);
					intent.putExtra("entry", MovieApplication.ENTRY_MY_ZOON);
					startActivityForResult(intent, UserLoginAct.FLAG_LOGIN);
				}
			}
		});

		share_l.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				openDialog();
				mAlertDialog.show();
			}
		});

		update_l.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				new UpDate(SettingAct.this, "检测升级中...").execute();

			}
		});

		about_l.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent in = new Intent(SettingAct.this, AboutActivity.class);
				mContext.startActivity(in);
			}
		});

//		imBack.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		});
		// setSinaShareStatus();// 设置新浪分享的状态
	}

	private AlertDialog mAlertDialog = null;
	private String items[] = null;

	private void openDialog() {
		items = new String[] { "新浪微博分享", "短信分享", "邮件分享", "取消" };

		mAlertDialog = new AlertDialog.Builder(this).setTitle("分享软件")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						switch (which) {
						case 0:

							Intent i = new Intent(SettingAct.this, SinaShareAct.class);
							startActivity(i);
							mAlertDialog.dismiss();
							break;
						case 1:
							// /下方是是通过Intent调用系统的图片查看器的关键代码
							// Intent localIntent = new Intent();
							// localIntent.setType("image/*");
							// localIntent.setAction("android.intent.action.GET_CONTENT");
							// startActivityForResult(localIntent,
							// PHOTO_PICKED_WITH_DATA);
							Intent intent = new Intent(android.content.Intent.ACTION_SENDTO);
							Uri smsToUri = Uri.parse("smsto:");
							intent.setData(smsToUri);
							intent.putExtra("sms_body",
									"“深圳爱电影Android客户端”  我正在使用一款很不错的Android应用：深圳爱电影。有了它，我可以很方便的在手机上预订电影票！大家都可以下载试试哦！（来自深圳爱电影Android客户端）");
							try {
								startActivity(intent);
							} catch (Exception e) {
								startActivity(Intent.createChooser(intent, "选择短信程序"));
							}
							mAlertDialog.dismiss();
							break;
						case 2:
							Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
							emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							emailIntent.setType("plain/text");
							emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "");
							emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
									R.string.app_name);
							emailIntent
									.putExtra(android.content.Intent.EXTRA_TEXT,
											"“深圳爱电影Android客户端”  我正在使用一款很不错的Android应用：深圳爱电影。有了它，我可以很方便的在手机上预订电影票！大家都可以下载试试哦！（来自深圳爱电影Android客户端）");
							try {
								startActivity(Intent.createChooser(emailIntent, "选择邮件程序"));
							} catch (Exception e) {
								startActivity(emailIntent);
							}

							mAlertDialog.dismiss();
							break;
						}
					}

				}).create();
	}

	private void findViewByIds() {
		tvNum = (TextView) findViewById(R.id.tvNum);
//		imBack = (ImageButton) findViewById(R.id.imBack);
		account_l = (LinearLayout) findViewById(R.id.account_l);
		sina_l = (LinearLayout) findViewById(R.id.sina_l);
		myzone_l = (LinearLayout) findViewById(R.id.myzone_l);
		share_l = (LinearLayout) findViewById(R.id.share_l);
		update_l = (LinearLayout) findViewById(R.id.update_l);
		about_l = (LinearLayout) findViewById(R.id.about_l);
		myCollect = (LinearLayout) findViewById(R.id.myCollect);
		textview_shareSina = (TextView) findViewById(R.id.share_textview_sina);
		textAccountState = (TextView) findViewById(R.id.setting_account_state_text);
		textUserNum = (TextView) findViewById(R.id.setting_user_num_text);
		
	}

	class AuthDialogListener implements WeiboDialogListener {

		@Override
		public void onComplete(Bundle values) {
			System.out.println("values-----------" + values);
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			expires_in_time = expires_in;
			System.out.println("expires_in   -------  " + expires_in);
			// mToken.setText("access_token : " + token + "  expires_in: "
			// + expires_in);293561c33ba8c9c2796a953a3c7a9e21
			AccessToken accessToken = new AccessToken(token, MovieApplication.WEIBO_SINA_SECRET);
			accessToken.setExpiresIn(expires_in);
			Weibo.getInstance().setAccessToken(accessToken);
			Weibo weibo = Weibo.getInstance();
			spUtil.setSinaToken(weibo.getAccessToken().getToken());
			spUtil.setSinaTokenSecret(weibo.getAccessToken().getSecret());
			spUtil.putString("expires_in", expires_in);
			spUtil.putString("expires_in_old", System.currentTimeMillis() + "");
			spUtil.setSinaToken(token);
			setSinaShareStatus();
		}

		@Override
		public void onError(DialogError e) {
			// Toast.makeText(getApplicationContext(), "Auth error : " +
			// e.getMessage(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCancel() {
			// Toast.makeText(getApplicationContext(), "Auth cancel",
			// Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(getApplicationContext(), "Auth exception : " + e.getMessage(),
					Toast.LENGTH_LONG).show();
		}

	}

	private void setSinaShareStatus() {
		if (spUtil.getSinaToken().equals("0")
				|| (System.currentTimeMillis()
						- Long.parseLong(spUtil.getString("expires_in_old", "0")) > (Long
						.parseLong(expires_in_time) * 1000)) || spUtil.getSinaToken().equals("")) {
			isSinaBinging = false;
			textview_shareSina.setText("未绑定");
		} else {
			isSinaBinging = true;
			textview_shareSina.setText("解除绑定");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO requestCode 与resultCode 都等于0 时为登录返回
		if (resultCode == UserLoginAct.FLAG_LOGIN) {
			//
			userBean = (UserBean) data.getSerializableExtra("UserBean");
			if (userBean.result == 1) {
				// 登录成功
				textAccountState.setText("账户登出");
				textUserNum.setVisibility(View.VISIBLE);
				textUserNum.setText(spUtil.getUserName());
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void dialogLogout() {
		AlertDialog.Builder ab = new Builder(this);
		ab.setTitle("登出提示");
		ab.setMessage("是否登出");
		ab.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//
				CmccDataStatistics.Comm_Logout(SettingAct.this, spUtil.getUserName());
				spUtil.clearNameAndPassword();
				textAccountState.setText("账户登录");
				textUserNum.setVisibility(View.GONE);
				textUserNum.setText("");
				userBean = null;
			}
		});
		ab.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//

			}
		});
		ab.show();
	}

	private String[] getAppKey() {
		ApplicationInfo info;
		String testData[] = new String[2];
		try {
			info = this.getPackageManager().getApplicationInfo(getPackageName(),
					PackageManager.GET_META_DATA);

			if (info == null) {
				System.out.println("info       ---");
			}
			if (info.metaData == null) {
				System.out.println("info.metaData      ---");
			}
			testData[0] = info.metaData.getString("SZICITY_APPKEY");
			testData[1] = info.metaData.getString("SZICITY_CHANNEL");
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return testData;
	}

	class UpDate extends MovieAsyncTask<String, String, UpDateBean> {
		public String exception;

		public UpDate(Activity activity, String loadingText) {
			super(activity, null, true, true, true, loadingText);
		}

		@Override
		protected UpDateBean doInBackground(String... params) {
			MovieLib lib = MovieLib.getInstance(SettingAct.this);
			try {
				upDateBean = lib.update(getAppKey()[0], getAppKey()[1],
						PublicUtils.getVersionName(SettingAct.this));
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
			return upDateBean;
		}

		@Override
		protected void onPostExecute(UpDateBean result) {
			super.onPostExecute(result);
			if (result != null && !result.updateReturn.equals("")) {
				if (upDateBean.updateReturn.equals("-2")) {
					Toast.makeText(SettingAct.this, "当前已是最新版本！", Toast.LENGTH_SHORT).show();
				} else if (upDateBean.updateReturn.equals("1")) {
					try {
						if (upDateBean.AppVersion != null) {
							if (!upDateBean.AppVersion.equals(PublicUtils
									.getVersionName(SettingAct.this))) {
								showDialogUp(upDateBean.DownLoad);
							} else {
								Toast.makeText(SettingAct.this, "当前已是最新版本！", Toast.LENGTH_SHORT)
										.show();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Toast.makeText(SettingAct.this, "版本检测失败，请重新尝试！", Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(SettingAct.this, "版本检测失败，请重新尝试！", Toast.LENGTH_LONG).show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	private void showDialogUp(final String url) throws Exception {
		final AlertDialog.Builder builder = new AlertDialog.Builder(SettingAct.this);
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

	private void showDialog() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(SettingAct.this);
		builder.setTitle("升级提示!");
		builder.setMessage("当前版本已是最新版本!");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

			}
		});
		builder.create().show();
	}

	private void showDialogUp() throws Exception {
		final AlertDialog.Builder builder = new AlertDialog.Builder(SettingAct.this);
		builder.setTitle("升级提示!");
		builder.setMessage("发现新版本 ，是否下载新版本？");
		builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

			}
		});
		builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

			}
		});
		builder.create().show();
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
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// if(NetImageView.isWifi(mContext)){
			showDailog("您确定要退出么？");
			// }else{
			// finish();
			// }
		}
		return false;
	}

	public void onResume() {
		super.onResume();
		setSinaShareStatus();
		CmccDataStatistics.onStart(this);
		if (spUtil.getUserName().equals("")) {
			textUserNum.setVisibility(View.GONE);
		} else {
			textAccountState.setText("账户登出");
			textUserNum.setText(spUtil.getUserName());
			textUserNum.setVisibility(View.VISIBLE);
		}

		MovieSaveDao movieSaveDao = new MovieSaveDao(SettingAct.this);
		int i = movieSaveDao.getSaveMovieNum();
		tvNum.setText("" + i);
	}

	public void onPause() {
		super.onPause();
		CmccDataStatistics.onStop(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// app.getAsyncImageLoader().recycleBitmaps();
	}
}
