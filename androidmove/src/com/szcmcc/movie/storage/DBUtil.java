package com.szcmcc.movie.storage;

import android.content.Context;
import android.net.Uri;

public class DBUtil extends BaseDBUtil{
	/**
	 * Define some common Content URI.
	 */

	public final static Uri CLEAN_DB_URI = build(MovieContract.CLEAN_DB_CONTENT_URI);

	
	
	



	/**
	 * -------------------------------------------------------
	 * -------------------------LoginUser---------------------
	 * -------------------------------------------------------
	 */
	
	
	
	/**
	 * -------------------------------------------------------
	 * -------------------------clean---------------------
	 * -------------------------------------------------------
	 */
	public static int cleanDB(Context context) {
		return context.getContentResolver().delete(CLEAN_DB_URI, null, null);
	}
	
	
}
