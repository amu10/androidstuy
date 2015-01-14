package com.szcmcc.movie.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.szcmcc.movie.bean.CinemaPrepareMovie;
import com.szcmcc.movie.bean.CinemaPrepareMovie.CinemaPrepareMovieInner;
import com.szcmcc.movie.bean.CinemaPrepareMovie.CinemaPrepareMovieInner.ByBill;
import com.szcmcc.movie.bean.CinemaPrepareMovie.CinemaPrepareMovieInner.BySeat;
import com.szcmcc.movie.bean.Result;
import com.szcmcc.movie.bean.UserBean;

public class MoviePrepareParse {
	private Context mContext;

	public MoviePrepareParse(Context context) {
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
	
	public CinemaPrepareMovie parseMovieCinemaPrepareInfo(String json) throws JSONException{
		if(json.equals("")||json.equals("null")||json == null){
			return null;
		}
		CinemaPrepareMovie cinemaPrepare = new CinemaPrepareMovie();
		JSONObject object = new JSONObject(json);
		if(object.has("cinemas")){
			JSONArray jsonArray = object.getJSONArray("cinemas");
			cinemaPrepare.list = parseMovieArrayInner(jsonArray);
		}
		if(object.has("result")){
			JSONObject jsonObject = object.getJSONObject("result");
			cinemaPrepare.result = parseResultObject(jsonObject);
		}
		if(object.has("isShow")){
			String isShow = object.getString("isShow");
			cinemaPrepare.isShow = isShow;
		}
		return cinemaPrepare;
	}
	
	public ArrayList<CinemaPrepareMovieInner> parseMovieArrayInner(JSONArray jsonArray) throws JSONException{
		if(jsonArray==null||jsonArray.length()==0){
			return null;
		}
		ArrayList<CinemaPrepareMovieInner> cinemaPrepareList = new ArrayList<CinemaPrepareMovieInner>();
		int length = jsonArray.length();
		for(int i=0;i<length;i++){
			CinemaPrepareMovie.CinemaPrepareMovieInner inner = parseInnerMovieCinemaPrepare(jsonArray.getJSONObject(i));
			
			if(inner!=null){
				cinemaPrepareList.add(inner);
			}
		}
		return cinemaPrepareList;
 	}
	public CinemaPrepareMovie.CinemaPrepareMovieInner parseInnerMovieCinemaPrepare(JSONObject jsonObject)throws JSONException{
		if(jsonObject==null||jsonObject.length()==0){
			return null;
		}
		
		CinemaPrepareMovie.CinemaPrepareMovieInner inner = new CinemaPrepareMovie.CinemaPrepareMovieInner();
		if(jsonObject.has("c_id")){
			inner.c_id = jsonObject.getString("c_id");
		}
		
		if(jsonObject.has("daysSeat")){
			try{
			JSONArray jsonArray = jsonObject.getJSONArray("daysSeat");
			inner.list = parseMovieArrayBySeat(jsonArray);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(jsonObject.has("daysBill")){
			JSONObject jsonObject1 = jsonObject.getJSONObject("daysBill");
			inner.byBill = parseMovieArrayByBill(jsonObject1);
		}
		return inner;
		}

	
	
//	public CinemaPrepareMovieCinema parseCinemaPrepareMovieCinemaInfo(String json) throws JSONException{
//		CinemaPrepareMovieCinema cinemaPrepareMovieCinema = new CinemaPrepareMovieCinema();
//		JSONObject object = new JSONObject(json);
//		if(object.has("daysSeat")){
//			try{
//			JSONArray jsonArray = object.getJSONArray("daysSeat");
//			cinemaPrepareMovieCinema.list = parseMovieArrayBySeat(jsonArray);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//		if(object.has("daysBill")){
//			JSONObject jsonObject = object.getJSONObject("daysBill");
//			cinemaPrepareMovieCinema.byBill = parseMovieArrayByBill(jsonObject);
//		}
//		if(object.has("result")){
//			JSONObject jsonObject = object.getJSONObject("result");
//			cinemaPrepareMovieCinema.result = parseResultObject(jsonObject);
//		}
//		return cinemaPrepareMovieCinema;
//	}
	
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
	
	
	
	
	
	
	
	
	public UserBean parseLoginInfo(String jsonStr) throws JSONException {
		// TODO Auto-generated method stub
		if(TextUtils.isEmpty(jsonStr)){
			return null;
		}
		UserBean bean = new UserBean();
		JSONObject object;
		try{
		 object = new JSONObject(jsonStr);
		}catch(JSONException e){
			jsonStr = jsonStr.substring(1);
			object = new JSONObject(jsonStr);
		}
		int result = object.optInt("result");
		bean.result = result;
		if(result == 1){
			//登录成功
			bean.uid = object.optString("uid");
			bean.token = object.optString("token");
			bean.username = object.optString("username");
			bean.mobile = object.optString("mobile");
			bean.record = object.optString("record");
			bean.rank = object.optString("rank");
			bean.traffic_rep_no = object.optString("traffic_rep_no");
			bean.report_weizhang_record = object.optString("report_weizhang_record");
			bean.report_weizhang_num = object.optString("report_weizhang_num");
		}else if(result == 0){
			//请求失败
			/*{"result":0,"error_no":"402","error_msg":"password error"}
			 * /
			 */
			bean.error_no = object.optString("error_no");
			bean.error_msg = object.optString("error_msg");
		}
		return bean;
	}
	/**
	 * 获得动态密码
	 * @param jsonStr
	 * @return
	 * @throws JSONException
	 */
	public String parseAutoPwd(String jsonStr) throws JSONException {
		/*{"result":1,"error_no":200,"error_msg":"\u83b7\u53d6\u6210\u529f"}*/
		JSONObject object;
		try{
			 object = new JSONObject(jsonStr);
		}catch(JSONException e){
			jsonStr = jsonStr.substring(1);
			object = new JSONObject(jsonStr);
	}
		int result = object.optInt("result");
		int errorNO;
		String errorMeg = "";
		if(result == 1){
			//
			errorNO = object.optInt("error_no");
			errorMeg = object.optString("error_msg");
		}else if(result == 0){
			
		}else{
			
		}
		
		return errorMeg;
	}
	
}
