package com.szcmcc.movie.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.szcmcc.movie.bean.CinemaPrepare;
import com.szcmcc.movie.bean.CinemaPrepareInfo;
import com.szcmcc.movie.bean.CinemaPrepareInfo.MovieCinemaPrepareInner;
import com.szcmcc.movie.bean.MovieTidbits;
import com.szcmcc.movie.bean.Result;

public class MovieCinemaPrepareParse {

	private static final String TAG = "MovieCinemaPrepareParse";
	private Context mContext;

	public MovieCinemaPrepareParse(Context context) {
		mContext = context;
	}
	
	public Result parseResultObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		Result result = new Result();
		if(jsonObject.has("code")){
			result.code = jsonObject.getString("code");
		}
		if(jsonObject.has("message")){
			result.message = jsonObject.getString("message");
		}
		return result;
		
	}
	
	public CinemaPrepareInfo.MovieCinemaPrepareInner.daysBill parsedaysBillObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		CinemaPrepareInfo.MovieCinemaPrepareInner.daysBill daysbill = new CinemaPrepareInfo.MovieCinemaPrepareInner.daysBill();
		if(jsonObject.has("day_time")){
			daysbill.day_time = jsonObject.getString("day_time");
		}
		if(jsonObject.has("s_time")){
			daysbill.s_time = jsonObject.getString("s_time");
		}
		return daysbill;
		
	}
	
	public CinemaPrepareInfo parseMovieCinemaPrepareInfo(String json) throws JSONException{
		CinemaPrepareInfo cinemaPrepareInfo = new CinemaPrepareInfo();
		JSONObject object = new JSONObject(json);
		if(object.has("movies")){
			JSONArray jsonArray = object.getJSONArray("movies");
			cinemaPrepareInfo.movies = parseMovieArrayInner(jsonArray);
		}
		if(object.has("result")){
			JSONObject jsonObject = object.getJSONObject("result");
			cinemaPrepareInfo.result = parseResultObject(jsonObject);
		}
		if(object.has("isShow")){
			String isShow = object.optString("isShow");
			cinemaPrepareInfo.isShow = isShow;
		}
		return cinemaPrepareInfo;
	}
	
	public ArrayList<MovieCinemaPrepareInner> parseMovieArrayInner(JSONArray jsonArray) throws JSONException{
		if(jsonArray==null||jsonArray.length()==0){
			return null;
		}
		ArrayList<MovieCinemaPrepareInner> cinemaPrepareList = new ArrayList<MovieCinemaPrepareInner>();
		int length = jsonArray.length();
		for(int i=0;i<length;i++){
			CinemaPrepareInfo.MovieCinemaPrepareInner inner = parseInnerMovieCinemaPrepare(jsonArray.getJSONObject(i));
			
			if(inner!=null){
				cinemaPrepareList.add(inner);
			}
		}
		return cinemaPrepareList;
 	}
	
	public CinemaPrepareInfo.MovieCinemaPrepareInner parseInnerMovieCinemaPrepare(JSONObject jsonObject)throws JSONException{
		if(jsonObject==null||jsonObject.length()==0){
			return null;
		}
		CinemaPrepareInfo.MovieCinemaPrepareInner inner = new CinemaPrepareInfo.MovieCinemaPrepareInner();
		if(jsonObject.has("m_id")){
			inner.m_id = jsonObject.getString("m_id");
		}
		if(jsonObject.has("upcomming")){
			inner.m_id = jsonObject.getString("upcomming");
		}
		if(jsonObject.has("name")){
			inner.name = jsonObject.getString("name");
		}
		if(jsonObject.has("rate")){
			inner.rate = jsonObject.getString("rate");
		}
		if(jsonObject.has("comment_count")){
			inner.comment_count = jsonObject.getString("comment_count");
		}
		if(jsonObject.has("director")){
			inner.director = jsonObject.getString("director");
		}
		if(jsonObject.has("main_actor")){
			inner.main_actors = jsonObject.getString("main_actor");
		}
		if(jsonObject.has("type")){
			inner.type = jsonObject.getString("type");
		}
		if(jsonObject.has("release_date")){
			inner.release_date = jsonObject.getString("release_date");
		}
		if(jsonObject.has("country")){
			inner.country = jsonObject.getString("country");
		}
		if(jsonObject.has("introduce")){
			inner.introduce = jsonObject.getString("introduce");
		}
		if(jsonObject.has("cover_image_url")){
			inner.cover_image_url = jsonObject.getString("cover_image_url");
		}
		if(jsonObject.has("client_tidbits_url")){
			try{
				JSONArray jsonArray = jsonObject.getJSONArray("client_tidbits_url"); 
				inner.client_tidbits_url = parseMovieCinemaPrepareImageArray(jsonArray);
				}catch(Exception e){
					Log.e(TAG,"e:"+e.toString());
					e.printStackTrace();
				}
		}
		if(jsonObject.has("client_placard_url1")){
			inner.client_placard_url1 = jsonObject.getString("client_placard_url1");
		}
		if(jsonObject.has("client_placard_url2")){
			inner.client_placard_url2 = jsonObject.getString("client_placard_url2");
		}
		if(jsonObject.has("trailersAndroid")){
			inner.trailersAndroid = jsonObject.getString("trailersAndroid");
		}
		if(jsonObject.has("daysBill")){
			JSONObject jsonObject1 = jsonObject.getJSONObject("daysBill");
			inner.daysbill = parsedaysBillObject(jsonObject1);
		}
		if(jsonObject.has("daysSeat")){
			try{
			JSONArray jsonArray = jsonObject.getJSONArray("daysSeat"); 
			inner.daysSeat = parseMovieCinemaPrepareArray(jsonArray);
			}catch(Exception e){
				Log.e(TAG,"e:"+e.toString());
				e.printStackTrace();
			}
		}
//		Log.e(TAG,"inner:"+inner);
		return inner;
		
	}
	
	public ArrayList<CinemaPrepare> parseMovieCinemaPrepareArray(JSONArray jsonArray) throws JSONException{
		if(jsonArray == null){
			return null;
		}
		 ArrayList<CinemaPrepare> list = new  ArrayList<CinemaPrepare>();
			int length = jsonArray.length();
			for(int i=0;i<length;i++){
				CinemaPrepare cinemaPrepare = parseMovieCinemaPrepareObject(jsonArray.getJSONObject(i));
				if(cinemaPrepare != null){
					list.add(cinemaPrepare);
				}
			}
		 
		return list;
	}
	
	public CinemaPrepare parseMovieCinemaPrepareObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		CinemaPrepare cinemaPrepare = new CinemaPrepare();
		
		if(jsonObject.has("day_time")){
			cinemaPrepare.day_time = jsonObject.getString("day_time");
		}
		if(jsonObject.has("movieSetName")){
			cinemaPrepare.movieSetName = jsonObject.getString("movieSetName");
		}
		if(jsonObject.has("showCode")){
			cinemaPrepare.showCode = jsonObject.getString("showCode");
		}
		if(jsonObject.has("room")){
			cinemaPrepare.room = jsonObject.getString("room");
		}
		if(jsonObject.has("s_time")){
			cinemaPrepare.s_time = jsonObject.getString("s_time");
		}
		if(jsonObject.has("price")){
			cinemaPrepare.price = jsonObject.getString("price");
		}
		if(jsonObject.has("language")){
			cinemaPrepare.language = jsonObject.getString("language");
		}
		if(jsonObject.has("type")){
			cinemaPrepare.type = jsonObject.getString("type");
		}
		if(jsonObject.has("cinemaPrice")){
			cinemaPrepare.cinemaPrice = jsonObject.getString("cinemaPrice");
		}
		return cinemaPrepare;
		
	}
	
	
	public ArrayList<MovieTidbits> parseMovieCinemaPrepareImageArray(JSONArray jsonArray) throws JSONException{
		if(jsonArray == null){
			return null;
		}
		 ArrayList<MovieTidbits> list = new  ArrayList<MovieTidbits>();
			int length = jsonArray.length();
			for(int i=0;i<length;i++){
				MovieTidbits movieTidbits = parseMovieCinemaPrepareImageObject(jsonArray.getJSONObject(i));
				if(movieTidbits != null){
					list.add(movieTidbits);
				}
			}
		 
		return list;
	}
	
	public MovieTidbits parseMovieCinemaPrepareImageObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		MovieTidbits movieTidbits = new MovieTidbits();
		
		if(jsonObject.has("client_tidbits_url_large")){
			movieTidbits.client_tidbits_url_large = jsonObject.getString("client_tidbits_url_large");
		}
		if(jsonObject.has("client_tidbits_url_small")){
			movieTidbits.client_tidbits_url_small = jsonObject.getString("client_tidbits_url_small");
		}
		
		return movieTidbits;
		
	}
}