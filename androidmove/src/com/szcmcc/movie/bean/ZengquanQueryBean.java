package com.szcmcc.movie.bean;

public class ZengquanQueryBean {

	
	/** 赠券码*/
	public String verifyCode = "";
	/** 适用影院，若适用所有影院，则返回“所有”代替影院名称串*/
	public String allowCinema = "";
	/**适用类型，1:2D、 2:3D、3：IMAX、4：贵宾厅*/
	public String filmType = "";
	/** 有效日期*/
	public String validDate = "";
	/** 赠券状态，2：发送成功（即未使用） 4：已使用*/
	public String status = "";
	/** 赠送时间*/
	public String presentTime = "";
}
