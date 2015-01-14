package com.mapabc.cn.apis.location;

import java.util.LinkedList;

import android.content.Context;
import android.location.Location;

import com.mapabc.mapapi.map.MapView;
import com.mapabc.mapapi.map.MyLocationOverlay;

public class MyLocationOverlayProxy extends MyLocationOverlay{

	 protected static final String TAG = "MyLocationOverlayProxy";
    private Location mLocation;
     private final LinkedList<Runnable> mRunOnFirstFix = new LinkedList<Runnable>();
	Context context;
	 public MyLocationOverlayProxy(Context arg0, MapView arg1) {
		super(arg0, arg1);
		context = arg0;
	 }
	
	@Override
	public boolean runOnFirstFix(final Runnable runnable) {
//		if (mLocation != null) {
//			new Thread(runnable).start();
//			return true;
//		} else {
//			mRunOnFirstFix.addLast(runnable);
//		    //请求cell id 定位了
//		    LocationByCellId.getInstance().reqLocThread(context,new LocationByCellId.ILocationByCellId(){
//                @Override
//                public boolean backLocation(Location location) {
//                    // TODO Auto-generated method stub
//                    if(location!= null){
//                    mLocation = location;
//                    Log.e(TAG,"back location : "+location.toString());
//                    }
//                    
//                    return false;
//                }
//		        
//		    });
//		    
//			
//			return false;
//		}
		if (mLocation != null) {
			new Thread(runnable).start();
			return true;
		} else {
			mRunOnFirstFix.addLast(runnable);
			return false;
		}
    }
	
	
	@Override
	public void onLocationChanged(Location location) {
        mLocation = location;
        for(final Runnable runnable : mRunOnFirstFix) {
			new Thread(runnable).start();
		}
		mRunOnFirstFix.clear();
		super.onLocationChanged(location);
	}

//    @Override
//    public GeoPoint getMyLocation() {
//        // TODO Auto-generated method stub
//        if(super.getMyLocation() == null){
//            if(mLocation != null){
//            //
//            int lat = (int)(mLocation.getLatitude()*1E6);
//            int lon = (int)(mLocation.getLongitude()*1E6);
////            	int lat = (int)(22.54*1e6);
////            	int lon = (int)(114.05*1e6);
//            GeoPoint geo = new GeoPoint(lat, lon);
//            Log.e(TAG,"return my location is cell id :"+geo.toString());
//            return geo;
//            }
//        }
//        return super.getMyLocation();
//    }
	
	
	

}
