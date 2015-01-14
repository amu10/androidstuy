package com.szcmcc.movie.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.YouhuiDetailBean;
import com.szcmcc.movie.bean.YouhuiDetailInfoBean;

public class MovieYouhuiDetailParse {
	private Context mContext;

	public MovieYouhuiDetailParse(Context context) {
		mContext = context;
	}
	
	
	public YouhuiDetailInfoBean parseMovieInfo(String json) throws JSONException{
		YouhuiDetailInfoBean youhuiDetailInfoBean = new YouhuiDetailInfoBean();
		JSONObject object = new JSONObject(json);
		if(object.has("data")){
			JSONArray jsonArray = object.getJSONArray("data");
			youhuiDetailInfoBean.data = parseMovieArray(jsonArray);
		}
		if(object.has("result_code")){
			youhuiDetailInfoBean.result_code = object.getString("result_code");
		}
		if(object.has("desc")){
			youhuiDetailInfoBean.desc = object.getString("desc");
		}
		return youhuiDetailInfoBean;
	}
	
	public ArrayList<YouhuiDetailBean> parseMovieArray(JSONArray jsonArray) throws JSONException{
		if(jsonArray==null||jsonArray.length()==0){
			return null;
		}
		ArrayList<YouhuiDetailBean> activitylist = new ArrayList<YouhuiDetailBean>();
		int length = jsonArray.length();
		for(int i=0;i<length;i++){
			YouhuiDetailBean youhuiDetailBean = parseMovieObject(jsonArray.getJSONObject(i));
			if(youhuiDetailBean!=null){
				activitylist.add(youhuiDetailBean);
			}
		}
		return activitylist;
 	}
	
	public YouhuiDetailBean parseMovieObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		YouhuiDetailBean youhuiDetailBean = new YouhuiDetailBean();
		
		if(jsonObject.has("big_img")){
			youhuiDetailBean.big_img = jsonObject.getString("big_img");
		}
		if(jsonObject.has("activity_name")){
			youhuiDetailBean.activity_name = jsonObject.getString("activity_name");
		}
		if(jsonObject.has("activity_details")){
			youhuiDetailBean.activity_details = jsonObject.getString("activity_details");
		}
		return youhuiDetailBean;
	}
}
