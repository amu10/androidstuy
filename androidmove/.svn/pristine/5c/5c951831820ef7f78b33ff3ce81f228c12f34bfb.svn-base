package com.szcmcc.movie.network;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.Result;
import com.szcmcc.movie.bean.UpDateBean;

public class UpDateParse {
	private Context mContext;

	public UpDateParse(Context context) {
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

	public UpDateBean update(String json) throws JSONException {
		UpDateBean upDateBean = new UpDateBean();
		JSONObject object = new JSONObject(json);

		if (object.has("Return")) {

			upDateBean.updateReturn = object.getString("Return");
		}
		if (object.has("Description")) {

			upDateBean.Description = object.getString("Description");
		}
		if (object.has("DownLoad")) {

			upDateBean.DownLoad = object.getString("DownLoad");
		}
		if(object.has("AppVersion")){
			upDateBean.AppVersion = object.getString("AppVersion");
		}

		return upDateBean;
	}
}
