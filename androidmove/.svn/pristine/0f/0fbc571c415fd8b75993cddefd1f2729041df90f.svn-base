package com.szcmcc.movie.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.itotem.loghandler.ErrorHandler;
import com.mapabc.mapapi.map.MapActivity;
import com.szcmcc.movie.R;
import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.bean.Point;
import com.szcmcc.movie.bean.PointList;
import com.szcmcc.movie.util.Log;
import com.szcmcc.movie.util.ScreenInfo;

public class BaseMapActivity extends MapActivity {
	// 顶部信号区－－－－－－－－－－－－－－－－S

	protected com.szcmcc.movie.view.SettingImageView setting = null;
	protected com.szcmcc.movie.view.HomeImageLayout homeImageLayout = null;
	protected com.szcmcc.movie.view.SettingImageLayout settingImageLayout = null;
	protected com.szcmcc.movie.view.TimeTextView currentTime = null;
	protected com.szcmcc.movie.view.NetImageView net = null;
	protected com.szcmcc.movie.view.HomeImageView home = null;
	protected com.szcmcc.movie.view.BackImageView back = null;
	protected MovieApplication app;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		if (this instanceof MovieCinemaAct) {
//			if (AppContext.getInstance().activitys
//					.containsKey("MovieCinemaAct")) {
//				AppContext.getInstance().activitys.get("MovieCinemaAct")
//						.finish();
//			}
//			AppContext.getInstance().activitys.put("MovieCinemaAct", this);
//		}
		app = (MovieApplication)getApplication();
		ScreenInfo.setScreenInfo(this);
		ErrorHandler.registerNewErrorHandler(this);
		ErrorHandler.enableEmailReports("ttpx.pan@itotem.com.cn", "影视通异常信息汇报");
	}

	protected void initTitleBar() {
		settingImageLayout = (com.szcmcc.movie.view.SettingImageLayout) findViewById(R.id.settingLayout);
		setting = (com.szcmcc.movie.view.SettingImageView) findViewById(R.id.setting);
		currentTime = (com.szcmcc.movie.view.TimeTextView) findViewById(R.id.currentTime);
		net = (com.szcmcc.movie.view.NetImageView) findViewById(R.id.net);
	}

	protected void initBackTitleBar() {
		homeImageLayout = (com.szcmcc.movie.view.HomeImageLayout) findViewById(R.id.homeLayout);
		home = (com.szcmcc.movie.view.HomeImageView) findViewById(R.id.home);
		currentTime = (com.szcmcc.movie.view.TimeTextView) findViewById(R.id.currentTime);
		back = (com.szcmcc.movie.view.BackImageView) findViewById(R.id.back);
		net = (com.szcmcc.movie.view.NetImageView) findViewById(R.id.net);
	}

	private LinearLayout container_l = null;
	private LinearLayout container_title = null;

	private int screen_width = 320;
	private int grid = 25;

	protected void printTable(PointList mPointList) {
		mPointList.seats = new Point[30][mPointList.column];
		mPointList.lable = new Point[mPointList.column];
		// container = (LinearLayout) findViewById(R.id.container_l);
		// container.addView(getSeatLable());
		// 屏幕是320*480
		container_l = (LinearLayout) findViewById(R.id.container_l);
		container_title = (LinearLayout) findViewById(R.id.container_title);
		TableRow.LayoutParams tp = new TableRow.LayoutParams(grid - 2, grid - 2);
		tp.leftMargin = 1;
		tp.rightMargin = 1;
		tp.topMargin = 1;
		tp.bottomMargin = 1;
		tp.gravity = Gravity.CENTER_VERTICAL;

		TableRow.LayoutParams margintp = new TableRow.LayoutParams(
				TableRow.LayoutParams.FILL_PARENT,
				TableRow.LayoutParams.WRAP_CONTENT);
		margintp.gravity = Gravity.CENTER_VERTICAL;
		margintp.weight = 1;
		int lj = 0;
		boolean isdouble = false;
		if (mPointList.column % 2 == 0) {
			lj = mPointList.column / 2;
			isdouble = true;
		} else {
			lj = mPointList.column / 2;
			isdouble = false;
		}
		for (int j = 1; j <= lj; j++) {
			Log.i("-----------" + j);
			if (j == lj && !isdouble) {
				Log.i("-----------" + j);
				Log.i((mPointList.column - j) + "----column-----" + (2 * j - 1));
				Log.i((j - 1) + "----column-----" + (2 * j));
				mPointList.lable[mPointList.column - j] = new Point(0 + "",
						(2 * j - 1) + "", j + "", 1);
				mPointList.lable[j - 1] = new Point(0 + "", (2 * j) + "", j
						+ "", 1);
				Log.i((mPointList.column - j + 1)
						+ "----mPointList.column-----" + (mPointList.column));
				mPointList.lable[lj] = new Point(0 + "",
						mPointList.column + "", j + "", 1);
			} else {
				Log.i((mPointList.column - j) + "----column-----" + (2 * j - 1));
				Log.i((j - 1) + "----column-----" + (2 * j));
				mPointList.lable[mPointList.column - j] = new Point(0 + "",
						(2 * j - 1) + "", j + "", 1);
				mPointList.lable[j - 1] = new Point(0 + "", (2 * j) + "", j
						+ "", 1);
			}

		}

		Log.i("----mPointList.column-------" + mPointList.column);

		for (int j = 1; j <= mPointList.column; j++) {
			Log.i(j + "----" + mPointList.lable[j - 1]);
		}
		for (int j = 1; j <= mPointList.column; j++) {

			// 产生一行
			TextView seatText = new TextView(this);
			seatText.setGravity(Gravity.CENTER);
			seatText.setTextSize(12);
			seatText.setPadding(1, 1, 1, 1);
			seatText.setMinHeight(grid);
			seatText.setMaxHeight(grid);

			Point bean = mPointList.lable[j - 1];

			switch (bean.type) {
			case 1:// 文 字
				seatText.setText(bean.column);
				seatText.setVisibility(View.VISIBLE);
				// seatText.setBackgroundResource(R.drawable.icon);
				break;
			case 0:// NULL
				seatText.setText(bean.row);
				seatText.setVisibility(View.INVISIBLE);
				break;
			}
			if (j == mPointList.column) {
				container_title.addView(seatText, tp);
				TextView textNULL = new TextView(this);
				textNULL.setGravity(Gravity.CENTER);
				textNULL.setTextSize(12);
				textNULL.setPadding(1, 1, 1, 1);
				textNULL.setMinHeight(grid);
				textNULL.setMaxHeight(grid);
				textNULL.setVisibility(View.INVISIBLE);
				container_title.addView(textNULL, tp);
			} else if (j == 1) {
				TextView textNULL = new TextView(this);
				textNULL.setGravity(Gravity.CENTER);
				textNULL.setTextSize(12);
				textNULL.setPadding(1, 1, 1, 1);
				textNULL.setMinHeight(grid);
				textNULL.setMaxHeight(grid);
				textNULL.setVisibility(View.INVISIBLE);
				container_title.addView(textNULL, tp);
				container_title.addView(seatText, tp);
			} else if (j == 2 || j == (mPointList.column - 2)) {
				// if (j == 2 || j == (mPointList.column - 2)) {
				container_title.addView(seatText, tp);
				TextView seatNone = new TextView(this);
				seatNone.setGravity(Gravity.CENTER);
				seatNone.setTextSize(12);
				seatNone.setPadding(1, 1, 1, 1);
				container_title.addView(seatNone, margintp);

			} else {
				container_title.addView(seatText, tp);
			}
		}

		for (int i = 1; i <= mPointList.rows; i++) {
			for (int j = 1; j <= mPointList.column; j++) {
				if (j == 1 || j == mPointList.column) {
					mPointList.seats[i - 1][j - 1] = new Point(i + "", j + "",
							i + "", -1);
				} else {
					mPointList.seats[i - 1][j - 1] = new Point(i + "", j + "",
							i + "", -1);
				}

			}

		}

		TableLayout tab = new TableLayout(this);

		for (int i = 1; i <= mPointList.rows; i++) {
			TableRow seatLine = new TableRow(this);

			// 产生一行
			for (int j = 1; j <= mPointList.column; j++) {

				TextView seatText = new TextView(this);
				seatText.setGravity(Gravity.CENTER);
				seatText.setTextSize(12);
				seatText.setPadding(1, 1, 1, 1);
				seatText.setMinHeight(grid);
				seatText.setMaxHeight(grid);

				Point bean = mPointList.seats[i - 1][j - 1];

				switch (bean.type) {
				case 1:// 文 字
					seatText.setText(bean.row);
					seatText.setVisibility(View.VISIBLE);
					// seatText.setBackgroundResource(R.drawable.icon);
					break;
				case -1:// 图片
					seatText.setBackgroundResource(R.drawable.seat_use);
					seatText.setVisibility(View.VISIBLE);
					break;

				}

				if (j == 1) {
					TextView lableColumn = new TextView(this);
					lableColumn.setGravity(Gravity.CENTER);
					lableColumn.setTextSize(12);
					lableColumn.setPadding(1, 1, 1, 1);
					lableColumn.setMinHeight(grid);
					lableColumn.setMaxHeight(grid);
					lableColumn.setText(i + "");
					seatLine.addView(lableColumn, tp);
					seatLine.addView(seatText, tp);
				} else if (j == mPointList.column) {
					seatLine.addView(seatText, tp);
					TextView lableColumn = new TextView(this);
					lableColumn.setGravity(Gravity.CENTER);
					lableColumn.setTextSize(12);
					lableColumn.setPadding(1, 1, 1, 1);
					lableColumn.setMinHeight(grid);
					lableColumn.setMaxHeight(grid);
					lableColumn.setText(i + "");
					seatLine.addView(lableColumn, tp);
				} else if (j == 2 || j == (mPointList.column - 2)) {
					seatLine.addView(seatText, tp);
					TextView seatNone = new TextView(this);
					seatNone.setGravity(Gravity.CENTER);
					seatNone.setTextSize(12);
					seatNone.setPadding(1, 1, 1, 1);
					seatLine.addView(seatNone, margintp);

				} else {
					seatLine.addView(seatText, tp);
				}

			}
			tab.addView(seatLine);
		}
		container_l.addView(tab);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (net != null) {
			net.onResume();
		}

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (net != null) {
			net.onStop();
		}

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	// 顶部信号区－－－－－－－－－－－－－－－－E
}
