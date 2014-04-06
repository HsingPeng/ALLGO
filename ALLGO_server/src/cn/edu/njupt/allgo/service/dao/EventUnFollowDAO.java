package cn.edu.njupt.allgo.service.dao;

import cn.edu.njupt.allgo.service.vo.EventFollowerVo;

public interface EventUnFollowDAO {

	/**
	 * 取消加入活动
	 * @param eid
	 * @param uid
	 * @return
	 */
	public boolean unfollow(int eid,int uid);
	
}
