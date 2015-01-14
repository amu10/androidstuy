package com.szcmcc.movie.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.MovieInfo;
import com.szcmcc.movie.bean.ReSendOrderBean;
import com.szcmcc.movie.bean.SaveMovieNew;
import com.szcmcc.movie.bean.SeatOrder;
import com.szcmcc.movie.bean.SeatOrderList;
import com.szcmcc.movie.bean.TicketExchangeList;
import com.szcmcc.movie.bean.ZSQBaseBean;
import com.szcmcc.movie.bean.ZSQCouponInfosBean;
import com.szcmcc.movie.bean.ZengquanQueryBean;
import com.szcmcc.movie.bean.ZengquanQueryBeanInfo;
import com.szcmcc.movie.cache.MovieSaveDao;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.network.ZSQParse;
import com.szcmcc.movie.storage.BaseDBUtil;
import com.szcmcc.movie.storage.SharedPreferencesUtil;

/**
 * 33-我的地盘-兑 换券 34-我的地盘-我 的收藏 35-我的地盘-评 论记录
 * 
 * @author Administrator
 * 
 */
public class MyZoneAct extends BaseActivity {

	private Context mContext = MyZoneAct.this;
	private ImageButton imBack;
	private ImageView menuone = null;
	private ImageView menutwo = null;
	private ImageView menuthree = null;
	private ImageView menuCoupon = null;// 折扣券
	// private ImageView menufour = null;
	private ImageView selectmenu = null;
	private ImageView menuTicketDescription = null; // 票务说明
	// private LinearLayout selectLinearLayout = null;
	private RelativeLayout container = null;
	private LinearLayout menuonepage = null;
	private LinearLayout menutwopage = null;
	private LinearLayout menuthreepage = null;
	private LinearLayout menufourpage = null;
	private LinearLayout menushuomingpage = null; // 票务说明显示区
	private LinearLayout menuDiKoupage = null;// 抵扣券
	SharedPreferencesUtil shareP;
	private ArrayList<SaveMovieNew> saveList = new ArrayList<SaveMovieNew>();
	private ArrayList<SeatOrder> seatList = null;
	private ArrayList<SeatOrder> duihuanList = null;
	private LinearLayout noSaveMessage = null, noBuyMessage = null;
	private MovieInfo movieInfo = null;
	private ProgressBar loading_progress = null;
	private boolean isLoading = true;
	private TextView phone_num;
	private int statuMenu = 0;
	private final int ZUOWEI_MENU = 0;
	private final int DUIHUAN_MENU = 1;
	private final int ZHEKOU_MENU = 2;
	private final int MYZENGQUAN_MENU = 3;
	private final int SHUOMING_MENU = 4;
	private ZSQBaseBean<ZSQCouponInfosBean> couponBean;// 抵扣券数据
	private TextView point;
	// private TextView myTicketTitle = null;
	String data;

	private int[] backgrounds = new int[] { R.drawable.icon_zuoweipiao_selector,
			R.drawable.icon_duihuanquan_selector, R.drawable.zsq_icon_zhekouquan_selector,
			R.drawable.icon_myzengquan_selector, R.drawable.zsq_icon_piaowu_selector };
	private int[] disablebackgrounds = new int[] { R.drawable.icon_zuoweipiao_2,
			R.drawable.icon_duihuanquanp, R.drawable.icon_zhekouquan_selector_b,
			R.drawable.my_zengquan2, R.drawable.icon_piaowu_selector_b };
	private SimpleDateFormat sDateFormat = null;
	private ZengquanQueryBeanInfo zengquanQueryBeanInfo = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// myzoneact
		setContentView(R.layout.myzoneact);
		initTitleBar();
		// rateTouxiang();
		// onSetting();
		shareP = SharedPreferencesUtil.getInstance(this);
		Intent in = getIntent();
		if (in.getExtras() != null) {
			try {
				movieInfo = (MovieInfo) in.getExtras().getSerializable("movieInfo");
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		findView();
		// saveMovieList = new ArrayList<SaveMovieNew>();
		initSeatOrders();
		initTicketExchanges();
		initMyZengquan();
		initMyDiKOuquan();
		new GetSeatMovieTask(this).execute();

		mSaveMovieNewListAdapter = new SaveMovieNewListAdapter();
		moviesave_listview.setAdapter(mSaveMovieNewListAdapter);
		// initSaveMovieNews();
		selectmenu = menuone;
		selectmenu.setTag(new Integer(backgrounds[0]));
		selectmenu.setEnabled(false);
		selectmenu.setImageResource(disablebackgrounds[0]);
		setListener();
		sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		data = sDateFormat.format(new java.util.Date());

	}

	private void findView() {
		imBack = (ImageButton) findViewById(R.id.imBack);
		menuone = (ImageView) findViewById(R.id.menuone);
		menutwo = (ImageView) findViewById(R.id.menutwo);
		menuthree = (ImageView) findViewById(R.id.menuthree);
		menuCoupon = (ImageView) findViewById(R.id.menuCoupon);
		menuTicketDescription = (ImageView) findViewById(R.id.menuTicketDescription);
		// menufour = (ImageView) findViewById(R.id.menufour);
		container = (RelativeLayout) findViewById(R.id.container);
		noSaveMessage = (LinearLayout) findViewById(R.id.noSaveMessage);
		menuonepage = (LinearLayout) findViewById(R.id.menuonepage);
		menutwopage = (LinearLayout) findViewById(R.id.menutwopage);
		menuthreepage = (LinearLayout) findViewById(R.id.menuthreepage);
		menufourpage = (LinearLayout) findViewById(R.id.menufourpage);
		menuDiKoupage = (LinearLayout) findViewById(R.id.menuDiKoupage);
		menushuomingpage = (LinearLayout) findViewById(R.id.menushuomingpage);
		phone_num = (TextView) findViewById(R.id.phone_num);
		noBuyMessage = (LinearLayout) findViewById(R.id.noBuyMessage);
		loading_progress = (ProgressBar) findViewById(R.id.loading_progress);
		phone_num.setText(shareP.getUserName());
		seatList = new ArrayList<SeatOrder>();
		duihuanList = new ArrayList<SeatOrder>();
		// myTicketTitle = (TextView) findViewById(R.id.myTicketTitle);
		// myTicketTitle.setText("我的票务");
		moviesave_listview = (ListView) findViewById(R.id.moviesave_listview);
		point = (TextView) findViewById(R.id.point);
	}

	private void setListener() {
		imBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		menuone.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				selectmenu.setEnabled(true);
				statuMenu = ZUOWEI_MENU;
				selectmenu.setImageResource((Integer) selectmenu.getTag());
				// selectLinearLayout.setVisibility(View.INVISIBLE);
				selectmenu = menuone;
				menuone.setTag(new Integer(backgrounds[0]));
				menuone.setEnabled(false);
				menuone.setImageResource(disablebackgrounds[0]);
				menuonepage.setVisibility(View.VISIBLE);
				menutwopage.setVisibility(View.GONE);
				menuthreepage.setVisibility(View.GONE);
				menufourpage.setVisibility(View.GONE);
				noSaveMessage.setVisibility(View.GONE);
				noBuyMessage.setVisibility(View.GONE);
				menushuomingpage.setVisibility(View.GONE);
				menuDiKoupage.setVisibility(View.GONE);
				// myTicketTitle.setText("我的票务");
				initMenuVisible();
			}
		});
		menutwo.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				selectmenu.setEnabled(true);
				statuMenu = DUIHUAN_MENU;
				selectmenu.setImageResource((Integer) selectmenu.getTag());
				// selectLinearLayout.setVisibility(View.INVISIBLE);
				selectmenu = menutwo;
				menutwo.setTag(new Integer(backgrounds[1]));
				menutwo.setEnabled(false);
				menutwo.setImageResource(disablebackgrounds[1]);
				initTicketExchanges();

				menutwopage.setVisibility(View.VISIBLE);
				menuonepage.setVisibility(View.GONE);
				menuthreepage.setVisibility(View.GONE);
				menufourpage.setVisibility(View.GONE);
				noSaveMessage.setVisibility(View.GONE);
				noBuyMessage.setVisibility(View.GONE);
				menushuomingpage.setVisibility(View.GONE);
				menuDiKoupage.setVisibility(View.GONE);
				// myTicketTitle.setText("我的票务");
				initMenuVisible();
				// if (!isLoading) {
				// if (duihuanList.size() == 0) {
				// noBuyMessage.setVisibility(View.VISIBLE);
				// } else {
				// noBuyMessage.setVisibility(View.GONE);
				// }
				// }
				// if (isLoading) {
				// loading_progress.setVisibility(View.VISIBLE);
				// } else {
				// loading_progress.setVisibility(View.GONE);
				// }
			}
		});

		menuthree.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				selectmenu.setEnabled(true);
				statuMenu = MYZENGQUAN_MENU;
				selectmenu.setImageResource((Integer) selectmenu.getTag());
				// selectLinearLayout.setVisibility(View.INVISIBLE);
				selectmenu = menuthree;
				menuthree.setTag(new Integer(backgrounds[3]));
				menuthree.setEnabled(false);
				menuthree.setImageResource(disablebackgrounds[3]);
				// new GetSaveMovieTask(MyZoneAct.this).execute();
				// initSaveMovieNews();
				menufourpage.setVisibility(View.GONE);
				menuthreepage.setVisibility(View.VISIBLE);
				menutwopage.setVisibility(View.GONE);
				menuonepage.setVisibility(View.GONE);
				noBuyMessage.setVisibility(View.GONE);
				menushuomingpage.setVisibility(View.GONE);
				loading_progress.setVisibility(View.GONE);
				menuDiKoupage.setVisibility(View.GONE);
				// myTicketTitle.setText("我的赠券");
				if (zengquanQueryBeanInfo == null) {
					new getPresentedTicketsTask(MyZoneAct.this).execute();
				} else {
					initMenuVisible();
				}
			}
		});
		// 票务说明点击事件
		menuTicketDescription.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectmenu.setEnabled(true);
				statuMenu = SHUOMING_MENU;
				selectmenu.setImageResource((Integer) selectmenu.getTag());
				selectmenu = menuTicketDescription;
				menuTicketDescription.setTag(new Integer(backgrounds[4]));
				menuTicketDescription.setEnabled(false);
				menuTicketDescription.setImageResource(disablebackgrounds[4]);
				menufourpage.setVisibility(View.GONE);
				menuthreepage.setVisibility(View.GONE);
				menutwopage.setVisibility(View.GONE);
				menuonepage.setVisibility(View.GONE);
				noBuyMessage.setVisibility(View.GONE);
				loading_progress.setVisibility(View.GONE);
				menuDiKoupage.setVisibility(View.GONE);
				menushuomingpage.setVisibility(View.VISIBLE);
				// myTicketTitle.setText("票务说明");

			}
		});
		// 抵扣券点击事件
		menuCoupon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectmenu.setEnabled(true);
				statuMenu = ZHEKOU_MENU;
				selectmenu.setImageResource((Integer) selectmenu.getTag());
				selectmenu = menuCoupon;
				menuCoupon.setTag(new Integer(backgrounds[2]));
				menuCoupon.setEnabled(false);
				menuCoupon.setImageResource(disablebackgrounds[2]);
				menufourpage.setVisibility(View.GONE);
				menuthreepage.setVisibility(View.GONE);
				menutwopage.setVisibility(View.GONE);
				menuonepage.setVisibility(View.GONE);
				noBuyMessage.setVisibility(View.GONE);
				loading_progress.setVisibility(View.GONE);
				menushuomingpage.setVisibility(View.GONE);
				menuDiKoupage.setVisibility(View.VISIBLE);
				if (couponBean == null) {
					new GetDiKouQuanTask(MyZoneAct.this).execute("");
				} else {
					initMenuVisible();
				}
			}
		});

		// menufour.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View v) {
		// selectmenu.setEnabled(true);
		// statuMenu = SAVE_MENU;
		// selectmenu.setImageResource((Integer) selectmenu.getTag());
		// // selectLinearLayout.setVisibility(View.INVISIBLE);
		// selectmenu = menufour;
		// menufour.setTag(new Integer(backgrounds[3]));
		// menufour.setEnabled(false);
		// menufour.setImageResource(disablebackgrounds[3]);
		// new GetSaveMovieTask(MyZoneAct.this).execute();
		// initSaveMovieNews();
		// menufourpage.setVisibility(View.VISIBLE);
		// menuthreepage.setVisibility(View.GONE);
		// menutwopage.setVisibility(View.GONE);
		// menuonepage.setVisibility(View.GONE);
		// noBuyMessage.setVisibility(View.GONE);
		// loading_progress.setVisibility(View.GONE);
		// myTicketTitle.setText("我的收藏");
		// // selectLinearLayout = menuthreepage;
		// }
		// });
	}

	// @Override
	// protected void onRestart() {
	// super.onRestart();
	// if (statuMenu == SAVE_MENU) {
	// new GetSaveMovieTask(this).execute();
	// } else {
	// initMenuVisible();
	// }
	// }

	// private void saveImage(String imageUrl){
	// SharedPreferences userInfo = getSharedPreferences("user_info", 0);
	// userInfo.edit().putString("imageUrl", imageUrl).commit();
	// }
	//
	// private void saveName(String name){
	// SharedPreferences userInfo = getSharedPreferences("user_info", 0);
	// userInfo.edit().putString("name", name).commit();
	// }
	//
	// private String getImage(){
	// SharedPreferences userInfo = getSharedPreferences("user_info", 0);
	// String imageUrl = userInfo.getString("imageUrl", "");
	// return imageUrl;
	// }
	//
	// private String getName(){
	// SharedPreferences userInfo = getSharedPreferences("user_info", 0);
	// String name = userInfo.getString("name", "");
	// return name;
	// }
	//
	// private EditText settingni = null;
	// private ImageView setHeadImage = null;
	// private ImageView setName = null;
	//
	// private ImageView mimageUrl = null;
	// private boolean editable = false;
	//
	// private void onSetting() {
	// settingni = (EditText) findViewById(R.id.settingni);
	// setHeadImage = (ImageView) findViewById(R.id.headImage);
	// setName = (ImageView) findViewById(R.id.nameInfo);
	// mimageUrl = (ImageView) findViewById(R.id.imageUrl);
	// touxiangr = (RelativeLayout) findViewById(R.id.touxiangr);
	// RoateUtil.tranlateLeft(mContext, mimageUrl);
	// if(getImage()==""||getImage().equals("")){}
	// else{
	// try {
	// Uri uri = Uri.parse(getImage());
	// Bitmap bitMap =
	// BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
	// mimageUrl.setImageBitmap(bitMap);
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// }
	//
	// }
	// if(getName()==""||getName().equals("")){}
	// else{
	// settingni.setText(getName());
	// }
	//
	// setHeadImage.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// openDialog();
	// mAlertDialog.show();
	// }
	// });
	//
	// setName.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// if (editable) {
	// editable = false;
	// settingni.setEnabled(false);
	// saveName(settingni.getText().toString());
	//
	// } else {
	// editable = true;
	// settingni.setEnabled(true);
	// }
	// }
	// });
	// }

	// private RelativeLayout touxiangr = null;

	// private void rateTouxiang() {
	// touxiangr = (RelativeLayout) findViewById(R.id.touxiangr);
	// RoateUtil.tranlateLeft(mContext, touxiangr);
	// touxiangr.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	//
	// }
	// });
	//
	// }
	//
	// private static final int TAKE_PHOTO_WITH_DATA = 0;
	// private static final int PHOTO_PICKED_WITH_DATA = 1;

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// switch (requestCode) {
	// case TAKE_PHOTO_WITH_DATA:
	//
	// if (data != null) {
	// final Bitmap photo = data.getParcelableExtra("data");
	// if (photo != null) {
	// mimageUrl.setImageBitmap(photo);
	// Uri selectedImageUri = data.getData();
	// saveImage(selectedImageUri+"");
	// }
	// }
	//
	// break;
	// case PHOTO_PICKED_WITH_DATA:
	// if (data != null) {
	// Uri selectedImageUri = data.getData();
	// if (selectedImageUri != null) {
	// try {
	// Bitmap bitMap =
	// BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri));
	// mimageUrl.setImageBitmap(bitMap);
	// saveImage(selectedImageUri+"");
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// }
	//
	// }
	// }
	// }
	// }
	//
	// /**
	// * 对话框
	// */
	// private String[] items = null;
	//
	// private AlertDialog mAlertDialog = null;
	//
	// private void openDialog() {
	// items = new String[] { "拍照", "从相册选择", "取消" };
	// mAlertDialog = new
	// AlertDialog.Builder(mContext).setTitle("您是否要").setItems(items, new
	// DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	//
	// switch (which) {
	// case 0:
	// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	// startActivityForResult(intent, TAKE_PHOTO_WITH_DATA);
	// mAlertDialog.dismiss();
	// break;
	// case 1:
	// // /下方是是通过Intent调用系统的图片查看器的关键代码
	// Intent localIntent = new Intent();
	// localIntent.setType("image/*");
	// localIntent.setAction("android.intent.action.GET_CONTENT");
	// startActivityForResult(localIntent, PHOTO_PICKED_WITH_DATA);
	// mAlertDialog.dismiss();
	// break;
	// }
	// }
	//
	// }).create();
	// }
	//
	// -------------------座位票列表-----------START
	private void initSeatOrders() {
		if (seat_listview != null)
			return;
		seat_listview = (ListView) findViewById(R.id.seats_listview);
		mSeatOrderListAdapter = new SeatOrderListAdapter();
		seat_listview.setAdapter(mSeatOrderListAdapter);

		seat_listview.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> mAdapterView, View parent, int position,
					long id) {
				mSeatOrderListAdapter.notifyDataSetChanged();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		seat_listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> mAdapterView, View parent, int position, long id) {
				Intent in = new Intent(MyZoneAct.this, OrdersSeatActivity.class);
				in.putExtra("SeatOrder", mSeatOrderListAdapter.getItem(position));
				startActivity(in);
			}
		});

	}

	private ListView seat_listview = null;
	private SeatOrderListAdapter mSeatOrderListAdapter = null;
	private SeatOrderList mSeatOrderList = null;

	public class SeatOrderListAdapter extends BaseAdapter {

		private LayoutInflater layoutFlater;

		private ArrayList<SeatOrder> list = null;

		public SeatOrderListAdapter() {
			super();
			layoutFlater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void addItem(ArrayList<SeatOrder> list) {
			this.list = list;
			notifyDataSetChanged();
		}

		public int getCount() {
			if (list != null) {
				return list.size();
			}
			return 0;
		}

		public SeatOrder getItem(int position) {
			return list.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolderListViewItme holder;
			if (convertView == null) {
				convertView = layoutFlater.inflate(R.layout.movie_seatorder_fourpage_list_item,
						null);
				holder = new ViewHolderListViewItme();
				// holder.imgeUrl = (ImageView)
				// convertView.findViewById(R.id.imgeUrl);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.address = (TextView) convertView.findViewById(R.id.address);
				holder.date = (TextView) convertView.findViewById(R.id.date);
				// holder.datetime = (TextView)
				// convertView.findViewById(R.id.datetime);
				holder.status_zero = (TextView) convertView.findViewById(R.id.status_zero);
				holder.status_one = (TextView) convertView.findViewById(R.id.status_one);
				holder.status_two = (TextView) convertView.findViewById(R.id.status_two);
				holder.chongxinxiafa = (ImageView) convertView.findViewById(R.id.chongxinxiafa);
				holder.fail_sms = (ImageView) convertView.findViewById(R.id.fail_sms);
				holder.fail_order = (ImageView) convertView.findViewById(R.id.fail_order);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolderListViewItme) convertView.getTag();
			}

			if (list.get(position).cinemaName.equals("")) {
				holder.name.setText("未知影片");
				// holder.address.setText("未知影院");
			} else {
				holder.name.setText(list.get(position).cinemaName);
			}

			holder.date.setText(list.get(position).date);

			holder.chongxinxiafa.setVisibility(View.GONE);
			holder.fail_sms.setVisibility(View.GONE);
			holder.fail_order.setVisibility(View.GONE);
			System.out.println("list.get(position).status           " + list.get(position).status);
			if (list.get(position).status.equals("0")) {
				holder.status_zero.setVisibility(View.GONE);
				holder.status_one.setVisibility(View.VISIBLE);
				holder.status_two.setVisibility(View.GONE);
				try {
					if (Integer.parseInt(list.get(position).date.split("-")[0]
							+ list.get(position).date.split("-")[1]
							+ list.get(position).date.split("-")[2]) < Integer.parseInt(data
							.split("-")[0] + data.split("-")[1] + data.split("-")[2])) {
						System.out.println("订单已失效");
						// TODO 添加订单已失效
					} else {
						System.out.println("订单未失效");
						// TODO 取消订单已失效
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			} else if (list.get(position).status.equals("1")) {
				holder.status_zero.setText("支付成功");
				holder.status_zero.setVisibility(View.VISIBLE);
				holder.status_one.setVisibility(View.GONE);
				holder.status_two.setVisibility(View.GONE);
				holder.chongxinxiafa.setVisibility(View.GONE);
			} else if (list.get(position).status.equals("2")) {

				holder.status_zero.setVisibility(View.GONE);
				holder.status_one.setVisibility(View.GONE);
				holder.status_two.setVisibility(View.VISIBLE);
				holder.chongxinxiafa.setVisibility(View.GONE);
			} else if (list.get(position).status.equals("3")) {
				holder.status_zero.setText("支付成功");
				holder.chongxinxiafa.setVisibility(View.VISIBLE);
				holder.status_zero.setVisibility(View.VISIBLE);
				holder.status_one.setVisibility(View.GONE);
				holder.status_two.setVisibility(View.GONE);
			} else if (list.get(position).status.equals("4")) {
				holder.status_zero.setText("支付成功");
				holder.status_zero.setVisibility(View.VISIBLE);
				holder.status_one.setVisibility(View.GONE);
				holder.status_two.setVisibility(View.GONE);
				holder.fail_order.setVisibility(View.VISIBLE);
				holder.chongxinxiafa.setVisibility(View.GONE);
			} else if (list.get(position).status.equals("5")) {
				holder.status_zero.setText("支付成功");
				holder.chongxinxiafa.setVisibility(View.VISIBLE);
				holder.status_zero.setVisibility(View.VISIBLE);
				holder.status_one.setVisibility(View.GONE);
				holder.status_two.setVisibility(View.GONE);
				holder.fail_sms.setVisibility(View.VISIBLE);
			} else if (list.get(position).status.equals("6")) {
				holder.status_zero.setText("支付成功");
				holder.chongxinxiafa.setVisibility(View.VISIBLE);
				holder.status_zero.setVisibility(View.VISIBLE);
				holder.status_one.setVisibility(View.GONE);
				holder.status_two.setVisibility(View.GONE);
			} else if (list.get(position).status.equals("7")) {
				holder.status_zero.setText("退费成功");
				holder.chongxinxiafa.setVisibility(View.GONE);
				holder.status_zero.setVisibility(View.VISIBLE);
				holder.status_one.setVisibility(View.GONE);
				holder.status_two.setVisibility(View.GONE);
			} else if (list.get(position).status.equals("8")) {
				holder.status_zero.setText("退费失败");
				holder.chongxinxiafa.setVisibility(View.GONE);
				holder.status_zero.setVisibility(View.VISIBLE);
				holder.status_one.setVisibility(View.GONE);
				holder.status_two.setVisibility(View.GONE);
			} else if (list.get(position).status.equals("11")) {
				holder.status_zero.setText("支付成功");
				holder.status_zero.setVisibility(View.VISIBLE);
				holder.status_one.setVisibility(View.GONE);
				holder.status_two.setVisibility(View.GONE);
				holder.chongxinxiafa.setVisibility(View.GONE);
			} else if (list.get(position).status.equals("12")) {
				holder.status_zero.setText("支付成功");
				holder.chongxinxiafa.setVisibility(View.GONE);
				holder.status_zero.setVisibility(View.VISIBLE);
				holder.status_one.setVisibility(View.GONE);
				holder.status_two.setVisibility(View.GONE);
				holder.fail_sms.setVisibility(View.VISIBLE);
			} else if (list.get(position).status.equals("13")) {
				holder.status_zero.setText("支付成功");
				holder.chongxinxiafa.setVisibility(View.GONE);
				holder.status_zero.setVisibility(View.VISIBLE);
				holder.status_one.setVisibility(View.GONE);
				holder.status_two.setVisibility(View.GONE);
				holder.fail_sms.setVisibility(View.VISIBLE);
			} else if (list.get(position).status.equals("14")) {
				holder.status_zero.setText("支付成功");
				holder.chongxinxiafa.setVisibility(View.GONE);
				holder.status_zero.setVisibility(View.VISIBLE);
				holder.status_one.setVisibility(View.GONE);
				holder.status_two.setVisibility(View.GONE);
				holder.fail_sms.setVisibility(View.VISIBLE);
			}

			holder.chongxinxiafa.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					new ReSendOrderMovieTask(MyZoneAct.this).execute(list.get(position).orderId,
							"1");
				}
			});
			// if ("0".equals(bean.status))// 支付成功
			// {
			// holder.status_zero.setVisibility(View.VISIBLE);
			// holder.status_one.setVisibility(View.GONE);
			// holder.status_two.setVisibility(View.GONE);
			// holder.chongxinxiafa.setVisibility(View.VISIBLE);
			// }
			//
			// else if ("1".equals(bean.status))// 等待支付
			// {
			// holder.status_zero.setVisibility(View.GONE);
			// holder.status_one.setVisibility(View.VISIBLE);
			// holder.status_two.setVisibility(View.GONE);
			// }
			//
			// else if ("2".equals(bean.status))// 已使用
			// {
			// holder.status_zero.setVisibility(View.GONE);
			// holder.status_one.setVisibility(View.GONE);
			// holder.status_two.setVisibility(View.VISIBLE);
			// }

			return convertView;
		}
	}

	private static class ViewHolderListViewItme {

		// public ImageView imgeUrl = null;
		public ImageView chongxinxiafa = null;
		public TextView name = null;
		public TextView address = null;
		public TextView date = null;
		// public TextView datetime = null;
		public TextView status_zero = null;
		public TextView status_one = null;
		public TextView status_two = null;
		public ImageView fail_sms, fail_order = null;

	}

	// -------------------座位票列表----------END

	// -------------------兑换券列表-----------START
	private void initTicketExchanges() {
		if (ticketchange_listview != null)
			return;
		ticketchange_listview = (ListView) findViewById(R.id.ticketchange_listview);
		mTicketExchangeListAdapter = new TicketExchangeListAdapter();
		ticketchange_listview.setAdapter(mTicketExchangeListAdapter);

		ticketchange_listview.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> mAdapterView, View parent, int position,
					long id) {
				mTicketExchangeListAdapter.notifyDataSetChanged();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		ticketchange_listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> mAdapterView, View parent, int position, long id) {
				Intent in = new Intent(MyZoneAct.this, OrdersDuihuanActivity.class);
				in.putExtra("SeatOrder", mTicketExchangeListAdapter.getItem(position));
				startActivity(in);
			}
		});

	}

	private ListView ticketchange_listview = null;
	private TicketExchangeListAdapter mTicketExchangeListAdapter = null;
	private TicketExchangeList mTicketExchangeList = null;

	public class TicketExchangeListAdapter extends BaseAdapter {

		private LayoutInflater layoutFlater;
		private ArrayList<SeatOrder> list = null;

		public TicketExchangeListAdapter() {
			super();
			layoutFlater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void addItem(ArrayList<SeatOrder> list) {
			this.list = list;
			notifyDataSetChanged();
		}

		public int getCount() {
			if (list != null) {
				return list.size();
			}
			return 0;
		}

		public SeatOrder getItem(int position) {
			return list.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolderListViewItme2 holder;
			if (convertView == null) {
				convertView = layoutFlater.inflate(R.layout.movie_ticketchange_fourpage_list_item,
						null);
				holder = new ViewHolderListViewItme2();
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.endtime = (TextView) convertView.findViewById(R.id.endtime);
				holder.status_zero_ex = (TextView) convertView.findViewById(R.id.status_zero_ex);
				holder.status_one_ex = (TextView) convertView.findViewById(R.id.status_one_ex);
				holder.status_two_ex = (TextView) convertView.findViewById(R.id.status_two_ex);
				holder.status_three_ex = (TextView) convertView.findViewById(R.id.status_two_ex);
				holder.chongxinxiafa = (ImageView) convertView.findViewById(R.id.chongxinxiafa);
				holder.fail_sms = (ImageView) convertView.findViewById(R.id.fail_sms);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolderListViewItme2) convertView.getTag();
			}
			if (list.get(position).cinemaName.equals("")) {
				holder.name.setText("未知影院");
			} else {
				holder.name.setText(list.get(position).cinemaName);
			}
			holder.endtime.setText(list.get(position).validityDate);
			holder.chongxinxiafa.setVisibility(View.GONE);
			holder.fail_sms.setVisibility(View.GONE);
			if ("1".equals(list.get(position).status))// 等待付款
			{
				holder.status_zero_ex.setVisibility(View.GONE);
				holder.status_one_ex.setVisibility(View.VISIBLE);
				holder.status_two_ex.setVisibility(View.GONE);
				holder.status_three_ex.setVisibility(View.GONE);
				try {
					System.out.println("listdata----"
							+ Integer.parseInt(list.get(position).date.split("-")[0]
									+ list.get(position).date.split("-")[1]
									+ list.get(position).date.split("-")[2]));
					System.out.println("sysdata----"
							+ Integer.parseInt(data.split("-")[0] + data.split("-")[1]
									+ data.split("-")[2]));
					if (Integer.parseInt(list.get(position).date.split("-")[0]
							+ list.get(position).date.split("-")[1]
							+ list.get(position).date.split("-")[2]) < Integer.parseInt(data
							.split("-")[0] + data.split("-")[1] + data.split("-")[2])) {
						// TODO 添加订单已失效
						System.out.println("订单已失效2");
					} else {
						// TODO 取消订单已失效
						System.out.println("订单未失效2");
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			} else if ("2".equals(list.get(position).status))// 支付成功
			{
				holder.status_zero_ex.setVisibility(View.VISIBLE);
				holder.status_one_ex.setVisibility(View.GONE);
				holder.status_two_ex.setVisibility(View.GONE);
				holder.status_three_ex.setVisibility(View.GONE);
				holder.chongxinxiafa.setVisibility(View.VISIBLE);
			} else if ("3".equals(list.get(position).status))// 支付失败
			{
				holder.status_zero_ex.setVisibility(View.GONE);
				holder.status_one_ex.setVisibility(View.GONE);
				holder.status_two_ex.setVisibility(View.GONE);
				holder.status_three_ex.setVisibility(View.VISIBLE);
			} else if ("4".equals(list.get(position).status))// 支付成功
			{
				holder.status_zero_ex.setVisibility(View.VISIBLE);
				holder.status_one_ex.setVisibility(View.GONE);
				holder.status_two_ex.setVisibility(View.GONE);
				holder.status_three_ex.setVisibility(View.GONE);
				holder.chongxinxiafa.setVisibility(View.VISIBLE);
			} else if ("5".equals(list.get(position).status))// 支付成功
			{
				holder.status_zero_ex.setVisibility(View.VISIBLE);
				holder.status_one_ex.setVisibility(View.GONE);
				holder.status_two_ex.setVisibility(View.GONE);
				holder.status_three_ex.setVisibility(View.GONE);
				holder.chongxinxiafa.setVisibility(View.VISIBLE);
				holder.fail_sms.setVisibility(View.VISIBLE);
			} else if ("6".equals(list.get(position).status))// 支付成功
			{
				holder.status_zero_ex.setVisibility(View.VISIBLE);
				holder.status_one_ex.setVisibility(View.GONE);
				holder.status_two_ex.setVisibility(View.GONE);
				holder.status_three_ex.setVisibility(View.GONE);
				holder.chongxinxiafa.setVisibility(View.GONE);
			} else if ("7".equals(list.get(position).status))// 支付成功
			{
				holder.status_zero_ex.setVisibility(View.VISIBLE);
				holder.status_one_ex.setVisibility(View.GONE);
				holder.status_two_ex.setVisibility(View.GONE);
				holder.status_three_ex.setVisibility(View.GONE);
				holder.chongxinxiafa.setVisibility(View.GONE);
			} else if ("8".equals(list.get(position).status))// 已使用
			{
				holder.status_zero_ex.setVisibility(View.GONE);
				holder.status_one_ex.setVisibility(View.GONE);
				holder.status_two_ex.setVisibility(View.VISIBLE);
				holder.status_three_ex.setVisibility(View.GONE);
			}

			holder.chongxinxiafa.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					new ReSendOrderMovieTask(MyZoneAct.this).execute(list.get(position).orderId,
							"0");
				}
			});

			return convertView;
		}
	}

	private static class ViewHolderListViewItme2 {

		public ImageView chongxinxiafa = null;
		public TextView name = null;
		public TextView status_zero_ex = null;
		public TextView status_one_ex = null;
		public TextView status_two_ex = null;
		public TextView status_three_ex = null;
		public TextView endtime = null;
		public ImageView fail_sms = null;

	}

	// -------------------兑换券列表----------END

	// -------------------抵扣券------------START

	private ListView myDiKou_listview = null;
	private myDiKouquanAdapter myDiKouquanAdapter = null;

	private void initMyDiKOuquan() {
		if (myDiKou_listview != null) {
			return;
		} else {
			myDiKou_listview = (ListView) findViewById(R.id.myDiKou_listview);
			myDiKouquanAdapter = new myDiKouquanAdapter();
			myDiKou_listview.setAdapter(myDiKouquanAdapter);
		}

	}

	private class myDiKouquanAdapter extends BaseAdapter {
		private List<ZSQCouponInfosBean> mList;

		public void setData(List<ZSQCouponInfosBean> list) {
			this.mList = list;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			if (mList != null) {
				return mList.size();
			} else {
				return 0;
			}
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.zsq_item_dikouquan, null);
				holder = new ViewHolder();
				holder.tvTypeName = (TextView) convertView.findViewById(R.id.tvTypeName);
				holder.tvVerifyCode = (TextView) convertView.findViewById(R.id.tvVerifyCode);
				holder.tvTime2 = (TextView) convertView.findViewById(R.id.tvTime2);
				holder.tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tvTypeName.setText(mList.get(position).getTypeName() + "：");
			holder.tvVerifyCode.setText(mList.get(position).getVerifyCode());
			holder.tvTime2.setText(mList.get(position).getEndDate());
			// 状态
			if (mList.get(position).getStatus() == 1) {
				holder.tvStatus.setText("未发送");
				holder.tvStatus.setTextColor(getResources().getColor(R.color.blue_top));
			} else if (mList.get(position).getStatus() == 2) {
				holder.tvStatus.setText("发送成功");
				holder.tvStatus.setTextColor(getResources().getColor(R.color.blue_top));
			} else if (mList.get(position).getStatus() == 3) {
				holder.tvStatus.setText("发送失败");
				holder.tvStatus.setTextColor(getResources().getColor(R.color.name_orange));
			} else if (mList.get(position).getStatus() == 4) {
				holder.tvStatus.setText("锁定");
				holder.tvStatus.setTextColor(getResources().getColor(R.color.name_orange));
			} else if (mList.get(position).getStatus() == 5) {
				holder.tvStatus.setText("已使用");
				holder.tvStatus.setTextColor(getResources().getColor(R.color.light_gray));
			} else if (mList.get(position).getStatus() == 6) {
				holder.tvStatus.setText("已过期");
				holder.tvStatus.setTextColor(getResources().getColor(R.color.light_gray));
			}
			return convertView;
		}

		private class ViewHolder {
			TextView tvTypeName;
			TextView tvVerifyCode;
			TextView tvTime2;
			TextView tvStatus;
		}

	}

	class GetDiKouQuanTask extends MovieAsyncTask<String, String, String> {

		public GetDiKouQuanTask(Activity activity) {
			super(activity, null, true, true, false);
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			isLoading = true;
			loading_progress.setVisibility(View.VISIBLE);
		}
		@Override
		protected String doInBackground(String... params) {
			String result = null;
			try {
				lib = MovieLib.getInstance(MyZoneAct.this);
				result = lib.getCouponInfos(MyZoneAct.this, "1", shareP.getUserName());
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			isLoading = false;
			loading_progress.setVisibility(View.GONE);
			if (result == null) {
//				Toast.makeText(MyZoneAct.this, "网络连接失败，请稍后再试", Toast.LENGTH_SHORT).show();
			} else {
				try{
				couponBean = new ZSQParse().parseCouponInfos(result);
				if (couponBean.getCode().equals("0")) {
					Toast.makeText(MyZoneAct.this, couponBean.getMessage(), Toast.LENGTH_SHORT)
							.show();
				} else if (couponBean.getCode().equals("1")) {
//					if(couponBean.getCoupons().size()==0){
//						noBuyMessage.setVisibility(View.VISIBLE);
//						point.setText("您未获得过抵扣券，积极参与爱电影客户端推出的活动，就有机会获得抵扣券哦！");
//						return;
//					}
					myDiKouquanAdapter.setData(couponBean.getCoupons());
				}
				}catch(Exception e){
					e.printStackTrace();
//					Toast.makeText(MyZoneAct.this, "网络连接失败，请稍后再试", Toast.LENGTH_SHORT).show();
				}
			}
			initMenuVisible();
		}
	}

	// -------------------抵扣券----------END

	// -------------------我的赠券------------START
	private void initMyZengquan() {
		if (myZengquan_listview != null)
			return;
		myZengquan_listview = (ListView) findViewById(R.id.myZengquan_listview);
		myZengquanAdapter = new MyZengquanAdapter();
		myZengquan_listview.setAdapter(myZengquanAdapter);
	}

	private ListView myZengquan_listview = null;
	private MyZengquanAdapter myZengquanAdapter = null;

	private class MyZengquanAdapter extends BaseAdapter {

		private ArrayList<ZengquanQueryBean> list = null;
		private LayoutInflater layoutFlater;

		public MyZengquanAdapter() {
			super();
			layoutFlater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void addItem(ArrayList<ZengquanQueryBean> list) {
			this.list = list;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			if (list != null) {
				return list.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolderListViewItme3 holder;
			if (convertView == null) {
				convertView = layoutFlater.inflate(R.layout.movie_myzengquan_fourpage_list_item,
						null);
				holder = new ViewHolderListViewItme3();
				holder.allowCinema = (TextView) convertView.findViewById(R.id.allowCinema);
				holder.filmType = (TextView) convertView.findViewById(R.id.filmType);
				holder.verifyCode = (TextView) convertView.findViewById(R.id.verifyCode);
				holder.validDate = (TextView) convertView.findViewById(R.id.validDate);
				holder.presentTime = (TextView) convertView.findViewById(R.id.presentTime);
				holder.noUse = (TextView) convertView.findViewById(R.id.noUse);
				holder.hasUse = (TextView) convertView.findViewById(R.id.hasUse);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolderListViewItme3) convertView.getTag();
			}

			holder.allowCinema.setText(list.get(position).allowCinema);
			holder.verifyCode.setText(list.get(position).verifyCode);
			holder.validDate.setText(list.get(position).validDate);
			holder.presentTime.setText(list.get(position).presentTime);

			// 适用类型，1:2D、 2:3D、3：IMAX、4：贵宾厅
			if (list.get(position).filmType.equals("1")) {
				holder.filmType.setText("2D");
			} else if (list.get(position).filmType.equals("2")) {
				holder.filmType.setText("3D");
			} else if (list.get(position).filmType.equals("3")) {
				holder.filmType.setText("IMAX");
			} else if (list.get(position).filmType.equals("4")) {
				holder.filmType.setText("贵宾厅");
			}

			// 赠券状态，2：发送成功（即未使用） 4：已使用
			if (list.get(position).status.equals("2")) {
				holder.noUse.setVisibility(View.VISIBLE);
				holder.hasUse.setVisibility(View.GONE);
			} else if (list.get(position).status.equals("4")) {
				holder.noUse.setVisibility(View.GONE);
				holder.hasUse.setVisibility(View.VISIBLE);
			}
			return convertView;
		}

	}

	private class ViewHolderListViewItme3 {

		public TextView allowCinema = null;
		public TextView filmType = null;
		public TextView verifyCode = null;
		public TextView validDate = null;
		public TextView presentTime = null;
		public TextView noUse = null, hasUse = null;

	}

	// --------------------我的赠券---------------END
	// -------------------收藏电影列表-----------START
	private void initSaveMovieNews() {
		moviesave_listview.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> mAdapterView, View parent, int position,
					long id) {
				mSaveMovieNewListAdapter.notifyDataSetChanged();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		moviesave_listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> mAdapterView, View parent, int position, long id) {
				// MovieSaveDao movieSaveDao = new MovieSaveDao(MyZoneAct.this);
				// movieSaveDao.deleteMovie(position+"");
				if (!mSaveMovieNewListAdapter.getSaveMovieItem(position).play_status.equals("2")) {
					Intent in = new Intent(MyZoneAct.this, MovieDetailActivity.class);
					in.putExtra("m_id", mSaveMovieNewListAdapter.getSaveMovieItem(position).m_id);
					in.putExtra("upcomming",
							mSaveMovieNewListAdapter.getSaveMovieItem(position).play_status);
					in.putExtra("cinemaPrepareInfo", "");
					in.putExtra("movie", "");
					in.putExtra("isFromSave", true);
					startActivity(in);
				} else {
					Toast.makeText(MyZoneAct.this, "该影片已下线！", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("recycle---");
		app.getAsyncImageLoader().recycleBitmaps();
	}

	private ListView moviesave_listview = null;
	private SaveMovieNewListAdapter mSaveMovieNewListAdapter = null;

	public class SaveMovieNewListAdapter extends BaseAdapter {
		private ArrayList<SaveMovieNew> saveMovieList = new ArrayList<SaveMovieNew>();
		private LayoutInflater layoutFlater;

		public SaveMovieNewListAdapter() {
			super();
			layoutFlater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return saveMovieList.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public void addItem(ArrayList<SaveMovieNew> list) {
			if (list != null) {
				saveMovieList = list;
				notifyDataSetChanged();
			}
		}

		public SaveMovieNew getSaveMovieItem(int position) {
			return saveMovieList.get(position);
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolderListViewItme4 holder;
			if (convertView == null) {
				convertView = layoutFlater.inflate(R.layout.movie_savemovie_fourpage_list_item,
						null);
				holder = new ViewHolderListViewItme4();
				holder.imageUrl = (ImageView) convertView.findViewById(R.id.imgeUrl);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.playtime = (TextView) convertView.findViewById(R.id.playtime);
				holder.play_zero = (TextView) convertView.findViewById(R.id.play_zero);
				holder.play_one = (TextView) convertView.findViewById(R.id.play_one);
				holder.play_two = (TextView) convertView.findViewById(R.id.play_two);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolderListViewItme4) convertView.getTag();
			}
			holder.name.setText(saveMovieList.get(position).getName());
			holder.playtime.setText(saveMovieList.get(position).getPlaytime());
			holder.imageUrl.setImageBitmap(BaseDBUtil.readBitMap(mContext, R.drawable.loadinglist));
			if (saveMovieList != null && URLUtil.isHttpUrl(saveMovieList.get(position).imageUrl)) {
				holder.imageUrl.setTag(saveMovieList.get(position).imageUrl);
				System.out.println("saveMovieList.get(position).imageUrl         "
						+ saveMovieList.get(position).imageUrl);
				Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext,
						saveMovieList.get(position).imageUrl, new ImageCallback() {

							@Override
							public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
								String url = (String) holder.imageUrl.getTag();
								if (url.equals(bitmapUrl)) {
									holder.imageUrl.setImageBitmap(bitmap);
								}
							}
						}, AsyncImageLoader.CACHE_TYPE_SD, true);
				if (bitmap != null) {
					holder.imageUrl.setImageBitmap(bitmap);
				}
			}
			if ("0".equals(saveMovieList.get(position).getPlay_status()))// 正在热映
			{
				holder.play_zero.setVisibility(View.VISIBLE);
				holder.play_one.setVisibility(View.GONE);
				holder.play_two.setVisibility(View.GONE);
			}

			else if ("1".equals(saveMovieList.get(position).getPlay_status()))// 即将上映
			{
				holder.play_zero.setVisibility(View.GONE);
				holder.play_one.setVisibility(View.VISIBLE);
				holder.play_two.setVisibility(View.GONE);
			}

			else if ("2".equals(saveMovieList.get(position).getPlay_status()))// 已下线
			{
				holder.play_zero.setVisibility(View.GONE);
				holder.play_one.setVisibility(View.GONE);
				holder.play_two.setVisibility(View.VISIBLE);
			}

			return convertView;
		}
	}

	private static class ViewHolderListViewItme4 {

		public TextView play_zero = null;
		public TextView play_one = null;
		public TextView play_two = null;
		public ImageView imageUrl = null;
		public TextView name = null;
		public TextView playtime = null;

	}

	class GetSeatMovieTask extends MovieAsyncTask<String, String, SeatOrderList> {
		public String exception;

		SeatOrderList seatOrderList = null;

		public GetSeatMovieTask(Activity activity) {
			super(activity, null, true, true, false);
		}

		@Override
		protected SeatOrderList doInBackground(String... params) {
			// CommentInfo commentInfo = null;

			if (seatList.size() != 0)
				seatList.clear();
			if (duihuanList.size() != 0) {
				duihuanList.clear();
			}
			try {
				seatOrderList = lib.getOrders(shareP.getUserName());
				for (int i = 0; i < seatOrderList.orders.size(); i++) {
					if (seatOrderList.orders.get(i).ticketType.equals("0")) {
						duihuanList.add(seatOrderList.orders.get(i));
					} else {
						seatList.add(seatOrderList.orders.get(i));
					}

				}
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return seatOrderList;
		}

		@Override
		protected void onPostExecute(SeatOrderList result) {
			super.onPostExecute(result);
			// System.out.println("result------------"+result.size());
			if (result != null) {
				mSeatOrderListAdapter.addItem(seatList);
				mTicketExchangeListAdapter.addItem(duihuanList);
			}
			if (seatList.size() == 0) {
				noBuyMessage.setVisibility(View.VISIBLE);
				point.setText("您还没有购买过电影订座票，快去看看有没有自己喜欢的吧！");
			} else {
				noBuyMessage.setVisibility(View.GONE);
			}
			isLoading = false;
			loading_progress.setVisibility(View.GONE);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			isLoading = true;
			loading_progress.setVisibility(View.VISIBLE);
		}
	}

	class ReSendOrderMovieTask extends MovieAsyncTask<String, String, ReSendOrderBean> {
		public String exception;

		ReSendOrderBean reSendOrderBean = null;

		public ReSendOrderMovieTask(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected ReSendOrderBean doInBackground(String... params) {

			try {
				reSendOrderBean = lib.reSendOrder(params[0], params[1]);

			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return reSendOrderBean;
		}

		@Override
		protected void onPostExecute(ReSendOrderBean result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.result.code.equals("1")) {
					Toast.makeText(MyZoneAct.this, result.result.message, Toast.LENGTH_SHORT)
							.show();
					System.out.println("result.result.message    " + result.result.message);
				} else {
					Toast.makeText(MyZoneAct.this, result.result.message, Toast.LENGTH_LONG).show();
					System.out.println("result.result.message  ！  " + result.result.message);
				}
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	class getPresentedTicketsTask extends MovieAsyncTask<String, String, ZengquanQueryBeanInfo> {
		public String exception;

		public getPresentedTicketsTask(Activity activity) {
			super(activity, null, true, true, false);
		}

		@Override
		protected ZengquanQueryBeanInfo doInBackground(String... params) {

			try {
				System.out.println("phone-----------" + shareP.getUserName());// 13923848213
				zengquanQueryBeanInfo = lib.getPresentedTickets(shareP.getUserName(), "");

			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return zengquanQueryBeanInfo;
		}

		@Override
		protected void onPostExecute(ZengquanQueryBeanInfo result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.code.equals("1")) {
					myZengquanAdapter.addItem(result.tickets);
				} else {
					System.out.println("result.result.message  ！  " + result.message);
				}
			}
			isLoading = false;
			initMenuVisible();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			isLoading = true;
			loading_progress.setVisibility(View.VISIBLE);
		}
	}

	class GetSaveMovieTask extends MovieAsyncTask<String, String, ArrayList<SaveMovieNew>> {
		public String exception;

		public GetSaveMovieTask(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected ArrayList<SaveMovieNew> doInBackground(String... params) {
			// CommentInfo commentInfo = null;

			if (saveList != null)
				saveList.clear();
			MovieSaveDao movieSaveDao = new MovieSaveDao(MyZoneAct.this);
			saveList = movieSaveDao.getAllMovie();
			if (saveList != null) {
				boolean flag = true;
				for (int i = 0; i < saveList.size(); i++) {
					flag = true;
					for (int j = 0; j < movieInfo.movies.size(); j++) {
						if (saveList.get(i).m_id.equals(movieInfo.movies.get(j).m_id)) {
							if (!saveList.get(i).play_status
									.equals(movieInfo.movies.get(j).upcomming)) {
								SaveMovieNew saveMovieNew = new SaveMovieNew(
										movieInfo.movies.get(j).m_id, movieInfo.movies.get(j).name,
										movieInfo.movies.get(j).client_placard_url2,
										movieInfo.movies.get(j).upcomming,
										movieInfo.movies.get(j).release_date);
								movieSaveDao.updateMovie(saveMovieNew);
							}
							flag = false;
						}

					}
					if (flag) {
						SaveMovieNew saveMovieNew = new SaveMovieNew(saveList.get(i).m_id,
								saveList.get(i).name, saveList.get(i).imageUrl, "2",
								saveList.get(i).playtime);
						movieSaveDao.updateMovie(saveMovieNew);
					}
				}

				// saveMovieList.addAll();
			}
			return saveList;
		}

		@Override
		protected void onPostExecute(ArrayList<SaveMovieNew> result) {
			super.onPostExecute(result);
			System.out.println("result------------" + result.size());
			if (result != null) {
				// if(result.size()==0){
				// noSaveMessage.setVisibility(View.VISIBLE);
				// }else{
				// noSaveMessage.setVisibility(View.GONE);
				// }

				mSaveMovieNewListAdapter.addItem(result);
				System.out.println("onPost----------");
				// initSaveMovieNews();
			}
			initMenuVisible();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	private void initMenuVisible() {
		if (statuMenu == ZUOWEI_MENU) {

			noSaveMessage.setVisibility(View.GONE);
			if (!isLoading) {
				if (seatList.size() == 0) {
					noBuyMessage.setVisibility(View.VISIBLE);
					point.setText("您还没有购买过电影订座票，快去看看有没有自己喜欢的吧！");
				} else {
					noBuyMessage.setVisibility(View.GONE);
				}
				loading_progress.setVisibility(View.GONE);
			} else {
				loading_progress.setVisibility(View.VISIBLE);
			}
		} else if (statuMenu == DUIHUAN_MENU) {
			noSaveMessage.setVisibility(View.GONE);

			if (!isLoading) {
				if (duihuanList.size() == 0) {
					noBuyMessage.setVisibility(View.VISIBLE);
					point.setText("您未购买过兑换券！");
				} else {
					noBuyMessage.setVisibility(View.GONE);
				}
				loading_progress.setVisibility(View.GONE);
			} else {
				loading_progress.setVisibility(View.VISIBLE);
			}
		} else if (statuMenu == MYZENGQUAN_MENU) {
			noSaveMessage.setVisibility(View.GONE);
			if (!isLoading) {
				try {
					if (zengquanQueryBeanInfo.tickets.size() == 0) {
						noBuyMessage.setVisibility(View.VISIBLE);
						point.setText("您未获得过赠券，积极参与爱电影客户端推出的活动，就有机会获得赠券哦！");
					} else {
						noBuyMessage.setVisibility(View.GONE);
					}
				} catch (NullPointerException e) {
					noBuyMessage.setVisibility(View.VISIBLE);
					point.setText("您未获得过赠券，积极参与爱电影客户端推出的活动，就有机会获得赠券哦！");
					e.printStackTrace();
				}
				loading_progress.setVisibility(View.GONE);
			} else {
				loading_progress.setVisibility(View.VISIBLE);
			}
		} else if (statuMenu == ZHEKOU_MENU) {
			noSaveMessage.setVisibility(View.GONE);
			if (!isLoading) {
				try {
					if (couponBean.getCoupons().size() == 0) {
						noBuyMessage.setVisibility(View.VISIBLE);
						point.setText("您未获得过抵扣券，积极参与爱电影客户端推出的活动，就有机会获得抵扣券哦！");
					} else {
						noBuyMessage.setVisibility(View.GONE);
					}
				} catch (NullPointerException e) {
					noBuyMessage.setVisibility(View.VISIBLE);
					point.setText("您未获得过抵扣券，积极参与爱电影客户端推出的活动，就有机会获得抵扣券哦！");
					e.printStackTrace();
				}
				loading_progress.setVisibility(View.GONE);
			} else {
				loading_progress.setVisibility(View.VISIBLE);
			}
		}
		// else if (statuMenu == SAVE_MENU) {
		// noBuyMessage.setVisibility(View.GONE);
		// if (!isLoading) {
		// if (saveList.size() == 0) {
		// noSaveMessage.setVisibility(View.VISIBLE);
		// } else {
		// noSaveMessage.setVisibility(View.GONE);
		// }
		// }
		// }
	}

	// -------------------收藏电影列表-----------END

	// -------------------我的评论列表-----------START
	// private void initMoviePinLuns() {
	// if (mywrite_listview != null)
	// return;
	// Log.i("-----.MyZoneAct.initMoviePinLuns--------");
	// mywrite_listview = (ListView) findViewById(R.id.mywrite_listview);
	// mMoviePinLuns = new ArrayList<MoviePinLun>();
	// CommentHistoryDao commentHistoryDao = new CommentHistoryDao(this);
	// if(commentHistoryDao.getAllMovie()!=null){
	// mMoviePinLuns.addAll(commentHistoryDao.getAllMovie());
	// }
	// mMoviePinLunListAdapter = new MoviePinLunListAdapter();
	// mywrite_listview.setAdapter(mMoviePinLunListAdapter);
	// mywrite_listview.setOnItemSelectedListener(new OnItemSelectedListener() {
	//
	// public void onItemSelected(AdapterView<?> mAdapterView, View parent, int
	// position, long id) {
	// mMoviePinLunListAdapter.notifyDataSetChanged();
	// }
	//
	// public void onNothingSelected(AdapterView<?> arg0) {
	// }
	// });
	// mywrite_listview.setOnItemClickListener(new OnItemClickListener() {
	//
	// public void onItemClick(AdapterView<?> mAdapterView, View parent, int
	// position, long id) {
	//
	// }
	// });
	//
	// }
	//
	// private ListView mywrite_listview = null;
	// private MoviePinLunListAdapter mMoviePinLunListAdapter = null;
	// private ArrayList<MoviePinLun> mMoviePinLuns = null;
	//
	// public class MoviePinLunListAdapter extends BaseAdapter {
	//
	// private LayoutInflater layoutFlater;
	//
	// public MoviePinLunListAdapter() {
	// super();
	// layoutFlater = (LayoutInflater)
	// mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// }
	//
	// public int getCount() {
	// return mMoviePinLuns.size();
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
	// public View getView(int position, View convertView, ViewGroup parent) {
	// final ViewHolderListViewItme4 holder;
	// if (convertView == null) {
	// convertView =
	// layoutFlater.inflate(R.layout.movie_pinlun_fourpage_list_item, null);
	// holder = new ViewHolderListViewItme4();
	// holder.imageUrl = (ImageView) convertView.findViewById(R.id.imageUrl);
	// holder.pointtrate = (RatingBar)
	// convertView.findViewById(R.id.pointtrate);
	// holder.name = (TextView) convertView.findViewById(R.id.name);
	//
	// holder.point = (TextView) convertView.findViewById(R.id.point);
	// holder.ping = (TextView) convertView.findViewById(R.id.ping);
	// convertView.setTag(holder);
	// } else {
	// holder = (ViewHolderListViewItme4) convertView.getTag();
	// }
	// holder.name.setText(mMoviePinLuns.get(position).name);
	// holder.ping.setText(mMoviePinLuns.get(position).ping);
	// System.out.println("point     "+mMoviePinLuns.get(position).point);
	// holder.pointtrate.setProgress((int)(
	// Float.parseFloat(mMoviePinLuns.get(position).point)*2));
	// holder.point.setText(mMoviePinLuns.get(position).point);
	// if (mMoviePinLuns != null &&
	// URLUtil.isHttpUrl(mMoviePinLuns.get(position).imageUrl)) {
	// holder.imageUrl.setTag(mMoviePinLuns.get(position).imageUrl);
	//
	// Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext,
	// mMoviePinLuns.get(position).imageUrl, new ImageCallback() {
	//
	// @Override
	// public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
	// String url = (String) holder.imageUrl.getTag();
	// if (url.equals(bitmapUrl)) {
	// holder.imageUrl.setImageBitmap(bitmap);
	// }
	// }
	// }, AsyncImageLoader.CACHE_TYPE_SD,true);
	// if (bitmap != null) {
	// holder.imageUrl.setImageBitmap(bitmap);
	// }
	// }
	//
	// return convertView;
	// }
	// }
	//
	// private static class ViewHolderListViewItme4 {
	//
	// public ImageView imageUrl = null;
	// public RatingBar pointtrate = null;
	// public TextView name = null;
	//
	// public TextView point = null;
	// public TextView ping = null;
	//
	//
	// }
	// -------------------我的评论列表-----------END
	public void onResume() {

		super.onResume();
		CmccDataStatistics.onStart(this);
	}

	public void onPause() {
		super.onPause();
		CmccDataStatistics.onStop(this);
	}
}
