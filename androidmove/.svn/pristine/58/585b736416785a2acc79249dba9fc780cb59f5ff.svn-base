package com.szcmcc.movie.util;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

/**
 * 执行3D旋转的动画类
 * @author pengjun
 */
public class Transition3d {

	/**
	 * 动画旋转的方向
	 */
	private static boolean curRotation; // 正转还是反转
	/**
	 * 事件的view
	 */
	private static View eventView;
	
	/**
	 * 持续时间
	 */
	private static boolean isDuration = true;
	
    /**
     * 动画监听,执行完第一个动画后需要监听并执行第二个动画效果
     * 
     */
    private static  final class DisplayNextView implements Animation.AnimationListener {
    	View visibleView;
        private DisplayNextView(View visibleView) {
        	this.visibleView = visibleView;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
        	visibleView.post(new SwapViews(visibleView));
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    /**
     * 负责开始执行第二个动画效果使第一个view 隐藏
     * 并使第二个view显示
     */
    private static final class SwapViews implements Runnable {
    	View visibleView;
        public SwapViews(View visibleView) {
        	this.visibleView = visibleView;
        }
        public void run() {
            final float centerX = visibleView.getWidth() / 2.0f;
            final float centerY = visibleView.getHeight() / 2.0f;
            final Rotate3dAnimation rotation;
            if (curRotation) {
            	 if(isDuration){
            		 rotation = new Rotate3dAnimation(-90, 0, centerX, centerY, 310.0f, false);     
            	 }else{
            		 rotation = new Rotate3dAnimation(-90, -15, centerX, centerY+80, 310.0f, false);    
            		 //-15 ,+80
            	 }
            } else {
                rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 310.0f, false);
            } 
            if(isDuration){
            rotation.setDuration(500);
            }
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());
            visibleView.startAnimation(rotation);
            visibleView.setVisibility(View.VISIBLE);
            if (eventView != null) {
    			eventView.setEnabled(true);
    		}
        }
    }

    /**
	 * 		两个view的反转动画
	 * @param goneView	需要隐藏的view
	 * @param visibleView	需要显示的view
	 * @param isRotation	旋转的方向,true,false
	 */
	private static void executeRotation(View goneView,
			View visibleView,boolean isRotation) {
		// TODO Auto-generated method stub
		final float centerX = goneView.getWidth() / 2.0f;
		final float centerY = goneView.getHeight() / 2.0f;
		 curRotation = isRotation;
		 final Rotate3dAnimation rotation;
		 if(curRotation){
		     rotation = new Rotate3dAnimation(0, 90, centerX, centerY, 310.0f, true);
		 }else{
			 rotation =  new Rotate3dAnimation(0, -90, centerX, centerY, 310.0f, true);
		 }
		 if(isDuration){
			 rotation.setDuration(300);			 
		 }
//	        rotation.setFillAfter(true);//导致动画后无事件问题
	        rotation.setInterpolator(new AccelerateInterpolator());
	        rotation.setAnimationListener(new DisplayNextView(visibleView));
	        goneView.startAnimation(rotation);
	        goneView.setVisibility(View.INVISIBLE);
	}

	/**
	 * 		两个view的反转动画
	 * @param goneView	需要隐藏的view
	 * @param visibleView	需要显示的view
	 * @param isRotation	旋转的方向,true,false
	 * @param buttonView 执行动画时是否屏蔽按钮事件,不屏蔽可以传null
	 */
	public static void executeRotation(View goneView,
			View visibleView,boolean isRotation,View buttonView) {
		isDuration = true;
		executeRotation(goneView,visibleView,isRotation);
		eventView = buttonView;
		if (eventView != null) {
			eventView.setEnabled(false);
		}
	}
	
	public static void executeTrapezoidLayout(View view){
		isDuration = false;
		executeRotation(view,view,true);
		
	}
}
