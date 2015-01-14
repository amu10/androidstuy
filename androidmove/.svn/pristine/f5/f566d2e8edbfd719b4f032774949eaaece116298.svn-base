package com.szcmcc.movie.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.adapter.ZSQGalleryAdapter;
import com.szcmcc.movie.bean.Movie;
import com.szcmcc.movie.bean.MovieInfo;
import com.szcmcc.movie.bean.UpDateBean;
import com.szcmcc.movie.cache.AppConstants;
import com.szcmcc.movie.fragment.BoxOfficeFragment;
import com.szcmcc.movie.fragment.NewsFragment;
import com.szcmcc.movie.fragment.PlaysFragment;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.util.PublicUtils;
import com.szcmcc.movie.view.MessageDialog;
import com.szcmcc.movie.view.TabPageIndicator;
import com.szcmcc.movie.view.ZSQGalleryView;

public class ZSQMovieActivity extends BaseFragmentActivity implements OnClickListener, OnItemClickListener, OnItemSelectedListener {
	protected MovieLib lib;
	private RatingBar rbar;
	private ImageButton imHot;
	private ImageButton imWill;
	private ImageButton imInfo;
	private LinearLayout lvMovieInfo;
	private LinearLayout lvMoviePic;
	private LinearLayout lightLinear;// 灯光
	private TextView tvMovieName;// 影片名称
	private TextView tvRate;// 影片得分
	private TextView tvReleaseDate;
	private ZSQGalleryView gallery1;// 正在热映
	private ZSQGalleryView gallery2;// 即将上映
	private ZSQGalleryAdapter adapter1;// 正在热映
	private ZSQGalleryAdapter adapter2;// 即将上映
	private static final int[] ICONS = new int[] { R.drawable.zsq_tab_group_line, R.drawable.zsq_tab_group_line, R.drawable.zsq_tab_group_line,
			R.drawable.zsq_tab_group_line, R.drawable.zsq_tab_group_line, R.drawable.zsq_tab_group_line, };
	private MovieInfo movieInfo;// 影片信息
	private ArrayList<Movie> hotMovieList = null;// 正在热映影片
	private ArrayList<Movie> willMovieList = null;// 即将上映影片
	public static MovieInfo willMovieInfo = null;
	private String downloadUrl = "";
	private GoogleMusicAdapter pagerAdapter;
	private ViewPager pager;
	private TabPageIndicator indicator;
	public final int CHINA_FLAG = 0;
	public final int USA_FLAG = 1;
	public static int currentFlag = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zsq_act_movie);
		// 接收影片信息
		movieInfo = (MovieInfo) getIntent().getSerializableExtra("movieInfo");
//		downloadUrl = getIntent().getStringExtra("downloadUrl");
		// 初始化
		init();
		new GetUpdateTask(this).execute();
		// 如果不为空就代表有升级地址可升级
//		if (!downloadUrl.equals("")) {
//			try {
//				showDialogUp(downloadUrl);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (movieInfo != null) {// 影片信息不为空
			if (hotMovieList != null && willMovieList != null) {// 热门影片和即将上映不为空
			} else {
				hotMovieList = new ArrayList<Movie>();
				willMovieList = new ArrayList<Movie>();
				for (int i = 0; i < movieInfo.movies.size(); i++) {// 分装进不同的list
					if(movieInfo.movies.get(i).upcomming == null) {
						continue;
					}
					movieInfo.movies.get(i).setGrayed(false);// 默认不置灰
					if (movieInfo.movies.get(i).upcomming.equals("0")) {
						hotMovieList.add(movieInfo.movies.get(i));
					} else if (movieInfo.movies.get(i).upcomming.equals("1")) {
						willMovieList.add(movieInfo.movies.get(i));
					}
				}

				adapter1 = new ZSQGalleryAdapter(ZSQMovieActivity.this);
				gallery1.setAdapter(adapter1);
				
				adapter2 = new ZSQGalleryAdapter(ZSQMovieActivity.this);
				gallery2.setAdapter(adapter2);
				
				gallery1.setUnselectedAlpha(0.5f);
				gallery2.setUnselectedAlpha(0.5f);
				
				gallery1.setFadingEdgeLength(0);
				gallery1.setSpacing(40); // 图片之间的间距
				gallery2.setFadingEdgeLength(0);
				gallery2.setSpacing(40); // 图片之间的间距
				
				if(hotMovieList.size() == 0) {
					MessageDialog.getInstance().setData(ZSQMovieActivity.this, R.string.data_failed_to_load, true);
				} else {
					adapter1.setData(hotMovieList);
					
					adapter2.setData(willMovieList);
					
					reSetNameRate(0, MovieApplication.HOT_MOVIE);
				}
			}
		}
		if (pagerAdapter == null) {
			pagerAdapter = new GoogleMusicAdapter(getSupportFragmentManager());
			pager.setAdapter(pagerAdapter);
			indicator.setViewPager(pager);
		}
	}

	class GoogleMusicAdapter extends FragmentStatePagerAdapter implements com.szcmcc.movie.adapter.IconPagerAdapter {

		public GoogleMusicAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 1) {// 头条新闻
				return NewsFragment.newInstance(MovieApplication.ENTRY_NEWS);
			} else if (position == 2) {// 电影资讯
				return NewsFragment.newInstance(MovieApplication.ENTRY_MOVIE_INFO);
			} else if (position == 3) {// 精彩影评
				return NewsFragment.newInstance(MovieApplication.ENTRY_FILM_REVIEW);
			} else if (position == 4) {// 经典人物
				return PlaysFragment.newInstance();
			} else if (position == 5) {// 经典台词
				return NewsFragment.newInstance(MovieApplication.ENTRY_CLSAAIC_LINES);
			} else {// 票房排行
				return BoxOfficeFragment.newInstance();
			}
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return MovieApplication.YXSortArray[position % MovieApplication.YXSortArray.length].toUpperCase();
		}

		@Override
		public int getCount() {
			return MovieApplication.YXSortArray.length;
		}

		@Override
		public int getIconResId(int index) {
			return ICONS[index];
		}
	}

	private void init() {
		rbar = (RatingBar) findViewById(R.id.rbar);
		tvMovieName = (TextView) findViewById(R.id.tvMovieName);
		tvRate = (TextView) findViewById(R.id.tvRate);
		tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
		imHot = (ImageButton) findViewById(R.id.imHot);
		imWill = (ImageButton) findViewById(R.id.imWill);
		imInfo = (ImageButton) findViewById(R.id.imInfo);
		gallery1 = (ZSQGalleryView) findViewById(R.id.gallery1);
		gallery2 = (ZSQGalleryView) findViewById(R.id.gallery2);
		lvMovieInfo = (LinearLayout) findViewById(R.id.lvMovieInfo);
		lvMoviePic = (LinearLayout) findViewById(R.id.lvMoivePic);
		pager = (ViewPager) findViewById(R.id.pager);
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		lightLinear = (LinearLayout) findViewById(R.id.lightLinear);

		imHot.setOnClickListener(this);
		imWill.setOnClickListener(this);
		imInfo.setOnClickListener(this);
		gallery1.setOnItemClickListener(this);
		gallery2.setOnItemClickListener(this);
		gallery1.setOnItemSelectedListener(this);
		gallery2.setOnItemSelectedListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.imHot:// 正在热映
			reSetBlueBg();
			imHot.setImageResource(R.drawable.zsq_blue_hot_b);
			lvMoviePic.setVisibility(View.VISIBLE);
			lvMovieInfo.setVisibility(View.GONE);
			gallery1.setVisibility(View.VISIBLE);
			gallery2.setVisibility(View.GONE);
			lightLinear.setVisibility(View.VISIBLE);
			reSetNameRate(gallery1.getSelectedItemPosition(), MovieApplication.HOT_MOVIE);
			break;
		case R.id.imWill:// 即将上映
			reSetBlueBg();
			imWill.setImageResource(R.drawable.zsq_blue_will_b);
			lvMoviePic.setVisibility(View.VISIBLE);
			lvMovieInfo.setVisibility(View.GONE);
			gallery1.setVisibility(View.GONE);
			gallery2.setVisibility(View.VISIBLE);
			lightLinear.setVisibility(View.VISIBLE);
			tvMovieName.setText(null);
			rbar.setVisibility(View.GONE);
			tvRate.setVisibility(View.GONE);
			tvReleaseDate.setVisibility(View.VISIBLE);
			tvReleaseDate.setText(null);
			if (willMovieList == null || willMovieList.size() == 0) {
				new GetMovieInfoTask(this).execute();
			} else {
				if (gallery2.getSelectedItemPosition() == -1) {
					reSetNameRate(0, MovieApplication.WILL_MOVIE);
				} else {
					reSetNameRate(gallery2.getSelectedItemPosition(), MovieApplication.WILL_MOVIE);
				}
			}
			break;
		case R.id.imInfo:// 影讯
			reSetBlueBg();
			imInfo.setImageResource(R.drawable.zsq_blue_info_b);
			lvMoviePic.setVisibility(View.GONE);
			lvMovieInfo.setVisibility(View.VISIBLE);
			lightLinear.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	private void reSetBlueBg() {
		imHot.setImageResource(R.drawable.zsq_blue_hot);
		imWill.setImageResource(R.drawable.zsq_blue_will);
		imInfo.setImageResource(R.drawable.zsq_blue_info);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		switch (arg0.getId()) {
		case R.id.gallery1:
			for (int i = 0; i < hotMovieList.size(); i++) {
				if (i != arg2) {
					hotMovieList.get(i).setGrayed(false);
				} else {
					if (hotMovieList.get(i).isGrayed()) {
						hotMovieList.get(i).setGrayed(false);
					} else {
						hotMovieList.get(i).setGrayed(true);
					}
				}
			}
			adapter1.setData(hotMovieList);
			break;
		case R.id.gallery2:
			for (int i = 0; i < willMovieList.size(); i++) {
				if (i != arg2) {
					willMovieList.get(i).setGrayed(false);
				} else {
					if (willMovieList.get(i).isGrayed()) {
						willMovieList.get(i).setGrayed(false);
					} else {
						willMovieList.get(i).setGrayed(true);
					}
				}
			}
			adapter2.setData(willMovieList);
			break;
		default:
			break;
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg0.getId()) {
		case R.id.gallery1:
			reSetNameRate(arg2, MovieApplication.HOT_MOVIE);
			break;
		case R.id.gallery2:
			reSetNameRate(arg2, MovieApplication.WILL_MOVIE);
			break;
		default:
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	private void reSetNameRate(int index, String type) {
		try {
			if (MovieApplication.HOT_MOVIE.equals(type)) {
				tvMovieName.setText(hotMovieList.get(index).name);
				tvReleaseDate.setVisibility(View.GONE);
				rbar.setRating(Float.parseFloat(hotMovieList.get(index).rate));
				tvRate.setText(hotMovieList.get(index).rate);
				rbar.setVisibility(View.VISIBLE);
				tvRate.setVisibility(View.VISIBLE);
			} else if (MovieApplication.WILL_MOVIE.equals(type)) {
				tvMovieName.setText(willMovieList.get(index).name);
				rbar.setVisibility(View.GONE);
				tvRate.setVisibility(View.GONE);
				tvReleaseDate.setVisibility(View.VISIBLE);
				tvReleaseDate.setText(willMovieList.get(index).release_date);
			}
		} catch (Exception e) {
		}
	}

	private void showDialogUp(final String url) throws Exception {
		final AlertDialog.Builder builder = new AlertDialog.Builder(ZSQMovieActivity.this);
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

	

	private class GetMovieInfoTask extends MovieAsyncTask<String, String, MovieInfo> {

		public GetMovieInfoTask(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected MovieInfo doInBackground(String... params) {
			try {
				lib = MovieLib.getInstance(ZSQMovieActivity.this);
				movieInfo = lib.getAllMovie("1");
				willMovieInfo = movieInfo;
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return movieInfo;
		}

		@Override
		protected void onPostExecute(MovieInfo result) {
			super.onPostExecute(result);
			if (result != null && result.isSuccess()) {
				if(result.movies != null && result.movies.size() > 0) {
					willMovieList = result.movies;
					adapter2.setData(willMovieList);
					adapter2.setImWill();//标记为即将上映
				} else {
					MessageDialog.getInstance().setData(ZSQMovieActivity.this, result.result.message, false);
				}
			} else {
				MessageDialog.getInstance().setData(ZSQMovieActivity.this, R.string.data_failed_to_load, false);
			}
		}
	}

	UpDateBean upDateBean;
	boolean updateFlag = false;

	class GetUpdateTask extends MovieAsyncTask<String, String, UpDateBean> {
		public String exception;

		public GetUpdateTask(Activity activity) {
			super(activity, null, true, true, false);
		}

		@Override
		protected UpDateBean doInBackground(String... params) {

			try {
				if (!updateFlag) {
					if (getAppKey()[0] == null || getAppKey()[0].equals("")) {
						upDateBean = lib.update("eceqgbyha79y", "CHANNEL001", PublicUtils.getVersionName(ZSQMovieActivity.this));
					} else {
						upDateBean = lib.update(getAppKey()[0], getAppKey()[1], PublicUtils.getVersionName(ZSQMovieActivity.this));
					}
				}
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
			if (result != null) {
				updateFlag = true;
				if (result.updateReturn.equals("-2")) {// 已是最新版本

				} else if (result.updateReturn.equals("1")) {// 服务器返回有版本更新
					try {
						// 返回的版本与当前版本相同，这里是针对服务器返回错误时做处理，一般不走这里
						if (result.AppVersion.equals(PublicUtils.getVersionName(ZSQMovieActivity.this))) {

						} else {// 确实可更新
							try {
								showDialogUp(result.DownLoad);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	private String[] getAppKey() {
		ApplicationInfo info;
		String testData[] = new String[2];
		try {
			info = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
			testData[0] = info.metaData.getString("SZICITY_APPKEY");
			testData[1] = info.metaData.getString("SZICITY_CHANNEL");
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return testData;
	}
	
	private void showDailog(String msg) {

		AlertDialog.Builder builder = new Builder(this);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle("确认退出");
		builder.setMessage(msg);

		builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				ExitQuest();
				finish();

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

	
	
}
