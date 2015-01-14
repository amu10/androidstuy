package com.szcmcc.movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.R;
import com.szcmcc.movie.util.PublicUtils;

public class AboutActivity extends BaseActivity {

	private LinearLayout versonInfo = null, tip = null, serviceMessage = null;
	private TextView about_verson = null;
	private ImageButton imBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		initTitleBar();
		findById();
		onClick();
	}

	private void findById() {
		versonInfo = (LinearLayout) findViewById(R.id.versonInfo);
		imBack = (ImageButton) findViewById(R.id.imBack);
		tip = (LinearLayout) findViewById(R.id.tip);
		serviceMessage = (LinearLayout) findViewById(R.id.serviceMessage);
		about_verson = (TextView) findViewById(R.id.about_verson);
		try {
			about_verson.setText("v" + PublicUtils.getVersionName(AboutActivity.this));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void onClick() {
		imBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		versonInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(AboutActivity.this, AboutVersionActivity.class);
				startActivity(in);
			}
		});
		tip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(AboutActivity.this, AboutTipActivity.class);
				startActivity(in);
			}
		});
		serviceMessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(AboutActivity.this, AboutServiceMessageActivity.class);
				startActivity(in);
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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		app.getAsyncImageLoader().recycleBitmaps();
	}
}
