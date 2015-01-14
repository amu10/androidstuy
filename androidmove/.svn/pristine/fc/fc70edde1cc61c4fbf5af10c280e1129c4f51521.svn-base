package com.szcmcc.movie.storage;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.szcmcc.movie.storage.MovieContract.Tables;
import com.szcmcc.movie.storage.MovieContract.Tables.DownloadLogTable;
import com.szcmcc.movie.storage.MovieContract.Tables.PictureToDBTable;
import com.szcmcc.movie.storage.MovieContract.Tables.PictureToSDCardTable;

public class BaseDBUtil {
	public final static Uri OPERATION_URI = build(MovieContract.OPERATION_CONTENT_URI);
	public final static Uri PICTURE_DB_URI = build(MovieContract.PICTURE_DB_CONTENT_URI);
	public final static Uri PICTURE_SDCARD_URI = build(MovieContract.PICTURE_SDCARD_CONTENT_URI);
	public final static Uri CLEAN_DB_URI = build(MovieContract.CLEAN_DB_CONTENT_URI);
	public final static Uri DOWN_LOG_URI = build(MovieContract.DOWN_LOG_CONTENT_URI);

	public static Uri build(String path, String id) {
		final Uri.Builder builder = new Uri.Builder();
		builder.scheme(MovieContract.SCHEME);
		builder.authority(MovieContract.AUTHORITY);
		builder.path(path);
		builder.appendPath(id);
		return builder.build();

	}

	public static Uri build(String path) {
		final Uri.Builder builder = new Uri.Builder();
		builder.scheme(MovieContract.SCHEME);
		builder.authority(MovieContract.AUTHORITY);
		builder.path(path);
		return builder.build();
	}

	// -------------------------Picture---------------------

	public static void addDBPicture(Context context, String url, Bitmap bitmap) {
		ContentValues values = new ContentValues();
		values.put(Tables.PictureToDBTable.URL, urlDecode(url));
		byte[] bit = changeBitmapToByte(bitmap);
		values.put(Tables.PictureToDBTable.BYTESTR, bit);
		context.getContentResolver().insert(PICTURE_DB_URI, values);
	}

	public static Bitmap getDBPicture(Context context, String url) {
		Cursor cursor = context.getContentResolver().query(PICTURE_DB_URI, null, urlDecode(url), null, null);
		Bitmap bitmap = null;
		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				byte[] bit = cursor.getBlob(cursor.getColumnIndex(PictureToDBTable.BYTESTR));
				bitmap = changeByteToBitmap(bit);
			}
		}
		cursor.close();
		return bitmap;
	}

	public static String urlDecode(String url) {
		String decodeUrl = null;
		try {
			decodeUrl = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			decodeUrl = url;
			e.printStackTrace();
		}
		return decodeUrl;
	}

	/**
	 * 将图片转化为二进制的方法
	 * 
	 * @param bitMap
	 *            要转换的图片
	 * @return byte数组的字符串表示
	 */
	public static byte[] changeBitmapToByte(Bitmap bitMap) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitMap.compress(Bitmap.CompressFormat.PNG, 100, bos);
		return bos.toByteArray();
	}

	/**
	 * 将二进制转化为图片的方法
	 * 
	 * @param b
	 * @return
	 */
	public static Bitmap changeByteToBitmap(byte[] b) {
		Bitmap bitmap = null;
		if (b.length != 0) {
			bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
		}
		return bitmap;
	}

	public static boolean addSDPicture(Context context, Bitmap bitmap, String url, String path) {
		boolean result = false;
		try {
			FileOutputStream out;
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			out = new FileOutputStream(file);
			result = bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			if (result) {
				out.flush();
				out.close();
				ContentValues contentValues = new ContentValues();
				contentValues.put(PictureToSDCardTable.PATH, path);
				contentValues.put(PictureToSDCardTable.URL, urlDecode(url));
				context.getContentResolver().insert(PICTURE_SDCARD_URI, contentValues);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Bitmap getSDPicture(Context context, String url,boolean isFromList) {
//		System.out.println("getsd----------       "+url);
		Cursor cursor = context.getContentResolver().query(PICTURE_SDCARD_URI, null, urlDecode(url), null, null);
		String path = null;
		Bitmap bitmap = null;
		
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		if(isFromList){
			bmpFactoryOptions.inSampleSize=2;
		}
		bmpFactoryOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		bmpFactoryOptions.inPurgeable = true;
		bmpFactoryOptions.inInputShareable = true;
		
		while (cursor.moveToNext()) {
			path = cursor.getString(cursor.getColumnIndex(PictureToSDCardTable.PATH));
//			System.out.println("path----------       "+path);
		}
		cursor.close();
		if (path != null) {
			FileInputStream fs = null;
			BufferedInputStream bs = null;
			try {
				fs = new FileInputStream(path);
				bs = new BufferedInputStream(fs);
//				Drawable d = Drawable.createFromStream(bs, path);
//				BitmapDrawable bd = (BitmapDrawable) d;
//
//				bitmap = bd.getBitmap();
//				System.out.println("bitmap            "+bitmap);
				bitmap = BitmapFactory.decodeStream(bs, null, bmpFactoryOptions);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(NullPointerException e){
				e.printStackTrace();
			}
			finally{
				try {
					fs.close();
					bs.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch(NullPointerException e){
					e.printStackTrace();
				}
			}
		}
		return bitmap;
	}

	// 返回图片路径
	public static String getSDpath(Context context, String url) {
		Cursor cursor = context.getContentResolver().query(PICTURE_SDCARD_URI, null, urlDecode(url), null, null);
		String path = null;
		while (cursor.moveToNext()) {
			path = cursor.getString(cursor.getColumnIndex(PictureToSDCardTable.PATH));
		}
		cursor.close();
		return path;
	}
	
	// 返回图片路径
		public static boolean isHasSDpath(Context context, String url) {
			boolean flag = false;
			Cursor cursor = context.getContentResolver().query(PICTURE_SDCARD_URI, null, urlDecode(url), null, null);
			String path = "";
			while (cursor.moveToNext()) {
				path = cursor.getString(cursor.getColumnIndex(PictureToSDCardTable.PATH));
			}
			if(!path.equals("")){
				flag = true;
			}
			cursor.close();
			return flag;
		}


	// ---------------下载-----------------------------
	/**
	 * 获取每条线程已经下载的文件长度
	 * 
	 * @param path
	 * @return
	 */
	public static Map<Integer, Integer> getDownloadLog(Context context, String path) {
		Map<Integer, Integer> threadMap = new HashMap<Integer, Integer>();
		Cursor cursor = context.getContentResolver().query(DOWN_LOG_URI, null, Tables.DownloadLogTable.DOWN_PATH + "=?", new String[] { path }, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex(DownloadLogTable.THREAD_ID));
			int downlength = cursor.getInt(cursor.getColumnIndex(DownloadLogTable.DOWN_LENGTH));
			threadMap.put(id, downlength);
		}
		cursor.close();
		return threadMap;
	}

	/**
	 * 保存每条线程已经下载的文件长度
	 * 
	 * @param path
	 * @param map
	 */
	public static void addDownloadLog(Context context, String path, Map<Integer, Integer> map) {// int
																								// threadid,
																								// int
																								// position
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			ContentValues values = new ContentValues();
			values.put(DownloadLogTable.DOWN_PATH, path);
			values.put(DownloadLogTable.DOWN_LENGTH, entry.getValue());
			values.put(DownloadLogTable.THREAD_ID, entry.getKey());
			context.getContentResolver().insert(DOWN_LOG_URI, values);
		}
	}

	/**
	 * 实时更新每条线程已经下载的文件长度
	 * 
	 * @param path
	 * @param map
	 */
	public static void updateDownloadLog(Context context, String path, Map<Integer, Integer> map) {
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			ContentValues values = new ContentValues();
			values.put(DownloadLogTable.DOWN_PATH, path);
			values.put(DownloadLogTable.DOWN_LENGTH, entry.getValue());
			values.put(DownloadLogTable.THREAD_ID, entry.getKey());
			context.getContentResolver().insert(DOWN_LOG_URI, values);
		}
	}

	/**
	 * 当文件下载完成后，删除对应的下载记录
	 * 
	 * @param path
	 */
	public static void deleteDownloadLog(Context context, String path) {
		context.getContentResolver().delete(DOWN_LOG_URI, DownloadLogTable.DOWN_PATH + "=?", new String[] { path });
	}
	
	/**
     * 以最省内存的方式读取本地资源的图片
     * @param context
     * @param resId
     * @return
     */  
   public static Bitmap readBitMap(Context context, int resId){
       BitmapFactory.Options opt = new BitmapFactory.Options();  
       opt.inPreferredConfig = Bitmap.Config.RGB_565;   
       opt.inPurgeable = true;  
       opt.inInputShareable = true;  
          //获取资源图片  
       try{
       InputStream is = context.getResources().openRawResource(resId);  
       return BitmapFactory.decodeStream(is,null,opt);  
       }catch(Exception e){
       	e.printStackTrace();
       }
       return null;
   }
}
