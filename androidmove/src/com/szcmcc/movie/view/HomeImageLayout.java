package com.szcmcc.movie.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.szcmcc.movie.activity.HomePageActivity;
import com.szcmcc.movie.activity.LoadingActivity;
import com.szcmcc.movie.activity.MovieCinemaAct;
import com.szcmcc.movie.bean.MovieInfo;

public class HomeImageLayout extends LinearLayout implements OnClickListener{
	private Context mContext = null;


	public HomeImageLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		startSetting(context);
	}

	private void startSetting(Context context) {
		mContext = context;
		setOnClickListener(this);
	}

	public HomeImageLayout(Context context) {
		super(context);
		startSetting(context);
	}
	

	@Override
	public void onClick(View v) {
		
//		if(NetImageView.isWifi(mContext)){
//			System.out.println("HomePageActivity");
		Intent intent = new Intent(mContext, HomePageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
//        }else{
//        	System.out.println("MovieCinemaAct");
//        	Intent intent = new Intent(mContext, MovieCinemaAct.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            mContext.startActivity(intent);
//        }
//		Intent intent = new Intent(mContext, HomePageActivity.class);
//		mContext.startActivity(intent);
	}
}
