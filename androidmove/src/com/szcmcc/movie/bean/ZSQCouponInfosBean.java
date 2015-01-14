package com.szcmcc.movie.bean;

import java.io.Serializable;

public class ZSQCouponInfosBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 702555885872957452L;
	private String couponName;
	private String verifyCode;
	private String typeName;
	private String startDate;
	private String userTime;
	private String allowChannel;
	private String couponTime;
	private int couponType;
	private String endDate;
	private int status;

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getUserTime() {
		return userTime;
	}

	public void setUserTime(String userTime) {
		this.userTime = userTime;
	}

	public String getAllowChannel() {
		return allowChannel;
	}

	public void setAllowChannel(String allowChannel) {
		this.allowChannel = allowChannel;
	}

	public String getCouponTime() {
		return couponTime;
	}

	public void setCouponTime(String couponTime) {
		this.couponTime = couponTime;
	}

	public int getCouponType() {
		return couponType;
	}

	public void setCouponType(int couponType) {
		this.couponType = couponType;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
