package com.szcmcc.movie.util;

import com.szcmcc.movie.R;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class RoateUtil {
	private static Animation mAnimationRight;
	private static Animation mAnimationLeftTen;
	private static Animation mAnimationTRight;
	private static Animation mAnimationCenter;
	private static Animation mAnimationTen;
	public static void tranlateTen(Context mContext, View view) {
		mAnimationTen = AnimationUtils.loadAnimation(mContext,
				R.anim.rotate_ten);
		mAnimationTen.setFillAfter(true);
		view.setAnimation(mAnimationTen);
	}
	public static void tranlate(Context mContext, View view) {
		mAnimationRight = AnimationUtils.loadAnimation(mContext,
				R.anim.rotate_right);
		
		mAnimationRight.setFillAfter(true);
		view.clearAnimation();
		view.setAnimation(mAnimationRight);
	}
	private static Animation rotate_right_little;
	public static void tranlateLittile(Context mContext, View view) {
		rotate_right_little = AnimationUtils.loadAnimation(mContext,
				R.anim.rotate_right_little);
		rotate_right_little.setFillAfter(true);
		view.setAnimation(rotate_right_little);
	}
	public static void tranlateLeft(Context mContext, View view) {
		mAnimationLeftTen = AnimationUtils.loadAnimation(mContext,
				R.anim.rotate_left);
		mAnimationLeftTen.setFillAfter(true);
		view.setAnimation(mAnimationLeftTen);
	}
	public static void tranlateT(Context mContext, View view) {
		mAnimationTRight = AnimationUtils.loadAnimation(mContext,
				R.anim.rotate_translate_right);
		mAnimationTRight.setFillAfter(true);
		view.clearAnimation();
		view.setAnimation(mAnimationTRight);
	}
	
	
	private static Animation rotate_translate_down;
	public static void tranlateTenDown(Context mContext, View view) {
		rotate_translate_down = AnimationUtils.loadAnimation(mContext,
				R.anim.rotate_translate_down);
		rotate_translate_down.setFillAfter(true);
		view.setAnimation(rotate_translate_down);
	}
	
	private static Animation rotate_down;
	public static void tranlateDown(Context mContext, View view) {
		rotate_down = AnimationUtils.loadAnimation(mContext,
				R.anim.rotate_down);
		rotate_down.setFillAfter(true);
		view.setAnimation(rotate_down);
	}
	
	
	public static void tranlateCenter(Context mContext, View view) {
		mAnimationCenter = AnimationUtils.loadAnimation(mContext,
				R.anim.rotate_center);
		mAnimationCenter.setFillAfter(true);
		view.setAnimation(mAnimationCenter);
	}

}
