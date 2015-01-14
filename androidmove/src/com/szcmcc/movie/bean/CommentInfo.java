package com.szcmcc.movie.bean;

import java.util.ArrayList;

public class CommentInfo {
	public ArrayList<Comment> comments = null;
	public ArrayList<Profession> professions = null;
	public String total = null;
	public String page_size = null;
	public String rate = null;
	public Result result = null;
	public boolean isSuccess(){
		if(result!=null&&result.success()){
			return true;
		}
		return false;
	}
}
