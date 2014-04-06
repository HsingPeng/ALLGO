package cn.edu.njupt.allgo.service.dao;

import java.util.List;

import cn.edu.njupt.allgo.service.vo.UnreadVo;

public interface UnreadDAO {

	/**
	 * 获取未读消息
	 * @param uid
	 * @return
	 */
	public List<UnreadVo> getUnread(int uid);
	
	
}
