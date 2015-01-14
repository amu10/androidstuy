package com.szcmcc.movie.storage;

import com.szcmcc.movie.MovieApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {

	private static Editor saveEditor;

	private static SharedPreferences saveInfo;

	private static final String TAG = "CmccompSharedPreferences";

	private static final String UID = "UID";

	private static SharedPreferencesUtil spUtil = new SharedPreferencesUtil();
	private static Context mContext;
	
	private  final String USER_NAME="userName";
	private  final String PASSWORD="password";
	private  final String IS_SAVE_PASSWORD="isSavePassword";
	private  final String WEIBO_139_KEY="weibo139key";
	private  final String WEIBO_SINA_KEY="weibosinakey";
	public static SharedPreferencesUtil getInstance(Context context){
		mContext = context;
		if(saveInfo  == null&&mContext != null){
			saveInfo = mContext.getSharedPreferences("cmcc_jiaotongbobao",
					Context.MODE_PRIVATE);
			saveEditor = saveInfo.edit();
		}
		return spUtil;
	}
	private SharedPreferencesUtil(){
		if(mContext != null){
		saveInfo = mContext.getSharedPreferences("cmcc_jiaotongbobao",
				Context.MODE_PRIVATE);
		saveEditor = saveInfo.edit();
		}
	}


	public void saveLoginType(String loginType){
		saveEditor.putString("loginType", loginType);
		saveEditor.commit();
	}
	public String getLoginType(){
		return saveInfo.getString("loginType", "");
	}

	public  String getString(String key) {
		return saveInfo.getString(key, "");
	}

	protected  void setString(String key,String value) {
		saveEditor.putString(key, value);
		saveEditor.commit();
	}
	
	/**
	 * 保存用户名密码
	 * @param userName
	 * @param password
	 */
	public  void savePassword(String userName,String password){
		saveEditor.putString(USER_NAME, userName);
		saveEditor.putString(PASSWORD, password);
		saveEditor.commit();
	}
	public void saveUid(String uid,String token){
		saveEditor.putString("uid", uid);
		saveEditor.putString("token", token);
		saveEditor.commit();
	}
	public  String [] getUid(){
		String uid = saveInfo.getString("uid", "");
		String token = saveInfo.getString("token", "");
		return new String[]{uid,token};
	}
	
	/**
	 * 获得用户名密码
	 * @return
	 */
	public  String [] getUserPassword(){
		String name = saveInfo.getString(USER_NAME, "");
		String password = saveInfo.getString(PASSWORD, "");
		return new String[]{name,password};
	}
	/**
	 * 清除或不记录密码
	 * 
	 */
	public  void clearPassword(){
		saveEditor.remove(PASSWORD);
		saveEditor.commit();
	}
	public  void clearNameAndPassword(){
		saveEditor.remove(USER_NAME);
		saveEditor.remove(PASSWORD);
		saveEditor.commit();
	}
	
	/**
	 * 是否保持密码
	 * @return
	 */
	public  boolean isSavePassword(){
		
		return saveInfo.getBoolean(IS_SAVE_PASSWORD, false);
	}
	/**
	 * 设置是否保持用户名密码
	 * @param b
	 */
	public  void setSavePassword(boolean b){
		saveEditor.putBoolean(IS_SAVE_PASSWORD, b);
		saveEditor.commit();
	}
	/**
	 * 设置是否保持用户名密码
	 * @param b
	 */
	public  void setSaveLoginInfo(boolean b,String userName,String password,String uid){
		saveEditor.putBoolean(IS_SAVE_PASSWORD, b);
		saveEditor.putString(USER_NAME, userName);
		saveEditor.putString(UID, uid);
		if(b){
			saveEditor.putString(PASSWORD, password);
		}else{
			saveEditor.remove(PASSWORD);
		}
		saveEditor.commit();
	}
	

	/**
	 * 清空数据初始化
	 */
	public  void clearAllData() {
	    if(saveEditor!=null){
		saveEditor.clear().commit();
		}
	}
	public String getUserName() {

		return saveInfo.getString(USER_NAME, "");
	}
	public String getPassword() {

		return saveInfo.getString(PASSWORD, "");
	}
	
	public boolean isWeibo139Binded(){
		return saveInfo.getBoolean("weibo139binded", false);
	}
	public boolean isWeiboSinabinded(){
		return saveInfo.getBoolean("weibosinabinded", false);
	}
	public void setWeibo139Binded(boolean binded){
		saveEditor.putBoolean("weibo139binded", binded);
		saveEditor.commit();
	}
	public void setWeiboSinaBinded(boolean binded){
		saveEditor.putBoolean("weibosinabinded", binded);
		saveEditor.commit();
	}
	
	public void saveWeibo139Key(String key){
		saveEditor.putString(WEIBO_139_KEY, key);
		saveEditor.commit();
	}
	public void saveWeiboSinaKey(String key){
		saveEditor.putString(WEIBO_SINA_KEY, key);
		saveEditor.commit();
	}
	public boolean isReportIllegalTipShowed(){
		return saveInfo.getBoolean("reportillegaltipshowed", false);
	}
	public void setReportIllegalTipShowed(boolean showed){
		saveEditor.putBoolean("reportillegaltipshowed", showed);
		saveEditor.commit();
	}
	
	public String getLast_pass_review_time(){
		return saveInfo.getString("last_pass_review_time", "2011-11-15");
	}
	
	public void setLast_pass_review_time(String last_pass_review_time){
		saveEditor.putString("last_pass_review_time", last_pass_review_time);
		saveEditor.commit();
	}
	
	/**
	 * 获得违章车牌号查询的json数据
	 * @return
	 */
	public String getIllegalCarNoJson() {
		// TODO Auto-generated method stub
		String illegalCarNoJsonStr = saveInfo.getString("illegal_car_no_json", "");
		return illegalCarNoJsonStr;
	}
	/**
	 * 设置存储违章车牌号的Json数据
	 * @param illegalCarNoJsonStr
	 */
	public void setillegalCarNoJson(String illegalCarNoJsonStr) {
		// TODO Auto-generated method stub
//		saveEditor.remove("illegal_car_no_json");
		saveEditor.putString("illegal_car_no_json", illegalCarNoJsonStr);
		saveEditor.commit();
	}
	/**
	 * 获得违章驾驶证号查询的json数据
	 * @return
	 */
	public String getIllegalCarLicenseJson() {
		// TODO Auto-generated method stub
		return saveInfo.getString("illegal_car_License_json", "");
	}
	/**
	 * 设置存储违章驾驶证号的Json数据
	 * @param illegalCarNoJsonStr
	 */
	public void setillegalCarLicenseJson(String illegalCarLicenseJsonStr) {
		// TODO Auto-generated method stub
		saveEditor.putString("illegal_car_License_json", illegalCarLicenseJsonStr);
		saveEditor.commit();
	}
	/**
	 * 保存用户的JSON数据
	 * @param dataObj
	 */
	public void saveUserJson(String dataObj) {
		// TODO Auto-generated method stub
		saveEditor.putString("UserJson", dataObj);
		saveEditor.commit();
	}
	/**
	 * 获得用户的JSON数据
	 * @return
	 */
	public String getsaveUserJson() {
		// TODO Auto-generated method stub
		return saveInfo.getString("UserJson", "");
	}
	/**
	 * 设置QQkey
	 * @param oauth_token
	 */
	public void setQQToken(String oauth_token) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 设置新浪的key
	 * @param spUtilShareSinaToken
	 * @param token
	 */
	public void setSinaToken(String value){
		saveEditor.putString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN, value);
		saveEditor.commit();
	}
	public String getSinaToken() {
		// TODO Auto-generated method stub
		return saveInfo.getString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN, "0");
	}
	/**
	 * 设置新浪的密码key
	 * @param key
	 * @param value
	 * @return
	 */
	public void setSinaTokenSecret(String value){
		saveEditor.putString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN_SECRET, value);
		saveEditor.commit();
	}
	public String getSinaTokenSecret() {
		// TODO Auto-generated method stub
		return saveInfo.getString(MovieApplication.SP_UTIL_SHARE_SINA_TOKEN_SECRET, "0");
	}
	public void cleanSinaToken(String key){
		saveEditor.remove(key);
		saveEditor.commit();
	}
	
	public String getString(String key, String defaultValue) {
		// TODO Auto-generated method stub
		return saveInfo.getString(key, defaultValue);
	}
	
	
	
	public boolean putString(String key,String value){
		saveEditor.putString(key, value);
		return saveEditor.commit();
	}
	
	
	/**
	  * 保存wifi状态下的网络流量
	  * @param length
	  */
	 public void saveWIFI(int length) {
	  saveEditor.putInt("WIFI", length);
	  saveEditor.commit();
	 }
	 
	 /**
	  * 获取wifi状态下的网络流量
	  * @return
	  */
	 public int getWIFI() {
	  return saveInfo.getInt("WIFI", 0);
	 }
	 
	 /**
	  * 保存2G状态下的网络流量
	  * @param length
	  */
	 public void save2G(int length) {
	  saveEditor.putInt("2G", length);
	  saveEditor.commit();
	 }
	 
	 /**
	  * 获取wifi状态下的网络流量
	  * @return
	  */
	 public int get2G() {
	  return saveInfo.getInt("2G", 0);
	 }
	 
	 /**
	  * 保存3G状态下的网络流量
	  * @param length
	  */
	 public void save3G(int length) {
	  saveEditor.putInt("3G", length);
	  saveEditor.commit();
	 }
	 
	 /**
	  * 获取wifi状态下的网络流量
	  * @return
	  */
	 public int get3G() {
	  return saveInfo.getInt("3G", 0);
	 }
	 
	 /**
	  * 保存统计开始时间
	  * @param time
	  */
	 public void saveStime(int time) {
	  saveEditor.putInt("stime", time);
	  saveEditor.commit();
	 }
	 
	 /**
	  * 获取统计开始时间
	  * @return
	  */
	 public String getStime() {
	  return saveInfo.getInt("stime", 0)+"";
	 }
	 
	 /**
	  * 保存统计结束时间
	  * @param time
	  */
	 public void saveEtime(int time) {
	  saveEditor.putInt("etime", time);
	  saveEditor.commit();
	 }
	 
	 /**
	  * 获取统计结束时间
	  * @return
	  */
	 public String getEtime() {
	  return saveInfo.getInt("etime", 0)+"";
	 }
	 
	 /**
	  * 提交流量统计数据
	  * @param ispostflowstatistics
	  */
	 public void savePostFlowStatistics(boolean ispostflowstatistics) {
	  saveEditor.putBoolean("ispostflowstatistics", ispostflowstatistics);
	  saveEditor.commit();
	 }
	 
	 /**
	  * 提交流量统计数据
	  * @return
	  */
	 public boolean IsPostFlowStatistics() {
	  return saveInfo.getBoolean("ispostflowstatistics", false);
	 }
}
