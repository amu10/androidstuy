package com.szcmcc.movie.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.ZengquanQueryBean;
import com.szcmcc.movie.bean.ZengquanQueryBeanInfo;

public class MovieZengquanParse {
	private Context mContext;

	public MovieZengquanParse(Context context) {
		mContext = context;
	}
	

	
	public ZengquanQueryBeanInfo parseMovieInfo(String json) throws JSONException{
		ZengquanQueryBeanInfo zengquanQueryBeanInfo = new ZengquanQueryBeanInfo();
		JSONObject object = new JSONObject(json);
		if(object.has("tickets")){
			JSONArray jsonArray = object.getJSONArray("tickets");
			zengquanQueryBeanInfo.tickets = parseMovieArray(jsonArray);
		}
		if(object.has("code")){
			zengquanQueryBeanInfo.code = object.getString("code");
		}
		if(object.has("message")){
			zengquanQueryBeanInfo.message = object.getString("message");
		}
		if(object.has("curPage")){
			zengquanQueryBeanInfo.curPage = object.getString("curPage");
		}
		if(object.has("pageSize")){
			zengquanQueryBeanInfo.pageSize = object.getString("pageSize");
		}
		if(object.has("pageCount")){
			zengquanQueryBeanInfo.pageCount = object.getString("pageCount");
		}
		return zengquanQueryBeanInfo;
	}
	
	public ArrayList<ZengquanQueryBean> parseMovieArray(JSONArray jsonArray) throws JSONException{
		if(jsonArray==null||jsonArray.length()==0){
			return null;
		}
		ArrayList<ZengquanQueryBean> tickets = new ArrayList<ZengquanQueryBean>();
		int length = jsonArray.length();
		for(int i=0;i<length;i++){
			ZengquanQueryBean zengquanQueryBean = parseMovieObject(jsonArray.getJSONObject(i));
			if(zengquanQueryBean!=null){
				tickets.add(zengquanQueryBean);
			}
		}
		return tickets;
 	}
	
	public ZengquanQueryBean parseMovieObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		ZengquanQueryBean zengquanQueryBean = new ZengquanQueryBean();
		
		if(jsonObject.has("verifyCode")){
			zengquanQueryBean.verifyCode = jsonObject.getString("verifyCode");
		}
		if(jsonObject.has("allowCinema")){
			zengquanQueryBean.allowCinema = jsonObject.getString("allowCinema");
		}
		if(jsonObject.has("filmType")){
			zengquanQueryBean.filmType = jsonObject.getString("filmType");
		}
		if(jsonObject.has("validDate")){
			zengquanQueryBean.validDate = jsonObject.getString("validDate");
		}
		if(jsonObject.has("status")){
			zengquanQueryBean.status = jsonObject.getString("status");
		}
		if(jsonObject.has("presentTime")){
			zengquanQueryBean.presentTime = jsonObject.getString("presentTime");
		}
		return zengquanQueryBean;
	}
}
