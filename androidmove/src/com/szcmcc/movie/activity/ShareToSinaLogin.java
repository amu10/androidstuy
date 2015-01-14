//package com.szcmcc.movie.activity;
//
//import oauth.signpost.exception.OAuthCommunicationException;
//import oauth.signpost.exception.OAuthExpectationFailedException;
//import oauth.signpost.exception.OAuthMessageSignerException;
//import oauth.signpost.exception.OAuthNotAuthorizedException;
//import android.app.Activity;
//import android.content.res.Configuration;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.net.http.SslError;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.webkit.CookieManager;
//import android.webkit.CookieSyncManager;
//import android.webkit.SslErrorHandler;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.cmcc.sdk.CmccDataStatistics;
//import com.szcmcc.movie.R;
//import com.szcmcc.movie.MovieApplication;
//import com.szcmcc.movie.network.MovieAsyncTask;
//import com.szcmcc.movie.storage.SharedPreferencesUtil;
//import com.szcmcc.movie.util._FakeX509TrustManager;
//import com.tencent.weibo.beans.OAuth;
//import com.tencent.weibo.utils.OAuthClient;
//
///**
// * 新浪和腾讯微博登录绑定页面
// * 
// * @author fancy
// */
//public class ShareToSinaLogin extends Activity {
//	private ImageView button_close;
//	private WebView webView;
//	private ProgressBar browser_progress;
//	private RelativeLayout progressLayout;
//	private MovieApplication app;
//	private SharedPreferencesUtil spUtil;
//	private boolean isSina;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		app = (MovieApplication) getApplication();
//		spUtil = SharedPreferencesUtil.getInstance(ShareToSinaLogin.this);
//		setContentView(R.layout.share_to_sina_login);
//		button_close = (ImageView) findViewById(R.id.imageview_share_to_sina_back);
//		webView = (WebView) findViewById(R.id.sina_webView);
//		browser_progress = (ProgressBar) findViewById(R.id.progressbar_share_to_sina);
//		progressLayout = (RelativeLayout) findViewById(R.id.layout_progressbar_share_to_sina);
//		setListener();
//		Uri uri = getIntent().getData();
//		String url = uri.toString();
//		TextView title = (TextView) findViewById(R.id.text_weibo_login_title);
//		Log.e("fancy", "weibologinurl=" + url);
//		if (url.contains("t.qq.com")) {
//			isSina = false;
//			title.setText(getResources().getString(R.string.binding_qq));
//			OAuthQQTask();
//		} else {
//			isSina = true;
//			title.setText(getResources().getString(R.string.binding_sina));
//			webView.loadUrl(url);
//		}
//
//	}
//
//	private void setListener() {
//		//挂掉
//		button_close.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				ShareToSinaLogin.this.finish();
//
//			}
//		});
//
//		webView.setFocusable(true);
//		webView.requestFocus();
//		webView.clearCache(true);
//		webView.getSettings().setJavaScriptEnabled(true);
//		webView.getSettings().setSupportZoom(true);
//		webView.getSettings().setBuiltInZoomControls(true);
//		CookieSyncManager.createInstance(ShareToSinaLogin.this);
//		CookieSyncManager.getInstance().startSync();
//		CookieManager.getInstance().removeSessionCookie();
//		webView.setWebViewClient(new WebViewClient() {
//			private int index = 0;
//
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				if (index == 0 && url.contains("vistafenghui")) {
//					index++;
//					new RetrieveAccessTokenTask(ShareToSinaLogin.this)
//							.execute(Uri.parse(url));
//				} else {
//					webView.loadUrl(url);
//				}
//				return true;
//			}
//
//			@Override
//			public void onPageStarted(WebView view, String url, Bitmap favicon) {
//				browser_progress.setVisibility(View.VISIBLE);
//				progressLayout.setVisibility(View.VISIBLE);
//				super.onPageStarted(view, url, favicon);
//			}
//
////			@Override
////			public void onReceivedSslError(WebView view,
////					SslErrorHandler handler, SslError error) {
////				progressLayout.setVisibility(View.INVISIBLE);
////				browser_progress.setVisibility(View.INVISIBLE);
////				handler.proceed();
////				super.onReceivedSslError(view, handler, error);
////			}
//
//			@Override
//			public void onPageFinished(WebView view, String url) {
//				progressLayout.setVisibility(View.INVISIBLE);
//				browser_progress.setVisibility(View.INVISIBLE);
//				super.onPageFinished(view, url);
//			}
//
//		});
//	}
//
//	private class RetrieveAccessTokenTask extends
//			MovieAsyncTask<Uri, String, String> {
//		private String exception = null;
//
//		public RetrieveAccessTokenTask(Activity activity) {
//			super(activity, null, true, true, true);
//
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
//
//		@Override
//		protected String doInBackground(Uri... params) {
//			String authUrl = null;
//			if (isSina) {
//				try {
//					String oauth_verifier = params[0]
//							.getQueryParameter(oauth.signpost.OAuth.OAUTH_VERIFIER);
//					app.getSinaOAuthProvider().setOAuth10a(true);
//					app.getSinaOAuthProvider().retrieveAccessToken(
//							app.getSinaOAuthConsumer(), oauth_verifier);
//					spUtil.setSinaToken(
//							MovieApplication.SP_UTIL_SHARE_SINA_TOKEN, app
//									.getSinaOAuthConsumer().getToken());
//					spUtil.setSinaToken(
//							MovieApplication.SP_UTIL_SHARE_SINA_TOKEN_SECRET,
//							app.getSinaOAuthConsumer().getTokenSecret());
//				} catch (OAuthMessageSignerException e) {
//					exception = e.getMessage();
//					e.printStackTrace();
//				} catch (OAuthExpectationFailedException e) {
//					exception = e.getMessage();
//					e.printStackTrace();
//				} catch (OAuthCommunicationException e) {
//					exception = e.getMessage();
//					e.printStackTrace();
//				} catch (OAuthNotAuthorizedException e) {
//					exception = e.getMessage();
//					e.printStackTrace();
//				}
//			} else {//下面是腾讯微博的验证过程
////				try {
////					Log.i("FANCY", "URL=" + params[0].toString());
////					// _FakeX509TrustManager.allowAllSSL();
////					// authqq = new OAuthClient();// OAuth 认证对象
////					// oauthqq = new
////					// OAuth(VistaApplication.WEIBO_QQ_CALLBACKURL); //
////					// 初始化OAuth请求令牌
////					// oauthqq.setOauth_consumer_key(VistaApplication.WEIBO_QQ_KEY);
////					// oauthqq.setOauth_consumer_secret(VistaApplication.WEIBO_QQ_SECRET);
////					// // oauth = auth.requestToken(oauth);
////					// String oauth_token =
////					// params[0].getQueryParameter("oauth_token");
////					// String oauth_verifier =
////					// params[0].getQueryParameter("oauth_verifier");
////					// oauthqq.setOauth_verifier(oauth_verifier);
////					// authqq.accessToken(oauthqq);
////					// String Oauth_token = oauthqq.getOauth_token();
////					// String token_secret = oauthqq.getOauth_token_secret();
////					// spUtil.setQQToken(Oauth_token);
////					// spUtil.setQQTokenSecret(token_secret);
////
////					_FakeX509TrustManager.allowAllSSL();
////					String oauth_token = params[0]
////							.getQueryParameter("oauth_token");
////					String oauth_verifier = params[0]
////							.getQueryParameter("oauth_verifier");
////					oauthqq.setOauth_verifier(oauth_verifier);
////					T_API tapi = new T_API();
////					authqq.accessToken(oauthqq);
////					String Oauth_token = oauthqq.getOauth_token();
////					String token_secret = oauthqq.getOauth_token_secret();
////					spUtil.setQQToken(Oauth_token);
////					spUtil.setQQToken(token_secret);
////
////				} catch (OAuthMessageSignerException e) {
////					exception = e.getMessage();
////					e.printStackTrace();
////				} catch (OAuthExpectationFailedException e) {
////					exception = e.getMessage();
////					e.printStackTrace();
////				} catch (OAuthCommunicationException e) {
////					exception = e.getMessage();
////					e.printStackTrace();
////				} catch (OAuthNotAuthorizedException e) {
////					exception = e.getMessage();
////					e.printStackTrace();
////				} catch (Exception e) {
////					e.printStackTrace();
////					exception = "绑定失败";
////				}
//			}
//			return authUrl;
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			super.onPostExecute(result);
//			if (exception != null) {
//				// new MessageDialog(ShareToSina.this, exception).show();
//				Toast.makeText(ShareToSinaLogin.this, exception,
//						Toast.LENGTH_SHORT).show();
//				ShareToSinaLogin.this.finish();
//				return;
//			}
////			if (picture != null) {
////				Intent intent = new Intent(ShareToSinaLogin.this,
////						WeiboShareDialog.class);
//////				intent.putExtra(MovieApplication.EXTRA_SHARE_PICTURE, picture);
////				if (isSina) {
////					intent.putExtra(MovieApplication.EXTRA_SHARE_PICTURE_WEIBO,
////							"sina");
////				} else {
////					intent.putExtra(MovieApplication.EXTRA_SHARE_PICTURE_WEIBO,
////							"qq");
////				}
////				ShareToSinaLogin.this.startActivity(intent);
////			}
//			ShareToSinaLogin.this.finish();
//		}
//
//	}
//
//	public static OAuthClient authqq;
//	public static OAuth oauthqq;
//
//	public void OAuthQQTask() {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					_FakeX509TrustManager.allowAllSSL();
//					authqq = new OAuthClient();// OAuth 认证对象
//					oauthqq = new OAuth(MovieApplication.WEIBO_QQ_CALLBACKURL); // 初始化OAuth请求令牌
//					oauthqq.setOauth_consumer_key(MovieApplication.WEIBO_QQ_KEY);
//					oauthqq.setOauth_consumer_secret(MovieApplication.WEIBO_QQ_SECRET);
//					// 获取request token
//					oauthqq = authqq.requestToken(oauthqq);
//
//					if (oauthqq.getStatus() == 1) {
//						return;
//					} else {
//						String oauth_token = oauthqq.getOauth_token();
//						String url = "http://open.t.qq.com/cgi-bin/authorize?oauth_token="
//								+ oauth_token;
//						webView.loadUrl(url);
//						// Uri uri = Uri.parse(url);
//						// Message msg = new Message();
//						// msg.what=5;
//						// msg.obj = uri;
//						// handler1.sendMessage(msg);
//					}
//
//				} catch (Exception e) {
//					e.printStackTrace();
//					// handler1.sendEmptyMessage(6);
//				}
//			}
//		}).start();
//	}
//
//	@Override
//	public void onConfigurationChanged(Configuration newConfig) {
//		super.onConfigurationChanged(newConfig);
//		finish();
//	}
//	public void onResume(){
//		super.onResume();
//		CmccDataStatistics.onStart(this);
//		}
//	public void onPause() {
//		super.onPause();
//		CmccDataStatistics.onStop(this);
//		}
//}
