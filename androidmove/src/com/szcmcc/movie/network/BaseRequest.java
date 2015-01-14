package com.szcmcc.movie.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.szcmcc.movie.bean.YinlianZhifuBean;
import com.szcmcc.movie.storage.SharedPreferencesUtil;
import com.szcmcc.movie.util.Md5Util;
import com.szcmcc.movie.util.PublicUtils;

public class BaseRequest {
	private DefaultHttpClient client;
	private final String KEY = "hg7er32sao9y";
private Context ctx;
	public static ArrayList<YinlianZhifuBean> list ;
	public BaseRequest(Context ctx) {
		this.ctx = ctx;
		HttpParams params = new BasicHttpParams();
		// 设置一些基本参数
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		HttpProtocolParams.setUseExpectContinue(params, true);
		HttpProtocolParams.setUserAgent(params, "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
				+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
		// 超时设置
		/* 从连接池中取连接的超时时间 */
		ConnManagerParams.setTimeout(params, 1000);
		/* 连接超时 */
		HttpConnectionParams.setConnectionTimeout(params, 50000);
		/* 请求超时 */
		HttpConnectionParams.setSoTimeout(params, 30000);

		// 设置我们的HttpClient支持HTTP和HTTPS两种模式
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

		// 使用线程安全的连接管理来创建HttpClient
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
		client = new DefaultHttpClient(conMgr, params);
	}

	/**
	 * 多参数的get请求
	 * 
	 * @param params
	 * @param url
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getRequest(List<NameValuePair> params, String url) throws HttpException, IOException {
		String result = null;

		String strURL;

		StringBuffer sb = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (NameValuePair nvp : params) {
				sb.append(nvp.getName()).append('=').append(nvp.getValue()).append('&');
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		String paramsStr = sb.toString();
		if (paramsStr != null && !paramsStr.equals("")) {
			strURL = url + "?" + paramsStr;
		} else {
			strURL = url;
		}

		HttpGet request = new HttpGet(strURL);
		HttpResponse response = client.execute(request);
		int httpStatusCode = response.getStatusLine().getStatusCode();
		if (httpStatusCode == HttpStatus.SC_OK) {
			result = EntityUtils.toString(response.getEntity());
		} else {
			throw new HttpException("Error Response:" + response.getStatusLine().toString());
		}

		return result;
	}

	/**
	 * get请求，无参数，return InputStream
	 * 
	 * @param url
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public InputStream getRequest(String url) throws HttpException, IOException {
		InputStream result = null;
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		int httpStatusCode = response.getStatusLine().getStatusCode();
		if (httpStatusCode == 200) {
			result = response.getEntity().getContent();
		} else {
			throw new HttpException("Error Response:" + response.getStatusLine().toString());
		}
		return result;
	}

	/**
	 * 多参数的post请求
	 */
	public String postRequest(List<NameValuePair> params, String url) throws HttpException, IOException {
		String result = null;
		HttpPost request = new HttpPost(url);
		try{
//			System.out.println("params   "+params);
			String data = parseJSON(params);
			saveNetworkFlow(data);
		HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		request.setEntity(entity);
		HttpResponse response = client.execute(request);
//		System.out.println("Baserequest：" + url + request);
		
		int httpStatusCode = response.getStatusLine().getStatusCode();
		if (httpStatusCode == HttpStatus.SC_OK) {
			result = EntityUtils.toString(response.getEntity());
		} 
		saveNetworkFlow(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 多参数，多文件的post请求
	 * 
	 * @param stringParams
	 * @param fileParams
	 * @param url
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String postRequest(List<NameValuePair> stringParams, List<NameValuePair> fileParams, String url) throws HttpException, IOException {
		String result = null;
		HttpPost request = new HttpPost(url);

		MultipartEntity entity = new MultipartEntity();
		for (NameValuePair snv : stringParams) {
			entity.addPart(snv.getName(), new StringBody(snv.getValue(), Charset.forName("UTF-8")));
		}

		for (NameValuePair fnv : fileParams) {
			File file = new File(fnv.getValue());
			if (file.isFile() && !file.isDirectory()) {
				entity.addPart(fnv.getName(), new FileBody(file));
			}
		}

		request.setEntity(entity);

		HttpResponse response = client.execute(request);

		int httpStatusCode = response.getStatusLine().getStatusCode();
		if (httpStatusCode == HttpStatus.SC_OK) {
			result = EntityUtils.toString(response.getEntity());
		} else {
			throw new HttpException("Error Response:" + response.getStatusLine().toString());
		}

		return result;
	}

	public static String poststh(String postUrl, String content) {
		URL url = null;

		try {
			url = new URL(postUrl);// POST_URL为你要post的目标地址
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		HttpURLConnection uc = null;
//		OutputStreamWriter out = null;
		BufferedReader rd = null;
		StringBuffer sb = new StringBuffer();
		InputStream in = null;
		
		try {
			// uc = (HttpURLConnection) url.openConnection();
			// uc.setRequestMethod("POST");
			// uc.setDoOutput(true); //....必须设置为'true'.
			// uc.setRequestProperty("Pragma:", "no-cache");
			// uc.setRequestProperty("Cache-Control", "no-cache");
			// uc.setRequestProperty("Content-Type", "text/xml");
			// out = new OutputStreamWriter(uc.getOutputStream(),"utf-8");
			// out.write(content);
			// out.flush();
			// out.close();

//			URL url = new URL(urlPath);
//			 打开连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置提交方式
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			// post方式不能使用缓存
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			// 设置连接超时时间
			conn.setConnectTimeout(20 * 1000);
			// 配置本次连接的Content-Type，配置为application/x-www-form-urlencoded
			conn.setRequestProperty("Content-Type", "text/xml");
			// 维持长连接
			conn.setRequestProperty("Connection", "Keep-Alive");
			// 设置浏览器编码
			conn.setRequestProperty("Charset", "UTF-8");
			
//			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
//			// 将请求参数数据向服务器端发送
//			dos.writeBytes(content);
//			dos.flush();
//			dos.close();
			 OutputStreamWriter out = new OutputStreamWriter(conn
	                    .getOutputStream());    
	            out.write(new String(content.getBytes("UTF-8")));
	            out.flush();
	            out.close();
			 in = conn.getInputStream();
			rd = new BufferedReader(new InputStreamReader(in, "utf-8"));
			
			String responseStr = "";
			while ((responseStr = rd.readLine()) != null) {
//				System.out.println("发送结果=" + responseStr);// 控制台打印出返回结果,如果调试成功这里可以注释掉.
			sb.append(responseStr);
			}

			rd.close();
			
//	            URLConnection con = url.openConnection();
//	            con.setDoOutput(true);
//	            con.setRequestProperty("Pragma:", "no-cache");
//	            con.setRequestProperty("Cache-Control", "no-cache");
//	            con.setRequestProperty("Content-Type", "text/xml");
//	           
//	            OutputStreamWriter out = new OutputStreamWriter(con
//	                    .getOutputStream());    
//	            out.write(new String(content.getBytes("UTF-8")));
//	            out.flush();
//	            out.close();
//	            
//	            BufferedReader br = new BufferedReader(new InputStreamReader(con
//	                    .getInputStream()));
//	           
//	            String line = "";
//	            for (line = br.readLine(); line != null; line = br.readLine()) {
//	                System.out.println(line);
//	            }

			try {
				
				list = YinlianZhifuParse.getBean(in);
//				System.out.println("list=------------    "+list.get(0).merchantId+"        "+list.get(0).version);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			return responseStr;
		} catch (IOException e) {

			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(NullPointerException e){
				e.printStackTrace();
			}
		}

		return sb.toString();
	}
	
	/**
	 * 把xml 字符串解析成list
	 * @param xmlStr
	 * @return
	 */
	public static ArrayList parseXMLStr(InputStream inStream) {
		if(inStream != null){
//			Log.i(TAG," parseXMLStr");
			ArrayList returnList = null ;
			Boolean startEntryElementFlag = false; 
//			Log.i(TAG,"xmlStr: "+xmlStr);
			XmlPullParserFactory pullFactory;
			try {
					//创建XmlPullParser,有两种方式  
				//方式一:使用工厂类XmlPullParserFactory  
				pullFactory = XmlPullParserFactory.newInstance();
				XmlPullParser xmlPullParser = pullFactory.newPullParser();  
				//          方式二:使用Android提供的实用工具类android.util.Xml  
				//          XmlPullParser xmlPullParser = Xml.newPullParser();  
				 xmlPullParser.setInput(inStream, "UTF-8");  
				 int eventType = xmlPullParser.getEventType(); 
//				 Log.i(TAG,"eventType : "+eventType);
				 boolean isDone = false;   
				  //具体解析xml  
				 while ((eventType != XmlPullParser.END_DOCUMENT)&&(isDone != true)) {
		               String localName = null;  
		               switch (eventType) {
		                   case XmlPullParser.START_DOCUMENT:  
		                   {
//		                	   Log.i(TAG,"START_DOCUMENT");
		                	   returnList = new ArrayList();  
		                   }  
		                       break;  
		                   case XmlPullParser.START_TAG:  
//		                   {
		                       localName = xmlPullParser.getName();  
//		                    }  
		                        break;  
		                    case XmlPullParser.END_TAG:  
//		                    {
		                        localName = xmlPullParser.getName();  
//		                        Log.i(TAG,"END_TAG localName : "+localName);
//		                        if((localName.equalsIgnoreCase(KEY_ns2))&&(startEntryElementFlag==true))  
//		                        {
//		                        	returnList.add(illegalCarNoItemBean);  
//		                            startEntryElementFlag = false;  
//		                        }  
//		                    }  
		                        break;  
//					case XmlPullParser.TEXT:
////					{
//						String text = xmlPullParser.getText();
////
////						Log.e(TAG, " TEXT: " + text);
//						// 递归出来 <![CDATA[......]]的数据
////						if (isReadCDATA) {
//							ByteArrayInputStream bais = new ByteArrayInputStream(
//									text.getBytes());
//							returnList = 	parseXMLResult(bais);
//							return returnList;
//							parseXMLStr(bais);
//							isReadCDATA = false;
//						}
//					}
		                    default: {
		                    	
		                    	break;  
		                    	}
		                    }  
		                eventType = xmlPullParser.next();  
		            }
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}
			return returnList;
		}
		return null;
	}
	
	/**
	  * 用于流量统计
	  * @param params
	  * @param url
	  * @return
	  * @throws HttpException
	  * @throws IOException
	  */
	 public String postFlowStatisticsRequest(List<NameValuePair> params, List<NameValuePair> network, String url) throws HttpException, IOException {
	  String result = null;
	  HttpPost request = new HttpPost(url);
	  String data = parseFlowStatisticsJSON(params, network);
	  saveNetworkFlow(data);
	  
	  request.setEntity(new StringEntity(data,HTTP.UTF_8));
	  HttpResponse response = client.execute(request);
	  int httpStatusCode = response.getStatusLine().getStatusCode();
	  if (httpStatusCode == HttpStatus.SC_OK) {
	   result = EntityUtils.toString(response.getEntity(),HTTP.UTF_8);
	  } else {
	//   throw new HttpException("Error Response:" + response.getStatusLine().toString());
	  }
	  saveNetworkFlow(result);
	  return result;
	 }
	 
	 /**
	  * 用于流量统计
	  * @param params
	  * @return
	  */
	 private String parseFlowStatisticsJSON(List<NameValuePair> params, List<NameValuePair> network){
	  JSONObject json = new JSONObject();
	  Collections.sort(params, new Comparator<NameValuePair>(){
	   
	   @Override
	   public int compare(NameValuePair lhs, NameValuePair rhs) {
	    return lhs.getName().compareTo(rhs.getName());
	   }
	   
	  });
	  StringBuilder sb = new StringBuilder();
	  try {
	   for (NameValuePair pair : params) {
	    sb.append(pair.getValue());
	    json.put(pair.getName(), pair.getValue());
	   }
	   if(null != network) {
	    json.put("network", getNetworkJSON(network));
	   }
	   Log.e("SkeyUtils", "json:" + json.toString());
	   return json.toString();
	  } catch (JSONException e) {
	   e.printStackTrace();
	  }
	  
	  return "";
	 }

	 /**
	  * 保存流量数据
	  * @param data
	  */
	 private void saveNetworkFlow(String data) {
	  if(TextUtils.isEmpty(data)) {
	   return;
	  }
	  //判断网络状态
	  String network = PublicUtils.ConnectionFast(ctx);
	  SharedPreferencesUtil spUtil = SharedPreferencesUtil.getInstance(ctx);
	  if(null != network) {
	   if("WIFI".equals(network)) {
	    int length = spUtil.getWIFI();
	    length = length + data.getBytes().length;
	    spUtil.saveWIFI(length);
	   } else if("2G".equals(network)) {
	    int length = spUtil.get2G();
	    length = length + data.getBytes().length;
	    spUtil.save2G(length);
	   } else if("3G".equals(network)) {
	    int length = spUtil.get3G();
	    length = length + data.getBytes().length;
	    spUtil.save3G(length);
	   }
	  }
	 }
	 
	 /**
	  * 保存流量数据
	  * @param data
	  */
	 private void saveNetworkFlow(long data) {
	  if(0 == data) {
	   return;
	  }
	  //判断网络状态
	  String network = PublicUtils.ConnectionFast(ctx);
	  SharedPreferencesUtil spUtil = SharedPreferencesUtil.getInstance(ctx);
	  if(null != network) {
	   if("WIFI".equals(network)) {
	    int length = spUtil.getWIFI();
	    length = (int) (length + data);
	    spUtil.saveWIFI(length);
	   } else if("2G".equals(network)) {
	    int length = spUtil.get2G();
	    length = (int) (length + data);
	    spUtil.save2G(length);
	   } else if("3G".equals(network)) {
	    int length = spUtil.get3G();
	    length = (int) (length + data);
	    spUtil.save3G(length);
	   }
	  }
	 }
	 
	 private JSONObject getNetworkJSON(List<NameValuePair> params) {
			JSONObject json = new JSONObject();
			Collections.sort(params, new Comparator<NameValuePair>(){

				@Override
				public int compare(NameValuePair lhs, NameValuePair rhs) {
					return lhs.getName().compareTo(rhs.getName());
				}
				
			});
			StringBuilder sb = new StringBuilder();
			try {
				for (NameValuePair pair : params) {
					sb.append(pair.getValue());
					json.put(pair.getName(), Double.parseDouble(pair.getValue()));
				}
				return json;
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}
	 
	 public String parseJSON(List<NameValuePair> params) {
			JSONObject json = new JSONObject();
			Collections.sort(params, new Comparator<NameValuePair>(){

				@Override
				public int compare(NameValuePair lhs, NameValuePair rhs) {
					return lhs.getName().compareTo(rhs.getName());
				}
				
			});
			StringBuilder sb = new StringBuilder();
			try {
				for (NameValuePair pair : params) {
					sb.append(pair.getValue());
					json.put(pair.getName(), pair.getValue());
				}
				sb.append(KEY);
				String skey = Md5Util.getMD5Str(sb.toString());
				json.put("sign", skey);
//				Log.e("SkeyUtils", "json:" + json.toString());
				return json.toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return "";
		}


}
