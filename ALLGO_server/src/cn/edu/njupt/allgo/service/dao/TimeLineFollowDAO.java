package cn.edu.njupt.allgo.service.dao;

import java.util.List;

import cn.edu.njupt.allgo.service.vo.EventVo;

public interface TimeLineFollowDAO {

	/**
	 * 获取我参加的活动
	 * @param uid
	 * @param page
	 * @param pagenum
	 * @return
	 */
	public List<EventVo> getFollowEvent(int uid , int page , int pagenum);
	
}
