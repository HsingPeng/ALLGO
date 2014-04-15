package cn.edu.njupt.allgo.service.dao;

import cn.edu.njupt.allgo.service.vo.UserDataVo;

public interface UpdateUserDetailDAO {

	
	public UserDataVo updateUserDetail(int uid , int usex , String ubirthday,String uaddress, String usatement);
	
}
