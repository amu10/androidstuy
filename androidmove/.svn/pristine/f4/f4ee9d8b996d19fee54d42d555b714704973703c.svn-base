package com.szcmcc.movie.activity;

import java.io.IOException;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.szcmcc.movie.MovieApplication;
import com.szcmcc.movie.R;
import com.szcmcc.movie.bean.FlowStatisticsBean;
import com.szcmcc.movie.network.MovieAsyncTask;
import com.szcmcc.movie.network.MovieLib;
import com.szcmcc.movie.storage.SharedPreferencesUtil;
import com.szcmcc.movie.util.PublicUtils;
import com.szcmcc.movie.view.ToastAlone;

public class BaseFragmentActivity extends FragmentActivity{
	SharedPreferencesUtil spUtil;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		spUtil = SharedPreferencesUtil.getInstance(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		SharedPreferencesUtil.getInstance(this).saveEtime(PublicUtils.getEtime());
	}

	public void ExitQuest(){
		MovieApplication.isCanClose = true;
		SharedPreferencesUtil spUtil = SharedPreferencesUtil.getInstance(BaseFragmentActivity.this);
		spUtil.saveEtime(PublicUtils.getEtime());
		String mobile = "";
		try {
			mobile = spUtil.getUserName();
		} catch (Exception e) {}
		new GetFlowStatisticsTask(BaseFragmentActivity.this).execute(MovieApplication.getAppVersionName(BaseFragmentActivity.this),  PublicUtils.getIMEI(BaseFragmentActivity.this), PublicUtils.getIMSI(BaseFragmentActivity.this),
				mobile, PublicUtils.getDouble(spUtil.get2G()), PublicUtils.getDouble(spUtil.get3G()), PublicUtils.getDouble(spUtil.getWIFI()), spUtil.getStime(), spUtil.getEtime());
	}
	
	class GetFlowStatisticsTask extends MovieAsyncTask<String, Integer, FlowStatisticsBean> {
		 String exception = null;
		  public GetFlowStatisticsTask(Activity activity) {
			  super(activity, null, true, true, false);
		  }

		  @Override
		  protected void onPreExecute() {
		//   super.onPreExecute();
		  }

		  @Override
		  protected FlowStatisticsBean doInBackground(String... params) {
		   FlowStatisticsBean json = null;
		  
		   MovieLib lib = MovieLib.getInstance(BaseFragmentActivity.this);
		   try {
		    json = lib.FlowStatisticsRequest(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8]);
		   } catch (HttpException e) {
		    exception = getResources().getString(R.string.exception_network);
		   } catch (IOException e) {
		    exception = getResources().getString(R.string.exception_network);
		   } catch (JSONException e) {
		    exception = getResources().getString(R.string.exception_json);
		   } catch (NullPointerException e){
		    e.printStackTrace();
		   }
		   return json;
		  }

		  @Override
		  protected void onPostExecute(FlowStatisticsBean result) {
		   super.onPostExecute(result);
		   if (exception != null) {
		    ToastAlone.showToast(BaseFragmentActivity.this, exception, Toast.LENGTH_SHORT);
		   }
		   if (result != null) {
		    if("1".equals(result.result)) {
		     //提交成功
		     spUtil.save2G(0);//初始化2G流量数据
		     spUtil.save3G(0);//初始化3G流量数据
		     spUtil.saveWIFI(0);//初始化WIFI流量数据
		     spUtil.savePostFlowStatistics(true);
		    }
		   } else {
//		    ToastAlone.showToast(LoadingActivity.this, R.string.error_msg, Toast.LENGTH_SHORT);
		   }
		   System.out.println("MovieApplication.isCanClose==="+MovieApplication.isCanClose);
		   if(MovieApplication.isCanClose){
		   System.exit(0);
		   }
//		// 判断SDK版本
//			int sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
//			// 如果是2.2返回sdkVersion =8
//
//			// 加判断
//			ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//			if (sdkVersion < 8) {
//				manager.restartPackage(getPackageName());
//				System.exit(0);
//			} else {
//				Intent startMain = new Intent(Intent.ACTION_MAIN);
//				startMain.addCategory(Intent.CATEGORY_HOME);
//				startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(startMain);
//				System.exit(0);
//
//			}
		  }

		 }
}
