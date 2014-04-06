package cn.edu.njupt.allgo.service.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletHelper {

	private HttpServletRequest request ;
	private HttpServletResponse response ;
	private HttpSession session ;
	Map<String , Object> outMap = new HashMap<String , Object>() ;
	
	public ServletHelper(HttpServletRequest request, HttpServletResponse response){
		this.request = request ;
		this.response = response ;
		session = request.getSession(false);
	}

	/**
	 * 得到request中的参数
	 * @param name
	 * @return String
	 */
	public String getStr(String name){
		String value = request.getParameter(name);
		return value ;
	}

	/**
	 * 得到request中的参数
	 * @param name
	 * @return int
	 */
	public int getInt(String name){
		String value = request.getParameter(name);
		int result = -1 ;
		if(value != null){
			result = Integer.parseInt(value);
		}
		return result ;
	}
	
	/**
	 * 把数据放入response中
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value){
		outMap.put(key,value);
	}
	
	/**
	 * 发送response
	 */
	public void send(){
		CommonUtil.renderJson(response,outMap);
	}
	
	/**
	 * 得到当前格林尼治时间
	 * @return
	 */
	public String time(){
		return DateTimeUtil.currentTime();
	}
	
	/**
	 * 得到当前用户uid
	 * @return
	 */
	public int getUid(){
		int uid = -1;
		if(session != null){
		uid = (int)session.getAttribute("uid");
		}
		return uid;
	}
	
	/**
	 * 得到当前用户uname
	 * @return
	 */
	public String getUname(){
		String uname = null;
		if(session != null){
			uname = (String)session.getAttribute("uname");
		}
		return uname;
	}
	
	/**
	 * 得到当前session,若没有则为null
	 * @return
	 */
	public HttpSession session(){
		return this.session;
	}
	
}
