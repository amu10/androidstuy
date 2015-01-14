package com.szcmcc.movie.bean;

import java.util.ArrayList;

public class TicketExchangeList {

	public ArrayList<TicketExchange> mTicketExchanges = new ArrayList<TicketExchange>();

	public void release() {
		mTicketExchanges.clear();
	}

	public void addTicketExchange(TicketExchange mTicketExchange) {
		mTicketExchanges.add(mTicketExchange);
	}
}
