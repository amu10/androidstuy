package com.szcmcc.movie.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.MovieCinema;
import com.szcmcc.movie.bean.OrderBySeat;
import com.szcmcc.movie.bean.Result;
import com.szcmcc.movie.bean.OrderBySeat.OrderBySeatInner.OrderBySeatInnerList;

public class OrderSeatParse {
	private Context mContext;

	public OrderSeatParse(Context context) {
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
	
	public OrderBySeat parseOrderBySeat(String json) throws JSONException{
		OrderBySeat orderBySeat = new OrderBySeat();
		JSONObject object = new JSONObject(json);
		if(object.has("order")){
			JSONObject jsonObject = object.getJSONObject("order");
			orderBySeat.orderBySeatInner = parseMovieOrderObject(jsonObject);
		}
		if(object.has("result")){
			JSONObject jsonObject = object.getJSONObject("result");
			orderBySeat.result = parseResultObject(jsonObject);
		}
		return orderBySeat;
	}
	
	public OrderBySeat.OrderBySeatInner parseMovieOrderObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		OrderBySeat.OrderBySeatInner orderInner = new OrderBySeat.OrderBySeatInner();
		if(jsonObject.has("orderid")){
			orderInner.orderid = jsonObject.getString("orderid");
		}
		if(jsonObject.has("lockSerialNo")){
			orderInner.lockSerialNo = jsonObject.getString("lockSerialNo");
		}
		if(jsonObject.has("showCode")){
			orderInner.showCode = jsonObject.getString("showCode");
		}
		if(jsonObject.has("payPhone")){
			orderInner.payPhone = jsonObject.getString("payPhone");
		}
		if(jsonObject.has("seats")){
			JSONArray jsonArray = jsonObject.getJSONArray("seats");
			orderInner.orderBySeatInnerList = parseOrderBySeatInnerListArray(jsonArray);
		}
		
		return orderInner;
	}	
	public ArrayList<OrderBySeatInnerList> parseOrderBySeatInnerListArray(JSONArray jsonArray) throws JSONException{
		if(jsonArray == null){
			return null;
		}
		 ArrayList<OrderBySeatInnerList> list = new  ArrayList<OrderBySeatInnerList>();
			int length = jsonArray.length();
			for(int i=0;i<length;i++){
				OrderBySeatInnerList movieCinema = parseOrderBySeatInnerListObject(jsonArray.getJSONObject(i));
				if(movieCinema != null){
					list.add(movieCinema);
				}
			}
		 
		return list;
	}
	
	public OrderBySeatInnerList parseOrderBySeatInnerListObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		OrderBySeatInnerList orderBySeatInnerList = new OrderBySeatInnerList();
		
		if(jsonObject.has("seatCode")){
			orderBySeatInnerList.seatCode = jsonObject.getString("seatCode");
		}
		if(jsonObject.has("seatRow")){
			orderBySeatInnerList.seatRow = jsonObject.getString("seatRow");
		}
		if(jsonObject.has("seatCol")){
			orderBySeatInnerList.seatCol = jsonObject.getString("seatCol");
		}
		if(jsonObject.has("seatAreaCode")){
			orderBySeatInnerList.seatAreaCode = jsonObject.getString("seatAreaCode");
		}
		if(jsonObject.has("price")){
			orderBySeatInnerList.price = jsonObject.getString("price");
		}
		
		return orderBySeatInnerList;
		
	}
	}