package com.szcmcc.movie.adapter;

import java.util.List;

import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.ZSQRank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ZSQBoxOfficeTopAdapter extends BaseAdapter {
	private Context mContext;
	private List<ZSQRank> mList;

	public ZSQBoxOfficeTopAdapter(Context context) {
		this.mContext = context;
	}

	public void setData(List<ZSQRank> list) {
		this.mList = list;
		notifyDataSetChanged();
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
			convertView = inflater.inflate(R.layout.zsq_item_box_office, null);
			holder = new ViewHolder();
			holder.tvTop = (TextView) convertView.findViewById(R.id.tvTop);
			holder.tvMovieName = (TextView) convertView.findViewById(R.id.tvMovieName);
			holder.tvInCome = (TextView) convertView.findViewById(R.id.tvInCome);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// Top排名
		holder.tvTop.setText(String.valueOf(position + 1));
		if (position == 0) {
			holder.tvTop.setBackgroundResource(R.drawable.top1);
		} else if (position == 1) {
			holder.tvTop.setBackgroundResource(R.drawable.top2);
		} else if (position == 2) {
			holder.tvTop.setBackgroundResource(R.drawable.top3);
		} else {
			holder.tvTop.setBackgroundResource(R.color.transparent);
		}
		holder.tvMovieName.setText(mList.get(position).getMovieName());
		holder.tvInCome.setText(mList.get(position).getRankCount());
		return convertView;
	}

	private static class ViewHolder {
		TextView tvTop;
		TextView tvMovieName;
		TextView tvInCome;
	}

}
