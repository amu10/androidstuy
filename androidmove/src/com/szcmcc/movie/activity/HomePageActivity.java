package com.szcmcc.movie.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.R;
import com.szcmcc.movie.adapter.ImageAdapter;
import com.szcmcc.movie.adapter.MovieNewAdapter;
import com.szcmcc.movie.adapter.MovieNoHotAdapter;
import com.szcmcc.movie.adapter.YouhuiAdapter;
import com.szcmcc.movie.bean.Movie;
import com.szcmcc.movie.bean.MovieInfo;
import com.szcmcc.movie.bean.UpDateBean;
import com.szcmcc.movie.cache.AppConstants;
import com.szcmcc.movie.network.LoadingDialog;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.storage.BaseDBUtil;
import com.szcmcc.movie.util.AnimUtils;
import com.szcmcc.movie.util.RoateUtil;
import com.szcmcc.movie.view.GalleryFlow;

//import dalvik.system.VMRuntime;

/**
 * 
 * @author
 * 
 */
public class HomePageActivity extends BaseActivity {
	/** Called when the activity is first created. */
	private Context mContext = HomePageActivity.this;

	private RatingBar ratingBar_score = null;
	private TextView textView_score = null;
	// private TextView textView_comment = null;
	private TextView textView_moviename = null;
	private TextView textView_buyticket = null;

	private RelativeLayout layout_moviename = null;
	private LinearLayout layout_score = null;

	Activity act = null;
	// private TextView textView_moviefast = null;
	private TextView textView_nearmovie = null;
	private TextView textView_youhui = null;
	private GalleryFlow viewPager = null;
	// private HomepageAdapter adapter_main;

	private ImageAdapter adapter_main;
	private boolean isListViewMoreHidden = true;
	private boolean isListViewCheapHidden = true;

	private ImageView imageView_more = null, imageView_noHot = null;
	// private ImageView imageView_cheap = null;
	// private ImageView imageView_new = null;

	private RelativeLayout relativeListMore, relativeListNoHot;
	private ListView listView_more = null, listView_noHot = null;
	private MovieNewAdapter adapter_new = null;
	private MovieNoHotAdapter adapter_noHot = null;
	private ListView listView_cheap = null;
	// private RelativeLayout relativeListCheap;
	private YouhuiAdapter adapter_youhui = null;
	private boolean isMoviesLoadAll;
	private LoadingDialog dialog = null;
	// private ArrayList<Movie> list = new ArrayList<Movie>();
	private int currentMoviePosition = 0;
	MovieInfo movieInfo = null;
	UpDateBean upDateBean = null;
	// private static final int PAGER_MARGIN_DP = 40;

	private LinearLayout listview_menuLayout = null;
	private boolean updateFlag = false;
	protected static final String TAG = "HomePageActivity";
	private boolean mOnPagerScoll = false;// 是否在页面滑动状态

	private LinearLayout homepage_moiveLayout, homepage_nearLayout;
	private LinearLayout buy_linearlayout = null, no_buy_linearlayout = null;
	// private int mPosition;
	boolean flag = false;

	private String downloadUrl = "";
	private ArrayList<Movie> hotMovieList = null;
	private ArrayList<Movie> noHotMovieList = null;

	// private ImageView discountButton = null;
	// AnimationDrawable ad;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_homepage);
		// VMRuntime.getRuntime().setTargetHeapUtilization(TARGET_HEAP_UTILIZATION);
		// VMRuntime.getRuntime().setMinimumHeapSize(CWJ_HEAP_SIZE);
		initTitleBar();
		// SharedPreferences prefs =
		// PreferenceManager.getDefaultSharedPreferences(this);
		// boolean firstTime = prefs.getBoolean("first_time", true);
		// if (firstTime){
		//
		// Editor pEdit = prefs.edit();
		// pEdit.putBoolean("first_time", false);
		// pEdit.commit();
		// if(!updateFlag){
		// new UpDate(HomePageActivity.this,"检测升级中...").execute();
		// }
		// }
		System.out.println("onCreat-------");
		initView();
		// 暂停启动界面加的
		// intFirstData();
		initData();
		setListener();
		if (!downloadUrl.equals("")) {
			try {
				showDialogUp(downloadUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// setupOnTouchListeners(viewPager);
	}

	private void initView() {
		buy_linearlayout = (LinearLayout) findViewById(R.id.buy_linearlayout);
		no_buy_linearlayout = (LinearLayout) findViewById(R.id.no_buy_linearlayout);
		textView_moviename = (TextView) findViewById(R.id.homepage_textView_moviename);
		textView_buyticket = (TextView) findViewById(R.id.homepage_textView_buyticket);
		ratingBar_score = (RatingBar) findViewById(R.id.homepage_ratingBar_score);
		textView_score = (TextView) findViewById(R.id.homepage_textView_score);
		// textView_comment = (TextView)
		// findViewById(R.id.homepage_textView_comment);
		layout_moviename = (RelativeLayout) findViewById(R.id.homepage_layout_moviename);
		layout_score = (LinearLayout) findViewById(R.id.homepage_layout_score);

		// textView_moviefast = (TextView)
		// findViewById(R.id.homepage_textView_moviefast);
		textView_nearmovie = (TextView) findViewById(R.id.homepage_textView_nearmovie);
		textView_youhui = (TextView) findViewById(R.id.homepage_textView_youhui);
		homepage_moiveLayout = (LinearLayout) findViewById(R.id.homepage_moiveLayout);
		homepage_nearLayout = (LinearLayout) findViewById(R.id.homepage_nearLayout);
		viewPager = (GalleryFlow) findViewById(R.id.homepage_viewPager);

		// relativeListCheap = (RelativeLayout)
		// findViewById(R.id.youhui_more_relative);
		relativeListMore = (RelativeLayout) findViewById(R.id.more_movie_relative);
		relativeListNoHot = (RelativeLayout) findViewById(R.id.noHot_movie_relative);
		// listView_cheap = (ListView) findViewById(R.id.youhui_more_listview);
		// imageView_cheap = (ImageView)
		// findViewById(R.id.youhui_listview_menu);
		// imageView_new = (ImageView) findViewById(R.id.new_btn);
		// imageView_new.setVisibility(View.VISIBLE);

		listView_more = (ListView) findViewById(R.id.more_listview);
		listView_noHot = (ListView) findViewById(R.id.noHot_listview);
		imageView_more = (ImageView) findViewById(R.id.listview_menu);
		imageView_noHot = (ImageView) findViewById(R.id.imageView_noHot);
		listview_menuLayout = (LinearLayout) findViewById(R.id.listview_menuLayout);
		// discountButton = (ImageView) findViewById(R.id.discountButton);

		hotMovieList = new ArrayList<Movie>();
		noHotMovieList = new ArrayList<Movie>();
		// RoateUtil.tranlate(mContext, textView_comment);
		RoateUtil.tranlate(mContext, layout_score);
		RoateUtil.tranlateT(mContext, layout_moviename);
		// TODO 最新优惠首页按钮，目前先屏蔽
		// ad = (AnimationDrawable) discountButton.getBackground();
		// discountButton.clearAnimation();
		// t.start();

	}

	// Thread t = new Thread() {
	//
	// @Override
	// public void run() {
	// try {
	// sleep(1000);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	//
	// if (ad.isRunning()) {
	// ad.stop();
	// }
	// ad.start();
	// }
	//
	// };

	private void showDialogUp(final String url) throws Exception {
		final AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
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

	// 临时加的，为了屏蔽启动界面
	// private void intFirstData() {
	// SharedPreferences prefs =
	// PreferenceManager.getDefaultSharedPreferences(this);
	// boolean firstTime = prefs.getBoolean("first_time", true);
	// if (firstTime) {
	//
	// Editor pEdit = prefs.edit();
	// pEdit.putBoolean("first_time", false);
	// pEdit.commit();
	// if (!updateFlag) {
	// new UpDate(HomePageActivity.this, "检测升级中...").execute();
	// }
	// }
	// new GetMovieInfoTask(this).execute();
	// }

	// private void showDialogUp() {
	// final AlertDialog.Builder builder = new
	// AlertDialog.Builder(HomePageActivity.this);
	// builder.setTitle("升级提示!");
	// builder.setMessage("发现新版本 ，是否下载新版本？");
	// builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int whichButton) {
	//
	// }
	// });
	// builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int whichButton) {
	//
	// }
	// });
	// builder.create().show();
	// }

	// class UpDate extends MovieAsyncTask<String, String, UpDateBean> {
	// public String exception;
	//
	// public UpDate(Activity activity, String loadingText) {
	// super(activity, null, true, true, false, loadingText);
	// }
	//
	// @Override
	// protected UpDateBean doInBackground(String... params) {
	//
	// try {
	// upDateBean =
	// lib.update(PublicUtils.getVersionName(HomePageActivity.this));
	// } catch (HttpException e) {
	// exception = getResources().getString(R.string.exception_network);
	// e.printStackTrace();
	// } catch (IOException e) {
	// exception = getResources().getString(R.string.exception_network);
	// e.printStackTrace();
	// } catch (JSONException e) {
	// exception = getResources().getString(R.string.exception_json);
	// e.printStackTrace();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return upDateBean;
	// }
	//
	// @Override
	// protected void onPostExecute(UpDateBean result) {
	// super.onPostExecute(result);
	// if (result != null && !result.updateReturn.equals("")) {
	// if (result.updateReturn.equals("-4")) {
	// Toast.makeText(HomePageActivity.this, "当前已是最新版本！",
	// Toast.LENGTH_SHORT).show();
	// } else if (result.updateReturn.equals("1")) {
	// try {
	// showDialogUp();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// } else {
	// // Toast.makeText(HomePageActivity.this,
	// // "版本检测失败，请重新尝试！",Toast.LENGTH_LONG).show();
	// }
	// updateFlag = true;
	// } else {
	// Toast.makeText(HomePageActivity.this, "版本检测失败，请重新尝试！",
	// Toast.LENGTH_LONG).show();
	// }
	// }
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// }
	//
	// }

	class GetMovieInfoTask extends MovieAsyncTask<String, String, MovieInfo> {
		public String exception;

		public GetMovieInfoTask(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected MovieInfo doInBackground(String... params) {

			try {
				movieInfo = lib.getAllMovie("0");
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
			return movieInfo;
		}

		@Override
		protected void onPostExecute(MovieInfo result) {
			super.onPostExecute(result);

			// progressBar.setVisibility(View.GONE);
			// text.setVisibility(View.GONE);
			if (result != null && result.isSuccess()) {
				AppConstants.movieInfo = result;
				initData();
				// Intent in = new
				// Intent(HomePageActivity.this,HomePageActivity.class);
				// in.putExtra("movieInfo", result);
				// startActivity(in);
				// finish();
				// System.out.println("result.movies        " + result.movies);
				// adapter_main.addItem(result.movies);
				// adapter_new.addItem(result.movies);
				// isMoviesLoadAll = true;
				// showMovieItem(result.movies.get(0));
				// if(adapter_main.getItem(viewPager.getCurrentItem()).release_date.equals("")||adapter_main.getItem(viewPager.getCurrentItem()).release_date.equals("null")||adapter_main.getItem(viewPager.getCurrentItem()).release_date==null){
				// buy_linearlayout.setVisibility(View.GONE);
				// textView_buyticket.setVisibility(View.GONE);
				// no_buy_linearlayout.setVisibility(View.VISIBLE);
				// goneRatingBar();
				// }else{
				// buy_linearlayout.setVisibility(View.VISIBLE);
				// textView_buyticket.setVisibility(View.VISIBLE);
				// no_buy_linearlayout.setVisibility(View.GONE);
				// visRatingBar();
				// }
			} else {
				Toast.makeText(HomePageActivity.this, "网络连接异常,请检查网络连接！", Toast.LENGTH_LONG).show();
				finish();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	private void initData() {
		adapter_main = new ImageAdapter(this);
		// 设置左右滑动时候的页面间距
		// final float scale = getResources().getDisplayMetrics().density;
		// int pagerMarginPixels = (int) (PAGER_MARGIN_DP * scale + 0.5f);
		// viewPager.setPageMargin(pagerMarginPixels);
		// viewPager.setPageMarginDrawable(new ColorDrawable(Color.BLACK));
		viewPager.setFadingEdgeLength(0);
		viewPager.setSpacing(30); // 图片之间的间距
		viewPager.setAdapter(adapter_main);
		viewPager.setSelection(0, false);
		// setupOnTouchListeners(viewPager);

		adapter_new = new MovieNewAdapter(this);
		adapter_noHot = new MovieNoHotAdapter(this);
		listView_more.setAdapter(adapter_new);
		listView_noHot.setAdapter(adapter_noHot);
		Intent in = getIntent();
		if (in.getExtras() != null) {
			try {
				movieInfo = (MovieInfo) in.getExtras().getSerializable("movieInfo");
				downloadUrl = in.getExtras().getString("downloadUrl");
				// System.out.println("movieInfo    "+movieInfo);
				if (movieInfo == null) {
					movieInfo = AppConstants.movieInfo;
				}

				setting.setMovieInfo(movieInfo);
				settingImageLayout.setMovieInfo(movieInfo);
				adapter_main.addItem(movieInfo.movies);
				for (int i = 0; i < movieInfo.movies.size(); i++) {
					if (movieInfo.movies.get(i).upcomming.equals("0")) {
						hotMovieList.add(movieInfo.movies.get(i));
					} else if (movieInfo.movies.get(i).upcomming.equals("1")) {
						noHotMovieList.add(movieInfo.movies.get(i));
					}

				}

				adapter_new.addItem(hotMovieList);
				adapter_noHot.addItem(noHotMovieList);
				isMoviesLoadAll = true;
				showMovieItem(movieInfo.movies.get(0));
				// if(adapter_main.getItem(viewPager.getCurrentItem()).release_date.equals("")||adapter_main.getItem(viewPager.getCurrentItem()).release_date.equals("null")||adapter_main.getItem(viewPager.getCurrentItem()).release_date==null){
				if (adapter_main.getItem(0).upcomming.equals("1")) {
					buy_linearlayout.setVisibility(View.GONE);
					textView_buyticket.setVisibility(View.GONE);
					no_buy_linearlayout.setVisibility(View.VISIBLE);
					goneRatingBar();
				} else {
					buy_linearlayout.setVisibility(View.VISIBLE);
					textView_buyticket.setVisibility(View.VISIBLE);
					no_buy_linearlayout.setVisibility(View.GONE);
					visRatingBar();
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				// movieInfo =
				// (MovieInfo)in.getExtras().getSerializable("movieInfo");
				// System.out.println("movieInfo    "+movieInfo);
				movieInfo = AppConstants.movieInfo;

				setting.setMovieInfo(movieInfo);
				settingImageLayout.setMovieInfo(movieInfo);
				adapter_main.addItem(movieInfo.movies);
				// adapter_new.addItem(movieInfo.movies);
				for (int i = 0; i < movieInfo.movies.size(); i++) {
					if (movieInfo.movies.get(i).upcomming.equals("0")) {
						hotMovieList.add(movieInfo.movies.get(i));
					} else if (movieInfo.movies.get(i).upcomming.equals("1")) {
						noHotMovieList.add(movieInfo.movies.get(i));
					}

				}
				adapter_new.addItem(hotMovieList);
				adapter_noHot.addItem(noHotMovieList);
				isMoviesLoadAll = true;
				showMovieItem(movieInfo.movies.get(0));
				// if(adapter_main.getItem(viewPager.getCurrentItem()).release_date.equals("")||adapter_main.getItem(viewPager.getCurrentItem()).release_date.equals("null")||adapter_main.getItem(viewPager.getCurrentItem()).release_date==null){
				if (adapter_main.getItem(0).upcomming.equals("1")) {
					buy_linearlayout.setVisibility(View.GONE);
					textView_buyticket.setVisibility(View.GONE);
					no_buy_linearlayout.setVisibility(View.VISIBLE);
					goneRatingBar();
				} else {
					buy_linearlayout.setVisibility(View.VISIBLE);
					textView_buyticket.setVisibility(View.VISIBLE);
					no_buy_linearlayout.setVisibility(View.GONE);
					visRatingBar();
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// adapter_youhui = new YouhuiAdapter(this);
		// listView_cheap.setAdapter(adapter_youhui);
	}

	private void setListener() {
		textView_nearmovie.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 进到周边电影
				// MovieCinemaAct mcinemaAct = new MovieCinemaAct();
				// act = mcinemaAct.getActivity();
				// if(act!=null){
				// if(!act.isFinishing()){
				// finish();
				// }
				// }
				System.out.println("act---------" + act);

				// if(NetImageView.isWifi(mContext)){
				Intent intent = new Intent(mContext, MovieCinemaAct.class);
				intent.putExtra("movieInfo", movieInfo);
				mContext.startActivity(intent);
				// }else{
				// Intent intent = new Intent(mContext, MovieCinemaAct.class);
				// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// intent.putExtra("movieInfo", movieInfo);
				// mContext.startActivity(intent);
				// }

			}
		});
		textView_youhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(HomePageActivity.this, DiscountActivity.class);
				in.putExtra("movieInfo", movieInfo);
				startActivity(in);
			}
		});

		textView_buyticket.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 进到购票页面
				if (movieInfo != null) {
					// Intent intent = new Intent(mContext, BuyTicketAct.class);
					// intent.putExtra("m_id",
					// movieInfo.movies.get(currentMoviePosition).m_id);
					// intent.putExtra("m_name",
					// movieInfo.movies.get(currentMoviePosition).name);
					// intent.putExtra("c_id", "");
					// intent.putExtra("cover_image_url",
					// movieInfo.movies.get(currentMoviePosition).client_placard_url2);
					// mContext.startActivity(intent);
					Intent intent = new Intent(mContext, BuyTicketNewAct.class);
					intent.putExtra("m_id", movieInfo.movies.get(currentMoviePosition).m_id);
					intent.putExtra("m_name", movieInfo.movies.get(currentMoviePosition).name);
					intent.putExtra("c_id", "");
					intent.putExtra("rate", movieInfo.movies.get(currentMoviePosition).rate);
					intent.putExtra("movies", movieInfo.movies);
					System.out.println("m_id--" + movieInfo.movies.get(currentMoviePosition).m_id
							+ "---m_name---" + movieInfo.movies.get(currentMoviePosition).name
							+ "---c_id--" + "---cover_image_url----"
							+ movieInfo.movies.get(currentMoviePosition).client_placard_url2
							+ "---rate----" + movieInfo.movies.get(currentMoviePosition).rate);
					mContext.startActivity(intent);

				}
			}
		});

		viewPager.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				showMovieItem(adapter_main.getItem(position));
				currentMoviePosition = position;
				adapter_main.setCurrentPosition(currentMoviePosition);
				if (movieInfo.movies.get(position).upcomming.equals("0")) {
					if (hotMovieList.get(position).m_id.equals(movieInfo.movies.get(position).m_id)) {
						adapter_new.setSelect(position);
						adapter_noHot.setSelect(-1);
					} else {
						for (int i = 0; i < hotMovieList.size(); i++) {
							if (movieInfo.movies.get(position).m_id.equals(hotMovieList.get(i).m_id)) {
								adapter_new.setSelect(i);
								adapter_noHot.setSelect(-1);
								break;
							}
						}
					}
				} else if (movieInfo.movies.get(position).upcomming.equals("1")) {
					if (noHotMovieList.get(position - hotMovieList.size()).m_id
							.equals(movieInfo.movies.get(position).m_id)) {
						adapter_noHot.setSelect(position - hotMovieList.size());
						adapter_new.setSelect(-1);
						System.out.println("1 =    "
								+ noHotMovieList.get(position - hotMovieList.size()).m_id + "    "
								+ movieInfo.movies.get(position).m_id);
					} else {
						for (int i = 0; i < noHotMovieList.size(); i++) {
							if (movieInfo.movies.get(position).m_id.equals(noHotMovieList.get(i).m_id)) {
								adapter_noHot.setSelect(i);
								adapter_new.setSelect(-1);
								System.out.println("1 for  !=    " + noHotMovieList.get(i).m_id
										+ "    " + movieInfo.movies.get(position).m_id);
								break;
							}
						}
					}
				}

				// listView_more.setSelection(position);

				// timeoutHandler.postDelayed(finalizer, 2 * 1000);
				// if(flag == true){
				// timeoutHandler.removeCallbacks(finalizer);
				// }
				// if(adapter_main.getItem(viewPager.getCurrentItem()).release_date.equals("")||adapter_main.getItem(viewPager.getCurrentItem()).release_date.equals("null")||adapter_main.getItem(viewPager.getCurrentItem()).release_date==null){
				if (adapter_main.getItem(position).upcomming.equals("1")) {
					buy_linearlayout.setVisibility(View.GONE);
					textView_buyticket.setVisibility(View.GONE);
					no_buy_linearlayout.setVisibility(View.VISIBLE);
					goneRatingBar();
				} else {
					buy_linearlayout.setVisibility(View.VISIBLE);
					textView_buyticket.setVisibility(View.VISIBLE);
					no_buy_linearlayout.setVisibility(View.GONE);
					visRatingBar();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		viewPager.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (isListViewMoreHidden && isListViewCheapHidden) {
					goMovieDetailActivity(position);
				} else {
					if (!isListViewMoreHidden) {
						AnimUtils.setToRightGoneAnim(relativeListMore, listView_more, 0.2f);
						isListViewMoreHidden = true;

					}
					if (!isListViewCheapHidden) {
						AnimUtils.setToRightGoneAnim(relativeListNoHot, listView_noHot, 0.2f);
						isListViewCheapHidden = true;
					}
				}
				listview_menuLayout.setVisibility(View.VISIBLE);
				imageView_noHot.setVisibility(View.VISIBLE);

			}
		});

		imageView_more.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (isListViewMoreHidden) {
					listView_more.setVisibility(View.VISIBLE);
					AnimUtils.setToLeftShowAnim(relativeListMore, 0.2f);
					imageView_noHot.setVisibility(View.GONE);
					// imageView_cheap.setVisibility(View.INVISIBLE);
					// imageView_new.setVisibility(View.INVISIBLE);
					isListViewMoreHidden = false;
				} else {
					AnimUtils.setToRightGoneAnim(relativeListMore, listView_more, 0.2f);
					// listView_more.setVisibility(View.GONE);
					isListViewMoreHidden = true;
					imageView_noHot.setVisibility(View.VISIBLE);
					// imageView_cheap.setVisibility(View.VISIBLE);
					// imageView_new.setVisibility(View.VISIBLE);
				}

			}
		});

		listview_menuLayout.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (isListViewMoreHidden) {
					listView_more.setVisibility(View.VISIBLE);
					AnimUtils.setToLeftShowAnim(relativeListMore, 0.2f);
					// imageView_cheap.setVisibility(View.INVISIBLE);
					// imageView_new.setVisibility(View.INVISIBLE);
					isListViewMoreHidden = false;
					imageView_noHot.setVisibility(View.GONE);
				} else {
					AnimUtils.setToRightGoneAnim(relativeListMore, listView_more, 0.2f);
					// listView_more.setVisibility(View.GONE);
					isListViewMoreHidden = true;
					imageView_noHot.setVisibility(View.VISIBLE);
					// imageView_cheap.setVisibility(View.VISIBLE);
					// imageView_new.setVisibility(View.VISIBLE);
				}

			}
		});

		imageView_noHot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isListViewCheapHidden) {
					listView_noHot.setVisibility(View.VISIBLE);
					AnimUtils.setToLeftShowAnim(relativeListNoHot, 0.2f);
					// imageView_cheap.setVisibility(View.INVISIBLE);
					// imageView_new.setVisibility(View.INVISIBLE);
					isListViewCheapHidden = false;
					listview_menuLayout.setVisibility(View.GONE);
				} else {
					AnimUtils.setToRightGoneAnim(relativeListNoHot, listView_noHot, 0.2f);
					// listView_more.setVisibility(View.GONE);
					isListViewCheapHidden = true;
					listview_menuLayout.setVisibility(View.VISIBLE);
					// imageView_cheap.setVisibility(View.VISIBLE);
					// imageView_new.setVisibility(View.VISIBLE);
				}
			}
		});

		listView_more.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> mAdapterView, View parent, int position, long id) {
				// 点击然后将信息显示在上面
				// 点击的选项有选中效果
				// 跳到影片界绍
				// dialog = new LoadingDialog(HomePageActivity.this);
				// dialog.show();
				// adapter_main.setHandler(handler);
				// System.out.println("ddd");
				// if (isListViewMoreHidden) {
				// listView_more.setVisibility(View.VISIBLE);
				// AnimUtils.setToLeftShowAnim(relativeListMore, 0.2f);
				// isListViewMoreHidden = false;
				// imageView_noHot.setVisibility(View.GONE);
				// } else
				if (!isListViewMoreHidden) {
					AnimUtils.setToRightGoneAnim(relativeListMore, listView_more, 0.2f);
					isListViewMoreHidden = true;
					imageView_noHot.setVisibility(View.VISIBLE);
					listView_more.setVisibility(View.VISIBLE);
				}
				if (adapter_main.getItem(position).m_id.equals(hotMovieList.get(position).m_id)) {
					adapter_main.setCurrentPosition(position);
					// viewPager.setCurrentItem(position, true);
					viewPager.setSelection(position, true);

				} else {
					for (int i = 0; i < movieInfo.movies.size(); i++) {
						if (adapter_main.getItem(i).m_id.equals(hotMovieList.get(position).m_id)) {
							adapter_main.setCurrentPosition(i);
							// viewPager.setCurrentItem(position, true);
							viewPager.setSelection(i, true);
						}
					}
				}

				adapter_noHot.setSelect(-1);
			}

		});

		listView_noHot.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> mAdapterView, View parent, int position, long id) {
				// 点击然后将信息显示在上面
				// 点击的选项有选中效果
				// 跳到影片界绍
				// dialog = new LoadingDialog(HomePageActivity.this);
				// dialog.show();
				// adapter_main.setHandler(handler);
				// System.out.println("ddd");
				// if (isListViewCheapHidden) {
				// listView_noHot.setVisibility(View.VISIBLE);
				// AnimUtils.setToLeftShowAnim(relativeListNoHot, 0.2f);
				// isListViewCheapHidden = false;
				// listview_menuLayout.setVisibility(View.GONE);
				// } else
				if (!isListViewCheapHidden) {
					AnimUtils.setToRightGoneAnim(relativeListNoHot, listView_noHot, 0.2f);
					isListViewCheapHidden = true;
					listview_menuLayout.setVisibility(View.VISIBLE);
					listView_noHot.setVisibility(View.VISIBLE);
				}
				if (adapter_main.getItem(hotMovieList.size() + position).m_id.equals(noHotMovieList
						.get(position).m_id)) {
					adapter_main.setCurrentPosition(hotMovieList.size() + position);
					// viewPager.setCurrentItem(position, true);
					viewPager.setSelection(hotMovieList.size() + position, true);
				} else {
					for (int i = 0; i < movieInfo.movies.size(); i++) {
						if (adapter_main.getItem(i).m_id.equals(noHotMovieList.get(position).m_id)) {
							adapter_main.setCurrentPosition(i);
							// viewPager.setCurrentItem(position, true);
							viewPager.setSelection(i, true);
						}
					}
				}
				adapter_new.setSelect(-1);
			}

		});
		// imageView_cheap.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View v) {
		// if (isListViewCheapHidden) {
		// //动画显示
		// listView_cheap.setVisibility(View.VISIBLE);
		// AnimUtils.setToLeftShowAnim(relativeListCheap,0.9f);
		// imageView_more.setVisibility(View.GONE);
		// isListViewCheapHidden = false;
		// } else {
		// //动画隐藏
		// AnimUtils.setToRightGoneAnim(relativeListCheap,listView_cheap,0.9f);
		// // listView_cheap.setVisibility(View.GONE);
		// imageView_more.setVisibility(View.VISIBLE);
		// isListViewCheapHidden = true;
		// }
		//
		// }
		// });

		// listView_cheap.setOnItemClickListener(new OnItemClickListener() {
		//
		// public void onItemClick(AdapterView<?> mAdapterView, View parent, int
		// position, long id) {
		// // 跳到优惠页面
		// Intent intent = new Intent(mContext, YouHuiAct.class);
		// Bundle mBundle = new Bundle();
		// mBundle.putSerializable("bean", adapter_youhui.getItem(position));
		// intent.putExtra("data", mBundle);
		// mContext.startActivity(intent);
		// }
		//
		// });

		// discountButton.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent in = new Intent(HomePageActivity.this,DiscountActivity.class);
		// startActivity(in);
		// }
		// });
	}

	private void showMovieItem(Movie bean) {
		ratingBar_score.setRating(Float.parseFloat(bean.rate));
		textView_score.setText(bean.rate + "分");
		// textView_comment.setText(bean.comment_count + "人评论");
		textView_moviename.setText(bean.name);
	}

	private void goneRatingBar() {
		ratingBar_score.setVisibility(View.GONE);
		textView_score.setVisibility(View.GONE);
		// textView_comment.setText("");
		// textView_comment.setVisibility(View.GONE);
	}

	private void visRatingBar() {
		ratingBar_score.setVisibility(View.VISIBLE);
		textView_score.setVisibility(View.VISIBLE);
		// textView_comment.setVisibility(View.VISIBLE);
	}

	// class GetMovieInfoTask extends MovieAsyncTask<String, String, MovieInfo>
	// {
	// public String exception;
	//
	// public GetMovieInfoTask(Activity activity) {
	// super(activity, null, true, true, true);
	// }
	//
	// @Override
	// protected MovieInfo doInBackground(String... params) {
	//
	// try {
	// movieInfo = lib.getAllMovie();
	// adapter_main.setMovieInfo(movieInfo);// 没用到
	// //
	// System.out.println("prepare   -   "+lib.getCinema().mList.get(0).cinemas.get(0).c_id);
	// //
	// Log.e("GetMovieInfoTask","prepare   -   "+lib.getMoviesAndCouponByCinema("1").movies.get(0).daysSeat.size());
	// //
	// System.out.println("prepare   -   "+lib.getMoviesAndCouponByCinema("1").movies.get(0).daysSeat.size());
	// //
	// System.out.println("prepare   -   "+lib.getMoviesAndCouponByCinema("1").movies.get(0).comment_count);
	// } catch (HttpException e) {
	// exception = getResources().getString(R.string.exception_network);
	// e.printStackTrace();
	// } catch (IOException e) {
	// exception = getResources().getString(R.string.exception_network);
	// e.printStackTrace();
	// } catch (JSONException e) {
	// exception = getResources().getString(R.string.exception_json);
	// e.printStackTrace();
	// } catch(Exception e){
	// e.printStackTrace();
	// }
	// return movieInfo;
	// }
	//
	// @Override
	// protected void onPostExecute(MovieInfo result) {
	// super.onPostExecute(result);
	// if (result != null && result.isSuccess()) {
	// System.out.println("result.movies        " + result.movies);
	// adapter_main.addItem(result.movies);
	// adapter_new.addItem(result.movies);
	// isMoviesLoadAll = true;
	// showMovieItem(result.movies.get(0));
	// if(adapter_main.getItem(viewPager.getCurrentItem()).release_date.equals("")||adapter_main.getItem(viewPager.getCurrentItem()).release_date.equals("null")||adapter_main.getItem(viewPager.getCurrentItem()).release_date==null){
	// buy_linearlayout.setVisibility(View.GONE);
	// textView_buyticket.setVisibility(View.GONE);
	// no_buy_linearlayout.setVisibility(View.VISIBLE);
	// goneRatingBar();
	// }else{
	// buy_linearlayout.setVisibility(View.VISIBLE);
	// textView_buyticket.setVisibility(View.VISIBLE);
	// no_buy_linearlayout.setVisibility(View.GONE);
	// visRatingBar();
	// }
	// }else{
	// Toast.makeText(HomePageActivity.this, "网络连接异常！",
	// Toast.LENGTH_SHORT).show();
	// }
	// }
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// }
	//
	// }

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

				// 判断SDK版本
				int sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
				// 如果是2.2返回sdkVersion =8

				// 加判断
				ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
				if (sdkVersion < 8) {
					manager.restartPackage(getPackageName());
					System.exit(0);
				} else {
					Intent startMain = new Intent(Intent.ACTION_MAIN);
					startMain.addCategory(Intent.CATEGORY_HOME);
					startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(startMain);
					System.exit(0);

				}

			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// if(NetImageView.isWifi(mContext)){
			showDailog("您确定要退出么？");
			// }else{
			// finish();
			// }
		}
		return false;
	}

	private void goMovieDetailActivity(int position) {
		if (movieInfo != null) {

			Movie movie = adapter_main.getItem(position);
			Intent intent = new Intent();
			intent.putExtra("cinemaPrepareInfo", "");
			intent.putExtra("movie", movie);
			intent.putExtra("currentMoviePosition", currentMoviePosition);
			intent.setClass(mContext, MovieDetailActivity.class);
			mContext.startActivity(intent);
		}

	}

	// private Handler handler = new Handler() {
	//
	// @Override
	// public void handleMessage(Message msg) {
	// switch (msg.what) {
	// case 0:
	// discountButton.setImageBitmap(BaseDBUtil.readBitMap(mContext,
	// R.drawable.back));
	// // discountButton.setImageResource(R.drawable.back);
	// sendEmptyMessageDelayed(1, 1000);
	// break;
	// case 1:
	// discountButton.setImageBitmap(BaseDBUtil.readBitMap(mContext,
	// R.drawable.back_tapped));
	// // discountButton.setImageResource(R.drawable.back_tapped);
	// sendEmptyMessageDelayed(0, 1000);
	// break;
	// }
	// }
	//
	// };

	// @Override
	// public boolean dispatchTouchEvent(MotionEvent m) {
	// if (mPaused)
	// return true;
	// // delayHideControls();
	// return super.dispatchTouchEvent(m);
	// }

	// private ImageViewTouch getCurrentImageView() {
	// try{
	// return (ImageViewTouch)
	// adapter_main.views.get((viewPager.getCurrentItem()));
	// }catch(NullPointerException e){
	// return null;
	// }
	// }

	// private ScaleGestureDetector mScaleGestureDetector;// 放大缩小手势检测器
	// private GestureDetector mGestureDetector;// 手势检测器
	// private boolean mOnScale = false;// 是否处于缩放状态
	// private boolean mPaused;
	//
	// private void setupOnTouchListeners(View rootView) {
	// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
	// mScaleGestureDetector = new ScaleGestureDetector(this, new
	// MyOnScaleGestureListener());
	// }
	// mGestureDetector = new GestureDetector(this, new MyGestureListener());
	//
	// OnTouchListener rootListener = new OnTouchListener() {
	// public boolean onTouch(View v, MotionEvent event) {
	// // NOTE: gestureDetector may handle onScroll..
	// //
	// Log.e(TAG,"onTouch ...mOnScale:"+mOnScale+", mOnPagerScoll:"+mOnPagerScoll);
	// if (!mOnScale) {
	// if (!mOnPagerScoll) {
	// // Log.e(TAG," 不在滑动 , 不在缩放");
	// mGestureDetector.onTouchEvent(event);
	// }
	// }
	// ImageViewTouch imageView = getCurrentImageView();
	// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
	// if (!mOnPagerScoll) {
	// // // Log.e(TAG," 不在滑动 ,开始缩放");
	// // mScaleGestureDetector.onTouchEvent(event);
	// }
	// }
	//
	// if (!mOnScale) {
	// // Log.e(TAG,"  不在缩放");
	// if (imageView != null) {
	// Matrix m = imageView.getImageViewMatrix();
	// if (imageView.mBitmapDisplayed.getBitmap() == null) {
	// Log.i(TAG, " imageView.mBitmapDisplayed.getBitmap() is null ");
	// return false;// 可以触发onPageChangeListener
	// // return true;//不可触发其他事件
	// }
	// RectF rect = new RectF(0, 0,
	// imageView.mBitmapDisplayed.getBitmap().getWidth(),
	// imageView.mBitmapDisplayed.getBitmap()
	// .getHeight());
	// m.mapRect(rect);
	// // Log.d(TAG, "rect.right: " + rect.right +
	// // ", rect.left: "
	// // + rect.left + ", imageView.getWidth(): "
	// // + imageView.getWidth());
	// // 图片超出屏幕范围后移动
	// if (!(rect.right > imageView.getWidth() + 0.1 && rect.left < -0.1)) {
	// try {
	// viewPager.onTouchEvent(event);
	// } catch (ArrayIndexOutOfBoundsException e) {
	// // why?
	// }
	// }
	// }
	// }
	// // return false;//放大后无法移动,会直接跳到下副图片
	// // We do not use the return value of
	// // mGestureDetector.onTouchEvent because we will not receive
	// // the "up" event if we return false for the "down" event.
	// return true;
	// }
	// };
	// rootView.setOnTouchListener(rootListener);
	// }
	//
	// private class MyOnScaleGestureListener extends
	// ScaleGestureDetector.SimpleOnScaleGestureListener {
	//
	// private static final String TAG = "MyOnScaleGestureListener";
	// float currentScale;
	// float currentMiddleX;
	// float currentMiddleY;
	//
	// @Override
	// public void onScaleEnd(ScaleGestureDetector detector) {
	//
	// final ImageViewTouch imageView = getCurrentImageView();
	//
	// // Log.d(TAG, "currentScale: " + currentScale + ", maxZoom: "
	// // + imageView.mMaxZoom);
	// if (currentScale > imageView.mMaxZoom) {
	// imageView.zoomToNoCenterWithAni(currentScale / imageView.mMaxZoom, 1,
	// currentMiddleX, currentMiddleY);
	// currentScale = imageView.mMaxZoom;
	// imageView.zoomToNoCenterValue(currentScale, currentMiddleX,
	// currentMiddleY);
	// } else if (currentScale < imageView.mMinZoom) {
	// imageView.zoomToNoCenterWithAni(currentScale, imageView.mMinZoom,
	// currentMiddleX, currentMiddleY);
	// currentScale = imageView.mMinZoom;
	// imageView.zoomToNoCenterValue(currentScale, currentMiddleX,
	// currentMiddleY);
	// } else {
	// imageView.zoomToNoCenter(currentScale, currentMiddleX, currentMiddleY);
	// }
	//
	// imageView.center(true, true);
	//
	// // NOTE: 延迟修正缩放后可能移动问题
	// imageView.postDelayed(new Runnable() {
	// @Override
	// public void run() {
	// mOnScale = false;
	// imageView.setEnabled(true);
	// }
	// }, 300);
	// // Log.d(TAG, "gesture onScaleEnd");
	// }
	//
	// @Override
	// public boolean onScaleBegin(ScaleGestureDetector detector) {
	// // Log.d(TAG, "gesture onScaleStart");
	// mOnScale = true;
	// return true;
	// }
	//
	// @Override
	// public boolean onScale(ScaleGestureDetector detector, float mx, float my)
	// {
	// // Log.d(TAG, "gesture onScale");
	// ImageViewTouch imageView = getCurrentImageView();
	// float ns = imageView.getScale() * detector.getScaleFactor();
	//
	// currentScale = ns;
	// currentMiddleX = mx;
	// currentMiddleY = my;
	// if (detector.isInProgress()) {
	// imageView.zoomToNoCenter(ns, mx, my);
	// }
	// return true;
	// }
	// }
	//
	// private class MyGestureListener extends
	// GestureDetector.SimpleOnGestureListener {
	//
	// @Override
	// public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
	// float distanceY) {
	// // Log.d(TAG,
	// // "gesture onScroll...mOnScale:"+mOnScale+" ,mPaused:"+mPaused);
	// if (mOnScale) {
	// return true;
	// }
	// if (mPaused) {
	// return false;
	// }
	// ImageViewTouch imageView = getCurrentImageView();
	// if (imageView != null) {
	// imageView.panBy(-distanceX, -distanceY);
	// imageView.center(true, true);
	//
	// // 超出边界效果去掉这个
	// imageView.center(true, true);
	// }
	// return true;
	// }
	//
	// @Override
	// public boolean onUp(MotionEvent e) {
	// // getCurrentImageView().center(true, true);
	// return super.onUp(e);
	// }
	//
	// @Override
	// public boolean onSingleTapConfirmed(MotionEvent e) {
	// System.out.println("...............");
	// if (isListViewMoreHidden) {
	// goMovieDetailActivity();
	// } else {
	// AnimUtils.setToRightGoneAnim(relativeListMore, listView_more, 0.2f);
	// isListViewMoreHidden = true;
	// }
	//
	// return true;
	// }
	//
	// @Override
	// public boolean onDoubleTap(MotionEvent e) {
	// if (mPaused) {
	// return false;
	// }
	// // ImageViewTouch imageView = getCurrentImageView();
	// // // Switch between the original scale and 3x scale.
	// // if(imageView!=null){
	// // if (imageView.mBaseZoom < 1) {
	// // if (imageView.getScale() > 2F) {
	// // imageView.zoomTo(1f);
	// // } else {
	// // imageView.zoomToPoint(3f, e.getX(), e.getY());
	// // }
	// // } else {
	// // if (imageView.getScale() > (imageView.mMinZoom + imageView.mMaxZoom) /
	// 2f) {
	// // imageView.zoomTo(imageView.mMinZoom);
	// // } else {
	// // imageView.zoomToPoint(imageView.mMaxZoom, e.getX(), e.getY());
	// // }
	// // }
	// // }
	// return true;
	// }
	// }

	// class UpDate extends MovieAsyncTask<String, String, UpDateBean> {
	// public String exception;
	//
	// public UpDate(Activity activity,String loadingText) {
	// super(activity, null, true, true, false,loadingText);
	// }
	//
	// @Override
	// protected UpDateBean doInBackground(String... params) {
	//
	// try {
	// upDateBean =
	// lib.update(PublicUtils.getVersionName(HomePageActivity.this));
	// } catch (HttpException e) {
	// exception = getResources().getString(R.string.exception_network);
	// e.printStackTrace();
	// } catch (IOException e) {
	// exception = getResources().getString(R.string.exception_network);
	// e.printStackTrace();
	// } catch (JSONException e) {
	// exception = getResources().getString(R.string.exception_json);
	// e.printStackTrace();
	// } catch(Exception e){
	// e.printStackTrace();
	// }
	// return upDateBean;
	// }
	//
	// @Override
	// protected void onPostExecute(UpDateBean result) {
	// super.onPostExecute(result);
	// if (result != null && !result.updateReturn.equals("")) {
	// System.out.println("version------"+result.updateReturn);
	// if(result.updateReturn.equals("-4")){
	// Toast.makeText(HomePageActivity.this, "当前已是最新版本！",
	// Toast.LENGTH_SHORT).show();
	// }
	// else if(result.updateReturn.equals("1")){
	// try{
	// showDialogUp();
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	// }
	// else{
	// // Toast.makeText(HomePageActivity.this,
	// "版本检测失败，请重新尝试！",Toast.LENGTH_LONG).show();
	// }
	// updateFlag = true;
	// }else{
	// Toast.makeText(HomePageActivity.this,
	// "版本检测失败，请重新尝试！",Toast.LENGTH_LONG).show();
	// }
	// }
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// }
	//
	// }
	//
	//
	// private void showDialogUp() throws Exception {
	// final AlertDialog.Builder builder = new
	// AlertDialog.Builder(HomePageActivity.this);
	// builder.setTitle("升级提示!");
	// builder.setMessage("发现新版本 ，是否下载新版本？");
	// builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int whichButton) {
	//
	// }
	// });
	// builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int whichButton) {
	//
	// }
	// });
	// builder.create().show();
	// }

	public void onResume() {
		super.onResume();
		if (listView_more != null) {
			imageView_more.setVisibility(View.VISIBLE);
			listView_more.setVisibility(View.GONE);
		}
		// handler.removeMessages(0);
		// handler.removeMessages(1);
		// handler.sendEmptyMessageDelayed(0, 1000);
		// AnimationDrawable ad = (AnimationDrawable)
		// discountButton.getBackground();
		// discountButton.clearAnimation();

		// if (ad.isRunning()) {
		// ad.stop();
		// }
		// ad.start();

		CmccDataStatistics.onStart(this);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		System.out.println("lowMemory-----------");
	}

	public void onPause() {
		super.onPause();

		// t.interrupt();
		// System.out.println("t.isAlive() ---2  "+t.isAlive());
		//
		// if (ad.isRunning()) {
		// ad.stop();
		// }
		CmccDataStatistics.onStop(this);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		// System.out.println("onRestart-------");
		// if(!ad.isRunning()){
		// ad.start();
		// }
	}

	@Override
	public void onStart() {
		super.onStart();
		// mPaused = false;

		// if (ad.isRunning()) {
		// ad.stop();
		// }
		// ad.start();
	}

	@Override
	public void onStop() {
		super.onStop();
		// mPaused = true;

	}

	@Override
	protected void onDestroy() {
		// ImageViewTouch imageView = getCurrentImageView();
		// imageView.mBitmapDisplayed.recycle();//导致异常问题.所调用了已经调用recyle的bitmap
		// imageView.clear();
		super.onDestroy();
		// if(NetImageView.isWifi(mContext)){
		AppConstants.mGeoPoint = null;
		AppConstants.movieCinema = null;
		AppConstants.mapOrListStatus = "";
		AppConstants.movieInfo = null;
		// AppConstants.movieCinemaList = null;
		app.getAsyncImageLoader().recycleBitmaps();
		// }
	}
}