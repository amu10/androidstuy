package com.szcmcc.movie.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieSaveDbHelper extends SQLiteOpenHelper {
	private static final String DBNAME = "movie_save.db"; // 数据库名称
	private static final int VERSION = 2; // 数据库版本

	public MovieSaveDbHelper(Context context) {
		// 第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类
		super(context, DBNAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String dbsql_seatOrder = "create table if not exists seatOrder(id TEXT primary key,m_id TEXT,name TEXT,imageUrl TEXT,address TEXT,data TEXT,datetime TEXT,status TEXT);";
		String dbsql_savemove = "create table if not exists savemovie(id TEXT primary key,m_id TEXT,name TEXT,imageUrl TEXT,play_status TEXT,playtime TEXT);";
		String dbsql_commentHistory =  "create table if not exists commentHistory(id TEXT primary key,m_id TEXT,name TEXT,imageUrl TEXT,ping TEXT,point TEXT);";
		db.execSQL(dbsql_seatOrder);
		db.execSQL(dbsql_savemove);
		db.execSQL(dbsql_commentHistory);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS seatOrder");
		db.execSQL("DROP TABLE IF EXISTS savemovie");
		db.execSQL("DROP TABLE IF EXISTS commentHistory");
		onCreate(db);
	}
}
