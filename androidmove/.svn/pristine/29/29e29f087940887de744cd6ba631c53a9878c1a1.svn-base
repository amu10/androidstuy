package com.szcmcc.movie.adapter;

import java.util.ArrayList;

import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.MovieCinema;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ZSQCouponAreaAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<MovieCinema> mList;

	public void setData(ArrayList<MovieCinema> list) {
		this.mList = list;
		notifyDataSetChanged();
	}

	public ZSQCouponAreaAdapter(Context context) {
		this.mContext = context;
	}

	@Override
	public int getCount() {
		if (mList != null) {
			return mList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.zsq_item_coupon, null);
			holder = new ViewHolder();
			holder.tvCinemaName = (TextView) convertView.findViewById(R.id.tvCinemaName);
			holder.listSubCoupon = (ListView) convertView.findViewById(R.id.listSubCoupon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvCinemaName.setText(mList.get(position).c_name);
		// subList
		ZSQSubCouponAreaAdapter adapter = new ZSQSubCouponAreaAdapter(mContext,
				mList.get(position).price, mList.get(position).c_id, mList.get(position).c_name,
				mList.get(position).companyId, mList.get(position).MovieCinemaInnerList);
		holder.listSubCoupon.setAdapter(adapter);
		setListViewHeightBasedOnChildren(holder.listSubCoupon);
		return convertView;
	}

	/**
	 * 传入一个ListView，根据他的Item的高度设置他的高度
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	private static class ViewHolder {
		TextView tvCinemaName;
		ListView listSubCoupon;
	}
}
