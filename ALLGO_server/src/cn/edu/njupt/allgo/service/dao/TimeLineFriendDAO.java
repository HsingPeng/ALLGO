package cn.edu.njupt.allgo.service.dao;

import java.util.List;

import cn.edu.njupt.allgo.service.vo.EventVo;

public interface TimeLineFriendDAO {

	/**
	 * 获得好友的活动
	 * @param page
	 * @param pagenum
	 * @return
	 */
	public List<EventVo> getFriendEvent(int uid , int page, int pagenum);
	
}
