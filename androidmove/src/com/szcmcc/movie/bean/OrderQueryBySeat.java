package com.szcmcc.movie.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderQueryBySeat  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3259241527854805279L;
	public String orderid = "";
	public String orderType = "";
	public String count = "";
	public String status = "";
	public String payType = "";
	public String showCode = "";
	public String lockSerialNo = "";
	public ArrayList<LockOrDebLockMovieSeatInfo> orderQueryBySeatList = null;

	public Result result = null;

	public boolean isSuccess() {
		if (result != null && result.success()) {
			return true;
		}
		return false;
	}

//	public static class OrderQueryBySeatList implements Serializable{
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = 5832457560187348030L;
//		public String seatCode = "";
//		public String seatRow = "";
//		public String seatCol = "";
//		public String seatAreaCode = "";
//		public String price = "";
//	}

}
