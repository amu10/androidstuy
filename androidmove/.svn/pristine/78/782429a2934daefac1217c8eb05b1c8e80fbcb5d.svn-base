package com.szcmcc.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.cmcc.sdk.CmccDataStatistics;
import com.mapabc.mapapi.core.GeoPoint;
import com.mapabc.mapapi.core.OverlayItem;
import com.mapabc.mapapi.map.ItemizedOverlay;
import com.mapabc.mapapi.map.MapView;
import com.szcmcc.movie.R;
import com.szcmcc.movie.map.MapPoi;
import com.szcmcc.movie.map.MyItemizedOverlay;
import com.szcmcc.movie.util.Log;

/**
 * 19-影院地图
 * 
 */
public class OneCinaemaAct extends BaseMapActivity {
	GeoPoint myGeoPoint = null;
	private LocationManager gpsLocationManager = null;
	Drawable marker;
	private String c_name = "",latitude = "",longitude = "",ordertype = "";
	private int latitudel = 0,longitudel = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initBackTitleBar();
		setContentView(R.layout.onecinaemaact);
		
		// 获取点
		Intent intent = getIntent();
		if(intent.getExtras()!=null){
			c_name = intent.getExtras().getString("c_name");
			latitude = intent.getExtras().getString("latitude");
			longitude = intent.getExtras().getString("longitude");
			ordertype = intent.getExtras().getString("ordertype");
			System.out.println("latitude         "+latitude+"     "+longitude);
			try{
				latitudel = (int)(Double.parseDouble(latitude)* 1000000);
				longitudel =(int)(Double.parseDouble(longitude)* 1000000);
				System.out.println("latitude         "+latitudel+"     "+longitudel);
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
		}
		if(!latitude.equals("")&&latitude.length()!=0){
		myGeoPoint = new GeoPoint(latitudel,longitudel);
		marker = getResources().getDrawable(R.drawable.play_icon);
		// 初始化图
		initMap();
		// 显示图层
		mapView.getController().animateTo(myGeoPoint);
		showPosition();
		// 点击事件
//		myItemizedOverlay.setOnFocusChangeListener(new MyOverlay());
		//显示地图信息框
		if(latitudel!=0&&longitudel!=0){
		MapView.LayoutParams geoLP = (MapView.LayoutParams) popView.getLayoutParams();
		GeoPoint geoPoint = new GeoPoint(latitudel,longitudel);
		geoLP.point = geoPoint;// 这行用于popView的定位
		mapView.getController().animateTo(geoLP.point);
		TextView nameTextView = (TextView) popView.findViewById(R.id.name);
		TextView location = (TextView) popView.findViewById(R.id.location);
		TextView typeImageView = (TextView) popView.findViewById(R.id.type);
		TextView kegouquan = (TextView) popView.findViewById(R.id.kegouquan);
		nameTextView.setText(c_name);
		if ("0".equals(ordertype)) {// 0 可订做,
			typeImageView.setVisibility(View.VISIBLE);
			kegouquan.setVisibility(View.GONE);
		} else if ("1".equals(ordertype)) {// 1可购卷
			typeImageView.setVisibility(View.GONE);
			kegouquan.setVisibility(View.VISIBLE);
		} else if ("2".equals(ordertype)) {// ,2是两者都有
			typeImageView.setVisibility(View.VISIBLE);
			kegouquan.setVisibility(View.VISIBLE);
		}
		mapView.updateViewLayout(popView, geoLP);
		popView.setVisibility(View.VISIBLE);
		}
		}
	}

	OverlayItem overlayitem = null;
	MyItemizedOverlay myItemizedOverlay;

	private void showPosition() {
		OverlayItem overlayitem = new OverlayItem(myGeoPoint, null, null);
		myItemizedOverlay = new MyItemizedOverlay(marker, this);
		myItemizedOverlay.addOverlay(overlayitem);
		MapPoi goods = new MapPoi();
		goods.setmGeoPoint(myGeoPoint);
		goods.setType(1);
		// myItemizedOverlay.addCurrentOverlayData(goods);
		mapView.getOverlays().add(myItemizedOverlay);

	}

	private MapView mapView;

	private void initMap() {
		Log.i("-------onCreate---initMap------");
		mapView = (MapView) findViewById(R.id.mapview);
		gpsLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		// LinearLayout zoomLayout = (LinearLayout) findViewById(R.id.zoom);
		// View zoomView = mapView.getZoomControls();
		//
		// zoomLayout.addView(zoomView, new LinearLayout.LayoutParams(
		// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mapView.displayZoomControls(true);
		mapView.setBuiltInZoomControls(false);
		mapView.getController().setZoom(17);
		// mapview 缓存
		mapView.setDrawingCacheEnabled(true);
		mapView.buildDrawingCache();
		mapView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
		mapView.preLoad();
		// setMapOnTouch();
		initPopUp();
	}

	private View popView;

	private void initPopUp() {
		Log.i("-------AroundTuanActivity.initPopUp()-------");
		popView = mapView.inflate(this, R.layout.pop_up1, null);
		int width = getWindowManager().getDefaultDisplay().getWidth();
		MapView.LayoutParams paramsMap = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT, -2, null, MapView.LayoutParams.BOTTOM_CENTER);
		paramsMap.x = (int) (paramsMap.x + marker.getIntrinsicWidth() * 1.2);
		paramsMap.y = paramsMap.y - marker.getIntrinsicHeight();
		mapView.addView(popView, paramsMap);
		// if (width >= 480) {
		// mapView.addView(popView, new MapView.LayoutParams(
		// MapView.LayoutParams.WRAP_CONTENT,
		// MapView.LayoutParams.WRAP_CONTENT, null,
		// MapView.LayoutParams.CENTER));
		// } else {
		// mapView.addView(popView, new MapView.LayoutParams(
		// MapView.LayoutParams.WRAP_CONTENT,
		// MapView.LayoutParams.WRAP_CONTENT, null,
		// MapView.LayoutParams.CENTER));
		// }

		// 由于我的气泡的尾巴是在下边居中的,因此要设置成MapView.LayoutParams.BOTTOM_CENTER.
		// 这里没有给GeoPoint,在onFocusChangeListener中设置
		// view.add(popView);
		popView.setVisibility(View.GONE);
		popView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				String id = (String) popView.getTag();
				// Intent intent = new Intent(AroundTuanActivity.this,
				// DetialActivity.class);
				// intent.putExtra("id", id);
				// startActivity(intent);
			}
		});
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

				MapView.LayoutParams geoLP = (MapView.LayoutParams) popView.getLayoutParams();
				GeoPoint geoPoint = new GeoPoint(newFocus.getPoint().getLatitudeE6(), newFocus.getPoint().getLongitudeE6());

				geoLP.point = geoPoint;// 这行用于popView的定位
				mapView.getController().animateTo(geoLP.point);
				TextView nameTextView = (TextView) popView.findViewById(R.id.name);
				TextView location = (TextView) popView.findViewById(R.id.location);
				TextView typeImageView = (TextView) popView.findViewById(R.id.type);
				TextView kegouquan = (TextView) popView.findViewById(R.id.kegouquan);
				
				nameTextView.setText(c_name);
				
				if ("0".equals(ordertype)) {// 0 可订做,
					typeImageView.setVisibility(View.VISIBLE);
					kegouquan.setVisibility(View.GONE);
				} else if ("1".equals(ordertype)) {// 1可购卷
					typeImageView.setVisibility(View.GONE);
					kegouquan.setVisibility(View.VISIBLE);
				} else if ("2".equals(ordertype)) {// ,2是两者都有
					typeImageView.setVisibility(View.VISIBLE);
					kegouquan.setVisibility(View.GONE);
				}
			
				mapView.updateViewLayout(popView, geoLP);
				popView.setVisibility(View.VISIBLE);
			}
		}
	}
	
	
	
	/**
	 * 初始化mapView
	 */

	private void setMapOnTouch() {
		mapView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Log.i("DOWN");


				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					Log.i("up");
					if (popView != null) {
						popView.setVisibility(View.GONE);
					}
				}
				return false;
			}
		});
	}
	public void onResume(){
		super.onResume();
		CmccDataStatistics.onStart(this);
		}
	public void onPause() {
		super.onPause();
		CmccDataStatistics.onStop(this);
		}
}
