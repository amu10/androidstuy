package com.szcmcc.movie.bean;

import java.io.Serializable;

public class CinemaPrepare implements Serializable{

	public String day_time = "";
	public String movieSetName = "";
	public String showCode = "";
	public String room = "";
	public String s_time = "";
	public String price = "";
	public String language = "";
	public String type = "";
	public String cinemaPrice = "";
	@Override
	public String toString() {
		return "CinemaPrepare [day_time=" + day_time + ", movieSetName="
				+ movieSetName + ", showCode=" + showCode + ", room=" + room
				+ ", s_time=" + s_time + ", price=" + price + ", language="
				+ language + ", type=" + type + "]";
	}
	
	
}
