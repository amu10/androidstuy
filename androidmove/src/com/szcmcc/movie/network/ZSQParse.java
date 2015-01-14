package com.szcmcc.movie.network;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.bean.MovieInfo;
import com.szcmcc.movie.bean.ZSQBaseBean;
import com.szcmcc.movie.bean.ZSQClassicsPersonBean;
import com.szcmcc.movie.bean.ZSQClassicsPersonDetailBean;
import com.szcmcc.movie.bean.ZSQClassicsWordsBean;
import com.szcmcc.movie.bean.ZSQCouponInfosBean;
import com.szcmcc.movie.bean.ZSQMovieNewsBean;
import com.szcmcc.movie.bean.ZSQNewsDetailBean;
import com.szcmcc.movie.bean.ZSQRank;
import com.szcmcc.movie.bean.ZSQWonderfulCommentBean;
import com.szcmcc.movie.bean.ZSQWonderfulCommentDetailBean;

public class ZSQParse {

	/**
	 * 电影内容解析
	 * 
	 * @param str
	 * @return
	 */
	public MovieInfo parseMovieInfo(String str) {
		Type type = new TypeToken<MovieInfo>() {
		}.getType();
		MovieInfo bean = new MovieInfo();
		bean = new Gson().fromJson(str, type);
		return bean;
	}

	/**
	 * 演员角色详情解析
	 * 
	 * @author zhangsiqi
	 * 
	 */
	public ZSQBaseBean<ZSQClassicsPersonDetailBean> parseClassicsPersonDetail(String str) {
		Type type = new TypeToken<ZSQBaseBean<ZSQClassicsPersonDetailBean>>() {
		}.getType();
		ZSQBaseBean<ZSQClassicsPersonDetailBean> bean = new ZSQBaseBean<ZSQClassicsPersonDetailBean>();
		bean = new Gson().fromJson(str, type);
		return bean;
	}

	/**
	 * 票房解析
	 * 
	 * @param str
	 * @return
	 */
	public ZSQBaseBean<ZSQRank> parseMovieRanking(String str) {
		Type type = new TypeToken<ZSQBaseBean<ZSQRank>>() {
		}.getType();
		ZSQBaseBean<ZSQRank> bean = new ZSQBaseBean<ZSQRank>();
		bean = new Gson().fromJson(str, type);
		return bean;
	}

	/**
	 * 头条新闻或电影资讯解析
	 * 
	 * @param str
	 * @return
	 */
	public ZSQBaseBean<ZSQMovieNewsBean> parseMovieNews(String str) {
		Type type = new TypeToken<ZSQBaseBean<ZSQMovieNewsBean>>() {
		}.getType();
		ZSQBaseBean<ZSQMovieNewsBean> bean = new ZSQBaseBean<ZSQMovieNewsBean>();
		bean = new Gson().fromJson(str, type);
		return bean;
	}

	/**
	 * 头条新闻详情解析
	 * 
	 * @param str
	 * @return
	 */
	public ZSQBaseBean<ZSQNewsDetailBean> parseNewsDetail(String str) {
		Type type = new TypeToken<ZSQBaseBean<ZSQNewsDetailBean>>() {
		}.getType();
		ZSQBaseBean<ZSQNewsDetailBean> bean = new ZSQBaseBean<ZSQNewsDetailBean>();
		bean = new Gson().fromJson(str, type);
		return bean;
	}

	/**
	 * 抵扣券解析
	 * 
	 * @param str
	 * @return
	 */
	public ZSQBaseBean<ZSQCouponInfosBean> parseCouponInfos(String str) {
		Type type = new TypeToken<ZSQBaseBean<ZSQCouponInfosBean>>() {
		}.getType();
		ZSQBaseBean<ZSQCouponInfosBean> bean = new ZSQBaseBean<ZSQCouponInfosBean>();
		bean = new Gson().fromJson(str, type);
		return bean;
	}

	/**
	 * 精彩影评详情解析
	 * 
	 * @param str
	 * @return
	 */
	public ZSQBaseBean<ZSQWonderfulCommentDetailBean> parseWonderfulCommentDetail(String str) {
		Type type = new TypeToken<ZSQBaseBean<ZSQWonderfulCommentDetailBean>>() {
		}.getType();
		ZSQBaseBean<ZSQWonderfulCommentDetailBean> bean = new ZSQBaseBean<ZSQWonderfulCommentDetailBean>();
		bean = new Gson().fromJson(str, type);
		return bean;
	}

	/**
	 * 精彩影评解析
	 * 
	 * @param str
	 * @return
	 */
	public ZSQBaseBean<ZSQWonderfulCommentBean> parseWonderfulComment(String str) {
		Type type = new TypeToken<ZSQBaseBean<ZSQWonderfulCommentBean>>() {
		}.getType();
		ZSQBaseBean<ZSQWonderfulCommentBean> bean = new ZSQBaseBean<ZSQWonderfulCommentBean>();
		bean = new Gson().fromJson(str, type);
		return bean;
	}

	/**
	 * 经典人物解析
	 * 
	 * @param str
	 * @return
	 */
	public ZSQBaseBean<ZSQClassicsPersonBean> parseClassicsPerson(String str) {
		Type type = new TypeToken<ZSQBaseBean<ZSQClassicsPersonBean>>() {
		}.getType();
		ZSQBaseBean<ZSQClassicsPersonBean> bean = new ZSQBaseBean<ZSQClassicsPersonBean>();
		bean = new Gson().fromJson(str, type);
		return bean;
	}

	public ZSQBaseBean<ZSQClassicsWordsBean> parseClassicsWords(String str) {
		Type type = new TypeToken<ZSQBaseBean<ZSQClassicsWordsBean>>() {
		}.getType();
		ZSQBaseBean<ZSQClassicsWordsBean> bean = new ZSQBaseBean<ZSQClassicsWordsBean>();
		bean = new Gson().fromJson(str, type);
		return bean;
	}
}
