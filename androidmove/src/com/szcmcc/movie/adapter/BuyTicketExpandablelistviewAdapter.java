//package com.szcmcc.movie.adapter;
//
//import java.util.ArrayList;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.szcmcc.movie.R;
//import com.szcmcc.movie.bean.BuyTicketInfoBean;
//import com.szcmcc.movie.view.BuyTicketExpandableChildView;
//
//public class BuyTicketExpandablelistviewAdapter extends BaseExpandableListAdapter {
//
//	private Context context;
//	
//	private String[] area = {"福田区", "罗湖区"};
//	
//	private ArrayList<BuyTicketInfoBean> childlist;
//	
//	private View[] view;
//	/** 
//	 * 当前点击的二级目录
//	 */
//	private int childposition;
//	
//	public BuyTicketExpandablelistviewAdapter(Context context) {
//		this.context = context;
//	}
//	
//	@Override
//	public int getGroupCount() {
//		// TODO Auto-generated method stub
//		return area.length;
//		
//	}
//
//	@Override
//	public int getChildrenCount(int groupPosition) {
//		if(null == childlist) {
//			return 0;
//		}
//		return 3;
//	}
//
//	@Override
//	public Object getGroup(int groupPosition) {
//		// TODO Auto-generated method stub
//		return "haha";
//	}
//
//	@Override
//	public Object getChild(int groupPosition, int childPosition) {
//		// TODO Auto-generated method stub
//		return childPosition;
//	}
//
//	@Override
//	public long getGroupId(int groupPosition) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public long getChildId(int groupPosition, int childPosition) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public boolean hasStableIds() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//		if(null == convertView){
//			convertView = LayoutInflater.from(context).inflate(R.layout.add_buyticket_listview_parent_view_layout,null);
//		}
//		TextView areaText = (TextView) convertView.findViewById(R.id.add_buyticket_listview_parent_view_layout_area);
//		areaText.setText(area[groupPosition]);
//		ImageView groupview_arrow = (ImageView) convertView.findViewById(R.id.groupview_arrow);
//		//设置GroupView箭头图片
//		if(isExpanded){
//			groupview_arrow.setImageResource(R.drawable.arrow_list_up);
//		}else{
//			groupview_arrow.setImageResource(R.drawable.arrow_list_down);
//		}
//		return convertView;
//	}
//
//	@Override
//	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
////		convertView = view[childPosition];
//		if(null == convertView) {
//			convertView = new BuyTicketExpandableChildView(context, parent);
////			convertView.setTag(childPosition);
//		}
//		((BuyTicketExpandableChildView)convertView).setData(childlist.get(childPosition).getArea(),childPosition, this.childposition);
//		return convertView;
//
////		if(null == view[childPosition]) {
////			view[childPosition] = new BuyTicketExpandableChildView(context, parent);
//////			buyticketexpandablechildview[childPosition].setTag(childPosition);
////		}
////		System.out.println("0000000----"+view.length);
////		((BuyTicketExpandableChildView)view[childPosition]).setData(childlist.get(childPosition).getArea(),childPosition, this.childposition);
////		return view[childPosition];
//	}
//
//	@Override
//	public boolean isChildSelectable(int groupPosition, int childPosition) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	public void setData(ArrayList<BuyTicketInfoBean> childlist) {
//		this.childlist = childlist;
//		if(null != view) {
//			view = null;
//		}
//		view = new View[childlist.size()];
//		//初始二级目录索引传-1
//		setChildPosition(-1);
//	}
//	
//	/**
//	 * 当前点击的二级目录索引
//	 * @param childposition
//	 */
//	public void setChildPosition(int childposition) {
//		this.childposition = childposition;
//		notifyDataSetChanged();
//	}
//}
