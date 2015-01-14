package com.szcmcc.movie;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import com.szcmcc.movie.activity.ZSQMovieActivity;
import com.szcmcc.movie.network.AsyncImageLoader;

public class MovieApplication extends Application {

	public static final String EXTRA_SHARE_PICTURE = "EXTRA_SHARE_PICTURE"; // 分享时的图片

	public static final String SP_UTIL_SHARE_SINA_TOKEN = "SP_UTIL_SHARE_SINA_TOKEN"; // 新浪的访问token

	public static final String SP_UTIL_SHARE_SINA_TOKEN_SECRET = "SP_UTIL_SHARE_SINA_TOKEN_SECRET"; // 新浪访问的密码

	public static final String EXTRA_SHARE_PICTURE_WEIBO = "EXTRA_SHARE_PICTURE_WEIBO"; // 分享到微博的图片

	public static final String WEIBO_QQ_CALLBACKURL = "WEIBO_QQ_CALLBACKURL";// qq分享的回调URL

	public static final String WEIBO_QQ_KEY = "WEIBO_QQ_KEY"; // qqkey

	public static final String WEIBO_QQ_SECRET = "WEIBO_QQ_SECRET";// qqsecret

	// private CommonsHttpOAuthConsumer sinaOAuthConsumer;
	//
	// private CommonsHttpOAuthProvider sinaOAuthProvider;

	public static final String WEIBO_SINA_KEY = "2168238665";// 正式

	public static final String WEIBO_SINA_SECRET = "ef8b3892881e31e04fbbc85f07f9ff01";

	// public static final String WEIBO_SINA_KEY = "3617345427";
	//
	// public static final String WEIBO_SINA_SECRET =
	// "5e92f2bbce0090f99a7efc54fab7ed66";

	public static final String WEIBO_SINA_CALLBACKURL = "http://szicity.com";

	public static boolean isCanClose = true;

	private AsyncImageLoader asyncImageLoader;
	public AsyncImageLoader getAsyncImageLoader() {
		if (asyncImageLoader == null) {
			asyncImageLoader = new AsyncImageLoader();
		}
		return asyncImageLoader;
	}

	/****************** 绑定到新浪微博 ************************/

	// public CommonsHttpOAuthProvider getSinaOAuthProvider() {
	// if (sinaOAuthProvider == null) {
	// sinaOAuthProvider = new
	// CommonsHttpOAuthProvider("http://api.t.sina.com.cn/oauth/request_token",
	// "http://api.t.sina.com.cn/oauth/access_token",
	// "http://api.t.sina.com.cn/oauth/authorize");
	// }
	// return sinaOAuthProvider;
	// }

	// public CommonsHttpOAuthConsumer getSinaOAuthConsumer() {
	// if (sinaOAuthConsumer == null) {
	// sinaOAuthConsumer = new CommonsHttpOAuthConsumer(WEIBO_SINA_KEY,
	// WEIBO_SINA_SECRET);
	// }
	// return sinaOAuthConsumer;
	// }
	/*** 影讯分类 ***/
	public static String[] YXSortArray = { "票房排行", "头条新闻", "电影资讯", "精彩影评", "经典人物", "经典台词" };
	/*** 入口常量 ***/
	public static String ENTRY_NEWS = "rk_news";// 头条新闻
	public static String ENTRY_MOVIE_INFO = "rk_movieInfo";// 电影资讯
	public static String ENTRY_FILM_REVIEW = "rk_film_review";// 精彩影评
	public static String ENTRY_PLAYS = "rk_plays";// 经典角色
	public static String ENTRY_CLSAAIC_LINES = "rk_classic_lines";// 经典台词
	public static String ENTRY_MY_ZOON = "rk_myZoon";// 我的票务
	/*** 分类常量 ***/
	public static String HOT_MOVIE = "hot_movie";// 热门电影
	public static String WILL_MOVIE = "will_movie";// 即将上映
	/*** list常量 ***/
	public static String DOWN = "down";// 下拉刷新
	public static String UP = "up";// 上拉加载

	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
			return "";
		}
		return versionName;
	}
}
