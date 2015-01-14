package com.itotem.view.pullrefresh;

import com.itotem.view.waterfall.ItotemWaterView;
import com.itotem.view.waterfall.ItotemWaterView.OnResetViewDataListener;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Adapter;

public class PullToRefreshWaterView extends PullToRefreshBase<ItotemWaterView> {

	public PullToRefreshWaterView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullToRefreshWaterView(Context context, int mode) {
		super(context, mode);
	}

	public PullToRefreshWaterView(Context context) {
		super(context);
	}

	@Override
	protected ItotemWaterView createRefreshableView(Context context, AttributeSet attrs) {
		ItotemWaterView waterView = new ItotemWaterView(context, attrs);
		return waterView;
	}

	@Override
	protected boolean isReadyForPullDown() {
		Log.e(LOG_TAG, "refreshableView.getScrollY()：" + mRefreshableView.getScrollY());
		return mRefreshableView.getScrollY() <= 0;
	}

	@Override
	protected boolean isReadyForPullUp() {
		return mRefreshableView.getScrollY() >= (mRefreshableView.getMaxCloumnHeight() - mRefreshableView.getHeight());
	}

	public void setOnResetViewDataListener(OnResetViewDataListener listener) {
		mRefreshableView.setOnResetViewDataListener(listener);
	}

	public void setAdapter(Adapter adapter) {
		mRefreshableView.setAdapter(adapter);
	}

	/**
	 * activity onPause的时候调用此方法，以释放所有占用的资源
	 */
	public void onPause() {
		mRefreshableView.onPause();
	}

	/**
	 * activity onPause的时候调用此方法，以加载需要加载的资源
	 */
	public void onResume() {
		mRefreshableView.onResume();
	}

}
