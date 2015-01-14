package com.szcmcc.movie.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.Movie;
import com.szcmcc.movie.bean.MovieInfo;
import com.szcmcc.movie.bean.SaveMovieNew;
import com.szcmcc.movie.cache.MovieSaveDao;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.storage.BaseDBUtil;
import com.szcmcc.movie.view.ToastAlone;

public class ZSQMyCollectAct extends BaseActivity implements OnClickListener {
	private ListView listSave;
	// private MovieInfo movieInfo = null;
	private ImageButton imBack;
	private MovieApplication app;
	private ArrayList<SaveMovieNew> saveList;
	private ArrayList<Movie> netlist = new ArrayList<Movie>();

	private SaveMovieNewListAdapter mSaveMovieNewListAdapter = null;
	private LinearLayout noSaveMessage = null;

	@Override
	protected void onResume() {
		super.onResume();
		new GetSaveMovieTask().execute();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zsq_my_collect_activity);

		netlist.addAll(((MovieInfo) getIntent().getSerializableExtra(
				"movieInfo")).movies);

		if (ZSQMovieActivity.willMovieInfo == null) {

			try {
				netlist.addAll(lib.getAllMovie("1").movies);

			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

			netlist.addAll(ZSQMovieActivity.willMovieInfo.movies);
		}

//		new GetSaveMovieTask().execute();
		init();
		setListener();
		mSaveMovieNewListAdapter = new SaveMovieNewListAdapter();
		listSave.setAdapter(mSaveMovieNewListAdapter);
	}

	private void init() {
		imBack = (ImageButton) findViewById(R.id.imBack);
		listSave = (ListView) findViewById(R.id.listSave);
		noSaveMessage = (LinearLayout) findViewById(R.id.noSaveMessage);
		imBack.setOnClickListener(this);
	}

	private void setListener() {
		listSave.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (!mSaveMovieNewListAdapter.getItem(position).play_status
						.equals("2")) {
					Movie movie = null;
					for (int i = 0; i < netlist.size(); i++) {
						if (netlist.get(i).m_id.equals(mSaveMovieNewListAdapter
								.getItem(position).m_id)) {
							movie = netlist.get(i);
							break;
						}
					}
					if (movie != null) {
						Intent intent = new Intent(ZSQMyCollectAct.this,
								ZSQMoiveDetailActivity.class);
						intent.putExtra("m_id", movie.m_id);
						intent.putExtra("upcomming", movie.upcomming);

						if (movie.upcomming.equals("0")) {
							intent.putExtra("canbuy", true);
						} else if (movie.upcomming.equals("1")) {
							intent.putExtra("canbuy", false);
						}

						startActivity(intent);
					}
				} else {
					ToastAlone.makeText(ZSQMyCollectAct.this, "该影片已下线！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imBack:
			finish();
			break;

		default:
			break;
		}
	}

	class GetSaveMovieTask extends
			AsyncTask<String, String, ArrayList<SaveMovieNew>> {
		public String exception;

		@Override
		protected ArrayList<SaveMovieNew> doInBackground(String... params) {

			try {
				if (saveList != null)
					saveList.clear();
				MovieSaveDao movieSaveDao = new MovieSaveDao(
						ZSQMyCollectAct.this);
				saveList = movieSaveDao.getAllMovie();

				if (saveList != null) {
					boolean flag = true;
					for (int i = 0; i < saveList.size(); i++) {
						flag = true;
						for (int j = 0; j < netlist.size(); j++) {

							if (saveList.get(i).name
									.equals(netlist.get(j).name)) {
								if (!saveList.get(i).play_status.equals(netlist
										.get(j).upcomming)) {
									SaveMovieNew saveMovieNew = new SaveMovieNew(
											netlist.get(j).m_id,
											netlist.get(j).name,
											netlist.get(j).client_placard_url2,
											netlist.get(j).upcomming,
											netlist.get(j).release_date);
									movieSaveDao.updateMovie(saveMovieNew);
									saveList = movieSaveDao.getAllMovie();
								}
								flag = false;
							}

						}
						if (flag) {
							SaveMovieNew saveMovieNew = new SaveMovieNew(
									saveList.get(i).m_id, saveList.get(i).name,
									saveList.get(i).imageUrl, "2",
									saveList.get(i).playtime);
							movieSaveDao.updateMovie(saveMovieNew);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (saveList != null)
					saveList.clear();
			}

			return saveList;
		}

		@Override
		protected void onPostExecute(ArrayList<SaveMovieNew> result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.size() == 0) {
					noSaveMessage.setVisibility(View.VISIBLE);
				} else {
					noSaveMessage.setVisibility(View.GONE);
					mSaveMovieNewListAdapter.addItem(result);
				}
			} else {
				noSaveMessage.setVisibility(View.VISIBLE);
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	public class SaveMovieNewListAdapter extends BaseAdapter {
		private ArrayList<SaveMovieNew> saveMovieList = new ArrayList<SaveMovieNew>();
		private LayoutInflater layoutFlater;
		private Context mContext;

		public SaveMovieNewListAdapter() {
			super();
			mContext = ZSQMyCollectAct.this;
			app = (MovieApplication) mContext.getApplicationContext();
			layoutFlater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return saveMovieList.size();
		}

		public SaveMovieNew getItem(int position) {
			return saveMovieList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public void addItem(ArrayList<SaveMovieNew> list) {
			if (list != null) {
				saveMovieList = list;
				notifyDataSetChanged();
			}
		}

		public SaveMovieNew getSaveMovieItem(int position) {
			return saveMovieList.get(position);
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final ViewHolderListViewItme4 holder;
			if (convertView == null) {
				convertView = layoutFlater.inflate(
						R.layout.movie_savemovie_fourpage_list_item, null);
				holder = new ViewHolderListViewItme4();
				holder.imageUrl = (ImageView) convertView
						.findViewById(R.id.imgeUrl);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.playtime = (TextView) convertView
						.findViewById(R.id.playtime);
				holder.play_zero = (TextView) convertView
						.findViewById(R.id.play_zero);
				holder.play_one = (TextView) convertView
						.findViewById(R.id.play_one);
				holder.play_two = (TextView) convertView
						.findViewById(R.id.play_two);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolderListViewItme4) convertView.getTag();
			}
			holder.name.setText(saveMovieList.get(position).getName());
			holder.playtime.setText(saveMovieList.get(position).getPlaytime());
			holder.imageUrl.setImageBitmap(BaseDBUtil.readBitMap(
					ZSQMyCollectAct.this, R.drawable.loadinglist));
			if (saveMovieList != null
					&& URLUtil.isHttpUrl(saveMovieList.get(position).imageUrl)) {
				holder.imageUrl.setTag(saveMovieList.get(position).imageUrl);
				System.out
						.println("saveMovieList.get(position).imageUrl         "
								+ saveMovieList.get(position).imageUrl);
				Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(
						ZSQMyCollectAct.this,
						saveMovieList.get(position).imageUrl,
						new ImageCallback() {

							@Override
							public void imageLoaded(Bitmap bitmap,
									String bitmapUrl) {
								String url = (String) holder.imageUrl.getTag();
								if (url.equals(bitmapUrl)) {
									holder.imageUrl.setImageBitmap(bitmap);
								}
							}
						}, AsyncImageLoader.CACHE_TYPE_SD, true);
				if (bitmap != null) {
					holder.imageUrl.setImageBitmap(bitmap);
				}
			}

			if ("0".equals(saveMovieList.get(position).getPlay_status()))// 正在热映
			{
				holder.play_zero.setVisibility(View.VISIBLE);
				holder.play_one.setVisibility(View.GONE);
				holder.play_two.setVisibility(View.GONE);
			}

			else if ("1".equals(saveMovieList.get(position).getPlay_status()))// 即将上映
			{
				holder.play_zero.setVisibility(View.GONE);
				holder.play_one.setVisibility(View.VISIBLE);
				holder.play_two.setVisibility(View.GONE);
			}

			else if ("2".equals(saveMovieList.get(position).getPlay_status()))// 已下线
			{
				holder.play_zero.setVisibility(View.GONE);
				holder.play_one.setVisibility(View.GONE);
				holder.play_two.setVisibility(View.VISIBLE);
			}

			return convertView;
		}
	}

	private static class ViewHolderListViewItme4 {

		public TextView play_zero = null;
		public TextView play_one = null;
		public TextView play_two = null;
		public ImageView imageUrl = null;
		public TextView name = null;
		public TextView playtime = null;

	}
}
