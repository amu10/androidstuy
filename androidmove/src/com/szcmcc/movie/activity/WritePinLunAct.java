package com.szcmcc.movie.activity;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.HttpException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.MoviePinLunList;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.storage.SharedPreferencesUtil;
import com.weibo.net.AccessToken;
import com.weibo.net.AsyncWeiboRunner;
import com.weibo.net.AsyncWeiboRunner.RequestListener;
import com.weibo.net.DialogError;
import com.weibo.net.Oauth2AccessTokenHeader;
import com.weibo.net.Utility;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboDialog;
import com.weibo.net.WeiboDialogListener;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;

/**
 * 06-影片评论-写 评论
 * 
 * @author Administrator
 * 
 */
public class WritePinLunAct extends BaseActivity implements RequestListener {

	private Context mContext;
	private RatingBar pointtrate = null;
	private ImageButton imBack;
	private ImageView fabiao = null;
	private MoviePinLunList moviePinLunList;
	private CheckBox checkBox;
	TextView checkText;
	private EditText input;
	private String m_id = "", content = "", upcomming = "", rate = "5.0", name = "", imageUrl = "",
			uid = "", token = "";
	private boolean isMoviesLoadAll = false;
	private boolean isSinaBinging = false;
	private SharedPreferencesUtil spUtil;
	boolean isCheckText = false;
	private TextView point = null;

	private String expires_in_time = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.writepinlunact);
		mContext = this;
		initTitleBar();
		spUtil = SharedPreferencesUtil.getInstance(this);
		expires_in_time = spUtil.getString("expires_in", "0");
		m_id = getIntent().getExtras().getString("m_id");
		upcomming = getIntent().getExtras().getString("upcomming");
		// name = getIntent().getExtras().getString("name");
		// imageUrl = getIntent().getExtras().getString("imageUrl");
		uid = getIntent().getExtras().getString("uid");
		token = getIntent().getExtras().getString("token");
		pointtrate = (RatingBar) findViewById(R.id.rating);
		fabiao = (ImageView) findViewById(R.id.fabiao);
		checkBox = (CheckBox) findViewById(R.id.check);
		point = (TextView) findViewById(R.id.point);
		imBack = (ImageButton) findViewById(R.id.imBack);
		checkText = (TextView) findViewById(R.id.checkText);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {// 选中
					isCheckText = true;
					System.out.println("00-----------"
							+ (System.currentTimeMillis() - Long.parseLong(spUtil.getString(
									"expires_in_old", "0"))));
					System.out.println("11-----------" + Long.parseLong(expires_in_time) * 1000);
					final Weibo weibo = Weibo.getInstance();
					if (!isSinaBinging
							|| (System.currentTimeMillis()
									- Long.parseLong(spUtil.getString("expires_in_old", "0")) > (Long
									.parseLong(expires_in_time) * 1000))
							|| spUtil.getSinaToken().equals("")) {
						// 未绑定
						final AlertDialog a = new AlertDialog(mContext) {

							@Override
							public void cancel() {
								// TODO Auto-generated method stub
								super.cancel();
								System.out.println("cancel00000");
								checkBox.setChecked(false);
								isCheckText = false;
							}

						};
						a.setTitle("提示");
						a.setMessage("你还没有绑定新浪微博，是否立即绑定？");
						a.setButton2("取消", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

								checkBox.setChecked(false);
								isCheckText = false;
								if (a.isShowing()) {
									a.cancel();
								}
							}
						});
						a.setButton("绑定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								weibo.setupConsumerConfig(MovieApplication.WEIBO_SINA_KEY,
										MovieApplication.WEIBO_SINA_SECRET);

								// Oauth2.0
								// 隐式授权认证方式
								weibo.setRedirectUrl(MovieApplication.WEIBO_SINA_CALLBACKURL);// 此处回调页内容应该替换为与appkey对应的应用回调页
								// 对应的应用回调页可在开发者登陆新浪微博开发平台之后，
								// 进入我的应用--应用详情--应用信息--高级信息--授权设置--应用回调页进行设置和查看，
								// 应用回调页不可为空

								weibo.authorize(WritePinLunAct.this, new AuthDialogListener());
							}
						});
						a.show();
						// new
						// AlertDialog.Builder(mContext).setTitle("提示").setMessage("你还没有绑定新浪微博，是否立即绑定？")
						// .setNegativeButton("取消", new
						// DialogInterface.OnClickListener() {
						//
						// @Override
						// public void onClick(DialogInterface dialog, int
						// which) {
						// // TODO Auto-generated method stub
						// checkBox.setChecked(false);
						// isCheckText = false;
						// }
						// }).setPositiveButton("绑定", new
						// DialogInterface.OnClickListener() {
						//
						// @Override
						// public void onClick(DialogInterface dialog, int
						// which) {
						// // new
						// //
						// OAuthSinaTask().execute(CampusApplication.WEIBO_SINA_CALLBACKURL);
						//
						// weibo.setupConsumerConfig(MovieApplication.WEIBO_SINA_KEY,
						// MovieApplication.WEIBO_SINA_SECRET);
						//
						// // Oauth2.0
						// // 隐式授权认证方式
						// weibo.setRedirectUrl(MovieApplication.WEIBO_SINA_CALLBACKURL);//
						// 此处回调页内容应该替换为与appkey对应的应用回调页
						// // 对应的应用回调页可在开发者登陆新浪微博开发平台之后，
						// // 进入我的应用--应用详情--应用信息--高级信息--授权设置--应用回调页进行设置和查看，
						// // 应用回调页不可为空
						//
						// weibo.authorize(WritePinLunAct.this, new
						// AuthDialogListener());
						// // new
						// // OAuthSinaTask(WritePinLunAct.this)
						// // .execute(MovieApplication.WEIBO_SINA_CALLBACKURL);
						// }
						// }).create().show();
					}
				} else {
					isCheckText = false;
				}
			}
		});
		checkText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				checkBox.setChecked(true);
				// 下面这个也可以 意思是模拟点击
				// checkBox.performClick();
			}
		});
		input = (EditText) findViewById(R.id.input);

		// input.addTextChangedListener(new TextWatcher() {
		// private CharSequence temp;
		// private int selectionStart;
		// private int selectionEnd;
		//
		// @Override
		// public void onTextChanged(CharSequence s, int start, int before,
		// int count) {
		// temp = s;
		// Log.i("-----------onTextChanged s" + s);
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence s, int start, int count,
		// int after) {
		// }
		//
		// @Override
		// public void afterTextChanged(Editable s) {
		// selectionStart = input.getSelectionStart();
		// selectionEnd = input.getSelectionEnd();
		// // System.out.println("start="+selectionStart+",end="+selectionEnd);
		// if (temp.length() > 140) {
		//
		// s.delete(selectionStart - 1, selectionEnd);
		// int tempSelection = selectionStart;
		// input.setText(s);
		// input.setSelection(tempSelection);// 设置光标在最后
		// Toast.makeText(WritePinLunAct.this, "最多140个字！",
		// Toast.LENGTH_SHORT).show();
		// }
		// }
		// });

		pointtrate.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				System.out.println("-----------pointtrate onRatingChanged+rating" + rating);
				rate = rating + "";
				point.setText(rate);
			}
		});

		fabiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				content = input.getText().toString();
				if (content.length() > 140) {
					Toast.makeText(WritePinLunAct.this, "评论内容不能超过140字", Toast.LENGTH_LONG).show();
					return;
				}
				if (content.equals("")) {
					Toast.makeText(WritePinLunAct.this, "评论内容不能为空！", Toast.LENGTH_SHORT).show();
				} else {
					if (!isMoviesLoadAll) {
						new GetMovieInfoTask(WritePinLunAct.this, "正在发表").execute();
					}
					System.out.println("check----------" + checkBox.isChecked() + "    "
							+ isSinaBinging);
					if (checkBox.isChecked()) {// 发表到新浪微博
						if (isSinaBinging) {// 已绑定
							shareSinaWeibo();
						}
					}
				}
			}
		});
		imBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
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
			if (!isSinaBinging) {
				checkBox.setChecked(false);
				isCheckText = false;
			}
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

			if (!isSinaBinging) {
				checkBox.setChecked(false);
				isCheckText = false;
			}
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(getApplicationContext(), "Auth exception : " + e.getMessage(),
					Toast.LENGTH_LONG).show();
		}

	}

	private void share2weibo() {
		try {
			String con = input.getText().toString();
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
	// if (result != null) {
	// if (sina_exception != null) {
	// Toast.makeText(WritePinLunAct.this, sina_exception, Toast.LENGTH_SHORT);
	// } else {
	// Intent intent = new Intent(WritePinLunAct.this, ShareToSinaLogin.class);
	// intent.setData(Uri.parse(result));
	// mContext.startActivity(intent);
	// }
	// } else {
	// Toast.makeText(WritePinLunAct.this, "绑定失败!", Toast.LENGTH_SHORT).show();
	// checkBox.setChecked(false);
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

	class GetMovieInfoTask extends MovieAsyncTask<String, String, MoviePinLunList> {
		public String exception;

		public GetMovieInfoTask(Activity activity, String text) {
			super(activity, null, true, true, true, text);
		}

		@Override
		protected MoviePinLunList doInBackground(String... params) {

			try {
				System.out.println("mid   " + m_id + "   " + content + "     " + uid + "    "
						+ token + "    " + rate + "    " + upcomming);
				moviePinLunList = lib.addComment(m_id, content, uid, token, rate, upcomming);
				// CommentHistoryDao commentHistoryDao = new
				// CommentHistoryDao(WritePinLunAct.this);
				// System.out.println("rate     " + rate);
				// MoviePinLun moviePinLun = new MoviePinLun(m_id, name,
				// imageUrl, content, rate);
				// commentHistoryDao.saveMovie(moviePinLun);
			} catch (HttpException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (IOException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (JSONException e) {
				exception = getResources().getString(R.string.exception_json);
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return moviePinLunList;
		}

		@Override
		protected void onPostExecute(MoviePinLunList result) {
			super.onPostExecute(result);
			if (result != null && result.isSuccess()) {
				if (result.result.code.equals("1")) {
					// showDialog(WritePinLunAct.this, R.string.commentOK);
					Toast.makeText(WritePinLunAct.this, "发表成功", Toast.LENGTH_SHORT).show();
					isMoviesLoadAll = true;
					WritePinLunAct.this.finish();
				} else {
					Toast.makeText(WritePinLunAct.this, "发表失败", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(WritePinLunAct.this, "发表失败", Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	/**
	 * 设置绑定状态
	 */
	private void setSinaShareStatus() {

		if (spUtil.getSinaToken().equals("0")
				|| (System.currentTimeMillis()
						- Long.parseLong(spUtil.getString("expires_in_old", "0")) > (Long
						.parseLong(expires_in_time) * 1000)) || spUtil.getSinaToken().equals("")) {
			isSinaBinging = false;
		} else {
			isSinaBinging = true;
			// }
		}

		// Weibo weibo = Weibo.getInstance();
		// if(weibo.getAccessToken()==null){
		// isSinaBinging = false;
		// }else{
		// if (spUtil.getString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN,
		// "0").equals("0")||(TextUtils.isEmpty((String)
		// (weibo.getAccessToken().getToken())))) {
		// isSinaBinging = false;
		// } else {
		// isSinaBinging = true;
		// }
		// }
	}

	private void showDialog(Context context, int msg) {
		Dialog dialog = new AlertDialog.Builder(context).setMessage(msg)
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						finish();
					}
				}).create();
		dialog.show();
	}

	@Override
	public void onComplete(String response) {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				spUtil.putString("expires_in_old", System.currentTimeMillis() + "");
				// Toast.makeText(WritePinLunAct.this, "分享成功",
				// Toast.LENGTH_SHORT).show();
				Toast.makeText(WritePinLunAct.this, R.string.send_sucess, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	@Override
	public void onIOException(IOException e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(final WeiboException e) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// Toast.makeText(WritePinLunAct.this, "分享失败",
				// Toast.LENGTH_SHORT).show();
				Toast.makeText(
						WritePinLunAct.this,
						String.format(WritePinLunAct.this.getString(R.string.send_failed) + ":%s",
								e.getMessage()), Toast.LENGTH_LONG).show();
			}
		});

	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				spUtil.putString("expires_in_old", System.currentTimeMillis() + "");
				System.out.println("分享成功--fenxiangOK");
				Toast.makeText(WritePinLunAct.this, "分享成功", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(WritePinLunAct.this, "分享失败", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				try {
					JSONObject object = new JSONObject(msg.obj.toString());
					if (object.has("errcode")) {
						String errcode = object.getString("errcode");
						if ("0".equals(errcode)) {
							spUtil.putString("expires_in_old", System.currentTimeMillis() + "");
							System.out.println("分享成功-errcode-fenxiangOK");
							Toast.makeText(WritePinLunAct.this, "分享成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(WritePinLunAct.this, object.getString("msg"),
									Toast.LENGTH_SHORT).show();
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(WritePinLunAct.this, "分享失败", Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
			}
			// WritePinLunAct.this.finish();
		}
	};

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		System.out.println("onRestart-------------------------");
		setSinaShareStatus();
		if (!isSinaBinging) {
			checkBox.setChecked(false);
			isCheckText = false;
		}
	}

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
