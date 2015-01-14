package com.szcmcc.movie.fragment;

import java.util.ArrayList;

import com.szcmcc.movie.R;
import com.szcmcc.movie.activity.CinemaPrepareAct;
import com.szcmcc.movie.adapter.ZSQCinemaAdapter;
import com.szcmcc.movie.bean.MovieCinema;
import com.szcmcc.movie.bean.MovieCinemaList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CinemaFragment extends Fragment implements OnItemClickListener {
	private MovieCinemaList movieCinemaList = null;// 启动新界面传的参数
	private ArrayList<MovieCinema> mMovieCinemas;
	private ListView listView;
	private ZSQCinemaAdapter cinemaAdapter;

	public static CinemaFragment getInstance(ArrayList<MovieCinema> movieCinemas,
			MovieCinemaList movieCinemaList) {
		CinemaFragment fragment = new CinemaFragment();
		Bundle args = new Bundle();
		args.putSerializable("movieCinemas", movieCinemas);
		args.putSerializable("movieCinemaList", movieCinemaList);
		fragment.setArguments(args);
		return fragment;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMovieCinemas = (ArrayList<MovieCinema>) (getArguments() != null ? getArguments()
				.getSerializable("movieCinemas") : null);
		movieCinemaList = (MovieCinemaList) (getArguments() != null ? getArguments()
				.getSerializable("movieCinemaList") : null);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(R.layout.zsq_pager_movie_cinema, container, false);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listView = (ListView) getView().findViewById(R.id.listCinema);
		listView.setOnItemClickListener(this);
		if (cinemaAdapter == null) {
			cinemaAdapter = new ZSQCinemaAdapter(getActivity());
			listView.setAdapter(cinemaAdapter);
		}
		cinemaAdapter.setData(mMovieCinemas);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg0.getId()) {
		case R.id.listCinema:
			Intent intent = new Intent(getActivity(), CinemaPrepareAct.class);
			intent.putExtra("movieCinema", cinemaAdapter.getItem(arg2));
			intent.putExtra("movieCinemaList", movieCinemaList);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

}
