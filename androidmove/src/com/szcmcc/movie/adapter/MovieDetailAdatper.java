package com.szcmcc.movie.adapter;


//public class MovieDetailAdatper extends BaseAdapter {
//	   private Context mContext;
//		
//		ArrayList<Comment> list;
//		private MovieApplication app;
//		private 
//	    public MovieDetailAdatper(Context context,ArrayList<Comment> list){
//	    	mContext = context;
//	    	app = (MovieApplication)mContext.getApplicationContext();
//	    	this.list = list;
//	    }
//		
//		@Override
//		public int getCount() {
//			return list.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return position;
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			final Holder holder;
//			if (convertView == null) {
//				convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_movie_detail_comment_item, parent, false);
//				holder = new Holder();
//				if(position==0){
//					holder.pinglun = (ImageView) convertView.findViewById(R.id.pinglun);
//				}
//				holder.imageUrl = (ImageView)convertView.findViewById(R.id.imageUrl);
//				holder.name = (TextView) convertView.findViewById(R.id.name);
//				holder.point = (TextView) convertView.findViewById(R.id.point);
//				holder.time = (TextView) convertView.findViewById(R.id.time);
//				holder.ping = (TextView) convertView.findViewById(R.id.ping);
//				holder.pointtrate = (RatingBar) convertView.findViewById(R.id.pointtrate);
//				convertView.setTag(holder);
//			}else{
//				holder = (Holder) convertView.getTag();
//			}
//			
//			Comment comment = list.get(position);
//			
//			holder.name.setText(list.get(position).nickname);
//			holder.time.setText(list.get(position).comment_time);
//			holder.ping.setText(list.get(position).content);
//			holder.pinglun.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					Intent in = new Intent(mContext, WritePinLunAct.class);
//					in.putExtra("m_id", m_id);
//					in.putExtra("upcomming", upcomming);
//					in.putExtra("name", name.getText());
//					in.putExtra("imageUrl", cover_image_url);
//					mContext.startActivity(in);
//				}
//			});
//			if(comment!=null&&URLUtil.isHttpUrl(comment.head_image)){
//				holder.imageUrl.setTag(comment.head_image);
//				Bitmap bitmap = app.getAsyncImageLoader().loadBitmapForView(mContext, comment.head_image, new ImageCallback() {
//					
//					@Override
//					public void imageLoaded(Bitmap bitmap, String bitmapUrl) {
//						String url = (String)holder.imageUrl.getTag();
//						if(url.equals(bitmapUrl)){
//							holder.imageUrl.setImageBitmap(bitmap);
//						}
//					}
//				}, AsyncImageLoader.CACHE_TYPE_SD);
//				if(bitmap!=null){
//					holder.imageUrl.setImageBitmap(bitmap);
//				}
//			}
//			
//			
//			return convertView;
//		}
//		private static class Holder {
//			public ImageView imageUrl;
//			public TextView name,point,time,ping;
//			public RatingBar pointtrate;
//			public ImageView pinglun = null;
//		}
//	
//}
