package com.szcmcc.movie.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.R;
import com.szcmcc.movie.util.PublicUtils;

public class AboutVersionActivity extends BaseActivity {

	private TextView verson = null;
	private ImageButton imBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_version);
		verson = (TextView) findViewById(R.id.verson);
		imBack = (ImageButton) findViewById(R.id.imBack);
		try {
			verson.setText("版本号：" + "v" + PublicUtils.getVersionName(AboutVersionActivity.this));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initTitleBar();
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
