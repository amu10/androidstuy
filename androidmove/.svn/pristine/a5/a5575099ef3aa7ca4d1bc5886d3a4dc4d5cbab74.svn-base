package com.szcmcc.movie.adapter;

import java.io.Serializable;
import java.util.ArrayList;

import com.szcmcc.movie.R;
import com.szcmcc.movie.activity.BuyTicketExchangeAct;
import com.szcmcc.movie.activity.CinemaPrepareAct;
import com.szcmcc.movie.activity.UserLoginAct;
import com.szcmcc.movie.bean.MovieCinema;
import com.szcmcc.movie.bean.MovieCinema.MovieCinemaInner;
import com.szcmcc.movie.storage.SharedPreferencesUtil;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ZSQSubCouponAreaAdapter extends BaseAdapter {
	private String mPrice;
	private Context mContext;
	private String mCompanyId;
	private String mC_id;
	private String mC_name;
	private ArrayList<MovieCinemaInner> mPriceList;
	private SharedPreferencesUtil shareP;

	public ZSQSubCouponAreaAdapter(Context context, String price, String c_id, String c_name,
			String companyId, ArrayList<MovieCinemaInner> priceList) {
		this.mContext = context;
		this.mPrice = price;
		this.mC_id = c_id;
		this.mC_name = c_name;
		this.mPriceList = priceList;
		this.mCompanyId = companyId;
		shareP = SharedPreferencesUtil.getInstance(this.mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getItem(int position) {
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
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.zsq_item_sub_coupon, null);
			holder = new ViewHolder();
			holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
			holder.ivBuy = (ImageView) convertView.findViewById(R.id.ivBuy);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvPrice.setText("￥" + mPrice);
		holder.ivBuy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (!shareP.getUserName().equals("")) {
					Intent intent = new Intent(mContext, BuyTicketExchangeAct.class);
					intent.putExtra("c_id", mC_id);
					intent.putExtra("companyId", mCompanyId);
					intent.putExtra("c_name", mC_name);
					intent.putExtra("price", mPrice);
//					intent.putExtra("priceList", (Serializable) mPriceList);
					intent.putExtra("payphone", shareP.getUserName());
					mContext.startActivity(intent);
				} else {
					Intent intent = new Intent(mContext, UserLoginAct.class);
					mContext.startActivity(intent);
				}

			}
		});
		return convertView;
	}

	private static class ViewHolder {
		TextView tvPrice;
		ImageView ivBuy;
	}

}
