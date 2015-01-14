package com.szcmcc.movie.network;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.MoviePinLunList;
import com.szcmcc.movie.bean.Result;

public class MoviePinLunParse {
	private Context mContext;

	public MoviePinLunParse(Context context) {
		this.mContext = context;
	}
	
	public Result parseResultObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		Result result = new Result();
		if(jsonObject.has("code")){
			result.code = jsonObject.getString("code");
		}
		if(jsonObject.has("message")){
			result.message = jsonObject.getString("message");
		}
		return result;
		
	}
	
	public MoviePinLunList parseMoviePinLunList(String json) throws JSONException{
		MoviePinLunList moviePinLunList = new MoviePinLunList();
		JSONObject object = new JSONObject(json);
		
		if(object.has("result")){
			JSONObject jsonObject = object.getJSONObject("result");
			moviePinLunList.result = parseResultObject(jsonObject);
		}
		return moviePinLunList;
	}
	
	
	
	
}
