package com.szcmcc.movie.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.szcmcc.movie.bean.OrderBySeat.OrderBySeatInner;
import com.szcmcc.movie.bean.OrderBySeat.OrderBySeatInner.OrderBySeatInnerList;

public class OrderPayOkBySeat implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8009506163696037189L;
	public OrderPayOkBySeatInner orderPayOkBySeatInner = null;
	public Result result = null;
	public boolean isSuccess(){
		if(result!=null&&result.success()){
			return true;
		}
		return false;
	}
	public static class OrderPayOkBySeatInner implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 8266684475067414502L;
		public String orderid = "";
		public String orderType = "";
		public String count = "";
		public String payMoney = "";
		public String showCode = "";
		public String lockSerialNo = "";
		public ArrayList<LockOrDebLockMovieSeatInfo> orderPayOkBySeatInnerList = null;
		
//		public static class OrderPayOkBySeatInnerList implements Serializable{
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = 7938286079927842731L;
//			public String seatCode = "";
//			public String seatRow = "";
//			public String seatCol = "";
//			public String seatAreaCode = "";
//			public String price = "";
//		}
	}
}