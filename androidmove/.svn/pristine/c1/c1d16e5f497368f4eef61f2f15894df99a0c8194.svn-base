package com.szcmcc.movie.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class CinemaPrepareInfo {
	public ArrayList<MovieCinemaPrepareInner> movies = null;

	public Result result = null;
	public String isShow = "";

	public boolean isSuccess() {
		if (result != null && result.success()) {
			return true;
		}
		return false;
	}

	public static class MovieCinemaPrepareInner implements Serializable {
		public ArrayList<CinemaPrepare> daysSeat = null;
		public String m_id = "";
		public String name = "";
		public String rate = "";
		public String comment_count = "";
		public String director = "";
		public String main_actors = "";
		public String type = "";
		public String release_date = "";
		public String country = "";
		public String introduce = "";
		public String cover_image_url = "";
		public String client_placard_url1 = "";
		public String client_placard_url2 = "";
		public String trailersAndroid = "";
		public String upcomming = "";
		public daysBill daysbill = null;
		public ArrayList<MovieTidbits> client_tidbits_url;// 花絮列表

		public static class daysBill implements Serializable {
			public String day_time = "";
			public String s_time = "";

			@Override
			public String toString() {
				return "daysBill [day_time=" + day_time + ", s_time=" + s_time + "]";
			}

		}

		@Override
		public String toString() {
			return "MovieCinemaPrepareInner [daysSeat=" + daysSeat + ", m_id=" + m_id + ", name="
					+ name + ", rate=" + rate + ", comment_count=" + comment_count + ", director="
					+ director + ", main_actors=" + main_actors + ", type=" + type
					+ ", release_date=" + release_date + ", country=" + country + ", introduce="
					+ introduce + ", cover_image_url=" + cover_image_url + ", trailersAndroid="
					+ trailersAndroid + ", daysbill=" + daysbill + "]";
		}

	}
}
