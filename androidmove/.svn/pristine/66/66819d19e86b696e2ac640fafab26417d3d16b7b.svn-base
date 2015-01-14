package com.szcmcc.movie.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.GetOrderTypeBeanInfo.GetOrderTypeBean.CouponBean;

public class PayYouhuiAdapter extends BaseAdapter {
	private Context mContext;
	ArrayList<CouponBean> list;
	private ArrayList<Holder> hList = new ArrayList<Holder>();
	private int curentposition = 0;

	public PayYouhuiAdapter(Context conext) {
		this.mContext = conext;
		list = new ArrayList<CouponBean>();
	}

	public void setData(ArrayList<CouponBean> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCurrent() {
		return curentposition;
	}

	public class Holder {
		TextView name = null;
		ImageView checkBox = null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public CouponBean getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.pay_youhui, null);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.checkBox = (ImageView) convertView.findViewById(R.id.checkbox);
			convertView.setTag(holder);
			if (!hList.contains(holder)) {
				hList.add(holder);
			}
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (list.get(position).type.equals("1")) {
			holder.name.setText("确认使用抵扣卷");
		} else {
			holder.name.setText(list.get(position).name);
		}

		if (curentposition == position) {
			holder.checkBox.setBackgroundResource(R.drawable.check);
		} else {
			holder.checkBox.setBackgroundResource(R.drawable.uncheck);
		}
		holder.checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hList.get(position).checkBox.setBackgroundResource(R.drawable.check);
				for (int i = 0; i < hList.size(); i++) {
					if (position != i)
						hList.get(i).checkBox.setBackgroundResource(R.drawable.uncheck);
				}
				curentposition = position;
				notifyDataSetChanged();
				// if (curentposition != position) {
				// if (curentposition > -1) {
				// hList.get(curentposition).checkBox.setBackgroundResource(R.drawable.uncheck);
				// }
				// curentposition = position;
				// holder.checkBox.setBackgroundResource(R.drawable.check);
				// notifyDataSetChanged();
				// } else if (curentposition == position) {
				// curentposition = -1;
				// holder.checkBox.setBackgroundResource(R.drawable.uncheck);
				// notifyDataSetChanged();
				// }
			}

		});

		return convertView;
	}

}
