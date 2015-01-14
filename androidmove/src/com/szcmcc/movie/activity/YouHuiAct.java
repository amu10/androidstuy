package com.szcmcc.movie.activity;

import java.io.IOException;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.YouhuiBean;
import com.szcmcc.movie.bean.YouhuiDetailBean;
import com.szcmcc.movie.bean.YouhuiDetailInfoBean;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;

/**
 * 07-最新优惠详情
 * 
 * @author Administrator
 * 
 */
public class YouHuiAct extends BaseActivity {

	private Context mContext = YouHuiAct.this;

	private YouhuiDetailBean youhuiDetailBean = null;
	private ProgressBar loading_progress = null;
	private ImageButton imBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.youhuiact);
		initTitleBar();
		try{
		Intent mIntent = getIntent();
		youhuiDetailBean = (YouhuiDetailBean) mIntent.getSerializableExtra("YouhuiDetailBean");
		show(youhuiDetailBean);
		}catch(Exception e){
			e.printStackTrace();
		}
		

	}

	private TextView name = null;
	private TextView date = null;
//	private TextView address = null;
//	private TextView context = null;
//	private TextView des = null;
	private ImageView image = null;
	private WebView webView = null;

	private void show(YouhuiDetailBean bean) throws Exception{
		name = (TextView) findViewById(R.id.name);
//		date = (TextView) findViewById(R.id.date);
//		address = (TextView) findViewById(R.id.address);
//		context = (TextView) findViewById(R.id.context);
//		des = (TextView) findViewById(R.id.des);
		image = (ImageView) findViewById(R.id.image);
		imBack = (ImageButton) findViewById(R.id.imBack);
		loading_progress = (ProgressBar) findViewById(R.id.loading_progress);

		name.setText(bean.activity_name);
//		date.setText(bean.time);
//		address.setText(bean.address);
//		context.setText(bean.content);
//		des.setText(bean.desc);
		setImageUrl(image, bean.big_img);
		webView = (WebView)findViewById(R.id.webview);
		webView.setFocusable(true);
		webView.requestFocus();
		webView.clearCache(true);
		
//		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(false);
		webView.getSettings().setDefaultTextEncodingName("UTF-8"); 
//		webView.loadUrl("http://wap1.szicity.com/cm/yxpt/uploadfile/tiebreakHtml/A201301072390-content.html");
		webView.loadUrl(bean.activity_details);
		imBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void setImageUrl(final ImageView imageView, String url) {
		if (TextUtils.isEmpty(url)) {
			return;
		}
		imageView.setTag(url);
		Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(this, url, new ImageCallback() {

			@Override
			public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
				String url = (String) imageView.getTag();
				if (url.equals(bitmapUrl)) {
					imageView.setImageBitmap(bitmap);
					imageView.setBackgroundColor(0x00000000);
					loading_progress.setVisibility(View.GONE);
				}
			}
		}, AsyncImageLoader.CACHE_TYPE_SD, false);
		if (bitmap != null) {
			loading_progress.setVisibility(View.GONE);
			imageView.setBackgroundColor(0x00000000);
			imageView.setImageBitmap(bitmap);
		}
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		CmccDataStatistics.onStart(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
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
