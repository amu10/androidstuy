package com.szcmcc.movie.picbrowse;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class StringUtil {
	
	public static String encode(String param, String encode){
		String ecdParam;
		try {
			ecdParam = URLEncoder.encode(param, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			ecdParam = param;
		}
		return ecdParam;
	}
	
	public static String decode(String param, String encode){
		String ecdParam;
		try {
			ecdParam = URLDecoder.decode(param,encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			ecdParam = param;
		}
		return ecdParam;
	}
	
	public static String getImgUrlBySize(String url,int size){
		try{
			String preUrl = url.substring(0, url.lastIndexOf("_"));
			String extName = url.substring(url.lastIndexOf("."));
			return preUrl+"_"+size+extName;
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		
	}
	
	public static int getImgSizeByUrl(String url){
		String retSize = url.substring(url.lastIndexOf("_")+1,url.lastIndexOf("."));
		int size = 0;
		try{
			size = Integer.parseInt(retSize);
		}catch(Exception e){
			e.printStackTrace();
		}
		return size;
	}

	public static String subString(String target ,int i) {
		int len = target.length();
		if(len>i){
			target = target.substring(0, i); 
		}
		return target;
	}

	public static int paseInt(String photoId) {
		try {
			return Integer.parseInt(photoId);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return 1;
		
	}

	public static String getExtName(String imagePath) {
		String extName = null;
		try{
			extName = imagePath.substring(imagePath.lastIndexOf('.')+1);
		}catch(Exception e){
		}
		return extName;
	}

	public static String getImgUrlBySizeAddExt(String url, int size) {
		try{
			String preUrl = url.substring(0, url.lastIndexOf("."));
			String extName = url.substring(url.lastIndexOf("."));
			return preUrl+"_"+size+extName;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static String getImgUrl(String url) {
		try{
			String preUrl = url.substring(0, url.lastIndexOf("_"));
			String extName = url.substring(url.lastIndexOf("."));
			return preUrl+extName;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static boolean parseBoolean(String flowOpen) {
		try{
			return Boolean.parseBoolean(flowOpen);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public static String getKey(String keyword) {
		try{
			String key = keyword.substring(keyword.indexOf("#")+1,keyword.lastIndexOf("#"));
			return key;
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
}
