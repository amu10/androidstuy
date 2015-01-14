package com.szcmcc.movie.activity;

import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.itotem.loghandler.ErrorHandler;
import com.itotem.view.zoomview.ViewZoomTouch;
import com.szcmcc.movie.R;
import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.bean.MovieSeatInfo;
import com.szcmcc.movie.bean.Point;
import com.szcmcc.movie.bean.PointList;
import com.szcmcc.movie.map.AppContext;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.util.Log;
import com.szcmcc.movie.util.ScreenInfo;

public class BaseActivity extends Activity {
	// 顶部信号区－－－－－－－－－－－－－－－－S

	protected com.szcmcc.movie.view.SettingImageView setting = null;
	protected com.szcmcc.movie.view.SettingImageLayout settingImageLayout = null;
	protected com.szcmcc.movie.view.TimeTextView currentTime = null;
	protected com.szcmcc.movie.view.NetImageView net = null;
	protected com.szcmcc.movie.view.HomeImageView home = null;
	protected com.szcmcc.movie.view.HomeImageLayout homeImageLayout = null;
	protected com.szcmcc.movie.view.BackImageView back = null;
	protected MovieApplication app;
	public MovieLib lib;
	public final static float TARGET_HEAP_UTILIZATION = 0.75f;
	public final static int CWJ_HEAP_SIZE = 6* 1024* 1024 ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (MovieApplication)getApplication();
		lib = MovieLib.getInstance(this);
		
		
		ScreenInfo.setScreenInfo(this);
		if (this instanceof HomePageActivity) {
			if (AppContext.getInstance().activitys.containsKey("MainAct")) {
				AppContext.getInstance().activitys.get("MainAct").finish();
			}
			AppContext.getInstance().activitys.put("MainAct",this);
		}
//		if (this instanceof SettingAct) {
//			if (AppContext.getInstance().activitys.containsKey("SettingAct")) {
//				AppContext.getInstance().activitys.get("SettingAct").finish();
//			}
//			AppContext.getInstance().activitys.put("SettingAct",this);
//		}
		ErrorHandler.registerNewErrorHandler(this);
		ErrorHandler.enableEmailReports("ttpx.pan@itotem.com.cn", "影视通异常信息汇报");
	}
	protected void initTitleBar() {
		home = (com.szcmcc.movie.view.HomeImageView) findViewById(R.id.home);
		homeImageLayout = (com.szcmcc.movie.view.HomeImageLayout) findViewById(R.id.homeLayout);
		setting = (com.szcmcc.movie.view.SettingImageView) findViewById(R.id.setting);
		back = (com.szcmcc.movie.view.BackImageView) findViewById(R.id.back);
		settingImageLayout = (com.szcmcc.movie.view.SettingImageLayout) findViewById(R.id.settingLayout);
		currentTime = (com.szcmcc.movie.view.TimeTextView) findViewById(R.id.currentTime);
		net = (com.szcmcc.movie.view.NetImageView) findViewById(R.id.net);
	}

//	protected void initBackTitleBar() {
//		home = (com.szcmcc.movie.view.HomeImageView) findViewById(R.id.home);
//		homeImageLayout = (com.szcmcc.movie.view.HomeImageLayout) findViewById(R.id.homeLayout);
//		currentTime = (com.szcmcc.movie.view.TimeTextView) findViewById(R.id.currentTime);
//		back = (com.szcmcc.movie.view.BackImageView) findViewById(R.id.back);
//		net = (com.szcmcc.movie.view.NetImageView) findViewById(R.id.net);
//	}

	protected ViewZoomTouch container_l = null;
	protected LinearLayout container_title = null;

	private int screen_width = 320;
	

	private TextView [][] seatPosition=null;
	
	protected void printTable(PointList mPointList,MovieSeatInfo movieSeat) {
		
//		System.out.println("dongdianzhousetprintTable1" + mPointList.column + "  " + mPointList.rows);
		seatPosition=new TextView [mPointList.rows][mPointList.column]; //座位数组暂时未传进来的行和列
		mPointList.seats = new Point[mPointList.rows + 1][mPointList.column + 1];//点的二维数组
		mPointList.lable = new Point[mPointList.column + 2];                 //标签行数座位列数外加2行标签
		// container = (LinearLayout) findViewById(R.id.container_l);
		// container.addView(getSeatLable());
		// 屏幕是320*480
		container_l = (ViewZoomTouch) findViewById(R.id.container_l); //座位
		container_title = (LinearLayout) findViewById(R.id.container_title);//座位标题
		LinearLayout linearRowNumLeft = (LinearLayout)findViewById(R.id.linear_row_num_1);//左边的作为排数
		LinearLayout linearRowNumRight = (LinearLayout)findViewById(R.id.linear_row_num_2);//右边的作为排数
		
		TableRow.LayoutParams tp = new TableRow.LayoutParams(mPointList.grid - 2,mPointList. grid - 2);//一个布局参数
		tp.leftMargin = 1;
		tp.rightMargin = 1;
		tp.topMargin = 1;
		tp.bottomMargin = 1;
		tp.gravity = Gravity.CENTER_VERTICAL;	
		//生成第一行数据lable
		for (int i = 2; i <= mPointList.column + 1; i++) {//设置第二列到到第一列数据
			if(i < 11){
				mPointList.lable[i - 1] = new Point("","","0" + (i - 1), 1);
			} else{
				mPointList.lable[i - 1] = new Point("","","" + (i - 1), 1);
			}
		}

		Log.i("----mPointList.column-------" + mPointList.column);

		for (int j = 1; j <= mPointList.column; j++) {
			Log.i(j + "----" + mPointList.lable[j - 1]);
		}
		for (int j = 1; j <= mPointList.column + 2 ; j++) {

			// 产生一行
			TextView seatText = new TextView(this);
			seatText.setGravity(Gravity.CENTER);
			seatText.setTextSize(8);
//			seatText.setPadding(1, 1, 1, 1);
			seatText.setMinHeight(mPointList.grid);
			seatText.setMaxHeight(mPointList.grid);

			Point bean = mPointList.lable[j - 1];//下面是设置第一行的数据

			if(bean != null){
				switch (bean.type) {
				case 1:// 当前仅处理1，其他的暂时不处理
					seatText.setText(bean.lable);
					seatText.setVisibility(View.VISIBLE);
					// seatText.setBackgroundResource(R.drawable.icon);
					break;
				case 0:// NULL
					seatText.setText(bean.row);
					seatText.setVisibility(View.INVISIBLE);
					break;
				}	
			}
			
			if (j == mPointList.column + 2) {//最后一列
//				container_title.addView(seatText, tp);
				TextView textNULL = new TextView(this);
				textNULL.setGravity(Gravity.CENTER);
				textNULL.setTextSize(8);
//				textNULL.setPadding(1, 1, 1, 1);
				textNULL.setMinHeight(mPointList.grid);
				textNULL.setMaxHeight(mPointList.grid);
				textNULL.setVisibility(View.INVISIBLE);
				container_title.addView(textNULL, tp);
			} else if (j == 1) { //第一列
				TextView textNULL = new TextView(this);
				textNULL.setGravity(Gravity.CENTER);
				textNULL.setTextSize(8);
//				textNULL.setPadding(1, 1, 1, 1);
				textNULL.setMinHeight(mPointList.grid);
				textNULL.setMaxHeight(mPointList.grid);
				textNULL.setVisibility(View.INVISIBLE);
				container_title.addView(textNULL, tp);
//				container_title.addView(seatText, tp);
			} else { //其他列
				container_title.addView(seatText, tp);
			}		
		}
		
		//生成坐标点数据
		if(movieSeat != null){
			if(movieSeat.seats != null && movieSeat.seats.size() > 0){
				for (int i = 0; i < movieSeat.seats.size(); i++) {
					int drawCol,drawRow;
					drawCol = Integer.parseInt(movieSeat.seats.get(i).graphCol);
					drawRow = Integer.parseInt(movieSeat.seats.get(i).graphRow);
					mPointList.seats[drawRow][drawCol] = new Point(movieSeat.seats.get(i).seatRow, movieSeat.seats.get(i).seatCol, movieSeat.seats.get(i).seatRow + movieSeat.seats.get(i).seatCol, Integer.parseInt(movieSeat.seats.get(i).seatState));
				}
			}
		}

		TableLayout tab = new TableLayout(this);

		for (int i = 1; i <= mPointList.rows; i++) {//行
			TableRow seatLine = new TableRow(this);
			TableRow seatLineNumLeft = new TableRow(this);
			TableRow seatLineNumRight = new TableRow(this);
			// 产生一行
			for (int j = 1; j <= mPointList.column + 2; j++) {//列

				if (j == 1) {//第一列
					TextView lableColumn = new TextView(this);
					lableColumn.setGravity(Gravity.CENTER);
					lableColumn.setTextSize(8);
//					lableColumn.setPadding(1, 1, 1, 1);
					lableColumn.setMinHeight(mPointList.grid);
					lableColumn.setMaxHeight(mPointList.grid);
					if(i < 10){
						lableColumn.setText("0" + i);
					} else {
						lableColumn.setText("" + i);
					}
					lableColumn.setVisibility(View.VISIBLE);
					seatLine.addView(lableColumn, tp);
//					seatLineNumLeft.addView(lableColumn, tp);
//					seatLine.addView(seatText, tp);
				} else if (j == mPointList.column + 2) {//最后一列
//					seatLine.addView(seatText, tp);
					TextView lableColumn = new TextView(this);
					lableColumn.setGravity(Gravity.CENTER);
					lableColumn.setTextSize(8);
//					lableColumn.setPadding(1, 1, 1, 1);
					lableColumn.setMinHeight(mPointList.grid);
					lableColumn.setMaxHeight(mPointList.grid);
					if(i < 10){
						lableColumn.setText("0" + i);
					} else {
						lableColumn.setText("" + i);
					}
					lableColumn.setVisibility(View.VISIBLE);
					seatLine.addView(lableColumn, tp);
//					seatLineNumRight.addView(lableColumn, tp);
				} else { //其他列 1,2
//					int drawCol,drawRow;
//					drawCol = Integer.parseInt(mPointList.mPoints.get)
					int drawlenth_Row = mPointList.seats.length;
					int drawlenth_Col = mPointList.seats[0].length;
					if(i < drawlenth_Row && j <= drawlenth_Col){
						if(mPointList.seats[i][j - 1] != null){//此行的此列中有座位
							TextView seatText = new TextView(this);
							seatText.setGravity(Gravity.CENTER);
							seatText.setTextSize(8);
//							seatText.setPadding(1, 1, 1, 1);
							seatText.setMinHeight(mPointList.grid);
							seatText.setMaxHeight(mPointList.grid);
//							seatText.setOnClickListener(new OnClickListener() {
//								
//								@Override
//								public void onClick(View v) {
//									Toast.makeText(BaseActivity.this, "hahha", Toast.LENGTH_SHORT).show();
//								}
//							});

							Point bean = mPointList.seats[i][j - 1];
							
							switch (bean.type) {//处理类型
							case 0://可售
								try{
								seatText.setId(Integer.parseInt(bean.lable));
								}catch(NumberFormatException e){
									e.printStackTrace();
								}
								seatText.setBackgroundResource(R.drawable.seat_use);
								seatText.setVisibility(View.VISIBLE);
								break;
							case 1:// 已售
								seatText.setBackgroundResource(R.drawable.seat_unuse);
								seatText.setVisibility(View.VISIBLE);
								// seatText.setBackgroundResource(R.drawable.icon);
								break;
							case -1://不可售则不显示其状态
//								seatText.setId(Integer.parseInt(bean.lable));
//								seatText.setId(Integer.parseInt(bean.lable));
//								seatText.setBackgroundResource(R.drawable.seat_use);
								seatText.setBackgroundResource(R.drawable.seat_unuse);
								seatText.setVisibility(View.VISIBLE);
								seatText.setClickable(false);
//								seatText.setVisibility(View.GONE);
								break;

							}
							seatLine.addView(seatText, tp);
						} else {//此行此列无座位仅为了撑起来布局
							TextView seatText = new TextView(this);
							seatText.setGravity(Gravity.CENTER);
							seatText.setTextSize(8);
//							seatText.setPadding(1, 1, 1, 1);
							seatText.setMinHeight(mPointList.grid);
							seatText.setMaxHeight(mPointList.grid);
							seatText.setBackgroundResource(R.drawable.seat_use);
							seatText.setVisibility(View.INVISIBLE);
							seatLine.addView(seatText,tp);
						}
					}else{//不在电影院数据范围内，和上面一样，仅起到填充布局的作用
						TextView seatText = new TextView(this);
						seatText.setGravity(Gravity.CENTER);
						seatText.setTextSize(8);
//						seatText.setPadding(1, 1, 1, 1);
						seatText.setMinHeight(mPointList.grid);
						seatText.setMaxHeight(mPointList.grid);
						seatText.setBackgroundResource(R.drawable.seat_use);
						seatText.setVisibility(View.INVISIBLE);
						seatLine.addView(seatText,tp);
					}
				}  
			}
			tab.addView(seatLine);
			if(linearRowNumLeft != null){
			linearRowNumLeft.addView(seatLineNumLeft);
			}
			if(linearRowNumRight != null){
			linearRowNumRight.addView(seatLineNumRight);
			}
		}
		container_l.addView(tab);
//		System.out.println("dongdianzhousetprintTable2" + mPointList.column + "  " + mPointList.rows);
	}
	
	/**
	 * /** 将资源文件drawable 用流的形式转为bitmap
	 * @param img ImageView
	 * @param res 资源文件，例：R.drawable.bg
	 */
	public void setBackGround(ImageView img,int res){
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		bmpFactoryOptions.inPurgeable = true;
		bmpFactoryOptions.inInputShareable = true;
		InputStream is = getResources().openRawResource(res);
		Bitmap imageBG = BitmapFactory.decodeStream(is,null,bmpFactoryOptions);
		img.setImageBitmap(imageBG);
	}

	/**
	 * 移除views方法
	 */
	public void removeViews(){
		try{
		if(container_l.getChildCount() > 0 ){
			container_l.removeAllViews();
		}
		if(container_title.getChildCount() > 0){
			container_title.removeAllViews();
		}}catch(NullPointerException e){
			e.printStackTrace();
		}
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

	// 顶部信号区－－－－－－－－－－－－－－－－E
}
