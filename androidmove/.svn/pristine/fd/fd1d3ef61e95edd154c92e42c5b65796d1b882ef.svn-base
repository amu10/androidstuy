package com.szcmcc.movie.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MovieDetialHeadScrollView extends ScrollView {
	boolean flag = false;

	public MovieDetialHeadScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MovieDetialHeadScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MovieDetialHeadScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

	
		try{
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			return super.onTouchEvent(ev);
		}
		if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			Log.d("down", "down-------------");
			if (getParent() != null) {
				Log.d("parent", "parent--------");
				getParent().requestDisallowInterceptTouchEvent(true);
				flag = true;
			}
			return super.onTouchEvent(ev);
		}
		if (ev.getAction() == MotionEvent.ACTION_UP) {
			
				return super.onTouchEvent(ev);
			
		}
		}catch(IndexOutOfBoundsException e){
			e.printStackTrace();
		}
		Log.d("null", "null-----------");
		return false;
	}

}
