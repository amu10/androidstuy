package com.szcmcc.movie.task;

import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;

import com.szcmcc.movie.bean.Movie;
import com.szcmcc.movie.bean.MovieNewList;
import com.szcmcc.movie.bean.YouHuiNew;
import com.szcmcc.movie.util.Log;

public class MainActTask extends AsyncTask<Object, Void, Object> {

	private UpdateListView mUpdateListView = null;

	private HTTPRequest mHTTPRequest = null;
	private String url = "";
	private MovieNewList mMovieNewList = null;

	private boolean running = true;

	public static interface UpdateListView {
		public void showprogressBar();

		public void initListView();
		public void onListViewListeners();
		public void updateListView();
	}

	public MainActTask(UpdateListView mUpdateListView,
			MovieNewList movieNewList, Map<String, String> params) {
		super();
		this.mUpdateListView = mUpdateListView;
		mMovieNewList = movieNewList;
		mHTTPRequest = new HTTPRequest(url, params);
	}

	public MainActTask(UpdateListView mUpdateListView, MovieNewList movieNewList) {
		super();
		this.mUpdateListView = mUpdateListView;
		mMovieNewList = movieNewList;
		mHTTPRequest = new HTTPRequest(url, new HashMap<String, String>());
	}

	// 缓冲
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Log.i("---before----.MainActTask.onPreExecute()-----------");
//		mMovieNewList.mMovieNews.clear();
		mUpdateListView.showprogressBar();
	}

	// 后台
	@Override
	protected Object doInBackground(Object... params) {
		if (running) {
			Log.i("----running---.MainActTask.doInBackground()-----------");
			// String json = mHTTPRequest.getServerResponseString();
			// parseData(json, mMovieNewList);
			Movie mMovieNew = null;
			for (int i = 0; i < 10; i++) {
				mMovieNew = new Movie();
				mMovieNew.name = "龙门飞甲" + i;
//				mMovieNew.imageUrl = "http://img4.cache.netease.com/ent/2012/2/17/20120217093718959cd.jpg";
//				mMovieNew.status = (i % 2) + "";
//				mMovieNew.scorerate = i +"";
//				mMovieNew.point = i + "分";
//				mMovieNew.date = "11月29日上映";
//				mMovieNew.pinluntext ="22"+i+"人评论";
				mMovieNewList.addMovieNew(mMovieNew);
			}
			
			
			YouHuiNew mYouHuiNew = null;
			for (int i = 0; i < 30; i++) {
				mYouHuiNew = new YouHuiNew();
				mYouHuiNew.name = "金逸国际影城" + i;
				mYouHuiNew.imageUrl = "http://img4.cache.netease.com/ent/2012/2/17/20120217093718959cd.jpg";
				mYouHuiNew.address ="UME国际影城";
				mYouHuiNew.date = "2月12日-2月14日";
				mYouHuiNew.des = "默契比拼 你来比划我来猜默契比拼 你来比划我来猜默契比拼 你来比划我来猜默契比拼 你来比划我来猜默契比拼 你来比划我来猜";
				mYouHuiNew.context = "默契比拼 你来比划我来猜";
				mMovieNewList.addYouHuiNew(mYouHuiNew);
			}
		}
		return null;
	}

	// 更新
	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		if (running) {
			Log.i("---update----.MainActTask.onPostExecute()-----------");
			mUpdateListView.initListView();
			mUpdateListView.onListViewListeners();
		}
	}

	public void stopRunnig() {
		running = false;
		mHTTPRequest.stopConnection();
	}

	private void parseData(String json, MovieNewList mMovieNewList2) {

	}

}
