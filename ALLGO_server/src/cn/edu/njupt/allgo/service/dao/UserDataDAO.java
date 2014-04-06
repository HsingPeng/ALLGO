package cn.edu.njupt.allgo.service.dao;

import cn.edu.njupt.allgo.service.vo.UserDataVo;

public interface UserDataDAO {

	/**
	 * 获取用户信息
	 * @param uid
	 * @return
	 */
	public UserDataVo getUserData(int uid);
	
}
