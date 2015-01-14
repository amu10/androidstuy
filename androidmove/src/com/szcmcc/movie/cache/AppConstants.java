package com.szcmcc.movie.cache;

import android.os.Environment;

import com.mapabc.mapapi.core.GeoPoint;
import com.szcmcc.movie.bean.MovieCinemaList;
import com.szcmcc.movie.bean.MovieInfo;

/**
 * 全局变量
 * @author Administrator
 *
 */
public class AppConstants {

	public static final String saveObject="date.ser";
	public static final String CURRVERSION="1";//当前版本号
	public static final String path_webview=Environment.getExternalStorageDirectory().getAbsolutePath()+"/movie/webview.jpg";
	public static final String imageCachePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/movie/imageCache/";
	public static String imageCachePath_data=Environment.getDownloadCacheDirectory().getAbsolutePath()+"/movie/imageCache/";
	public static int screenWidth=320;
	public static int maxImageCount = 500; 
	public static GeoPoint mGeoPoint = null;
	public static MovieCinemaList movieCinema = null;
	public static String mapOrListStatus = "";
	public static MovieInfo movieInfo = null;
//	public static MovieCinemaList movieCinemaList = null;
	
	//----影院可订座影院的影院ID
	/** 博纳*/
	public static final int BONA_CINEMA = 1; 

	/** 深圳市海岸影城有限公司*/
	public static final int HAIAN_CINEMA = 2; 
	
	/** 中影南国影城*/
	public static final int ZHONGYINGNANGUO_CINAME = 4; 
	
	/** 深圳市中影益田影城有限公司 */
	public static final int ZHONGYINGYITIAN_CINEMA = 6; 
	
	/** 百老汇影院*/
	public static final int BAILAOHUI_CINAME = 7;
	
	/** 保利*/
	public static final int BAOLI_CINEMA = 8; 
	
	/** 太平洋电影城京基店(南山区)*/
	public static final int TAIPINGYANG_JINGJI_CINEMA = 12; 
	
	/** 百誉世贸大浪影城(宝安区龙华店)*/
	public static final int BAIYU_DALANG_CINEMA = 13; 
	
	/** 太平洋电影城东海店(福田区)*/
	public static final int TAIPINGYANG_DONGHAI_CINEMA = 15;
	
	/** 太平洋电影城天利店(南山区)*/
	public static final int TAIPINGYANG_TIANLI_CINEMA = 16;
	
	/** 橙天嘉禾影城（中国）有限公司*/
	public static final int CHENGTIAN_JIAHE_CINEMA = 17;
	
	/** 华夏太古国际影城(宝安区)*/
	public static final int TAIGU_CINEMA = 18; 
	
	/** 百誉世贸国际影城(光明新区公明店) */
	public static final int BAIYU_GUANGMING_CINEMA = 19; 
	
	/** 环星影城(宝安区)*/
	public static final int HUANXING_CINEMA = 22; 
	
	/** 雅图大新分店*/
	public static final int YATU_DAXIN_CINEMA = 31;
	
	/** 雅图松坪山分店*/
	public static final int YATU_SONGPINGSHAN_CINEMA = 32;
	
	/** 雅图梅林店*/
	public static final int YATU_MEILIN_CINEMA = 33;
	
	/** 雅图白石洲分店*/
	public static final int YATU_BAISHIZHOU_CINEMA = 34;
	
	/** 雅图西丽店*/
	public static final int YATU_XILI_CINEMA = 35;
	
	/** 雅图蛇口分店*/
	public static final int YATU_SHEKOU_CINEMA = 36;
	
	/** 今典-西乡*/
	public static final int JINDIAN_XIXIANG_CINEMA = 39; 
	
	/** 今典-龙华*/
	public static final int JINDIAN_LONGHUA_CINEMA = 40;  
	
	/** 今典-沙井*/
	public static final int JINDIAN_SHAJING_CINEMA = 41; 

	/** 今典-龙岗（龙岗摩尔影城）*/
	public static final int JINDIAN_LONGGANG_CINEMA = 42; 
	
	/** 横店*/
	public static final int HENGDIAN = 43;
	
	/** 金域世纪*/
	public static final int JINYUSHIJI = 44;
	
	/** 金域新百花*/
	public static final int JINYUXINBAIHUA = 45;
	
	/** 南国艺恒国际影城（沙井店）*/
	public static final int NANGUOYIHENGGUOJI_SHAJING_CINAME = 52;
	
	/** 南国艺恒国际影城（坂田店）*/
	public static final int NANGUOYIHENGGUOJI_BANTIAN_CINAME = 53;
	
	/** 中影国际IMAX影城*/
	public static final int ZHONGYINGGUOJI_IMAX_CINEMA = 55;
	
	/** 博纳龙岗店*/
	public static final int BONA_LONGGANG_CINEMA = 60; 
}
