package com.szcmcc.movie.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.LockOrDebLockMovieSeatInfo;
import com.szcmcc.movie.bean.OrderQueryBySeat;
import com.szcmcc.movie.bean.Result;

public class OrderQueryBySeatParse {

	private Context mContext;

	public OrderQueryBySeatParse(Context context) {
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
	
	public OrderQueryBySeat parseOrderQueryBySeat(String json) throws JSONException{
		OrderQueryBySeat orderQueryBySeat = new OrderQueryBySeat();
		JSONObject object = new JSONObject(json);
		if(object.has("orderid")){
			orderQueryBySeat.orderid =  object.getString("orderid");
		}
		if(object.has("orderType")){
			orderQueryBySeat.orderType =  object.getString("orderType");
		}
		if(object.has("count")){
			orderQueryBySeat.count =  object.getString("count");
		}
		if(object.has("status")){
			orderQueryBySeat.status =  object.getString("status");
		}
		if(object.has("payType")){
			orderQueryBySeat.payType =  object.getString("payType");
		}
		if(object.has("showCode")){
			orderQueryBySeat.showCode =  object.getString("showCode");
		}
		if(object.has("lockSerialNo")){
			orderQueryBySeat.lockSerialNo =  object.getString("lockSerialNo");
		}
		
		if(object.has("seats")){
			JSONArray jsonArray = object.getJSONArray("seats");
			orderQueryBySeat.orderQueryBySeatList = parseOrderQueryBySeatListArray(jsonArray);
		}
		if(object.has("result")){
			JSONObject jsonObject = object.getJSONObject("result");
			orderQueryBySeat.result = parseResultObject(jsonObject);
		}
		return orderQueryBySeat;
	}
	
	public ArrayList<LockOrDebLockMovieSeatInfo> parseOrderQueryBySeatListArray(JSONArray jsonArray) throws JSONException{
		if(jsonArray == null){
			return null;
		}
		 ArrayList<LockOrDebLockMovieSeatInfo> list = new  ArrayList<LockOrDebLockMovieSeatInfo>();
			int length = jsonArray.length();
			for(int i=0;i<length;i++){
				LockOrDebLockMovieSeatInfo movieCinema = parseOrderQueryBySeatListObject(jsonArray.getJSONObject(i));
				if(movieCinema != null){
					list.add(movieCinema);
				}
			}
		 
		return list;
	}
	
	public LockOrDebLockMovieSeatInfo parseOrderQueryBySeatListObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		LockOrDebLockMovieSeatInfo orderQueryBySeatList = new LockOrDebLockMovieSeatInfo();
		
		if(jsonObject.has("seatCode")){
			orderQueryBySeatList.seatCode = jsonObject.getString("seatCode");
		}
		if(jsonObject.has("seatRow")){
			orderQueryBySeatList.seatRow = jsonObject.getString("seatRow");
		}
		if(jsonObject.has("seatCol")){
			orderQueryBySeatList.seatCol = jsonObject.getString("seatCol");
		}
		if(jsonObject.has("seatAreaCode")){
			orderQueryBySeatList.seatAreaCode = jsonObject.getString("seatAreaCode");
		}
		if(jsonObject.has("price")){
			orderQueryBySeatList.price = jsonObject.getString("price");
		}
		
		return orderQueryBySeatList;
		
	}
	}