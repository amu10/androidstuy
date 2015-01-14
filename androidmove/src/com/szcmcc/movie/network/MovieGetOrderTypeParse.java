package com.szcmcc.movie.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.GetOrderTypeBeanInfo;
import com.szcmcc.movie.bean.GetOrderTypeBeanInfo.GetOrderTypeBean;
import com.szcmcc.movie.bean.GetOrderTypeBeanInfo.GetOrderTypeBean.CouponBean;
import com.szcmcc.movie.bean.GetOrderTypeBeanInfo.MerchantBean;

public class MovieGetOrderTypeParse {
	private Context mContext;

	public MovieGetOrderTypeParse(Context context) {
		mContext = context;
	}
	
	
	public MerchantBean parseMerchantBeanObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		MerchantBean merchantBean = new MerchantBean();
		if(jsonObject.has("spid")){
			merchantBean.spid = jsonObject.getString("spid");
		}
		if(jsonObject.has("sppwd")){
			merchantBean.sppwd = jsonObject.getString("sppwd");
		}
		if(jsonObject.has("spcode")){
			merchantBean.spcode = jsonObject.getString("spcode");
		}
		if(jsonObject.has("merchantCode")){
			merchantBean.merchantCode = jsonObject.getString("merchantCode");
		}
		if(jsonObject.has("callback")){
			merchantBean.callback = jsonObject.getString("callback");
		}
		return merchantBean;
	}
	
	public ArrayList<GetOrderTypeBean> parseGetOrderTypeBeanArray(JSONArray jsonArray) throws JSONException{
		if(jsonArray == null){
			return null;
		}
		 ArrayList<GetOrderTypeBean> list = new  ArrayList<GetOrderTypeBean>();
			int length = jsonArray.length();
			for(int i=0;i<length;i++){
				GetOrderTypeBean bean = parseGetOrderTypeBeanObject(jsonArray.getJSONObject(i));
				if(bean != null){
					list.add(bean);
				}
			}
		 
		return list;
	}
	

	
	public GetOrderTypeBean parseGetOrderTypeBeanObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		GetOrderTypeBean bean = new GetOrderTypeBean();
		
		if(jsonObject.has("id")){
			bean.id = jsonObject.getString("id");
		}
		if(jsonObject.has("name")){
			bean.name = jsonObject.getString("name");
		}
		if(jsonObject.has("desc")){
			bean.desc = jsonObject.getString("desc");
		}
		if(jsonObject.has("moviePayType")){
			bean.moviePayType = jsonObject.getString("moviePayType");
		}
		if(jsonObject.has("rule")){
			bean.rule = jsonObject.getString("rule");
		}
		if(jsonObject.has("handfee")){
			bean.handfee = jsonObject.getString("handfee");
		}
		if(jsonObject.has("coupon")){
			JSONArray jsonArray = jsonObject.getJSONArray("coupon");
			bean.couponBeanList = parseCouponBeanArray(jsonArray);
		}
		return bean;
		
	}
	
	public ArrayList<CouponBean> parseCouponBeanArray(JSONArray jsonArray) throws JSONException{
		if(jsonArray == null){
			return null;
		}
		 ArrayList<CouponBean> list = new  ArrayList<CouponBean>();
			int length = jsonArray.length();
			for(int i=0;i<length;i++){
				CouponBean bean = parseCouponBeanObject(jsonArray.getJSONObject(i));
				if(bean != null){
					list.add(bean);
				}
			}
		 
		return list;
	}
	
	public CouponBean parseCouponBeanObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		CouponBean bean = new CouponBean();
		
		if(jsonObject.has("id")){
			bean.id = jsonObject.getString("id");
		}
		if(jsonObject.has("name")){
			bean.name = jsonObject.getString("name");
		}
		if(jsonObject.has("remark")){
			bean.remark = jsonObject.getString("remark");
		}
		if(jsonObject.has("type")){
			bean.type = jsonObject.getString("type");
		}
		if(jsonObject.has("money")){
			bean.money = jsonObject.getString("money");
		}
		if(jsonObject.has("discount")){
			bean.discount = jsonObject.getString("discount");
		}
		return bean;
		
	}
	public GetOrderTypeBeanInfo parseGetOrderTypeBeanInfo(String json) throws JSONException{
		
		GetOrderTypeBeanInfo getOrderTypeBeanInfo = new GetOrderTypeBeanInfo();
		JSONObject object = new JSONObject(json);
		
		if(object.has("code")){
			getOrderTypeBeanInfo.code = object.getString("code");
		}
		if(object.has("message")){
			getOrderTypeBeanInfo.message = object.getString("message");
		}
		if(object.has("freeRegistUrl")){
			getOrderTypeBeanInfo.freeRegistUrl = object.getString("freeRegistUrl");
		}
		if(object.has("linkUrl")){
			getOrderTypeBeanInfo.linkUrl = object.getString("linkUrl");
		}
		if(object.has("merchant")){
			JSONObject jsonObject = object.getJSONObject("merchant");
			getOrderTypeBeanInfo.merchantBean = parseMerchantBeanObject(jsonObject);
		}
		if(object.has("type")){
			JSONArray jsonArray = object.getJSONArray("type");
			getOrderTypeBeanInfo.getOrderTypeBeanList = parseGetOrderTypeBeanArray(jsonArray);
		}
		return getOrderTypeBeanInfo;
	}
}
