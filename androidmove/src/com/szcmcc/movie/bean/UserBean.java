package com.szcmcc.movie.bean;

import org.json.JSONObject;

public class UserBean extends BaseBean<UserBean>{
	/*
	 *{
"result":1,
"uid":"11",
"token":"fe8c54dbc62644e30bd77e9047b5cdd8",
"username":"iPhuan",
"mobile":"13922820065",
"record":"15005",
"rank":"1",
"traffic_rep_no":549,
"report_weizhang_record":60,
"report_weizhang_num":4
} 
	 */
	public String uid;
	public String token;
	public String username;
	public String mobile;
	public String record;
	public String rank;
	public String traffic_rep_no;
	public String report_weizhang_record;
	public String report_weizhang_num;
	public String error_no;
	public String error_msg;
	public int result;
	@Override
	public String toString() {
		return "UserBean [result=" + result + ", uid=" + uid + ", token=" + token + ", username="
				+ username + ", mobile=" + mobile + ", record=" + record
				+ ", rank=" + rank + ", traffic_rep_no=" + traffic_rep_no
				+ ", report_weizhang_record=" + report_weizhang_record
				+ ", report_weizhang_num=" + report_weizhang_num
				+ ", error_no=" + error_no + ", error_msg=" + error_msg + "]";
	}
	@Override
	public BaseBean<UserBean> parseJSON(JSONObject paramJSONObject) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
