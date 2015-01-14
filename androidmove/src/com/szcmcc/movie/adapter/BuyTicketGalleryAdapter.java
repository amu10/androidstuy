package com.szcmcc.movie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.szcmcc.movie.R;

public class BuyTicketGalleryAdapter extends BaseAdapter{

	private Context context;
	
	private int[] imageID;
	
	public BuyTicketGalleryAdapter(Context context) {
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return imageID.length;
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
		if(null == convertView) {
			convertView = LayoutInflater.from(context).inflate(R.layout.buyticket_gallery_image, parent, false);
			ImageView gallery_image = (ImageView) convertView.findViewById(R.id.gallery_image);
			gallery_image.setImageResource(imageID[position]);
		}
		return convertView;
	}

	public void setData(int[] imageID) {
		this.imageID = imageID;
	}
}
