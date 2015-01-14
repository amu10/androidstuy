package com.itotem.view.touchimageview;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class TouchImageActivity extends Activity 
{
	private static final String TAG = TouchImageActivity.class.getSimpleName();

	private static final int REQUIRED_BITMAP_SIZE = 400;

	private static final int PAGER_MARGIN_DP = 40;

	private ViewPager mViewPager;//图片容器
	private TextView mPageShwo;//页码
	private TextView mPicName;//图片名称

	private ImagePagerAdapter mPagerAdapter;//图片人容器适配器

	private GestureDetector mGestureDetector;//手势检测器
	private ScaleGestureDetector mScaleGestureDetector;//放大缩小手势检测器

	private boolean mPaused;
	private boolean mOnScale = false;
	private boolean mOnPagerScoll = false;

	// 传入参数
	private List<String> mImageList;
	private int mPosition;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.viewpager);
//
//		mViewPager = (ViewPager) findViewById(R.id.viewPager);
//		mPageShwo = (TextView) findViewById(R.id.tv_page);
//		mPicName = (TextView) findViewById(R.id.tv_pic_name);

		final float scale = getResources().getDisplayMetrics().density;
		int pagerMarginPixels = (int) (PAGER_MARGIN_DP * scale + 0.5f);
		mViewPager.setPageMargin(pagerMarginPixels);
		mViewPager.setPageMarginDrawable(new ColorDrawable(Color.BLACK));

		mPagerAdapter = new ImagePagerAdapter();
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(mPageChangeListener);
		setupOnTouchListeners(mViewPager);

		// 参数传入
		mImageList = new ArrayList<String>();
		for (File file : new File("/sdcard/Photo/k800i").listFiles()) {
			mImageList.add(file.getPath());
		}
		mPosition = 0;

		mViewPager.setCurrentItem(mPosition, false);
		updateShowInfo();
	}

	private void updateShowInfo() {
		if (mImageList.size() > 0) {
			mPageShwo.setText(String.format("%d/%d", mPosition + 1,
					mImageList.size()));
			mPicName.setText(getPositionFileName(mPosition));
		}
	}

	private String getPositionFileName(int position) {
		String path = mImageList.get(position);
		String[] splits = path.split("/");
		String name = "";
		if (splits.length > 0) {
			name = splits[splits.length - 1];
		}

		return name;
	}

	// decodes image and scales it to reduce memory consumption
	private Bitmap decodeFile(File f) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = REQUIRED_BITMAP_SIZE;

			// Find the correct scale value. It should be the power of 2.
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
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

	private void setupOnTouchListeners(View rootView) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
			mScaleGestureDetector = new ScaleGestureDetector(this,
					new MyOnScaleGestureListener());
		}
		mGestureDetector = new GestureDetector(this, new MyGestureListener());

		OnTouchListener rootListener = new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				// NOTE: gestureDetector may handle onScroll..
				if (!mOnScale) {
					if (!mOnPagerScoll) {
						mGestureDetector.onTouchEvent(event);
					}
				}

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
					if (!mOnPagerScoll) {
						mScaleGestureDetector.onTouchEvent(event);
					}
				}

				ImageViewTouch imageView = getCurrentImageView();
				if (!mOnScale) {
					Matrix m = imageView.getImageViewMatrix();
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
							mViewPager.onTouchEvent(event);
						} catch (ArrayIndexOutOfBoundsException e) {
							// why?
						}
					}
				}

				// We do not use the return value of
				// mGestureDetector.onTouchEvent because we will not receive
				// the "up" event if we return false for the "down" event.
				return true;
			}
		};

		rootView.setOnTouchListener(rootListener);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent m) {
		if (mPaused)
			return true;
		// delayHideControls();
		return super.dispatchTouchEvent(m);
	}

	@Override
	protected void onDestroy() {
		ImageViewTouch imageView = getCurrentImageView();
		imageView.mBitmapDisplayed.recycle();
		imageView.clear();
		super.onDestroy();
	}

	private ImageViewTouch getCurrentImageView() {
		return (ImageViewTouch) mPagerAdapter.views.get((mViewPager
				.getCurrentItem()));
	}

	ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageSelected(int position, int prePosition) {
			// Log.d(TAG, "onPageSelected" + position + ", prePosition: "
			// + prePosition);
			ImageViewTouch preImageView = mPagerAdapter.views.get(prePosition);
			if (preImageView != null) {
				preImageView.setImageBitmapResetBase(
						preImageView.mBitmapDisplayed.getBitmap(), true);
			}
			mPosition = position;
			updateShowInfo();
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			// Log.d(TAG, "onPageScrolled");
			mOnPagerScoll = true;
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			// Log.d(TAG, "onPageScrollStateChanged: " + state);
			if (state == ViewPager.SCROLL_STATE_DRAGGING) {
				mOnPagerScoll = true;
			} else if (state == ViewPager.SCROLL_STATE_SETTLING) {
				mOnPagerScoll = false;
			} else {
				mOnPagerScoll = false;
			}
		}
	};

	private class MyGestureListener extends
			GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
//			 Log.d(TAG, "gesture onScroll...mOnScale:"+mOnScale+" ,mPaused:"+mPaused);
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

	private class MyOnScaleGestureListener extends
			ScaleGestureDetector.SimpleOnScaleGestureListener {

		float currentScale;
		float currentMiddleX;
		float currentMiddleY;

		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {

			final ImageViewTouch imageView = getCurrentImageView();

			Log.d(TAG, "currentScale: " + currentScale + ", maxZoom: "
					+ imageView.mMaxZoom);
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

			if (detector.isInProgress()) {
				imageView.zoomToNoCenter(ns, mx, my);
			}
			return true;
		}
	}

	private class ImagePagerAdapter extends PagerAdapter {
		public Map<Integer, ImageViewTouch> views = new HashMap<Integer, ImageViewTouch>();

		@Override
		public int getCount() {
			// Log.d(TAG, "getCount");
			return mImageList.size();
		}

		@Override
		public Object instantiateItem(View container, int position) {
			// Log.d(TAG, "instantiateItem");
			ImageViewTouch imageView = new ImageViewTouch(
					TouchImageActivity.this);
			imageView.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			imageView.setBackgroundColor(Color.BLACK);
			imageView.setFocusableInTouchMode(true);

			Bitmap b = decodeFile(new File(mImageList.get(position)));
			imageView.setImageBitmapResetBase(b, true);

			((ViewPager) container).addView(imageView);
			views.put(position, imageView);

			return imageView;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// Log.d(TAG, "destroyItem");
			ImageViewTouch imageView = (ImageViewTouch) object;
			imageView.mBitmapDisplayed.recycle();
			imageView.clear();
			((ViewPager) container).removeView(imageView);
			views.remove(position);
		}

		@Override
		public void startUpdate(View container) {
			// Log.d(TAG, "startUpdate");
		}

		@Override
		public void finishUpdate(View container) {
			// Log.d(TAG, "finishUpdate");
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// Log.d(TAG, "isViewFromObject");
			return view == ((ImageViewTouch) object);
		}

		@Override
		public Parcelable saveState() {
			// Log.d(TAG, "saveState");
			return null;
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
			// Log.d(TAG, "restoreState");
		}
	}
	
}
