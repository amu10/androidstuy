package com.szcmcc.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.R;
import com.szcmcc.movie.adapter.MovieSeatOrderInfoAdapter;
import com.szcmcc.movie.bean.LockOrDebLockMovieSeatsInfo;
import com.szcmcc.movie.bean.OrderQueryBySeat;

/**
 * 15-座位票购买 成功
 *
 */
public class MovieTicketSuccessAct extends BaseActivity {

	private Context myContext;
	
	private RelativeLayout relativelayout_paysuccess_order_info;
	private ListView list_paysuccess_orderinfo_details;
	private TextView textview_orderpay_success_recyphone,textview_orderpay_success_movieName,
	textview_orderpay_success_showtime,textview_orderpay_success_movieCinemaName;
	
	private String movie_ShowTime, movie_Name, movie_Cinema_Name,sevcphone;
	private LockOrDebLockMovieSeatsInfo lockMovieSeatsInfo;
	private MovieSeatOrderInfoAdapter movieSeatOrderInfoAdapter;
	private TextView successText ;
	private String cinemaRoom = "",cinemaTime = "";
	private ImageButton imBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movieticketsuccessact);
		myContext = this;
		initTitleBar();
		getExtra();
		initView();
		initData();
		BuyMovieTicketAct.buyMovieTicketAct.finish();
		SeatSelectedAct.seatSelectedAct.finish();
	}

	private void initData() {
		movieSeatOrderInfoAdapter = new MovieSeatOrderInfoAdapter(myContext);
		if(lockMovieSeatsInfo != null){
			if(lockMovieSeatsInfo.lockSeats != null && lockMovieSeatsInfo.lockSeats.size() > 0){
				movieSeatOrderInfoAdapter.addList(lockMovieSeatsInfo.lockSeats);
			}
		}
		System.out.println("dongdianzhouMovieTicket" + movie_Cinema_Name + movie_Name + movie_ShowTime);
		list_paysuccess_orderinfo_details.setAdapter(movieSeatOrderInfoAdapter);
		if(!TextUtils.isEmpty(movie_Cinema_Name)){
			textview_orderpay_success_movieCinemaName.setText(movie_Cinema_Name+"  "+cinemaRoom);
		}
		if(!TextUtils.isEmpty(movie_Name)){
			textview_orderpay_success_movieName.setText(movie_Name);
		}
		if(!TextUtils.isEmpty(movie_ShowTime)){
			textview_orderpay_success_showtime.setText(movie_ShowTime+" "+cinemaTime);
		}
		if(!TextUtils.isEmpty(sevcphone)){
			System.out.println("recyphone          "+sevcphone);
			textview_orderpay_success_recyphone.setText(sevcphone);
		}
		imBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void getExtra() {
		Intent intent = getIntent();
		if(intent != null) {
			lockMovieSeatsInfo =  (LockOrDebLockMovieSeatsInfo) intent.getSerializableExtra("lockMovieSeatsInfo");
			movie_ShowTime = intent.getStringExtra("showTime");
			movie_Cinema_Name = intent.getStringExtra("movieCinemaName");
			movie_Name = intent.getStringExtra("movieName");
			sevcphone = intent.getStringExtra("sevcphone");
			cinemaRoom = intent.getStringExtra("cinemaRoom");
			cinemaTime = intent.getStringExtra("cinemaTime");
		}
	}

	private void initView() {
		relativelayout_paysuccess_order_info = (RelativeLayout) findViewById(R.id.relativelayout_paysuccess_order_info);
		list_paysuccess_orderinfo_details = (ListView) findViewById(R.id.list_paysuccess_orderinfo_details);
		
		View view = LayoutInflater.from(myContext).inflate(R.layout.movie_orderinfo_paysuccess_header, null);
		textview_orderpay_success_recyphone = (TextView) view.findViewById(R.id.textview_orderpay_success_recyphone);
		textview_orderpay_success_movieName = (TextView) view.findViewById(R.id.textview_orderpay_success_movieName);
		textview_orderpay_success_showtime = (TextView) view.findViewById(R.id.textview_orderpay_success_showtime);
		textview_orderpay_success_movieCinemaName = (TextView) view.findViewById(R.id.textview_orderpay_success_movieCinemaName);
		imBack = (ImageButton) findViewById(R.id.imBack);
		list_paysuccess_orderinfo_details.addHeaderView(view);
		successText = (TextView)findViewById(R.id.successText);
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
