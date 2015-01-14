package com.szcmcc.movie.fragment;

import java.io.IOException;

import org.apache.http.HttpException;
import org.json.JSONException;

import com.itotem.view.pullrefresh.PullToRefreshBase;
import com.itotem.view.pullrefresh.PushToRefreshGridView;
import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.activity.ZSQMoviePlayerActivity;
import com.szcmcc.movie.adapter.ZSQMoviePlayerAdapter;
import com.szcmcc.movie.bean.ZSQBaseBean;
import com.szcmcc.movie.bean.ZSQClassicsPersonBean;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.network.ZSQParse;
import com.szcmcc.movie.view.MessageDialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PlaysFragment extends Fragment {
	protected MovieLib lib;
	private ZSQMoviePlayerAdapter adapter;
	private ZSQBaseBean<ZSQClassicsPersonBean> bean;
	private PushToRefreshGridView pushToRefreshGridView;
	private String myCurPage = "1";

	public static PlaysFragment newInstance() {
		PlaysFragment fragment = new PlaysFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			bean = (ZSQBaseBean<ZSQClassicsPersonBean>) savedInstanceState.getSerializable("bean");
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("bean", bean);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(R.layout.zsq_pager_movie_player, container, false);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		pushToRefreshGridView = (PushToRefreshGridView) getView().findViewById(R.id.gv);
		GridView gvPlayer = pushToRefreshGridView.getRefreshableView();
		if (adapter == null) {
			adapter = new ZSQMoviePlayerAdapter(getActivity());
		}
		gvPlayer.setAdapter(adapter);
		// 角色点击事件
		gvPlayer.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getActivity(), ZSQMoviePlayerActivity.class);
				intent.putExtra("personId",
						String.valueOf(bean.getClassicsPersons().get(arg2).getPersonId()));
				startActivity(intent);
			}
		});
		setListener();// 下拉刷新，上拉加载
	}

	private void setListener() {
		pushToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh() {
				new GetMovieInfoTask(getActivity(), "1", MovieApplication.DOWN).execute("");
				pushToRefreshGridView.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh() {
				int tempCurPage = Integer.parseInt(myCurPage);
				if (tempCurPage < Integer.parseInt(bean.getPageCount())) {
					new GetMovieInfoTask(getActivity(), String.valueOf(tempCurPage + 1),
							MovieApplication.UP).execute("");
				} else {
					Toast.makeText(getActivity(), "已加载全部数据", Toast.LENGTH_SHORT).show();
				}
				pushToRefreshGridView.onRefreshComplete();
			}
		});
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			if (bean == null) {
				new GetMovieInfoTask(getActivity(), "1", MovieApplication.DOWN).execute("");
			} else {
				if (adapter == null) {
					adapter = new ZSQMoviePlayerAdapter(getActivity());
				}
				adapter.setData(bean.getClassicsPersons());
			}
		}
	}

	private class GetMovieInfoTask extends MovieAsyncTask<String, String, String> {
		private String mCurPage;
		private String mType;

		public GetMovieInfoTask(Activity activity, String curPage, String type) {
			super(activity, null, true, true, true);
			lib = MovieLib.getInstance(getActivity());
			this.mCurPage = curPage;
			this.mType = type;
		}

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			try {
				result = lib.getClassicsPerson(getActivity(), mCurPage);
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result == null) {
//				Toast.makeText(getActivity(), "网络连接失败，请稍后再试", Toast.LENGTH_SHORT).show();
				MessageDialog.getInstance().setData(getActivity(), R.string.data_failed_to_load, false);
			} else {
				if (mType.equals(MovieApplication.DOWN)) {//下拉刷新
					bean = new ZSQParse().parseClassicsPerson(result);
					if (bean != null && bean.getCode().equals("1")) {
						if(bean.getClassicsPersons() != null && bean.getClassicsPersons().size() > 0) {
							adapter.setData(bean.getClassicsPersons());
						} else {
							MessageDialog.getInstance().setData(getActivity(), bean.getMessage(), false);
						}
						myCurPage = mCurPage;
					} else if (bean.getCode().equals("0")) {
//						Toast.makeText(getActivity(), bean.getMessage(), Toast.LENGTH_SHORT).show();
						MessageDialog.getInstance().setData(getActivity(), bean.getMessage(), false);
					} else {
//						Toast.makeText(getActivity(), "网络连接失败，请稍后再试", Toast.LENGTH_SHORT).show();
						MessageDialog.getInstance().setData(getActivity(), R.string.data_failed_to_load, false);
					}
				} else if (mType.equals(MovieApplication.UP)) {
					ZSQBaseBean<ZSQClassicsPersonBean> tempBean = new ZSQParse()
							.parseClassicsPerson(result);
					if (tempBean.getCode().equals("1")) {
						bean.getClassicsPersons().addAll(tempBean.getClassicsPersons());
						adapter.setData(bean.getClassicsPersons());
						myCurPage = mCurPage;
					} else if (tempBean.getCode().equals("0")) {
//						Toast.makeText(getActivity(), tempBean.getMessage(), Toast.LENGTH_SHORT)
//								.show();
						MessageDialog.getInstance().setData(getActivity(), tempBean.getMessage(), false);
					} else {
//						Toast.makeText(getActivity(), "网络连接失败，请稍后再试", Toast.LENGTH_SHORT).show();
						MessageDialog.getInstance().setData(getActivity(), R.string.data_failed_to_load, false);
					}

				}
			}
		}
	}

}
