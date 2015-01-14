package com.szcmcc.movie.bean;


public class OrderPayOk {

	public OrderPayOkInner orderPayOkInner = null;
	public Result result = null;
	public boolean isSuccess(){
		if(result!=null&&result.success()){
			return true;
		}
		return false;
	}
	public static class OrderPayOkInner{
		public String orderid = "";
		public String orderType = "";
		public String c_id = "";
		public String count = "";
		public String payMoney = "";
		public String expired_time = "";
	}
}
