package com.szcmcc.movie.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.Point;
import com.szcmcc.movie.bean.PointList;
import com.szcmcc.movie.util.Log;

public class TestSelectedTable extends Activity {
	private Context mContext = TestSelectedTable.this;
	private PointList mPointList = new PointList();
	


	// private PointListAdapter mPointListAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// aact testselectedtable
		setContentView(R.layout.testselectedtable);
		printTable(mPointList);
		// mPointListAdapter = new PointListAdapter();
		// gridview.setAdapter(mPointListAdapter);
	}



	
	
	private LinearLayout container_l = null;
	private LinearLayout container_title = null;


	private int screen_width = 320;
	private int grid = 24;
	private void printTable(PointList mPointList) {
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
		for (int j = 1; j <= mPointList.column; j++) {
			if (j == 1 || j == mPointList.column) {
				mPointList.lable[j - 1] = new Point(0 + "", j + "",j + "", 0);
			} else {
				mPointList.lable[j - 1] = new Point(0 + "", j + "",j + "", 1);

			}

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
				seatText.setText(bean.row);
				seatText.setVisibility(View.VISIBLE);
				seatText.setBackgroundResource(R.drawable.icon_img);
				break;
			case 0:// NULL
				seatText.setText(bean.row);
				seatText.setVisibility(View.INVISIBLE);
				break;
			}
			if (j == 3 || j == (mPointList.column - 3)) {
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
					mPointList.seats[i - 1][j - 1] = new Point(i + "", j + "",i + "", 1);
				} else {
					mPointList.seats[i - 1][j - 1] = new Point(i + "", j + "",i + "", -1);
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
					seatText.setBackgroundResource(R.drawable.icon_img);
					break;
				case -1:// 图片
					seatText.setBackgroundResource(R.drawable.seat_use);
					seatText.setVisibility(View.VISIBLE);
					break;

				}

				if (j == 3 || j == (mPointList.column - 3)) {
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

	// public class PointListAdapter extends BaseAdapter {
	//
	// private LayoutInflater layoutFlater;
	//
	// public PointListAdapter() {
	// super();
	// layoutFlater = (LayoutInflater) mContext
	// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// }
	//
	// public int getCount() {
	// return mPointList.mPoints.size();
	// }
	//
	// public Object getItem(int position) {
	// return position;
	// }
	//
	// public long getItemId(int position) {
	// return position;
	// }
	//
	// public View getView(int position, View convertview, ViewGroup parent) {
	// final ViewHolderListViewItem holder;
	// if (convertview == null) {
	// convertview = layoutFlater.inflate(R.layout.grid_seat_item,
	// null);
	// holder = new ViewHolderListViewItem();
	// holder.seatnum = (TextView) convertview
	// .findViewById(R.id.seatnum);
	// holder.seatimage = (ImageView) convertview
	// .findViewById(R.id.seatimage);
	// holder.textspace = (TextView) convertview
	// .findViewById(R.id.textspace);
	// convertview.setTag(holder);
	// } else {
	// holder = (ViewHolderListViewItem) convertview.getTag();
	// holder.visibleView.setVisibility(View.INVISIBLE);
	//
	// }
	//
	// Point bean = mPointList.mPoints.get(position);
	//
	// switch (bean.type) {
	// case -1:
	// holder.seatnum.setVisibility(View.VISIBLE);
	// holder.seatnum.setText(bean.lable);
	// holder.visibleView=holder.seatnum;
	// break;
	// case 1:
	// holder.seatimage.setVisibility(View.VISIBLE);
	// holder.visibleView=holder.seatimage;
	// break;
	// case 0:
	// holder.textspace.setVisibility(View.VISIBLE);
	// holder.visibleView=holder.textspace;
	// break;
	//
	// }
	// return convertview;
	// }
	// }

	// private static class ViewHolderListViewItem {
	//
	// public View visibleView =null;
	// public TextView seatnum =null;
	// public ImageView seatimage =null;
	// public TextView textspace =null;
	//
	//
	// }

	private LinearLayout getSeatLable() {
		LinearLayout line = new LinearLayout(mContext);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		line.setLayoutParams(params);
		line.addView(getSeatTextLable(" "));

		for (int i = 1; i <= 14; i++) {
			line.addView(getSeatTextLable(i + ""));
		}
		line.addView(getSeatTextLable(" "));
		return line;
	}

	// <TextView android:layout_width="23dip" android:textSize="16sp"
	// android:textColor="#6A6862" android:textStyle="bold"
	// android:ellipsize="end" android:singleLine="true"
	// android:layout_gravity="center_vertical" android:layout_height="23dip"
	// android:textAppearance="?android:attr/textAppearanceMedium"
	// android:gravity="center" android:text="4" />
	private View getSeatTextLable(String num) {
		TextView mTextView = new TextView(mContext);
		Log.i("------------------[num=" + num + "]------------");
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(20, 20);
		p.gravity = Gravity.CENTER_VERTICAL;
		p.setMargins(3, 3, 3, 3);
		mTextView.setLayoutParams(p);
		mTextView.setTextSize(16);
		mTextView.setGravity(Gravity.CENTER);
		mTextView.setTextColor(R.color.pay_success);
		mTextView.setText("" + num);
		return mTextView;
	}

	// <ImageView android:layout_width="20dip" android:background="#308AB9"
	// android:layout_margin="3dip" android:layout_height="20dip"
	// android:layout_gravity="center_vertical" />
	private View getSeatImage(int x, int y) {
		ImageView mImageView = new ImageView(mContext);
		Log.i("------------------[x=" + x + ",y=" + y + "]------------");
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(20, 20);
		p.gravity = Gravity.CENTER_VERTICAL;
		p.setMargins(3, 3, 3, 3);
		mImageView.setLayoutParams(p);
		mImageView.setBackgroundColor(R.color.pay_success);
		return mImageView;
	}
}
