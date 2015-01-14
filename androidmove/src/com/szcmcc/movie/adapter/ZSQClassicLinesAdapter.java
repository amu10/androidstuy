package com.szcmcc.movie.adapter;

import java.util.List;

import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.ZSQClassicsWordsBean;
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

public class ZSQClassicLinesAdapter extends BaseAdapter {
	private Context mContext;
	private List<ZSQClassicsWordsBean> mList;
	private MovieApplication app;

	public ZSQClassicLinesAdapter(Context context) {
		this.mContext = context;
		this.app = (MovieApplication) context.getApplicationContext();
	}

	public void setData(List<ZSQClassicsWordsBean> list) {
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
			convertView = inflater.inflate(R.layout.zsq_item_classic_lines, null);
			holder = new ViewHolder();
			holder.tvMovieName = (TextView) convertView.findViewById(R.id.tvMovieName);
			holder.tvClassicLines = (TextView) convertView.findViewById(R.id.tvClassicLines);
			holder.ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvMovieName.setText("--《" + mList.get(position).getSourceMovie() + "》");
		holder.tvClassicLines.setText(mList.get(position).getContent());
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
								holder.ivPoster.setImageBitmap(bitmap);
							}
						}
					}, AsyncImageLoader.CACHE_TYPE_SD, false);
			if (bitmap != null) {
				holder.ivPoster.setImageBitmap(bitmap);
			}
		}

		return convertView;
	}

	private static class ViewHolder {
		ImageView ivPoster;
		TextView tvMovieName;
		TextView tvClassicLines;
	}

}
