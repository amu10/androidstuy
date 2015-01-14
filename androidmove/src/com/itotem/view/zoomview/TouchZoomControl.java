package com.itotem.view.zoomview;

import com.itotem.view.touchimageview.GestureDetector;
import com.itotem.view.touchimageview.ScaleGestureDetector;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.animation.ScaleAnimation;

public class TouchZoomControl {

	protected static final String TAG = TouchZoomControl.class.getSimpleName();
	Context activity;
	private GestureDetector mGestureDetector;// 手势检测器
	private ScaleGestureDetector mScaleGestureDetector;// 放大缩小手势检测器

	private boolean mOnScale = false;			//是否在缩放
//	private boolean mOnPagerScoll = false;	//是否在左右滑动
	ViewZoomTouch zoomView;
	
	private final double mMaxZoomLevel = 2;// 放大的最大倍数
	private final double mMinZoomLevel = 1D;// 缩小的最小倍数
	private double mZoomLevel = 1.0D;
	
	public  void setupOnTouchListeners(ViewZoomTouch rootView) {
		zoomView = rootView;
		activity = rootView.getContext();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
			mScaleGestureDetector = new ScaleGestureDetector(activity,
					new MyOnScaleGestureListener());
		}
		mGestureDetector = new GestureDetector(activity, new MyGestureListener());
		OnTouchListener rootListener = new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				// NOTE: gestureDetector may handle onScroll..
				// Log.e(TAG,"onTouch ...mOnScale:"+mOnScale+", mOnPagerScoll:"+mOnPagerScoll);
				if (!mOnScale) {
//					if (!mOnPagerScoll) {
						// Log.e(TAG," 不在滑动 , 不在缩放");
						mGestureDetector.onTouchEvent(event);
//					}
				}
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
//					if (!mOnPagerScoll) {
						// Log.e(TAG," 不在滑动 ");
						mScaleGestureDetector.onTouchEvent(event);
//					}
				}
				return true;
			}
		};
		rootView.setOnTouchListener(rootListener);
	}

	private class MyGestureListener extends
			GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			Log.d(TAG, "gesture onScroll...mOnScale:" + mOnScale );
			if (mOnScale) {
				return true;
			}

//			ViewZoomTouch imageView = getCurrentImageView();
			zoomView.panBy(-distanceX, -distanceY);
			zoomView.center(true, true);

			// 超出边界效果去掉这个
			zoomView.center(true, true);

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
//			ViewZoomTouch imageView = getCurrentImageView();
			// Switch between the original scale and 3x scale.
			/*
			if (zoomView.mBaseZoom < 1) {
				if (zoomView.getScale() > 2F) {
					zoomView.zoomTo(1f);
				} else {
					zoomView.zoomToPoint(3f, e.getX(), e.getY());
				}
			} else {
				if (zoomView.getScale() > (zoomView.mMinZoom + zoomView.mMaxZoom) / 2f) {
					zoomView.zoomTo(zoomView.mMinZoom);
				} else {
					zoomView.zoomToPoint(zoomView.mMaxZoom, e.getX(),
							e.getY());
				}
			}
			*/
			if(zoomView.mCurZoom <=1){
				zoomAroundVisiblePoint((int)e.getX(),(int)e.getY(), 2d);
			}else if(zoomView.mCurZoom >1){
				zoomAroundVisiblePoint((int)e.getX(),(int)e.getY(), 1d);
			}
			return true;
		}
	}

	private class MyOnScaleGestureListener extends
			ScaleGestureDetector.SimpleOnScaleGestureListener {

		float mCurrentScale = 1.0F;
		// float currentScale;
		float currentMiddleX;
		float currentMiddleY;

		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {

//			final ViewZoomTouch imageView = getCurrentImageView();
			// zoomAroundVisiblePoint((int) this.currentMiddleX,
			// (int) this.currentMiddleY, this.mCurrentScale);
			zoomView.clearAnimation();
//			Log.e(TAG,"on Scale end : mZoomLevel"+mZoomLevel);
//			Log.d(TAG, "currentScale: " + mCurrentScale + ", maxZoom: "
//					+ zoomView.mMaxZoom + ", minZoom:" + zoomView.mMinZoom);
			// if (mCurrentScale > imageView.mMaxZoom) {
			if (mCurrentScale > mMaxZoomLevel) {
				// // Log.d(TAG, "onScaleEnd if 1,极限放大了,最后有个缩小的动画");
				// imageView
				// .zoomToNoCenterWithAni(currentScale
				// / imageView.mMaxZoom, 1, currentMiddleX,
				// currentMiddleY);
				// mCurrentScale = imageView.mMaxZoom;
				mCurrentScale = (float) mMaxZoomLevel;
//				mCurrentScale = (float) mMinZoomLevel;
				// imageView.zoomToNoCenterValue(currentScale, currentMiddleX,
				// currentMiddleY);
				// } else if (mCurrentScale < imageView.mMinZoom) {
			} else if (mCurrentScale < mMinZoomLevel) {
				// // Log.d(TAG, "onScaleEnd if 2,极限缩小了,最后有个放大的动画");
				// imageView.zoomToNoCenterWithAni(currentScale,
				// imageView.mMinZoom, currentMiddleX, currentMiddleY);
				// mCurrentScale = imageView.mMinZoom;
				mCurrentScale = (float) mMinZoomLevel;
//				mCurrentScale = (float) mMaxZoomLevel;
				// imageView.zoomToNoCenterValue(currentScale, currentMiddleX,
				// currentMiddleY);
			} else {
				// // Log.d(TAG, "onScaleEnd if 3,正常放大缩小");
				// imageView.zoomToNoCenter(currentScale, currentMiddleX,
				// currentMiddleY);
			}
			zoomAroundVisiblePoint((int) this.currentMiddleX,
					(int) this.currentMiddleY, this.mCurrentScale);
			// imageView.clearAnimation();

			zoomView.center(true, true);

			// NOTE: 延迟修正缩放后可能移动问题
			zoomView.postDelayed(new Runnable() {
				@Override
				public void run() {
					mOnScale = false;
					mCurrentScale = 1;
//					mCurrentScale = (float) mZoomLevel;
				}
			}, 300);
			// Log.d(TAG, "gesture onScaleEnd");
		}

		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			// Log.d(TAG, "gesture onScaleStart");
			// mCurrentScale = 1.0F;
			mOnScale = true;
			return true;
		}

		// private float mFocusX;
		// private float mFocusY;

		@Override
		public boolean onScale(ScaleGestureDetector detector, float mx, float my) {
			// Log.d(TAG, "gesture onScale");
//			ViewZoomTouch zoomView = getCurrentImageView();
			// float ns = zoomView.getScale() * detector.getScaleFactor();

			// currentScale = ns;
			currentMiddleX = mx;
			currentMiddleY = my;
			if (detector.isInProgress()) {
//				 Log.e(TAG," on Scale : mZoomLevel:"+mZoomLevel+",currentScale:"+mCurrentScale+" , currentMiddleX:"+currentMiddleX+" , currentMiddleY:"+currentMiddleY+" , detector.getScaleFactor():"+detector.getScaleFactor());
				// zoomView.zoomToNoCenter(ns, mx, my);
				// for (int i = 0; ; i = 1)
				// {
				// float f = this.mCurrentScale * detector.getScaleFactor();
				/*
				if(mCurrentScale == mMinZoomLevel){
					mCurrentScale =mMaxZoomLevel;
				}else if(mCurrentScale == mMaxZoomLevel){
					mCurrentScale =mMinZoomLevel;
				}
				*/
//				/*
				float f = (float) (mCurrentScale * detector.getScaleFactor());
				ScaleAnimation localScaleAnimation = new ScaleAnimation(
						this.mCurrentScale, f, this.mCurrentScale, f, mx, my);
				localScaleAnimation.setFillAfter(true);
				localScaleAnimation.setDuration(50L);
				zoomView.startAnimation(localScaleAnimation);
				this.mCurrentScale = f;
//				*/
				// }
			}
			return true;
		}
	}

	public void zoomAroundVisiblePoint(int paramInt1, int paramInt2,
			double scaleDouble) {
		double d = checkMinMax(scaleDouble);
//		Log.e(TAG,"checkMinMax : "+ d);
//		double d = scaleDouble;
		int i = zoomView.getScrollX();
		int j = zoomView.getScrollY();
//		Log.w(TAG," paramint 1:"+paramInt1+" , paramInt2:"+paramInt2+",zoomViewX:"+i+", zoomViewY:"+j);
		doZoom((int) (d * (i + paramInt1) - paramInt1), (int) (d
				* (j + paramInt2) - paramInt2), d);
	}
	
	private double checkMinMax(double scaleDouble) {
//		Log.e(TAG,"scaleDouble: "+scaleDouble+" , mZoomLevel : "+mZoomLevel);
		if (scaleDouble * this.mZoomLevel > this.mMaxZoomLevel) {
			scaleDouble = this.mMaxZoomLevel / this.mZoomLevel;
		}
		if (scaleDouble * this.mZoomLevel <= this.mMinZoomLevel) {
			scaleDouble = this.mMinZoomLevel / this.mZoomLevel;
		}
		return scaleDouble/mZoomLevel;
//		return scaleDouble;
	}
	
	public void doZoom(int paramInt1, int paramInt2, double scaleDouble) {
		ViewGroup.LayoutParams localLayoutParams = zoomView.getLayoutParams();
//		int i = Math.round((float) (scaleDouble * localLayoutParams.width));
//		int j = Math.round((float) (scaleDouble * localLayoutParams.height));
//		Log.e(TAG,
//				"i="+i+",j="+j+
//				", width : "+zoomView.getWidth()+", height:"+zoomView.getHeight());
//		scaleDouble = i / localLayoutParams.width;
		int k = zoomView.getWidth() - zoomView.getPaddingRight()
				- zoomView.getPaddingLeft();
		 int h = (zoomView.getHeight() - zoomView.getPaddingBottom() -
				 zoomView.getPaddingTop());
//		if(i<=0||j<=0){
//			 i = Math.round((float) (scaleDouble * k));
//			 j = Math.round((float) (scaleDouble * h));
//			localLayoutParams.width = i;
//			localLayoutParams.height = j;			
//		}else{
//			localLayoutParams.width = i;
//			localLayoutParams.height = j;			
//		}
		 localLayoutParams.width = Math.round((float) (scaleDouble * k));
		 localLayoutParams.height = Math.round((float) (scaleDouble * h));
		zoomView.scaleChildView(paramInt1,paramInt2,scaleDouble);
		zoomView.setLayoutParams(localLayoutParams);
		this.mZoomLevel = (scaleDouble * this.mZoomLevel);
//		this.mZoomLevel = scaleDouble ;
//		Log.w(TAG," mZoomLevel : "+mZoomLevel);
//		 if (this.clamping){
//		 paramInt1 = clamp(paramInt1, k, localLayoutParams.width);
//		 }
		/*
		if(mZoomLevel == 1){
			zoomView.scrollTo(0, 0);//
		}else{
			zoomView.scrollTo(paramInt1, paramInt2);//			
		}
		*/
//		onZoom(scaleDouble);
	}
	
	  private int clamp(int paramInt1, int paramInt2, int paramInt3)
	  {
	    if ((paramInt2 >= paramInt3) || (paramInt1 < 0));
	    for (paramInt1 = 0; ; paramInt1 = paramInt3 - paramInt2)
	      do
	        return paramInt1;
	      while (paramInt2 + paramInt1 <= paramInt3);
	  }
	  
}
