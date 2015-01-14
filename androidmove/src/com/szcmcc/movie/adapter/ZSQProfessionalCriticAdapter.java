package com.szcmcc.movie.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.Comment;
import com.szcmcc.movie.bean.Profession;

public class ZSQProfessionalCriticAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Profession> mList;
	
	public ZSQProfessionalCriticAdapter(Context context) {
		this.mContext = context;
	}
	
	public void setData(ArrayList<Profession> list) {
		this.mList = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if(mList!=null){
			return mList.size();
		}
		return 0;
	}

	@Override
	public Profession getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.zsq_item_professional_critic, null);
			holder = new ViewHolder();
			holder.tvCommentator = (TextView)convertView.findViewById(R.id.tvCommentator);
			holder.tvContent = (TextView)convertView.findViewById(R.id.tvContent);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvContent.setText(mList.get(position).content);
		holder.tvCommentator.setText(mList.get(position).author);
		return convertView;
	}

	private static class ViewHolder {
		TextView tvContent;
		TextView tvCommentator;
	}

}
