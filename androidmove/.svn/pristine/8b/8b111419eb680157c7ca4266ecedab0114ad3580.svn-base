package com.szcmcc.movie.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.szcmcc.movie.R;
import com.szcmcc.movie.activity.BuyMovieTicketAct.GetOrderInfoTask;
import com.szcmcc.movie.bean.GetOrderTypeBeanInfo.GetOrderTypeBean.CouponBean;
import com.szcmcc.movie.bean.LockOrDebLockMovieSeatsInfo;
import com.szcmcc.movie.bean.OrderBySeat;
import com.szcmcc.movie.bean.ValidateCodeBeanInfo;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.storage.SharedPreferencesUtil;
import com.szcmcc.movie.view.ToastAlone;

public class YanzhengActivity extends BaseActivity {

	private ListView listview;
	private TextView title, pay_ok;
	private CouponBean bean;
	private int size = 0;
	YanzhengAdapter adapter;
	SharedPreferencesUtil spUtil;
	private LockOrDebLockMovieSeatsInfo lockMovieSeatsInfo;
	private String movie_seat_seatCode,movie_seat_seatRow,movie_seat_seatCol;
	private String movie_ShowTime, movie_Name, movie_Cinema_Name, movie_seatPaytype, moviecinema_id;
	private String cinemaRoom = "", cinemaTime = "",companyId = "",payPrice = "";
	/** 根据优惠券计算的单价*/
	private String jisuanPrice = "";
	/** 计算后应该支付的价格*/
	private String Amount = "";
	private String liushuiNum = "";
	/** 优惠券填写列表 editList.size()即可获得优惠券数量*/
	ArrayList<String> editList = new ArrayList<String>();
	private String spID = "", spPwd = "", spCode = "", merchantCode = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yanzheng);
		spUtil = SharedPreferencesUtil.getInstance(this);
		findView();
		setData();
		setListener();
	}

	private void findView() {
		listview = (ListView) findViewById(R.id.listview);
		pay_ok = (TextView) findViewById(R.id.pay_ok);
		title = (TextView) findViewById(R.id.title);

	}
	private void setData() {
		Intent in = getIntent();
		if (in.getExtras() != null) {
			bean = (CouponBean) in.getExtras().getSerializable("CouponBean");
			size = in.getExtras().getInt("size");
			lockMovieSeatsInfo = (LockOrDebLockMovieSeatsInfo)in.getExtras().getSerializable("lockMovieSeatsInfo");
			movie_seat_seatCode = in.getExtras().getString("movie_seat_seatCode");
			movie_seat_seatRow = in.getExtras().getString("movie_seat_seatRow");
			movie_seat_seatCol = in.getExtras().getString("movie_seat_seatCol");
			movie_ShowTime = in.getStringExtra("showTime");
			movie_Cinema_Name = in.getStringExtra("merchantName");
			movie_Name = in.getStringExtra("movieName");
			moviecinema_id = in.getStringExtra("moviecinema_id");
			companyId = in.getStringExtra("companyId");
			cinemaRoom = in.getStringExtra("cinemaRoom");
			cinemaTime = in.getStringExtra("cinemaTime");
			payPrice = in.getStringExtra("payPrice");
			spID = in.getExtras().getString("spID");
			spPwd = in.getExtras().getString("spPwd");
			spCode = in.getExtras().getString("spCode");
			merchantCode = in.getExtras().getString("merchantCode");
			if (bean.type.equals("1")) {
				title.setText("抵扣券码录入");
			} else {
				title.setText(bean.name);
			}
			adapter = new YanzhengAdapter(this);
			listview.setAdapter(adapter);
			adapter.setData(size);
		}

	}

	private void setListener() {
		pay_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				editList.clear();
				if (!spUtil.getUserName().equals("")) {
					
					for (int i = 0; i < adapter.getCount(); i++) {
						if (!adapter.getEditText(i).getText().toString().equals("")) {
							editList.add(adapter.getEditText(i).getText().toString());
						}
					}
					if (editList.size() == 0) {
						ToastAlone.showToast(YanzhengActivity.this, "还未填写验证码", Toast.LENGTH_SHORT).show();
						return;
					} else {	
						getValidateCodeBeanInfoTask = new GetValidateCodeBeanInfoTask(YanzhengActivity.this);
						getValidateCodeBeanInfoTask.execute(spUtil.getUserName(), setCode(editList),lockMovieSeatsInfo.showCode,bean.name);
					}
				} else {
					Intent intent = new Intent(YanzhengActivity.this, UserLoginAct.class);
					startActivity(intent);
				}

			}
		});
	}
	
	Handler hander = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case 0:
				GetOrderInfoTask orderInfoTask = new GetOrderInfoTask(YanzhengActivity.this, "正在生成订单...");
				orderInfoTask.execute(lockMovieSeatsInfo.c_id, lockMovieSeatsInfo.recyPhone, lockMovieSeatsInfo.recyPhone,
						lockMovieSeatsInfo.ticketCount, "2", "1", lockMovieSeatsInfo.lockSerialNo,
						lockMovieSeatsInfo.showCode, movie_seat_seatCode, movie_seat_seatRow, movie_seat_seatCol,bean.name,editList.size()+"",Amount,liushuiNum);
				break;
			}
		}
		
	};

	String setCode(ArrayList<String> code) {
		StringBuffer sb = new StringBuffer();
		if (code != null && code.size() > 0) {
			for (int i = 0; i < code.size(); i++) {
				if (code.size() == 1) {
					sb.append(code.get(i));
				} else if (i == code.size() - 1) {
					sb.append(code.get(i));
				} else {
					sb.append(code.get(i) + ",");
				}
			}
		}
		return sb.toString();
	}

	private class YanzhengAdapter extends BaseAdapter {

		private Context mContext = null;
		private ArrayList<Holder> holderList = new ArrayList<Holder>();

		public YanzhengAdapter(Context mContext) {
			this.mContext = mContext;
		}

		int size = 0;

		@Override
		public int getCount() {
			return size;
		}

		public void setData(int size) {
			this.size = size;
			notifyDataSetChanged();
		}

		public EditText getEditText(int position) {
			return holderList.get(position).number;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final Holder holder;
			if (convertView == null) {
				holder = new Holder();
				convertView = LayoutInflater.from(mContext).inflate(R.layout.pay_youhui_chlid, null);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.number = (EditText) convertView.findViewById(R.id.number);
				convertView.setTag(holder);
				if (!holderList.contains(holder)) {
					holderList.add(holder);
				}
			} else {
				holder = (Holder) convertView.getTag();
			}

			holder.name.setText("第" + (position + 1) + "张");

			return convertView;
		}

		class Holder {
			TextView name = null;
			EditText number = null;
		}
	}

	GetValidateCodeBeanInfoTask getValidateCodeBeanInfoTask = null;

	class GetValidateCodeBeanInfoTask extends MovieAsyncTask<String, String, ValidateCodeBeanInfo> {
		public String exception;
		ValidateCodeBeanInfo validateCodeBeanInfo;

		public GetValidateCodeBeanInfoTask(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected ValidateCodeBeanInfo doInBackground(String... params) {

			try {
				validateCodeBeanInfo = lib.getValidateCodeBeanInfo(params[0], params[1], params[2],params[3], "att");
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return validateCodeBeanInfo;
		}

		@Override
		protected void onPostExecute(ValidateCodeBeanInfo result) {
			super.onPostExecute(result);
			if (result != null) {
				if(result.code.equals("1")){
					if(bean.type.equals("1")){
						jisuanPrice = bean.money;
					}else if(bean.type.equals("2")){
						jisuanPrice = (Float.parseFloat(payPrice)-Float.parseFloat(bean.money))+"";
					}else if(bean.type.equals("3")){
						jisuanPrice = (Float.parseFloat(payPrice)*(Float.parseFloat(bean.discount)/10))+"";
					}else{
						Toast.makeText(YanzhengActivity.this, "验证失败，请重新验证", Toast.LENGTH_LONG).show();
						return;
					}
					Amount = ((Float.parseFloat(jisuanPrice)*editList.size())+((size-editList.size())*Float.parseFloat(payPrice)))+"";
					System.out.println("Amount-----"+jisuanPrice+"-------"+Amount);
					liushuiNum = result.lockNum;
					Message m = new Message();
					m.what = 0;
					m.obj = result.lockNum;
					hander.sendMessage(m);
				}else{
					ToastAlone.showToast(YanzhengActivity.this, result.message, Toast.LENGTH_LONG).show();
				}
			} else {
				ToastAlone.showToast(YanzhengActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
			}
		}
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

					orderBySeat = (OrderBySeat) MovieLib.getInstance(YanzhengActivity.this).generateOrder(params[0], params[1], params[2], params[3], params[4],
							params[5], params[6], params[7], params[8], params[9], params[10],params[11],params[12],params[13],params[14],"att");
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
				}
				return orderBySeat;
			}

			@Override
			protected void onPostExecute(OrderBySeat result) {
//				if (!TextUtils.isEmpty(exception)) {
//					Toast.makeText(mContext, exception, Toast.LENGTH_SHORT).show();
//				}
				// 处理订单信息。
				if (result != null && result.isSuccess()) {
					if (result.orderBySeatInner != null) {
						// getOrderLoadingDialog.close();
//						orderBySeat = result;
						Intent in = new Intent(YanzhengActivity.this, BuyTicketWebActivity.class);
						in.putExtra("c_id", moviecinema_id);
						in.putExtra("orderNo", result.orderBySeatInner.orderid);
						in.putExtra("companyId", companyId);
						in.putExtra("Amount", Amount);
						in.putExtra("mobile", spUtil.getUserName());
						in.putExtra("targetMobile", spUtil.getUserName());
						in.putExtra("merchantName", movie_Cinema_Name);
						in.putExtra("merchandise", movie_Cinema_Name + "座位票" + lockMovieSeatsInfo.lockSeats.size() + "张");
						in.putExtra("lockMovieSeatsInfo", lockMovieSeatsInfo);
						in.putExtra("showTime", movie_ShowTime);// 放映时间
						in.putExtra("movieName", movie_Name);
						in.putExtra("cinemaRoom", cinemaRoom);
						in.putExtra("cinemaTime", cinemaTime);
						in.putExtra("isDuihuan", false);
						in.putExtra("spID", spID);
						in.putExtra("spPwd", spPwd);
						in.putExtra("spCode", spCode);
						in.putExtra("merchantCode", merchantCode);
						startActivity(in);
					}
				} else {
					// getOrderLoadingDialog.close();
					Toast.makeText(YanzhengActivity.this, "生成订单失败！", Toast.LENGTH_SHORT).show();
				}
				super.onPostExecute(result);
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}

		}
}
