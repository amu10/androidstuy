package com.szcmcc.movie.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.szcmcc.movie.util.Log;

public class NetManger {

	public static boolean checkNetWork(Context context) {
		boolean isAvailable = netWorkIsAvailable(context);
		if (!isAvailable) {//
			// openDialog(context);
			return false;
		}
		return true;
	}

	public static boolean netWorkIsAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null) {
			if (activeNetInfo.isAvailable()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	private static void openDialog(final Context context) {
		final Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("没有可用的网络");
		builder.setMessage("请开启GPRS或WIFI网络连接");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Intent mIntent = new Intent("/");
				ComponentName comp = new ComponentName("com.android.settings",
						"com.android.settings.WirelessSettings");
				mIntent.setComponent(comp);
				mIntent.setAction("android.intent.action.VIEW");
				context.startActivity(mIntent);
				((Activity) context).finish();

			}
		}).setNeutralButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		}).create().show();

	}

	public static String doPost(String reqUrl, HashMap<String, String> hashMap) {

		DefaultHttpClient httpclient = new DefaultHttpClient();

		ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		HttpPost httppost = new HttpPost(reqUrl);
		Log.i("request :" + reqUrl);
		InputStream content = null;
		String returnConnection = null;

		if (hashMap != null) {
			Set<String> keys = hashMap.keySet();
			for (Iterator<String> i = keys.iterator(); i.hasNext();) {
				String key = (String) i.next();
				String value = (String) hashMap.get(key);
				if (key == null || key.trim().length() == 0 || value == null
						|| value.trim().length() == 0) {
					continue;
				}
				Log.i(key + " : " + value);
				pairs.add(new BasicNameValuePair(key, value));
			}
		}
		int responseCode = 0;
		try {
			UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(pairs,
					"utf-8");

			httppost.addHeader("Accept-Encoding", "gzip");

			/** ��POST��ݷ���http���� */
			httppost.setEntity(p_entity);

			/** ����ʵ�ʵ�HTTP POST���� */
			long start = System.currentTimeMillis();
			HttpResponse response = httpclient.execute(httppost);
			long end = System.currentTimeMillis();
			Log.i("total : " + (end - start));
			responseCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			content = entity.getContent();

			Header gzipHeader = response.getFirstHeader("Content-Encoding");
			if (gzipHeader != null
					&& gzipHeader.getValue().equalsIgnoreCase("gzip")) {
				content = new GZIPInputStream(content);
				returnConnection = convertStreamToString(content);
			} else {
				returnConnection = convertStreamToString(content);
			}

		} catch (Exception uee) {
			uee.printStackTrace();
		}
		try {
			if (content != null) {
				content.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		httpclient.getConnectionManager().shutdown();
		Log.i("" + returnConnection);
		return returnConnection;
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
