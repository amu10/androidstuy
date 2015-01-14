package com.szcmcc.movie.view;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class BiggerMyProcessbar extends ImageView{

	public BiggerMyProcessbar(Context context) {
        this(context, null);
    }
	public ExecutorService pool = Executors.newFixedThreadPool(1);
	public BiggerMyProcessbar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
//		TypedArray a = context.obtainStyledAttributes(attrs,
//    			R.styleable.myprocessbar);
//			a.recycle();
		stop_flag = true;
		index = -1;
		rr = null;
		startAnimation();
	}
//	String pb_str[] = {"","��","����","������"};
	int pb_res[] = null;//{R.drawable.progress1,R.drawable.p2,R.drawable.p3,R.drawable.p4};
	int index;
	
	
	boolean stop_flag = false;
	refresh_runnable rr;
	
	@Override
    public void setVisibility(int v) {
        if (getVisibility() != v) {
            super.setVisibility(v);
            if (v == GONE || v == INVISIBLE) {
            	stopAnimation();
            } else if (v == VISIBLE) {
                startAnimation();
            }
        }
    }

//	@Override
//	protected void onVisibilityChanged(View changedView, int v) {
//		  if (getVisibility() != v) {
//	            super.setVisibility(v);
//	            if (v == GONE || v == INVISIBLE) {
//	            	stopAnimation();
//	            } else if (v == VISIBLE) {
//	                startAnimation();
//	            }
//	        }
//	}
	private void startAnimation() {
		// TODO Auto-generated method stub
		stop_flag = false;
		if(rr == null){
			rr = new refresh_runnable();
			Log.d("", "new refresh_runnable()");
		}
		post(rr);
	}
	private void stopAnimation() {
		// TODO Auto-generated method stub
		stop_flag = true;
		removeCallbacks(rr);
//		rr = null;
	}
	
	class refresh_runnable implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(stop_flag)
				removeCallbacks(this);
			else{
				if(index == -1)
					index = 0;
				else
					index++;
				index %= pb_res.length;
				setImageResource(pb_res[index]);
//				setText(pb_str[index]);
				postDelayed(rr, 1000);
			}
		}
	}
}
