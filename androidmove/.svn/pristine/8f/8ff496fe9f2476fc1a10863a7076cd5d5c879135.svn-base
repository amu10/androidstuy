package com.szcmcc.movie.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.YouhuiBean;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.view.MarqueeTextView;

public class ZSQEventInfoAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<YouhuiBean> mList;
	private MovieApplication app;

	public ZSQEventInfoAdapter(Context context) {
		this.mContext = context;
		this.app = (MovieApplication) context.getApplicationContext();
	}

	public void setData(ArrayList<YouhuiBean> list) {
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
	public YouhuiBean getItem(int position) {
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
		final ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.zsq_item_event_info, null);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvName);
//			holder.tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
			holder.tvTime = (MarqueeTextView) convertView.findViewById(R.id.tvTime);
			holder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvTitle.setText(mList.get(position).activity_name);
//		holder.tvAddress.setText(mList.get(position).address);
		try{
			String startDate = mList.get(position).start_date.split(" ")[0].split("-")[1];
			String startTime = mList.get(position).start_date.split(" ")[0].split("-")[2];
			String endDate = mList.get(position).end_date.split(" ")[0].split("-")[1];
			String endTime = mList.get(position).end_date.split(" ")[0].split("-")[2];
			holder.tvTime.setText("活动时间: " +startDate+"月"+startTime+"日-"+endDate+"月"+endTime+"日");
		}catch(Exception e){
			e.printStackTrace();
			holder.tvTime.setText("活动时间: " + mList.get(position).start_date+"-"+mList.get(position).end_date);
		}
		
		// 图片显示
		if (mList != null && URLUtil.isHttpUrl(mList.get(position).big_img)) {
			holder.ivIcon.setTag(mList.get(position).big_img);
			Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext,
					mList.get(position).big_img, new ImageCallback() {

						@Override
						public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
							String url = (String) holder.ivIcon.getTag();
							if (url.equals(bitmapUrl)) {
								holder.ivIcon.setImageBitmap(bitmap);
							}
						}
					}, AsyncImageLoader.CACHE_TYPE_SD, false);
			if (bitmap != null) {
				holder.ivIcon.setImageBitmap(bitmap);
			}
		}
		return convertView;
	}

	private static class ViewHolder {
		TextView tvTitle;
//		TextView tvAddress;
		MarqueeTextView tvTime;
		ImageView ivIcon;
	}

}
