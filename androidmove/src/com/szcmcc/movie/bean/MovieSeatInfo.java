package com.szcmcc.movie.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 电影座位的信息
 * @author Haylee
 *
 */
public class MovieSeatInfo  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8911598651066767533L;
	public Result result;
	public List<SeatInfo> seats;

}
