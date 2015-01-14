package com.szcmcc.movie.view;

import com.szcmcc.movie.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;


/**
 * 暂时适配了M9(640*960)、三星(480*800)、模拟器(320*480) 适配方法：在下面方法适配即可
 * 
 * @author Haylee
 * 
 */
public class LoadingImagView extends ImageView {

	private Paint mFramePaint = null;
	private boolean mUsecenters;
	private RectF mBigOval;
	private float mStart;
	private float mSweep;

	private static final float SWEEP_INC = 7; // 设置画弧的速度
	private static final float START_INC = 0;// 设置画弧的角度

	protected boolean isStop = true;
	private int curIndex = 2;
	private int tempIndex = 0;

	public LoadingImagView(Context context) {
		super(context);
		createPaint();
	}

	public LoadingImagView(Context context, AttributeSet attrs) {
		super(context, attrs);
		createPaint();
		fitLayout(context);
	}

	// 适配布局
	private void fitLayout(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int widthPixels = dm.widthPixels;
		int heightPixels = dm.heightPixels;
		System.out.println("该手机的分辨率各个参数为：" + dm.widthPixels + "   " + dm.heightPixels + "   " + dm.density + "   " + dm.densityDpi + "   " + dm.xdpi
				+ "   " + dm.ydpi);
		if (widthPixels == 320 && heightPixels == 480) {
			if (dm.densityDpi == DisplayMetrics.DENSITY_LOW) {

			} else if (dm.densityDpi == DisplayMetrics.DENSITY_MEDIUM) {
				mBigOval = new RectF(47, 19, 122, 96);
			} else if (dm.densityDpi == DisplayMetrics.DENSITY_HIGH) {

			}

		} else if (widthPixels == 480 && heightPixels == 800) {
			if (dm.densityDpi == DisplayMetrics.DENSITY_LOW) {

			} else if (dm.densityDpi == DisplayMetrics.DENSITY_MEDIUM) {

			} else if (dm.densityDpi == DisplayMetrics.DENSITY_HIGH) {
				mBigOval = new RectF(70, 30, 183, 143);
			}

		} else if (widthPixels == 640 && heightPixels == 960) {
			if (dm.densityDpi == DisplayMetrics.DENSITY_LOW) {

			} else if (dm.densityDpi == DisplayMetrics.DENSITY_MEDIUM) {

			} else if (dm.densityDpi == DisplayMetrics.DENSITY_HIGH) {

			} else if (dm.densityDpi == 320) {
				mBigOval = new RectF(94, 41, 243, 190);// 35, 15, 95, 75
			}

		} else if (widthPixels == 480 && heightPixels == 854) {
			if (dm.densityDpi == DisplayMetrics.DENSITY_LOW) {

			} else if (dm.densityDpi == DisplayMetrics.DENSITY_MEDIUM) {

			} else if (dm.densityDpi == DisplayMetrics.DENSITY_HIGH) {
				mBigOval = new RectF(72, 30, 183, 140);
			}

		} else if (widthPixels == 320 && heightPixels == 240) {
			if (dm.densityDpi == DisplayMetrics.DENSITY_LOW) {

			} else if (dm.densityDpi == DisplayMetrics.DENSITY_MEDIUM) {

			} else if (dm.densityDpi == DisplayMetrics.DENSITY_HIGH) {

			}

		} else if (widthPixels == 600 && heightPixels == 1024) {
			if (dm.densityDpi == DisplayMetrics.DENSITY_LOW) {

			} else if (dm.densityDpi == DisplayMetrics.DENSITY_MEDIUM) {

			} else if (dm.densityDpi == DisplayMetrics.DENSITY_HIGH) {
			}

		} else if (widthPixels == 540 && heightPixels == 960) {
			if (dm.densityDpi == DisplayMetrics.DENSITY_LOW) {

			} else if (dm.densityDpi == DisplayMetrics.DENSITY_MEDIUM) {

			} else if (dm.densityDpi == DisplayMetrics.DENSITY_HIGH) {
				mBigOval = new RectF(80, 38, 174, 131);
			}
		} else if (widthPixels == 800 && heightPixels == 1280) {
			if (dm.densityDpi == DisplayMetrics.DENSITY_LOW) {

			} else if (dm.densityDpi == DisplayMetrics.DENSITY_MEDIUM) {

			} else if (dm.densityDpi == DisplayMetrics.DENSITY_HIGH) {

			} else if (dm.densityDpi == 320) {
				mBigOval = new RectF(94, 41, 243, 190);// 35, 15, 95, 75
			}
		} else if (widthPixels == 480 && heightPixels == 320) {
			if (dm.densityDpi == DisplayMetrics.DENSITY_LOW) {

			} else if (dm.densityDpi == DisplayMetrics.DENSITY_MEDIUM) {
				mBigOval = new RectF(47, 19, 122, 96);
			} else if (dm.densityDpi == DisplayMetrics.DENSITY_HIGH) {

			} else if (dm.densityDpi == 320) {

			}
		} else if (widthPixels == 720 && heightPixels == 1184) {
			if (dm.densityDpi == DisplayMetrics.DENSITY_LOW) {

			} else if (dm.densityDpi == DisplayMetrics.DENSITY_MEDIUM) {
				
			} else if (dm.densityDpi == DisplayMetrics.DENSITY_HIGH) {

			} else if (dm.densityDpi == 320) {
				mBigOval = new RectF(94, 39, 245, 190);//196
				
			}
		}else if (widthPixels == 720 && heightPixels == 1280) {
			if (dm.densityDpi == DisplayMetrics.DENSITY_LOW) {

			} else if (dm.densityDpi == DisplayMetrics.DENSITY_MEDIUM) {
				
			} else if (dm.densityDpi == DisplayMetrics.DENSITY_HIGH) {

			} else if (dm.densityDpi == 320) {
				mBigOval = new RectF(94, 41, 245, 190);//196
				
			}
		}else if (widthPixels == 1080 && heightPixels == 1920) {
			if (dm.densityDpi == DisplayMetrics.DENSITY_LOW) {
				
			} else if (dm.densityDpi == DisplayMetrics.DENSITY_MEDIUM) {
				
			} else if (dm.densityDpi == DisplayMetrics.DENSITY_HIGH) {
				
			} else if (dm.densityDpi == 480) {
				mBigOval = new RectF(137, 60, 370, 282);//196
				
			}
		}
		if (mBigOval == null) {
			mBigOval = new RectF(47, 19, 122, 96);
		}
	}

	public LoadingImagView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		createPaint();
	}

	// 创建画笔
	private void createPaint() {
		mFramePaint = new Paint();
		mFramePaint.setAntiAlias(true);
		mFramePaint.setStyle(Paint.Style.FILL);
		mFramePaint.setColor(0x88AAADAF);
		mUsecenters = true;
		setImageDrawable(getResources().getDrawable(R.drawable.loading1));

	}

	private void drawArcs(Canvas canvas, RectF oval, boolean useCenter, Paint paint) {
		canvas.drawArc(oval, mStart, mSweep, useCenter, paint);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (isStop) {
			drawArcs(canvas, mBigOval, mUsecenters, mFramePaint);
			mSweep += SWEEP_INC;
			if (mSweep > 360) {
				tempIndex = curIndex % 3;
				if (tempIndex == 0) {
					setImageDrawable(getResources().getDrawable(R.drawable.loading3));
				} else if (tempIndex == 1) {
					setImageDrawable(getResources().getDrawable(R.drawable.loading1));
				} else if (tempIndex == 2) {
					setImageDrawable(getResources().getDrawable(R.drawable.loading2));
				}
				mSweep -= 360;
				mStart += START_INC;
				if (mStart >= 360) {
					mStart -= 360;
				}
				curIndex++;
			}
			invalidate();
		}
		super.onDraw(canvas);
	}
}
