package com.szcmcc.movie.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class BackImageLinear extends RelativeLayout implements OnClickListener{
	private Context mContext = null;

	public BackImageLinear(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		startSetting(context);
	}

	public BackImageLinear(Context context, AttributeSet attrs) {
		super(context, attrs);
		startSetting(context);
	}

	private void startSetting(Context context) {
		mContext = context;
		setOnClickListener(this);
	}

	public BackImageLinear(Context context) {
		super(context);
		startSetting(context);
	}

	@Override
	public void onClick(View v) {
		((Activity) mContext).finish();
	}
}
