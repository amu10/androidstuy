package com.szcmcc.movie.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.R;
import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.bean.Comment;
import com.szcmcc.movie.bean.CommentInfo;
import com.szcmcc.movie.bean.MoreCommentListBean;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieCommentLib;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.storage.SharedPreferencesUtil;

public class MoreCommentActivity extends BaseActivity{

	private CommentInfo commentInfo = null;
	private boolean isMoviesLoadAll = false;
	private String m_id = "",upcomming = "",comentId = "0",total = "";
	private MovieCommentLib commentLib;
	private ArrayList<Comment> list = new ArrayList<Comment>();
	private boolean isCanMoreCommented = true;
	private int currentPage = 1;
	private ListView listView = null;
	private ImageView pinglun_top = null;
	private MovieDetailMoreCommentAdatper adapter = null;
	SharedPreferencesUtil shareP;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_comment);
		commentLib = MovieCommentLib.getInstance(this);
		shareP = SharedPreferencesUtil.getInstance(this);
		init();
	}



	private void init(){
		Intent in = getIntent();
		if(in.getExtras()!=null){
			m_id = in.getExtras().getString("m_id");
			upcomming = in.getExtras().getString("upcomming");
//			comentId = in.getExtras().getString("comentId");
//			MoreCommentListBean moreList = new MoreCommentListBean();
//			list.addAll(moreList.getList());
		}
		pinglun_top = (ImageView) findViewById(R.id.pinglun_top);
		listView = (ListView)findViewById(R.id.movie_detail_listView_main);
		new GetCommentInfoTask(MoreCommentActivity.this).execute();
		adapter = new MovieDetailMoreCommentAdatper(MoreCommentActivity.this,list);
		listView.setAdapter(adapter);
		onClick();
	}

	private void onClick(){
		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				System.out.println("visibleItemCount    "+firstVisibleItem+"        "+visibleItemCount+"       "+totalItemCount);
				if(isCanMoreCommented){
				if (firstVisibleItem+visibleItemCount >= totalItemCount&&totalItemCount>=10) {
					isCanMoreCommented = false;
					comentId = list.get(totalItemCount -1).c_id;
					new GetCommentInfoTask(MoreCommentActivity.this).execute();
				} 
				}
			}
		});
		
		pinglun_top.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!shareP.getUserName().equals("")) {
					Intent in = new Intent(MoreCommentActivity.this, WritePinLunAct.class);
					in.putExtra("m_id", m_id);
					in.putExtra("upcomming", upcomming);
//					in.putExtra("name", name.getText());
//					in.putExtra("imageUrl", cover_image_url);
					in.putExtra("uid", shareP.getUid()[0]);
					in.putExtra("token", shareP.getUid()[1]);
					System.out.println("shareP.getUid()[0]    "+shareP.getUid()[0]+"    "+shareP.getUid()[1]);
					startActivity(in);
				} else {
					Intent intent = new Intent(MoreCommentActivity.this, UserLoginAct.class);
					startActivity(intent);
				}
			}
		});
	}

	class GetCommentInfoTask extends MovieAsyncTask<String, String, CommentInfo> {
		public String exception;

		public GetCommentInfoTask(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected CommentInfo doInBackground(String... params) {
			// CommentInfo commentInfo = null;

			try {
				commentInfo = commentLib.getCommentsByMovieId(m_id, comentId, "10",upcomming);

				if (commentInfo.comments != null) {
					list.addAll(commentInfo.comments);
					
					System.out.println("list--------" + list.size());
				}

			} catch (HttpException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (IOException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (JSONException e) {
				exception = getResources().getString(R.string.exception_json);
				e.printStackTrace();
			}
			return commentInfo;
		}

		@Override
		protected void onPostExecute(CommentInfo result) {
			super.onPostExecute(result);
			if (result != null && result.isSuccess()) {
				total = commentInfo.total;
				System.out.println("total    "+total+"       "+currentPage);
				if (Integer.parseInt(total) <= (10*currentPage)) {
					isCanMoreCommented = false;
					System.out.println("total 1   "+total);
				}
				else{
					currentPage += 1;
					isCanMoreCommented = true;
					System.out.println("total  2  "+total);
				}
				adapter.notifyDataSetChanged();
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}
	
	
	class MovieDetailMoreCommentAdatper extends BaseAdapter {
		private Context mContext;

		ArrayList<Comment> list;
		private MovieApplication app;

		private MovieDetailMoreCommentAdatper(Context context, ArrayList<Comment> list) {
			mContext = context;
			app = (MovieApplication) mContext.getApplicationContext();
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final Holder holder;
			if (convertView == null) {
				System.out.println("position    " + position);
				holder = new Holder();

				convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_movie_detail_comment_item, parent, false);
				// holder.pinglun =
				// (ImageView)convertView.findViewById(R.id.pinglun);
				// else{
				// convertView =
				// LayoutInflater.from(mContext).inflate(R.layout.layout_movie_detail_comment_item,
				// parent, false);
				// }
				holder.imageUrl = (ImageView) convertView.findViewById(R.id.imageUrl);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.point = (TextView) convertView.findViewById(R.id.point);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.ping = (TextView) convertView.findViewById(R.id.ping);
				holder.pointtrate = (RatingBar) convertView.findViewById(R.id.pointtrate);

				convertView.setTag(holder);
				// if(position == 0){
				// holder.pinglun.setVisibility(View.VISIBLE);
				// }else{
				// holder.pinglun.setVisibility(View.GONE);
				// }
			} else {
				System.out.println("getposition    " + position);

				holder = (Holder) convertView.getTag();
				// if(position == 0){
				// holder.pinglun.setVisibility(View.VISIBLE);
				// }else{
				// holder.pinglun.setVisibility(View.GONE);
				// }
			}
			// holder = new Holder();
			// convertView =
			// LayoutInflater.from(mContext).inflate(R.layout.layout_movie_detail_list0,
			// parent, false);
			// holder.imageUrl =
			// (ImageView)convertView.findViewById(R.id.imageUrl);
			// holder.name = (TextView) convertView.findViewById(R.id.name);
			// holder.point = (TextView) convertView.findViewById(R.id.point);
			// holder.time = (TextView) convertView.findViewById(R.id.time);
			// holder.ping = (TextView) convertView.findViewById(R.id.ping);
			// holder.pointtrate = (RatingBar)
			// convertView.findViewById(R.id.pointtrate);
			// holder.pinglun =
			// (ImageView)convertView.findViewById(R.id.pinglun);
			// if(position == 0){
			// holder.pinglun.setVisibility(View.VISIBLE);
			// }else{
			// holder.pinglun.setVisibility(View.GONE);
			// }
			// if(position == 0){
			// holder.pinglun.setVisibility(View.VISIBLE);
			// }else{
			// holder.pinglun.setVisibility(View.GONE);
			// }
			Comment comment = list.get(position);

			holder.name.setText(list.get(position).nickname);
			holder.time.setText(list.get(position).comment_time);
			holder.ping.setText(list.get(position).content);
			// pinglun.setOnClickListener(new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// Intent in = new Intent(mContext, WritePinLunAct.class);
			// in.putExtra("m_id", m_id);
			// in.putExtra("upcomming", upcomming);
			// in.putExtra("name", name.getText());
			// in.putExtra("imageUrl", cover_image_url);
			// mContext.startActivity(in);
			// }
			// });
			if (comment != null && URLUtil.isHttpUrl(comment.head_image)) {
				holder.imageUrl.setTag(comment.head_image);
				Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext, comment.head_image, new ImageCallback() {

					@Override
					public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
						String url = (String) holder.imageUrl.getTag();
						if (url.equals(bitmapUrl)) {
							holder.imageUrl.setImageBitmap(bitmap);
						}
					}
				}, AsyncImageLoader.CACHE_TYPE_SD,true);
				if (bitmap != null) {
					holder.imageUrl.setImageBitmap(bitmap);
				}
			}

			return convertView;
		}

	}

	private static class Holder {
		public ImageView imageUrl;
		public TextView name, point, time, ping;
		public RatingBar pointtrate;
		// public ImageView pinglun;
	}
	
	public void onResume(){
		super.onResume();
		CmccDataStatistics.onStart(this);
		}
	public void onPause() {
		super.onPause();
		CmccDataStatistics.onStop(this);
		}
}
