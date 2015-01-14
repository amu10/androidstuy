package com.szcmcc.movie.activity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.GetOrderTypeBeanInfo;
import com.szcmcc.movie.bean.GetOrderTypeBeanInfo.GetOrderTypeBean;
import com.szcmcc.movie.bean.Order;
import com.szcmcc.movie.bean.YinlianResultBean;
import com.szcmcc.movie.cache.AppConstants;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.YinlianResultParse;
import com.szcmcc.movie.view.MessageDialog;
import com.szcmcc.movie.view.ToastAlone;
import com.unionpay.upomp.bypay.util.UPOMP;

/**
 * 16-购买兑换券
 * 
 */
public class BuyTicketExchangeAct extends BaseActivity {
	private Context mContext = BuyTicketExchangeAct.this;
	private ImageButton imBack;
	private ImageView jian, jia;
	private TextView buyCount;
	private ImageView pay = null;
	// private ListView listView;
	// private boolean isChecked = false;
	private int buyCountNum = 1;
	// private chooseAdapter adapter;
	private Order order = null;
	private String c_id = "", c_name = "", payPrice = "", payType = "2", companyId = "";
	private String payphoneNum = "";
	private EditText recvphone;
	private TextView name, payphone;
	private int currentItemCheck = -1;
	// private int currentItemCheck = 0;
	private int currentItemMessage = -1;
	private LinearLayout jiaLinear = null, jianLinear = null;
	/** 统一支付的商户号相关参数信息 */
	// private String spID = "", spPwd = "", spCode = "", merchantCode = "",
	// spType = "";
	private String timestamp = "", timeout = "", msg_id = "", orderNo = "", mobile = "", targetMobile = "", merchantName = "", merchandise = "";
	public static Activity buyTicketExchangeAct;
	String responseStr;
	private LinearLayout list_orderinfo_pay_style;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buyticketexchangeact);
		initTitleBar();
		buyTicketExchangeAct = this;
		init();
		onClick();
	}

	private void init() {

		Intent in = getIntent();
		if (in.getExtras() != null) {
			c_id = in.getExtras().getString("c_id");
			c_name = in.getExtras().getString("c_name");
			payphoneNum = in.getExtras().getString("payphone");
			companyId = in.getExtras().getString("companyId");
			payPrice = (String) in.getSerializableExtra("price");

		}
		imBack = (ImageButton) findViewById(R.id.imBack);
		jiaLinear = (LinearLayout) findViewById(R.id.jiaLinear);
		jianLinear = (LinearLayout) findViewById(R.id.jianLinear);
		payphone = (TextView) findViewById(R.id.payphone);
		recvphone = (EditText) findViewById(R.id.recvphone);
		name = (TextView) findViewById(R.id.c_name);
		buyCount = (TextView) findViewById(R.id.buyCount);
		pay = (ImageView) findViewById(R.id.payOk);
		jian = (ImageView) findViewById(R.id.jian);
		jia = (ImageView) findViewById(R.id.jia);
		jia.setBackgroundResource(R.drawable.jia2);
		list_orderinfo_pay_style = (LinearLayout) findViewById(R.id.list_orderinfo_pay_style);

		name.setText(c_name);
		buyCount.setText("1");
		payphone.setText(payphoneNum);
		recvphone.setText(payphoneNum);
		new GetOrderTypeBeanInfoTask(this).execute(c_id);
	}

	private void onClick() {
		imBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		pay.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new GetMovieInfoTask(BuyTicketExchangeAct.this, "正在生成订单...").execute();
			}
		});

		jia.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					if (buyCountNum >= 6) {

					} else {
						if (buyCountNum == 5) {
							jia.setBackgroundResource(R.drawable.jia2);
						}
						buyCountNum++;
						buyCount.setText(buyCountNum + "");
						((TextView) (list_orderinfo_pay_style.getChildAt(0).findViewById(R.id.sumMoney))).setText((buyCountNum * (int) Float
								.parseFloat(payPrice)) + "");
						((TextView) (list_orderinfo_pay_style.getChildAt(0).findViewById(R.id.count))).setText(buyCountNum+"");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
		jiaLinear.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					if (buyCountNum >= 6) {

					} else {
						if (buyCountNum == 5) {
							jia.setBackgroundResource(R.drawable.jia2);
						}
						buyCountNum++;
						buyCount.setText(buyCountNum + "");
						((TextView) (list_orderinfo_pay_style.getChildAt(0).findViewById(R.id.sumMoney))).setText((buyCountNum * (int) Float
								.parseFloat(payPrice)) + "");
						((TextView) (list_orderinfo_pay_style.getChildAt(0).findViewById(R.id.count))).setText(buyCountNum+"");
						// adapter.notifyDataSetChanged();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		});
		jian.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					if (buyCountNum > 1) {
						buyCountNum--;
						buyCount.setText(buyCountNum + "");
						// adapter.notifyDataSetChanged();
						((TextView) (list_orderinfo_pay_style.getChildAt(0).findViewById(R.id.sumMoney))).setText((buyCountNum * (int) Float
								.parseFloat(payPrice)) + "");
						((TextView) (list_orderinfo_pay_style.getChildAt(0).findViewById(R.id.count))).setText(buyCountNum+"");
						jia.setBackgroundResource(R.drawable.jia2);
					} else {
						ToastAlone.makeText(BuyTicketExchangeAct.this, R.string.ticket_exchange, Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		jianLinear.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					if (buyCountNum > 1) {
						buyCountNum--;
						buyCount.setText(buyCountNum + "");
						// adapter.notifyDataSetChanged();
						((TextView) (list_orderinfo_pay_style.getChildAt(0).findViewById(R.id.sumMoney))).setText((buyCountNum * (int) Float.parseFloat(payPrice)) + "");
						((TextView) (list_orderinfo_pay_style.getChildAt(0).findViewById(R.id.count))).setText(buyCountNum+"");
						jia.setBackgroundResource(R.drawable.jia2);
					} else {
						ToastAlone.makeText(BuyTicketExchangeAct.this, R.string.ticket_exchange, Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//	class chooseAdapter extends BaseAdapter {
//
//		protected static final String TAG = "chooseAdapter";
//		private ArrayList<MovieCinema.MovieCinemaInner> list = new ArrayList<MovieCinema.MovieCinemaInner>();
//
//		public chooseAdapter(ArrayList<MovieCinema.MovieCinemaInner> list) {
//			this.list.clear();
//			if (list != null && list.size() != 0) {
//				this.list.add(list.get(0));
//				// MovieCinemaInner movieCinemaInner = new MovieCinemaInner();
//				// movieCinemaInner.context = list.get(0).context;
//				// movieCinemaInner.payTpyeName = "银联支付";
//				// movieCinemaInner.payTypeId = "8";
//				// movieCinemaInner.price = list.get(0).price;
//				// movieCinemaInner.unit = list.get(0).unit;
//				// // this.list = list;
//				// this.list.add(movieCinemaInner);
//			}
//
//		}
//
//		private ArrayList<Holder> hList = new ArrayList<Holder>();
//		private ArrayList<Holder> mesList = new ArrayList<Holder>();
//
//		@Override
//		public int getCount() {
//			// if (list.size() > 0) {
//			// return 2;
//			// }
//			return list.size();
//			// return 0;
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return position;
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return position;
//		}
//
//		@Override
//		public View getView(final int position, View convertView, ViewGroup parent) {
//			final Holder holder;
//			if (convertView == null) {
//				holder = new Holder();
//				convertView = LayoutInflater.from(mContext).inflate(R.layout.buytickexchange_choose_list, parent, false);
//				holder.checkBox = (ImageView) convertView.findViewById(R.id.checkbox);
//				holder.sumMoneyUnit = (TextView) convertView.findViewById(R.id.sumMoneyUnit);
//				holder.unit = (TextView) convertView.findViewById(R.id.unit);
//				holder.zhifuname = (TextView) convertView.findViewById(R.id.zhifuname);
//				holder.help = (LinearLayout) convertView.findViewById(R.id.helpLinear);
//				holder.price = (TextView) convertView.findViewById(R.id.priceMoney);
//				holder.sumMoney = (TextView) convertView.findViewById(R.id.sumMoney);
//				holder.count = (TextView) convertView.findViewById(R.id.count);
//				holder.helpMessage = (TextView) convertView.findViewById(R.id.helpMessage);
//				convertView.setTag(holder);
//
//				if (!hList.contains(holder)) {
//					hList.add(holder);
//				}
//				if (!mesList.contains(holder)) {
//					mesList.add(holder);
//				}
//			} else {
//
//				holder = (Holder) convertView.getTag();
//			}
//			if (currentItemCheck == position) {
//				holder.checkBox.setBackgroundResource(R.drawable.check);
//			} else {
//				holder.checkBox.setBackgroundResource(R.drawable.uncheck);
//			}
//
//			if (currentItemMessage == position) {
//				holder.helpMessage.setVisibility(View.VISIBLE);
//			} else {
//				holder.helpMessage.setVisibility(View.GONE);
//			}
//
//			// TODO 屏蔽选择框
//			// holder.checkBox.setVisibility(View.GONE);
//
//			holder.count.setText(buyCount.getText().toString());
//			holder.price.setText(Float.parseFloat(list.get(position).price) + "");
//			holder.unit.setText(list.get(position).unit);
//			holder.sumMoneyUnit.setText(list.get(position).unit);
//			holder.zhifuname.setText(list.get(position).payTpyeName);
//			holder.sumMoney.setText((Float.parseFloat(buyCount.getText().toString()) * Float.parseFloat(list.get(position).price)) + "");
//			holder.helpMessage.setText(list.get(position).context);
//			holder.help.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					if (currentItemMessage != position) {
//						if (currentItemMessage > -1) {
//							mesList.get(currentItemMessage).helpMessage.setVisibility(View.GONE);
//						}
//						currentItemMessage = position;
//						holder.helpMessage.setVisibility(View.VISIBLE);
//						notifyDataSetChanged();
//					} else if (currentItemMessage == position) {
//						currentItemMessage = -1;
//						holder.helpMessage.setVisibility(View.GONE);
//						notifyDataSetChanged();
//					}
//					// switch (holder.helpMessage.getVisibility()) {
//					// case View.GONE:
//					// holder.helpMessage.setVisibility(View.VISIBLE);
//					// break;
//					// case View.VISIBLE:
//					// holder.helpMessage.setVisibility(View.GONE);
//					// break;
//					// }
//				}
//			});
//			holder.checkBox.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//
//					// if(position == 0){//这里为了屏蔽M值 所有加入此判断
//					if (currentItemCheck != position) {
//						if (currentItemCheck > -1) {
//							hList.get(currentItemCheck).checkBox.setBackgroundResource(R.drawable.uncheck);
//						}
//						currentItemCheck = position;
//						holder.checkBox.setBackgroundResource(R.drawable.check);
//						payType = list.get(currentItemCheck).payTypeId;
//						System.out.println("payType--------box------   " + payType);
//						notifyDataSetChanged();
//					} else if (currentItemCheck == position) {
//						currentItemCheck = -1;
//						holder.checkBox.setBackgroundResource(R.drawable.uncheck);
//						notifyDataSetChanged();
//					}
//
//					// TODO 屏蔽未选择 选择框隐藏支付按钮功能
//					if (currentItemCheck == -1) {
//						pay.setVisibility(View.GONE);
//					} else {
//						pay.setVisibility(View.VISIBLE);
//					}
//				}
//			});
//
//			holder.zhifuname.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//
//					// if(position == 0){//这里为了屏蔽M值 所有加入此判断
//					if (currentItemCheck != position) {
//						if (currentItemCheck > -1) {
//							hList.get(currentItemCheck).checkBox.setBackgroundResource(R.drawable.uncheck);
//						}
//						currentItemCheck = position;
//						holder.checkBox.setBackgroundResource(R.drawable.check);
//						payType = list.get(currentItemCheck).payTypeId;
//						System.out.println("payType--------box------   " + payType);
//						notifyDataSetChanged();
//					} else if (currentItemCheck == position) {
//						currentItemCheck = -1;
//						holder.checkBox.setBackgroundResource(R.drawable.uncheck);
//						notifyDataSetChanged();
//					}
//
//					// TODO 屏蔽未选择 选择框隐藏支付按钮功能
//					if (currentItemCheck == -1) {
//						pay.setVisibility(View.GONE);
//					} else {
//						pay.setVisibility(View.VISIBLE);
//					}
//				}
//			});
//			return convertView;
//		}
//
//		public class Holder {
//			ImageView checkBox = null;
//			TextView zhifuname = null;
//			LinearLayout help = null;
//			TextView price = null;
//			TextView sumMoney = null;
//			TextView count = null;
//			TextView helpMessage = null;
//			TextView unit = null;
//			TextView sumMoneyUnit = null;
//		}
//	}

	// private void showDialog(final String payType) {
	//
	// System.out.println("dialog---------");
	// LayoutInflater layout = (LayoutInflater)
	// this.getSystemService(LAYOUT_INFLATER_SERVICE);
	// View v = layout.inflate(R.layout.buy_dialog,
	// (ViewGroup) this.findViewById(R.id.dialog_layout));
	// // final AlertDialog builder = new
	// // AlertDialog.Builder(BuyTicketExchangeAct.this).setView(v).create();
	// final Dialog builder = new Dialog(BuyTicketExchangeAct.this,
	// R.style.dialog);
	// builder.setContentView(v);
	//
	// TextView pay_message = (TextView) v.findViewById(R.id.pay_message);
	// TextView pay_no = (TextView) v.findViewById(R.id.pay_no);
	// TextView pay_ok = (TextView) v.findViewById(R.id.pay_ok);
	// try {// mlist.get(0).payTpyeName==请进入下一步选择手机支付或赠券支付完成购票
	// pay_message.setText("您购买 “"
	// + c_name
	// + "” 兑换券共"
	// + buyCountNum
	// + "张，总价"
	// + (Float.parseFloat(buyCount.getText().toString()) *
	// Float.parseFloat(mlist
	// .get(0).price)) + "" + mlist.get(0).unit + ","
	// + "请进入下一步选择手机支付或赠券支付完成购票" + "。兑换券将为您保留15分钟，请您尽快支付。");
	// } catch (NumberFormatException e) {
	// e.printStackTrace();
	// }
	// pay_no.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// builder.dismiss();
	// }
	// });
	// pay_ok.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// if (payType.equals("8")) {
	// if (!isMoviesLoadAll) {
	// new GetMovieInfoYinLianTask(BuyTicketExchangeAct.this, "正在生成订单...")
	// .execute();
	// builder.dismiss();
	// }
	// } else {
	// if (!isMoviesLoadAll) {
	// new GetMovieInfoTask(BuyTicketExchangeAct.this, "正在生成订单...").execute();
	// builder.dismiss();
	// }
	// }
	// }
	// });
	// builder.show();
	// }

	class GetMovieInfoTask extends MovieAsyncTask<String, String, Order> {
		public String exception;
		private String spID = "", spPwd = "", spCode = "", merchantCode = "";

		public GetMovieInfoTask(Activity activity, String loadingText) {
			super(activity, null, true, true, true, loadingText);
		}

		@Override
		protected Order doInBackground(String... params) {

			try {

				if (!c_id.equals("")) {
					order = lib.generateOrder(c_id, payphone.getText().toString(), recvphone.getText().toString(), buyCount.getText().toString(),
							payType, "0");

					boolean flag = true;
					for (int i = 0; i < AppConstants.movieCinema.mList.size(); i++) {
						if (!flag) {
							break;
						}
						for (int j = 0; j < AppConstants.movieCinema.mList.get(i).cinemas.size(); j++) {
							if (AppConstants.movieCinema.mList.get(i).cinemas.get(j).c_id.equals(c_id)) {
								spID = AppConstants.movieCinema.mList.get(i).cinemas.get(j).coupon.spid;
								spPwd = AppConstants.movieCinema.mList.get(i).cinemas.get(j).coupon.sppwd;
								spCode = AppConstants.movieCinema.mList.get(i).cinemas.get(j).coupon.spcode;
								merchantCode = AppConstants.movieCinema.mList.get(i).cinemas.get(j).coupon.merchantCode;
								flag = false;
								System.out.println("spi--------------" + spID);
								break;
							}
						}
					}
				}
			} catch (HttpException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (IOException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (JSONException e) {
				exception = getResources().getString(R.string.exception_json);
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return order;
		}

		@Override
		protected void onPostExecute(Order result) {
			super.onPostExecute(result);
			if (result != null && result.isSuccess()) {
				// new
				// OrderPayTask(BuyTicketExchangeAct.this,"正在支付订单...").execute();
				Intent in = new Intent(BuyTicketExchangeAct.this, BuyTicketWebActivity.class);
				in.putExtra("c_id", c_id);
				in.putExtra("orderNo", result.orderInner.orderid);
				in.putExtra("companyId", companyId);
				in.putExtra("Amount", (Float.parseFloat(buyCount.getText().toString()) * Float.parseFloat(payPrice)) + "");
				in.putExtra("mobile", payphone.getText().toString());
				in.putExtra("targetMobile", recvphone.getText().toString());
				in.putExtra("merchantName", c_name);
				in.putExtra("merchandise", c_name + "兑换券" + buyCount.getText().toString() + "张");
				in.putExtra("expired_time", result.orderInner.expired_time);
				in.putExtra("count", result.orderInner.count);
				in.putExtra("isDuihuan", true);
				in.putExtra("spID", spID);
				in.putExtra("spPwd", spPwd);
				in.putExtra("spCode", spCode);
				in.putExtra("merchantCode", merchantCode);
				startActivity(in);
			} else {
//				Toast.makeText(BuyTicketExchangeAct.this, "生成订单失败！", Toast.LENGTH_SHORT).show();
				MessageDialog.getInstance().setData(BuyTicketExchangeAct.this, R.string.data_failed_to_generate_orders, false);
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	// private String getPackageNameString(){
	// System.out.println("getPackageNameString()          "+getPackageName());
	// return this.getPackageName();
	// }

	// private void setSp() {
	// Date date = new Date(System.currentTimeMillis());
	// SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	// String time = format.format(date);
	//
	// timestamp = time;
	// timeout = (Long.parseLong(time) + (1000 * 3600 * 24)) + "";
	// String timeRandom = Math.round(Math.floor(Math.random() * (1 + 9999 -
	// 1000) + 1000)) + "";
	// msg_id = time + timeRandom;
	//
	// }
	private class MyURLSpan extends ClickableSpan {
		private String mUrl = "";

		MyURLSpan(String url) {
			mUrl = url;
		}

		@Override
		public void onClick(View widget) {
			try {
				System.out.println("mUrl----  "+mUrl);
				if(mUrl.equals("")||mUrl == null){
					return;
				}
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(mUrl));
				startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setPayData(final ArrayList<GetOrderTypeBean> list) {
		final ArrayList<View> hList = new ArrayList<View>();
		for (int i = 0; i < list.size(); i++) {
			if (!list.get(i).moviePayType.equals("2")) {
				continue;
			}
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
//			final LinearLayout choosePhonePayType = (LinearLayout) view.findViewById(R.id.choosePhonePayType);
			// final ImageView checkboxPayAll = (ImageView)
			// view.findViewById(R.id.checkboxPayAll);
			// final ImageView checkboxPayYouhui = (ImageView)
			// view.findViewById(R.id.checkboxPayYouhui);
			final TextView helpMessage = (TextView) view.findViewById(R.id.helpMessage);
			if (!hList.contains(view)) {
				hList.add(view);
			}

			if (list != null && list.size() > 0) {
				// if(!moviePayType.get(position).payTpyeName.equals("手机支付")){//由于当钱仅支持手机支付，所以屏蔽了其他的支付方式
				// viewHoder.checkBox_payStyle.setClickable(false);
				// }
				price.setText((int) Float.parseFloat(payPrice) + "");
				zhifuname.setText(list.get(position).name);
				count.setText(buyCountNum + "");
				unit.setText("元");
				sumMoney.setText((buyCountNum * (int) Float.parseFloat(payPrice)) + "");

				// if (!list.get(position).moviePayType.equals("2")) {
				// helpMessage.setText(list.get(position).desc);
				// } else {
				if (getOrderTypeBeanInfo != null) {
					String htmlLinkText1[] = new String[getOrderTypeBeanInfo.linkUrl.split(",").length];
					String mes = list.get(position).desc;
					String htmlLinkText = "";
					System.out.println("getOrderTypeBeanInfo.freeRegistUrl.-----"+getOrderTypeBeanInfo.freeRegistUrl);
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
//							style.setSpan(myURLSpan, sp.getSpanStart(url), sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						}
//						helpMessage.setText(style);
					}
				}
				// }
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

				if (currentItemCheck == -1) {
					checkBox.setBackgroundResource(R.drawable.uncheck);

					pay.setVisibility(View.GONE);
				} else {

					pay.setVisibility(View.VISIBLE);
				}

				checkBox.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (currentItemCheck != position) {
							if (currentItemCheck > -1) {
								hList.get(currentItemCheck).findViewById(R.id.checkbox).setBackgroundResource(R.drawable.uncheck);
							}
							// for(int i =
							// 0;i<list_orderinfo_pay_style.getChildCount();i++){
							// ((ImageView)(list_orderinfo_pay_style.getChildAt(i).findViewById(R.id.checkBox))).setBackgroundResource(R.drawable.uncheck);
							// }
							currentItemCheck = position;
							checkBox.setBackgroundResource(R.drawable.check);
							payType = list.get(position).moviePayType;
							System.out.println("payType-----------" + payType);
//							if (list.get(position).couponBeanList != null && list.get(position).couponBeanList.size() != 0) {
//								if (payType.equals("2")) {
//									choosePhonePayType.setVisibility(View.VISIBLE);
//								} else {
//									for (int i = 0; i < hList.size(); i++) {
//										hList.get(i).findViewById(R.id.choosePhonePayType).setVisibility(View.GONE);
//									}
//								}
//							} else {
//								for (int i = 0; i < hList.size(); i++) {
//									hList.get(i).findViewById(R.id.choosePhonePayType).setVisibility(View.GONE);
//								}
//							}
						} else if (currentItemCheck == position) {
							currentItemCheck = -1;
							checkBox.setBackgroundResource(R.drawable.uncheck);
							for (int i = 0; i < hList.size(); i++) {
								hList.get(i).findViewById(R.id.choosePhonePayType).setVisibility(View.GONE);
							}
							// choosePhonePayType.setVisibility(View.GONE);
						}
						// TODO
						if (currentItemCheck == -1) {
							pay.setVisibility(View.GONE);
						} else {
							pay.setVisibility(View.VISIBLE);
						}

					}
				});

				zhifuname.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (currentItemCheck != position) {
							if (currentItemCheck > -1) {
								hList.get(currentItemCheck).findViewById(R.id.checkbox).setBackgroundResource(R.drawable.uncheck);
							}
							currentItemCheck = position;
							checkBox.setBackgroundResource(R.drawable.check);
							payType = list.get(position).moviePayType;
//							if (list.get(position).couponBeanList != null && list.get(position).couponBeanList.size() != 0) {
//								if (payType.equals("2")) {
//
//									choosePhonePayType.setVisibility(View.VISIBLE);
//								} else {
//									for (int i = 0; i < hList.size(); i++) {
//										hList.get(i).findViewById(R.id.choosePhonePayType).setVisibility(View.GONE);
//									}
//								}
//							} else {
//								for (int i = 0; i < hList.size(); i++) {
//									hList.get(i).findViewById(R.id.choosePhonePayType).setVisibility(View.GONE);
//								}
//							}
						} else if (currentItemCheck == position) {
							currentItemCheck = -1;
							checkBox.setBackgroundResource(R.drawable.uncheck);
							for (int i = 0; i < hList.size(); i++) {
								hList.get(i).findViewById(R.id.choosePhonePayType).setVisibility(View.GONE);
							}
							// choosePhonePayType.setVisibility(View.GONE);
						}
						// TODO
						if (currentItemCheck == -1) {
							pay.setVisibility(View.GONE);
						} else {
							pay.setVisibility(View.VISIBLE);
						}
					}
				});
			}

			list_orderinfo_pay_style.addView(view);
			break;
		}
		if(list_orderinfo_pay_style.getChildCount() == 0) {
			MessageDialog.getInstance().setData(BuyTicketExchangeAct.this, R.string.data_failed_to_get_paytype, false);
		}
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
				getOrderTypeBeanInfo = lib.getMovieGetOrderType(c_id, "att", "");
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
						setPayData(result.getOrderTypeBeanList);
					}
				}else {
					MessageDialog.getInstance().setData(BuyTicketExchangeAct.this, result.message, false);
				}
			} else {
//				Toast.makeText(BuyTicketExchangeAct.this, "获取支付方式失败", Toast.LENGTH_SHORT).show();
				MessageDialog.getInstance().setData(BuyTicketExchangeAct.this, R.string.data_failed_to_get_paytype, false);
			}
		}
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
			String msg_id, String orderNo, String amount, String mobile, String targetMobile, String merchantName, String merchandise) {

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
				+ "</amount><orderDesc>empty</orderDesc><addr>empty</addr><backUrl>http://wap.szicity.com</backUrl><payType>8</payType><spPay></spPay><outMerchantCode>empty</outMerchantCode><period>10</period><periodUnit>4</periodUnit><reqsource>1</reqsource></datas></interfaceData>";
		return xml;
	}

	@Override
	public void onResume() {
		super.onResume();
		String result = UPOMP.getPayResult();
		ArrayList<YinlianResultBean> list;
		if (result != null) {
			try {
				InputStream is = new ByteArrayInputStream(result.getBytes("UTF-8"));
				list = YinlianResultParse.getBean(is);
				if (list != null && list.size() != 0) {
					if (list.get(0).respCode.equals("0000")) {
						Intent in = new Intent(BuyTicketExchangeAct.this, TicketExchangeSuccessAct.class);
						in.putExtra("c_name", merchantName);
						in.putExtra("sevcphone", targetMobile);
						in.putExtra("expired_time", order.orderInner.expired_time);
						in.putExtra("count", order.orderInner.count);
						in.putExtra("SendShort", true);
						in.putExtra("isBuySuccess", true);
					} else {
						System.out.println("支付失败    " + list.get(0).respDesc);
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("result ----   " + result);
			// 解析result结果
		}
		CmccDataStatistics.onStart(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		CmccDataStatistics.onStop(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		app.getAsyncImageLoader().recycleBitmaps();
	}
}
