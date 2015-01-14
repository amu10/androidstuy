package com.szcmcc.movie.activity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.R;
import com.szcmcc.movie.adapter.MovieSeatOrderInfoAdapter;
import com.szcmcc.movie.adapter.PayYouhuiAdapter;
import com.szcmcc.movie.bean.GetOrderTypeBeanInfo;
import com.szcmcc.movie.bean.GetOrderTypeBeanInfo.GetOrderTypeBean;
import com.szcmcc.movie.bean.LockOrDebLockMovieSeatInfo;
import com.szcmcc.movie.bean.LockOrDebLockMovieSeatsInfo;
import com.szcmcc.movie.bean.MoviePayType;
import com.szcmcc.movie.bean.OrderBySeat;
import com.szcmcc.movie.bean.OrderPayOkBySeat;
import com.szcmcc.movie.bean.OrderQueryBySeat;
import com.szcmcc.movie.bean.YinlianResultBean;
import com.szcmcc.movie.network.BaseRequest;
import com.szcmcc.movie.network.LoadingDialog;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.network.YinlianResultParse;
import com.szcmcc.movie.storage.SharedPreferencesUtil;
import com.szcmcc.movie.util.PublicUtils;
import com.szcmcc.movie.util.TextUtil;
import com.szcmcc.movie.view.MessageDialog;
import com.unionpay.upomp.bypay.util.UPOMP;
import com.unionpay.upomp.bypay.util.Utils;

/**
 * 14-购买座位票
 * 
 */
public class BuyMovieTicketAct extends BaseActivity {
	private Context mContext = BuyMovieTicketAct.this;

	private Activity activity = null;
	private RelativeLayout relativelayout_order_info = null, relative_pay_now = null;
//	private LinearLayout movieSeatinfo_pay_layout = null;
	private ListView list_orderinfo_details = null;
	private LinearLayout list_orderinfo_pay_style;
	private ImageView textview_pay_now = null;
	private MovieSeatOrderInfoAdapter movieSeatOrderInfoAdapter;
	// private MovieSeatPayStyleAdapter movieSeatPayStyleAdapter;
	private String movie_ShowTime, movie_Name, movie_Cinema_Name, movie_seatPaytype, moviecinema_id;
	private String seatCode, seatAreaCode, seatRow, seatCol, payPrices;
	private LockOrDebLockMovieSeatsInfo lockMovieSeatsInfo;
	private OrderPayOkBySeat orderPayOkBySeat;
	private OrderQueryBySeat orderQueryBySeat;
	private OrderBySeat orderBySeat;
	private int curentposition = -1;
	// private int curentposition = 0;
	private ArrayList<MoviePayType> mlist = new ArrayList<MoviePayType>();

	private String movie_seat_seatCode, movie_seat_seatCol, movie_seat_seatRow;
	private boolean stop_OrderQuery = false;
	private int queryOrder = 0;
	private String payType = "2";
	private Handler myHandler = null;
	private LoadingDialog loadingDialog;
	SharedPreferencesUtil shareP;
	private String companyId = "";

	private String cinemaRoom = "", cinemaTime = "";
	int c_id = 0;
	private boolean isCanShouYinlianDialog = true;
	private ImageButton imBack;

	/** 统一支付的商户号相关参数信息 */
	private String spID = "", spPwd = "", spCode = "", merchantCode = "", spType = "";
	private String timestamp = "", timeout = "", msg_id = "", targetMobile = "", merchantName = "", merchandise = "";
//	 private String YinlianUrl = "http://219.136.91.63:19983/cm/powerManager/httponepay.pay";//银联测试地址
	private String YinlianUrl = "http://wap.szicity.com/cm/powerManager/httponepay.pay";//银联正式地址
	String responseStr;
	public static Activity buyMovieTicketAct;
	GetOrderTypeBeanInfoTask getOrderTypeBeanInfoTask;
	private String payPrice = "";// 票单价
	private String totalPrice_smallpayment = "";// 票总价，用于小额支付
	private String totalPrice_alipay = "";// 票总价，用于小额支付支付宝支付
	private int phonePayType = 0;
	/** 全额支付 */
	private final int PHONE_PAYTYPE_ALL = 0;
	/** 抵扣券支付 */
	private final int PHONE_PAYTYPE_YOUHUI = 1;
	// View footview;

//	String payType = "2";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buymovieticketact);
		buyMovieTicketAct = this;
		initTitleBar();
		shareP = SharedPreferencesUtil.getInstance(this);
		getExtra();
		initView();
		initdata();
		setListener();
	}

	private void initdata() {
		List<String> movie_seat_seatCodes, movie_seat_seatCols, movie_seat_seatRows;
		getOrderTypeBeanInfoTask = new GetOrderTypeBeanInfoTask(this);
		getOrderTypeBeanInfoTask.execute();
		movie_seat_seatCodes = new ArrayList<String>();
		movie_seat_seatCols = new ArrayList<String>();
		movie_seat_seatRows = new ArrayList<String>();
		if (movieSeatOrderInfoAdapter == null) {
			movieSeatOrderInfoAdapter = new MovieSeatOrderInfoAdapter(mContext);
		}
		// try {
		// if (movieSeatPayStyleAdapter == null) {
		//
		// movieSeatPayStyleAdapter = new MovieSeatPayStyleAdapter(mContext,
		// Integer.parseInt(lockMovieSeatsInfo.ticketCount));
		//
		// }
		// movieSeatPayStyleAdapter.setView(relative_pay_now);
		// } catch (NumberFormatException e) {
		//
		// e.printStackTrace();
		// return;
		// }
		if (lockMovieSeatsInfo != null) {
			movieSeatOrderInfoAdapter.addList(lockMovieSeatsInfo.lockSeats);
			list_orderinfo_details.setAdapter(movieSeatOrderInfoAdapter);
			if (mlist.size() != 0) {
				mlist.clear();
			}

			// for (int i = 0; i < lockMovieSeatsInfo.payType.size(); i++) {
			// if (lockMovieSeatsInfo.payType.get(i).price != null &&
			// !lockMovieSeatsInfo.payType.get(i).price.equals("")
			// && !lockMovieSeatsInfo.payType.get(i).price.equals("null")) {
			// mlist.add(lockMovieSeatsInfo.payType.get(i));
			// }
			// }
			// movieSeatPayStyleAdapter.addList(mlist);
			// footview =
			// LayoutInflater.from(this).inflate(R.layout.choose_phone_type_footview,
			// null);
			// footview.setVisibility(View.GONE);
			// footview.findViewById(R.id.checkboxPayAll).setOnClickListener(new
			// OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// phonePayType = PHONE_PAYTYPE_ALL;
			// footview.findViewById(R.id.checkboxPayAll).setBackgroundResource(R.drawable.check);
			// footview.findViewById(R.id.checkboxPayYouhui).setBackgroundResource(R.drawable.uncheck);
			// movieSeatPayStyleAdapter.notifyDataSetChanged();
			// }
			// });
			// footview.findViewById(R.id.checkboxPayYouhui).setOnClickListener(new
			// OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// phonePayType = PHONE_PAYTYPE_YOUHUI;
			// footview.findViewById(R.id.checkboxPayAll).setBackgroundResource(R.drawable.uncheck);
			// footview.findViewById(R.id.checkboxPayYouhui).setBackgroundResource(R.drawable.check);
			// movieSeatPayStyleAdapter.notifyDataSetChanged();
			// }
			// });

			// list_orderinfo_pay_style.setAdapter(movieSeatPayStyleAdapter);
			if (lockMovieSeatsInfo.lockSeats != null && lockMovieSeatsInfo.lockSeats.size() > 0) {
				for (LockOrDebLockMovieSeatInfo lockOrDebLockMovieSeatInfo : lockMovieSeatsInfo.lockSeats) {
					if (lockOrDebLockMovieSeatInfo != null) {
						movie_seat_seatCodes.add(lockOrDebLockMovieSeatInfo.seatCode);
						movie_seat_seatCols.add(lockOrDebLockMovieSeatInfo.seatCol);
						movie_seat_seatRows.add(lockOrDebLockMovieSeatInfo.seatRow);
					}
				}
			}
			movie_seat_seatCode = TextUtil.montageField(movie_seat_seatCodes);
			movie_seat_seatCol = TextUtil.montageField(movie_seat_seatCols);
			movie_seat_seatRow = TextUtil.montageField(movie_seat_seatRows);
		}
	}

	// 处理数据的监听事件
	private void setListener() {

		textview_pay_now.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// showDialog(setSeatPayType());
				// movieSeatPayStyleAdapter.notifyDataSetChanged();
				// relative_pay_now.setVisibility(View.GONE);
				// textview_pay_now.setClickable(false);// 设置支付后不再处理点击事件
				if (!shareP.getUserName().equals("")) {
					if (lockMovieSeatsInfo != null) {
						if (payType.equals("100")) {
							GetOrderInfoYinlianTask orderInfoTask = new GetOrderInfoYinlianTask(BuyMovieTicketAct.this, "正在生成订单...");
							System.out.println("setSeatPayType" + "payType" + payType);
							orderInfoTask.execute(lockMovieSeatsInfo.c_id, lockMovieSeatsInfo.recyPhone, lockMovieSeatsInfo.recyPhone,
									lockMovieSeatsInfo.ticketCount, payType, "1", lockMovieSeatsInfo.lockSerialNo, lockMovieSeatsInfo.showCode,
									movie_seat_seatCode, movie_seat_seatRow, movie_seat_seatCol);
						} else if (payType.equals("2")) {
							if (phonePayType == PHONE_PAYTYPE_ALL) {
								GetOrderInfoTask orderInfoTask = new GetOrderInfoTask(BuyMovieTicketAct.this, "正在生成订单...");
								orderInfoTask.execute(lockMovieSeatsInfo.c_id, lockMovieSeatsInfo.recyPhone, lockMovieSeatsInfo.recyPhone,
										lockMovieSeatsInfo.ticketCount, payType, "1", lockMovieSeatsInfo.lockSerialNo,
										lockMovieSeatsInfo.showCode, movie_seat_seatCode, movie_seat_seatRow, movie_seat_seatCol);
							} else {
								showYanzhengDialog();
							}
						} else if(payType.equals("5")) {//小额支付
							GetOrderInfoTask orderInfoTask = new GetOrderInfoTask(BuyMovieTicketAct.this, "正在生成订单...");
							orderInfoTask.execute(lockMovieSeatsInfo.c_id, lockMovieSeatsInfo.recyPhone, lockMovieSeatsInfo.recyPhone,
									lockMovieSeatsInfo.ticketCount, payType, "1", lockMovieSeatsInfo.lockSerialNo,
									lockMovieSeatsInfo.showCode, movie_seat_seatCode, movie_seat_seatRow, movie_seat_seatCol, totalPrice_smallpayment);
						} else if(payType.equals("6")) {//支付宝支付
							GetOrderInfoTask orderInfoTask = new GetOrderInfoTask(BuyMovieTicketAct.this, "正在生成订单...");
							orderInfoTask.execute(lockMovieSeatsInfo.c_id, lockMovieSeatsInfo.recyPhone, lockMovieSeatsInfo.recyPhone,
									lockMovieSeatsInfo.ticketCount, payType, "1", lockMovieSeatsInfo.lockSerialNo,
									lockMovieSeatsInfo.showCode, movie_seat_seatCode, movie_seat_seatRow, movie_seat_seatCol, totalPrice_alipay);
						} else if(payType.equals("4")) {//移动赠券
							GetOrderInfoTask orderInfoTask = new GetOrderInfoTask(BuyMovieTicketAct.this, "正在生成订单...");
							System.out.println("setSeatPayType" + "payType" + payType);
							orderInfoTask.execute(lockMovieSeatsInfo.c_id, lockMovieSeatsInfo.recyPhone, lockMovieSeatsInfo.recyPhone,
									lockMovieSeatsInfo.ticketCount, payType, "1", lockMovieSeatsInfo.lockSerialNo, lockMovieSeatsInfo.showCode,
									movie_seat_seatCode, movie_seat_seatRow, movie_seat_seatCol);
						}
					}
				} else {
					Intent intent = new Intent(BuyMovieTicketAct.this, UserLoginAct.class);
					startActivity(intent);
				}
			}
		});

		imBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	/**
	 * 设置座位的支付类型:若用户选择则设置为用户选择的支付类型id，设置其类型为类型，但是默认为 手机支付。
	 */
	// private String setSeatPayType(String payType) {
	// String payType = "2";// 2是返回的数据中手机支付的默认。
	// if (movieSeatPayStyleAdapter.getCurrent() > -1) {
	// // if (lockMovieSeatsInfo != null &&
	// // lockMovieSeatsInfo.payType.size() > 0) {
	// try {
	// payType =
	// movieSeatPayStyleAdapter.getItem(movieSeatPayStyleAdapter.getCurrent()).moviePayType;
	// System.out.println("payType--     " + payType);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// // }
	// }
	// return payType;
	// }

	// 发送短信
	private void sendShort(String phone, String sms) {
		Uri smsToUri = Uri.parse("smsto:" + phone);
		Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO, smsToUri);
		mIntent.putExtra("sms_body", sms);
		startActivity(mIntent);
	}

	private void getExtra() {
		Intent intent = getIntent();
		lockMovieSeatsInfo = (LockOrDebLockMovieSeatsInfo) intent.getSerializableExtra("lockMovieSeatsInfo");
		movie_ShowTime = intent.getStringExtra("showTime");
		movie_Cinema_Name = intent.getStringExtra("movieCinemaName");
		System.out.println("movie_Cinema_Name---------------" + movie_Cinema_Name);
		movie_Name = intent.getStringExtra("movieName");
		moviecinema_id = intent.getStringExtra("moviecinema_id");
		cinemaRoom = intent.getStringExtra("cinemaRoom");
		cinemaTime = intent.getStringExtra("cinemaTime");
		companyId = intent.getStringExtra("companyId");
		payPrice = intent.getStringExtra("payPrice");
		try {

			c_id = Integer.parseInt(moviecinema_id);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	private void initView() {
		imBack = (ImageButton) findViewById(R.id.imBack);
		relativelayout_order_info = (RelativeLayout) findViewById(R.id.relativelayout_order_info);
//		movieSeatinfo_pay_layout = (LinearLayout) findViewById(R.id.movieSeatinfo_pay_layout);
		list_orderinfo_details = (ListView) findViewById(R.id.list_orderinfo_details);
		// list_orderinfo_details.setClickable(false);
		list_orderinfo_pay_style = (LinearLayout) findViewById(R.id.list_orderinfo_pay_style);
		textview_pay_now = (ImageView) findViewById(R.id.textview_pay_now);
		relative_pay_now = (RelativeLayout) findViewById(R.id.relative_pay_now);
		View view = LayoutInflater.from(mContext).inflate(R.layout.movieseat_orderinfo_header, null);
		TextView movieSeatinfo_order_phone = (TextView) view.findViewById(R.id.movieSeatinfo_order_phone);
		if (lockMovieSeatsInfo != null) {
			System.out.println("lockMovieSeatsInfo.recyPhone    " + lockMovieSeatsInfo.recyPhone);
			if (!TextUtils.isEmpty(lockMovieSeatsInfo.recyPhone)) {

				movieSeatinfo_order_phone.setText(lockMovieSeatsInfo.recyPhone);
			}
		}

		TextView movieSeat_order_moviename = (TextView) view.findViewById(R.id.movieSeat_order_moviename);
		if (!TextUtils.isEmpty(movie_Name)) {

			movieSeat_order_moviename.setText(movie_Name);
		}
		TextView movieSeatInfo_order_time = (TextView) view.findViewById(R.id.movieSeatInfo_order_time);
		if (!TextUtils.isEmpty(movie_ShowTime)) {

			movieSeatInfo_order_time.setText(movie_ShowTime + "  " + cinemaTime);
		}
		TextView movieSeatinfo_order_cinemaname = (TextView) view.findViewById(R.id.movieSeatinfo_order_cinemaname);
		if (!TextUtils.isEmpty(movie_Cinema_Name)) {

			movieSeatinfo_order_cinemaname.setText(movie_Cinema_Name + "  " + cinemaRoom);
		}
		list_orderinfo_details.addHeaderView(view);

	}

	// 获取订单
	class GetOrderInfoTask extends MovieAsyncTask<String, String, OrderBySeat> {
		public String exception;

		// LoadingDialog getOrderLoadingDialog = null;

		public GetOrderInfoTask(Activity activity, String loadingText) {
			super(activity, null, true, true, true, loadingText);
		}

		@Override
		protected OrderBySeat doInBackground(String... params) {

			OrderBySeat orderBySeat = null;
			try {
				if(params.length == 12) {//小额支付、支付宝支付 加price参数
					orderBySeat = (OrderBySeat) MovieLib.getInstance(mContext).generateOrder(params[0], params[1], params[2], params[3], params[4],
							params[5], params[6], params[7], params[8], params[9], params[10], "", "", params[11], "", "");
				} else {
					orderBySeat = (OrderBySeat) MovieLib.getInstance(mContext).generateOrder(params[0], params[1], params[2], params[3], params[4],
							params[5], params[6], params[7], params[8], params[9], params[10], "", "", "", "", "");
				}
				// getOrderLoadingDialog = new LoadingDialog(taskContext);
				// getOrderLoadingDialog.setLoadingText("正在生成订单");
				// getOrderLoadingDialog.show();
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
			return orderBySeat;
		}

		@Override
		protected void onPostExecute(OrderBySeat result) {
//			if (!TextUtils.isEmpty(exception)) {
//				Toast.makeText(mContext, exception, Toast.LENGTH_SHORT).show();
//			}
			// 处理订单信息。
			if (result != null && result.isSuccess()) {
				if (result.orderBySeatInner != null) {
					// getOrderLoadingDialog.close();
					if (payType.equals("2")) {
						orderBySeat = result;
						Intent in = new Intent(BuyMovieTicketAct.this, BuyTicketWebActivity.class);
						in.putExtra("c_id", moviecinema_id);
						in.putExtra("orderNo", result.orderBySeatInner.orderid);
						in.putExtra("companyId", companyId);
						in.putExtra("Amount", (lockMovieSeatsInfo.lockSeats.size() * Float.parseFloat(payPrice)) + "");
						in.putExtra("mobile", shareP.getUserName());
						in.putExtra("targetMobile", shareP.getUserName());
						in.putExtra("merchantName", movie_Cinema_Name);
						in.putExtra("merchandise", movie_Cinema_Name + "座位票" + lockMovieSeatsInfo.lockSeats.size() + "张");
						in.putExtra("lockMovieSeatsInfo", lockMovieSeatsInfo);
						in.putExtra("showTime", movie_ShowTime);// 放映时间
						in.putExtra("movieName", movie_Name);
						in.putExtra("cinemaRoom", cinemaRoom);
						in.putExtra("cinemaTime", cinemaTime);
						in.putExtra("isDuihuan", false);
						in.putExtra("spID", getOrderTypeBeanInfo.merchantBean.spid);
						in.putExtra("spPwd", getOrderTypeBeanInfo.merchantBean.sppwd);
						in.putExtra("spCode", getOrderTypeBeanInfo.merchantBean.spcode);
						in.putExtra("merchantCode", getOrderTypeBeanInfo.merchantBean.merchantCode);
						startActivity(in);
					} else if(payType.equals("5")) {//小额支付
						orderBySeat = result;
						Intent in = new Intent(BuyMovieTicketAct.this, BuyTicketWebSmallPaymentActivity.class);
						in.putExtra("c_id", moviecinema_id);
						in.putExtra("orderNo", result.orderBySeatInner.orderid);
						in.putExtra("companyId", companyId);
						in.putExtra("Amount", totalPrice_smallpayment);
						in.putExtra("mobile", shareP.getUserName());
						in.putExtra("targetMobile", shareP.getUserName());
						in.putExtra("merchantName", movie_Cinema_Name);
						in.putExtra("merchandise", movie_Cinema_Name + "座位票" + lockMovieSeatsInfo.lockSeats.size() + "张");
						in.putExtra("lockMovieSeatsInfo", lockMovieSeatsInfo);
						in.putExtra("showTime", movie_ShowTime);// 放映时间
						in.putExtra("movieName", movie_Name);
						in.putExtra("cinemaRoom", cinemaRoom);
						in.putExtra("cinemaTime", cinemaTime);
						in.putExtra("isDuihuan", false);
						in.putExtra("spID", getOrderTypeBeanInfo.merchantBean.spid);
						System.out.println("getOrderTypeBeanInfo.merchantBean.spid     "+getOrderTypeBeanInfo.merchantBean.spid);
						in.putExtra("spPwd", getOrderTypeBeanInfo.merchantBean.sppwd);
						in.putExtra("spCode", getOrderTypeBeanInfo.merchantBean.spcode);
						in.putExtra("merchantCode", getOrderTypeBeanInfo.merchantBean.merchantCode);
						startActivity(in);
					} else if(payType.equals("6")) {//支付宝支付
						orderBySeat = result;
						Intent in = new Intent(BuyMovieTicketAct.this, BuyTicketWebAlipayActivity.class);
						in.putExtra("c_id", moviecinema_id);
						in.putExtra("orderNo", result.orderBySeatInner.orderid);
						in.putExtra("companyId", companyId);
						in.putExtra("Amount", totalPrice_alipay);
						in.putExtra("mobile", shareP.getUserName());
						in.putExtra("targetMobile", shareP.getUserName());
						in.putExtra("merchantName", movie_Cinema_Name);
						in.putExtra("merchandise", movie_Cinema_Name + "座位票" + lockMovieSeatsInfo.lockSeats.size() + "张");
						in.putExtra("lockMovieSeatsInfo", lockMovieSeatsInfo);
						in.putExtra("showTime", movie_ShowTime);// 放映时间
						in.putExtra("movieName", movie_Name);
						in.putExtra("cinemaRoom", cinemaRoom);
						in.putExtra("cinemaTime", cinemaTime);
						in.putExtra("isDuihuan", false);
						in.putExtra("spID", getOrderTypeBeanInfo.merchantBean.spid);
						System.out.println("getOrderTypeBeanInfo.merchantBean.spid     "+getOrderTypeBeanInfo.merchantBean.spid);
						in.putExtra("spPwd", getOrderTypeBeanInfo.merchantBean.sppwd);
						in.putExtra("spCode", getOrderTypeBeanInfo.merchantBean.spcode);
						in.putExtra("merchantCode", getOrderTypeBeanInfo.merchantBean.merchantCode);
						startActivity(in);
					} else if(payType.equals("4")) {//移动赠券
						orderBySeat = result;
						Intent in = new Intent(BuyMovieTicketAct.this, BuyTicketZengquanWebActivity.class);
						in.putExtra("c_id", moviecinema_id);
						in.putExtra("orderNo", result.orderBySeatInner.orderid);
						in.putExtra("companyId", companyId);
						in.putExtra("Amount", (lockMovieSeatsInfo.lockSeats.size() * Float.parseFloat(payPrice)));
						in.putExtra("mobile", shareP.getUserName());
						in.putExtra("targetMobile", shareP.getUserName());
						in.putExtra("merchantName", movie_Cinema_Name);
						in.putExtra("merchandise", movie_Cinema_Name + "座位票" + lockMovieSeatsInfo.lockSeats.size() + "张");
						in.putExtra("lockMovieSeatsInfo", lockMovieSeatsInfo);
						in.putExtra("showTime", movie_ShowTime);// 放映时间
						in.putExtra("movieName", movie_Name);
						in.putExtra("cinemaRoom", cinemaRoom);
						in.putExtra("cinemaTime", cinemaTime);
						in.putExtra("isDuihuan", false);
						in.putExtra("url", getOrderTypeBeanInfo.merchantBean.callback);
						startActivity(in);
					}
					// PayOrderInfoTask payOrderInfoTask = new
					// PayOrderInfoTask(taskContext, "正在支付订单....");
					// payOrderInfoTask.execute(result.orderBySeatInner.orderid,
					// "1");
				}
			} else {
				// getOrderLoadingDialog.close();
//				Toast.makeText(mContext, "生成订单失败！", Toast.LENGTH_SHORT).show();
				MessageDialog.getInstance().setData(BuyMovieTicketAct.this, R.string.data_failed_to_generate_orders, false);
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	class GetOrderInfoYinlianTask extends MovieAsyncTask<String, String, OrderBySeat> {
		public String exception;

		// LoadingDialog getOrderLoadingDialog = null;

		public GetOrderInfoYinlianTask(Activity activity, String loadingText) {
			super(activity, null, true, true, true, loadingText);
		}

		@Override
		protected OrderBySeat doInBackground(String... params) {

			OrderBySeat orderBySeat = null;
			try {

				orderBySeat = (OrderBySeat) MovieLib.getInstance(mContext).generateOrder(params[0], params[1], params[2], params[3], params[4],
						params[5], params[6], params[7], params[8], params[9], params[10], "", "", "", "", "");
				// getOrderLoadingDialog = new LoadingDialog(taskContext);
				// getOrderLoadingDialog.setLoadingText("正在生成订单");
				// getOrderLoadingDialog.show();
				System.out.println("orderBySeat    " + orderBySeat.orderBySeatInner.orderid);
				setSp(c_id);
				String xml = "";
				String orderDesc = "";
				try {
					orderDesc = "在线订座票  " + lockMovieSeatsInfo.lockSeats.size() + "张";
				} catch (Exception e) {}
				if (moviecinema_id.equals("7")) {
					xml = sendXml(spID, spPwd, spCode, "0101", merchantCode, timestamp, timeout, msg_id, orderBySeat.orderBySeatInner.orderid,
							(lockMovieSeatsInfo.lockSeats.size() * (Float.parseFloat(payPrice))) + "", orderDesc, params[1], params[1], movie_Cinema_Name,
//							(lockMovieSeatsInfo.lockSeats.size() * 0.01) + "", orderDesc, params[1], params[1], movie_Cinema_Name,
							movie_Cinema_Name + "座位票" + params[3] + "张");
				} else {
					xml = sendXml(spID, spPwd, spCode, "0101", merchantCode, timestamp, timeout, msg_id, orderBySeat.orderBySeatInner.orderid,
							(lockMovieSeatsInfo.lockSeats.size() * (Float.parseFloat(payPrice) + 1)) + "", orderDesc, params[1], params[1], movie_Cinema_Name,
//							(lockMovieSeatsInfo.lockSeats.size() * 0.01) + "", orderDesc, params[1], params[1], movie_Cinema_Name,
							movie_Cinema_Name + "座位票" + params[3] + "张");
				}
//				if (moviecinema_id.equals("7")) {
//					xml = sendXml(spID, spPwd, spCode, "0101", merchantCode, timestamp, timeout, msg_id, orderBySeat.orderBySeatInner.orderid,
//							(lockMovieSeatsInfo.lockSeats.size() * (Float.parseFloat(payPrice))) + "", params[1], params[1], movie_Cinema_Name,
//							movie_Cinema_Name + "座位票" + params[3] + "张");
//				} else {
//					xml = sendXml(spID, spPwd, spCode, "0101", merchantCode, timestamp, timeout, msg_id, orderBySeat.orderBySeatInner.orderid,
//							(lockMovieSeatsInfo.lockSeats.size() * (Float.parseFloat(payPrice) + 1)) + "", params[1], params[1], movie_Cinema_Name,
//							movie_Cinema_Name + "座位票" + params[3] + "张");
//				}
				// System.out.println(" --- " + spID + "     ---    " + spPwd +
				// "    msg_id   " + msg_id
				// + "    orderBySeat.orderBySeatInner.orderid    " +
				// orderBySeat.orderBySeatInner.orderid + "    --   " +
				// params[1] + " --  "
				// + spCode + "  --   " + spType + "  --  " + merchantCode +
				// "   --  " + timestamp + "  --  " + timeout + "  ---  " +
				// msg_id
				// + "  --  " + movie_Cinema_Name + "   ---  "
				// + (lockMovieSeatsInfo.lockSeats.size() *
				// (Float.parseFloat(movieSeatPayStyleAdapter.getList().get(0).price)
				// + 1)) + "");
				 System.out.println("xml---------  " + xml);
				responseStr = BaseRequest.poststh(YinlianUrl, xml);
				System.out.println("responseStr    " + responseStr);
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
			return orderBySeat;
		}

		@Override
		protected void onPostExecute(OrderBySeat result) {
//			if (!TextUtils.isEmpty(exception)) {
//				Toast.makeText(mContext, exception, Toast.LENGTH_SHORT).show();
//			}
			// 处理订单信息。
			if (result != null && result.isSuccess()) {
				if (result.orderBySeatInner != null) {
					// getOrderLoadingDialog.close();
					orderBySeat = result;
					if (responseStr != null && !responseStr.equals("") && !responseStr.contains("respCode")) {
						// 初始化手机POS环境
						Utils.setPackageName(getPackageName());
						// MY_PKG是你项目的包名
						UPOMP.init();
						// 设置Intent指向AndroidManifest中设置的action
						// action的名字 改成（接入商名称_商户证书号_插件版本）例如
						// hanxin_303310048990001_2_0
						Intent intent = new Intent("itotem_MOVIE_1.1.0");
						// System.out.println("action-------        "+c_name+"_"+BaseRequest.list.get(0).merchantId+"_"+BaseRequest.list.get(0).version);
						// 将封装好的xml报文传入bundle
						Bundle mBundle = new Bundle();
						mBundle.putString("xml", responseStr);
						System.out.println("responseStr          " + responseStr);
						// 如果你的activity关闭了，请设置pkg（插件返回要调起activity的action
						// name），否则不需要写
						// mBundle.putString("pkg",
						// "itotem_com_movie_BuyTicketExchangeAct");
						// 将bundle置于intent中
						intent.putExtras(mBundle);
						// 使用intent跳转至支付插件
						startActivity(intent);
					} else {
//						Toast.makeText(mContext, "生成订单失败！", Toast.LENGTH_SHORT).show();
						MessageDialog.getInstance().setData(BuyMovieTicketAct.this, R.string.data_failed_to_generate_orders, false);
					}

					// Intent in = new Intent(BuyMovieTicketAct.this,
					// BuyTicketWebActivity.class);
					// in.putExtra("c_id", moviecinema_id);
					// in.putExtra("orderNo", result.orderBySeatInner.orderid);
					// in.putExtra("companyId", companyId);
					// in.putExtra("Amount",
					// (lockMovieSeatsInfo.lockSeats.size() *
					// Float.parseFloat(mlist.get(curentposition).price)));
					// in.putExtra("mobile", shareP.getUserName());
					// in.putExtra("targetMobile", shareP.getUserName());
					// in.putExtra("merchantName", movie_Cinema_Name);
					// in.putExtra("merchandise", movie_Cinema_Name + "座位票" +
					// lockMovieSeatsInfo.lockSeats.size() + "张");
					// in.putExtra("orderQueryBySeat", orderQueryBySeat);
					// in.putExtra("showTime", movie_ShowTime);// 放映时间
					// in.putExtra("movieName", movie_Name);
					// in.putExtra("isDuihuan", false);
					// startActivity(in);
				}
			} else {
				// getOrderLoadingDialog.close();
//				Toast.makeText(mContext, "生成订单失败！", Toast.LENGTH_SHORT).show();
				MessageDialog.getInstance().setData(BuyMovieTicketAct.this, R.string.data_failed_to_generate_orders, false);
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	private void setSp(int c_id) {
		long currentTime = System.currentTimeMillis();
		Date date = new Date(currentTime);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(date);

		Date date_out = new Date(currentTime + 1000 * 3600 * 24);
		String time_out = format.format(date_out);
		
		timestamp = time;
		timeout = Long.parseLong(time_out) + "";
		System.out.println("timeout--------------" + timeout);
		String timeRandom = Math.round(Math.floor(Math.random() * (1 + 9999 - 1000) + 1000)) + "";
		msg_id = time + timeRandom;

	}

	/**
	 * 
	 * @param spID
	 * @param spPwd
	 * @param spCode
	 * @param spType
	 * @param merchantCode
	 * @param timestamp
	 *            yyyyMMddHHmmss
	 * @param timeout
	 *            yyyyMMddHHmmss
	 * @param msg_id
	 *            请求平台流水号(当前时间+4位随机数)
	 * @param orderNo
	 *            订单号
	 * @param amount
	 *            订单总金额
	 * @param mobile
	 *            手机号码
	 * @param targetMobile
	 *            接收号码
	 * @param merchantName
	 *            商户名称
	 * @param merchandise
	 *            订单名称
	 * @return
	 */
	private String sendXml(String spID, String spPwd, String spCode, String spType, String merchantCode, String timestamp, String timeout,
			String msg_id, String orderNo, String amount, String orderDesc, String mobile, String targetMobile, String merchantName, String merchandise) {

		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><interfaceData><spID>"
				+ spID
				+ "</spID><spPwd>"
				+ spPwd
				+ "</spPwd><spCode>"
				+ spCode
				+ "</spCode><spType>"
				+ spType
				+ "</spType><merchantCode>"
				+ merchantCode
				+ "</merchantCode><timestamp>"
				+ timestamp
				+ "</timestamp><timeout>"
				+ timeout
				+ "</timeout><datas><msg_id>"
				+ msg_id
				+ "</msg_id><orderNo>"
				+ orderNo
				+ "</orderNo><mobile>"
				+ mobile
				+ "</mobile><targetMobile>"
				+ targetMobile
				+ "</targetMobile><merchantName>"
				+ merchantName
				+ "</merchantName><merchandise>"
				+ merchandise
				+ "</merchandise><amount>"
				+ amount
				+ "</amount><orderDesc>"
				+ orderDesc
				+ "</orderDesc><addr>empty</addr><backUrl>http://wap.szicity.com</backUrl><payType>8</payType><spPay></spPay><outMerchantCode>empty</outMerchantCode><period>10</period><periodUnit>4</periodUnit><reqsource>1</reqsource></datas></interfaceData>";
		return xml;// amount
	}

	// 解除座位的锁定
	private void startDebLockSeat() {
		List<String> list_price = new ArrayList<String>();
		List<String> list_seatCode = new ArrayList<String>();
		List<String> list_seatRow = new ArrayList<String>();
		List<String> list_seatCol = new ArrayList<String>();
		List<String> list_seatAreaCode = new ArrayList<String>();
		if (orderQueryBySeat != null) {
			if (orderQueryBySeat.orderQueryBySeatList != null && orderQueryBySeat.orderQueryBySeatList.size() > 0) {
				for (int i = 0; i < orderQueryBySeat.orderQueryBySeatList.size(); i++) {
					list_seatCode.add(orderQueryBySeat.orderQueryBySeatList.get(i).seatCode);
					list_price.add(orderQueryBySeat.orderQueryBySeatList.get(i).price);
					list_seatCol.add(orderQueryBySeat.orderQueryBySeatList.get(i).seatCol);
					list_seatRow.add(orderQueryBySeat.orderQueryBySeatList.get(i).seatRow);
					list_seatAreaCode.add(orderQueryBySeat.orderQueryBySeatList.get(i).seatAreaCode);
				}
			}
		}
		seatCode = TextUtil.montageField(list_seatCode);
		seatAreaCode = TextUtil.montageField(list_seatAreaCode);
		seatRow = TextUtil.montageField(list_seatRow);
		seatCol = TextUtil.montageField(list_seatCol);
		payPrices = TextUtil.montageField(list_price);
		new LockMovieSeatInfoTask(BuyMovieTicketAct.this).execute(moviecinema_id, orderQueryBySeat.showCode, "1", orderQueryBySeat.count,
				lockMovieSeatsInfo.recyPhone, seatCode, seatRow, seatCol, seatAreaCode, payPrices);
	}

	// 解除锁定座位信息
	class LockMovieSeatInfoTask extends MovieAsyncTask<String, String, LockOrDebLockMovieSeatsInfo> {
		public String exception;

		public LockMovieSeatInfoTask(Activity activity) {
			super(activity, null, true, true, true, "");
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
			System.out.println("dongdianzhouSeatSelectedActivity" + result.toString());
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


	/** 使用优惠券 */
	private void showYanzhengDialog() {
		LayoutInflater layout = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(R.layout.show_dialog_yanzheng, (ViewGroup) this.findViewById(R.id.dialog_layout));
		final Dialog builder = new Dialog(BuyMovieTicketAct.this, R.style.dialog);
		builder.setContentView(v);

		TextView pay_no = (TextView) v.findViewById(R.id.pay_no);
		TextView pay_ok = (TextView) v.findViewById(R.id.pay_ok);
		final ListView listview = (ListView) v.findViewById(R.id.listview);
		final PayYouhuiAdapter adapter = new PayYouhuiAdapter(this);
		listview.setAdapter(adapter);
		for (int i = 0; i < getOrderTypeBeanInfo.getOrderTypeBeanList.size(); i++) {
			if (getOrderTypeBeanInfo.getOrderTypeBeanList.get(i).moviePayType.equals("2")) {
				adapter.setData(getOrderTypeBeanInfo.getOrderTypeBeanList.get(i).couponBeanList);
				break;
			}
		}
		pay_no.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				builder.dismiss();
			}
		});

		pay_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				builder.dismiss();
				Intent in = new Intent(BuyMovieTicketAct.this, YanzhengActivity.class);

				in.putExtra("CouponBean", adapter.getItem(adapter.getCurrent()));
				in.putExtra("size", lockMovieSeatsInfo.lockSeats.size());
				in.putExtra("lockMovieSeatsInfo", lockMovieSeatsInfo);
				in.putExtra("movie_seat_seatCode", movie_seat_seatCode);
				in.putExtra("movie_seat_seatRow", movie_seat_seatRow);
				in.putExtra("movie_seat_seatCol", movie_seat_seatCol);
				in.putExtra("moviecinema_id", moviecinema_id);
				in.putExtra("companyId", companyId);
				in.putExtra("merchantName", movie_Cinema_Name);
				in.putExtra("showTime", movie_ShowTime);// 放映时间
				in.putExtra("movieName", movie_Name);
				in.putExtra("cinemaRoom", cinemaRoom);
				in.putExtra("cinemaTime", cinemaTime);
				in.putExtra("payPrice", payPrice);
				in.putExtra("isDuihuan", false);
				in.putExtra("spID", getOrderTypeBeanInfo.merchantBean.spid);
				in.putExtra("spPwd", getOrderTypeBeanInfo.merchantBean.sppwd);
				in.putExtra("spCode", getOrderTypeBeanInfo.merchantBean.spcode);
				in.putExtra("merchantCode", getOrderTypeBeanInfo.merchantBean.merchantCode);
				startActivity(in);
			}
		});

		builder.show();
	}

	

	GetOrderTypeBeanInfo getOrderTypeBeanInfo;

	class GetOrderTypeBeanInfoTask extends MovieAsyncTask<String, String, GetOrderTypeBeanInfo> {
		public String exception;

		public GetOrderTypeBeanInfoTask(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected GetOrderTypeBeanInfo doInBackground(String... params) {

			try {
				getOrderTypeBeanInfo = lib.getMovieGetOrderType(moviecinema_id, "att", PublicUtils.getVersionName(BuyMovieTicketAct.this));
//				getOrderTypeBeanInfo = lib.getMovieGetOrderType(moviecinema_id, "att", "1");
//				getOrderTypeBeanInfo = lib.getMovieGetOrderType(moviecinema_id, "att", "");
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return getOrderTypeBeanInfo;
		}

		@Override
		protected void onPostExecute(GetOrderTypeBeanInfo result) {
			super.onPostExecute(result);
			if (result != null) {
				if("1".equals(result.code)) {
					if(result.getOrderTypeBeanList != null && result.getOrderTypeBeanList.size() > 0) {
						// movieSeatPayStyleAdapter.addList(result.getOrderTypeBeanList);
						setPayData(result.getOrderTypeBeanList, Integer.parseInt(lockMovieSeatsInfo.ticketCount));
						// spID = "SZDYPDM000079";
						// spPwd = "666666";
						// spCode = "SZDYPDM000079";
						// spType = "0101";
						// merchantCode = "888075578000040";
						spID = result.merchantBean.spid;
						spPwd = result.merchantBean.sppwd;
						spCode = result.merchantBean.spcode;
						spType = "0101";
						merchantCode = result.merchantBean.merchantCode;
					}
				} else {
					MessageDialog.getInstance().setData(BuyMovieTicketAct.this, result.message, false);
				}
			}else{
//				Toast.makeText(BuyMovieTicketAct.this, "获取支付方式失败", Toast.LENGTH_SHORT).show();
				MessageDialog.getInstance().setData(BuyMovieTicketAct.this, R.string.data_failed_to_get_paytype, false);
			}
		}
	}

	// private void setAnimationUpToDown(View view){
	// Animation animation = AnimationUtils.loadAnimation(this,
	// R.anim.pay_uptodown_translate);
	// view.startAnimation(animation);
	// }
	// private void setAnimationDownToUp(View view){
	// Animation animation = AnimationUtils.loadAnimation(this,
	// R.anim.pay_downtoup_translate);
	// view.startAnimation(animation);
	// }
	// private void setAnimationOtherUpToDown(View view){
	// Animation animation = AnimationUtils.loadAnimation(this,
	// R.anim.pay_otherview_uptodown_translate);
	// view.startAnimation(animation);
	// }
	// private void setAnimationOtherDownToUp(View view){
	// Animation animation = AnimationUtils.loadAnimation(this,
	// R.anim.pay_otherview_downtoup_translate);
	// view.startAnimation(animation);
	// }
	private void animationToDown(View view) {
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, -0.7f, Animation.RELATIVE_TO_SELF, 0f);
		translateAnimation.setDuration(300);
		animationSet.addAnimation(translateAnimation);
		view.startAnimation(animationSet);
	}

	private void animationToUp(View view) {
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0.4f, Animation.RELATIVE_TO_SELF, 0f);
		translateAnimation.setDuration(300);
		animationSet.addAnimation(translateAnimation);
		view.startAnimation(animationSet);
	}

	private void animationOtherToUp(View view) {
		AnimationSet animationSet = new AnimationSet(true);
		ScaleAnimation translateAnimation = new ScaleAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
		translateAnimation.setDuration(300);
		animationSet.addAnimation(translateAnimation);

		view.startAnimation(animationSet);
	}

	private class MyURLSpan extends ClickableSpan {
		private String mUrl;

		MyURLSpan(String url) {
			mUrl = url;
		}

		@Override
		public void onClick(View widget) {
			try {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(mUrl));
				startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//
	private void setPayData(final ArrayList<GetOrderTypeBean> list, int movieCinemaNum) {
		final ArrayList<View> hList = new ArrayList<View>();
		for (int i = 0; i < list.size(); i++) {
			final int position = i;
			final View view = LayoutInflater.from(this).inflate(R.layout.buytickexchange_choose_list, null);
			final ImageView checkBox = (ImageView) view.findViewById(R.id.checkbox);
			TextView sumMoneyUnit = (TextView) view.findViewById(R.id.sumMoneyUnit);
			TextView unit = (TextView) view.findViewById(R.id.unit);
			TextView zhifuname = (TextView) view.findViewById(R.id.zhifuname);
			ImageView help = (ImageView) view.findViewById(R.id.help);
			TextView price = (TextView) view.findViewById(R.id.priceMoney);
			TextView sumMoney = (TextView) view.findViewById(R.id.sumMoney);
			TextView count = (TextView) view.findViewById(R.id.count);
			final LinearLayout choosePhonePayType = (LinearLayout) view.findViewById(R.id.choosePhonePayType);
			final ImageView checkboxPayAll = (ImageView) view.findViewById(R.id.checkboxPayAll);
			final ImageView checkboxPayYouhui = (ImageView) view.findViewById(R.id.checkboxPayYouhui);
			final TextView helpMessage = (TextView) view.findViewById(R.id.helpMessage);
			if (!hList.contains(view)) {
				hList.add(view);
			}

			if (list != null && list.size() > 0) {
				// if(!moviePayType.get(position).payTpyeName.equals("手机支付")){//由于当钱仅支持手机支付，所以屏蔽了其他的支付方式
				// viewHoder.checkBox_payStyle.setClickable(false);
				// }
				if (list.get(position).moviePayType.equals("100")) {
					if (moviecinema_id.equals("7")) {
						price.setText((int) Float.parseFloat(payPrice) + "");
					} else {
						price.setText("(" + (int) Float.parseFloat(payPrice) + "+" + 1 + ")");
					}
				} else {
					price.setText((int) Float.parseFloat(payPrice) + "");
				}

				unit.setText("元");
				zhifuname.setText(list.get(position).name);
				if (list.get(position).moviePayType.equals("5") || list.get(position).moviePayType.equals("6")) {//小额支付、支付宝支付
					if(!TextUtils.isEmpty(list.get(i).rule) && !TextUtils.isEmpty(list.get(i).handfee)) {
						try {
							if("1".equals(list.get(i).rule)) {
								count.setText(100 + (int)(Double.parseDouble(list.get(i).handfee) * 100) + "%×" + movieCinemaNum);
							} else if("2".equals(list.get(i).rule)) {
								price.setText("(" + (int) Float.parseFloat(payPrice) + "+" + (int)(Double.parseDouble(list.get(i).handfee)) + ")元");
								count.setText(movieCinemaNum + "");
								unit.setText("");
							}
						} catch (Exception e) {
						}
					} else {
						count.setText(movieCinemaNum + "");
					}
				} else {
					count.setText(movieCinemaNum + "");
				}
				
				if (list.get(position).moviePayType.equals("100")) {
					if (moviecinema_id.equals("7")) {
						sumMoney.setText((movieCinemaNum * (int) Float.parseFloat(payPrice)) + "");
					} else {
						sumMoney.setText((movieCinemaNum * ((int) Float.parseFloat(payPrice) + 1)) + "");
					}
				} else if(list.get(position).moviePayType.equals("5")) {//小额支付
					if(!TextUtils.isEmpty(list.get(i).rule) && !TextUtils.isEmpty(list.get(i).handfee)) {
						try {
							if("1".equals(list.get(i).rule)) {
								double total =movieCinemaNum * (int)(((int) Float.parseFloat(payPrice))*(1+Double.parseDouble(list.get(i).handfee)) + 0.5);
								sumMoney.setText((int)total + "");
							} else if("2".equals(list.get(i).rule)) {
								double total = movieCinemaNum * (int)(((int) Float.parseFloat(payPrice) + Double.parseDouble(list.get(i).handfee)) + 0.5);
								sumMoney.setText((int)total + "");
							}
						} catch (Exception e) {
						}
					} else {
						sumMoney.setText((movieCinemaNum * (int) Float.parseFloat(payPrice)) + "");
					}
					totalPrice_smallpayment = sumMoney.getText().toString();
				} else if(list.get(position).moviePayType.equals("6")) {//支付宝支付
					if(!TextUtils.isEmpty(list.get(i).rule) && !TextUtils.isEmpty(list.get(i).handfee)) {
						try {
							if("1".equals(list.get(i).rule)) {
								double total =movieCinemaNum * (int)(((int) Float.parseFloat(payPrice))*(1+Double.parseDouble(list.get(i).handfee)) + 0.5);
								sumMoney.setText((int)total + "");
							} else if("2".equals(list.get(i).rule)) {
								double total = movieCinemaNum * (int)(((int) Float.parseFloat(payPrice) + Double.parseDouble(list.get(i).handfee)) + 0.5);
								sumMoney.setText((int)total + "");
							}
						} catch (Exception e) {
						}
					} else {
						sumMoney.setText((movieCinemaNum * (int) Float.parseFloat(payPrice)) + "");
					}
					totalPrice_alipay = sumMoney.getText().toString();
				} else {
					sumMoney.setText((movieCinemaNum * (int) Float.parseFloat(payPrice)) + "");
				}
				if (!list.get(position).moviePayType.equals("2")) {
					helpMessage.setText(list.get(position).desc);
				} else {
					if (getOrderTypeBeanInfo != null) {
						String htmlLinkText1[] = new String[getOrderTypeBeanInfo.linkUrl.split(",").length];
						String mes = list.get(position).desc;
						String htmlLinkText = "";
						if(getOrderTypeBeanInfo.freeRegistUrl.contains("http")){
						htmlLinkText = "<font color=#ff0000><u><a href=\"" + getOrderTypeBeanInfo.freeRegistUrl
								+ "\">免费注册</a></u></font>";
						}
						try {
							
							for (int m = 0; m < getOrderTypeBeanInfo.linkUrl.split(",").length; m++) {
								if (list.get(position).desc.contains(getOrderTypeBeanInfo.linkUrl.split(",")[m])) {
									if(getOrderTypeBeanInfo.linkUrl.split(",")[m].contains("http")){
									htmlLinkText1[m] = "<font color=\"#0080FF\"><u><a href=\"" + getOrderTypeBeanInfo.linkUrl.split(",")[m] + "\">"
											+ getOrderTypeBeanInfo.linkUrl.split(",")[m] + "</a></u></font>";
									mes = mes.replace(getOrderTypeBeanInfo.linkUrl.split(",")[m], htmlLinkText1[m]);
									}
								}
							}
							helpMessage.setText(Html.fromHtml(mes + "   " + htmlLinkText));
						} catch (Exception e) {
							e.printStackTrace();
							helpMessage.setText(Html.fromHtml(list.get(position).desc + "   " + htmlLinkText));
						}

						helpMessage.setMovementMethod(LinkMovementMethod.getInstance());
						CharSequence text = helpMessage.getText();
						if (text instanceof Spannable) {
							int end = text.length();
							Spannable sp = (Spannable) helpMessage.getText();
							URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
							SpannableStringBuilder style = new SpannableStringBuilder(text);
							style.clearSpans(); // should clear old spans
							for (URLSpan url : urls) {
								new MyURLSpan(url.getURL());
//								style.setSpan(myURLSpan, sp.getSpanStart(url), sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
							}
//							helpMessage.setText(style);
						}
					}
				}
				sumMoneyUnit.setText("元");
				// 设置选中状态
				help.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						switch (helpMessage.getVisibility()) {
						case View.GONE:
							helpMessage.setVisibility(View.VISIBLE);
							break;
						case View.VISIBLE:
							helpMessage.setVisibility(View.GONE);
							break;
						}
					}
				});

				if (curentposition == -1) {
					checkBox.setBackgroundResource(R.drawable.uncheck);

					relative_pay_now.setVisibility(View.GONE);
				} else {

					relative_pay_now.setVisibility(View.VISIBLE);
				}

				checkBox.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (curentposition != position) {
							if (curentposition > -1) {
								hList.get(curentposition).findViewById(R.id.checkbox).setBackgroundResource(R.drawable.uncheck);
							}
							// for(int i =
							// 0;i<list_orderinfo_pay_style.getChildCount();i++){
							// ((ImageView)(list_orderinfo_pay_style.getChildAt(i).findViewById(R.id.checkBox))).setBackgroundResource(R.drawable.uncheck);
							// }
							curentposition = position;
//							payType = list.get(position).moviePayType;
							checkBox.setBackgroundResource(R.drawable.check);
							payType = list.get(position).moviePayType;
							System.out.println("payType-----------" + payType);
							if (list.get(position).couponBeanList != null && list.get(position).couponBeanList.size() != 0) {
								if (payType.equals("2")) {
									animationToDown(choosePhonePayType);
									for (int i = 0; i < hList.size(); i++) {
										if (i != position) {
											animationToDown(hList.get(i));
										}
									}

									choosePhonePayType.setVisibility(View.VISIBLE);
									phonePayType = PHONE_PAYTYPE_ALL;
									checkboxPayAll.setBackgroundResource(R.drawable.check);
									checkboxPayYouhui.setBackgroundResource(R.drawable.uncheck);
								} else {
									for (int i = 0; i < hList.size(); i++) {
										if (hList.get(i).findViewById(R.id.choosePhonePayType).getVisibility() == View.VISIBLE) {
											// setAnimationDownToUp(choosePhonePayType);
											for (int j = 0; j < hList.size(); j++) {
												if (i != j) {
													animationToUp(hList.get(j));
												}
											}
										}
										hList.get(i).findViewById(R.id.choosePhonePayType).setVisibility(View.GONE);
									}
								}
							} else {
								for (int i = 0; i < hList.size(); i++) {
									if (hList.get(i).findViewById(R.id.choosePhonePayType).getVisibility() == View.VISIBLE) {
										// setAnimationDownToUp(choosePhonePayType);
										for (int j = 0; j < hList.size(); j++) {
											if (i != j) {
												animationToUp(hList.get(j));
											}
										}
									}
									hList.get(i).findViewById(R.id.choosePhonePayType).setVisibility(View.GONE);
								}
							}
						} else if (curentposition == position) {
							curentposition = -1;
							checkBox.setBackgroundResource(R.drawable.uncheck);
							for (int i = 0; i < hList.size(); i++) {
								if (hList.get(i).findViewById(R.id.choosePhonePayType).getVisibility() == View.VISIBLE) {
									// setAnimationDownToUp(choosePhonePayType);
									for (int j = 0; j < hList.size(); j++) {
										if (i != j) {
											animationToUp(hList.get(j));
										}
									}
								}
								hList.get(i).findViewById(R.id.choosePhonePayType).setVisibility(View.GONE);
							}
							// choosePhonePayType.setVisibility(View.GONE);
						}
						// TODO
						if (curentposition == -1) {
							relative_pay_now.setVisibility(View.GONE);
						} else {
							relative_pay_now.setVisibility(View.VISIBLE);
						}

					}
				});

				zhifuname.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (curentposition != position) {
							if (curentposition > -1) {
								hList.get(curentposition).findViewById(R.id.checkbox).setBackgroundResource(R.drawable.uncheck);
							}
							curentposition = position;
							checkBox.setBackgroundResource(R.drawable.check);
							payType = list.get(position).moviePayType;
							if (list.get(position).couponBeanList != null && list.get(position).couponBeanList.size() != 0) {
								if (payType.equals("2")) {
									animationToDown(choosePhonePayType);
									for (int i = 0; i < hList.size(); i++) {
										if (i != position) {
											animationToDown(hList.get(i));
										}
									}

									choosePhonePayType.setVisibility(View.VISIBLE);
									phonePayType = PHONE_PAYTYPE_ALL;
									checkboxPayAll.setBackgroundResource(R.drawable.check);
									checkboxPayYouhui.setBackgroundResource(R.drawable.uncheck);
								} else {
									for (int i = 0; i < hList.size(); i++) {
										if (hList.get(i).findViewById(R.id.choosePhonePayType).getVisibility() == View.VISIBLE) {
											// setAnimationDownToUp(choosePhonePayType);
											for (int j = 0; j < hList.size(); j++) {
												if (i != j) {
													animationToUp(hList.get(j));
												}
											}
										}

										hList.get(i).findViewById(R.id.choosePhonePayType).setVisibility(View.GONE);
									}
								}
							} else {
								for (int i = 0; i < hList.size(); i++) {
									if (hList.get(i).findViewById(R.id.choosePhonePayType).getVisibility() == View.VISIBLE) {
										// setAnimationDownToUp(choosePhonePayType);
										for (int j = 0; j < hList.size(); j++) {
											if (i != j) {
												animationToUp(hList.get(j));
											}
										}
									}

									hList.get(i).findViewById(R.id.choosePhonePayType).setVisibility(View.GONE);
								}
							}
						} else if (curentposition == position) {
							curentposition = -1;
							checkBox.setBackgroundResource(R.drawable.uncheck);
							for (int i = 0; i < hList.size(); i++) {
								if (hList.get(i).findViewById(R.id.choosePhonePayType).getVisibility() == View.VISIBLE) {
									// setAnimationDownToUp(choosePhonePayType);
									for (int j = 0; j < hList.size(); j++) {
										if (i != j) {
											animationToUp(hList.get(j));
										}
									}
								}
								hList.get(i).findViewById(R.id.choosePhonePayType).setVisibility(View.GONE);
							}
							// choosePhonePayType.setVisibility(View.GONE);
						}
						// TODO
						if (curentposition == -1) {
							relative_pay_now.setVisibility(View.GONE);
						} else {
							relative_pay_now.setVisibility(View.VISIBLE);
						}

					}
				});

				checkboxPayAll.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						phonePayType = PHONE_PAYTYPE_ALL;
						checkboxPayAll.setBackgroundResource(R.drawable.check);
						checkboxPayYouhui.setBackgroundResource(R.drawable.uncheck);
					}
				});

				checkboxPayYouhui.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						phonePayType = PHONE_PAYTYPE_YOUHUI;
						checkboxPayAll.setBackgroundResource(R.drawable.uncheck);
						checkboxPayYouhui.setBackgroundResource(R.drawable.check);
					}
				});
			}

			list_orderinfo_pay_style.addView(view);
		}
	}

	// http://210.21.197.66:8090/szwxcsMovieInterface/MovieTicketServlet?userId=att&userPass=att&methodVit=GETPAYTYPECOUPON&c_id=8&channel=att

	public void onResume() {
		super.onResume();
		String result = UPOMP.getPayResult();
//		UPOMP.setPayResult();
		UPOMP.init();
//		System.out.println("支付状态：-----result----" + result);
		ArrayList<YinlianResultBean> list;
		if (result != null) {
			try {
				InputStream is = new ByteArrayInputStream(result.getBytes("UTF-8"));
				list = YinlianResultParse.getBean(is);
				if (list != null && list.size() != 0) {
					System.out.println("支付成功：--listsize---" + list.size());
					if (list.get(0).respCode.equals("0000")) {
						System.out.println("支付成功：==");
						Intent in = new Intent(BuyMovieTicketAct.this, MovieTicketSuccessAct.class);
						in.putExtra("lockMovieSeatsInfo", lockMovieSeatsInfo);
						in.putExtra("showTime", movie_ShowTime);
						in.putExtra("movieCinemaName", movie_Cinema_Name);
						in.putExtra("movieName", movie_Name);
						in.putExtra("sevcphone", lockMovieSeatsInfo.recyPhone);
						in.putExtra("cinemaRoom", cinemaRoom);
						in.putExtra("cinemaTime", cinemaTime);
						// in.putExtra("isBuySuccess", true);
						startActivity(in);
					} else {
						System.out.println("支付失败    " + list.get(0).respDesc);
					}
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("result ----   " + result);
			// 解析result结果
		}

		CmccDataStatistics.onStart(this);
	}

	public void onPause() {
		super.onPause();
		CmccDataStatistics.onStop(this);
	}
}
