package com.szcmcc.movie.network;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.LockOrDebLockMovieSeatInfo;
import com.szcmcc.movie.bean.LockOrDebLockMovieSeatsInfo;
import com.szcmcc.movie.bean.MoviePayType;
import com.szcmcc.movie.bean.MovieSeatInfo;
import com.szcmcc.movie.bean.Result;
import com.szcmcc.movie.bean.SeatInfo;

public class MovieParse {
	private Context mContext;

	public MovieParse(Context context) {
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

	// public MovieInfo parseMovieInfo(String json) throws JSONException{
	// MovieInfo movieInfo = new MovieInfo();
	// JSONObject object = new JSONObject(json);
	// if(object.has("movies")){
	// JSONArray jsonArray = object.getJSONArray("movies");
	// movieInfo.movies = parseMovieArray(jsonArray);
	// }
	// if(object.has("result")){
	// JSONObject jsonObject = object.getJSONObject("result");
	// movieInfo.result = parseResultObject(jsonObject);
	// }
	// if(object.has("isShow")){
	// String isShow = object.optString("isShow");
	// movieInfo.isShow = isShow;
	// }
	// return movieInfo;
	// }
	//
	// public ArrayList<Movie> parseMovieArray(JSONArray jsonArray) throws
	// JSONException{
	// if(jsonArray==null||jsonArray.length()==0){
	// return null;
	// }
	// ArrayList<Movie> movieList = new ArrayList<Movie>();
	// int length = jsonArray.length();
	// for(int i=0;i<length;i++){
	// Movie movie = parseMovieObject(jsonArray.getJSONObject(i));
	// if(movie!=null){
	// movieList.add(movie);
	// }
	// }
	// return movieList;
	// }

	// public Movie parseMovieObject(JSONObject jsonObject) throws
	// JSONException{
	// if(jsonObject == null){
	// return null;
	// }
	// Movie movie = new Movie();
	//
	// if(jsonObject.has("comment_count")){
	// movie.comment_count = jsonObject.getString("comment_count");
	// }
	// if(jsonObject.has("country")){
	// movie.country = jsonObject.getString("country");
	// }
	// if(jsonObject.has("cover_image_url")){
	// movie.cover_image_url = jsonObject.getString("cover_image_url");
	// }
	// if(jsonObject.has("director")){
	// movie.director = jsonObject.getString("director");
	// }
	// if(jsonObject.has("introduce")){
	// movie.introduce = jsonObject.getString("introduce");
	// }
	// if(jsonObject.has("m_id")){
	// movie.m_id = jsonObject.getString("m_id");
	// }
	// if(jsonObject.has("main_actor")){
	// movie.main_actor = jsonObject.getString("main_actor");
	// }
	// if(jsonObject.has("name")){
	// movie.name = jsonObject.getString("name");
	// }
	// if(jsonObject.has("rate")){
	// movie.rate = jsonObject.getString("rate");
	// }
	// if(jsonObject.has("release_date")){
	// movie.release_date = jsonObject.getString("release_date");
	// }
	// if(jsonObject.has("trailersAndroid")){
	// movie.trailersAndroid = jsonObject.getString("trailersAndroid");
	// }
	// if(jsonObject.has("trailersIphone")){
	// movie.trailersIphone = jsonObject.getString("trailersIphone");
	// }
	// if(jsonObject.has("type")){
	// movie.type = jsonObject.getString("type");
	// }
	// if(jsonObject.has("upcomming")){
	// movie.upcomming = jsonObject.getString("upcomming");
	// }
	// movie.client_placard_url1 = jsonObject.optString("client_placard_url1");
	// movie.client_placard_url2 = jsonObject.optString("client_placard_url2");
	// JSONArray tidbitsArray = jsonObject.optJSONArray("client_tidbits_url");
	// ArrayList<MovieTidbits>list = new ArrayList<MovieTidbits>();
	// int tidbitsSize = tidbitsArray.length();
	// for(int i=0;i<tidbitsSize;i++){
	// MovieTidbits tidbits = new MovieTidbits();
	// JSONObject tidbitsObj = tidbitsArray.getJSONObject(i);
	// tidbits.client_tidbits_url_large =
	// tidbitsObj.optString("client_tidbits_url_large");
	// tidbits.client_tidbits_url_small =
	// tidbitsObj.optString("client_tidbits_url_small");
	// list.add(tidbits);
	// }
	// movie.client_tidbits_url = list;
	// return movie;
	// }

	/**
	 * 解析电影座位的信息
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public MovieSeatInfo parseMovieSeatInfo(String json) throws JSONException {
		MovieSeatInfo movieSeatInfo = new MovieSeatInfo();
		JSONObject jsonObject = new JSONObject(json);
		if (jsonObject.has("result")) {
			JSONObject object = jsonObject.getJSONObject("result");
			movieSeatInfo.result = parseResultObject(object);
		}
		// if(jsonObject.has("seats")){
		// if(!TextUtils.isEmpty(jsonObject.getString("seats"))){
		// JSONArray jsonArray = jsonObject.getJSONArray("seats");
		// movieSeatInfo.seats = parseMovieSeatArray(jsonArray);
		// }
		// }
		JSONArray jsonArray = jsonObject.optJSONArray("seats");
		movieSeatInfo.seats = parseMovieSeatArray(jsonArray);

		return movieSeatInfo;
	}

	/**
	 * 解析电影座位数组
	 * 
	 * @param jsonArray
	 * @return
	 * @throws JSONException
	 */
	private List<SeatInfo> parseMovieSeatArray(JSONArray jsonArray) throws JSONException {

		List<SeatInfo> seatList = new ArrayList<SeatInfo>();
		if (jsonArray == null) {
			return seatList;
		}
		for (int i = 0; i < jsonArray.length(); i++) {
			SeatInfo seatInfo = parseSeatInfo(jsonArray.getJSONObject(i));
			if (seatInfo != null) {
				seatList.add(seatInfo);
			}
		}
		return seatList;
	}

	/**
	 * 解析单个座位
	 * 
	 * @param jsonObject
	 * @return
	 * @throws JSONException
	 */
	private SeatInfo parseSeatInfo(JSONObject jsonObject) throws JSONException {
		SeatInfo seatInfo = new SeatInfo();
		if (jsonObject != null) {
			if (jsonObject.has("seatCode")) {
				seatInfo.seatCode = jsonObject.getString("seatCode");
			}
			if (jsonObject.has("seatAreaCode")) {
				seatInfo.seatAreaCode = jsonObject.getString("seatAreaCode");
			}
			if (jsonObject.has("graphRow")) {
				seatInfo.graphRow = jsonObject.getString("graphRow");
			}
			if (jsonObject.has("graphCol")) {
				seatInfo.graphCol = jsonObject.getString("graphCol");
			}
			if (jsonObject.has("seatRow")) {
				seatInfo.seatRow = jsonObject.getString("seatRow");
			}
			if (jsonObject.has("seatCol")) {
				seatInfo.seatCol = jsonObject.getString("seatCol");
			}
			if (jsonObject.has("damageFlg")) {
				seatInfo.damageFlg = jsonObject.getString("damageFlg");
			}
			if (jsonObject.has("seatState")) {
				// System.out.println("dongdianzhouMovieParseparseSeatInfo" +
				// jsonObject.getString("seatState"));
				seatInfo.seatState = jsonObject.getString("seatState");
			}
		}
		// System.out.println("dongdianzhouMovieParse" + seatInfo.toString());
		return seatInfo;
	}

	/**
	 * 解析锁定座位信息
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public LockOrDebLockMovieSeatsInfo parseLockOrDebLockMovieSeatsInfo(String json)
			throws JSONException {
		String lockSets = "";
		LockOrDebLockMovieSeatsInfo lockOrDebLockMovieSeatsInfo = new LockOrDebLockMovieSeatsInfo();
		JSONObject object = new JSONObject(json);
		if (object.has("lockSets")) {
			lockSets = object.getString("lockSets");
		}
		JSONObject jsonObject = new JSONObject(lockSets);
		if (jsonObject.has("c_id")) {
			lockOrDebLockMovieSeatsInfo.c_id = jsonObject.getString("c_id");
		}
		if (jsonObject.has("showCode")) {
			lockOrDebLockMovieSeatsInfo.showCode = jsonObject.getString("showCode");
		}
		if (jsonObject.has("lockedType")) {
			lockOrDebLockMovieSeatsInfo.lockedType = jsonObject.getString("lockedType");
		}
		if (jsonObject.has("ticketCount")) {
			lockOrDebLockMovieSeatsInfo.ticketCount = jsonObject.getString("ticketCount");
		}
		if (jsonObject.has("recvPhone")) {
			lockOrDebLockMovieSeatsInfo.recyPhone = jsonObject.getString("recvPhone");
		}
		if (jsonObject.has("lockSerialNo")) {
			lockOrDebLockMovieSeatsInfo.lockSerialNo = jsonObject.getString("lockSerialNo");
		}
		if (jsonObject.has("lockSeats")) {
			JSONArray jsonArray = jsonObject.getJSONArray("lockSeats");
			lockOrDebLockMovieSeatsInfo.lockSeats = parseLockOrDebLockMovieseatInfo(jsonArray);
		}
		if (jsonObject.has("payType")) {
			JSONArray jsonArray = jsonObject.getJSONArray("payType");
			lockOrDebLockMovieSeatsInfo.payType = parseMoviePayType(jsonArray);
		}
		if (object.has("result")) {
			lockOrDebLockMovieSeatsInfo.result = parseResultObject(object.getJSONObject("result"));
		}
		return lockOrDebLockMovieSeatsInfo;
	}

	/**
	 * 解析座位票的支付类型数组
	 * 
	 * @param jsonArray
	 * @return
	 * @throws JSONException
	 */
	private List<MoviePayType> parseMoviePayType(JSONArray jsonArray) throws JSONException {
		List<MoviePayType> list = new ArrayList<MoviePayType>();
		for (int i = 0; i < jsonArray.length(); i++) {
			MoviePayType moviePayType = (MoviePayType) parseMoviePayType(jsonArray.getJSONObject(i));
			if (moviePayType != null) {
				list.add(moviePayType);
			}
		}
		return list;
	}

	/**
	 * 解析座位票的支付类型
	 * 
	 * @param jsonObject
	 * @return
	 * @throws JSONException
	 */
	private MoviePayType parseMoviePayType(JSONObject jsonObject) throws JSONException {
		MoviePayType moviePayType = new MoviePayType();
		if (jsonObject.has("context")) {
			moviePayType.context = jsonObject.getString("context");
		}
		if (jsonObject.has("payTpyeName")) {
			moviePayType.payTpyeName = jsonObject.getString("payTpyeName");
		}
		if (jsonObject.has("payTypeId")) {
			moviePayType.payTypeId = jsonObject.getString("payTypeId");
		}
		if (jsonObject.has("price")) {
			moviePayType.price = jsonObject.getString("price");
		}
		if (jsonObject.has("unit")) {
			moviePayType.unit = jsonObject.getString("unit");
		}
		return moviePayType;
	}

	/**
	 * 解析锁定的桌位数组
	 * 
	 * @param jsonArray
	 * @return
	 * @throws JSONException
	 */
	private List<LockOrDebLockMovieSeatInfo> parseLockOrDebLockMovieseatInfo(JSONArray jsonArray)
			throws JSONException {

		List<LockOrDebLockMovieSeatInfo> list = new ArrayList<LockOrDebLockMovieSeatInfo>();
		for (int i = 0; i < jsonArray.length(); i++) {
			LockOrDebLockMovieSeatInfo lockMovieSeatInfo = parseLockOrDebLockMovieSeatInfo(jsonArray
					.getJSONObject(i));
			if (lockMovieSeatInfo != null) {
				list.add(lockMovieSeatInfo);
			}
		}
		return list;
	}

	/**
	 * 解析单个的桌位
	 * 
	 * @param object
	 * @return
	 * @throws JSONException
	 */
	private LockOrDebLockMovieSeatInfo parseLockOrDebLockMovieSeatInfo(JSONObject jsonObject)
			throws JSONException {
		LockOrDebLockMovieSeatInfo lockOrDebLockMovieSeatInfo = new LockOrDebLockMovieSeatInfo();
		if (jsonObject.has("seatCode")) {
			lockOrDebLockMovieSeatInfo.seatCode = jsonObject.getString("seatCode");
		}
		if (jsonObject.has("seatRow")) {
			lockOrDebLockMovieSeatInfo.seatRow = jsonObject.getString("seatRow");
		}
		if (jsonObject.has("seatCol")) {
			lockOrDebLockMovieSeatInfo.seatCol = jsonObject.getString("seatCol");
		}
		if (jsonObject.has("seatAreaCode")) {
			lockOrDebLockMovieSeatInfo.seatAreaCode = jsonObject.getString("seatAreaCode");
		}
		if (jsonObject.has("price")) {
			lockOrDebLockMovieSeatInfo.price = jsonObject.getString("price");
		}

		return lockOrDebLockMovieSeatInfo;
	}

}
