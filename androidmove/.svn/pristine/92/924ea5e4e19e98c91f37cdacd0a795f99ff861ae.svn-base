package com.szcmcc.movie.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class GetOrderTypeBeanInfo implements Serializable {

	public String code = "";
	public String message = "";
	public String freeRegistUrl = "";
	public String linkUrl = "";
	public ArrayList<GetOrderTypeBean> getOrderTypeBeanList;
	public MerchantBean merchantBean;
	public static class GetOrderTypeBean implements Serializable{
		public String id = "";
		public String name = "";
		public String desc = "";
		public String moviePayType = "";
		public String rule = "";
		public String handfee = "";
		public ArrayList<CouponBean> couponBeanList;
		public static class CouponBean implements Serializable{
			public String id = "";
			public String name = "";
			public String remark = "";
			public String type = "";//优惠券类型  1换购券 2代金券 3折扣券
			public String money = "";//优惠金额,type:1时money为支付金额，2时票价-money,3时money没用，此时discount为票价的打折
			public String discount = "";//折口（折口券时才有值）
		}
	}
	
	public static class MerchantBean implements Serializable{
		public String spid = "";
		public String sppwd = "";
		public String spcode = "";
		public String merchantCode = "";
		public String callback = "";
	}
	
}
