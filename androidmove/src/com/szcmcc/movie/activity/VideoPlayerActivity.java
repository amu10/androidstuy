package com.szcmcc.movie.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.R;
import com.szcmcc.movie.view.ToastAlone;

public class VideoPlayerActivity extends Activity {
	private static final String TAG = "VideoPlayerActivity";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videoplayer);
		
		getDataFromIntent();
		iniView();
	}


	@Override
	public void onStop() {
		super.onStop();
		// CmccDataStatistics.onStop(this);
		// your code
	}

	View admar_layout;

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			admar_layout.setVisibility(View.GONE);
			video_layout.setVisibility(View.VISIBLE);
		}
	};

	VideoView videoView;
	View pro_layout, video_layout;

	private void iniView() {
		video_layout = findViewById(R.id.video_layout);
		video_layout.setVisibility(View.VISIBLE);
		videoView = (VideoView) findViewById(R.id.videoView);
		pro_layout = findViewById(R.id.pro_layout);
		videoView.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				pro_layout.setVisibility(View.GONE);
			}
		});

		// videoView.setVideoPath(vedioUrl);
		if (TextUtils.isEmpty(vedioUrl)) {
			Toast.makeText(this, "视频地址为空", Toast.LENGTH_SHORT).show();
		} else {
			if (vedioUrl.toLowerCase().startsWith("rtsp://")) {
				Log.e(TAG, "开始播放视频地址:" + vedioUrl);
				System.out.println("vedioUrl----"+vedioUrl);
				videoView.setVideoURI(Uri.parse(vedioUrl));
				// 控制暂停和播放
				// videoView.setMediaController(new MediaController(this));
				videoView.requestFocus();
				videoView.start();
				try{
				videoView.setOnErrorListener(new OnErrorListener() {
					@Override
					public boolean onError(MediaPlayer mp, int what, int extra) {
						// TODO Auto-generated method stub
						Log.e(TAG, "what : " + what + " , extra: " + extra);
						if (what == 1) {
							// MyToast.makeText(VideoPlayerActivity.this,
							// "无法播放此视频", Toast.LENGTH_SHORT).show();
							finish();
						}
						return false;
					}
				});
				}catch(Exception e){
					e.printStackTrace();
				}
				
			} else {
				Toast.makeText(this, "无法播放视频地址:" + vedioUrl, Toast.LENGTH_SHORT).show();
				finish();
			}
		}

		videoView.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				System.out.println("finish-------------");
				Toast.makeText(VideoPlayerActivity.this, "结束！", Toast.LENGTH_SHORT).show();
				finish();
			}
		});

	}

	String vedioUrl;

	private void getDataFromIntent() {
		Intent intent = getIntent();
		vedioUrl = intent.getStringExtra("url");
		// intent.getSerializableExtra("news");
		// vedioUrl = news.getMp4Url();
	}
	public void onResume(){
		super.onResume();
		CmccDataStatistics.onStart(this);
		}
	public void onPause() {
		super.onPause();
		CmccDataStatistics.onStop(this);
		}
}