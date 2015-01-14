package com.szcmcc.movie.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.szcmcc.movie.R;
import com.szcmcc.movie.activity.LoadingActivity;
import com.szcmcc.movie.activity.SeatSelectedAct;
import com.szcmcc.movie.activity.ZSQMovieActivity;
import com.szcmcc.movie.activity.ZSQMoviePlayerActivity;
import com.szcmcc.movie.activity.ZSQNewsActivity;

public class MessageDialog {

	private Activity activity;
	private String message;
	private boolean needFinish;
	private AlertDialog dialog;
	
	private static MessageDialog instance = null;     
	public static MessageDialog getInstance() {    
		if (instance == null) {
			instance = new MessageDialog();
		}    
		return instance;    
	} 
	
	public void setData(Activity activity, String message, boolean needFinish) {
		this.activity = activity;
		this.message = message;
		this.needFinish = needFinish;
		if(TextUtils.isEmpty(this.message)) {
			this.message = activity.getResources().getString(R.string.data_failed_to_load);
		}
		if(dialog == null) {
			showDialog();
		}
	}
	public void setData(Activity activity, int message, boolean needFinish) {
		this.activity = activity;
		this.message = this.activity.getResources().getString(message);
		this.needFinish = needFinish;
		if(dialog == null) {
			showDialog();
		}
	}
	
//	public MessageDialog(Activity activity, String message, boolean needFinish) {
//		this.activity = activity;
//		this.message = message;
//		this.needFinish = needFinish;
//		if(TextUtils.isEmpty(this.message)) {
//			this.message = activity.getResources().getString(R.string.data_failed_to_load);
//		}
//		if(dialog == null || !dialog.isShowing()) {
//			showDialog();
//		}
//	}
//	public MessageDialog(Activity activity, int message, boolean needFinish) {
//		this.activity = activity;
//		this.message = this.activity.getResources().getString(message);
//		this.needFinish = needFinish;
//		if(dialog == null || !dialog.isShowing()) {
//			showDialog();
//		}
//	}
	
	public void showDialog() {
		Builder builder = new AlertDialog.Builder(activity);
			builder.setTitle("提示")
				.setMessage(message)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						actFinish();
						MessageDialog.this.dialog = null;
					}
				})
				.setOnCancelListener(new DialogInterface.OnCancelListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) {
						dialog.dismiss();
						actFinish();	
						MessageDialog.this.dialog = null;
					}
				}).show();
			dialog = builder.create();
	}
	
	private void actFinish() {
		if(needFinish) {
			if(activity instanceof LoadingActivity) {
				((LoadingActivity)activity).finish();
			} else if(activity instanceof ZSQNewsActivity) {
				((ZSQNewsActivity)activity).finish();
			} else if(activity instanceof ZSQMoviePlayerActivity) {
				((ZSQMoviePlayerActivity)activity).finish();
			} else if(activity instanceof ZSQMovieActivity) {
				((ZSQMovieActivity)activity).finish();
			} else if(activity instanceof SeatSelectedAct) {
				((SeatSelectedAct)activity).finish();
			}
		}
	}
}
