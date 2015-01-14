package com.szcmcc.movie.adapter;

import java.util.ArrayList;

import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.YouhuiBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ZSQOffersInfoAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<YouhuiBean> mList;

	public ZSQOffersInfoAdapter(Context context) {
		this.mContext = context;
	}

	public void setData(ArrayList<YouhuiBean> list) {
		this.mList = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
			convertView = inflater.inflate(R.layout.zsq_item_offers_info, null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
//		holder.tvName.setText(mList.get(position).title);
		return convertView;
	}

	private static class ViewHolder {
		TextView tvName;

	}

}
