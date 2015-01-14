package com.szcmcc.movie.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ZSQFilmDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 511301734738614944L;
	private String m_id;
	private String name;
	private String rate;
	private String comment_count;
	private String director;
	private String main_actor;
	private String type;
	private String release_date;
	private String country;
	private String introduce;
	private String cover_image_url;
	private String trailersAndroid;
	private String trailersIphone;
	private String upcomming;
	private String client_placard_url1;
	private String client_placard_url2;
	private String client_placard_url3;
	private String client_placard_url4;
	private ArrayList<MovieTidbits> client_tidbits_url;
	private String phone_addr1;
	private String phone_addr2;

	public ArrayList<MovieTidbits> getClient_tidbits_url() {
		return client_tidbits_url;
	}

	public void setClient_tidbits_url(ArrayList<MovieTidbits> client_tidbits_url) {
		this.client_tidbits_url = client_tidbits_url;
	}

	public String getM_id() {
		return m_id;
	}

	public void setM_id(String m_id) {
		this.m_id = m_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getComment_count() {
		return comment_count;
	}

	public void setComment_count(String comment_count) {
		this.comment_count = comment_count;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getMain_actor() {
		return main_actor;
	}

	public void setMain_actor(String main_actor) {
		this.main_actor = main_actor;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getCover_image_url() {
		return cover_image_url;
	}

	public void setCover_image_url(String cover_image_url) {
		this.cover_image_url = cover_image_url;
	}

	public String getTrailersAndroid() {
		return trailersAndroid;
	}

	public String getTrailersIphone() {
		return trailersIphone;
	}

	public void setTrailersIphone(String trailersIphone) {
		this.trailersIphone = trailersIphone;
	}

	public void setTrailersAndroid(String trailersAndroid) {
		this.trailersAndroid = trailersAndroid;
	}

	public String getUpcomming() {
		return upcomming;
	}

	public void setUpcomming(String upcomming) {
		this.upcomming = upcomming;
	}

	public String getClient_placard_url1() {
		return client_placard_url1;
	}

	public void setClient_placard_url1(String client_placard_url1) {
		this.client_placard_url1 = client_placard_url1;
	}

	public String getClient_placard_url2() {
		return client_placard_url2;
	}

	public void setClient_placard_url2(String client_placard_url2) {
		this.client_placard_url2 = client_placard_url2;
	}

	public String getClient_placard_url3() {
		return client_placard_url3;
	}

	public void setClient_placard_url3(String client_placard_url3) {
		this.client_placard_url3 = client_placard_url3;
	}

	public String getClient_placard_url4() {
		return client_placard_url4;
	}

	public void setClient_placard_url4(String client_placard_url4) {
		this.client_placard_url4 = client_placard_url4;
	}

	public String getPhone_addr1() {
		return phone_addr1;
	}

	public void setPhone_addr1(String phone_addr1) {
		this.phone_addr1 = phone_addr1;
	}

	public String getPhone_addr2() {
		return phone_addr2;
	}

	public void setPhone_addr2(String phone_addr2) {
		this.phone_addr2 = phone_addr2;
	}
}
