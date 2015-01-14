package com.szcmcc.movie.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.Comment;
import com.szcmcc.movie.bean.PrefessionInfo;
import com.szcmcc.movie.bean.Profession;
import com.szcmcc.movie.bean.Result;

public class MoviePrefessionParse {
	private Context mContext;

	public MoviePrefessionParse(Context context) {
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
	
	public PrefessionInfo parseMovieInfo(String json) throws JSONException{
		PrefessionInfo prefessionInfo = new PrefessionInfo();
		JSONObject object = new JSONObject(json);
		if(object.has("professions")){
			JSONArray jsonArray = object.getJSONArray("professions");
			prefessionInfo.professions = parseMovieProfessionArray(jsonArray);
		}
		
		if(object.has("total")){
			prefessionInfo.total = object.getString("total");
		}
//		if(object.has("page_size")){
//			prefessionInfo.page_size = object.getString("page_size");
//		}
//		if(object.has("curPage")){
//			prefessionInfo.curPage = object.getString("curPage");
//		}
		if(object.has("result")){
			JSONObject jsonObject = object.getJSONObject("result");
			prefessionInfo.result = parseResultObject(jsonObject);
		}
		return prefessionInfo;
	}
	
	
	
	public ArrayList<Profession> parseMovieProfessionArray(JSONArray jsonArray) throws JSONException{
		if(jsonArray==null||jsonArray.length()==0){
			return null;
		}
		ArrayList<Profession> professionList = new ArrayList<Profession>();
		int length = jsonArray.length();
		for(int i=0;i<length;i++){
			Profession profession = parseMovieProfessionObject(jsonArray.getJSONObject(i));
			if(profession!=null){
				professionList.add(profession);
			}
		}
		return professionList;
 	}
	
	public Profession parseMovieProfessionObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		Profession profession = new Profession();
		
		if(jsonObject.has("content")){
			profession.content = jsonObject.getString("content");
		}
		if(jsonObject.has("author")){
			profession.author = jsonObject.getString("author");
		}
		return profession;
	}
}
