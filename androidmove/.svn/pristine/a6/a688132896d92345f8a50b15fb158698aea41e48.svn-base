package com.szcmcc.movie.network;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.util.Log;

public class MovieRequest {
	/**
	 * 存放普通参数的list name--value
	 */
	// private List<NameValuePair> strParams;
	/**
	 * 存放上传文件的list name--filePath
	 */
	// private List<NameValuePair> fileParams;
	// ?userId=att&userPass=att&methodVit=GETALLMOVIE
	/**
	 * 测试接口
	 */
//	private final String url = "http://210.21.197.66:4928/szwxcsMovieInterface/MovieTicketServlet?userId=att&userPass=att";
//	private final String url = "http://210.21.197.66:8001/szwxcsMovieInterface/MovieTicketServlet?userId=att&userPass=att";
	 private final String url =
	 "http://120.196.125.11:26182/MovieTicketServlet?userId=att&userPass=att";//
	// 正式接口

	private final String youhuiUrl = "http://120.196.125.11:26149/mbYxActivity!getMbYxActivityList.action?spId=yxpt_yingshi&spPwd=yingshi1234";// 正式
	// private final String youhuiUrl =
	// "http://211.139.143.67:6000/mbYxActivity!getMbYxActivityList.action?spId=yxpt_tssq&spPwd=tssq1234";
	// private final String youhuiDetail =
	// "http://211.139.143.67:6000/mbYxActivity!getMbYxActivityDetail.action?spId=yxpt_tssq&spPwd=tssq1234";
	private final String youhuiDetail = "http://120.196.125.11:26149/mbYxActivity!getMbYxActivityDetail.action?spId=yxpt_yingshi&spPwd=yingshi1234";// 正式
	private final String updateUrl = "http://120.196.125.11:26780/sdk/sdk_vup.php";// 正式
	private final String urlLogin = "http://120.196.125.11:26120";
	/**
	 * 登录接口
	 */
	public static final String LOGIN = "/index.php?m=api&c=member&a=login";
	/**
	 * 获得动态密码接口
	 */
	public static final String LOGIN_DYNAMIC_PWD = "/index.php?m=traffic&c=member&a=getDynamicPassword";

	// 客户端流量统计接口地址
	public static final String flowstatistics_url = "http://120.196.125.11:26780/newv/?r=quantity&key=b3e3b2ee69e8ebc126ff09049eac6af5";

	private BaseRequest baseRequest;
	Context ctx;

	public MovieRequest(Context ctx) {
		// strParams = new ArrayList<NameValuePair>();
		// fileParams = new ArrayList<NameValuePair>();
		baseRequest = new BaseRequest(ctx);
		this.ctx = ctx;
	}

	// private void addShareParams() {
	// strParams.add(new BasicNameValuePair("userId", "att"));
	// strParams.add(new BasicNameValuePair("userPass", "att"));
	// }

	/**
	 * 获取所有电影
	 * 
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getAllMovieRequest(String upcomming) throws HttpException, IOException {
		String methodVit = "GETMOVIELIST";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("upcomming", upcomming));
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	/**
	 * 获取影片详情
	 * 
	 * @param upcomming
	 * @param m_id
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getMovieDetailRequest(String upcomming, String m_id) throws HttpException,
			IOException {
		String methodVit = "GETMOVIEBYID";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("upcomming", upcomming));
		strParams.add(new BasicNameValuePair("m_id", m_id));
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	/**
	 * 获取电影评论
	 * 
	 * @param m_id
	 * @param max_id
	 * @param upcoming
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getCommentsByMovieIdRequest(String m_id, String max_id, String page_size,
			String upcoming) throws HttpException, IOException {
		String methodVit = "GETCOMMENTSBYMOVIEID";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("m_id", m_id));
		strParams.add(new BasicNameValuePair("max_id", max_id));
		strParams.add(new BasicNameValuePair("page_size", page_size));
		strParams.add(new BasicNameValuePair("upcoming", upcoming));
		String result = baseRequest.postRequest(strParams, url);
		// System.out.println("result----------------" + url + "&methodVit="
		// + methodVit + "&m_id=" + m_id + "&max_id=" + max_id
		// + "&page_size=" + page_size + "&upcoming=" + upcoming);
		return result;
	}

	/**
	 * 获取电影评论
	 * 
	 * @param m_id
	 * @param max_id
	 * @param upcoming
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getProfessionCommentsByIdRequest(String m_id, String max_id, String page_size,
			String upcoming) throws HttpException, IOException {
		String methodVit = "GETPROFESSIONCOMMENTSBYID";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("m_id", m_id));
		strParams.add(new BasicNameValuePair("max_id", max_id));
		strParams.add(new BasicNameValuePair("page_size", page_size));
		strParams.add(new BasicNameValuePair("upcoming", upcoming));
		String result = baseRequest.postRequest(strParams, url);
		// System.out.println("result----------------" + url + "&methodVit="
		// + methodVit + "&m_id=" + m_id + "&max_id=" + max_id
		// + "&page_size=" + page_size + "&upcoming=" + upcoming);
		return result;
	}

	/**
	 * 发表评论
	 * 
	 * @param m_id
	 * @param content
	 * @param uid
	 * @param token
	 * @param rate
	 * @param upcoming
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String addCommentRequest(String m_id, String content, String uid, String token,
			String rate, String upcoming) throws HttpException, IOException {
		String methodVit = "ADDCOMMENT";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("m_id", m_id));
		String encodeStr = URLEncoder.encode(content);
		Log.e("Tag ", "content:" + encodeStr);
		strParams.add(new BasicNameValuePair("content", encodeStr));
		strParams.add(new BasicNameValuePair("uid", uid));
		strParams.add(new BasicNameValuePair("token", token));
		strParams.add(new BasicNameValuePair("rate", rate));
		strParams.add(new BasicNameValuePair("upcoming", upcoming));
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	/**
	 * 获取影院信息
	 * 
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getCinemaRequest() throws HttpException, IOException {
		String methodVit = "GETCINEMA";
		Long timestamp = System.currentTimeMillis();
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("timestamp", timestamp.toString()));
		 System.out.println("getCinemaRequest+   url==：     " + url
		 + "&methodVit=GETCINEMA&timestamp=" + timestamp.toString());
		String result = baseRequest.postRequest(strParams, url);

		return result;
	}

	/**
	 * 通过影院获取排期
	 * 
	 * @param c_id
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getMoviesAndCouponByCinemaRequest(String c_id) throws HttpException, IOException {
		String methodVit = "GETMOVIESANDCOUPONBYCINEMA";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("c_id", c_id));
		String result = baseRequest.postRequest(strParams, url);
		 System.out.println("getMoviesAndCouponByCinemaRequest+   url==：     "
		 + url + "&methodVit=GETMOVIESANDCOUPONBYCINEMA" + "&c_id="
		 + c_id);
		return result;
	}

	/**
	 * 通过影片获取排期
	 * 
	 * @param m_id
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getMoviesAndCouponByMovieRequest(String m_id) throws HttpException, IOException {
		String methodVit = "GETMOVIESANDCOUPONBYMOVIE";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("m_id", m_id));
		String result = baseRequest.postRequest(strParams, url);
		 System.out.println("getMoviesAndCouponByMovieRequest：url：     " + url
		 + "&methodVit=GETMOVIESANDCOUPONBYMOVIE&m_id=" + m_id);
		return result;
	}

	/**
	 * 登录请求
	 * 
	 * @param m_id
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getLoginRequest(String userName, String password, String loginType)
			throws HttpException, IOException {
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("phone_no", userName));
		strParams.add(new BasicNameValuePair("password", password));
		strParams.add(new BasicNameValuePair("login_type", loginType));// 1服务密码,2动态密码
		String result = baseRequest.postRequest(strParams, urlLogin + LOGIN);
		return result;
	}

	public String getAutoPwdRequest(String phoneNum) throws HttpException, IOException {
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("phone_no", phoneNum));// 动态密码
		String result = baseRequest.postRequest(strParams, urlLogin + LOGIN_DYNAMIC_PWD);
		return result;
	}

	/**
	 * 通过影院id和影片id获取电影票信息
	 * 
	 * @param c_id
	 * @param m_id
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getTicketByMovieAndCinemaRequest(String c_id, String m_id) throws HttpException,
			IOException {
		String methodVit = "GETTICKETBYMOVIEANDCINEMA";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("m_id", m_id));
		strParams.add(new BasicNameValuePair("c_id", c_id));
		// System.out.println("getTicketByMovieAndCinemaRequest：url：     " + url
		// + "&methodVit=GETTICKETBYMOVIEANDCINEMA&m_id=" + m_id
		// + "&c_id=" + c_id);
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	/**
	 * 生成订单请求
	 * 
	 * @param c_id
	 * @param payPhone
	 * @param recvPhone
	 * @param count
	 * @param payType
	 * @param orderType
	 *            = 0时，下面传null
	 * @param lockSerialNo
	 * @param showCode
	 * @param seatCode
	 * @param seatRow
	 * @param seatCol
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String generateOrderRequest(String c_id, String payPhone, String recvPhone,
			String count, String payType, String orderType) throws HttpException, IOException {
		String methodVit = "GENERATEORDER";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		// System.out.println("payType==   " + payType);
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("c_id", c_id));
		strParams.add(new BasicNameValuePair("payPhone", payPhone));
		strParams.add(new BasicNameValuePair("recvPhone", recvPhone));
		strParams.add(new BasicNameValuePair("count", count));
		strParams.add(new BasicNameValuePair("payType", payType));
		strParams.add(new BasicNameValuePair("orderType", orderType));
		// System.out.println("generateOrderRequest  url----=" + url
		// + "&methodVit=" + methodVit + "&c_id=" + c_id + "&payPhone="
		// + payPhone + "&recvPhone=" + recvPhone + "&count=" + count
		// + "&payType=" + payType + "&orderType=" + orderType);
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	// 通过座位票获取订单
	public String generateOrderRequest(String c_id, String payPhone, String recvPhone,
			String count, String payType, String orderType, String lockSerialNo, String showCode,
			String seatCode, String seatRow, String seatCol, String couponName, String couponCount,
			String price, String lockNum, String channel) throws HttpException, IOException {
		String methodVit = "GENERATEORDER";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("c_id", c_id));
		strParams.add(new BasicNameValuePair("payPhone", payPhone));
		strParams.add(new BasicNameValuePair("recvPhone", recvPhone));
		strParams.add(new BasicNameValuePair("count", count));
		strParams.add(new BasicNameValuePair("payType", payType));
		strParams.add(new BasicNameValuePair("orderType", orderType));
		if (orderType.equals("1")) {
			strParams.add(new BasicNameValuePair("lockSerialNo", lockSerialNo));
			strParams.add(new BasicNameValuePair("showCode", showCode));
			strParams.add(new BasicNameValuePair("seatCode", seatCode));
			strParams.add(new BasicNameValuePair("seatRow", seatRow));
			strParams.add(new BasicNameValuePair("seatCol", seatCol));
			strParams.add(new BasicNameValuePair("couponName", couponName));
			strParams.add(new BasicNameValuePair("couponCount", couponCount));
			strParams.add(new BasicNameValuePair("price", price));
			strParams.add(new BasicNameValuePair("lockNum", lockNum));
			strParams.add(new BasicNameValuePair("channel", channel));
			 System.out.println("generateOrderRequest  url----=" + url
			 + "&methodVit=" + methodVit + "&c_id=" + c_id
			 + "&payPhone=" + payPhone + "&recvPhone=" + recvPhone
			 + "&count=" + count + "&payType=" + payType + "&orderType="
			 + orderType + "&lockSerialNo=" + lockSerialNo
			 + "&showCode=" + showCode + "&seatCode=" + seatCode
			 + "&seatRow=" + seatRow + "&seatCol=" + seatCol
			 + "&couponName=" + couponName + "&couponCount="
			 + couponCount + "&price=" + price + "&lockNum=" + lockNum
			 + "&channel=att");
		}
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	/**
	 * 支付接口请求
	 * 
	 * @param orderid
	 * @param orderType
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String paymentRequest(String orderid, String orderType, String payType)
			throws HttpException, IOException {
		String methodVit = "PAYMENT";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("orderid", orderid));
		strParams.add(new BasicNameValuePair("orderType", orderType));
		strParams.add(new BasicNameValuePair("payType", payType));
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	/**
	 * 订单确认接口请求
	 * 
	 * @param orderid
	 * @param orderType
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String orderConfirmRequest(String orderid, String orderType) throws HttpException,
			IOException {
		String methodVit = "ORDERCONFIRM";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("orderid", orderid));
		strParams.add(new BasicNameValuePair("orderType", orderType));
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	/**
	 * 获取座位信息的请求
	 * 
	 * @param showCode
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public String getMovieSeatRequest(String showCode) throws HttpException, IOException {
		// http://210.21.197.66:8098/szwxcsMovieInterface/MovieTicketServlet?userId=att&userPass=att&methodVit=GETSEATSBYCINEMAANDSCREENINGS&showCode=121312
		String methodVit = "GETSEATSBYCINEMAANDSCREENINGS";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("showCode", showCode));// "121312"默认
		 System.out.println("获取座位信息：url    " + url
		 + "&methodVit=GETSEATSBYCINEMAANDSCREENINGS&showCode="
		 + showCode);
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	/**
	 * 获取锁定电影座位
	 * 
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public String getLockMovieSeatsRequest(String c_id, String showCode, String lockedtype,
			String ticketcount, String recvPhone, String seatCode, String seatRow, String seatCol,
			String seatAreaCode, String price) throws HttpException, IOException {
		String methodVit = "LOCKSEAT";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("c_id", c_id));// "121312"默认
		strParams.add(new BasicNameValuePair("showCode", showCode));// "121312"默认
		strParams.add(new BasicNameValuePair("lockedType", lockedtype));// "121312"默认
		strParams.add(new BasicNameValuePair("ticketCount", ticketcount));// "121312"默认
		strParams.add(new BasicNameValuePair("recvPhone", recvPhone));// "121312"默认
		strParams.add(new BasicNameValuePair("seatCode", seatCode));// "121312"默认
		strParams.add(new BasicNameValuePair("seatRow", seatRow));// "121312"默认
		strParams.add(new BasicNameValuePair("seatCol", seatCol));// "121312"默认
		strParams.add(new BasicNameValuePair("seatAreaCode", seatAreaCode));// "121312"默认
		strParams.add(new BasicNameValuePair("price", price));// "121312"默认
		System.out.println("请求参数：" + strParams);
		 System.out.println("jiechusuodingUrl---------   " + url + "&"
		 + "methodVit=" + methodVit + "&c_id=" + c_id + "&showCode="
		 + showCode + "&lockedType=" + lockedtype + "&ticketCount="
		 + ticketcount + "&recvPhone=" + recvPhone + "&seatCode="
		 + seatCode + "&seatRow=" + seatRow + "&seatCol=" + seatCol
		 + "&seatAreaCode=" + seatAreaCode + "&price=" + price);
		long time = System.currentTimeMillis();
		System.out.println("开始锁定座位：时间戳：" + time);
		String result = baseRequest.postRequest(strParams, url);
		System.out.println("得到锁定结果：时间戳：" + System.currentTimeMillis());
		System.out.println("本次锁定座位用时：" + (System.currentTimeMillis() - time) + "毫秒");
		return result;
	}

	public String queryOrderInfoRequest(String orderid, String orderType) throws HttpException,
			IOException {
		String methodVit = "QUERYORDERINFO";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("orderid", orderid));
		strParams.add(new BasicNameValuePair("orderType", orderType));
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	public String sendShortRequest(String recvPhone, String orderid, String orderType)
			throws HttpException, IOException {
		String methodVit = "SENDSHORT";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("recvPhone", recvPhone));
		strParams.add(new BasicNameValuePair("orderid", orderid));
		strParams.add(new BasicNameValuePair("orderType", orderType));
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	public String updateRequest(String appkey, String channel, String appversion)
			throws HttpException, IOException {
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("appkey", appkey));
		strParams.add(new BasicNameValuePair("channel", channel));
		strParams.add(new BasicNameValuePair("appversion", appversion));
		// System.out.println(updateUrl + "?appkey=" + appkey + "&channel="
		// + channel + "&appversion=" + appversion);
		String result = baseRequest.postRequest(strParams, updateUrl + "?appkey=" + appkey
				+ "&channel=" + channel + "&appversion=" + appversion);

		return result;
	}

	public String getOrdersRequest(String phone) throws HttpException, IOException {
		String methodVit = "GETORDERS";
		// strParams.clear();
		// addShareParams();
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("phone", phone));
		strParams.add(new BasicNameValuePair("type", "att"));
		strParams.add(new BasicNameValuePair("pageSize", "1000"));
		String result = baseRequest.postRequest(strParams, url);
		 System.out.println("strParams      " + strParams);
		 System.out.println("getOrdersRequest+   urlOrders==：     " + url
		 + "&methodVit=GETORDERS&phone=" + phone + "&type=att");
		return result;
	}

	public String reSendOrderRequest(String orderid, String orderType) throws HttpException,
			IOException {
		String methodVit = "RESENDORDER";
		// strParams.clear();
		// addShareParams();
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("orderid", orderid));
		strParams.add(new BasicNameValuePair("orderType", orderType));
		String result = baseRequest.postRequest(strParams, url);
		// System.out.println("strParams      " + strParams);
		// System.out.println("getOrdersRequest+   urlOrders==：     " + url
		// + "&methodVit=RESENDORDER&orderid=" + orderid + "&orderType="
		// + orderType);
		return result;
	}

	/**
	 * 获取演员详细信息接口
	 * 
	 * @param personId
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getClassicsPersonDetailRequest(String personId) throws HttpException, IOException {
		String methodVit = "GETCLASSICSPERSONDETAIL";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("personId", personId));
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	/**
	 * 获取票房信息接口
	 * 
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getMovieRankingRequest(String rankArea) throws HttpException, IOException {
		String methodVit = "GETMOVIERANKING";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("rankArea", rankArea));
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	/**
	 * 获取头条新闻或电影资讯接口
	 * 
	 * @param curPage
	 * @param type
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getMovieNewsRequest(String curPage, String type) throws HttpException,
			IOException {
		String methodVit = "GETMOVIENEWS";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("pageSize", "10"));
		strParams.add(new BasicNameValuePair("curPage", curPage));
		strParams.add(new BasicNameValuePair("type", type));
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	/**
	 * 获取头条新闻详情接口
	 * 
	 * @param newsId
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getNewsDetailRequest(String newsId) throws HttpException, IOException {
		String methodVit = "GETNEWSDETAIL";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("newsId", newsId));
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	/**
	 * 获取精彩影评接口
	 * 
	 * @param curPage
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getWonderfulCommentRequest(String curPage) throws HttpException, IOException {
		String methodVit = "GETWONDERFULCOMMENT";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("pageSize", "10"));
		strParams.add(new BasicNameValuePair("curPage", curPage));
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	/**
	 * 获取精彩影评详情接口
	 * 
	 * @param commendId
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getWonderfulCommentDetailRequest(String commendId) throws HttpException,
			IOException {
		String methodVit = "GETWONDERFULCOMMENTDETAIL";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("commentId", commendId));
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	/**
	 * 获取经典人物接口
	 * 
	 * @param curPage
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getClassicsPersonRequest(String curPage) throws HttpException, IOException {
		String methodVit = "GETCLASSICSPERSON";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("pageSize", "12"));
		strParams.add(new BasicNameValuePair("curPage", curPage));
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	/**
	 * 获取经典台词接口
	 * 
	 * @param curPage
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getClassicsWordsRequest(String curPage) throws HttpException, IOException {
		String methodVit = "GETCLASSICSWORD";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("pageSize", "10"));
		strParams.add(new BasicNameValuePair("curPage", curPage));
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	/**
	 * 获取抵扣券接口
	 * 
	 * @param curPage
	 * @param phone
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getCouponInfosRequest(String curPage, String phone) throws HttpException,
			IOException {
		String methodVit = "GETCOUPONINFOS";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("pageSize", "10"));
		strParams.add(new BasicNameValuePair("curPage", curPage));
		strParams.add(new BasicNameValuePair("phone", phone));
		strParams.add(new BasicNameValuePair("channel", "att"));
		strParams.add(new BasicNameValuePair("couponType", ""));
		// System.out.println("url---getCouponInfosRequest----"+url+"&methodVit="+methodVit+"&pageSize=10"+"&curPage="+curPage+"&phone="+phone+"&channel=att"+"&couponType=");
		String result = baseRequest.postRequest(strParams, url);
		return result;
	}

	/**
	 * 最新优惠请求接口
	 * 
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getDiscountActivityRequest() throws HttpException, IOException {
		String methodVit = "GETDISCOUNTACTIVITY";
		// strParams.clear();
		// addShareParams();
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		String result = baseRequest.postRequest(strParams, url);
		// System.out.println("strParams      " + strParams);
		// System.out.println("getDiscountActivityRequest   url==：     " + url
		// + "&methodVit=GETDISCOUNTACTIVITY");
		return result;
	}

	/**
	 * 赠券记录查询接口
	 * 
	 * @param phone
	 * @param curPage
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getPresentedTicketsRequest(String phone, String curPage) throws HttpException,
			IOException {
		String methodVit = "GETPRESENTEDTICKETS";
		// strParams.clear();
		// addShareParams();
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("phone", phone));
		strParams.add(new BasicNameValuePair("pageSize", ""));
		strParams.add(new BasicNameValuePair("curPage", curPage));
		String result = baseRequest.postRequest(strParams, url);
		// System.out.println("strParams      " + strParams);
		// System.out.println("getPresentedTicketsRequest   url==：     " + url
		// + "&methodVit=GETPRESENTEDTICKETS&phone=" + phone
		// + "&pageSize=&curPage=" + curPage);
		return result;
	}

	/**
	 * 获取支付方式接口
	 * 
	 * @param c_id
	 * @param channel
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getPayTypeCouponRequest(String c_id, String channel, String type) throws HttpException,
			IOException {
		String methodVit = "GETPAYTYPECOUPON";
		// strParams.clear();
		// addShareParams();
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("c_id", c_id));
		strParams.add(new BasicNameValuePair("channel", channel));
		strParams.add(new BasicNameValuePair("type", type));
		 System.out.println("getPayTypeCouponRequest   url==：     " + url
		 + "&methodVit=GETPAYTYPECOUPON&c_id=" + c_id + "&channel="
		 + channel + "&type=" + type);
		String result = baseRequest.postRequest(strParams, url);

		return result;
	}

	public String getValidateCodeRequest(String phone, String code, String showCode,
			String couponName, String channel) throws HttpException, IOException {
		String methodVit = "VALIDATECODE";
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("userId", "att"));
		strParams.add(new BasicNameValuePair("userPass", "att"));
		strParams.add(new BasicNameValuePair("methodVit", methodVit));
		strParams.add(new BasicNameValuePair("phone", phone));
		strParams.add(new BasicNameValuePair("code", code));
		strParams.add(new BasicNameValuePair("showCode", showCode));
		strParams.add(new BasicNameValuePair("couponName", couponName));
		strParams.add(new BasicNameValuePair("channel", channel));
		// System.out.println("getValidateCodeRequest   url==：     " + url
		// + "&methodVit=VALIDATECODE&phone=" + phone + "&code=" + code
		// + "&showCode=" + showCode + "&couponName=" + couponName
		// + "&channel=" + channel);
		String result = baseRequest.postRequest(strParams, url);

		return result;
	}

	/***
	 * 最新优惠请求接口
	 * 
	 * @param activity_class
	 *            1为优惠 2为活动
	 * @param startDate
	 *            YYYY-MM-DD eg: 2013-03-05
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getYouhuiActivityRequest(String activity_class, String startDate)
			throws HttpException, IOException {
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("accessType", "1"));
		strParams.add(new BasicNameValuePair("activity_class", activity_class));
		strParams.add(new BasicNameValuePair("activity_type", "0"));
		strParams.add(new BasicNameValuePair("status", "4"));
		if (!startDate.equals("")) {
			strParams.add(new BasicNameValuePair("startDate", startDate));
		}
		strParams.add(new BasicNameValuePair("pageNo", "1"));
		strParams.add(new BasicNameValuePair("pageCount", "100"));
		String result = baseRequest.postRequest(strParams, youhuiUrl);
		 System.out.println("strParams      " + strParams);
		 System.out.println("------------getYouhuiActivityRequest---------result-----      " + result);
		// System.out.println("getDiscountActivityRequest   url==：     "
		// + youhuiUrl + "&accessType=1&activity_class=" + activity_class
		// + "&activity_type=0&status=4&startDate=" + startDate
		// + "&pageNo=1&pageCount=100");
		return result;
	}

	/***
	 * 最新优惠详情请求接口
	 * 
	 * @param activityId
	 *            活动id
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	// &activityId=A201301072390&accessType=1
	public String getYouhuiDetailActivityRequest(String activityId) throws HttpException,
			IOException {
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		strParams.add(new BasicNameValuePair("activityId", activityId));
		strParams.add(new BasicNameValuePair("accessType", "1"));
		String result = baseRequest.postRequest(strParams, youhuiDetail);
		// System.out.println("strParams      " + strParams);
		// System.out.println("getDiscountActivityRequest   url==：     "
		// + youhuiDetail + "&activityId=" + activityId + "&accessType=1");
		return result;
	}

	/**
	 * 客户端流量统计接口
	 * 
	 * @param ver
	 *            客户端版本号
	 * @param IMEI
	 *            安卓IMEI值
	 * @param IMSI
	 *            移动卡的唯一标识
	 * @param moblie
	 *            手机号
	 * @param two
	 *            2G网络
	 * @param three
	 *            3G网络
	 * @param wlan
	 *            WIFI网络
	 * @param stime
	 *            统计开始时间 单位秒
	 * @param etime
	 *            统计结束时间 单位秒
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getFlowStatisticsRequest(String ver, String IMEI, String IMSI, String moblie,
			String two, String three, String wlan, String stime, String etime)
			throws HttpException, IOException {

		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		if (!TextUtils.isEmpty("appkey")) {
			strParams.add(new BasicNameValuePair("appkey", getAppKey()[0]));
		}
		if (!TextUtils.isEmpty("ver")) {
			strParams.add(new BasicNameValuePair("ver", ver));
		}
		if (!TextUtils.isEmpty("IMEI")) {
			strParams.add(new BasicNameValuePair("IMEI", IMEI));
		}
		if (!TextUtils.isEmpty("UDID")) {
			strParams.add(new BasicNameValuePair("UDID", ""));
		}
		if (!TextUtils.isEmpty("IMSI")) {
			strParams.add(new BasicNameValuePair("IMSI", IMSI));
		}
		if (!TextUtils.isEmpty("moblie")) {
			strParams.add(new BasicNameValuePair("moblie", moblie));
		}
		// if (!TextUtils.isEmpty("network")) {
		// strParams.add(new BasicNameValuePair("network",
		// baseRequest.parseFlowStatisticsJSON(getNameValuePair(two, three,
		// wlan), null)));
		// }
		if (!TextUtils.isEmpty("stime")) {
			strParams.add(new BasicNameValuePair("stime", stime));
		}
		if (!TextUtils.isEmpty("etime")) {
			strParams.add(new BasicNameValuePair("etime", etime));
		}
		return baseRequest.postFlowStatisticsRequest(strParams, getNameValuePair(two, three, wlan),
				flowstatistics_url);
	}

	private List<NameValuePair> getNameValuePair(String two, String three, String wlan) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("2G", two));
		list.add(new BasicNameValuePair("3G", three));
		list.add(new BasicNameValuePair("WLAN", wlan));
		return list;
	}

	private String[] getAppKey() {
		ApplicationInfo info;
		String testData[] = new String[2];
		try {
			info = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(),
					PackageManager.GET_META_DATA);
			testData[0] = info.metaData.getString("SZICITY_APPKEY");
			testData[1] = info.metaData.getString("SZICITY_CHANNEL");
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return testData;
	}
}
