package com.szcmcc.movie.adapter;

import java.util.List;

import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.ZSQClassicsPersonBean;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ZSQMoviePlayerAdapter extends BaseAdapter {
	private Context mContext;
	private List<ZSQClassicsPersonBean> mList;
	private MovieApplication app;

	public ZSQMoviePlayerAdapter(Context context) {
		this.mContext = context;
		this.app = (MovieApplication) context.getApplicationContext();
	}

	public void setData(List<ZSQClassicsPersonBean> list) {
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
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.zsq_item_movie_player, null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.ivAvatar = (ImageView) convertView.findViewById(R.id.ivAvatar);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(mList.get(position).getPersonName());
		holder.ivAvatar.setImageBitmap(null);
		holder.ivAvatar.setBackgroundResource(R.drawable.zsq_default_icon_play);
		// 图片显示
		if (mList != null && URLUtil.isHttpUrl(mList.get(position).getPosterAddr())) {
			holder.ivAvatar.setTag(mList.get(position).getPosterAddr());
			Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext,
					mList.get(position).getPosterAddr(), new ImageCallback() {

						@Override
						public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
							String url = (String) holder.ivAvatar.getTag();
							if (url.equals(bitmapUrl)) {
								holder.ivAvatar.setImageBitmap(bitmap);
							}
						}
					}, AsyncImageLoader.CACHE_TYPE_SD, false);
			if (bitmap != null) {
				holder.ivAvatar.setImageBitmap(bitmap);
			}
		}

		return convertView;
	}

	private static class ViewHolder {
		ImageView ivAvatar;
		TextView tvName;
	}

}
