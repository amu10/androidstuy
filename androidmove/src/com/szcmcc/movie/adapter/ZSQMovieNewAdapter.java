package com.szcmcc.movie.adapter;

import java.util.List;

import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.ZSQMovieNewsBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ZSQMovieNewAdapter extends BaseAdapter {
	private Context mContext;
	private List<ZSQMovieNewsBean> mList;

	public ZSQMovieNewAdapter(Context context) {
		this.mContext = context;
	}

	public void setData(List<ZSQMovieNewsBean> list) {
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
			convertView = inflater.inflate(R.layout.zsq_item_new, null);
			holder = new ViewHolder();
			holder.tvNew = (TextView) convertView.findViewById(R.id.tvNew);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvNew.setText(mList.get(position).getNewsTitle());
		return convertView;
	}

	private static class ViewHolder {
		TextView tvNew;
	}

}
