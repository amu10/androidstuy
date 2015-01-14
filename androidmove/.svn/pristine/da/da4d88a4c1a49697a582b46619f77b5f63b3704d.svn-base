package com.szcmcc.movie.network;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.ReSendOrderBean;
import com.szcmcc.movie.bean.Result;
import com.szcmcc.movie.bean.UpDateBean;

public class ReSendParse {
	private Context mContext;
	public ReSendParse(Context context) {
		mContext = context;
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
	
	public ReSendOrderBean reSendOrderParse(String json) throws JSONException{
		ReSendOrderBean reSendOrderBean = new ReSendOrderBean();
		JSONObject object = new JSONObject(json);
		
		if(object.has("result")){
			JSONObject jsonObject = object.getJSONObject("result");
			reSendOrderBean.result = parseResultObject(jsonObject);
		}
		
		return reSendOrderBean;
	}
}
