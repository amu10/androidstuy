package com.szcmcc.movie.activity;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.network.LoadingDialog;
import com.szcmcc.movie.storage.SharedPreferencesUtil;
import com.szcmcc.movie.util.Log;
import com.weibo.net.AccessToken;
import com.weibo.net.AsyncWeiboRunner;
import com.weibo.net.AsyncWeiboRunner.RequestListener;
import com.weibo.net.DialogError;
import com.weibo.net.Oauth2AccessTokenHeader;
import com.weibo.net.Utility;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboDialogListener;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;

/**
 * 12-分享微博
 * 
 * @author Administrator
 * 
 */
public class SinaShareAct extends BaseActivity implements RequestListener {

	private ImageView fenxiang = null;
	private ImageButton imBack;
	private Context mContext;
	private SharedPreferencesUtil spUtil;
	private LoadingDialog mLoadingDialog;
	private boolean isSinaBinging = false;
	private TextView share_textview_status;
	private boolean isFromMovieDetailActivity = false;
	private String m_name = "", introduce = "";
	private String expires_in_time = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sinashareact);
		mContext = this;

		spUtil = SharedPreferencesUtil.getInstance(mContext);
		expires_in_time = spUtil.getString("expires_in", "0");
		initTitleBar();
		fenxiang = (ImageView) findViewById(R.id.fenxiang);
		imBack = (ImageButton) findViewById(R.id.imBack);
		share_textview_status = (TextView) findViewById(R.id.share_textview_status);
		mLoadingDialog = new LoadingDialog(SinaShareAct.this);
		mLoadingDialog.setLoadingText("正在分享...");
		imBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		fenxiang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (et_content.length() > 140) {
					Toast.makeText(SinaShareAct.this, "评论内容不能超过140字", Toast.LENGTH_LONG).show();
					return;
				}
				final Weibo weibo = Weibo.getInstance();
				if (!isSinaBinging
						|| (System.currentTimeMillis()
								- Long.parseLong(spUtil.getString("expires_in_old", "0")) > (Long
								.parseLong(expires_in_time) * 1000))
						|| spUtil.getSinaToken().equals("")) {
					// 未绑定
					new AlertDialog.Builder(mContext).setTitle("提示")
							.setMessage("你还没有绑定新浪微博，是否立即绑定？")
							.setNegativeButton("取消", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
								}
							}).setPositiveButton("绑定", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// new
									// OAuthSinaTask().execute(CampusApplication.WEIBO_SINA_CALLBACKURL);

									weibo.setupConsumerConfig(MovieApplication.WEIBO_SINA_KEY,
											MovieApplication.WEIBO_SINA_SECRET);

									// Oauth2.0
									// 隐式授权认证方式
									weibo.setRedirectUrl(MovieApplication.WEIBO_SINA_CALLBACKURL);// 此处回调页内容应该替换为与appkey对应的应用回调页
									// 对应的应用回调页可在开发者登陆新浪微博开发平台之后，
									// 进入我的应用--应用详情--应用信息--高级信息--授权设置--应用回调页进行设置和查看，
									// 应用回调页不可为空

									weibo.authorize(SinaShareAct.this, new AuthDialogListener());
									// new
									// OAuthSinaTask(WritePinLunAct.this)
									// .execute(MovieApplication.WEIBO_SINA_CALLBACKURL);
								}
							}).create().show();
				} else {
					if (isSinaBinging) {// 已绑定
						shareSinaWeibo();
					}
				}
			}
		});

		onInputNum();
		setSinaShareStatus();
	}

	EditText et_content;// 定义一个文本输入框
	TextView tv_num;// 用来显示剩余字数
	int num = 140;// 限制的最大字数

	private void onInputNum() {
		et_content = (EditText) findViewById(R.id.et_content);
		tv_num = (TextView) findViewById(R.id.tv_num);
		Intent in = getIntent();
		if (in.getExtras() != null && !in.getExtras().equals("") && !in.getExtras().equals("null")) {
			isFromMovieDetailActivity = in.getExtras().getBoolean("isFromMovieDetailActivity");
			m_name = in.getExtras().getString("name");
			introduce = in.getExtras().getString("introduce");
		}
		if (isFromMovieDetailActivity) {
			String contextLinshi = "“ " + m_name + " ”  " + introduce + "  （来自深圳爱电影Android客户端）";
			String context = contextLinshi;
			try {
				if (contextLinshi.length() > 140) {
					context = contextLinshi.substring(0, 137) + "...";
				}

				et_content.setText(context);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			et_content.setText(R.string.share_soft_context);
		}
		tv_num.setText(et_content.getText().length() + "");
		et_content.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;
			private int selectionStart;
			private int selectionEnd;

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				temp = s;
				Log.i("-----------onTextChanged s" + s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				int number = s.length();
				tv_num.setText("" + number);
				selectionStart = et_content.getSelectionStart();
				selectionEnd = et_content.getSelectionEnd();
				// System.out.println("start="+selectionStart+",end="+selectionEnd);
				if (temp.length() > num) {
					// s.delete(selectionStart - 1, selectionEnd);
					// int tempSelection = selectionStart;
					// et_content.setText(s);
					// et_content.setSelection(tempSelection);// 设置光标在最后
					tv_num.setText("" + (num - number));
				}
			}
		});

	}

	/**
	 * 设置绑定状态
	 */
	private void setSinaShareStatus() {

		// Weibo weibo = Weibo.getInstance();
		// if(weibo.getAccessToken()==null){
		// isSinaBinging = false;
		// share_textview_status.setText("未绑定");
		// }else{ ||(TextUtils.isEmpty((String)
		// (weibo.getAccessToken().getToken())))

		if (spUtil.getSinaToken().equals("0")
				|| (System.currentTimeMillis()
						- Long.parseLong(spUtil.getString("expires_in_old", "0")) > (Long
						.parseLong(expires_in_time) * 1000)) || spUtil.getSinaToken().equals("")) {
			isSinaBinging = false;
			share_textview_status.setText("未绑定");
		} else {
			isSinaBinging = true;
			share_textview_status.setText("已绑定");
		}
		// }
	}

	class AuthDialogListener implements WeiboDialogListener {

		@Override
		public void onComplete(Bundle values) {
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
			spUtil.putString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN, weibo.getAccessToken()
					.getToken());
			spUtil.putString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN_SECRET, weibo
					.getAccessToken().getSecret());
			spUtil.putString("expires_in", expires_in);
			spUtil.putString("expires_in_old", System.currentTimeMillis() + "");
			spUtil.setSinaToken(token);
			// Intent intent = new Intent();
			// // intent.setClass(AuthorizeActivity.this, TestActivity.class);
			// startActivity(intent);
			spUtil.setWeiboSinaBinded(true);
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
			// if (!isSinaBinging) {
			// checkBox.setChecked(false);
			// isCheckText = false;
			// }
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(getApplicationContext(), "Auth exception : " + e.getMessage(),
					Toast.LENGTH_LONG).show();
		}

	}

	private void share2weibo() {
		try {
			String con = et_content.getText().toString();
			if (con.trim().length() >= 140)
				con = con.substring(0, 140);
			Utility.setAuthorization(new Oauth2AccessTokenHeader());
			AccessToken accessToken = new AccessToken(spUtil.getSinaToken(),
					MovieApplication.WEIBO_SINA_SECRET);
			accessToken.setExpiresIn(spUtil.getString("expires_in", "0"));
			Weibo weibo = Weibo.getInstance();
			weibo.setAccessToken(accessToken);
			weibo.setupConsumerConfig(spUtil.getString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN),
					spUtil.getString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN_SECRET));
			// weibo.share2weibo(this,
			// spUtil.getString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN),spUtil.getString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN_SECRET),
			// con, null);
			update(weibo, Weibo.getAppKey(), con, "", "");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// handler.sendEmptyMessage(1);
		}
		// Weibo weibo = Weibo.getInstance();
		// weibo.share2weibo(this, weibo.getAccessToken().getToken(),
		// weibo.getAccessToken()
		// .getSecret(), content, null);
	}

	public void shareSinaWeibo() {

		// final Weibo weibo = Weibo.getInstance();
		// // System.out.println("dongdianzhou" +
		// spUtil.getString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN) + "    "
		// // +
		// spUtil.getString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN_SECRET));
		// weibo.setAccessToken(new
		// AccessToken(spUtil.getString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN),
		// spUtil
		// .getString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN_SECRET)));
		// dialog = new ProgressDialog(SinaShareAct.this);
		if (!mLoadingDialog.isShow()) {
			// dialog.setMessage("正在分享...");
			mLoadingDialog.show();
		}
		new Thread(new Runnable() {
			@Override
			public void run() {

				// 发表微博
				// try {
				// String con = input.getText().toString();
				// if (con.trim().length() >= 130)
				// con = con.substring(0, 130);
				// update(weibo, MovieApplication.WEIBO_SINA_KEY, con);// 发送微博
				// handler.sendEmptyMessage(0);
				// } catch (Exception e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// handler.sendEmptyMessage(1);
				// }
				share2weibo();
				// Intent i = new Intent(WritePinLunAct.this,
				// ShareActivity.class);
				// WritePinLunAct.this.startActivity(i);
			}
		}).start();
	}

	private String upload(Weibo weibo, String source, String file, String status, String lon,
			String lat) throws WeiboException {
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", source);
		bundle.add("pic", file);
		bundle.add("status", status);
		if (!TextUtils.isEmpty(lon)) {
			bundle.add("lon", lon);
		}
		if (!TextUtils.isEmpty(lat)) {
			bundle.add("lat", lat);
		}
		String rlt = "";
		String url = Weibo.SERVER + "statuses/upload.json";
		AsyncWeiboRunner weiboRunner = new AsyncWeiboRunner(weibo);
		weiboRunner.request(this, url, bundle, Utility.HTTPMETHOD_POST, this);

		return rlt;
	}

	private String update(Weibo weibo, String source, String status, String lon, String lat)
			throws MalformedURLException, IOException, WeiboException {
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", source);
		bundle.add("status", status);
		if (!TextUtils.isEmpty(lon)) {
			bundle.add("lon", lon);
		}
		if (!TextUtils.isEmpty(lat)) {
			bundle.add("lat", lat);
		}
		String rlt = "";
		String url = Weibo.SERVER + "statuses/update.json";
		AsyncWeiboRunner weiboRunner = new AsyncWeiboRunner(weibo);
		weiboRunner.request(this, url, bundle, Utility.HTTPMETHOD_POST, this);
		return rlt;
	}

	// public void shareSinaWeibo() {
	// final Weibo weibo = Weibo.getInstance();
	// System.out.println("dongdianzhou" +
	// spUtil.getString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN) + "    "
	// + spUtil.getString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN_SECRET));
	// weibo.setAccessToken(new
	// AccessToken(spUtil.getString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN),
	// spUtil
	// .getString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN_SECRET)));
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	//
	// // 发表微博
	// try {
	// String con = et_content.getText().toString();
	// if (con.trim().length() >= 130)
	// con = con.substring(0, 130);
	// update(weibo, MovieApplication.WEIBO_SINA_KEY, con);// 发送微博
	// //+" http://wap.szicity.com/m/41OXKeTG_dO5F3crpzmfcQdWbc_2fMdulz6jQdtEPlZTtwDkU6HiRiQKSTCEYnyx5s-6C3Wg5eYe0Xox8V45EfvEziDxUBoLVnI4D9v-z30Y31c=.html"
	// // handler.sendEmptyMessage(0);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// // handler.sendEmptyMessage(1);
	// }
	// }
	// }).start();
	// }

	// private class OAuthSinaTask extends MovieAsyncTask<String, String,
	// String> {
	// private String sina_exception = null;
	//
	// public OAuthSinaTask(Activity activity) {
	// super(activity, null, true, true, true);
	// }
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// }
	//
	// @Override
	// protected String doInBackground(String... params) {
	// String authUrl = null;
	// try {
	// authUrl =
	// app.getSinaOAuthProvider().retrieveRequestToken(app.getSinaOAuthConsumer(),
	// params[0]);
	// } catch (OAuthMessageSignerException e) {
	// sina_exception = e.getMessage();
	// e.printStackTrace();
	// } catch (OAuthNotAuthorizedException e) {
	// sina_exception = e.getMessage();
	// e.printStackTrace();
	// } catch (OAuthExpectationFailedException e) {
	// sina_exception = e.getMessage();
	// e.printStackTrace();
	// } catch (OAuthCommunicationException e) {
	// sina_exception = e.getMessage();
	// e.printStackTrace();
	// }
	// return authUrl;
	// }
	//
	// @Override
	// protected void onPostExecute(String result) {
	// super.onPostExecute(result);
	// if (sina_exception != null) {
	// Toast.makeText(SinaShareAct.this, sina_exception, Toast.LENGTH_SHORT);
	// } else {
	// Intent intent = new Intent(SinaShareAct.this, ShareToSinaLogin.class);
	// intent.setData(Uri.parse(result));
	// mContext.startActivity(intent);
	// }
	// }
	//
	// }

	/**
	 * 
	 * @param weibo
	 * @param source
	 * @param status
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws WeiboException
	 */
	private String update(Weibo weibo, String source, String status) throws MalformedURLException,
			IOException, WeiboException {
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", source);
		bundle.add("status", status);
		String rlt = "";
		String url = Weibo.SERVER + "statuses/update.json";
		AsyncWeiboRunner weiboRunner = new AsyncWeiboRunner(weibo);
		weiboRunner.request(this, url, bundle, Utility.HTTPMETHOD_POST, this);
		return rlt;
	}

	@Override
	public void onComplete(String response) {
		System.out.println("response=onComplete-====" + response);
		handler.sendEmptyMessage(0);
	}

	@Override
	public void onIOException(IOException e) {
		// TODO Auto-generated method stub
		e.printStackTrace();
		handler.sendEmptyMessage(1);
	}

	@Override
	public void onError(WeiboException e) {
		// TODO Auto-generated method stub
		e.printStackTrace();
		handler.sendEmptyMessage(1);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (mLoadingDialog != null && mLoadingDialog.isShow()) {
				mLoadingDialog.close();
			}
			switch (msg.what) {
			case 0:
				System.out.println("0000000000000");
				spUtil.putString("expires_in_old", System.currentTimeMillis() + "");
				Toast.makeText(SinaShareAct.this, "分享成功", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				System.out.println("111111111111");
				Toast.makeText(SinaShareAct.this, "分享失败", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				System.out.println("22222222");
				try {
					JSONObject object = new JSONObject(msg.obj.toString());
					if (object.has("errcode")) {
						String errcode = object.getString("errcode");
						if ("0".equals(errcode)) {
							spUtil.putString("expires_in_old", System.currentTimeMillis() + "");
							Toast.makeText(SinaShareAct.this, "分享成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(SinaShareAct.this, object.getString("msg"),
									Toast.LENGTH_SHORT).show();
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(SinaShareAct.this, "分享失败", Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
			}
			SinaShareAct.this.finish();
		}
	};

	public void onResume() {
		super.onResume();
		setSinaShareStatus();
		CmccDataStatistics.onStart(this);
	}

	public void onPause() {
		super.onPause();
		CmccDataStatistics.onStop(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		app.getAsyncImageLoader().recycleBitmaps();
	}
}
