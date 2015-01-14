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

public class BuyTicketWebActivity extends BaseActivity {

	/** 拼写HTML的字符串 */
	String webHtmlString = "";

	/** 统一支付的商户号相关参数信息 */
	private String spID = "", spPwd = "", spCode = "", merchantCode = "";
	private String timestamp = "", msg_id = "", orderNo = "", mobile = "", targetMobile = "", merchantName = "", merchandise = "", uid = "",
			token = "";
	private String c_id = "", companyId = "", expired_time = "", count = "";
	private String Amount = "";
	private WebView webView;
	SharedPreferencesUtil shareP;

	private String cinemaRoom = "", cinemaTime = "";
	LockOrDebLockMovieSeatsInfo lockMovieSeatsInfo;
	private String showTime = "", movieName = "";

	boolean isDuihuan = false;
	private ProgressBar loading_progress = null;
	
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
//				if (webView.canGoBack()) {  
//					webView.goBack();  
//				} else {
					finish();
//				}
			}
		});
	}
	
	private void initData() {
		Intent in = getIntent();
		if (in.getExtras() != null) {
			c_id = in.getExtras().getString("c_id");
			orderNo = in.getExtras().getString("orderNo");
			Amount = in.getExtras().getString("Amount");
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
			spID = in.getExtras().getString("spID");
			spPwd = in.getExtras().getString("spPwd");
			spCode = in.getExtras().getString("spCode");
			merchantCode = in.getExtras().getString("merchantCode");
		}

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(date);

		timestamp = time;
		String timeRandom = Math.round(Math.floor(Math.random() * (1 + 9999 - 1000) + 1000)) + "";
		msg_id = time + timeRandom;

		uid = shareP.getUid()[0];
		token = shareP.getUid()[1];
		// 测试
		// init("sp0001M000106", "666666", "sp0001M000106", "888001930000011",
		// timestamp, msg_id, orderNo, Amount + "", mobile, targetMobile,
		// merchantName, merchandise, uid, token);
		// System.out.println("http://192.168.0.47:8080/movieTouch/NewPayReqCallbackServlet?orderNo="+orderNo+"&msg_id="+msg_id+"&timestamp="+timestamp+"&reqSource=1&uid="+uid+"&token="+token);
		init(spID, spPwd, spCode, merchantCode, timestamp, msg_id, orderNo, Amount, mobile, targetMobile, merchantName, merchandise, uid, token);
		System.out.println("html---" + webHtmlString);
		webView.loadData(webHtmlString, "text/html", "UTF-8");
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
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				System.out.println("url web----  " + url);
				String a = "function submitForm(id){var form = document.getElementById(id);form.submit();}";
				// webView.stringByEvaluatingJavaScriptFromString
				String payment = "submitForm('payment')";
				// webView.
				view.loadUrl("javascript:" + a);
				view.loadUrl("javascript:" + payment);
				view.loadUrl("javascript:window.HTMLOUT.showHTML(document.getElementsByTagName('html')[0].innerHTML);");
				if (url.contains("http://wap.szicity.com/cm/powerManager/touchsimple/onepay.jsp")
						|| url.contains("http://wap.szicity.com/cm/powerManager/onepay.jsp")
						|| url.contains("http://219.136.91.63:19983/cm/powerManager/sjzf.jsp")
						|| url.contains("http://wap.szicity.com/cm/powerManager/touchsimple/sjzf.jsp")
						|| url.contains("http://wap.szicity.com/cm/powerManager/touchsimple/zffail.jsp")) {
					loading_progress.setVisibility(View.GONE);
					webView.clearCache(true);
					webView.clearHistory();
				}
				if (url.contains("PresentTicketPayResultServlet?result=0")) {
					if (isDuihuan) {
						System.out.println("duihuan---------------" + targetMobile);
						Intent in = new Intent(BuyTicketWebActivity.this, TicketExchangeSuccessAct.class);
						in.putExtra("c_name", merchantName);
						in.putExtra("sevcphone", targetMobile);
						in.putExtra("expired_time", expired_time);
						in.putExtra("count", count);
						in.putExtra("SendShort", true);
						startActivity(in);
					} else {
						System.out.println("zuowei---------------" + targetMobile);
						Intent in = new Intent(BuyTicketWebActivity.this, MovieTicketSuccessAct.class);
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
					System.out.println("duihuan---------------" + targetMobile);
					Intent in = new Intent(BuyTicketWebActivity.this, TicketExchangeSuccessAct.class);
					in.putExtra("c_name", merchantName);
					in.putExtra("sevcphone", targetMobile);
					in.putExtra("expired_time", expired_time);
					in.putExtra("count", count);
					in.putExtra("SendShort", true);
					startActivity(in);
				} else {
					System.out.println("zuowei---------------" + targetMobile);
					Intent in = new Intent(BuyTicketWebActivity.this, MovieTicketSuccessAct.class);
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
//			} else if (html.contains("resp_code=1001")) {
//				Toast.makeText(BuyTicketWebActivity.this, "外部系统 ID或密码错误", Toast.LENGTH_LONG).show();
//				finish();
//			} else if (html.contains("resp_code=1002")) {
//				Toast.makeText(BuyTicketWebActivity.this, "接入IP非法", Toast.LENGTH_LONG).show();
//				finish();
//			} else if (html.contains("resp_code=2003")) {
//				Toast.makeText(BuyTicketWebActivity.this, "订单已经存在", Toast.LENGTH_LONG).show();
//				finish();
//			} else if (html.contains("resp_code=1003")) {
//				Toast.makeText(BuyTicketWebActivity.this, "请求数据格式异常", Toast.LENGTH_LONG).show();
//				finish();
//			} else if (html.contains("resp_code=2001")) {
//				Toast.makeText(BuyTicketWebActivity.this, "该业务尚未开通", Toast.LENGTH_LONG).show();
//				finish();
//			} else if (html.contains("resp_code=2002")) {
//				Toast.makeText(BuyTicketWebActivity.this, "订单不存在", Toast.LENGTH_LONG).show();
//				finish();
//			} else if (html.contains("resp_code=9001")) {
//				Toast.makeText(BuyTicketWebActivity.this, "未知错误", Toast.LENGTH_LONG).show();
//				finish();
//			} else if (html.contains("resp_code=90019")) {
//				Toast.makeText(BuyTicketWebActivity.this, "响应超时", Toast.LENGTH_LONG).show();
//				finish();
//			} else if (html.contains("resp_code=03001")) {
//				Toast.makeText(BuyTicketWebActivity.this, "必要参数为空", Toast.LENGTH_LONG).show();
//				finish();
//			} else if (html.contains("resp_code=03004")) {
//				Toast.makeText(BuyTicketWebActivity.this, "SPID 密码有问题", Toast.LENGTH_LONG).show();
//				finish();
//			} else if (html.contains("resp_code=03003")) {
//				Toast.makeText(BuyTicketWebActivity.this, "不存在该商家的支付方式", Toast.LENGTH_LONG).show();
//				finish();
//			} else if (html.contains("resp_code=M20019")) {
//				Toast.makeText(BuyTicketWebActivity.this, "手机号未开户", Toast.LENGTH_LONG).show();
//				finish();
//			} else if (html.contains("resp_code=")) {
//				Toast.makeText(BuyTicketWebActivity.this, "手机支付异常", Toast.LENGTH_LONG).show();
//				finish();
			}

		
		}
	}

	/**
	 * 
	 * @param spID
	 *            频道SP_ID
	 * @param spPwd
	 *            频道SP密码
	 * @param spCode
	 *            频道业务代码
	 * @param merchantCode
	 *            商户编号
	 * @param timestamp
	 *            提交时间戳 yyyyMMddHHmmss
	 * @param msg_id
	 *            请求平台流水号 当前时间+4位随机数
	 * @param orderNo
	 *            订单号
	 * @param amount
	 *            订单总金额
	 * @param mobile
	 *            手机号码
	 * @param targetMobile
	 *            接收号码
	 * @param merchantName
	 *            商户名称(电影院名)
	 * @param merchandise
	 *            订单名称 包括商品名称，数量等(比如什么什么影院兑换券1张之类的)
	 * @param uid
	 * @param token
	 */
	// http://219.136.91.63:19983/cm/powerManager/onepay.pay
	//http://219.136.91.63:19983/cm/powerManager/touchsimple/onepay.pay
	// http://wap.szicity.com/cm/powerManager/touchsimple/onepay.pay 正式
	private void init(String spID, String spPwd, String spCode, String merchantCode, String timestamp, String msg_id, String orderNo, String amount,
			String mobile, String targetMobile, String merchantName, String merchandise, String uid, String token) {
		webHtmlString = "<html><body><form id=\"payment\" action=\"http://wap.szicity.com/cm/powerManager/touchsimple/onepay.pay\" 	method=\"post\"><input type=\"hidden\" name=\"spID\" value=\""
				+ spID
				+ "\"><input type=\"hidden\" name=\"spPwd\" value=\""
				+ spPwd
				+ "\"><input type=\"hidden\" name=\"spCode\" value=\""
				+ spCode
				+ "\"><input type=\"hidden\" name=\"spType\" value=\"0101\"><input type=\"hidden\" name=\"merchantCode\" value=\""
				+ merchantCode
				+ "\"><input type=\"hidden\" name=\"timestamp\" value=\""
				+ timestamp
				+ "\"><input  type=\"hidden\"  name=\"msg_id\" value=\""
				+ msg_id
				+ "\"><input type=\"hidden\" name=\"orderNo\" value=\""
				+ orderNo
				+ "\"><input type=\"hidden\" name=\"amount\" value=\""
				+ amount
				+ "\"><input type=\"hidden\" name=\"mobile\" value=\""
				+ mobile
				+ "\"><input type=\"hidden\" name=\"targetMobile\" value=\""
				+ targetMobile
				+ "\"><input type=\"hidden\" name=\"merchantName\" value=\""
				+ merchantName
				+ "\"><input type=\"hidden\" name=\"merchandise\" value=\""
				+ merchandise
//				+ "\"><input type=\"hidden\" name=\"backUrl\" value=\"/http://wap.szicity.com\"><input type=\"hidden\" name=\"uid\" value=\""
				+ "\"><input type=\"hidden\" name=\"backUrl\" value=\"/refresh=false\"><input type=\"hidden\" name=\"uid\" value=\""
				+ uid
				+ "\"><input type=\"hidden\" name=\"token\" value=\""
				+ token
				+ "\"><input type=\"hidden\" name=\"orderDesc\" value=\"empty\"><input type=\"hidden\" name=\"addr\" value=\"empty\"><input type=\"hidden\" name=\"payType\" value=\"24\"><input type=\"hidden\" name=\"spPay\" value=\"\"><input type=\"hidden\" name=\"period\" value=\"10\"><input type=\"hidden\" name=\"periodUnit\" value=\"4\"><input type=\"hidden\" name=\"outMerchantCode\" value=\"empty\"><input type=\"hidden\" name=\"reqsource\" value=\"1\"></form></body></html>";
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		webView.clearCache(true);
		webView.clearHistory();
		webView.destroyDrawingCache();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (webView.canGoBack() && event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
//			webView.goBack();  
//			return true;  
//		} 
		return super.onKeyDown(keyCode, event);
	}
}
