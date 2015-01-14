package com.szcmcc.movie.activity;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.sdk.CmccDataStatistics;
import com.itotem.view.zoomview.ViewZoomTouch;
import com.szcmcc.movie.R;
import com.szcmcc.movie.adapter.ArrayWheelAdapter;
import com.szcmcc.movie.adapter.OnWheelChangedListener;
import com.szcmcc.movie.bean.CinemaPrepareMovie;
import com.szcmcc.movie.bean.CinemaPrepareMovieCinema;
import com.szcmcc.movie.bean.MovieCinema;
import com.szcmcc.movie.bean.MovieCinemaList;
import com.szcmcc.movie.bean.MovieSeatInfo;
import com.szcmcc.movie.bean.MovieTicket;
import com.szcmcc.movie.bean.MovieTicketList;
import com.szcmcc.movie.bean.PointList;
import com.szcmcc.movie.bean.TimeGrid;
import com.szcmcc.movie.cache.AppConstants;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.storage.SharedPreferencesUtil;
import com.szcmcc.movie.util.ScreenInfo;
import com.szcmcc.movie.util.Transition3d;
import com.szcmcc.movie.view.MarqueeTextView;
import com.szcmcc.movie.view.WheelView;

/**
 * Class Name: 购票界面
 * 
 * @author panxin 2012-9-13 下午3:34:49
 */
public class BuyTicketAct extends BaseActivity {

	private Context mContext = BuyTicketAct.this;
//	private PointList mPointList = new PointList();
	private ImageView backGround;
	private LinearLayout chooseLinear;
	private Button chooseOk, chooseBack;
	private WheelView city = null, country = null;
	private boolean isMoviesLoadAll = false;
	private String c_id = "", m_id = "", m_name = "", cover_image_url = "";
	CinemaPrepareMovieCinema cinemaPrepareMovieCinema = null;
	private MovieCinemaList movieCinema = null;
	private TextView zaoshang = null, zhongwu = null, wanshang = null;
	private ArrayList<MovieTicket> seatAllList = new ArrayList<MovieTicket>();// 通过座位获取的全部影片信息
	private ArrayList<MovieTicket> currentTimeBySeatList = new ArrayList<MovieTicket>();// 临时记录当前时间的影片信息
	/** 记录选择影院功能里区域的列表 */
	private ArrayList<MovieCinemaList.MovieCinemaListInner> quyuList = new ArrayList<MovieCinemaList.MovieCinemaListInner>();
	private ArrayList<String> quyuNameList = new ArrayList<String>();
	/** 记录选择影院功能里影院的列表 */
	private ArrayList<ArrayList<MovieCinema>> cList = new ArrayList<ArrayList<MovieCinema>>();

	private ArrayList<ArrayList<String>> cNameList = new ArrayList<ArrayList<String>>();
	/** 记录当前日期和时间选的是哪个列表，为了传属于哪个列表里的哪一个价格项的movieSeat_ShowCode而用 */
	ArrayList<MovieTicket> cuMovieTicketsList = null;
	private ArrayList<MovieCinema.MovieCinemaInner> priceList = new ArrayList<MovieCinema.MovieCinemaInner>();
	private CinemaPrepareMovie cinemaPrepareMovie = null;
	private ArrayList<String> dataList = new ArrayList<String>();
	private ArrayList<ArrayList<TimeGrid>> timeList = new ArrayList<ArrayList<TimeGrid>>();
	private int currentPositionI = 0, currentPositionJ = 0;// 区域和影院的索引
	private TextView duihuanpiaoMoney = null;
	private String mprice = "";
	private int currentDataPosition = 0;// 当前天的索引
	private LinearLayout seatsLinear;
	private LinearLayout seatsLinear1;
	private LinearLayout duihuanLinear;
	// 与购票相关
	private String movieSeat_ShowCode = "";// 获取座位票的排期编码
	private String movie_ShowTime = "", movie_CinemaName = "", movie_cinemaPrice = "";
	private boolean fromMoviePrepareAct = false;
	private MovieSeatInfo movieSeat;
	private LinearLayout xuanzuoLinear = null;// 选作布局
	private ImageView yingyuanxiangqing = null;
	private ImageView zuoweipiao = null;
	private ImageView duihuanquan = null;
	private ImageView zao_jiantou = null, wu_jiantou = null, wan_jiantou = null;
	private TextView mName = null;
	private TextView buy = null;
	private TextView goseatselected = null;
	private TextView duihuantext1, duihuantext2, duihuantext3;
	SharedPreferencesUtil shareP;
	private int mostCol, mostRow;
	private ProgressBar getSeat_LoadingProgressBar;
	private SimpleDateFormat sDateFormat = null, sTimeFormat = null;

	private TextView duihuanpiaoTitle = null;
	private String duihuanpiaoTitleCinemaName = "";
	private Map<String, MovieSeatInfo> cancheSeatData;
	private int cancheposition = 0;
	String data;
	private String reStartShowCode = "";
	private TextView no_changci = null;
	// private boolean isSeatTimeFlag = true;
	private String orderType = "";
	private String time = "", prepareData = "";

	private TextView seat_message;

	private String cinemaRoom = "", cinemaTime = "";
	GetMovieSeatInfoTask getMovieSeatInfoTask = null;
	// private boolean isCanLoadNewSeat = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buyticketact);
		shareP = SharedPreferencesUtil.getInstance(this);
		sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		sTimeFormat = new SimpleDateFormat("HH:mm");
		data = sDateFormat.format(new java.util.Date());
		initZoneSubSlected();
		initTitleBar();
		onclick();
		initThreeLine();
		if (!isMoviesLoadAll) {
			// System.out.println("dongdianzhaouBuyTicket2" + isMoviesLoadAll);
			new GetMovieInfoTask(this).execute();
		}
	}

	private LinearLayout leftl = null;
	private TextView leftl_textview = null;
	private LinearLayout rightl = null;
	private TextView rgithl_textview = null;
	private MarqueeTextView name = null;

	private void initZoneSubSlected() {
		cancheSeatData = new HashMap<String, MovieSeatInfo>();
		Intent in = getIntent();
		if (in.getExtras() != null) {
			c_id = in.getExtras().getString("c_id");
			m_id = in.getExtras().getString("m_id");
			m_name = in.getExtras().getString("m_name");
			cover_image_url = in.getExtras().getString("cover_image_url");
			movieSeat_ShowCode = in.getStringExtra("showCode");
			System.out.println("c_id      ----------------           " + c_id);
			movie_ShowTime = in.getStringExtra("movie_showtime");
			if (in.getBooleanExtra("noindexActivity", false)) {
				fromMoviePrepareAct = true;
			}
			time = in.getStringExtra("time");
			prepareData = in.getStringExtra("prepareData");
		}
		container_l = (ViewZoomTouch) findViewById(R.id.container_l);
		duihuantext1 = (TextView) findViewById(R.id.tuihuantext1);
		duihuantext2 = (TextView) findViewById(R.id.tuihuantext2);
		duihuantext3 = (TextView) findViewById(R.id.tuihuantext3);
		no_changci = (TextView) findViewById(R.id.no_changci);
		seat_message = (TextView) findViewById(R.id.seat_message);
		duihuanpiaoTitle = (TextView) findViewById(R.id.duihuanpiaoTitle);
		movie_listview = (ListView) findViewById(R.id.movie_listview);
		getSeat_LoadingProgressBar = (ProgressBar) findViewById(R.id.getseat_loading_progress);
		zao_jiantou = (ImageView) findViewById(R.id.zao_jiantou);
		wu_jiantou = (ImageView) findViewById(R.id.wu_jiantou);
		wan_jiantou = (ImageView) findViewById(R.id.wan_jiantou);
		mName = (TextView) findViewById(R.id.mName);
		duihuanpiaoMoney = (TextView) findViewById(R.id.duihuanpiaoMoney);
		name = (MarqueeTextView) findViewById(R.id.m_name);
		zaoshang = (TextView) findViewById(R.id.zaoshang);
		zhongwu = (TextView) findViewById(R.id.zhongwu);
		wanshang = (TextView) findViewById(R.id.wanshang);
		chooseOk = (Button) findViewById(R.id.chooseOk);
		chooseBack = (Button) findViewById(R.id.chooseBack);
		chooseLinear = (LinearLayout) findViewById(R.id.chooseLinear);
		leftl = (LinearLayout) findViewById(R.id.leftl);
		leftl_textview = (TextView) findViewById(R.id.leftl_textview);
		rightl = (LinearLayout) findViewById(R.id.rgithl);
		rgithl_textview = (TextView) findViewById(R.id.rgithl_textview);
		backGround = (ImageView) findViewById(R.id.bigimage);
		name.setText(m_name);
		mName.setText(m_name);

		// 选择大区
		leftl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				changeFramesDownToUpIn(BuyTicketAct.this, chooseLinear);
				chooseLinear.setVisibility(View.VISIBLE);
				canEnableClick(false);

			}
		});

		// 选择影院
		rightl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				changeFramesDownToUpIn(BuyTicketAct.this, chooseLinear);
				chooseLinear.setVisibility(View.VISIBLE);
				canEnableClick(false);
			}
		});
		chooseOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (quyuList.size() != 0 && cList.size() != 0) {
					if (!cList.get(country.getCurrentItem()).get(city.getCurrentItem()).c_id.equals(c_id)) {
						if (cancheSeatData.size() > 0) {
							cancheSeatData.clear();
						}
						removeViews();
						goseatselected.setVisibility(View.GONE);
						leftl_textview.setText(quyuList.get(country.getCurrentItem()).region_name);
						// System.out.println("5------------" +
						// cList.get(country.getCurrentItem()).get(city.getCurrentItem()).c_id);
						rgithl_textview.setText(cList.get(country.getCurrentItem()).get(city.getCurrentItem()).c_name);
						duihuanpiaoTitle.setText(cList.get(country.getCurrentItem()).get(city.getCurrentItem()).c_name);
						c_id = cList.get(country.getCurrentItem()).get(city.getCurrentItem()).c_id;
						System.out.println("c-id   =     " + c_id);
						highLight.setBackgroundDrawable(null);
						visibleJiantou(zao_jiantou, wu_jiantou, wan_jiantou);
						new GetMovieInfoTask1(BuyTicketAct.this).execute();
					}
					changeFramesUpToDownOut(BuyTicketAct.this, chooseLinear);
					chooseLinear.setVisibility(View.GONE);
					canEnableClick(true);

				}
			}
		});

		chooseBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				changeFramesUpToDownOut(BuyTicketAct.this, chooseLinear);
				chooseLinear.setVisibility(View.GONE);
				canEnableClick(true);
			}
		});
	}

	private void showJiantou(View showView, View goneView1, View goneView2) {
		showView.setVisibility(View.VISIBLE);
		goneView1.setVisibility(View.GONE);
		goneView2.setVisibility(View.GONE);
	}

	private void visibleJiantou(View showView, View goneView1, View goneView2) {
		showView.setVisibility(View.GONE);
		goneView1.setVisibility(View.GONE);
		goneView2.setVisibility(View.GONE);
	}
//	Bitmap bitmap = null;
	private void setImageBackGround(final ImageView imageView, String imageUrl) {

		imageView.setTag(imageUrl);
		Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(this, imageUrl, new ImageCallback() {

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

	private void canEnableClick(boolean can) {
		if (!can) {
			leftl.setEnabled(false);
			rightl.setEnabled(false);
			line1.setEnabled(false);
			line2.setEnabled(false);
			line3.setEnabled(false);
			yingyuanxiangqing.setEnabled(false);
			zuoweipiao.setEnabled(false);
			duihuanquan.setEnabled(false);
			buy.setEnabled(false);
			goseatselected.setEnabled(false);
			jia.setEnabled(false);
			jian.setEnabled(false);
			movie_listview.setEnabled(false);
		} else {
			leftl.setEnabled(true);
			rightl.setEnabled(true);
			line1.setEnabled(true);
			line2.setEnabled(true);
			line3.setEnabled(true);
			yingyuanxiangqing.setEnabled(true);
			zuoweipiao.setEnabled(true);
			duihuanquan.setEnabled(true);
			buy.setEnabled(true);
			goseatselected.setEnabled(true);
			jia.setEnabled(true);
			jian.setEnabled(true);
			movie_listview.setEnabled(true);
		}
	}

	/**
	 * 设置下面的座位布局
	 */
	private void setSeatLayout(int seatCol, int seatRow, MovieSeatInfo movieSeatInfo) {
		PointList mPointList = new PointList();
		mPointList.column = seatCol;
		mPointList.rows = seatRow;
		mPointList.grid = ScreenInfo.getSeatGridWidth(seatCol + 2);
		printTable(mPointList, movieSeatInfo);
		mostCol = 0;
		mostRow = 0;
	}

	private void setChooseWheel() {
		boolean flag = true;
		for (int i = 0; i < cList.size(); i++) {
			if (!flag) {
				break;
			}
			for (int j = 0; j < cList.get(i).size(); j++) {
				if (cList.get(i).get(j).c_id.equals(c_id)) {
					currentPositionI = i;
					currentPositionJ = j;
					// System.out.println("i j" + i + "    " + j);
					flag = false;
					break;
				}
			}
		}

		for (int i = 0; i < quyuList.size(); i++) {
			ArrayList<String> linshiList = new ArrayList<String>();
//			ArrayList<String> linshiListSeat = new ArrayList<String>();
//			ArrayList<String> linshiList1 = new ArrayList<String>();
			quyuNameList.add(quyuList.get(i).region_name);
			for (int j = 0; j < cList.get(i).size(); j++) {
				linshiList.add(cList.get(i).get(j).c_name);
			}

			cNameList.add(linshiList);
		}
		country = (WheelView) findViewById(R.id.country);

		country.setVisibleItems(5);
		country.setAdapter(new ArrayWheelAdapter(quyuNameList));

		city = (WheelView) findViewById(R.id.city);
		city.setVisibleItems(5);
		// System.out.println("cNameList.get(0).size()   --  " +
		// cNameList.get(0).size());
		country.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				city.setAdapter(new ArrayWheelAdapter(cNameList.get(newValue)));
				city.setCurrentItem(0);
			}
		});
//		c_id = cList.get(currentPositionI).get(currentPositionJ).c_id;
//		country.setCurrentItem(0);
		city.setAdapter(new ArrayWheelAdapter(cNameList.get(0)));
//		city.setCurrentItem(0);

	}

	private void changeFramesUpToDownOut(Activity activity, View layout) {
		final Animation animation = AnimationUtils.loadAnimation(activity, R.anim.uptodown_out);
		layout.startAnimation(animation);
	}

	private void changeFramesDownToUpIn(Activity activity, View layout) {
		final Animation animation = AnimationUtils.loadAnimation(activity, R.anim.downtoup_in);
		layout.startAnimation(animation);
	}

	private void getData() {

		if (cinemaPrepareMovieCinema == null) {
//			seat_message.setVisibility(View.VISIBLE);
			goseatselected.setVisibility(View.GONE);
			return;
		}
		if (!cinemaPrepareMovieCinema.result.success()) {
//			seat_message.setVisibility(View.VISIBLE);
			goseatselected.setVisibility(View.GONE);
		}
		// 初始化电影票信息数据（右边的listview里数据）
		if (cinemaPrepareMovieCinema.list != null) {
			// if(isSeatTimeFlag){
			if (seatAllList.size() != 0) {
				seatAllList.clear();
			}
			for (int i = 0; i < cinemaPrepareMovieCinema.list.size(); i++) {
				MovieTicket mMovieTicket = new MovieTicket();
				mMovieTicket.time = cinemaPrepareMovieCinema.list.get(i).s_time;
				mMovieTicket.movitype = cinemaPrepareMovieCinema.list.get(i).type;
				mMovieTicket.price = cinemaPrepareMovieCinema.list.get(i).price;
				mMovieTicket.type = cinemaPrepareMovieCinema.list.get(i).language;
				mMovieTicket.data = cinemaPrepareMovieCinema.list.get(i).day_time;
				mMovieTicket.showcode = cinemaPrepareMovieCinema.list.get(i).showCode;
				mMovieTicket.room = cinemaPrepareMovieCinema.list.get(i).room;
				seatAllList.add(mMovieTicket);

			}
			// if (seatAllList.size() == 0) {
			// seat_message.setVisibility(View.VISIBLE);
			// } else {
			// seat_message.setVisibility(View.GONE);
			// }
		}
		// }
		else if (cinemaPrepareMovieCinema.byBill != null) {
			// if(!isSeatTimeFlag){
			if (seatAllList.size() != 0) {
				seatAllList.clear();
			}

			String s_time = cinemaPrepareMovieCinema.byBill.s_time;
			if (cinemaPrepareMovieCinema.byBill.s_time.contains("|")) {
				s_time = cinemaPrepareMovieCinema.byBill.s_time.replaceAll("\\|", "");
			}
			String times[] = s_time.split("\\ ");
			for (int i = 0; i < times.length; i++) {
				try {
					MovieTicket mMovieTicket = new MovieTicket();
					mMovieTicket.time = times[i];
					mMovieTicket.data = cinemaPrepareMovieCinema.byBill.day_time;
					Integer.parseInt(mMovieTicket.time.substring(0, 1));// 因为服务器问题，有可能返回空字符串，这里为了不加进列表里做的处理。
					seatAllList.add(mMovieTicket);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
				// }
			}
		}

		ArrayList<MovieTicket> cuMovieTickets = getCurentMovieTicket(seatAllList);
		cuMovieTicketsList = cuMovieTickets;

		initMovieTickets(cuMovieTickets);

		if (orderType.equals("1")) {
			duihuantext1.setVisibility(View.VISIBLE);
			duihuantext1.setText("1、本电影暂不支持在线选座功能,仅提电影兑换票购买;");
			duihuantext2.setText("2、电影兑换票限定在本影院现场选座使用,有效期1个月;");
			duihuantext3.setText("3、情人节、圣诞节等特殊节假日不可使用,兑换3D电影请咨询影院。");
		} else if (orderType.equals("2")) {
			duihuantext1.setVisibility(View.GONE);
			duihuantext2.setText("1、电影兑换票限定在本影院现场选座使用,有效期1个月;");
			duihuantext3.setText("2、情人节、圣诞节等特殊节假日不可使用,兑换3D电影请咨询影院。");
		}
		// 判断是否可以选座购票，如果既可以选座也可以兑换票，就让先现实选座，可切换到兑换票；
		// 如果只能支持其中一种，就让该界面显示，并且不可切换。
		if (orderType.equals("2")) {
			zuoweipiao.setVisibility(View.GONE);
			duihuanquan.setVisibility(View.VISIBLE);
			if (duihuanLinear.getVisibility() == View.VISIBLE) {
				duihuanLinear.setVisibility(View.INVISIBLE);
				Transition3d.executeRotation(duihuanLinear, seatsLinear, true, null);
			}
			startGetMovieSeatInfoTask();// 开启任务获取座位信息
		} else if (orderType.equals("0")) {
			zuoweipiao.setVisibility(View.GONE);
			duihuanquan.setVisibility(View.GONE);
			if (duihuanLinear.getVisibility() == View.VISIBLE) {
				duihuanLinear.setVisibility(View.INVISIBLE);
				Transition3d.executeRotation(duihuanLinear, seatsLinear, true, null);
			}
			startGetMovieSeatInfoTask();// 开启任务获取座位信息
		} else if (orderType.equals("1")) {
			zuoweipiao.setVisibility(View.GONE);
			duihuanquan.setVisibility(View.GONE);
			if (seatsLinear.getVisibility() == View.VISIBLE) {
				seatsLinear.setVisibility(View.INVISIBLE);
				Transition3d.executeRotation(seatsLinear, duihuanLinear, false, null);
			}
		}

	}

	private LinearLayout line1 = null;
	private LinearLayout line2 = null;
	private LinearLayout line3 = null;

	private LinearLayout highLight = null;
	private int[] backgound = new int[] { R.drawable.noon_slectedbg, R.drawable.noon_unslectedbg };

	private void initThreeLine() {
		line1 = (LinearLayout) findViewById(R.id.line1);
		line2 = (LinearLayout) findViewById(R.id.line2);
		line3 = (LinearLayout) findViewById(R.id.line3);
		highLight = line1;
		line1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				highLight.setBackgroundResource(backgound[1]);
				line1.setBackgroundColor(0x60919191);
				showJiantou(zao_jiantou, wu_jiantou, wan_jiantou);
				highLight = line1;
				selected = -1;
				if (seatAllList.size() != 0) {
					System.out.println("seatAllList   " + seatAllList.size());
					if (currentTimeBySeatList.size() != 0) {
						currentTimeBySeatList.clear();
					}
					for (int i = 0; i < seatAllList.size(); i++) {
						try {
							if (Integer.parseInt(seatAllList.get(i).time.split(":")[0].trim()) < 12) {
								currentTimeBySeatList.add(seatAllList.get(i));
								// System.out.println("currentTimeBySeatList    "
								// + currentTimeBySeatList.size());
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				// System.out.println("currentTimeBySeatList    " +
				// currentTimeBySeatList.size());
				ArrayList<MovieTicket> cuMovieTickets = getCurentMovieTicket(currentTimeBySeatList);
				cuMovieTicketsList = cuMovieTickets;
				initMovieTickets(cuMovieTickets);
				if (orderType.equals("2")) {
					if (duihuanquan.getVisibility() == View.GONE) {
						mMovieTicketListAdapter.setFlag(false);
						mMovieTicketListAdapter.notifyDataSetChanged();
					} else {
						mMovieTicketListAdapter.setFlag(true);
						mMovieTicketListAdapter.notifyDataSetChanged();
					}
				} else if (orderType.equals("0")) {
					mMovieTicketListAdapter.setFlag(true);
					mMovieTicketListAdapter.notifyDataSetChanged();
				} else if (orderType.equals("1")) {
					mMovieTicketListAdapter.setFlag(false);
					mMovieTicketListAdapter.notifyDataSetChanged();
				}
			}
		});

		line2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				highLight.setBackgroundResource(backgound[1]);
				line2.setBackgroundColor(0x60919191);
				showJiantou(wu_jiantou, zao_jiantou, wan_jiantou);
				highLight = line2;
				selected = -1;

				if (seatAllList.size() != 0) {
					if (currentTimeBySeatList.size() != 0) {
						currentTimeBySeatList.clear();
					}
					for (int i = 0; i < seatAllList.size(); i++) {
						try {
							if (Integer.parseInt(seatAllList.get(i).time.split(":")[0].trim()) >= 12
									&& Integer.parseInt(seatAllList.get(i).time.split(":")[0].trim()) < 18) {
								currentTimeBySeatList.add(seatAllList.get(i));
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				// System.out.println("currentTimeBySeatList    " +
				// currentTimeBySeatList.size());
				ArrayList<MovieTicket> cuMovieTickets = getCurentMovieTicket(currentTimeBySeatList);
				cuMovieTicketsList = cuMovieTickets;
				initMovieTickets(cuMovieTickets);
				if (orderType.equals("2")) {
					if (duihuanquan.getVisibility() == View.GONE) {
						mMovieTicketListAdapter.setFlag(false);
						mMovieTicketListAdapter.notifyDataSetChanged();
					} else {
						mMovieTicketListAdapter.setFlag(true);
						mMovieTicketListAdapter.notifyDataSetChanged();
					}
				} else if (orderType.equals("0")) {
					mMovieTicketListAdapter.setFlag(true);
					mMovieTicketListAdapter.notifyDataSetChanged();
				} else if (orderType.equals("1")) {
					mMovieTicketListAdapter.setFlag(false);
					mMovieTicketListAdapter.notifyDataSetChanged();
				}
			}
		});

		line3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				highLight.setBackgroundResource(backgound[1]);
				line3.setBackgroundColor(0x60919191);
				showJiantou(wan_jiantou, zao_jiantou, wu_jiantou);
				highLight = line3;
				selected = -1;

				if (seatAllList.size() != 0) {
					if (currentTimeBySeatList.size() != 0) {
						currentTimeBySeatList.clear();
					}
					for (int i = 0; i < seatAllList.size(); i++) {
						try {
							if (Integer.parseInt(seatAllList.get(i).time.split(":")[0].trim()) >= 18) {
								currentTimeBySeatList.add(seatAllList.get(i));
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				// System.out.println("currentTimeBySeatList    " +
				// currentTimeBySeatList.size());
				ArrayList<MovieTicket> cuMovieTickets = getCurentMovieTicket(currentTimeBySeatList);
				cuMovieTicketsList = cuMovieTickets;
				initMovieTickets(cuMovieTickets);
				if (orderType.equals("2")) {
					if (duihuanquan.getVisibility() == View.GONE) {
						mMovieTicketListAdapter.setFlag(false);
						mMovieTicketListAdapter.notifyDataSetChanged();
					} else {
						mMovieTicketListAdapter.setFlag(true);
						mMovieTicketListAdapter.notifyDataSetChanged();
					}
				} else if (orderType.equals("0")) {
					mMovieTicketListAdapter.setFlag(true);
					mMovieTicketListAdapter.notifyDataSetChanged();
				} else if (orderType.equals("1")) {
					mMovieTicketListAdapter.setFlag(false);
					mMovieTicketListAdapter.notifyDataSetChanged();
				}
			}
		});
	}

	private void showDialogUp() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(BuyTicketAct.this);
		builder.setMessage("订座功能即将开放，敬请期待！");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

			}
		});

		builder.create().show();
	}

	private void onclick() {
		duihuanquan = (ImageView) findViewById(R.id.duihuanquan);
		zuoweipiao = (ImageView) findViewById(R.id.zuoweipiao);
		yingyuanxiangqing = (ImageView) findViewById(R.id.yingyuanxiangqing);
		buy = (TextView) findViewById(R.id.duihuanquanBuy);

		seatsLinear = (LinearLayout) findViewById(R.id.seatsl);
		duihuanLinear = (LinearLayout) findViewById(R.id.duihuan);

		goseatselected = (TextView) findViewById(R.id.goseatselected);
		goseatselected.setVisibility(View.GONE);
		goseatselected.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// showDialogUp();
				if (!shareP.getUserName().equals("")) {
					if (selected > -1) {
						String companyId = "";
						for (int i = 0; i < movieCinema.mList.size(); i++) {
							for (int j = 0; j < movieCinema.mList.get(i).cinemas.size(); j++) {
								if (c_id.equals(movieCinema.mList.get(i).cinemas.get(j).c_id)) {
									companyId = movieCinema.mList.get(i).cinemas.get(j).companyId;
								}
							}
						}
						Intent intent = new Intent(mContext, SeatSelectedAct.class);
						if (movieSeat != null) {
							intent.putExtra("movieSeat", movieSeat);
						}
						intent.putExtra("moviecinema_id", c_id);
						intent.putExtra("companyId", companyId);
						intent.putExtra("movieSeat_ShowCode", movieSeat_ShowCode);
						intent.putExtra("movie_cinemaPrice", movie_cinemaPrice);// 将票价歘过去
						intent.putExtra("movieName", m_name);
						intent.putExtra("cover_image_url", cover_image_url);
						intent.putExtra("payphone", shareP.getUserName());
						if (TextUtils.isEmpty(movie_ShowTime)) {
							movie_ShowTime = zhangshu.getText().toString();
						}
						intent.putExtra("showTime", movie_ShowTime); // 放映时间
						movie_CinemaName = rgithl_textview.getText().toString();
						if (!TextUtils.isEmpty(movie_CinemaName)) {
							intent.putExtra("movieCinemaName", movie_CinemaName);// 电影院
						}
						intent.putExtra("cinemaRoom", cinemaRoom);
						intent.putExtra("cinemaTime", cinemaTime);
						System.out.println("cinemaTime--------------------------" + cinemaTime);
						mContext.startActivity(intent);
					} else {
						Toast.makeText(BuyTicketAct.this, "请选择时间和价格！", Toast.LENGTH_SHORT).show();
					}
				} else {
					Intent intent = new Intent(BuyTicketAct.this, UserLoginAct.class);
					mContext.startActivity(intent);
				}
			}
		});

		container_l.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!shareP.getUserName().equals("")) {
					if (selected > -1) {
						String companyId = "";
						for (int i = 0; i < movieCinema.mList.size(); i++) {
							for (int j = 0; j < movieCinema.mList.get(i).cinemas.size(); j++) {
								if (c_id.equals(movieCinema.mList.get(i).cinemas.get(j).c_id)) {
									companyId = movieCinema.mList.get(i).cinemas.get(j).companyId;
								}
							}
						}
						Intent intent = new Intent(mContext, SeatSelectedAct.class);
						if (movieSeat != null) {
							intent.putExtra("movieSeat", movieSeat);
						}
						intent.putExtra("moviecinema_id", c_id);
						intent.putExtra("companyId", companyId);
						intent.putExtra("movieSeat_ShowCode", movieSeat_ShowCode);
						System.out.println("price ===}     " + movie_cinemaPrice);
						intent.putExtra("movie_cinemaPrice", movie_cinemaPrice);// 将票价歘过去
						intent.putExtra("movieName", m_name);
						intent.putExtra("cover_image_url", cover_image_url);
						intent.putExtra("payphone", shareP.getUserName());
						if (TextUtils.isEmpty(movie_ShowTime)) {
							movie_ShowTime = zhangshu.getText().toString();
						}
						intent.putExtra("showTime", movie_ShowTime); // 放映时间
						movie_CinemaName = rgithl_textview.getText().toString();
						if (!TextUtils.isEmpty(movie_CinemaName)) {
							intent.putExtra("movieCinemaName", movie_CinemaName);// 电影院
						}
						intent.putExtra("cinemaRoom", cinemaRoom);
						intent.putExtra("cinemaTime", cinemaTime);
						mContext.startActivity(intent);
					} else {
						Toast.makeText(BuyTicketAct.this, "请选择时间和价格！", Toast.LENGTH_SHORT).show();
					}
				} else {
					Intent intent = new Intent(BuyTicketAct.this, UserLoginAct.class);
					mContext.startActivity(intent);
				}
			}
		});

		yingyuanxiangqing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (movieCinema != null) {
					try {
						if(c_id.equals("")){
							return;
						}
						Intent intent = new Intent(mContext, CinemaDesAct.class);
						intent.putExtra("c_id", c_id);
						for (int i = 0; i < movieCinema.mList.size(); i++) {
							for (int j = 0; j < movieCinema.mList.get(i).cinemas.size(); j++) {
								if (c_id.equals(movieCinema.mList.get(i).cinemas.get(j).c_id)) {
									intent.putExtra("movieCinema", movieCinema.mList.get(i).cinemas.get(j));
								}
							}
						}
						mContext.startActivity(intent);
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
				}
			}
		});

		// 购票，座位票
		zuoweipiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				zuoweipiao.setVisibility(View.GONE);
				duihuanquan.setVisibility(View.VISIBLE);

				// isSeatTimeFlag = true;
				// getData();
				duihuanLinear.setVisibility(View.INVISIBLE);
				Transition3d.executeRotation(duihuanLinear, seatsLinear, true, null);
				mMovieTicketListAdapter.setFlag(true);
				mMovieTicketListAdapter.notifyDataSetChanged();
			}
		});

		// 购票，兑换券
		duihuanquan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				zuoweipiao.setVisibility(View.VISIBLE);
				duihuanquan.setVisibility(View.GONE);
				seatsLinear.setVisibility(View.INVISIBLE);
				// isSeatTimeFlag = false;
				// getData();
				Transition3d.executeRotation(seatsLinear, duihuanLinear, false, null);
				mMovieTicketListAdapter.setFlag(false);
				mMovieTicketListAdapter.notifyDataSetChanged();
				// if (getSeat_LoadingProgressBar.isShown()) {
				getSeat_LoadingProgressBar.setVisibility(View.INVISIBLE);
				// }
			}
		});

		buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("priceList   " + priceList.size());
				System.out.println("priceList(Serializable)   " + (Serializable) priceList.size());
				String companyId = "";
				if (!shareP.getUserName().equals("")) {
					for (int i = 0; i < movieCinema.mList.size(); i++) {
						for (int j = 0; j < movieCinema.mList.get(i).cinemas.size(); j++) {
							if (c_id.equals(movieCinema.mList.get(i).cinemas.get(j).c_id)) {
								companyId = movieCinema.mList.get(i).cinemas.get(j).companyId;
							}
						}
					}
					Intent intent = new Intent(BuyTicketAct.this, BuyTicketExchangeAct.class);
					intent.putExtra("c_id", c_id);
					intent.putExtra("companyId", companyId);
					intent.putExtra("c_name", rgithl_textview.getText().toString());
					intent.putExtra("price", duihuanpiaoMoney.getText().toString());
					intent.putExtra("priceList", (Serializable) priceList);
					intent.putExtra("payphone", shareP.getUserName());
					mContext.startActivity(intent);
				} else {
					Intent intent = new Intent(BuyTicketAct.this, UserLoginAct.class);
					mContext.startActivity(intent);
				}

			}
		});
		onDateClick();
	}

	private int dateIndex = 0;

	private ImageView jian = null;
	private ImageView jia = null;
	private TextView zhangshu = null;

	private void onDateClick() {

		jian = (ImageView) findViewById(R.id.jian);// 左箭头
		jia = (ImageView) findViewById(R.id.jia);// 右箭头
		zhangshu = (TextView) findViewById(R.id.zhangshu);// 中间的点击text

		jia.setOnClickListener(new OnClickListener() {// 向后一天
			public void onClick(View v) {
				if (currentDataPosition < dataList.size() - 1) {

					currentDataPosition += 1;
					selected = -1;
					System.out.println("currentDataPosition  +   " + currentDataPosition);
					jian.setVisibility(View.VISIBLE);
					if (currentDataPosition >= dataList.size() - 1) {
						jia.setVisibility(View.INVISIBLE);
					}
					highLight.setBackgroundDrawable(null);
					changciBySeatByCurrentPositionData(currentDataPosition);
					visibleJiantou(zao_jiantou, wu_jiantou, wan_jiantou);
					ArrayList<MovieTicket> curentMovieTicket = getCurentMovieTicket(seatAllList);
					cuMovieTicketsList = curentMovieTicket;
					initMovieTickets(curentMovieTicket);
					try {
						String data = sDateFormat.format(new java.util.Date());
						long systemData = sDateFormat.parse(data).getTime();// 得到系统时间
						if (sDateFormat.parse(dataList.get(currentDataPosition)).getTime() == systemData) {// 当天
							zhangshu.setText(dataList.get(currentDataPosition) + "(今)");
						} else {
							zhangshu.setText(dataList.get(currentDataPosition));
						}

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (orderType.equals("2")) {
						if (duihuanquan.getVisibility() == View.GONE) {
							mMovieTicketListAdapter.setFlag(false);
							mMovieTicketListAdapter.notifyDataSetChanged();
						} else {
							mMovieTicketListAdapter.setFlag(true);
							mMovieTicketListAdapter.notifyDataSetChanged();
						}
					} else if (orderType.equals("0")) {
						mMovieTicketListAdapter.setFlag(true);
						mMovieTicketListAdapter.notifyDataSetChanged();
					} else if (orderType.equals("1")) {
						mMovieTicketListAdapter.setFlag(false);
						mMovieTicketListAdapter.notifyDataSetChanged();
					}

				}

			}

		});
		;
		jian.setOnClickListener(new OnClickListener() {// 向前一天
			public void onClick(View v) {
				if (currentDataPosition > 0) {
					currentDataPosition -= 1;
					selected = -1;
					System.out.println("currentDataPosition   -  " + currentDataPosition);

					if (currentDataPosition == 0) {
						jian.setVisibility(View.INVISIBLE);
						jia.setVisibility(View.VISIBLE);
					}
					highLight.setBackgroundDrawable(null);
					changciBySeatByCurrentPositionData(currentDataPosition);
					visibleJiantou(zao_jiantou, wu_jiantou, wan_jiantou);
					ArrayList<MovieTicket> curentMovieTicket = getCurentMovieTicket(seatAllList);
					cuMovieTicketsList = curentMovieTicket;
					initMovieTickets(curentMovieTicket);
					try {
						String data = sDateFormat.format(new java.util.Date());
						long systemData = sDateFormat.parse(data).getTime();// 得到系统时间
						if (sDateFormat.parse(dataList.get(currentDataPosition)).getTime() == systemData) {// 当天
							zhangshu.setText(dataList.get(currentDataPosition) + "(今)");
						} else {
							zhangshu.setText(dataList.get(currentDataPosition));
						}

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (orderType.equals("2")) {
						if (duihuanquan.getVisibility() == View.GONE) {
							mMovieTicketListAdapter.setFlag(false);
							mMovieTicketListAdapter.notifyDataSetChanged();
						} else {
							mMovieTicketListAdapter.setFlag(true);
							mMovieTicketListAdapter.notifyDataSetChanged();
						}
					} else if (orderType.equals("0")) {
						mMovieTicketListAdapter.setFlag(true);
						mMovieTicketListAdapter.notifyDataSetChanged();
					} else if (orderType.equals("1")) {
						mMovieTicketListAdapter.setFlag(false);
						mMovieTicketListAdapter.notifyDataSetChanged();
					}
				}
			}

		});
		zhangshu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				visibleJiantou(zao_jiantou, wu_jiantou, wan_jiantou);
				line1.setBackgroundDrawable(null);
				line2.setBackgroundDrawable(null);
				line3.setBackgroundDrawable(null);
				ArrayList<MovieTicket> curentMovieTicket = getCurentMovieTicket(seatAllList);
				cuMovieTicketsList = curentMovieTicket;
				initMovieTickets(curentMovieTicket);

				if (orderType.equals("2")) {
					if (duihuanquan.getVisibility() == View.GONE) {
						mMovieTicketListAdapter.setFlag(false);
						mMovieTicketListAdapter.notifyDataSetChanged();
					} else {
						mMovieTicketListAdapter.setFlag(true);
						mMovieTicketListAdapter.notifyDataSetChanged();
					}
				} else if (orderType.equals("0")) {
					mMovieTicketListAdapter.setFlag(true);
					mMovieTicketListAdapter.notifyDataSetChanged();
				} else if (orderType.equals("1")) {
					mMovieTicketListAdapter.setFlag(false);
					mMovieTicketListAdapter.notifyDataSetChanged();
				}
			}
		});
	}

	// -------------------电影票----------START
	private void initMovieTickets(ArrayList<MovieTicket> list) {
		if (movie_listview != null) {
			// mMovieTicketListAdapter.notifyDataSetChanged();
			mMovieTicketListAdapter = null;
		}

		mMovieTicketListAdapter = new MovieTicketListAdapter(list);
		movie_listview.setAdapter(mMovieTicketListAdapter);

		if (list.size() == 0) {
			no_changci.setVisibility(View.VISIBLE);
		} else {
			no_changci.setVisibility(View.GONE);
		}
		movie_listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> mAdapterView, View parent, int position, long id) {
				Log.v("cxm", "position==" + position);
				String curentData = dataList.get(currentDataPosition);
				System.out.println("curentData           " + curentData);

				System.out.println("data         " + data);
				Time t = new Time();
				t.setToNow();
				String mHour = t.hour + "";
				String mMinuts = t.minute + "";
				if (t.minute < 10) {
					mMinuts = 0 + "" + t.minute;
				}
				if (t.hour < 10) {
					mHour = 0 + "" + t.hour;
				}
				// cancheposition = position;
				// if(isSeatTimeFlag){
				if (cinemaPrepareMovieCinema != null) {
					if (cinemaPrepareMovieCinema.list != null) {
						if (cuMovieTicketsList != null && cuMovieTicketsList.size() > 0) {
							if (mMovieTicketListAdapter.getFlag()) {
								System.out.println("cuMovieTicketsList.get(position).data           " + cuMovieTicketsList.get(position).data);
								if (cuMovieTicketsList.get(position).data.equals(data)) {
									System.out.println("time----"
											+ Integer.parseInt(cuMovieTicketsList.get(position).time.split(":")[0]
													+ cuMovieTicketsList.get(position).time.split(":")[1]) + "   ----------------   "
											+ Integer.parseInt(mHour + "" + mMinuts));
									if (Integer.parseInt(cuMovieTicketsList.get(position).time.split(":")[0]
											+ cuMovieTicketsList.get(position).time.split(":")[1]) > Integer.parseInt(mHour + "" + mMinuts)) {

										selected = position;
										mMovieTicketListAdapter.notifyDataSetChanged();
										movie_cinemaPrice = cuMovieTicketsList.get(position).price;
										movieSeat_ShowCode = cuMovieTicketsList.get(position).showcode;
										reStartShowCode = cuMovieTicketsList.get(position).showcode;
										if (cancheSeatData.containsKey(cuMovieTicketsList.get(position).showcode)) {
											System.out.println("ontiem---containsKey     " + cuMovieTicketsList.get(position).showcode);
											removeViews();
											if (cancheSeatData.get(cuMovieTicketsList.get(position).showcode) == null) {
												cinemaRoom = cuMovieTicketsList.get(position).room;
												cinemaTime = cuMovieTicketsList.get(position).time;
												if(getMovieSeatInfoTask!=null){
													getMovieSeatInfoTask.cancel(true);
												}
												getMovieSeatInfoTask = new GetMovieSeatInfoTask(BuyTicketAct.this, "座位获取中...");
												getMovieSeatInfoTask.execute(cuMovieTicketsList.get(position).showcode);
											} else {
												computerMostColAndRow(cancheSeatData.get(cuMovieTicketsList.get(position).showcode));
												setSeatLayout(mostCol, mostRow, cancheSeatData.get(cuMovieTicketsList.get(position).showcode));
												movieSeat = cancheSeatData.get(cuMovieTicketsList.get(position).showcode);
												cinemaRoom = cuMovieTicketsList.get(position).room;
												cinemaTime = cuMovieTicketsList.get(position).time;
											}

										} else {
											System.out.println("ontiem-=--!containsKey     " + cuMovieTicketsList.get(position).showcode);
											removeViews();
											cinemaRoom = cuMovieTicketsList.get(position).room;
											cinemaTime = cuMovieTicketsList.get(position).time;
											if(getMovieSeatInfoTask!=null){
												getMovieSeatInfoTask.cancel(true);
											}
											getMovieSeatInfoTask = new GetMovieSeatInfoTask(BuyTicketAct.this, "座位获取中...");
											getMovieSeatInfoTask.execute(cuMovieTicketsList.get(position).showcode);
										}
									}
								} else if (Integer.parseInt(cuMovieTicketsList.get(position).data.split("-")[0]
										+ cuMovieTicketsList.get(position).data.split("-")[1] + cuMovieTicketsList.get(position).data.split("-")[2]) < Integer
										.parseInt(data.split("-")[0] + data.split("-")[1] + data.split("-")[2])) {

								} else {
									selected = position;
									mMovieTicketListAdapter.notifyDataSetChanged();
									movie_cinemaPrice = cuMovieTicketsList.get(position).price;
									movieSeat_ShowCode = cuMovieTicketsList.get(position).showcode;
									System.out.println("cuMovieTicketsList.get(position).showcode          " + movieSeat_ShowCode);
									reStartShowCode = cuMovieTicketsList.get(position).showcode;
									if (cancheSeatData.containsKey(cuMovieTicketsList.get(position).showcode)) {

										removeViews();
										if (cancheSeatData.get(cuMovieTicketsList.get(position).showcode) == null) {
											cinemaRoom = cuMovieTicketsList.get(position).room;
											cinemaTime = cuMovieTicketsList.get(position).time;
											if(getMovieSeatInfoTask!=null){
												getMovieSeatInfoTask.cancel(true);
											}
											getMovieSeatInfoTask = new GetMovieSeatInfoTask(BuyTicketAct.this, "座位获取中...");
											getMovieSeatInfoTask.execute(cuMovieTicketsList.get(position).showcode);
										} else {
											computerMostColAndRow(cancheSeatData.get(cuMovieTicketsList.get(position).showcode));
											setSeatLayout(mostCol, mostRow, cancheSeatData.get(cuMovieTicketsList.get(position).showcode));
											movieSeat = cancheSeatData.get(cuMovieTicketsList.get(position).showcode);
											cinemaRoom = cuMovieTicketsList.get(position).room;
											cinemaTime = cuMovieTicketsList.get(position).time;
										}
									} else {
										removeViews();
										cinemaRoom = cuMovieTicketsList.get(position).room;
										cinemaTime = cuMovieTicketsList.get(position).time;
										if(getMovieSeatInfoTask!=null){
											getMovieSeatInfoTask.cancel(true);
										}
										getMovieSeatInfoTask = new GetMovieSeatInfoTask(BuyTicketAct.this, "座位获取中...");
										getMovieSeatInfoTask.execute(cuMovieTicketsList.get(position).showcode);
									}
								}

							}
						}
					}
					// }
				}
			}
		});

	}

	private int selected = -1;
	private boolean ismListViewHidden = true;
	private ListView movie_listview = null;
	private MovieTicketListAdapter mMovieTicketListAdapter = null;
	private MovieTicketList mMovieTicketList = null;

	public class MovieTicketListAdapter extends BaseAdapter {

		private boolean flag = true;
		private ArrayList<MovieTicket> list = null;
		private LayoutInflater layoutFlater;
		Time t = new Time();

		public MovieTicketListAdapter(ArrayList<MovieTicket> list) {
			super();
			layoutFlater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.list = list;
		}

		public void setFlag(boolean flag) {
			this.flag = flag;
		}

		public boolean getFlag() {
			return flag;
		}

		public int getCount() {
			return list.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolderListViewItme holder;
			if (convertView == null) {
				convertView = layoutFlater.inflate(R.layout.movie_ticket_list_item, null);
				holder = new ViewHolderListViewItme();
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.price = (TextView) convertView.findViewById(R.id.movie_order_prices);
				holder.listl = (LinearLayout) convertView.findViewById(R.id.listl);
				// holder.room = (TextView) convertView.findViewById(R.id.type);
				holder.room = (TextView) convertView.findViewById(R.id.room);
				holder.roomLinear = (LinearLayout) convertView.findViewById(R.id.roomLinear);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolderListViewItme) convertView.getTag();
				holder.listl.setBackgroundResource(itembg[1]);
			}
			t.setToNow();
			// int mHour = t.hour;
			// int mMinuts = t.minute;
			String mHour = t.hour + "";
			String mMinuts = t.minute + "";
			if (t.minute < 10) {
				mMinuts = 0 + "" + t.minute;
			}
			if (t.hour < 10) {
				mHour = 0 + "" + t.hour;
			}
			MovieTicket bean = list.get(position);
			if (!bean.time.equals("")) {
				try {
					// System.out.println("bean.data==    "+bean.data+"      data===   "+data);
					if (bean.data.equals(data)) {
						if (Integer.parseInt(bean.time.split(":")[0] + bean.time.split(":")[1]) < Integer.parseInt(mHour + mMinuts)) {
							holder.time.setTextColor(0xff888888);
							holder.price.setTextColor(0xff888888);
							holder.room.setTextColor(0xff888888);
						} else {
							holder.time.setTextColor(0xffffffff);
							holder.price.setTextColor(0xff257DA8);
							holder.room.setTextColor(0xffffffff);
						}
					} else if (Integer.parseInt(bean.data.split("-")[0] + bean.data.split("-")[1] + bean.data.split("-")[2]) < Integer.parseInt(data
							.split("-")[0] + data.split("-")[1] + data.split("-")[2])) {
						holder.time.setTextColor(0xff888888);
						holder.price.setTextColor(0xff888888);
						holder.room.setTextColor(0xff888888);
					} else {
						holder.time.setTextColor(0xffffffff);
						holder.price.setTextColor(0xff257DA8);
						holder.room.setTextColor(0xffffffff);
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
				// if (mHour == Integer.parseInt(bean.time.split(":")[0])) {
				// if (Integer.parseInt(bean.time.split(":")[1]) < mMinuts) {
				// holder.time.setTextColor(0xff888888);
				// holder.price.setTextColor(0xff888888);
				// holder.room.setTextColor(0xff888888);
				// }
				// } else if (mHour > Integer.parseInt(bean.time.split(":")[0]))
				// {
				// holder.time.setTextColor(0xff888888);
				// holder.price.setTextColor(0xff888888);
				// holder.room.setTextColor(0xff888888);
				// } else {
				// holder.time.setTextColor(0xffffffff);
				// holder.price.setTextColor(0xff257DA8);
				// holder.room.setTextColor(0xffffffff);
				// }
				holder.time.setText(bean.time);
				holder.time.setVisibility(View.VISIBLE);
			} else
				holder.time.setVisibility(View.GONE);

			if (bean.price.equals("")) {
				holder.time.setTextColor(0xff888888);
				holder.price.setTextColor(0xff888888);
				holder.room.setTextColor(0xff888888);
			}
			if (!bean.room.equals("") && !bean.room.equals("null")) {
				holder.room.setText(bean.room);
				holder.room.setVisibility(View.VISIBLE);
				holder.roomLinear.setVisibility(View.VISIBLE);

			} else {
				holder.room.setVisibility(View.GONE);
				holder.roomLinear.setVisibility(View.GONE);
			}
			if (!bean.price.equals("")) {
				if (flag) {
					holder.price.setText((int) Float.parseFloat(bean.price) + "元");
					holder.price.setVisibility(View.VISIBLE);
					holder.room.setVisibility(View.VISIBLE);
					holder.roomLinear.setVisibility(View.VISIBLE);
					if (!bean.time.equals("")) {
						try {
							// System.out.println("bean.data==    "+bean.data+"      data===   "+data);
							if (bean.data.equals(data)) {
								if (Integer.parseInt(bean.time.split(":")[0] + bean.time.split(":")[1]) < Integer.parseInt(mHour + mMinuts)) {
									holder.time.setTextColor(0xff888888);
									holder.price.setTextColor(0xff888888);
									holder.room.setTextColor(0xff888888);
								} else {
									holder.time.setTextColor(0xffffffff);
									holder.price.setTextColor(0xff257DA8);
									holder.room.setTextColor(0xffffffff);
								}
							} else if (Integer.parseInt(bean.data.split("-")[0] + bean.data.split("-")[1] + bean.data.split("-")[2]) < Integer
									.parseInt(data.split("-")[0] + data.split("-")[1] + data.split("-")[2])) {
								holder.time.setTextColor(0xff888888);
								holder.price.setTextColor(0xff888888);
								holder.room.setTextColor(0xff888888);
							} else {
								holder.time.setTextColor(0xffffffff);
								holder.price.setTextColor(0xff257DA8);
								holder.room.setTextColor(0xffffffff);
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (IndexOutOfBoundsException e) {
							e.printStackTrace();
						}
						// if (mHour ==
						// Integer.parseInt(bean.time.split(":")[0])) {
						// if (Integer.parseInt(bean.time.split(":")[1]) <
						// mMinuts) {
						// holder.time.setTextColor(0xff888888);
						// holder.price.setTextColor(0xff888888);
						// holder.room.setTextColor(0xff888888);
						// }
						// } else if (mHour >
						// Integer.parseInt(bean.time.split(":")[0])) {
						// holder.time.setTextColor(0xff888888);
						// holder.price.setTextColor(0xff888888);
						// holder.room.setTextColor(0xff888888);
						// } else {
						// holder.time.setTextColor(0xffffffff);
						// holder.price.setTextColor(0xff257DA8);
						// holder.room.setTextColor(0xffffffff);
						// }
						holder.time.setText(bean.time);
						holder.time.setVisibility(View.VISIBLE);
					}
				} else {
					holder.time.setTextColor(0xff888888);
					holder.price.setTextColor(0xff888888);
					holder.room.setTextColor(0xff888888);
					holder.price.setVisibility(View.GONE);
					holder.room.setVisibility(View.GONE);
					holder.roomLinear.setVisibility(View.GONE);
				}
			} else {
				holder.price.setVisibility(View.GONE);
				holder.room.setVisibility(View.GONE);
				holder.roomLinear.setVisibility(View.GONE);
			}

			// if(!bean.type.equals("")&&!bean.type.equals("null")){
			// holder.type.setText(bean.type);
			// holder.type.setVisibility(View.VISIBLE);
			// }else{
			// holder.type.setVisibility(View.GONE);
			// }

			if (selected != -1) {
				if (selected == position) {
					holder.listl.setBackgroundColor(0x60919191);
				}
			} else if (selected == -1) {
				holder.listl.setBackgroundColor(0x00000000);
			}

			return convertView;
		}
	}

	private int[] itembg = new int[] { R.drawable.noon_slectedbg, R.drawable.noon_unslectedbg };

	private static class ViewHolderListViewItme {
		public TextView time = null;
		public TextView price = null;
		public LinearLayout listl = null;
		public TextView type = null;
		public TextView movitype = null;
		public TextView room = null;
		public LinearLayout roomLinear = null;

	}

	// -------------------电影票-----------END

	// -------------------座位-----------START

	private void initWheel() {

		if (cinemaPrepareMovie != null && movieCinema != null)
			if (cinemaPrepareMovie.list != null && movieCinema.mList != null) {
				boolean flag = true;

				for (int i = 0; i < movieCinema.mList.size(); i++) {
					ArrayList<MovieCinema> linshi_list = new ArrayList<MovieCinema>();
					for (int j = 0; j < movieCinema.mList.get(i).cinemas.size(); j++) {

						for (int k = 0; k < cinemaPrepareMovie.list.size(); k++) {
							if (movieCinema.mList.get(i).cinemas.get(j).c_id.equals(cinemaPrepareMovie.list.get(k).c_id)) {

								linshi_list.add(movieCinema.mList.get(i).cinemas.get(j));
								flag = false;
								break;

							}
						}
					}
					if (flag == false) {
						ArrayList<MovieCinema> linshiListSeat = new ArrayList<MovieCinema>();
						ArrayList<MovieCinema> linshiList1 = new ArrayList<MovieCinema>();
						ArrayList<MovieCinema> linshiList2 = new ArrayList<MovieCinema>();
						quyuList.add(movieCinema.mList.get(i));
						for (int j = 0; j < linshi_list.size(); j++) {
							if (linshi_list.get(j).order_type.equals("0") || linshi_list.get(j).order_type.equals("2")) {
								linshiListSeat.add(linshi_list.get(j));
							} else {
								linshiList1.add(linshi_list.get(j));
							}
						}
						linshiList2.addAll(linshiListSeat);
						linshiList2.addAll(linshiList1);
						cList.add(linshiList2);
						flag = true;
					}
				}
				setChooseWheel();
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
					time1 = Integer.parseInt(itemBean1.split("-")[0] + itemBean1.split("-")[1] + itemBean1.split("-")[2]);
				}
				if (itemBean2 != null && !"".equals(itemBean2)) {
					time2 = Integer.parseInt(itemBean2.split("-")[0] + itemBean2.split("-")[1] + itemBean2.split("-")[2]);
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
				day = sDateFormat.parse(list.get(i)).getDate();
				copyList.add(day);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
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
		return orderedlist;
	}

	/**
	 * 获取到当前天的所有电影票list
	 * 
	 * @return
	 */
	public ArrayList<MovieTicket> getCurentMovieTicket(ArrayList<MovieTicket> currentList) {
		ArrayList<MovieTicket> curentMovieTicket = new ArrayList<MovieTicket>();
		try {
			String curentData = dataList.get(currentDataPosition);
			if (currentList != null && currentList.size() > 0) {
				for (int i = 0; i < currentList.size(); i++) {
					if (currentList.get(i).data.equals(curentData)) {
						curentMovieTicket.add(currentList.get(i));
					}
				}
//				Log.v("cxm", "curentMovieTicket.size()==" + curentMovieTicket.size());
			}

			Comparator<MovieTicket> comparator;
			comparator = new TimeLowToHighComparator();
			Collections.sort(curentMovieTicket, comparator);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return curentMovieTicket;
	}

	public class TimeLowToHighComparator implements Comparator<MovieTicket> {
		@Override
		public int compare(MovieTicket itemBean1, MovieTicket itemBean2) {
			int time1 = 0;
			int time2 = 0;
			try {
				if (itemBean1.time != null && !"".equals(itemBean1.time)) {
					// meters1 = Double.parseDouble(itemBean1.meters);
					time1 = Integer.parseInt(itemBean1.time.split(":")[0] + itemBean1.time.split(":")[1]);
				}
				if (itemBean2.time != null && !"".equals(itemBean2.time)) {
					time2 = Integer.parseInt(itemBean2.time.split(":")[0] + itemBean2.time.split(":")[1]);
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

	/**
	 * 处理不同时间下场次的信息
	 */
	private void changciData() {
		Comparator<String> comparatorData;
		dataList.clear();
		if (timeList.size() > 0) {
			timeList.clear();
		}
		if (cinemaPrepareMovieCinema != null) {
			if (cinemaPrepareMovieCinema.list != null) {

				// 得到datalist数据
				for (int i = 0; i < cinemaPrepareMovieCinema.list.size(); i++) {// 获取时间datalist
					Log.v("cxm", "cinemaPrepareMovieCinema.list.get(i).day_time==" + cinemaPrepareMovieCinema.list.get(i).day_time);
					if (!dataList.contains(cinemaPrepareMovieCinema.list.get(i).day_time)) {
						dataList.add(cinemaPrepareMovieCinema.list.get(i).day_time);
					}
				}
				// dataList = orderData(dataList);
				comparatorData = new DataLowToHighComparator();
				Collections.sort(dataList, comparatorData);
				for (int i = 0; i < dataList.size(); i++) {// 获取时间timelist
					ArrayList<TimeGrid> times = new ArrayList<TimeGrid>();// 一天的时间安排
					Log.v("cxm", "dataList--" + i + "==" + dataList.get(i));
					for (int j = 0; j < cinemaPrepareMovieCinema.list.size(); j++) {
						if (cinemaPrepareMovieCinema.list.get(j).day_time.equals(dataList.get(i))) {
							times.add(new TimeGrid(cinemaPrepareMovieCinema.list.get(j).s_time,cinemaPrepareMovieCinema.list.get(j).price,cinemaPrepareMovieCinema.list.get(j).room,cinemaPrepareMovieCinema.list.get(j).day_time, cinemaPrepareMovieCinema.list
									.get(j).movieSetName, cinemaPrepareMovieCinema.list.get(j).showCode,
									cinemaPrepareMovieCinema.list.get(j).s_time));
						}
					}
					timeList.add(times);
				}

				changciBySeatByCurrentPositionData(0);
			} else if (cinemaPrepareMovieCinema.byBill != null) {
				int zaoNum = 0, zhongNum = 0, wanNum = 0;
				if (dataList.size() == 0) {
					dataList.add(cinemaPrepareMovieCinema.byBill.day_time);
				}
				String s_time = cinemaPrepareMovieCinema.byBill.s_time;
				if (cinemaPrepareMovieCinema.byBill.s_time.contains("|")) {
					s_time = cinemaPrepareMovieCinema.byBill.s_time.replaceAll("\\|", "");
				}
				String times[] = s_time.split("\\ ");

				for (int i = 0; i < times.length; i++) {

					try {
						if (Integer.parseInt(times[i].split(":")[0]) < 12) {
							// System.out.println("time00000000  <12         "+times[i]);
							zaoNum++;
						} else if (Integer.parseInt(times[i].split(":")[0]) >= 12 && Integer.parseInt(times[i].split(":")[0].trim()) < 18) {
							// System.out.println("time00000000    >12<18       "+times[i]);
							zhongNum++;
						} else if (Integer.parseInt(times[i].split(":")[0]) >= 18) {
							wanNum++;
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				zaoshang.setText(zaoNum + "");
				zhongwu.setText(zhongNum + "");
				wanshang.setText(wanNum + "");

			}
			for (int i = 0; i < dataList.size(); i++) {
				if (dataList.get(i).equals(prepareData)) {
//					System.out.println("currentDataPosition----------------" + currentDataPosition);
					currentDataPosition = i;
					break;
				}
			}
			if (dataList.size() != 0) {

				String data = sDateFormat.format(new java.util.Date());

//				System.out.println("data===========" + data);
//				System.out.println("dataList.get(currentDataPosition)             ===" + dataList.get(currentDataPosition));
				if (data.equals(dataList.get(currentDataPosition))) {
					zhangshu.setText(dataList.get(currentDataPosition) + "(今)");
				} else {
					zhangshu.setText(dataList.get(currentDataPosition));
				}

				// try {
				// long todayData = 0L;
				// int mostZaoDayXiaBiao = 0;
				// if (cinemaPrepareMovieCinema.list != null &&
				// cinemaPrepareMovieCinema.list.size() > 0) {// 设置座位票数据
				//
				// int mostZaoDay =
				// sDateFormat.parse(cinemaPrepareMovieCinema.list.get(0).day_time).getDay();
				// for (int i = 0; i < cinemaPrepareMovieCinema.list.size();
				// i++) {// 遍历找今天
				// todayData =
				// sDateFormat.parse(cinemaPrepareMovieCinema.list.get(i).day_time).getTime();
				// if (i > 0) {
				// int tempday =
				// sDateFormat.parse(cinemaPrepareMovieCinema.list.get(i).day_time).getDay();
				// if (mostZaoDay > tempday) {
				// mostZaoDay = tempday;
				// mostZaoDayXiaBiao = i;
				// }
				// }
				// long systemData = sDateFormat.parse(data).getTime();
				// System.out.println("systemData------" + systemData +
				// "          " + todayData);
				// if (todayData == systemData) {
				// zhangshu.setText(cinemaPrepareMovieCinema.list.get(i).day_time
				// + "(今)");
				// }
				// }
				// System.out.println(cinemaPrepareMovieCinema.list.size());
				// if (TextUtils.isEmpty(zhangshu.getText().toString())) {//
				// 返回数据没有今天
				// zhangshu.setText(cinemaPrepareMovieCinema.list.get(mostZaoDayXiaBiao).day_time);
				// }
				// } else if (cinemaPrepareMovieCinema.byBill != null) {//
				// 设置无座票的今天
				// todayData =
				// sDateFormat.parse(cinemaPrepareMovieCinema.byBill.day_time).getTime();
				// long systemData = sDateFormat.parse(data).getTime();
				// if (todayData == systemData) {
				// zhangshu.setText(cinemaPrepareMovieCinema.byBill.day_time +
				// "(今)");
				// } else {
				// zhangshu.setText(cinemaPrepareMovieCinema.byBill.day_time);
				// }
				// }
				// } catch (ParseException e) {
				// e.printStackTrace();
				// }
			}

			if (dataList.size() <= 1) {
				jia.setVisibility(View.INVISIBLE);
				jian.setVisibility(View.INVISIBLE);
			} else {
				if (currentDataPosition == 0) {
					jia.setVisibility(View.VISIBLE);
					jian.setVisibility(View.INVISIBLE);
				} else if (currentDataPosition > 0) {
					jia.setVisibility(View.INVISIBLE);
					jian.setVisibility(View.VISIBLE);
				}
			}
		}
	}

	/**
	 * 通过座位票数据得来的场次数据（计算早中晚场次数据）
	 * 
	 * @param currentPosition
	 */
	private void changciBySeatByCurrentPositionData(int currentPosition) {
		int zaoNum = 0, zhongNum = 0, wanNum = 0;
		if (cinemaPrepareMovieCinema.list != null) {
			for (int i = 0; i < timeList.get(currentPosition).size(); i++) {
				try {
					if (Integer.parseInt(timeList.get(currentPosition).get(i).time.split(":")[0].trim()) < 12) {
						zaoNum++;
					} else if (Integer.parseInt(timeList.get(currentPosition).get(i).time.split(":")[0].trim()) >= 12
							&& Integer.parseInt(timeList.get(currentPosition).get(i).time.split(":")[0].trim()) < 18) {
						zhongNum++;
					} else if (Integer.parseInt(timeList.get(currentPosition).get(i).time.split(":")[0].trim()) >= 18) {
						wanNum++;
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			zaoshang.setText(zaoNum + "");
			zhongwu.setText(zhongNum + "");
			wanshang.setText(wanNum + "");
		}
	}

	/**
	 * 计算最大的行和列
	 */
	private void computerMostColAndRow(MovieSeatInfo movieSeatInfo) {
		int tempCol, tempRow;
		if (movieSeatInfo != null) {
			if (movieSeatInfo.seats != null && movieSeatInfo.seats.size() > 0) {
				mostCol = Integer.parseInt(movieSeatInfo.seats.get(0).graphCol);
				mostRow = Integer.parseInt(movieSeatInfo.seats.get(0).graphRow);
				for (int i = 1; i < movieSeatInfo.seats.size(); i++) {
					tempCol = Integer.parseInt(movieSeatInfo.seats.get(i).graphCol);
					tempRow = Integer.parseInt(movieSeatInfo.seats.get(i).graphRow);
					if (mostCol < tempCol) {
						mostCol = tempCol;
					}
					if (mostRow < tempRow) {
						mostRow = tempRow;
					}
				}
			}
		}
	}

	/**
	 * 开启获取座位的任务
	 * 
	 * @param movieSeat_ShowCode
	 */
	private void startGetMovieSeatInfoTask() {
		selected = -1;

		System.out.println("time------------" + time);
		// Log.v("cxm",
		// "startGetMovieSeatInfoTask---fromMoviePrepareAct=="+fromMoviePrepareAct);
		if (fromMoviePrepareAct) {// 从排期购票
			if (!TextUtils.isEmpty(movieSeat_ShowCode)) {
				if (cinemaPrepareMovieCinema != null) {
					try {
						System.out.println("data------0----------" + prepareData);
						if (cinemaPrepareMovieCinema.list != null && cinemaPrepareMovieCinema.list.size() > 0) {
							for (int i = 0; i < getCurentMovieTicket(seatAllList).size(); i++) {
								System.out.println("data-----1--------------" + getCurentMovieTicket(seatAllList).get(i).data);
								// System.out.println("time  - i  " +
								// getCurentMovieTicket(seatAllList).get(i).time);
								if (Integer.parseInt(getCurentMovieTicket(seatAllList).get(i).data.split("-")[0]
										+ getCurentMovieTicket(seatAllList).get(i).data.split("-")[1]
										+ getCurentMovieTicket(seatAllList).get(i).data.split("-")[2]) < Integer.parseInt(data.split("-")[0]
										+ data.split("-")[1] + data.split("-")[2])) {

								} else {
									if (getCurentMovieTicket(seatAllList).get(i).time.equals(time)
											&& getCurentMovieTicket(seatAllList).get(i).data.equals(prepareData)) {
										selected = i;
									}
								}
							}

							// Time t = new Time();
							// t.setToNow();
							// String mHour = t.hour+"";
							// String mMinuts = t.minute+"";
							// if(t.minute<10){
							// mMinuts = 0+""+t.minute;
							// }
							// if(t.hour<10){
							// mHour = 0+""+t.hour;
							// }
							//
							// System.out.println("data=========   " + mHour +
							// "       " + mMinuts);
							// // if(isCanLoadNewSeat){
							// for (int i = 0; i <
							// getCurentMovieTicket(seatAllList).size(); i++) {
							// if
							// (Integer.parseInt(getCurentMovieTicket(seatAllList).get(i).time.split(":")[0]+getCurentMovieTicket(seatAllList).get(i).time.split(":")[1])
							// >=Integer.parseInt(mHour+mMinuts)) {
							// selected = i;
							// break;
							// }
							// }
							// if (selected == -1) {
							//
							// for (int i = 0; i <
							// getCurentMovieTicket(seatAllList).size(); i++) {
							// if
							// (Integer.parseInt(getCurentMovieTicket(seatAllList).get(i).time.split(":")[0])
							// > mHour) {
							// selected = i;
							// break;
							// }
							// }
							// }
							System.out.println("selected           " + selected);
							if (selected == -1) {
								goseatselected.setVisibility(View.GONE);
							}
							if (selected != -1) {
								// goseatselected.setVisibility(View.VISIBLE);
								// System.out.println("tim------------       " +
								// getCurentMovieTicket(seatAllList).get(selected).time);
								if (cinemaPrepareMovieCinema.list != null && cinemaPrepareMovieCinema.list.size() > 0) {
									movieSeat_ShowCode = getCurentMovieTicket(seatAllList).get(selected).showcode;
									if (!TextUtils.isEmpty(movieSeat_ShowCode)) {
										reStartShowCode = movieSeat_ShowCode;
										// System.out.println("reStartShowCode      "+reStartShowCode);
										if(getMovieSeatInfoTask!=null){
											getMovieSeatInfoTask.cancel(true);
										}
										getMovieSeatInfoTask = new GetMovieSeatInfoTask(BuyTicketAct.this, "");
										getMovieSeatInfoTask.execute(movieSeat_ShowCode);
									}
								}
							}
							if (selected >= 0) {
								cinemaRoom = cuMovieTicketsList.get(selected).room;
								cinemaTime = cuMovieTicketsList.get(selected).time;
							}
							movie_listview.setSelection(selected);
							// movieSeat_ShowCode =
							// getCurentMovieTicket(seatAllList).get(selected).showcode;
							// reStartShowCode = movieSeat_ShowCode;
							// new GetMovieSeatInfoTask(BuyTicketAct.this,
							// "").execute(movieSeat_ShowCode);
							fromMoviePrepareAct = false;
						}
					} catch (NullPointerException e) {
						e.printStackTrace();
					} catch (IndexOutOfBoundsException e) {
						e.printStackTrace();
					}
				}
			}
		} else {// 首页购票
			movieSeat_ShowCode = "";
			Log.v("cxm", "cinemaPrepareMovieCinema==" + cinemaPrepareMovieCinema);
			if (cinemaPrepareMovieCinema != null) {
				try {
					// SimpleDateFormat sDateFormat = new
					// SimpleDateFormat("hh:dd");
					// String date = sDateFormat.format(new java.util.Date());
					Time t = new Time();
					t.setToNow();
					String mHour = t.hour + "";
					String mMinuts = t.minute + "";
					if (t.minute < 10) {
						mMinuts = 0 + "" + t.minute;
					}
					if (t.hour < 10) {
						mHour = 0 + "" + t.hour;
					}

					// System.out.println("data=========   " + mHour + "       "
					// + mMinuts);
					Log.v("cxm", "getCurentMovieTicket(seatAllList).size()=" + getCurentMovieTicket(seatAllList).size());

					for (int i = 0; i < getCurentMovieTicket(seatAllList).size(); i++) {
						// Log.v("cxm",
						// "getCurentMovieTicket(seatAllList).get(i).time.split(':')[0]+getCurentMovieTicket(seatAllList).get(i).time.split(':')[1]=="+getCurentMovieTicket(seatAllList).get(i).time.split(":")[0]+",next========"+getCurentMovieTicket(seatAllList).get(i).time.split(":")[1]);
						// Log.v("cxm", "mHour+mMinuts===="+mHour+mMinuts);
						if (Integer.parseInt(getCurentMovieTicket(seatAllList).get(i).data.split("-")[0]
								+ getCurentMovieTicket(seatAllList).get(i).data.split("-")[1]
								+ getCurentMovieTicket(seatAllList).get(i).data.split("-")[2]) < Integer.parseInt(data.split("-")[0]
								+ data.split("-")[1] + data.split("-")[2])) {

						} else {
							if (Integer.parseInt(getCurentMovieTicket(seatAllList).get(i).time.split(":")[0]
									+ getCurentMovieTicket(seatAllList).get(i).time.split(":")[1]) >= Integer.parseInt(mHour + mMinuts)) {
								// Log.v("cxm", "i=="+i);
								selected = i;
								break;
							}
						}
					}
					// if(isCanLoadNewSeat){
					// for (int i = 0; i <
					// getCurentMovieTicket(seatAllList).size(); i++) {
					// if
					// (Integer.parseInt(getCurentMovieTicket(seatAllList).get(i).time.split(":")[0])
					// == mHour
					// &&
					// Integer.parseInt(getCurentMovieTicket(seatAllList).get(i).time.split(":")[1])
					// >= mMinuts) {
					// selected = i;
					// break;
					// }
					// }
					// if (selected == -1) {
					//
					// for (int i = 0; i <
					// getCurentMovieTicket(seatAllList).size(); i++) {
					// if
					// (Integer.parseInt(getCurentMovieTicket(seatAllList).get(i).time.split(":")[0])
					// > mHour) {
					// selected = i;
					// break;
					// }
					// }
					// }

					System.out.println("selected           " + selected);
					if (selected == -1) {
						goseatselected.setVisibility(View.GONE);
					}
					if (selected != -1) {
						// goseatselected.setVisibility(View.VISIBLE);
						// System.out.println("tim------------       " +
						// getCurentMovieTicket(seatAllList).get(selected).time);
						if (cinemaPrepareMovieCinema.list != null && cinemaPrepareMovieCinema.list.size() > 0) {
							movieSeat_ShowCode = getCurentMovieTicket(seatAllList).get(selected).showcode;
							if (!TextUtils.isEmpty(movieSeat_ShowCode)) {
								reStartShowCode = movieSeat_ShowCode;
								// System.out.println("reStartShowCode      "+reStartShowCode);
								if(getMovieSeatInfoTask!=null){
									getMovieSeatInfoTask.cancel(true);
								}
								getMovieSeatInfoTask = new GetMovieSeatInfoTask(BuyTicketAct.this, "");
								getMovieSeatInfoTask.execute(movieSeat_ShowCode);
							}
						}
					}
					if (selected >= 0) {
						cinemaRoom = cuMovieTicketsList.get(selected).room;
						cinemaTime = cuMovieTicketsList.get(selected).time;
					}
					movie_listview.setSelection(selected);
					// }
				} catch (NullPointerException e) {
					e.printStackTrace();
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}

			// }
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
				System.out.println("c_id====" + c_id);
				if (AppConstants.movieCinema == null) {
					movieCinema = lib.getCinema(BuyTicketAct.this);
					AppConstants.movieCinema = movieCinema;
				} else {
					movieCinema = AppConstants.movieCinema;
				}
				if (!m_id.equals("") && c_id.equals("")) {
					cinemaPrepareMovie = lib.getMoviesAndCouponByMovie(m_id);
//					if (cinemaPrepareMovie != null) {
//						if (cinemaPrepareMovie.list != null) {
//							if (cinemaPrepareMovie.list.size() > 0) {
//								// c_id = cinemaPrepareMovie.list.get(0).c_id;
//								System.out.println("initWheelc_id====" + c_id);
////								initWheel();
//							}
//						}
//					}
					
				}
				if (!c_id.equals("") && !m_id.equals("")) {
					System.out.println("noinitWheelc_id====" + c_id);
					if (cinemaPrepareMovie == null) {
						System.out.println("cinemaPrepareMovie  1----       " + cinemaPrepareMovie);
						cinemaPrepareMovie = lib.getMoviesAndCouponByMovie(m_id); // 通过影片获取的影院当期
					}
					cinemaPrepareMovieCinema = lib.getTicketByMovieAndCinema(c_id, m_id);
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
			}
			return movieCinema;
		}

		@Override
		protected void onPostExecute(MovieCinemaList result) {
			super.onPostExecute(result);
			if (!exception.equals("")) {
				Toast.makeText(BuyTicketAct.this, "网络连接失败", Toast.LENGTH_SHORT).show();
				finish();
			}
			if(c_id.equals("")){
				chooseLinear.setVisibility(View.VISIBLE);
				canEnableClick(false);
			}

			try {
				if (cinemaPrepareMovie.list.size() == 0) {
					Toast.makeText(BuyTicketAct.this, "没有可供购票的影院", Toast.LENGTH_LONG).show();
					finish();
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
				Toast.makeText(BuyTicketAct.this, "没有可供购票的影院", Toast.LENGTH_LONG).show();
				finish();

			}
			if (result != null && result.isSuccess()) {
				// if(result.list!=null){
				// isSeatTimeFlag = true;
				// }else{
				// isSeatTimeFlag = false;
				// }
				if (movieCinema.mList != null) {
					boolean flag = true;

					for (int i = 0; i < movieCinema.mList.size(); i++) {
						if (!flag) {
							break;
						}
						for (int j = 0; j < movieCinema.mList.get(i).cinemas.size(); j++) {
							if (movieCinema.mList.get(i).cinemas.get(j).c_id.equals(c_id)) {
								duihuanpiaoMoney.setText("¥ " + movieCinema.mList.get(i).cinemas.get(j).price);

								orderType = movieCinema.mList.get(i).cinemas.get(j).order_type;
								priceList = movieCinema.mList.get(i).cinemas.get(j).MovieCinemaInnerList;
								flag = false;
								break;
							}
						}
					}

					changciData();
					getData();
					if (cList.size() != 0) {
						leftl_textview.setText(quyuList.get(country.getCurrentItem()).region_name);
						rgithl_textview.setText(cList.get(country.getCurrentItem()).get(city.getCurrentItem()).c_name);
						duihuanpiaoTitle.setText(cList.get(country.getCurrentItem()).get(city.getCurrentItem()).c_name);
					} else {
						initWheel();
						try {
							if(c_id.equals("")){
								leftl_textview.setText("选择区");
								rgithl_textview.setText("选择影院");
								duihuanpiaoTitle.setText(cList.get(currentPositionI).get(currentPositionJ).c_name);
							}else{
							if (!cList.get(currentPositionI).get(currentPositionJ).c_id.equals(c_id)) {
								Toast.makeText(BuyTicketAct.this, "影院信息获取错误，请核对影院购票", Toast.LENGTH_LONG).show();
							}
							leftl_textview.setText(quyuList.get(currentPositionI).region_name);
							rgithl_textview.setText(cList.get(currentPositionI).get(currentPositionJ).c_name);
							duihuanpiaoTitle.setText(cList.get(currentPositionI).get(currentPositionJ).c_name);
							}
						} catch (IndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					}
					isMoviesLoadAll = true;
					// 当前处理是显示该影院当天首场电影的票价
					if (cinemaPrepareMovieCinema != null)
						if (cinemaPrepareMovieCinema.list != null && cinemaPrepareMovieCinema.list.size() > 0) {
							// if(fromMoviePrepareAct) {
							if (null != cuMovieTicketsList) {
								// Log.v("cxm",
								// "-----------seleted=="+selected+",fromMoviePrepareAct=="+fromMoviePrepareAct);
								if (selected < 0) {
									movie_cinemaPrice = cuMovieTicketsList.get(0).price;
								} else {
									movie_cinemaPrice = cuMovieTicketsList.get(selected).price;
								}
							}
							// }else if(!fromMoviePrepareAct) {
							// //Log.v("cxm",
							// "cuMovieTicketsList.get(0)=="+cuMovieTicketsList.get(0).toString());
							// movie_cinemaPrice =
							// cinemaPrepareMovieCinema.list.get(0).price;
							// System.out.println("movie_cinemaPrice  0================       "+selected+"               "+movie_cinemaPrice);
							// }

							// if(null != cuMovieTicketsList) {
							// Log.v("cxm", "selected=="+selected);
							// movie_cinemaPrice =
							// cuMovieTicketsList.get(0).price;
							// }
						}
					if (cinemaPrepareMovieCinema != null) {

						if (cinemaPrepareMovieCinema.list == null) {
							selected = -1;
							// goseatselected.setVisibility(View.GONE);

							// seat_message.setText("暂无座位票");
							// seat_message.setVisibility(View.VISIBLE);
						} else {
							if (cinemaPrepareMovieCinema.list.size() == 0) {
								selected = -1;
								// goseatselected.setVisibility(View.GONE);

							} else {
								// goseatselected.setVisibility(View.VISIBLE);
							}
							// seat_message.setVisibility(View.GONE);
						}
					} else {
						goseatselected.setVisibility(View.GONE);
					}
				} else {
					Toast.makeText(BuyTicketAct.this, "加载失败，请尝试重新加载！", Toast.LENGTH_LONG).show();
					// finish();
				}
				if (!cover_image_url.equals(""))
					setImageBackGround(backGround, cover_image_url);
			} else {
				Toast.makeText(BuyTicketAct.this, "加载失败，请尝试重新加载！", Toast.LENGTH_LONG).show();
				// finish();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	class GetMovieInfoTask1 extends MovieAsyncTask<String, String, CinemaPrepareMovieCinema> {
		public String exception;

		public GetMovieInfoTask1(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected CinemaPrepareMovieCinema doInBackground(String... params) {

			try {
				if (!c_id.equals("") && !m_id.equals("")) {
					System.out.println("c-   " + c_id + "      " + m_id);
					cinemaPrepareMovieCinema = lib.getTicketByMovieAndCinema(c_id, m_id);
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
			}
			return cinemaPrepareMovieCinema;
		}

		@Override
		protected void onPostExecute(CinemaPrepareMovieCinema result) {
			super.onPostExecute(result);
			if (result != null && result.isSuccess()) {
				// if(result.list!=null){
				// isSeatTimeFlag = true;
				// }else{
				// isSeatTimeFlag = false;
				// }
				boolean flag = true;

				for (int i = 0; i < movieCinema.mList.size(); i++) {
					if (!flag) {
						break;
					}
					for (int j = 0; j < movieCinema.mList.get(i).cinemas.size(); j++) {
						if (movieCinema.mList.get(i).cinemas.get(j).c_id.equals(c_id)) {
							duihuanpiaoMoney.setText("¥ " + movieCinema.mList.get(i).cinemas.get(j).price);
							// if (priceList.size() != 0) {
							// priceList.clear();
							// }
							orderType = movieCinema.mList.get(i).cinemas.get(j).order_type;
							priceList = movieCinema.mList.get(i).cinemas.get(j).MovieCinemaInnerList;
							System.out.println("priceList    ---t   " + priceList.size());
							flag = false;
							break;
						}
					}
				}
				changciData();
				getData();
				if (result.list != null && result.list.size() > 0) {
					movie_cinemaPrice = getCurentMovieTicket(seatAllList).get(0).price;
				}

				if (cinemaPrepareMovieCinema != null) {

					if (cinemaPrepareMovieCinema.list == null) {
						selected = -1;
						goseatselected.setVisibility(View.GONE);

						// seat_message.setText("暂无座位票");
						// seat_message.setVisibility(View.VISIBLE);
						System.out.println("gone----------");
					} else {
						if (cinemaPrepareMovieCinema.list.size() == 0) {
							selected = -1;
							// goseatselected.setVisibility(View.GONE);

						} else {
							// goseatselected.setVisibility(View.VISIBLE);
						}
						// seat_message.setVisibility(View.GONE);
						System.out.println("vis----------");
					}
				} else {
					goseatselected.setVisibility(View.GONE);
				}
			} else {

				changciData();
				getData();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	class GetMovieSeatInfoTask extends MovieAsyncTask<String, String, MovieSeatInfo> {
		public String exception;
		public int selectedLinshi = 0;

		public GetMovieSeatInfoTask(Activity activity, String loadingText) {
			super(activity, null, true, true, false, loadingText);
		}

		@Override
		protected MovieSeatInfo doInBackground(String... params) {
			// seat_message.setVisibility(View.GONE);
			// seat_message.setText(""); 

			MovieSeatInfo movieSeatInfo = null;
			try {
				selectedLinshi = selected;
				// isCanLoadNewSeat = false;
				// cancheSeatData.remove(params[0]);s
				// if
				// (!cancheSeatData.containsKey(cuMovieTicketsList.get(selected).showcode))
				// {
				try {
					cancheSeatData.put(cuMovieTicketsList.get(selected).showcode, movieSeatInfo);
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
				// }
				// System.out.println("0000000----------------"+cuMovieTicketsList.get(selected).showcode);
				movieSeatInfo = MovieLib.getInstance(mContext).getMovieSeat(params[0]);

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
			return movieSeatInfo;
		}

		@Override
		protected void onPostExecute(MovieSeatInfo result) {
			if (getSeat_LoadingProgressBar.isShown()) {
				getSeat_LoadingProgressBar.setVisibility(View.INVISIBLE);
			}
			if (!TextUtils.isEmpty(exception)) {
			}
			if (result != null && result.result.success()) {// 获取数据成功
				if (result.seats != null && result.seats.size() > 0) {
					// isCanLoadNewSeat = true;
					try {
						removeViews();
						goseatselected.setVisibility(View.VISIBLE);
						// if
						// (!cancheSeatData.containsKey(cuMovieTicketsList.get(selected).showcode))
						// {
						cancheSeatData.put(cuMovieTicketsList.get(selectedLinshi).showcode, result);
						// }
						computerMostColAndRow(cancheSeatData.get(cuMovieTicketsList.get(selectedLinshi).showcode));
						// 进行座位的布局：
						// System.out.println("dongdianzhouBuyTicketAct1" +
						// mostCol + "  " + mostRow);
						setSeatLayout(mostCol, mostRow, cancheSeatData.get(cuMovieTicketsList.get(selectedLinshi).showcode));
						movieSeat = cancheSeatData.get(cuMovieTicketsList.get(selectedLinshi).showcode);

						selected = selectedLinshi;
						mMovieTicketListAdapter.notifyDataSetChanged();
						movie_cinemaPrice = cuMovieTicketsList.get(selected).price;
						movieSeat_ShowCode = cuMovieTicketsList.get(selected).showcode;
						reStartShowCode = cuMovieTicketsList.get(selected).showcode;
						cinemaRoom = cuMovieTicketsList.get(selected).room;
						cinemaTime = cuMovieTicketsList.get(selected).time;

						// goseatselected.setVisibility(View.VISIBLE);
					} catch (IndexOutOfBoundsException e) {
						e.printStackTrace();
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
				} else {
					Toast.makeText(mContext, "暂无座位信息!", Toast.LENGTH_SHORT).show();
					seat_message.setVisibility(View.VISIBLE);
					goseatselected.setVisibility(View.GONE);
					try {
						// if
						// (!cancheSeatData.containsKey(cuMovieTicketsList.get(selected).showcode))
						// {
						cancheSeatData.put(cuMovieTicketsList.get(selectedLinshi).showcode, result);
						// }
						removeViews();
					} catch (IndexOutOfBoundsException e) {
						e.printStackTrace();
					}
				}
			} else {
				goseatselected.setVisibility(View.GONE);
				seat_message.setVisibility(View.VISIBLE);
				Toast.makeText(mContext, "获取座位数据失败!", Toast.LENGTH_SHORT).show();
			}
			getMovieSeatInfoTask = null;
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			goseatselected.setVisibility(View.GONE);
			seat_message.setVisibility(View.GONE);
			getSeat_LoadingProgressBar.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		if (!reStartShowCode.equals("")) {
			removeViews();
			cancheSeatData.clear();
			if(getMovieSeatInfoTask!=null){
				getMovieSeatInfoTask.cancel(true);
			}
			getMovieSeatInfoTask = new GetMovieSeatInfoTask(BuyTicketAct.this, "");
			getMovieSeatInfoTask.execute(reStartShowCode);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		backGround.setImageBitmap(null);
//		if(backGround!=null){
//			backGround.recycle();
//		}
		if(getMovieSeatInfoTask!=null){
			getMovieSeatInfoTask.cancel(true);
		}
		seatAllList = null;
		currentTimeBySeatList = null;
		quyuList = null;
		quyuNameList = null;
		cList = null;
		cNameList = null;
		cuMovieTicketsList = null;
		priceList = null;
		cinemaPrepareMovie = null;
		dataList = null;
		timeList = null;
//		app.getAsyncImageLoader().cleanCache();
		app.getAsyncImageLoader().recycleBitmaps();
	}

	public void onResume() {
		super.onResume();
		CmccDataStatistics.onStart(this);
		
	}

	public void onPause() {
		super.onPause();
		CmccDataStatistics.onStop(this);
		if(getMovieSeatInfoTask!=null){
			getMovieSeatInfoTask.cancel(true);
		}
	}
}
