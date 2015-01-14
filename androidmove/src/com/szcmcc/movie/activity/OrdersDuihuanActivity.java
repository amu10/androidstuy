package com.szcmcc.movie.activity;

import java.io.IOException;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.ReSendOrderBean;
import com.szcmcc.movie.bean.SeatOrder;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.view.MarqueeTextView;

public class OrdersDuihuanActivity extends Activity {

	private ImageButton imBack;
	private TextView order_number, price, count, all_price, date, end_date, status, sendButton;
	private SeatOrder seatOrder;
	private MarqueeTextView cinema_name;
	protected MovieLib lib;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orders_duihuan);

		findView();
		init();
		setListener();
	}

	private void findView() {
		lib = MovieLib.getInstance(this);
		cinema_name = (MarqueeTextView) findViewById(R.id.cinema_name);
		order_number = (TextView) findViewById(R.id.order_number);
		price = (TextView) findViewById(R.id.price);
		count = (TextView) findViewById(R.id.count);
		all_price = (TextView) findViewById(R.id.all_price);
		date = (TextView) findViewById(R.id.date);
		end_date = (TextView) findViewById(R.id.end_date);
		status = (TextView) findViewById(R.id.status);
		sendButton = (TextView) findViewById(R.id.sendButton);
		imBack = (ImageButton) findViewById(R.id.imBack);
		sendButton.setVisibility(View.GONE);
	}

	private void init() {
		try {
			seatOrder = (SeatOrder) getIntent().getExtras().getSerializable("SeatOrder");
			cinema_name.setText(seatOrder.cinemaName);
			order_number.setText(seatOrder.orderId);
			price.setText(seatOrder.price);
			count.setText(seatOrder.count);
			all_price.setText(seatOrder.amount);
			date.setText(seatOrder.date);
			end_date.setText(seatOrder.validityDate);
			switch (Integer.parseInt(seatOrder.status)) {
			case 1:
				status.setText("等待付款");
				break;
			case 2:
				sendButton.setVisibility(View.VISIBLE);
				status.setText("支付成功");
				break;
			case 3:
				status.setText("支付失败");
				break;
			case 4:
				sendButton.setVisibility(View.VISIBLE);
				status.setText("支付成功，发送成功");
				break;
			case 5:
				sendButton.setVisibility(View.VISIBLE);
				status.setText("支付成功，发送失败");
				break;
			case 6:
				sendButton.setVisibility(View.VISIBLE);
				status.setText("支付成功，短信发送，等待确认");
				break;
			case 7:
				sendButton.setVisibility(View.VISIBLE);
				status.setText("支付成功，正在发送");
				break;
			case 8:
				status.setText("已使用");
				break;
			default:
				break;
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	private void setListener() {
		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new ReSendOrderMovieTask(OrdersDuihuanActivity.this)
						.execute(seatOrder.orderId, "0");
			}
		});
		imBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
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
			} catch(Exception e){
				e.printStackTrace();
			}
			return reSendOrderBean;
		}

		@Override
		protected void onPostExecute(ReSendOrderBean result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.result.code.equals("1")) {
					Toast.makeText(OrdersDuihuanActivity.this, result.result.message,
							Toast.LENGTH_SHORT).show();
					System.out.println("result.result.message    " + result.result.message);
				} else {
					Toast.makeText(OrdersDuihuanActivity.this, result.result.message,
							Toast.LENGTH_LONG).show();
					System.out.println("result.result.message  ！  " + result.result.message);
				}
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

}
