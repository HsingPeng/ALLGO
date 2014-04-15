package cn.edu.njupt.allgo.service.dao;

import cn.edu.njupt.allgo.service.vo.UserDataVo;

public interface RegisterDAO {

	/**
	 * 注册
	 * @param uname
	 * @param usex
	 * @param uemail
	 * @param upassword
	 * @param string 
	 * @return
	 */
	public UserDataVo register(String uname, int usex, String uemail,
			String upassword, String uregdate);
	
	/**
	 * 检测邮箱,用户名是否存在
	 * @param uname
	 * @param usex
	 * @param uemail
	 * @param upassword
	 * @return
	 */
	public String isExist(String uname,String uemail);
}
