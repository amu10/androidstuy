package com.szcmcc.movie.adapter;

import java.util.ArrayList;

import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.MovieTidbits;
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

public class ZSQStillsAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<MovieTidbits> mMovieTidbits;
	private MovieApplication app;

	public ZSQStillsAdapter(Context context) {
		this.mContext = context;
		this.app = (MovieApplication) context.getApplicationContext();
	}

	public void setData(ArrayList<MovieTidbits> movieTidbits) {
		this.mMovieTidbits = movieTidbits;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mMovieTidbits != null) {
			return mMovieTidbits.size();
		} else {
			return 0;
		}

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mMovieTidbits.get(position);
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
			convertView = inflater.inflate(R.layout.zsq_itotem_stills, null);
			holder = new ViewHolder();
			holder.ivStills = (ImageView) convertView.findViewById(R.id.ivStills);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 海报显示
		if (mMovieTidbits != null) {
			if (URLUtil.isHttpUrl(mMovieTidbits.get(position).client_tidbits_url_small)) {
				holder.ivStills.setTag(mMovieTidbits.get(position).client_tidbits_url_small);
				Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext,
						mMovieTidbits.get(position).client_tidbits_url_small, new ImageCallback() {

							@Override
							public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
								String url = (String) holder.ivStills.getTag();
								if (url.equals(bitmapUrl)) {
									holder.ivStills.setBackgroundColor(0x00000000);
									holder.ivStills.setImageBitmap(bitmap);
								}
							}
						}, AsyncImageLoader.CACHE_TYPE_SD, false);
				if (bitmap != null) {
					holder.ivStills.setBackgroundColor(0x00000000);
					holder.ivStills.setImageBitmap(bitmap);
				}

			}
		}
		return convertView;
	}

	private static class ViewHolder {
		ImageView ivStills;
	}

}
