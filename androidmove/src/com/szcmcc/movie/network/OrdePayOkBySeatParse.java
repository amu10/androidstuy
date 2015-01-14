package com.szcmcc.movie.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.LockOrDebLockMovieSeatInfo;
import com.szcmcc.movie.bean.OrderPayOkBySeat;
import com.szcmcc.movie.bean.Result;

public class OrdePayOkBySeatParse {

	private Context mContext;

	public OrdePayOkBySeatParse(Context context) {
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
	
	public OrderPayOkBySeat parseOrderPayOkBySeat(String json) throws JSONException{
		OrderPayOkBySeat orderPayOkBySeat = new OrderPayOkBySeat();
		JSONObject object = new JSONObject(json);
		if(object.has("order")){
			JSONObject jsonObject = object.getJSONObject("order");
			orderPayOkBySeat.orderPayOkBySeatInner = parseMovieOrderObject(jsonObject);
		}
		if(object.has("result")){
			JSONObject jsonObject = object.getJSONObject("result");
			orderPayOkBySeat.result = parseResultObject(jsonObject);
		}
		return orderPayOkBySeat;
	}
	
	public OrderPayOkBySeat.OrderPayOkBySeatInner parseMovieOrderObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		OrderPayOkBySeat.OrderPayOkBySeatInner orderInner = new OrderPayOkBySeat.OrderPayOkBySeatInner();
		if(jsonObject.has("orderid")){
			orderInner.orderid = jsonObject.getString("orderid");
		}
		if(jsonObject.has("lockSerialNo")){
			orderInner.lockSerialNo = jsonObject.getString("lockSerialNo");
		}
		if(jsonObject.has("showCode")){
			orderInner.showCode = jsonObject.getString("showCode");
		}
		if(jsonObject.has("orderType")){
			orderInner.orderType = jsonObject.getString("orderType");
		}
		if(jsonObject.has("count")){
			orderInner.count = jsonObject.getString("count");
		}
		if(jsonObject.has("payMoney")){
			orderInner.payMoney = jsonObject.getString("payMoney");
		}
		if(jsonObject.has("seats")){
			JSONArray jsonArray = jsonObject.getJSONArray("seats");
			orderInner.orderPayOkBySeatInnerList = parseOrderPayOkBySeatInnerListArray(jsonArray);
		}
		
		return orderInner;
	}	
	public ArrayList<LockOrDebLockMovieSeatInfo> parseOrderPayOkBySeatInnerListArray(JSONArray jsonArray) throws JSONException{
		if(jsonArray == null){
			return null;
		}
		 ArrayList<LockOrDebLockMovieSeatInfo> list = new  ArrayList<LockOrDebLockMovieSeatInfo>();
			int length = jsonArray.length();
			for(int i=0;i<length;i++){
				LockOrDebLockMovieSeatInfo movieCinema = parseOrderPayOkBySeatInnerListObject(jsonArray.getJSONObject(i));
				if(movieCinema != null){
					list.add(movieCinema);
				}
			}
		 
		return list;
	}
	
	public LockOrDebLockMovieSeatInfo parseOrderPayOkBySeatInnerListObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		LockOrDebLockMovieSeatInfo orderPayOkBySeatInnerList = new LockOrDebLockMovieSeatInfo();
		
		if(jsonObject.has("seatCode")){
			orderPayOkBySeatInnerList.seatCode = jsonObject.getString("seatCode");
		}
		if(jsonObject.has("seatRow")){
			orderPayOkBySeatInnerList.seatRow = jsonObject.getString("seatRow");
		}
		if(jsonObject.has("seatCol")){
			orderPayOkBySeatInnerList.seatCol = jsonObject.getString("seatCol");
		}
		if(jsonObject.has("seatAreaCode")){
			orderPayOkBySeatInnerList.seatAreaCode = jsonObject.getString("seatAreaCode");
		}
		if(jsonObject.has("price")){
			orderPayOkBySeatInnerList.price = jsonObject.getString("price");
		}
		
		return orderPayOkBySeatInnerList;
		
	}
	}