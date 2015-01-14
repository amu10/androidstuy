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
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.storage.BaseDBUtil;
import com.szcmcc.movie.util.RoateUtil;

public class MovieNewAdapter extends BaseAdapter {
	private ArrayList<Movie> movieList;
	private Context mContext;
	private MovieApplication app;
	private int select_index = 0;

	public MovieNewAdapter(Context context) {
		mContext = context;
		app = (MovieApplication) mContext.getApplicationContext();
		movieList = new ArrayList<Movie>();
	}

	public void addItem(List<Movie> list) {
		if (list != null) {
			movieList.addAll(list);
			notifyDataSetChanged();
		}
	}

	public void setSelect(int select) {
		select_index = select;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return movieList.size();
	}

	@Override
	public Object getItem(int position) {
		return movieList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
 		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.movie_new_list_item, null);
			holder = new ViewHolder(convertView);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// holder.getScore().clearAnimation();
		Movie movie = movieList.get(position);
		holder.getScore().setText(movie.rate);
		holder.getScore().setVisibility(View.GONE);
		if (movie.release_date == null || movie.release_date.equals("") || movie.release_date.equals("null")) {
			holder.getDate().setVisibility(View.GONE);
			
		}
		holder.getDate().setText(movie.release_date);
		holder.getName().setText(movie.name);
//		final ImageView imageView = ;
		holder.getPic().setImageBitmap(BaseDBUtil.readBitMap(mContext, R.drawable.loadinglist));
		if (movie != null && URLUtil.isHttpUrl(movie.client_placard_url2)) {
			holder.getPic().setTag(movie.client_placard_url2);
			Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext, movie.client_placard_url2, new ImageCallback() {

				@Override
				public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
					String url = (String) holder.getPic().getTag();
					if (url.equals(bitmapUrl)) {
						holder.getPic().setImageBitmap(bitmap);
					}
				}
			}, AsyncImageLoader.CACHE_TYPE_SD,false);
			if (bitmap != null) {
				holder.getPic().setImageBitmap(bitmap);
			}
		}

		if ("0".equals(movie.upcomming))// 已上映
		{
			RoateUtil.tranlateCenter(mContext, holder.getScore());
			holder.getDate().setVisibility(View.INVISIBLE);
			holder.getScore().setVisibility(View.VISIBLE);
			holder.getScoreImage().setVisibility(View.GONE);
			holder.getScore_bk().setVisibility(View.VISIBLE);
		} else if ("1".equals(movie.upcomming))// 未上映
		{
			//TODO 这里只显示时钟，如果有需求判断日期是否为空用下面注释代码即可
			RoateUtil.tranlateCenter(mContext, holder.getScore());
			holder.getDate().setVisibility(View.INVISIBLE);
			holder.getScore().setText("");
			holder.getScore().setVisibility(View.GONE);
			holder.getScoreImage().setVisibility(View.VISIBLE);
			holder.getScore_bk().setVisibility(View.VISIBLE);
//			if (movie.release_date == null || movie.release_date.equals("")||movie.release_date.equals("null")) {
//				//TODO 此处是因为即将上映，但日期无值，固显示评分
//				RoateUtil.tranlateCenter(mContext, holder.getScore());
//				holder.getDate().setVisibility(View.INVISIBLE);
//				holder.getScore().setText("");
//				holder.getScore().setVisibility(View.GONE);
//				holder.getScoreImage().setVisibility(View.VISIBLE);
//				holder.getScore_bk().setVisibility(View.VISIBLE);
//			} else {
//				holder.getDate().setVisibility(View.VISIBLE);
//				holder.getScore().clearAnimation();
//				holder.getScore().setVisibility(View.GONE);
//				holder.getScoreImage().setVisibility(View.GONE);
//				holder.getScore_bk().setVisibility(View.INVISIBLE);
//			}
		}
		if (select_index == position) {
			holder.getFrame().setImageResource(R.drawable.film_cover_red_bg);
			holder.getName().setTextColor(mContext.getResources().getColor(R.color.name_orange));
		} else {
			holder.getFrame().setImageResource(R.drawable.film_cover_black_bg);
			holder.getName().setTextColor(mContext.getResources().getColor(R.color.white));
		}

		return convertView;
	}

	private class ViewHolder {
		private ImageView pic;
		private TextView score;
		private TextView scoreImage;
		private ImageView score_bk;
		private TextView date;
		private TextView name;
		private ImageView frame;
		private View view;

		public ViewHolder(View view) {
			this.view = view;
		}

		public ImageView getPic() {
			if (pic == null) {
				pic = (ImageView) view.findViewById(R.id.movie_item_imageView_pic);
			}
			return pic;
		}

		public TextView getScore() {
			if (score == null) {
				score = (TextView) view.findViewById(R.id.movie_item_textView_score);
			}
			return score;
		}

		public ImageView getScore_bk() {
			if (score_bk == null) {
				score_bk = (ImageView) view.findViewById(R.id.movie_item_imageView_score);
			}
			return score_bk;
		}

		public TextView getDate() {
			if (date == null) {
				date = (TextView) view.findViewById(R.id.movie_item_textView_date);
			}
			return date;
		}

		public TextView getName() {
			if (name == null) {
				name = (TextView) view.findViewById(R.id.movie_item_textView_name);
			}
			return name;
		}

		public ImageView getFrame() {
			if (frame == null) {
				frame = (ImageView) view.findViewById(R.id.movie_item_imageView_frame);
			}
			return frame;
		}

		public TextView getScoreImage() {
			if (scoreImage == null) {
				scoreImage = (TextView) view.findViewById(R.id.movie_item_textView_score_image);
			}
			return scoreImage;
		}

		

	}
}
