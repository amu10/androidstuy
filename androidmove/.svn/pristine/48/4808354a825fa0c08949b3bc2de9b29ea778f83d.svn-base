package com.szcmcc.movie.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class MovieDetialHeadListView extends ListView{

	public MovieDetialHeadListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public MovieDetialHeadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public MovieDetialHeadListView(Context context) {
		super(context);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
	
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			return super.onTouchEvent(ev);
		}
		if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			if (getParent() != null) {
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			return super.onTouchEvent(ev);
		}
		if (ev.getAction() == MotionEvent.ACTION_UP) {
				return super.onTouchEvent(ev);
		}

		return false;
	}

}
