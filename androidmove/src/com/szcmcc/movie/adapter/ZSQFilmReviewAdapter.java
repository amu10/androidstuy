package com.szcmcc.movie.adapter;

import java.util.List;

import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.ZSQWonderfulCommentBean;
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

public class ZSQFilmReviewAdapter extends BaseAdapter {
	private Context mContext;
	private List<ZSQWonderfulCommentBean> mList;
	private MovieApplication app;

	public ZSQFilmReviewAdapter(Context context) {
		this.mContext = context;
		this.app = (MovieApplication) context.getApplicationContext();
	}

	public void setData(List<ZSQWonderfulCommentBean> list) {
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
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.zsq_item_film_review, null);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.tvCreatePerson = (TextView) convertView.findViewById(R.id.tvCreatePerson);
			holder.ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvTitle.setText(mList.get(position).getCommentTitle());
		holder.tvCreatePerson.setText(mList.get(position).getCreatePerson());
		holder.ivPoster.setImageBitmap(null);
		holder.ivPoster.setBackgroundResource(R.drawable.zsq_default_icon_words);
		// 图片显示
		if (mList != null && URLUtil.isHttpUrl(mList.get(position).getImgAddr())) {
			holder.ivPoster.setTag(mList.get(position).getImgAddr());
			Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext,
					mList.get(position).getImgAddr(), new ImageCallback() {

						@Override
						public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
							String url = (String) holder.ivPoster.getTag();
							if (url.equals(bitmapUrl)) {
								holder.ivPoster.setBackgroundColor(0x00000000);
								holder.ivPoster.setImageBitmap(bitmap);
							}
						}
					}, AsyncImageLoader.CACHE_TYPE_SD, false);
			if (bitmap != null) {
				holder.ivPoster.setBackgroundColor(0x00000000);
				holder.ivPoster.setImageBitmap(bitmap);
			}
		}

		return convertView;
	}

	private static class ViewHolder {
		TextView tvTitle;
		TextView tvCreatePerson;
		ImageView ivPoster;
	}

}
