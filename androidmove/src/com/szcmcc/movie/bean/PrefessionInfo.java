package com.szcmcc.movie.bean;

import java.util.ArrayList;

public class PrefessionInfo {
	public ArrayList<Profession> professions = null;
	public String total = "";
//	public String curPage = "";
//	public String page_size = "";
	public Result result = null;
	public boolean isSuccess(){
		if(result!=null&&result.success()){
			return true;
		}
		return false;
	}
}
