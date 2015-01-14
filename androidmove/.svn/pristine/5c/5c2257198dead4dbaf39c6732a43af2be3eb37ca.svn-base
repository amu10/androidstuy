package com.szcmcc.movie.task;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.szcmcc.movie.network.LoadingDialog;
import com.szcmcc.movie.network.MovieAsyncTask;

public abstract class OrderPayMainTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	private LoadingDialog ld;
	protected Activity taskContext;
	private boolean isShow = true;
	private boolean isCanClose = true;
	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}


	public boolean isCanClose() {
		return isCanClose;
	}

	public void setCanClose(boolean isCanClose) {
		this.isCanClose = isCanClose;
	}

	public OrderPayMainTask(Activity activity, final DialogInterface.OnCancelListener l, final boolean interruptTask,
			final boolean interruptIfRunning,boolean isShow,String loadingText) {
		super();
		taskContext = activity;
		this.isShow = isShow;
		if(isShow&&isCanClose){
			ld = new LoadingDialog(activity);
			if(!TextUtils.isEmpty(loadingText)){
				ld.setLoadingText(loadingText);
			}
			ld.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					if (interruptTask) {
						OrderPayMainTask.this.cancel(interruptIfRunning);
					}
					if (l != null) {
						l.onCancel(dialog);
					}
				}
			});
		}
	}
	public OrderPayMainTask(Activity activity, final DialogInterface.OnCancelListener l, final boolean interruptTask,
			final boolean interruptIfRunning,boolean isShow,boolean isCanClose,String loadingText) {
		super();
		taskContext = activity;
		this.isShow = isShow;
		this.isCanClose = isCanClose;
		if(isShow&&isCanClose){
			ld = new LoadingDialog(activity);
			System.out.println("dongdianzhouMOvieAsync2" + loadingText);
			if(!TextUtils.isEmpty(loadingText)){
				ld.setLoadingText(loadingText);
			}
			ld.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					if (interruptTask) {
						OrderPayMainTask.this.cancel(interruptIfRunning);
					}
					if (l != null) {
						l.onCancel(dialog);
					}
				}
			});
		}
	}

	public void closeDialog(boolean isCanClose){
		if(ld!=null&&isCanClose){
			ld.close();
		}
	}
	@Override
	protected Result doInBackground(Params... params) {
		return null;
	}

	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
		if(ld!=null&&isCanClose){
			ld.close();
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(ld!=null && isShow&&isCanClose){
			ld.show();
		}
	}
}
