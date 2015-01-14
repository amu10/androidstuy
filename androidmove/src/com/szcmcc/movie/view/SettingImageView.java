package com.szcmcc.movie.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.szcmcc.movie.activity.SettingAct;
import com.szcmcc.movie.bean.MovieInfo;

public class SettingImageView extends ImageView implements OnClickListener {

	private Context mContext = null;

	private MovieInfo movieInfo = null;
	public SettingImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		startSetting(context);
	}

	public SettingImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		startSetting(context);
	}

	private void startSetting(Context context) {
		mContext = context;
		setOnClickListener(this);
	}

	public SettingImageView(Context context) {
		super(context);
		startSetting(context);
	}

	public void setMovieInfo(MovieInfo movieInfo){
		this.movieInfo = movieInfo;
	}
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(mContext, SettingAct.class);
		intent.putExtra("movieInfo", movieInfo);
		mContext.startActivity(intent);
	}

}
