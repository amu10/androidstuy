package com.szcmcc.movie.bean;

public class SendShort {

	public String recvPhone = "";
	public String orderid = "";
	public String orderType = "";
	public String expired_time = "";
	
	public Result result = null;
	public boolean isSuccess(){
		if(result!=null&&result.success()){
			return true;
		}
		return false;
	}
	
}
