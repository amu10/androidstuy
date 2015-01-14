package com.szcmcc.movie.bean;

import java.io.Serializable;
import java.util.ArrayList;


public class MovieCinemaList implements Serializable{

//	public MovieCinemaListInner movieCinemaListInner = null;
	public ArrayList<MovieCinemaListInner> mList = null;

	public String update_time = "";
	public Result result = null;
	public boolean isSuccess(){
		if(result!=null&&result.success()){
			return true;
		}
		return false;
	}

	
	public static class MovieCinemaListInner implements Serializable{
		public ArrayList<MovieCinema> cinemas = null;
		public String region_id = "";
		public String region_name = "";
	}
}
