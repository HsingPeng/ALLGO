package cn.edu.njupt.allgo.service.dao;

import cn.edu.njupt.allgo.service.vo.UserDataVo;

public interface LoginDAO {
	
	/**
	 * 登录账号
	 * @param username
	 * @param password
	 * @return
	 */
	public UserDataVo login(String username,String password);
}
