package com.szcmcc.movie.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.SeatOrder;
import com.szcmcc.movie.bean.SeatOrderList;

public class OrdersParse {
	private Context mContext;

	public OrdersParse(Context context) {
		mContext = context;
	}

	public SeatOrderList parseSeatOrderList(String json) throws JSONException {
		SeatOrderList seatOrderList = new SeatOrderList();
		JSONObject object = new JSONObject(json);
		if (object.has("orders")) {
			JSONArray jsonArray = object.getJSONArray("orders");
			seatOrderList.orders = parseSeatOrderInner(jsonArray);
		}
		if (object.has("code")) {
			seatOrderList.code = object.getString("code");
		}
		if (object.has("message")) {
			seatOrderList.message = object.getString("message");
		}
		return seatOrderList;
	}

	public ArrayList<SeatOrder> parseSeatOrderInner(JSONArray jsonArray) throws JSONException {
		if (jsonArray == null || jsonArray.length() == 0) {
			return null;
		}
		ArrayList<SeatOrder> seatOrderList = new ArrayList<SeatOrder>();
		int length = jsonArray.length();
		for (int i = 0; i < length; i++) {
			SeatOrder seatOrder = parseSeatOrder(jsonArray.getJSONObject(i));

			if (seatOrder != null) {
				seatOrderList.add(seatOrder);
			}
		}
		return seatOrderList;
	}

	public SeatOrder parseSeatOrder(JSONObject jsonObject) throws JSONException {
		if (jsonObject == null || jsonObject.length() == 0) {
			return null;
		}
		SeatOrder seatOrder = new SeatOrder();
		if (jsonObject.has("validityDate")) {
			seatOrder.validityDate = jsonObject.getString("validityDate");
		}
		if (jsonObject.has("amount")) {
			seatOrder.amount = jsonObject.getString("amount");
		}
		if (jsonObject.has("price")) {
			seatOrder.price = jsonObject.getString("price");
		}
		if (jsonObject.has("ticketType")) {
			seatOrder.ticketType = jsonObject.getString("ticketType");
		}
		if (jsonObject.has("orderId")) {
			seatOrder.orderId = jsonObject.getString("orderId");
		}
		if (jsonObject.has("cinemaName")) {
			seatOrder.cinemaName = jsonObject.getString("cinemaName");
		}
		if (jsonObject.has("status")) {
			seatOrder.status = jsonObject.getString("status");
		}
		if (jsonObject.has("date")) {
			seatOrder.date = jsonObject.getString("date");
		}
		if (jsonObject.has("payType")) {
			seatOrder.payType = jsonObject.getString("payType");
		}
		if (jsonObject.has("count")) {
			seatOrder.count = jsonObject.getString("count");
		}
		if(jsonObject.has("name")){
			seatOrder.name = jsonObject.getString("name");
		}
		if(jsonObject.has("showdate")){
			seatOrder.showdate = jsonObject.getString("showdate");
		}

		return seatOrder;

	}

}
