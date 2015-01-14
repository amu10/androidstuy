package com.szcmcc.movie.cache;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.szcmcc.movie.bean.SaveMovieNew;

/**
 * 操作客户端数据库中商品的dao层类
 * 
 */
public class MovieSaveDao {

	private MovieSaveDbHelper movieSaveDbHelper;
	private SQLiteDatabase database;

	public MovieSaveDao(Context context) {
		movieSaveDbHelper = new MovieSaveDbHelper(context);
	}

	/**
	 * 向本地数据库中添加商品
	 * 
	 * @param saveMovieNew
	 * @return
	 */
	public boolean saveMovie(SaveMovieNew saveMovieNew) {
		database = movieSaveDbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		boolean result = false;
		try {
			// if(saveMovieNew.getId()!=null){
			// cv.put("id", saveMovieNew.getId());
			// }else{
			// cv.put("id", UUIDGenerator.getUUID());
			// }
			// cv.put("id", UUIDGenerator.getUUID());
			cv.put("m_id", saveMovieNew.getId());
			cv.put("name", saveMovieNew.getName());
			cv.put("imageUrl", saveMovieNew.getImageUrl());
			cv.put("play_status", saveMovieNew.getPlay_status());
			cv.put("playtime", saveMovieNew.getPlaytime());
			database.insert("savemovie", null, cv);
			result = true;
		} catch (Exception e) {
			result = false;
		} finally {
			if (null != database) {
				database.close();
			}
		}
		return result;

	}

	/**
	 * 获取本地数据库中所有的商品
	 * 
	 * @return
	 */
	public ArrayList<SaveMovieNew> getAllMovie() {
		ArrayList<SaveMovieNew> list = null;
		database = movieSaveDbHelper.getReadableDatabase();
		Cursor cursor = null;
		try {
			String sql = "select m_id,name,imageUrl,play_status,playtime from savemovie;";
			cursor = database.rawQuery(sql, null);
			if (null == list) {
				list = new ArrayList<SaveMovieNew>();
			}
			while (cursor.moveToNext()) {
				System.out.println(cursor.getString(0) + "              " + cursor.getString(1)
						+ "              " + cursor.getString(2) + "              "
						+ cursor.getString(3));
				list.add(new SaveMovieNew(cursor.getString(0), cursor.getString(1), cursor
						.getString(2), cursor.getString(3), cursor.getString(4)));
			}
		} catch (Exception e) {

		} finally {
			if (null != database) {
				database.close();
			}
			if (cursor != null) {
				cursor.close();
			}
		}
		return list;
	}

	/**
	 * 根据name删除本地数据库中商品
	 * 
	 * @param name
	 * @return
	 */
	public boolean deleteMovie(String name) {
		boolean result = false;
		try {
			database = movieSaveDbHelper.getReadableDatabase();
			String where = "name=?";
			String args[] = { name };
			database.delete("savemovie", where, args);
			result = true;
		} catch (Exception e) {
			result = false;
		} finally {
			if (null != database) {
				database.close();
			}
		}

		return result;
	}

	/**
	 * 修改本地数据库中商品
	 * 
	 * @param list
	 * @return
	 */
	public boolean updateMovie(List<SaveMovieNew> list) {
		database = movieSaveDbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		boolean result = false;
		try {
			for (SaveMovieNew saveMovieNew : list) {
				cv.put("m_id", saveMovieNew.getId());
				cv.put("name", saveMovieNew.getName());
				cv.put("imageUrl", saveMovieNew.getImageUrl());
				cv.put("play_status", saveMovieNew.getPlay_status());
				cv.put("playtime", saveMovieNew.getPlaytime());
				database.update("savemovie", cv, "m_id=?", new String[] { saveMovieNew.getId() });
				result = true;
			}
		} catch (Exception e) {
			result = false;
		} finally {
			if (null != database) {
				database.close();
			}
		}
		return result;

	}

	/**
	 * 获取收藏电影的数量
	 * 
	 * @return
	 */
	public int getSaveMovieNum() {
		int i = 0;
		database = movieSaveDbHelper.getReadableDatabase();
		Cursor cursor = null;
		try {
			String sql = "select m_id,name,imageUrl,play_status,playtime from savemovie;";
			cursor = database.rawQuery(sql, null);
			i = cursor.getCount();
		} catch (Exception e) {

		} finally {
			if (null != database) {
				database.close();
			}
			if (cursor != null) {
				cursor.close();
			}
		}
		return i;
	}

	public boolean updateMovie(SaveMovieNew saveMovieNew) {
		database = movieSaveDbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		boolean result = false;
		try {

			cv.put("m_id", saveMovieNew.getId());
			cv.put("name", saveMovieNew.getName());
			cv.put("imageUrl", saveMovieNew.getImageUrl());
			cv.put("play_status", saveMovieNew.getPlay_status());
			cv.put("playtime", saveMovieNew.getPlaytime());
			database.update("savemovie", cv, "name=?", new String[] { saveMovieNew.getName() });
			result = true;

		} catch (Exception e) {
			result = false;
		} finally {
			if (null != database) {
				database.close();
			}
		}
		return result;

	}

	/**
	 * 查询数据库里是否包含要查询的name
	 * 
	 * @param name
	 * @return
	 */
	public boolean isContains(String name) {
		boolean isContains = false;
		database = movieSaveDbHelper.getReadableDatabase();
		Cursor cursor = null;
		try {
			String sql = "select name from savemovie;";
			cursor = database.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				if (cursor.getString(0).equals(name)) {
					isContains = true;
					break;
				}
			}
		} catch (Exception e) {

		} finally {
			if (null != database) {
				database.close();
			}
			if (cursor != null) {
				cursor.close();
			}
		}
		return isContains;
	}

}
