package com.szcmcc.movie.network;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.TextUtils;

public abstract class MovieAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	private LoadingDialog ld;
	protected Activity taskContext;
	private boolean isShow = true;
	
	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public MovieAsyncTask(Activity activity, final DialogInterface.OnCancelListener l, final boolean interruptTask,
			final boolean interruptIfRunning,boolean isShow) {
		super();
		taskContext = activity;
		this.isShow = isShow;
		if(isShow){
			ld = new LoadingDialog(activity);
			ld.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					if (interruptTask) {
						MovieAsyncTask.this.cancel(interruptIfRunning);
					}
					if (l != null) {
						l.onCancel(dialog);
					}
				}
			});
		}
	}
	public MovieAsyncTask(Activity activity, final DialogInterface.OnCancelListener l, final boolean interruptTask,
			final boolean interruptIfRunning,boolean isShow,String loadingText) {
		super();
		taskContext = activity;
		this.isShow = isShow;
		if(isShow){
			ld = new LoadingDialog(activity);
			if(!TextUtils.isEmpty(loadingText)){
				ld.setLoadingText(loadingText);
			}
			ld.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					if (interruptTask) {
						MovieAsyncTask.this.cancel(interruptIfRunning);
					}
					if (l != null) {
						l.onCancel(dialog);
					}
				}
			});
		}
	}

	@Override
	protected Result doInBackground(Params... params) {
		return null;
	}

	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
		if(ld!=null){
			ld.close();
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(ld!=null && isShow){
			ld.show();
		}
	}
}
