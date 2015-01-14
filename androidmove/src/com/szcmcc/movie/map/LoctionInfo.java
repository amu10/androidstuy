package com.szcmcc.movie.map;

public class LoctionInfo {

	String lat;
	String lon;
	
	public LoctionInfo(String lat, String lon) {
		this.lat = lat;
		this.lon = lon;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	
	
}
