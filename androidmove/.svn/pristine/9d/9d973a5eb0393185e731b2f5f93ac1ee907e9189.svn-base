package com.szcmcc.movie.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.ValidateCodeBeanInfo;
import com.szcmcc.movie.bean.ValidateCodeBeanInfo.ValidateCodeBean;

public class ValidateCodeBeanInfoParse {
	private Context mContext;

	public ValidateCodeBeanInfoParse(Context context) {
		mContext = context;
	}
	
	
	
	public ArrayList<ValidateCodeBean> parseValidateCodeBeanArray(JSONArray jsonArray) throws JSONException{
		if(jsonArray == null){
			return null;
		}
		 ArrayList<ValidateCodeBean> list = new  ArrayList<ValidateCodeBean>();
			int length = jsonArray.length();
			for(int i=0;i<length;i++){
				ValidateCodeBean bean = parseValidateCodeBeanObject(jsonArray.getJSONObject(i));
				if(bean != null){
					list.add(bean);
				}
			}
		 
		return list;
	}
	

	
	
	
	public ValidateCodeBean parseValidateCodeBeanObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		ValidateCodeBean bean = new ValidateCodeBean();
		
		if(jsonObject.has("code")){
			bean.code = jsonObject.getString("code");
		}
		if(jsonObject.has("status")){
			bean.status = jsonObject.getString("status");
		}
		if(jsonObject.has("message")){
			bean.message = jsonObject.getString("message");
		}
		return bean;
		
	}
	public ValidateCodeBeanInfo parseValidateCodeBeanInfo(String json) throws JSONException{
		
		ValidateCodeBeanInfo validateCodeBeanInfo = new ValidateCodeBeanInfo();
		JSONObject object = new JSONObject(json);
		
		if(object.has("code")){
			validateCodeBeanInfo.code = object.getString("code");
		}
		if(object.has("message")){
			validateCodeBeanInfo.message = object.getString("message");
		}
		if(object.has("lockNum")){
			validateCodeBeanInfo.lockNum = object.getString("lockNum");
		}
		if(object.has("coupon")){
			JSONArray jsonArray = object.getJSONArray("coupon");
			validateCodeBeanInfo.couponList = parseValidateCodeBeanArray(jsonArray);
		}
		return validateCodeBeanInfo;
	}
}
