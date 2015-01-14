package com.szcmcc.movie.picbrowse;


import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout.LayoutParams;

public class AnimUtil {
	 /*** ������Ҳ����Ķ���Ч�� * @return*/      
	public static Animation inFromRightAnimation() {          
		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(200);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;      
	}  
	
	/*** ���������˳��Ķ���Ч��       
	 * @return*/      
	public static Animation outToLeftAnimation() {          
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoLeft.setDuration(200);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
	} 
	
	/**       * �����������Ķ���Ч��       * @return       */      
	public static Animation inFromLeftAnimation() {          
		Animation inFromLeft = new TranslateAnimation(                  
				Animation.RELATIVE_TO_PARENT, -1.0f,                 
				Animation.RELATIVE_TO_PARENT, 0.0f,                  
				Animation.RELATIVE_TO_PARENT, 0.0f,                  
				Animation.RELATIVE_TO_PARENT, 0.0f);          
		inFromLeft.setDuration(200);          
		inFromLeft.setInterpolator(new AccelerateInterpolator());          
		return inFromLeft;      
		}    
	
	/*** ������Ҳ��˳�ʱ�Ķ���Ч��      
	 ** @return*/      
	public static Animation outToRightAnimation() {          
		Animation outtoRight = new TranslateAnimation(                  
				Animation.RELATIVE_TO_PARENT, 0.0f,                  
				Animation.RELATIVE_TO_PARENT, +1.0f,                  
				Animation.RELATIVE_TO_PARENT, 0.0f,                  
				Animation.RELATIVE_TO_PARENT, 0.0f);          
		outtoRight.setDuration(200);          
		outtoRight.setInterpolator(new AccelerateInterpolator());          
		return outtoRight;      
	}
	
	/*** ����Ӵ��ϻ�������ʱ�Ķ���Ч��      
	 ** @return*/      
	public static Animation moveUpToDownAnimation(final View view,final LayoutParams lp) {          
		Animation moveAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, 
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, 
                Animation.RELATIVE_TO_SELF, 1.0f);
		moveAction.setDuration(500);
		moveAction.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				view.setLayoutParams(lp);
				
			}
		});
		return moveAction;      
	}
	
	/*** ����Ӵ��ϻ�������ʱ�Ķ���Ч��      
	 ** @return*/      
	public static Animation moveUpToDownAnimation(final View view) {          
		Animation moveAction = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, 
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, 
                Animation.RELATIVE_TO_PARENT, 1.0f);
		moveAction.setDuration(500);
		moveAction.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.GONE);
				
			}
		});
		return moveAction;      
	}
	
	/*** ����Ӵ��ϻ�������ʱ�Ķ���Ч��      
	 ** @return*/      
	public static Animation moveUpToDownAnimation() {          
		Animation moveAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, 
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, 
                Animation.RELATIVE_TO_SELF, 1.0f);
		moveAction.setDuration(500);
		return moveAction;      
	}
	
	/*** ����Ӵ��ϻ�������ʱ�Ķ���Ч��      
	 ** @return*/      
	public static Animation moveUpToDownAnimationSelf(final View view) {          
		Animation moveAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, 
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, 
                Animation.RELATIVE_TO_SELF, 0.0f);
		moveAction.setDuration(300);
		moveAction.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.VISIBLE);
				
			}
		});
		return moveAction;      
	}
	
	/*** ����Ӵ��ϻ�������ʱ�Ķ���Ч��      
	 ** @return*/      
	public static Animation moveDownToUpAnimation(final View view) {          
		Animation moveAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, 
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, 
                Animation.RELATIVE_TO_SELF, -1.0f);
		moveAction.setDuration(300);
		moveAction.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.GONE);
				
			}
		});
		return moveAction;      
	}
	
	/*** ����Ӵ��ϻ�������ʱ�Ķ���Ч��      
	 ** @return*/      
	public static Animation moveDownToUpAnimation() {          
		Animation moveAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, 
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, 
                Animation.RELATIVE_TO_SELF, 0.0f);
		moveAction.setDuration(300);
		return moveAction;      
	}
	
	public static Animation getAlphaAnim(){
		AlphaAnimation myAnimation_Alpha=new AlphaAnimation(0.1f, 1.0f);//����1:������ʼʱ��͸����,����2:��������ʱ��͸����
		myAnimation_Alpha.setDuration(400);//����ʱ�����ʱ��Ϊ 5000����
		return myAnimation_Alpha;
	}

}
