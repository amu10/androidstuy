package com.szcmcc.movie.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderBySeat  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7924136716022732309L;
	public OrderBySeatInner orderBySeatInner = null;
	public Result result = null;
	public boolean isSuccess(){
		if(result!=null&&result.success()){
			return true;
		}
		return false;
	}
	public static class OrderBySeatInner implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1372491967518292986L;
		public String orderid = "";
		public String lockSerialNo = "";
		public String payPhone = "";
		public String showCode = "";
		public ArrayList<OrderBySeatInnerList>  orderBySeatInnerList = null;
		
		public static class OrderBySeatInnerList implements Serializable{
			/**
			 * 
			 */
			private static final long serialVersionUID = -3728381406231606183L;
			public String seatCode = "";
			public String seatRow = "";
			public String seatCol = "";
			public String seatAreaCode = "";
			public String price = "";
		}
	}
}
