package com.szcmcc.movie.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.szcmcc.movie.bean.Comment;
import com.szcmcc.movie.bean.CommentInfo;
import com.szcmcc.movie.bean.Profession;
import com.szcmcc.movie.bean.Result;

public class MovieCommentParse {
	private Context mContext;

	public MovieCommentParse(Context context) {
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
	
	public CommentInfo parseMovieInfo(String json) throws JSONException{
		CommentInfo commentInfo = new CommentInfo();
		JSONObject object = new JSONObject(json);
		if(object.has("comments")){
			JSONArray jsonArray = object.getJSONArray("comments");
			commentInfo.comments = parseMovieArray(jsonArray);
		}
		if(object.has("professions")){
			JSONArray jsonArray = object.getJSONArray("professions");
			commentInfo.professions = parseMovieProfessionArray(jsonArray);
		}
		
		if(object.has("total")){
			commentInfo.total = object.getString("total");
		}
		if(object.has("page_size")){
			commentInfo.page_size = object.getString("page_size");
		}
		if(object.has("rate")){
			commentInfo.rate = object.getString("rate");
		}
		if(object.has("result")){
			JSONObject jsonObject = object.getJSONObject("result");
			commentInfo.result = parseResultObject(jsonObject);
		}
		return commentInfo;
	}
	
	public ArrayList<Comment> parseMovieArray(JSONArray jsonArray) throws JSONException{
		if(jsonArray==null||jsonArray.length()==0){
			return null;
		}
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		int length = jsonArray.length();
		for(int i=0;i<length;i++){
			Comment comment = parseMovieObject(jsonArray.getJSONObject(i));
			if(comment!=null){
				commentList.add(comment);
			}
		}
		return commentList;
 	}
	
	public Comment parseMovieObject(JSONObject jsonObject) throws JSONException{
		if(jsonObject == null){
			return null;
		}
		Comment comment = new Comment();
		
		if(jsonObject.has("c_id")){
			comment.c_id = jsonObject.getString("c_id");
		}
		if(jsonObject.has("content")){
			comment.content = jsonObject.getString("content");
		}
		if(jsonObject.has("comment_time")){
			comment.comment_time = jsonObject.getString("comment_time");
		}
		if(jsonObject.has("nickname")){
			comment.nickname = jsonObject.getString("nickname");
		}
		if(jsonObject.has("head_image")){
			comment.head_image = jsonObject.getString("head_image");
		}
		return comment;
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
