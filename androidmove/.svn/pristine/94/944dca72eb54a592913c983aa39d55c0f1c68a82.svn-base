package com.szcmcc.movie.network;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.Result;
import com.szcmcc.movie.bean.SendShort;

public class MovieSendShordParse {

	private Context mContext;

	public MovieSendShordParse(Context context) {
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

	public SendShort parseMovieSendShort(String json) throws JSONException {

		SendShort sendShort = new SendShort();
		JSONObject object = new JSONObject(json);
		if (object.has("orderid")) {
			sendShort.orderid = object.getString("orderid");
		}
		if (object.has("orderType")) {
			sendShort.orderType = object.getString("orderType");
		}
		if (object.has("recvPhone")) {
			sendShort.recvPhone = object.getString("recvPhone");
		}
		if (object.has("expired_time")) {
			sendShort.expired_time = object.getString("expired_time");
		}
		if (object.has("result")) {
			JSONObject jsonObject = object.getJSONObject("result");
			sendShort.result = parseResultObject(jsonObject);
		}
		return sendShort;
	}
}
