package com.szcmcc.movie.bean;

import org.json.JSONObject;

import android.content.Context;

public class FlowStatisticsBean extends BaseBean<FlowStatisticsBean> {

	public String result;
	
	public String info;
	
	@Override
	public FlowStatisticsBean parseJSON(JSONObject inputJsonObj) {
		result = inputJsonObj.optString("result");
		info = inputJsonObj.optString("info");
		return this;
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}



}
