package com.szcmcc.movie.adapter;

import com.szcmcc.movie.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HorizontallySortAdapter extends BaseAdapter {
	private int mIndex = 0;
	private Context mContext;
	private String[] mRegionArray;

	public HorizontallySortAdapter(Context context, String[] regionArray) {
		this.mContext = context;
		this.mRegionArray = regionArray;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mRegionArray.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
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
			convertView = inflater.inflate(R.layout.zsq_item_horizontally, null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.ivBlueLine = (ImageView) convertView.findViewById(R.id.ivBlueLine);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 显示蓝线
		if (position != mIndex) {
			holder.ivBlueLine.setVisibility(View.INVISIBLE);
			holder.tvName.setTextColor(mContext.getResources().getColor(R.color.white));
		} else {
			holder.ivBlueLine.setVisibility(View.VISIBLE);
			holder.tvName.setTextColor(mContext.getResources().getColor(R.color.blue_top));
		}
		holder.tvName.setText(mRegionArray[position]);
		return convertView;
	}

	public void setCurrentIndex(int index) {
		// TODO Auto-generated method stub
		mIndex = index;
	}

	private static class ViewHolder {
		TextView tvName;
		ImageView ivBlueLine;
	}

}
