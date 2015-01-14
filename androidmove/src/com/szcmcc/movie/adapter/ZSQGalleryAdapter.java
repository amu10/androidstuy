package com.szcmcc.movie.adapter;

import java.util.ArrayList;

import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.activity.BuyTicketNewAct;
import com.szcmcc.movie.activity.ZSQMoiveDetailActivity;
import com.szcmcc.movie.bean.Movie;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.view.LoadingImagView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class ZSQGalleryAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Movie> mMovieList = null;
	private MovieApplication app;
	private int hight;
	private boolean imWill = false;//标记是否是即将上映模块

	public ZSQGalleryAdapter(Context context) {
		this.mContext = context;
		this.app = (MovieApplication) context.getApplicationContext();

		DisplayMetrics metric = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
		hight = metric.heightPixels;
		Log.i("zzz", "hight = " + hight);
	}

	public void setData(ArrayList<Movie> movieList) {
		this.mMovieList = movieList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mMovieList != null) {
			return mMovieList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mMovieList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.zsq_item_poster, null);
			holder = new ViewHolder();
			holder.ivLoadIv = (LoadingImagView) convertView.findViewById(R.id.ivLoadIv);
			holder.ivStills = (ImageView) convertView.findViewById(R.id.ivStills);
			holder.rvGray = (RelativeLayout) convertView.findViewById(R.id.rvGray);
			holder.ivMovieInfo = (ImageView) convertView.findViewById(R.id.ivMovieInfo);
			holder.ivBuy = (ImageView) convertView.findViewById(R.id.ivBuy);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 读取图片
		holder.ivLoadIv.setVisibility(View.VISIBLE);

		// 海报显示
		Movie movie = mMovieList.get(position);
		if (movie != null && URLUtil.isHttpUrl(movie.client_placard_url1)) {
			holder.ivStills.setTag(movie.client_placard_url1);
			Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext,
					movie.client_placard_url1, new ImageCallback() {

						@Override
						public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
							String url = (String) holder.ivStills.getTag();
							if (url.equals(bitmapUrl)) {
								holder.ivLoadIv.setVisibility(View.GONE);
								holder.ivStills.setBackgroundResource(R.color.black);
								holder.ivStills.setImageBitmap(bitmap);
							}
						}
					}, AsyncImageLoader.CACHE_TYPE_SD, false);
			if (bitmap != null) {
				holder.ivLoadIv.setVisibility(View.GONE);
				holder.ivStills.setBackgroundResource(R.color.black);
				// holder.ivStills.setBackgroundColor(0x00000000);
				holder.ivStills.setImageBitmap(bitmap);
			}

		}
		// 是否蒙灰处理
		if (!mMovieList.get(position).isGrayed()) {
			holder.rvGray.setVisibility(View.GONE);
		} else if (mMovieList.get(position).isGrayed()) {
			holder.rvGray.setVisibility(View.VISIBLE);
		}
		// 区分正在热映与即将上映
		if (mMovieList.get(position).upcomming.equals("0")) {
			holder.ivBuy.setVisibility(View.VISIBLE);
		} else if (mMovieList.get(position).upcomming.equals("1")) {
			holder.ivBuy.setVisibility(View.INVISIBLE);
		}
		// 电影详情响应事件
		holder.ivMovieInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, ZSQMoiveDetailActivity.class);
				intent.putExtra("movie", mMovieList.get(position));
				intent.putExtra("movies", mMovieList);
				intent.putExtra("m_id", mMovieList.get(position).m_id);
				intent.putExtra("upcomming", mMovieList.get(position).upcomming);
				
				if(mMovieList.get(position).upcomming.equals("0")){
					intent.putExtra("canbuy", true);
				}else if(mMovieList.get(position).upcomming.equals("1")){
					intent.putExtra("canbuy",false);
				}
				
				mContext.startActivity(intent);
			}
		});
		// 购票响应事件
		holder.ivBuy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mMovieList != null) {
					Intent intent = new Intent(mContext, BuyTicketNewAct.class);
					intent.putExtra("m_id", mMovieList.get(position).m_id);
					intent.putExtra("m_name", mMovieList.get(position).name);
					// intent.putExtra("c_id", "");
					intent.putExtra("rate", mMovieList.get(position).rate);
					intent.putExtra("movies", mMovieList);
					mContext.startActivity(intent);
				}
			}
		});
		convertView.setLayoutParams(new Gallery.LayoutParams(hight / 5 * 2, hight / 5 * 3));

		// if (width == 800) {// 三星note
		// convertView.setLayoutParams(new
		// Gallery.LayoutParams(LayoutParams.WRAP_CONTENT,
		// ((width - 240) * 13 / 10)));
		// } else if (width == 480) {// HTC G12
		// convertView.setLayoutParams(new Gallery.LayoutParams((width - 170),
		// ((width - 130) * 13 / 10)));
		// } else {
		// convertView.setLayoutParams(new Gallery.LayoutParams((width - 170),
		// ((width - 130) * 13 / 10)));
		// }

		return convertView;
	}

	private static class ViewHolder {
		LoadingImagView ivLoadIv;
		ImageView ivStills;
		ImageView poster;
		ImageView ivMovieInfo;
		ImageView ivBuy;
		RelativeLayout rvGray;
	}
	
	public void setImWill() {
		imWill = true;
	}
	
}
