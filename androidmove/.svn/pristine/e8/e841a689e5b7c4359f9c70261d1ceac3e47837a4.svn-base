package com.szcmcc.movie.cache;
//package com.szcmcc.movie.cache;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.szcmcc.movie.bean.MoviePinLun;
//import com.szcmcc.movie.bean.SaveMovieNew;
//
///**
// * 操作客户端数据库中商品的dao层类
// *
// */
//public class SeatOrderDao {
//
//	private MovieSaveDbHelper movieSaveDbHelper;
//	private SQLiteDatabase database;
//
//	public CommentHistoryDao(Context context) {
//		movieSaveDbHelper = new MovieSaveDbHelper(context);
//	}
//	
//	
///**
// * 向本地数据库中添加商品
// * @param saveMovieNew
// * @return
// */
//	public boolean saveMovie(MoviePinLun moviePinLun) {
//		database = movieSaveDbHelper.getWritableDatabase();
//		ContentValues cv = new ContentValues();
//		boolean result = false;
//			try {
////				if(saveMovieNew.getId()!=null){
////					cv.put("id", saveMovieNew.getId());
////				}else{
////					cv.put("id", UUIDGenerator.getUUID());
////				}
////				cv.put("id", UUIDGenerator.getUUID());
//				cv.put("m_id", moviePinLun.m_id);
//				cv.put("name", moviePinLun.name);
//				cv.put("imageUrl", moviePinLun.imageUrl);
//				cv.put("ping", moviePinLun.ping);
//				cv.put("point", moviePinLun.point);
//				database.insert("commentHistory", null, cv);
//				result = true;
//			} catch (Exception e) {
//				result = false;
//			} finally {
//				if (null != database) {
//					database.close();
//				}
//			}
//			return result;
//
//	}
//	/**
//	 * 获取本地数据库中所有的商品
//	 * @return
//	 */
//	public List<MoviePinLun> getAllMovie() {
//		List<MoviePinLun> list = null;
//		database = movieSaveDbHelper.getReadableDatabase();
//		Cursor cursor = null;
//		try{
//		String sql = "select m_id,name,imageUrl,ping,point from commentHistory;";
//		cursor = database.rawQuery(sql, null);
//		while (cursor.moveToNext()) {
//			if (null == list) {
//				list = new ArrayList<MoviePinLun>();
//			}
//			System.out.println(cursor.getString(0)+"              "+cursor.getString(1)+"              "+cursor.getString(4)+"              "+cursor.getString(3));
//			list.add(new MoviePinLun(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
//		}
//		}catch(Exception e){
//			
//		}finally{
//			if(null!=database){
//				database.close();
//			}if(cursor!=null){
//				cursor.close();
//			}
//		}
//		return list;
//	}
//	
//	/**
//	 * 查询数据库里是否包含要查询的id
//	 * @param m_id
//	 * @return
//	 */
//	public boolean isContains(String m_id) {
//		boolean isContains = false;
//		database = movieSaveDbHelper.getReadableDatabase();
//		Cursor cursor = null;
//		try{
//		String sql = "select m_id from commentHistory;";
//		cursor = database.rawQuery(sql, null);
//		while (cursor.moveToNext()) {
//			if(cursor.getString(0).equals(m_id)){
//				isContains = true;
//				break;
//			}
//		}
//		}catch(Exception e){
//			
//		}finally{
//			if(null!=database){
//				database.close();
//			}if(cursor!=null){
//				cursor.close();
//			}
//		}
//		return isContains;
//	}
//	
//	
//	/**
//	 * 根据id删除本地数据库中商品
//	 * @param id
//	 * @return
//	 */
//	public boolean deleteMovie(String id) {
//		boolean result = false;
//		try{
//		database = movieSaveDbHelper.getReadableDatabase();
//		String where = "m_id=?";
//		String args[] = {id};
//		 database.delete("commentHistory", where, args);
//		 result = true;
//		}catch(Exception e){
//			result = false;
//		}finally{
//			if(null!=database){
//				database.close();
//			}
//		}
//		
//		return result;
//	}
//	
//	/**
//	 * 修改本地数据库中商品
//	 * @param list
//	 * @return
//	 */
//		public boolean updateMovie(List<MoviePinLun> list) {
//			database = movieSaveDbHelper.getWritableDatabase();
//			ContentValues cv = new ContentValues();
//			boolean result = false;
//				try {
//					for(MoviePinLun moviePinLun:list){
//						cv.put("m_id", moviePinLun.m_id);
//						cv.put("name", moviePinLun.name);
//						cv.put("imageUrl", moviePinLun.imageUrl);
//						cv.put("ping", moviePinLun.ping);
//						cv.put("point", moviePinLun.point);
//						database.update("commentHistory", cv, "m_id=?",new String[]{moviePinLun.m_id});
//						result = true;
//					}
//				} catch (Exception e) {
//					result = false;
//				} finally {
//					if (null != database) {
//						database.close();
//					}
//				}
//				return result;
//
//		}
//
//}
