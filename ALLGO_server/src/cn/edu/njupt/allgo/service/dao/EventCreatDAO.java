package cn.edu.njupt.allgo.service.dao;

import cn.edu.njupt.allgo.service.vo.EventVo;

public interface EventCreatDAO {

	/**
	 * 添加活动
	 * @param outline
	 * @param uid
	 * @param uname
	 * @param startdate
	 * @param enddate
	 * @param content
	 * @param place
	 * @param position
	 * @param ecategoryname
	 * @param visible
	 * @return
	 */
	public EventVo creatEvent(String outline,int uid,String uname,String startdate,
			String enddate,String content,String place,String position ,String ecategoryname,int visible);	
}
