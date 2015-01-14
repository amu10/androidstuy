package com.szcmcc.movie.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szcmcc.movie.R;
import com.szcmcc.movie.activity.BuyTicketNewAct;
import com.szcmcc.movie.bean.CinemaPrepareMovie;
import com.szcmcc.movie.bean.CinemaPrepareMovie.CinemaPrepareMovieInner.BySeat;
import com.szcmcc.movie.bean.MovieCinema;
import com.szcmcc.movie.util.PublicUtils;

public class BuyTicketExpandableChildView extends LinearLayout {

	private Context context;

	private View view;

	private LinearLayout lvSuppCouponName;

	private TextView cinemaText;

	private ImageView childview_arrow;

	private LinearLayout above_linear, bloew_linear;
	/** 座位票 */
	private LinearLayout seat_ticket;
	// /** 当天影院排期信息*/
	// public ArrayList<BySeat> byseat_list_today;
	// /** 第二天影院排期信息*/
	// public ArrayList<BySeat> byseat_list_romorrow;

	private String m_id;

	private String c_id;

	public String currentDate;

	private String today = PublicUtils.getDateFormat().split(" ")[0];

	private String tomorrow = PublicUtils.getNextDayFormat(today);

	private MovieCinema moviecinema;

	public BuyTicketExpandableChildView(Context context) {
		super(context);
		this.context = context;
		initView();
		onClick();
	}

	private void initView() {
		view = LayoutInflater.from(context).inflate(R.layout.add_buyticket_listview_child_parent_view_layout, null);
		cinemaText = (TextView) view.findViewById(R.id.cinema);
		childview_arrow = (ImageView) view.findViewById(R.id.childview_arrow);
		above_linear = (LinearLayout) view.findViewById(R.id.above_linear);
		bloew_linear = (LinearLayout) view.findViewById(R.id.bloew_linear);
		seat_ticket = (LinearLayout) view.findViewById(R.id.seat_ticket);
		lvSuppCouponName = (LinearLayout) view.findViewById(R.id.lvSuppCouponName);
		addView(view);
	}

	Handler handler = new Handler();

	private void onClick() {
		above_linear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((BuyTicketNewAct) context).setCinemaHeight(above_linear.getHeight());
				if (bloew_linear.getChildCount() == 0) {
					((BuyTicketNewAct) context).setCinameNameStr(moviecinema.c_name);
					((BuyTicketNewAct) context).setCinemaPosition((Integer) getTag() + 1);
					((BuyTicketNewAct) context).removeView3((Integer) getTag());
					currentDate = ((BuyTicketNewAct) context).getCurrentDate();
					// 避免重复请求
					// if(currentDate.equals(today) && null !=
					// byseat_list_today) {
					// addChildView(byseat_list_today);
					// return;
					// } else if(currentDate.equals(tomorrow) && null !=
					// byseat_list_romorrow) {
					// addChildView(byseat_list_romorrow);
					// return;
					// }
					// 根据影院+影片获取排期
					// new
					// GetMovieInfoTask1((BuyTicketNewAct)context).execute();
					/** 当天影院排期信息 */
					ArrayList<BySeat> byseat_list_today;
					/** 第二天影院排期信息 */
					ArrayList<BySeat> byseat_list_romorrow;
					byseat_list_today = new ArrayList<BySeat>();
					byseat_list_romorrow = new ArrayList<BySeat>();
					System.out.println("c_id=========" + c_id);
					for (int i = 0; i < cinemaPrepareMovie.list.size(); i++) {
						if (cinemaPrepareMovie.list.get(i).c_id.equals(c_id)) {
							for (int j = 0; j < cinemaPrepareMovie.list.get(i).list.size(); j++) {
								if (cinemaPrepareMovie.list.get(i).list.get(j).day_time.equals(today)) {
									byseat_list_today.add(cinemaPrepareMovie.list.get(i).list.get(j));
								}
								// 当前日期的下一天
								else if (cinemaPrepareMovie.list.get(i).list.get(j).day_time.equals(tomorrow)) {
									byseat_list_romorrow.add(cinemaPrepareMovie.list.get(i).list.get(j));
								}

							}
						}
					}

					// for (int i = 0; i < result.list.size(); i++) {
					// //排期日期与当前系统时间相同，格式：2013-01-07
					// if(result.list.get(i).day_time.equals(today)) {
					// byseat_list_today.add(result.list.get(i));
					// }
					// //当前日期的下一天
					// else if(result.list.get(i).day_time.equals(tomorrow)) {
					// byseat_list_romorrow.add(result.list.get(i));
					// }
					// }
					byseat_list_today = getBySeat(byseat_list_today);
					byseat_list_romorrow = getBySeat(byseat_list_romorrow);
					if (currentDate.equals(today)) {
						addChildView(byseat_list_today);
					} else if (currentDate.equals(tomorrow)) {
						addChildView(byseat_list_romorrow);
					}
				} else {
					((BuyTicketNewAct) context).setCinameNameStr("");
					((BuyTicketNewAct) context).setCinemaPosition(0);
					removeAllView();
				}
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						((BuyTicketNewAct) context).setListTitle();
					}

				}, 100);

			}
		});
	}

	CinemaPrepareMovie cinemaPrepareMovie;

	/**
	 * 添加二级目录数据
	 * 
	 * @param moviecinema
	 * @param m_id
	 * @param currentDate
	 *            当前选择日期
	 */
	public void setData(MovieCinema moviecinema, String m_id, CinemaPrepareMovie cinemaPrepareMovie) {
		this.moviecinema = moviecinema;
		this.cinemaPrepareMovie = cinemaPrepareMovie;
		cinemaText.setText(moviecinema.c_name);
		this.m_id = m_id;
		this.c_id = moviecinema.c_id;
		// 仅座位票
		if (0 == Integer.parseInt(moviecinema.order_type)) {
		}
		// 仅兑换券
		else if (1 == Integer.parseInt(moviecinema.order_type)) {
			seat_ticket.setVisibility(View.INVISIBLE);
			// 二级菜单不可点
			above_linear.setOnClickListener(null);
		}
		// 座位票+兑换券
		else if (2 == Integer.parseInt(moviecinema.order_type)) {
		}

		/**
		 * 新增suppCouponName字段 zhangsiqi
		 */
		if (moviecinema.suppCouponName != null && !moviecinema.suppCouponName.equals("") &&!moviecinema.suppCouponName.equals("null")) {
			String[] tempArray;
			tempArray = moviecinema.suppCouponName.split(",");
			if (lvSuppCouponName.getChildCount() != 0) {
				lvSuppCouponName.removeAllViews();
			}
			for (int i = 0; i < tempArray.length; i++) {
				View view = LayoutInflater.from(context).inflate(R.layout.zsq_item_hui, null);
				final TextView tvSuppCouponName = (TextView) view.findViewById(R.id.tvSuppCouponName);
				final LinearLayout lv = (LinearLayout) view.findViewById(R.id.lv);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				// lp.setMargins(6, 6, 0, 0);
				lv.setLayoutParams(lp);
				tvSuppCouponName.setText(tempArray[i]);
				lvSuppCouponName.addView(view);
			}
			lvSuppCouponName.setVisibility(View.VISIBLE);
		} else {
			lvSuppCouponName.setVisibility(View.INVISIBLE);
		}

	}

	/**
	 * 增加三级目录
	 */
	public void addChildView(ArrayList<BySeat> byseat_list) {
		if (null == byseat_list || 0 == byseat_list.size()) {
			ToastAlone.makeText(context, "没有找到您所要查询的信息", Toast.LENGTH_SHORT).show();
			return;
		}
		String nowTime = PublicUtils.getDateFormat().split(" ")[1].substring(0, 5);

		childview_arrow.setImageResource(R.drawable.arrow_list_up);
		for (int i = 0; i < byseat_list.size(); i++) {
			if (today.equals(((BuyTicketNewAct) context).getCurrentDate()) && PublicUtils.isCompareTime(nowTime, byseat_list.get(i).s_time)) {
				continue;
			}
			BuyTicketExpandableChildLinearView buyticketexpandablechildlinearview = new BuyTicketExpandableChildLinearView(context,
					(LinearLayout) view);
			buyticketexpandablechildlinearview.setTag(i);
			buyticketexpandablechildlinearview.setData(moviecinema, byseat_list.get(i), m_id);
			bloew_linear.addView(buyticketexpandablechildlinearview);
		}
		if (bloew_linear.getChildCount() == 0) {
			ToastAlone.makeText(context, "没有找到您所要查询的信息", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 删除三级目录
	 */
	public void removeAllView() {
		bloew_linear.removeAllViews();
		childview_arrow.setImageResource(R.drawable.arrow_list_down);
	}

	// class GetMovieInfoTask1 extends MovieAsyncTask<String, String,
	// CinemaPrepareMovieCinema> {
	// public String exception;
	//
	// public GetMovieInfoTask1(Activity activity) {
	// super(activity, null, true, true, true);
	// }
	//
	// @Override
	// protected CinemaPrepareMovieCinema doInBackground(String... params) {
	// CinemaPrepareMovieCinema cinemaPrepareMovieCinema = null;
	// try {
	// if (!c_id.equals("") && !m_id.equals("")) {
	// System.out.println("c-   " + c_id + "      " + m_id);
	// cinemaPrepareMovieCinema =
	// ((BuyTicketNewAct)context).lib.getTicketByMovieAndCinema(c_id, m_id);
	// }
	// } catch (HttpException e) {
	// exception = getResources().getString(R.string.exception_network);
	// e.printStackTrace();
	// } catch (IOException e) {
	// exception = getResources().getString(R.string.exception_network);
	// e.printStackTrace();
	// } catch (JSONException e) {
	// exception = getResources().getString(R.string.exception_json);
	// e.printStackTrace();
	// } catch (NullPointerException e) {
	// e.printStackTrace();
	// }
	// return cinemaPrepareMovieCinema;
	// }
	//
	// @Override
	// protected void onPostExecute(CinemaPrepareMovieCinema result) {
	// super.onPostExecute(result);
	// if (result != null && result.isSuccess()) {
	//
	// byseat_list_today = new ArrayList<BySeat>();
	// byseat_list_romorrow = new ArrayList<BySeat>();
	//
	// if(null == result.list) {
	// Toast.makeText(context, "没有找到您所要查询的信息", Toast.LENGTH_SHORT).show();
	// return;
	// }
	// for(int i = 0;i<cinemaPrepareMovie.list.size();i++){
	// if(cinemaPrepareMovie.list.get(i).c_id.equals(c_id)){
	// for(int j = 0;j<cinemaPrepareMovie.list.get(i).list.size();j++){
	// if(cinemaPrepareMovie.list.get(i).list.get(j).day_time.equals(today)) {
	// byseat_list_today.add(cinemaPrepareMovie.list.get(i).list.get(j));
	// }
	// //当前日期的下一天
	// else
	// if(cinemaPrepareMovie.list.get(i).list.get(j).day_time.equals(tomorrow))
	// {
	// byseat_list_romorrow.add(cinemaPrepareMovie.list.get(i).list.get(j));
	// }
	//
	// }
	// }
	// }
	//
	// // for (int i = 0; i < result.list.size(); i++) {
	// // //排期日期与当前系统时间相同，格式：2013-01-07
	// // if(result.list.get(i).day_time.equals(today)) {
	// // byseat_list_today.add(result.list.get(i));
	// // }
	// // //当前日期的下一天
	// // else if(result.list.get(i).day_time.equals(tomorrow)) {
	// // byseat_list_romorrow.add(result.list.get(i));
	// // }
	// // }
	// byseat_list_today = getBySeat(byseat_list_today);
	// byseat_list_romorrow = getBySeat(byseat_list_romorrow);
	// if(currentDate.equals(today)) {
	// addChildView(byseat_list_today);
	// }
	// else if(currentDate.equals(tomorrow)) {
	// addChildView(byseat_list_romorrow);
	// }
	// // } else {
	// // Toast.makeText(context, "加载失败，请尝试重新加载！", Toast.LENGTH_LONG).show();
	// }
	// }
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// }
	//
	// }
	/**
	 * 排序的影院排期信息（晚--早）
	 * 
	 * @param list
	 * @return
	 */
	private ArrayList<BySeat> getBySeat(ArrayList<BySeat> list) {
		if (null != list) {
			Comparator<BySeat> comparator;
			comparator = new TimeHighToLowComparator();
			Collections.sort(list, comparator);
		}
		return list;
	}

	public class TimeHighToLowComparator implements Comparator<BySeat> {
		@Override
		public int compare(BySeat itemBean1, BySeat itemBean2) {
			int time1 = 0;
			int time2 = 0;
			try {
				if (itemBean1.s_time != null && !"".equals(itemBean1.s_time)) {
					// meters1 = Double.parseDouble(itemBean1.meters);
					time1 = Integer.parseInt(itemBean1.s_time.split(":")[0] + itemBean1.s_time.split(":")[1]);
				}
				if (itemBean2.s_time != null && !"".equals(itemBean2.s_time)) {
					time2 = Integer.parseInt(itemBean2.s_time.split(":")[0] + itemBean2.s_time.split(":")[1]);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			if (time1 < time2) {
				return -1;
			} else if (time1 > time2) {
				return 1;
			} else {
				return 0;
			}
		}

	}
}
