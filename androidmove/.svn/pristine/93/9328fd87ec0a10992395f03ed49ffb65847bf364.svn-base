package com.szcmcc.movie.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.sdk.CmccDataStatistics;
import com.mapabc.mapapi.core.GeoPoint;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.MovieCinema;
import com.szcmcc.movie.bean.MovieCinemaList;
import com.szcmcc.movie.bean.MovieCinemaList.MovieCinemaListInner;
import com.szcmcc.movie.bean.MovieInfo;
import com.szcmcc.movie.bean.MovieNewList;
import com.szcmcc.movie.cache.AppConstants;
import com.szcmcc.movie.map.MapPoi;
import com.szcmcc.movie.map.NetManger;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.network.LoadingDialog;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.network.MovieRequest;
import com.szcmcc.movie.util.Log;
import com.szcmcc.movie.util.PublicUtils;
import com.szcmcc.movie.util.TextUtil;

/**
 * 
 * 22-首页-周边影 院 23-首页-周边影院-列表模式 24-首页-周边影院-列表选择 25-首页-周边影院-列表地区
 */
public class MovieCinemaAct extends BaseMapActivity implements View.OnClickListener {
	/**
	 * 
	 */
	protected static final String TAG = MovieCinemaAct.class.getSimpleName();
	private Context mContext = MovieCinemaAct.this;
//	private ImageView change_list_map = null;
//	private LinearLayout cinemalist_l = null;
//	private RelativeLayout map_layout = null;
	private MovieCinemaList movieCinemaList = null;
	// private MovieCinemaList movieNearbyCinema = null;
	private ArrayList<MovieCinema> movieNearbyCinema;
	private ArrayList<MovieCinema> list = new ArrayList<MovieCinema>();
	// private ArrayList<MovieCinemaListInner> mlist = new
	// ArrayList<MovieCinemaListInner>();
//	private boolean ismap = true;
//	private View curr_l = null;
	protected MovieLib lib;
	private boolean isMoviesLoadAll = false;

	private TextView noCinemaText = null;
	// 搜索模块：
	private List<String> search_area_result;
	private List<String> search_cinema_result;
	private int curr_navi_index = -1; // 用于判断文字颜色的索引
	private GridView gridView1 = null;
	private NaviAdapter mNaviAdapter = null; // 导航的适配器
//	TimeThread thread = null;
//	GeoPoint myGeoPoint = null;
//	GeoPoint currCenterPoint = null;
	boolean deleteOldData;
	boolean shouldGetData = false;

//	private MapView mapView;
//	MapController mMapController;
//	List<Overlay> overlays;//
//	private MyLocationOverlay mLocationOverlay;// 地图上层绘制
	// private LocationManager gpsLocationManager = null;

//	double startLat, startLon, endLat, endLon;
	private TextView myzhoubian = null;
	boolean isFrist = true;
	long start = 0;
	long end = 0;
	GeoPoint startGeo;
	GeoPoint endGeo;

	private TextView moviefast = null;
//	private TextView nearmovie = null;
	private TextView textView_youhui = null;
	private MovieInfo movieInfo = null;
	// 初始化搜索的布局
	View popSearchView;
	Dialog search_dialgo;
	ImageView searchImage;
	ListView listView;
	EditText search_edit;
	TextView no_ciname_message;
	SearchAdapter searchAdapter;
	// ----------弹出搜索框S
	private ImageView search_btn = null;
	private ImageView cancle = null;
	// ----------弹出搜索框E
	// -------------------导航-----------START
//	private LinearLayout spinner_l = null;
//	private TextView curr_text = null;
//	private RelativeLayout nav_hl = null;
	// private String[] stones = new String[] { "我的周边", "福田1", "福田2", "福田3",
	// "福田4", "福田5", "福田6", "福田7", "福田81", "福田82", "福田83" };
	private boolean isShowNavi = false;

	private int[] backgrounds = new int[] { R.drawable.mapbtn, R.drawable.liebiaomoshi, };
	private ListView cinemas_listview = null;
	private MovieCinemaListAdapter mMovieCinemaListAdapter = null;
//	private ArrayList<MovieCinema> sortList = new ArrayList<MovieCinema>();
//	private ArrayList<MovieCinema> sortList1 = new ArrayList<MovieCinema>();
//	private ArrayList<MovieCinema> sortListSeatAndQuan = new ArrayList<MovieCinema>();
//	private ArrayList<MovieCinema> beforeSortListSeat = new ArrayList<MovieCinema>();
//	private ArrayList<MovieCinema> sortListSeat = new ArrayList<MovieCinema>();
//	private ArrayList<MovieCinema> sortList0 = new ArrayList<MovieCinema>();
//	private ArrayList<MovieCinema> beforeSortListSeatAndQuan = new ArrayList<MovieCinema>();
//	private ArrayList<MovieCinema> beforeSortListQuan = new ArrayList<MovieCinema>();
//	private ArrayList<MovieCinema> sortListQuan = new ArrayList<MovieCinema>();
//	private ArrayList<MovieCinema> sortList2 = new ArrayList<MovieCinema>();
	Drawable markerEat;
	boolean myLoc = false;
	// public static ArrayList list = new ArrayList();
//	ArrayList<MapPoi> arrayList = new ArrayList();
	private int total;
	private View popView;
//	View overlay;
//	private LocationManager locationManager = null;
//	private LinearLayout moviefastLayout = null;
//	private Criteria criteria = null;
	private LinearLayout listViewLinear = null;
	String currLat;
	String currLon;
	// ----------------------数据处理------------------------------------E
	boolean needToGetData = false;
	private int currPage;
	private String goodsType = "1";
	double dis = 0;
	private boolean isActivityStart = true;

//	public void release() {
//		change_list_map = null;
//		if (cinemalist_l != null) {
//			cinemalist_l.removeAllViews();
//			cinemalist_l = null;
//		}
//		if (map_layout != null) {
//			map_layout.removeAllViews();
//			map_layout = null;
//		}
//	}

	private void onTitleClick() {
		moviefast = (TextView) findViewById(R.id.moviefast);
//		moviefastLayout = (LinearLayout) findViewById(R.id.moiveLayout);
//		nearmovie = (TextView) findViewById(R.id.nearmovie);
		textView_youhui = (TextView) findViewById(R.id.homepage_textView_youhui);
		moviefast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 回到首页
				// if(NetImageView.isWifi(mContext)){
				Intent intent = new Intent(mContext, HomePageActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("movieInfo", movieInfo);
				intent.putExtra("downloadUrl", "");
				mContext.startActivity(intent);
				// }else{
				// Intent intent = new Intent(mContext, HomePageActivity.class);
				// intent.putExtra("movieInfo", movieInfo);
				// intent.putExtra("downloadUrl", "");
				// mContext.startActivity(intent);
				// }
			}
		});

		textView_youhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(MovieCinemaAct.this, DiscountActivity.class);
				in.putExtra("movieInfo", movieInfo);
				startActivity(in);
				finish();
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// this.setMapMode(MAP_MODE_VECTOR);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.moviecinemaact);
		initTitleBar();

		// initYouHuiListView();
		// initNaviLayout();
		initSearch();
		onTitleClick();
		// moviecinemaact
		findViewByIds();
		initMovieCinemas();

		if (!NetManger.checkNetWork(this)) {
			// Toast.makeText(this, "离线状态，附近团购不可用", 0).show();
			// map_layout.setVisibility(View.GONE);
			needToGetData = true;
			Log.i("-------onResume--NO NET-------");
		} else {
			// map_layout.setVisibility(View.INVISIBLE);
			Log.i("-------onResume-- NET-------");
		}
		// if (needToGetData) {
		currPage = 0;
		// sendToServer("1");
		// }
//		initTopView();

//		if (!AppConstants.mapOrListStatus.equals("")) {
//			// System.out.println("AppConstants.mapOrListStatus              ---"+AppConstants.mapOrListStatus);
//			if (AppConstants.mapOrListStatus.equals("map_layout")) {
//				// System.out.println("map_layout              ---"+map_layout);
//				curr_l = map_layout;
//				map_layout.setVisibility(View.VISIBLE);
//				cinemalist_l.setVisibility(View.INVISIBLE);
//				ismap = true;
//				change_list_map.setBackgroundResource(backgrounds[1]);
//			} else if (AppConstants.mapOrListStatus.equals("cinemalist_l")) {
//				// System.out.println("cinemalist_l              ---"+cinemalist_l);
//				curr_l = cinemalist_l;
//				map_layout.setVisibility(View.INVISIBLE);
//				cinemalist_l.setVisibility(View.VISIBLE);
//				change_list_map.setBackgroundResource(backgrounds[0]);
//				ismap = false;
//			}
//		} else {
//			cinemalist_l.setVisibility(View.VISIBLE);
//			map_layout.setVisibility(View.INVISIBLE);
//			curr_l = cinemalist_l;
//			ismap = false;
//			change_list_map.setBackgroundResource(backgrounds[0]);
//		}

//		change_list_map.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//
//				if (!ismap) {
//					// curr_l.setVisibility(View.INVISIBLE);
//					// map_layout.setVisibility(View.VISIBLE);
//					Transition3d.executeRotation(curr_l, map_layout, true, change_list_map);
//					change_list_map.setBackgroundResource(backgrounds[1]);
//					curr_l = map_layout;
//
//					AppConstants.mapOrListStatus = "map_layout";
//					Log.i("-----MovieCinemaAct.ismap=" + ismap);
//					ismap = true;
//				} else {
//					// curr_l.setVisibility(View.INVISIBLE);
//					// cinemalist_l.setVisibility(View.VISIBLE);
//					Transition3d.executeRotation(curr_l, cinemalist_l, false, change_list_map);
//					change_list_map.setBackgroundResource(backgrounds[0]);
//					curr_l = cinemalist_l;
//					AppConstants.mapOrListStatus = "cinemalist_l";
//					Log.i("-----MovieCinemaAct.ismap=" + ismap);
//					ismap = false;
//				}
//
//			}
//		});
//		markerEat = getResources().getDrawable(R.drawable.eat_icon);
		// 地图
		// initGoogleMapView();
		locationDialog();
		Message ms = new Message();
		ms.what = 1;
		handlerLocation.sendMessage(ms);
	}

	private void animationToDown(View view) {
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, -0.1f, Animation.RELATIVE_TO_SELF, 0f);
		translateAnimation.setDuration(300);
		animationSet.addAnimation(translateAnimation);
		view.startAnimation(animationSet);
	}

	private void animationToUp(View view) {
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0.1f, Animation.RELATIVE_TO_SELF, 0f);
		translateAnimation.setDuration(300);
		animationSet.addAnimation(translateAnimation);
		view.startAnimation(animationSet);
	}

	LoadingDialog loadingDialog;
	int time = 0;
//	final Timer timer = new Timer();

	private void locationDialog() {
		loadingDialog = new LoadingDialog(this, "加载数据中...");

		loadingDialog.show();
//		initGoogleMapView();

	}

	boolean lacationFlag = false;
	private Handler handlerLocation = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				lacationFlag = true;

				if (!isMoviesLoadAll) {
					new GetMovieInfoTask(MovieCinemaAct.this).execute();
//					new GetCinemaTast1(MovieCinemaAct.this).execute();
				}
				break;
			case 2:
				loadingDialog.close();
				break;
			case 3:

				Toast.makeText(MovieCinemaAct.this, "定位失败", Toast.LENGTH_SHORT).show();
				lacationFlag = true;

				if (!isMoviesLoadAll) {
					new GetMovieInfoTask(MovieCinemaAct.this).execute();
//					new GetCinemaTast1(MovieCinemaAct.this).execute();
				}
			}
		}
	};

	private void showDailog(String msg) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle("确认退出");
		builder.setMessage(msg);

		builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent startMain = new Intent(Intent.ACTION_MAIN);
				startMain.addCategory(Intent.CATEGORY_HOME);
				startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(startMain);// 跳转到home下
				finish();
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return false;
	}


	private void initSearch() {
		search_area_result = new ArrayList<String>();
		search_cinema_result = new ArrayList<String>();
		initPopupMenu();
		search_btn = (ImageView) findViewById(R.id.search_btn);
		search_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				search_dialgo.show();
			}
		});
	}

	// 初始化搜索的布局

	private void initPopupMenu() {
		if (popSearchView == null) {
			popSearchView = this.getLayoutInflater().inflate(R.layout.search_window, null);
			searchImage = (ImageView) popSearchView.findViewById(R.id.searchImage);
			listView = (ListView) popSearchView.findViewById(R.id.messageListView);
			no_ciname_message = (TextView) popSearchView.findViewById(R.id.no_ciname_message);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// System.out.println("dongdianzhou123" +
					// search_cinema_result.size());
					if (search_cinema_result != null && search_cinema_result.size() > 0) {
						String movieCinemaName = search_cinema_result.get(position);
						// System.out.println("dongdianzhouMovieCinemahah" +
						// movieCinemaName + list.size());
						if (movieCinemaList != null) {
							if (movieCinemaList.mList != null && movieCinemaList.mList.size() > 0) {
								for (int i = 0; i < movieCinemaList.mList.size(); i++) {
									if (movieCinemaList.mList.get(i).cinemas != null && movieCinemaList.mList.get(i).cinemas.size() > 0) {
										for (int j = 0; j < movieCinemaList.mList.get(i).cinemas.size(); j++) {
											if (movieCinemaList.mList.get(i).cinemas.get(j).c_name.equals(movieCinemaName)) {
												Intent intent = new Intent(mContext, CinemaDesAct.class);
												intent.putExtra("c_id", movieCinemaList.mList.get(i).cinemas.get(j).c_id);
												intent.putExtra("movieCinema", movieCinemaList.mList.get(i).cinemas.get(j));
												startActivity(intent);
											}
										}
									}
								}
							}
						}
					}
					search_edit.setText("");
					search_dialgo.dismiss();
					listView.setVisibility(View.INVISIBLE);
				}
			});
			if (searchAdapter == null) {
				searchAdapter = new SearchAdapter();
			}
			search_edit = (EditText) popSearchView.findViewById(R.id.search_edit);
			search_edit.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {// 编辑框的文字变化后搜索

					String keyword = search_edit.getText().toString();
					if (keyword.length() == 0) {
						searchAdapter.clear();
						searchAdapter.notifyDataSetChanged();
						listView.setVisibility(View.INVISIBLE);
					}
					if (!TextUtils.isEmpty(keyword)) {
						if (search_area_result.size() > 0) {
							search_area_result.clear();
							listView.removeViews(0, search_area_result.size());
						}
						if (search_cinema_result.size() > 0) {
							search_cinema_result.clear();
						}
						// System.out.println("dongdainzhouListview1" +
						// listView.getHeaderViewsCount());
						if (listView.getHeaderViewsCount() > 0) {
							for (int i = 0; i < listView.getHeaderViewsCount(); i++) {
								listView.removeHeaderView(listView.getChildAt(i));
							}
							searchAdapter.notifyDataSetChanged();
						}
						// System.out.println("dongdainzhouListview2" +
						// listView.getHeaderViewsCount());
						searchCinema(keyword);
						handleSearchResult();
					}
				}
			});
			search_dialgo = new Dialog(this, R.style.customer);
			// Window mWindow = search_dialgo.getWindow();
			// WindowManager.LayoutParams lp = mWindow.getAttributes();
			// lp.x = 10;
			// lp.y = -143;
			// search_dialgo.onWindowAttributesChanged(lp);
			search_dialgo.setCanceledOnTouchOutside(false);
			// search_dialgo.requestWindowFeature(Window.FEATURE_NO_TITLE);
			search_dialgo.setContentView(popSearchView);
			cancle = (ImageView) popSearchView.findViewById(R.id.cancle);
			cancle.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					listView.setVisibility(View.INVISIBLE);
					no_ciname_message.setVisibility(View.GONE);
					search_dialgo.dismiss();

				}
			});
		}

	}

	/**
	 * 处理搜索后的结果
	 */
	private void handleSearchResult() {
		if (search_area_result.size() <= 0 && search_cinema_result.size() <= 0) {
			// Toast.makeText(mContext, "没有与其相应的影院，请重新输入关键字！",
			// Toast.LENGTH_SHORT)
			// .show();
			no_ciname_message.setVisibility(View.VISIBLE);

			return;
		} else {
			no_ciname_message.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
			if (search_area_result.size() > 0) {
				for (int i = 0; i < search_area_result.size(); i++) {
					final TextView areaTextView = new TextView(mContext);
					areaTextView.setTextSize(23f);
					areaTextView.setTextColor(Color.BLACK);
					areaTextView.setGravity(Gravity.CENTER);
					areaTextView.setText(search_area_result.get(i));
					areaTextView.setPadding(12, 12, 12, 12);
					areaTextView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							search_edit.setText("");
							search_dialgo.dismiss();
							listView.setVisibility(View.INVISIBLE);
							String areaName = areaTextView.getText().toString();
							if (!TextUtils.isEmpty(areaName)) {
								try {
									for (int j = 0; j < movieCinemaList.mList.size(); j++) {
										if (movieCinemaList.mList.get(j).region_name.equals(areaName)) { // 区域的点击处理
											curr_navi_index = j;
//											curr_text.setText(movieCinema.mList.get(j).region_name);
											isShowNavi = false;
											mNaviAdapter.notifyDataSetChanged();
											if (list.size() != 0) {
												list.clear();
											}

											list.addAll(movieCinemaList.mList.get(j).cinemas);
											// System.out.println("dongdaiznhouSearchResult_Area"
											// + list.size());
											if (mMovieCinemaListAdapter != null) {
												mMovieCinemaListAdapter = null;
											}
											mMovieCinemaListAdapter = new MovieCinemaListAdapter(list);
											cinemas_listview.setAdapter(mMovieCinemaListAdapter);
										}
									}
								} catch (NullPointerException e) {
									e.printStackTrace();
								}
							}
						}
					});
					try {
						listView.addHeaderView(areaTextView);
					} catch (Exception e) {
						// System.out.println("dongdianzhou" +
						// "Cannot add header view to list -- setAdapter has already been called");
						e.printStackTrace();
					} finally {
						if (search_cinema_result.size() <= 0) {
							Toast.makeText(mContext, "没有与其相应的影院，请重新输入关键字！", Toast.LENGTH_SHORT).show();
						}
					}
					// System.out.println("dongdainzhou" + listView.getCount() +
					// areaTextView);
				}
			}
			searchAdapter.addList(search_cinema_result);
			listView.setAdapter(searchAdapter);
		}
	}

	/***
	 * 通过关键字搜索附近的电影院
	 * 
	 * @param keyword
	 */
	private void searchCinema(String keyword) {
		// System.out.println("dongdianzhou" + TextUtil.isNumeric(keyword) +
		// TextUtil.isStr(keyword) + TextUtil.isStrOrNumeric(keyword));
		if (TextUtil.isNumeric(keyword) || TextUtil.isStr(keyword) || TextUtil.isStrOrNumeric(keyword)) {
			// System.out.println("dongdainzhouMovieCinemaAct" + "改关键字不是汉字");
			// Toast.makeText(mContext, "请输入有效的关键字！",
			// Toast.LENGTH_SHORT).show();
			return;
		}

		if (movieCinemaList != null) {
			if (movieCinemaList.mList != null && movieCinemaList.mList.size() > 1) {
				for (int i = 0; i < movieCinemaList.mList.size(); i++) {
					String areaName = movieCinemaList.mList.get(i).region_name;
					if (TextUtil.iscontain(areaName, keyword)) {// 该区域名中包含关键字。
						search_area_result.add(areaName);
					}
					if (movieCinemaList.mList.get(i).cinemas != null && movieCinemaList.mList.get(i).cinemas.size() > 0) {
						for (int j = 0; j < movieCinemaList.mList.get(i).cinemas.size(); j++) {
							String movieCinemaName = movieCinemaList.mList.get(i).cinemas.get(j).c_name;
							if (TextUtil.iscontain(movieCinemaName, keyword)) {// 电影院名字中包含关键字
								search_cinema_result.add(movieCinemaName);
							}
						}
					}
				}
			}
			// System.out.println("dongdianzhou" + search_area_result.size() +
			// search_cinema_result.size());
		}
	}

	// ----------弹出搜索框E
	// -------------------导航-----------START

	private void initNaviLayout() {
		if (movieCinemaList == null) {
			return;
		}

		if (movieCinemaList.mList == null || movieCinemaList.mList.size() == 0) {
			return;
		}

		curr_navi_index = -1;
		initGallery();
	}

	private void changeScaleFromSmallToBig(Activity activity, View layout) {
		final Animation animation = AnimationUtils.loadAnimation(activity, R.anim.scale_from_small_to_big);
		layout.startAnimation(animation);
	}

	private void changeScaleFromBigToSmall(Activity activity, View layout) {
		final Animation animation = AnimationUtils.loadAnimation(activity, R.anim.scale_from_big_to_small);
		layout.startAnimation(animation);
	}

	public void initGallery() {
		myzhoubian = (TextView) findViewById(R.id.myzhoubian);
		noCinemaText = (TextView) findViewById(R.id.noCinemaText);
		gridView1 = (GridView) findViewById(R.id.gridView);
		gridView1.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridView1.setColumnWidth(100);
		gridView1.setHorizontalSpacing(5);
		gridView1.setNumColumns(movieCinemaList.mList.size());
		gridView1.setStretchMode(GridView.NO_STRETCH);
		LayoutParams params = new LayoutParams(movieCinemaList.mList.size() * (100 + 5), LayoutParams.WRAP_CONTENT);
		gridView1.setLayoutParams(params);
		// System.out.println("movieCinema.mList      "+movieCinema.mList.size());
		mNaviAdapter = new NaviAdapter(movieCinemaList.mList);
		gridView1.setAdapter(mNaviAdapter);
		gridView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				intiListView(arg2);

			}
		});
		myzhoubian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intiNearList();
			}

		});

	}

	/**
	 * 刷新区域列表数据和ui
	 * 
	 * @param position
	 */
	private void intiListView(int position) {
		if (movieCinemaList == null) {
			return;
		}
		if (movieCinemaList.mList == null) {
			return;
		}
		curr_navi_index = position;
		myzhoubian.setTextColor(0xf0000000);
		isShowNavi = true;
		mNaviAdapter.notifyDataSetChanged();
		if (list.size() != 0) {
			list.clear();
		}
		list.addAll(movieCinemaList.mList.get(position).cinemas);

		dis = 0;
		mMovieCinemaListAdapter = new MovieCinemaListAdapter(list);
		cinemas_listview.setAdapter(mMovieCinemaListAdapter);
		if (list.size() == 0) {
			noCinemaText.setVisibility(View.VISIBLE);
		} else {
			noCinemaText.setVisibility(View.GONE);
		}
	}

//	private void seatAndQuanSort(ArrayList<MovieCinema> list) {
//		Comparator<MovieCinema> comparator;
//		try {
//			for (int i = 0; i < list.size(); i++) {
//				MovieCinema bean = list.get(i);
//				try {
//					double myLat = ((double) myGeoPoint.getLatitudeE6()) / 1000000;
//					double myLon = ((double) myGeoPoint.getLongitudeE6()) / 1000000;
//					double movLat = Double.parseDouble(list.get(i).latitude);
//					double movLon = Double.parseDouble(list.get(i).longitude);
//					dis = CalculateDisByLonAndLat.gps2m(myLat, myLon, movLat, movLon);
//					bean.meters = dis + "";
//					sortListSeatAndQuan.add(bean);
//				} catch (NumberFormatException e) {
//					e.printStackTrace();
//					bean.meters = "";
//					sortList1.add(bean);
//				}
//
//			}
//			comparator = new MetersLowToHighComparator();
//			Collections.sort(sortListSeatAndQuan, comparator);
//			if (sortList1.size() != 0)
//				sortListSeatAndQuan.addAll(sortList1);
//			// System.out.println("sortListSeat     "+sortListSeatAndQuan);
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//			sortListSeatAndQuan.clear();
//			sortListSeatAndQuan.addAll(list);
//		}
//	}

//	private void seatSort(ArrayList<MovieCinema> list) {
//		Comparator<MovieCinema> comparator;
//		try {
//			for (int i = 0; i < list.size(); i++) {
//				MovieCinema bean = list.get(i);
//				try {
//					double myLat = ((double) myGeoPoint.getLatitudeE6()) / 1000000;
//					double myLon = ((double) myGeoPoint.getLongitudeE6()) / 1000000;
//					double movLat = Double.parseDouble(list.get(i).latitude);
//					double movLon = Double.parseDouble(list.get(i).longitude);
//					dis = CalculateDisByLonAndLat.gps2m(myLat, myLon, movLat, movLon);
//					bean.meters = dis + "";
//					sortListSeat.add(bean);
//				} catch (NumberFormatException e) {
//					e.printStackTrace();
//					bean.meters = "";
//					sortList0.add(bean);
//				}
//
//			}
//			comparator = new MetersLowToHighComparator();
//			Collections.sort(sortListSeat, comparator);
//			if (sortList0.size() != 0)
//				sortListSeat.addAll(sortList0);
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//			sortListSeat.clear();
//			sortListSeat.addAll(list);
//		}
//	}

//	private void quanSort(ArrayList<MovieCinema> list) {
//		Comparator<MovieCinema> comparator;
//		try {
//			for (int i = 0; i < list.size(); i++) {
//				MovieCinema bean = list.get(i);
//				try {
//					double myLat = ((double) myGeoPoint.getLatitudeE6()) / 1000000;
//					double myLon = ((double) myGeoPoint.getLongitudeE6()) / 1000000;
//					double movLat = Double.parseDouble(list.get(i).latitude);
//					double movLon = Double.parseDouble(list.get(i).longitude);
//					dis = CalculateDisByLonAndLat.gps2m(myLat, myLon, movLat, movLon);
//					bean.meters = dis + "";
//					sortListQuan.add(bean);
//				} catch (NumberFormatException e) {
//					e.printStackTrace();
//					bean.meters = "";
//					sortList2.add(bean);
//				}
//
//			}
//			comparator = new MetersLowToHighComparator();
//			Collections.sort(sortListQuan, comparator);
//			if (sortList2.size() != 0)
//				sortListQuan.addAll(sortList2);
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//			sortListQuan.clear();
//			sortListQuan.addAll(list);
//		}
//	}

	/**
	 * 刷新我的周边数据和ui
	 */
	private void intiNearList() {
		curr_navi_index = -1;
//		curr_text.setText("我的周边");
		isShowNavi = true;
		myzhoubian.setTextColor(0xf03D83A9);
		if (list.size() != 0) {
			list.clear();
		}
//		if (sortList.size() != 0) {
//			sortList.clear();
//		}
//		if (sortList1.size() != 0) {
//			sortList1.clear();
//		}
		list.addAll(movieNearbyCinema);
//		for (int i = 0; i < list.size(); i++) {
//			if (list.get(i).order_type.equals("0") || list.get(i).order_type.equals("2")) {
//				sortList.add(list.get(i));
//			} else {
//				sortList1.add(list.get(i));
//			}
//		}
//		if (sortList1.size() != 0) {
//			sortList.addAll(sortList1);
//		}

		if (mNaviAdapter != null)
			mNaviAdapter.notifyDataSetChanged();
		if (mMovieCinemaListAdapter != null) {
			mMovieCinemaListAdapter = null;
		}
//		if (!isShowNavi) {
//			animationToDown(listViewLinear);
//			changeScaleFromSmallToBig(MovieCinemaAct.this, nav_hl);
//			nav_hl.setVisibility(View.VISIBLE);
//			isShowNavi = true;
//		} else {
//			animationToUp(listViewLinear);
//			changeScaleFromBigToSmall(MovieCinemaAct.this, nav_hl);
//			nav_hl.setVisibility(View.GONE);
//			isShowNavi = false;
//		}
		mMovieCinemaListAdapter = new MovieCinemaListAdapter(list);
		cinemas_listview.setAdapter(mMovieCinemaListAdapter);
		// System.out.println("list-------------"+list.size());
		if (list.size() == 0) {
			noCinemaText.setVisibility(View.VISIBLE);
		} else {
			noCinemaText.setVisibility(View.GONE);
		}
	}

	public class MetersLowToHighComparator implements Comparator<MovieCinema> {
		@Override
		public int compare(MovieCinema itemBean1, MovieCinema itemBean2) {
			double meters1 = 0;
			double meters2 = 0;
			if (itemBean1.meters != null && !"".equals(itemBean1.meters)) {
				meters1 = Double.parseDouble(itemBean1.meters);
			}
			if (itemBean2.meters != null && !"".equals(itemBean2.meters)) {
				meters2 = Double.parseDouble(itemBean2.meters);
			}
			if (meters1 < meters2) {
				return -1;
			} else if (meters1 > meters2) {
				return 1;
			} else {
				return 0;
			}
		}

	}

	public Activity getActivity() {
		return MovieCinemaAct.this;
	}

	public class NaviAdapter extends BaseAdapter {

		private ArrayList<MovieCinemaListInner> list = null;
		private LayoutInflater layoutFlater;

		public NaviAdapter(ArrayList<MovieCinemaListInner> list) {
			super();
			layoutFlater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.list = list;
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
			final ViewHolderGalleryItem holder;
			if (convertView == null) {
				convertView = layoutFlater.inflate(R.layout.navi_item, null);
				holder = new ViewHolderGalleryItem();
				holder.navi_item_text1 = (TextView) convertView.findViewById(R.id.navi_item_text1);
				holder.navi_item_text2 = (TextView) convertView.findViewById(R.id.navi_item_text2);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolderGalleryItem) convertView.getTag();
				holder.navi_item_text1.setVisibility(View.INVISIBLE);
				holder.navi_item_text2.setVisibility(View.INVISIBLE);
			}

			holder.navi_item_text1.setText(list.get(position).region_name);
			holder.navi_item_text2.setText(list.get(position).region_name);
			if (curr_navi_index != -1) {
				if (curr_navi_index == position) {
					holder.navi_item_text1.setVisibility(View.VISIBLE);
					holder.navi_item_text2.setVisibility(View.INVISIBLE);
				} else {
					holder.navi_item_text2.setVisibility(View.VISIBLE);
					holder.navi_item_text1.setVisibility(View.INVISIBLE);
				}
			} else if (curr_navi_index == -1) {
				holder.navi_item_text1.setVisibility(View.INVISIBLE);
				holder.navi_item_text2.setVisibility(View.VISIBLE);
			}

			return convertView;
		}
	}

	private static class ViewHolderGalleryItem {
		public TextView navi_item_text1;
		public TextView navi_item_text2;
	}

	// -------------------导航----------END
	private void findViewByIds() {
//		change_list_map = (ImageView) findViewById(R.id.change_list_map);
//		cinemalist_l = (LinearLayout) findViewById(R.id.cinemalist_l);
//		map_layout = (RelativeLayout) findViewById(R.id.map_layout);
		listViewLinear = (LinearLayout) findViewById(R.id.listviewLinear);
		Intent in = getIntent();
		if (in.getExtras() != null) {
			try {
				movieInfo = (MovieInfo) in.getExtras().getSerializable("movieInfo");
				if (movieInfo == null) {
					movieInfo = AppConstants.movieInfo;

				}
				setting.setMovieInfo(movieInfo);
				settingImageLayout.setMovieInfo(movieInfo);
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// -------------------电影院列表-----------START
	private void initMovieCinemas() {
		if (cinemas_listview != null)
			return;
		Log.i("-----.MyZoneAct.initMovieCinemas--------");
		cinemas_listview = (ListView) findViewById(R.id.cinemas_listview);
		// mMovieCinemaList = new MovieCinemaList();
		// MovieCinema mMovieCinema = null;
		// for (int i = 0; i < 10; i++) {
		// mMovieCinema = new MovieCinema();
		// mMovieCinema.c_name = "金逸国际影城" + i;
		// mMovieCinema.meters = "10" + i + "m";
		// mMovieCinema.address =
		// "深圳市福田区福华一路西新怡景商业中心城FG014深圳市福田区福华一路西新怡景商业中心城FG014" + i;
		// mMovieCinema.image_url = "imageUrl" + i;
		// // if (i == 0) {
		// // mMovieCinema.setType("0");
		// // } else {
		// mMovieCinema.order_type = "1";
		// // }
		// mMovieCinema.setGeoPoint(new GeoPoint(39 + i, 39 + i));
		// if (i % 2 == 0) {
		// mMovieCinema.status = "0";
		// }
		// if (i % 2 == 1) {
		// mMovieCinema.status = "1";
		// }
		//
		// list.add(mMovieCinema);
		// }
		for (int i = 0; i < list.size(); i++)
			mMovieCinemaListAdapter = new MovieCinemaListAdapter(list);
		cinemas_listview.setAdapter(mMovieCinemaListAdapter);
		// cinemas_listview
		// .setOnItemSelectedListener(new OnItemSelectedListener() {
		//
		// public void onItemSelected(AdapterView<?> mAdapterView,
		// View parent, int position, long id) {
		// mMovieCinemaListAdapter.notifyDataSetChanged();
		// }
		//
		// public void onNothingSelected(AdapterView<?> arg0) {
		// }
		// });
		cinemas_listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> mAdapterView, View parent, int position, long id) {
				Intent intent = new Intent(mContext, CinemaPrepareAct.class);
				intent.putExtra("movieCinema", list.get(position));
				intent.putExtra("movieCinemaList", movieCinemaList);
				mContext.startActivity(intent);
			}
		});

	}

	// MovieCinema myMovieCinema = null;

	// private MovieCinemaList mMovieCinemaList = null;

	public class MovieCinemaListAdapter extends BaseAdapter {

		public ArrayList<MovieCinema> list = null;
		private LayoutInflater layoutFlater;

		public MovieCinemaListAdapter(ArrayList<MovieCinema> list) {
			super();
			layoutFlater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			this.list = list;
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
			final ViewHolderListViewItem holder;
			if (convertView == null) {
				convertView = layoutFlater.inflate(R.layout.movie_cinema_list_item, null);
				holder = new ViewHolderListViewItem();

				holder.kedingzuo = (TextView) convertView.findViewById(R.id.kedingzuo);
				holder.kegouquan = (TextView) convertView.findViewById(R.id.kegouquan);
				holder.imageUrl = (ImageView) convertView.findViewById(R.id.imageUrl);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.address = (TextView) convertView.findViewById(R.id.address);
				holder.meters = (TextView) convertView.findViewById(R.id.meters);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolderListViewItem) convertView.getTag();
			}
			holder.meters.setText("");
			holder.imageUrl.setImageBitmap(null);
			MovieCinema bean = list.get(position);
			holder.name.setText(bean.c_name);
			holder.address.setText(bean.address);

			try {
				double dis = Double.parseDouble(bean.meters);
				if (dis > 10000) {
					String disText = (dis / 1000 + "").substring(0, 6);
					bean.meters = disText + "km";
				} else {
					bean.meters = dis + "m";
				}
				holder.meters.setText(bean.meters);
			} catch (NumberFormatException e) {
				holder.meters.setText(bean.meters);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if ("0".equals(bean.order_type)) {
				holder.kedingzuo.setVisibility(View.VISIBLE);// 0 可订做,
				holder.kegouquan.setVisibility(View.GONE);
			}

			else if ("1".equals(bean.order_type)) {
				holder.kedingzuo.setVisibility(View.GONE);// 1可购卷
				holder.kegouquan.setVisibility(View.VISIBLE);
			} else if ("2".equals(bean.order_type)) {
				holder.kedingzuo.setVisibility(View.VISIBLE);// 2两者都有
				holder.kegouquan.setVisibility(View.VISIBLE);
			}

			if (bean != null && URLUtil.isHttpUrl(bean.image_url)) {
				holder.imageUrl.setTag(bean.image_url);
				holder.imageUrl.setScaleType(ScaleType.FIT_XY);
				Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext, bean.image_url, new ImageCallback() {

					@Override
					public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
						String url = (String) holder.imageUrl.getTag();
						if (url.equals(bitmapUrl)) {
							holder.imageUrl.setImageBitmap(bitmap);
						}
					}
				}, AsyncImageLoader.CACHE_TYPE_SD, true);
				if (bitmap != null) {

					holder.imageUrl.setImageBitmap(bitmap);
				}
			}

			return convertView;
		}
	}

	private static class ViewHolderListViewItem {

		public ImageView imageUrl = null;
		public TextView kedingzuo = null;
		public TextView kegouquan = null;
		public TextView name = null;
		// public TextView status = null;
		public TextView address = null;
		public TextView meters = null;

	}

	// -------------------电影院列表----------END

	private MovieNewList mMovieNewList = null;

	// -------------------优惠列表-----------START
	// public void initYouHuiListView() {
	// mMovieNewList = new MovieNewList();
	// YouHuiNew mYouHuiNew = null;
	// for (int i = 0; i < 30; i++) {
	// mYouHuiNew = new YouHuiNew();
	// mYouHuiNew.name = "金逸国际影城" + i;
	// mYouHuiNew.imageUrl =
	// "http://img4.cache.netease.com/ent/2012/2/17/20120217093718959cd.jpg";
	// mYouHuiNew.address = "UME国际影城";
	// mYouHuiNew.date = "2月12日-2月14日";
	// mYouHuiNew.des =
	// "默契比拼 你来比划我来猜默契比拼 你来比划我来猜默契比拼 你来比划我来猜默契比拼 你来比划我来猜默契比拼 你来比划我来猜";
	// mYouHuiNew.context = "默契比拼 你来比划我来猜";
	// mMovieNewList.addYouHuiNew(mYouHuiNew);
	// }
	// youhui_more_relative = (RelativeLayout)
	// findViewById(R.id.youhui_more_relative);
	// youhui_more_listview = (ListView)
	// findViewById(R.id.youhui_more_listview);
	// mYouHuiNewListAdapter = new YouHuiNewListAdapter();
	// youhui_more_listview.setAdapter(mYouHuiNewListAdapter);
	// //
	// // listview_menu = (ImageView) findViewById(R.id.listview_menu);
	// // listview_menu.setOnClickListener(new OnClickListener() {
	// //
	// // public void onClick(View v) {
	// // if (ismListViewHidden) {
	// // youhui_more_listview.setVisibility(View.VISIBLE);
	// // ismListViewHidden = false;
	// // } else {
	// // youhui_more_listview.setVisibility(View.GONE);
	// // ismListViewHidden = true;
	// // }
	// //
	// // }
	// // });
	// youhui_listview_menu = (ImageView)
	// findViewById(R.id.youhui_listview_menu);
	// new_btn = (ImageView) findViewById(R.id.new_btn);
	// new_btn.setVisibility(View.VISIBLE);
	// youhui_listview_menu.setOnClickListener(new OnClickListener() {
	//
	// public void onClick(View v) {
	// if (isyouhuimListViewHidden) {
	// youhui_more_listview.setVisibility(View.VISIBLE);
	// AnimUtils.setToLeftShowAnim(youhui_more_relative, 0.9f);
	// isyouhuimListViewHidden = false;
	// } else {
	// AnimUtils.setToRightGoneAnim(youhui_more_relative, youhui_more_listview,
	// 0.9f);
	// // youhui_more_listview.setVisibility(View.GONE);
	// isyouhuimListViewHidden = true;
	// }
	//
	// }
	// });
	// youhui_more_listview.setOnItemClickListener(new OnItemClickListener() {
	//
	// public void onItemClick(AdapterView<?> mAdapterView, View parent, int
	// position, long id) {
	// // 跳到优惠页面
	//
	// Intent intent = new Intent(mContext, YouHuiAct.class);
	// Bundle mBundle = new Bundle();
	// mBundle.putSerializable("bean", mMovieNewList.mYouHuiNews.get(position));
	// intent.putExtra("data", mBundle);
	// mContext.startActivity(intent);
	// }
	//
	// });
	// }

	// private ImageView youhui_listview_menu = null;
	// private ImageView new_btn = null;

	// private boolean isyouhuimListViewHidden = true;
	// private ListView youhui_more_listview = null;
	// private YouHuiNewListAdapter mYouHuiNewListAdapter = null;

	// RelativeLayout youhui_more_relative;

	// public class YouHuiNewListAdapter extends BaseAdapter {
	//
	// private LayoutInflater layoutFlater;
	//
	// public YouHuiNewListAdapter() {
	// super();
	// layoutFlater = (LayoutInflater)
	// mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// }
	//
	// public int getCount() {
	// return mMovieNewList.mYouHuiNews.size();
	// }
	//
	// public Object getItem(int position) {
	// return position;
	// }
	//
	// public long getItemId(int position) {
	// return position;
	// }
	//
	// public View getView(int position, View convertView, ViewGroup parent) {
	// final ViewHolder holder;
	// if (convertView == null) {
	// convertView = layoutFlater.inflate(R.layout.youhui_movie_new_list_item,
	// null);
	// holder = new ViewHolder();
	// holder.imageUrl = (ImageView) convertView.findViewById(R.id.imageUrl);
	// holder.logo = (ImageView) convertView.findViewById(R.id.logo);
	// holder.address = (TextView) convertView.findViewById(R.id.address);
	// holder.date = (TextView) convertView.findViewById(R.id.date);
	// holder.name = (TextView) convertView.findViewById(R.id.name);
	// // holder.name2 = (TextView)
	// // convertView.findViewById(R.id.name2);
	// convertView.setTag(holder);
	// } else {
	// holder = (ViewHolder) convertView.getTag();
	// }
	// YouHuiNew bean = mMovieNewList.mYouHuiNews.get(position);
	// String url = bean.imageUrl;
	//
	// // ImageView cacheImage = holder.imageUrl;
	// // holder.imageUrl.setTag(item.imageUrl);
	// // MainAct.mAsyncImageLoader.getListViewLogoItem(mContext, url,
	// // cacheImage, youhui_more_listview);
	//
	// // holder.imageUrl.setText(item.imageUrl);
	// // holder.logo.setText(bean.logo);
	// holder.address.setText(bean.address);
	// holder.date.setText(bean.date);
	// holder.name.setText(bean.name);
	// // holder.name2.setText(bean.name);
	// return convertView;
	// }
	// }
	//
	// private static class ViewHolder {
	//
	// public ImageView imageUrl;
	// public ImageView logo;
	// public TextView address;
	// public TextView date;
	// public TextView name;
	// // public TextView name2;
	// }

	// -------------------优惠列表----------END
	// -----------------地图------------------S

	int cachePage;
	ArrayList<MapPoi> chcheList = new ArrayList<MapPoi>();

	// MyAdapter cacheAdapter;

	// protected void readFromLocal() {
	// // String json = SpUtil.read(this, "aroundlist" + cachePage);
	// // if (json != null && !"".equals(json)) {
	// ResultData<Goods> resultData = new ResultData<Goods>();
	// ArrayList<Goods> arrayList = new ArrayList<Goods>();
	// predList(arrayList);
	// resultData.setArrayList(arrayList);
	// // ResultData resultData = ParseManger.getTodayList(json);
	// cachePage++;
	// // if (listView.getFooterViewsCount() == 0) {
	// // listView.addFooterView(footView);
	// // }
	// chcheList.addAll(resultData.getArrayList());
	// if (chcheList != null) {
	// Collections.sort(chcheList, new ComparatorHotelDistance());
	// // 进行距离分组
	// chcheList = predList(chcheList);
	// }
	// // if (cacheAdapter == null) {
	// // cacheAdapter = new MyAdapter(this, chcheList, listView);
	// // listView.setAdapter(cacheAdapter);
	// // } else {
	// // cacheAdapter.notifyDataSetChanged();
	// // }
	// mapView.getOverlays().clear();
	// initMyLoc();
	// if (chcheList != null && chcheList.size() > 0) {
	// setUi(chcheList);
	// // }
	// } else {
	// // 判断网络
	// if (!NetManger.checkNetWork(this)) {
	// Toast.makeText(this, "网络不可用!", 0).show();
	// }
	// }
	// }

	// private void setUi() {
	//
	// // 读取本地缓存
	// // readFromLocal();
	// sendToServer("1");
	// // 判断手机配置
	// ActivityManager am = (ActivityManager)
	// getSystemService(Context.ACTIVITY_SERVICE);
	//
	// String result = null;
	// double cpuInfo = 0;
	// CMDExecute cmdexe = new CMDExecute();
	// try {
	// String[] args = { "/system/bin/cat", "/proc/cpuinfo" };
	// result = cmdexe.run(args, "/system/bin/");
	// result = result.substring(result.indexOf("BogoMIPS\t:")
	// + "BogoMIPS\t:".length(), result.indexOf("\nFeatures"));
	// cpuInfo = Double.valueOf(result);
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }

	// 始终显示地图
	// listView.setVisibility(View.VISIBLE);
	// map_layout.setVisibility(View.GONE);
	// list_map_icon.setText(" 地图模式 ");

	/*
	 * if(cpuInfo<500){//cpu小于500HZ Toast.makeText(this, "您的手机配置较低，将自动进入列表页面。",
	 * 1).show(); listView.setVisibility(View.VISIBLE);
	 * map_layout.setVisibility(View.GONE); list_map_icon.setText("地图模式");
	 * }else{ boolean openMap = getSharedPreferences("openMap",
	 * MODE_WORLD_WRITEABLE) .getBoolean("openMap", true); if(openMap){
	 * getSharedPreferences("openMap", MODE_WORLD_WRITEABLE)
	 * .edit().putBoolean("openMap", true).commit();
	 * listView.setVisibility(View.GONE);
	 * map_layout.setVisibility(View.VISIBLE); list_map_icon.setText("列表模式");
	 * }else{ getSharedPreferences("openMap", MODE_WORLD_WRITEABLE)
	 * .edit().putBoolean("openMap", false).commit();
	 * listView.setVisibility(View.VISIBLE);
	 * map_layout.setVisibility(View.GONE); list_map_icon.setText("地图模式"); } }
	 */

	// }

	// private TextView search_bar, category_text;

	// ----------------------数据处理------------------------------------S
//	private static ArrayList predList(ArrayList arrayList) {
//		ArrayList arrayList2 = new ArrayList();
//		int temp = -1;
//		boolean isTen = true;
//		for (int i = 0; i < arrayList.size(); i++) {
//			MapPoi goods = (MapPoi) arrayList.get(i);
//			double b = goods.getDistance();
//			if (b < 20) {
//				if (isTen) {
//					isTen = false;
//					arrayList2.add("约10米");
//				}
//				arrayList2.add(goods);
//			} else if (b < 100) {
//				int c = (int) b / 10 * 10;
//				if (c != temp) {
//					temp = c;
//					arrayList2.add("约" + c + "米");
//				}
//				arrayList2.add(goods);
//			} else if (b < 1000) {
//				int c = (int) b / 100 * 100;
//				if (c != temp) {
//					temp = c;
//					arrayList2.add("约" + c + "米");
//				}
//				arrayList2.add(goods);
//			} else if (b < 10000) {
//				int c = (int) b / 1000 * 1000;
//				if (c != temp) {
//					temp = c;
//					arrayList2.add("约" + c + "米");
//				}
//				arrayList2.add(goods);
//			} else if (b < 100000) {
//				int c = (int) b / 10000 * 10000;
//				if (c != temp) {
//					temp = c;
//					arrayList2.add("约" + c + "米");
//				}
//				arrayList2.add(goods);
//			}
//		}
//		return arrayList2;
//	}

//	@Override
//	public void onCallbackFromThread(ResultData resultData, int tashId) {
//		Log.i("-------onCallbackFromThread-------");
//		overlay.setVisibility(View.GONE);
//		isFrist = true;
//		shouldGetData = false;
//
//		if (startGeo != null && endGeo != null) {
//			startGeo = endGeo;
//		}
//		if (resultData != null && resultData.getArrayList() != null) {
//			needToGetData = false;
//		} else {
//			return;
//		}
//		currPage++;
//		if (!deleteOldData) {
//			this.arrayList.addAll(resultData.getArrayList());
//			if (this.arrayList != null) {
//				Collections.sort(this.arrayList, new ComparatorHotelDistance());
//				// 进行距离分组
//				this.arrayList = predList(this.arrayList);
//			}
//		} else {
//			currPage = 1;
//			this.arrayList = resultData.getArrayList();
//			if (this.arrayList != null) {
//				Collections.sort(this.arrayList, new ComparatorHotelDistance());
//				// 进行距离分组
//				this.arrayList = predList(this.arrayList);
//			}
//		}
//		this.total = resultData.getTotal();
//		// if ((currPage) * APIContants.MAP_PAGENUM >= total) {
//		// listView.removeFooterView(footView);
//		// } else {
//		// if (listView.getFooterViewsCount() == 0) {
//		// listView.addFooterView(footView);
//		// }
//		// }
//		// if (deleteOldData) {
//		// myAdapter = new MyAdapter(this, arrayList, listView);
//		// listView.setAdapter(myAdapter);
//		// } else {
//		// if (myAdapter == null) {
//		// myAdapter = new MyAdapter(this, arrayList, listView);
//		// listView.setAdapter(myAdapter);
//		// } else {
//		// myAdapter.notifyDataSetChanged();
//		// }
//		// }
//
//		// refreshMap();
//	}

	// @Override
	// protected void onResume() {
	// super.onResume();
	// if(!isMoviesLoadAll){
	// new GetMovieInfoTask(this).execute();
	// }
	// Log.i("-------onResume---------");
	// if (!NetManger.checkNetWork(this)) {
	// // Toast.makeText(this, "离线状态，附近团购不可用", 0).show();
	// // map_layout.setVisibility(View.GONE);
	// needToGetData = true;
	// Log.i("-------onResume--NO NET-------");
	// } else {
	// // map_layout.setVisibility(View.INVISIBLE);
	// Log.i("-------onResume-- NET-------");
	// }
	// // if (needToGetData) {
	// currPage = 0;
	// sendToServer("1");
	// // }
	// initTopView();
	// }

	// private void sendToServer(String radius) {
	// deleteOldData = true;
	// String param = "?ctype=iphone&m=get_aroundlist";
	// String reqUrl = APIContants.API + param;
	// HashMap<String, String> hashMap = new HashMap<String, String>();
	// hashMap.put("start", String.valueOf(currPage * APIContants.MAP_PAGENUM));
	// hashMap.put("num", String.valueOf(APIContants.MAP_PAGENUM));
	// hashMap.put("currPage", currPage + "");
	// if (currLat != null && !"".equals(currLat) && currLon != null &&
	// !"".equals(currLon)) {
	// hashMap.put("lat", currLat);
	// hashMap.put("lon", currLon);
	// } else {
	// if (AppContext.currLoc != null) {
	// hashMap.put("lat", AppContext.currLoc.getLat());
	// hashMap.put("lon", AppContext.currLoc.getLon());
	// } else {
	// String lat = getSharedPreferences("locInfo",
	// MODE_WORLD_WRITEABLE).getString("currlat", "39.896691");
	// String lng = getSharedPreferences("locInfo",
	// MODE_WORLD_WRITEABLE).getString("currlng", "116.3918");
	// hashMap.put("lat", lat);
	// hashMap.put("lon", lng);
	// }
	// }
	//
	// hashMap.put("radius", radius);
	// // hashMap.put("goodstype",goodsType);
	//
	// // ThreadManger.exeTask(this, APIContants.AROUNDLIST, hashMap, reqUrl,
	// // true, this);
	// }
	// private void sendToServer(String lat, String lon) {
	// Log.i("-------MovieCinemaAct.sendToServer---lat=" + lat + "lon=" + lon);
	// overlay.setVisibility(View.VISIBLE);
	// String param = "?ctype=iphone&m=get_aroundlist";
	// String reqUrl = APIContants.API + param;
	// HashMap<String, String> hashMap = new HashMap<String, String>();
	// hashMap.put("start", "0");
	// hashMap.put("num", String.valueOf(APIContants.MAP_PAGENUM));
	// currLat = lat;
	// currLon = lon;
	// hashMap.put("lat", lat);
	// hashMap.put("lon", lon);
	// hashMap.put("currPage", currPage + "");
	//
	// hashMap.put("radius", "0");
	// // hashMap.put("goodstype",goodsType);
	//
	// // ThreadManger.exeTask(this, APIContants.AROUNDLIST, hashMap, reqUrl,
	// // false, this);
	// }

//	private void initGoogleMapView() {
//		// 初始化map
//		initMap();
//		// getMyLocationGeoPoint();
//		// initTop();
//		// initCate();
//		TextView my_loc = (TextView) findViewById(R.id.my_loc);
//		my_loc.setOnClickListener(this);
//		// initFootView();
//	}

	private boolean isCanHandlerRepeat = true;
//	private Handler handler = new Handler() {
//		public void handleMessage(Message msg) {
//			// System.out.println("AppConstants.mGeoPoint     --     "+AppConstants.mGeoPoint);
//			// if(AppConstants.mGeoPoint!=null){
//			// myGeoPoint = AppConstants.mGeoPoint;
//			// }else{
//			myGeoPoint = mLocationOverlay.getMyLocation();
//			// }
//			// System.out.println("myGeoPoint        "+myGeoPoint.getLongitudeE6()+"               "+myGeoPoint.getLatitudeE6());
//			if (myGeoPoint != null) {
//				if (myGeoPoint.getLongitudeE6() != 0 && myGeoPoint.getLatitudeE6() != 0) {
//					AppConstants.mGeoPoint = myGeoPoint;
//					Message ms = new Message();
//					ms.what = 1;
//					handlerLocation.sendMessage(ms);
//					mMapController.animateTo(myGeoPoint);
//				} else {
//					if (isCanHandlerRepeat) {
//						Message ms = new Message();
//						ms.what = Constants.FIRST_LOCATION;
//						handler.sendMessage(ms);
//					}
//				}
//				// handlerLocation.handleMessage(Message.obtain(handler, 1));
//			}
//			android.util.Log.e(TAG, "handler location :" + msg.what + " , myGeoPoint: " + myGeoPoint);
//			// sendToServer("1");
//			try {
//				if (msg.what == Constants.FIRST_LOCATION) {
//
//					mMapController.animateTo(myGeoPoint);
//					// android.util.Log.e(TAG,"geo: "+myGeoPoint.toString());
//					double lng = (double) myGeoPoint.getLongitudeE6() / 1000000;
//					double lat = (double) myGeoPoint.getLatitudeE6() / 1000000;
//					// 成功后需要请求周边的数据
//
//				} else if (Constants.LOCATION_MANUAL == msg.what) {
//					//
//					mMapController.animateTo(myGeoPoint);
//				}
//			} catch (NullPointerException e) {
//				e.printStackTrace();
//			}
//		}
//
//	};

	// 获得当前位置向服务端请求周边信息
	// private GeoPoint getMyLocationGeoPoint() {
	// // Log.i("-------MovieCinemaAct.getMyLocationGeoPoint()-------");
	// // if (locationManager == null) {
	// // locationManager = (LocationManager)
	// // getSystemService(Context.LOCATION_SERVICE);
	// // criteria = new Criteria();
	// // criteria.setAccuracy(Criteria.ACCURACY_FINE);
	// // criteria.setAltitudeRequired(false);
	// // criteria.setBearingRequired(false);
	// // criteria.setCostAllowed(false);
	// // criteria.setPowerRequirement(Criteria.POWER_LOW);
	// // }
	// //
	// // // 取得效果最好的Criteria
	// // String provider = locationManager.getBestProvider(criteria, true);
	// // Log.i("-------provider()--provider=-----" + provider);
	// // // 得到Location
	// // Location location = locationManager.getLastKnownLocation(provider);
	// GeoPoint geoPoint = null;
	// // if (location != null) {
	// // geoPoint = new GeoPoint((int) (location.getLatitude() * 1E6),
	// // (int) (location.getLongitude() * 1E6));
	// // } else {
	// if (myGeoPoint != null)
	// geoPoint = new GeoPoint(39909230, 116397428);
	// System.out.println("geopoint    " + geoPoint);
	// // }
	// // Log.i("-------MovieCinemaAct.initView()--Latitude=-----"
	// // + (int) (geoPoint.getLatitudeE6()));
	// // Log.i("-------MovieCinemaAct.initView()--Longitude=-----"
	// // + (int) (geoPoint.getLongitudeE6()));
	// return geoPoint;
	//
	// }

//	private void initMap() {
//		Log.i("-------onCreate---initMap------");
//		mapView = (MapView) findViewById(R.id.mapview);
//		mMapController = mapView.getController();
//
//		// gpsLocationManager = (LocationManager)
//		// this.getSystemService(Context.LOCATION_SERVICE);
//
//		// LinearLayout zoomLayout = (LinearLayout) findViewById(R.id.zoom);
//		// View zoomView = mapView.getZoomControls();
//		//
//		// zoomLayout.addView(zoomView, new LinearLayout.LayoutParams(
//		// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//		mapView.displayZoomControls(true);
//		mapView.setBuiltInZoomControls(false);
//		mMapController.setZoom(8);// 17周边1km,12周边5km
//		// mapview 缓存
//		mapView.setDrawingCacheEnabled(true);
//		mapView.buildDrawingCache();
//		mapView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//		mapView.preLoad();
//
//		mLocationOverlay = new MyLocationOverlay(this, mapView);
//		mLocationOverlay.enableMyLocation();
//		overlays = mapView.getOverlays();
//		overlays.add(mLocationOverlay);
//		// 实现初次定位使定位结果居中显示
//		// handler.sendMessage(Message.obtain(handler,
//		// Constants.FIRST_LOCATION));
//		GeoPoint geo = new GeoPoint((int) (22.54 * 1e6), (int) (114.05 * 1e6));
//		mMapController.setCenter(geo);
//		// mMapController.animateTo(geo);
//		System.out.println("AppConstants.mGeoPoint  map              " + AppConstants.mGeoPoint);
//		if (AppConstants.mGeoPoint != null) {
//			myGeoPoint = AppConstants.mGeoPoint;
//			Message ms = new Message();
//			ms.what = Constants.FIRST_LOCATION;
//			handler.sendMessage(ms);
//			// handler.sendMessage(Message.obtain(handler,
//			// Constants.FIRST_LOCATION));
//			// handlerLocation.handleMessage(Message.obtain(handler, 1));
//		} else {
//			thread = new TimeThread();
//			thread.start();
//			mLocationOverlay.runOnFirstFix(new Runnable() {
//				public void run() {
//					android.util.Log.e(TAG, " runOnFirstFix...");
//					System.out.println("handler--------------");
//					Message ms = new Message();
//					ms.what = Constants.FIRST_LOCATION;
//					handler.sendMessage(ms);
//
//				}
//			});
//		}
//
//		setMapOnTouch();
//		initPopUp();
//	}

//	class TimeThread extends Thread {
//
//		@Override
//		public void run() {
//			long curtime = System.currentTimeMillis();
//
//			while (!lacationFlag) {
//				if (System.currentTimeMillis() - curtime >= 1000 * 50) {
//					// System.out.println("curtime - System.currentTimeMillis()      ----------    "+(curtime
//					// - System.currentTimeMillis()));
//					Message ms = new Message();
//					ms.what = 3;
//					handlerLocation.sendMessage(ms);
//					// handlerLocation.handleMessage(Message.obtain(handler,
//					// 1));
//					isCanHandlerRepeat = false;
//
//					break;
//				}
//
//			}
//			System.out.println("stop");
//		}
//
//	}

	/**
	 * 缓冲条
	 */
//	private void initTopView() {
//		Log.i("-------MovieCinemaAct.initTopView()-------");
//		// 中间显示字母的标签
//		overlay = this.getLayoutInflater().inflate(R.layout.pop_dialog, null);
//		android.view.WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
//				WindowManager.LayoutParams.TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, PixelFormat.TRANSLUCENT);
//		int srcHei = getWindowManager().getDefaultDisplay().getHeight();
//		int srcWid = getWindowManager().getDefaultDisplay().getWidth();
//		if (srcHei < 800) {
//			int y = getWindowManager().getDefaultDisplay().getHeight() / 2;
//			int x = getWindowManager().getDefaultDisplay().getWidth() / 2;
//			Log.i("y : " + y);
//			Log.i("x : " + x);
//			layoutParams.y = -(y - 80);
//			layoutParams.x = x - 40;
//		} else {
//			int y = getWindowManager().getDefaultDisplay().getHeight() / 2;
//			int x = getWindowManager().getDefaultDisplay().getWidth() / 2;
//			Log.i("y : " + y);
//			Log.i("x : " + x);
//			layoutParams.y = -(y - 120);
//			layoutParams.x = x - 60;
//		}
//
//		// getWindowManager().addView(overlay, layoutParams);
//		overlay.setVisibility(View.GONE);
//	}

	// View footView;
	// private void initFootView() {
	// footView = LayoutInflater.from(this).inflate(R.layout.next_page_bar,
	// null);
	// View next_page = (View) footView.findViewById(R.id.next_page);
	// ProgressBar loading = (ProgressBar) footView.findViewById(R.id.loading);
	// next_page.setOnClickListener(this);
	// }
	//
	// private void initCate() {
	// if(AppContext.typeList!=null){
	// initCateView(AppContext.typeList);
	// }
	// }

	// protected void initCateView(ArrayList<Type> arrayList2) {
	// if(arrayList2!=null && arrayList2.size()>0){
	// category_text.setClickable(true);
	// cateStr = new String[arrayList2.size()];
	// for(int i=0;i<arrayList2.size();i++){
	// Type type = arrayList2.get(i);
	// cateStr[i] = type.getTypeName();
	// }
	// category_text.setText(cateStr[0]);
	// }else{
	// category_text.setClickable(false);
	// }
	// }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.right2_icon:// search
		// if (search_bar.getVisibility() == View.INVISIBLE) {
		// search_bar.setVisibility(View.VISIBLE);
		// } else {
		// search_bar.setVisibility(View.INVISIBLE);
		// }
		// break;
		// case R.id.category_text:// 分类选择
		// break;
//		case R.id.my_loc:// 我的位置
//			// 实现初次定位使定位结果居中显示
//			mLocationOverlay.runOnFirstFix(new Runnable() {
//				public void run() {
//					android.util.Log.e(TAG, " runOnFirstFix...");
//					Message ms = new Message();
//					ms.what = Constants.FIRST_LOCATION;
//					handler.sendMessage(ms);
//					// handler.sendMessage(Message.obtain(handler,
//					// Constants.FIRST_LOCATION));
//				}
//			});
			// if (myGeoPoint != null) {
			// Log.i("-------my_loc-------");
			// // 当前中心 和 我的位置的 距离
			// if (endLat > 0 && endLon > 0) {
			// double stLat = (double) (myGeoPoint.getLatitudeE6() / 1e6);
			// double stLon = (double) (myGeoPoint.getLongitudeE6() / 1e6);
			// double distance = getDistance(stLat, stLon, endLat / 1e6,
			// endLon / 1e6);
			// if (distance / 1000 > 2) {
			// myLoc = true;
			// // 获取中点值
			// sendToServer(String.valueOf(stLat),
			// String.valueOf(stLon));
			// }
			// }
			//
			// mMapController.animateTo(myGeoPoint);
			// }
//			break;
		// case R.id.right_icon:// 地图、列表 切换
		// currPage = 0;
		// this.arrayList.clear();
		// deleteOldData = true;
		// if(listView.getVisibility() == View.GONE){
		// getSharedPreferences("openMap", MODE_WORLD_WRITEABLE)
		// .edit().putBoolean("openMap", false).commit();
		// listView.setVisibility(View.VISIBLE);
		// map_layout.setVisibility(View.GONE);
		// list_map_icon.setText(" 地图模式 ");
		// sendToServer("1");
		// }else{
		// getSharedPreferences("openMap", MODE_WORLD_WRITEABLE)
		// .edit().putBoolean("openMap", true).commit();
		// listView.setVisibility(View.GONE);
		// map_layout.setVisibility(View.VISIBLE);
		// list_map_icon.setText(" 列表模式 ");
		// sendToServer("0");
		// }
		// break;
		// case R.id.next_page:// 获取下一页数据
		// if(!NetManger.checkNetWork(this)){
		// readFromLocal();
		// }else{
		// if(currPage == 0 && total == 0){
		// sendToServer("1");
		// }else{
		// if((currPage)*APIContants.PAGENUM<total){//获取下一页数据
		// sendToServer("1");
		// }
		// }
		// }
		// break;
		// default:
		// break;
		// }
		}
	}

	// String[] cateStr = { "火锅餐饮", "KTV", "生活用品", "旅游" };
	// int cate_which;

	// TextView list_map_icon;
	// private void initTop() {
	// findViewById(R.id.left_icon).setVisibility(View.GONE);
	// list_map_icon = (TextView) findViewById(R.id.right_icon);
	// list_map_icon.setVisibility(View.VISIBLE);
	// list_map_icon.setTextSize(14);
	// list_map_icon.setOnClickListener(this);
	// list_map_icon.setBackgroundResource(R.drawable.button1_selector);
	// list_map_icon.setText(" 列表模式 ");
	// TextView title = (TextView) findViewById(R.id.title);
	// title.setText("周边团购");
	// category_text=(TextView)findViewById(R.id.category_text);
	// category_text.setOnClickListener(this);
	// category_text.setVisibility(View.GONE);
	// }

	/**
	 * 初始化mapView
	 */

//	private void setMapOnTouch() {
//		mapView.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if (event.getAction() == MotionEvent.ACTION_DOWN) {
//					Log.i("DOWN");
//
//					// 记录开始坐标
//					GeoPoint gp = mapView.getMapCenter();
//					if (isFrist) {
//						// 记录开始时间
//						start = System.currentTimeMillis();
//						isFrist = false;
//						startGeo = gp;
//						startLat = startGeo.getLatitudeE6();
//						startLon = startGeo.getLongitudeE6();
//					}
//
//				} else if (event.getAction() == MotionEvent.ACTION_UP) {
//					Log.i("up");
//					if (popView != null) {
//						popView.setVisibility(View.GONE);
//					}
//					// 记录结束坐标
//					GeoPoint gp = mapView.getMapCenter();
//					endGeo = gp;
//					endLat = gp.getLatitudeE6();
//					endLon = gp.getLongitudeE6();
//
//					// 计算距离
//					double distance = getDistance(startLat / 1e6, startLon / 1e6, endLat / 1e6, endLon / 1e6);
//					Log.i("distance： " + distance / 1000 + "KM");
//
//					if (distance / 1000 > 1 && !shouldGetData) {// 两点距离大于0.1km
//						// 是，加载下一页数据
//						deleteOldData = true;
//						shouldGetData = true;
//						currCenterPoint = gp;
//						// sendToServer(String.valueOf(endLat / 1e6),
//						// String.valueOf(endLon / 1e6));
//					}
//					if (!NetManger.netWorkIsAvailable(MovieCinemaAct.this)) {
//						shouldGetData = false;
//					}
//				}
//				return false;
//			}
//		});
//	}

	public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
		double EARTH_RADIUS = 6378.137d;
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 
	 * @param startLat
	 * @param startLon
	 * @param endLat
	 * @param endLon
	 * @return 距离 单位 ：米
	 */
	public double getDistance(double startLat, double startLon, double endLat, double endLon) {
		Log.i("-------MovieCinemaAct.getDistance-------");
		float[] results = new float[1];
		Location.distanceBetween(startLat, startLon, endLat, endLon, results);
		return results[0];
	}



	

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	class SearchAdapter extends BaseAdapter {

		private LayoutInflater layoutFlater;
		private List<String> searchResultList;

		private ViewHolder viewHolder;

		public SearchAdapter() {
			super();
			layoutFlater = LayoutInflater.from(mContext);
		}

		public void addList(List<String> list) {
			this.searchResultList = list;
		}

		public void clear() {
			if (this.searchResultList != null)
				this.searchResultList.clear();
		}

		@Override
		public int getCount() {
			return searchResultList.size();
		}

		@Override
		public Object getItem(int position) {
			return searchResultList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// System.out.println("dongdianzhou" + "wofengle" +
			// searchResultList.size() + "getView");
			viewHolder = new ViewHolder();
			if (convertView == null) {
				convertView = layoutFlater.inflate(R.layout.search_list, null);
				viewHolder.searchContext = (TextView) convertView.findViewById(R.id.contextText);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.searchContext.setText(searchResultList.get(position));
			return convertView;
		}

		class ViewHolder {
			private TextView searchContext;
		}
	}

	// public void getMovieCinemaFromStatic(){
	//
	// }

	class GetMovieInfoTask extends MovieAsyncTask<String, String, MovieCinemaList> {
		public String exception;

		public GetMovieInfoTask(Activity activity) {
			super(activity, null, true, true, false);
		}

		@Override
		protected MovieCinemaList doInBackground(String... params) {

			try {

				lib = MovieLib.getInstance(MovieCinemaAct.this);
				if (AppConstants.movieCinema != null) {
					movieCinemaList = AppConstants.movieCinema;
				} else {
					movieCinemaList = lib.getCinema(MovieCinemaAct.this);
				}
				movieNearbyCinema = new ArrayList<MovieCinema>();

				// mlist = movieCinema.mList;
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

			return movieCinemaList;
		}

		@Override
		protected void onPostExecute(MovieCinemaList result) {
			super.onPostExecute(result);
			if (result != null && result.isSuccess()) {
				MovieCinema tempMovie;
				try {
					ArrayList<MovieCinemaListInner> movieInner = result.mList;
					for (int i = 0; i < movieInner.size(); i++) {
						try {
							ArrayList<MovieCinema> movieCinemaList = movieInner.get(i).cinemas;
//							double diss = CalculateDisByLonAndLat.gps2m(((double) myGeoPoint.getLatitudeE6()) / 1000000,
//									((double) myGeoPoint.getLongitudeE6()) / 1000000, 22.53323, 114.063578);
//							if (diss > 1000000) {
//								mMapController.setZoom(4);
//							} else {
//								if (diss < 5000 && diss > 1000) {
//									mMapController.setZoom(11);
//								} else if (diss <= 1000) {
//									mMapController.setZoom(14);
//								} else if (diss > 5000 && diss < 50000) {
//									mMapController.setZoom(10);
//								} else if (diss >= 50000 && diss < 100000) {
//									mMapController.setZoom(8);
//								} else if (diss >= 100000) {
//									mMapController.setZoom(6);
//								}
//							}
							for (int j = 0; j < movieCinemaList.size(); j++) {
								try {
									tempMovie = movieCinemaList.get(j);
//									String latStr = tempMovie.latitude;
//									String lonStr = tempMovie.longitude;
//									double lat = Double.parseDouble(latStr);
//									double lon = Double.parseDouble(lonStr);
//									double dis = CalculateDisByLonAndLat.gps2m(((double) myGeoPoint.getLatitudeE6()) / 1000000,
//											((double) myGeoPoint.getLongitudeE6()) / 1000000, lat, lon);
//
//									if (dis <= 5000) {
//										movieNearbyCinema.add(tempMovie);
//									}
									movieNearbyCinema.add(tempMovie);
								} catch (NumberFormatException e) {
									e.printStackTrace();
								}
							}
						} catch (NullPointerException e) {
							e.printStackTrace();
							break;
						}
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				Message ms = new Message();
				ms.what = 2;
				handlerLocation.sendMessage(ms);
				Message msg = new Message();
				try {
					initNaviLayout();

					isMoviesLoadAll = true;

					// 显示到地图上先
//					if (movieCinema != null) {
//						if (movieCinema.mList != null) {
//							if (movieCinema.mList.size() > 0) {
//								// System.out.println("movieCinema.mList====   "+movieCinema.mList.size());
//								if (movieNearbyCinema.size() != 0) {
//									setNearUi(movieNearbyCinema);
//								} else {
//									setUi(movieCinema.mList);
//								}
//							}
//						}
//					}
//					mapView.invalidate();
					if (movieNearbyCinema.size() == 0) {
						intiListView(0);
						msg.what = 0;
					} else if (movieNearbyCinema.size() != 0) {
						intiNearList();
						msg.what = 1;
					}
				} catch (NullPointerException e) {

					msg.what = 2;
					Toast.makeText(MovieCinemaAct.this, "定位失败，请尝试重新连接或确认您手机的定位系统是否已打开", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}

//				if (AppConstants.movieCinema == null) {
//					handler1.sendMessage(msg);
//				}

				AppConstants.movieCinema = result;
			} else {
				Message ms = new Message();
				ms.what = 2;
				handlerLocation.sendMessage(ms);
				// handlerLocation.handleMessage(Message.obtain(handler, 2));
				Toast.makeText(MovieCinemaAct.this, "连接失败!", Toast.LENGTH_LONG).show();
				finish();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

	}

//	private Handler handler1 = new Handler() {
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case 0:
//				showDialog(false);
//				break;
//			case 1:
//				showDialog(true);
//				break;
//			case 2:
//				change_list_map.setBackgroundResource(backgrounds[0]);
//				intiListView(0);
//				Transition3d.executeRotation(curr_l, cinemalist_l, false, change_list_map);
//				curr_l = cinemalist_l;
//				ismap = false;
//				break;
//			}
//
//		}
//	};

//	private void showDialog(final boolean isHasNear) {
//
//		final AlertDialog.Builder builder = new AlertDialog.Builder(MovieCinemaAct.this);
//		builder.setTitle(R.string.map_and_list_dialog_title);
//		if (!isHasNear)
//			builder.setMessage(R.string.no_cinema_by_near);
//		else
//			builder.setMessage(R.string.cinema_by_near);
//
//		builder.setPositiveButton("地图模式", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int whichButton) {
//				if (curr_l == cinemalist_l) {
//					Transition3d.executeRotation(curr_l, map_layout, false, change_list_map);
//					curr_l = map_layout;
//					change_list_map.setBackgroundResource(backgrounds[1]);
//					ismap = true;
//				}
//				AppConstants.mapOrListStatus = "map_layout";
//
//			}
//		});
//		builder.setNeutralButton("列表模式", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int whichButton) {
//				if (curr_l == map_layout) {
//					Transition3d.executeRotation(curr_l, cinemalist_l, false, change_list_map);
//					curr_l = cinemalist_l;
//					change_list_map.setBackgroundResource(backgrounds[0]);
//
//					ismap = false;
//				}
//				AppConstants.mapOrListStatus = "cinemalist_l";
//			}
//		});
//		if (isActivityStart) {
//			builder.create().show();
//		}
//	}

	// ----------------地图-------------------E

	@Override
	protected void onStop() {
		super.onStop();
		isActivityStart = false;
		System.out.println("recycle----");

	}

	class GetCinemaTast1 extends MovieAsyncTask<String, String, String> {
		public String exception;
		String json = "";

		public GetCinemaTast1(Activity activity) {
			super(activity, null, true, true, false);
		}

		@Override
		protected String doInBackground(String... params) {

			try {

				MovieRequest movieRequest = new MovieRequest(MovieCinemaAct.this);
				json = movieRequest.getCinemaRequest();
				System.out.println("json getCinemaRequest   ------    " + json);
				PublicUtils.saveToLocalFile(MovieCinemaAct.this, json, "cinema_json");
			} catch (HttpException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (IOException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			}
			return json;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null && !json.equals("")) {

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
//		sortList = null;
		movieCinemaList = null;
//		sortList1 = null;
//		sortListSeatAndQuan = null;
//		beforeSortListSeat = null;
//		sortListSeat = null;
//		sortList0 = null;
//		beforeSortListSeatAndQuan = null;
//		beforeSortListQuan = null;
//		sortListQuan = null;
//		sortList2 = null;
		search_area_result = null;
		search_cinema_result = null;
//		overlays = null;
		// if(!NetImageView.isWifi(mContext)){
		// AppConstants.mGeoPoint = null;
		// AppConstants.movieCinema = null;
		// AppConstants.mapOrListStatus = "";
		// AppConstants.movieInfo = null;
		// AppConstants.movieCinemaList = null;
		app.getAsyncImageLoader().recycleBitmaps();
		// }
	}

	public void onResume() {
		super.onResume();
//		this.mLocationOverlay.enableMyLocation();
		CmccDataStatistics.onStart(this);
	}

	public void onPause() {
		super.onPause();
//		this.mLocationOverlay.disableMyLocation();
		CmccDataStatistics.onStop(this);
	}
}
