package com.szcmcc.movie.network;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.OrderPay;
import com.szcmcc.movie.bean.Result;

public class MovieOrderPayParse {

	private Context mContext;

	public MovieOrderPayParse(Context context) {
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
	
	public OrderPay.OrderPayInner parseMovieOrderObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		OrderPay.OrderPayInner orderInner = new OrderPay.OrderPayInner();
		if(jsonObject.has("orderid")){
			orderInner.orderid = jsonObject.getString("orderid");
		}
		if(jsonObject.has("payPhone")){
			orderInner.payPhone = jsonObject.getString("payPhone");
		}
		
		if(jsonObject.has("orderType")){
			orderInner.orderType = jsonObject.getString("orderType");
		}
		if(jsonObject.has("payMoney")){
			orderInner.payMoney = jsonObject.getString("payMoney");
		}
		return orderInner;
	}
	
	public OrderPay parseMovieOrderPay(String json) throws JSONException{
		
		OrderPay orderPay = new OrderPay();
		JSONObject object = new JSONObject(json);
		if(object.has("order")){
			JSONObject jsonObject = object.getJSONObject("order");
			orderPay.orderPayInner = parseMovieOrderObject(jsonObject);
		}
		if(object.has("result")){
			JSONObject jsonObject = object.getJSONObject("result");
			orderPay.result = parseResultObject(jsonObject);
		}
		return orderPay;
	}
}
