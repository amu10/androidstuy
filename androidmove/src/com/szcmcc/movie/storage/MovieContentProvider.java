package com.szcmcc.movie.storage;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class MovieContentProvider extends ContentProvider {

	private MovieDB movieDB;

	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/itotem.movie";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/itotem.movie";
	
	private static final int OPERATION = 10;
	private static final int PICTURE_DB = 20;
	private static final int PICTURE_SDCARD = 30;
	private static final int CLEAN_DB = 40;
	private static final int DOWNLOAD_LOG = 50;
	

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		sURIMatcher.addURI(MovieContract.AUTHORITY, MovieContract.OPERATION_CONTENT_URI, OPERATION);
		sURIMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PICTURE_DB_CONTENT_URI, PICTURE_DB);
		sURIMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PICTURE_SDCARD_CONTENT_URI, PICTURE_SDCARD);
		sURIMatcher.addURI(MovieContract.AUTHORITY, MovieContract.CLEAN_DB_CONTENT_URI, CLEAN_DB);
		sURIMatcher.addURI(MovieContract.AUTHORITY, MovieContract.DOWN_LOG_CONTENT_URI, DOWNLOAD_LOG);
		sURIMatcher.addURI(MovieContract.AUTHORITY, MovieContract.CLEAN_DB_CONTENT_URI, CLEAN_DB);
	}

	@Override
	public boolean onCreate() {
		movieDB = new MovieDB(getContext());
		movieDB.open();
		return true;
	}

	@Override
	public String getType(Uri uri) {
		switch (sURIMatcher.match(uri)) {
		case PICTURE_DB:
		case OPERATION:
			return CONTENT_TYPE;
		case PICTURE_SDCARD:
			return CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long result = -1;
		Uri resultUri = null;
		switch (sURIMatcher.match(uri)) {
		case PICTURE_DB:
			result = movieDB.addDBPicture(values);
			resultUri = buildResultUri(MovieContract.PICTURE_DB_CONTENT_URI, result);
			break;
		case PICTURE_SDCARD:
			result = movieDB.addSDPicture(values);
			resultUri = buildResultUri(MovieContract.PICTURE_SDCARD_CONTENT_URI,result);
			break;
		case DOWNLOAD_LOG:
			result = movieDB.addDownLog(values);
			resultUri = buildResultUri(MovieContract.DOWN_LOG_CONTENT_URI,result);
			break;
		
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		if (result == 0 || result == -1) {
			return null;
		}
		getContext().getContentResolver().notifyChange(resultUri, null);
		return resultUri;

	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int result = 0;
		switch (sURIMatcher.match(uri)) {
		case CLEAN_DB:
			result = movieDB.cleanDatabase();
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		if (result > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return result;

	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		switch (sURIMatcher.match(uri)) {
		case PICTURE_DB:
			return movieDB.getDBPicture(selection);
		case PICTURE_SDCARD:
			return movieDB.getSDPicture(selection);
		
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int result = 0;
		switch (sURIMatcher.match(uri)) {
		case PICTURE_DB:
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		if (result > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}

		return result;
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		int result = 0;
		switch (sURIMatcher.match(uri)) {
		case PICTURE_DB:
			break;
//		case ENCOURAGE:
//			gameDB.beginTransaction();
//			for (ContentValues vs : values) {
//				gameDB.addEncourage(vs);
//			}
//			gameDB.setTransactionSuccessful();
//			gameDB.endTransaction();
//			result = values.length;
//			break;
		default:
			result = super.bulkInsert(uri, values);
			break;
		}

		if (result > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}

		return result;
	}

	/**
	 * Build new result URI for given path.
	 * 
	 * @param path
	 *            resource's path.
	 * @param result
	 *            operation' result code.
	 * @return new instance of uri.
	 */
	private Uri buildResultUri(String path, long result) {
		final Uri.Builder builder = new Uri.Builder();
		builder.scheme(MovieContract.SCHEME);
		builder.authority(MovieContract.AUTHORITY);
		builder.path(path);
		builder.appendPath(String.valueOf(result));
		return builder.build();
	}

}
