package com.szcmcc.movie.bean;

import java.io.Serializable;

public class ZSQClientTidbitsUrlBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3705583623951271920L;
	private String client_tidbits_url_large;
	private String client_tidbits_url_small;

	public String getClient_tidbits_url_large() {
		return client_tidbits_url_large;
	}

	public void setClient_tidbits_url_large(String client_tidbits_url_large) {
		this.client_tidbits_url_large = client_tidbits_url_large;
	}

	public String getClient_tidbits_url_small() {
		return client_tidbits_url_small;
	}

	public void setClient_tidbits_url_small(String client_tidbits_url_small) {
		this.client_tidbits_url_small = client_tidbits_url_small;
	}

}
