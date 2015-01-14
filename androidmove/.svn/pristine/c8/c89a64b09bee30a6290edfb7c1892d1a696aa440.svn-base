package com.szcmcc.movie.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.ImageView.ScaleType;

import com.itotem.view.touchimageview.ImageViewTouch;
import com.itotem.view.touchimageview.PagerAdapter;
import com.itotem.view.touchimageview.ViewPager;
import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.Movie;
import com.szcmcc.movie.bean.MovieInfo;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.storage.BaseDBUtil;
import com.szcmcc.movie.view.LoadingImagView;
import com.szcmcc.movie.view.NetImageView;

public class HomepageAdapter extends PagerAdapter {
	protected static final String TAG = "HomepageAdapter";
	public Map<Integer, ImageViewTouch> views = new HashMap<Integer, ImageViewTouch>();
	private ArrayList<Movie> mMovieNews;
	private Activity mContext;
	private MovieApplication app;
	private MovieInfo movieInfo;//没用到
//	private LoadingImagView loadingImagView; 
	private int currentPosition = 0;
	int widthBg = 0, heightBg = 0;
	private boolean isCanLoadLargePic = true;
	private boolean isCanForSearchSd = true;
	public HomepageAdapter(Activity context) {
		mContext = context;
		mMovieNews = new ArrayList<Movie>();
		app = (MovieApplication)mContext.getApplicationContext();
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

		widthBg = wm.getDefaultDisplay().getWidth();// 屏幕宽度

		heightBg = wm.getDefaultDisplay().getHeight();// 屏幕高度
	}
	
	public void addItem(List<Movie> movieList){
		if(movieList!=null){
		mMovieNews.addAll(movieList);
		notifyDataSetChanged();
		}
	}
	

	public Movie getItem(int position){
		if(position>=0&&position<mMovieNews.size()){
			return mMovieNews.get(position);
		}
		return null;
	}
	
	public void setMovieInfo(MovieInfo movieInfo){
		this.movieInfo = movieInfo;
//		notifyDataSetChanged();
	}
	
	public void setCurrentPosition(int position){
		currentPosition = position;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
//		ImageViewTouch imageView = (ImageViewTouch)object;
//		((ViewPager) container).removeView(imageView);
//		views.remove(position);
		View view = (View)object;
		((ViewPager)container).removeView(view);
		views.remove(position);
//		imageView.mBitmapDisplayed.recycle();//导致异常问题.所调用了已经调用recyle的bitmap
//		imageView.clear();
	}

	@Override
	public void finishUpdate(View container) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mMovieNews.size();
	}

	
	
	@Override
	public Object instantiateItem(View container, final int position) {
		

		View view = LayoutInflater.from(mContext).inflate(R.layout.homepage_viewpager, null);
		final ImageViewTouch imageView = (ImageViewTouch) view.findViewById(R.id.homepage_viewpager_imag);
		final LoadingImagView loadingImagView = (LoadingImagView) view.findViewById(R.id.homepager_loading_viewpager_progress);
		loadingImagView.setVisibility(View.VISIBLE);
		imageView.setBackgroundColor(Color.BLACK);
		imageView.setFocusableInTouchMode(true);
		imageView.setScaleType(ScaleType.FIT_CENTER);
//		imageView.setLayoutParams(new LayoutParams(widthBg - widthBg / 4, heightBg - heightBg / 4));
		//client_placard_url1 大图
		isCanLoadLargePic = true;
		Movie movie = mMovieNews.get(position);
//		if(movie!=null&&URLUtil.isHttpUrl(movie.client_placard_url2)){
//			imageView.setTag(movie.client_placard_url2);
//			System.out.println("position-cu-xiao--------        "+position +"       "+currentPosition);
//			Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext, movie.client_placard_url2, new ImageCallback() {
//				
//				@Override
//				public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
//					String url = (String)imageView.getTag();
//					if(url.equals(bitmapUrl)){
//						loadingImagView.setVisibility(View.GONE);
//						imageView.setImageBitmapResetBase(bitmap, true);
//					}
//				}
//			}, AsyncImageLoader.CACHE_TYPE_SD,false);
//			if(bitmap!=null){
//				loadingImagView.setVisibility(View.GONE);
//				imageView.setImageBitmapResetBase(bitmap, true);
//			}
//		}
//		if(isCanForSearchSd){
//		for(int i = 0;i<mMovieNews.size();i++){
//			if(!BaseDBUtil.isHasSDpath(mContext,mMovieNews.get(i).client_placard_url2)){
//				isCanLoadLargePic = false;
//				System.out.println("ishassd  false-----------    "+isCanLoadLargePic);
//				break;
//			}
//			System.out.println("ishassd  for-----------    "+isCanLoadLargePic);
//		}
//		}
//		//大图
//		if(isCanLoadLargePic&&NetImageView.isWifi(mContext)){
//			isCanForSearchSd = false;
			System.out.println("ishassd true-----------    "+isCanLoadLargePic);
		if(movie!=null&&URLUtil.isHttpUrl(movie.client_placard_url1)){
			imageView.setTag(movie.client_placard_url1);
			System.out.println("position-cu---------        "+position +"       "+currentPosition);
			Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext, movie.client_placard_url1, new ImageCallback() {
				
				@Override
				public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
					String url = (String)imageView.getTag();
					if(url.equals(bitmapUrl)){
						loadingImagView.setVisibility(View.GONE);
						imageView.setImageBitmapResetBase(bitmap, true);
					}
				}
			}, AsyncImageLoader.CACHE_TYPE_SD,false);
			if(bitmap!=null){
				loadingImagView.setVisibility(View.GONE);
				imageView.setImageBitmapResetBase(bitmap, true);
			}
		}
//		}
		
		
		
		
		((ViewPager)container).addView(view);
		views.put(position, imageView);
//		imageView.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if(movieInfo.movies.size()>0){
//					
//					Intent intent = new Intent();
////					intent.putExtra("currentMoviePosition", currentMoviePosition);
//					
//					System.out.println("m_id      "+movieInfo.movies.get(position).m_id);
//					intent.putExtra("m_id",movieInfo.movies.get(position).m_id);
//					intent.putExtra("name", movieInfo.movies.get(position).name);
//					intent.putExtra("rate",  movieInfo.movies.get(position).rate);
//					intent.putExtra("comment_count",  movieInfo.movies.get(position).comment_count);
//					intent.putExtra("director",  movieInfo.movies.get(position).director);
//					intent.putExtra("main_actors",  movieInfo.movies.get(position).main_actor);
//					intent.putExtra("type",  movieInfo.movies.get(position).type);
//					intent.putExtra("release_date",  movieInfo.movies.get(position).release_date);
//					intent.putExtra("country",  movieInfo.movies.get(position).country);
//					intent.putExtra("introduce",  movieInfo.movies.get(position).introduce);
//					intent.putExtra("cover_image_url",  movieInfo.movies.get(position).client_placard_url1);
//					intent.putExtra("trailersAndroid",  movieInfo.movies.get(position).trailersAndroid);
//					intent.putExtra("upcomming",  movieInfo.movies.get(position).upcomming);
//					
//					intent.setClass(mContext, MovieDetailActivity.class);
//					mContext.startActivity(intent);
//					}
//			}
//		});
		return view;
	}


	@Override
	public boolean isViewFromObject(View view, Object object) {
		
		return view == ((View) object);
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
		// TODO Auto-generated method stub

	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startUpdate(View container) {
		// TODO Auto-generated method stub

	}
	
	

}
