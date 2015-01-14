package com.szcmcc.movie.network;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.Order;
import com.szcmcc.movie.bean.Result;

public class MovieOrderParse {
	private Context mContext;

	public MovieOrderParse(Context context) {
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
	
	public Order.OrderInner parseMovieOrderObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		Order.OrderInner orderInner = new Order.OrderInner();
		if(jsonObject.has("orderid")){
			orderInner.orderid = jsonObject.getString("orderid");
		}
		if(jsonObject.has("expired_time")){
			orderInner.expired_time = jsonObject.getString("expired_time");
		}
		if(jsonObject.has("price")){
			orderInner.price = jsonObject.getString("price");
		}
		if(jsonObject.has("payPhone")){
			orderInner.payPhone = jsonObject.getString("payPhone");
		}
		
		if(jsonObject.has("recvPhone")){
			orderInner.recvPhone = jsonObject.getString("recvPhone");
		}
		if(jsonObject.has("count")){
			orderInner.count = jsonObject.getString("count");
		}
		return orderInner;
	}
	
	public Order parseMovieOrder(String json) throws JSONException{
		
		Order order = new Order();
		JSONObject object = new JSONObject(json);
		if(object.has("order")){
			JSONObject jsonObject = object.getJSONObject("order");
			order.orderInner = parseMovieOrderObject(jsonObject);
		}
		if(object.has("result")){
			JSONObject jsonObject = object.getJSONObject("result");
			order.result = parseResultObject(jsonObject);
		}
		return order;
	}
}
