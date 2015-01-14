package com.szcmcc.movie.network;

import java.io.File;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;

import com.szcmcc.movie.storage.DBUtil;

public class AsyncImageLoader {
//public WeakHashMap<String, Bitmap> imageCache = new WeakHashMap<String, Bitmap>();
	public WeakHashMap<String, SoftReference<Bitmap>> imageCache = new WeakHashMap<String, SoftReference<Bitmap>>();
	private ExecutorService executorService = Executors.newFixedThreadPool(3);
	private final Handler handler = new Handler();
	public static final int CACHE_TYPE_DB = 1;
	public static final int CACHE_TYPE_SD = 2;
	public static final int CACHE_TYPE_RUNTIME = 3;
	public static final int NO_CACHE = 4;

	/**
	 * 
	 * @param imageUrl
	 *            :图像url地址
	 * @param callback
	 *            :回调接口
	 * @param isFromList
	 *            :这里是判断是否是列表图片而不是背景大图，如果是列表，因为图片小 而且多，这里准备进行压缩
	 * @return 返回内存中缓存的图像，第一次加载返回null
	 */
	public Bitmap loadBitmapForView(final Context context,
			final String imageUrl, final ImageCallback callback,
			final int cache_type, final boolean isFromList) {
		if (imageCache.containsKey(imageUrl)
				&& imageCache.get(imageUrl) == null) {
			imageCache.remove(imageUrl);
		}

		if (imageCache.containsKey(imageUrl)) {
			Bitmap bitmap = imageCache.get(imageUrl).get();
			if (bitmap != null) {
//				System.out.println("imageCache--2-----"+bitmap);
				return imageCache.get(imageUrl).get();
			}
		}

		Bitmap pic = DBUtil.getSDPicture(context, imageUrl,isFromList);
//		System.out.println("getbitmap   SD   ---"+pic);
		SoftReference<Bitmap> soft = new SoftReference<Bitmap>(
				pic);
		imageCache.put(imageUrl, soft);
		if (pic != null) {
			return pic;
		}
		executorService.submit(new Runnable() {
			public void run() {
				try {
					final Bitmap bitmap;
					if (isFromList) {
						bitmap = loadImageFromUrl(imageUrl, isFromList);
					} else {
						bitmap = loadImageFromUrl(imageUrl, isFromList);
					}
					switch (cache_type) {
					case CACHE_TYPE_DB:
						DBUtil.addDBPicture(context, imageUrl, bitmap);
						break;
					case CACHE_TYPE_SD:
						if (Environment.getExternalStorageState().equals(
								Environment.MEDIA_MOUNTED)) {
							String picturePath = Environment
									.getExternalStorageDirectory().getPath();
							picturePath = picturePath + "/yidongyingyuan";
							File dirFile = new File(picturePath);
							if (!(dirFile.exists()) || !(dirFile.isDirectory())) {
								dirFile.mkdir();
							}
							picturePath = picturePath + "/image_cache";
							dirFile = new File(picturePath);
							if (!(dirFile.exists()) || !(dirFile.isDirectory())) {
								dirFile.mkdir();
							}
//							File pictureFile = new File(dirFile, System
//									.currentTimeMillis()
//									+ ".jpg");
							File pictureFile = new File(dirFile, imageUrl.substring(imageUrl.lastIndexOf("/")+1));
							String path = pictureFile.getPath();
							DBUtil
									.addSDPicture(context, bitmap, imageUrl,
											path);
						} else {
							SoftReference<Bitmap> soft = new SoftReference<Bitmap>(
									bitmap);
							imageCache.put(imageUrl, soft);
//							System.out.println("imageCache.put  " + soft);
						}
						break;
					case CACHE_TYPE_RUNTIME:
						SoftReference<Bitmap> soft = new SoftReference<Bitmap>(
								bitmap);
						imageCache.put(imageUrl, soft);
						break;
					case NO_CACHE:
						break;
					}

					if (callback != null) {
						handler.post(new Runnable() {
							public void run() {
								callback.imageLoaded(bitmap, imageUrl);
							}
						});
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		return null;
	}

	public Bitmap loadBitmapToDB(final Context context, final String imageUrl) {
		Bitmap pic = DBUtil.getDBPicture(context, imageUrl);
		if (pic != null) {
			return pic;
		}
		try {
			pic = BitmapFactory.decodeStream(new URL(imageUrl).openStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		executorService.submit(new Runnable() {
			public void run() {
				try {
					final Bitmap bitmap = BitmapFactory.decodeStream(new URL(
							imageUrl).openStream());
					DBUtil.addDBPicture(context, imageUrl, bitmap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return null;
	}

//	public Bitmap loadBitmapToSD(final Context context, final String imageUrl) {
//		Bitmap pic = DBUtil.getSDPicture(context, imageUrl);
//		if (pic != null) {
//			return pic;
//		}
//		executorService.submit(new Runnable() {
//			public void run() {
//				try {
//					final Bitmap bitmap = BitmapFactory.decodeStream(new URL(
//							imageUrl).openStream());
//					if (Environment.getExternalStorageState().equals(
//							Environment.MEDIA_MOUNTED)) {
//						String picturePath = Environment
//								.getExternalStorageDirectory().getPath();
//						picturePath = picturePath + "/yidongyingyuan";
//						File dirFile = new File(picturePath);
//						if (!(dirFile.exists()) || !(dirFile.isDirectory())) {
//							dirFile.mkdir();
//						}
//						picturePath = picturePath + "/image_cache";
//						dirFile = new File(picturePath);
//						if (!(dirFile.exists()) || !(dirFile.isDirectory())) {
//							dirFile.mkdir();
//						}
//						File pictureFile = new File(dirFile, System
//								.currentTimeMillis()
//								+ ".jpg");
//						String path = pictureFile.getPath();
//						DBUtil.addSDPicture(context, bitmap, imageUrl, path);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		return null;
//	}

	protected Bitmap loadImageFromUrl(String imageUrl, boolean isFromList) {
		try {
			BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
			bmpFactoryOptions.inPreferredConfig = Bitmap.Config.RGB_565;
			bmpFactoryOptions.inPurgeable = true;
			bmpFactoryOptions.inInputShareable = true;
			Bitmap bitmap = BitmapFactory.decodeStream(new URL(imageUrl)
					.openStream(), null, bmpFactoryOptions);
			SoftReference<Bitmap> soft = new SoftReference<Bitmap>(bitmap);
			imageCache.put(imageUrl, soft);
			return bitmap;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public interface ImageCallback {
		public void imageLoaded(Bitmap bitmap, String bitmapUrl);
	}

	public void recycleBitmaps() {
//		imageCache.clear();
		System.gc();
	}
}
