package com.szcmcc.movie.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import com.szcmcc.movie.activity.MovieDetailActivity;
import com.szcmcc.movie.bean.Comment;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.util.TimeTools;

public class ZSQPeopleCriticAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Comment> mList;
	private MovieApplication app;

	public ZSQPeopleCriticAdapter(Context context) {
		this.mContext = context;
		this.app = (MovieApplication) context.getApplicationContext();
	}

	public void setData(ArrayList<Comment> list) {
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
		final ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.zsq_item_people_critic, null);
			holder = new ViewHolder();
			holder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			holder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
			holder.ivAvatar = (ImageView) convertView.findViewById(R.id.ivAvatar);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Date date = null;
		String time = mList.get(position).comment_time;
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = format.parse(mList.get(position).comment_time);
			time = TimeTools.getTimeString(mContext, date.getTime() / 1000 + "");
			holder.tvTime.setText(time);
		}catch(ParseException e){
			e.printStackTrace();
			holder.tvTime.setText(mList.get(position).comment_time);
		}
		holder.tvUserName.setText(mList.get(position).nickname);
		
		holder.tvContent.setText(mList.get(position).content);

		if (mList != null && URLUtil.isHttpUrl(mList.get(position).head_image)) {
			holder.ivAvatar.setTag(mList.get(position).head_image);
			Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext,
					mList.get(position).head_image, new ImageCallback() {

						@Override
						public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
							String url = (String) holder.ivAvatar.getTag();
							if (url.equals(bitmapUrl)) {
								holder.ivAvatar.setBackgroundColor(0x00000000);
								holder.ivAvatar.setImageBitmap(bitmap);
							}
						}
					}, AsyncImageLoader.CACHE_TYPE_SD, false);
			if (bitmap != null) {
				holder.ivAvatar.setBackgroundColor(0x00000000);
				holder.ivAvatar.setImageBitmap(bitmap);
			}

		}

		return convertView;
	}

	private static class ViewHolder {
		ImageView ivAvatar;
		TextView tvUserName;
		TextView tvTime;
		TextView tvContent;
	}

}
