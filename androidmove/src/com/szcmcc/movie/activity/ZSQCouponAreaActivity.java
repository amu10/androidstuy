package com.szcmcc.movie.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.szcmcc.movie.R;
import com.szcmcc.movie.adapter.IconPagerAdapter;
import com.szcmcc.movie.bean.MovieCinema;
import com.szcmcc.movie.bean.MovieCinemaList;
import com.szcmcc.movie.bean.MovieCinemaList.MovieCinemaListInner;
import com.szcmcc.movie.cache.AppConstants;
import com.szcmcc.movie.fragment.CouponAreaFragment;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.view.MessageDialog;
import com.szcmcc.movie.view.TabPageIndicator;

public class ZSQCouponAreaActivity extends BaseFragmentActivity {
	protected MovieLib lib;
	private MovieCinemaList movieCinemaList = null;

	private GoogleMusicAdapter pagerAdapter;
	private ViewPager pager;
	private TabPageIndicator indicator;
	public static int[] icons;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zsq_act_coupon_area);
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (movieCinemaList == null) {
			movieCinemaList = new MovieCinemaList();
			
			if (AppConstants.movieCinema != null) {
				movieCinemaList = getList(AppConstants.movieCinema);
				icons = new int[movieCinemaList.mList.size()];
				for (int i = 0; i < icons.length; i++) {
					icons[i] = R.drawable.zsq_tab_group_line;
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
		pager = (ViewPager) findViewById(R.id.pagerCoupon);
		indicator = (TabPageIndicator) findViewById(R.id.indicatorCoupon);
	}

	private class GoogleMusicAdapter extends FragmentStatePagerAdapter implements IconPagerAdapter {

		public GoogleMusicAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return CouponAreaFragment.getInstance(movieCinemaList.mList.get(position).cinemas);
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
			return icons[index];
		}
	}
	
	
	private MovieCinemaList getList(MovieCinemaList result){
		MovieCinemaList list = new MovieCinemaList();
		ArrayList<MovieCinemaListInner> innerList = new ArrayList<MovieCinemaListInner>();
		if(result!=null&&result.mList.size()!=0){
			for(int i = 0;i<result.mList.size();i++){
			ArrayList<MovieCinema> cinemas = result.mList.get(i).cinemas;
			MovieCinemaListInner cinemas_new = new MovieCinemaListInner();
			cinemas_new.cinemas = new ArrayList<MovieCinema>();
			cinemas_new.region_id = result.mList.get(i).region_id;
			cinemas_new.region_name =  result.mList.get(i).region_name;
			if(cinemas != null&&cinemas.size()>0){
				for(int j = 0;j<cinemas.size();j++){
					if(cinemas.get(j).coupon!=null && ("1".equals(cinemas.get(j).order_type) || "2".equals(cinemas.get(j).order_type))){
						
						cinemas_new.cinemas.add(cinemas.get(j));
					}
				}
			}
			System.out.println("list-=-=-=- 1   "+AppConstants.movieCinema.mList.get(i).cinemas.size());
			innerList.add(cinemas_new);
			System.out.println("list-=-=-=-2    "+AppConstants.movieCinema.mList.get(i).cinemas.size());
			}
		}
		list.mList = innerList;
		list.result = result.result;
		list.update_time = result.update_time;
		return list;
	}

	private class GetMovieInfoTask extends MovieAsyncTask<String, String, MovieCinemaList> {

		public GetMovieInfoTask(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected MovieCinemaList doInBackground(String... params) {
			try {
				lib = MovieLib.getInstance(ZSQCouponAreaActivity.this);
//				if (AppConstants.movieCinema != null) {
//					movieCinemaList = AppConstants.movieCinema;
//				} else {
					movieCinemaList = lib.getCinema(ZSQCouponAreaActivity.this);
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
					icons = new int[result.mList.size()];
					for (int i = 0; i < icons.length; i++) {
						icons[i] = R.drawable.zsq_tab_group_line;
					}
					try {
						if (AppConstants.movieCinema == null) {
							AppConstants.movieCinema = result;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					movieCinemaList = getList(result);
					indicator.notifyDataSetChanged();
				} else {
					MessageDialog.getInstance().setData(ZSQCouponAreaActivity.this, result.result.message, false);
				}
			} else {
				MessageDialog.getInstance().setData(ZSQCouponAreaActivity.this, R.string.data_failed_to_load, false);
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
