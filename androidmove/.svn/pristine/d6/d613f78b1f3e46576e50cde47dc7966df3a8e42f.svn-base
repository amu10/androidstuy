package com.szcmcc.movie.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.LockOrDebLockMovieSeatsInfo;
import com.szcmcc.movie.bean.OrderQueryBySeat;
import com.szcmcc.movie.cache.AppConstants;
import com.szcmcc.movie.storage.SharedPreferencesUtil;

public class BuyTicketZengquanWebActivity extends BaseActivity {

	/** 拼写HTML的字符串 */
	String webHtmlString = "";

	private String timestamp = "", msg_id = "", orderNo = "", mobile = "", targetMobile = "", merchantName = "", merchandise = "", uid = "",
			token = "";
	private String c_id = "", companyId = "", expired_time = "", count = "";
	private double Amount = 0;
	private WebView webView;
	SharedPreferencesUtil shareP;

	private String cinemaRoom = "", cinemaTime = "";
	LockOrDebLockMovieSeatsInfo lockMovieSeatsInfo;
	private String showTime = "", movieName = "";

	boolean isDuihuan = false;
	private ProgressBar loading_progress = null;
	String url = "";
	private ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buyticket_web);
		findView();
		initData();
		setLinstener();
	}

	private void findView() {
		back = (ImageView) findViewById(R.id.back);
		webView = (WebView) findViewById(R.id.webView);
		shareP = SharedPreferencesUtil.getInstance(this);
		loading_progress = (ProgressBar) findViewById(R.id.loading_progress);
	}
	
	private void setLinstener() {
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (webView.canGoBack()) {  
					webView.goBack();  
				} else {
					finish();
				}
			}
		});
	}

	private void initData() {
		Intent in = getIntent();
		if (in.getExtras() != null) {
			c_id = in.getExtras().getString("c_id");
			orderNo = in.getExtras().getString("orderNo");
			Amount = (double) in.getExtras().getFloat("Amount");
			mobile = in.getExtras().getString("mobile");
			targetMobile = in.getExtras().getString("targetMobile");
			merchantName = in.getExtras().getString("merchantName");
			merchandise = in.getExtras().getString("merchandise");
			companyId = in.getExtras().getString("companyId");
			expired_time = in.getExtras().getString("expired_time");
			count = in.getExtras().getString("count");
			isDuihuan = in.getExtras().getBoolean("isDuihuan");
			lockMovieSeatsInfo = (LockOrDebLockMovieSeatsInfo) in.getSerializableExtra("lockMovieSeatsInfo");
			showTime = in.getExtras().getString("showTime");
			movieName = in.getExtras().getString("movieName");
			cinemaRoom = in.getExtras().getString("cinemaRoom");
			cinemaTime = in.getExtras().getString("cinemaTime");
			url = in.getExtras().getString("url");
		}

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(date);

		timestamp = time;
		String timeRandom = Math.round(Math.floor(Math.random() * (1 + 9999 - 1000) + 1000)) + "";
		msg_id = time + timeRandom;

		uid = shareP.getUid()[0];
		token = shareP.getUid()[1];
		webView.loadUrl(url+"?orderNo="+orderNo+"&msg_id="+msg_id+"&timestamp="+timestamp+"&reqSource=1&uid="+uid+"&token="+token);
//		System.out.println("url----+---"+url+"?orderNo="+orderNo+"&msg_id="+msg_id+"&timestamp="+timestamp+"&reqSource=1&uid="+uid+"&token="+token);
		webListener();
	}

	private void webListener() {
		webView.setFocusable(true);
		webView.requestFocus();
		webView.clearCache(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.addJavascriptInterface(new MyJavaSriptInterface(), "HTMLOUT");
		WebViewClient wc = new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
//				System.out.println("url web----  " + url);
				String a = "function submitForm(id){var form = document.getElementById(id);form.submit();}";
				// webView.stringByEvaluatingJavaScriptFromString
				String payment = "submitForm('payment')";
				// webView.
				view.loadUrl("javascript:" + a);
				view.loadUrl("javascript:" + payment);
				view.loadUrl("javascript:window.HTMLOUT.showHTML(document.getElementsByTagName('html')[0].innerHTML);");
				if (url.contains("http://wap.szicity.com/cm/powerManager/touchsimple/onepay.jsp")
						|| url.contains("http://wap.szicity.com/cm/powerManager/onepay.jsp")
						|| url.contains(BuyTicketZengquanWebActivity.this.url)//移动赠券
						|| url.contains("http://wap.szicity.com/cm/tyb_television/szwxcsMovieNewTouch/PayPresentTicketCallbackServlet")) {
					loading_progress.setVisibility(View.GONE);
					webView.clearCache(true);
					webView.clearHistory();
				}
				if (url.contains("PresentTicketPayResultServlet?result=0")) {
					if (isDuihuan) {
//						System.out.println("duihuan---------------" + targetMobile);
						Intent in = new Intent(BuyTicketZengquanWebActivity.this, TicketExchangeSuccessAct.class);
						in.putExtra("c_name", merchantName);
						in.putExtra("sevcphone", targetMobile);
						in.putExtra("expired_time", expired_time);
						in.putExtra("count", count);
						in.putExtra("SendShort", true);
						startActivity(in);
					} else {
//						System.out.println("zuowei---------------" + targetMobile);
						Intent in = new Intent(BuyTicketZengquanWebActivity.this, MovieTicketSuccessAct.class);
						in.putExtra("lockMovieSeatsInfo", lockMovieSeatsInfo);
						in.putExtra("showTime", showTime);
						in.putExtra("movieCinemaName", merchantName);
						in.putExtra("movieName", movieName);
						in.putExtra("sevcphone", targetMobile);
						in.putExtra("cinemaRoom", cinemaRoom);
						in.putExtra("cinemaTime", cinemaTime);
						startActivity(in);
					}
					finish();
				}
			}

		};
		webView.setWebViewClient(wc);
	}

	class MyJavaSriptInterface {
		/**
		 * 获得webview的html内容
		 * 
		 * @param html
		 */
		public void showHTML(String html) {
			// Log.e(TAG,"MyJavaSriptInterface showHTML : "+html);
			System.out.println("html---      a   " + html);
			if (html.contains("resp_code=00000")) {
				if (isDuihuan) {
//					System.out.println("duihuan---------------" + targetMobile);
					Intent in = new Intent(BuyTicketZengquanWebActivity.this, TicketExchangeSuccessAct.class);
					in.putExtra("c_name", merchantName);
					in.putExtra("sevcphone", targetMobile);
					in.putExtra("expired_time", expired_time);
					in.putExtra("count", count);
					in.putExtra("SendShort", true);
					startActivity(in);
				} else {
//					System.out.println("zuowei---------------" + targetMobile);
					Intent in = new Intent(BuyTicketZengquanWebActivity.this, MovieTicketSuccessAct.class);
					in.putExtra("lockMovieSeatsInfo", lockMovieSeatsInfo);
					in.putExtra("showTime", showTime);
					in.putExtra("movieCinemaName", merchantName);
					in.putExtra("movieName", movieName);
					in.putExtra("sevcphone", targetMobile);
					in.putExtra("cinemaRoom", cinemaRoom);
					in.putExtra("cinemaTime", cinemaTime);
					startActivity(in);
				}
				finish();
			} else if (html.contains("resp_code=1001")) {
				Toast.makeText(BuyTicketZengquanWebActivity.this, "外部系统 ID或密码错误", Toast.LENGTH_LONG).show();
				finish();
			} else if (html.contains("resp_code=1002")) {
				Toast.makeText(BuyTicketZengquanWebActivity.this, "接入IP非法", Toast.LENGTH_LONG).show();
				finish();
			} else if (html.contains("resp_code=2003")) {
				Toast.makeText(BuyTicketZengquanWebActivity.this, "订单已经存在", Toast.LENGTH_LONG).show();
				finish();
			} else if (html.contains("resp_code=1003")) {
				Toast.makeText(BuyTicketZengquanWebActivity.this, "请求数据格式异常", Toast.LENGTH_LONG).show();
				finish();
			} else if (html.contains("resp_code=2001")) {
				Toast.makeText(BuyTicketZengquanWebActivity.this, "该业务尚未开通", Toast.LENGTH_LONG).show();
				finish();
			} else if (html.contains("resp_code=2002")) {
				Toast.makeText(BuyTicketZengquanWebActivity.this, "订单不存在", Toast.LENGTH_LONG).show();
				finish();
			} else if (html.contains("resp_code=9001")) {
				Toast.makeText(BuyTicketZengquanWebActivity.this, "未知错误", Toast.LENGTH_LONG).show();
				finish();
			} else if (html.contains("resp_code=90019")) {
				Toast.makeText(BuyTicketZengquanWebActivity.this, "响应超时", Toast.LENGTH_LONG).show();
				finish();
			} else if (html.contains("resp_code=03001")) {
				Toast.makeText(BuyTicketZengquanWebActivity.this, "必要参数为空", Toast.LENGTH_LONG).show();
				finish();
			} else if (html.contains("resp_code=03004")) {
				Toast.makeText(BuyTicketZengquanWebActivity.this, "SPID 密码有问题", Toast.LENGTH_LONG).show();
				finish();
			} else if (html.contains("resp_code=03003")) {
				Toast.makeText(BuyTicketZengquanWebActivity.this, "不存在该商家的支付方式", Toast.LENGTH_LONG).show();
				finish();
			}

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (webView.canGoBack() && event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
			webView.goBack();  
			return true;  
		} 
		return super.onKeyDown(keyCode, event);
	}
}
