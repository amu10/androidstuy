package com.szcmcc.movie.activity;

import java.io.IOException;

import org.apache.http.HttpException;
import org.json.JSONException;

import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.MovieCinemaList;
import com.szcmcc.movie.bean.ZSQBaseBean;
import com.szcmcc.movie.bean.ZSQClassicsPersonDetailBean;
import com.szcmcc.movie.cache.AppConstants;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;
import com.szcmcc.movie.view.MessageDialog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ZSQMoviePlayerActivity extends Activity implements OnClickListener {
	private MovieApplication app;
	protected MovieLib lib;
	private ImageButton imBack;
	private Button btnIntroduction;
	private Button btnWorks;
	private Button btnAchievement;
	private TextView tvName;
	private TextView tvEngName;
	private TextView tvCountry;
	private TextView tvBirthplace;
	private TextView tvBirthday;
	private TextView tvProfession;
	private TextView tvInfo;
	private ImageView ivAvatar;
	private String personId;
	private ZSQBaseBean<ZSQClassicsPersonDetailBean> bean;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zsq_act_player);
		app = (MovieApplication) getApplicationContext();
		personId = getIntent().getStringExtra("personId");
		init();
		new GetMovieInfoTask(this, personId).execute("");
	}

	private void init() {
		imBack = (ImageButton) findViewById(R.id.imBack);
		ivAvatar = (ImageView) findViewById(R.id.ivAvatar);
		btnIntroduction = (Button) findViewById(R.id.btnIntroduction);
		btnWorks = (Button) findViewById(R.id.btnWorks);
		btnAchievement = (Button) findViewById(R.id.btnAchievement);
		tvName = (TextView) findViewById(R.id.tvName);
		tvEngName = (TextView) findViewById(R.id.tvEngName);
		tvCountry = (TextView) findViewById(R.id.tvCountry);
		tvBirthplace = (TextView) findViewById(R.id.tvBirthplace);
		tvBirthday = (TextView) findViewById(R.id.tvBirthday);
		tvProfession = (TextView) findViewById(R.id.tvProfession);
		tvInfo = (TextView) findViewById(R.id.tvInfo);

		imBack.setOnClickListener(this);
		btnIntroduction.setOnClickListener(this);
		btnAchievement.setOnClickListener(this);
		btnWorks.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imBack:
			finish();
			break;
		case R.id.btnIntroduction:
			reSetButtonShow();
			btnIntroduction.setBackgroundResource(R.drawable.zsq_btn_pressed);
			btnIntroduction.setTextColor(getResources().getColor(R.color.blue_top));
			if(!TextUtils.isEmpty(bean.getClassicsPerson().getSummary())) {
				tvInfo.setText(bean.getClassicsPerson().getSummary());
			} else {
				tvInfo.setText("暂无信息");
			}
			break;
		case R.id.btnAchievement:
			reSetButtonShow();
			btnAchievement.setBackgroundResource(R.drawable.zsq_btn_pressed);
			btnAchievement.setTextColor(getResources().getColor(R.color.blue_top));
			if(!TextUtils.isEmpty(bean.getClassicsPerson().getAchievement().replaceAll("<br/>", "\n"))) {
				tvInfo.setText(bean.getClassicsPerson().getAchievement().replaceAll("<br/>", "\n"));
			} else {
				tvInfo.setText("暂无信息");
			}
			break;
		case R.id.btnWorks:
			reSetButtonShow();
			btnWorks.setBackgroundResource(R.drawable.zsq_btn_pressed);
			btnWorks.setTextColor(getResources().getColor(R.color.blue_top));
			if(!TextUtils.isEmpty(bean.getClassicsPerson().getWorks())) {
				tvInfo.setText(bean.getClassicsPerson().getWorks());
			} else {
				tvInfo.setText("暂无信息");
			}
			break;
		default:
			break;
		}
	}

	private void reSetButtonShow() {
		btnIntroduction.setBackgroundResource(R.drawable.zsq_btn_universal);
		btnAchievement.setBackgroundResource(R.drawable.zsq_btn_universal);
		btnWorks.setBackgroundResource(R.drawable.zsq_btn_universal);
		btnIntroduction.setTextColor(getResources().getColor(R.color.light_gray));
		btnAchievement.setTextColor(getResources().getColor(R.color.light_gray));
		btnWorks.setTextColor(getResources().getColor(R.color.light_gray));
	}

	private void showStills() {
		if (bean != null && URLUtil.isHttpUrl(bean.getClassicsPerson().getPosterAddr())) {
			ivAvatar.setTag(bean.getClassicsPerson().getPosterAddr());
			Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(this,
					bean.getClassicsPerson().getPosterAddr(), new ImageCallback() {

						@Override
						public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
							String url = (String) ivAvatar.getTag();
							if (url.equals(bitmapUrl)) {
								ivAvatar.setBackgroundColor(0x00000000);
								ivAvatar.setImageBitmap(bitmap);
							}
						}
					}, AsyncImageLoader.CACHE_TYPE_SD, false);
			if (bitmap != null) {
				ivAvatar.setBackgroundColor(0x00000000);
				ivAvatar.setImageBitmap(bitmap);
			}
		}
	}

	private class GetMovieInfoTask extends
			MovieAsyncTask<String, String, ZSQBaseBean<ZSQClassicsPersonDetailBean>> {
		private String mPersonId;

		public GetMovieInfoTask(Activity activity, String personId) {
			super(activity, null, true, true, true);
			this.mPersonId = personId;
		}

		@Override
		protected ZSQBaseBean<ZSQClassicsPersonDetailBean> doInBackground(String... params) {
			lib = MovieLib.getInstance(ZSQMoviePlayerActivity.this);
			try {
				bean = lib.getClassicsPersonDetail(ZSQMoviePlayerActivity.this, mPersonId);
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bean;
		}

		@Override
		protected void onPostExecute(ZSQBaseBean<ZSQClassicsPersonDetailBean> result) {
			super.onPostExecute(result);
			if (result == null) {
//				Toast.makeText(ZSQMoviePlayerActivity.this, "网络连接失败，请稍后再试", Toast.LENGTH_SHORT)
//						.show();
				MessageDialog.getInstance().setData(ZSQMoviePlayerActivity.this, R.string.data_failed_to_load, true);
			} else {
				if (result.getCode().equals("0")) {
//					Toast.makeText(ZSQMoviePlayerActivity.this, bean.getMessage(),
//							Toast.LENGTH_SHORT).show();
					MessageDialog.getInstance().setData(ZSQMoviePlayerActivity.this, result.getMessage(), true);
				} else if (result.getCode().equals("1")) {
					if(!TextUtils.isEmpty(result.getClassicsPerson().getcName())) {
						tvName.setText(result.getClassicsPerson().getcName());
						tvEngName.setText(result.getClassicsPerson().geteName());
						tvCountry.setText(result.getClassicsPerson().getNationality());
						tvBirthplace.setText(result.getClassicsPerson().getBirthplace());
						tvBirthday.setText(result.getClassicsPerson().getBirthday());
						tvProfession.setText(result.getClassicsPerson().getOccupation());
						tvInfo.setText(result.getClassicsPerson().getSummary());
						showStills();
					} else {
						MessageDialog.getInstance().setData(ZSQMoviePlayerActivity.this, result.getMessage(), true);
					}
				}
			}
		}
	}

}
