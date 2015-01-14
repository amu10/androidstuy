package com.szcmcc.movie.network;

import java.io.IOException;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.content.Context;

import com.szcmcc.movie.bean.CommentInfo;
import com.szcmcc.movie.bean.PrefessionInfo;

public class MovieCommentLib {

	private static MovieCommentLib movieCommentLib;
	private MovieRequest movieRequest;
	private MovieCommentParse movieCommentParse;
	private MoviePrefessionParse moviePrefessionParse;
	
	private MovieCommentLib(Context context) {
		movieRequest = new MovieRequest(context);
		movieCommentParse = new MovieCommentParse(context);
		moviePrefessionParse = new MoviePrefessionParse(context);
	}

	public synchronized static MovieCommentLib getInstance(Context context) {
		if (movieCommentLib == null) {
			movieCommentLib = new MovieCommentLib(context);
		}
		return movieCommentLib;
	}
	
	public CommentInfo getCommentsByMovieId(String m_id,String max_id,String page_size,String upcomming) throws HttpException, IOException, JSONException{
		String json = movieRequest.getCommentsByMovieIdRequest(m_id,max_id,page_size,upcomming);
		System.out.println("comment_json----     "+json);
		return movieCommentParse.parseMovieInfo(json);
	}
	
	public PrefessionInfo getProfessionCommentsById(String m_id,String max_id,String page_size,String upcomming) throws HttpException, IOException, JSONException{
		String json = movieRequest.getProfessionCommentsByIdRequest(m_id,max_id,page_size,upcomming);
		System.out.println("getProfessionCommentsById_json----     "+json);
		return moviePrefessionParse.parseMovieInfo(json);
	}
}
