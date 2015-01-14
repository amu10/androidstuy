package com.szcmcc.movie.fragment;

import java.io.IOException;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.itotem.view.pullrefresh.PullToRefreshBase;
import com.itotem.view.pullrefresh.PullToRefreshListView;
import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.activity.ZSQNewsActivity;
import com.szcmcc.movie.adapter.ZSQClassicLinesAdapter;
import com.szcmcc.movie.adapter.ZSQFilmReviewAdapter;
import com.szcmcc.movie.adapter.ZSQMovieNewAdapter;
import com.szcmcc.movie.bean.ZSQBaseBean;
import com.szcmcc.movie.bean.ZSQClassicsWordsBean;
import com.szcmcc.movie.bean.ZSQMovieNewsBean;
import com.szcmcc.movie.bean.ZSQWonderfulCommentBean;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.network.ZSQParse;
import com.szcmcc.movie.view.MessageDialog;

public class NewsFragment extends Fragment {
	protected MovieLib lib;
	private PullToRefreshListView pullToRefreshListView;
	private ZSQBaseBean<ZSQMovieNewsBean> movieNewsBean;// 新闻头条数据
	private ZSQBaseBean<ZSQMovieNewsBean> movieInfoBean;// 电影资讯数据
	private ZSQBaseBean<ZSQWonderfulCommentBean> commentBean;// 精彩影评数据
	private ZSQBaseBean<ZSQClassicsWordsBean> wordsBean;// 经典台词
	private ZSQMovieNewAdapter movieNewAdapter;
	private ZSQMovieNewAdapter moviewInfoAdapter;
	private ZSQFilmReviewAdapter filmReviewAdapter;
	private ZSQClassicLinesAdapter classicLinesAdapter;
	private String arg;
	private String myCurPage = "1";

	public static NewsFragment newInstance(String entry) {
		NewsFragment fragment = new NewsFragment();
		Bundle args = new Bundle();
		args.putString("entry", entry);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		arg = getArguments() != null ? getArguments().getString("entry") : "rk_news";
		if (savedInstanceState != null) {
			if (MovieApplication.ENTRY_NEWS.equals(arg)) {// 头条新闻
				movieNewsBean = (ZSQBaseBean<ZSQMovieNewsBean>) savedInstanceState
						.getSerializable("movieNewsBean");
			} else if (MovieApplication.ENTRY_MOVIE_INFO.equals(arg)) {// 电影资讯
				movieInfoBean = (ZSQBaseBean<ZSQMovieNewsBean>) savedInstanceState
						.getSerializable("movieInfoBean");
			} else if (MovieApplication.ENTRY_FILM_REVIEW.equals(arg)) {// 精彩影评
				commentBean = (ZSQBaseBean<ZSQWonderfulCommentBean>) savedInstanceState
						.getSerializable("commentBean");
			} else if (MovieApplication.ENTRY_CLSAAIC_LINES.equals(arg)) {// 经典台词
				wordsBean = (ZSQBaseBean<ZSQClassicsWordsBean>) savedInstanceState
						.getSerializable("wordsBean");
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(R.layout.zsq_pager_movie_new, container, false);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		pullToRefreshListView = (PullToRefreshListView) getView().findViewById(R.id.list);
		ListView list = pullToRefreshListView.getRefreshableView();
		if (MovieApplication.ENTRY_NEWS.equals(arg)) {// 头条新闻
			if (movieNewAdapter == null) {
				movieNewAdapter = new ZSQMovieNewAdapter(getActivity());
			}
			list.setAdapter(movieNewAdapter);
		} else if (MovieApplication.ENTRY_MOVIE_INFO.equals(arg)) {// 电影资讯
			if (moviewInfoAdapter == null) {
				moviewInfoAdapter = new ZSQMovieNewAdapter(getActivity());
			}
			list.setAdapter(moviewInfoAdapter);
		} else if (MovieApplication.ENTRY_FILM_REVIEW.equals(arg)) {// 精彩影评
			if (filmReviewAdapter == null) {
				filmReviewAdapter = new ZSQFilmReviewAdapter(getActivity());
			}
			list.setAdapter(filmReviewAdapter);
		} else if (MovieApplication.ENTRY_CLSAAIC_LINES.equals(arg)) {// 经典台词
			if (classicLinesAdapter == null) {
				classicLinesAdapter = new ZSQClassicLinesAdapter(getActivity());
			}
			list.setAdapter(classicLinesAdapter);
		}
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (!MovieApplication.ENTRY_CLSAAIC_LINES.equals(arg)) {
					Intent intent = new Intent(getActivity(), ZSQNewsActivity.class);
					if (MovieApplication.ENTRY_NEWS.equals(arg)) {
						intent.putExtra("entry", MovieApplication.ENTRY_NEWS);
						intent.putExtra(
								"newsId",
								String.valueOf(movieNewsBean.getHeadlineNews().get(arg2 - 1)
										.getNewsId()));
					} else if (MovieApplication.ENTRY_MOVIE_INFO.equals(arg)) {
						intent.putExtra("entry", MovieApplication.ENTRY_MOVIE_INFO);
						intent.putExtra(
								"newsId",
								String.valueOf(movieInfoBean.getHeadlineNews().get(arg2 - 1)
										.getNewsId()));
					} else if (MovieApplication.ENTRY_FILM_REVIEW.equals(arg)) {
						intent.putExtra("entry", MovieApplication.ENTRY_FILM_REVIEW);
						intent.putExtra(
								"commentId",
								String.valueOf(commentBean.getComments().get(arg2 - 1)
										.getCommentId()));
					}
					startActivity(intent);
				}
			}
		});
		setListener();// 下拉刷新，上拉加载
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			if (MovieApplication.ENTRY_NEWS.equals(arg)) {// 头条新闻
				if (movieNewsBean == null) {
					new GetMovieInfoTask(getActivity(), MovieApplication.ENTRY_NEWS, myCurPage,
							MovieApplication.DOWN).execute("");
				} else {
					if (movieNewAdapter == null) {
						movieNewAdapter = new ZSQMovieNewAdapter(getActivity());
					}
					movieNewAdapter.setData(movieNewsBean.getHeadlineNews());
				}
			} else if (MovieApplication.ENTRY_MOVIE_INFO.equals(arg)) {// 电影资讯
				if (movieInfoBean == null) {
					new GetMovieInfoTask(getActivity(), MovieApplication.ENTRY_MOVIE_INFO,
							myCurPage, MovieApplication.DOWN).execute("");
				} else {
					if (moviewInfoAdapter == null) {
						moviewInfoAdapter = new ZSQMovieNewAdapter(getActivity());
					}
					moviewInfoAdapter.setData(movieInfoBean.getHeadlineNews());
				}
			} else if (MovieApplication.ENTRY_FILM_REVIEW.equals(arg)) {// 精彩影评
				if (commentBean == null) {
					new GetMovieInfoTask(getActivity(), MovieApplication.ENTRY_FILM_REVIEW,
							myCurPage, MovieApplication.DOWN).execute("");
				} else {
					if (filmReviewAdapter == null) {
						filmReviewAdapter = new ZSQFilmReviewAdapter(getActivity());
					}
					filmReviewAdapter.setData(commentBean.getComments());
				}
			} else if (MovieApplication.ENTRY_CLSAAIC_LINES.equals(arg)) {// 经典台词
				if (wordsBean == null) {
					new GetMovieInfoTask(getActivity(), MovieApplication.ENTRY_CLSAAIC_LINES,
							myCurPage, MovieApplication.DOWN).execute("");
				} else {
					if (classicLinesAdapter == null) {
						classicLinesAdapter = new ZSQClassicLinesAdapter(getActivity());
					}
					classicLinesAdapter.setData(wordsBean.getClassicsWords());
				}
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (MovieApplication.ENTRY_NEWS.equals(arg)) {// 头条新闻
			outState.putSerializable("movieNewsBean", movieNewsBean);
		} else if (MovieApplication.ENTRY_MOVIE_INFO.equals(arg)) {// 电影资讯
			outState.putSerializable("movieInfoBean", movieInfoBean);
		} else if (MovieApplication.ENTRY_FILM_REVIEW.equals(arg)) {// 精彩影评
			outState.putSerializable("commentBean", commentBean);
		} else if (MovieApplication.ENTRY_CLSAAIC_LINES.equals(arg)) {// 经典台词
			outState.putSerializable("wordsBean", wordsBean);
		}
	}

	private void setListener() {
		pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh() {
				if (MovieApplication.ENTRY_NEWS.equals(arg)) {
					new GetMovieInfoTask(getActivity(), arg, "1", MovieApplication.DOWN)
							.execute("");
				} else if (MovieApplication.ENTRY_MOVIE_INFO.equals(arg)) {
					new GetMovieInfoTask(getActivity(), arg, "1", MovieApplication.DOWN)
							.execute("");
				} else if (MovieApplication.ENTRY_FILM_REVIEW.equals(arg)) {
					new GetMovieInfoTask(getActivity(), arg, "1", MovieApplication.DOWN)
							.execute("");
				} else if (MovieApplication.ENTRY_CLSAAIC_LINES.equals(arg)) {
					new GetMovieInfoTask(getActivity(), arg, "1", MovieApplication.DOWN)
							.execute("");
				}
				pullToRefreshListView.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh() {
				int tempCurPage = Integer.parseInt(myCurPage);
				if (MovieApplication.ENTRY_NEWS.equals(arg)) {
					if (tempCurPage < Integer.parseInt(movieNewsBean.getPageCount())) {
						new GetMovieInfoTask(getActivity(), arg, String.valueOf(tempCurPage + 1),
								MovieApplication.UP).execute("");
					} else {
						Toast.makeText(getActivity(), "已加载全部数据", Toast.LENGTH_SHORT).show();
					}
				} else if (MovieApplication.ENTRY_MOVIE_INFO.equals(arg)) {
					if (tempCurPage < Integer.parseInt(movieInfoBean.getPageCount())) {
						new GetMovieInfoTask(getActivity(), arg, String.valueOf(tempCurPage + 1),
								MovieApplication.UP).execute("");
					} else {
						Toast.makeText(getActivity(), "已加载全部数据", Toast.LENGTH_SHORT).show();
					}
				} else if (MovieApplication.ENTRY_FILM_REVIEW.equals(arg)) {
					if (tempCurPage < Integer.parseInt(commentBean.getPageCount())) {
						new GetMovieInfoTask(getActivity(), arg, String.valueOf(tempCurPage + 1),
								MovieApplication.UP).execute("");
					} else {
						Toast.makeText(getActivity(), "已加载全部数据", Toast.LENGTH_SHORT).show();
					}
				} else if (MovieApplication.ENTRY_CLSAAIC_LINES.equals(arg)) {
					if (tempCurPage < Integer.parseInt(wordsBean.getPageCount())) {
						new GetMovieInfoTask(getActivity(), arg, String.valueOf(tempCurPage + 1),
								MovieApplication.UP).execute("");
					} else {
						Toast.makeText(getActivity(), "已加载全部数据", Toast.LENGTH_SHORT).show();
					}
				}
				pullToRefreshListView.onRefreshComplete();
			}
		});
	}

	private class GetMovieInfoTask extends MovieAsyncTask<String, String, String> {
		private String mEntry;
		private String mCurPage;
		private String mType;

		public GetMovieInfoTask(Activity activity, String entry, String curPage, String type) {
			super(activity, null, true, true, true);
			lib = MovieLib.getInstance(getActivity());
			this.mEntry = entry;
			this.mCurPage = curPage;
			this.mType = type;
		}

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			try {
				if (mEntry.equals(MovieApplication.ENTRY_NEWS)) {// 头条新闻
					result = lib.getMovieNews(getActivity(), mCurPage, "1");
				} else if (mEntry.equals(MovieApplication.ENTRY_MOVIE_INFO)) {// 电影资讯
					result = lib.getMovieNews(getActivity(), mCurPage, "2");
				} else if (mEntry.equals(MovieApplication.ENTRY_FILM_REVIEW)) {// 精彩影评
					result = lib.getWonderfulComment(getActivity(), mCurPage);
				} else if (mEntry.equals(MovieApplication.ENTRY_CLSAAIC_LINES)) {// 经典台词
					result = lib.getClassicsWords(getActivity(), mCurPage);
				}
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
				try {
					if (mType.equals(MovieApplication.DOWN)) {// 下拉刷新
						if (mEntry.equals(MovieApplication.ENTRY_FILM_REVIEW)) {
							// 精彩影评
							commentBean = new ZSQParse().parseWonderfulComment(result);
							if(commentBean != null && "1".equals(commentBean.getCode())) {
								if(commentBean.getComments() != null && commentBean.getComments().size() > 0) {
									filmReviewAdapter.setData(commentBean.getComments());
								} else {
									MessageDialog.getInstance().setData(getActivity(), commentBean.getMessage(), false);
								}
							}
						} else if (mEntry.equals(MovieApplication.ENTRY_CLSAAIC_LINES)) {
							// 经典台词
							wordsBean = new ZSQParse().parseClassicsWords(result);
							if(wordsBean != null && "1".equals(wordsBean.getCode())) {
								if(wordsBean.getClassicsWords() != null && wordsBean.getClassicsWords().size() > 0) {
									classicLinesAdapter.setData(wordsBean.getClassicsWords());
								} else {
									MessageDialog.getInstance().setData(getActivity(), wordsBean.getMessage(), false);
								}
							}
						} else if (mEntry.equals(MovieApplication.ENTRY_NEWS)) {
							// 头条新闻
							movieNewsBean = new ZSQParse().parseMovieNews(result);
							if(movieNewsBean != null && "1".equals(movieNewsBean.getCode())) {
								if(movieNewsBean.getHeadlineNews() != null && movieNewsBean.getHeadlineNews().size() > 0) {
									movieNewAdapter.setData(movieNewsBean.getHeadlineNews());
								} else {
									MessageDialog.getInstance().setData(getActivity(), movieNewsBean.getMessage(), false);
								}
							}
							
						} else if (mEntry.equals(MovieApplication.ENTRY_MOVIE_INFO)) {
							// 电影资讯
							movieInfoBean = new ZSQParse().parseMovieNews(result);
							if(movieInfoBean != null && "1".equals(movieInfoBean.getCode())) {
								if(movieInfoBean.getHeadlineNews() != null && movieInfoBean.getHeadlineNews().size() > 0) {
									moviewInfoAdapter.setData(movieInfoBean.getHeadlineNews());
								} else {
									MessageDialog.getInstance().setData(getActivity(), movieInfoBean.getMessage(), false);
								}
							}
						}
					} else if (mType.equals(MovieApplication.UP)) {// 加载更多
						if (mEntry.equals(MovieApplication.ENTRY_FILM_REVIEW)) {
							ZSQBaseBean<ZSQWonderfulCommentBean> tempTommentBean = new ZSQParse()
									.parseWonderfulComment(result);
							commentBean.getComments().addAll(tempTommentBean.getComments());
							filmReviewAdapter.setData(commentBean.getComments());
						} else if (mEntry.equals(MovieApplication.ENTRY_CLSAAIC_LINES)) {
							ZSQBaseBean<ZSQClassicsWordsBean> tempWordsBean = new ZSQParse()
									.parseClassicsWords(result);
							wordsBean.getClassicsWords().addAll(tempWordsBean.getClassicsWords());
							classicLinesAdapter.setData(wordsBean.getClassicsWords());
						} else if (mEntry.equals(MovieApplication.ENTRY_NEWS)) {
							ZSQBaseBean<ZSQMovieNewsBean> tempMovieNewsBean = new ZSQParse()
									.parseMovieNews(result);
							movieNewsBean.getHeadlineNews().addAll(
									tempMovieNewsBean.getHeadlineNews());
							movieNewAdapter.setData(movieNewsBean.getHeadlineNews());
						} else if (mEntry.equals(MovieApplication.ENTRY_MOVIE_INFO)) {
							ZSQBaseBean<ZSQMovieNewsBean> tempMovieInfoBean = new ZSQBaseBean<ZSQMovieNewsBean>();
							tempMovieInfoBean = new ZSQParse().parseMovieNews(result);
							movieInfoBean.getHeadlineNews().addAll(
									tempMovieInfoBean.getHeadlineNews());
							moviewInfoAdapter.setData(movieInfoBean.getHeadlineNews());
						}
						myCurPage = mCurPage;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
