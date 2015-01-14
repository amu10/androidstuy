package com.szcmcc.movie.activity;
//package com.szcmcc.movie.activity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.RatingBar;
//import android.widget.TextView;
//import android.widget.AdapterView.OnItemClickListener;
//
//import com.szcmcc.movie.R;
//import com.szcmcc.movie.bean.Movie;
//import com.szcmcc.movie.bean.MovieNewList;
//import com.szcmcc.movie.bean.MoviePinLun;
//import com.szcmcc.movie.bean.MoviePinLunList;
//import com.szcmcc.movie.network.AsyncImageLoader;
//import com.szcmcc.movie.task.MainActTask;
//import com.szcmcc.movie.util.Log;
//import com.szcmcc.movie.util.RoateUtil;
//
///**
// * 评论页面
// * 
// * @author Administrator
// * 
// */
//public class PinLunListAct extends BaseActivity implements
//		MainActTask.UpdateListView {
//
//	private Context mContext = PinLunListAct.this;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.pinlunlistact);
//		initBackTitleBar();
//
//		onClick();
////		onFourClick();
//		mMovieNewList = new MovieNewList();
//		mMainActTask = new MainActTask(this, mMovieNewList);
//		mMainActTask.execute();
//		initPinLunList();
//
//	}
//
////	private ImageView imageUrl1 = null;
////	private ImageView imageUrl2 = null;
////	private ImageView imageUrl3 = null;
////	private ImageView imageUrl4 = null;
////
////	private void onFourClick() {
////		imageUrl1 = (ImageView) findViewById(R.id.imageUrl1);
////		imageUrl2 = (ImageView) findViewById(R.id.imageUrl2);
////		imageUrl3 = (ImageView) findViewById(R.id.imageUrl3);
////		imageUrl4 = (ImageView) findViewById(R.id.imageUrl4);
////		imageUrl1.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////				// 剧照
////				Intent intent = new Intent(mContext, MoviePicsAct.class);
////				mContext.startActivity(intent);
////			}
////		});
////		imageUrl2.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////				// 剧照
////				Intent intent = new Intent(mContext, MoviePicsAct.class);
////				mContext.startActivity(intent);
////			}
////		});
////		imageUrl3.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////				// 剧照
////				Intent intent = new Intent(mContext, MoviePicsAct.class);
////				mContext.startActivity(intent);
////			}
////		});
////		imageUrl4.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////				// 剧照
////				Intent intent = new Intent(mContext, MoviePicsAct.class);
////				mContext.startActivity(intent);
////			}
////		});
////
////	}
//
////	private ImageButton play = null;
////	private ImageView shoucang = null;
//	private ImageView write = null;
////	private ImageView share = null;
////
////	private int[] backgrounds = new int[] { R.drawable.shoucang_selector,
////			R.drawable.yishoucang };
////	private boolean hasSave = false;
//
//	private void onClick() {
////		play = (ImageButton) findViewById(R.id.play);
////		shoucang = (ImageView) findViewById(R.id.shoucang);
//		write = (ImageView) findViewById(R.id.write);
////		share = (ImageView) findViewById(R.id.share);
////
////		if (hasSave) {
////			shoucang.setImageResource(backgrounds[1]);
////		} else {
////			shoucang.setImageResource(backgrounds[0]);
////		}
////		play.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////				// 剧照
////				Intent intent = new Intent(mContext, MoviePicsAct.class);
////				mContext.startActivity(intent);
////			}
////		});
////
////		shoucang.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////				Log.i("---shoucang-----onClick-----hasSave" + hasSave);
////				if (hasSave) {
////					shoucang.setImageResource(backgrounds[0]);
////					hasSave = false;
////				} else {
////					shoucang.setImageResource(backgrounds[1]);
////					hasSave = true;
////				}
////			}
////		});
////
//		write.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// 写评论
//				Intent intent = new Intent(mContext, WritePinLunAct.class);
//				mContext.startActivity(intent);
//			}
//		});
////
////		share.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////				openDialog();
////			}
////		});
//	}
//
////	/**
////	 * 对话框
////	 */
////	private String[] items = null;
////
////	private void openDialog() {
////		items = new String[] { "新浪微博分享", "短信分享", "邮件分享", "取消" };
////		new AlertDialog.Builder(mContext).setTitle("您是否要").setItems(items,
////				new DialogInterface.OnClickListener() {
////
////					@Override
////					public void onClick(DialogInterface dialog, int which) {
////
////						switch (which) {
////						case 0:
////							Intent intent = new Intent(mContext,
////									SinaShareAct.class);
////							mContext.startActivity(intent);
////							break;
////						case 1:
////
////							break;
////						}
////					}
////
////				}).show();
////	}
//
//	class MoviePinLunItemClick implements OnClickListener {
//
//		private boolean canrun = true;
//
//		public void setCanRun(boolean flag) {
//			this.canrun = flag;
//		}
//
//		public MoviePinLunItemClick(MoviePinLun item) {
//			this.item = item;
//		}
//
//		private MoviePinLun item;
//
//		public void onClick(View v) {
//
//		}
//
//	}
//
//	// -------------------评论列表------------START
//	public void initPinLunList() {
//		mMoviePinLunList = new MoviePinLunList();
//		MoviePinLun mMoviePinLun = null;
//		for (int i = 0; i < 10; i++) {
//			mMoviePinLun = new MoviePinLun();
//			mMoviePinLun.name = "Rainbow" + i;
//			mMoviePinLun.imageUrl = "图片" + i;
//			mMoviePinLun.time = i + "分钟前";
//			mMoviePinLun.sharecount = i + "5";
//			mMoviePinLun.goodcount = i + "5";
//			if (i % 2 == 0) {
//				mMoviePinLun.point = "5";
//			} else {
//				mMoviePinLun.point = "";
//			}
//
//			mMoviePinLun.ping = "真的不错哦真的不错哦真的不错哦  ";
//			mMoviePinLunList.addMoviePinLun(mMoviePinLun);
//		}
//		morepinlun_listview = (ListView) findViewById(R.id.morepinlun_listview);
//		mPinLunListAdapter = new PinLunListAdapter();
//		morepinlun_listview.setAdapter(mPinLunListAdapter);
//
//	}
//
//	private ListView morepinlun_listview = null;
//	private PinLunListAdapter mPinLunListAdapter = null;
//
//	public class PinLunListAdapter extends BaseAdapter {
//
//		private LayoutInflater layoutFlater;
//
//		public PinLunListAdapter() {
//			super();
//			layoutFlater = (LayoutInflater) mContext
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		}
//
//		public int getCount() {
//			return mMoviePinLunList.mMoviePinLuns.size();
//		}
//
//		public Object getItem(int position) {
//			return position;
//		}
//
//		public long getItemId(int position) {
//			return position;
//		}
//
//		public View getView(int position, View convertView, ViewGroup parent) {
//			final ViewHoldePinLunItem holder;
//			if (convertView == null) {
//				convertView = (LinearLayout) layoutFlater.inflate(
//						R.layout.movie_pinlun_list_item2, null);
//				holder = new ViewHoldePinLunItem();
//				holder.imageUrl = (ImageView) convertView
//						.findViewById(R.id.imageUrl);
//				holder.pointtrate = (RatingBar) convertView
//						.findViewById(R.id.pointtrate);
//				holder.name = (TextView) convertView.findViewById(R.id.name);
//				holder.time = (TextView) convertView.findViewById(R.id.time);
//				// sharecount = (TextView) convertView
//				// .findViewById(R.id.sharecount);
//				// goodcount = (TextView)
//				// convertView.findViewById(R.id.goodcount);
//				holder.point = (TextView) convertView.findViewById(R.id.point);
//				holder.ping = (TextView) convertView.findViewById(R.id.ping);
//				holder.feng_l = (LinearLayout) convertView
//						.findViewById(R.id.feng_l);
//
//				convertView.setTag(holder);
//			} else {
//				holder = (ViewHoldePinLunItem) convertView.getTag();
//			}
//
//			// 分割线
//			// line = (ImageView) layoutFlater.inflate(R.layout.line, null);
//			// 数据
//			MoviePinLun bean = mMoviePinLunList.mMoviePinLuns.get(position);
//
//			// imageUrl.setImageResource(R.drawable.icon);
//			holder.name.setText(bean.name);
//			holder.time.setText(bean.time);
//			// sharecount.setText(bean.sharecount);
//			// goodcount.setText(bean.goodcount);
//			holder.ping.setText(bean.ping);
//			//
//			if ("".equals(bean.point)) {
//				holder.feng_l.setVisibility(View.GONE);
//			} else {
//				holder.feng_l.setVisibility(View.VISIBLE);
//				holder.point.setText(bean.point);
//			}
//
//			return convertView;
//		}
//	}
//
//	private MoviePinLunList mMoviePinLunList = null;
//
//	private static class ViewHoldePinLunItem {
//		// private LinearLayout pic_listview = null;
//		public ImageView imageUrl = null;
//		public RatingBar pointtrate = null;
//		public TextView name = null;
//		public TextView time = null;
//		// public TextView sharecount = null;
//		// public TextView goodcount = null;
//		public TextView point = null;
//		public TextView ping = null;
//		public LinearLayout feng_l = null;
//	}
//
//	// -------------------评论列表----------END
//	// private void showMoviePinLunList(MoviePinLunList mMoviePinLunList) {
//	// LayoutInflater layoutFlater = (LayoutInflater) mContext
//	// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	// if (pic_listview != null) {
//	// pic_listview.removeAllViews();
//	// int lines = mMoviePinLunList.mMoviePinLuns.size();
//	// LinearLayout convertView = null;
//	// MoviePinLun bean = null;
//	// MoviePinLunItemClick listen = null;
//	// for (int i = 0; i < lines; i++) {
//	// // 布局
//	// convertView = (LinearLayout) layoutFlater.inflate(
//	// R.layout.movie_pinlun_list_item, null);
//	// // 分割线
//	// // line = (ImageView) layoutFlater.inflate(R.layout.line, null);
//	//
//	// // 数据
//	// bean = mMoviePinLunList.mMoviePinLuns.get(i);
//	//
//	// imageUrl = (ImageView) convertView.findViewById(R.id.imageUrl);
//	// pointtrate = (RatingBar) convertView
//	// .findViewById(R.id.pointtrate);
//	// name = (TextView) convertView.findViewById(R.id.name);
//	// time = (TextView) convertView.findViewById(R.id.time);
//	// // sharecount = (TextView) convertView
//	// // .findViewById(R.id.sharecount);
//	// // goodcount = (TextView)
//	// // convertView.findViewById(R.id.goodcount);
//	// point = (TextView) convertView.findViewById(R.id.point);
//	// ping = (TextView) convertView.findViewById(R.id.ping);
//	// feng_l = (LinearLayout) convertView.findViewById(R.id.feng_l);
//	//
//	// // imageUrl.setImageResource(R.drawable.icon);
//	// name.setText(bean.name);
//	// time.setText(bean.time);
//	// // sharecount.setText(bean.sharecount);
//	// // goodcount.setText(bean.goodcount);
//	// ping.setText(bean.ping);
//	// //
//	// if ("".equals(bean.point)) {
//	// feng_l.setVisibility(View.GONE);
//	// } else {
//	// feng_l.setVisibility(View.VISIBLE);
//	// point.setText(bean.point);
//	// }
//	// listen = new MoviePinLunItemClick(bean);
//	// // 事件
//	// convertView.setOnClickListener(listen);
//	// pic_listview.addView(convertView);
//	// TextView showmore = (TextView) layoutFlater.inflate(
//	// R.layout.show_more, null);
//	// showmore.setOnClickListener(new OnClickListener() {
//	//
//	// @Override
//	// public void onClick(View v) {
//	// Intent intent = new Intent(mContext,
//	// PinLunListAct.class);
//	// mContext.startActivity(intent);
//	// }
//	// });
//	// pic_listview.addView(showmore);
//	// }
//	// }
//	//
//	// }
//
//	// -------------------影片列表-----------START
//	public void initListView() {
//		more_listview = (ListView) findViewById(R.id.more_listview);
//		mMovieNewListAdapter = new MovieNewListAdapter();
//		more_listview.setAdapter(mMovieNewListAdapter);
//
//		listview_menu = (ImageView) findViewById(R.id.listview_menu);
//		listview_menu.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				if (ismListViewHidden) {
//					more_listview.setVisibility(View.VISIBLE);
//					ismListViewHidden = false;
//				} else {
//					more_listview.setVisibility(View.GONE);
//					ismListViewHidden = true;
//				}
//
//			}
//		});
//
//	}
//
//	private int selected = -1;
//	private ImageView listview_menu = null;
//	private boolean ismListViewHidden = true;
//	private ListView more_listview = null;
//	private MovieNewListAdapter mMovieNewListAdapter = null;
//	private MovieNewList mMovieNewList = null;
//	private MainActTask mMainActTask = null;
//	public static AsyncImageLoader mAsyncImageLoader = null;
//
//	public class MovieNewListAdapter extends BaseAdapter {
//
//		private LayoutInflater layoutFlater;
//
//		public MovieNewListAdapter() {
//			super();
//			layoutFlater = (LayoutInflater) mContext
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		}
//
//		public int getCount() {
//			return mMovieNewList.mMovieNews.size();
//		}
//
//		public Object getItem(int position) {
//			return position;
//		}
//
//		public long getItemId(int position) {
//			return position;
//		}
//
//		public View getView(int position, View convertView, ViewGroup parent) {
//			final ViewHolderListViewItem holder;
//			if (convertView == null) {
//				convertView = layoutFlater.inflate(
//						R.layout.movie_new_list_item, null);
//				holder = new ViewHolderListViewItem();
////				holder.imageUrl = (ImageView) convertView
////						.findViewById(R.id.imageUrl);
////				holder.point = (com.szcmcc.movie.view.RoteTextView) convertView
////						.findViewById(R.id.point);
////				holder.pointbg = (TextView) convertView
////						.findViewById(R.id.pointbg);
////				holder.date = (TextView) convertView.findViewById(R.id.date);
////				holder.name = (TextView) convertView.findViewById(R.id.name);
////				holder.name_orange = (TextView) convertView
////						.findViewById(R.id.name_orange);
//				RoateUtil.tranlateCenter(mContext, holder.point);
//				convertView.setTag(holder);
//			} else {
//				holder = (ViewHolderListViewItem) convertView.getTag();
//				holder.point.setVisibility(View.INVISIBLE);
//				holder.pointbg.setVisibility(View.INVISIBLE);
//				holder.date.setVisibility(View.INVISIBLE);
//			}
//			Movie item = mMovieNewList.mMovieNews.get(position);
//			String url = item.cover_image_url;
//
//			// ImageView cacheImage = holder.imageUrl;
//			// holder.imageUrl.setTag(item.imageUrl);
//			// MainAct.mAsyncImageLoader.getListViewLogoItem(mContext, url,
//			// cacheImage, more_listview);
//
//			holder.point.setText(item.rate);
//			holder.date.setText(item.release_date);
//
//			if ("0".equals(item.upcomming))// 已上映
//			{
//				holder.date.setVisibility(View.INVISIBLE);
//				holder.pointbg.setVisibility(View.VISIBLE);
//				// holder.point.setVisibility(View.VISIBLE);
//				// holder.pointbg.setVisibility(View.VISIBLE);
//				//				
//				if (mMovieNewList.index == position) {
//					holder.point.setVisibility(View.VISIBLE);
//					holder.point.setVisibility(View.VISIBLE);
//					// holder.point2.setVisibility(View.VISIBLE);
//					// holder.point.setVisibility(View.GONE);
//					holder.name_orange.setVisibility(View.VISIBLE);
//					holder.name.setVisibility(View.GONE);
//				} else {
//					holder.point.setVisibility(View.VISIBLE);
//					holder.point.setVisibility(View.VISIBLE);
//					// holder.point2.setVisibility(View.GONE);
//					holder.name_orange.setVisibility(View.GONE);
//					holder.name.setVisibility(View.VISIBLE);
//
//				}
//
//			} else if ("1".equals(item.upcomming))// 未上映
//			{
//				holder.date.setVisibility(View.VISIBLE);
//				holder.point.setVisibility(View.INVISIBLE);
//				holder.pointbg.setVisibility(View.INVISIBLE);
//				if (mMovieNewList.index == position) {
//					// holder.point2.setVisibility(View.VISIBLE);
//					// holder.point.setVisibility(View.GONE);
//					holder.name_orange.setVisibility(View.VISIBLE);
//					holder.name.setVisibility(View.GONE);
//				} else {
//					// holder.point2.setVisibility(View.GONE);
//					holder.name_orange.setVisibility(View.GONE);
//					holder.name.setVisibility(View.VISIBLE);
//
//				}
//			}
//			holder.name.setText(item.name);
//			if (selected != -1) {
//				if (selected == position) {
//					holder.name.setTextColor(0xB35201);
//				} else {
//					holder.name.setTextColor(0xB1AFAD);
//				}
//			}
//
//			return convertView;
//		}
//	}
//
//	private static class ViewHolderListViewItem {
//
//		public ImageView imageUrl;
//		public com.szcmcc.movie.view.RoteTextView point;
//		public TextView pointbg;
//		public TextView date;
//		public TextView name;
//		public TextView name_orange;
//		// public LinearLayout date_l;
//	}
//
//	// -------------------影片列表----------END
//	@Override
//	public void showprogressBar() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void updateListView() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onListViewListeners() {
//		// more_listview.setOnItemSelectedListener(new OnItemSelectedListener()
//		// {
//		//
//		// public void onItemSelected(AdapterView<?> mAdapterView,
//		// View parent, int position, long id) {
//		// showMovieItem(mMovieNewList.mMovieNews.get(position));
//		// }
//		//
//		// public void onNothingSelected(AdapterView<?> arg0) {
//		// }
//		// });
//		more_listview.setOnItemClickListener(new OnItemClickListener() {
//
//			public void onItemClick(AdapterView<?> mAdapterView, View parent,
//					int position, long id) {
//				// 跳到影片界绍
//
//				Intent intent = new Intent(mContext, PinLunListAct.class);
//				mContext.startActivity(intent);
//			}
//
//		});
//
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		Log.i("----------.MovieDesAct.onResume()-");
//		if (more_listview != null) {
//			listview_menu.setVisibility(View.VISIBLE);
//			more_listview.setVisibility(View.GONE);
//		}
//	}
//}
