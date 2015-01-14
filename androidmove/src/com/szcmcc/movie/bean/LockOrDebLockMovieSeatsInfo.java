package com.szcmcc.movie.bean;

import java.io.Serializable;
import java.util.List;

public class LockOrDebLockMovieSeatsInfo  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -644626244999254624L;
	public Result result;
	public String c_id;
	public String showCode;
	public String lockedType;
	public String ticketCount;
	public String recyPhone;
	public String lockSerialNo;
	public List<LockOrDebLockMovieSeatInfo> lockSeats;
	public List<MoviePayType> payType;
	@Override
	public String toString() {
		return "LockOrDebLockMovieSeatsInfo [result=" + result + ", c_id="
				+ c_id + ", showCode=" + showCode + ", lockedType="
				+ lockedType + ", ticketCount=" + ticketCount + ", recyPhone="
				+ recyPhone + ", lockSerialNo=" + lockSerialNo + "]";
	}
	
}
