package cn.edu.njupt.allgo.service.dao;

import cn.edu.njupt.allgo.service.vo.EventAddVo;

public interface EventAddDAO {

	/**
	 * 添加活动补充
	 * @param uid
	 * @param eid
	 * @param text
	 * @return
	 */
	public EventAddVo creatAdd(int eid ,String addtime,String text);
	
}
