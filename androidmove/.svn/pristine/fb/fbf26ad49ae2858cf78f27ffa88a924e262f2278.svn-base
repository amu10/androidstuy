package com.mapabc.cn.apis.location;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

public class LocationByCellId {

    
    private static final String TAG = "LocationByCellId";
//    private static LocationCell LOCATION;
    private static Location LOCATION;
    private static LocationByCellId singleton = new LocationByCellId();
    Handler handler;
    private LocationByCellId(){
        handler = new Handler();
    }
    public static LocationByCellId getInstance(){
        
        return singleton;
    }
    
    public boolean reqLocThread(final Context mContext,ILocationByCellId listener){
        iLocationByCellId = listener;
        if(LOCATION == null){
        new Thread(new Runnable(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                LOCATION =  requestLocation(mContext);
                if(iLocationByCellId != null){
                   
                    handler.post(new Runnable(){
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            iLocationByCellId.backLocation(LOCATION);   
                        }
                    });
                }
            }
             
         }).start();
        }else{
            iLocationByCellId.backLocation(LOCATION); 
            return true;
        }
        return false;
    }
    
//    public LocationCell requestLocation(Context mContext)
    private Location requestLocation(Context mContext)
    {
         String streetNum = "";
//         LocationCell location = null ;
         Location location = null ;
            try
            {
                TelephonyManager telephonyManager =(TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);     
                GsmCellLocation gcl = (GsmCellLocation)telephonyManager.getCellLocation();
                if(gcl==null)
                {//手机没有sim卡
                    Log.e(TAG,"请求cell id 没有获得gcl,没有手机sim卡");
                return  null;
                }
                int cellId = gcl.getCid();
                int lac = gcl.getLac();
                int mcc = 0;
                int mnc = 0;
                String networkOperator = telephonyManager.getNetworkOperator();
                if (networkOperator != null && !networkOperator.equals(""))
                {
                mcc = Integer.valueOf(networkOperator.substring(0, 3));
                mnc = Integer.valueOf(networkOperator.substring(3, 5));
                }
                
                HttpClient httpclient = new DefaultHttpClient();
                HttpParams httpParams = httpclient.getParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
                HttpConnectionParams.setSoTimeout(httpParams, 10000);
                /*
                ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                if(manager!=null)
                {
                NetworkInfo networkinfo = manager.getActiveNetworkInfo();
                String stringExtraInfo =  networkinfo.getExtraInfo();
                if(stringExtraInfo!=null&& ("cmwap".equals(stringExtraInfo) || "uniwap".equals(stringExtraInfo) ))
                {
                    HttpHost proxy = new HttpHost("10.0.0.172", 80, "http");
                        httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
                }
                else if(stringExtraInfo!=null && "ctwap".equals(stringExtraInfo))
                {
                    HttpHost proxy = new HttpHost("10.0.0.200", 80, "http");
                        httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
                }
                }
                */
                
                HttpPost post = new HttpPost("http://www.google.com/loc/json");
                StringBuffer postData = new StringBuffer();
                postData.append("{\n").append("   \"version\":\"1.1.0\",\n")
                        .append("   \"host\":\"maps.google.com\",\n")
                        .append("   \"request_address\":true,\n")
                        .append("   \"address_language\":\"zh_cn\",\n")
                        .append("   \"cell_towers\":[\n")
                        .append("   {\n")
                        .append("      \"cell_id\":").append(cellId).append(",\n")
                        .append("       \"location_area_code\":").append(lac).append(",\n")
                        .append("       \"mobile_country_code\":").append(mcc).append(",\n") // 移动国家号码
                        .append("       \"mobile_network_code\":").append(mnc).append(",\n") // 移动网号// 01是联通// ;中国联通CDMA系统是03// ,移动是00
                        .append("      \"age\":0\n").append("   }\n").append("   ]\n").append("}");
        
                     ByteArrayEntity entity = new ByteArrayEntity(postData.toString().getBytes());
                     post.setEntity(entity);
                     post.setHeader("content-type", "application/binary");
                     HttpResponse response;
        
                     response = httpclient.execute(post);
                     HttpEntity en = response.getEntity();
        
                     String responseString = EntityUtils.toString(en);
                     Log.i(TAG,"cell id location response info : "+responseString);
                    
                     
                     JSONObject jsonObject = new JSONObject(responseString);
                     if (jsonObject.has("location"))
                     {
                         location = new LocationCell("provider");
                         JSONObject locationJSONObject = jsonObject.getJSONObject("location");
                         if(locationJSONObject.has("latitude"))
                         {
                         double latitude = locationJSONObject.getDouble("latitude");
                         location.setLatitude(latitude);
                         }
                         if(locationJSONObject.has("longitude"))
                         {
                         double longitude = locationJSONObject.getDouble("longitude");
                         location.setLongitude(longitude);
                         }
                         /*
                         if (locationJSONObject.has("address"))
                         {
                         JSONObject addressJSONObject = locationJSONObject.getJSONObject("address");
                         if (addressJSONObject.has("city"))
                         {
                             String cityName = addressJSONObject.getString("city");
                             if (cityName.contains("市"))
                             {
                             cityName = cityName.substring(0,cityName.indexOf("市"));
                             }
                             if (cityName.contains("省"))
                             {
                             cityName = cityName.substring(cityName.indexOf("省") + 1);
                             }
                             location.setCityName(cityName);
                         }
                         if (addressJSONObject.has("country"))
                         {
                             String countryName = addressJSONObject.getString("country");// 国家
                             location.setCountryName(countryName);
                         }
                         if (addressJSONObject.has("country_code"))
                         {
                             String countryCode = addressJSONObject.getString("country_code");// 国家编码
                             location.setCountryCode(countryCode);
                         }
                         if(addressJSONObject.has("street"))
                         {
                             String street = addressJSONObject.getString("street");// 街道
                             location.setStreet(street);
                         }
                         if(addressJSONObject.has("street_number"))
                         {
                              streetNum = addressJSONObject.getString("street_number");// 街道
                              location.setStreetNum(streetNum);
                         }       
                         } 
                         */
                     }else{
                         Log.i(TAG,"返回没有值cell id 定位 ");
                         return null;
                     }
            }
            catch (Exception e1)
            {
                Log.w("LocationManager", e1.toString());
            }
//            return Place.streetPosition+streetNum;
            return location;
    }
    ILocationByCellId iLocationByCellId;
    public void setILocationByCellId(ILocationByCellId iLocationByCellId){
        this.iLocationByCellId = iLocationByCellId;
    }
    public interface ILocationByCellId{
//        public boolean backLocation(LocationCell location);
        public boolean backLocation(Location location);
    }
//}
    /**
     * location 的子类
     */
   public class LocationCell extends Location{

        public LocationCell(Location l) {
            super(l);
            // TODO Auto-generated constructor stub
        }

        public void setStreetNum(String streetNum) {
            // TODO Auto-generated method stub
            
        }

        public void setStreet(String street) {
            // TODO Auto-generated method stub
            
        }

        public void setCountryCode(String countryCode) {
            // TODO Auto-generated method stub
            
        }

        public void setCountryName(String countryName) {
            // TODO Auto-generated method stub
            
        }

        public LocationCell(String provider) {
            super(provider);
            // TODO Auto-generated constructor stub
        }
        
        public void setCityName(String cityName) {
            // TODO Auto-generated method stub
            
        }
    }
   /**
    * 
    {"location":
    {"latitude":39.9596515,"longitude":116.3715753,
    "address":
    {
    "country":"中国","country_code":"CN","region":"北京市",
    "city":"北京市","street":"新街口外大街","street_number":"12号"
    },
    "accuracy":816.0
    },
    "access_token":"2:QdVRKVVJOuBxtZR_:NVzQ2yw9cM6h-CzT"
    }
    */
}
