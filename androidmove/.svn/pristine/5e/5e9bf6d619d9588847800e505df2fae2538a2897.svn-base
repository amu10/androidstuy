package com.szcmcc.movie.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.URLUtil;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.CinemaPrepareMovie;
import com.szcmcc.movie.bean.Movie;
import com.szcmcc.movie.bean.MovieCinema;
import com.szcmcc.movie.bean.MovieCinemaList;
import com.szcmcc.movie.cache.AppConstants;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.storage.BaseDBUtil;
import com.szcmcc.movie.util.PublicUtils;
import com.szcmcc.movie.view.BuyTicketExpandableChildView;
import com.szcmcc.movie.view.MarqueeTextView;
import com.szcmcc.movie.view.MessageDialog;
import com.szcmcc.movie.view.ToastAlone;

public class BuyTicketNewAct extends BaseActivity {

	private LinearLayout title_linear;
	private HorizontalScrollView horizontalscrollview;
	private LinearLayout scrollview_linear;
	private LinearLayout horizontalscrollview_layout;
	private RatingBar ratingBar_score = null;
	private TextView time_today, time_tomorrow, textView_score;
	private MarqueeTextView m_nameTV;
	private ImageView title_arrow;
	private String currentDate;
	private MovieCinemaList movieCinema = null;
	private CinemaPrepareMovie cinemaPrepareMovie = null;
	private String c_id = "", m_id = "", m_name = "", rate = "";
	private ArrayList<Movie> movie_list;
	private ArrayList<Movie> movie_list_hot;
	private String today, tomorrow;
	private static int currentImagePosition = -1;
	private com.szcmcc.movie.view.YScrollView scrollview_parent;
	private TextView movie_detail_button;
	private LinearLayout listTitle;
	private TextView listTitleQuyuName, listTitleCinemaName;
	public static BuyTicketNewAct buyTicketNewAct;
	private int quyuHeight = 0, cinemaHeight = 0;
	private int quyuPosition = 0, cinemaPosition = 0;
	// private int quyuHeightSum = 0, cinemaHeightSum = 0;
	private String quyuNameStr = "", cinameNameStr = "";

	// GestureDetector myGesture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		buyTicketNewAct = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buyticketact_new);
		initZoneSubSlected();
		initView();
		onClick();
		// if (!isMoviesLoadAll) {
		new GetMovieInfoTask(this).execute();
		// }
	}

	private void initZoneSubSlected() {
		movie_list_hot = new ArrayList<Movie>();
		Intent in = getIntent();
		if (in.getExtras() != null) {
			// c_id = in.getExtras().getString("c_id");
			m_id = in.getExtras().getString("m_id");
			m_name = in.getExtras().getString("m_name");
			movie_list = (ArrayList<Movie>) in.getExtras().getSerializable("movies");
			rate = in.getExtras().getString("rate");
			try {
				for (int i = 0; i < movie_list.size(); i++) {
					if ("0".equals(movie_list.get(i).upcomming)) {
						movie_list_hot.add(movie_list.get(i));
					}
				}
				for (int i = 0; i < movie_list_hot.size(); i++) {
					if (m_id.equals(movie_list_hot.get(i).m_id)) {
						currentImagePosition = i;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void initView() {
		title_linear = (LinearLayout) findViewById(R.id.buyticketact_title);
		ratingBar_score = (RatingBar) findViewById(R.id.ratingBar_score);
		m_nameTV = (MarqueeTextView) findViewById(R.id.m_name);
		horizontalscrollview = (HorizontalScrollView) findViewById(R.id.horizontalscrollview);
		horizontalscrollview_layout = (LinearLayout) findViewById(R.id.horizontalscrollview_layout);
		time_today = (TextView) findViewById(R.id.time_today);
		time_tomorrow = (TextView) findViewById(R.id.time_tomorrow);
		title_arrow = (ImageView) findViewById(R.id.title_arrow);
		textView_score = (TextView) findViewById(R.id.textView_score);
		scrollview_linear = (LinearLayout) findViewById(R.id.scrollview_linear);
		scrollview_parent = (com.szcmcc.movie.view.YScrollView) findViewById(R.id.scrollview_parent);
		movie_detail_button = (TextView) findViewById(R.id.movie_detail_button);
		listTitle = (LinearLayout) findViewById(R.id.listTitle);
		listTitleQuyuName = (TextView) findViewById(R.id.listTitleQuyuName);
		listTitleCinemaName = (TextView) findViewById(R.id.listTitleCinemaName);
		setTitle(m_name, rate);
		today = PublicUtils.getDateFormat().split(" ")[0];
		tomorrow = PublicUtils.getNextDayFormat(today);
		time_today.setText(today + "(今)");
		time_today.setTextColor(Color.rgb(33, 179, 252));
		time_today.setBackgroundResource(R.drawable.buyticket_time);
		time_tomorrow.setText(tomorrow + "(明)");
		time_tomorrow.setVisibility(View.INVISIBLE);
		// 默认选中当天
		currentDate = today;
		quyuList = new ArrayList<MovieCinemaList.MovieCinemaListInner>();
		cList = new ArrayList<ArrayList<MovieCinema>>();
	}

	private void setTitle(String m_name, String rate) {
		m_nameTV.setText(m_name);
		if (!TextUtils.isEmpty(rate)) {
			ratingBar_score.setRating(Float.parseFloat(rate));
			textView_score.setText(rate);
		}
	}

	private void onClick() {
		title_linear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (View.GONE == horizontalscrollview.getVisibility()) {
					horizontalscrollview.setVisibility(View.VISIBLE);
					title_arrow.setImageResource(R.drawable.arrow_up);
					addImageView();
				} else if (View.VISIBLE == horizontalscrollview.getVisibility()) {
					horizontalscrollview.setVisibility(View.GONE);
					title_arrow.setImageResource(R.drawable.arrow_down);
				}
			}
		});

		time_today.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null == v.getBackground()) {
					time_today.setBackgroundResource(R.drawable.buyticket_time);
					time_tomorrow.setBackgroundDrawable(null);
					time_today.setTextColor(Color.rgb(33, 179, 252));
					time_tomorrow.setTextColor(Color.rgb(255, 255, 255));
					currentDate = today;
					listTitle.setVisibility(View.GONE);
					// refreshData();
					if (0 != scrollview_linear.getChildCount()) {
						scrollview_linear.removeAllViews();
					}
					getData();
				}
			}
		});

		time_tomorrow.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null == v.getBackground()) {
					time_tomorrow.setBackgroundResource(R.drawable.buyticket_time);
					time_today.setBackgroundDrawable(null);
					time_today.setTextColor(Color.rgb(255, 255, 255));
					time_tomorrow.setTextColor(Color.rgb(33, 179, 252));
					currentDate = tomorrow;
					listTitle.setVisibility(View.GONE);
					// refreshData();
					if (0 != scrollview_linear.getChildCount()) {
						scrollview_linear.removeAllViews();
					}
					getData();
				}
			}
		});

		scrollview_parent.setOnTouchListener(new OnTouchListener() {
			private int lastY = 0;
			private int touchEventId = -9983761;
			Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					View scroller = (View) msg.obj;
					if (msg.what == touchEventId) {
						if (lastY == scroller.getScrollY()) {
							handleStop(scroller);
						} else {
							handler.sendMessageDelayed(
									handler.obtainMessage(touchEventId, scroller), 5);
							lastY = scroller.getScrollY();
						}
						getScrollStatus(lastY);
					}
				}
			};

			private void handleStop(Object view) {
				ScrollView scroller = (ScrollView) view;
				distance = scroller.getScrollY();
				getScrollStatus(distance);
			}

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {

				case MotionEvent.ACTION_MOVE:
					distance = scrollview_parent.getScrollY();
					getScrollStatus(distance);
					break;
				case MotionEvent.ACTION_UP:
					// handler.sendMessageDelayed(handler.obtainMessage(1),
					// 200);
					handler.sendMessageDelayed(handler.obtainMessage(touchEventId, v), 5);
					break;
				}
				return false;
			}
		});
		movie_detail_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ZSQMoiveDetailActivity.zSQMoiveDetailActivity != null) {
					ZSQMoiveDetailActivity.zSQMoiveDetailActivity.finish();
				}
				try {
					Movie movie = movie_list_hot.get(currentImagePosition);
					Intent intent = new Intent(BuyTicketNewAct.this, ZSQMoiveDetailActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.putExtra("m_id", movie.m_id);
					startActivity(intent);
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		});

		listTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listTitle.setVisibility(View.GONE);
				try {
					for (int i = 0; i < quyuList.size(); i++) {
						((ImageView) ((LinearLayout) ((LinearLayout) ((LinearLayout) scrollview_linear
								.getChildAt(i)).findViewById(R.id.groupview)))
								.findViewById(R.id.groupview_arrow))
								.setImageResource(R.drawable.arrow_list_down);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				removeviewAll();
			}
		});
	}

	private void getScrollStatus(int distance) {
		if (distance >= (quyuPosition * quyuHeight)) {
			if (!quyuNameStr.equals("")) {

				listTitle.setVisibility(View.VISIBLE);
				listTitleQuyuName.setText(quyuNameStr);
				if (distance >= (quyuPosition * quyuHeight + cinemaPosition * cinemaHeight)) {
					listTitleCinemaName.setText(cinameNameStr);
				} else {
					listTitleCinemaName.setText("");
				}
			} else {
				listTitle.setVisibility(View.GONE);
			}
		} else {
			listTitleCinemaName.setText("");
			listTitle.setVisibility(View.GONE);
		}
	}

	public void setListTitle() {
		distance = scrollview_parent.getScrollY();
		if (distance > (quyuPosition * quyuHeight)) {
			if (!quyuNameStr.equals("")) {

				listTitle.setVisibility(View.VISIBLE);
				listTitleQuyuName.setText(quyuNameStr);
				if (distance > (quyuPosition * quyuHeight + cinemaPosition * cinemaHeight)) {
					listTitleCinemaName.setText(cinameNameStr);
				} else {
					listTitleCinemaName.setText("");
				}
			} else {
				listTitle.setVisibility(View.GONE);
			}
		} else {
			listTitle.setVisibility(View.GONE);
		}
	}

	int distance = 0;

	private void addImageView() {
		if (0 != horizontalscrollview_layout.getChildCount()) {
			return;
		}

		for (int i = 0; i < movie_list_hot.size(); i++) {
			// 只显示热映影片
			// if(!"0".equals(movie_list_hot.get(i).upcomming)) {
			// continue;
			// }
			final View view = LayoutInflater.from(this).inflate(R.layout.buyticket_gallery_image,
					horizontalscrollview_layout, false);
			final ImageView image = (ImageView) view.findViewById(R.id.gallery_image);
			horizontalscrollview_layout.addView(view);

			final int position = i;

			image.setImageBitmap(BaseDBUtil.readBitMap(this, R.drawable.loadinglist));
			if (movie_list_hot.get(i) != null
					&& URLUtil.isHttpUrl(movie_list_hot.get(i).client_placard_url2)) {
				image.setTag(movie_list_hot.get(i).client_placard_url2);
				Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(this,
						movie_list_hot.get(i).client_placard_url2, new ImageCallback() {

							@Override
							public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
								String url = (String) image.getTag();
								if (url.equals(bitmapUrl)) {
									image.setImageBitmap(bitmap);
								}
							}
						}, AsyncImageLoader.CACHE_TYPE_SD, false);
				if (bitmap != null) {
					image.setImageBitmap(bitmap);
				}
			}
			// 默认显示
			if (m_id.equals(movie_list_hot.get(i).m_id)) {
				currentImagePosition = i;
				view.setBackgroundResource(R.drawable.film_cover_red_bg);
			}
			// 影片图片的点击事件
			view.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (currentImagePosition == position) {
						return;
					}
					if (-1 != currentImagePosition) {
						horizontalscrollview_layout.getChildAt(currentImagePosition)
								.setBackgroundResource(R.drawable.buyticket_image);
					}
					m_id = movie_list_hot.get(position).m_id;
					setTitle(movie_list_hot.get(position).name, movie_list_hot.get(position).rate);
					new GetMovieInfoTask(BuyTicketNewAct.this).execute();
					if (0 != scrollview_linear.getChildCount()) {
						scrollview_linear.removeAllViews();
					}
					listTitle.setVisibility(View.GONE);
					view.setBackgroundResource(R.drawable.film_cover_red_bg);
					// view.setBackgroundResource(R.drawable.buyticket_image1);
					currentImagePosition = position;
				}
			});
		}
	}

	/** 记录选择影院功能里区域的列表 */
	private ArrayList<MovieCinemaList.MovieCinemaListInner> quyuList;
	// private ArrayList<String> quyuNameList = new ArrayList<String>();
	/** 记录选择影院功能里影院的列表 */
	private ArrayList<ArrayList<MovieCinema>> cList;

	/**
	 * 过滤出所有有该影片的影院
	 */
	private void getData() {
		quyuList.clear();
		cList.clear();
		String nowTime = PublicUtils.getDateFormat().split(" ")[1].substring(0, 5);
		try {
			boolean flag = true;
			boolean isFlag = true, isHasCurrentDay = true;
			for (int i = 0; i < movieCinema.mList.size(); i++) {
				ArrayList<MovieCinema> linshi_list = new ArrayList<MovieCinema>();
				if (movieCinema.mList.get(i).cinemas == null) {
					continue;
				}
				for (int j = 0; j < movieCinema.mList.get(i).cinemas.size(); j++) {

					for (int k = 0; k < cinemaPrepareMovie.list.size(); k++) {
						if (movieCinema.mList.get(i).cinemas.get(j).c_id
								.equals(cinemaPrepareMovie.list.get(k).c_id)) {

							linshi_list.add(movieCinema.mList.get(i).cinemas.get(j));
							flag = false;
							break;

						}
					}
				}
				if (flag == false) {
					ArrayList<MovieCinema> linshiListSeat = new ArrayList<MovieCinema>();
					// ArrayList<MovieCinema> linshiList1 = new
					// ArrayList<MovieCinema>();
					ArrayList<MovieCinema> linshiList2 = new ArrayList<MovieCinema>();
					// quyuList.add(movieCinema.mList.get(i));
					for (int j = 0; j < linshi_list.size(); j++) {
						isFlag = true;
						if (linshi_list.get(j).order_type.equals("0")
								|| linshi_list.get(j).order_type.equals("2")) {
							for (int k = 0; k < cinemaPrepareMovie.list.size(); k++) {
								if (!isFlag) {
									break;
								}
								if (linshi_list.get(j).c_id
										.equals(cinemaPrepareMovie.list.get(k).c_id)) {
									try {
										// System.out.println("cinemaPrepareMovie.list.get(k).list+++"
										// +
										// cinemaPrepareMovie.list.get(k).list);
										for (int m = 0; m < cinemaPrepareMovie.list.get(k).list
												.size(); m++) {
											if (cinemaPrepareMovie.list.get(k).list.get(m).day_time
													.equals(currentDate)
													&& !PublicUtils.isCompareTime(nowTime,
															cinemaPrepareMovie.list.get(k).list
																	.get(m).s_time)) {
												linshiListSeat.add(linshi_list.get(j));
												isFlag = false;
												break;
											}
										}
									} catch (NullPointerException e) {
										e.printStackTrace();
									}
								}
							}

						} else {
							// linshiList1.add(linshi_list.get(j));
						}
					}

					if (linshiListSeat.size() == 0) {
						flag = true;
						continue;
					}

					for (int m = 0; m < linshiListSeat.size(); m++) {
						if (!isHasCurrentDay) {
							break;
						}
						for (int n = 0; n < cinemaPrepareMovie.list.size(); n++) {
							if (!isHasCurrentDay) {
								break;
							}
							if (linshiListSeat.get(m).c_id
									.equals(cinemaPrepareMovie.list.get(n).c_id)) {
								if (cinemaPrepareMovie.list.get(n).list != null
										&& cinemaPrepareMovie.list.get(n).list.size() != 0) {

									for (int k = 0; k < cinemaPrepareMovie.list.get(n).list.size(); k++) {
										if (cinemaPrepareMovie.list.get(n).list.get(k).day_time
												.equals(currentDate)
												&& !PublicUtils
														.isCompareTime(nowTime,
																cinemaPrepareMovie.list.get(n).list
																		.get(k).s_time)) {
											quyuList.add(movieCinema.mList.get(i));
											isHasCurrentDay = false;
											break;
										}
									}
								}
							}
						}
					}
					if (isHasCurrentDay) {
						continue;
					}
					// quyuList.add(movieCinema.mList.get(i));
					linshiList2.addAll(linshiListSeat);
					// linshiList2.addAll(linshiList1);
					cList.add(linshiList2);
					isHasCurrentDay = true;
					flag = true;
				}
			}

			if (cinemaPrepareMovie.isShow != null && cinemaPrepareMovie.isShow.equals("1")) {
				time_tomorrow.setVisibility(View.VISIBLE);
			} else {
				time_tomorrow.setVisibility(View.INVISIBLE);
			}

			addView();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	Handler handler = new Handler();

	/**
	 * 添加一级目录
	 */
	private void addView() {
		if (0 != scrollview_linear.getChildCount()) {
			scrollview_linear.removeAllViews();
		}
		if (quyuList.size() == 0) {
//			ToastAlone.makeText(BuyTicketNewAct.this, "当前暂无排期", Toast.LENGTH_SHORT).show();
			MessageDialog.getInstance().setData(BuyTicketNewAct.this, R.string.no_waiting, false);
			return;
		}
		for (int i = 0; i < quyuList.size(); i++) {
			final View view = LayoutInflater.from(this).inflate(
					R.layout.add_buyticket_listview_parent_view_layout, scrollview_linear, false);
			final ImageView groupview_arrow = (ImageView) view.findViewById(R.id.groupview_arrow);
			TextView area = (TextView) view
					.findViewById(R.id.add_buyticket_listview_parent_view_layout_area);
			area.setText(quyuList.get(i).region_name);
			final LinearLayout childview2 = (LinearLayout) view.findViewById(R.id.childview2);
			final int position1 = i;

			view.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					quyuHeight = view.getHeight();
					if (0 == childview2.getChildCount()) {
						quyuNameStr = quyuList.get(position1).region_name;
						quyuPosition = position1;

						removeview2(position1);
						groupview_arrow.setImageResource(R.drawable.arrow_list_up);
						// 添加二级目录
						for (int j = 0; j < cList.get(position1).size(); j++) {
							BuyTicketExpandableChildView buyticketexpandablechildview = new BuyTicketExpandableChildView(
									BuyTicketNewAct.this);
							buyticketexpandablechildview.setData(cList.get(position1).get(j), m_id,
									cinemaPrepareMovie);
							buyticketexpandablechildview.setTag(j);
							childview2.addView(buyticketexpandablechildview);
						}
						// listTitle.setVisibility(View.GONE);
						listTitleCinemaName.setText(cinameNameStr);
						listTitleQuyuName.setText(quyuNameStr);
						handler.postDelayed(new Runnable() {

							@Override
							public void run() {
								distance = scrollview_parent.getScrollY();
								if (distance > (quyuPosition * quyuHeight)) {
									if (!quyuNameStr.equals("")) {

										listTitle.setVisibility(View.VISIBLE);
										listTitleQuyuName.setText(quyuNameStr);
										if (distance > (quyuPosition * quyuHeight + cinemaPosition
												* cinemaHeight)) {
											listTitleCinemaName.setText(cinameNameStr);
										} else {
											listTitleCinemaName.setText("");
										}
									} else {
										listTitle.setVisibility(View.GONE);
									}
								} else {
									listTitle.setVisibility(View.GONE);
								}
								// scrollview_parent.fullScroll(View.FOCUS_UP);
							}
						}, 100);
					} else {
						quyuPosition = 0;
						quyuNameStr = "";
						groupview_arrow.setImageResource(R.drawable.arrow_list_down);
						childview2.removeAllViews();
					}

				}
			});
			scrollview_linear.addView(view);

		}
		// quyuHeight = view.getHeight();

	}

	public void setCinemaHeight(int height) {
		this.cinemaHeight = height;
	}

	public void setCinemaPosition(int pos) {
		this.cinemaPosition = pos;
	}

	public void setCinameNameStr(String name) {
		this.cinameNameStr = name;
	}

	/**
	 * 得到当前选择日期
	 * 
	 * @return
	 */
	public String getCurrentDate() {
		return currentDate;
	}

	/**
	 * 通过影片id获取client_placard_url2
	 * 
	 * @param m_id
	 * @return
	 */
	public String getClient_placard_url2(String m_id) {
		for (int i = 0; i < movie_list_hot.size(); i++) {
			if (m_id.equals(movie_list_hot.get(i).m_id)) {
				return movie_list_hot.get(i).client_placard_url2;
			}
		}
		return null;
	}

	/**
	 * 刷新三级目录
	 */
	// private void refreshData() {
	// // 遍历寻找展开的一级目录
	// for (int i = 0; i < scrollview_linear.getChildCount(); i++) {
	// View view = scrollview_linear.getChildAt(i);
	// LinearLayout childview = (LinearLayout)
	// view.findViewById(R.id.childview2);
	// if (0 != childview.getChildCount()) {
	// // 遍历寻找展开的二级目录
	// for (int j = 0; j < childview.getChildCount(); j++) {
	// BuyTicketExpandableChildView childview2 = (BuyTicketExpandableChildView)
	// childview.getChildAt(j);
	// LinearLayout childview3 = (LinearLayout)
	// childview2.findViewById(R.id.bloew_linear);
	// childview2.currentDate = currentDate;
	// if (0 != childview3.getChildCount()) {
	// childview2.removeAllView();
	// if (currentDate.equals(today)) {
	// childview2.addChildView(childview2.byseat_list_today);
	// } else if (currentDate.equals(tomorrow)) {
	// childview2.addChildView(childview2.byseat_list_romorrow);
	// }
	// // 停止遍历二级目录
	// break;
	// }
	// }
	// // 停止遍历一级目录
	// break;
	// }
	// }
	// }

	/**
	 * 关闭非当前点击的一级页面(删除二级目录)
	 * 
	 * @param position1
	 *            当前点击一级目录索引
	 */
	private void removeview2(int position1) {
		for (int i = 0; i < quyuList.size(); i++) {
			if (i != position1) {
				((LinearLayout) ((LinearLayout) scrollview_linear.getChildAt(i))
						.findViewById(R.id.childview2)).removeAllViews();
				((ImageView) ((LinearLayout) ((LinearLayout) ((LinearLayout) scrollview_linear
						.getChildAt(i)).findViewById(R.id.groupview)))
						.findViewById(R.id.groupview_arrow))
						.setImageResource(R.drawable.arrow_list_down);
			}
		}
	}

	private void removeviewAll() {
		try {
			for (int i = 0; i < quyuList.size(); i++) {
				((LinearLayout) ((LinearLayout) scrollview_linear.getChildAt(i)).findViewById(R.id.childview2)).removeAllViews();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 关闭非当前点击的二级页面(删除三级目录)
	 * 
	 * @param position2
	 *            当前点击一二级目录索引
	 */
	public void removeView3(int position2) {
		for (int i = 0; i < quyuList.size(); i++) {
			LinearLayout view2 = (LinearLayout) ((LinearLayout) scrollview_linear.getChildAt(i))
					.findViewById(R.id.childview2);
			if (0 != view2.getChildCount()) {
				for (int j = 0; j < view2.getChildCount(); j++) {
					if (j != position2) {
						((LinearLayout) ((LinearLayout) view2.getChildAt(j))
								.findViewById(R.id.bloew_linear)).removeAllViews();
						((ImageView) ((LinearLayout) ((LinearLayout) (((LinearLayout) ((LinearLayout) view2
								.getChildAt(j)).findViewById(R.id.above_linear))
								.findViewById(R.id.above_fir))).findViewById(R.id.seat_ticket))
								.findViewById(R.id.childview_arrow))
								.setImageResource(R.drawable.arrow_list_down);
					}
				}
			}
		}
	}

	class GetMovieInfoTask extends MovieAsyncTask<String, String, MovieCinemaList> {
		public String exception = "";

		public GetMovieInfoTask(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected MovieCinemaList doInBackground(String... params) {

			try {
				// System.out.println("c_id====" + c_id);
				if (AppConstants.movieCinema == null) {
					movieCinema = lib.getCinema(BuyTicketNewAct.this);
					AppConstants.movieCinema = movieCinema;
				} else {
					movieCinema = AppConstants.movieCinema;
				}

				if (!m_id.equals("") && c_id.equals("")) {
					cinemaPrepareMovie = lib.getMoviesAndCouponByMovie(m_id);
				}
				if (!c_id.equals("") && !m_id.equals("")) {
					// System.out.println("noinitWheelc_id====" + c_id);
					if (cinemaPrepareMovie == null) {
						cinemaPrepareMovie = lib.getMoviesAndCouponByMovie(m_id); // 通过影片获取的影院当期
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
			} catch (NullPointerException e) {
				exception = "连接异常";

				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return movieCinema;
		}

		@Override
		protected void onPostExecute(MovieCinemaList result) {
			super.onPostExecute(result);
//			if (!exception.equals("")) {
//				Toast.makeText(BuyTicketNewAct.this, exception, Toast.LENGTH_SHORT).show();
//				finish();
//			}

			if (null == cinemaPrepareMovie) {
//				Toast.makeText(BuyTicketNewAct.this, "没有可供购票的影院", Toast.LENGTH_LONG).show();
				MessageDialog.getInstance().setData(BuyTicketNewAct.this, R.string.data_failed_to_load, false);
				return;
			}
			if(null == cinemaPrepareMovie.list || cinemaPrepareMovie.list.size() == 0) {
				MessageDialog.getInstance().setData(BuyTicketNewAct.this, cinemaPrepareMovie.result.message, false);
				return;
			}

			if (result != null && result.isSuccess()) {
				if (movieCinema.mList != null && movieCinema.mList.size() > 0) {
					getData();
				} else {
//					Toast.makeText(BuyTicketNewAct.this, "加载失败，请尝试重新加载！", Toast.LENGTH_LONG).show();
					MessageDialog.getInstance().setData(BuyTicketNewAct.this, result.result.message, false);
				}
			} else {
//				Toast.makeText(BuyTicketNewAct.this, "加载失败，请尝试重新加载！", Toast.LENGTH_LONG).show();
				MessageDialog.getInstance().setData(BuyTicketNewAct.this, R.string.data_failed_to_load, false);
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		buyTicketNewAct = null;
		movieCinema = null;
		cinemaPrepareMovie = null;
		movie_list.clear();
		movie_list_hot.clear();
		System.gc();
	}

}
