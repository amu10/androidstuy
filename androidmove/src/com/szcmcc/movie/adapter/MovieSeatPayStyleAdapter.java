package com.szcmcc.movie.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.MoviePayType;

public class MovieSeatPayStyleAdapter extends BaseAdapter {

	private Context myContext;
	
	private List<MoviePayType> moviePayType;
	
	private int movieCinemaNum = 0;
	
	
	private ArrayList<Holder> hList = new ArrayList<Holder>();
	private int curentposition = -1;
	private String payType = "0";
	
	private View pay = null;
	public MovieSeatPayStyleAdapter(Context context,int movieCinemaNum){
		myContext = context;
		this.movieCinemaNum = movieCinemaNum;
	}
	
	public void addList(List<MoviePayType> list){
		moviePayType = list;
	}
	
//	public void setSelectPosition(int position){
//		this.curentposition = position;
//	}
	
	public String getPayType(){
		
		return payType;
	}
	public int getCurrent(){
		return curentposition;
	}
	
	public void setView(View payView){
		this.pay = payView;
	}
	public String getCureenItem(int position){
		return moviePayType.get(position).payTpyeName;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return moviePayType.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return moviePayType.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder;
		if(convertView == null){
			holder = new Holder();
			convertView = LayoutInflater.from(myContext).inflate(R.layout.buytickexchange_choose_list, null);
			holder.checkBox = (ImageView) convertView.findViewById(R.id.checkbox);
			holder.sumMoneyUnit = (TextView)convertView.findViewById(R.id.sumMoneyUnit);
			holder.unit = (TextView)convertView.findViewById(R.id.unit);
			holder.zhifuname = (TextView) convertView.findViewById(R.id.zhifuname);
			holder.help = (ImageView) convertView.findViewById(R.id.help);
			holder.price = (TextView) convertView.findViewById(R.id.priceMoney);
			holder.sumMoney = (TextView) convertView.findViewById(R.id.sumMoney);
			holder.count = (TextView) convertView.findViewById(R.id.count);
			holder.helpMessage = (TextView) convertView.findViewById(R.id.helpMessage);
			convertView.setTag(holder);
			if (!hList.contains(holder)) {
				hList.add(holder);
			}
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (curentposition == position) {
			holder.checkBox.setBackgroundResource(R.drawable.check);
		} else {
			holder.checkBox.setBackgroundResource(R.drawable.uncheck);
		}
		System.out.println("dongdianzhouMovieSeatPayStyleAdapter3" + moviePayType);
		if(moviePayType != null && moviePayType.size() > 0){
//			if(!moviePayType.get(position).payTpyeName.equals("手机支付")){//由于当钱仅支持手机支付，所以屏蔽了其他的支付方式
//				viewHoder.checkBox_payStyle.setClickable(false);
//			}
			holder.price.setText(moviePayType.get(position).price);
			holder.zhifuname.setText(moviePayType.get(position).payTpyeName);
			holder.count.setText(movieCinemaNum + "");
			holder.unit.setText(moviePayType.get(position).unit);
			holder.sumMoney.setText((movieCinemaNum * Integer.parseInt(moviePayType.get(position).price)) + "");
			holder.helpMessage.setText(moviePayType.get(position).context);
			holder.sumMoneyUnit.setText(moviePayType.get(position).unit);
			//设置选中状态
			holder.help.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					switch (holder.helpMessage.getVisibility()) {
					case View.GONE:
						holder.helpMessage.setVisibility(View.VISIBLE);
						break;
					case View.VISIBLE:
						holder.helpMessage.setVisibility(View.GONE);
						break;
					}
				}
			});
			holder.checkBox.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (curentposition != position) {
						if (curentposition > -1) {
							hList.get(curentposition).checkBox.setBackgroundResource(R.drawable.uncheck);
						}
						curentposition = position;
						holder.checkBox.setBackgroundResource(R.drawable.check);
						payType = moviePayType.get(position).payTypeId;
						notifyDataSetChanged();
					} else if (curentposition == position) {
						curentposition = -1;
						holder.checkBox.setBackgroundResource(R.drawable.uncheck);
						notifyDataSetChanged();
					}
					if(curentposition == -1){
						pay.setVisibility(View.GONE);
					}else{
						pay.setVisibility(View.VISIBLE);
					}
					
				}
			});
		}
		return convertView;
	}
	
	//计算总钱数
	private int computeSunmoney(int position) {
		int univalence = 0;
		int number = 0;
		if(!TextUtils.isEmpty(moviePayType.get(position).price)){
			univalence  = Integer.parseInt(moviePayType.get(position).price);
			number = movieCinemaNum;
		}
		return univalence * number;
	}
	
	public class Holder {
		ImageView checkBox = null;
		TextView zhifuname = null;
		ImageView help = null;
		TextView price = null;
		TextView sumMoney = null;
		TextView count = null;
		TextView helpMessage = null;
		TextView unit = null;
		TextView sumMoneyUnit = null;
	}

}
