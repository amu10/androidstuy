package com.szcmcc.movie.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmcc.sdk.CmccDataStatistics;
import com.itotem.view.touchimageview.GestureDetector;
import com.itotem.view.touchimageview.ImageViewTouch;
import com.itotem.view.touchimageview.PagerAdapter;
import com.itotem.view.touchimageview.ScaleGestureDetector;
import com.itotem.view.touchimageview.ViewPager;
import com.itotem.view.touchimageview.ViewPager.OnPageChangeListener;
import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.MovieInfo;
import com.szcmcc.movie.bean.MovieTidbits;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.view.LoadingImagView;

public class MoviePicsAct extends BaseActivity {
	private com.szcmcc.movie.view.BackImageView back = null;
	private ImageAdapter adapter_main;
	private ViewPager viewPager = null;
	private boolean isMoviesLoadAll;
	private boolean isFullScrean = false;
	private ImageView backGround;
	private LinearLayout bottom;
	private RelativeLayout top;
	private String cover_image_url = "";
	MovieInfo movieInfo = null;
	TextView picTotalText, picCountText;
	private boolean mOnPagerScoll = false;// 是否在页面滑动状态
	private static final String TAG = "MyOnScaleGestureListener";
	private boolean isCanLoadLargePic = true;
	private boolean isCanForLoadSd = true;
	ArrayList<MovieTidbits> list = new ArrayList<MovieTidbits>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moviepicsact);
		initTitleBar();
		initView();
		initData();
//		setListener();
//		setupOnTouchListeners(viewPager);
//		// final ImageViewTouch imageView = new ImageViewTouch(this);
//		back = (com.szcmcc.movie.view.BackImageView) findViewById(R.id.back);
//		top = (LinearLayout) findViewById(R.id.top);
//		bottom = (LinearLayout) findViewById(R.id.bottom);
//		picTotalText = (TextView) findViewById(R.id.pic_total_text);
//		picCountText = (TextView) findViewById(R.id.pic_count_text);
//
//		viewPager = (ViewPager) findViewById(R.id.viewPager);
//		// if(!isMoviesLoadAll){
//		// new GetMovieInfoTask(this).execute();
//		// }
//		Intent intent = getIntent();
//		ArrayList<MovieTidbits> tidbitsUrlList = (ArrayList<MovieTidbits>) intent.getSerializableExtra("tidbitsUrlList");
//		adapter_main = new ImageAdapter(this);
//		
//		adapter_main.addItem(tidbitsUrlList);
//		viewPager.setAdapter(adapter_main);
//		picTotalText.setText(adapter_main.getCount() + "");
//		picCountText.setText(1 + "");
//		viewPager.setCurrentItem(0, false);
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position, int prePosition) {
				// TODO Auto-generated method stub
				picCountText.setText(position + 1 + "");
				ImageViewTouch preImageView = adapter_main.views.get(prePosition);
				if (preImageView != null) {
					preImageView.setImageBitmapResetBase(
							preImageView.mBitmapDisplayed.getBitmap(), true);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				mOnPagerScoll = true;
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				if (state == ViewPager.SCROLL_STATE_DRAGGING) {
					mOnPagerScoll = true;
				} else if (state == ViewPager.SCROLL_STATE_SETTLING) {
					mOnPagerScoll = false;
				} else {
					mOnPagerScoll = false;
				}
			}
		});
		setupOnTouchListeners(viewPager);
		
//		viewPager.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(MoviePicsAct.this, "aaaaa", Toast.LENGTH_SHORT).show();
//				
//			}
//		});
	}
	private void initView() {
		back = (com.szcmcc.movie.view.BackImageView) findViewById(R.id.back);
		top = (RelativeLayout) findViewById(R.id.top);
		bottom = (LinearLayout) findViewById(R.id.bottom);
		picTotalText = (TextView) findViewById(R.id.pic_total_text);
		picCountText = (TextView) findViewById(R.id.pic_count_text);
		backGround = (ImageView) findViewById(R.id.bigimage);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		
	}
	
	private void changeFromDownToUpOut(Activity activity, View layout) {
		final Animation animation = AnimationUtils.loadAnimation(activity, R.anim.downtoup_out);
		layout.startAnimation(animation);
	}

	private void changeFromUpToDownOut(Activity activity, View layout) {
		final Animation animation = AnimationUtils.loadAnimation(activity, R.anim.uptodown_out);
		layout.startAnimation(animation);
	}
	private void changeFromDownToUpIn(Activity activity, View layout) {
		final Animation animation = AnimationUtils.loadAnimation(activity, R.anim.downtoup_in);
		layout.startAnimation(animation);
	}

	private void changeFromUpToDownIn(Activity activity, View layout) {
		final Animation animation = AnimationUtils.loadAnimation(activity, R.anim.uptodown_in);
		layout.startAnimation(animation);
	}
	private void initData(){
		Intent intent = getIntent();
		int position = 0;
		
		ArrayList<MovieTidbits> tidbitsUrlList = (ArrayList<MovieTidbits>) intent.getSerializableExtra("tidbitsUrlList");
		position = intent.getIntExtra("position", 0);
		
		try{
		for(int i = 0;i<tidbitsUrlList.size();i++){
			if(!tidbitsUrlList.get(i).client_tidbits_url_small.trim().equals("")&&tidbitsUrlList.get(i).client_tidbits_url_small.trim()!=null&&!tidbitsUrlList.get(i).client_tidbits_url_small.trim().equals("null")){
				list.add(tidbitsUrlList.get(i));
			}
		}
		cover_image_url = intent.getExtras().getString("cover_image_url");
//		System.out.println("cover_image_url============"+cover_image_url);
//		2013.05.27  测试李娜说不要背景
//		setImageUrl(backGround, cover_image_url);
		adapter_main = new ImageAdapter(this);
		
		adapter_main.addItem(list);
		viewPager.setAdapter(adapter_main);
		picTotalText.setText(adapter_main.getCount() + "");
		picCountText.setText((1 + position)+ "");
		viewPager.setCurrentItem(position, false);
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		
	}

	private void setImageUrl(final ImageView imageView, String url) {
		if (TextUtils.isEmpty(url)) {
			imageView.setImageBitmap(null);
			return;
		}
		if (url.equals("null")) {
			imageView.setImageBitmap(null);
			return;
		}
		
		imageView.setTag(url);
		Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(this, url, new ImageCallback() {

			@Override
			public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
				String url = (String) imageView.getTag();
				if (url.equals(bitmapUrl)) {
					imageView.setImageBitmap(bitmap);
				}
			}
		}, AsyncImageLoader.CACHE_TYPE_SD, true);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		}
	}
	
	class GetMovieInfoTask extends MovieAsyncTask<String, String, MovieInfo> {
		public String exception;

		public GetMovieInfoTask(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected MovieInfo doInBackground(String... params) {

			try {
				movieInfo = lib.getAllMovie("1");
				adapter_main.setMovieInfo(movieInfo);
				// list =movieInfo.movies;
			} catch (HttpException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (IOException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (JSONException e) {
				exception = getResources().getString(R.string.exception_json);
				e.printStackTrace();
			} catch(Exception e){
				e.printStackTrace();
			}
			return movieInfo;
		}

		@Override
		protected void onPostExecute(MovieInfo result) {
			super.onPostExecute(result);
			if (result != null && result.isSuccess()) {
				System.out.println("result.movies        " + result.movies);
				// adapter_main.addItem(result.movies);

				isMoviesLoadAll = true;
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	public class ImageAdapter extends PagerAdapter {
		protected static final String TAG = "HomepageAdapter";
		public Map<Integer, ImageViewTouch> views = new HashMap<Integer, ImageViewTouch>();
		private ArrayList<MovieTidbits> mMovieNews;
		private Activity mContext;
		private MovieApplication app;
		private MovieInfo movieInfo;//没用到
//		private Handler handler = null;
//		public void setHandler(Handler handler){
//			this.handler = handler;
//		}
		public ImageAdapter(Activity context) {
			mContext = context;
			mMovieNews = new ArrayList<MovieTidbits>();
			app = (MovieApplication)mContext.getApplicationContext();
		}
		
		public void addItem(List<MovieTidbits> movieList){
			if(movieList!=null){
			mMovieNews.addAll(movieList);
			notifyDataSetChanged();
			}
		}
		
		public MovieTidbits getItem(int position){
			if(position>=0&&position<mMovieNews.size()){
				return mMovieNews.get(position);
			}
			return null;
		}
		
		public void setMovieInfo(MovieInfo movieInfo){
			this.movieInfo = movieInfo;
//			notifyDataSetChanged();
		}

		@Override
		public void destroyItem(View container, int position, Object object) {

//			ImageView imageView = (ImageView)object;
//			((ViewPager) container).removeView(imageView);
			View view = (View)object;
			((ViewPager)container).removeView(view);
			views.remove(position);
//			imageView.mBitmapDisplayed.recycle();//导致异常问题.所调用了已经调用recyle的bitmap
//			imageView.clear();
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
			MovieTidbits movie = mMovieNews.get(position);
//			final ImageView imageView = new ImageView(mContext);
//			imageView.setScaleType(ScaleType.FIT_XY);
//			imageView.setImageResource(R.drawable.loading_bg);
			View view = LayoutInflater.from(mContext).inflate(R.layout.moviepicloading_item, null);
			final ImageViewTouch imageView = (ImageViewTouch) view.findViewById(R.id.moviepic_viewpager_imag);
			final LoadingImagView loadingImagView = (LoadingImagView) view.findViewById(R.id.moviepic_loading_viewpager_progress);
			loadingImagView.setVisibility(View.VISIBLE);
//			final ImageViewTouch imageView = new ImageViewTouch(mContext);
//			imageView.setScaleType(ScaleType.FIT_XY);//无法缩放了此属性
//			imageView.setImageResource(R.drawable.loading_bg);
//			imageView.setLayoutParams(new LayoutParams(
//					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//			imageView.setBackgroundColor(Color.BLACK);
			imageView.setFocusableInTouchMode(true);
			
//			if(movie!=null&&URLUtil.isHttpUrl(movie.client_tidbits_url_small)){
//				imageView.setTag(movie.client_tidbits_url_small);
//				Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext, movie.client_tidbits_url_small, new ImageCallback() {
//					
//					@Override
//					public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
//						String url = (String)imageView.getTag();
//						if(url.equals(bitmapUrl)){
//							loadingImagView.setVisibility(View.GONE);
//							imageView.setImageBitmapResetBase(bitmap, true);
//						}
//					}
//				}, AsyncImageLoader.CACHE_TYPE_SD,false);
//				if(bitmap!=null){
//					loadingImagView.setVisibility(View.GONE);
//					imageView.setImageBitmapResetBase(bitmap, true);
//				}
//			}
//			
//			if(isCanForLoadSd)
//				for (int i = 0;i < list.size(); i++) {
//					if (!BaseDBUtil.isHasSDpath(MoviePicsAct.this, list.get(i).client_tidbits_url_small)) {
//						isCanLoadLargePic = false;
//						break;
//					}
//				}
//			if(isCanLoadLargePic &&NetImageView.isWifi(MoviePicsAct.this)){
//				isCanForLoadSd = false;
				if(movie!=null&&URLUtil.isHttpUrl(movie.client_tidbits_url_large)){
					imageView.setTag(movie.client_tidbits_url_large);
					Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext, movie.client_tidbits_url_large, new ImageCallback() {
						
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
//			}
			
			((ViewPager)container).addView(view);
			views.put(position, imageView);
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

	@Override
	public void onStart() {
		super.onStart();
		mPaused = false;
	}

	@Override
	public void onStop() {
		super.onStop();
		mPaused = true;
	}
	@Override
	protected void onDestroy() {
//		ImageViewTouch imageView = getCurrentImageView();
//		imageView.mBitmapDisplayed.recycle();//导致异常问题.所调用了已经调用recyle的bitmap
//		imageView.clear();
		super.onDestroy();
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent m) {
		if (mPaused)
			return true;
		// delayHideControls();
		return super.dispatchTouchEvent(m);
	}
	
	private ImageViewTouch getCurrentImageView() {
		return (ImageViewTouch) adapter_main.views.get((viewPager
				.getCurrentItem()));
	}
	
	private ScaleGestureDetector mScaleGestureDetector;// 放大缩小手势检测器
	private GestureDetector mGestureDetector;//手势检测器
	private boolean mOnScale = false;//是否处于缩放状态
	private boolean mPaused;
	private boolean isClick = false;
	
	private void setupOnTouchListeners(View rootView) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
			mScaleGestureDetector = new ScaleGestureDetector(this,
					new MyOnScaleGestureListener());
		}
		mGestureDetector = new GestureDetector(this, new MyGestureListener());
		
		OnTouchListener rootListener = new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				// NOTE: gestureDetector may handle onScroll..
//				Log.e(TAG,"onTouch ...mOnScale:"+mOnScale+", mOnPagerScoll:"+mOnPagerScoll);
				//float x = 0,y=0,mx = 0,my=0;
				if(event.getAction() == event.ACTION_DOWN){
					isClick = true;
					System.out.println("actiondown==="+isClick);
				}else if(event.getAction() == event.ACTION_MOVE){
					isClick = false;
					System.out.println("actionmove==="+isClick);
				}else if(event.getAction()==event.ACTION_UP){
					if(isClick){
						System.out.println("aaaaaaaaa");
						if(top.getVisibility()==View.VISIBLE){
						changeFromDownToUpOut(MoviePicsAct.this,top);
						changeFromUpToDownOut(MoviePicsAct.this,bottom);
						top.setVisibility(View.GONE);
						bottom.setVisibility(View.GONE);
						}else if(top.getVisibility()==View.GONE){
							changeFromDownToUpIn(MoviePicsAct.this,bottom);
							changeFromUpToDownIn(MoviePicsAct.this,top);
							top.setVisibility(View.VISIBLE);
							bottom.setVisibility(View.VISIBLE);
						}
						return true;
					}
				}
				if (!mOnScale) {
					if (!mOnPagerScoll) {
						Log.e(TAG," 不在滑动 , 不在缩放");
						mGestureDetector.onTouchEvent(event);
					}
				}
				ImageViewTouch imageView = getCurrentImageView();
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
					if (!mOnPagerScoll) {
						Log.e(TAG," 不在滑动 ,开始缩放");
						mScaleGestureDetector.onTouchEvent(event);
					}
				}
				
				if (!mOnScale) {
//					Log.e(TAG,"  不在缩放");
					Matrix m = imageView.getImageViewMatrix();
					if(imageView.mBitmapDisplayed.getBitmap() == null){
						Log.i(TAG," imageView.mBitmapDisplayed.getBitmap() is null ");
						return false;//可以触发onPageChangeListener
//						return true;//不可触发其他事件
					}
					RectF rect = new RectF(0, 0, imageView.mBitmapDisplayed
							.getBitmap().getWidth(), imageView.mBitmapDisplayed
							.getBitmap().getHeight());
					m.mapRect(rect);
					// Log.d(TAG, "rect.right: " + rect.right + ", rect.left: "
					// + rect.left + ", imageView.getWidth(): "
					// + imageView.getWidth());
					// 图片超出屏幕范围后移动
					if (!(rect.right > imageView.getWidth() + 0.1 && rect.left < -0.1)) {
						try {
								viewPager.onTouchEvent(event);
						} catch (ArrayIndexOutOfBoundsException e) {
							// why?
						}
					}
				}
//				return false;//放大后无法移动,会直接跳到下副图片
				// We do not use the return value of
				// mGestureDetector.onTouchEvent because we will not receive
				// the "up" event if we return false for the "down" event.
				return true;
			}
		};
		rootView.setOnTouchListener(rootListener);
	}

	private class MyOnScaleGestureListener extends
			ScaleGestureDetector.SimpleOnScaleGestureListener {

		private static final String TAG = "MyOnScaleGestureListener";
		float currentScale;
		float currentMiddleX;
		float currentMiddleY;

		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {

			final ImageViewTouch imageView = getCurrentImageView();

//			Log.d(TAG, "currentScale: " + currentScale + ", maxZoom: "
//					+ imageView.mMaxZoom);
			if (currentScale > imageView.mMaxZoom) {
				imageView
						.zoomToNoCenterWithAni(currentScale
								/ imageView.mMaxZoom, 1, currentMiddleX,
								currentMiddleY);
				currentScale = imageView.mMaxZoom;
				imageView.zoomToNoCenterValue(currentScale, currentMiddleX,
						currentMiddleY);
			} else if (currentScale < imageView.mMinZoom) {
				imageView.zoomToNoCenterWithAni(currentScale,
						imageView.mMinZoom, currentMiddleX, currentMiddleY);
				currentScale = imageView.mMinZoom;
				imageView.zoomToNoCenterValue(currentScale, currentMiddleX,
						currentMiddleY);
			} else {
				imageView.zoomToNoCenter(currentScale, currentMiddleX,
						currentMiddleY);
			}

			imageView.center(true, true);

			// NOTE: 延迟修正缩放后可能移动问题
			imageView.postDelayed(new Runnable() {
				@Override
				public void run() {
					mOnScale = false;
					imageView.setEnabled(true);
				}
			}, 300);
			// Log.d(TAG, "gesture onScaleEnd");
		}

		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
//			 Log.d(TAG, "gesture onScaleStart");
			mOnScale = true;
			return true;
		}

		@Override
		public boolean onScale(ScaleGestureDetector detector, float mx, float my) {
//			 Log.d(TAG, "gesture onScale");
			ImageViewTouch imageView = getCurrentImageView();
			float ns = imageView.getScale() * detector.getScaleFactor();

			currentScale = ns;
			currentMiddleX = mx;
			currentMiddleY = my;
//			Log.e(TAG," on Scale : currentScale:"+currentScale+" , currentMiddleX:"+currentMiddleX+" , currentMiddleY:"+currentMiddleY+" , detector.isInProgress():"+detector.isInProgress());
			if (detector.isInProgress()) {
				imageView.zoomToNoCenter(ns, mx, my);
			}
			return true;
		}
	}
	
	private class MyGestureListener extends
	GestureDetector.SimpleOnGestureListener {

@Override
public boolean onScroll(MotionEvent e1, MotionEvent e2,
		float distanceX, float distanceY) {
//	 Log.d(TAG, "gesture onScroll...mOnScale:"+mOnScale+" ,mPaused:"+mPaused);
	if (mOnScale) {
		return true;
	}
	if (mPaused) {
		return false;
	}
	ImageViewTouch imageView = getCurrentImageView();
	imageView.panBy(-distanceX, -distanceY);
	imageView.center(true, true);

	// 超出边界效果去掉这个
	imageView.center(true, true);

	return true;
}

		@Override
		public boolean onUp(MotionEvent e) {
			// getCurrentImageView().center(true, true);
			return super.onUp(e);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			if (mPaused) {
				return false;
			}
			ImageViewTouch imageView = getCurrentImageView();
			// Switch between the original scale and 3x scale.
			if (imageView.mBaseZoom < 1) {
				if (imageView.getScale() > 2F) {
					imageView.zoomTo(1f);
				} else {
					imageView.zoomToPoint(3f, e.getX(), e.getY());
				}
			} else {
				if (imageView.getScale() > (imageView.mMinZoom + imageView.mMaxZoom) / 2f) {
					imageView.zoomTo(imageView.mMinZoom);
				} else {
					imageView.zoomToPoint(imageView.mMaxZoom, e.getX(),
							e.getY());
				}
			}

			return true;
		}
	}
	public void onResume(){
		super.onResume();
		CmccDataStatistics.onStart(this);
		}
	public void onPause() {
		super.onPause();
		CmccDataStatistics.onStop(this);
		}
}
