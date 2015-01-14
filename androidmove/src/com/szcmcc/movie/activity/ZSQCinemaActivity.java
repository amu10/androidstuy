package com.szcmcc.movie.activity;

import java.io.IOException;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.szcmcc.movie.R;
import com.szcmcc.movie.adapter.IconPagerAdapter;
import com.szcmcc.movie.bean.MovieCinemaList;
import com.szcmcc.movie.cache.AppConstants;
import com.szcmcc.movie.fragment.CinemaFragment;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.view.MessageDialog;
import com.szcmcc.movie.view.TabPageIndicator;

public class ZSQCinemaActivity extends BaseFragmentActivity {
	protected MovieLib lib;
	private MovieCinemaList movieCinemaList = null;

	private GoogleMusicAdapter pagerAdapter;
	private ViewPager pager;
	private TabPageIndicator indicator;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zsq_act_cinema);
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (movieCinemaList == null) {
			movieCinemaList = new MovieCinemaList();
			
			if (AppConstants.movieCinema != null) {
				movieCinemaList = AppConstants.movieCinema;
				ZSQCouponAreaActivity.icons = new int[movieCinemaList.mList.size()];
				for (int i = 0; i < ZSQCouponAreaActivity.icons.length; i++) {
					ZSQCouponAreaActivity.icons[i] = R.drawable.zsq_tab_group_line;
				}
			} else {
				new GetMovieInfoTask(this).execute();
			}
		}
		if (pagerAdapter == null) {
			pagerAdapter = new GoogleMusicAdapter(getSupportFragmentManager());
			pager.setAdapter(pagerAdapter);
			indicator.setViewPager(pager);
		}
	}

	private void init() {
		pager = (ViewPager) findViewById(R.id.pagerCinema);
		indicator = (TabPageIndicator) findViewById(R.id.indicatorCinema);
	}

	private class GoogleMusicAdapter extends FragmentStatePagerAdapter implements IconPagerAdapter {

		public GoogleMusicAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return CinemaFragment.getInstance(movieCinemaList.mList.get(position).cinemas,
					movieCinemaList);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return movieCinemaList.mList.get(position).region_name;
		}

		@Override
		public int getCount() {
			if (movieCinemaList.mList != null) {
				return movieCinemaList.mList.size();
			} else {
				return 0;
			}
		}

		@Override
		public int getIconResId(int index) {
			return ZSQCouponAreaActivity.icons[index];
		}
	}

	private class GetMovieInfoTask extends MovieAsyncTask<String, String, MovieCinemaList> {

		public GetMovieInfoTask(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected MovieCinemaList doInBackground(String... params) {
			try {
				lib = MovieLib.getInstance(ZSQCinemaActivity.this);
//				if (AppConstants.movieCinema != null) {
//					movieCinemaList = AppConstants.movieCinema;
//				} else {
					movieCinemaList = lib.getCinema(ZSQCinemaActivity.this);
//				}
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return movieCinemaList;
		}

		@Override
		protected void onPostExecute(MovieCinemaList result) {
			super.onPostExecute(result);
			if (result != null && result.isSuccess()) {
				if(result.mList != null && result.mList.size() > 0) {
					ZSQCouponAreaActivity.icons = new int[result.mList.size()];
					for (int i = 0; i < ZSQCouponAreaActivity.icons.length; i++) {
						ZSQCouponAreaActivity.icons[i] = R.drawable.zsq_tab_group_line;
					}
					indicator.notifyDataSetChanged();
					try {
						if (AppConstants.movieCinema == null) {
							AppConstants.movieCinema = result;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					MessageDialog.getInstance().setData(ZSQCinemaActivity.this, result.result.message, false);
				}
			} else {
				MessageDialog.getInstance().setData(ZSQCinemaActivity.this, R.string.data_failed_to_load, false);
			}
		}
	}
	
	private void showDailog(String msg) {

		AlertDialog.Builder builder = new Builder(this);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle("确认退出");
		builder.setMessage(msg);

		builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				ExitQuest();
				finish();

			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// if(NetImageView.isWifi(mContext)){
			showDailog("您确定要退出么？");
			// }else{
			// finish();
			// }
		}
		return false;
	}
}
