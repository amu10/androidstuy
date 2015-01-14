package com.szcmcc.movie.activity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.MovieCinema;
import com.szcmcc.movie.network.AsyncImageLoader;
import com.szcmcc.movie.network.AsyncImageLoader.ImageCallback;

public class CinemaDesAct extends BaseActivity {

	private Context mContext = CinemaDesAct.this;
	private String imageUrl = "", address = "", tel = "", opentimes = "", car = "", c_id = "", c_name = "", latitude = "", longitude = "",
			ordertype = "";
	private TextView name = null;
	private ScrollView allMessage = null;
	private ImageView imageView = null;
	private ImageView bgImage = null;
	private MovieCinema movieCinema = null;
	private boolean isCanPhone = true;
	private TextView gomap = null;
	private TextView gophone = null;
	private TextView opentime = null;
	private TextView traffic = null;
	private ProgressBar loading_progress = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cinemadesact);
		initTitleBar();
		findView();
		initData();
		onclick();
	}

	

	private void findView() {
		imageView = (ImageView) findViewById(R.id.imageView);
		allMessage = (ScrollView) findViewById(R.id.scrollview);
		name = (TextView) findViewById(R.id.c_name);
		gomap = (TextView) findViewById(R.id.gomap);
		opentime = (TextView) findViewById(R.id.opentime);
		traffic = (TextView) findViewById(R.id.traffic);
		gophone = (TextView) findViewById(R.id.gophone);
		loading_progress = (ProgressBar) findViewById(R.id.loading_progress);
		bgImage = (ImageView) findViewById(R.id.bgImage);
		setBackGround(bgImage, R.drawable.brown_background);
		Intent in = getIntent();
		if (in.getExtras() != null) {
			c_id = in.getExtras().getString("c_id");
			movieCinema = (MovieCinema) in.getSerializableExtra("movieCinema");
		}
	}

	private void initData() {
		try {
			imageUrl = movieCinema.client_cinema_image_url;
			address = movieCinema.address;
			tel = movieCinema.telephone;
			opentimes = movieCinema.open_time;
			car = movieCinema.traffic;
			c_name = movieCinema.c_name;
			latitude = movieCinema.latitude;
			longitude = movieCinema.longitude;
			ordertype = movieCinema.order_type;
			setImageUrl(imageView, imageUrl);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		if (address.equals("null") || address.equals("")) {
			gomap.setText("暂无信息");
		} else {
			gomap.setText("地址：" + address);
		}
		if (tel.equals("null") || tel.equals("")) {
			gophone.setText("暂无信息");
			isCanPhone = false;
		} else {
			isCanPhone = true;
			gophone.setText(tel);
		}
		if (opentimes.equals("null") || opentimes.equals("")) {
			opentime.setText("暂无信息");
		} else {
			String after = opentimes;
			try {
				Pattern p = Pattern.compile("\\s*|\t|\r|\n");
				Matcher m = p.matcher(opentimes);
				after = m.replaceAll("");

			} catch (Exception e) {
				e.printStackTrace();
			}
			opentime.setText(after);
		}
		if (car.equals("null") || car.equals("")) {
			traffic.setText("暂无信息");
		} else {
			traffic.setText(car);
		}
		if (c_name.equals("null") || c_name.equals("")) {
			name.setText("暂无信息");
		} else {
			name.setText(c_name);
		}
		allMessage.setVisibility(View.VISIBLE);
	}
	
	private void onclick() {
		final ArrayList<String> phoneList = new ArrayList<String>();

		gophone.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					if (isCanPhone) {
						if (!tel.equals("") && !tel.equals("null")) {
							if (tel.split("\\|").length == 1) {
								showDialog(tel.split("\\|")[0]);
							} else if (tel.split("\\|").length > 1) {
								if (phoneList.size() != 0) {
									phoneList.clear();
								}
								for (int i = 0; i < tel.split("\\|").length; i++) {
									phoneList.add(tel.split("\\|")[i]);
								}
								showDialog(phoneList);
							}
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	private void showDialog(final ArrayList<String> sList) {

		LayoutInflater layout = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(R.layout.cinema_phone_dialog, (ViewGroup) this.findViewById(R.id.dialog_layout));
		final AlertDialog builder = new AlertDialog.Builder(CinemaDesAct.this).setView(v).create();
		ListView listView = (ListView) v.findViewById(R.id.listView);
		DialogAdapter adapter = new DialogAdapter(sList);
		listView.setAdapter(adapter);
		TextView pay_no = (TextView) v.findViewById(R.id.pay_no);
		pay_no.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				builder.dismiss();
			}
		});
		builder.show();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				builder.dismiss();
				showDialog(sList.get(position));
			}
		});
	}

	class DialogAdapter extends BaseAdapter {

		ArrayList<String> sList = null;
		TextView phone = null;

		public DialogAdapter(ArrayList<String> sList) {
			this.sList = sList;
		}

		@Override
		public int getCount() {
			return sList.size();
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
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.ciname_dialog_list, parent, false);
				phone = (TextView) convertView.findViewById(R.id.phone);
			}
			phone.setText(sList.get(position));
			return convertView;
		}

	}

	private void showDialog(String phoneNum) {

		final AlertDialog.Builder builder = new AlertDialog.Builder(CinemaDesAct.this);
		builder.setTitle("提示信息!");

		builder.setMessage("您确定要拨打:  " + phoneNum + "  ?");

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.CALL");
				if (tel.contains("|")) {
					intent.setData(Uri.parse("tel:" + tel.split("\\|")[0]));
				} else {
					intent.setData(Uri.parse("tel:" + tel));// mobile为你要拨打的电话号码，模拟器中为模拟器编号也可
				}
				startActivity(intent);
			}
		});
		builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

			}
		});
		builder.create().show();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	

	private void setImageUrl(final ImageView imageView, String url) {
		if (TextUtils.isEmpty(url)) {
			return;
		}
		imageView.setTag(url);
		Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(this, url, new ImageCallback() {

			@Override
			public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
				String url = (String) imageView.getTag();
				if (url.equals(bitmapUrl)) {
					imageView.setImageBitmap(bitmap);
					loading_progress.setVisibility(View.GONE);
				}
			}
		}, AsyncImageLoader.CACHE_TYPE_SD, true);
		if (bitmap != null) {
			loading_progress.setVisibility(View.GONE);
			imageView.setImageBitmap(bitmap);
		}
	}


}
