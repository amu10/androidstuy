package com.szcmcc.movie.activity;

import java.io.IOException;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.OrderPayOk;
import com.szcmcc.movie.network.MovieAsyncTask;

/**
 * 17-兑换券购买 成功
 *
 */
public class TicketExchangeSuccessAct extends BaseActivity {

	private String orderid = "",name = "",phone = "",expiredTime = "",buyCount = "";
	private OrderPayOk orderPayOk = null;
	private TextView buyresult = null,c_name = null,message = null,expired_time = null,count = null,sevcphone = null;
	private boolean isMoviesLoadAll = false;
	private boolean isSendShortOk = false;
	private boolean isBuySuccess = false;
	private ImageButton imBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ticketexchangesuccessact);
		initTitleBar();
		findViewById();
		Intent in = getIntent();
		if(in.getExtras()!=null){
			orderid = in.getExtras().getString("orderid");
			name = in.getExtras().getString("c_name");
			phone = in.getExtras().getString("sevcphone");
			expiredTime = in.getExtras().getString("expired_time");
			buyCount = in.getExtras().getString("count");
			isSendShortOk = in.getExtras().getBoolean("SendShort");
			isBuySuccess = in.getExtras().getBoolean("isBuySuccess");
			System.out.println("orderid   "+orderid+"     +"+name+"         "+phone+"      "+isSendShortOk);
		}
		if(isBuySuccess){
			buyresult.setText("购买成功");
		}else{
		buyresult.setText("短信下发成功！");
		}
		sevcphone.setText(phone);
		if(isSendShortOk){
		message.setVisibility(View.VISIBLE);
		}
		else
			message.setVisibility(View.GONE);
		c_name.setText(name);
		expired_time.setText(expiredTime);
		count.setText(buyCount);
//		if (!isMoviesLoadAll) {
//			new GetMovieInfoTask(TicketExchangeSuccessAct.this).execute();
//		}
		BuyTicketExchangeAct.buyTicketExchangeAct.finish();
	}
	
	private void findViewById(){
		sevcphone = (TextView)findViewById(R.id.sevcphone);
		buyresult = (TextView)findViewById(R.id.buyresult);
		c_name = (TextView)findViewById(R.id.c_name);
		message = (TextView)findViewById(R.id.message);
		expired_time = (TextView)findViewById(R.id.data);
		count = (TextView)findViewById(R.id.count);
		imBack = (ImageButton) findViewById(R.id.imBack);
		imBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	class GetMovieInfoTask extends MovieAsyncTask<String, String, OrderPayOk> {
		public String exception;

		public GetMovieInfoTask(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected OrderPayOk doInBackground(String... params) {

			try {

				if (!orderid.equals("")) {
					orderPayOk = lib.orderConfirm(orderid, "0");
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
			}
			return orderPayOk;
		}

		@Override
		protected void onPostExecute(OrderPayOk result) {
			super.onPostExecute(result);
			if (result != null && result.isSuccess()) {
				
					buyresult.setText("短信下发成功！");
					sevcphone.setText(phone);
					if(isSendShortOk){
					message.setVisibility(View.VISIBLE);
					}
					else
						message.setVisibility(View.GONE);
					c_name.setText(name);
					expired_time.setText(orderPayOk.orderPayOkInner.expired_time);
					count.setText(orderPayOk.orderPayOkInner.count);
					
			
				isMoviesLoadAll = true;
			}else{
				buyresult.setText(R.string.buy_no);
				c_name.setText(name);
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}
	public void onResume(){
		super.onResume();
		CmccDataStatistics.onStart(this);
		}
	public void onPause() {
		super.onPause();
		CmccDataStatistics.onStop(this);
		}
	
}
