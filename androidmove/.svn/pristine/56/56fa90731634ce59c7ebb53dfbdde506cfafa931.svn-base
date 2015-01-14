package com.szcmcc.movie.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.LockOrDebLockMovieSeatInfo;
import com.szcmcc.movie.bean.SeatInfo;

public class ShowSelectSeatAdapter extends BaseAdapter {
	private Context myContext;
	private List<SeatInfo> showSelectSeat;
	private ViewHoder viewHoder;

	
	public ShowSelectSeatAdapter(Context context){
		this.myContext = context;
		showSelectSeat = new ArrayList<SeatInfo>();
	}
	
	public void addItem(SeatInfo seatInfo){
		showSelectSeat.add(seatInfo);
	}
	
	public void removeItem(SeatInfo seatInfo){
		showSelectSeat.remove(seatInfo);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return showSelectSeat.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return showSelectSeat.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHoder = new ViewHoder();
		if(convertView == null){
			convertView = LayoutInflater.from(myContext).inflate(R.layout.list_seatselect_showseat_item, null);
//		    viewHoder.showSelectSeat_seatcode = (TextView) convertView.findViewById(R.id.seatselect_textview_seatcode);
		    viewHoder.showSelectSeat_seatcol = (TextView) convertView.findViewById(R.id.seatselect_textview_seatcol);
		    viewHoder.showSelectSeat_seatrow = (TextView) convertView.findViewById(R.id.seatselect_textview_searow);
		    convertView.setTag(viewHoder);
		} else {
			viewHoder = (ViewHoder) convertView.getTag();
		}
		System.out.println("dongdianzhouShowSelectSeat" + showSelectSeat.size());
		if(showSelectSeat != null && showSelectSeat.size() > 0){
//			viewHoder.showSelectSeat_seatcode.setText(showSelectSeat.get(position).seatAreaCode);
			viewHoder.showSelectSeat_seatcol.setText(showSelectSeat.get(position).seatCol);
			viewHoder.showSelectSeat_seatrow.setText(showSelectSeat.get(position).seatRow);
		}
		return convertView;
	}
	
	class ViewHoder {
//		private TextView showSelectSeat_seatcode;
		private TextView showSelectSeat_seatrow;
		private TextView showSelectSeat_seatcol;
	}

}
