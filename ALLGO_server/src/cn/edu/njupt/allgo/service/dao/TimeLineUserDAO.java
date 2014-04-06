package cn.edu.njupt.allgo.service.dao;

import java.util.List;

import cn.edu.njupt.allgo.service.vo.EventVo;

public interface TimeLineUserDAO {

	/**
	 * 获取我的活动
	 * @param uid
	 * @param page
	 * @param pagenum
	 * @return
	 */
	public List<EventVo> getUserEvent(int uid , int page , int pagenum);

}
