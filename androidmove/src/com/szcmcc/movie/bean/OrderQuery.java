package com.szcmcc.movie.bean;

import java.util.ArrayList;

public class OrderQuery {

	public ArrayList<OrderQueryInner> list = null;
	public String orderid = "";
	public String orderTpye = "";
	public String count = "";
	
	public String payType = "";
	
	public Result result = null;
	public boolean isSuccess(){
		if(result!=null&&result.success()){
			return true;
		}
		return false;
	}
	
	public static class OrderQueryInner{
		public String childorderid = "";
		public String orderstatu = "";
	}
}
