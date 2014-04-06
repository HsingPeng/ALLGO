package cn.edu.njupt.allgo.service.dao;

import cn.edu.njupt.allgo.service.vo.EventFollowerVo;

public interface EventFollowDAO {

	/**
	 * 加入活动
	 * @param eid
	 * @param uname
	 * @param uid
	 * @return
	 */
	public EventFollowerVo follow(int eid,String uname ,int uid);
	
	/**
	 * 检测是否已经跟随
	 * @param eid
	 * @param uid
	 * @return
	 */
	public EventFollowerVo isfollowed(int eid,int uid);
	
}
