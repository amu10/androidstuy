package com.szcmcc.movie.fragment;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.szcmcc.movie.R;
import com.szcmcc.movie.activity.ZSQMovieActivity;
import com.szcmcc.movie.adapter.ZSQBoxOfficeTopAdapter;
import com.szcmcc.movie.bean.ZSQBaseBean;
import com.szcmcc.movie.bean.ZSQRank;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.network.ZSQParse;
import com.szcmcc.movie.view.MessageDialog;
import com.tencent.weibo.beans.Message;

public class BoxOfficeFragment extends Fragment {
	protected MovieLib lib;
	private ZSQBaseBean<ZSQRank> rankBean;
	private ArrayList<ZSQRank> chinaRank;
	private ArrayList<ZSQRank> usaRank;
	private ZSQBoxOfficeTopAdapter adapter;
	private TextView tvChina;
	private TextView tvUSA;
	private TextView tvTime;
	private String timeUSA;
	private String timeChina;

	public static BoxOfficeFragment newInstance() {
		BoxOfficeFragment fragment = new BoxOfficeFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			rankBean = (ZSQBaseBean<ZSQRank>) savedInstanceState.getSerializable("rankBean");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(R.layout.zsq_pager_box_office_top, container, false);
		tvChina = (TextView) v.findViewById(R.id.tvChina);
		tvUSA = (TextView)v.findViewById(R.id.tvUSA);
		tvTime = (TextView)v.findViewById(R.id.tvTime);
		if(ZSQMovieActivity.currentFlag == 0){
			tvUSA.setBackgroundResource(R.color.transparent);
			tvChina.setBackgroundResource(R.color.blue_top);
			tvChina.setTextColor(getResources().getColor(R.color.black));
			tvUSA.setTextColor(getResources().getColor(R.color.zsq_white));
		}else{
			tvChina.setBackgroundResource(R.color.transparent);
			tvUSA.setBackgroundResource(R.color.blue_top);
			tvUSA.setTextColor(getResources().getColor(R.color.black));
			tvChina.setTextColor(getResources().getColor(R.color.zsq_white));
		}
		return v;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			if (rankBean != null) {
				if (adapter == null) {
					adapter = new ZSQBoxOfficeTopAdapter(getActivity());
				}
				adapter.setData(rankBean.getRank());
			} else {
				new GetMovieInfoTask(getActivity(), "0").execute("");
			}
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ListView listTop = (ListView) getView().findViewById(R.id.listTop);
//		if (rankBean != null) {
//			try{
//			tvTime.setText(rankBean.getRank().get(0).getRankDate());
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
		// 内地票房按钮
		
		tvChina.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ZSQMovieActivity.currentFlag = 0;
				tvUSA.setBackgroundResource(R.color.transparent);
				tvChina.setBackgroundResource(R.color.blue_top);
				tvChina.setTextColor(getResources().getColor(R.color.black));
				tvUSA.setTextColor(getResources().getColor(R.color.zsq_white));
				tvTime.setText(timeChina);
				if (chinaRank == null) {
					new GetMovieInfoTask(getActivity(), "0").execute("");
				} else {
					adapter.setData(chinaRank);
				}
			}
		});
		// 北美票房按钮
		
		tvUSA.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ZSQMovieActivity.currentFlag = 1;
				tvChina.setBackgroundResource(R.color.transparent);
				tvUSA.setBackgroundResource(R.color.blue_top);
				tvUSA.setTextColor(getResources().getColor(R.color.black));
				tvChina.setTextColor(getResources().getColor(R.color.zsq_white));
				tvTime.setText(timeUSA);
				if (usaRank == null) {
					new GetMovieInfoTask(getActivity(), "1").execute("");
				} else {
					adapter.setData(usaRank);
				}
			}
		});
		if (adapter == null) {
			adapter = new ZSQBoxOfficeTopAdapter(getActivity());
		}
		listTop.setAdapter(adapter);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("rankBean", (Serializable) rankBean);
	}

	private class GetMovieInfoTask extends MovieAsyncTask<String, String, String> {
		private String mRankArea;

		public GetMovieInfoTask(Activity activity, String rankArea) {
			super(activity, null, true, true, true);
			lib = MovieLib.getInstance(getActivity());
			this.mRankArea = rankArea;
		}

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			try {
				result = lib.getMovieRanking(getActivity(), mRankArea);
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
				try{
				rankBean = new ZSQParse().parseMovieRanking(result);
				if (rankBean != null && rankBean.getCode().equals("1")) {
					if (mRankArea.equals("0")) {
						chinaRank = (ArrayList<ZSQRank>) rankBean.getRank();
						if(chinaRank != null && chinaRank.size() > 0) {
							adapter.setData(chinaRank);
							timeChina = chinaRank.get(0).getRankDate();
							tvTime.setText(timeChina);
						} else {
							MessageDialog.getInstance().setData(getActivity(), rankBean.getMessage(), false);
						}
					} else if (mRankArea.equals("1")) {
						usaRank = (ArrayList<ZSQRank>) rankBean.getRank();
						if(usaRank != null && usaRank.size() > 0) {
							adapter.setData(usaRank);
							timeUSA = usaRank.get(0).getRankDate();
							tvTime.setText(timeUSA);
						} else {
							MessageDialog.getInstance().setData(getActivity(), rankBean.getMessage(), false);
						}
					}

				} else if (rankBean.getCode().equals("0")) {
					MessageDialog.getInstance().setData(getActivity(), rankBean.getMessage(), false);
//					Toast.makeText(getActivity(), rankBean.getMessage(), Toast.LENGTH_SHORT).show();
				} else {
					MessageDialog.getInstance().setData(getActivity(), R.string.data_failed_to_load, false);
//					Toast.makeText(getActivity(), "网络连接失败，请稍后再试", Toast.LENGTH_SHORT).show();
				}
			}catch(Exception e){
				e.printStackTrace();
				MessageDialog.getInstance().setData(getActivity(), R.string.data_failed_to_load, false);
//				Toast.makeText(getActivity(), "网络连接失败，请稍后再试", Toast.LENGTH_SHORT).show();
			}
				}
		}
	}

}
