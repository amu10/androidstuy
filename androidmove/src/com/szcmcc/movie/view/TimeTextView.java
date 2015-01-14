package com.szcmcc.movie.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import com.szcmcc.movie.util.Log;

public class TimeTextView extends TextView {

	public TimeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		startTimeThread();
	}

	public TimeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		startTimeThread();
	}

	public TimeTextView(Context context) {
		super(context);
		startTimeThread();
	}

	private void startTimeThread() {
		new Thread(timeThread).start();
	}

	public void stopTime()
	{
		runnalbe=false;
	}
	private String time = null;
	private boolean runnalbe=true;
	private Thread timeThread = new Thread() {
		public void run() {
			while(runnalbe)
			{
				try {
					
					time = fmt.format(new Date(System.currentTimeMillis()));
					Log.i("----com.szcmcc.movie.view.TimeTextView-----------"+time);
					Message mMessage = handler.obtainMessage(1);
					handler.sendMessage(mMessage);
					Thread.sleep(60*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		};
	};
	SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				setText(time);
				handler.removeMessages(1);
				break;

			}
		};
	};
}
