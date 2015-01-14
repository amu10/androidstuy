package com.szcmcc.movie.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.CinemaPrepare;
import com.szcmcc.movie.bean.CinemaPrepareInfo;
import com.szcmcc.movie.bean.CinemaPrepareMovie;
import com.szcmcc.movie.bean.MovieCinema;
import com.szcmcc.movie.bean.MovieCinemaList;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.storage.BaseDBUtil;
import com.szcmcc.movie.storage.SharedPreferencesUtil;
import com.szcmcc.movie.util.PublicUtils;
import com.szcmcc.movie.view.BuyTicketPrepareChildView;
import com.szcmcc.movie.view.MarqueeTextView;
import com.szcmcc.movie.view.MessageDialog;
import com.szcmcc.movie.view.ToastAlone;

public class CinemaPrepareAct extends BaseActivity {

	private TextView cNameTextView;
	private MarqueeTextView mNameTextView;
	private TextView cinemaDetailButton, buyDuihuanTicket;
	private LinearLayout image_layout;// 影片图片
	private ImageView title_arrow;
	private LinearLayout parentLinear, quyu_linear, cinema_linear;
	private RatingBar ratingBar_score;
	private TextView textView_score;
	private TextView time_today, time_tomorrow;
	private LinearLayout prepare_linear;
	private LinearLayout lvMovie;
	private String mId;
	private String upcomming;
	private LinearLayout buyticketact_date;
	private LinearLayout duihuanMessage;

	// /** 记录选择影院功能里区域的列表 */
	// private ArrayList<MovieCinemaList.MovieCinemaListInner> quyuList;
	// /** 记录选择影院功能里影院的列表 */
	// private ArrayList<ArrayList<MovieCinema>> cList;

	private MovieCinemaList movieCinemaList;
	private MovieCinema movieCinema;

	private String today = PublicUtils.getDateFormat().split(" ")[0];
	private String tomorrow = PublicUtils.getNextDayFormat(today);
	private String currentDate;
	SharedPreferencesUtil shareP;
	String c_id = "";
	String c_name = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_cinema_prepare);
		findView();
		intData();
		setListener();
	}

	private void findView() {
		lvMovie = (LinearLayout) findViewById(R.id.lvMovie);
		cNameTextView = (TextView) findViewById(R.id.cNameTextView);
		mNameTextView = (MarqueeTextView) findViewById(R.id.mNameTextView);
		cinemaDetailButton = (TextView) findViewById(R.id.cinemaDetailButton);
		buyDuihuanTicket = (TextView) findViewById(R.id.buyDuihuanTicket);
		image_layout = (LinearLayout) findViewById(R.id.image_layout);
		title_arrow = (ImageView) findViewById(R.id.title_arrow);
		parentLinear = (LinearLayout) findViewById(R.id.parentLinear);
		quyu_linear = (LinearLayout) findViewById(R.id.quyu_linear);
		cinema_linear = (LinearLayout) findViewById(R.id.cinema_linear);
		ratingBar_score = (RatingBar) findViewById(R.id.ratingBar_score);
		textView_score = (TextView) findViewById(R.id.textView_score);
		time_today = (TextView) findViewById(R.id.time_today);
		time_tomorrow = (TextView) findViewById(R.id.time_tomorrow);
		prepare_linear = (LinearLayout) findViewById(R.id.prepare_linear);
		currentDate = today;
		today = PublicUtils.getDateFormat().split(" ")[0];
		tomorrow = PublicUtils.getNextDayFormat(today);
		time_today.setText(today + "(今)");
		time_today.setTextColor(Color.rgb(33, 179, 252));
		time_today.setBackgroundResource(R.drawable.buyticket_time);
		time_tomorrow.setText(tomorrow + "(明)");
		time_tomorrow.setVisibility(View.INVISIBLE);
		shareP = SharedPreferencesUtil.getInstance(this);
		buyticketact_date = (LinearLayout) findViewById(R.id.buyticketact_date);
		duihuanMessage = (LinearLayout) findViewById(R.id.duihuanMessage);
	}

	private void intData() {
		Intent in = getIntent();
		if (in != null) {
			movieCinemaList = (MovieCinemaList) in.getExtras().getSerializable("movieCinemaList");
			movieCinema = (MovieCinema) in.getExtras().getSerializable("movieCinema");
			cNameTextView.setText(movieCinema.c_name);
			c_id = movieCinema.c_id;
			if (movieCinema.order_type.equals("0")) {
				buyDuihuanTicket.setVisibility(View.INVISIBLE);
				buyticketact_date.setVisibility(View.VISIBLE);
				prepare_linear.setVisibility(View.VISIBLE);
				duihuanMessage.setVisibility(View.GONE);
			} else {
				if (movieCinema.order_type.equals("1")) {
					buyticketact_date.setVisibility(View.INVISIBLE);
					prepare_linear.setVisibility(View.INVISIBLE);
					duihuanMessage.setVisibility(View.VISIBLE);
				} else {
					buyticketact_date.setVisibility(View.VISIBLE);
					prepare_linear.setVisibility(View.VISIBLE);
					duihuanMessage.setVisibility(View.GONE);
				}
				buyDuihuanTicket.setVisibility(View.VISIBLE);
			}

			if (movieCinemaList == null || movieCinemaList.mList == null) {
				ToastAlone.makeText(this, "当前影院暂无排期", Toast.LENGTH_SHORT).show();
				finish();
			}
			for (int i = 0; i < movieCinemaList.mList.size(); i++) {
				final int position = i;
				View view = LayoutInflater.from(this).inflate(R.layout.cinema_prepare_quyulist,
						null);
				final TextView quyuName = (TextView) view.findViewById(R.id.quyuName);
				final TextView quyuNameLine = (TextView) view.findViewById(R.id.quyuNameLine);
				quyuName.setText(movieCinemaList.mList.get(i).region_name);
				quyuNameLine.setVisibility(View.GONE);
				quyuName.setTextColor(Color.rgb(255, 255, 255));
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						for (int i = 0; i < quyu_linear.getChildCount(); i++) {
							if (i != position) {
								((TextView) (quyu_linear.getChildAt(i).findViewById(R.id.quyuName)))
										.setTextColor(Color.rgb(255, 255, 255));
								((TextView) (quyu_linear.getChildAt(i)
										.findViewById(R.id.quyuNameLine))).setVisibility(View.GONE);
							}
						}
						((TextView) (quyu_linear.getChildAt(position).findViewById(R.id.quyuName)))
								.setTextColor(Color.rgb(31, 136, 193));
						((TextView) (quyu_linear.getChildAt(position)
								.findViewById(R.id.quyuNameLine))).setVisibility(View.VISIBLE);
						cinema_linear.removeAllViews();
						initCinemaByPos(position);

					}
				});
				quyu_linear.addView(view);
			}
			boolean flag = true;
			for (int i = 0; i < movieCinemaList.mList.size(); i++) {
				if (!flag) {
					break;
				}
				for (int j = 0; j < movieCinemaList.mList.get(i).cinemas.size(); j++) {
					if (movieCinemaList.mList.get(i).cinemas.get(j).c_id.equals(movieCinema.c_id)) {
						flag = false;
						for (int m = 0; m < quyu_linear.getChildCount(); m++) {
							((TextView) (quyu_linear.getChildAt(i).findViewById(R.id.quyuName)))
									.setTextColor(Color.rgb(255, 255, 255));
							((TextView) (quyu_linear.getChildAt(i).findViewById(R.id.quyuNameLine)))
									.setVisibility(View.GONE);
						}
						((TextView) (quyu_linear.getChildAt(i).findViewById(R.id.quyuName)))
								.setTextColor(Color.rgb(31, 136, 193));
						((TextView) (quyu_linear.getChildAt(i).findViewById(R.id.quyuNameLine)))
								.setVisibility(View.VISIBLE);
						cinema_linear.removeAllViews();
						initCinemaByPos(i);
						for (int n = 0; n < cinema_linear.getChildCount(); n++) {
							((TextView) (cinema_linear.getChildAt(n).findViewById(R.id.cinemaName)))
									.setTextColor(Color.rgb(255, 255, 255));
						}
						((TextView) (cinema_linear.getChildAt(j).findViewById(R.id.cinemaName)))
								.setTextColor(Color.rgb(31, 136, 193));
						c_id = movieCinemaList.mList.get(i).cinemas.get(j).c_id;
						c_name = movieCinemaList.mList.get(i).cinemas.get(j).c_name;
						new GetCinemaPrepareInfoTask(CinemaPrepareAct.this).execute(c_id);
						break;
					}

				}
			}
		}

		time_today.setBackgroundResource(R.drawable.buyticket_time);
		time_today.setTextColor(Color.rgb(33, 179, 252));
		time_tomorrow.setBackgroundDrawable(null);
		time_tomorrow.setTextColor(Color.rgb(255, 255, 255));
	}

	private void setListener() {// 影片详情点击区域
		lvMovie.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CinemaPrepareAct.this, ZSQMoiveDetailActivity.class);
				intent.putExtra("m_id", mId);
				intent.putExtra("upcomming", upcomming);
				startActivity(intent);
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
					addMoviePaiqiView(cinemaPrepareInfo, currentImagePosition, currentDate);
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
					addMoviePaiqiView(cinemaPrepareInfo, currentImagePosition, currentDate);
				}
			}
		});

		cNameTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (parentLinear.getVisibility() == View.VISIBLE) {
					title_arrow.setBackgroundResource(R.drawable.arrow_down);
					parentLinear.setVisibility(View.GONE);
				} else {
					title_arrow.setBackgroundResource(R.drawable.arrow_up);
					parentLinear.setVisibility(View.VISIBLE);
				}
			}
		});

		buyDuihuanTicket.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!shareP.getUserName().equals("")) {
					ArrayList<MovieCinema.MovieCinemaInner> priceList = null;
					String companyId = "";
					String price = "";
					boolean flag = true;
					for (int i = 0; i < movieCinemaList.mList.size(); i++) {
						if (!flag) {
							break;
						}
						for (int j = 0; j < movieCinemaList.mList.get(i).cinemas.size(); j++) {
							if (c_id.equals(movieCinemaList.mList.get(i).cinemas.get(j).c_id)) {
								companyId = movieCinemaList.mList.get(i).cinemas.get(j).companyId;
								priceList = movieCinemaList.mList.get(i).cinemas.get(j).MovieCinemaInnerList;
								price = movieCinemaList.mList.get(i).cinemas.get(j).price;

								flag = false;
								break;
							}
						}
					}
					Intent intent = new Intent(CinemaPrepareAct.this, BuyTicketExchangeAct.class);
					intent.putExtra("c_id", c_id);
					intent.putExtra("companyId", companyId);
					intent.putExtra("c_name", c_name);
					intent.putExtra("price", price);
					// intent.putExtra("priceList", (Serializable) priceList);
					intent.putExtra("payphone", shareP.getUserName());

					startActivity(intent);
				} else {
					Intent intent = new Intent(CinemaPrepareAct.this, UserLoginAct.class);
					startActivity(intent);
				}
			}
		});

		cinemaDetailButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(CinemaPrepareAct.this, CinemaDesAct.class);
				in.putExtra("movieCinema", movieCinema);
				in.putExtra("c_id", c_id);
				startActivity(in);
			}
		});
	}

	private void initCinemaByPos(final int quyuPos) {
		try {
			for (int i = 0; i < movieCinemaList.mList.get(quyuPos).cinemas.size(); i++) {
				View view = LayoutInflater.from(this).inflate(R.layout.cinema_prepare_cinemalist,
						null);
				TextView cinemaName = (TextView) view.findViewById(R.id.cinemaName);
				cinemaName.setText(movieCinemaList.mList.get(quyuPos).cinemas.get(i).c_name);

				final int position = i;
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						for (int i = 0; i < cinema_linear.getChildCount(); i++) {
							if (i != position) {
								((TextView) (cinema_linear.getChildAt(i)
										.findViewById(R.id.cinemaName))).setTextColor(Color.rgb(
										255, 255, 255));
							}
						}
						((TextView) (cinema_linear.getChildAt(position)
								.findViewById(R.id.cinemaName))).setTextColor(Color.rgb(31, 136,
								193));
						c_id = movieCinemaList.mList.get(quyuPos).cinemas.get(position).c_id;
						c_name = movieCinemaList.mList.get(quyuPos).cinemas.get(position).c_name;
						movieCinema = movieCinemaList.mList.get(quyuPos).cinemas.get(position);
						if (movieCinema.order_type.equals("0")) {
							buyDuihuanTicket.setVisibility(View.INVISIBLE);
							buyticketact_date.setVisibility(View.VISIBLE);
							prepare_linear.setVisibility(View.VISIBLE);
							duihuanMessage.setVisibility(View.GONE);
						} else {
							if (movieCinema.order_type.equals("1")) {
								buyticketact_date.setVisibility(View.INVISIBLE);
								prepare_linear.setVisibility(View.INVISIBLE);
								duihuanMessage.setVisibility(View.VISIBLE);
							} else {
								buyticketact_date.setVisibility(View.VISIBLE);
								prepare_linear.setVisibility(View.VISIBLE);
								duihuanMessage.setVisibility(View.GONE);
							}
							buyDuihuanTicket.setVisibility(View.VISIBLE);
						}
						parentLinear.setVisibility(View.GONE);
						title_arrow.setBackgroundResource(R.drawable.arrow_down);
						new GetCinemaPrepareInfoTask(CinemaPrepareAct.this).execute(c_id);
						cNameTextView.setText(c_name);

					}
				});
				cinema_linear.addView(view);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	int currentImagePosition = -1;

	private void initMovieImage(final CinemaPrepareInfo cinemaPrepareInfo) {
		if (0 != image_layout.getChildCount()) {
			return;
		}
		if (cinemaPrepareInfo.movies == null) {
			return;
		}
		for (int i = 0; i < cinemaPrepareInfo.movies.size(); i++) {
			final View view = LayoutInflater.from(this).inflate(R.layout.buyticket_gallery_image,
					image_layout, false);
			final ImageView image = (ImageView) view.findViewById(R.id.gallery_image);
			image_layout.addView(view);

			final int position = i;

			image.setImageBitmap(BaseDBUtil.readBitMap(this, R.drawable.loadinglist));
			if (cinemaPrepareInfo.movies.get(i) != null
					&& URLUtil.isHttpUrl(cinemaPrepareInfo.movies.get(i).client_placard_url2)) {
				image.setTag(cinemaPrepareInfo.movies.get(i).client_placard_url2);
				Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(this,
						cinemaPrepareInfo.movies.get(i).client_placard_url2, new ImageCallback() {

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
			if (i == 0) {
				mId = cinemaPrepareInfo.movies.get(0).m_id;// 初始化时影片id
				upcomming = cinemaPrepareInfo.movies.get(0).upcomming;
				setTitle(cinemaPrepareInfo.movies.get(0).name, cinemaPrepareInfo.movies.get(0).rate);
				view.setBackgroundResource(R.drawable.film_cover_red_bg);
				currentImagePosition = 0;
				addMoviePaiqiView(cinemaPrepareInfo, currentImagePosition, currentDate);

			}
			// 影片图片的点击事件
			view.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (currentImagePosition == position) {
						return;
					}
					if (-1 != currentImagePosition) {
						image_layout.getChildAt(currentImagePosition).setBackgroundResource(
								R.drawable.buyticket_image);
					}
					mId = cinemaPrepareInfo.movies.get(position).m_id;// 点击更新影片id
					upcomming = cinemaPrepareInfo.movies.get(position).upcomming;
					setTitle(cinemaPrepareInfo.movies.get(position).name,
							cinemaPrepareInfo.movies.get(position).rate);
					view.setBackgroundResource(R.drawable.film_cover_red_bg);
					currentImagePosition = position;
					addMoviePaiqiView(cinemaPrepareInfo, currentImagePosition, currentDate);
				}
			});
		}
	}

	private void addMoviePaiqiView(CinemaPrepareInfo cinemaPrepareInfo, int pos, String currentDate) {

		prepare_linear.removeAllViews();
		try {
			if (cinemaPrepareInfo == null || cinemaPrepareInfo.movies == null
					|| null == cinemaPrepareInfo.movies.get(pos).daysSeat
					|| 0 == cinemaPrepareInfo.movies.get(pos).daysSeat.size()) {
				if (movieCinema.order_type.equals("1")) {

				} else {
					ToastAlone.makeText(this, "没有找到您所要查询的信息", Toast.LENGTH_SHORT).show();
				}
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ToastAlone.makeText(this, "没有找到您所要查询的信息", Toast.LENGTH_SHORT).show();
			return;
		}
		try {
			/** 当天影院排期信息 */
			ArrayList<CinemaPrepare> byseat_list_today;
			/** 第二天影院排期信息 */
			ArrayList<CinemaPrepare> byseat_list_romorrow;
			byseat_list_today = new ArrayList<CinemaPrepare>();
			byseat_list_romorrow = new ArrayList<CinemaPrepare>();
			for (int j = 0; j < cinemaPrepareInfo.movies.get(pos).daysSeat.size(); j++) {
				if (cinemaPrepareInfo.movies.get(pos).daysSeat.get(j).day_time.equals(today)) {
					byseat_list_today.add(cinemaPrepareInfo.movies.get(pos).daysSeat.get(j));
				}
				// 当前日期的下一天
				else if (cinemaPrepareInfo.movies.get(pos).daysSeat.get(j).day_time
						.equals(tomorrow)) {
					byseat_list_romorrow.add(cinemaPrepareInfo.movies.get(pos).daysSeat.get(j));
				}
			}
			byseat_list_today = getCinemaPrepare(byseat_list_today);
			byseat_list_romorrow = getCinemaPrepare(byseat_list_romorrow);
			String nowTime = PublicUtils.getDateFormat().split(" ")[1].substring(0, 5);

			if (today.equals(currentDate)) {
				for (int i = 0; i < byseat_list_today.size(); i++) {
					if (today.equals(currentDate)
							&& PublicUtils.isCompareTime(nowTime, byseat_list_today.get(i).s_time)) {
						continue;
					}
					BuyTicketPrepareChildView buyTicketPrepareChildView = new BuyTicketPrepareChildView(
							this, (LinearLayout) prepare_linear);
					buyTicketPrepareChildView.setTag(i);
					buyTicketPrepareChildView.setData(movieCinema, byseat_list_today.get(i),
							cinemaPrepareInfo.movies.get(pos).m_id);
					prepare_linear.addView(buyTicketPrepareChildView);
				}
			} else {
				for (int i = 0; i < byseat_list_romorrow.size(); i++) {
					if (today.equals(currentDate)
							&& PublicUtils.isCompareTime(nowTime,
									byseat_list_romorrow.get(i).s_time)) {
						continue;
					}
					BuyTicketPrepareChildView buyTicketPrepareChildView = new BuyTicketPrepareChildView(
							this, (LinearLayout) prepare_linear);
					buyTicketPrepareChildView.setTag(i);
					buyTicketPrepareChildView.setData(movieCinema, byseat_list_romorrow.get(i),
							cinemaPrepareInfo.movies.get(pos).m_id);
					prepare_linear.addView(buyTicketPrepareChildView);
				}
			}
			if (prepare_linear.getChildCount() == 0) {
				if (movieCinema.order_type.equals("1")) {

				} else {
					ToastAlone.makeText(this, "没有找到您所要查询的信息", Toast.LENGTH_SHORT).show();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (cinemaPrepareInfo.isShow != null && cinemaPrepareInfo.isShow.equals("1")) {
			time_tomorrow.setVisibility(View.VISIBLE);
		} else {
			time_tomorrow.setVisibility(View.INVISIBLE);
		}
	}

	public String getCurrentDate() {
		return currentDate;
	}

	private void setTitle(String m_name, String rate) {
		mNameTextView.setText(m_name);
		if (!TextUtils.isEmpty(rate)) {
			ratingBar_score.setRating(Float.parseFloat(rate));
			textView_score.setText(rate);
		}
		//没有影片时默认显示空
		if("-1".equals(rate)) {
			ratingBar_score.setRating(0);
			textView_score.setText("");
		}
	}

	/**
	 * 排序的影院排期信息（早--晚）
	 * 
	 * @param list
	 * @return
	 */
	private ArrayList<CinemaPrepare> getCinemaPrepare(ArrayList<CinemaPrepare> list) {
		if (null != list) {
			Comparator<CinemaPrepare> comparator;
			comparator = new TimeLowToHighComparator();
			Collections.sort(list, comparator);
		}
		return list;
	}

	public class TimeLowToHighComparator implements Comparator<CinemaPrepare> {
		@Override
		public int compare(CinemaPrepare itemBean1, CinemaPrepare itemBean2) {
			int time1 = 0;
			int time2 = 0;
			try {
				if (itemBean1.s_time != null && !"".equals(itemBean1.s_time)) {
					// meters1 = Double.parseDouble(itemBean1.meters);
					time1 = Integer.parseInt(itemBean1.s_time.split(":")[0]
							+ itemBean1.s_time.split(":")[1]);
				}
				if (itemBean2.s_time != null && !"".equals(itemBean2.s_time)) {
					time2 = Integer.parseInt(itemBean2.s_time.split(":")[0]
							+ itemBean2.s_time.split(":")[1]);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			if (time1 < time2) {
				return -1;
			} else if (time1 > time2) {
				return 1;
			} else {
				return 0;
			}
		}

	}

	CinemaPrepareInfo cinemaPrepareInfo;

	/**
	 * 通过影院获取该影院的所有当期
	 */
	class GetCinemaPrepareInfoTask extends MovieAsyncTask<String, String, CinemaPrepareInfo> {
		public String exception;

		CinemaPrepareMovie cinemaPrepareMovie;

		public GetCinemaPrepareInfoTask(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected CinemaPrepareInfo doInBackground(String... params) {
			cinemaPrepareInfo = null;
			try {
				if (!params[0].equals("")) {
					cinemaPrepareInfo = lib.getMoviesAndCouponByCinema(params[0]);
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
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return cinemaPrepareInfo;
		}

		@Override
		protected void onPostExecute(CinemaPrepareInfo result) {
			super.onPostExecute(result);
			if (result != null && result.isSuccess()) {
				if(result.movies != null && result.movies.size() > 0) {
					image_layout.removeAllViews();
					initMovieImage(result);
				} else {
					MessageDialog.getInstance().setData(CinemaPrepareAct.this, result.result.message, false);
					image_layout.removeAllViews();
					prepare_linear.removeAllViews();
					setTitle("", "-1");
					mId = "";
					upcomming = "";
				}
//				if (result.movies == null) {
//					ToastAlone.makeText(CinemaPrepareAct.this, "当前影院暂无排期", Toast.LENGTH_SHORT)
//							.show();
//					finish();
//				}
			} else {
				MessageDialog.getInstance().setData(CinemaPrepareAct.this, R.string.data_failed_to_load, false);
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
