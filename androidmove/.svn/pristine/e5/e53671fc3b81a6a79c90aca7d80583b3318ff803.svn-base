package com.szcmcc.movie.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.R;

public class AboutServiceMessageActivity extends BaseActivity {
	private ImageButton imBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_service_message);
		initTitleBar();
		imBack = (ImageButton) findViewById(R.id.imBack);
		imBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
