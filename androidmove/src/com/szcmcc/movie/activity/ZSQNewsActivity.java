package com.szcmcc.movie.activity;

import java.io.IOException;

import org.apache.http.HttpException;
import org.json.JSONException;

import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.ZSQBaseBean;
import com.szcmcc.movie.bean.ZSQNewsDetailBean;
import com.szcmcc.movie.bean.ZSQRank;
import com.szcmcc.movie.bean.ZSQWonderfulCommentDetailBean;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.network.ZSQParse;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.view.MessageDialog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ZSQNewsActivity extends Activity implements OnClickListener {
	private MovieApplication app;
	protected MovieLib lib;
	private String entry;
	private String newsId;
	private String commentId;
	private ImageButton imBack;// 返回按钮
	private ImageView ivIcon;// 精彩影评上方图片
	private ZSQBaseBean<ZSQNewsDetailBean> newsDetailBean;
	private ZSQBaseBean<ZSQWonderfulCommentDetailBean> commentDetailBean;
	private TextView tvTitle;
	private TextView tvTime;
	private TextView tvInfo;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zsq_act_news);
		app = (MovieApplication) getApplicationContext();
		entry = getIntent().getStringExtra("entry");
		newsId = getIntent().getStringExtra("newsId");
		commentId = getIntent().getStringExtra("commentId");
		init();
		new GetMovieInfoTask(this, entry, newsId, commentId).execute("");

	}

	private void init() {
		imBack = (ImageButton) findViewById(R.id.imBack);
		ivIcon = (ImageView) findViewById(R.id.ivIcon);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTime = (TextView) findViewById(R.id.tvTime);
		tvInfo = (TextView) findViewById(R.id.tvInfo);

		imBack.setOnClickListener(this);

		if (MovieApplication.ENTRY_FILM_REVIEW.equals(entry)) {
			ivIcon.setVisibility(View.VISIBLE);
		} else {
			ivIcon.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imBack:
			finish();
			break;
		default:
			break;
		}
	}

	private void showStills() {
		if (commentDetailBean != null
				&& URLUtil.isHttpUrl(commentDetailBean.getComment().getImgAddr())) {
			ivIcon.setTag(commentDetailBean.getComment().getImgAddr());
			Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(this,
					commentDetailBean.getComment().getImgAddr(), new ImageCallback() {

						@Override
						public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
							String url = (String) ivIcon.getTag();
							if (url.equals(bitmapUrl)) {
								ivIcon.setBackgroundColor(0x00000000);
								ivIcon.setImageBitmap(bitmap);
							}
						}
					}, AsyncImageLoader.CACHE_TYPE_SD, false);
			if (bitmap != null) {
				ivIcon.setBackgroundColor(0x00000000);
				ivIcon.setImageBitmap(bitmap);
			}
		}
	}

	private class GetMovieInfoTask extends MovieAsyncTask<String, String, String> {
		private String mNewsId;
		private String mCommentId;
		private String mEntry;

		public GetMovieInfoTask(Activity activity, String entry, String newsId, String commentId) {
			super(activity, null, true, true, true);
			lib = MovieLib.getInstance(ZSQNewsActivity.this);
			this.mNewsId = newsId;
			this.mEntry = entry;
			this.mCommentId = commentId;
		}

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			try {
				if (mEntry.equals(MovieApplication.ENTRY_NEWS)
						|| mEntry.equals(MovieApplication.ENTRY_MOVIE_INFO)) {
					result = lib.getNewsDetail(ZSQNewsActivity.this, mNewsId);
				} else if (mEntry.equals(MovieApplication.ENTRY_FILM_REVIEW)) {
					result = lib.getWonderfulCommentDetail(ZSQNewsActivity.this, mCommentId);
				}
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result == null) {
//				Toast.makeText(ZSQNewsActivity.this, "网络连接失败，请稍后再试", Toast.LENGTH_SHORT).show();
				MessageDialog.getInstance().setData(ZSQNewsActivity.this, R.string.data_failed_to_load, true);
			} else {
				try{
				if (mEntry.equals(MovieApplication.ENTRY_NEWS)
						|| mEntry.equals(MovieApplication.ENTRY_MOVIE_INFO)) {
					newsDetailBean = new ZSQParse().parseNewsDetail(result);
					if (newsDetailBean.getCode().equals("1")) {
						tvTitle.setText(newsDetailBean.getNewsInfo().getNewsTitle());
						tvTime.setText(newsDetailBean.getNewsInfo().getNewsDate());
						tvInfo.setText(newsDetailBean.getNewsInfo().getContent());
					} else if (newsDetailBean.getCode().equals("0")) {
						Toast.makeText(ZSQNewsActivity.this, newsDetailBean.getMessage(),
								Toast.LENGTH_SHORT).show();
					} else {
//						Toast.makeText(ZSQNewsActivity.this, "网络连接失败，请稍后再试", Toast.LENGTH_SHORT)
//								.show();
						MessageDialog.getInstance().setData(ZSQNewsActivity.this, R.string.data_failed_to_load, true);
					}
				} else if (mEntry.equals(MovieApplication.ENTRY_FILM_REVIEW)) {
					commentDetailBean = new ZSQParse().parseWonderfulCommentDetail(result);
					if (commentDetailBean.getCode().equals("1")) {
						tvTitle.setText(commentDetailBean.getComment().getCommentTitle());
						tvTime.setText(commentDetailBean.getComment().getCreateTime());
						tvInfo.setText(commentDetailBean.getComment().getContent());
						showStills();
					} else if (commentDetailBean.getCode().equals("0")) {
						Toast.makeText(ZSQNewsActivity.this, commentDetailBean.getMessage(),
								Toast.LENGTH_SHORT).show();
					} else {
//						Toast.makeText(ZSQNewsActivity.this, "网络连接失败，请稍后再试", Toast.LENGTH_SHORT)
//								.show();
						MessageDialog.getInstance().setData(ZSQNewsActivity.this, R.string.data_failed_to_load, true);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
//				Toast.makeText(ZSQNewsActivity.this, "网络连接失败，请稍后再试", Toast.LENGTH_SHORT)
//				.show();
				MessageDialog.getInstance().setData(ZSQNewsActivity.this, R.string.data_failed_to_load, true);
			}
				}
		}
	}
}
