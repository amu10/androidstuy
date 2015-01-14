package com.szcmcc.movie.adapter;

import java.util.ArrayList;
import java.util.List;

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
import com.szcmcc.movie.bean.Movie;
import com.szcmcc.movie.bean.YouHuiNew;
import com.szcmcc.movie.bean.YouhuiBean;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.storage.BaseDBUtil;

public class YouhuiAdapter extends BaseAdapter {
	private ArrayList<YouhuiBean> list;
	private Context mContext;
	private MovieApplication app;

	public YouhuiAdapter(Context context) {
		mContext = context;
		app = (MovieApplication) mContext.getApplicationContext();
		list = new ArrayList<YouhuiBean>();
//		YouhuiBean youhuiBean = null;
//		for (int i = 0; i < 10; i++) {
//			youhuiBean = new YouhuiBean();
//			youhuiBean.title = "金逸国际影城" + i;
//			youhuiBean.imgsmallurl = "http://img4.cache.netease.com/ent/2012/2/17/20120217093718959cd.jpg";
//			youhuiBean.address = "UME国际影城";
//			youhuiBean.time = "2月12日-2月14日";
//			youhuiBean.desc = "默契比拼 你来比划我来猜默契比拼 你来比划我来猜默契比拼 你来比划我来猜默契比拼 你来比划我来猜默契比拼 你来比划我来猜";
//			youhuiBean.content = "默契比拼 你来比划我来猜";
//			youhuiBean.imgbigurl = "http://img4.cache.netease.com/ent/2012/2/17/20120217093718959cd.jpg";
//			youhuiBean.isnew = "true";
//			list.add(youhuiBean);
//		}
	}

	public void addItem(ArrayList<YouhuiBean> list) {
		this.list = list;
		notifyDataSetChanged();
	}
	
	public YouhuiBean getYouhuiBeanItem(int position){
		return list.get(position);
	}

	@Override
	public int getCount() {
		if(list == null){
			return 0;
		}
		return list.size();
	}

	@Override
	public YouhuiBean getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.youhui_movie_new_list_item, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.imageUrl);
//			holder.logo = (ImageView) convertView.findViewById(R.id.logo);
			holder.address = (TextView) convertView.findViewById(R.id.address);
			holder.time = (TextView) convertView.findViewById(R.id.date);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		YouhuiBean bean = list.get(position);
		String url = bean.big_img;
		holder.image.setImageBitmap(BaseDBUtil.readBitMap(mContext, R.drawable.loadinglist));
		if (bean != null && URLUtil.isHttpUrl(url)) {
			holder.image.setTag(url);
			Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext, url, new ImageCallback() {

				@Override
				public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
					String url = (String) holder.image.getTag();
					if (url.equals(bitmapUrl)) {
						holder.image.setImageBitmap(bitmap);
					}
				}
			}, AsyncImageLoader.CACHE_TYPE_SD, false);
			if (bitmap != null) {
				holder.image.setImageBitmap(bitmap);
			}
		}
//		holder.address.setText(bean.address);
//		holder.time.setText(bean.time);
//		holder.name.setText(bean.title);
//		if (bean.isnew.equals("true")) {
//			holder.logo.setVisibility(View.VISIBLE);
//		} else {
//			holder.logo.setVisibility(View.GONE);
//		}
		return convertView;
	}

	private static class ViewHolder {

		public ImageView image;
//		public ImageView logo;
		public TextView address;
		public TextView time;
		public TextView name;
	}

}
