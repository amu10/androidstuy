package com.szcmcc.movie.activity;

import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.MovieInfo;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ZSQMainTabActivity extends TabActivity implements OnClickListener {
	protected static final int TAB_NUM_ZERO = 0;
	protected static final int TAB_NUM_ONE = 1;
	protected static final int TAB_NUM_TOW = 2;
	protected static final int TAB_NUM_THREE = 3;
	protected static final int TAB_NUM_FOUR = 4;

	private MovieInfo movieInfo;// 影片信息

	private ImageView ivMovie;// 影片按钮
	private ImageView ivCinema;// 影院按钮
	private ImageView ivPreferential;// 优惠按钮
	private ImageView ivCoupon;// 兑换券按钮
	private ImageView ivMore;// 兑换券按钮
	private RelativeLayout rvTabMovie;
	private RelativeLayout rvTabCinema;
	private RelativeLayout rvTabPreferential;
	private RelativeLayout rvTabCoupon;
	private RelativeLayout rvTabMore;

	private TabHost mTabHost; // 拿到Layout中的Tabhost

	private TabSpec movieSpec; // 创建的TabHost子选单 TabSpec
	private TabSpec cinemaSpec; // 创建的TabHost子选单 TabSpec
	private TabSpec preferentialSpec; // 创建的TabHost子选单 TabSpec
	private TabSpec couponSpec; // 创建的TabHost子选单 TabSpec
	private TabSpec moreSpec; // 创建的TabHost子选单 TabSpec
	private String downloadUrl = "";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zsq_activity_main_tab);
		// 接收影片信息
		movieInfo = (MovieInfo) getIntent().getSerializableExtra("movieInfo");
		downloadUrl = getIntent().getStringExtra("downloadUrl");
		findViewById(); // 查看所有id
		initTabHost();
		setAllOnClickListener(); // 设置所有监听
		addSpec();
	}

	private void addSpec() {
		mTabHost.addTab(movieSpec);
		mTabHost.addTab(cinemaSpec);
		mTabHost.addTab(preferentialSpec);
		mTabHost.addTab(couponSpec);
		mTabHost.addTab(moreSpec);
		mTabHost.setCurrentTab(TAB_NUM_ZERO);
	}

	/**
	 * 初始化TabHost
	 */
	private void initTabHost() {
		mTabHost = getTabHost();
		movieSpec = mTabHost.newTabSpec("0");
		cinemaSpec = mTabHost.newTabSpec("1");
		preferentialSpec = mTabHost.newTabSpec("2");
		couponSpec = mTabHost.newTabSpec("3");
		moreSpec = mTabHost.newTabSpec("4");
		
		movieSpec.setIndicator(null, null);
		cinemaSpec.setIndicator(null, null);
		preferentialSpec.setIndicator(null, null);
		couponSpec.setIndicator(null, null);
		moreSpec.setIndicator(null, null);

		// 影片
		Intent movieIntent = new Intent(this, ZSQMovieActivity.class);
		movieIntent.putExtra("movieInfo", movieInfo);
		movieIntent.putExtra("downloadUrl", downloadUrl);
		movieSpec.setContent(movieIntent);
		// 影院
		Intent cinemaIntent = new Intent(this, ZSQCinemaActivity.class);
		cinemaSpec.setContent(cinemaIntent);
		// 优惠
		Intent preferentialIntent = new Intent(this, ZSQConcessionsActivity.class);
		preferentialSpec.setContent(preferentialIntent);
		// 兑换券
		Intent couponIntent = new Intent(this, ZSQCouponAreaActivity.class);
		couponSpec.setContent(couponIntent);
		// 更多
		Intent moreIntent = new Intent(this, SettingAct.class);
		moreIntent.putExtra("movieInfo", movieInfo);
		moreSpec.setContent(moreIntent);
	}

	/**
	 * 找到Layout中的 所需ID
	 */
	protected void findViewById() {
		ivMovie = (ImageView) findViewById(R.id.ivMovie);
		ivCinema = (ImageView) findViewById(R.id.ivCinema);
		ivPreferential = (ImageView) findViewById(R.id.ivPreferential);
		ivCoupon = (ImageView) findViewById(R.id.ivCoupon);
		ivMore = (ImageView) findViewById(R.id.ivMore);
		
//		rvTabMovie = (RelativeLayout) findViewById(R.id.rvTabMovie);
//		rvTabCinema = (RelativeLayout) findViewById(R.id.rvTabCinema);
//		rvTabPreferential = (RelativeLayout) findViewById(R.id.rvTabPreferential);
//		rvTabCoupon = (RelativeLayout) findViewById(R.id.rvTabCoupon);
//		rvTabMore = (RelativeLayout) findViewById(R.id.rvTabMore);
	}

	/**
	 * 设置点击事件
	 */
	public void setAllOnClickListener() {
		ivMovie.setOnClickListener(this);
		ivCinema.setOnClickListener(this);
		ivPreferential.setOnClickListener(this);
		ivCoupon.setOnClickListener(this);
		ivMore.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivMovie:
			tabOnClick(R.id.ivMovie);
			break;
		case R.id.ivCinema:
			tabOnClick(R.id.ivCinema);
			break;
		case R.id.ivPreferential:
			tabOnClick(R.id.ivPreferential);
			break;
		case R.id.ivCoupon:
			tabOnClick(R.id.ivCoupon);
			break;
		case R.id.ivMore:
			tabOnClick(R.id.ivMore);
			break;
		}
	}

	private void tabOnClick(int ids) {
		switch (ids) {
		case R.id.ivMovie:
//			reSetTabBg();
			reSetTabIvBg();
			ivMovie.setImageResource(R.drawable.zsq_tab_movie_b);
			mTabHost.setCurrentTab(TAB_NUM_ZERO); // 执行 TabHost位置上的Activity
			break;
		case R.id.ivCinema:
//			reSetTabBg();
			reSetTabIvBg();
			ivCinema.setImageResource(R.drawable.zsq_tab_cinema_b);
			mTabHost.setCurrentTab(TAB_NUM_ONE);
			break;
		case R.id.ivPreferential:
//			reSetTabBg();
			reSetTabIvBg();
			ivPreferential.setImageResource(R.drawable.zsq_tab_preferential_b);
			mTabHost.setCurrentTab(TAB_NUM_TOW);
			break;
		case R.id.ivCoupon:
//			reSetTabBg();
			reSetTabIvBg();
			ivCoupon.setImageResource(R.drawable.zsq_tab_coupon_b);
			mTabHost.setCurrentTab(TAB_NUM_THREE);
			break;
		case R.id.ivMore:
//			reSetTabBg();
			reSetTabIvBg();
			ivMore.setImageResource(R.drawable.zsq_tab_more_b);
			mTabHost.setCurrentTab(TAB_NUM_FOUR);
			break;
		}
	}

//	private void reSetTabBg() {
//		rvTabMovie.setBackgroundResource(R.drawable.zsq_tab_bg);
//		rvTabCinema.setBackgroundResource(R.drawable.zsq_tab_bg);
//		rvTabPreferential.setBackgroundResource(R.drawable.zsq_tab_bg);
//		rvTabCoupon.setBackgroundResource(R.drawable.zsq_tab_bg);
//		rvTabMore.setBackgroundResource(R.drawable.zsq_tab_bg);
//	}

	private void reSetTabIvBg() {
		ivMovie.setImageResource(R.drawable.zsq_tab_movie_a);
		ivCinema.setImageResource(R.drawable.zsq_tab_cinema_a);
		ivPreferential.setImageResource(R.drawable.zsq_tab_preferential_a);
		ivCoupon.setImageResource(R.drawable.zsq_tab_coupon_a);
		ivMore.setImageResource(R.drawable.zsq_tab_more_a);
	}

}
