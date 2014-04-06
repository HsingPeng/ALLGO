package cn.edu.njupt.allgo.service.dao;

import cn.edu.njupt.allgo.service.vo.EventVo;

public interface EventDestroyDAO {

	/**
	 * 删除活动
	 * @param eid
	 * @return
	 */
	public EventVo destroyEvent(int eid,int uid);
	
}
