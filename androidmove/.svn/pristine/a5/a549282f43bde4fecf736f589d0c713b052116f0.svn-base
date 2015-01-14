package com.szcmcc.movie.activity;

import java.io.IOException;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.ReSendOrderBean;
import com.szcmcc.movie.bean.SeatOrder;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.view.MarqueeTextView;

public class OrdersSeatActivity extends Activity {

	private ImageButton imBack;
	private TextView movie_name, show_time, order_number, price, count, all_price, date, status,
			sendButton;
	private SeatOrder seatOrder;
	private MarqueeTextView cinema_name;
	protected MovieLib lib;
	private LinearLayout movieNameLinear, showDateLinear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orders_seat);

		findView();
		init();
		setListener();
	}

	private void findView() {
		lib = MovieLib.getInstance(this);
		cinema_name = (MarqueeTextView) findViewById(R.id.cinema_name);
		order_number = (TextView) findViewById(R.id.order_number);
		movie_name = (TextView) findViewById(R.id.movie_name);
		show_time = (TextView) findViewById(R.id.show_time);
		price = (TextView) findViewById(R.id.price);
		count = (TextView) findViewById(R.id.count);
		all_price = (TextView) findViewById(R.id.all_price);
		date = (TextView) findViewById(R.id.date);
		status = (TextView) findViewById(R.id.status);
		sendButton = (TextView) findViewById(R.id.sendButton);
		movieNameLinear = (LinearLayout) findViewById(R.id.movieNameLinear);
		showDateLinear = (LinearLayout) findViewById(R.id.showDateLinear);
		sendButton.setVisibility(View.GONE);
		imBack = (ImageButton) findViewById(R.id.imBack);
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
			movie_name.setText(seatOrder.name);
			show_time.setText(seatOrder.showdate);
			if (seatOrder.name.equals("")) {
				movieNameLinear.setVisibility(View.GONE);
			}
			if (seatOrder.showdate.equals("")) {
				showDateLinear.setVisibility(View.GONE);
			}
			switch (Integer.parseInt(seatOrder.status)) {
			case 0:
				status.setText("等待付款");
				break;
			case 1:
				status.setText("支付成功");
				break;
			case 2:
				status.setText("支付失败");
				break;
			case 3:
				sendButton.setVisibility(View.VISIBLE);
				status.setText("支付成功，订购成功");
				break;
			case 4:
				status.setText("支付成功，订购失败");
				break;
			case 5:
				sendButton.setVisibility(View.VISIBLE);
				status.setText("支付成功，订购成功，发送成功");
				break;
			case 6:
				sendButton.setVisibility(View.VISIBLE);
				status.setText("支付成功，订购成功，发送失败");
				break;
			case 7:
				sendButton.setVisibility(View.VISIBLE);
				status.setText("退费成功");
				break;
			case 8:
				sendButton.setVisibility(View.VISIBLE);
				status.setText("退费失败");
				break;
			case 11:
				sendButton.setVisibility(View.VISIBLE);
				status.setText("支付成功，订购中");
				break;
			case 12:
				sendButton.setVisibility(View.VISIBLE);
				status.setText("支付成功，订购成功，发送成功，打票失败");
				break;
			case 13:
				sendButton.setVisibility(View.VISIBLE);
				status.setText("支付成功，订购成功，发送成功，打票确认失败");
				break;
			case 14:
				sendButton.setVisibility(View.VISIBLE);
				status.setText("支付成功，订购成功，发送成功，打票成功");
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
				new ReSendOrderMovieTask(OrdersSeatActivity.this).execute(seatOrder.orderId, "1");
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
			}
			return reSendOrderBean;
		}

		@Override
		protected void onPostExecute(ReSendOrderBean result) {
			super.onPostExecute(result);
			if (result != null) {
				System.out.println("result.result    " + result.result);
				System.out.println("result.result    " + result.result.code);
				if (result.result.code.equals("1")) {
					Toast.makeText(OrdersSeatActivity.this, result.result.message,
							Toast.LENGTH_SHORT).show();
					System.out.println("result.result.message    " + result.result.message);
				} else {
					Toast.makeText(OrdersSeatActivity.this, result.result.message,
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
