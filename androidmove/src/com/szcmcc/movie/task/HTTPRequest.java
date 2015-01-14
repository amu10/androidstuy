/**
 * Copyright (C) 2011 David Schonert
 *
 * This file is part of BlueSky.
 *
 * BlueSky is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * BlueSky is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BlueSky.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.szcmcc.movie.task;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import android.content.Context;

import com.szcmcc.movie.util.Log;

public class HTTPRequest {

	private URL url;

	private Map<String, String> params = null;
	private String encode = "UTF-8";
	private Context context = null;

	private HttpURLConnection conn = null;
	private String urlString = null;

	public HTTPRequest(Context context, String urlString,
			Map<String, String> params) {
		super();
		this.params = params;
		this.context = context;
		this.urlString = urlString;
	}

	public HTTPRequest(Context context, String urlString,
			Map<String, String> params, String encode) {
		super();
		this.params = params;
		this.encode = encode;
		this.context = context;
		this.urlString = urlString;
	}

	public HTTPRequest(String urlString, Map<String, String> params) {
		super();
		this.params = params;
		this.urlString = urlString;
	}

	private InputStream getInputStream() throws Exception {
		StringBuilder parambuilder = new StringBuilder("");
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				parambuilder.append(entry.getKey()).append("=")
						.append(URLEncoder.encode(entry.getValue(), encode))
						.append("&");
			}
			parambuilder.deleteCharAt(parambuilder.length() - 1);
		}
		byte[] data = parambuilder.toString().getBytes();

		url = new URL(urlString);
		conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);// 允许对外发送请求参数
		conn.setUseCaches(false);// 不进行缓存
		conn.setConnectTimeout(5 * 1000);
		conn.setRequestMethod("POST");
		// 下面设置http请求头
		conn.setRequestProperty(
				"Accept",
				"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		conn.setRequestProperty("Accept-Language", "zh-CN");
		conn.setRequestProperty(
				"User-Agent",
				"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		conn.setRequestProperty("Connection", "Keep-Alive");

		// 发送参数
		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(data);// 把参数发送出去
		outStream.flush();
		outStream.close();
		Log.i("-------.BaseParser.post-ResponseCode" + conn.getResponseCode());
		if (conn.getResponseCode() == 200) {
			// 访问正常用就把数据保下来
			int contenlength = conn.getContentLength() / 4;
			Log.i("-------.BaseParser.post-contenlength" + contenlength);
			// if(context instanceof ThreeGVolumeListner){
			// ThreeGVolumeListner listener=(ThreeGVolumeListner) context;
			// listener.sendInformMessageDeal();}
			// if (NetWorkInfo.isIswifi()) {
			// SettingSharePreference.updateWIFI(context, contenlength);
			// } else {
			// SettingSharePreference.updateGPRS(context, contenlength);
			// }
			return conn.getInputStream();
		}
		return null;
	}

	protected String getServerResponseString() {
		byte bytes[];
		String result = null;
		try {
			bytes = readStream(getInputStream());
			result = new String(bytes, encode);
			Log.i("-------..BaseParser.getServerResponseString()\n" + result
					+ "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 读取流
	 * 
	 * @param inStream
	 * @return 字节数组
	 * @throws Exception
	 */
	private byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[10240];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

	public void stopConnection() {
		Log.i("-------.BaseParser.stopConnection()---");
		conn.disconnect();
	}

}