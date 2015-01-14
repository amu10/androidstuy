package com.szcmcc.movie.bean;

import java.io.Serializable;

public class ZSQClassicsPersonBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6957028561751258750L;
	private int personId;
	private String personName;
	private String posterAddr;

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPosterAddr() {
		return posterAddr;
	}

	public void setPosterAddr(String posterAddr) {
		this.posterAddr = posterAddr;
	}

}
