package com.szcmcc.movie.activity;

import java.io.IOException;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itotem.view.pullrefresh.PullToRefreshBase;
import com.itotem.view.pullrefresh.PullToRefreshListView;
import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.adapter.ZSQPeopleCriticAdapter;
import com.szcmcc.movie.adapter.ZSQProfessionalCriticAdapter;
import com.szcmcc.movie.adapter.ZSQStillsAdapter;
import com.szcmcc.movie.bean.CommentInfo;
import com.szcmcc.movie.bean.Movie;
import com.szcmcc.movie.bean.MovieInfo;
import com.szcmcc.movie.bean.PrefessionInfo;
import com.szcmcc.movie.bean.SaveMovieNew;
import com.szcmcc.movie.cache.AppConstants;
import com.szcmcc.movie.cache.MovieSaveDao;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieCommentLib;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.storage.SharedPreferencesUtil;
import com.szcmcc.movie.view.MarqueeTextView;
import com.szcmcc.movie.view.MessageDialog;
import com.szcmcc.movie.view.ToastAlone;

public class ZSQMoiveDetailActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	private MovieApplication app;
	protected MovieLib lib;
	private ImageButton imBack;
	private ImageView ivStills;// 电影海报
	private TextView tvInfo;// 剧情介绍
	private MarqueeTextView tvMovieName;// 电影名称
	private TextView tvStarring;// 电影主演
	private TextView tvScore;// 电影评分
	private TextView tvDirector;// 电影导演
	private TextView tvMovieType;// 电影类型
	private TextView tvMovieTime;// 电影上映时间
	private TextView tvPeopleCriticNum;// 大众点评个数
	private RatingBar rBar;// 评分条
	private Button btnPlot;// 剧情介绍按钮
	private Button btnPeopleCritic;// 大众影评按钮
	private Button btnStills;// 剧照按钮
	private Button btnProfessionalCritic;// 专业影评按钮
	private ScrollView svPlot;// 剧情介绍显示区
	private LinearLayout lvPeopleCritic;// 大众影评显示区
	private LinearLayout lvStills;// 剧照显示区
	private LinearLayout lvProfessionalCritic;// 专业影评显示区
	private PullToRefreshListView pullToRefreshListView1;
	private PullToRefreshListView pullToRefreshListView2;
	private ListView listPeopleCritic;
	private ListView listProfessionalCritic;
	private ZSQPeopleCriticAdapter peopleCriticAdapter;
	private ZSQProfessionalCriticAdapter professionalCriticAdapter;
	private ZSQStillsAdapter stillsAdapter;
	private GridView gvStills;// 剧照
	// private Movie mMovie = null;
	private CommentInfo commentInfo = null;// 大众影评数据
	private PrefessionInfo prefessionInfo = null;// 专业影评数据
	private MovieCommentLib commentLib;
	// private String comentId = "0";// 大众影评Id
	// private ArrayList<Comment> commentList = new ArrayList<Comment>();
	// private ArrayList<Profession> professionList = new
	// ArrayList<Profession>();
	private ImageView ivPlay;// 播放按钮
	private ImageView saveBtn, shareBtn;// 收藏按钮和分享按钮
	private Button btnWrite;// 写评论按钮
	private SharedPreferencesUtil shareP;
	private String m_id;// 影片id
	private String upcomming;
	private TextView movie_detail_buy;
	public static ZSQMoiveDetailActivity zSQMoiveDetailActivity = null;
	GetCommentInfoTask getCommentInfoTask;
	GetPrefessionInfoTask getPrefessionInfoTask;
	private int currentCommentCount = 0;
	private int currentProfessionCount = 0;
	private boolean canBuy = true;
	private MovieInfo mMovieInfo;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zsq_act_movie_detail);
		zSQMoiveDetailActivity = this;
		app = (MovieApplication) getApplicationContext();
		commentLib = MovieCommentLib.getInstance(this);
		shareP = SharedPreferencesUtil.getInstance(this);

		// 接受数据,从影片进入
		// mMovie = (Movie) getIntent().getSerializableExtra("movie");
		m_id = getIntent().getStringExtra("m_id");
		upcomming = getIntent().getStringExtra("upcomming");
		canBuy = getIntent().getBooleanExtra("canbuy", true);
		
		if (TextUtils.isEmpty(upcomming)) {
			upcomming = "0";
		}
		Log.i("zzz", "m_id = " + m_id);
		Log.i("zzz", "upcomming = " + upcomming);
		// 从影院进入
		// if (mMovie == null) {
		// m_id = getIntent().getStringExtra("m_id");
		// getMovie(m_id);
		// }
		if (TextUtils.isEmpty(m_id) || TextUtils.isEmpty(upcomming)) {
			ToastAlone.makeText(this, "影片错误", Toast.LENGTH_SHORT).show();
			finish();
		}
		try {
			init();
			new GetMovieInfoTask(this).execute();

//			getSaveStatus();
			setListener1();// 下拉刷新，上拉加载
			setListener2();// 下拉刷新，上拉加载
			peopleCriticAdapter = new ZSQPeopleCriticAdapter(ZSQMoiveDetailActivity.this);
			listPeopleCritic.setAdapter(peopleCriticAdapter);
			professionalCriticAdapter = new ZSQProfessionalCriticAdapter(
					ZSQMoiveDetailActivity.this);
			listProfessionalCritic.setAdapter(professionalCriticAdapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init() {
		ivPlay = (ImageView) findViewById(R.id.ivPlay);
		ivStills = (ImageView) findViewById(R.id.ivStills);
		saveBtn = (ImageView) findViewById(R.id.saveBtn);
		shareBtn = (ImageView) findViewById(R.id.shareBtn);
		rBar = (RatingBar) findViewById(R.id.rBar);
		tvPeopleCriticNum = (TextView) findViewById(R.id.tvPeopleCriticNum);
		tvInfo = (TextView) findViewById(R.id.tvInfo);
		tvMovieTime = (TextView) findViewById(R.id.tvMovieTime);
		tvMovieType = (TextView) findViewById(R.id.tvMovieType);
		tvStarring = (TextView) findViewById(R.id.tvStarring);
		tvMovieName = (MarqueeTextView) findViewById(R.id.tvMovieName);
		tvScore = (TextView) findViewById(R.id.tvScore);
		tvDirector = (TextView) findViewById(R.id.tvDirector);
		imBack = (ImageButton) findViewById(R.id.imBack);
		btnPlot = (Button) findViewById(R.id.btnPlot);
		btnStills = (Button) findViewById(R.id.btnStills);
		btnPeopleCritic = (Button) findViewById(R.id.btnPeopleCritic);
		btnProfessionalCritic = (Button) findViewById(R.id.btnProfessionalCritic);
		svPlot = (ScrollView) findViewById(R.id.svPlot);
		lvStills = (LinearLayout) findViewById(R.id.lvStills);
		lvPeopleCritic = (LinearLayout) findViewById(R.id.lvPeopleCritic);
		lvProfessionalCritic = (LinearLayout) findViewById(R.id.lvProfessionalCritic);
		pullToRefreshListView1 = (PullToRefreshListView) findViewById(R.id.listPeopleCritic);
		pullToRefreshListView2 = (PullToRefreshListView) findViewById(R.id.listProfessionalCritic);
		listProfessionalCritic = pullToRefreshListView2.getRefreshableView();
		listPeopleCritic = pullToRefreshListView1.getRefreshableView();
		gvStills = (GridView) findViewById(R.id.gvStills);
		btnWrite = (Button) findViewById(R.id.btnwrite);
		movie_detail_buy = (TextView) findViewById(R.id.movie_detail_buy);

		//即将上映影片详情，把“购座位票”、“大众影评”和“专业影评”隐藏掉
		if(!canBuy){
			movie_detail_buy.setVisibility(View.GONE);
			btnPeopleCritic.setVisibility(View.INVISIBLE);
			btnProfessionalCritic.setVisibility(View.INVISIBLE);
		}
		
		movie_detail_buy.setOnClickListener(this);
		saveBtn.setOnClickListener(this);
		shareBtn.setOnClickListener(this);
		imBack.setOnClickListener(this);
		btnPlot.setOnClickListener(this);
		btnPeopleCritic.setOnClickListener(this);
		btnStills.setOnClickListener(this);
		btnProfessionalCritic.setOnClickListener(this);
		ivPlay.setOnClickListener(this);
		btnWrite.setOnClickListener(this);
		gvStills.setOnItemClickListener(this);
		
		try {

			/**
			 * 已更改 if (mMovie.director == null || mMovie.director.equals("") ||
			 * mMovie.director.equals("null")) { tvDirector.setText("暂无信息!"); }
			 * else { tvDirector.setText(mMovie.director); } // 主演显示 if
			 * (mMovie.main_actor == null || mMovie.main_actor.equals("") ||
			 * mMovie.main_actor.equals("null")) { tvStarring.setText("暂无信息!");
			 * } else { tvStarring.setText(mMovie.main_actor); }
			 * tvMovieType.setText(mMovie.type);
			 * tvMovieTime.setText(mMovie.country + " " + mMovie.release_date +
			 * " 上映"); tvInfo.setText(mMovie.introduce);
			 */

			// if (mMovie.upcomming.equals("0")) {
			// // TODO 将购买按钮显示 此类型属于热映电影 可购买
			// } else {
			// // TODO 将购买按钮隐藏 此类型属于即将上映电影 不可购买
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setListener1() {
		pullToRefreshListView1.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh() {
				System.out.println("onPullDownToRefresh     ");
				// comentId = "0";
				if (getCommentInfoTask != null) {
					getCommentInfoTask.cancel(true);
				}
				getCommentInfoTask = new GetCommentInfoTask(ZSQMoiveDetailActivity.this,
						MovieApplication.DOWN);
				getCommentInfoTask.execute("0", currentCommentCount + "");
				// pullToRefreshListView1.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh() {
				System.out.println("onPullUpToRefresh");
				try {
					if (Integer.parseInt(commentInfo.total) > currentCommentCount) {
						// comentId =
						// commentInfo.comments.get(commentInfo.comments.size()
						// - 1).c_id;
						if (getCommentInfoTask != null) {
							getCommentInfoTask.cancel(true);
						}
						getCommentInfoTask = new GetCommentInfoTask(ZSQMoiveDetailActivity.this,
								MovieApplication.UP);
						getCommentInfoTask.execute("0", (currentCommentCount + 10) + "");
					} else {
						ToastAlone.makeText(ZSQMoiveDetailActivity.this, "已加载全部评论",
								Toast.LENGTH_SHORT).show();
						pullToRefreshListView1.onRefreshComplete();
					}
				} catch (Exception e) {
					e.printStackTrace();
					pullToRefreshListView1.onRefreshComplete();
				}

			}

		});
	}

	private void setListener2() {
		pullToRefreshListView2.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh() {
				System.out.println("onPullDownToRefresh     ");
				// comentId = "0";
				if (getPrefessionInfoTask != null) {
					getPrefessionInfoTask.cancel(true);
				}
				getPrefessionInfoTask = new GetPrefessionInfoTask(ZSQMoiveDetailActivity.this,
						MovieApplication.DOWN);
				getPrefessionInfoTask.execute("0", currentProfessionCount + "");
				// pullToRefreshListView1.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh() {
				System.out.println("onPullUpToRefresh");
				try {
					if (Integer.parseInt(prefessionInfo.total) > currentProfessionCount) {
						// comentId =
						// commentInfo.comments.get(commentInfo.comments.size()
						// - 1).c_id;
						if (getPrefessionInfoTask != null) {
							getPrefessionInfoTask.cancel(true);
						}
						getPrefessionInfoTask = new GetPrefessionInfoTask(
								ZSQMoiveDetailActivity.this, MovieApplication.UP);
						getPrefessionInfoTask.execute("0", (currentProfessionCount + 10) + "");
					} else {
						ToastAlone.makeText(ZSQMoiveDetailActivity.this, "已加载全部评论",
								Toast.LENGTH_SHORT).show();
						pullToRefreshListView2.onRefreshComplete();
					}
				} catch (Exception e) {
					e.printStackTrace();
					pullToRefreshListView2.onRefreshComplete();
				}

			}

		});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.gvStills:
			if (mMovieInfo != null) {
				Intent in = new Intent(ZSQMoiveDetailActivity.this, MoviePicsAct.class);
				in.putExtra(
						"cover_image_url",
						mMovieInfo.getMovie().getClient_tidbits_url().get(arg2).client_tidbits_url_large);
				in.putExtra("tidbitsUrlList", mMovieInfo.getMovie().getClient_tidbits_url());
				in.putExtra("position", arg2);
				startActivity(in);
			}
			break;

		default:
			break;
		}

	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.imBack:
			finish();
			break;
		case R.id.btnStills:// 剧照按钮
			setButtonStatus();
			setViewGone();
			btnStills.setBackgroundResource(R.drawable.zsq_btn_pressed);
			btnStills.setTextColor(getResources().getColor(R.color.blue_top));
			lvStills.setVisibility(View.VISIBLE);
			if (stillsAdapter == null) {
				stillsAdapter = new ZSQStillsAdapter(ZSQMoiveDetailActivity.this);
				gvStills.setAdapter(stillsAdapter);
				if (mMovieInfo != null) {
					stillsAdapter.setData(mMovieInfo.getMovie().getClient_tidbits_url());
				}

				/**
				 * 已更新 stillsAdapter.setData(mMovie.client_tidbits_url);
				 */
			} else {

			}
			break;
		case R.id.btnPlot:
			setButtonStatus();
			setViewGone();
			btnPlot.setBackgroundResource(R.drawable.zsq_btn_pressed);
			btnPlot.setTextColor(getResources().getColor(R.color.blue_top));
			svPlot.setVisibility(View.VISIBLE);
			break;
		case R.id.btnPeopleCritic:
			setButtonStatus();
			setViewGone();
			btnPeopleCritic.setBackgroundResource(R.drawable.zsq_btn_pressed);
			btnPeopleCritic.setTextColor(getResources().getColor(R.color.blue_top));
			lvPeopleCritic.setVisibility(View.VISIBLE);
			// if (peopleCriticAdapter == null) {
			// peopleCriticAdapter = new
			// ZSQPeopleCriticAdapter(ZSQMoiveDetailActivity.this);
			// listPeopleCritic.setAdapter(peopleCriticAdapter);
			if (commentInfo == null) {
				if (getCommentInfoTask != null) {
					getCommentInfoTask.cancel(true);
				}
				getCommentInfoTask = new GetCommentInfoTask(this, MovieApplication.UP);
				getCommentInfoTask.execute("0", "10");
			}
			// }
			break;
		case R.id.btnProfessionalCritic:
			setButtonStatus();
			setViewGone();
			btnProfessionalCritic.setBackgroundResource(R.drawable.zsq_btn_pressed);
			btnProfessionalCritic.setTextColor(getResources().getColor(R.color.blue_top));
			lvProfessionalCritic.setVisibility(View.VISIBLE);

			if (prefessionInfo == null) {
				if (getPrefessionInfoTask != null) {
					getPrefessionInfoTask.cancel(true);
				}
				getPrefessionInfoTask = new GetPrefessionInfoTask(this, MovieApplication.UP);
				getPrefessionInfoTask.execute("0", "10");
			}

			break;
		case R.id.saveBtn:
			setSaveStatus();
			break;
		case R.id.shareBtn:
			openDialog();
			break;
		case R.id.ivPlay:

			if (mMovieInfo != null) {
				try {
					Intent it = new Intent(Intent.ACTION_VIEW);
					it.setDataAndType(Uri.parse(mMovieInfo.getMovie().getTrailersIphone()),
							"video/*");
					startActivity(it);
				} catch (Exception e) {
					 intent = new Intent();
					 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					 intent.putExtra("url", mMovieInfo.getMovie().getTrailersIphone());
					 intent.setClass(this, VideoPlayerActivity.class);
					 startActivity(intent);
				}
			}
			/**
			 * 已更新 try{ Intent it = new Intent(Intent.ACTION_VIEW);
			 * it.setDataAndType(Uri.parse(mMovie.trailersIphone), "video/mp4");
			 * startActivity(it); }catch(Exception e){ e.printStackTrace(); }
			 */

			break;
		case R.id.btnwrite:
			if (!shareP.getUserName().equals("")) {
				intent = new Intent(this, WritePinLunAct.class);
				intent.putExtra("m_id", m_id);
				intent.putExtra("upcomming", upcomming);
				intent.putExtra("uid", shareP.getUid()[0]);
				intent.putExtra("token", shareP.getUid()[1]);
				startActivity(intent);
			} else {
				intent = new Intent(this, UserLoginAct.class);
				startActivity(intent);
			}
			break;
		case R.id.movie_detail_buy:
			if (BuyTicketNewAct.buyTicketNewAct != null) {
				BuyTicketNewAct.buyTicketNewAct.finish();
			}
			if (mMovieInfo != null) {
				Intent in = new Intent(ZSQMoiveDetailActivity.this, BuyTicketNewAct.class);
				in.putExtra("m_id", m_id);
				in.putExtra("m_name", mMovieInfo.getMovie().getName());
				// intent.putExtra("c_id", "");
				in.putExtra("rate", mMovieInfo.getMovie().getRate());
				in.putExtra("movies", AppConstants.movieInfo.movies);
				startActivity(in);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 从影院进入，获取对应的影片数据
	 * 
	 * @param m_id
	 */
	private void getMovie(String m_id) {
		try {
			for (int i = 0; i < AppConstants.movieInfo.movies.size(); i++) {
				if (AppConstants.movieInfo.movies.get(i).m_id.equals(m_id)) {
					// mMovie = AppConstants.movieInfo.movies.get(i);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSaveStatus() {
		MovieSaveDao movieSaveDao = new MovieSaveDao(this);
		if (mMovieInfo != null && !movieSaveDao.isContains(mMovieInfo.getMovie().getName())) {
			SaveMovieNew saveMovieNew = new SaveMovieNew(m_id, mMovieInfo.getMovie().getName(),
					mMovieInfo.getMovie().getClient_placard_url2(), upcomming, mMovieInfo
							.getMovie().getRelease_date());
			saveBtn.setImageResource(R.drawable.yishoucang);
			movieSaveDao.saveMovie(saveMovieNew);
			ToastAlone.makeText(this, R.string.saveOK, Toast.LENGTH_SHORT).show();
		} else {
			try {
				movieSaveDao.deleteMovie(mMovieInfo.getMovie().getName());
				saveBtn.setImageResource(R.drawable.shoucang_selector);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void getSaveStatus() {
		MovieSaveDao movieSaveDao = new MovieSaveDao(this);
		if (movieSaveDao.isContains(mMovieInfo.getMovie().getName())) {
			saveBtn.setImageResource(R.drawable.yishoucang);
		} else {
			saveBtn.setImageResource(R.drawable.shoucang_selector);
		}
	}

	private void setViewGone() {
		svPlot.setVisibility(View.GONE);
		lvPeopleCritic.setVisibility(View.GONE);
		lvStills.setVisibility(View.GONE);
		lvProfessionalCritic.setVisibility(View.GONE);
	}

	private void setButtonStatus() {
		btnPlot.setBackgroundResource(R.drawable.zsq_btn_universal);
		btnPlot.setTextColor(getResources().getColor(R.color.light_gray));
		btnPeopleCritic.setBackgroundResource(R.drawable.zsq_btn_universal);
		btnPeopleCritic.setTextColor(getResources().getColor(R.color.light_gray));
		btnStills.setBackgroundResource(R.drawable.zsq_btn_universal);
		btnStills.setTextColor(getResources().getColor(R.color.light_gray));
		btnProfessionalCritic.setBackgroundResource(R.drawable.zsq_btn_universal);
		btnProfessionalCritic.setTextColor(getResources().getColor(R.color.light_gray));
	}

	private AlertDialog mAlertDialog = null;
	private String items[] = null;

	private void openDialog() {
		items = new String[] { "新浪微博分享", "短信分享", "邮件分享", "取消" };
		mAlertDialog = new AlertDialog.Builder(this).setTitle("分享影片")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String introduce = "";
						if (mMovieInfo.getMovie().getIntroduce() != null) {
							introduce = mMovieInfo.getMovie().getIntroduce();
						}
						switch (which) {
						case 0:
							Intent i = new Intent(ZSQMoiveDetailActivity.this, SinaShareAct.class);
							i.putExtra("isFromMovieDetailActivity", true);
							i.putExtra("name", mMovieInfo.getMovie().getName());
							i.putExtra("introduce", introduce);
							startActivity(i);
							mAlertDialog.dismiss();
							break;
						case 1:
							Intent intent = new Intent(android.content.Intent.ACTION_SENDTO);
							Uri smsToUri = Uri.parse("smsto:");
							intent.setData(smsToUri);
							if (!introduce.equals("") && !introduce.equals("null"))
								intent.putExtra("sms_body", "电影：" + mMovieInfo.getMovie().getName()
										+ " ， " + introduce);
							try {
								startActivity(intent);
							} catch (Exception e) {
								startActivity(Intent.createChooser(intent, "选择短信程序"));
							}
							mAlertDialog.dismiss();
							break;
						case 2:
							Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
							emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							emailIntent.setType("plain/text");
							emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "");
							if (!introduce.equals("") && !introduce.equals("null")) {
								emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
										mMovieInfo.getMovie().getName());
								emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, introduce);
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
		mAlertDialog.show();
	}

	private void showStills() {
		if (mMovieInfo != null && URLUtil.isHttpUrl(mMovieInfo.getMovie().getClient_placard_url2())) {
			ivStills.setTag(mMovieInfo.getMovie().getClient_placard_url2());
			Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(this,
					mMovieInfo.getMovie().getClient_placard_url2(), new ImageCallback() {

						@Override
						public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
							String url = (String) ivStills.getTag();
							if (url.equals(bitmapUrl)) {
								ivStills.setBackgroundColor(0x00000000);
								ivStills.setImageBitmap(bitmap);
								if (mMovieInfo.getMovie().getTrailersAndroid() != null) {
									ivPlay.setVisibility(View.VISIBLE);
								}
							}
						}
					}, AsyncImageLoader.CACHE_TYPE_SD, false);
			if (bitmap != null) {
				ivStills.setBackgroundColor(0x00000000);
				ivStills.setImageBitmap(bitmap);
				if (mMovieInfo != null && mMovieInfo.getMovie() != null
						&& mMovieInfo.getMovie().getTrailersAndroid() != null) {
					ivPlay.setVisibility(View.VISIBLE);
				}
			}
		}

	}

	class GetCommentInfoTask extends MovieAsyncTask<String, String, CommentInfo> {
		private String mType;

		public GetCommentInfoTask(Activity activity, String type) {
			super(activity, null, true, true, true);
			this.mType = type;
		}

		@Override
		protected CommentInfo doInBackground(String... params) {
			try {
				commentInfo = commentLib
						.getCommentsByMovieId(m_id, params[0], params[1], upcomming);
				// if (commentInfo.comments != null) {
				// if (MovieApplication.DOWN.equals(mType)) {
				// commentList.clear();
				// commentList.addAll(commentInfo.comments);
				// } else if (MovieApplication.UP.equals(mType)) {
				// commentList.addAll(commentInfo.comments);
				// }
				// }

			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return commentInfo;
		}

		@Override
		protected void onPostExecute(CommentInfo result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pullToRefreshListView1.onRefreshComplete();
			if (result != null && result.isSuccess()) {
				tvPeopleCriticNum.setText("共" + result.total + "条大众影评");
				if (MovieApplication.UP.equals(mType)) {
					currentCommentCount += 10;
				}
				try {
//					if(result.comments == null || result.comments.size() == 0) {
//						new MessageDialog(ZSQMoiveDetailActivity.this, result.result.message, false);
//					} else {
//						peopleCriticAdapter.setData(result.comments);
//					}
					if(result.comments != null && result.comments.size() > 0) {
						peopleCriticAdapter.setData(result.comments);
					} else {
						MessageDialog.getInstance().setData(ZSQMoiveDetailActivity.this, result.result.message, false);
					}
//					if (result.comments == null || result.comments.size() == 0) {
//						ToastAlone.showToast(ZSQMoiveDetailActivity.this, "暂无评论",
//								Toast.LENGTH_SHORT).show();
//					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
//				ToastAlone.showToast(ZSQMoiveDetailActivity.this, "数据加载失败", Toast.LENGTH_SHORT)
//						.show();
				MessageDialog.getInstance().setData(ZSQMoiveDetailActivity.this, R.string.data_failed_to_load, false);
			}
			getCommentInfoTask = null;
		}
	}

	private class GetMovieInfoTask extends MovieAsyncTask<String, String, MovieInfo> {

		public GetMovieInfoTask(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected MovieInfo doInBackground(String... params) {
			try {
				lib = MovieLib.getInstance(ZSQMoiveDetailActivity.this);
				mMovieInfo = lib.getMoiveDetail(upcomming, m_id);
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mMovieInfo;
		}

		@Override
		protected void onPostExecute(MovieInfo result) {
			super.onPostExecute(result);
			if (result != null && result.isSuccess()) {
			    //查询本地数据库是否收藏
			    getSaveStatus();
			    
				// 显示
				tvMovieName.setText(result.getMovie().getName());
				rBar.setRating(Float.parseFloat(result.getMovie().getRate()));
				tvScore.setText(result.getMovie().getRate() + "分");
				// 导演显示
				if (result.getMovie().getDirector() == null
						|| result.getMovie().getDirector().equals("")
						|| result.getMovie().getDirector().equals("null")) {
					tvDirector.setText("暂无信息!");
				} else {
					tvDirector.setText(result.getMovie().getDirector());
				}
				// 主演显示
				if (result.getMovie().getMain_actor() == null
						|| result.getMovie().getMain_actor().equals("")
						|| result.getMovie().getMain_actor().equals("null")) {
					tvStarring.setText("暂无信息!");
				} else {
					tvStarring.setText(result.getMovie().getMain_actor());
				}
				tvMovieType.setText(result.getMovie().getType());
				tvMovieTime.setText(result.getMovie().getCountry() + " "
						+ mMovieInfo.getMovie().getRelease_date() + " 上映");
				tvInfo.setText(result.getMovie().getIntroduce());
				// 显示海报
				showStills();
				if (result.getMovie().getUpcomming().equals("0")) {
					movie_detail_buy.setVisibility(View.VISIBLE);
				} else {
					movie_detail_buy.setVisibility(View.GONE);
				}
			} else {
				ToastAlone.showToast(ZSQMoiveDetailActivity.this, "数据加载失败", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

	class GetPrefessionInfoTask extends MovieAsyncTask<String, String, PrefessionInfo> {
		private String mType;

		public GetPrefessionInfoTask(Activity activity, String type) {
			super(activity, null, true, true, true);
			this.mType = type;
		}

		@Override
		protected PrefessionInfo doInBackground(String... params) {
			try {
				prefessionInfo = commentLib.getProfessionCommentsById(m_id, params[0], params[1],
						upcomming);

			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return prefessionInfo;
		}

		@Override
		protected void onPostExecute(PrefessionInfo result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pullToRefreshListView2.onRefreshComplete();
			if (result != null && result.isSuccess()) {
				if (MovieApplication.UP.equals(mType)) {
					currentProfessionCount += 10;
				}
				try {
					if(result.professions != null && result.professions.size() > 0) {
						professionalCriticAdapter.setData(result.professions);
					} else {
						MessageDialog.getInstance().setData(ZSQMoiveDetailActivity.this, result.result.message, false);
					}
//					if (result.professions.size() == 0) {
//						ToastAlone.showToast(ZSQMoiveDetailActivity.this, "暂无评论",
//								Toast.LENGTH_SHORT).show();
//					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
//				ToastAlone.showToast(ZSQMoiveDetailActivity.this, "数据加载失败", Toast.LENGTH_SHORT)
//						.show();
				MessageDialog.getInstance().setData(ZSQMoiveDetailActivity.this, R.string.data_failed_to_load, false);
			}
			getCommentInfoTask = null;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		zSQMoiveDetailActivity = null;
	}

}
