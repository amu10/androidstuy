package com.szcmcc.movie.activity;

import java.io.IOException;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.R;
import com.szcmcc.movie.adapter.YouhuiAdapter;
import com.szcmcc.movie.bean.MovieInfo;
import com.szcmcc.movie.bean.YouhuiInfoBean;
import com.szcmcc.movie.cache.AppConstants;
import com.szcmcc.movie.network.MovieAsyncTask;

public class DiscountActivity extends BaseActivity {

	private ListView discountListView = null;
	private YouhuiAdapter adapter = null;
	private TextView nearmovie = null;
	private TextView moviefast = null;
	private MovieInfo movieInfo = null;
	private TextView noYouhuiText = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discount_activity);
		initTitleBar();
		findView();
		setListener();
//		new GetDiscountActivityTask(this).execute();
	}

	private void findView() {
		discountListView = (ListView) findViewById(R.id.discount_listview);
		moviefast = (TextView) findViewById(R.id.homepage_textView_moviefast);
		nearmovie = (TextView) findViewById(R.id.homepage_textView_nearmovie);
		noYouhuiText = (TextView) findViewById(R.id.noYouhuiText);
		try {
			Intent in = getIntent();
			movieInfo = (MovieInfo) in.getExtras().getSerializable("movieInfo");

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		if (movieInfo == null) {
			movieInfo = AppConstants.movieInfo;
		}
		setting.setMovieInfo(movieInfo);
		settingImageLayout.setMovieInfo(movieInfo);
		adapter = new YouhuiAdapter(this);
		discountListView.setAdapter(adapter);
	}

	private void setListener() {
		discountListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent in = new Intent(DiscountActivity.this, YouHuiAct.class);
				in.putExtra("YouhuiBean", adapter.getYouhuiBeanItem(position));
				startActivity(in);
			}
		});

		moviefast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 回到首页
				// if(NetImageView.isWifi(mContext)){
				Intent intent = new Intent(DiscountActivity.this, HomePageActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("movieInfo", movieInfo);
				intent.putExtra("downloadUrl", "");
				startActivity(intent);
			}
		});

		nearmovie.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DiscountActivity.this, MovieCinemaAct.class);
				intent.putExtra("movieInfo", movieInfo);
				startActivity(intent);
				finish();
			}
		});
	}

//	class GetDiscountActivityTask extends MovieAsyncTask<String, String, YouhuiInfoBean> {
//		public String exception;
//
//		YouhuiInfoBean youhuiInfoBean = null;
//
//		public GetDiscountActivityTask(Activity activity) {
//			super(activity, null, true, true, true);
//		}
//
//		@Override
//		protected YouhuiInfoBean doInBackground(String... params) {
//
//			try {
//				youhuiInfoBean = lib.getDiscountActivity();
//
//			} catch (HttpException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (JSONException e) {
//				e.printStackTrace();
//			} catch (NullPointerException e) {
//				e.printStackTrace();
//			} catch(Exception e){
//				e.printStackTrace();
//			}
//			return youhuiInfoBean;
//		}
//
//		@Override
//		protected void onPostExecute(YouhuiInfoBean result) {
//			super.onPostExecute(result);
//			if (result != null) {
//				if (result.code.equals("1")) {
//					adapter.addItem(result.activitylist);
//					try {
//						if (result.activitylist.size() == 0) {
//							noYouhuiText.setVisibility(View.VISIBLE);
//						}
//					} catch (NullPointerException e) {
//						e.printStackTrace();
//						noYouhuiText.setVisibility(View.VISIBLE);
//					}
//				} else {
//					noYouhuiText.setVisibility(View.VISIBLE);
////					Toast.makeText(DiscountActivity.this, result.message, Toast.LENGTH_LONG).show();
//					System.out.println("result.GetDiscountActivityTask.message  ！  -----------" + result.message);
//				}
//			}
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
//	}

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
