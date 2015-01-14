package com.szcmcc.movie.bean;

import java.util.ArrayList;

public class CinemaPrepareMovieCinema {

	public ArrayList<BySeat> list = null;
	public ByBill byBill = null;
	public Result result = null;
	public String isShow = "";
	public boolean isSuccess(){
		if(result!=null&&result.success()){
			return true;
		}
		return false;
	}
	
	public static class BySeat{
		public String day_time = "";
		public String movieSetName = "";
		public String showCode = "";
		public String room = "";
		public String s_time = "";
		public String price = "";
		public String language = "";
		public String type = "";
		public String cinemaPrice = "";
	}
	public static class ByBill{
		public String day_time = "";
		public String s_time = "";
	}
}
