package com.szcmcc.movie.bean;

import java.io.Serializable;
import java.util.List;

public class ZSQBaseBean<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5174326337485936347L;
	private String code;
	private String message;
	private List<T> rank;
	private String curPage;
	private String pageSize;
	private String pageCount;
	private String type;
	private List<T> headlineNews;
	private List<T> comments;
	private List<T> classicsPersons;
	private List<T> classicsWords;
	private List<T> coupons;
	private ZSQClassicsPersonDetailBean classicsPerson;
	private ZSQNewsDetailBean newsInfo;
	private ZSQWonderfulCommentDetailBean comment;

	public List<T> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<T> coupons) {
		this.coupons = coupons;
	}

	public ZSQWonderfulCommentDetailBean getComment() {
		return comment;
	}

	public void setComment(ZSQWonderfulCommentDetailBean comment) {
		this.comment = comment;
	}

	public ZSQNewsDetailBean getNewsInfo() {
		return newsInfo;
	}

	public void setNewsInfo(ZSQNewsDetailBean newsInfo) {
		this.newsInfo = newsInfo;
	}

	public ZSQClassicsPersonDetailBean getClassicsPerson() {
		return classicsPerson;
	}

	public void setClassicsPerson(ZSQClassicsPersonDetailBean classicsPerson) {
		this.classicsPerson = classicsPerson;
	}

	public List<T> getClassicsWords() {
		return classicsWords;
	}

	public void setClassicsWords(List<T> classicsWords) {
		this.classicsWords = classicsWords;
	}

	public List<T> getClassicsPersons() {
		return classicsPersons;
	}

	public void setClassicsPersons(List<T> classicsPersons) {
		this.classicsPersons = classicsPersons;
	}

	public List<T> getComments() {
		return comments;
	}

	public void setComments(List<T> comments) {
		this.comments = comments;
	}

	public String getCurPage() {
		return curPage;
	}

	public void setCurPage(String curPage) {
		this.curPage = curPage;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<T> getHeadlineNews() {
		return headlineNews;
	}

	public void setHeadlineNews(List<T> headlineNews) {
		this.headlineNews = headlineNews;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<T> getRank() {
		return rank;
	}

	public void setRank(List<T> rank) {
		this.rank = rank;
	}

}
