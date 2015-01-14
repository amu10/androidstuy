package com.szcmcc.movie.activity;

import java.io.IOException;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.sdk.CmccDataStatistics;
import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.UserBean;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.storage.SharedPreferencesUtil;

/**
 * 28-动态密码登 录 29-服务密码登 录
 * 
 * @author Administrator
 * 
 */
public class UserLoginAct extends Activity {
	public static final int FLAG_LOGIN = 1;
	private ImageView imgLeft = null;
	private ImageView imgRight = null;

	private Button buttonPwdRight = null;
	private Button buttonPwdLeft = null;

	private TextView textUserNum = null;
	private TextView textUserPwd = null;
	private Button buttonLogin = null; // 登录按钮
	private Button buttonGetAutopwd = null;
	private TextView textPassType;// 密码文字
	SharedPreferencesUtil spUtil;
	Intent intent;
	MovieLib lib;
	private ImageButton imBack;
	private String entry;

	public void release() {
		imgLeft = null;
		imgRight = null;
		textUserNum = null;
		textUserPwd = null;
		buttonLogin = null;
		buttonGetAutopwd = null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userloginact);
		intent = getIntent();
		entry = intent.getStringExtra("entry");
		spUtil = SharedPreferencesUtil.getInstance(this);
		lib = MovieLib.getInstance(this);
		findViewByIds();
		setListener();

	}

	private void findViewByIds() {
		imgLeft = (ImageView) findViewById(R.id.userlogin_image_pwd_auto);
		imgRight = (ImageView) findViewById(R.id.userlogin_image_pwd_server);
		buttonPwdRight = (Button) findViewById(R.id.userlogin_button_pwd_auto);
		buttonPwdLeft = (Button) findViewById(R.id.userlogin_button_pwd_server);
		buttonPwdRight.setEnabled(true);
		buttonPwdRight.setSelected(false);
		buttonPwdLeft.setEnabled(false);
		buttonPwdLeft.setSelected(true);
		textUserNum = (TextView) findViewById(R.id.autopwd_num);
		textUserNum.setText(spUtil.getUserName());
		textUserPwd = (TextView) findViewById(R.id.autopwd_pass);
		buttonLogin = (Button) findViewById(R.id.login);
		buttonGetAutopwd = (Button) findViewById(R.id.getautopwd);
		textPassType = (TextView) findViewById(R.id.userlogin_pass_type_text);
		imBack = (ImageButton) findViewById(R.id.imBack);
	}

	private void setListener() {
		buttonLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String userNum = "";
				String userPwd = "";
				String userType = "2";
				userNum = textUserNum.getText().toString();
				if (TextUtils.isEmpty(userNum)) {
					Toast.makeText(UserLoginAct.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if (userNum.length() < 11) {
					Toast.makeText(UserLoginAct.this, "手机号位数不够", Toast.LENGTH_SHORT).show();
					return;
				}
				if (buttonPwdRight.isSelected()) {
					// 动态密码 默认请求
					userPwd = textUserPwd.getText().toString();
					if (TextUtils.isEmpty(userPwd)) {
						Toast.makeText(UserLoginAct.this, "动态密码不能为空", Toast.LENGTH_SHORT).show();
						return;
					}
					userType = "2";
					// 请求网络

				} else if (buttonPwdLeft.isSelected()) {
					// 服务密码 请求
					userPwd = textUserPwd.getText().toString();
					if (TextUtils.isEmpty(userPwd)) {
						Toast.makeText(UserLoginAct.this, "服务密码不能为空", Toast.LENGTH_SHORT).show();
						return;
					}
					userType = "1";
					// 请求网络

				} else {
					// 错误
					Toast.makeText(UserLoginAct.this, "密码类型异常", Toast.LENGTH_SHORT).show();
					return;
				}
				new GetLoginInfoTask(UserLoginAct.this).execute(userNum, userPwd, userType);
				spUtil.saveLoginType(userType);
			}
		});

		// 点击动态密码
		buttonPwdRight.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// rightAnim();
				leftAnim();
				buttonGetAutopwd.setVisibility(View.VISIBLE);
				textPassType.setText("动态密码:");
				textUserPwd.setHint("请输入动态密码");
			}
		});

		// 点击服务密码
		buttonPwdLeft.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// leftAnim();
				rightAnim();
				buttonGetAutopwd.setVisibility(View.GONE);
				textPassType.setText("服务密码:");
				textUserPwd.setHint("请输入服务密码");
			}
		});

		buttonGetAutopwd.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 获得动态密码短信息
				// 发送网络请求
				String phoneNum = textUserNum.getText().toString();
				if (TextUtils.isEmpty(phoneNum)) {
					Toast.makeText(UserLoginAct.this, "请输入手机号", Toast.LENGTH_SHORT).show();
					return;
				}
				if (phoneNum.length() < 11) {
					Toast.makeText(UserLoginAct.this, "手机号位数不够", Toast.LENGTH_SHORT).show();
					return;
				}
				new GetAutoPwdTask(UserLoginAct.this).execute(phoneNum);

			}
		});

		imBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * 从右边移动到左边的动画
	 */
	public void rightAnim() {
		// Animation rightAnim = AnimationUtils.loadAnimation(this,
		// R.anim.push_left_out);
		TranslateAnimation rightAnim = new TranslateAnimation(Animation.ABSOLUTE, 0,
				Animation.ABSOLUTE, -100, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
		rightAnim.setDuration(300);
		// rightAnim.setInterpolator(new AccelerateInterpolator());//加速器
		rightAnim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				imgLeft.clearAnimation();
				imgRight.clearAnimation();
				imgRight.setVisibility(View.INVISIBLE);
				imgLeft.setVisibility(View.VISIBLE);
			}
		});
		imgRight.setAnimation(rightAnim);
		buttonPwdRight.setSelected(false);
		buttonPwdRight.setTextColor(getResources().getColor(android.R.color.white));
		buttonPwdLeft.setSelected(true);
		buttonPwdLeft.setTextColor(getResources().getColor(android.R.color.black));
		buttonPwdRight.setEnabled(true);
		buttonPwdLeft.setEnabled(false);
	}

	/**
	 * 从左边移动到右边的动画
	 */
	public void leftAnim() {
		// Animation leftAnim =
		// AnimationUtils.loadAnimation(this,R.anim.push_right_out);
		TranslateAnimation leftAnim = new TranslateAnimation(Animation.ABSOLUTE, 0,
				Animation.ABSOLUTE, 100, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
		leftAnim.setDuration(300);
		// leftAnim.setInterpolator(new DecelerateInterpolator());
		// leftAnim.setInterpolator(new AccelerateInterpolator());//加速器
		leftAnim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				imgLeft.clearAnimation();
				imgRight.clearAnimation();
				imgLeft.setVisibility(View.INVISIBLE);
				imgRight.setVisibility(View.VISIBLE);
			}
		});
		imgLeft.setAnimation(leftAnim);
		buttonPwdRight.setSelected(true);
		buttonPwdRight.setTextColor(getResources().getColor(android.R.color.black));
		buttonPwdLeft.setSelected(false);
		buttonPwdLeft.setTextColor(getResources().getColor(android.R.color.white));
		buttonPwdRight.setEnabled(false);
		buttonPwdLeft.setEnabled(true);

	}

	class GetLoginInfoTask extends MovieAsyncTask<String, String, UserBean> {
		private static final String TAG = "GetLoginInfoTask";
		public String exception;

		public GetLoginInfoTask(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected UserBean doInBackground(String... params) {
			UserBean bean = null;
			try {
				bean = lib.getLogin(params[0], params[1], params[2]);
			} catch (HttpException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (IOException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (JSONException e) {
				exception = getResources().getString(R.string.exception_json);
				e.printStackTrace();
			} catch(Exception e){
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			}
			return bean;
		}

		@Override
		protected void onPostExecute(UserBean result) {
			super.onPostExecute(result);
			if (result == null) {
				Toast.makeText(UserLoginAct.this, "登录异常", Toast.LENGTH_SHORT).show();
				spUtil.clearAllData();
				return;
			}
			if (result.result != 1) {
//				if (result.error_msg.equals("password error")) {
//					Toast.makeText(UserLoginAct.this, "密码错误！", Toast.LENGTH_SHORT).show();
//				}
//				if (result.error_msg.equals("user is't exists")) {
//					Toast.makeText(UserLoginAct.this, "用户名不存在", Toast.LENGTH_SHORT).show();
//				} else {
//					System.out.println("result.error_msg    " + result.error_msg);
//					Toast.makeText(UserLoginAct.this, "登录失败", Toast.LENGTH_SHORT).show();
//				}
					Toast.makeText(UserLoginAct.this, result.error_msg, Toast.LENGTH_SHORT).show();
				spUtil.clearAllData();
				return;
			}
			Toast.makeText(UserLoginAct.this, "登录成功", Toast.LENGTH_SHORT).show();
			CmccDataStatistics.Comm_Login(UserLoginAct.this, textUserNum.getText().toString());
			spUtil.savePassword(textUserNum.getText().toString(), textUserPwd.getText().toString());
			spUtil.saveUid(result.uid, result.token);
			intent.putExtra("UserBean", result);
			if (entry == null) {
				setResult(FLAG_LOGIN, intent);
			} else if (entry.equals(MovieApplication.ENTRY_MY_ZOON)) {
				intent = new Intent(UserLoginAct.this, MyZoneAct.class);
				startActivity(intent);
			} else {
				setResult(FLAG_LOGIN, intent);
			}
			UserLoginAct.this.finish();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	class GetAutoPwdTask extends MovieAsyncTask<String, String, String> {
		private static final String TAG = "GetLoginInfoTask";
		public String exception;

		public GetAutoPwdTask(Activity activity) {
			super(activity, null, true, true, true);
		}

		@Override
		protected String doInBackground(String... params) {
			String autoPwdStr = "";
			try {
				autoPwdStr = lib.getAutoPwd(params[0]);
			} catch (HttpException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (IOException e) {
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			} catch (JSONException e) {
				exception = getResources().getString(R.string.exception_json);
				e.printStackTrace();
			} catch(Exception e){
				exception = getResources().getString(R.string.exception_network);
				e.printStackTrace();
			}
			return autoPwdStr;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (TextUtils.isEmpty(result)) {
				Toast.makeText(UserLoginAct.this, "由于您的手机号登陆失败超过3次，请24小时后再尝试", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			Toast.makeText(UserLoginAct.this, result, Toast.LENGTH_SHORT).show();
			Log.e(TAG, "result:" + result.toString());
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	public void onResume() {
		super.onResume();
		CmccDataStatistics.onStart(this);
	}

	public void onPause() {
		super.onPause();
		CmccDataStatistics.onStop(this);
	}
}
