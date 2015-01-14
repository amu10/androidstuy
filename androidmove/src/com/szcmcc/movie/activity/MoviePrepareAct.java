package com.szcmcc.movie.activity;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.CinemaPrepare;
import com.szcmcc.movie.bean.CinemaPrepareInfo;
import com.szcmcc.movie.bean.Movie;
import com.szcmcc.movie.bean.MovieCinema;
import com.szcmcc.movie.bean.MovieNewList;
import com.szcmcc.movie.bean.TimeGrid;
import com.szcmcc.movie.cache.AppConstants;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.storage.BaseDBUtil;
import com.szcmcc.movie.storage.SharedPreferencesUtil;
import com.szcmcc.movie.util.Log;
import com.szcmcc.movie.util.RoateUtil;

/**
 * 26-影院排期
 * 
 */
public class MoviePrepareAct extends BaseActivity {
	private Context mContext = MoviePrepareAct.this;
	// MovieInfo movieInfo = null;
	private boolean isMoviesLoadAll;
	private String c_id = "", c_name = "", m_id = "", address = "";
	private CinemaPrepareInfo cinemaPrepareInfo;
	private SimpleDateFormat sDateFormat1 = null, sTimeFormat1 = null;
	private LinearLayout rateLinear = null;
	private int currentListviewPosition = 0;
	private int currentDataPosition = 0;
	private ArrayList<String> dataList = new ArrayList<String>();
	private ArrayList<ArrayList<TimeGrid>> timeList = new ArrayList<ArrayList<TimeGrid>>();
	ArrayList<CinemaPrepare> currentList = new ArrayList<CinemaPrepare>();
	private TextView movies_jiantou;
	private String orderType = "";
	private TextView buyDuihuan;
	SharedPreferencesUtil shareP;

	// private ArrayList<String> timeGridItem
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movieprepareact);
		// movieprepareact
		// getBundle();
		shareP = SharedPreferencesUtil.getInstance(this);
		initTitleBar();
		sDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		sTimeFormat1 = new SimpleDateFormat("HH:mm");
		findViewByIds();
		onClick();

		initMoreMovies();
		if (!isMoviesLoadAll) {
			new GetMovieInfoTask(MoviePrepareAct.this).execute();
		}
		mMovieNewAdapter = new MoviePrepareAdapter(MoviePrepareAct.this);
		more_listview.setAdapter(mMovieNewAdapter);
	}

	// private void getBundle() {
	// Intent mIntent = getIntent();
	// mMovieCinema = (MovieCinema) mIntent
	// .getSerializableExtra("MovieCinema");
	// }

	private MovieCinema mMovieCinema = null;

	private void findViewByIds() {
		Intent in = getIntent();
		if (in.getExtras() != null) {
			c_id = in.getExtras().getString("c_id");
			c_name = in.getExtras().getString("c_name");
			address = in.getExtras().getString("address");
		}

		System.out.println("c_id-----prepare    " + c_id);
		buyDuihuan = (TextView) findViewById(R.id.buyDuihuan);
		movies_jiantou = (TextView) findViewById(R.id.movies_jiantou);
		rateLinear = (LinearLayout) findViewById(R.id.rateLinear);
		cinemaname = (TextView) findViewById(R.id.cinemaname);
		cinemaname.setText(c_name);
		more_listview = (ListView) findViewById(R.id.more_listview);
		scorerate = (RatingBar) findViewById(R.id.scorerate);
		scoretext = (TextView) findViewById(R.id.scoretext);
		pinluntext = (TextView) findViewById(R.id.pinluntext);
		yingyuanxiangqing = (TextView) findViewById(R.id.yingyuanxiangqing);
		moviedes = (TextView) findViewById(R.id.moviedes);
		// bottom = (LinearLayout) findViewById(R.id.bottom);

	}

	private TextView yingyuanxiangqing = null;
	// private ImageView duihuangquan = null;
	private TextView moviedes = null;
	ArrayList<MovieCinema.MovieCinemaInner> priceList;

	private void onClick() {

		buyDuihuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!shareP.getUserName().equals("")) {
					String companyId = "";
					String price = "";
					boolean flag = true;
					for (int i = 0; i < AppConstants.movieCinema.mList.size(); i++) {
						if (!flag) {
							break;
						}
						for (int j = 0; j < AppConstants.movieCinema.mList.get(i).cinemas.size(); j++) {
							if (c_id.equals(AppConstants.movieCinema.mList.get(i).cinemas.get(j).c_id)) {
								companyId = AppConstants.movieCinema.mList.get(i).cinemas.get(j).companyId;
								priceList = AppConstants.movieCinema.mList.get(i).cinemas.get(j).MovieCinemaInnerList;
								price = AppConstants.movieCinema.mList.get(i).cinemas.get(j).price;

								flag = false;
								break;
							}
						}
					}
					Intent intent = new Intent(MoviePrepareAct.this, BuyTicketExchangeAct.class);
					intent.putExtra("c_id", c_id);
					intent.putExtra("companyId", companyId);
					intent.putExtra("c_name", c_name);
					intent.putExtra("price", price);
					intent.putExtra("priceList", (Serializable) priceList);
					intent.putExtra("payphone", shareP.getUserName());

					mContext.startActivity(intent);
				} else {
					Intent intent = new Intent(MoviePrepareAct.this, UserLoginAct.class);
					mContext.startActivity(intent);
				}
			}
		});
		moviedes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, MovieDetailActivity.class);
				intent.putExtra("cinemaPrepareInfo",
						cinemaPrepareInfo.movies.get(currentListviewPosition));
				intent.putExtra("isFromMoviePrepareAct", true);
				mContext.startActivity(intent);
			}
		});

		more_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				try {
					mMovieNewAdapter.setSelect(position);
					if (currentListviewPosition != position) {
						currentDataPosition = 0;
					}

					onDateClick(position);
					if (dataList.size() <= 1) {
						jia.setVisibility(View.INVISIBLE);
						jian.setVisibility(View.INVISIBLE);
					} else {
						jia.setVisibility(View.VISIBLE);
						jian.setVisibility(View.INVISIBLE);
					}
					currentListviewPosition = position;
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	/**
	 * 对list数组进行排序，list数据必须可排序数据 规则：小到大
	 * 
	 * @param list
	 * @return
	 */
	public ArrayList<String> orderData(ArrayList<String> list) {

		ArrayList<String> orderedlist = new ArrayList<String>();
		ArrayList<Integer> copyList = new ArrayList<Integer>();
		int min = 0;
		int cur = 0;
		int minIndex = 0;

		for (int i = 0; i < list.size(); i++) {
			int day;
			try {
				day = sDateFormat1.parse(list.get(i)).getDate();

				copyList.add(day);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (copyList.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				minIndex = i;
				min = copyList.get(i);
				for (int j = i + 1; j < list.size(); j++) {

					cur = copyList.get(j);
					if (min > cur) {
						minIndex = j;
						min = cur;
					}
				}
				orderedlist.add(list.get(minIndex));
				if (minIndex != i) {
					list.set(minIndex, list.get(i));
				}

			}
		}
		return orderedlist;
	}

	private ImageView jian = null;
	private ImageView jia = null;
	private TextView zhangshu = null;

	private HashMap<String, ArrayList<TimeGrid>> timetable = new HashMap<String, ArrayList<TimeGrid>>();

	private void onDateClick(int position) {
		jian = (ImageView) findViewById(R.id.jian);
		jia = (ImageView) findViewById(R.id.jia);
		zhangshu = (TextView) findViewById(R.id.zhangshu);
		if (!address.trim().equals("") || !address.trim().equals("null"))
			yingyuanxiangqing.setText(address);

		jia.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// addLogic();
				if (currentDataPosition < dataList.size() - 1) {
					currentDataPosition += 1;
					System.out.println("currentDataPosition     " + currentDataPosition);
					jian.setVisibility(View.VISIBLE);
					if (currentDataPosition >= dataList.size() - 1) {
						jia.setVisibility(View.INVISIBLE);
					}
					onDateClick(currentListviewPosition);
					// zhangshu.setText(dataList.get(currentDataPosition));

					// initGridView();
				}

			}

		});
		;
		jian.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// decreaseLogic();
				if (currentDataPosition > 0) {
					currentDataPosition -= 1;
					System.out.println("currentDataPosition     " + currentDataPosition);

					if (currentDataPosition == 0) {
						jian.setVisibility(View.INVISIBLE);
						jia.setVisibility(View.VISIBLE);
					}
					onDateClick(currentListviewPosition);

					// zhangshu.setText(dataList.get(currentDataPosition));
					// initGridView();
				}
			}

		});
		// setZhangshu();

		// 初始化时间表
		Comparator<TimeGrid> comparator;
		Comparator<String> comparatorData;
		moviedes.setText(cinemaPrepareInfo.movies.get(position).name);
		movies_jiantou.setVisibility(View.VISIBLE);
		try {
			System.out.println("cinemaPrepareInfo.movies.get(position).rate          "
					+ cinemaPrepareInfo.movies.get(position).rate);
			scorerate
					.setProgress((int) (Float.parseFloat(cinemaPrepareInfo.movies.get(position).rate) * 2));
		} catch (NumberFormatException e) {
			scorerate.setProgress(10);
			e.printStackTrace();
		}
		scoretext.setText(cinemaPrepareInfo.movies.get(position).rate);
		pinluntext.setText(cinemaPrepareInfo.movies.get(position).comment_count);
		m_id = cinemaPrepareInfo.movies.get(position).m_id;
		dataList.clear();
		if (timeList.size() > 0) {
			timeList.clear();
		}
		if (cinemaPrepareInfo.movies.get(position).daysSeat != null) {
			if (cinemaPrepareInfo.movies.get(position).daysSeat.size() != 0) {

				// if (dataList.size() == 0) {
				// dataList.add(cinemaPrepareInfo.movies.get(position).daysSeat.get(0).day_time);
				// } else if (dataList.size() > 0) {
				for (int i = 0; i < cinemaPrepareInfo.movies.get(position).daysSeat.size(); i++) {
					if (!dataList
							.contains(cinemaPrepareInfo.movies.get(position).daysSeat.get(i).day_time)) {
						dataList.add(cinemaPrepareInfo.movies.get(position).daysSeat.get(i).day_time);
					}
				}

				comparatorData = new DataLowToHighComparator();
				Collections.sort(dataList, comparatorData);
				System.out.println("dataList   ----" + dataList.size());
				// }
				for (int i = 0; i < dataList.size(); i++) {
					ArrayList<TimeGrid> times = new ArrayList<TimeGrid>();
					for (int j = 0; j < cinemaPrepareInfo.movies.get(position).daysSeat.size(); j++) {
						if (cinemaPrepareInfo.movies.get(position).daysSeat.get(j).day_time
								.equals(dataList.get(i))) {
							times.add(new TimeGrid(
									cinemaPrepareInfo.movies.get(position).daysSeat.get(j).s_time,
									cinemaPrepareInfo.movies.get(position).daysSeat.get(j).price,
									cinemaPrepareInfo.movies.get(position).daysSeat.get(j).room,
									cinemaPrepareInfo.movies.get(position).daysSeat.get(j).day_time,
									cinemaPrepareInfo.movies.get(position).daysSeat.get(j).movieSetName,
									cinemaPrepareInfo.movies.get(position).daysSeat.get(j).showCode,
									cinemaPrepareInfo.movies.get(position).daysSeat.get(j).s_time));
						}
					}
					comparator = new TimeLowToHighComparator();
					Collections.sort(times, comparator);
					timeList.add(times);
				}
			} else if (cinemaPrepareInfo.movies.get(position).daysbill.s_time != null) {
				if (dataList.size() == 0) {
					dataList.add(cinemaPrepareInfo.movies.get(0).daysbill.day_time);
				}
				try {
					// dataList = orderData(dataList);
					comparatorData = new DataLowToHighComparator();
					Collections.sort(dataList, comparatorData);
					ArrayList<TimeGrid> times = new ArrayList<TimeGrid>();

					if (cinemaPrepareInfo.movies.get(position).daysbill.day_time.equals(dataList
							.get(currentDataPosition))) {
						String s_time = cinemaPrepareInfo.movies.get(position).daysbill.s_time;
						if (cinemaPrepareInfo.movies.get(position).daysbill.s_time.contains("|")) {
							s_time = cinemaPrepareInfo.movies.get(position).daysbill.s_time
									.replaceAll("\\|", "");
						}
						boolean flag = true;
						String duihuanPrice = "";
						for (int i = 0; i < AppConstants.movieCinema.mList.size(); i++) {
							if (!flag) {
								break;
							}
							for (int j = 0; j < AppConstants.movieCinema.mList.get(i).cinemas
									.size(); j++) {
								if (AppConstants.movieCinema.mList.get(i).cinemas.get(j).c_id
										.equals(c_id)) {
									duihuanPrice = AppConstants.movieCinema.mList.get(i).cinemas
											.get(j).price;
									flag = false;
									break;
								}
							}
						}

						String shijian[] = s_time.split("\\ ");
						for (int i = 0; i < shijian.length; i++) {
							try {
								int a = Integer.parseInt(new TimeGrid(shijian[i], "", "", "", "",
										"", "").time.substring(0, 1));
								times.add(new TimeGrid(shijian[i], duihuanPrice, "", "", "", "", ""));
							} catch (NumberFormatException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					}
					comparator = new TimeLowToHighComparator();
					Collections.sort(times, comparator);
					timeList.add(times);
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		} else if (cinemaPrepareInfo.movies.get(position).daysbill.s_time != null) {
			if (dataList.size() == 0) {
				dataList.add(cinemaPrepareInfo.movies.get(0).daysbill.day_time);
			}
			try {
				// dataList = orderData(dataList);
				comparatorData = new DataLowToHighComparator();
				Collections.sort(dataList, comparatorData);
				ArrayList<TimeGrid> times = new ArrayList<TimeGrid>();

				if (cinemaPrepareInfo.movies.get(position).daysbill.day_time.equals(dataList
						.get(currentDataPosition))) {
					String s_time = cinemaPrepareInfo.movies.get(position).daysbill.s_time;
					if (cinemaPrepareInfo.movies.get(position).daysbill.s_time.contains("|")) {
						s_time = cinemaPrepareInfo.movies.get(position).daysbill.s_time.replaceAll(
								"\\|", "");
					}

					boolean flag = true;
					String duihuanPrice = "";
					for (int i = 0; i < AppConstants.movieCinema.mList.size(); i++) {
						if (!flag) {
							break;
						}
						for (int j = 0; j < AppConstants.movieCinema.mList.get(i).cinemas.size(); j++) {
							if (AppConstants.movieCinema.mList.get(i).cinemas.get(j).c_id
									.equals(c_id)) {
								duihuanPrice = AppConstants.movieCinema.mList.get(i).cinemas.get(j).price;
								flag = false;
								break;
							}
						}
					}
					String shijian[] = s_time.split("\\ ");
					for (int i = 0; i < shijian.length; i++) {
						try {
							int a = Integer.parseInt(new TimeGrid(shijian[i], "", "", "", "", "",
									"").time.substring(0, 1));
							times.add(new TimeGrid(shijian[i], duihuanPrice, "", "", "", "", ""));
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
				comparator = new TimeLowToHighComparator();
				Collections.sort(times, comparator);
				timeList.add(times);
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}

		try {
			String data = sDateFormat1.format(new java.util.Date());
			long systemData = sDateFormat1.parse(data).getTime();// 得到系统时间
			if (sDateFormat1.parse(dataList.get(currentDataPosition)).getTime() == systemData) {// 当天
				System.out.println("dangtian   ----"
						+ sDateFormat1.parse(dataList.get(currentDataPosition)).getTime());
				zhangshu.setText(dataList.get(currentDataPosition) + "(今)");
			} else {
				zhangshu.setText(dataList.get(currentDataPosition));
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		rateLinear.setVisibility(View.VISIBLE);
		initGridView();
	}

	public class TimeLowToHighComparator implements Comparator<TimeGrid> {
		@Override
		public int compare(TimeGrid itemBean1, TimeGrid itemBean2) {
			int time1 = 0;
			int time2 = 0;
			try {
				if (itemBean1.time != null && !"".equals(itemBean1.time)) {
					// meters1 = Double.parseDouble(itemBean1.meters);
					time1 = Integer.parseInt(itemBean1.time.split(":")[0]
							+ itemBean1.time.split(":")[1]);
				}
				if (itemBean2.time != null && !"".equals(itemBean2.time)) {
					time2 = Integer.parseInt(itemBean2.time.split(":")[0]
							+ itemBean2.time.split(":")[1]);
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

	class DataLowToHighComparator implements Comparator<String> {
		@Override
		public int compare(String itemBean1, String itemBean2) {
			int time1 = 0;
			int time2 = 0;
			try {
				if (itemBean1 != null && !"".equals(itemBean1)) {
					// meters1 = Double.parseDouble(itemBean1.meters);
					time1 = Integer.parseInt(itemBean1.split("-")[0] + itemBean1.split("-")[1]
							+ itemBean1.split("-")[2]);
				}
				if (itemBean2 != null && !"".equals(itemBean2)) {
					time2 = Integer.parseInt(itemBean2.split("-")[0] + itemBean2.split("-")[1]
							+ itemBean2.split("-")[2]);
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

	// -------------------影片列表-----------START
	private void initMoreMovies() {
		more_listview = (ListView) findViewById(R.id.more_listview);
	}

	private RatingBar scorerate = null;
	private TextView scoretext = null;
	private TextView pinluntext = null;
	private TextView cinemaname = null;

	private void showMovieItem(Movie bean) {
		Log.i("-----MainAct.showMovieItem-" + bean.rate);
		scorerate.setRating(Float.parseFloat(bean.rate) * 2f);
		scoretext.setText(bean.rate);
		// pinluntext.setText(bean.comment_count);
		cinemaname.setText(bean.name);
		// bigimage.setImageResource(bean.pinluntext);
	}

	private boolean ismListViewHidden = true;
	private ListView more_listview = null;
	private MoviePrepareAdapter mMovieNewAdapter = null;
	private MovieNewList mMovieNewList = null;

	// -------------------影片列表----------END

	// 时间表-------------------------------------------S
	public void updateGridView() {
		if (mTimeGridAdapter != null)
			mTimeGridAdapter.notifyDataSetChanged();
	}

	public void initGridView() {
		// for (int i = 0; i < 12; i++) {
		// times.add(new TimeGrid("8:" + i));
		// }
		if (timeList == null || timeList.size() == 0) {
			return;
		}
		if (time_grid != null) {
			mTimeGridAdapter = null;
		}
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sTimeFormat = new SimpleDateFormat("HH:mm");

		String time = sTimeFormat.format(new Date(System.currentTimeMillis()));
		String data = sDateFormat.format(new java.util.Date());
		if (cinemaPrepareInfo.movies != null) {
			try {
				long todayData = sDateFormat.parse(dataList.get(currentDataPosition)).getTime();
				long systemData = sDateFormat.parse(data).getTime();
				if (todayData == systemData) {
					for (int i = 0; i < timeList.size(); i++) {
						for (int j = 0; j < timeList.get(i).size(); j++) {
							if (Integer.parseInt(timeList.get(i).get(j).time.split(":")[0].trim()) < Integer
									.parseInt(time.split(":")[0].trim())) {
								timeList.get(i).get(j).enable = false;
							} else if (Integer.parseInt(timeList.get(i).get(j).time.split(":")[0]
									.trim()) == Integer.parseInt(time.split(":")[0].trim())) {
								if (Integer.parseInt(timeList.get(i).get(j).time.split(":")[1]
										.trim()) < Integer.parseInt(time.split(":")[1].trim())) {
									timeList.get(i).get(j).enable = false;
								}
							}
						}
					}
				} else if (todayData < systemData) {
					for (int i = 0; i < timeList.size(); i++) {
						for (int j = 0; j < timeList.get(i).size(); j++) {
							timeList.get(i).get(j).enable = false;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		time_grid = (GridView) findViewById(R.id.time_grid);
		mTimeGridAdapter = new TimeGridAdapter();
		time_grid.setAdapter(mTimeGridAdapter);
		time_grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				System.out.println("orderType--------" + orderType);
				if (orderType.equals("1")) {
					return;
				}
				SharedPreferencesUtil shareP = SharedPreferencesUtil.getInstance(mContext);
				if (!shareP.getUserName().equals("")) {
					if (timeList.get(currentDataPosition).get(arg2).enable) {
						timeList.get(currentDataPosition).get(arg2).enable = false;
						mTimeGridAdapter.notifyDataSetChanged();
						String showCode = "";
						String movie_showtime = "";
						String movie_showdate = "";
						String companyId = "";
						String price = "";
						String room = "";
						boolean flag = true;
						for (int i = 0; i < AppConstants.movieCinema.mList.size(); i++) {
							if (!flag) {
								break;
							}
							for (int j = 0; j < AppConstants.movieCinema.mList.get(i).cinemas
									.size(); j++) {
								if (c_id.equals(AppConstants.movieCinema.mList.get(i).cinemas
										.get(j).c_id)) {
									companyId = AppConstants.movieCinema.mList.get(i).cinemas
											.get(j).companyId;
									flag = false;
									break;
								}
							}
						}
						if (cinemaPrepareInfo.movies.get(currentListviewPosition).daysSeat != null) {
							if (cinemaPrepareInfo.movies.get(currentListviewPosition).daysSeat
									.size() != 0) {
								showCode = timeList.get(currentDataPosition).get(arg2).showCode;
								movie_showdate = timeList.get(currentDataPosition).get(arg2).day_time;
								movie_showtime = timeList.get(currentDataPosition).get(arg2).s_time;
								price = timeList.get(currentDataPosition).get(arg2).price;
								room = timeList.get(currentDataPosition).get(arg2).room;
								// showCode =
								// cinemaPrepareInfo.movies.get(currentListviewPosition).daysSeat.get(arg2).showCode;
								// movie_showdate =
								// cinemaPrepareInfo.movies.get(currentListviewPosition).daysSeat.get(arg2).day_time;
								// movie_showtime =
								// cinemaPrepareInfo.movies.get(currentListviewPosition).daysSeat.get(arg2).s_time;
								// price =
								// cinemaPrepareInfo.movies.get(currentListviewPosition).daysSeat.get(arg2).price;s
								System.out.println("price-------pp------" + price);
								// room =
								// cinemaPrepareInfo.movies.get(currentListviewPosition).daysSeat.get(arg2).room;
							}
						}
						m_id = cinemaPrepareInfo.movies.get(currentListviewPosition).m_id;
						// Intent in = new Intent(MoviePrepareAct.this,
						// BuyTicketAct.class);
						// in.putExtra("m_id", m_id);
						// in.putExtra("c_id", c_id);
						// in.putExtra("showCode", showCode);
						// in.putExtra("movie_showtime", movie_showtime);
						// in.putExtra("m_name", moviedes.getText().toString());
						// in.putExtra("cover_image_url",
						// cinemaPrepareInfo.movies.get(currentListviewPosition).client_placard_url2);
						// // in.putExtra("noindexActivity", true);
						// in.putExtra("time",
						// timeList.get(currentDataPosition).get(arg2).time);
						// in.putExtra("prepareData",
						// dataList.get(currentDataPosition));
						// startActivity(in);

						Intent intent = new Intent(mContext, SeatSelectedAct.class);
						intent.putExtra("moviecinema_id", c_id);
						intent.putExtra("companyId", companyId);
						intent.putExtra("movieSeat_ShowCode", showCode);
						intent.putExtra("movie_cinemaPrice", price);// 将票价歘过去
						intent.putExtra("movieName", moviedes.getText().toString());
						intent.putExtra(
								"cover_image_url",
								cinemaPrepareInfo.movies.get(currentListviewPosition).client_placard_url2);
						intent.putExtra("payphone", shareP.getUserName());
						// if (TextUtils.isEmpty(movie_showdate)) {
						// movie_showdate = zhangshu.getText().toString();
						// }
						intent.putExtra("showTime", movie_showdate); // 放映时间
						intent.putExtra("movieCinemaName", c_name);// 电影院
						intent.putExtra("cinemaRoom", room);
						intent.putExtra("cinemaTime", movie_showtime);

						// System.out.println("----moviecinema_id----"+c_id+"----companyId----"+companyId+"----movieSeat_ShowCode----"+showCode+
						// "----movie_cinemaPrice----"+price+"----movieName----"+moviedes.getText().toString()+
						// "----cover_image_url----"+cinemaPrepareInfo.movies.get(currentListviewPosition).client_placard_url2+"----payphone----"+
						// shareP.getUserName()+"----showTime----"+movie_showdate+"----movieCinemaName----"+c_name+
						// "----cinemaRoom----"+room+"----cinemaTime----"+movie_showtime);
						mContext.startActivity(intent);
					}
				} else {
					Intent intent = new Intent(mContext, UserLoginAct.class);
					mContext.startActivity(intent);
				}

			}
		});

	}

	private GridView time_grid = null;
	private TimeGridAdapter mTimeGridAdapter = null;

	public class TimeGridAdapter extends BaseAdapter {

		private LayoutInflater layoutFlater;

		public TimeGridAdapter() {
			super();
			layoutFlater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			if (timeList.size() != 0) {
				return timeList.get(currentDataPosition).size();
			}
			return 0;
		}

		public TimeGrid getItem(int position) {
			return timeList.get(currentDataPosition).get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolderGridViewItem holder;
			if (convertView == null) {
				convertView = layoutFlater.inflate(R.layout.grid_item, null);
				holder = new ViewHolderGridViewItem();
				holder.timegrid = (TextView) convertView.findViewById(R.id.timegrid);
				holder.timegrid_un = (TextView) convertView.findViewById(R.id.timegrid_un);
				holder.room = (TextView) convertView.findViewById(R.id.room);
				holder.room_un = (TextView) convertView.findViewById(R.id.room_un);
				holder.price = (TextView) convertView.findViewById(R.id.price);
				holder.price_un = (TextView) convertView.findViewById(R.id.price_un);
				holder.timegridLinear = (LinearLayout) convertView
						.findViewById(R.id.timegridLinear);
				holder.timegridLinear_un = (LinearLayout) convertView
						.findViewById(R.id.timegridLinear_un);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolderGridViewItem) convertView.getTag();
				holder.timegridLinear.setVisibility(View.INVISIBLE);
				holder.timegridLinear_un.setVisibility(View.GONE);
			}

			TimeGrid bean = timeList.get(currentDataPosition).get(position);
			if (bean.room.equals("")) {
				holder.room.setVisibility(View.GONE);
				holder.room_un.setVisibility(View.GONE);
			} else {
				holder.room.setVisibility(View.VISIBLE);
				holder.room_un.setVisibility(View.VISIBLE);
			}
			holder.timegrid.setText(bean.time);
			holder.timegrid_un.setText(bean.time);
			holder.room.setText(bean.room);
			holder.room_un.setText(bean.room);
			holder.price.setText(bean.price + "元");
			holder.price_un.setText(bean.price + "元");
			if (bean.enable) {
				if (orderType.equals("1")) {
					holder.timegridLinear.setVisibility(View.GONE);
					holder.timegridLinear_un.setVisibility(View.VISIBLE);
				} else {
					holder.timegridLinear.setVisibility(View.VISIBLE);
					holder.timegridLinear_un.setVisibility(View.GONE);
				}
			} else {
				holder.timegridLinear.setVisibility(View.GONE);
				holder.timegridLinear_un.setVisibility(View.VISIBLE);
			}

			return convertView;
		}
	}

	private class ViewHolderGridViewItem {
		public TextView timegrid, room, price;
		public TextView timegrid_un, room_un, price_un;
		public LinearLayout timegridLinear, timegridLinear_un;

	}

	// 时间表-------------------------------------------E

	/**
	 * 通过影院获取该影院的所有当期
	 * 
	 * @author Haylee
	 * 
	 */
	class GetMovieInfoTask extends MovieAsyncTask<String, String, CinemaPrepareInfo> {
		public String exception;

		public GetMovieInfoTask(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected CinemaPrepareInfo doInBackground(String... params) {

			try {
				// movieInfo = lib.getAllMovie();
				System.out.println("c_id ---  " + c_id);
				if (AppConstants.movieCinema == null || AppConstants.movieCinema.mList == null) {
					AppConstants.movieCinema = lib.getCinema(MoviePrepareAct.this);
				}
				if (!c_id.equals("")) {
					cinemaPrepareInfo = lib.getMoviesAndCouponByCinema(c_id);

				}
				// adapter_main.setMovieInfo(movieInfo);
				// list =movieInfo.movies;
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
			return cinemaPrepareInfo;
		}

		@Override
		protected void onPostExecute(CinemaPrepareInfo result) {
			super.onPostExecute(result);
			if (result != null && result.isSuccess()) {
				if (cinemaPrepareInfo.movies != null) {
					// adapter_main.addItem(result.movies);
					mMovieNewAdapter.addItem(result.movies);
					onDateClick(0);
					if (dataList.size() <= 1) {
						jia.setVisibility(View.INVISIBLE);
						jian.setVisibility(View.INVISIBLE);
					} else {
						jia.setVisibility(View.VISIBLE);
						jian.setVisibility(View.INVISIBLE);
					}
					if (AppConstants.movieCinema.mList.size() != 0) {
						for (int i = 0; i < AppConstants.movieCinema.mList.size(); i++) {
							for (int j = 0; j < AppConstants.movieCinema.mList.get(i).cinemas
									.size(); j++) {
								if (c_id.equals(AppConstants.movieCinema.mList.get(i).cinemas
										.get(j).c_id)) {
									orderType = AppConstants.movieCinema.mList.get(i).cinemas
											.get(j).order_type;
								}
							}
						}
					}
					if (orderType.equals("1") || orderType.equals("2")) {
						buyDuihuan.setVisibility(View.VISIBLE);
					}

				} else {
					yingyuanxiangqing.setText("该影院暂无排期！");
					finish();
				}
				isMoviesLoadAll = true;
			} else {
				Toast.makeText(MoviePrepareAct.this, "加载失败，请尝试重新加载！", Toast.LENGTH_LONG).show();
				finish();
			}
		}
	}

	public class MoviePrepareAdapter extends BaseAdapter {
		private ArrayList<CinemaPrepareInfo.MovieCinemaPrepareInner> movieList;
		private Context mContext;
		private MovieApplication app;
		private int select_index = 0;

		public MoviePrepareAdapter(Context context) {
			mContext = context;
			app = (MovieApplication) mContext.getApplicationContext();
			movieList = new ArrayList<CinemaPrepareInfo.MovieCinemaPrepareInner>();
		}

		public void addItem(List<CinemaPrepareInfo.MovieCinemaPrepareInner> list) {
			if (list != null) {
				movieList.addAll(list);
				notifyDataSetChanged();
			}
		}

		public void setSelect(int select) {
			select_index = select;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return movieList.size();
		}

		@Override
		public Object getItem(int position) {
			return movieList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.movie_new_list_item,
						null);
				holder = new ViewHolder(convertView);
				RoateUtil.tranlateCenter(mContext, holder.getScore());
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// holder.getScore().clearAnimation();
			CinemaPrepareInfo.MovieCinemaPrepareInner movie = movieList.get(position);

			holder.getScore().setText(movie.rate);
			holder.getDate().setText(movie.release_date);
			holder.getName().setText(movie.name);
			final ImageView imageView = holder.getPic();
			// imageView.setImageResource(R.drawable.loading);
			holder.getPic().setImageBitmap(BaseDBUtil.readBitMap(mContext, R.drawable.loadinglist));
			if (movie != null && URLUtil.isHttpUrl(movie.client_placard_url2)) {
				imageView.setTag(movie.client_placard_url2);
				Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext,
						movie.client_placard_url2, new ImageCallback() {

							@Override
							public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
								String url = (String) imageView.getTag();
								if (url.equals(bitmapUrl)) {
									imageView.setImageBitmap(bitmap);
								}
							}
						}, AsyncImageLoader.CACHE_TYPE_SD, true);
				if (bitmap != null) {
					imageView.setImageBitmap(bitmap);
				}
			}

			// if ("0".equals(movie.upcomming))// 已上映
			// {
			// holder.getDate().setVisibility(View.INVISIBLE);
			// holder.getScore().setVisibility(View.VISIBLE);
			// holder.getScore_bk().setVisibility(View.VISIBLE);
			// }else if ("1".equals(movie.upcomming))// 未上映
			// {
			// holder.getDate().setVisibility(View.VISIBLE);
			// holder.getScore().clearAnimation();
			// holder.getScore().setVisibility(View.INVISIBLE);
			// holder.getScore_bk().setVisibility(View.INVISIBLE);
			// }
			holder.getDate().setVisibility(View.INVISIBLE);
			holder.getScore().setVisibility(View.VISIBLE);
			holder.getScore_bk().setVisibility(View.VISIBLE);
			if (select_index == position) {
				holder.getFrame().setImageResource(R.drawable.film_cover_red_bg);
				holder.getName()
						.setTextColor(mContext.getResources().getColor(R.color.name_orange));
			} else {
				holder.getFrame().setImageResource(R.drawable.film_cover_black_bg);
				holder.getName().setTextColor(mContext.getResources().getColor(R.color.white));
			}

			return convertView;
		}

		private class ViewHolder {
			private ImageView pic;
			private TextView score;
			private ImageView score_bk;
			private TextView date;
			private TextView name;
			private ImageView frame;
			private View view;

			public ViewHolder(View view) {
				this.view = view;
			}

			public ImageView getPic() {
				if (pic == null) {
					pic = (ImageView) view.findViewById(R.id.movie_item_imageView_pic);
				}
				return pic;
			}

			public TextView getScore() {
				if (score == null) {
					score = (TextView) view.findViewById(R.id.movie_item_textView_score);
				}
				return score;
			}

			public ImageView getScore_bk() {
				if (score_bk == null) {
					score_bk = (ImageView) view.findViewById(R.id.movie_item_imageView_score);
				}
				return score_bk;
			}

			public TextView getDate() {
				if (date == null) {
					date = (TextView) view.findViewById(R.id.movie_item_textView_date);
				}
				return date;
			}

			public TextView getName() {
				if (name == null) {
					name = (TextView) view.findViewById(R.id.movie_item_textView_name);
				}
				return name;
			}

			public ImageView getFrame() {
				if (frame == null) {
					frame = (ImageView) view.findViewById(R.id.movie_item_imageView_frame);
				}
				return frame;
			}

		}
	}

	@Override
	protected void onRestart() {
		// initGridView();
		System.out.println("restart-------------" + currentListviewPosition);
		onDateClick(currentListviewPosition);
		super.onRestart();

	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("recycle----");
		app.getAsyncImageLoader().recycleBitmaps();
	}

	public void onResume() {
		super.onResume();
		CmccDataStatistics.onStart(this);
	}

	public void onPause() {
		super.onPause();
		CmccDataStatistics.onStop(this);
	}
}
