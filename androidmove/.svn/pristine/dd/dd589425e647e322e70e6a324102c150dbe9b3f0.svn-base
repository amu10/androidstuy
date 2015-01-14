package com.szcmcc.movie.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.MovieCinema;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;

public class ZSQCinemaAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<MovieCinema> mList;
	private MovieApplication app;

	public ZSQCinemaAdapter(Context context) {
		this.mContext = context;
		this.app = (MovieApplication) context.getApplicationContext();
	}

	public void setData(ArrayList<MovieCinema> list) {
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
	public MovieCinema getItem(int position) {
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
			convertView = inflater.inflate(R.layout.zsq_item_cinema, null);
			holder = new ViewHolder();
			holder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.tvAddress = (TextView) convertView
					.findViewById(R.id.tvAddress);
			holder.ivZuo = (ImageView) convertView.findViewById(R.id.ivZuo);
			holder.ivQuan = (ImageView) convertView.findViewById(R.id.ivQuan);
			holder.lvSuppCouponName = (LinearLayout) convertView
					.findViewById(R.id.lvSuppCouponName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(mList.get(position).c_name);
		if(TextUtils.isEmpty(mList.get(position).address) || "null".equals(mList.get(position).address)) {
			holder.tvAddress.setText("");
		} else {
			holder.tvAddress.setText(mList.get(position).address);
		}
		if (mList.get(position).order_type.equals("0")) {
			holder.ivZuo.setVisibility(View.VISIBLE);
			holder.ivQuan.setVisibility(View.GONE);
		} else if (mList.get(position).order_type.equals("1")) {
			holder.ivZuo.setVisibility(View.GONE);
			holder.ivQuan.setVisibility(View.VISIBLE);
		} else if (mList.get(position).order_type.equals("2")) {
			holder.ivZuo.setVisibility(View.VISIBLE);
			holder.ivQuan.setVisibility(View.VISIBLE);
		} else {
			holder.ivZuo.setVisibility(View.GONE);
			holder.ivQuan.setVisibility(View.GONE);
		}
		System.out.println("cuppCouponName-----  +"+mList.get(position).suppCouponName);
		// 动态添加优惠
		if (mList.get(position).suppCouponName != null
				&& !mList.get(position).suppCouponName.equals("")&&!mList.get(position).suppCouponName.equals("null")) {
			String[] tempArray;
			tempArray = mList.get(position).suppCouponName.split(",");
			if (holder.lvSuppCouponName.getChildCount() != 0) {
				holder.lvSuppCouponName.removeAllViews();
			}
			for (int i = 0; i < tempArray.length; i++) {
				View view = LayoutInflater.from(mContext).inflate(
						R.layout.zsq_item_hui, null);
				final TextView tvSuppCouponName = (TextView) view
						.findViewById(R.id.tvSuppCouponName);
				final LinearLayout lv = (LinearLayout) view
						.findViewById(R.id.lv);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				lp.setMargins(6, 6, 0, 0);
				lv.setLayoutParams(lp);
				tvSuppCouponName.setText(tempArray[i]);
				holder.lvSuppCouponName.addView(view);
			}
			holder.lvSuppCouponName.setVisibility(View.VISIBLE);
		} else {
			holder.lvSuppCouponName.setVisibility(View.GONE);
		}
		// 图片显示
		if (mList != null && URLUtil.isHttpUrl(mList.get(position).image_url)) {
			holder.ivIcon.setTag(mList.get(position).image_url);
			Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(
					mContext, mList.get(position).image_url,
					new ImageCallback() {

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
		// mList.get(position).image_url;
		return convertView;
	}

	private static class ViewHolder {
		TextView tvName;
		TextView tvAddress;
		ImageView ivZuo;
		ImageView ivQuan;
		ImageView ivIcon;
		LinearLayout lvSuppCouponName;
	}

}
