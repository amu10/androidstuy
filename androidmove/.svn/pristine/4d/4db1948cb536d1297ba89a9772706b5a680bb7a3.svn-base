package com.szcmcc.movie.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.MovieCinema;
import com.szcmcc.movie.bean.MovieCinema.Coupon;
import com.szcmcc.movie.bean.MovieCinema.MovieCinemaInner;
import com.szcmcc.movie.bean.MovieCinemaList;
import com.szcmcc.movie.bean.MovieCinemaList.MovieCinemaListInner;
import com.szcmcc.movie.bean.Result;

public class MovieCinemaParse {
	private Context mContext;

	public MovieCinemaParse(Context context) {
		mContext = context;
	}

	public Result parseResultObject(JSONObject jsonObject) throws JSONException {
		if (jsonObject == null) {
			return null;
		}
		Result result = new Result();
		if (jsonObject.has("code")) {
			result.code = jsonObject.getString("code");
		}
		if (jsonObject.has("message")) {
			result.message = jsonObject.getString("message");
		}
		return result;

	}

	public MovieCinemaList parseMovieCinemaInfo(String json) throws JSONException {
		MovieCinemaList movieCinemaList = new MovieCinemaList();
		JSONObject object = new JSONObject(json);
		if (object.has("regions")) {
			JSONArray jsonArray = object.getJSONArray("regions");
			movieCinemaList.mList = parseMovieArrayInner(jsonArray);
		}
		if (object.has("update_time")) {
			movieCinemaList.update_time = object.getString("update_time");
		}
		if (object.has("result")) {
			JSONObject jsonObject = object.getJSONObject("result");
			movieCinemaList.result = parseResultObject(jsonObject);
		}
		return movieCinemaList;
	}

	public ArrayList<MovieCinemaListInner> parseMovieArrayInner(JSONArray jsonArray) throws JSONException {
		if (jsonArray == null || jsonArray.length() == 0) {
			return null;
		}
		ArrayList<MovieCinemaListInner> cinemaList = new ArrayList<MovieCinemaListInner>();
		int length = jsonArray.length();
		for (int i = 0; i < length; i++) {
			MovieCinemaList.MovieCinemaListInner inner = parseInnerMovieCinema(jsonArray.getJSONObject(i));

			if (inner != null) {
				cinemaList.add(inner);
			}
		}
		return cinemaList;
	}

	public MovieCinemaList.MovieCinemaListInner parseInnerMovieCinema(JSONObject jsonObject) throws JSONException {
		if (jsonObject == null || jsonObject.length() == 0) {
			return null;
		}
		MovieCinemaList.MovieCinemaListInner inner = new MovieCinemaList.MovieCinemaListInner();
		if (jsonObject.has("region_id")) {
			inner.region_id = jsonObject.getString("region_id");
		}
		if (jsonObject.has("region_name")) {
			inner.region_name = jsonObject.getString("region_name");
		}
		if (jsonObject.has("cinemas")) {
			JSONArray jsonArray = jsonObject.getJSONArray("cinemas");
			inner.cinemas = parseMovieCinemaArray(jsonArray);
		}
		return inner;

	}

	public ArrayList<MovieCinema> parseMovieCinemaArray(JSONArray jsonArray) throws JSONException {
		if (jsonArray == null) {
			return null;
		}
		ArrayList<MovieCinema> list = new ArrayList<MovieCinema>();
		int length = jsonArray.length();
		for (int i = 0; i < length; i++) {
			MovieCinema movieCinema = parseMovieCinemaObject(jsonArray.getJSONObject(i));
			if (movieCinema != null) {
				list.add(movieCinema);
			}
		}

		return list;
	}

	public MovieCinema parseMovieCinemaObject(JSONObject jsonObject) throws JSONException {
		if (jsonObject == null) {
			return null;
		}
		MovieCinema movieCinema = new MovieCinema();

		if (jsonObject.has("c_id")) {
			movieCinema.c_id = jsonObject.getString("c_id");
		}
		if (jsonObject.has("c_name")) {
			movieCinema.c_name = jsonObject.getString("c_name");
		}
		if (jsonObject.has("address")) {
			movieCinema.address = jsonObject.getString("address");
		}
		if (jsonObject.has("image_url")) {
			movieCinema.image_url = jsonObject.getString("image_url");
		}
		if (jsonObject.has("open_time")) {
			movieCinema.open_time = jsonObject.getString("open_time");
		}
		if (jsonObject.has("traffic")) {
			movieCinema.traffic = jsonObject.getString("traffic");
		}
		if (jsonObject.has("longitude")) {
			movieCinema.longitude = jsonObject.getString("longitude");
		}
		if (jsonObject.has("latitude")) {
			movieCinema.latitude = jsonObject.getString("latitude");
		}
		if (jsonObject.has("telephone")) {
			movieCinema.telephone = jsonObject.getString("telephone");
		}
		if (jsonObject.has("order_type")) {
			movieCinema.order_type = jsonObject.getString("order_type");
		}
		if (jsonObject.has("suppCouponName")) {
			movieCinema.suppCouponName = jsonObject.getString("suppCouponName");
		}
		if (jsonObject.has("price")) {
			movieCinema.price = jsonObject.getString("price");
		}
		if (jsonObject.has("mprice")) {
			movieCinema.mprice = jsonObject.getString("mprice");
		}
		if (jsonObject.has("client_cinema_image_url")) {
			movieCinema.client_cinema_image_url = jsonObject.getString("client_cinema_image_url");
		}
		if (jsonObject.has("companyId")) {
			movieCinema.companyId = jsonObject.getString("companyId");
		}
		if (jsonObject.has("payType")) {
			JSONArray jsonArray = jsonObject.getJSONArray("payType");
			movieCinema.MovieCinemaInnerList = parseMovieCinemaInnerArray(jsonArray);
		}
		if (jsonObject.has("coupon")) {
			JSONObject object = jsonObject.optJSONObject("coupon");
			if (object != null) {
				movieCinema.coupon = parseCouponObject(object);
			}

		}
		return movieCinema;

	}

	public ArrayList<MovieCinemaInner> parseMovieCinemaInnerArray(JSONArray jsonArray) throws JSONException {
		if (jsonArray == null) {
			return null;
		}
		ArrayList<MovieCinemaInner> list = new ArrayList<MovieCinemaInner>();
		int length = jsonArray.length();
		for (int i = 0; i < length; i++) {
			MovieCinemaInner movieCinemaInner = parseMovieCinemaListObject(jsonArray.getJSONObject(i));
			if (movieCinemaInner != null) {
				list.add(movieCinemaInner);
			}
		}

		return list;
	}

	public MovieCinemaInner parseMovieCinemaListObject(JSONObject jsonObject) throws JSONException {
		if (jsonObject == null) {
			return null;
		}
		MovieCinemaInner movieCinemaInner = new MovieCinemaInner();

		if (jsonObject.has("context")) {
			movieCinemaInner.context = jsonObject.getString("context");
		}
		if (jsonObject.has("payTpyeName")) {
			movieCinemaInner.payTpyeName = jsonObject.getString("payTpyeName");
		}
		if (jsonObject.has("payTypeId")) {
			movieCinemaInner.payTypeId = jsonObject.getString("payTypeId");
		}
		if (jsonObject.has("price")) {
			movieCinemaInner.price = jsonObject.getString("price");
		}
		if (jsonObject.has("unit")) {
			movieCinemaInner.unit = jsonObject.getString("unit");
		}
		return movieCinemaInner;

	}

	public Coupon parseCouponObject(JSONObject jsonObject) throws JSONException {
		if (jsonObject == null) {
			return null;
		}
		Coupon coupon = new Coupon();

		if (jsonObject.has("spid")) {
			coupon.spid = jsonObject.getString("spid");
		}
		if (jsonObject.has("sppwd")) {
			coupon.sppwd = jsonObject.getString("sppwd");
		}
		if (jsonObject.has("spcode")) {
			coupon.spcode = jsonObject.getString("spcode");
		}
		if (jsonObject.has("merchantCode")) {
			coupon.merchantCode = jsonObject.getString("merchantCode");
		}
		return coupon;

	}
}
