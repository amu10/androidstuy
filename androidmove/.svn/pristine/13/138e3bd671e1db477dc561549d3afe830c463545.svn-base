package com.szcmcc.movie.activity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.adapter.MovieNewAdapter;
import com.szcmcc.movie.adapter.MovieNoHotAdapter;
import com.szcmcc.movie.bean.CinemaPrepareInfo;
import com.szcmcc.movie.bean.Comment;
import com.szcmcc.movie.bean.CommentInfo;
import com.szcmcc.movie.bean.Movie;
import com.szcmcc.movie.bean.MovieInfo;
import com.szcmcc.movie.bean.MovieTidbits;
import com.szcmcc.movie.bean.SaveMovieNew;
import com.szcmcc.movie.cache.AppConstants;
import com.szcmcc.movie.cache.MovieSaveDao;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieCommentLib;
import com.szcmcc.movie.storage.BaseDBUtil;
import com.szcmcc.movie.storage.SharedPreferencesUtil;
import com.szcmcc.movie.util.AnimUtils;
import com.szcmcc.movie.util.TimeTools;
import com.szcmcc.movie.view.MarqueeTextView;
import com.szcmcc.movie.view.NetImageView;

/**
 * 03-影片介绍
 * 
 * @author Administrator
 * 
 */
public class MovieDetailActivity extends BaseActivity {

	private ArrayList<Comment> list = new ArrayList<Comment>();
	ArrayList<Holder> holderList = new ArrayList<Holder>();
	private ListView listView;
	private ImageView imageView_more = null, imageView_noHot = null;
	private ListView listView_more = null, listView_noHot = null;
	private boolean isListViewMoreHidden = true, isListViewCheapHidden = true;
	private MovieNewAdapter adapter_new = null;
	private MovieNoHotAdapter adapter_noHot = null;
	private MovieDetailAdatper adapter = null;
	private boolean isMoviesLoadAll = false;
	private TextView rate, director, main_actors, type, release_date, country, introduce,
			detail_textView_moviename, no_detail_textView_moviename;
	MarqueeTextView name;
	private RatingBar ratingBar;
	private ArrayList<Movie> movieList = new ArrayList<Movie>();
	private String m_id = "", comentId = "0", total = "";
	private String upcomming = "";
	private String cover_image_url = "";
	private MovieCommentLib commentLib;
	int currentMoviePosition = 0;
	private MovieInfo movieInfo = null;
	private ImageButton play;
	private LinearLayout playLayout;
	private ImageView saveMovie = null, share = null;
	/** 购票按钮 */
	private TextView buy = null;
	private ImageView pinglun_top, pinglun;
	private com.szcmcc.movie.view.LinearTrapezoidLayout imageLiulan;
	private ImageView backGround = null;
	private RelativeLayout more_relativeLayout, relativeListNoHot;
	private LinearLayout headLinear = null;
	ImageView imageUrl1;
	ImageView imageUrl2;
	ImageView imageUrl3;
	ImageView imageUrl4;
	ArrayList<MovieTidbits> tidbitsUrlList;
	SharedPreferencesUtil shareP;
	// private CommentInfo commentInfo = null;
	/** 更多评论按钮 */
	private View footView = null;
	private int currentPage = 1;
	private boolean isCanMoreCommented = true;
	private TextView more_comment_text = null;
	/** 更多评论的状态，如果为1，则为更多评论，如果为2，则为无评论。这里是为了判断是否可以点击 */
	private String status = "1";
	private final String NO_COMMENT = "1";
	private final String MORE_COMMENT = "2";

	private RelativeLayout no_buy_linearlayout = null;
	private LinearLayout buy_linearlayout = null;
	private boolean isFromSave = false;
	private boolean isFromMoviePrepareAct = false;
	private LinearLayout headScrollView = null;
	private LinearLayout listview_menuLayout = null;
	private ProgressBar loading_progress = null;
	// private LinearLayout no_comment = null;

	private boolean isCanLoadLargePic = true;
	private boolean isCanForLoadSd = true;
	private ArrayList<Movie> hotMovieList = null;
	private ArrayList<Movie> noHotMovieList = null;
	public static MovieDetailActivity movieDetailAct = null;

	// private HashMap<String,Bitmap> bitmapMap = null;
	// private ArrayList<String> urlList = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		movieDetailAct = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_movie_detail);
		commentLib = MovieCommentLib.getInstance(this);
		shareP = SharedPreferencesUtil.getInstance(this);

		init();
		adapter_new = new MovieNewAdapter(this);
		adapter_noHot = new MovieNoHotAdapter(this);
		listView_more.setAdapter(adapter_new);
		listView_noHot.setAdapter(adapter_noHot);
		if (AppConstants.movieInfo != null) {
			movieInfo = AppConstants.movieInfo;
			movieList = AppConstants.movieInfo.movies;
			for (int i = 0; i < movieInfo.movies.size(); i++) {
				if (movieInfo.movies.get(i).upcomming.equals("0")) {
					hotMovieList.add(movieInfo.movies.get(i));
				} else if (movieInfo.movies.get(i).upcomming.equals("1")) {
					noHotMovieList.add(movieInfo.movies.get(i));
				}

			}

			adapter_new.addItem(hotMovieList);
			adapter_noHot.addItem(noHotMovieList);
			// adapter_new.addItem(movieList);
		}
		addList();
		if (!isMoviesLoadAll) {
			if (AppConstants.movieInfo != null) {
				// movieInfo = AppConstants.movieInfo;
				System.out.println("!=null--------------");
				new GetMovieInfoTask(MovieDetailActivity.this).execute();
				// movieList = AppConstants.movieInfo.movies;
			} else {
				System.out.println("======null--------------");
				new GetMovieInfoNullTask(MovieDetailActivity.this).execute();

			}

		}

		adapter = new MovieDetailAdatper(MovieDetailActivity.this, list);

		listView.setAdapter(adapter);

		setListener();

	}

	@Override
	public void onLowMemory() {
		System.out.println("onlowmemory===================");
		super.onLowMemory();
	}

	private void init() {

		// no_comment = (LinearLayout)findViewById(R.id.no_comment);
		detail_textView_moviename = (TextView) findViewById(R.id.detail_textView_moviename);
		no_detail_textView_moviename = (TextView) findViewById(R.id.no_detail_textView_moviename);
		buy = (TextView) findViewById(R.id.goupiao);
		listView = (ListView) findViewById(R.id.movie_detail_listView_main);
		listview_menuLayout = (LinearLayout) findViewById(R.id.listview_menuLayout);
		View headerView = getLayoutInflater().inflate(R.layout.layout_movie_detail_head, null);
		// LinearTrapezoidLayout trapezoidLayout =
		// (LinearTrapezoidLayout)headerView.findViewById(R.id.movie_detail_head_trapzoid);

		listView.addHeaderView(headerView);
		footView = LayoutInflater.from(this).inflate(R.layout.movie_detail_footview_more_comment,
				null);

		listView.addFooterView(footView);
		loading_progress = (ProgressBar) footView.findViewById(R.id.loading_progress);
		more_comment_text = (TextView) footView.findViewById(R.id.more_comment_text);
		more_comment_text.setText("加载评论中...");
		loading_progress.setVisibility(View.VISIBLE);
		// footView.setVisibility(View.GONE);
		headLinear = (LinearLayout) listView.findViewById(R.id.headLinear);
		headScrollView = (LinearLayout) listView.findViewById(R.id.headScrollviewLinear);
		pinglun = (ImageView) listView.findViewById(R.id.pinglun);
		play = (ImageButton) listView.findViewById(R.id.play);
		playLayout = (LinearLayout) listView.findViewById(R.id.playLayout);
		name = (MarqueeTextView) listView.findViewById(R.id.name);
		rate = (TextView) listView.findViewById(R.id.scoreNum);
		director = (TextView) listView.findViewById(R.id.director);
		main_actors = (TextView) listView.findViewById(R.id.main_actors);
		type = (TextView) listView.findViewById(R.id.type);
		release_date = (TextView) listView.findViewById(R.id.release_date);
		country = (TextView) listView.findViewById(R.id.country);
		introduce = (TextView) listView.findViewById(R.id.introduce);
		ratingBar = (RatingBar) listView.findViewById(R.id.scorerate);
		saveMovie = (ImageView) listView.findViewById(R.id.shoucang);
		listView_more = (ListView) findViewById(R.id.more_listview);
		listView_noHot = (ListView) findViewById(R.id.noHot_listview);
		imageView_more = (ImageView) findViewById(R.id.listview_menu);
		imageView_noHot = (ImageView) findViewById(R.id.imageView_noHot);
		pinglun_top = (ImageView) findViewById(R.id.pinglun_top);
		share = (ImageView) listView.findViewById(R.id.share);
		imageLiulan = (com.szcmcc.movie.view.LinearTrapezoidLayout) listView
				.findViewById(R.id.movie_detail_head_trapzoid);
		backGround = (ImageView) findViewById(R.id.movie_detail_imageView_pic);
		more_relativeLayout = (RelativeLayout) findViewById(R.id.more_relativeLayout);
		relativeListNoHot = (RelativeLayout) findViewById(R.id.noHot_movie_relative);
		imageUrl1 = (ImageView) listView.findViewById(R.id.imageUrl1);
		imageUrl2 = (ImageView) listView.findViewById(R.id.imageUrl2);
		imageUrl3 = (ImageView) listView.findViewById(R.id.imageUrl3);
		imageUrl4 = (ImageView) listView.findViewById(R.id.imageUrl4);
		buy_linearlayout = (LinearLayout) findViewById(R.id.buy_linearlayout);
		no_buy_linearlayout = (RelativeLayout) findViewById(R.id.no_buy_linearlayout);
		hotMovieList = new ArrayList<Movie>();
		noHotMovieList = new ArrayList<Movie>();
		imageLiulan.setWillNotDraw(false);
		// bitmapMap = new HashMap<String,Bitmap>();
		// urlList = new ArrayList<String>();
	}

	private void addList() {

		Intent beforeIntent = getIntent();
		if (beforeIntent.getExtras() != null) {
			if (beforeIntent.getSerializableExtra("cinemaPrepareInfo").equals("")
					&& !beforeIntent.getSerializableExtra("movie").equals("")) {
				Movie movie = (Movie) beforeIntent.getSerializableExtra("movie");

				// m_id = beforeIntent.getExtras().getString("m_id");
				// System.out.println("m_id      " + m_id);
				// upcomming = beforeIntent.getExtras().getString("upcomming");
				// cover_image_url =
				// beforeIntent.getExtras().getString("cover_image_url");
				// name.setText(beforeIntent.getExtras().getString("name"));
				// detail_textView_moviename.setText(beforeIntent.getExtras().getString("name"));
				// rate.setText(beforeIntent.getExtras().getString("rate"));
				// director.setText(beforeIntent.getExtras().getString("director"));
				// main_actors.setText(beforeIntent.getExtras().getString("main_actors"));
				// type.setText(beforeIntent.getExtras().getString("type"));
				// if(beforeIntent.getExtras().getString("release_date").equals("")||beforeIntent.getExtras().getString("release_date").equals("null")||beforeIntent.getExtras().getString("release_date")==null){
				// release_date.setText("即将上映");
				// }else{
				// release_date.setText(beforeIntent.getExtras().getString("release_date"));
				// }
				// country.setText(beforeIntent.getExtras().getString("country"));
				// introduce.setText(beforeIntent.getExtras().getString("introduce"));
				// ratingBar.setProgress((int)
				// (Float.parseFloat(beforeIntent.getExtras().getString("rate"))
				// * 2));
				// play.setTag(beforeIntent.getStringExtra("trailersAndroid"));//
				// 获得播放地址
				currentMoviePosition = beforeIntent.getIntExtra("currentMoviePosition", 0);
				m_id = movie.m_id;
				System.out.println("m_id      " + m_id);
				upcomming = movie.upcomming;
				// 大图client_placard_url1
				System.out.println("movieList       " + movieList.size());
				if (isCanForLoadSd)
					for (int i = 0; i < movieList.size(); i++) {
						if (!BaseDBUtil.isHasSDpath(this, movieList.get(i).client_placard_url2)) {
							isCanLoadLargePic = false;
							System.out.println("isCanLoadLargePic       " + isCanLoadLargePic);
							break;
						}
					}
				System.out.println("isCanLoadLargePic   sss    " + isCanLoadLargePic);
				// if (isCanLoadLargePic && NetImageView.isWifi(this)) {
				// isCanForLoadSd = false;
				cover_image_url = movie.client_placard_url1;
				// } else {
				// cover_image_url = movie.client_placard_url2;
				// }
				// cover_image_url = movie.client_placard_url1;

				name.setText(movie.name);
				detail_textView_moviename.setText(movie.name);
				no_detail_textView_moviename.setText(movie.name);
				rate.setText(movie.rate);
				// director.setText(movie.director);
				// if (movie.main_actor.equals("null") ||
				// movie.main_actor.equals("")) {
				// main_actors.setText("暂无信息！");
				// } else {
				// main_actors.setText(movie.main_actor);
				// }
				// type.setText(movie.type);
				if (upcomming.equals("1")) {
					release_date.setText(movie.release_date);
					// pinglun.setBackgroundResource(R.drawable.comment_btn_tapped);
					// pinglun.setEnabled(false);
					buy_linearlayout.setVisibility(View.GONE);
					no_buy_linearlayout.setVisibility(View.VISIBLE);
					no_detail_textView_moviename.setVisibility(View.VISIBLE);
				} else {
					// pinglun.setBackgroundResource(R.drawable.pinglun_click);
					// pinglun.setEnabled(true);
					release_date.setText(movie.release_date);
					buy_linearlayout.setVisibility(View.VISIBLE);
					no_buy_linearlayout.setVisibility(View.GONE);
					no_detail_textView_moviename.setVisibility(View.GONE);
				}
				// country.setText(movie.country);
				// introduce.setText(movie.introduce);
				// ratingBar.setProgress((int) (Float.parseFloat(movie.rate) *
				// 2));
				// play.setTag(movie.trailersAndroid);// 获得播放地址
				//
				// if (movie.trailersAndroid.equals("")) {
				// play.setVisibility(View.GONE);
				// playLayout.setVisibility(View.GONE);
				// } else {
				// play.setVisibility(View.VISIBLE);
				// playLayout.setVisibility(View.VISIBLE);
				// }
				MovieSaveDao movieSaveDao = new MovieSaveDao(this);
				if (movieSaveDao.isContains(m_id)) {
					saveMovie.setBackgroundResource(R.drawable.yishoucang);
				} else {
					saveMovie.setBackgroundResource(R.drawable.shoucang_selector);
				}
				// tidbitsUrlList = movie.client_tidbits_url;
				try {
					if (tidbitsUrlList.get(0).client_tidbits_url_small.equals("")) {
						imageUrl1.setVisibility(View.GONE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					if (tidbitsUrlList.get(1).client_tidbits_url_small.equals("")) {
						imageUrl2.setVisibility(View.GONE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					if (tidbitsUrlList.get(2).client_tidbits_url_small.equals("")) {
						imageUrl3.setVisibility(View.GONE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					if (tidbitsUrlList.get(3).client_tidbits_url_small.equals("")) {
						imageUrl4.setVisibility(View.GONE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				System.out.println("tidbitsUrlList          -" + tidbitsUrlList + "              -"
						+ tidbitsUrlList.size());
			}
			setImageUrl(backGround, cover_image_url);
		}
		if (beforeIntent.getExtras().getBoolean("isFromMoviePrepareAct")) {
			System.out.println("MovieCinemaPrepareInner         --"
					+ (CinemaPrepareInfo.MovieCinemaPrepareInner) beforeIntent
							.getSerializableExtra("cinemaPrepareInfo"));
			CinemaPrepareInfo.MovieCinemaPrepareInner movie = (CinemaPrepareInfo.MovieCinemaPrepareInner) beforeIntent
					.getSerializableExtra("cinemaPrepareInfo");
			m_id = movie.m_id;
			System.out.println("m_id      " + m_id);
			upcomming = "0";
			if (isCanForLoadSd)
				for (int i = 0; i < movieList.size(); i++) {
					if (!BaseDBUtil.isHasSDpath(this, movieList.get(i).client_placard_url2)) {
						isCanLoadLargePic = false;
						break;
					}
				}
			// if (isCanLoadLargePic && NetImageView.isWifi(this)) {
			// isCanForLoadSd = false;
			cover_image_url = movie.client_placard_url1;
			// } else {
			// cover_image_url = movie.client_placard_url2;
			// }
			name.setText(movie.name);
			detail_textView_moviename.setText(movie.name);
			no_detail_textView_moviename.setText(movie.name);
			rate.setText(movie.rate);
			director.setText(movie.director);
			if (movie.main_actors.equals("null") || movie.main_actors.equals("")) {
				main_actors.setText("暂无信息！");
			} else {
				main_actors.setText(movie.main_actors);
			}
			type.setText(movie.type);
			if (movie.release_date.equals("") || movie.release_date.equals("null")
					|| movie.release_date == null) {
				release_date.setText("即将上映");
				// pinglun.setBackgroundResource(R.drawable.comment_btn_tapped);
				// pinglun.setEnabled(false);
				buy_linearlayout.setVisibility(View.GONE);
				no_buy_linearlayout.setVisibility(View.VISIBLE);
				no_detail_textView_moviename.setVisibility(View.VISIBLE);
			} else {
				// pinglun.setBackgroundResource(R.drawable.pinglun_click);
				// pinglun.setEnabled(true);
				release_date.setText(movie.release_date);
				buy_linearlayout.setVisibility(View.VISIBLE);
				no_buy_linearlayout.setVisibility(View.GONE);
				no_detail_textView_moviename.setVisibility(View.GONE);
			}
			country.setText(movie.country);
			introduce.setText(movie.introduce);
			ratingBar.setProgress((int) (Float.parseFloat(movie.rate) * 2));
			play.setTag(movie.trailersAndroid);// 获得播放地址
			if (movie.trailersAndroid.equals("")) {
				play.setVisibility(View.GONE);
				playLayout.setVisibility(View.GONE);
			} else {
				play.setVisibility(View.VISIBLE);
				playLayout.setVisibility(View.VISIBLE);
			}
			isFromMoviePrepareAct = beforeIntent.getExtras().getBoolean("isFromMoviePrepareAct");
			setImageUrl(backGround, cover_image_url);
			MovieSaveDao movieSaveDao = new MovieSaveDao(this);
			if (movieSaveDao.isContains(m_id)) {
				saveMovie.setBackgroundResource(R.drawable.yishoucang);
			} else {
				saveMovie.setBackgroundResource(R.drawable.shoucang_selector);
			}
			tidbitsUrlList = movie.client_tidbits_url;
			try {
				if (tidbitsUrlList.get(0).client_tidbits_url_small.equals("")) {
					imageUrl1.setVisibility(View.GONE);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (tidbitsUrlList.get(1).client_tidbits_url_small.equals("")) {
					imageUrl2.setVisibility(View.GONE);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (tidbitsUrlList.get(2).client_tidbits_url_small.equals("")) {
					imageUrl3.setVisibility(View.GONE);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (tidbitsUrlList.get(3).client_tidbits_url_small.equals("")) {
					imageUrl4.setVisibility(View.GONE);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				MovieTidbits movieTidbits1 = tidbitsUrlList.get(0);
				setImageUrl(imageUrl1, movieTidbits1.client_tidbits_url_small);
				MovieTidbits movieTidbits2 = tidbitsUrlList.get(1);
				setImageUrl(imageUrl2, movieTidbits2.client_tidbits_url_small);
				MovieTidbits movieTidbits3 = tidbitsUrlList.get(2);
				setImageUrl(imageUrl3, movieTidbits3.client_tidbits_url_small);
				MovieTidbits movieTidbits4 = tidbitsUrlList.get(3);
				setImageUrl(imageUrl4, movieTidbits4.client_tidbits_url_small);
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}
		if (beforeIntent.getExtras().getBoolean("isFromSave")) {
			if (!beforeIntent.getExtras().getString("m_id").equals("")) {
				m_id = beforeIntent.getExtras().getString("m_id");
				upcomming = beforeIntent.getExtras().getString("upcomming");
				isFromSave = beforeIntent.getExtras().getBoolean("isFromSave");
			}
		}

	}

	private void setListener() {

		System.out.println("ddd");
		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
					int totalItemCount) {
				if (firstVisibleItem >= 1) {
					pinglun_top.setVisibility(View.VISIBLE);
					pinglun.setVisibility(View.GONE);
				} else {
					pinglun_top.setVisibility(View.GONE);
					pinglun.setVisibility(View.VISIBLE);
				}
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// if (!isListViewMoreHidden) {
				// AnimUtils.setToRightGoneAnim(more_relativeLayout,
				// listView_more, 0.2f);
				// // listView_more.setVisibility(View.GONE);
				// isListViewMoreHidden = true;
				//
				// }
				if (!isListViewMoreHidden) {
					AnimUtils.setToRightGoneAnim(more_relativeLayout, listView_more, 0.2f);
					isListViewMoreHidden = true;

				}
				if (!isListViewCheapHidden) {
					AnimUtils.setToRightGoneAnim(relativeListNoHot, listView_noHot, 0.2f);
					isListViewCheapHidden = true;
				}
				listview_menuLayout.setVisibility(View.VISIBLE);
				imageView_noHot.setVisibility(View.VISIBLE);
			}
		});
		// imageView_more.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View v) {
		//
		// if (isListViewMoreHidden) {
		// listView_more.setVisibility(View.VISIBLE);
		// AnimUtils.setToLeftShowAnim(more_relativeLayout, 0.2f);
		// isListViewMoreHidden = false;
		// imageView_noHot.setVisibility(View.GONE);
		// } else {
		// AnimUtils.setToRightGoneAnim(more_relativeLayout, listView_more,
		// 0.2f);
		// // listView_more.setVisibility(View.GONE);
		// isListViewMoreHidden = true;
		// imageView_noHot.setVisibility(View.VISIBLE);
		// }
		//
		// }
		//
		// });

		listview_menuLayout.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (isListViewMoreHidden) {
					listView_more.setVisibility(View.VISIBLE);
					AnimUtils.setToLeftShowAnim(more_relativeLayout, 0.2f);
					isListViewMoreHidden = false;
					imageView_noHot.setVisibility(View.GONE);
				} else {
					AnimUtils.setToRightGoneAnim(more_relativeLayout, listView_more, 0.2f);
					// listView_more.setVisibility(View.GONE);
					isListViewMoreHidden = true;
					imageView_noHot.setVisibility(View.VISIBLE);
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

				if (movieInfo.movies.get(position).m_id.equals(hotMovieList.get(position).m_id)) {
					// if
					// (movieInfo.movies.get(position).trailersAndroid.equals(""))
					// {
					// play.setVisibility(View.GONE);
					// playLayout.setVisibility(View.GONE);
					// } else {
					// play.setVisibility(View.VISIBLE);
					// playLayout.setVisibility(View.VISIBLE);
					// }
					try {
						// tidbitsUrlList =
						// movieInfo.movies.get(position).client_tidbits_url;
						if (tidbitsUrlList.get(0).client_tidbits_url_small.equals("")) {
							imageUrl1.setVisibility(View.GONE);
						} else {
							imageUrl1.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						if (tidbitsUrlList.get(1).client_tidbits_url_small.equals("")) {
							imageUrl2.setVisibility(View.GONE);
						} else {
							imageUrl2.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						if (tidbitsUrlList.get(2).client_tidbits_url_small.equals("")) {
							imageUrl3.setVisibility(View.GONE);
						} else {
							imageUrl3.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						if (tidbitsUrlList.get(3).client_tidbits_url_small.equals("")) {
							imageUrl4.setVisibility(View.GONE);
						} else {
							imageUrl4.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					setData(0, position);
				} else {
					for (int i = 0; i < movieInfo.movies.size(); i++) {
						if (movieInfo.movies.get(i).m_id.equals(hotMovieList.get(position).m_id)) {
							// if
							// (movieInfo.movies.get(i).trailersAndroid.equals(""))
							// {
							// play.setVisibility(View.GONE);
							// playLayout.setVisibility(View.GONE);
							// } else {
							// play.setVisibility(View.VISIBLE);
							// playLayout.setVisibility(View.VISIBLE);
							// }

							try {
								// tidbitsUrlList =
								// movieInfo.movies.get(i).client_tidbits_url;
								if (tidbitsUrlList.get(0).client_tidbits_url_small.equals("")) {
									imageUrl1.setVisibility(View.GONE);
								} else {
									imageUrl1.setVisibility(View.VISIBLE);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							try {
								if (tidbitsUrlList.get(1).client_tidbits_url_small.equals("")) {
									imageUrl2.setVisibility(View.GONE);
								} else {
									imageUrl2.setVisibility(View.VISIBLE);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							try {
								if (tidbitsUrlList.get(2).client_tidbits_url_small.equals("")) {
									imageUrl3.setVisibility(View.GONE);
								} else {
									imageUrl3.setVisibility(View.VISIBLE);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							try {
								if (tidbitsUrlList.get(3).client_tidbits_url_small.equals("")) {
									imageUrl4.setVisibility(View.GONE);
								} else {
									imageUrl4.setVisibility(View.VISIBLE);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							setData(0, i);
							break;
						}
					}

				}

			}
		});

		listView_noHot.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> mAdapterView, View parent, int position, long id) {
				if (movieInfo.movies.get(hotMovieList.size() + position).m_id.equals(noHotMovieList
						.get(position).m_id)) {
					// if (movieInfo.movies.get(hotMovieList.size() +
					// position).trailersAndroid
					// .equals("")) {
					// play.setVisibility(View.GONE);
					// playLayout.setVisibility(View.GONE);
					// } else {
					// play.setVisibility(View.VISIBLE);
					// playLayout.setVisibility(View.VISIBLE);
					// }

					try {
						// tidbitsUrlList =
						// movieInfo.movies.get(hotMovieList.size() +
						// position).client_tidbits_url;
						if (tidbitsUrlList.get(0).client_tidbits_url_small.equals("")) {
							imageUrl1.setVisibility(View.GONE);
						} else {
							imageUrl1.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						if (tidbitsUrlList.get(1).client_tidbits_url_small.equals("")) {
							imageUrl2.setVisibility(View.GONE);
						} else {
							imageUrl2.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						if (tidbitsUrlList.get(2).client_tidbits_url_small.equals("")) {
							imageUrl3.setVisibility(View.GONE);
						} else {
							imageUrl3.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						if (tidbitsUrlList.get(3).client_tidbits_url_small.equals("")) {
							imageUrl4.setVisibility(View.GONE);
						} else {
							imageUrl4.setVisibility(View.VISIBLE);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					setData(1, hotMovieList.size() + position);
				} else {
					for (int i = 0; i < movieInfo.movies.size(); i++) {
						if (movieInfo.movies.get(i).m_id.equals(noHotMovieList.get(position).m_id)) {
							// if
							// (movieInfo.movies.get(i).trailersAndroid.equals(""))
							// {
							// play.setVisibility(View.GONE);
							// playLayout.setVisibility(View.GONE);
							// } else {
							// play.setVisibility(View.VISIBLE);
							// playLayout.setVisibility(View.VISIBLE);
							// }

							// try {
							// tidbitsUrlList =
							// movieInfo.movies.get(i).client_tidbits_url;
							// if
							// (tidbitsUrlList.get(0).client_tidbits_url_small.equals(""))
							// {
							// imageUrl1.setVisibility(View.GONE);
							// } else {
							// imageUrl1.setVisibility(View.VISIBLE);
							// }
							// } catch (Exception e) {
							// e.printStackTrace();
							// }
							try {
								if (tidbitsUrlList.get(1).client_tidbits_url_small.equals("")) {
									imageUrl2.setVisibility(View.GONE);
								} else {
									imageUrl2.setVisibility(View.VISIBLE);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							try {
								if (tidbitsUrlList.get(2).client_tidbits_url_small.equals("")) {
									imageUrl3.setVisibility(View.GONE);
								} else {
									imageUrl3.setVisibility(View.VISIBLE);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							try {
								if (tidbitsUrlList.get(3).client_tidbits_url_small.equals("")) {
									imageUrl4.setVisibility(View.GONE);
								} else {
									imageUrl4.setVisibility(View.VISIBLE);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							setData(1, i);
							break;
						}
					}
				}
			}
		});

		play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 播放RTSP流媒体
				if (NetImageView.isWifi(MovieDetailActivity.this)) {
					Intent intent = new Intent();
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					System.out.println("url              ========    " + (String) play.getTag());
					intent.putExtra("url", (String) play.getTag());
					intent.setClass(MovieDetailActivity.this, VideoPlayerActivity.class);
					startActivity(intent);
				} else {
					showDailog("当前网络不是WIFI环境，您确定要播放么");
				}
			}
		});

		playLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 播放RTSP流媒体
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				System.out.println("url              ========    " + (String) play.getTag());
				intent.putExtra("url", (String) play.getTag());
				intent.setClass(MovieDetailActivity.this, VideoPlayerActivity.class);
				startActivity(intent);
			}
		});

		saveMovie.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// if (!isListViewMoreHidden) {
				// AnimUtils.setToRightGoneAnim(more_relativeLayout,
				// listView_more, 0.2f);
				// // listView_more.setVisibility(View.GONE);
				// isListViewMoreHidden = true;
				// imageView_noHot.setVisibility(View.VISIBLE);
				// }
				if (!isListViewMoreHidden) {
					AnimUtils.setToRightGoneAnim(more_relativeLayout, listView_more, 0.2f);
					isListViewMoreHidden = true;

				}
				if (!isListViewCheapHidden) {
					AnimUtils.setToRightGoneAnim(relativeListNoHot, listView_noHot, 0.2f);
					isListViewCheapHidden = true;
				}
				listview_menuLayout.setVisibility(View.VISIBLE);
				imageView_noHot.setVisibility(View.VISIBLE);

				MovieSaveDao movieSaveDao = new MovieSaveDao(MovieDetailActivity.this);
				if (!movieSaveDao.isContains(m_id)) {
					SaveMovieNew saveMovieNew = new SaveMovieNew(m_id, name.getText() + "",
							movieList.get(currentMoviePosition).client_placard_url2, upcomming,
							release_date.getText().toString());
					saveMovie.setBackgroundResource(R.drawable.yishoucang);
					movieSaveDao.saveMovie(saveMovieNew);
					Toast.makeText(MovieDetailActivity.this, R.string.saveOK, Toast.LENGTH_SHORT)
							.show();
				} else {
					try {
						movieSaveDao.deleteMovie(m_id);
						saveMovie.setBackgroundResource(R.drawable.shoucang_selector);
						// Toast.makeText(MovieDetailActivity.this,
						// R.string.hasSaved, Toast.LENGTH_SHORT).show();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isListViewMoreHidden) {
					AnimUtils.setToRightGoneAnim(more_relativeLayout, listView_more, 0.2f);
					isListViewMoreHidden = true;
				}
				if (!isListViewCheapHidden) {
					AnimUtils.setToRightGoneAnim(relativeListNoHot, listView_noHot, 0.2f);
					isListViewCheapHidden = true;
				}
				listview_menuLayout.setVisibility(View.VISIBLE);
				imageView_noHot.setVisibility(View.VISIBLE);
				openDialog();
				mAlertDialog.show();
			}
		});

		buy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent intent = new Intent(MovieDetailActivity.this,
				// BuyTicketAct.class);
				// intent.putExtra("m_id", m_id);
				// intent.putExtra("m_name", name.getText().toString());
				// intent.putExtra("c_id", "");
				// intent.putExtra("cover_image_url", cover_image_url);
				// startActivity(intent);
				if (BuyTicketNewAct.buyTicketNewAct != null) {
					BuyTicketNewAct.buyTicketNewAct.finish();
				}

				Intent intent = new Intent(MovieDetailActivity.this, BuyTicketNewAct.class);
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
				startActivity(intent);
			}
		});

		pinglun_top.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!shareP.getUserName().equals("")) {
					Intent in = new Intent(MovieDetailActivity.this, WritePinLunAct.class);
					in.putExtra("m_id", m_id);
					in.putExtra("upcomming", upcomming);
					in.putExtra("name", name.getText());
					in.putExtra("imageUrl", cover_image_url);
					in.putExtra("uid", shareP.getUid()[0]);
					in.putExtra("token", shareP.getUid()[1]);
					System.out.println("shareP.getUid()[0]    " + shareP.getUid()[0] + "    "
							+ shareP.getUid()[1]);
					startActivity(in);
				} else {
					Intent intent = new Intent(MovieDetailActivity.this, UserLoginAct.class);
					startActivity(intent);
				}
			}
		});
		pinglun.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!shareP.getUserName().equals("")) {

					Intent in = new Intent(MovieDetailActivity.this, WritePinLunAct.class);
					in.putExtra("m_id", m_id);
					in.putExtra("upcomming", upcomming);
					in.putExtra("name", name.getText());
					in.putExtra("imageUrl", cover_image_url);
					in.putExtra("uid", shareP.getUid()[0]);
					in.putExtra("token", shareP.getUid()[1]);
					startActivity(in);
				} else {
					Intent intent = new Intent(MovieDetailActivity.this, UserLoginAct.class);
					startActivity(intent);
				}
			}
		});
		imageUrl1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(MovieDetailActivity.this, MoviePicsAct.class);
				// tidbitsUrlList
				in.putExtra("cover_image_url", cover_image_url);
				in.putExtra("tidbitsUrlList", tidbitsUrlList);
				in.putExtra("position", 0);
				startActivity(in);
			}
		});
		imageUrl2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(MovieDetailActivity.this, MoviePicsAct.class);
				// tidbitsUrlList
				in.putExtra("cover_image_url", cover_image_url);
				in.putExtra("tidbitsUrlList", tidbitsUrlList);
				in.putExtra("position", 1);
				startActivity(in);
			}
		});
		imageUrl3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(MovieDetailActivity.this, MoviePicsAct.class);
				// tidbitsUrlList
				in.putExtra("cover_image_url", cover_image_url);
				in.putExtra("tidbitsUrlList", tidbitsUrlList);
				in.putExtra("position", 2);
				startActivity(in);
			}
		});
		imageUrl4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(MovieDetailActivity.this, MoviePicsAct.class);
				// tidbitsUrlList
				in.putExtra("cover_image_url", cover_image_url);
				in.putExtra("tidbitsUrlList", tidbitsUrlList);
				in.putExtra("position", 3);
				startActivity(in);
			}
		});

		footView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (status.equals(MORE_COMMENT)) {
					if (isCanMoreCommented) {
						if (list.size() >= 10) {
							isCanMoreCommented = false;
							// MoreCommentListBean moreList = new
							// MoreCommentListBean();
							// moreList.cleanList();
							// moreList.setList(list);
							// System.out.println("moreList   "+moreList.getList().size());
							// comentId = list.get(9).c_id;

							Intent in = new Intent(MovieDetailActivity.this,
									MoreCommentActivity.class);
							in.putExtra("m_id", m_id);
							// in.putExtra("comentId", list.get(9).c_id);
							in.putExtra("upcomming", upcomming);
							startActivity(in);

							// new
							// GetCommentInfoTask(MovieDetailActivity.this).execute();
						}
					}
				} else {
					System.out.println("no_comment");
				}
			}
		});

		headLinear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isListViewMoreHidden) {
					AnimUtils.setToRightGoneAnim(more_relativeLayout, listView_more, 0.2f);
					// listView_more.setVisibility(View.GONE);
					isListViewMoreHidden = true;
				}

			}
		});

		// headScrollView.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// if (!isListViewMoreHidden) {
		// AnimUtils.setToRightGoneAnim(more_relativeLayout, listView_more,
		// 0.2f);
		// // listView_more.setVisibility(View.GONE);
		// isListViewMoreHidden = true;
		// }
		// }
		// });
		// headScrollView.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// if(event.getAction() == MotionEvent.ACTION_DOWN){
		// return false;
		// }
		// if(event.getAction() == MotionEvent.ACTION_MOVE){
		// return false;
		// }
		// if (event.getAction() == MotionEvent.ACTION_UP) {
		// if (!isListViewMoreHidden) {
		// AnimUtils.setToRightGoneAnim(more_relativeLayout, listView_more,
		// 0.2f);
		// // listView_more.setVisibility(View.GONE);
		// isListViewMoreHidden = true;
		// return false;
		// }
		//
		// }
		// return true;
		// }
		// });
	}

	GetCommentInfoTask getCommentInfoTask;

	private void setData(int movieType, int position) {
		try {

			if (currentMoviePosition != position) {
				currentMoviePosition = position;
				if (movieType == 0) {
					if (movieInfo.movies.get(position).m_id.equals(hotMovieList.get(position).m_id)) {
						adapter_new.setSelect(position);
					} else {
						for (int i = 0; i < hotMovieList.size(); i++) {
							if (movieInfo.movies.get(position).m_id
									.equals(hotMovieList.get(i).m_id)) {
								adapter_new.setSelect(i);
								break;
							}
						}
					}

					adapter_noHot.setSelect(-1);
				} else if (movieType == 1) {
					if (movieInfo.movies.get(position).m_id.equals(noHotMovieList.get(position
							- hotMovieList.size()).m_id)) {
						adapter_noHot.setSelect(position - hotMovieList.size());
					} else {
						for (int i = 0; i < noHotMovieList.size(); i++) {
							if (movieInfo.movies.get(position).m_id
									.equals(noHotMovieList.get(i).m_id)) {
								adapter_noHot.setSelect(i);
								break;
							}
						}
					}
					adapter_new.setSelect(-1);
				}
				m_id = movieInfo.movies.get(position).m_id;
				if (isCanForLoadSd)
					for (int i = 0; i < movieList.size(); i++) {
						if (!BaseDBUtil.isHasSDpath(MovieDetailActivity.this,
								movieList.get(i).client_placard_url2)) {
							isCanLoadLargePic = false;
							break;
						}
					}
				// if (isCanLoadLargePic &&
				// NetImageView.isWifi(MovieDetailActivity.this)) {
				// isCanForLoadSd = false;
				cover_image_url = movieInfo.movies.get(position).client_placard_url1;
				// } else {
				// cover_image_url =
				// movieInfo.movies.get(position).client_placard_url2;
				// }
				upcomming = movieInfo.movies.get(position).upcomming;
				name.setText(movieList.get(position).name);
				rate.setText(movieList.get(position).rate);
				// director.setText(movieList.get(position).director);
				// if (movieList.get(position).main_actor.equals("null") ||
				// movieList.get(position).main_actor.equals("")) {
				// main_actors.setText("暂无信息！");
				// } else {
				// main_actors.setText(movieList.get(position).main_actor);
				// }
				detail_textView_moviename.setText(movieList.get(position).name);
				no_detail_textView_moviename.setText(movieList.get(position).name);
				// type.setText(movieList.get(position).type);
				if (upcomming.equals("1")) {
					release_date.setText(movieList.get(position).release_date);
					// pinglun.setBackgroundResource(R.drawable.comment_btn_tapped);
					// pinglun.setEnabled(false);
					buy_linearlayout.setVisibility(View.GONE);
					no_buy_linearlayout.setVisibility(View.VISIBLE);
					no_detail_textView_moviename.setVisibility(View.VISIBLE);
				} else {
					// pinglun.setBackgroundResource(R.drawable.pinglun_click);
					// pinglun.setEnabled(true);
					release_date.setText(movieList.get(position).release_date);
					buy_linearlayout.setVisibility(View.VISIBLE);
					no_buy_linearlayout.setVisibility(View.GONE);
					no_detail_textView_moviename.setVisibility(View.GONE);
				}
				// country.setText(movieList.get(position).country);
				// introduce.setText(movieList.get(position).introduce);
				// play.setTag(movieList.get(position).trailersAndroid);
				ratingBar.setProgress((int) (Float.parseFloat(movieList.get(position).rate) * 2));
				setImageUrl(backGround, "");
				setImageUrl(backGround, cover_image_url);
				MovieSaveDao movieSaveDao = new MovieSaveDao(MovieDetailActivity.this);
				if (movieSaveDao.isContains(m_id)) {
					saveMovie.setBackgroundResource(R.drawable.yishoucang);
				} else {
					saveMovie.setBackgroundResource(R.drawable.shoucang_selector);
				}
				// tidbitsUrlList = movieList.get(position).client_tidbits_url;
				try {

					imageUrl1.setImageBitmap(null);
					imageUrl2.setImageBitmap(null);
					imageUrl3.setImageBitmap(null);
					imageUrl4.setImageBitmap(null);
					if (tidbitsUrlList.size() != 0) {
						MovieTidbits movieTidbits1 = tidbitsUrlList.get(0);
						setImageUrl(imageUrl1, movieTidbits1.client_tidbits_url_small);
						MovieTidbits movieTidbits2 = tidbitsUrlList.get(1);
						setImageUrl(imageUrl2, movieTidbits2.client_tidbits_url_small);
						MovieTidbits movieTidbits3 = tidbitsUrlList.get(2);
						setImageUrl(imageUrl3, movieTidbits3.client_tidbits_url_small);
						MovieTidbits movieTidbits4 = tidbitsUrlList.get(3);
						setImageUrl(imageUrl4, movieTidbits4.client_tidbits_url_small);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				}

				if (list.size() > 0) {
					list.clear();
				}
				comentId = "0";
				currentPage = 1;
				if (listView.getFooterViewsCount() != 0) {
					try {
						listView.removeFooterView(footView);
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
				}
				listView.addFooterView(footView);
				more_comment_text.setText("加载评论中...");
				loading_progress.setVisibility(View.VISIBLE);
				// footView.setVisibility(View.GONE);e
				if (movieType == 0) {
					// if (isListViewMoreHidden) {
					// listView_more.setVisibility(View.VISIBLE);
					// AnimUtils.setToLeftShowAnim(more_relativeLayout, 0.2f);
					// isListViewMoreHidden = false;
					// imageView_noHot.setVisibility(View.GONE);
					//
					// }
					if (!isListViewMoreHidden) {
						AnimUtils.setToRightGoneAnim(more_relativeLayout, listView_more, 0.2f);
						// listView_more.setVisibility(View.GONE);
						isListViewMoreHidden = true;
						imageView_noHot.setVisibility(View.VISIBLE);
						listview_menuLayout.setVisibility(View.VISIBLE);
					}
				} else if (movieType == 1) {
					// if (isListViewCheapHidden) {
					// listView_noHot.setVisibility(View.VISIBLE);
					// AnimUtils.setToLeftShowAnim(relativeListNoHot, 0.2f);
					// isListViewCheapHidden = false;
					// listview_menuLayout.setVisibility(View.GONE);
					// }
					if (!isListViewCheapHidden) {
						AnimUtils.setToRightGoneAnim(relativeListNoHot, listView_noHot, 0.2f);
						// listView_more.setVisibility(View.GONE);
						isListViewCheapHidden = true;
						listview_menuLayout.setVisibility(View.VISIBLE);
						imageView_noHot.setVisibility(View.VISIBLE);
					}
				}
				if (getCommentInfoTask != null) {
					System.out.println("isCancelled()------------------"
							+ getCommentInfoTask.isCancelled());
					if (getCommentInfoTask.isCancelled() == false) {
						System.out.println("cancel------------------");
						getCommentInfoTask.cancel(true);
					}
				}
				getCommentInfoTask = new GetCommentInfoTask(MovieDetailActivity.this);
				// new GetCommentInfoTask(MovieDetailActivity.this).execute();
				getCommentInfoTask.execute();

				// if (adapter != null) {
				// adapter = null;
				// }
				// System.out.println("--------------------=================");
				//
				// adapter = new MovieDetailAdatper(MovieDetailActivity.this,
				// list);
				// listView.setAdapter(adapter);
			} else {
				if (movieType == 0) {
					// if (isListViewMoreHidden) {
					// listView_more.setVisibility(View.VISIBLE);
					// AnimUtils.setToLeftShowAnim(more_relativeLayout, 0.2f);
					// isListViewMoreHidden = false;
					// imageView_noHot.setVisibility(View.GONE);
					//
					// } else
					if (!isListViewMoreHidden) {
						AnimUtils.setToRightGoneAnim(more_relativeLayout, listView_more, 0.2f);
						// listView_more.setVisibility(View.GONE);
						isListViewMoreHidden = true;
						imageView_noHot.setVisibility(View.VISIBLE);
						listView_more.setVisibility(View.VISIBLE);
					}
					adapter_noHot.setSelect(-1);
				} else if (movieType == 1) {
					// if (isListViewCheapHidden) {
					// listView_noHot.setVisibility(View.VISIBLE);
					// AnimUtils.setToLeftShowAnim(relativeListNoHot, 0.2f);
					// isListViewCheapHidden = false;
					// listview_menuLayout.setVisibility(View.GONE);
					// } else
					if (!isListViewCheapHidden) {
						AnimUtils.setToRightGoneAnim(relativeListNoHot, listView_noHot, 0.2f);
						// listView_more.setVisibility(View.GONE);
						isListViewCheapHidden = true;
						listview_menuLayout.setVisibility(View.VISIBLE);
						listView_noHot.setVisibility(View.VISIBLE);
					}
					adapter_new.setSelect(-1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class GetCommentInfoTask extends MovieAsyncTask<String, String, CommentInfo> {
		public String exception;
		CommentInfo commentInfo;

		public GetCommentInfoTask(Activity activity) {
			super(activity, null, true, true, false);
		}

		@Override
		protected CommentInfo doInBackground(String... params) {

			try {

				commentInfo = commentLib.getCommentsByMovieId(m_id, comentId, "10", upcomming);

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
			return commentInfo;
		}

		@Override
		protected void onPostExecute(CommentInfo result) {
			super.onPostExecute(result);
			if (result != null && result.isSuccess()) {

				if (listView.getFooterViewsCount() != 0) {
					listView.removeFooterView(footView);
					loading_progress.setVisibility(View.GONE);
				}
				if (commentInfo.comments != null) {
					if (list.size() != 0) {
						list.clear();
					}
					list.addAll(commentInfo.comments);
				}
				// if
				// (movieInfo.movies.get(currentMoviePosition).upcomming.equals("1"))
				// {
				// status = NO_COMMENT;
				// more_comment_text.setText("即将上映的 影片，无法评论！");
				//
				// // footView.setVisibility(View.VISIBLE);e
				// if (listView.getFooterViewsCount() == 0) {
				// listView.addFooterView(footView);
				// }
				// } else {
				if (list.size() == 0) {
					status = NO_COMMENT;
					more_comment_text.setText("目前还没有评论，快去抢沙发吧！");
					// footView.setVisibility(View.VISIBLE);
					if (listView.getFooterViewsCount() == 0) {
						listView.addFooterView(footView);
					}
				} else {
					total = commentInfo.total;
					status = MORE_COMMENT;
					more_comment_text.setText("更多评论");
					if (Integer.parseInt(total) > 10) {
						// footView.setVisibility(View.VISIBLE);
						if (listView.getFooterViewsCount() == 0) {
							listView.addFooterView(footView);
						}
					} else {
						// footView.setVisibility(View.GONE);
						if (listView.getFooterViewsCount() != 0) {
							listView.removeFooterView(footView);
						}
					}
				}
				// }
				isMoviesLoadAll = true;

				if (!isCanMoreCommented) {
					// currentPage += 1;
					isCanMoreCommented = true;
				}

				adapter.notifyDataSetChanged();

			} else {
				if (listView.getFooterViewsCount() != 0) {
					listView.removeFooterView(footView);
					loading_progress.setVisibility(View.GONE);
				}
				if (list.size() == 0) {
					status = NO_COMMENT;
					more_comment_text.setText("目前还没有评论，快去抢沙发吧！");
					// footView.setVisibility(View.VISIBLE);
					if (listView.getFooterViewsCount() == 0) {
						listView.addFooterView(footView);
					}
				}
				isMoviesLoadAll = true;
				if (!isCanMoreCommented) {
					// currentPage += 1;
					isCanMoreCommented = true;
				}

				adapter.notifyDataSetChanged();
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	class GetMovieInfoNullTask extends MovieAsyncTask<String, String, MovieInfo> {
		public String exception;
		CommentInfo commentInfo;

		public GetMovieInfoNullTask(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected MovieInfo doInBackground(String... params) {

			try {
				System.out.println("mid   " + m_id);

				// if (AppConstants.movieInfo != null) {
				// // movieInfo = AppConstants.movieInfo;
				// } else {
				// movieInfo = lib.getAllMovie();
				// }

				for (int i = 0; i < movieInfo.movies.size(); i++) {
					if (movieInfo.movies.get(i).m_id.equals(m_id)) {
						upcomming = movieInfo.movies.get(i).upcomming;
						break;
					}
				}
				commentInfo = commentLib.getCommentsByMovieId(m_id, "0", "10", upcomming);

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
			return movieInfo;
		}

		@Override
		protected void onPostExecute(MovieInfo result) {
			super.onPostExecute(result);
			if (result != null && result.isSuccess()) {
				AppConstants.movieInfo = result;
				loading_progress.setVisibility(View.GONE);
				listView.removeFooterView(footView);
				if (commentInfo != null) {
					if (commentInfo.comments != null) {
						if (list.size() != 0) {
							list.clear();
						}
						list.addAll(commentInfo.comments);
					}
				}
				// if
				// (result.movies.get(currentMoviePosition).upcomming.equals("1"))
				// {
				// status = NO_COMMENT;
				// more_comment_text.setText("即将上映的 影片，无法评论！");
				// // footView.setVisibility(View.VISIBLE);e
				// if (listView.getFooterViewsCount() == 0) {
				// listView.addFooterView(footView);
				// }
				// } else {
				if (list.size() == 0) {
					status = NO_COMMENT;
					more_comment_text.setText("目前还没有评论，快去抢沙发吧！");
					// footView.setVisibility(View.VISIBLE);
					if (listView.getFooterViewsCount() == 0) {
						listView.addFooterView(footView);
					}
				} else {
					status = MORE_COMMENT;
					more_comment_text.setText("更多评论");
					if (commentInfo != null) {
						if (commentInfo.isSuccess()) {
							total = commentInfo.total;
							if (Integer.parseInt(total) > 10) {
								// footView.setVisibility(View.VISIBLE);
								if (listView.getFooterViewsCount() == 0) {
									listView.addFooterView(footView);
								}
							}
						}
					}
				}
				// }
				if (isFromMoviePrepareAct) {
					try {
						for (int i = 0; i < result.movies.size(); i++) {
							if (m_id.equals(result.movies.get(i).m_id)) {
								currentMoviePosition = i;
								break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (isFromSave) {
					// boolean hasMid = false;
					// for(int i = 0;i<result.movies.size();i++){
					// if (result.movies.get(i).m_id.equals(m_id)) {
					// hasMid = true;
					// break;
					// }
					// }
					// if(hasMid == false){
					// try {
					// MovieSaveDao movieSaveDao = new
					// MovieSaveDao(MovieDetailActivity.this);
					// movieSaveDao.deleteMovie(m_id);
					// saveMovie.setBackgroundResource(R.drawable.shoucang_selector);
					// } catch (Exception e) {
					// e.printStackTrace();
					// }
					// Toast.makeText(MovieDetailActivity.this, "该影片已下线！",
					// Toast.LENGTH_SHORT).show();
					// finish();
					// }
					for (int i = 0; i < result.movies.size(); i++) {
						if (m_id.equals(result.movies.get(i).m_id)) {
							currentMoviePosition = i;
							name.setText(result.movies.get(i).name);
							detail_textView_moviename.setText(result.movies.get(i).name);
							no_detail_textView_moviename.setText(result.movies.get(i).name);
							rate.setText(result.movies.get(i).rate);
							// director.setText(result.movies.get(i).director);
							// if
							// (result.movies.get(i).main_actor.equals("null")
							// || result.movies.get(i).main_actor.equals("")) {
							// main_actors.setText("暂无信息！");
							// } else {
							// main_actors.setText(result.movies.get(i).main_actor);
							// }
							// type.setText(result.movies.get(i).type);
							if (result.movies.get(i).upcomming.equals("1")) {
								release_date.setText(result.movies.get(i).release_date);
								// pinglun.setBackgroundResource(R.drawable.comment_btn_tapped);
								// pinglun.setEnabled(false);
								buy_linearlayout.setVisibility(View.GONE);
								no_buy_linearlayout.setVisibility(View.VISIBLE);
								no_detail_textView_moviename.setVisibility(View.VISIBLE);
							} else {
								// pinglun.setBackgroundResource(R.drawable.pinglun_click);
								// pinglun.setEnabled(true);
								release_date.setText(result.movies.get(i).release_date);
								buy_linearlayout.setVisibility(View.VISIBLE);
								no_buy_linearlayout.setVisibility(View.GONE);
								no_detail_textView_moviename.setVisibility(View.GONE);
							}
							// country.setText(result.movies.get(i).country);
							// introduce.setText(result.movies.get(i).introduce);
							// ratingBar.setProgress((int)
							// (Float.parseFloat(result.movies.get(i).rate) *
							// 2));
							// play.setTag(result.movies.get(i).trailersAndroid);//
							// 获得播放地址
							upcomming = result.movies.get(i).upcomming;
							if (isCanForLoadSd)
								for (int j = 0; j < movieList.size(); j++) {
									if (!BaseDBUtil.isHasSDpath(MovieDetailActivity.this,
											movieList.get(j).client_placard_url2)) {
										isCanLoadLargePic = false;
										break;
									}
								}
							// if (isCanLoadLargePic &&
							// NetImageView.isWifi(MovieDetailActivity.this)) {
							// isCanForLoadSd = false;
							cover_image_url = result.movies.get(i).client_placard_url1;
							// } else {
							// cover_image_url =
							// result.movies.get(i).client_placard_url2;
							// }

							setImageUrl(backGround, cover_image_url);

							// tidbitsUrlList =
							// result.movies.get(i).client_tidbits_url;
							saveMovie.setBackgroundResource(R.drawable.yishoucang);
							try {
								MovieTidbits movieTidbits1 = tidbitsUrlList.get(0);
								setImageUrl(imageUrl1, movieTidbits1.client_tidbits_url_small);
								MovieTidbits movieTidbits2 = tidbitsUrlList.get(1);
								setImageUrl(imageUrl2, movieTidbits2.client_tidbits_url_small);
								MovieTidbits movieTidbits3 = tidbitsUrlList.get(2);
								setImageUrl(imageUrl3, movieTidbits3.client_tidbits_url_small);
								MovieTidbits movieTidbits4 = tidbitsUrlList.get(3);
								setImageUrl(imageUrl4, movieTidbits4.client_tidbits_url_small);
							} catch (ArrayIndexOutOfBoundsException e) {
								e.printStackTrace();
							}
						}

					}

					isFromSave = false;

				}
				for (int i = 0; i < result.movies.size(); i++) {
					if (result.movies.get(i).upcomming.equals("0")) {
						hotMovieList.add(result.movies.get(i));
					} else if (result.movies.get(i).upcomming.equals("1")) {
						noHotMovieList.add(result.movies.get(i));
					}

				}

				adapter_new.addItem(hotMovieList);
				adapter_noHot.addItem(noHotMovieList);
				// adapter_new.addItem(result.movies);
				isMoviesLoadAll = true;
				movieList = result.movies;
				try {
					adapter_new.setSelect(currentMoviePosition);
					listView_more.setSelection(currentMoviePosition);
					adapter_new.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					// cover_image_url 大图
					setImageUrl(backGround, cover_image_url);
					MovieTidbits movieTidbits1 = tidbitsUrlList.get(0);
					setImageUrl(imageUrl1, movieTidbits1.client_tidbits_url_small);
					MovieTidbits movieTidbits2 = tidbitsUrlList.get(1);
					setImageUrl(imageUrl2, movieTidbits2.client_tidbits_url_small);
					MovieTidbits movieTidbits3 = tidbitsUrlList.get(2);
					setImageUrl(imageUrl3, movieTidbits3.client_tidbits_url_small);
					MovieTidbits movieTidbits4 = tidbitsUrlList.get(3);
					setImageUrl(imageUrl4, movieTidbits4.client_tidbits_url_small);
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(MovieDetailActivity.this, "加载失败，请尝试重新加载！", Toast.LENGTH_LONG).show();
				finish();
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	class GetMovieInfoTask extends MovieAsyncTask<String, String, MovieInfo> {
		public String exception;
		CommentInfo commentInfo;

		public GetMovieInfoTask(Activity activity) {
			super(activity, null, true, true, false);
		}

		@Override
		protected MovieInfo doInBackground(String... params) {

			try {
				System.out.println("mid   " + m_id);

				// if(AppConstants.movieInfo!=null){
				// // movieInfo = AppConstants.movieInfo;
				// }else{
				// movieInfo = lib.getAllMovie();
				// }

				for (int i = 0; i < movieInfo.movies.size(); i++) {
					if (movieInfo.movies.get(i).m_id.equals(m_id)) {
						upcomming = movieInfo.movies.get(i).upcomming;
						break;
					}
				}
				commentInfo = commentLib.getCommentsByMovieId(m_id, "0", "10", upcomming);

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
			return movieInfo;
		}

		@Override
		protected void onPostExecute(MovieInfo result) {
			super.onPostExecute(result);
			if (result != null && result.isSuccess()) {
				listView.removeFooterView(footView);
				loading_progress.setVisibility(View.GONE);
				AppConstants.movieInfo = result;
				if (commentInfo != null) {
					if (commentInfo.comments != null) {
						if (list.size() != 0) {
							list.clear();
						}
						list.addAll(commentInfo.comments);
					}
				}
				// if
				// (result.movies.get(currentMoviePosition).upcomming.equals("1"))
				// {
				// status = NO_COMMENT;
				// more_comment_text.setText("即将上映的 影片，无法评论！");
				// // footView.setVisibility(View.VISIBLE);e
				// if (listView.getFooterViewsCount() == 0) {
				// listView.addFooterView(footView);
				// }
				// } else {
				if (list.size() == 0) {
					status = NO_COMMENT;
					more_comment_text.setText("目前还没有评论，快去抢沙发吧！");
					// footView.setVisibility(View.VISIBLE);
					if (listView.getFooterViewsCount() == 0) {
						listView.addFooterView(footView);
					}
				} else {
					status = MORE_COMMENT;
					more_comment_text.setText("更多评论");
					if (commentInfo != null) {
						if (commentInfo.isSuccess()) {
							total = commentInfo.total;
							if (Integer.parseInt(total) > 10) {
								// footView.setVisibility(View.VISIBLE);
								if (listView.getFooterViewsCount() == 0) {
									listView.addFooterView(footView);
								}
							}
						}
					}
				}
				// }
				if (isFromMoviePrepareAct) {
					try {
						for (int i = 0; i < result.movies.size(); i++) {
							if (m_id.equals(result.movies.get(i).m_id)) {
								currentMoviePosition = i;
								break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (isFromSave) {
					// boolean hasMid = false;
					// for(int i = 0;i<result.movies.size();i++){
					// if (result.movies.get(i).m_id.equals(m_id)) {
					// hasMid = true;
					// break;
					// }
					// }
					// if(hasMid == false){
					// try {
					// MovieSaveDao movieSaveDao = new
					// MovieSaveDao(MovieDetailActivity.this);
					// movieSaveDao.deleteMovie(m_id);
					// saveMovie.setBackgroundResource(R.drawable.shoucang_selector);
					// } catch (Exception e) {
					// e.printStackTrace();
					// }
					// Toast.makeText(MovieDetailActivity.this, "该影片已下线！",
					// Toast.LENGTH_SHORT).show();
					// finish();
					// }
					for (int i = 0; i < result.movies.size(); i++) {
						if (m_id.equals(result.movies.get(i).m_id)) {
							currentMoviePosition = i;
							name.setText(result.movies.get(i).name);
							detail_textView_moviename.setText(result.movies.get(i).name);
							no_detail_textView_moviename.setText(result.movies.get(i).name);
							rate.setText(result.movies.get(i).rate);
							// director.setText(result.movies.get(i).director);
							// if
							// (result.movies.get(i).main_actor.equals("null")
							// || result.movies.get(i).main_actor.equals("")) {
							// main_actors.setText("暂无信息！");
							// } else {
							// main_actors.setText(result.movies.get(i).main_actor);
							// }
							// type.setText(result.movies.get(i).type);
							if (result.movies.get(i).upcomming.equals("1")) {
								release_date.setText(result.movies.get(i).release_date);
								// pinglun.setBackgroundResource(R.drawable.comment_btn_tapped);
								// pinglun.setEnabled(false);
								buy_linearlayout.setVisibility(View.GONE);
								no_buy_linearlayout.setVisibility(View.VISIBLE);
								no_detail_textView_moviename.setVisibility(View.VISIBLE);
							} else {
								// pinglun.setBackgroundResource(R.drawable.pinglun_click);
								// pinglun.setEnabled(true);
								release_date.setText(result.movies.get(i).release_date);
								buy_linearlayout.setVisibility(View.VISIBLE);
								no_buy_linearlayout.setVisibility(View.GONE);
								no_detail_textView_moviename.setVisibility(View.GONE);
							}
							// country.setText(result.movies.get(i).country);
							// introduce.setText(result.movies.get(i).introduce);
							// ratingBar.setProgress((int)
							// (Float.parseFloat(result.movies.get(i).rate) *
							// 2));
							// play.setTag(result.movies.get(i).trailersAndroid);//
							// 获得播放地址
							// upcomming = result.movies.get(i).upcomming;
							// cover_image_url =
							// result.movies.get(i).client_placard_url2;
							//
							// tidbitsUrlList =
							// result.movies.get(i).client_tidbits_url;
							// saveMovie.setBackgroundResource(R.drawable.yishoucang);
							break;
						}

					}

					isFromSave = false;

				}

				isMoviesLoadAll = true;
				movieList = result.movies;
				try {
					if (result.movies.get(currentMoviePosition).upcomming.equals("0")) {
						if (result.movies.get(currentMoviePosition).m_id.equals(hotMovieList
								.get(currentMoviePosition).m_id)) {
							adapter_new.setSelect(currentMoviePosition);
							adapter_noHot.setSelect(-1);
						} else {
							for (int i = 0; i < hotMovieList.size(); i++) {
								if (movieInfo.movies.get(currentMoviePosition).m_id
										.equals(hotMovieList.get(i).m_id)) {
									adapter_new.setSelect(i);
									adapter_noHot.setSelect(-1);
									break;
								}
							}
						}
					} else if (result.movies.get(currentMoviePosition).upcomming.equals("1")) {
						if (noHotMovieList.get(currentMoviePosition - hotMovieList.size()).m_id
								.equals(movieInfo.movies.get(currentMoviePosition).m_id)) {
							adapter_noHot.setSelect(currentMoviePosition - hotMovieList.size());
							adapter_new.setSelect(-1);
							System.out
									.println("1 =    "
											+ noHotMovieList.get(currentMoviePosition
													- hotMovieList.size()).m_id + "    "
											+ movieInfo.movies.get(currentMoviePosition).m_id);
						} else {
							for (int i = 0; i < noHotMovieList.size(); i++) {
								if (movieInfo.movies.get(currentMoviePosition).m_id
										.equals(noHotMovieList.get(i).m_id)) {
									adapter_noHot.setSelect(i);
									adapter_new.setSelect(-1);
									System.out.println("1 for  !=    " + noHotMovieList.get(i).m_id
											+ "    "
											+ movieInfo.movies.get(currentMoviePosition).m_id);
									break;
								}
							}
						}
					}

					// listView_more.setSelection(currentMoviePosition);
					adapter_new.notifyDataSetChanged();
					adapter_noHot.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					setImageUrl(backGround, cover_image_url);
					MovieTidbits movieTidbits1 = tidbitsUrlList.get(0);
					setImageUrl(imageUrl1, movieTidbits1.client_tidbits_url_small);
					MovieTidbits movieTidbits2 = tidbitsUrlList.get(1);
					setImageUrl(imageUrl2, movieTidbits2.client_tidbits_url_small);
					MovieTidbits movieTidbits3 = tidbitsUrlList.get(2);
					setImageUrl(imageUrl3, movieTidbits3.client_tidbits_url_small);
					MovieTidbits movieTidbits4 = tidbitsUrlList.get(3);
					setImageUrl(imageUrl4, movieTidbits4.client_tidbits_url_small);
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(MovieDetailActivity.this, "加载失败，请尝试重新加载！", Toast.LENGTH_LONG).show();
				finish();
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	private void showDailog(String msg) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle("确认播放");
		builder.setMessage(msg);

		builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				System.out.println("url              ========    " + (String) play.getTag());
				intent.putExtra("url", (String) play.getTag());
				intent.setClass(MovieDetailActivity.this, VideoPlayerActivity.class);
				startActivity(intent);
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}

	// private void setImageBackGround(final ImageView imageView) {
	//
	// imageView.setTag(cover_image_url);
	// Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(this,
	// cover_image_url, new ImageCallback() {
	//
	// @Override
	// public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
	// String url = (String) imageView.getTag();
	// if (url.equals(bitmapUrl)) {
	// imageView.setImageBitmap(bitmap);
	// }
	// }
	// }, AsyncImageLoader.CACHE_TYPE_SD);
	// if (bitmap != null) {
	// imageView.setImageBitmap(bitmap);
	// }
	//
	// }

	private void setImageUrl(final ImageView imageView, String url) {
		if (TextUtils.isEmpty(url)) {
			imageView.setImageBitmap(null);
			return;
		}
		if (url.equals("null")) {
			imageView.setImageBitmap(null);
			return;
		}
		imageView.setTag(url);
		Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(this, url, new ImageCallback() {

			@Override
			public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
				String url = (String) imageView.getTag();
				if (url.equals(bitmapUrl)) {
					imageView.setImageBitmap(bitmap);
				}
			}
		}, AsyncImageLoader.CACHE_TYPE_SD, true);
		if (bitmap != null) {
			// if(!bitmapMap.containsKey(url)){
			// bitmapMap.put(url,bitmap);
			// urlList.add(url);
			// }
			imageView.setImageBitmap(bitmap);
		}
	}

	private AlertDialog mAlertDialog = null;
	private String items[] = null;

	private void openDialog() {
		items = new String[] { "新浪微博分享", "短信分享", "邮件分享", "取消" };
		mAlertDialog = new AlertDialog.Builder(this).setTitle("分享影片")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						switch (which) {
						case 0:

							Intent i = new Intent(MovieDetailActivity.this, SinaShareAct.class);
							i.putExtra("isFromMovieDetailActivity", true);
							i.putExtra("name", name.getText().toString());
							i.putExtra("introduce", introduce.getText().toString());
							startActivity(i);
							mAlertDialog.dismiss();
							break;
						case 1:
							// /下方是是通过Intent调用系统的图片查看器的关键代码
							// Intent localIntent = new Intent();
							// localIntent.setType("image/*");
							// localIntent.setAction("android.intent.action.GET_CONTENT");
							// startActivityForResult(localIntent,
							// PHOTO_PICKED_WITH_DATA);
							Intent intent = new Intent(android.content.Intent.ACTION_SENDTO);
							Uri smsToUri = Uri.parse("smsto:");
							intent.setData(smsToUri);
							if (!introduce.getText().toString().trim().equals("")
									&& !introduce.getText().toString().trim().equals("null"))
								intent.putExtra("sms_body", "电影："
										+ name.getText().toString().trim() + " ， "
										+ introduce.getText().toString().trim());
							try {
								startActivity(intent);
							} catch (Exception e) {
								startActivity(Intent.createChooser(intent, "选择短信程序"));
							}

							// Intent intent = new Intent(Intent.ACTION_VIEW);
							// if(!introduce.getText().toString().equals("")&&!introduce.getText().toString().equals("null"))
							// intent.putExtra("sms_body",
							// introduce.getText().toString());
							// intent.setType("vnd.android-dir/mms-sms");
							// startActivity(intent);
							mAlertDialog.dismiss();
							break;
						case 2:
							Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
							emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							emailIntent.setType("plain/text");
							emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "");
							if (!introduce.getText().toString().trim().equals("")
									&& !introduce.getText().toString().trim().equals("null")) {
								emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, name
										.getText().toString().trim());
								emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, introduce
										.getText().toString().trim());
							}
							try {
								startActivity(Intent.createChooser(emailIntent, "选择邮件程序"));
							} catch (Exception e) {
								startActivity(emailIntent);
							}

							mAlertDialog.dismiss();
							break;
						}
					}

				}).create();
	}

	class MovieDetailAdatper extends BaseAdapter {
		private Context mContext;

		ArrayList<Comment> list;
		private MovieApplication app;

		private MovieDetailAdatper(Context context, ArrayList<Comment> list) {
			mContext = context;
			app = (MovieApplication) mContext.getApplicationContext();
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final Holder holder;
			if (convertView == null) {
				holder = new Holder();

				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.layout_movie_detail_comment_item, parent, false);
				// holder.pinglun =
				// (ImageView)convertView.findViewById(R.id.pinglun);
				// else{
				// convertView =
				// LayoutInflater.from(mContext).inflate(R.layout.layout_movie_detail_comment_item,
				// parent, false);
				// }
				holder.imageUrl = (ImageView) convertView.findViewById(R.id.imageUrl);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.point = (TextView) convertView.findViewById(R.id.point);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.ping = (TextView) convertView.findViewById(R.id.ping);
				holder.pointtrate = (RatingBar) convertView.findViewById(R.id.pointtrate);

				convertView.setTag(holder);
			} else {

				holder = (Holder) convertView.getTag();
			}
			Comment comment = list.get(position);

			holder.name.setText(list.get(position).nickname);
			String data = list.get(position).comment_time.split("\\ ")[0];
			String time = list.get(position).comment_time.split("\\ ")[1];
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sTimeFormat = new SimpleDateFormat("HH:mm:ss");
			String data1 = sDateFormat.format(new java.util.Date());
			String time1 = sTimeFormat.format(new java.util.Date());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = null;

			long systemData = 0, systemTime = 0, beanData = 0, beanTime = 0;
			try {
				systemData = sDateFormat.parse(data1).getTime();
				systemTime = sTimeFormat.parse(time1).getTime();
				beanData = sDateFormat.parse(data).getTime();
				beanTime = sTimeFormat.parse(time).getTime();
				date = format.parse(list.get(position).comment_time);
				// System.out.println("data     --- "+date.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println("beanData    "+beanData+"         systemData        "+systemData+"            "+(systemData-beanData)/(1000*3600*24));
			// if(beanData!=0&&((systemData-beanData)/(1000*3600*24))<1){
			// if(systemTime>beanTime&&(systemTime-beanTime)/(1000*3600)<1){
			// //几分中前
			// if(((int)(systemTime-beanTime)/(1000*3600/60))==0){
			// holder.time.setText("刚刚");
			// }else
			// holder.time.setText((int)(systemTime-beanTime)/(1000*3600/60)+"分钟前");
			// System.out.println("fenzhong        "+(int)(systemTime-beanTime)/(1000*3600/60));
			// }else
			// if(systemTime>beanTime&&(systemTime-beanTime)/(1000*3600)>=1){//几小时前
			// holder.time.setText((int)(systemTime-beanTime)/(1000*3600)+"小时前");
			// System.out.println("xiaoshi        "+(int)(systemTime-beanTime)/(1000*3600));
			// }
			// }else
			// if(beanData!=0&&((systemData-beanData)/(1000*3600*24))>=1){//几天前
			// holder.time.setText((int)((float)((systemData-beanData)/(1000*3600*24)))+"天前");
			// System.out.println("tian   "+(int)((float)((systemData-beanData)/(1000*3600*24))));
			// }
			holder.time.setText(TimeTools.getTimeString(MovieDetailActivity.this, date.getTime()
					/ 1000 + ""));
			holder.ping.setText(list.get(position).content);
			if (comment != null && URLUtil.isHttpUrl(comment.head_image)) {
				holder.imageUrl.setTag(comment.head_image);
				Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext,
						comment.head_image, new ImageCallback() {

							@Override
							public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
								String url = (String) holder.imageUrl.getTag();
								if (url.equals(bitmapUrl)) {

									holder.imageUrl.setImageBitmap(bitmap);
								}
							}
						}, AsyncImageLoader.CACHE_TYPE_SD, true);
				if (bitmap != null) {
					// if(!bitmapMap.containsKey(comment.head_image)){
					// bitmapMap.put(comment.head_image,bitmap);
					// urlList.add(comment.head_image);
					// }
					holder.imageUrl.setImageBitmap(bitmap);
				}
			}

			return convertView;
		}

	}

	private static class Holder {
		public ImageView imageUrl;
		public TextView name, point, time, ping;
		public RatingBar pointtrate;
		// public ImageView pinglun;
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		isCanMoreCommented = true;
	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// System.out.println("urlList.size--------"+urlList.size());
		// for(int i = 0;i<urlList.size();i++){
		// if(bitmapMap.get(urlList.get(i))!=null){
		// if(!bitmapMap.get(urlList.get(i)).isRecycled()){
		// System.out.println("url----------"+urlList.get(i));
		// System.out.println("bitmap----------"+bitmapMap.get(urlList.get(i)));
		// bitmapMap.get(urlList.get(i)).recycle();
		// }
		// }
		// }
		app.getAsyncImageLoader().recycleBitmaps();
	}

	public void onResume() {
		super.onResume();
		if (listView_more != null) {
			imageView_more.setVisibility(View.VISIBLE);
			listView_more.setVisibility(View.GONE);
			isListViewMoreHidden = true;
		}
		CmccDataStatistics.onStart(this);
	}

	public void onPause() {
		super.onPause();
		CmccDataStatistics.onStop(this);
	}
}
