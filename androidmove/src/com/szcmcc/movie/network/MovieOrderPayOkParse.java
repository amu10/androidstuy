package com.szcmcc.movie.network;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.OrderPayOk;
import com.szcmcc.movie.bean.Result;

public class MovieOrderPayOkParse {
	private Context mContext;

	public MovieOrderPayOkParse(Context context) {
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
	
	public OrderPayOk.OrderPayOkInner parseMovieOrderObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		OrderPayOk.OrderPayOkInner orderPayOkInner = new OrderPayOk.OrderPayOkInner();
		if(jsonObject.has("orderid")){
			orderPayOkInner.orderid = jsonObject.getString("orderid");
		}
		if(jsonObject.has("orderType")){
			orderPayOkInner.orderType = jsonObject.getString("orderType");
		}
		if(jsonObject.has("c_id")){
			orderPayOkInner.c_id = jsonObject.getString("c_id");
		}
		if(jsonObject.has("count")){
			orderPayOkInner.count = jsonObject.getString("count");
		}
		if(jsonObject.has("payMoney")){
			orderPayOkInner.payMoney = jsonObject.getString("payMoney");
		}
		if(jsonObject.has("expired_time")){
			orderPayOkInner.expired_time = jsonObject.getString("expired_time");
		}
		return orderPayOkInner;
	}
	
	public OrderPayOk parseMovieOrderPayOk(String json) throws JSONException{
		
		OrderPayOk orderPayOk = new OrderPayOk();
		JSONObject object = new JSONObject(json);
		if(object.has("order")){
			JSONObject jsonObject = object.getJSONObject("order");
			orderPayOk.orderPayOkInner = parseMovieOrderObject(jsonObject);
		}
		if(object.has("result")){
			JSONObject jsonObject = object.getJSONObject("result");
			orderPayOk.result = parseResultObject(jsonObject);
		}
		return orderPayOk;
	}
}
