package com.szcmcc.movie.network;

import java.io.IOException;

import org.apache.http.HttpException;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.szcmcc.movie.bean.CinemaPrepareInfo;
import com.szcmcc.movie.bean.CinemaPrepareMovie;
import com.szcmcc.movie.bean.CinemaPrepareMovieCinema;
import com.szcmcc.movie.bean.FlowStatisticsBean;
import com.szcmcc.movie.bean.GetOrderTypeBeanInfo;
import com.szcmcc.movie.bean.LockOrDebLockMovieSeatsInfo;
import com.szcmcc.movie.bean.MovieCinemaList;
import com.szcmcc.movie.bean.MovieInfo;
import com.szcmcc.movie.bean.MoviePinLunList;
import com.szcmcc.movie.bean.MovieSeatInfo;
import com.szcmcc.movie.bean.Order;
import com.szcmcc.movie.bean.OrderBySeat;
import com.szcmcc.movie.bean.OrderPay;
import com.szcmcc.movie.bean.OrderPayOk;
import com.szcmcc.movie.bean.OrderPayOkBySeat;
import com.szcmcc.movie.bean.OrderQuery;
import com.szcmcc.movie.bean.OrderQueryBySeat;
import com.szcmcc.movie.bean.ReSendOrderBean;
import com.szcmcc.movie.bean.SeatOrderList;
import com.szcmcc.movie.bean.SendShort;
import com.szcmcc.movie.bean.UpDateBean;
import com.szcmcc.movie.bean.UserBean;
import com.szcmcc.movie.bean.ValidateCodeBeanInfo;
import com.szcmcc.movie.bean.YouhuiDetailInfoBean;
import com.szcmcc.movie.bean.YouhuiInfoBean;
import com.szcmcc.movie.bean.ZSQBaseBean;
import com.szcmcc.movie.bean.ZSQClassicsPersonDetailBean;
import com.szcmcc.movie.bean.ZengquanQueryBeanInfo;

public class MovieLib {
	private static MovieLib movieLib;
	private MovieRequest movieRequest;
	private MovieParse movieParse;
	private MoviePinLunParse moviePinLunParse;
	private MovieCinemaParse movieCinemaParse;
	private MovieCinemaPrepareParse movieCinemaPrepareParse;
	private MoviePrepareParse moviePrepareParse;
	private MovieCinemaAndMoviePrepareParse movieCinemaAndMoviePrepareParse;
	private MovieOrderParse movieOrderParse;
	private MovieOrderPayParse movieOrderPayParse;
	private MovieOrderPayOkParse movieOrderPayOkParse;
	private MovieOrderQueryParse movieOrderQueryParse;
	private MovieSendShordParse movieSendShordParse;
	private OrderSeatParse orderSeatParse;
	private OrdePayOkBySeatParse ordePayOkBySeatParse;
	private OrderQueryBySeatParse orderQueryBySeatParse;
	private UpDateParse upDateParse;
	private OrdersParse ordersParse;
	private ReSendParse reSendParse;
	private MovieYouhuiParse movieYouhuiParse;
	private MovieYouhuiDetailParse movieYouhuiDetailParse;
	private MovieZengquanParse movieZengquanParse;
	private MovieGetOrderTypeParse movieGetOrderTypeParse;
	private ValidateCodeBeanInfoParse validateCodeBeanInfoParse;
	private Context ctx;

	private ZSQParse zsqParse;

	private MovieLib(Context context) {
		movieRequest = new MovieRequest(context);
		movieParse = new MovieParse(context);
		moviePinLunParse = new MoviePinLunParse(context);
		movieCinemaParse = new MovieCinemaParse(context);
		movieCinemaPrepareParse = new MovieCinemaPrepareParse(context);
		moviePrepareParse = new MoviePrepareParse(context);
		movieCinemaAndMoviePrepareParse = new MovieCinemaAndMoviePrepareParse(context);
		movieOrderParse = new MovieOrderParse(context);
		movieOrderPayParse = new MovieOrderPayParse(context);
		movieOrderPayOkParse = new MovieOrderPayOkParse(context);
		moviePrepareParse = new MoviePrepareParse(context);
		movieOrderQueryParse = new MovieOrderQueryParse(context);
		movieSendShordParse = new MovieSendShordParse(context);
		orderSeatParse = new OrderSeatParse(context);
		ordePayOkBySeatParse = new OrdePayOkBySeatParse(context);
		orderQueryBySeatParse = new OrderQueryBySeatParse(context);
		upDateParse = new UpDateParse(context);
		ordersParse = new OrdersParse(context);
		reSendParse = new ReSendParse(context);
		movieYouhuiParse = new MovieYouhuiParse(context);
		movieZengquanParse = new MovieZengquanParse(context);
		movieGetOrderTypeParse = new MovieGetOrderTypeParse(context);
		validateCodeBeanInfoParse = new ValidateCodeBeanInfoParse(context);
		movieYouhuiDetailParse = new MovieYouhuiDetailParse(context);
		zsqParse = new ZSQParse();
		ctx = context;
	}

	public synchronized static MovieLib getInstance(Context context) {
		if (movieLib == null) {
			movieLib = new MovieLib(context);
		}
		return movieLib;
	}

	/**
	 * 获取全部影片
	 * 
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public MovieInfo getAllMovie(String upcomming) throws HttpException, IOException, JSONException {
		String json = movieRequest.getAllMovieRequest(upcomming);
		System.out.println("-----getAllMovie------json-------  "+ json);
		return zsqParse.parseMovieInfo(json);
	}

	public MovieInfo getMoiveDetail(String upcomming, String m_id) throws HttpException,
			IOException, JSONException {
		String json = movieRequest.getMovieDetailRequest(upcomming, m_id);
		Log.i("zzz", "getMovieDetail = " + json);
		return zsqParse.parseMovieInfo(json);
	}

	/**
	 * 添加评论
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
	 * @throws JSONException
	 */
	public MoviePinLunList addComment(String m_id, String content, String uid, String token,
			String rate, String upcoming) throws HttpException, IOException, JSONException {
		String json = movieRequest.addCommentRequest(m_id, content, uid, token, rate, upcoming);
		// System.out.println("json  --  addComment      " + json);
		return moviePinLunParse.parseMoviePinLunList(json);
	}

	/**
	 * 获取全部电影院
	 * 
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public MovieCinemaList getCinema(Context context) throws HttpException, IOException,
			JSONException {
		String json = movieRequest.getCinemaRequest();
		System.out.println("---------getCinema-----------json------------  "+json);
		return movieCinemaParse.parseMovieCinemaInfo(json);
	}

	/**
	 * 演员详情
	 * 
	 * @param context
	 * @param personId
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public ZSQBaseBean<ZSQClassicsPersonDetailBean> getClassicsPersonDetail(Context context,
			String personId) throws HttpException, IOException, JSONException {
		String json = movieRequest.getClassicsPersonDetailRequest(personId);
		return zsqParse.parseClassicsPersonDetail(json);
	}

	/**
	 * 获取票房排行
	 * 
	 * @param context
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public String getMovieRanking(Context context, String rankArea) throws HttpException,
			IOException, JSONException {
		String json = movieRequest.getMovieRankingRequest(rankArea);
		// System.out.println("电影排行——"+json);
		return json;
	}

	/**
	 * 获取头条新闻
	 * 
	 * @param context
	 * @param curPage
	 * @param type
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public String getMovieNews(Context context, String curPage, String type) throws HttpException,
			IOException, JSONException {
		String json = movieRequest.getMovieNewsRequest(curPage, type);
		if (type.equals("1")) {
			Log.i("zzz", "头条新闻 = " + json);
		} else {
			Log.i("zzz", "电影资讯 = " + json);
		}

		return json;
	}

	/**
	 * 获取头条新闻详情
	 * 
	 * @param context
	 * @param newsId
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public String getNewsDetail(Context context, String newsId) throws HttpException, IOException,
			JSONException {
		String json = movieRequest.getNewsDetailRequest(newsId);
		Log.i("zzz", "头条新闻详情 = " + json);
		return json;
	}

	/**
	 * 获取精彩影评
	 * 
	 * @param context
	 * @param curPage
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public String getWonderfulComment(Context context, String curPage) throws HttpException,
			IOException, JSONException {
		String json = movieRequest.getWonderfulCommentRequest(curPage);
		Log.i("zzz", "精彩影评 = " + json);
		return json;
	}

	/**
	 * 获取精彩影评详情
	 * 
	 * @param context
	 * @param commendId
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public String getWonderfulCommentDetail(Context context, String commendId)
			throws HttpException, IOException, JSONException {
		String json = movieRequest.getWonderfulCommentDetailRequest(commendId);
		Log.i("zzz", "精彩影评详情 = " + json);
		return json;
	}

	/**
	 * 获取经典人物
	 * 
	 * @param context
	 * @param curPage
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public String getClassicsPerson(Context context, String curPage) throws HttpException,
			IOException, JSONException {
		String json = movieRequest.getClassicsPersonRequest(curPage);
		return json;
	}

	/**
	 * 获取抵扣券
	 * 
	 * @param context
	 * @param curPage
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public String getCouponInfos(Context context, String curPage, String phone)
			throws HttpException, IOException, JSONException {
		String json = movieRequest.getCouponInfosRequest(curPage, phone);
		Log.i("zzz", "抵扣券  = " + json);
		return json;
	}

	/**
	 * 获取经典台词
	 * 
	 * @param context
	 * @param curPage
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public String getClassicsWords(Context context, String curPage) throws HttpException,
			IOException, JSONException {
		String json = movieRequest.getClassicsWordsRequest(curPage);
		Log.i("zzz", "经典台词  = " + json);
		return json;
	}

	/**
	 * 通过影院id获取影院排期
	 * 
	 * @param c_id
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public CinemaPrepareInfo getMoviesAndCouponByCinema(String c_id) throws HttpException,
			IOException, JSONException {
		String json = movieRequest.getMoviesAndCouponByCinemaRequest(c_id);
		 System.out.println("json  --  getMoviesAndCouponByCinema    -   " +
		 json);
		return movieCinemaPrepareParse.parseMovieCinemaPrepareInfo(json);
	}

	/**
	 * 通过影片id获取影院排期
	 * 
	 * @param m_id
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public CinemaPrepareMovie getMoviesAndCouponByMovie(String m_id) throws HttpException,
			IOException, JSONException {
		String json = movieRequest.getMoviesAndCouponByMovieRequest(m_id);
		System.out.println("---------getMoviesAndCouponByMovie----通过影片获取影院排期 -------json------------  "+json);
		return moviePrepareParse.parseMovieCinemaPrepareInfo(json);
	}

	/**
	 * 获得登录数据的接口请求
	 * 
	 * @param userName
	 * @param password
	 * @param loginType
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public UserBean getLogin(String userName, String password, String loginType)
			throws HttpException, IOException, JSONException {
		String json = movieRequest.getLoginRequest(userName, password, loginType);
		// System.out.println("json  --  getLogin        " + json);
		return moviePrepareParse.parseLoginInfo(json);
	}

	/**
	 * 获得动态密码
	 * 
	 * @param phoneNum
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public String getAutoPwd(String phoneNum) throws HttpException, IOException, JSONException {
		String json = movieRequest.getAutoPwdRequest(phoneNum);
		System.out.println("phoneNum===" + phoneNum);
		System.out.println("json  --  getLogin        " + json);
		return moviePrepareParse.parseAutoPwd(json);
	}

	/**
	 * 通过影院id和影片id 获取电影票信息
	 * 
	 * @param c_id
	 * @param m_id
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public CinemaPrepareMovieCinema getTicketByMovieAndCinema(String c_id, String m_id)
			throws HttpException, IOException, JSONException {
		String json = movieRequest.getTicketByMovieAndCinemaRequest(c_id, m_id);
		// System.out.println("json  --  getTicketByMovieAndCinema        " +
		// json);
		return movieCinemaAndMoviePrepareParse.parseMovieCinemaPrepareInfo(json);
	}

	/**
	 * 生成订单（兑换票）
	 * 
	 * @param c_id
	 * @param payPhone
	 * @param recvPhone
	 * @param count
	 * @param payType
	 * @param orderType
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public Order generateOrder(String c_id, String payPhone, String recvPhone, String count,
			String payType, String orderType) throws HttpException, IOException, JSONException {
		String json = movieRequest.generateOrderRequest(c_id, payPhone, recvPhone, count, payType,
				orderType);
		// System.out.println("json  --  generateOrder:" + json);
		return movieOrderParse.parseMovieOrder(json);
	}

	/**
	 * 生成订单（座位票）
	 * 
	 * @param c_id
	 * @param payPhone
	 * @param recvPhone
	 * @param count
	 * @param payType
	 * @param orderType
	 * @param lockSerialNo
	 * @param showCode
	 * @param seatCode
	 * @param seatRow
	 * @param seatCol
	 * @param couponName
	 * @param couponCount
	 * @param price
	 * @param lockNum
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public OrderBySeat generateOrder(String c_id, String payPhone, String recvPhone, String count,
			String payType, String orderType, String lockSerialNo, String showCode,
			String seatCode, String seatRow, String seatCol, String couponName, String couponCount,
			String price, String lockNum, String channel) throws HttpException, IOException,
			JSONException {
		String json = movieRequest.generateOrderRequest(c_id, payPhone, recvPhone, count, payType,
				orderType, lockSerialNo, showCode, seatCode, seatRow, seatCol, couponName,
				couponCount, price, lockNum, channel);
		 System.out.println("json  --  generateOrder:" + json);
		return orderSeatParse.parseOrderBySeat(json);
	}

	/**
	 * 支付订单
	 * 
	 * @param orderid
	 * @param orderType
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public OrderPay payment(String orderid, String orderType, String payType) throws HttpException,
			IOException, JSONException {
		String json = movieRequest.paymentRequest(orderid, orderType, payType);
		// System.out.println("json  --  payment        " + json);
		// return movieOrderPayParse.parseMovieOrderPay(json);
		return null;
	}

	/**
	 * 支付订单(座位票)
	 * 
	 * @param orderid
	 * @param orderType
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public OrderPay paySeatMent(String orderid, String orderType, String payType)
			throws HttpException, IOException, JSONException {
		String json = movieRequest.paymentRequest(orderid, orderType, payType);
		// System.out.println("json  --  payment        " + json);
		return movieOrderPayParse.parseMovieOrderPay(json);
	}

	/**
	 * 确认订单（通过兑换票购买）
	 * 
	 * @param orderid
	 * @param orderType
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public OrderPayOk orderConfirm(String orderid, String orderType) throws HttpException,
			IOException, JSONException {
		String json = movieRequest.orderConfirmRequest(orderid, orderType);
		// System.out.println("json  --  orderConfirm        " + json);
		return movieOrderPayOkParse.parseMovieOrderPayOk(json);
	}

	/**
	 * 确认订单（通过座位票购买）
	 * 
	 * @param orderid
	 * @param orderType
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public OrderPayOkBySeat orderConfirmBySeat(String orderid, String orderType)
			throws HttpException, IOException, JSONException {
		String json = movieRequest.orderConfirmRequest(orderid, orderType);
		// System.out.println("json  --  orderConfirmBySeat        " + json);
		return ordePayOkBySeatParse.parseOrderPayOkBySeat(json);
	}

	/**
	 * 获取座位信息
	 * 
	 * @param string
	 * @return
	 */
	public MovieSeatInfo getMovieSeat(String showCode) throws HttpException, IOException,
			JSONException {
		String json = movieRequest.getMovieSeatRequest(showCode);
		System.out.println("-------------getMovieSeat------------json---------   "+json);
		MovieSeatInfo movieSeatInfo = movieParse.parseMovieSeatInfo(json);
		return movieSeatInfo;
	}

	/**
	 * 锁定解锁电影座位
	 * 
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public LockOrDebLockMovieSeatsInfo getLockMovieSeats(String c_id, String showCode,
			String lockedtype, String ticketcount, String recvPhone, String seatCode,
			String seatRow, String seatCol, String seatAreaCode, String price)
			throws HttpException, IOException, JSONException {
		String json = movieRequest.getLockMovieSeatsRequest(c_id, showCode, lockedtype,
				ticketcount, recvPhone, seatCode, seatRow, seatCol, seatAreaCode, price);
		System.out.println("锁定座位得到返回的数据：" + json);
		LockOrDebLockMovieSeatsInfo lockMovieSeatsInfo = movieParse
				.parseLockOrDebLockMovieSeatsInfo(json);
		// System.out.println("dongdianzhouSeatSelectedActivity"
		// + lockMovieSeatsInfo.toString() + lockMovieSeatsInfo.result.message);
		return lockMovieSeatsInfo;
	}

	/**
	 * 查询订单信息（兑换票）
	 * 
	 * @param orderid
	 * @param orderType
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public OrderQuery queryOrderInfo(String orderid, String orderType) throws HttpException,
			IOException, JSONException {
		String json = movieRequest.queryOrderInfoRequest(orderid, orderType);
		// System.out.println("json  --  queryOrderInfo        " + json);
		return movieOrderQueryParse.parseMovieOrderQuery(json);
	}

	/**
	 * 查询订单信息（座位票）
	 * 
	 * @param orderid
	 * @param orderType
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public OrderQueryBySeat queryOrderInfoBySeat(String orderid, String orderType)
			throws HttpException, IOException, JSONException {
		String json = movieRequest.queryOrderInfoRequest(orderid, orderType);
		// System.out.println("json  --  queryOrderInfoBySeat        " + json);
		return orderQueryBySeatParse.parseOrderQueryBySeat(json);
	}

	/**
	 * 短信发送
	 * 
	 * @param recvPhone
	 * @param orderid
	 * @param orderType
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public SendShort sendShort(String recvPhone, String orderid, String orderType)
			throws HttpException, IOException, JSONException {
		String json = movieRequest.queryOrderInfoRequest(orderid, orderType);
		// System.out.println("json  --  sendShort        " + json);
		return movieSendShordParse.parseMovieSendShort(json);
	}

	public UpDateBean update(String appkey, String channel, String appversion)
			throws HttpException, IOException, JSONException {
		String json = movieRequest.updateRequest(appkey, channel, appversion);
		// System.out.println("json  --  update        " + json);
		return upDateParse.update(json);
	}

	public SeatOrderList getOrders(String phone) throws HttpException, IOException, JSONException {
		String json = movieRequest.getOrdersRequest(phone);
		 System.out.println("json  --  getOrders      " + json);
		return ordersParse.parseSeatOrderList(json);
	}

	public ReSendOrderBean reSendOrder(String orderid, String orderType) throws HttpException,
			IOException, JSONException {
		String json = movieRequest.reSendOrderRequest(orderid, orderType);
		// System.out.println("json  --  reSendOrder      " + json);
		return reSendParse.reSendOrderParse(json);
	}

	// /**
	// * 最新优惠
	// *
	// * @return
	// * @throws HttpException
	// * @throws IOException
	// * @throws JSONException
	// */
	// public YouhuiInfoBean getDiscountActivity() throws HttpException,
	// IOException, JSONException {
	// String json = movieRequest.getDiscountActivityRequest();
	// System.out.println("json  --  getDiscountActivity      " + json);
	// return movieYouhuiParse.parseMovieInfo(json);
	// }

	/**
	 * 赠券查询记录
	 * 
	 * @param phone
	 * @param curPage
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public ZengquanQueryBeanInfo getPresentedTickets(String phone, String curPage)
			throws HttpException, IOException, JSONException {
		String json = movieRequest.getPresentedTicketsRequest(phone, curPage);
		// System.out.println("json  --  getPresentedTickets      " + json);
		return movieZengquanParse.parseMovieInfo(json);
	}

	public GetOrderTypeBeanInfo getMovieGetOrderType(String c_id, String channel, String type)
			throws HttpException, IOException, JSONException {
		String json = movieRequest.getPayTypeCouponRequest(c_id, channel, type);
		 System.out.println("json  --  getMovieGetOrderType      " + json);
		return movieGetOrderTypeParse.parseGetOrderTypeBeanInfo(json);
	}

	public ValidateCodeBeanInfo getValidateCodeBeanInfo(String phone, String code, String showCode,
			String couponName, String channel) throws HttpException, IOException, JSONException {
		String json = movieRequest.getValidateCodeRequest(phone, code, showCode, couponName,
				channel);
		// System.out.println("json  --  getValidateCodeBeanInfo      " + json);
		return validateCodeBeanInfoParse.parseValidateCodeBeanInfo(json);
	}

	/***
	 * 
	 * @param activity_class
	 *            1为优惠 2为活动
	 * @param startDate
	 *            YYYY-MM-DD eg: 2013-03-05
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public YouhuiInfoBean getYouhuiActivity(String activity_class, String startDate)
			throws HttpException, IOException, JSONException {
		String json = movieRequest.getYouhuiActivityRequest(activity_class, startDate);
		// System.out.println("json  --  getYouhuiActivity      " + json);
		return movieYouhuiParse.parseMovieInfo(json);
	}

	public YouhuiDetailInfoBean getYouhuiDetailActivity(String activityId) throws HttpException,
			IOException, JSONException {
		String json = movieRequest.getYouhuiDetailActivityRequest(activityId);
		// System.out.println("json  --  getYouhuiDetailActivity      " + json);
		return movieYouhuiDetailParse.parseMovieInfo(json);
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
	public FlowStatisticsBean FlowStatisticsRequest(String ver, String IMEI, String IMSI,
			String moblie, String two, String three, String wlan, String stime, String etime)
			throws HttpException, IOException, JSONException {
		String json = movieRequest.getFlowStatisticsRequest(ver, IMEI, IMSI, moblie, two, three,
				wlan, stime, etime);
		// System.out.println(" getFlowStatisticsRequest-----json------   " +
		// json);
		return parseFlowStatistics(ctx, json);
	}

	public FlowStatisticsBean parseFlowStatistics(Context ctx, String json) {
		try {
			JSONObject jsonObj = new JSONObject(json);
			FlowStatisticsBean bean = new FlowStatisticsBean();
			return bean.parseJSON(jsonObj);
		} catch (Exception e) {
			return null;
		}
	}

}
