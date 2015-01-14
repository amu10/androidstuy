package com.szcmcc.movie.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.PixelFormat;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.mapabc.mapapi.core.GeoPoint;
import com.mapabc.mapapi.core.OverlayItem;
import com.mapabc.mapapi.map.ItemizedOverlay;
import com.mapabc.mapapi.map.MapActivity;
import com.mapabc.mapapi.map.MapView;
import com.mapabc.mapapi.map.Overlay;
import com.szcmcc.movie.R;
import com.szcmcc.movie.map.ThreadManger.DoInSubThread;
import com.szcmcc.movie.util.Log;

/**
 * 周边
 */
public class AroundTuanActivity extends MapActivity implements
		View.OnClickListener, ThreadCallBack, DoInSubThread {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aroundtuan_view);
		Log.i("-------onCreate---needToGetData------" + needToGetData);
		initView();
		// refreshMap();
		// if (!NetManger.checkNetWork(this)) {
		// needToGetData = true;
		// } else {
		// setUi();
		// }
	}

	// public class CMDExecute {
	//
	// public synchronized String run(String[] cmd, String workdirectory)
	// throws Exception {
	// String result = "";
	//
	// try {
	// ProcessBuilder builder = new ProcessBuilder(cmd);
	// // set working directory
	// if (workdirectory != null)
	// builder.directory(new File(workdirectory));
	// builder.redirectErrorStream(true);
	// Process process = builder.start(); // 开始执行cmd
	// InputStream in = process.getInputStream(); // 获得返回结果
	// byte[] re = new byte[1024];
	// while (in.read(re) != -1) {
	// result = result + new String(re);
	// }
	// in.close();
	//
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// return result;
	// }
	// }

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
	private MapView mapView;
	// public static ArrayList list = new ArrayList();
	ArrayList<MapPoi> arrayList = new ArrayList();
	private int total;
	private View popView, map_layout;

	// private TextView search_bar, category_text;

	// ----------------------数据处理------------------------------------S
	private static ArrayList predList(ArrayList arrayList) {
		ArrayList arrayList2 = new ArrayList();
		int temp = -1;
		boolean isTen = true;
		for (int i = 0; i < arrayList.size(); i++) {
			MapPoi goods = (MapPoi) arrayList.get(i);
			double b = goods.getDistance();
			if (b < 20) {
				if (isTen) {
					isTen = false;
					arrayList2.add("约10米");
				}
				arrayList2.add(goods);
			} else if (b < 100) {
				int c = (int) b / 10 * 10;
				if (c != temp) {
					temp = c;
					arrayList2.add("约" + c + "米");
				}
				arrayList2.add(goods);
			} else if (b < 1000) {
				int c = (int) b / 100 * 100;
				if (c != temp) {
					temp = c;
					arrayList2.add("约" + c + "米");
				}
				arrayList2.add(goods);
			} else if (b < 10000) {
				int c = (int) b / 1000 * 1000;
				if (c != temp) {
					temp = c;
					arrayList2.add("约" + c + "米");
				}
				arrayList2.add(goods);
			} else if (b < 100000) {
				int c = (int) b / 10000 * 10000;
				if (c != temp) {
					temp = c;
					arrayList2.add("约" + c + "米");
				}
				arrayList2.add(goods);
			}
		}
		return arrayList2;
	}

	@Override
	public void onCallbackFromThread(ResultData resultData, int tashId) {
		Log.i("-------onCallbackFromThread-------");
		overlay.setVisibility(View.GONE);
		isFrist = true;
		shouldGetData = false;

		if (startGeo != null && endGeo != null) {
			startGeo = endGeo;
		}
		if (resultData != null && resultData.getArrayList() != null) {
			needToGetData = false;
		} else {
			return;
		}
		currPage++;
		if (!deleteOldData) {
			this.arrayList.addAll(resultData.getArrayList());
			if (this.arrayList != null) {
				Collections.sort(this.arrayList, new ComparatorHotelDistance());
				// 进行距离分组
				this.arrayList = predList(this.arrayList);
			}
		} else {
			currPage = 1;
			this.arrayList = resultData.getArrayList();
			if (this.arrayList != null) {
				Collections.sort(this.arrayList, new ComparatorHotelDistance());
				// 进行距离分组
				this.arrayList = predList(this.arrayList);
			}
		}
		this.total = resultData.getTotal();
		// if ((currPage) * APIContants.MAP_PAGENUM >= total) {
		// listView.removeFooterView(footView);
		// } else {
		// if (listView.getFooterViewsCount() == 0) {
		// listView.addFooterView(footView);
		// }
		// }
		// if (deleteOldData) {
		// myAdapter = new MyAdapter(this, arrayList, listView);
		// listView.setAdapter(myAdapter);
		// } else {
		// if (myAdapter == null) {
		// myAdapter = new MyAdapter(this, arrayList, listView);
		// listView.setAdapter(myAdapter);
		// } else {
		// myAdapter.notifyDataSetChanged();
		// }
		// }

		refreshMap();
	}

	// ----------------------数据处理------------------------------------E
	boolean needToGetData = false;
	private int currPage;
	private String goodsType = "1";

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("-------onResume---------");
		if (!NetManger.checkNetWork(this)) {
			Toast.makeText(this, "离线状态，附近团购不可用", 0).show();
			// map_layout.setVisibility(View.GONE);
			needToGetData = true;
			Log.i("-------onResume--NO NET-------");
		} else {
			// map_layout.setVisibility(View.INVISIBLE);
			Log.i("-------onResume-- NET-------");
		}
		// if (needToGetData) {
		currPage = 0;
		sendToServer("1");
		// }
		initTopView();
	}

	String currLat;
	String currLon;

	private void sendToServer(String radius) {
		deleteOldData = true;
		String param = "?ctype=iphone&m=get_aroundlist";
		String reqUrl = APIContants.API + param;
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("start", String.valueOf(currPage * APIContants.MAP_PAGENUM));
		hashMap.put("num", String.valueOf(APIContants.MAP_PAGENUM));
		hashMap.put("currPage", currPage + "");
		if (currLat != null && !"".equals(currLat) && currLon != null
				&& !"".equals(currLon)) {
			hashMap.put("lat", currLat);
			hashMap.put("lon", currLon);
		} else {
			if (AppContext.currLoc != null) {
				hashMap.put("lat", AppContext.currLoc.getLat());
				hashMap.put("lon", AppContext.currLoc.getLon());
			} else {
				String lat = getSharedPreferences("locInfo",
						MODE_WORLD_WRITEABLE).getString("currlat", 22.54*1e6+"");
				String lng = getSharedPreferences("locInfo",
						MODE_WORLD_WRITEABLE).getString("currlng", 114.05*1e6+"");
				hashMap.put("lat", lat);
				hashMap.put("lon", lng);
			}
		}

		hashMap.put("radius", radius);
		// hashMap.put("goodstype",goodsType);

		ThreadManger.exeTask(this, APIContants.AROUNDLIST, hashMap, reqUrl,
				true, this);
	}

	private void sendToServer(String lat, String lon) {
		Log.i("-------AroundTuanActivity.sendToServer---lat=" + lat + "lon="
				+ lon);
		overlay.setVisibility(View.VISIBLE);
		String param = "?ctype=iphone&m=get_aroundlist";
		String reqUrl = APIContants.API + param;
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("start", "0");
		hashMap.put("num", String.valueOf(APIContants.MAP_PAGENUM));
		currLat = lat;
		currLon = lon;
		hashMap.put("lat", lat);
		hashMap.put("lon", lon);
		hashMap.put("currPage", currPage + "");

		hashMap.put("radius", "0");
		// hashMap.put("goodstype",goodsType);

		ThreadManger.exeTask(this, APIContants.AROUNDLIST, hashMap, reqUrl,
				false, this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (overlay != null) {
			getWindowManager().removeView(overlay);
		}
	}

	private void initView() {
		Log.i("-------AroundTuanActivity.initView()-------");
		// 初始化map
		initMap();
		// initTop();
		// initCate();
		map_layout = findViewById(R.id.map_layout);
		TextView my_loc = (TextView) findViewById(R.id.my_loc);
		my_loc.setOnClickListener(this);
		// initFootView();
	}

	private void initMap() {
		Log.i("-------onCreate---initMap------");
		mapView = (MapView) findViewById(R.id.mapview);
		gpsLocationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		// LinearLayout zoomLayout = (LinearLayout) findViewById(R.id.zoom);
		// View zoomView = mapView.getZoomControls();
		//
		// zoomLayout.addView(zoomView, new LinearLayout.LayoutParams(
		// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mapView.displayZoomControls(true);
		mapView.setBuiltInZoomControls(true);
		mapView.getController().setZoom(17);
		// mapview 缓存
		mapView.setDrawingCacheEnabled(true);
		mapView.buildDrawingCache();
		mapView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
		mapView.preLoad();
		setMapOnTouch();
		initPopUp();
	}

	View overlay;

	/**
	 * 缓冲条
	 */
	private void initTopView() {
		Log.i("-------AroundTuanActivity.initTopView()-------");
		// 中间显示字母的标签
		overlay = this.getLayoutInflater().inflate(R.layout.pop_dialog, null);
		android.view.WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		int srcHei = getWindowManager().getDefaultDisplay().getHeight();
		int srcWid = getWindowManager().getDefaultDisplay().getWidth();
		if (srcHei < 800) {
			int y = getWindowManager().getDefaultDisplay().getHeight() / 2;
			int x = getWindowManager().getDefaultDisplay().getWidth() / 2;
			Log.i("y : " + y);
			Log.i("x : " + x);
			layoutParams.y = -(y - 80);
			layoutParams.x = x - 40;
		} else {
			int y = getWindowManager().getDefaultDisplay().getHeight() / 2;
			int x = getWindowManager().getDefaultDisplay().getWidth() / 2;
			Log.i("y : " + y);
			Log.i("x : " + x);
			layoutParams.y = -(y - 120);
			layoutParams.x = x - 60;
		}

		getWindowManager().addView(overlay, layoutParams);
		overlay.setVisibility(View.GONE);
	}

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
	boolean myLoc = false;

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
		case R.id.my_loc:// 我的位置

			if (myGeoPoint != null) {
				Log.i("-------my_loc-------");
				// 当前中心 和 我的位置的 距离
				if (endLat > 0 && endLon > 0) {
					double stLat = (double) (myGeoPoint.getLatitudeE6() / 1e6);
					double stLon = (double) (myGeoPoint.getLongitudeE6() / 1e6);
					double distance = getDistance(stLat, stLon, endLat / 1e6,
							endLon / 1e6);
					if (distance / 1000 > 2) {
						myLoc = true;
						// 获取中点值
						sendToServer(String.valueOf(stLat),
								String.valueOf(stLon));
					}
				}

				mapView.getController().animateTo(myGeoPoint);
			}
			break;
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
	private LocationManager gpsLocationManager = null;

	double startLat, startLon, endLat, endLon;

	boolean isFrist = true;
	long start = 0;
	long end = 0;
	GeoPoint startGeo;
	GeoPoint endGeo;

	private void setMapOnTouch() {
		mapView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Log.i("DOWN");

					// 记录开始坐标
					GeoPoint gp = mapView.getMapCenter();
					if (isFrist) {
						// 记录开始时间
						start = System.currentTimeMillis();
						isFrist = false;
						startGeo = gp;
						startLat = startGeo.getLatitudeE6();
						startLon = startGeo.getLongitudeE6();
					}

				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					Log.i("up");
					if (popView != null) {
						popView.setVisibility(View.GONE);
					}
					// 记录结束坐标
					GeoPoint gp = mapView.getMapCenter();
					endGeo = gp;
					endLat = gp.getLatitudeE6();
					endLon = gp.getLongitudeE6();

					// 计算距离
					double distance = getDistance(startLat / 1e6,
							startLon / 1e6, endLat / 1e6, endLon / 1e6);
					Log.i("distance： " + distance / 1000 + "KM");

					if (distance / 1000 > 1 && !shouldGetData) {// 两点距离大于0.1km
																// 是，加载下一页数据
						deleteOldData = true;
						shouldGetData = true;
						currCenterPoint = gp;
						sendToServer(String.valueOf(endLat / 1e6),
								String.valueOf(endLon / 1e6));
					}
					if (!NetManger.netWorkIsAvailable(AroundTuanActivity.this)) {
						shouldGetData = false;
					}
				}
				return false;
			}
		});
	}

	boolean deleteOldData;
	boolean shouldGetData = false;

	public static double GetDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double EARTH_RADIUS = 6378.137d;
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
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
	public double getDistance(double startLat, double startLon, double endLat,
			double endLon) {
		Log.i("-------AroundTuanActivity.getDistance-------");
		float[] results = new float[1];
		Location.distanceBetween(startLat, startLon, endLat, endLon, results);
		return results[0];
	}

	private void initPopUp() {
		Log.i("-------AroundTuanActivity.initPopUp()-------");
		popView = mapView.inflate(this, R.layout.pop_up, null);
		int width = getWindowManager().getDefaultDisplay().getWidth();
		if (width >= 480) {
			mapView.addView(popView, new MapView.LayoutParams(440, 120, null,
					MapView.LayoutParams.BOTTOM_CENTER));
		} else {
			mapView.addView(popView, new MapView.LayoutParams(300, 90, null,
					MapView.LayoutParams.BOTTOM_CENTER));
		}

		// 由于我的气泡的尾巴是在下边居中的,因此要设置成MapView.LayoutParams.BOTTOM_CENTER.
		// 这里没有给GeoPoint,在onFocusChangeListener中设置
		// view.add(popView);
		popView.setVisibility(View.GONE);
		popView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String id = (String) popView.getTag();
				// Intent intent = new Intent(AroundTuanActivity.this,
				// DetialActivity.class);
				// intent.putExtra("id", id);
				// startActivity(intent);
			}
		});
	}

	public ArrayList predData() {
		Log.i("-------AroundTuanActivity.predData-------");
		ArrayList arrayList = new ArrayList();
		MapPoi goods = null;

		for (int i = 1; i < 20; i++) {
			goods = new MapPoi();
			if (i % 2 == 0) {
				goods.setType(1);
			} else {
				goods.setType(2);
			}
			goods.setDesc("描述" + i);
			goods.setTuan_name("拉手" + i);
			goods.setId("1");
			goods.setPrice("5元" + i);
			GeoPoint geoPoint = new GeoPoint(39909230 + i * 1000,
					116397428 + (i % 2) * 1000);
			goods.setmGeoPoint(geoPoint);
			arrayList.add(goods);
		}

		return arrayList;
	}

	public void refreshMap() {
		Log.i("-------AroundTuanActivity.refreshMap---");
		mapView.getOverlays().clear();

		initMyLoc();
		if (this.arrayList != null && this.arrayList.size() > 0) {
			setUi(this.arrayList);
		}
		mapView.invalidate();
	}

	MyItemizedOverlay myItemizedOverlay;

	private void setUi(ArrayList arrayList) {
		if (popView != null && popView.getVisibility() == View.VISIBLE) {
			popView.setVisibility(View.GONE);
		}
		Log.i("-------AroundTuanActivity.setUi(ArrayList arrayList)---"
				+ arrayList.size());
		List<Overlay> overlays = mapView.getOverlays();
		myItemizedOverlay = null;
		// myItemizedOverlay.setOnFocusChangeListener(new MyOverlay());
		// 先对type进行过滤
		ArrayList arrayList2 = sort(arrayList);
		Log.i("-------AroundTuanActivity.setUi(ArrayList arrayList2)---"
				+ arrayList2.size());
		for (int i = 0; i < arrayList2.size(); i++) {
			ArrayList<MapPoi> goodsList = (ArrayList<MapPoi>) arrayList2.get(i);
			myItemizedOverlay = null;
			for (int j = 0; j < goodsList.size(); j++) {
				MapPoi goods = goodsList.get(j);
				if (myItemizedOverlay == null) {
					if (goods.getType() == 1) {
						myItemizedOverlay = new MyItemizedOverlay(
								getResources().getDrawable(R.drawable.eat_icon),
								this);
					} else if (goods.getType() == 2) {
						myItemizedOverlay = new MyItemizedOverlay(
								getResources()
										.getDrawable(R.drawable.play_icon),
								this);
					} else if (goods.getType() == 3 || goods.getType() == 5
							|| goods.getType() == 6) {
						myItemizedOverlay = new MyItemizedOverlay(
								getResources().getDrawable(
										R.drawable.beauty_icon), this);
					}
					myItemizedOverlay.setOnFocusChangeListener(new MyOverlay());
				}
				GeoPoint gp = goods.getmGeoPoint();
				OverlayItem overlayitem = new OverlayItem(gp, goods.getDesc(),
						goods.getId() + "#" + goods.getPrice() + "#"
								+ goods.getTuan_name() + "#" + goods.getTime()
								+ "#" + goods.getUrl());
				myItemizedOverlay.addOverlay(overlayitem);
//				myItemizedOverlay.addCurrentOverlayData(goods);
			}
			if (myItemizedOverlay != null) {
				overlays.add(myItemizedOverlay);
			}
		}
	}

	private ArrayList sort(ArrayList arrayList2) {
		Log.i("-------AroundTuanActivity.sort(ArrayList arrayList)---"
				+ arrayList2.size());
		ArrayList total = new ArrayList();
		ArrayList eat = new ArrayList(); // 吃
		ArrayList play = new ArrayList();// 玩
		ArrayList beauty = new ArrayList();// 美
		for (int i = 0; i < arrayList2.size(); i++) {
			Object obj = arrayList2.get(i);
			if (obj instanceof MapPoi) {
				MapPoi goods = (MapPoi) obj;
				if (goods.getType() == 1) {// 吃
					eat.add(goods);
				} else if (goods.getType() == 2) {// 玩
					play.add(goods);
				} else if (goods.getType() == 3 || goods.getType() == 5
						|| goods.getType() == 6) {// 美
					beauty.add(goods);
				}
			}
		}
		total.add(eat);
		total.add(play);
		total.add(beauty);
		return total;
	}

	GeoPoint myGeoPoint = null;
	GeoPoint currCenterPoint = null;

	private void initMyLoc() {
		if (AppContext.currLoc != null) {
			int lat = (int) (Double.valueOf(AppContext.currLoc.getLat()) * 1e6);
			int lng = (int) (Double.valueOf(AppContext.currLoc.getLon()) * 1e6);
			myGeoPoint = new GeoPoint(lat, lng);
			Log.i("-------AroundTuanActivity.sendToServer---lat=" + lat
					+ "lon=" + lng);
		} else {
			// Location gpsLocation = gpsLocationManager
			// .getLastKnownLocation(LocationManager.GPS_PROVIDER);
			// String lat = getSharedPreferences("locInfo",
			// MODE_WORLD_WRITEABLE)
			// .getString("currlat", "116.29079498888888");
			// String lng = getSharedPreferences("locInfo",
			// MODE_WORLD_WRITEABLE)
			// .getString("currlng", "40.03327892222223");
			// myGeoPoint = new GeoPoint((int) (Double.valueOf(lat) * 1e6),
			// (int) (Double.valueOf(lng) * 1e6));
			myGeoPoint = new GeoPoint(39909230, 116397428);
			// Log.i("-------AroundTuanActivity.sendToServer---lat="+lat+"lon="+lng);
		}
		if (myLoc) {
			mapView.getController().animateTo(myGeoPoint);
			myLoc = false;
		} else {
			if (currCenterPoint != null) {
				mapView.getController().animateTo(currCenterPoint);
			} else {
				mapView.getController().animateTo(myGeoPoint);
			}
		}

		OverlayItem overlayitem = new OverlayItem(myGeoPoint, null, null);
		myItemizedOverlay = new MyItemizedOverlay(getResources().getDrawable(
				R.drawable.my_location), this);
		myItemizedOverlay.addOverlay(overlayitem);
		MapPoi goods = new MapPoi();
		goods.setmGeoPoint(myGeoPoint);
		goods.setType(-1);
//		myItemizedOverlay.addCurrentOverlayData(goods);
		mapView.getOverlays().add(myItemizedOverlay);
	}

	class MyOverlay implements ItemizedOverlay.OnFocusChangeListener {
		private ItemizedOverlay predOver;

		@Override
		public void onFocusChanged(ItemizedOverlay overlay, OverlayItem newFocus) {

			if (popView != null) {
				popView.setVisibility(View.GONE);
			}

			Log.i("overlay : " + overlay);
			Log.i("newFocus : " + newFocus);

			if (newFocus != null) {

				MapView.LayoutParams geoLP = (MapView.LayoutParams) popView
						.getLayoutParams();
				GeoPoint geoPoint = new GeoPoint(newFocus.getPoint()
						.getLatitudeE6(), newFocus.getPoint().getLongitudeE6());

				geoLP.point = geoPoint;// 这行用于popView的定位
				mapView.getController().animateTo(geoLP.point);
				TextView nameTextView = (TextView) popView
						.findViewById(R.id.name);
				TextView location = (TextView) popView
						.findViewById(R.id.location);
				ImageView typeImageView = (ImageView) popView
						.findViewById(R.id.type);
				// TextView timeText = (TextView)
				// popView.findViewById(R.id.time1);

				String snippet = newFocus.getSnippet();// id#price#tuan_name
				String[] price_name = snippet.split("#");
				nameTextView.setText(newFocus.getTitle());
				location.setText(price_name[1] + "元");
				// tuan_name.setText(price_name[2]);
				popView.setTag(price_name[0]);
				if (!"-1".equals(price_name[3])) {
					typeImageView.setVisibility(View.VISIBLE);
				} else {
					typeImageView.setVisibility(View.GONE);
				}
				// if (price_name.length > 4) {
				// String userFace = price_name[4];
				// final ImageView user_face = (ImageView) popView
				// .findViewById(R.id.user_face);
				// Bitmap drawable = null;
				// Bitmap drawable = AsyncImageLoader.loadDrawable(userFace,
				// new ImageCallback() {
				//
				// public void imageLoaded(Bitmap imageDrawable,
				// String imageUrl) {
				// if (imageDrawable != null) {
				// user_face.setImageBitmap(imageDrawable);
				// } else {
				// user_face
				// .setImageResource(R.drawable.thum);
				// }
				// }
				// });
				// if (drawable != null) {
				// user_face.setImageBitmap(drawable);
				// } else {
				// user_face.setImageResource(R.drawable.thum);
				// }
				mapView.updateViewLayout(popView, geoLP);
				popView.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	// class MyAdapter extends BaseAdapter {
	//
	// Context context;
	// ListView listView;
	// ArrayList<Goods> arrayList;
	//
	// public MyAdapter(Context context, ArrayList<Goods> arrayList,
	// ListView listView) {
	// this.context = context;
	// this.listView = listView;
	// this.arrayList = arrayList;
	// }
	//
	// public void refresh(ArrayList arrayList) {
	// this.notifyDataSetChanged();
	// }
	//
	// @Override
	// public int getCount() {
	// if (arrayList != null && arrayList.size() > 0) {
	// return arrayList.size();
	// }
	// return 0;
	// }
	//
	// @Override
	// public Object getItem(int position) {
	// return arrayList.get(position);
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// return 0;
	// }
	//
	// class Viewhodler {
	// ImageView icon, right_flag;
	// TextView price;
	// TextView value;
	// TextView desc;
	// TextView stop_time;
	// TextView sell_discount;
	// TextView distance;
	// }
	//
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	//
	// final Viewhodler viewhodler;
	//
	// if (convertView == null) {
	// viewhodler = new Viewhodler();
	// LayoutInflater inflater = ((Activity) context)
	// .getLayoutInflater();
	// convertView = inflater.inflate(R.layout.goods_item, null);
	//
	// viewhodler.icon = (ImageView) convertView
	// .findViewById(R.id.icon);
	// viewhodler.price = (TextView) convertView
	// .findViewById(R.id.price);
	// viewhodler.value = (TextView) convertView
	// .findViewById(R.id.value);
	// viewhodler.desc = (TextView) convertView
	// .findViewById(R.id.desc);
	// viewhodler.stop_time = (TextView) convertView
	// .findViewById(R.id.stop_time);
	// viewhodler.sell_discount = (TextView) convertView
	// .findViewById(R.id.sell_discount);
	// viewhodler.distance = (TextView) convertView
	// .findViewById(R.id.distance);
	// viewhodler.right_flag = (ImageView) convertView
	// .findViewById(R.id.right_flag);
	//
	// convertView.setTag(viewhodler);
	//
	// } else {
	// viewhodler = (Viewhodler) convertView.getTag();
	// }
	//
	// Object obj = arrayList.get(position);
	// if (obj instanceof Goods) {
	// convertView.setBackgroundColor(0);
	// viewhodler.desc.setVisibility(View.VISIBLE);
	// viewhodler.desc.setTextSize(14);
	// viewhodler.desc.setTextColor(getResources().getColor(
	// R.color.item_text));
	// viewhodler.icon.setVisibility(View.VISIBLE);
	// viewhodler.price.setVisibility(View.VISIBLE);
	// viewhodler.value.setVisibility(View.VISIBLE);
	// viewhodler.stop_time.setVisibility(View.VISIBLE);
	// viewhodler.sell_discount.setVisibility(View.VISIBLE);
	// viewhodler.distance.setVisibility(View.VISIBLE);
	// viewhodler.right_flag.setVisibility(View.VISIBLE);
	//
	// Goods goods = (Goods) obj;
	// viewhodler.price.setText("￥" + goods.getPrice());
	//
	// String tuan_name = goods.getTuan_name();
	// String str = "[" + tuan_name + "] " + goods.getDesc();
	// SpannableString desc_text = new SpannableString(str);
	// desc_text.setSpan(new ForegroundColorSpan(context
	// .getResources().getColor(R.color.tuan_name1)), 0,
	// tuan_name.length() + 2,
	// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	// viewhodler.desc.setText(desc_text);
	//
	// viewhodler.sell_discount.setText("售出" + goods.getSellCount());
	// // 加上删除线
	// SpannableString ss = new SpannableString("￥" + goods.getValue());
	// ss.setSpan(new StrikethroughSpan(), 0, ss.length(),
	// Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	// viewhodler.value.setText(ss);
	// // 计算剩余时间
	// String surplusTime = TimeUtil.getDistanceTime(
	// System.currentTimeMillis(), goods.getStopTime());
	// viewhodler.stop_time.setText("剩余" + surplusTime);
	//
	// int distance = (int) goods.getDistance();
	// if (distance > 0) {
	// if (distance < 1000) {
	// viewhodler.distance.setText("约" + distance + "米");
	// } else {
	// int distanceInt = (int) Math.ceil(distance / 1000);
	// viewhodler.distance.setText("约" + distanceInt + "千米");
	// }
	// viewhodler.distance.setVisibility(View.VISIBLE);
	// } else {
	// viewhodler.distance.setVisibility(View.GONE);
	// }
	//
	// String url = goods.getUrl();
	// viewhodler.icon.setTag(url);
	// Bitmap bitmap=null;
	// // 下载图片
	// // Bitmap bitmap = AsyncImageLoader.loadDrawable(url,
	// // new ImageCallback() {
	// // @Override
	// // public void imageLoaded(Bitmap imageDrawable,
	// // String imageUrl) {
	// // ImageView imageView = (ImageView) listView
	// // .findViewWithTag(imageUrl);
	// // if (imageDrawable != null && imageView != null) {
	// // // imageView.setImageBitmap(imageDrawable);
	// // imageView
	// // .setBackgroundDrawable(new BitmapDrawable(
	// // imageDrawable));
	// // }
	// // }
	// // });
	// if (bitmap != null) {
	// // viewhodler.icon.setImageBitmap(bitmap);
	// viewhodler.icon.setBackgroundDrawable(new BitmapDrawable(
	// bitmap));
	// } else {
	// viewhodler.icon.setBackgroundResource(R.drawable.thum);
	// }
	// } else {// 显示距离分组
	// convertView.setBackgroundColor(getResources().getColor(
	// R.color.distance_back));
	// String str = (String) obj;
	// viewhodler.desc.setVisibility(View.VISIBLE);
	// viewhodler.desc.setText(str);
	// viewhodler.desc.setTextColor(Color.WHITE);
	// viewhodler.desc.setTextSize(18);
	// viewhodler.icon.setVisibility(View.GONE);
	// viewhodler.price.setVisibility(View.GONE);
	// viewhodler.value.setVisibility(View.GONE);
	// viewhodler.stop_time.setVisibility(View.GONE);
	// viewhodler.sell_discount.setVisibility(View.GONE);
	// viewhodler.distance.setVisibility(View.GONE);
	// viewhodler.right_flag.setVisibility(View.GONE);
	// }
	//
	// return convertView;
	// }
	// }

}
