package com.szcmcc.movie.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.YouhuiBean;
import com.szcmcc.movie.bean.YouhuiInfoBean;

public class MovieYouhuiParse {
	private Context mContext;

	public MovieYouhuiParse(Context context) {
		mContext = context;
	}
	
	
	public YouhuiInfoBean parseMovieInfo(String json) throws JSONException{
		YouhuiInfoBean youhuiInfoBean = new YouhuiInfoBean();
		JSONObject object = new JSONObject(json);
		if(object.has("data")){
			JSONArray jsonArray = object.getJSONArray("data");
			youhuiInfoBean.data = parseMovieArray(jsonArray);
		}
		if(object.has("result_code")){
			youhuiInfoBean.result_code = object.getString("result_code");
		}
		if(object.has("desc")){
			youhuiInfoBean.desc = object.getString("desc");
		}
		return youhuiInfoBean;
	}
	
	public ArrayList<YouhuiBean> parseMovieArray(JSONArray jsonArray) throws JSONException{
		if(jsonArray==null||jsonArray.length()==0){
			return null;
		}
		ArrayList<YouhuiBean> activitylist = new ArrayList<YouhuiBean>();
		int length = jsonArray.length();
		for(int i=0;i<length;i++){
			YouhuiBean youhuiBean = parseMovieObject(jsonArray.getJSONObject(i));
			if(youhuiBean!=null){
				activitylist.add(youhuiBean);
			}
		}
		return activitylist;
 	}
	
	public YouhuiBean parseMovieObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		YouhuiBean youhuiBean = new YouhuiBean();
		
		if(jsonObject.has("result_code")){
			youhuiBean.result_code = jsonObject.getString("result_code");
		}
		if(jsonObject.has("id")){
			youhuiBean.id = jsonObject.getString("id");
		}
		if(jsonObject.has("big_img")){
			youhuiBean.big_img = jsonObject.getString("big_img");
		}
		if(jsonObject.has("activity_name")){
			youhuiBean.activity_name = jsonObject.getString("activity_name");
		}
		if(jsonObject.has("start_date")){
			youhuiBean.start_date = jsonObject.getString("start_date");
		}
		if(jsonObject.has("end_date")){
			youhuiBean.end_date = jsonObject.getString("end_date");
		}
		return youhuiBean;
	}
}
