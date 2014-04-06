package cn.edu.njupt.allgo.logic;

import cn.edu.njupt.allgo.vo.UserDataVo;
import android.content.Context;

public interface UserDataLogic {
	
	/**
	 * 从本地获取UserData
	 * @param context
	 * @return
	 */
	public void initUserData () ;
	
	/**
	 * 从网络获取UserData
	 * @param context
	 * @return
	 */
	public void getUserData () ;
	
	/**
	 * 存储UserData
	 * @param context
	 * @return
	 */
	public void saveUserData () ;
}
