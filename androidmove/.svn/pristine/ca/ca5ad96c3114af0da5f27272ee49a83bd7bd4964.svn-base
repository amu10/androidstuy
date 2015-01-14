package com.szcmcc.movie.storage;

import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.szcmcc.movie.storage.MovieContract.Tables;
import com.szcmcc.movie.storage.MovieContract.Tables.AbstractTable;
import com.szcmcc.movie.storage.MovieContract.Tables.PictureToDBTable;

public class MovieDB {
	private static final String TAG = MovieDB.class.getSimpleName();

	private String mDBName = "movie.db";

	private SQLiteDatabase mSQLiteDB;

	private BlogDBOpenHelper mDBOpenHelper;

	private final Context mContext;

	public MovieDB(Context context) {
		mContext = context;
	}

	public MovieDB(Context context, String dbName) {
		mContext = context;
		if (!TextUtils.isEmpty(dbName)) {
			mDBName = dbName;
		}
	}

	public MovieDB open() {
		mDBOpenHelper = new BlogDBOpenHelper(mContext, mDBName, null, MovieContract.DB_VERSION);
		mSQLiteDB = mDBOpenHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDBOpenHelper.close();
	}

	public void beginTransaction() {
		mSQLiteDB.beginTransaction();
	}

	public void endTransaction() {
		if (mSQLiteDB.inTransaction()) {
			mSQLiteDB.endTransaction();
		}
	}

	public void setTransactionSuccessful() {
		mSQLiteDB.setTransactionSuccessful();
	}

	// 删除表
	public int cleanDatabase() {
		return 0;
	}

	private class BlogDBOpenHelper extends SQLiteOpenHelper {

		public BlogDBOpenHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			final Set<Class<? extends AbstractTable>> tables = Tables.getTables();
			for (Class<? extends AbstractTable> table : tables) {
				try {// 创建表
					for (String statment : Tables.getCreateStatments(table)) {
						Log.d(TAG, statment);
						db.execSQL(statment);
					}
				} catch (Throwable e) {
					Log.e(TAG, "Can't create table " + table.getSimpleName());
				}
			}
		}

		// 更新版本
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d(TAG, "onUpgrade: " + oldVersion + " >> " + newVersion);
			final Set<Class<? extends AbstractTable>> tables = Tables.getTables();
			for (Class<? extends AbstractTable> table : tables) {
				try {
					db.execSQL("DROP TABLE IF EXISTS " + Tables.getTableName(table));
				} catch (Throwable e) {
					Log.e(TAG, "Can't create table " + table.getSimpleName());
				}
			}
			onCreate(db);
		}

	}

	// -------------------------picture db---------------------
	public long addDBPicture(ContentValues values) {
		long aff = 0;
		if (values.containsKey(Tables.PictureToDBTable.URL)) {
			aff = updateDBPicture(values, Tables.PictureToDBTable.URL + "=?", new String[] { values
					.getAsString(Tables.PictureToDBTable.URL) });
			if (aff == 0) {
				aff = mSQLiteDB.insert(Tables.PictureToDBTable.TABLE_NAME, "", values);
			}
		}
		return aff;

	}

	public Cursor getDBPicture(String imgUrl) {
		return mSQLiteDB.query(Tables.PictureToDBTable.TABLE_NAME, null, PictureToDBTable.URL + "=?",
				new String[] { imgUrl }, null, null, null);

	}

	public int updateDBPicture(ContentValues values, String whereClause, String[] whereArgs) {
		return mSQLiteDB.update(Tables.PictureToDBTable.TABLE_NAME, values, whereClause, whereArgs);
	}

	// -------------------------picture sdcard---------------------
	public long addSDPicture(ContentValues values) {
		long aff = 0;
		if (values.containsKey(Tables.PictureToSDCardTable.PATH)) {
			aff = updateSDPicture(values, Tables.PictureToSDCardTable.PATH + "=?", new String[] { values
					.getAsString(Tables.PictureToSDCardTable.PATH) });
			if (aff == 0) {
				aff = mSQLiteDB.insert(Tables.PictureToSDCardTable.TABLE_NAME, "", values);
			}
		}
		return aff;

	}

	public Cursor getSDPicture(String imgUrl) {
		return mSQLiteDB.query(Tables.PictureToSDCardTable.TABLE_NAME, null, PictureToDBTable.URL + "=?",
				new String[] { imgUrl }, null, null, null);

	}

	public int updateSDPicture(ContentValues values, String whereClause, String[] whereArgs) {
		return mSQLiteDB.update(Tables.PictureToSDCardTable.TABLE_NAME, values, whereClause, whereArgs);
	}

	// -----------------------downloadlog----------------
	public long addDownLog(ContentValues values) {
		long aff = 0;
		if (values.containsKey(Tables.DownloadLogTable.DOWN_PATH)
				&& values.containsKey(Tables.DownloadLogTable.THREAD_ID)) {
			aff = updateDownLog(values, Tables.DownloadLogTable.DOWN_PATH + "=? and "
					+ Tables.DownloadLogTable.THREAD_ID + "=? ", new String[] {
					values.getAsString(Tables.DownloadLogTable.DOWN_PATH),
					values.getAsString(Tables.DownloadLogTable.THREAD_ID) });
			if (aff == 0) {
				aff = mSQLiteDB.insert(Tables.DownloadLogTable.TABLE_NAME, "", values);
			}
		}
		return aff;
	}

	public int updateDownLog(ContentValues values, String whereClause, String[] whereArgs) {
		return mSQLiteDB.update(Tables.DownloadLogTable.TABLE_NAME, values, whereClause, whereArgs);
	}

	public Cursor getDownLog(String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		return mSQLiteDB.query(Tables.DownloadLogTable.TABLE_NAME, columns, selection, selectionArgs, groupBy, having,
				orderBy);
	}

	public int deleteDownLog(String whereClause, String[] whereArgs) {
		return mSQLiteDB.delete(Tables.DownloadLogTable.TABLE_NAME, whereClause, whereArgs);
	}



	

}
