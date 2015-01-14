package com.szcmcc.movie.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szcmcc.movie.R;
import com.szcmcc.movie.activity.BuyTicketNewAct;
import com.szcmcc.movie.activity.SeatSelectedAct;
import com.szcmcc.movie.activity.UserLoginAct;
import com.szcmcc.movie.bean.MovieCinema;
import com.szcmcc.movie.bean.CinemaPrepareMovie.CinemaPrepareMovieInner.BySeat;
import com.szcmcc.movie.storage.SharedPreferencesUtil;
import com.szcmcc.movie.util.PublicUtils;

public class BuyTicketExpandableChildLinearView extends LinearLayout {

	private Context context;
	
	private View view;
	
	private ImageView point;
	
	private ImageView lean_line;
	
	private ImageView reservation;
	
	private TextView time;
	
	private TextView room;
	
	private TextView price;
	
	private TextView cinemaPrice;
	
	private RelativeLayout buyticket_reservation;
	
	private String today = PublicUtils.getDateFormat().split(" ")[0];

	private MovieCinema moviecinema;
	
	private BySeat byseat;
	
	private String m_id;
	
	public BuyTicketExpandableChildLinearView(Context context, LinearLayout linear) {
		super(context);
		this.context = context;
		initView(linear);
		addView(view);
		onClick();
	}
	
	private void initView(LinearLayout linear) {
		view = LayoutInflater.from(context).inflate(R.layout.add_buyticket_listview_child_view_layout, linear, false);
		time = (TextView) view.findViewById(R.id.time);
		point = (ImageView) view.findViewById(R.id.point);
		lean_line = (ImageView) view.findViewById(R.id.lean_line);
		reservation = (ImageView) view.findViewById(R.id.reservation);
		room = (TextView) view.findViewById(R.id.room);
		price = (TextView) view.findViewById(R.id.price);
		cinemaPrice = (TextView) view.findViewById(R.id.cinemaPrice);
		buyticket_reservation = (RelativeLayout) view.findViewById(R.id.buyticket_reservation);
		Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/LCD_DIGITAL.ttf");
		time.setTypeface(typeface);
	}
	
	private void onClick() {
		buyticket_reservation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferencesUtil shareP = SharedPreferencesUtil.getInstance(context);
				if (!shareP.getUserName().equals("")) {
					Intent intent = new Intent(context, SeatSelectedAct.class);
					intent.putExtra("moviecinema_id", moviecinema.c_id);
					intent.putExtra("companyId", moviecinema.companyId);
					intent.putExtra("movieSeat_ShowCode", byseat.showCode);
					intent.putExtra("movie_cinemaPrice", byseat.price);// 将票价歘过去
					intent.putExtra("movieName", byseat.movieSetName);
					intent.putExtra("cover_image_url", ((BuyTicketNewAct)context).getClient_placard_url2(m_id));
					intent.putExtra("payphone", shareP.getUserName());
					intent.putExtra("showTime", byseat.day_time); // 放映时间
					intent.putExtra("movieCinemaName", moviecinema.c_name);// 电影院
					intent.putExtra("cinemaRoom", byseat.room);
					intent.putExtra("cinemaTime", byseat.s_time);
//					System.out.println("----moviecinema_id----"+moviecinema.c_id+"----companyId----"+moviecinema.companyId+"----movieSeat_ShowCode----"+byseat.showCode+
//							"----movie_cinemaPrice----"+byseat.price+"----movieName----"+byseat.movieSetName+
//							"----cover_image_url----"+((BuyTicketNewAct)context).getClient_placard_url2(m_id)+"----payphone----"+
//							shareP.getUserName()+"----showTime----"+byseat.day_time+"----movieCinemaName----"+moviecinema.c_name+
//							"----cinemaRoom----"+byseat.room+"----cinemaTime----"+byseat.s_time);
					context.startActivity(intent);
				} else {
					Intent intent = new Intent(context, UserLoginAct.class);
					context.startActivity(intent);
				}
			}
		});
		setOnClickListener(null);
	}

	public void setData(MovieCinema moviecinema, BySeat byseat, String m_id) {
		this.moviecinema = moviecinema;
		this.byseat = byseat;
		this.m_id = m_id;
		//系统当前时间
		String nowTime = PublicUtils.getDateFormat().split(" ")[1].substring(0, 5);
		//时间过期
		if(today.equals(((BuyTicketNewAct)context).getCurrentDate()) && PublicUtils.isCompareTime(nowTime, byseat.s_time)) {
			point.setImageResource(R.drawable.buyticket_point1);
			time.setTextColor(0xFF727272);
			room.setTextColor(0xFF727272);
			price.setTextColor(0xFF727272);
			lean_line.setImageResource(R.drawable.lean_line);
			reservation.setImageResource(R.drawable.buyticket_reservation1);
			buyticket_reservation.setOnClickListener(null);
		}
		time.setText(byseat.s_time);
		room.setText(byseat.room);
		price.setText("￥"+byseat.price);
		//正式服务器没有该字段，为便于显示，加判断
		if (!TextUtils.isEmpty(byseat.cinemaPrice) && Double.valueOf(byseat.price) < Double.valueOf(byseat.cinemaPrice)) {
			cinemaPrice.setText("/"+byseat.cinemaPrice);
			lean_line.setVisibility(View.VISIBLE);
		} else {
			cinemaPrice.setText("");
			lean_line.setVisibility(View.INVISIBLE);
		}
	}
	
}
