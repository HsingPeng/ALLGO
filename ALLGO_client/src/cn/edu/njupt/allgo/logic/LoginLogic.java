package cn.edu.njupt.allgo.logic;

import android.content.Context;

public interface LoginLogic {
	
	/**
	 * 登录
	 * @param name
	 * @param password
	 */
	public void login(String name , String password);
	
	/**
	 * 取消登录
	 */
	public void cancelLogin() ;
	
	/**
	 * 检查是否已经登录
	 */
	public void isLogin();
	
	/**
	 * 从服务器下线
	 */
	public void logOff();
	
	/**
	 * 直接下线
	 */
	public void logOffLocal();
	
	/**
	 * 设置服务器地址
	 */
	public void setURL();
	
	/**
	 * 删除用户数据
	 */
	public void logOffDB();
}
