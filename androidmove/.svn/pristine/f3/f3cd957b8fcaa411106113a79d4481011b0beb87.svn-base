package com.szcmcc.movie.util;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class AnimUtils {

	// private static AnimUtils animUtils = new AnimUtils();
	// private static Context mContext;
	// private AnimUtils(){
	//		
	// }
	//	
	// public static AnimUtils getInstance(Context context){
	// mContext = context;
	// return animUtils;
	// }
	public static final int SHOW_POSITION_LEFT_TOP = 1;
	public static final int SHOW_POSITION_RIGHT_TOP = 2;
	public static final int SHOW_POSITION_CENTER_TOP = 3;
	public static final int SHOW_POSITION_LEFT_BOTTOM = 4;
	public static final int SHOW_POSITION_RIGHT_BOTTOM = 5;
	public static final int SHOW_POSITION_CENTER_BOTTOM = 6;

	public static final int HIDDEN_POSITION_LEFT_TOP = 1;
	public static final int HIDDEN_POSITION_RIGHT_TOP = 2;
	public static final int HIDDEN_POSITION_CENTER_TOP = 3;

	/**
	 * 单个view 从上到下动画显示
	 * 
	 * @param visibleView
	 * @param context
	 */
//	public static void setVisibleAnim(View visibleView, Context context) {
//		if (!visibleView.isShown()) {
//			visibleView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.animation_slide_in_up));
//			visibleView.setVisibility(View.VISIBLE);
//		}
//	}
	/**
	 * 单个view 从下到上动画显示
	 * 
	 * @param visibleView
	 * @param context
	 */
//	public static void setUpInAnim(View visibleView, Context context) {
//		if (!visibleView.isShown()) {
//			visibleView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_up));
//			visibleView.setVisibility(View.VISIBLE);
//		}
//	}
	/**
	 * 单个view 向下动画隐藏
	 * 
	 * @param visibleView
	 * @param context
	 */
//	public static void setDownOutAnim(View visibleView, Context context) {
//		if (visibleView.isShown()) {
//			visibleView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_down));
//			visibleView.setVisibility(View.GONE);
//		}
//	}
	
	/*
	 * 从下倒上动画显示
	 * @param visibleView
	 * @param context
	 * 
	 */
//	public static void setlayoutVisibleAnim(View visibleView, Context context) {
//		if (!visibleView.isShown()) {
//			visibleView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_bottomin_up));
//			visibleView.setVisibility(View.VISIBLE);
//		}
//	}
	
	/*
	 * 向下收起隐藏
	 * @param goneView
	 * @param context
	 * @param layout 指的是goneView的父布局
	 */
//	public static void setlayoutGoneAnim(View goneView, Context context,final LinearLayout layout) {
//		if (goneView.isShown()) {
//			Animation ani=AnimationUtils.loadAnimation(context, R.anim.slide_bottomout_up);
//			ani.setAnimationListener(new AnimationListener() {
//				
//				@Override
//				public void onAnimationStart(Animation animation) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void onAnimationRepeat(Animation animation) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void onAnimationEnd(Animation animation) {
//					layout.setVisibility(8);
//					
//				}
//			});
//			goneView.setAnimation(ani);
//			goneView.setVisibility(View.GONE);
//		}
//	}
	

	/**
	 * 单个view 从下到上收起隐藏
	 * 
	 * @param goneView
	 * @param context
	 */
//	public static void setGoneAnim(View goneView, Context context) {
//		if (goneView.isShown()) {
//			goneView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.animation_slide_out_up));
//			goneView.setVisibility(View.GONE);
//		}
//	}
    /**
	 * 单个view
	 * 从下到上收起隐藏
	 * @param goneView
	 * @param context
	 */
//    public static void setGoneRightAnim(View goneView,Context context){
//        if(goneView.isShown()){
//        	goneView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.outtoright));
//        	goneView.setVisibility(View.GONE);
//        }
//    }
	 /**
		 * 单个view
		 * 从左到右收起隐藏
		 * @param goneView
		 * @param context
		 */
	 public static void setToRightGoneAnim(View animView,final View goneView ,float toX){
		 TranslateAnimation translateAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF,0, TranslateAnimation.RELATIVE_TO_SELF,toX, TranslateAnimation.RELATIVE_TO_PARENT,0, TranslateAnimation.RELATIVE_TO_PARENT,0);
		 translateAnim.setDuration(400);
//		 translateAnim.setInterpolator(new DecelerateInterpolator());
		 translateAnim.setInterpolator(new AccelerateInterpolator());
		 translateAnim.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				if(goneView != null){
					goneView.setVisibility(View.GONE);
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			 
		 });
		 animView.startAnimation(translateAnim);
	 }
	 /**
		 * 单个view
		 * 从右到左显示
		 * @param goneView
		 * @param context
		 */
	 public static void setToLeftShowAnim(View showView ,float fromX){
		 TranslateAnimation translateAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF,fromX,TranslateAnimation.RELATIVE_TO_SELF,0, TranslateAnimation.RELATIVE_TO_PARENT,0, TranslateAnimation.RELATIVE_TO_PARENT,0);
		 translateAnim.setDuration(300);
		 translateAnim.setInterpolator(new AccelerateInterpolator());
//		 translateAnim.setFillAfter(true);
		 showView.startAnimation(translateAnim);
	 }
	/**
	 * 单个view 旋转动画向上旋转
	 * 
	 * @param rotateView
	 * @param context
	 */
	public static void setRotateUpAnim(View rotateView) {
		if (rotateView != null && rotateView.isShown()) {
			// rotateView.clearAnimation();
			// if(mFlipUpAnimation == null){
			RotateAnimation mFlipUpAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
					RotateAnimation.RELATIVE_TO_SELF, 0.5f);
			mFlipUpAnimation.setInterpolator(new LinearInterpolator());
			mFlipUpAnimation.setDuration(250);
//			mFlipUpAnimation.setFillAfter(true);
			// }
			rotateView.setAnimation(mFlipUpAnimation);
		}
	}

	/**
	 * 单个view 旋转动画向下旋转
	 * 
	 * @param rotateView
	 * @param context
	 */
	public static void setRotateDownAnim(View rotateView) {
		if (rotateView != null && rotateView.isShown()) {
			// rotateView.clearAnimation();
			// if(mFlipDownAnimation == null){
			RotateAnimation mFlipDownAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
					RotateAnimation.RELATIVE_TO_SELF, 0.5f);
			mFlipDownAnimation.setInterpolator(new LinearInterpolator());
			mFlipDownAnimation.setDuration(250);
			mFlipDownAnimation.setFillAfter(true);
			// }

			rotateView.setAnimation(mFlipDownAnimation);
		}
	}

	/**
	 * 使view 旋转动画向下旋转
	 * 
	 * @param rotateView
	 * @param context
	 */
	public static void applyRotateDownAnim(View rotateView) {
		if (rotateView != null && rotateView.isShown()) {
			rotateView.clearAnimation();
			RotateAnimation mFlipDownAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
					RotateAnimation.RELATIVE_TO_SELF, 0.5f);
			mFlipDownAnimation.setInterpolator(new LinearInterpolator());
			mFlipDownAnimation.setDuration(250);
			mFlipDownAnimation.setFillAfter(true);

			rotateView.startAnimation(mFlipDownAnimation);
		}
	}

	/**
	 * 用于view显示动画 效果：模拟放入物品效果
	 * 
	 * @param visibleView
	 * @param context
	 */
	public static void applyPutAnim(View view, int fromX, int toX, int fromY, int toY) {
		if (view != null && view.isShown()) {
			view.clearAnimation();
			AnimationSet set = new AnimationSet(true);
			set.setInterpolator(new AccelerateInterpolator());
			// 旋转动画
			RotateAnimation animation_r = new RotateAnimation(0, 360 * 2, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
					RotateAnimation.RELATIVE_TO_SELF, 0.5f);
			animation_r.setDuration(800);

			set.addAnimation(animation_r);

			ScaleAnimation animation_s = new ScaleAnimation(1.0f, 0.2f, 1.0f, 0.2f, Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 0.0f);
			animation_s.setDuration(800);
			set.addAnimation(animation_s);
			//			
			TranslateAnimation animation_t = new TranslateAnimation(Animation.ABSOLUTE, fromX, Animation.ABSOLUTE, toX,
					Animation.ABSOLUTE, fromY, Animation.ABSOLUTE, toY);
			animation_t.setDuration(800);
			set.addAnimation(animation_t);
			//			
			view.startAnimation(set);
			view.setVisibility(View.GONE);
		}
	}

	/**
	 * 用于view显示动画 效果
	 * 
	 * @param view
	 * @param showPosition
	 * @param scale
	 */
	public static void applyShowAnim(View view, int showPosition, boolean scale, long durationMillis,
			AnimationListener l) {
		if (view != null && !view.isShown()) {
			view.clearAnimation();
			AnimationSet set = new AnimationSet(true);
			set.setInterpolator(new DecelerateInterpolator());
			TranslateAnimation animation_t = null;
			ScaleAnimation animation_s = null;
			switch (showPosition) {
			case SHOW_POSITION_LEFT_TOP:
				animation_t = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
						0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
				animation_t.setDuration(durationMillis);
				set.addAnimation(animation_t);
				if (scale) {

					animation_s = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
							Animation.RELATIVE_TO_SELF, 0.0f);
					animation_s.setDuration(durationMillis);
					set.addAnimation(animation_s);
				}
				break;
			case SHOW_POSITION_RIGHT_TOP:
				animation_t = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
						0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
				animation_t.setDuration(durationMillis);
				set.addAnimation(animation_t);
				if (scale) {

					animation_s = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f,
							Animation.RELATIVE_TO_SELF, 0.0f);
					animation_s.setDuration(durationMillis);
					set.addAnimation(animation_s);
				}
				break;
			case SHOW_POSITION_CENTER_TOP:
				animation_t = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
						0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
				animation_t.setDuration(durationMillis);
				set.addAnimation(animation_t);
				if (scale) {
					animation_s = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.0f);
					animation_s.setDuration(durationMillis);
					set.addAnimation(animation_s);
				}
				break;
			case SHOW_POSITION_LEFT_BOTTOM:
				animation_t = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
						0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
				animation_t.setDuration(durationMillis);
				set.addAnimation(animation_t);
				if (scale) {

					animation_s = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
							Animation.RELATIVE_TO_SELF, 1.0f);
					animation_s.setDuration(durationMillis);
					set.addAnimation(animation_s);
				}
				break;
			}

			if (l != null) {
				set.setAnimationListener(l);
			}
			view.startAnimation(set);
			view.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 用于view隐藏动画 效果
	 * 
	 * @param view
	 * @param showPosition
	 * @param scale
	 */
	public static void applyHiddenAnim(View view, int hiddenPosition, boolean scale, long durationMillis,
			AnimationListener l) {
		if (view != null && view.isShown()) {
			view.clearAnimation();
			AnimationSet set = new AnimationSet(true);
			set.setInterpolator(new DecelerateInterpolator());
			TranslateAnimation animation_t = null;
			ScaleAnimation animation_s = null;
			switch (hiddenPosition) {
			case HIDDEN_POSITION_LEFT_TOP:
				animation_t = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
						0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
				animation_t.setDuration(durationMillis);
				set.addAnimation(animation_t);
				animation_s = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
						Animation.RELATIVE_TO_SELF, 0.0f);
				animation_s.setDuration(durationMillis);
				set.addAnimation(animation_s);
				break;
			case HIDDEN_POSITION_RIGHT_TOP:
				animation_t = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
						1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
				animation_t.setDuration(durationMillis);
				set.addAnimation(animation_t);
				animation_s = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
						Animation.RELATIVE_TO_SELF, 0.0f);
				animation_s.setDuration(durationMillis);
				set.addAnimation(animation_s);
				break;
			case HIDDEN_POSITION_CENTER_TOP:
				animation_t = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
						0.5f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
				animation_t.setDuration(durationMillis);
				set.addAnimation(animation_t);
				animation_s = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.0f);
				animation_s.setDuration(durationMillis);
				set.addAnimation(animation_s);
				break;
			}

			if (l != null) {
				set.setAnimationListener(l);
			}
			view.startAnimation(set);
			view.setVisibility(View.INVISIBLE);
		}
	}

	public static void applyShowAnimRelParent(View view, int showPosition, boolean scale, long durationMillis,
			AnimationListener l) {
		if (view != null && !view.isShown()) {
			view.clearAnimation();
			AnimationSet set = new AnimationSet(true);
			set.setInterpolator(new DecelerateInterpolator());
			TranslateAnimation animation_t = null;
			ScaleAnimation animation_s = null;
			switch (showPosition) {
			case SHOW_POSITION_LEFT_TOP:
				if (scale) {
					animation_t = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
							Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
							Animation.RELATIVE_TO_SELF, 0.0f);
					animation_t.setDuration(durationMillis);
					set.addAnimation(animation_t);

					animation_s = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
							Animation.RELATIVE_TO_PARENT, 0.0f);
					animation_s.setDuration(durationMillis);
					set.addAnimation(animation_s);
				}
				break;
			case SHOW_POSITION_RIGHT_TOP:
				if (scale) {
					animation_t = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f,
							Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
							Animation.RELATIVE_TO_SELF, 0.0f);
					animation_t.setDuration(durationMillis);
					set.addAnimation(animation_t);

					animation_s = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_PARENT, 1.0f,
							Animation.RELATIVE_TO_PARENT, 0.0f);
					animation_s.setDuration(durationMillis);
					set.addAnimation(animation_s);
				}
				break;
			case SHOW_POSITION_CENTER_TOP:
				animation_t = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
						0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
				animation_t.setDuration(durationMillis);
				set.addAnimation(animation_t);
				if (scale) {
					animation_s = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.0f);
					animation_s.setDuration(durationMillis);
					set.addAnimation(animation_s);
				}
				break;
			case SHOW_POSITION_LEFT_BOTTOM:
				animation_t = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
						0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
				animation_t.setDuration(durationMillis);
				set.addAnimation(animation_t);
				if (scale) {

					animation_s = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
							Animation.RELATIVE_TO_SELF, 1.0f);
					animation_s.setDuration(durationMillis);
					set.addAnimation(animation_s);
				}
				break;
			}

			if (l != null) {
				set.setAnimationListener(l);
			}
			view.startAnimation(set);
			view.setVisibility(View.VISIBLE);
		}
	}

	public static void applyShowAnimRelParent(View view, boolean scale, long durationMillis, AnimationListener l,
			float fromParentXValue, float toSelfXValue, float fromParentYValue, float toSelfYValue) {
		if (view != null && !view.isShown()) {
			view.clearAnimation();
			AnimationSet set = new AnimationSet(true);
			set.setInterpolator(new DecelerateInterpolator());

			TranslateAnimation animation_t = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, fromParentXValue,
					Animation.RELATIVE_TO_SELF, toSelfXValue, Animation.RELATIVE_TO_PARENT, fromParentYValue,
					Animation.RELATIVE_TO_SELF, toSelfYValue);
			animation_t.setDuration(durationMillis);
			set.addAnimation(animation_t);

			ScaleAnimation animation_s = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_PARENT,
					fromParentXValue, Animation.RELATIVE_TO_PARENT, fromParentYValue);
			animation_s.setDuration(durationMillis);
			set.addAnimation(animation_s);

			if (l != null) {
				set.setAnimationListener(l);
			}
			view.startAnimation(set);
			view.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 模拟两控件的位置调换
	 * 
	 * @param view1
	 * @param view2
	 */
	public static void applyTSFromOneToOther(final View view1, final View view2, final AnimationListener l) {
		final int left = view2.getLeft();
		final int top = view2.getTop();
		final int myLeft = view1.getLeft();
		final int myTop = view1.getTop();

		ScaleAnimation animation_s = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animation_s.setDuration(300);
		animation_s.setInterpolator(new DecelerateInterpolator());
		animation_s.setFillAfter(true);
		animation_s.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation animation) {
				AnimationSet set = new AnimationSet(true);
				set.setInterpolator(new DecelerateInterpolator());

				ScaleAnimation animation_s = new ScaleAnimation(1.5f, 1.2f, 1.5f, 1.2f, Animation.RELATIVE_TO_SELF,
						0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				animation_s.setDuration(300);
				animation_s.setFillAfter(true);
				set.addAnimation(animation_s);
				TranslateAnimation animation_t = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, left
						- myLeft, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, top - myTop);
				animation_t.setDuration(300);
				animation_t.setFillAfter(true);
				set.addAnimation(animation_t);
				if (l != null) {
					set.setAnimationListener(l);
				}
				view1.clearAnimation();
				view1.startAnimation(set);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationStart(Animation animation) {
				view1.setClickable(false);
			}

		});

		view1.clearAnimation();
		view1.startAnimation(animation_s);

		ScaleAnimation animation_s2 = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animation_s2.setDuration(300);
		animation_s2.setInterpolator(new DecelerateInterpolator());
		animation_s2.setFillAfter(true);
		animation_s2.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation animation) {
				AnimationSet set = new AnimationSet(true);
				set.setInterpolator(new DecelerateInterpolator());

				ScaleAnimation animation_s = new ScaleAnimation(0.5f, 0.8f, 0.5f, 0.8f, Animation.RELATIVE_TO_SELF,
						0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				animation_s.setDuration(300);
				animation_s.setFillAfter(true);
				set.addAnimation(animation_s);
				TranslateAnimation animation_t = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE,
						myLeft - left, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, myTop - top);
				animation_t.setDuration(300);
				animation_t.setFillAfter(true);
				set.addAnimation(animation_t);
				view2.clearAnimation();
				view2.startAnimation(set);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationStart(Animation animation) {
				view2.setClickable(false);
			}

		});

		view2.clearAnimation();
		view2.startAnimation(animation_s2);

	}

	/**
	 * 可变参形式 放入多个view，依次执行显示动画
	 * 
	 * @param view
	 */
	public static void showViews(final int begin, final View... view) {
		int count = view.length;
		if (begin >= 0 && begin < count) {
			View applyView = view[begin];
			if (applyView != null && !applyView.isShown()) {
				ScaleAnimation animation_s = new ScaleAnimation(0.5f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF,
						0.5f, Animation.RELATIVE_TO_SELF, 0.0f);
				animation_s.setDuration(300);
				animation_s.setFillAfter(true);
				animation_s.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationEnd(Animation animation) {
						showViews(begin + 1, view);
					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationStart(Animation animation) {

					}

				});
				applyView.startAnimation(animation_s);
				applyView.setVisibility(View.VISIBLE);
			} else {
				showViews(begin + 1, view);
			}
		}

	}

	/**
	 * 模拟晃动效果
	 * 
	 * @param view
	 */
	public static void applyShakeAnim(View view) {
		TranslateAnimation animation_t = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -0.3f, Animation.RELATIVE_TO_SELF, 0.1f);
		animation_t.setDuration(500);
		animation_t.setFillAfter(true);
		animation_t.setRepeatCount(Animation.INFINITE);
		animation_t.setRepeatMode(Animation.REVERSE);
		animation_t.setInterpolator(new AccelerateInterpolator());
		view.startAnimation(animation_t);
	}

	public static void applyRotation(View view, float start, float end) {
		view.clearAnimation();
		final float centerX = view.getWidth() / 2.0f;
		final float centerY = view.getHeight() / 2.0f;

		// Create a new 3D rotation with the supplied parameter
		// The animation listener is used to trigger the next animation
		final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end, centerX, centerY, 0.0f, true);
		rotation.setDuration(1200);
		rotation.setFillAfter(false);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setRepeatCount(3);
		view.startAnimation(rotation);
	}
	
	public static void showTouchAnim(final View view1,final View view2){
		final Handler handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				view2.setVisibility(View.INVISIBLE);
			}
			
		};
		TranslateAnimation animation_t = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.3f);
		animation_t.setDuration(500);
//		animation_t.setFillAfter(true);
		animation_t.setRepeatCount(10);
		animation_t.setRepeatMode(Animation.RESTART);
		animation_t.setInterpolator(new AccelerateInterpolator());
		animation_t.setAnimationListener(new AnimationListener(){
			
			@Override
			public void onAnimationEnd(Animation animation) {
				view1.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				view2.setVisibility(View.VISIBLE);
				handler.sendEmptyMessageDelayed(1, 100);
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}
			
		});
		view1.startAnimation(animation_t);
		view1.setVisibility(View.VISIBLE);
		
	}
	
	   public static void applyFastRotatePutAnim(View view, int toX, int toY,boolean isBack) {
	        if (view != null && view.isShown()) {
	            view.clearAnimation();
	            AnimationSet set = new AnimationSet(true);
	            set.setInterpolator(new AccelerateInterpolator());
	            // 旋转动画
	            RotateAnimation animation_r;
	            if(isBack){
	                animation_r = new RotateAnimation(0, 360 * 4, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
	                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
	            }else{//默认falsa
	                animation_r = new RotateAnimation(0, -360 * 4, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
	                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
	            }
	            animation_r.setDuration(800);

	            set.addAnimation(animation_r);

//	            ScaleAnimation animation_s ;
//	            if(isBack){
//	                animation_s = new ScaleAnimation(0.2f, 1.0f, 0.2f, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
//                            Animation.RELATIVE_TO_SELF, 0.0f);
//	            }else{
//	                animation_s = new ScaleAnimation(1.0f, 0.2f, 1.0f, 0.2f, Animation.RELATIVE_TO_SELF, 0.0f,
//                            Animation.RELATIVE_TO_SELF, 0.0f);
//	            }
//	            
//	            animation_s.setDuration(2000);
//	            set.addAnimation(animation_s);
	            //   
	            TranslateAnimation animation_t;
	            if(isBack){
	                animation_t = new TranslateAnimation(Animation.ABSOLUTE, toX, Animation.ABSOLUTE, 0,
	                        Animation.ABSOLUTE, toY, Animation.ABSOLUTE, 0);
	            }else{//默认是false
	                animation_t = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, toX,
	                        Animation.ABSOLUTE, 0, Animation.ABSOLUTE, toY);
	            }
	            animation_t.setDuration(800);
	            set.addAnimation(animation_t);
	            //          
	            view.startAnimation(set);
//	            view.setVisibility(View.GONE);
	        }
	    }
	   
}
