package com.szcmcc.movie.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6406570107248751817L;
	public ArrayList<Movie> movies = null;
	public Result result = null;
	public String isShow = "";

	public boolean isSuccess() {
		if (result != null && result.success()) {
			return true;
		}
		return false;
	}

	private ZSQFilmDetails movie;

	public ZSQFilmDetails getMovie() {
		return movie;
	}

	public void setMovie(ZSQFilmDetails movie) {
		this.movie = movie;
	}

	@Override
	public String toString() {
		return "MovieInfo [movies=" + movies + ", result=" + result + "]";
	}

}
