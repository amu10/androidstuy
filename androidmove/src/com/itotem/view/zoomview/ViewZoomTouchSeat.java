/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.itotem.view.zoomview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class ViewZoomTouchSeat extends 
//ViewGroup 
//ImageView
//FrameLayout
LinearLayout
{

	@SuppressWarnings("unused")
	private static final String TAG = "ViewZoomTouch";

	// This is the base transformation which is used to show the image
	// initially. The current computation for this shows the image in
	// it's entirety, letterboxing as needed. One could choose to
	// show the image as cropped instead.
	//
	// This matrix is recomputed when we go from the thumbnail image to
	// the full size image.
	protected Matrix mBaseMatrix = new Matrix();

	// This is the supplementary transformation which reflects what
	// the user has done in terms of zooming and panning.
	//
	// This matrix remains the same when we go from the thumbnail image
	// to the full size image.
	protected Matrix mSuppMatrix = new Matrix();

	// This is the final matrix which is computed as the concatentation
	// of the base matrix and the supplementary matrix.
	protected final Matrix mDisplayMatrix = new Matrix();

	// Temporary buffer used for getting the values out of a matrix.
	private final float[] mMatrixValues = new float[9];

	// The current bitmap being displayed.
	public final RotateView mBitmapDisplayed = new RotateView(null);

	int mThisWidth = -1, mThisHeight = -1;

	public final float mMaxZoom = 1.4f;
	public final float mMinZoom = 1f;
	public float mBaseZoom;

	public double mCurZoom;
	TouchZoomControlSeat touchZoomControl;
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		mThisWidth = right - left;
		mThisHeight = bottom - top;
//		Log.w(TAG,"mThisWidth : "+mThisWidth+" , mThisHeight:"+mThisHeight);
		Runnable r = mOnLayoutRunnable;
		if (r != null) {
			mOnLayoutRunnable = null;
			r.run();
		}
		if (mBitmapDisplayed.getView() != null) {
			getProperBaseMatrix(mBitmapDisplayed, mBaseMatrix);
			setImageMatrix(getImageViewMatrix());
		}
//		TouchZoomControl touchZoomControl = new TouchZoomControl();
//		touchZoomControl.setupOnTouchListeners(this);
	}
	 private Matrix mMatrix;
	 
	  public void setImageMatrix(Matrix matrix) {
	        // collaps null and identity to just null
	        if (matrix != null && matrix.isIdentity()) {
	            matrix = null;
	        }
	        
	        // don't invalidate unless we're actually changing our matrix
	        if (matrix == null && !mMatrix.isIdentity() ||
	                matrix != null && !mMatrix.equals(matrix)) {
	            mMatrix.set(matrix);
//	            Log.e(TAG," setImageMatrix,,,matrix:"+matrix);
	            configureBounds();
	            invalidate();
	        }
	    }
	    
	protected Handler mHandler = new Handler();


	public void clear() {
		setZoomView(null,true);
	}

	private Runnable mOnLayoutRunnable = null;



	public void setZoomRotateViewResetBase(final RotateView rotateView,
			final boolean resetSupp) {
		final int viewWidth = getWidth();

		if (viewWidth <= 0) {
			mOnLayoutRunnable = new Runnable() {
				public void run() {
					setZoomRotateViewResetBase(rotateView, resetSupp);
				}
			};
			return;
		}

		if (rotateView.getView() != null) {
			getProperBaseMatrix(rotateView, mBaseMatrix);
//			setImageBitmap(bitmap.getView(), bitmap.getRotation());
			this.removeAllViews();
//			this.clear();
			addView(rotateView.getView());
		} else {
			mBaseMatrix.reset();
//			setImageBitmap(null);
		}

		if (resetSupp) {
			mSuppMatrix.reset();
		}
		setImageMatrix(getImageViewMatrix());
//		mMaxZoom = maxZoom();
//		mMinZoom = minZoom();
//		if(mMaxZoom<=0){
//			mMaxZoom = 1.8f;
//		}
//		if(mMinZoom<=0){
//			mMinZoom = 1f;
//		}
		mBaseZoom = getScale(mBaseMatrix);
	}

	// Center as much as possible in one or both axis. Centering is
	// defined as follows: if the image is scaled down below the
	// view's dimensions then center it (literally). If the image
	// is scaled larger than the view and is translated out of view
	// then translate it back into view (i.e. eliminate black bars).
	public void center(boolean horizontal, boolean vertical) {
		centerCharge(horizontal, vertical, false);
	}

	private void centerCharge(boolean horizontal, boolean vertical,
			boolean hasAni) {
		if (mBitmapDisplayed.getView() == null) {
			return;
		}

		Matrix m = getImageViewMatrix();

		RectF rect = new RectF(0, 0, mBitmapDisplayed.getView().getWidth(),
				mBitmapDisplayed.getView().getHeight());

		m.mapRect(rect);

		float height = rect.height();
		float width = rect.width();

		float deltaX = 0, deltaY = 0;

		if (vertical) {
			int viewHeight = getHeight();
			if (height < viewHeight) {
				deltaY = (viewHeight - height) / 2 - rect.top;
			} else if (rect.top > 0) {
				deltaY = -rect.top;
			} else if (rect.bottom < viewHeight) {
				deltaY = getHeight() - rect.bottom;
			}
		}

		if (horizontal) {
			int viewWidth = getWidth();
			if (width < viewWidth) {
				deltaX = (viewWidth - width) / 2 - rect.left;
			} else if (rect.left > 0) {
				deltaX = -rect.left;
			} else if (rect.right < viewWidth) {
				deltaX = viewWidth - rect.right;
			}
		}

		postTranslate(deltaX, deltaY);

		if (hasAni) {
			// Animation animation = new MatrixTransformAnimation(
			// getImageViewMatrix());
			// animation.setDuration(500);
			// animation.setFillAfter(true);
			// startAnimation(animation);
		} else {
			setImageMatrix(getImageViewMatrix());
		}
	}

	protected void centerWithAni(boolean horizontal, boolean vertical) {
		centerCharge(horizontal, vertical, true);
	}

	public ViewZoomTouchSeat(Context context) {
		super(context);
		init();
	}

	public ViewZoomTouchSeat(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	 private ScaleType mScaleType;
	private void init() {
		mMatrix     = new Matrix();
		  mScaleType  = ScaleType.FIT_CENTER;
//		setScaleType(ImageView.ScaleType.MATRIX);
		  touchZoomControl = new TouchZoomControlSeat();
		touchZoomControl.setupOnTouchListeners(this);
	}
	
	public TouchZoomControlSeat getTouchZoomControl(){
		return touchZoomControl;
	}

	protected float getValue(Matrix matrix, int whichValue) {
		matrix.getValues(mMatrixValues);
		return mMatrixValues[whichValue];
	}

	// Get the scale factor out of the matrix.
	public float getScale(Matrix matrix) {
		return getValue(matrix, Matrix.MSCALE_X);
	}

	public float getScale() {
		return getScale(mSuppMatrix);
	}

	// Setup the base matrix so that the image is centered and scaled properly.
	private void getProperBaseMatrix(RotateView bitmap, Matrix matrix) {
		float viewWidth = getWidth();
		float viewHeight = getHeight();

		float w = bitmap.getWidth();
		float h = bitmap.getHeight();
		matrix.reset();

		// We limit up-scaling to 3x otherwise the result may look bad if it's
		// a small icon.
		float widthScale = Math.min(viewWidth / w, 3.0f);
		float heightScale = Math.min(viewHeight / h, 3.0f);

		// float widthScale = viewWidth / w;
		// float heightScale = viewHeight / h;
		float scale = Math.min(widthScale, heightScale);
		// Log.d(TAG, "scale: " + scale);

		matrix.postConcat(bitmap.getRotateMatrix());

		// if (scale < 1F) {
		matrix.postScale(scale, scale);

		matrix.postTranslate((viewWidth - w * scale) / 2F, (viewHeight - h
				* scale) / 2F);
		// } else {
		// matrix.postTranslate((viewWidth - w) / 2F, (viewHeight - h) / 2F);
		// }
	}

	// Combine the base matrix and the supp matrix to make the final matrix.
	public Matrix getImageViewMatrix() {
		// The final matrix is computed as the concatentation of the base matrix
		// and the supplementary matrix.
		mDisplayMatrix.set(mBaseMatrix);
		mDisplayMatrix.postConcat(mSuppMatrix);
		return mDisplayMatrix;
	}

	static final float SCALE_RATE = 1.25F;

	// Sets the maximum zoom, which is a scale relative to the base matrix. It
	// is calculated to show the image at 400% zoom regardless of screen or
	// image orientation. If in the future we decode the full 3 megapixel image,
	// rather than the current 1024x768, this should be changed down to 200%.
	protected float maxZoom() {
		if (mBitmapDisplayed.getView() == null) {
//			Log.w(TAG,"mBitmapDisplayed.getView() == null");
			return 1F;
		}

		float fw = (float) mBitmapDisplayed.getWidth() / (float) mThisWidth;
		float fh = (float) mBitmapDisplayed.getHeight() / (float) mThisHeight;
		float max = Math.max(fw, fh) * 4;
		Log.w(TAG,"max : "+max+" mBitmapDisplayed width : "+mBitmapDisplayed.getWidth()+" mBitmapDisplayed height : "+mBitmapDisplayed.getHeight());
		if(max==0){
			return 2;
		}
		return max;
	}

	protected float minZoom() {
		float baseScale = getScale(mBaseMatrix);
		if (baseScale < 1) {
			return 1F;
		} else {
			return 1F / baseScale;
		}
	}

	protected void zoomTo(float scale, float centerX, float centerY) {
		if (scale > mMaxZoom) {
			scale = mMaxZoom;
		}

		float oldScale = getScale();
		float deltaScale = scale / oldScale;

		mSuppMatrix.postScale(deltaScale, deltaScale, centerX, centerY);
		setImageMatrix(getImageViewMatrix());
		center(true, true);
	}

	protected void zoomTo(final float scale, final float centerX,
			final float centerY, final float durationMs) {
		final float incrementPerMs = (scale - getScale()) / durationMs;
		final float oldScale = getScale();
		final long startTime = System.currentTimeMillis();

		mHandler.post(new Runnable() {
			public void run() {
				long now = System.currentTimeMillis();
				float currentMs = Math.min(durationMs, now - startTime);
				float target = oldScale + (incrementPerMs * currentMs);
				zoomTo(target, centerX, centerY);

				if (currentMs < durationMs) {
					mHandler.post(this);
				}
			}
		});
	}

	public void zoomTo(float scale) {
		float cx = getWidth() / 2F;
		float cy = getHeight() / 2F;

		zoomTo(scale, cx, cy);
	}

	public void zoomToPoint(float scale, float pointX, float pointY) {
		float cx = getWidth() / 2F;
		float cy = getHeight() / 2F;

		panBy(cx - pointX, cy - pointY);
		zoomTo(scale, cx, cy);
	}

	public void zoomToNoCenter(float scale, float centerX, float centerY) {
		// if (scale > mMaxZoom) {
		// scale = mMaxZoom;
		// }

		float oldScale = getScale();
		float deltaScale = scale / oldScale;
//		Log.e(TAG," oldScale : "+oldScale+" , daltaScale:"+deltaScale);
		mSuppMatrix.postScale(deltaScale, deltaScale, centerX, centerY);
		setImageMatrix(getImageViewMatrix());
	}

	public class MatrixTransformAnimation extends Animation {
		Matrix mFrom;
		Matrix mTo;

		public MatrixTransformAnimation(Matrix from, Matrix to) {
			mFrom = from;
			mTo = to;
		}

		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			Matrix matrix = t.getMatrix();
			// matrix.set(mMatrix);
			float from = getValue(mFrom, Matrix.MSCALE_X);
			float to = getValue(mTo, Matrix.MSCALE_X);
			matrix.setScale(from / to, from / to);
		}
	}

	public void zoomToNoCenterValue(float scale, float centerX, float centerY) {
		float oldScale = getScale();
		float deltaScale = scale / oldScale;

		mSuppMatrix.postScale(deltaScale, deltaScale, centerX, centerY);
		getImageViewMatrix();
	}

	public void zoomToNoCenterWithAni(float scale, float toScale,
			float centerX, float centerY) {
		ScaleAnimation animation = new ScaleAnimation(scale, toScale, scale,
				toScale, centerX, centerY);
		animation.setDuration(300);
		animation.setAnimationListener(new ScaleAnimation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// setImageMatrix(getImageMatrix());
			}
		});
		startAnimation(animation);

		// float oldScale = getScale();
		// float deltaScale = scale / oldScale;
		// mSuppMatrix.postScale(deltaScale, deltaScale, centerX, centerY);
		// Animation animation = new MatrixTransformAnimation(
		// getImageViewMatrix(), getImageViewMatrix());
		// animation.setDuration(300);
		// startAnimation(animation);
	}

	protected void zoomIn() {
		zoomIn(SCALE_RATE);
	}

	protected void zoomOut() {
		zoomOut(SCALE_RATE);
	}

	protected void zoomIn(float rate) {
		if (getScale() >= mMaxZoom) {
			return; // Don't let the user zoom into the molecular level.
		}
		if (mBitmapDisplayed.getView() == null) {
			return;
		}

		float cx = getWidth() / 2F;
		float cy = getHeight() / 2F;

		mSuppMatrix.postScale(rate, rate, cx, cy);
		setImageMatrix(getImageViewMatrix());
	}

	protected void zoomOut(float rate) {
		if (mBitmapDisplayed.getView() == null) {
			return;
		}

		float cx = getWidth() / 2F;
		float cy = getHeight() / 2F;

		// Zoom out to at most 1x.
		Matrix tmp = new Matrix(mSuppMatrix);
		tmp.postScale(1F / rate, 1F / rate, cx, cy);

		if (getScale(tmp) < mMinZoom) {
			mSuppMatrix.setScale(mMinZoom, mMinZoom, cx, cy);
		}
		// if (getScale(tmp) < 1F) {
		// mSuppMatrix.setScale(1F, 1F, cx, cy);
		// }
		else {
			mSuppMatrix.postScale(1F / rate, 1F / rate, cx, cy);
		}
		setImageMatrix(getImageViewMatrix());
		center(true, true);
	}

	protected void postTranslate(float dx, float dy) {
		mSuppMatrix.postTranslate(dx, dy);
	}

	public void panBy(float dx, float dy) {
		postTranslate(dx, dy);
		setImageMatrix(getImageViewMatrix());
	}
	
	// This function changes bitmap, reset base matrix according to the size
	// of the bitmap, and optionally reset the supplementary matrix.
	public void setZoomView(View zoomView,boolean resetSupp) {
		// TODO Auto-generated method stub
//		 mDrawableWidth = zoomView.getWidth();
//         mDrawableHeight = zoomView.getHeight();
//		mBitmapDisplayed = new RotateView(zoomView);
		mBitmapDisplayed.setView(zoomView);
		setZoomRotateViewResetBase(mBitmapDisplayed, resetSupp);
	}
	
	  private int mDrawableWidth;
	    private int mDrawableHeight;
	    int  mPaddingLeft;
	    int  mPaddingRight;
	    int  mPaddingTop;
	    int  mPaddingBottom;
//	    RotateView rotateView;
	    private Matrix mDrawMatrix = null;
	    // Avoid allocations...
	    private RectF mTempSrc = new RectF();
	    private RectF mTempDst = new RectF();
	    
	    
    private void configureBounds() {

        int dwidth = mDrawableWidth;
        int dheight = mDrawableHeight;

        int vwidth = getWidth() - mPaddingLeft - mPaddingRight;
        int vheight = getHeight() - mPaddingTop - mPaddingBottom;

        boolean fits = (dwidth < 0 || vwidth == dwidth) &&
                       (dheight < 0 || vheight == dheight);

        if (dwidth <= 0 || dheight <= 0 || ScaleType.FIT_XY == mScaleType) {
            /* If the drawable has no intrinsic size, or we're told to
                scaletofit, then we just fill our entire view.
            */
//        	rotateView.getView().setBounds(0, 0, vwidth, vheight);
            mDrawMatrix = null;
        } else {
            // We need to do the scaling ourself, so have the drawable
            // use its native size.
//        	rotateView.setBounds(0, 0, dwidth, dheight);

            if (ScaleType.MATRIX == mScaleType) {
                // Use the specified matrix as-is.
                if (mMatrix.isIdentity()) {
                    mDrawMatrix = null;
                } else {
                    mDrawMatrix = mMatrix;
                }
            } else if (fits) {
                // The bitmap fits exactly, no transform needed.
                mDrawMatrix = null;
            } else if (ScaleType.CENTER == mScaleType) {
                // Center bitmap in view, no scaling.
                mDrawMatrix = mMatrix;
                mDrawMatrix.setTranslate((vwidth - dwidth) * 0.5f,
                                         (vheight - dheight) * 0.5f);
            } else if (ScaleType.CENTER_CROP == mScaleType) {
                mDrawMatrix = mMatrix;

                float scale;
                float dx = 0, dy = 0;

                if (dwidth * vheight > vwidth * dheight) {
                    scale = (float) vheight / (float) dheight; 
                    dx = (vwidth - dwidth * scale) * 0.5f;
                } else {
                    scale = (float) vwidth / (float) dwidth;
                    dy = (vheight - dheight * scale) * 0.5f;
                }

                mDrawMatrix.setScale(scale, scale);
                mDrawMatrix.postTranslate(dx, dy);
            } else if (ScaleType.CENTER_INSIDE == mScaleType) {
                mDrawMatrix = mMatrix;
                float scale;
                float dx;
                float dy;
                
                if (dwidth <= vwidth && dheight <= vheight) {
                    scale = 1.0f;
                } else {
                    scale = Math.min((float) vwidth / (float) dwidth, 
                            (float) vheight / (float) dheight);
                }
                
                dx = (vwidth - dwidth * scale) * 0.5f;
                dy = (vheight - dheight * scale) * 0.5f;

                mDrawMatrix.setScale(scale, scale);
                mDrawMatrix.postTranslate(dx, dy);
            } else {
                // Generate the required transform.
                mTempSrc.set(0, 0, dwidth, dheight);
                mTempDst.set(0, 0, vwidth, vheight);
                
                mDrawMatrix = mMatrix;
                mDrawMatrix.setRectToRect(mTempSrc, mTempDst,
                                          scaleTypeToScaleToFit(mScaleType));
                
            }
        }
    }
    
    private  Matrix.ScaleToFit scaleTypeToScaleToFit(ScaleType st)  {
        // ScaleToFit enum to their corresponding Matrix.ScaleToFit values
        return sS2FArray[0];
    }    
    private static final Matrix.ScaleToFit[] sS2FArray = {
        Matrix.ScaleToFit.FILL,
        Matrix.ScaleToFit.START,
        Matrix.ScaleToFit.CENTER,
        Matrix.ScaleToFit.END
    };
    
    public void setChildDipSize(int paramInt1, int paramInt2)
    {
      if (getChildCount() == 0);
      while (true)
      {
        View localView = getChildAt(0);
        ViewGroup.LayoutParams localLayoutParams = localView.getLayoutParams();
        localLayoutParams.width = (int)(paramInt1 * getResources().getDisplayMetrics().density);
        localLayoutParams.height = (int)(paramInt2 * getResources().getDisplayMetrics().density);
        localView.setLayoutParams(localLayoutParams);
//        this.mZoomLevel = 1.0D;
//        onZoom(1.0D);
        return;
      }
    }
    
    public void scaleChildView(int scrollX,int scrollY,double scaleDouble)
    {
    	int count = getChildCount();
    	
      if (getChildCount() == 0){
    	  return;
      }
      mCurZoom = scaleDouble;
//      while (true)
      for(int index=0;index<count;index++)
      {
        View curChildView = getChildAt(index);
        ViewGroup.LayoutParams localLayoutParams = curChildView.getLayoutParams();
//        int widthBefore = localLayoutParams.width;
//        int heightBefore = localLayoutParams.height;
//        Log.w(TAG,"3 curChildView "+index+":"+"widthBefore:"+widthBefore+",heightBefore:"+heightBefore);
//        int i = Math.round((float) (scaleDouble * widthBefore));
//		int j = Math.round((float) (scaleDouble * heightBefore));
//		int k = curChildView.getWidth() - curChildView.getPaddingRight()
//				- curChildView.getPaddingLeft();
//		 int h = (curChildView.getHeight() - curChildView.getPaddingBottom() -
//				 curChildView.getPaddingTop());
		 int k = curChildView.getWidth();
		 int h = curChildView.getHeight();
//		 if(i<=0||j<=0){
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
//		 Log.w(TAG,"3 curChildView "+index+":"+"k:"+k+", h:"+ h+", width:"+localLayoutParams.width+",height:"+localLayoutParams.height);
        curChildView.setLayoutParams(localLayoutParams);
        scaleChildView(curChildView,scaleDouble);
//        curChildView.scrollTo(scrollX,scrollY);
//        this.mZoomLevel = (paramDouble * this.mZoomLevel);
//        return;
      }
    }
    
    public void scaleChildView(View curView,double scaleDouble)
    {
    	int count = 0;
    	if(curView instanceof ViewGroup){
    		count = ((ViewGroup) curView).getChildCount();
    	}else{
    		return;
    	}
    	
      if (getChildCount() == 0){
    	  return;
      }
//      while (true)
      for(int index=0;index<count;index++)
      {
        View curChildView = ((ViewGroup)curView).getChildAt(index);
        ViewGroup.LayoutParams localLayoutParams = curChildView.getLayoutParams();
//        int widthBefore = localLayoutParams.width;
//        int heightBefore = localLayoutParams.height;
//        Log.w(TAG,"2 curChildView "+index+":"+"widthBefore:"+widthBefore+",heightBefore:"+heightBefore);
//        int i = Math.round((float) (scaleDouble * widthBefore));
//		int j = Math.round((float) (scaleDouble * heightBefore));
//		int k = curChildView.getWidth() - curChildView.getPaddingRight()
//				- curChildView.getPaddingLeft();
//		 int h = (curChildView.getHeight() - curChildView.getPaddingBottom() -
//				 curChildView.getPaddingTop());
        int k = curChildView.getWidth();
		 int h = curChildView.getHeight();
//		 if(i<=0||j<=0){
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
//		 Log.e(TAG,"2 curChildView "+index+":"+"k:"+k+", h:"+ h+", width:"+localLayoutParams.width+",height:"+localLayoutParams.height);
        curChildView.setLayoutParams(localLayoutParams);
        scaleChildView(curChildView,scaleDouble);
//        curChildView.scrollTo(scrollX,scrollY);
//        this.mZoomLevel = (paramDouble * this.mZoomLevel);
//        return;
      }
    }
    
}
