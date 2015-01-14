package com.szcmcc.movie.map;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import com.mapabc.mapapi.core.OverlayItem;
import com.mapabc.mapapi.map.ItemizedOverlay;
import com.mapabc.mapapi.map.MapView;
import com.mapabc.mapapi.map.Projection;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.MovieCinema;



public class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private List<OverlayItem> items = new ArrayList<OverlayItem>();
    private ArrayList currentOverlayData = new ArrayList();
    private Context _Context;
    private MapView _mapView;
    private boolean isShow=false;
	
	public MyItemizedOverlay(Drawable defaultMarker,Context context) {
		super(boundCenterBottom(defaultMarker));
//		super(defaultMarker);
		this._Context=context;
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// TODO Auto-generated method stub
		 populate();
		super.draw(canvas, mapView, false);
		/*if(shadow){
			drawInfo(mapView, canvas);
		}*/
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView) {
		
		return super.onTouchEvent(event, mapView);
	}
	
	public void drawInfo(MapView mapView,Canvas canvas){
		if(currentOverlayData!=null && currentOverlayData.size()>0){
			for(int i=0;i<currentOverlayData.size();i++){
				MapPoi userData=(MapPoi)currentOverlayData.get(i);
				Point point=null;
				Projection projection = mapView.getProjection();
				if(userData.getmGeoPoint()!=null){
					point=projection.toPixels(userData.getmGeoPoint(), null);
				}
				Bitmap bitmap=null;
				if(userData.getType()== 1){//��
					bitmap = BitmapFactory.decodeResource(mapView.getResources(), R.drawable.eat_icon);
				}else if(userData.getType()== 2 ||userData.getType()== 5 
						|| userData.getType()== 6){//��
					bitmap = BitmapFactory.decodeResource(mapView.getResources(), R.drawable.play_icon);
				}else if(userData.getType()== 3){//��
					bitmap = BitmapFactory.decodeResource(mapView.getResources(), R.drawable.beauty_icon);
				}else if(userData.getType()== -1){
					bitmap = BitmapFactory.decodeResource(mapView.getResources(), R.drawable.my_location);
				}
				if(bitmap!=null){
					canvas.drawBitmap(bitmap, point.x-20, point.y-20, null);
				}
			}
		}
	}
	
//	public void addCurrentOverlayData(MapPoi userData){
//		currentOverlayData.add(userData);
//	}
	public void addCurrentOverlayData(MovieCinema userData){
		currentOverlayData.add(userData);
	}
    public void addOverlay(OverlayItem overlay) {
        items.add(overlay);
        populate();
    }
	
	@Override
	protected OverlayItem createItem(int i) {
		return items.get(i);
	}

	@Override
	public int size() {
		return items.size();
	}
	
	protected boolean isRouteDisplayed() {
        return false;
    }

}
