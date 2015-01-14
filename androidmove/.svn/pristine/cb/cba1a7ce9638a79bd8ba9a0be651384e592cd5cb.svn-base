package com.szcmcc.movie.bean;


public class OrderPay {

	public OrderPayInner orderPayInner = null;
	public Result result = null;
	public boolean isSuccess(){
		if(result!=null&&result.success()){
			return true;
		}
		return false;
	}
	public static class OrderPayInner{
		public String orderid = "";
		public String orderType = "";
		public String payMoney = "";
		public String payPhone = "";
	}
}
