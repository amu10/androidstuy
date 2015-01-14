package com.szcmcc.movie.bean;

public class Order {

	public OrderInner orderInner = null;
	public Result result = null;
	public boolean isSuccess(){
		if(result!=null&&result.success()){
			return true;
		}
		return false;
	}
	public static class OrderInner{
		public String orderid = "";
		public String expired_time = "";
		public String price = "";
		public String payPhone = "";
		public String recvPhone = "";
		public String count = "";
	}
}
