package com.szcmcc.movie.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.MovieCinema;
import com.szcmcc.movie.bean.OrderQuery;
import com.szcmcc.movie.bean.Result;
import com.szcmcc.movie.bean.OrderQuery.OrderQueryInner;

public class MovieOrderQueryParse {

	private Context mContext;

	public MovieOrderQueryParse(Context context) {
		mContext = context;
	}

	public Result parseResultObject(JSONObject jsonObject) throws JSONException {
		if (jsonObject == null) {
			return null;
		}
		Result result = new Result();
		if (jsonObject.has("code")) {
			result.code = jsonObject.getString("code");
		}
		if (jsonObject.has("message")) {
			result.message = jsonObject.getString("message");
		}
		return result;

	}

	public OrderQuery parseMovieOrderQuery(String json) throws JSONException {

		OrderQuery qrderQuery = new OrderQuery();
		JSONObject object = new JSONObject(json);
		if (object.has("orderid")) {
			qrderQuery.orderid = object.getString("orderid");
		}
		if (object.has("orderType")) {
			qrderQuery.orderTpye = object.getString("orderType");
		}
		if (object.has("count")) {
			qrderQuery.count = object.getString("count");
		}
		if (object.has("childorder")) {
			JSONArray jsonArray = object.getJSONArray("childorder");
			qrderQuery.list = parseOrderQueryArray(jsonArray);
		}
		if (object.has("payType")) {
			qrderQuery.payType = object.getString("payType");
		}
		if (object.has("result")) {
			JSONObject jsonObject = object.getJSONObject("result");
			qrderQuery.result = parseResultObject(jsonObject);
		}
		return qrderQuery;
	}

	public ArrayList<OrderQueryInner> parseOrderQueryArray(JSONArray jsonArray) throws JSONException{
		if(jsonArray == null){
			return null;
		}
		 ArrayList<OrderQueryInner> list = new  ArrayList<OrderQueryInner>();
			int length = jsonArray.length();
			for(int i=0;i<length;i++){
				OrderQueryInner orderQueryInner = parseOrderQueryInnerObject(jsonArray.getJSONObject(i));
				if(orderQueryInner != null){
					list.add(orderQueryInner);
				}
			}
		 
		return list;
	}
	
	public OrderQueryInner parseOrderQueryInnerObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		OrderQueryInner orderQueryInner = new OrderQueryInner();
		if(jsonObject.has("childorderid")){
			orderQueryInner.childorderid = jsonObject.getString("childorderid");
		}
		if(jsonObject.has("orderstatu")){
			orderQueryInner.orderstatu = jsonObject.getString("orderstatu");
		}
		return orderQueryInner;
	}
}
