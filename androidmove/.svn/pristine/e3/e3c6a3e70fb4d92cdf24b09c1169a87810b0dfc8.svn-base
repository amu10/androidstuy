package com.szcmcc.movie.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;

import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.Movie;
import com.szcmcc.movie.bean.MovieInfo;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.view.LoadingImagView;

public class ImageAdapter extends BaseAdapter {

	int mGalleryItemBackground;
	private Context mContext;
//	private Integer[] mImageIds;
//	private ImageView[] mImages;
	int widthBg = 0, heightBg = 0;
	private ArrayList<Movie> mMovieNews;
//	private Activity mContext;
	private MovieApplication app;
	private MovieInfo movieInfo;// 没用到
	// private LoadingImagView loadingImagView;
	private int currentPosition = 0;

	private boolean isCanLoadLargePic = true;
	private boolean isCanForSearchSd = true;
	public ImageAdapter(Context c) {
		mContext = c;
//		mImageIds = ImageIds;
//		mImages = new ImageView[mImageIds.length];
		app = (MovieApplication) mContext.getApplicationContext();
		mMovieNews = new ArrayList<Movie>();
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

		widthBg = wm.getDefaultDisplay().getWidth();// 屏幕宽度

		heightBg = wm.getDefaultDisplay().getHeight();// 屏幕高度
	}
	
	public void addItem(List<Movie> movieList) {
		if (movieList != null) {
			mMovieNews.addAll(movieList);
			notifyDataSetChanged();
		}
	}

	

	public void setMovieInfo(MovieInfo movieInfo) {
		this.movieInfo = movieInfo;
		// notifyDataSetChanged();
	}

	public void setCurrentPosition(int position) {
		currentPosition = position;
	}

	/**
	 * 创建倒影效果
	 * 
	 * @return
	 */
//	public boolean createReflectedImages() {
//		// 倒影图和原图之间的距离
//		final int reflectionGap = 4;
//		int index = 0;
//
//		for (int imageId : mImageIds) {
//			// 返回原图解码之后的bitmap对象
//			Bitmap originalImage = BitmapFactory.decodeResource(mContext.getResources(), imageId);
//			int width = originalImage.getWidth();
//			int height = originalImage.getHeight();
//
//			// 创建矩阵对象
//			Matrix matrix = new Matrix();
//
//			// 指定一个角度以0,0为坐标进行旋转
//			// matrix.setRotate(30);
//
//			// 指定矩阵(x轴不变，y轴相反)
//			matrix.preScale(1, -1);
//
//			// 将矩阵应用到该原图之中，返回一个宽度不变，高度为原图1/2的倒影位图
//			// Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
//			// height/2, width, height/2, matrix, false);
//
//			// 创建一个宽度不变，高度为原图+倒影图高度的位图
//			Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height), Config.ARGB_8888);
//
//			// 将上面创建的位图初始化到画布
//			Canvas canvas = new Canvas(bitmapWithReflection);
//			canvas.drawBitmap(originalImage, 0, 0, null);
//
//			Paint deafaultPaint = new Paint();
//			deafaultPaint.setAntiAlias(false);
//			// canvas.drawRect(0, height, width, height +
//			// reflectionGap,deafaultPaint);
//			canvas.drawBitmap(bitmapWithReflection, 0, height + reflectionGap, null);
//
//			Paint paint = new Paint();
//			paint.setAntiAlias(false);
//
//			/**
//			 * 参数一:为渐变起初点坐标x位置， 参数二:为y轴位置， 参数三和四:分辨对应渐变终点， 最后参数为平铺方式，
//			 * 这里设置为镜像Gradient是基于Shader类，所以我们通过Paint的setShader方法来设置这个渐变
//			 */
//			LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
//					0x00ffffff, TileMode.MIRROR);
//			// 设置阴影
//			// paint.setShader(shader);
//			paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_IN));
//			// 用已经定义好的画笔构建一个矩形阴影渐变效果
//			canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);
//
//			// 创建一个ImageView用来显示已经画好的bitmapWithReflection
//			ImageView imageView = new ImageView(mContext);
//			imageView.setImageBitmap(bitmapWithReflection);
//			// 设置imageView大小 ，也就是最终显示的图片大小
//			System.out.println("width    " + widthBg + "    " + heightBg);
//			imageView.setLayoutParams(new GalleryFlow.LayoutParams(widthBg - widthBg / 4, heightBg - heightBg / 4));
//			// imageView.setScaleType(ScaleType.MATRIX);
//			mImages[index++] = imageView;
//		}
//		return true;
//	}

//	@SuppressWarnings("unused")
//	private Resources getResources() {
//		return null;
//	}

	public int getCount() {
		return mMovieNews.size();
	}

	public Movie getItem(int position) {
		if (position >= 0 && position < mMovieNews.size()) {
			return mMovieNews.get(position);
		}
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final Holder holder;
		if (convertView == null) {
			convertView =  LayoutInflater.from(mContext).inflate(R.layout.homepage_viewpager, null);
			holder = new Holder();
			holder.imageView = (ImageView)convertView.findViewById(R.id.homepage_viewpager_imag);
			holder.loadingImagView =  (LoadingImagView) convertView.findViewById(R.id.homepager_loading_viewpager_progress);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		
		holder.loadingImagView.setVisibility(View.VISIBLE);
		
		holder.imageView.setFocusableInTouchMode(true);
		holder.imageView.setScaleType(ScaleType.FIT_CENTER);
		
		holder.imageView.setLayoutParams(new LayoutParams(widthBg - widthBg / 4, heightBg - heightBg /3));
		holder.imageView.setBackgroundColor(0xff000000);
		// client_placard_url1 大图
		isCanLoadLargePic = true;
		Movie movie = mMovieNews.get(position);
//		if (movie != null && URLUtil.isHttpUrl(movie.client_placard_url2)) {
//			holder.imageView.setTag(movie.client_placard_url2);
//			System.out.println("position-cu-xiao--------        " + position + "       " + currentPosition);
//			Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext, movie.client_placard_url2, new ImageCallback() {
//
//				@Override
//				public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
//					String url = (String) holder.imageView.getTag();
//					if (url.equals(bitmapUrl)) {
////						holder.loadingImagView.setVisibility(View.GONE);
////						holder.imageView.setBackgroundColor(0x00000000);
//						holder.imageView.setBackgroundResource(R.drawable.shade);
//						holder.imageView.setImageBitmap(bitmap);
//					}
//				}
//			}, AsyncImageLoader.CACHE_TYPE_SD, false);
//			if (bitmap != null) {
//				holder.imageView.setBackgroundColor(0x00000000);
////				holder.loadingImagView.setVisibility(View.GONE);
//				holder.imageView.setImageBitmap(bitmap);
//			}
//		}
//		if (isCanForSearchSd) {
//			for (int i = 0; i < mMovieNews.size(); i++) {
//				if (!BaseDBUtil.isHasSDpath(mContext, mMovieNews.get(i).client_placard_url2)) {
//					isCanLoadLargePic = false;
//					System.out.println("ishassd  false-----------    " + isCanLoadLargePic);
//					break;
//				}
//				System.out.println("ishassd  for-----------    " + isCanLoadLargePic);
//			}
//		}
//		// 大图
//		if (isCanLoadLargePic && NetImageView.isWifi(mContext)) {
//			isCanForSearchSd = false;
			if (movie != null && URLUtil.isHttpUrl(movie.client_placard_url1)) {
				holder.imageView.setTag(movie.client_placard_url1);
				Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext, movie.client_placard_url1, new ImageCallback() {

					@Override
					public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
						String url = (String) holder.imageView.getTag();
						if (url.equals(bitmapUrl)) {
							holder.loadingImagView.setVisibility(View.GONE);
							holder.imageView.setBackgroundColor(0x00000000);
							holder.imageView.setImageBitmap(bitmap);
						}
					}
				}, AsyncImageLoader.CACHE_TYPE_SD, false);
				if (bitmap != null) {
					holder.loadingImagView.setVisibility(View.GONE);
					holder.imageView.setBackgroundColor(0x00000000);
					holder.imageView.setImageBitmap(bitmap);
				}
			}
//		}
		return convertView;
	}

	public float getScale(boolean focused, int offset) {
		return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
	}

	public class Holder{
		public ImageView imageView = null;
		public LoadingImagView loadingImagView = null;
	}
}
