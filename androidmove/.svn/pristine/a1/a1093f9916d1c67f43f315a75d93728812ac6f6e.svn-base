package com.szcmcc.movie.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.CinemaPrepareMovieCinema;
import com.szcmcc.movie.bean.CinemaPrepareMovieCinema.ByBill;
import com.szcmcc.movie.bean.CinemaPrepareMovieCinema.BySeat;
import com.szcmcc.movie.bean.Result;

public class MovieCinemaAndMoviePrepareParse {
	private Context mContext;
	public MovieCinemaAndMoviePrepareParse(Context context) {
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
	
	public CinemaPrepareMovieCinema parseMovieCinemaPrepareInfo(String json) throws JSONException{
		CinemaPrepareMovieCinema cinemaPrepareMovieCinema = new CinemaPrepareMovieCinema();
		JSONObject object = new JSONObject(json);
		if(object.has("daysSeat")){
			try{
			JSONArray jsonArray = object.getJSONArray("daysSeat");
			cinemaPrepareMovieCinema.list = parseMovieArrayBySeat(jsonArray);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(object.has("daysBill")){
			JSONObject jsonObject = object.getJSONObject("daysBill");
			cinemaPrepareMovieCinema.byBill = parseMovieArrayByBill(jsonObject);
		}
		if(object.has("result")){
			JSONObject jsonObject = object.getJSONObject("result");
			cinemaPrepareMovieCinema.result = parseResultObject(jsonObject);
		}
		if(object.has("isShow")){
			String isShow = object.optString("isShow");
			cinemaPrepareMovieCinema.isShow = isShow;
		}
		
		return cinemaPrepareMovieCinema;
	}
	
	public ArrayList<BySeat> parseMovieArrayBySeat(JSONArray jsonArray) throws JSONException{
		if(jsonArray==null||jsonArray.length()==0){
			return null;
		}
		ArrayList<BySeat> seatList = new ArrayList<BySeat>();
		int length = jsonArray.length();
		for(int i=0;i<length;i++){
			BySeat bySeat = parseInnerObjectBySeat(jsonArray.getJSONObject(i));
			
			if(bySeat!=null){
				seatList.add(bySeat);
			}
		}

		return seatList;
 	}
	
	public BySeat parseInnerObjectBySeat(JSONObject jsonObject)throws JSONException{
		if(jsonObject==null||jsonObject.length()==0){
		return null;
	}
	BySeat bySeat = new BySeat();
	
	if(jsonObject.has("day_time")){
		bySeat.day_time = jsonObject.getString("day_time");
	}
	if(jsonObject.has("movieSetName")){
		bySeat.movieSetName = jsonObject.getString("movieSetName");
	}
	if(jsonObject.has("showCode")){
		bySeat.showCode = jsonObject.getString("showCode");
	}
	if(jsonObject.has("room")){
		bySeat.room = jsonObject.getString("room");
	}
	if(jsonObject.has("s_time")){
		bySeat.s_time = jsonObject.getString("s_time");
	}
	if(jsonObject.has("price")){
		bySeat.price = jsonObject.getString("price");
	}
	if(jsonObject.has("language")){
		bySeat.language = jsonObject.getString("language");
	}
	if(jsonObject.has("type")){
		bySeat.type = jsonObject.getString("type");
	}
	if(jsonObject.has("cinemaPrice")){
		bySeat.cinemaPrice = jsonObject.getString("cinemaPrice");
	}
	return bySeat;
		
	}
	
	public ByBill parseMovieArrayByBill(JSONObject jsonObject) throws JSONException{
//		if(jsonArray==null||jsonArray.length()==0){
//			return null;
//		}
		if(jsonObject==null||jsonObject.length()==0){
			return null;
		}
		ByBill byBill = new ByBill();
		
		if(jsonObject.has("day_time")){
			byBill.day_time = jsonObject.getString("day_time");
		}
		if(jsonObject.has("s_time")){
			byBill.s_time = jsonObject.getString("s_time");
		}
		return byBill;
 	}
}
