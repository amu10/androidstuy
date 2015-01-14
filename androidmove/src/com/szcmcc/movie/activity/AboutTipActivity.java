package com.szcmcc.movie.activity;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class AboutTipActivity extends BaseActivity {
	private ImageButton imBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_tip);
		initTitleBar();
		imBack = (ImageButton) findViewById(R.id.imBack);
		imBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public void onResume() {
		super.onResume();
		CmccDataStatistics.onStart(this);
	}

	public void onPause() {
		super.onPause();
		CmccDataStatistics.onStop(this);
	}

}
