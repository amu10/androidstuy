package com.szcmcc.movie.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.mapabc.mapapi.core.GeoPoint;

/**
 * 电影院信息
 * 
 */
public class MovieCinema implements Serializable {

	public String c_id = "";
	public String suppCouponName = "";
	public String c_name = "";
	public String image_url = "";
	// public String status = "";
	public String address = "";
	public String open_time = "";
	public String traffic = "";
	public String meters = "";
	public String order_type = "";// 0 可订做,1可购卷,2是两者都有
	public String longitude = "";
	public String latitude = "";
	public String telephone = "";
	public String price = "";
	public String mprice = "";
	public String client_cinema_image_url = "";
	public String companyId = "";
	public ArrayList<MovieCinemaInner> MovieCinemaInnerList = null;
	public Coupon coupon = null;

	public static class MovieCinemaInner implements Serializable {
		public String context = "";
		public String payTpyeName = "";
		public String payTypeId = "";
		public String price = "";
		public String unit = "";
	}

	public static class Coupon implements Serializable {
		public String spid = "";
		public String sppwd = "";
		public String spcode = "";
		public String merchantCode = "";
	}

	private GeoPoint geoPoint = null;

	public GeoPoint getGeoPoint() {
		return geoPoint;
	}

	public void setGeoPoint(GeoPoint geoPoint) {
		this.geoPoint = geoPoint;
	}

	public MovieCinema() {

	}
}
