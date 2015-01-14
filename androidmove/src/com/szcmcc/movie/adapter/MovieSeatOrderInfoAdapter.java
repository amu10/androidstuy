package com.szcmcc.movie.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.szcmcc.movie.R;
import com.szcmcc.movie.activity.OneCinaemaAct;
import com.szcmcc.movie.bean.LockOrDebLockMovieSeatInfo;
import com.szcmcc.movie.bean.SeatInfo;

public class MovieSeatOrderInfoAdapter extends BaseAdapter {

	private List<LockOrDebLockMovieSeatInfo> movieSeatdata;
	private Context myContext;
	private ViewHoder viewHoder;
	private boolean isFirstLoad = true;

	public MovieSeatOrderInfoAdapter(Context context) {
		myContext = context;
	}

	// 添加数据
	public void addList(List<LockOrDebLockMovieSeatInfo> list) {
		movieSeatdata = list;
	}

	@Override
	public int getCount() {
		if(movieSeatdata!=null){
		return movieSeatdata.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return movieSeatdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHoder = new ViewHoder();
		if (convertView == null) {
			convertView = LayoutInflater.from(myContext).inflate(
					R.layout.movieseat_orderinfo_item, null);
			viewHoder.movieSeat_seatCode = (TextView) convertView
					.findViewById(R.id.movieSeat_orderInfo_seatCode);
			viewHoder.movieSeat_seatCol = (TextView) convertView
					.findViewById(R.id.movieSeat_orderInfo_seatCol);
			viewHoder.movieSeat_seatRow = (TextView) convertView
					.findViewById(R.id.movieSeat_orderInfo_seatRow);
			viewHoder.movieSeat_seatNum = (TextView) convertView
					.findViewById(R.id.textview_orderinfo_num);
			convertView.setTag(viewHoder);
		} else {
			viewHoder = (ViewHoder) convertView.getTag();
		}
		if (position == 0) {
			viewHoder.movieSeat_seatNum.setVisibility(View.VISIBLE);
		}
		if (movieSeatdata != null && movieSeatdata.size() > 0) {
			viewHoder.movieSeat_seatCode
					.setText(movieSeatdata.get(position).seatAreaCode);
			viewHoder.movieSeat_seatCol
					.setText(movieSeatdata.get(position).seatCol);
			viewHoder.movieSeat_seatRow
					.setText(movieSeatdata.get(position).seatRow);
			if(isFirstLoad){
				if(movieSeatdata.size()>2){
					viewHoder.movieSeat_seatNum.setText(movieSeatdata.size()
							+ "位 V");
				}else{
					viewHoder.movieSeat_seatNum.setText(movieSeatdata.size()
							+ "位");
				}
				
			}
		}
//		viewHoder.movieSeat_seatNum.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				isFirstLoad = false;
//				if(viewHoder.movieSeat_seatNum.getText().toString().equals("共" + movieSeatdata.size() + "位 V")){
//					System.out.println("我走了上拉效果的设置！");
//					viewHoder.movieSeat_seatNum.setText("共" + movieSeatdata.size()
//							+ "位 ^");
//				} else{
//					System.out.println("我走了下拉效果的设置！");
//					viewHoder.movieSeat_seatNum.setText("共" + movieSeatdata.size()
//							+ "位 V");
//				}
//				MovieSeatOrderInfoAdapter.this.notifyDataSetChanged();
//			}
//		});
		return convertView;
	}

	class ViewHoder {
		public TextView movieSeat_seatCode;
		public TextView movieSeat_seatRow;
		public TextView movieSeat_seatCol;
		public TextView movieSeat_seatNum;
	}
}
