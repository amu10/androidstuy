package com.szcmcc.movie.bean;

import java.util.ArrayList;


public class MoviePinLunList {

	public Result result = null;
	public boolean isSuccess(){
		if(result!=null&&result.success()){
			return true;
		}
		return false;
	}
	
	public ArrayList<MoviePinLun> mMoviePinLuns = new ArrayList<MoviePinLun>();

	public void release() {
		mMoviePinLuns.clear();
	}

	public void addMoviePinLun(MoviePinLun mMoviePinLun) {
		mMoviePinLuns.add(mMoviePinLun);
	}
}
