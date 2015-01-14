package com.szcmcc.movie.fragment;

import java.util.ArrayList;

import com.szcmcc.movie.R;
import com.szcmcc.movie.adapter.ZSQCinemaAdapter;
import com.szcmcc.movie.adapter.ZSQCouponAreaAdapter;
import com.szcmcc.movie.bean.MovieCinema;
import com.szcmcc.movie.bean.MovieCinemaList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CouponAreaFragment extends Fragment {
	private ArrayList<MovieCinema> mMovieCinemas;
	private ListView listView;
	private ZSQCouponAreaAdapter adapter;

	public static CouponAreaFragment getInstance(ArrayList<MovieCinema> movieCinemas) {
		CouponAreaFragment fragment = new CouponAreaFragment();
		Bundle args = new Bundle();
		args.putSerializable("movieCinemas", movieCinemas);
		fragment.setArguments(args);
		return fragment;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMovieCinemas = (ArrayList<MovieCinema>) (getArguments() != null ? getArguments()
				.getSerializable("movieCinemas") : null);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(R.layout.zsq_pager_coupon_area, container, false);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listView = (ListView) getView().findViewById(R.id.listCouponArea);
		if (adapter == null) {
			adapter = new ZSQCouponAreaAdapter(getActivity());
			listView.setAdapter(adapter);
		}
		adapter.setData(mMovieCinemas);
	}

}
