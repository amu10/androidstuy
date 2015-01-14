package com.szcmcc.movie.cache;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.szcmcc.movie.util.Log;

public class BitmapUtil {

	/**
	 * 3、Bitmap → byte[] Java代码
	 * 
	 * @param bm
	 * @return
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bytes = null;
		try {
			bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
			bytes = baos.toByteArray();
		} catch (Exception e) {
		} finally {
			try {
				baos.flush();
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return bytes;
	}

	/**
	 * 4、 byte[] → Bitmap Java代码
	 * 
	 * @param b
	 * @return
	 */
	public static Bitmap Bytes2Bimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	// /**
	// * 根据图片网络地址获取图片的byte[]类型数据
	// *
	// * @param urlPath
	// * 图片网络地址
	// * @return 图片数据
	// */
	// public static byte[] getImageFromURL(String urlPath) {
	// byte[] data = null;
	// InputStream is = null;
	// HttpURLConnection conn = null;
	// try {
	// URL url = new URL(urlPath);
	// conn = (HttpURLConnection) url.openConnection();
	// conn.setDoInput(true);
	// // conn.setDoOutput(true);
	// conn.setRequestMethod("GET");
	// conn.setConnectTimeout(6000);
	// is = conn.getInputStream();
	// if (conn.getResponseCode() == 200) {
	// data = readInputStream(is);
	// } else
	// System.out.println("发生异常！");
	//
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// conn.disconnect();
	// try {
	// is.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// return data;
	// }

	public static byte[] readInputStream(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = -1;
		try {
			while ((length = is.read(buffer)) != -1) {
				baos.write(buffer, 0, length);
			}
			baos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] data = baos.toByteArray();
		try {
			is.close();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static Bitmap getSamllerBitmapByInputStream(Context context,
			InputStream _InputStream) {

		byte[] imageByte = readInputStream(_InputStream);

		Bitmap bitmap = null;
		if (imageByte.length > 50 * 1024) {
			Log.i("PRESS" + imageByte.length);
			// 以下是把图片转化为缩略图再加载
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			bitmap = BitmapFactory.decodeByteArray(imageByte, 0,
					imageByte.length, options);
			options.inJustDecodeBounds = false;
			int be = (int) (options.outHeight / (float) 200);
			if (be <= 0) {
				be = 1;
			}
			options.inSampleSize = be;
			bitmap = BitmapFactory.decodeByteArray(imageByte, 0,
					imageByte.length, options);
		} else {
			Log.i("NOPRESS" + imageByte.length);
			bitmap = BitmapFactory.decodeByteArray(imageByte, 0,
					imageByte.length);
		}
		//
		// AsyncImageLoader.savePic(bitmap, System.currentTimeMillis()
		// + "test.jpg");
		return bitmap;
	}
	// public static Bitmap getSamllerBitmapByURL(String url) {
	//
	// byte[] imageByte = getImageFromURL(url);
	//
	// Bitmap bitmap = null;
	// if (imageByte.length > 30 * 1024) {
	// // 以下是把图片转化为缩略图再加载
	// BitmapFactory.Options options = new BitmapFactory.Options();
	// options.inJustDecodeBounds = true;
	// bitmap = BitmapFactory.decodeByteArray(imageByte, 0,
	// imageByte.length, options);
	// options.inJustDecodeBounds = false;
	// int be = (int) (options.outHeight / (float) 100);
	// if (be <= 0) {
	// be = 1;
	// }
	// options.inSampleSize = be;
	// bitmap = BitmapFactory.decodeByteArray(imageByte, 0,
	// imageByte.length, options);
	// }else
	// {
	// bitmap= BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
	// }
	//
	// AsyncImageLoader.savePic(bitmap, System.currentTimeMillis()
	// + "test.jpg");
	// return bitmap;
	// }

	// public static Bitmap getSmallBitmap(String _File) {
	// Bitmap bmp = null;
	// BitmapFactory.Options opts = new BitmapFactory.Options();
	// opts.inJustDecodeBounds = true;
	// BitmapFactory.decodeFile(_File, opts);
	// opts.inSampleSize = computeSampleSize(opts, -1, 256 * 256);// 调整图片质量
	// opts.inJustDecodeBounds = false;
	// try {
	// bmp = BitmapFactory.decodeFile(_File, opts);
	//
	// } catch (OutOfMemoryError err) {
	// System.out.println(err);
	// }
	// AsyncImageLoader.savePic(bmp, System.currentTimeMillis() + "test.jpg");
	// return bmp;
	// }

	// public static int computeSampleSize(BitmapFactory.Options options,
	// int minSideLength, int maxNumOfPixels) {
	// int initialSize = computeInitialSampleSize(options, minSideLength,
	// maxNumOfPixels);
	//
	// int roundedSize;
	// if (initialSize <= 8) {
	// roundedSize = 1;
	// while (roundedSize < initialSize) {
	// roundedSize <<= 1;
	// }
	// } else {
	// roundedSize = (initialSize + 7) / 8 * 8;
	// }
	//
	// return roundedSize;
	// }

	// private static int computeInitialSampleSize(BitmapFactory.Options
	// options,
	// int minSideLength, int maxNumOfPixels) {
	// double w = options.outWidth;
	// double h = options.outHeight;
	//
	// int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
	// .sqrt(w * h / maxNumOfPixels));
	// int upperBound = (minSideLength == -1) ? 256 : (int) Math.min(
	// Math.floor(w / minSideLength), Math.floor(h / minSideLength));// 调整图片质量
	//
	// if (upperBound < lowerBound) {
	// // return the larger one when there is no overlapping zone.
	// return lowerBound;
	// }
	//
	// if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
	// return 1;
	// } else if (minSideLength == -1) {
	// return lowerBound;
	// } else {
	// return upperBound;
	// }
	// }

}
