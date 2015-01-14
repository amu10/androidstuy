package com.szcmcc.movie.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.sdk.CmccDataStatistics;
import com.itotem.view.scrollview.HVScrollView;
import com.itotem.view.scrollview.SyncHorizontalScrollView;
import com.itotem.view.scrollview.SyncScrollView;
import com.itotem.view.zoomview.ViewZoomTouchSeat;
import com.szcmcc.movie.R;
import com.szcmcc.movie.adapter.ShowSelectSeatAdapter;
import com.szcmcc.movie.bean.LockOrDebLockMovieSeatsInfo;
import com.szcmcc.movie.bean.MovieSeatInfo;
import com.szcmcc.movie.bean.Point;
import com.szcmcc.movie.bean.PointList;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.util.RoateUtil;
import com.szcmcc.movie.util.ScreenInfo;
import com.szcmcc.movie.util.TextUtil;
import com.szcmcc.movie.view.MarqueeTextView;
import com.szcmcc.movie.view.MessageDialog;

public class SeatSelectedAct extends BaseActivity {
	private Context mContext = SeatSelectedAct.this;
	private PointList mPointList = new PointList();

	private TextView seatSelectNum = null, select_seat_tishi = null;
	private LinearLayout gopayseat = null;
//	private LinearLayout bottom = null;

	// 座位
	// private MovieSeatInfo movieSeatInfo = null;
	private String moviecinema_id = "";
	// private String movieSeat_ShowCode = "";
	private String cinemaRoom = "", cinemaTime = "";
	// 锁位编号、座行、列行、座区号、票价之间用|分割
	private String lockedType = "0";// 锁定类型：0为锁定，1为释放，暂时默认为锁定
	private String ticketCount = "0";// 票的张数 暂时默认为2
	private String recvPhone = "";// 订票电话，方法写好（可能不获取全部电话）
	private String seatCode = "";
	private String seatRow = "";
	private String seatCol = "";
	private String seatAreaCode = "";
	private String payPrices = "";// 票价，暂时默认为90
	private String payPrice = "";
	private String movie_ShowTime = "", movie_CinemaName = "", movie_Name = "", cover_image_url = "", companyId;

	private ImageView backgroundImage = null;
	private LockOrDebLockMovieSeatsInfo lockMovieSeatsInfo;
	private int mostCol, mostRow;

	private ViewZoomTouchSeat container_l = null;
	private LinearLayout container_title = null;

	// 下面是选中后添加到显示区域
	private ListView list_showSeatSelect;
	private ShowSelectSeatAdapter showSelectSeatAdapter;
	private List<String> list_price, list_seatCode, list_seatRow, list_seatCol, list_seatAreaCode;
	private int seatSelectedNum = 0;

	HVScrollView hvScrollView;// 内容滚动
	SyncScrollView syncScrollViewLeft; // 左边滚动
	SyncScrollView syncScrollViewRight; // 右边滚动
	SyncHorizontalScrollView shScroolView;// 顶部滚动

	public static Activity seatSelectedAct;
	private ImageView jia, jian;
	GetMovieSeatInfoTask getMovieSeatInfoTask = null;
	private String showcode = "";
	private MarqueeTextView cinemaName = null;
	private TextView date = null,time = null,movieName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seatselectedact);
		initTitleBar();
		findView();
		getExtra();
		seatSelectedAct = this;
		// mPointList.rows = ScreenInfo.getSeatGridWidth();
		
		// ============
//		if (!cover_image_url.equals(""))
//			setImageBackGround(backgroundImage, cover_image_url);
			setBackGround(backgroundImage,R.drawable.brown_background);

		onClick();
		initdata();
//		RoateUtil.tranlateTenDown(mContext, bottom);
		if (getMovieSeatInfoTask != null) {
			getMovieSeatInfoTask.cancel(true);
		}
		getMovieSeatInfoTask = new GetMovieSeatInfoTask(SeatSelectedAct.this, "");
		getMovieSeatInfoTask.execute(showcode);
	}


	private void findView(){
		backgroundImage = (ImageView) findViewById(R.id.bigimage);
		select_seat_tishi = (TextView) findViewById(R.id.textview_seat_noselected);
		select_seat_tishi.setVisibility(View.VISIBLE);
		seatSelectNum = (TextView) findViewById(R.id.seatSelectNum);
		seatSelectNum.setText("" + seatSelectedNum);
		hvScrollView = (HVScrollView) findViewById(R.id.hv_scroll_view);// 内容滚动
		syncScrollViewLeft = (SyncScrollView) findViewById(R.id.scroll_view_1); // 左边滚动
		syncScrollViewRight = (SyncScrollView) findViewById(R.id.scroll_view_2); // 右边滚动
		shScroolView = (SyncHorizontalScrollView) findViewById(R.id.scroll_view_horizontal); // 顶部滚动
		jia = (ImageView) findViewById(R.id.jia);
		jian = (ImageView) findViewById(R.id.jian);
		movieName = (TextView)findViewById(R.id.movieName);
		cinemaName = (MarqueeTextView)findViewById(R.id.cinemaName);
//		room = (MarqueeTextView)findViewById(R.id.room);
		date = (TextView)findViewById(R.id.date);
		time = (TextView)findViewById(R.id.time);
		hvScrollView.setScrollHView(shScroolView);
		hvScrollView.setScrollVView(syncScrollViewLeft);
		hvScrollView.setScrollV2View(syncScrollViewRight);
		// ============
		shScroolView.setScrollView(hvScrollView);
		syncScrollViewLeft.setScrollView(hvScrollView);
		syncScrollViewRight.setScrollView(hvScrollView);
	}

	/**
	 * 设置下面的座位布局
	 */
	private void setSeatLayout(int seatCol, int seatRow, MovieSeatInfo movieSeatInfo) {
		// System.out.println("dongdianzhousetSeatLayout1" + mostCol + "  " +
		// mostRow);
		mPointList.column = seatCol;
		mPointList.rows = seatRow;
		mPointList.grid = ScreenInfo.getSeatGridWidth(seatCol + 2);
		printTableLocal(mPointList, movieSeatInfo);
		// System.out.println("dongdianzhousetSeatLayout2" + mostCol + "  " +
		// mostRow);
	}

	/**
	 * 计算最大的行和列
	 */
	private void computerMostColAndRow(MovieSeatInfo movieSeatInfo) {
		int tempCol, tempRow;
		if (movieSeatInfo != null) {
			if (movieSeatInfo.seats != null && movieSeatInfo.seats.size() > 0) {
				mostCol = Integer.parseInt(movieSeatInfo.seats.get(0).graphCol);
				mostRow = Integer.parseInt(movieSeatInfo.seats.get(0).graphRow);
				for (int i = 1; i < movieSeatInfo.seats.size(); i++) {
					tempCol = Integer.parseInt(movieSeatInfo.seats.get(i).graphCol);
					tempRow = Integer.parseInt(movieSeatInfo.seats.get(i).graphRow);
					if (mostCol < tempCol) {
						mostCol = tempCol;
					}
					if (mostRow < tempRow) {
						mostRow = tempRow;
					}
				}
			}
		}
	}

	/**
	 * 生成座位的方法
	 */
	protected void printTableLocal(PointList mPointList, final MovieSeatInfo movieSeat) {

		// seatPosition=new TextView [mPointList.rows][mPointList.column];
		// //座位数组暂时未传进来的行和列
		mPointList.seats = new Point[mPointList.rows + 1][mPointList.column + 1];// 点的二维数组
		mPointList.lable = new Point[mPointList.column + 2]; // 标签行数座位列数外加2行标签
		// container = (LinearLayout) findViewById(R.id.container_l);
		// container.addView(getSeatLable());
		// 屏幕是320*480
		container_l = (ViewZoomTouchSeat) findViewById(R.id.container_l); // 座位
		container_title = (LinearLayout) findViewById(R.id.container_title);// 座位标题
		LinearLayout linearRowNumLeft = (LinearLayout) findViewById(R.id.linear_row_num_1);// 左边的作为排数
		LinearLayout linearRowNumRight = (LinearLayout) findViewById(R.id.linear_row_num_2);// 右边的作为排数

		TableRow.LayoutParams tp = new TableRow.LayoutParams(mPointList.grid - 2, mPointList.grid - 2);// 一个布局参数
		tp.leftMargin = 1;
		tp.rightMargin = 1;
		tp.topMargin = 1;
		tp.bottomMargin = 1;
		tp.gravity = Gravity.CENTER_VERTICAL;

		TableRow.LayoutParams tp1 = new TableRow.LayoutParams(5, mPointList.grid - 2);// 一个布局参数
		tp.leftMargin = 1;
		tp.rightMargin = 1;
		tp.topMargin = 1;
		tp.bottomMargin = 1;
		tp.gravity = Gravity.CENTER_VERTICAL;

		// 生成第一行数据lable
		for (int i = 2; i <= mPointList.column + 1; i++) {// 设置第二列到到第一列数据
			if (i < 11) {
				mPointList.lable[i - 1] = new Point("", "", "0" + (i - 1), 1);
			} else {
				mPointList.lable[i - 1] = new Point("", "", "" + (i - 1), 1);
			}
		}
		for (int j = 1; j <= mPointList.column + 2; j++) {

			// 产生一行
			TextView seatText = new TextView(this);
			seatText.setBackgroundResource(R.drawable.dot);
			seatText.setGravity(Gravity.CENTER);
			seatText.setTextSize(8);
			// seatText.setPadding(1, 1, 1, 1);
			seatText.setMinHeight(mPointList.grid);
			seatText.setMaxHeight(mPointList.grid);
			// seatText.setTextColor(R.color.seat_select_coordinate_heng);

			Point bean = mPointList.lable[j - 1];// 下面是设置第一行的数据

			if (bean != null) {
				switch (bean.type) {
				case 1:// 当前仅处理1，其他的暂时不处理
//					seatText.setText(bean.lable);
					seatText.setVisibility(View.VISIBLE);
					// seatText.setBackgroundResource(R.drawable.icon);
					break;
				case 0:// NULL
					seatText.setText(bean.row);
					seatText.setVisibility(View.INVISIBLE);
					break;
				}
			}
			ImageView iv = new ImageView(this);
			if ((mPointList.column + 2 + 1) % 2 == 0) {
				if (j == (mPointList.column + 2 + 1) / 2) {
					container_title.addView(iv, tp1);
				}
			} else {
				if (j == (mPointList.column + 2 + 2) / 2) {
					container_title.addView(iv, tp1);
				}
			}
			if (j == mPointList.column + 2) {// 最后一列
				// container_title.addView(seatText, tp);
				TextView textNULL = new TextView(this);
				textNULL.setGravity(Gravity.CENTER);
				textNULL.setTextSize(8);
				// textNULL.setPadding(1, 1, 1, 1);
				textNULL.setMinHeight(mPointList.grid);
				textNULL.setMaxHeight(mPointList.grid);
				textNULL.setVisibility(View.INVISIBLE);
				// textNULL.setTextColor(R.color.seat_select_coordinate_heng);
				container_title.addView(textNULL, tp);
			} else if (j == 1) { // 第一列
				TextView textNULL = new TextView(this);
				textNULL.setGravity(Gravity.CENTER);
				textNULL.setTextSize(8);
				// textNULL.setPadding(1, 1, 1, 1);
				textNULL.setMinHeight(mPointList.grid);
				textNULL.setMaxHeight(mPointList.grid);
				textNULL.setVisibility(View.INVISIBLE);
				// textNULL.setTextColor(R.color.seat_select_coordinate_heng);
				container_title.addView(textNULL, tp);
				// container_title.addView(seatText, tp);
			} else { // 其他列
				container_title.addView(seatText, tp);
			}

		}

		// 生成坐标点数据
		if (movieSeat != null) {
			if (movieSeat.seats != null && movieSeat.seats.size() > 0) {
				for (int i = 0; i < movieSeat.seats.size(); i++) {
					// System.out.println("dongdianzhouSeatSelectAct" +
					// movieSeat.seats.get(i).seatState);
					int drawCol, drawRow;
					drawCol = Integer.parseInt(movieSeat.seats.get(i).graphCol);
					drawRow = Integer.parseInt(movieSeat.seats.get(i).graphRow);
					mPointList.seats[drawRow][drawCol] = new Point(movieSeat.seats.get(i).seatRow, movieSeat.seats.get(i).seatCol,
							movieSeat.seats.get(i).seatRow + "," + movieSeat.seats.get(i).seatCol, Integer.parseInt(movieSeat.seats.get(i).seatState));
				}
			}
		}

		TableLayout tab = new TableLayout(this);

		for (int i = 1; i <= mPointList.rows; i++) {// 行
			TableRow seatLine = new TableRow(this);
			TableRow seatLineNumLeft = new TableRow(this);
			TableRow seatLineNumRight = new TableRow(this);
			// 产生一行
			for (int j = 1; j <= mPointList.column + 2; j++) {// 列
				ImageView iv = new ImageView(this);
				iv.setImageResource(R.drawable.cinema_middle_line);
				if ((mPointList.column + 2 + 1) % 2 == 0) {
					if (j == (mPointList.column + 2 + 1) / 2) {
						seatLine.addView(iv, tp1);
					}
				} else {
					if (j == (mPointList.column + 2 + 2) / 2) {
						seatLine.addView(iv, tp1);
					}
				}
				if (j == 1) {// 第一列
					TextView lableColumn = new TextView(this);
					lableColumn.setBackgroundResource(R.drawable.dot);
					lableColumn.setGravity(Gravity.CENTER);
					lableColumn.setTextSize(8);
					// lableColumn.setPadding(1, 1, 1, 1);
					lableColumn.setMinHeight(mPointList.grid);
					lableColumn.setMaxHeight(mPointList.grid);
//					if (i < 10) {
//						lableColumn.setText("0" + i);
//					} else {
//						lableColumn.setText("" + i);
//					}
					lableColumn.setVisibility(View.VISIBLE);
					// lableColumn.setTextColor(R.color.seat_select_coordinate_shu);
					// seatLine.addView(lableColumn, tp);
					seatLineNumLeft.addView(lableColumn, tp);
					// seatLine.addView(seatText, tp);
				} else if (j == mPointList.column + 2) {// 最后一列
					// seatLine.addView(seatText, tp);
					TextView lableColumn = new TextView(this);
					lableColumn.setBackgroundResource(R.drawable.dot);
					lableColumn.setGravity(Gravity.CENTER);
					lableColumn.setTextSize(8);
					// lableColumn.setPadding(1, 1, 1, 1);
					lableColumn.setMinHeight(mPointList.grid);
					lableColumn.setMaxHeight(mPointList.grid);
//					if (i < 10) {
//						lableColumn.setText("0" + i);
//					} else {
//						lableColumn.setText("" + i);
//					}
					lableColumn.setVisibility(View.VISIBLE);
					// lableColumn.setTextColor(R.color.seat_select_coordinate_shu);
					// seatLine.addView(lableColumn, tp);
					seatLineNumRight.addView(lableColumn, tp);
				} else { // 从第二列开始
					// int drawCol,drawRow;
					// drawCol = Integer.parseInt(mPointList.mPoints.get)
					int drawlenth_Row = mPointList.seats.length;// 座位数组行数15
					int drawlenth_Col = mPointList.seats[0].length; // 座位数组中列数23

					if (i < drawlenth_Row && j <= drawlenth_Col) {
						if (mPointList.seats[i][j - 1] != null) {// 此行的此列中有座位
							final TextView seatText = new TextView(this);
							seatText.setGravity(Gravity.CENTER);
							seatText.setTextSize(8);
							// seatText.setPadding(1, 1, 1, 1);
							seatText.setMinHeight(mPointList.grid);
							seatText.setMaxHeight(mPointList.grid);
							// seatText.setTextColor(R.color.seat_select_coordinate_shu);
							// TODO 由于项目需求取消 所有此处注释掉。
							seatText.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									try {
										String[] split = null;
										String idStr = (String) seatText.getTag();
										if (idStr.contains(",")) {
											split = idStr.split(",");
										}
										if (movieSeat != null) {
											if (movieSeat.seats != null && movieSeat.seats.size() > 0) {
												for (int k = 0; k < movieSeat.seats.size(); k++) {
													if (movieSeat.seats.get(k).seatCol.equals(split[1])
															&& movieSeat.seats.get(k).seatRow.equals(split[0])) {// 处理选中的座位
														// TODO将图片变为选中状态
														int id = seatText.getId();
														if (id == 1) {// 选中，取消选中
															// seatText.isSelected();
															seatText.setId(0);
															seatText.setBackgroundResource(R.drawable.seat_use);
															showSelectSeatAdapter.removeItem(movieSeat.seats.get(k));
															showSelectSeatAdapter.notifyDataSetChanged();
															// isSelected =
															// false;
															list_price.remove(payPrice);
															list_seatAreaCode.remove(movieSeat.seats.get(k).seatAreaCode);
															list_seatCode.remove(movieSeat.seats.get(k).seatCode);
															list_seatCol.remove(movieSeat.seats.get(k).seatCol);
															list_seatRow.remove(movieSeat.seats.get(k).seatRow);
															if (seatSelectedNum > 0) {
																seatSelectedNum--;
															} else {
																seatSelectedNum = 0;
															}
															ticketCount = seatSelectedNum + "";
															seatSelectNum.setText("" + seatSelectedNum);
															if (seatSelectedNum == 0) {
																select_seat_tishi.setVisibility(View.VISIBLE);
															}
															// try{
															// new
															// DeLockMovieSeatInfoTask(SeatSelectedAct.this,"解除锁定中...").execute(moviecinema_id,
															// movieSeat_ShowCode,
															// "1", "1",
															// recvPhone,
															// movieSeat.seats.get(k).seatCode,
															// movieSeat.seats.get(k).seatRow,
															// movieSeat.seats.get(k).seatCol,
															// movieSeat.seats.get(k).seatAreaCode,
															// payPrice);
															// }catch(IndexOutOfBoundsException
															// e){
															// e.printStackTrace();
															// }
														} else if (id == 0) {// 未选中,应选中
															if (seatSelectedNum >= 8) {
																Toast.makeText(mContext, "不好意思，一次最多买8张票！", Toast.LENGTH_SHORT).show();
																return;
															}
															select_seat_tishi.setVisibility(View.GONE);
															seatText.setId(1);
															seatText.setBackgroundResource(R.drawable.seat_select);
															showSelectSeatAdapter.addItem(movieSeat.seats.get(k));
															showSelectSeatAdapter.notifyDataSetChanged();
															// isSelected =
															// true;
															list_price.add(payPrice);
															list_seatAreaCode.add(movieSeat.seats.get(k).seatAreaCode);
															list_seatCode.add(movieSeat.seats.get(k).seatCode);
															list_seatCol.add(movieSeat.seats.get(k).seatCol);
															list_seatRow.add(movieSeat.seats.get(k).seatRow);
															if (seatSelectedNum >= 0) {
																seatSelectedNum++;
															}
															ticketCount = seatSelectedNum + "";
															seatSelectNum.setText("" + seatSelectedNum);
														}
													}
												}
											}
										}
									} catch (NullPointerException e) {
										e.printStackTrace();
									}
								}
							});

							Point bean = mPointList.seats[i][j - 1];
							// System.out.println("dongdianzhouSeatSelectedAct"
							// + "i  " + i + "j  " + j + bean.type);

							switch (bean.type) {// 处理类型
							case 0:// 可售
								seatText.setTag(bean.lable);
								seatText.setId(0);
								seatText.setBackgroundResource(R.drawable.seat_use);
								seatText.setVisibility(View.VISIBLE);
								break;
							case 1:// 已售
								seatText.setBackgroundResource(R.drawable.seat_unuse);
								seatText.setVisibility(View.VISIBLE);
								seatText.setClickable(false);
								// seatText.setBackgroundResource(R.drawable.icon);
								break;
							case -1:// 不可售则不显示其状态
								// seatText.setId(Integer.parseInt(bean.lable));
								// seatText.setId(Integer.parseInt(bean.lable));
								// seatText.setTag(bean.lable);
								// seatText.setBackgroundResource(R.drawable.seat_use);
								seatText.setBackgroundResource(R.drawable.seat_unuse);
								seatText.setVisibility(View.VISIBLE);
								seatText.setClickable(false);
								// seatText.setVisibility(View.GONE);
								break;

							}
							seatLine.addView(seatText, tp);
						} else {// 此行此列无座位仅为了撑起来布局
							TextView seatText = new TextView(this);
							seatText.setGravity(Gravity.CENTER);
							seatText.setTextSize(8);
							// seatText.setPadding(1, 1, 1, 1);
							seatText.setMinHeight(mPointList.grid);
							seatText.setMaxHeight(mPointList.grid);
							seatText.setBackgroundResource(R.drawable.seat_use);
							seatText.setVisibility(View.INVISIBLE);
							seatLine.addView(seatText, tp);
						}
					} else {// 不在电影院数据范围内，和上面一样，仅起到填充布局的作用
						TextView seatText = new TextView(this);
						seatText.setGravity(Gravity.CENTER);
						seatText.setTextSize(8);
						// seatText.setPadding(1, 1, 1, 1);
						seatText.setMinHeight(mPointList.grid);
						seatText.setMaxHeight(mPointList.grid);
						seatText.setBackgroundResource(R.drawable.seat_use);
						seatText.setVisibility(View.INVISIBLE);
						seatLine.addView(seatText, tp);
					}
				}
			}
			tab.addView(seatLine);
			if (linearRowNumLeft != null) {
				linearRowNumLeft.addView(seatLineNumLeft);
			}
			if (linearRowNumRight != null) {
				linearRowNumRight.addView(seatLineNumRight);
			}
		}
		container_l.addView(tab);
		// System.out.println("dongdianzhousetprintTable2" + mPointList.column +
		// "  " + mPointList.rows);
	}

	// //锁定成功失败监听
	// public interface IsBindListener {
	//
	// public void setIamgeDrawable(TextView textView);
	// }
	// 初始化数据
	private void initdata() {
		showSelectSeatAdapter = new ShowSelectSeatAdapter(mContext);
		// falsedata();
		list_showSeatSelect.setAdapter(showSelectSeatAdapter);
		list_price = new ArrayList<String>();
		list_seatCode = new ArrayList<String>();
		list_seatRow = new ArrayList<String>();
		list_seatCol = new ArrayList<String>();
		list_seatAreaCode = new ArrayList<String>();
	}

	// 做假数据，真实用不着，
	// private void falsedata() {
	// for (int i = 0; i < 3; i++) {
	// if (movieSeatInfo != null && movieSeatInfo.seats.size() > 0) {
	// showSelectSeatAdapter.addItem(movieSeatInfo.seats.get(i));
	// }
	// }
	//
	// }

	private void getExtra() {
		Intent intent = getIntent();
		if (intent != null) {
			// movieSeatInfo = (MovieSeatInfo)
			// intent.getSerializableExtra("movieSeat");
			moviecinema_id = intent.getStringExtra("moviecinema_id");
			showcode = intent.getStringExtra("movieSeat_ShowCode");
			movie_CinemaName = intent.getStringExtra("movieCinemaName");
			movie_Name = intent.getStringExtra("movieName");
			movie_ShowTime = intent.getStringExtra("showTime");
			payPrice = intent.getStringExtra("movie_cinemaPrice");
//			cover_image_url = intent.getExtras().getString("cover_image_url");
			companyId = intent.getExtras().getString("companyId");
			cinemaRoom = intent.getExtras().getString("cinemaRoom");
			cinemaTime = intent.getExtras().getString("cinemaTime");
			cinemaName.setText(movie_CinemaName+"  "+cinemaRoom);
//			room.setText(cinemaRoom);
			try{
			date.setText(movie_ShowTime.split("-")[1]+"月"+movie_ShowTime.split("-")[2]+"日");
			}catch(Exception e){
				date.setText(movie_ShowTime);
			}
			movieName.setText(movie_Name);
			time.setText(cinemaTime);
		}
	}

	// 开启锁定和解锁座位任务
	private void startLockSeatOrDebLockSeatTask() {
		System.out.println("影院名称："+movie_CinemaName);
		seatCode = TextUtil.montageField(list_seatCode);
		seatAreaCode = TextUtil.montageField(list_seatAreaCode);
		seatRow = TextUtil.montageField(list_seatRow);
		seatCol = TextUtil.montageField(list_seatCol);
		payPrices = TextUtil.montageField_Price(list_price);
		try {
			recvPhone = getIntent().getExtras().getString("payphone");
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		new LockMovieSeatInfoTask(SeatSelectedAct.this, "座位将被锁定\n15分钟，请在\n此时间内完成\n支付...").execute(moviecinema_id, showcode, lockedType, ticketCount, recvPhone, seatCode,
				seatRow, seatCol, seatAreaCode, payPrices);
	}

	private void onClick() {
		list_showSeatSelect = (ListView) findViewById(R.id.list_seatselect_showseat);
		gopayseat = (LinearLayout) findViewById(R.id.gopayseat);
//		bottom = (LinearLayout) findViewById(R.id.bottom);
		gopayseat.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
//				System.out.println("ticketCount   " + ticketCount);
				try {
					if (Integer.parseInt(ticketCount) != 0) {// 已选票
						startLockSeatOrDebLockSeatTask();
					} else {
						Toast.makeText(mContext, "当前还没有选择座位，请先选择座位！", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		jia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if (container_l.getTouchZoomControl().getZoomView().mCurZoom <= 1) {
						container_l.getTouchZoomControl().zoomAroundVisiblePoint(0, 0, 1.4d);
						jia.setBackgroundResource(R.drawable.jia_seat_tab);
						jian.setBackgroundResource(R.drawable.jian_seat);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		jian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if (container_l.getTouchZoomControl().getZoomView().mCurZoom > 1) {
						container_l.getTouchZoomControl().zoomAroundVisiblePoint(0, 0, 1d);
						jia.setBackgroundResource(R.drawable.jia_seat);
						jian.setBackgroundResource(R.drawable.jian_seat_tab);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// bottom.setOnClickListener(new OnClickListener() {
		// public void onClick(View v) {
		//
		// }
		// });
		// ;

	}

	// 锁定座位信息
	class LockMovieSeatInfoTask extends MovieAsyncTask<String, String, LockOrDebLockMovieSeatsInfo> {
		public String exception;

		public LockMovieSeatInfoTask(Activity activity, String loadingText) {
			super(activity, null, true, true, true, loadingText);
		}

		@Override
		protected LockOrDebLockMovieSeatsInfo doInBackground(String... params) {

			LockOrDebLockMovieSeatsInfo lockOrDebLockMovieSeatsInfo = null;
			try {

				lockOrDebLockMovieSeatsInfo = (LockOrDebLockMovieSeatsInfo) MovieLib.getInstance(mContext).getLockMovieSeats(params[0], params[1],
						params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9]);
			} catch (HttpException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (IOException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (JSONException e) {
				exception = getResources().getString(R.string.exception_json);
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return lockOrDebLockMovieSeatsInfo;
		}

		@Override
		protected void onPostExecute(LockOrDebLockMovieSeatsInfo result) {
			if (!TextUtils.isEmpty(exception)) {
				Toast.makeText(mContext, exception, Toast.LENGTH_SHORT).show();
			}

			if (result != null) {
				if(result.result.success()) {
					if (result.lockSerialNo != null) {// 锁位成功
						lockMovieSeatsInfo = result;
//					System.out.println("lockMovieSeatsInfo------------" + lockMovieSeatsInfo.result.message);
						Intent intent = new Intent(mContext, BuyMovieTicketAct.class);
						if (lockMovieSeatsInfo != null) {
							if (!lockMovieSeatsInfo.c_id.equals("null") && lockMovieSeatsInfo != null) {
								intent.putExtra("lockMovieSeatsInfo", lockMovieSeatsInfo);
								intent.putExtra("movieName", movie_Name); // 电影的名字
								intent.putExtra("showTime", movie_ShowTime);// 放映时间
								intent.putExtra("movieCinemaName", movie_CinemaName); // 电影院的名字
								intent.putExtra("moviecinema_id", moviecinema_id);// 电影院的id。
								intent.putExtra("companyId", companyId);
								intent.putExtra("cinemaRoom", cinemaRoom);
								intent.putExtra("cinemaTime", cinemaTime);
								intent.putExtra("payPrice", payPrice);
								mContext.startActivity(intent);
							} else {
//							Toast.makeText(mContext, "锁位失败,请过段时间再尝试!", Toast.LENGTH_SHORT).show();
								MessageDialog.getInstance().setData(SeatSelectedAct.this, result.result.message, false);
							}
						}
					} else {
//					Toast.makeText(mContext, "锁位失败,请过段时间再尝试!", Toast.LENGTH_SHORT).show();
						MessageDialog.getInstance().setData(SeatSelectedAct.this, result.result.message, false);
					}
				} else {
					MessageDialog.getInstance().setData(SeatSelectedAct.this, R.string.data_failed_to_get_seat, false);
				}

			} else {
//				Toast.makeText(mContext, "锁位失败,请过段时间再尝试!", Toast.LENGTH_SHORT).show();
				MessageDialog.getInstance().setData(SeatSelectedAct.this, R.string.data_failed_to_load, false);
//				System.out.println("dondgdianzhouSeatSelectedAct" + "数据返回结果失败！");
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	// 解除锁定座位信息
	class DeLockMovieSeatInfoTask extends MovieAsyncTask<String, String, LockOrDebLockMovieSeatsInfo> {
		public String exception;

		public DeLockMovieSeatInfoTask(Activity activity, String loadingText) {
			super(activity, null, true, true, true, loadingText);
		}

		@Override
		protected LockOrDebLockMovieSeatsInfo doInBackground(String... params) {

			LockOrDebLockMovieSeatsInfo lockOrDebLockMovieSeatsInfo = null;
			try {

				lockOrDebLockMovieSeatsInfo = (LockOrDebLockMovieSeatsInfo) MovieLib.getInstance(mContext).getLockMovieSeats(params[0], params[1],
						params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9]);
			} catch (HttpException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (IOException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (JSONException e) {
				exception = getResources().getString(R.string.exception_json);
				e.printStackTrace();
			}
			return lockOrDebLockMovieSeatsInfo;
		}

		@Override
		protected void onPostExecute(LockOrDebLockMovieSeatsInfo result) {
			if (!TextUtils.isEmpty(exception)) {
				Toast.makeText(mContext, exception, Toast.LENGTH_SHORT).show();
			}
			if (result != null) { // 此时由于数据为false。暂时不考虑是否成功
				lockMovieSeatsInfo = result;
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		// try {
		// startDebLockSeat();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	// 解除座位的锁定
	// private void startDebLockSeat() {
	//
	// try {
	// new DeLockMovieSeatInfoTask(SeatSelectedAct.this,
	// "正在加载...").execute(moviecinema_id, showcode, "1", ticketCount, recvPhone,
	// seatCode, seatRow, seatCol, seatAreaCode, payPrices);
	// } catch (NullPointerException e) {
	// e.printStackTrace();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	class GetMovieSeatInfoTask extends MovieAsyncTask<String, String, MovieSeatInfo> {
		public String exception;
		public int selectedLinshi = 0;

		public GetMovieSeatInfoTask(Activity activity, String loadingText) {
			super(activity, null, true, true, true, loadingText);
		}

		@Override
		protected MovieSeatInfo doInBackground(String... params) {

			MovieSeatInfo movieSeatInfo = null;
			try {
				movieSeatInfo = MovieLib.getInstance(mContext).getMovieSeat(params[0]);

			} catch (HttpException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (IOException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (JSONException e) {
				exception = getResources().getString(R.string.exception_json);
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch(Exception e){
				e.printStackTrace();
			}
			return movieSeatInfo;
		}

		@Override
		protected void onPostExecute(MovieSeatInfo result) {
			if (result != null && result.result.success()) {// 获取数据成功
				if (result.seats != null && result.seats.size() > 0) {
					computerMostColAndRow(result);
					setSeatLayout(mostCol, mostRow, result);
				} else {
//					Toast.makeText(mContext, "暂无座位信息!", Toast.LENGTH_SHORT).show();
					MessageDialog.getInstance().setData(SeatSelectedAct.this, result.result.message, false);
				}
			} else {
//				Toast.makeText(mContext, "获取座位数据失败!", Toast.LENGTH_SHORT).show();
				MessageDialog.getInstance().setData(SeatSelectedAct.this, R.string.data_failed_to_get_seats, true);
//				finish();
			}
			getMovieSeatInfoTask = null;
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

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
