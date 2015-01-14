package com.szcmcc.movie.bean;

import java.io.Serializable;

public class ZSQMovieNewsBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3481326638271159595L;
	private int newsId;
	private String newsTitle;
	private String newsDate;
	private String source;

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(String newsDate) {
		this.newsDate = newsDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
